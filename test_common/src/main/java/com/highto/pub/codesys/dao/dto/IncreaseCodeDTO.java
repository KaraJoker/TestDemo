package com.highto.pub.codesys.dao.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 编码bean
 * 
 * @author wang xp
 * @date 2015-07-24
 */
@Document(collection = "wms_code")
public class IncreaseCodeDTO {

	@Id
	private String id;

	private String name;

	private long currentIndex;

	private int digit;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(long currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getDigit() {
		return digit;
	}

	public void setDigit(int digit) {
		this.digit = digit;
	}

}
