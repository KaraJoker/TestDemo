package com.cn.company.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePathConfig {

	@Value("${filepath.snapshotFileBasePath}")
	private String snapshotFileBasePath;
	@Value("${filepath.jFileBasePath}")
	private String jFileBasePath;

	public String getSnapshotFileBasePath() {
		return snapshotFileBasePath;
	}

	public void setSnapshotFileBasePath(String snapshotFileBasePath) {
		this.snapshotFileBasePath = snapshotFileBasePath;
	}

	public String getjFileBasePath() {
		return jFileBasePath;
	}

	public void setjFileBasePath(String jFileBasePath) {
		this.jFileBasePath = jFileBasePath;
	}

}
