package com.zicms.common.beetl.function;

import org.springframework.stereotype.Component;

import com.zicms.common.utils.FileUtil;

@Component
public class FileFunction {

    public String formatFileSize(Long size) {
        if (size == null) {
            return "未知";
        }
        return FileUtil.getHumanSize(size);
    }
}
