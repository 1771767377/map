package com.zicms.web.zjcdn.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageHelper;
import com.zicms.web.zjcdn.utils.JDBCUtils;
import com.zicms.web.zjcdn.utils.JdbcTools;

@Controller
@RequestMapping("violationMessage")
public class ViolationController {
	
	@RequestMapping("pic")
	public String jump1() {
		
		return "zjcdn/data_analysis_img";
	}
	
	@RequestMapping("video")
	public String jump2() {
		
		return "zjcdn/data_analysis_video";
	}
	
	/**
	   * 不合规信息查询img
	 * @param model
	 * @param request
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value="getViolationMessagePic",method=RequestMethod.POST)
	public String getViolationMessagePic(@RequestParam Map<String,Object> param,Model model, HttpServletRequest request) throws SQLException{
		//String pageNum = param.get("pageNum");
		int pageNum = Integer.valueOf((String) param.get("pageNum")).intValue();
		int pageSize = Integer.valueOf((String) param.get("pageSize")).intValue();
		int pages;  //总页数
		int total=getPicTotal(param);//查询总记录数
		if(total%pageSize==0){
            pages=total/pageSize;
        }else{
            pages=total/pageSize+1;
        }
		int start = (pageNum-1)*pageSize;
		param.put("pages", pages);
		param.put("total", total);
		Connection conn = JDBCUtils.getConnection(); 
		PreparedStatement ps = null;
		PageHelper.startPage(param);                                                                /*limit "+start+","+pageSize
*/		String sql = "SELECT * from img_history WHERE retrial_status = '1' and province = '3310000' ";
		//条件查询
		StringBuilder bsql = new StringBuilder();
		bsql.append(sql);
		List<Object> listparam = new ArrayList<Object>();
		if (!param.get("manufactor").equals("")&&param.get("manufactor")!=null) {
			bsql.append(" and information like \"%\"?\"%\" ");
			listparam.add(param.get("manufactor").toString());
		}
		if (!param.get("domain").equals("")&&param.get("domain")!=null) {
			bsql.append(" and img_host like \"%\"?\"%\" ");
			listparam.add(param.get("domain").toString());
		}
		if (!param.get("ip").equals("")&&param.get("ip")!=null) {
			bsql.append(" and iplist like \"%\"?\"%\" ");
			listparam.add(param.get("ip").toString());
		}
		if (!param.get("startdate").equals("")&&param.get("startdate")!=null) {
			bsql.append(" and insertdate >= ? ");
			listparam.add(param.get("startdate").toString());
		}
		if (!param.get("enddate").equals("")&&param.get("enddate")!=null) {
			bsql.append(" and insertdate <= ? ");
			listparam.add(param.get("enddate").toString());
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
		page.put("pages", pages);
		page.put("total", total);
		page.put("list", list);
		model.addAttribute("page", page);
		JDBCUtils.closeConnection(conn);
		return "zjcdn/data-list-img";
	}
	
	/**
	   * 不合规信息查询video
	 * @param model
	 * @param request
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value="getViolationMessageVideo",method=RequestMethod.POST)
	public String getViolationMessageVideo(@RequestParam Map<String,Object> param, Model model, HttpServletRequest request) throws SQLException{
		
		int pageNum = Integer.valueOf((String) param.get("pageNum")).intValue();
		int pageSize = Integer.valueOf((String) param.get("pageSize")).intValue();
		int pages;  //总页数
		int total=getVideoTotal(param);//查询总记录数
		if(total%pageSize==0){
            pages=total/pageSize;
        }else{
            pages=total/pageSize+1;
        }
		int start = (pageNum-1)*pageSize;
		Connection conn = JDBCUtils.getConnection(); 
		PreparedStatement ps = null;
		PageHelper.startPage(param);                                                                /*limit "+start+","+pageSize
*/		String sql = "SELECT * from video_history WHERE retrial_status = '1' and province = '3310000' ";
		//条件查询
		StringBuilder bsql = new StringBuilder();
		bsql.append(sql);
		List<Object> listparam = new ArrayList<Object>();
		if (!param.get("manufactor").equals("")&&param.get("manufactor")!=null) {
			bsql.append(" and information like \"%\"?\"%\" ");
			listparam.add(param.get("manufactor").toString());
		}
		if (!param.get("domain").equals("")&&param.get("domain")!=null) {
			bsql.append(" and video_host like \"%\"?\"%\" ");
			listparam.add(param.get("domain").toString());
		}
		if (!param.get("ip").equals("")&&param.get("ip")!=null) {
			bsql.append(" and iplist like \"%\"?\"%\" ");
			listparam.add(param.get("ip").toString());
		}
		if (!param.get("startdate").equals("")&&param.get("startdate")!=null) {
			bsql.append(" and insertdate >= ? ");
			listparam.add(param.get("startdate").toString());
		}
		if (!param.get("enddate").equals("")&&param.get("enddate")!=null) {
			bsql.append(" and insertdate <= ? ");
			listparam.add(param.get("enddate").toString());
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
		
		/*for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("videourl")!=null&&!list.get(i).get("videourl").equals("")) {
				String newurl = list.get(i).get("videourl").toString().substring(0, 12)+"……";
				list.get(i).put("videourl", newurl);
			}
		}*/
		
		Map<String,Object> page = new HashMap<String,Object>();
		page.put("pageNum", pageNum);
		page.put("pageSize", pageSize);
		page.put("pages", pages);
		page.put("total", total);
		page.put("list", list);
		model.addAttribute("page", page);
		JDBCUtils.closeConnection(conn);
		return "zjcdn/data-list-video";
	}
	
