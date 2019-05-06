package com.zicms.web.datacenter.controller.imageCheck;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.datacenter.model.ImageCheck;
import com.zicms.web.datacenter.service.ImageCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;

@Controller
@RequestMapping("Recheck")
public class ImageRecheckController {

	@Resource
	private ImageCheckService imageCheckService;

	public static final String FILE_PATH = "/upload/";

	/**
	 * 复审页面搜索框
	 */
	@RequestMapping
	public String toImageCheck(Model model) {
		SysUser user = SysUserUtils.getSessionLoginUser();
		String province = user.getProvince();
		String[] proArr = province.split(",");
		int count = imageCheckService.getRecheckCount(proArr);
		model.addAttribute("count", count);
		return "check/image-recheck-manager";
	}

	/**
	 * 进入复审页面（显示初审为不良的图片）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(Model model, HttpServletRequest request) {
		String imageturl = request.getParameter("imageturl");
		String checkdate = request.getParameter("checkdate");
		String iplist = request.getParameter("iplist");
		SysUser user = SysUserUtils.getSessionLoginUser();
		String province = user.getProvince();
		String[] proArr = province.split(",");
		String retrialAccount = user.getUsername();
		Map<String, Object> param = new HashMap<String, Object>();
		List<ImageCheck> list = null;
		param.put("retrialAccount", retrialAccount);
		param.put("province", proArr);
		param.put("imageturl", imageturl);
		param.put("checkdate", checkdate);
		param.put("iplist", iplist);
		imageCheckService.updateRecheck(param);
		list = imageCheckService.findRecheckList(param);
		if(list.size()==0){
			System.out.println("没有需要复审的图片");
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String retrialTime = sdf.format(new Date());
			ImageCheck imag = new ImageCheck();
			imag.setRetrialAccount(retrialAccount);
			imag.setRetrialTime(retrialTime);
			imag.setRetrialStatus(101);
			imageCheckService.updateRecheckTop(imag,list);
			model.addAttribute("page", list);
		}
		return "check/image-recheck-list";
	}

	/**
	 * 更新图片信息(复审将图片设为正常)
	 */
	@RequestMapping(value = "revoke_image", method = RequestMethod.POST)
	public @ResponseBody void approve_save(@ModelAttribute  ImageCheck imag) {
		imageCheckService.reUpdateToNormal(imag);
	}
	
	/**
	 * 撤销对图片的操作
	 * @param imag
	 */
	@RequestMapping(value = "cancel_check", method = RequestMethod.POST)
	public @ResponseBody void cancel_check(@ModelAttribute ImageCheck imag){
		imageCheckService.undoReupdate(imag);
	}

	 /**
    * 提交复审最后一页数据
    * @return
	 * @throws IOException 
    */
   @RequestMapping(value = "/submit", method = RequestMethod.GET)
   public String submit(HttpServletRequest request, HttpServletResponse response) throws IOException {
   	//获取请求上下文
	   String path = request.getContextPath();
       SysUser user = SysUserUtils.getSessionLoginUser();
       // 获取当前用户账号
       String retrialAccount = user.getUsername();
       Map<String, Object> param = new HashMap<String, Object>();
       param.put("retrialAccount", retrialAccount);
       int i = imageCheckService.updateRecheck(param);
       PrintWriter pw = response.getWriter();
       if(i!=-1){
       	response.setContentType("text/html; charset=utf-8");
       	pw.write("<script>alert('数据已成功提交！');</script>");
       	pw.write("<script>window.location.href="+path+"/</script>");
       }else{
       	pw.write("<script>alert('数据提交失败')</script>");
       }
       pw.flush();
       return "";
   }
   
}
