package com.zicms.web.util;

import org.apache.commons.lang3.StringUtils;

public class SerialNumberUtil {
	
	/**
	 * 生成序号
	 * @param tag        标识符
	 * @param index      当前序号
	 * @param length     序号长度
	 * @param mainIndex  主序号，需要几位传几位，例如00，侧从0开始，填充成2位
	 * @return
	 */
	public static String createNumber(String suffix,String tag,int index,int length,String mainIndex){
		int mi = Integer.parseInt(mainIndex);
		if(((index+1) + "").length() > length){
			index = 1;
			mi ++;
		}
		if(StringUtils.isNotBlank(suffix)){
			return suffix + fill(mi, mainIndex.length()) + tag + fill(index,length);
		}
		return fill(mi, mainIndex.length()) + tag + fill(index,length);
	}
	
	public static String createNumber(String tag,int index,int length,String mainIndex){
		return createNumber(null,tag, index, length, mainIndex);
		
	}
	/**
	 * 获取下一个序号
	 *   如果长度超过后，自动从1开始
	 * @param index   当前序号
 	 * @param length  序号长度
	 * @return
	 */
	public static int getNextIndex(int index,int length){
		if(((index+1)+"").length() > length){
			return 1;
		}
		return index+1;
	}
	
	/**
	 * 获取下一个主序号
	 * @param index
	 * @param length
	 * @return 
	 */
	private static String fill(int index,int length){
		if((index+"").length() >= length){
			return index+"";
		}else{
			StringBuffer sb = new StringBuffer();
			for(int i = (index+"").length(); i < length;i++){
				sb.append("0");
			}
			return sb.toString()+index;
		}
	}
	
	public static String getMainIndex(int index,int length,String mainIndex){
		index ++;
		int mi = Integer.parseInt(mainIndex);
		if((index+"").length() > length){
			mi ++;
		}
		return fill(mi, mainIndex.length());
	}
	
	public static void main(String[] args) {
		System.out.println(getMainIndex(999, 3, "00"));
		System.out.println(createNumber("PC",1, 3, "00"));
		System.out.println(createNumber("PC",999, 3, "00"));
		System.out.println(getNextIndex(888, 3));
	}

}
