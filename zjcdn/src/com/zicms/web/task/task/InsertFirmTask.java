package com.zicms.web.task.task;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zicms.web.ipip.City;
import com.zicms.web.overview.mapper.OverviewMapper;
import com.zicms.web.overview.model.TopFive;
import com.zicms.web.overview.service.OverviewService;
import com.zicms.web.task.service.MaintainTaskDefinitionService;
import com.zicms.web.util.HttpUtil;

/**
 * 定时更新数据库中域名对应的公司，ip对应的供应商
 * @author forever 2018-12-12
 *
 */
@Service("insertFirmTask")
public class InsertFirmTask {
	private static final String table[] = {"image_domain_top","image_ip_top","text_domain_top","text_ip_top"};
	private static final Logger log = LoggerFactory.getLogger(InsertFirmTask.class);
	@Resource
	private MaintainTaskDefinitionService taskService;
	
	@Resource OverviewMapper overviewMapper;
	
	public void run() throws InterruptedException {
		Thread t1 = new Thread(new Thread1());
		Thread t2 = new Thread(new Thread2());
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		log.info("数据更新成功！");
	}
	
	//更新域名厂商
	class Thread1 implements Runnable{
		public void run() {
			for(int i=0; i<table.length; i=i+2) {
				List<TopFive> domainTopn = overviewMapper.getDomainTopn(table[i], 10);
				for(TopFive top: domainTopn) {
					String result = HttpUtil.get("http://39.155.242.100:8899/register?domain="+top.getResourceDomain());
					String name = JSONObject.parseObject(result).getString("name");
					if("null".equals(name)) {
						top.setDomainFirm("未知");
					}else {
						top.setDomainFirm(name);
					}
				}
				overviewMapper.updateDomainFirm(table[i],domainTopn);
			}
		}
	}
	
	//更新ip运营商
	class Thread2 implements Runnable{
		public void run() {
			for(int i=1; i<table.length; i=i+2) {
				List<TopFive> iptopn = overviewMapper.getIpTopn(table[i], 10);
				//读取ipip文件
				String filepath = OverviewService.class.getClassLoader().getResource("ipip/mydata4vipweek2.datx").getPath();
				try{
					City city = new City(filepath.replaceFirst("/", ""));
					for(TopFive tf: iptopn){
						String[] info = city.find(tf.getResourceIp());
						tf.setIpOperators(info[info.length-1]);
					}
				}catch (Exception e) {
					log.error("获取运营商信息失败！");
					e.printStackTrace();
				}
				overviewMapper.updateIpOperators(table[i],iptopn);
			}
		}
	}
}
