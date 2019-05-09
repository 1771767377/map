package com.zicms.web.biaoge.controller;

import com.zicms.web.biaoge.model.Checked;
import com.zicms.web.biaoge.service.CheckedService;
import com.zicms.web.datacenter.model.ProvinceDict;
import com.zicms.web.datacenter.service.ProvinceDictService;
import com.zicms.web.util.DateUtils;
import com.zicms.web.util.FileUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*审核完成率统计*/
@Controller
@RequestMapping(value = "check")
public class CheckedController {

	@Resource
	private CheckedService checkedService;
	@Resource
	private ProvinceDictService provinceDictService;

	private static final Logger logger = LoggerFactory
			.getLogger(FileUtil.class);

	/**
	 * 跳转到审核完成率显示标题页面
	 */
	@RequestMapping
	public String toImageCheck(Model model) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		String startDefault = sdf1.format(cal.getTime());
		String endDefault = sdf2.format(cal.getTime());
		// 获得当前账号审核的省份
		List<ProvinceDict> provinces = provinceDictService.findProvinces();
		model.addAttribute("provinces", provinces);
		model.addAttribute("startDefault", startDefault);
		model.addAttribute("endDefault", endDefault);
		return "biaoge/checked/checked_manager";
	}

	/**
	 * 审核完成率统计列表页面
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String checked(Model model, HttpServletRequest request,
			@RequestParam Map<String, Object> params) {
		// 查询省份、采集日期，待审核量和已审核量，默认状态下是昨天一天的审核量以及待审核量
		String zone = request.getParameter("zone");
		String dateStart = request.getParameter("dateStart");// 统计开始时间
		String dateEnd = request.getParameter("dateEnd");// 统计结束时间
		params.put("zone", zone);
		List<Checked> page = null;
		if (dateStart == null || dateStart.isEmpty() || dateEnd == null
				|| dateEnd.isEmpty()) {
			// 日期范围为空，按照省份或者不按照省份查询(默认上一天)
			page = checkedService.findPageAll(params);
		} else {
			// 日期范围不为空
			if (dateStart == null || dateEnd == null) {// 仍然按照日期为上一天查询
				// page = checkedService.findPageAll(params);
			} else {// 按照日期范围不为空查询
				params.put("dateStart", dateStart);
				params.put("dateEnd", dateEnd);
				page = checkedService.findPageAllByDate(params);
			}
		}
		model.addAttribute("page", page);
		return "biaoge/checked/checked_list";
	}

	/**
	 * 重新加载侧边栏
	 * 
	 * @param params
	 * @param
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "selectAll", method = RequestMethod.GET)
	public String selectAll(@RequestParam Map<String, Object> params,
			Model model, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String zone = request.getParameter("zone");
		String province = request.getParameter("province");
		String dateStart = request.getParameter("dateStart");// 统计开始时间
		String dateEnd = request.getParameter("dateEnd");// 统计结束时间
		model.addAttribute("zone", zone);
		model.addAttribute("province", province);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		return "biaoge/checked/checkedSift";
	}

	/**
	 * 获取所有的省份
	 */
	@RequestMapping(value = "checkedEchart", method = RequestMethod.GET)
	public List<Checked> getAllProvince(HttpServletResponse response,
			@RequestParam Map<String, Object> params, HttpServletRequest request) {
		String zone = (String) params.get("zone");
		String dateStart = request.getParameter("dateStart");// 统计开始时间
		String dateEnd = request.getParameter("dateEnd");// 统计结束时间
		if (zone == null || zone.isEmpty()) {
			logger.error("省份参数为空啊，这咋能行呢");
		}
		List<Checked> checkeds = null;
		if (dateStart == null || dateStart.isEmpty() || dateEnd == null
				|| dateEnd.isEmpty()) {
			// 日期范围为空，按照省份查询昨日各小时时间段的审核率
			// 获取上一天的时间
			dateStart = DateUtils.getNextDay_1(new Date()).get(1);
			dateEnd = DateUtils.getNextDay_1(new Date()).get(0);
			params.put("dateStart", dateStart);
			params.put("dateEnd", dateEnd);
		} else {
			// 日期范围不为空
			params.put("dateStart", dateStart);
			params.put("dateEnd", dateEnd);
		}
		// 获取日期中的天数
		int day1 = DateUtils.getDay(dateStart);
		int day2 = DateUtils.getDay(dateEnd);
		int days = DateUtils.daysBetween(dateStart, dateEnd);
		// 解决24点是明天1点的问题
		String regex = "\\d{4}(\\-)(\\d{2})(\\-)(\\d{2})(\\s)([0]{2})(:)(\\d{2})(:)(\\d{2})";
		if (dateEnd.matches(regex)) {
			// 包含月末和月初之间的流转
			if (day2 - day1 == 1 || (day1 == 28 && day2 == 1)
					|| (day1 == 29 && day2 == 1) || (day1 == 30 && day2 == 1)
					|| (day1 == 31 && day2 == 1)) {// 还是同一天
				day2 = day1;
			}
		}
		if (day1 == day2) {
			checkeds = checkedService.findAllByHour(params);// 按照小时查询数据并显示在表中
		} else {// 按天查询(保证7天，超过则按照起始日期加到正好7天的时间)
			if (dateEnd.matches(regex)) {// 最终时间是0点，按照天数大于7计算
				if (days > 7) {// 按照起始日期七天计算，如果最终时间是0点，则大于7否则大于6
					if (dateStart.matches(regex)) {// 开始日期也匹配
						dateEnd = DateUtils.getNewDate(dateStart, 7);
					} else {
						dateEnd = DateUtils.getNewDate(dateStart, 6);
					}
				}
			} else {// 最终时间不是0点，按照天数大于6计算
				if (days > 6) {// 按照起始日期七天计算，如果最终时间是0点，则大于7否则大于6
					if (dateStart.matches(regex)) {// 开始日期也匹配
						dateEnd = DateUtils.getNewDate(dateStart, 7);
					} else {
						dateEnd = DateUtils.getNewDate(dateStart, 6);
					}
				}
			}
			params.put("dateEnd", dateEnd);
			checkeds = checkedService.findAllByDay(params);// 按照天查询数据并显示在表中,显示七天
		}
		try {
			JSONArray pro = JSONArray.fromObject(checkeds.toArray());
			response.getWriter().print(pro);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return checkeds;
	}
}
