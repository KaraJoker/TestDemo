package com.cn.company.msg.msjobj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonMO {


	private Boolean code;

	private String msg;

	private Object data;
}