	//查询总数
	public int getPicTotal(Map<String,Object> param) throws SQLException {
		
		int count=0;
		Connection conn = JDBCUtils.getConnection(); 
		String sql = "SELECT count(*) from img_history WHERE retrial_status = '1' and province = '3310000' ";
		PreparedStatement ps = null;
		//条件查询
		StringBuilder bsql = new StringBuilder();
		bsql.append(sql);
		List<Object> listparam = new ArrayList<Object>();
		if (!param.get("manufactor").equals("")&&param.get("manufactor")!=null) {
			bsql.append(" and information like \"%\"?\"%\" ");
			listparam.add(param.get("manufactor").toString());
		}
		if (!param.get("domain").equals("")&&param.get("domain")!=null) {
			bsql.append(" and img_host like \"%\"?\"%\" ");
			listparam.add(param.get("domain").toString());
		}
		if (!param.get("ip").equals("")&&param.get("ip")!=null) {
			bsql.append(" and iplist like \"%\"?\"%\" ");
			listparam.add(param.get("ip").toString());
		}
		if (!param.get("startdate").equals("")&&param.get("startdate")!=null) {
			bsql.append(" and insertdate >= ? ");
			listparam.add(param.get("startdate").toString());
		}
		if (!param.get("enddate").equals("")&&param.get("enddate")!=null) {
			bsql.append(" and insertdate <= ? ");
			listparam.add(param.get("enddate").toString());
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
        JDBCUtils.closeConnection(conn);
		return count;
	}
	
	//查询总数
	public int getVideoTotal(Map<String,Object> param) throws SQLException {
		
		int count=0;
		Connection conn = JDBCUtils.getConnection(); 
		String sql = "SELECT count(*) from video_history WHERE retrial_status = '1' and province = '3310000' ";
		PreparedStatement ps = null;
		//条件查询
		StringBuilder bsql = new StringBuilder();
		bsql.append(sql);
		List<Object> listparam = new ArrayList<Object>();
		if (!param.get("manufactor").equals("")&&param.get("manufactor")!=null) {
			bsql.append(" and information like \"%\"?\"%\" ");
			listparam.add(param.get("manufactor").toString());
		}
		if (!param.get("domain").equals("")&&param.get("domain")!=null) {
			bsql.append(" and video_host like \"%\"?\"%\" ");
			listparam.add(param.get("domain").toString());
		}
		if (!param.get("ip").equals("")&&param.get("ip")!=null) {
			bsql.append(" and iplist like \"%\"?\"%\" ");
			listparam.add(param.get("ip").toString());
		}
		if (!param.get("startdate").equals("")&&param.get("startdate")!=null) {
			bsql.append(" and insertdate >= ? ");
			listparam.add(param.get("startdate").toString());
		}
		if (!param.get("enddate").equals("")&&param.get("enddate")!=null) {
			bsql.append(" and insertdate <= ? ");
			listparam.add(param.get("enddate").toString());
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
        JDBCUtils.closeConnection(conn);
		return count;
	}
	
	
	
}
