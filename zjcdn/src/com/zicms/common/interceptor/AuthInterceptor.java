package com.zicms.common.interceptor;

import com.zicms.common.beetl.utils.BeetlUtils;
import com.zicms.common.constant.Constant;
import com.zicms.web.sys.model.SysResource;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

public class AuthInterceptor implements HandlerInterceptor {

	/*
	 * private Set<String> ignorePath = new HashSet<String>
	 * (Arrays.asList("/login", "/captcha", "/notlogin", "/ErrorHandler"));
	 */

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI(); // 请求路径
		String ctx = request.getContextPath();
		String path = uri.substring(ctx.length());
		if ("/App".equals(path)) {
			return true;
		}
		if ("/applogin".equals(path)) {
			return true;
		}

		// if(!ignorePath.contains(path)){
		// 获得session中的登陆用户
		SysUser sessionUser = SysUserUtils.getSessionLoginUser();

		// 获得缓存中的登陆用户
		SysUser cacheUser = SysUserUtils.getCacheLoginUser();

		if (sessionUser == null || cacheUser == null) { // 转到登陆页面
			response.sendRedirect(ctx + "/notlogin");
			return false;
		} else {

			Map<String, SysResource> allRes = BeetlUtils.getBeetlSharedVars(Constant.CACHE_ALL_RESOURCE);
			String perPath = path.substring(1);
			SysResource sysResource = allRes.get(perPath);
			// 判断如果url不在数据库中，则默认都有权限访问
			if (sysResource == null || Constant.RESOURCE_COMMON.equals(sysResource.getCommon())) {
				return true;
			}

			// 实时的权限验证,检测用户认证是否改变，如果认证改变则重置，否则不进行任何操作
			SysUserUtils.setUserAuth();

			// 从缓存中的用户权限 map<url:resource>
			Map<String, SysResource> userRes = SysUserUtils.getUserResources();
			if (userRes.containsKey(perPath)) { // 有权限则过
				return true;
			} else { // 没有权限跳到没有权限
				response.sendRedirect(ctx + "/notauth");
				return false;
			}
		}
		// }
		// return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
