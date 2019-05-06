package com.zicms.web.util;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转换位汉语拼音，英文字符不变
 * @author xuke
 *
 */
public class Cn2Spell {
 
    /**
    * 汉字转换位汉语拼音首字母，英文字符不变
    * @param chines 汉字
    * @return 拼音
    */
    public static String converterToFirstSpell(String chines){ 
    	if(StringUtils.isBlank(chines))
    		return "";
    	chines = chines.replaceAll("\\s*", "");
    	chines = chines.replaceAll(" ", "");
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                	String[] str = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                	if(str != null && str.length > 0){
                		pinyinName += str[0].charAt(0);
                	}
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
            	pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }
 
    /**
    * 汉字转换位汉语拼音，英文字符不变
    * @param chines 汉字
    * @return 拼音
    */
    public static String converterToSpell(String chines){    	
    	if(StringUtils.isBlank(chines))
    		return "";
    	chines = chines.replaceAll("\\s*", "");
    	chines = chines.replaceAll(" ", "");
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                	String[] str = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                	if(str != null && str.length > 0){
                		 pinyinName += str[0];
                	}
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
            	pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }
    
    public static void main(String[] args) {
    	String str = "伍　贵";
    	
    	int len = str.length();
    	char c1 = '　';
    	String s = String.valueOf(c1);
    	System.out.println(s.contains(s));
    	for (int i = 0 ;i < len; i++){
    		char c = str.charAt(i);
    		if(c != c1){
    			System.out.println(c);
    		}
    	}
    	str = str.replaceAll(s, "");
    	System.out.println(str);
//    	
//    	System.out.println(str.trim());
//    	str = str.replaceAll(" ", "");
//    	str = str.replaceAll("\\s*", "");
//		System.out.println(str);
	}
}