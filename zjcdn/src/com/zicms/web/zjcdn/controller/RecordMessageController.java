package com.zicms.web.zjcdn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.zicms.common.utils.FileUtil;
import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.zjcdn.model.Audit;
import com.zicms.web.zjcdn.service.TasksService;
import com.zicms.web.zjcdn.utils.JDBCUtil;
import com.zicms.web.zjcdn.utils.JdbcTools;

@Controller
@RequestMapping("recordMessage")
public class RecordMessageController {
 
	@Resource
	private TasksService tasksService;
	
	@RequestMapping
	public String jump(Model model) {
		
		String username = SysUserUtils.getCacheLoginUser().getUsername();
		model.addAttribute("username", username);
		return "zjcdn/data_analysis_message";
	}
	
	/** 查询审核后未备案的数据
	 * @param params
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value="getRecordMessage",method=RequestMethod.POST)
	public String getRecordMessage(@RequestParam Map<String,Object> params,Model model) throws SQLException {
		
		int pageNum = Integer.valueOf((String) params.get("pageNum")).intValue();
		int pageSize = Integer.valueOf((String) params.get("pageSize")).intValue();
		int pages;
		int total = getTotal(params);
		if(total%pageNum==0) {
			pages=total/pageSize;
		}else {
			pages=total/pageSize+1;
		}
		int start = (pageNum-1)*pageSize;
		params.put("pages", pages);
		params.put("total", total);
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		PageHelper.startPage(params);
		String sql = " SELECT * from audit_filing where 1 = 1 ";
		//条件查询
		StringBuilder bsql = new StringBuilder();
		bsql.append(sql);
		List<Object> listparam = new ArrayList<Object>();
		if(!params.get("domain").equals("")&&params.get("domain")!=null) {
			bsql.append("and domain like \"%\"?\"%\" ");
			listparam.add(params.get("domain").toString());
		}	
		if(!params.get("ip").equals("")&&params.get("ip")!=null) {
			bsql.append(" and ip like \"%\"?\"%\" ");
			listparam.add(params.get("ip").toString());
		}
		if(!params.get("url").equals("")&&params.get("url")!=null){
			bsql.append("and url like \"%\"?\"%\" ");
			listparam.add(params.get("url").toString());
		}
		if(!params.get("startdate").equals("")&&params.get("startdate")!=null) {
			bsql.append(" and exportdate >= ? ");
			listparam.add(params.get("startdate").toString());
		}
		if(!params.get("enddate").equals("")&&params.get("enddate")!=null) {
			bsql.append(" and exportdate <= ? ");
			listparam.add(params.get("enddate").toString());
		}
		bsql.append("limit ?,?");
		ps = conn.prepareStatement(bsql.toString()); 
		for (int i = 0; i < listparam.size(); i++) {
			ps.setString(i+1, listparam.get(i).toString());
		}
		ps.setInt(listparam.size()+2, pageSize);
		ps.setInt(listparam.size()+1, start);
		ResultSet resultSet = ps.executeQuery();
		
		List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		Map<String,Object> page = new HashMap<String,Object>();
		page.put("pageNum", pageNum);
		page.put("pageSize", pageSize);
		page.put("total", total);
		page.put("pages", pages);
		page.put("list", list);
		model.addAttribute("page", page);
		JDBCUtil.closeConnection(conn);
		
		String username = SysUserUtils.getCacheLoginUser().getUsername();
		model.addAttribute("username", username);
		return "zjcdn/data-list-message";
	}
	
	
	/**求出总条数
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int getTotal(@RequestParam Map<String,Object> params) throws SQLException {
		
		int count = 0;
		Connection conn = JDBCUtil.getConnection();
		String sql = " SELECT count(*) from audit_filing where 1 = 1 ";
		PreparedStatement ps = null;
		//条件查询
		StringBuilder bsql = new StringBuilder();
		bsql.append(sql);
		List<Object> listparam = new ArrayList<Object>();
		if(!params.get("domain").equals("")&&params.get("domain")!=null) {
			bsql.append("and domain like \"%\"?\"%\" ");
			listparam.add(params.get("domain").toString());
		}	
		if(!params.get("ip").equals("")&&params.get("ip")!=null) {
			bsql.append(" and ip like \"%\"?\"%\" ");
			listparam.add(params.get("ip").toString());
		}
		if(!params.get("url").equals("")&&params.get("url")!=null){
			bsql.append("and url like \"%\"?\"%\" ");
			listparam.add(params.get("url").toString());
		}
		if(!params.get("startdate").equals("")&&params.get("startdate")!=null) {
			bsql.append(" and exportdate >= ? ");
			listparam.add(params.get("startdate").toString());
		}
		if(!params.get("enddate").equals("")&&params.get("enddate")!=null) {
			bsql.append(" and exportdate <= ? ");
			listparam.add(params.get("enddate").toString());
		}
		ps = conn.prepareStatement(bsql.toString());
		
		for (int i = 0; i < listparam.size(); i++) {
			ps.setString(i+1, listparam.get(i).toString());
		}
		ResultSet resultSet = ps.executeQuery();
		
		if(resultSet.next()){
            count = resultSet.getInt(1);  //对总记录数赋值
        }
		resultSet.close();
        JDBCUtil.closeConnection(conn);
		return count;
	}
	
	@RequestMapping("jumpUploadFile")
	public String jumpUploadFile() {
		
		return "zjcdn/uploadfile";
	}
	
	@RequestMapping(value="uploadFile",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFile(HttpServletRequest request , HttpServletResponse response) {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
        MultipartFile file = multipartRequest.getFile("file");  
        String fileName = file.getOriginalFilename();
        String fileSuffix = FileUtil.getFileSuffix(fileName);
        if(!fileSuffix.equals("xls") && !fileSuffix.equals("xlsx")){
        	return "illegal";
        }
        //上传至服务器
        String path = request.getServletContext().getRealPath("upload");
        File targetFile = new File(path,fileName);//创建 内存中的File对象
        if(!targetFile.getParentFile().exists()){
        	targetFile.getParentFile().mkdirs();
        }
        /**
         * 转存文件写入硬盘  
         * 需要注意 newFile对应的硬盘中的文件不能存在 
         * 存在则需要删除  否则会抛出 文件已经存在且不能删除 异常
         */
        targetFile.deleteOnExit();
        try {
        	file.transferTo(targetFile);
        	try{
        		List<Audit> list = ParseExcel(fileSuffix, targetFile);
        		for(Audit audit: list) {
        			System.out.println(audit.toString());
        		}
        		//tasksService.insertDatas(list);        			
        	}catch(Exception e){
        		System.out.println(e.getMessage());
        		return "insertError";
        	}
        	return "success";
        } catch (Exception e) {
        	return "fail";
        }
		
	}
	
	private List<Audit> ParseExcel(String suffix, File saveFile) throws Exception {
        org.apache.poi.ss.usermodel.Workbook wb = null;
        InputStream in = null;
        try {
            in = new FileInputStream(saveFile);
            if("xls".equals(suffix)){
            	wb = new HSSFWorkbook(in);            	
            }else if("xlsx".equals(suffix)){
            	wb = new XSSFWorkbook(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        // 获取Sheet表中所包含的总行数和总列数
        int firstRowIndex = sheet.getFirstRowNum()+1;
        int lastRowIndex = sheet.getLastRowNum();
        List<Audit> list = new ArrayList<Audit>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(int rIndex=firstRowIndex; rIndex<=lastRowIndex; rIndex++){
        	Audit task = new Audit();
        	//task.setInsertdate(importTime);
        	Row row = sheet.getRow(rIndex);
        	if(row != null){
        		int firstCellIndex = row.getFirstCellNum();
        		int lastCellIndex = row.getLastCellNum();
        		for(int cIndex=firstCellIndex; cIndex<lastCellIndex; cIndex++){    
        			Cell cell = row.getCell(cIndex);
        			if(cIndex==0){
        				String statuscode = cell.toString();
        				task.setStatuscode(statuscode);
        			}else if(cIndex==1) {
        				Date exportdate = cell.getDateCellValue();
        				task.setExportdate(sdf.format(exportdate));
        			}else if(cIndex==2){
        				String domain = cell.toString();
        				task.setDomain(domain);
        			}else if(cIndex==3) {
        				String url = cell.toString();
        				task.setUrl(url);
        			}else if(cIndex==4) {
        				String ip = cell.toString();
        				task.setIp(ip);
        			}else if(cIndex==5) {
        				String ysrecord = cell.toString();
        				if(ysrecord.equals("未备案")) {
        					ysrecord = "1";
        				}else {
        					ysrecord = "1";
        				}
        				task.setYsrecord(ysrecord);
        			}
        		}
        		list.add(task);
        	}
        }
        wb.close();
        return list;
    }
	
	@RequestMapping("deleteMessage")
	@ResponseBody
	public String deleteMessage(@RequestParam String id) {
		
		tasksService.deleteMessage(id);
		
		return "success";
	}
	
}
