package com.zicms.web.zjcdn.controller;

import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.zjcdn.model.Main;
import com.zicms.web.zjcdn.service.MainService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("main")
public class MainController {

	@Resource
	private MainService mainService;

	private static final String BAIDU_APP_KEY = "1FhyZv3dzYHoOP8Ph4CXxOnoAB477qfT";

	public static void main(String[] args){
		String ip = "111.202.206.210";
		System.out.println("getLatitude"+getLatitude(ip));
		/*InetAddress ia=null;
		try {
			ia=ia.getLocalHost();

			String localname=ia.getHostName();
			String localip=ia.getHostAddress();
			System.out.println("本机名称是："+ localname);
			System.out.println("本机的ip是 ："+localip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	/**
	 * 获取登录用户IP地址
	 *
	 * @param request
	 * @return
	 */
      public static String getIpAddr(HttpServletRequest request) {
		         String ip = request.getHeader("x-forwarded-for");
		         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			             ip = request.getHeader("Proxy-Client-IP");
			         }
		         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			             ip = request.getHeader("WL-Proxy-Client-IP");
			         }
		         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			             ip = request.getRemoteAddr();
			         }
		         if (ip.equals("0:0:0:0:0:0:0:1")) {
			             ip = "本地";
			         }
		         return ip;
		     }


	@RequestMapping("getMessage")
	@ResponseBody
	public Map<String, Object> geMessage(HttpServletRequest request){

      	Map<String,Object> map = new HashMap<String, Object>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//获取当前登陆用户
		String username = SysUserUtils.getCacheLoginUser().getUsername();
		//获取当前日期
		String date = sdf.format(new Date());
		//获取本机ip地址
		InetAddress ia=null;
		String ip = "";
		try {
			ia=ia.getLocalHost();
			ip=ia.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//String ip = getIpAddr(request);
		mainService.updateIp(ip,date,username);
		if(username.equals("admin")){
			 List<Main> list = mainService.findIpa(username);
			 String ip1 = list.get(0).toJSONString();
			 List<Object> list1 = getLatitude(ip1);
			 map.put("test",list1);
		}
		if (username.equals("test")){
			List<Main> list = mainService.findIpa(username);
			String ip1 = list.get(0).toJSONString();
			List<Object> list1 = getLatitude(ip1);
			map.put("admin",list1);
		}

		List<Object> list = getLatitude(ip);
		map.put("main",list);

      	return  map;
	}

	/**
	 * 返回经纬度坐标的map longitude(经度),latitude(纬度)
	 */
	public static List<Object> getLatitude(String ip) {
		List<Object> list = new ArrayList<Object>();
		try {
			ip = URLEncoder.encode(ip, "UTF-8");
			URL resjson = new URL("http://api.map.baidu.com/location/ip?ip="+ip+"&ak="+BAIDU_APP_KEY+"&coor=bd09ll");
			BufferedReader in = new BufferedReader(new InputStreamReader(resjson.openStream()));
			//System.out.println(resjson);
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			in.close();
			String str = sb.toString();
			JSONObject jsonObject = JSONObject.fromObject(str);
			Object o = jsonObject.get("content");
			//取出经纬度
			JSONObject object = JSONObject.fromObject(o);
			Object o1 = object.get("point");
			//取出所在省份
			//JSONObject object1 = jsonObject.fromObject(o);
			Object o2 = object.get("address");

			list.add(o1);
			list.add(o2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
}
