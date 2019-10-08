package com.highto.framework.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HttpSendParameters implements Serializable {

	private static final long serialVersionUID = -2038756504677799164L;

	private List<List<String>> content = new ArrayList<List<String>>();

	public void addEntity(String key, String vlaue) {
		List<String> entity = new ArrayList<String>();
		entity.add(key);
		entity.add(vlaue);
		content.add(entity);
	}

	public String getParametersStr() {
		if (!content.isEmpty()) {
			String body = content.get(0).get(0) + "=" + content.get(0).get(1);
			for (int i = 1; i < content.size(); i++) {
				List<String> pContent = content.get(i);
				body += ("&" + pContent.get(0) + "=" + pContent.get(1));
			}
			return body;
		} else {
			return "";
		}
	}

	public List<List<String>> getContent() {
		return content;
	}

	public String getParameterValue(String key) {
		for (List<String> entry : content) {
			if (entry.get(0).equals(key)) {
				return entry.get(1);
			}
		}
		return null;
	}

	public void updateFirstEntity(String key, String vlaue) {
		for (List<String> entry : content) {
			if (entry.get(0).equals(key)) {
				entry.remove(1);
				entry.add(vlaue);
				return;
			}
		}
	}

}