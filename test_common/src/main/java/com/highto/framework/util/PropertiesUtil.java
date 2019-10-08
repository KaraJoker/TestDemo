package com.highto.framework.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtil {
	// 属性文件的路径
	private String profilepath;

	private Properties props = new Properties();

	public PropertiesUtil() {
		super();
	}

	public PropertiesUtil(String profilepath) {
		super();
		this.profilepath = profilepath;
		InputStream in = getClass().getResourceAsStream(profilepath);
		try {
			if (in == null) {
				in = new BufferedInputStream(new FileInputStream(profilepath));
			}
			props.load(in);
		} catch (IOException e) {
			System.err.println("属性文件加载出错");
		}
	}

	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param key
	 *            主键
	 * @return String
	 */
	public String getKeyValue(String key) {
		return props.getProperty(key);
	}

	/**
	 * 清空属性
	 * 
	 */
	public void clearProperties() {
		props = new Properties();
		try {
			OutputStream fos = new FileOutputStream(profilepath);
			props.store(fos, "");
			fos.close();
		} catch (IOException e) {
			System.err.println("属性文件清空错误");
		}
	}

	/**
	 * 删除属性
	 * 
	 * @param key
	 */
	public void removeProperties(String key) {
		props.remove(key);
	}

	/**
	 * 更新（或插入）一对properties信息(主键及其键值) 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname
	 *            键名
	 * @param keyvalue
	 *            键值
	 */
	public void writeProperties(String keyname, String keyvalue) {
		try {
			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(profilepath);
			props.setProperty(keyname, keyvalue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, "Update '" + keyname + "' value");
			// 关闭流
			fos.close();
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}

	public String getProfilepath() {
		return profilepath;
	}

	public void setProfilepath(String profilepath) {
		this.profilepath = profilepath;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

}
