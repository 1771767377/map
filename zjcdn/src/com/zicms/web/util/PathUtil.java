package com.zicms.web.util;


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * 路径工具类
 * @author taosq
 * @date 2015-03-29
 *
 */
public class PathUtil {
	
	private static String rootDir;
	private static String webInfDir;
	private static String studentImgDir;
	private static String _studentImgDir = "upload/student";
	private static String tempDir;
	private static String _TempDir = "upload/temp";
	private static String docDir;
	private static String _DocDir = "doc";
	
	public static String DEFAULT_PROJECT_IMG ="project_default.png";
	
	
	public static String STUDENT_XML = "/hbm/Student.hbm.xml";
	
	
	
	public static String getWebRootDir(){
		if(StringUtils.isBlank(rootDir)){
			String folderPath = PathUtil.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			if (folderPath.indexOf("WEB-INF") > 0) {
				rootDir = folderPath.substring(0, folderPath
						.indexOf("WEB-INF/classes"));
			}
			rootDir = rootDir.replaceAll("%20", " ");
		}
		return rootDir;
	}
	
	public static String getWebInfDir(){
		if(StringUtils.isBlank(webInfDir)){
			try {
				webInfDir = PathUtil.class.getResource("").toURI().getPath();
				webInfDir = webInfDir.substring(0, webInfDir.indexOf("classes"));
				webInfDir = webInfDir.replaceAll("%20", " ");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return webInfDir;
	}
	

	public static String  getStudentImgDir(){
		if(StringUtils.isBlank(studentImgDir)){
			File file = new File(getWebRootDir(),_studentImgDir);
			if(!file.exists()){
				file.mkdirs();
			}
			studentImgDir = file.getAbsolutePath();
		}
		return studentImgDir;
	}
	
	public static String  getTempDir(){
		if(StringUtils.isBlank(tempDir)){
			File file = new File(getWebInfDir(),_TempDir);
			if(!file.exists()){
				file.mkdirs();
			}
			tempDir = file.getAbsolutePath();
		}
		return tempDir;
	}
	
	

	
	public static String  getDocDir(){
		if(StringUtils.isBlank(docDir)){
			File file = new File(getWebInfDir(),_DocDir);
			if(!file.exists()){
				file.mkdirs();
			}
			docDir = file.getAbsolutePath();
		}
		return docDir;
	}
	
	
	
}
