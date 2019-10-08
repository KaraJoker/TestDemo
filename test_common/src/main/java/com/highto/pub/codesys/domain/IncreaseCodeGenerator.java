package com.highto.pub.codesys.domain;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 自增编码生成器
 * 
 * @author wang xp
 * @date 2015-07-21
 */
public class IncreaseCodeGenerator {

	private String id;

	private String name;

	/**
	 * 当前自增游标
	 */
	private AtomicLong currentIndex = new AtomicLong(0);

	/**
	 * 10进制几位
	 */
	private int digit;

	/**
	 * 补0模板
	 */
	private String[] zerosTemplate;

	public IncreaseCodeGenerator(String name, int digit) {
		this.name = name;
		this.digit = digit;
		buildZerosTemplate();
	}

	/**
	 * 创建补0模板
	 */
	private void buildZerosTemplate() {
		zerosTemplate = new String[digit - 1];
		for (int i = 0; i < digit - 1; i++) {
			int zeroCount = digit - (i + 1);
			StringBuilder sb = new StringBuilder();
			for (int c = 0; c < zeroCount; c++) {
				sb.append("0");
			}
			zerosTemplate[i] = sb.toString();
		}
	}

	public IncreaseCodeGenerator(String name, int digit, long currentIndex) {
		this(name, digit);
		this.currentIndex = new AtomicLong(currentIndex);
	}

	/**
	 * 根据当前编码增长
	 * 
	 * @param size
	 *            位数
	 * @return
	 */
	public String getIncreaseCode() {
		long num = currentIndex.incrementAndGet();
		return (zerosTemplate[((int) Math.log10(num))] + num);
	}

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

	public AtomicLong getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(AtomicLong currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getDigit() {
		return digit;
	}

	public void setDigit(int digit) {
		this.digit = digit;
	}

}
