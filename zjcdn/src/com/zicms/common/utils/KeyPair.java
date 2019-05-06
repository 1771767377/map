/**
 * 
 */
package com.zicms.common.utils;

/**
 * @author chenxd
 * @date 09/18/2015
 */
public class KeyPair {
private String key;
	
	private String value;

	public KeyPair(Integer key, String value) {
		this.key = key.intValue() + "";
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
