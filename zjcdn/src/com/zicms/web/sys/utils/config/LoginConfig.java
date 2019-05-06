package com.zicms.web.sys.utils.config;

/**
 * 登录配置
 * 
 * @author taosq
 * 
 */
public class LoginConfig {

	private String loginErrorCount;// 登录错误次数限制,等于0时不启用

	private String loginUnlockTime;// 锁定时间,单位分钟

	private String pwdFristLoginMod;// 首次登录需要修改密码,为1需要,0不需要

	private String pwdNextModTime;// 下次修改时间,单位天

	private String loginCaptchaOn;// 是否启用登录验证码

	private String pwdStrongVerifcation;// 密码强校验

	public String getLoginErrorCount() {
		return loginErrorCount;
	}

	public void setLoginErrorCount(String loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}

	public String getLoginUnlockTime() {
		return loginUnlockTime;
	}

	public void setLoginUnlockTime(String loginUnlockTime) {
		this.loginUnlockTime = loginUnlockTime;
	}

	public String getPwdFristLoginMod() {
		return pwdFristLoginMod;
	}

	public void setPwdFristLoginMod(String pwdFristLoginMod) {
		this.pwdFristLoginMod = pwdFristLoginMod;
	}

	public String getPwdNextModTime() {
		return pwdNextModTime;
	}

	public void setPwdNextModTime(String pwdNextModTime) {
		this.pwdNextModTime = pwdNextModTime;
	}

	public String getLoginCaptchaOn() {
		return loginCaptchaOn;
	}

	public void setLoginCaptchaOn(String loginCaptchaOn) {
		this.loginCaptchaOn = loginCaptchaOn;
	}

	public String getPwdStrongVerifcation() {
		return pwdStrongVerifcation;
	}

	public void setPwdStrongVerifcation(String pwdStrongVerifcation) {
		this.pwdStrongVerifcation = pwdStrongVerifcation;
	}

	
}
