package com.zicms.web.util;

public class StringUtil {
	/**
	 * imageturl转换成域名，截取
	 * 
	 * @param str
	 * @return
	 */
	public static String formatSubStr(String str) {
		if (str.startsWith("http://") || str.startsWith("https://")) {
			str = str.split("://")[1];
		}
		int sta = str.indexOf("/");
		if (sta > 0) {
			str = str.substring(0, sta);
		}
		return str;
	}
	
	/**
	 * imageturl转换成域名
	 * 
	 * @param str
	 * @return
	 */
	public static String formatDomain(String str) {
		if (str.startsWith("http://www.") || str.startsWith("https://www.") || str.startsWith("www.")) {
			str = str.split("www.")[1];
		}else if(str.startsWith("http://") || str.startsWith("https://")) {
			str = str.split("://")[1];
		}
		int sta = str.indexOf("/");
		if (sta > 0) {
			str = str.substring(0, sta);
		}
		return str;
	}
	
    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf((val.toString().trim()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 将驼峰风格替换为下划线风�?
     */
    public static String camelhumpToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder((size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isLowercaseAlpha(c)) {
                sb.append(toUpperAscii(c));
            } else {
                sb.append('_').append(c);
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    /**
     * 将下划线风格替换为驼峰风�?
     */
    public static String underlineToCamelhump(String name) {
        if (!name.contains("_"))
            return name;
        char[] buffer = name.toCharArray();
        int count = 0;
        boolean lastUnderscore = false;
        for (int i = 0; i < buffer.length; i++) {
            char c = buffer[i];
            if (c == '_') {
                lastUnderscore = true;
            } else {
                c = (lastUnderscore && count != 0) ? toUpperAscii(c) : toLowerAscii(c);
                buffer[count++] = c;
                lastUnderscore = false;
            }
        }
        if (count != buffer.length) {
            buffer = subarray(buffer, 0, count);
        }
        return new String(buffer);
    }

    public static char[] subarray(char[] src, int offset, int len) {
        char[] dest = new char[len];
        System.arraycopy(src, offset, dest, 0, len);
        return dest;
    }

    public static boolean isLowercaseAlpha(char c) {
        return (c >= 'a') && (c <= 'z');
    }

    public static char toUpperAscii(char c) {
        if (isLowercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }

    public static char toLowerAscii(char c) {
        if ((c >= 'A') && (c <= 'Z')) {
            c += (char) 0x20;
        }
        return c;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0)
            return true;
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String firstCharToUpper(String str) {
        StringBuffer sb = new StringBuffer(str);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public static String firstCharToLower(String str) {
        StringBuffer sb = new StringBuffer(str);
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }
    //将日期字符串转化为yyyyMMddHHmmss格式
    public static String  strTostr(String date){
    	if(date==null||date.isEmpty()){
    		return null;
    	}
    	String newDate1=null;
    	String newDate2=null;
    	String newDate3=null;
    	if(date.indexOf("-")>0){
    		newDate1=date.replace("-", "");
    	}else{
    		return newDate1;
    	}
    	if(newDate1.indexOf(" ")>0){
    		newDate2=newDate1.replace(" ", "");
    	}else{
    		return newDate2;
    	}
    	if(newDate2.indexOf(":")>0){
    		newDate3=newDate2.replace(":", "");
    	}
    	return newDate3;
    }
}
