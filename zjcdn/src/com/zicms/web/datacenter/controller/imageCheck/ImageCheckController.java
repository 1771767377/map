package com.zicms.web.datacenter.controller.imageCheck;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.datacenter.model.ImageCheck;
import com.zicms.web.datacenter.service.ImageCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.util.FileUtil;

@Controller
@RequestMapping(value = "Check")
public class ImageCheckController {

    @Resource
    private ImageCheckService imageCheckService;

    public static final String FILE_PATH = "/upload/";

    /**
     * 跳转到全部图片显示页面（未审核）
     */
    @RequestMapping
    public String toImageCheck(Model model) {
        String user_id = SysUserUtils.getSessionLoginUser().getUsername();
        String name = SysUserUtils.getSessionLoginUser().getName();
        System.out.println(user_id);
        System.out.println(name);

        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        int count = imageCheckService.getCount(proArr);
        model.addAttribute("count", count);
        return "check/image-manager";
    }

    @RequestMapping(value = "/url")
    public String toUrl(Model model) {
        return "check/iframe";
    }

    /**
     * 进入到初审页面
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String list(Model model, HttpServletRequest request) {
        String imageturl = request.getParameter("imageturl");
        String date = request.getParameter("date");
        String iplist = request.getParameter("iplist");
        SysUser user = SysUserUtils.getSessionLoginUser();
        String trialAccount = user.getUsername();
        String province = user.getProvince();
        Map<String, Object> param = new HashMap<String, Object>();
        List<ImageCheck> list = null;
        param.put("trialAccount", trialAccount);
        param.put("province", province);
        param.put("imageturl", imageturl);
        param.put("date", date);
        param.put("iplist", iplist);
        if (imageturl == "" && iplist == "" && date == "") {
            list = imageCheckService.findPageAll(param);
        } else {
            list = imageCheckService.findList(param);
        }
        if(list.size()>0){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String trialTime = sdf.format(new Date());
        	ImageCheck imag = new ImageCheck();
        	imag.setTrialAccount(trialAccount);
        	imag.setTrialTime(trialTime);
        	imag.setTrialStatus(2);
        	imageCheckService.updateTop(imag, list);
        }
        model.addAttribute("page", list);
        return "check/image-list";
    }

    /**
     * 更新图片信息(双击图片)
     */
    @RequestMapping(value = "check_image", method = RequestMethod.POST)
    public @ResponseBody
    Integer result_image(@ModelAttribute ImageCheck imag, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String checkdate = sdf.format(new Date());
        imag.setTrialStatus(1);
        imag.setRetrialStatus(0);
        imag.setCheckdate(checkdate);
        imageCheckService.updateToBad(imag);
        return null;
    }

    /**
     * 更新图片信息（点击不确定）
     */
    @RequestMapping(value = "unconfirm", method = RequestMethod.POST)
    public @ResponseBody
    Integer unconfirm(@ModelAttribute ImageCheck imag, HttpServletRequest request) {
    	 SysUser user = SysUserUtils.getSessionLoginUser();
         String trialAccount = user.getUsername();
         String uuid = request.getParameter("uuid");
         imag.setTrialStatus(3);
         imag.setTrialAccount(trialAccount);
         imag.setUuid(uuid);
         int count = imageCheckService.updateToUnconfirm(imag);
         return count;
    }

    /**
     * 撤销图片已选状态（将图片状态更改为正常）
     * 
     * @param imag
     * @param mode
     * @param request
     */
    @RequestMapping(value = "/revoke_image", method = RequestMethod.POST)
    public void approve_save(@ModelAttribute ImageCheck imag, HttpServletRequest request) {
        imag.setTrialStatus(2);
        imageCheckService.updateToNormal(imag);
    }
    
    /**
     * 提交最后一页数据
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String submit(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String path = request.getContextPath();
    	ImageCheck imag = new ImageCheck();
        SysUser user = SysUserUtils.getSessionLoginUser();
        // 获取当前用户账号
        String trialAccount = user.getUsername();
        imag.setTrialAccount(trialAccount);
        int i = imageCheckService.submitPage(imag);
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

    /**
     * 回滚一页数据(已审的数据重新再审一次)
     */
    @RequestMapping(value="rollback", method = RequestMethod.POST)
    public void rollback(){
    	SysUser user = SysUserUtils.getSessionLoginUser();
    	String username = user.getUsername();
    	imageCheckService.rollback(username);
    }
    
   

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /** CSV文件列分隔符 */
    private static final String CSV_COLUMN_SEPARATOR = ",";

    /** CSV文件行分隔符 */
    private static final String CSV_RN = "\r\n";

    /**
     * 
     * @param dataList 集合数据
     * @param colNames 表头部数据
     * @param mapKey 查找的对应数据
     * @param response 返回结果
     */
    public static boolean doExport(List<Map<String, Object>> dataList, String colNames, String mapKey, OutputStream os) {
        try {
            StringBuffer buf = new StringBuffer();

            String[] colNamesArr = null;
            String[] mapKeyArr = null;

            colNamesArr = colNames.split(",");
            mapKeyArr = mapKey.split(",");

            // 完成数据csv文件的封装
            // 输出列头
            for (int i = 0; i < colNamesArr.length; i++) {
                buf.append(colNamesArr[i]).append(CSV_COLUMN_SEPARATOR);
            }
            buf.append(CSV_RN);

            if (null != dataList) { // 输出数据
                for (int i = 0; i < dataList.size(); i++) {
                    for (int j = 0; j < mapKeyArr.length; j++) {
                        buf.append(dataList.get(i).get(mapKeyArr[j])).append(CSV_COLUMN_SEPARATOR);
                    }
                    buf.append(CSV_RN);
                }
            }
            // 写出响应
            os.write(buf.toString().getBytes("GBK"));
            os.flush();
            return true;
        } catch (Exception e) {
            logger.error("doExport错误...", e);
        }
        return false;
    }

    /**
     * @throws UnsupportedEncodingException
     * 
     *         setHeader
     */
    public static void responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fn = fileName + sdf.format(new Date()).toString() + ".csv";
        // 读取字符编码
        String utf = "UTF-8";

        // 设置响应
        response.setContentType("application/ms-txt.numberformat:@"); // 之前成功代码
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
    }

    /**
     * 设置让浏览器弹出下载对话框的Header. 根据浏览器的不同设置不同的编码格式 防止中文乱码
     * 
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = null;
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                encodedfileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            } else {
                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 菜单选择
     */
    @RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
    public String showLayer(@PathVariable("mode") String mode, Model model, HttpServletRequest request) {
        String imageturl = request.getParameter("imageturl");
        // List<ImageCheck> list = imageCheckService.findUrl(imageturl);
        ImageCheck image = imageCheckService.findUrl(imageturl);
        model.addAttribute("page", image);
        return "check/save";
    }

    @RequestMapping(value = "{mode}/layer", method = RequestMethod.GET)
    public String show(@PathVariable("mode") String mode, Model model, HttpServletRequest request) {
        String imageturl = request.getParameter("imageturl");
        ImageCheck image = imageCheckService.findUrl(imageturl);
        model.addAttribute("page", image);
        return "check/save";
    }

    @RequestMapping(value = "{mode}/showcsv", method = RequestMethod.GET)
    public String showCsv(@PathVariable("mode") String mode, Model model, HttpServletRequest request) {
        return "check/pic";
    }

}
