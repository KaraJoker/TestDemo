package com.highto.pub.codesys.dao.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.highto.pub.codesys.domain.HierarchyCodeNode;

/**
 * 编码bean
 * 
 * @author wang xp
 * @date 2015-07-24
 */
@Document(collection = "wms_code")
public class HierarchyCodeDTO {

	@Id
	private String id;

	private String name;

	private int[] struc;

	private List<HierarchyCodeNode> list;

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

	public List<HierarchyCodeNode> getList() {
		return list;
	}

	public void setList(List<HierarchyCodeNode> list) {
		this.list = list;
	}

	public int[] getStruc() {
		return struc;
	}

	public void setStruc(int[] struc) {
		this.struc = struc;
	}

}
