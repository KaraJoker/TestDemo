package com.cn.company.cqrs.c.service.disruptor;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class SnapshotJsonUtil {
	private ObjectMapper objectMapper;
	private JsonFactory factory;

	public SnapshotJsonUtil() {
		factory = new JsonFactory();
		objectMapper = new ObjectMapper();
		// 处理map
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// 禁止未知属性打断反序
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		factory.setCodec(objectMapper);
	}

	public void save(String fileBasePath, String fileName, Object data) throws IOException {
		File file = new File(fileBasePath + "/" + fileName + ".json");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		JsonGenerator jsonGenerator = factory.createGenerator(file, JsonEncoding.UTF8);
		jsonGenerator.useDefaultPrettyPrinter();
		jsonGenerator.writeObject(data);
	}

	public Object recovery(String fileBasePath, Class clazz) throws IOException {

		// System.out.println(clazz.getClass());
		File recentFile = getRecentFile(fileBasePath);
		if (recentFile == null)
			return null;
		Object object = objectMapper.readValue(recentFile, clazz);
		return object;
	}

	public File getRecentFile(String fileBasePath) {
		File folder = new File(fileBasePath);
		// 获得folder文件夹下面所有文件
		File[] files = folder.listFiles();
		File recentFile = null;
		long recentCreateTime = 0;
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					// 获取后缀名
					String fileName = file.getName();
					;
					String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);// 后缀
					String prefix = fileName.substring(0, fileName.lastIndexOf("."));// 文件名
					if (suffix.equals("json")) {
						long createTime = Long.parseLong(prefix);
						if (recentCreateTime < createTime) {
							recentFile = file;
							recentCreateTime = createTime;
						}
					}
				}
			}
		}

		return recentFile;
	}
}
