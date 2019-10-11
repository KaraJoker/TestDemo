package com.cn.company.init;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.cn.company.cqrs.c.service.disruptor.CoreSnapshot;
import com.cn.company.cqrs.c.service.disruptor.ProcessCoreCommandEventHandler;
import com.cn.company.cqrs.c.service.disruptor.SnapshotJsonUtil;
import com.cn.company.util.FileUtil;
import org.eclipse.jetty.client.HttpClient;
import org.springframework.context.ApplicationContext;

import com.highto.framework.ddd.Command;
import com.highto.framework.ddd.CommonCommand;
import com.highto.framework.ddd.SingletonEntityRepository;

public class InitProcessor {
	private HttpClient httpClient;

	private HttpClient sslHttpClient;

	private SnapshotJsonUtil snapshotJsonUtil;

	private ProcessCoreCommandEventHandler coreCommandEventHandler;

	private SingletonEntityRepository singletonEntityRepository;

	private ApplicationContext applicationContext;

	FileUtil fileUtil = new FileUtil();

	public InitProcessor(HttpClient httpClient, HttpClient sslHttpClient, SnapshotJsonUtil snapshotJsonUtil,
			ProcessCoreCommandEventHandler coreCommandEventHandler, SingletonEntityRepository singletonEntityRepository,
			ApplicationContext applicationContext) {
		this.httpClient = httpClient;
		this.sslHttpClient = sslHttpClient;
		this.snapshotJsonUtil = snapshotJsonUtil;
		this.coreCommandEventHandler = coreCommandEventHandler;
		this.singletonEntityRepository = singletonEntityRepository;
		this.applicationContext = applicationContext;
	}

	public void init() {

		httpClient.setFollowRedirects(false);
		try {
			httpClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		sslHttpClient.setFollowRedirects(false);
		try {
			sslHttpClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			recover();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			System.exit(0);
		}
	}

	private void recover() throws Throwable {
		// snapshot 恢复
		// core member snapshot恢复
		CoreSnapshot memberSnapshot = null;
		try {
			memberSnapshot = (CoreSnapshot) snapshotJsonUtil.recovery(coreCommandEventHandler.getSnapshotFileBasePath(),
					CoreSnapshot.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (memberSnapshot != null) {
			singletonEntityRepository.fill(
					(SingletonEntityRepository) memberSnapshot.getContentMap().get(SingletonEntityRepository.class));
			// memberDiamondAccountRepository.fill((MemberDiamondAccountRepository)
			// memberSnapshot.getContentMap()
			// .get(MemberDiamondAccountRepository.class), null);
			// memberCashAccountRepository.fill(
			// (MemberCashAccountRepository)
			// memberSnapshot.getContentMap().get(MemberCashAccountRepository.class),
			// null);
		}

		// core 命令

		List<Command> commands = fileUtil.read(coreCommandEventHandler.getjFileBasePath());
		invokeCommands(commands);

	}

	private void invokeCommands(List<Command> commands)
			throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		for (Command command : commands) {
			if (command instanceof CommonCommand) {
				CommonCommand cmd = (CommonCommand) command;
				Class clazz = Class.forName(cmd.getType());
				Object service = applicationContext.getBean(clazz);
				if (cmd.getParameters() != null && cmd.getParameters().length > 0) {
					try {
						service.getClass().getMethod(cmd.getMethod(), cmd.getParameterTypes()).invoke(service,
								cmd.getParameters());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					service.getClass().getMethod(cmd.getMethod()).invoke(service);
				}
			}
		}
	}

}
