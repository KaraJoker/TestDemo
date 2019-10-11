package com.cn.company.util;

import com.highto.framework.ddd.Command;
import com.highto.framework.nio.ByteBufferSerializer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 2016/8/30.
 */
public class FileUtil {
	public String getRecentFileName(String fileBasePath) {
		File folder = new File(fileBasePath);
		// 获得folder文件夹下面所有文件
		String[] fileNames = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return true;
			}
		});
		String recentFileName = null;
		long recentCreateTime = 0;
		if (fileNames != null) {
			for (String fileName : fileNames) {
				long createTime = Long.parseLong(fileName);
				if (recentCreateTime < createTime) {
					recentFileName = fileName;
					recentCreateTime = createTime;
				}
			}
		}
		if (recentFileName != null) {
			return fileBasePath + "/" + recentFileName;
		} else {
			return null;
		}
	}

	/**
	 * 注意 若cmd文件很大 。大小超过int最大值时 会有问题
	 *
	 * @param fileBasePath
	 * @throws IOException
	 */
	public List<Command> read(String fileBasePath) throws Throwable {
		String fileName = getRecentFileName(fileBasePath);
		List<Command> commands = new ArrayList<>();
		if (fileName != null) {
			RandomAccessFile file = new RandomAccessFile(fileName, "r");
			FileChannel channel = file.getChannel();
			long size = channel.size();
			ByteBuffer buffer = ByteBuffer.allocate((int) size);
			channel.read(buffer);

			buffer.flip();
			while (buffer.hasRemaining()) {
				Command command = ByteBufferSerializer.byteBufferToObj(buffer);
				commands.add(command);
			}
		}
		return commands;
	}

}
