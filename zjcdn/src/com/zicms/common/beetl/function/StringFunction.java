package com.zicms.common.beetl.function;

import org.springframework.stereotype.Component;

@Component
public class StringFunction {

	public String getLastValue(String params) {
		String[] array = params.split(",");
		if (array.length > 0)
			return array[array.length - 1];

		return params;
	}

	public String getfirstValue(String params) {
		String[] index = params.split(",");

		return index[0];
	}

	public String formatFlowType(String params) {
		if ("people_flow".equals(params)) {
			return "人员进出证申请";
		} else if ("goods_flow".equals(params)) {
			return "物品进出证申请";
		} else if ("visitor_flow".equals(params)) {
			return "临时访客出入申请";
		} else if ("plug_flow".equals(params)) {
			return "防火封堵孔洞申请";
		} else if ("build_flow".equals(params)) {
			return "施工单位申请";
		} else if ("temporary_flow".equals(params)) {
			return "施工出入证申请";
		} else if ("fire_flow".equals(params)) {
			return "动火审批";
		} else if ("stairway_flow".equals(params)) {
			return "货梯审批";
		}
		return "其他类型申请";

	}

	public String formatSubStr(String str) {
		if(str.startsWith("http://")){
			str = str.split("://")[1];
		}
		int sta = str.indexOf("/");
		if (sta > 0) {
			str = str.substring(0, sta);
		} else {
			str = str;
		}
		return str;
	}

	public String formatSubUrl(String str) {
		/*if(str.startsWith("http://")){
			str = str.split("://")[1];
		}*/
		String strs = "";
		if(str!=null&&!str.equals("")) {
			strs=str.toString().substring(0, 22)+"……";
		}
		
		return strs;
	}

}
