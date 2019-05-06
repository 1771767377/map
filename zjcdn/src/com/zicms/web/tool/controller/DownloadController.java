package com.zicms.web.tool.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zicms.common.utils.FileUtil;
import com.zicms.web.sys.service.SysConfigService;
import com.zicms.web.tool.model.Doc;
import com.zicms.web.tool.service.DocService;
import com.zicms.web.util.ConstantUtil;
import com.zicms.web.util.ResponseUtils;

@Controller
@RequestMapping("download")
public class DownloadController {

    @Resource
    private DocService docService;

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 在线查看
     * 
     * @param id
     * @param md5
     * @throws Exception
     */
    @RequestMapping("doc/view")
    public void onlinesee(Long id, String md5, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Doc doc = docService.findDoc(id, md5);
        File file = null;
        if (doc != null) {
            file = new File(doc.getPath());
            if (file.exists()) {
                if (ConstantUtil.ON_LINE_SEE_SUFFIX.contains(doc.getSuffix())) {
                    FileUtil.downloadFile(response, file, file.getName(), true);
                } else {
                    ResponseUtils.renderText(response, "不支持的文件类型");
                }
            }
        }
        if (file == null) {
            ResponseUtils.renderText(response, "文件不存在");
        }
    }

    /*
     * 下载
     * 
     * @param id
     * 
     * @param md5
     * 
     * @throws Exception
     */
    @RequestMapping("doc")
    public void download(Long id, String md5, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Doc doc = docService.findDoc(id, md5);
        File file = null;
        if (doc != null) {
            file = new File(doc.getPath());
            if (file.exists()) {
                doc.setDownloadCount(doc.getDownloadCount() == null ? 1 : doc.getDownloadCount() + 1);
                docService.updateByPrimaryKeySelective(doc);
                FileUtil.downloadFile(response, file, file.getName(), false);
            }
        }
        if (file == null) {
            ResponseUtils.renderText(response, "文件不存在");
        }
    }

    /*
     * 下载
     * 
     * @param id
     * 
     * @param md5
     * 
     * @throws Exception
     */
    @RequestMapping(value = "attach", method = RequestMethod.POST)
    public void attach(Long id, Integer type, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // List<Doc> docs = docService.findDoc(id, type);
        //
        // if (docs != null && !docs.isEmpty()) {
        // if (docs.size() == 1) {
        // File file = new File(docs.get(0).getPath());
        // if (file != null && file.exists()) {
        // FileUtils
        // .downloadFile(response, file, file.getName(), false);
        // } else {
        // ResponseUtils.renderText(response, "文件不存在");
        // }
        // } else {
        // Map<File, String> downQuene = new HashMap<File, String>();
        // File file = null;
        // for (Doc d : docs) {
        // file = new File(d.getPath());
        // if(file != null && file.exists()){
        // downQuene.put(file, d.getName());
        // }
        // }
        // String packageName = URLDecoder
        // .decode(DateUtils.formatDate(new Date(),
        // "yyyy-MM-dd HH:mm:ss"), "UTF-8");
        // response.setContentType("application/zip");
        // response.setHeader(
        // "Content-Disposition",
        // "attachment;filename=\""
        // + new String(packageName.getBytes(),
        // "ISO-8859-1") + ".rar\"");
        // FileUtils.zipDownLoad(downQuene, response);
        // }
        // }
    }

}
