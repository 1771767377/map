package com.zicms.web.datacenter.controller.textCheck;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "TextExport")
public class TextExportController {

    /**
     * 跳转到导出页面
     */
    @RequestMapping
    public String toExport(Model model) {
        return "export/export_text";
    }
}
