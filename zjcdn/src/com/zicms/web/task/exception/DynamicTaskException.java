package com.zicms.web.task.exception;

@SuppressWarnings("serial")
public class DynamicTaskException extends RuntimeException {
    public DynamicTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
