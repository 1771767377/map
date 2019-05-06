package com.zicms.web.tool.controller;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zicms.web.sys.service.SysConfigService;
import com.zicms.web.util.ResponseUtils;
import com.zicms.web.util.Result;
import com.zicms.web.util.SysConfigKey;

@Controller
@RequestMapping("upload")
public class UploadController{

	@Resource
	private SysConfigService sysConfigService;
	
	@RequestMapping("doc")
	public void upload(@RequestParam MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		Result  r = null;
		if (file != null) {
			try {
				String uuid = UUID.randomUUID().toString();	
				File f = new File(sysConfigService.findByKey(SysConfigKey.FILE_SAVE_ROOT_PATH), uuid);
				request.getSession().setAttribute(uuid,file.getOriginalFilename());
				FileUtils.copyInputStreamToFile(file.getInputStream(),f);
				r = new Result(Result.SUCCESS, uuid,null);
			} catch (Exception e) {
				r = new Result(-2, null,null);
			}
		} else {
			r = new Result(-1, null,null);
		}
		ResponseUtils.renderText(response, r);
	}
	
	
	
	/*@RequestMapping("/uploadGoodsPic")
	public String uploadGoodsPic (HttpServletRequest request,HttpServletResponse response,String updateWay,Model model) throws IOException{
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        MultipartFile file  =  multipartRequest.getFile("goodspic");
	    String filename = UUID.randomUUID().toString();
		//保存上传的文件到服务器
	    String filePath = new UpAndDownUtils().fileSave(file,filename+".jpg",request);
        return filePath;
	}*/	
}
