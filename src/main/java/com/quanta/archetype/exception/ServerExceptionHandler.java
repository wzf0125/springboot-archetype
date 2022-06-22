package com.quanta.archetype.exception;

import com.alibaba.fastjson.JSON;
import com.quanta.archetype.bean.JsonResponse;
import com.quanta.archetype.utils.WechatBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author Leslie Leung
 * @date 2021/12/14
 */
@Slf4j
@ControllerAdvice
@Order(1)
public class ServerExceptionHandler {

    private static final String ERROR_MESSAGE_TEMPLATE = "%s\n[Msg]%s\n[File]%s\n[LogId]%s\n[Url]%s\n[Args]%s\n[Token]%s";

    @Value("${project.isDebug}")
    private boolean isDebug;

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    JsonResponse<Object> errorResult(Exception e) throws IOException {
        log.error(e.getMessage());
        if(isDebug){ // 本地调试只输出错误信息
            e.printStackTrace();
        }else{
            WechatBot.send(createErrorMessage(e));
        }
        return JsonResponse.error(String.format("操作失败，请重试(Server)[%s]", e.getMessage()));
    }
    private String createErrorMessage(Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String logId = (String) request.getAttribute("requestId");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDate = formatter.format(date);
        String msg = String.format(ERROR_MESSAGE_TEMPLATE,
                currentDate,
                e.getMessage(),
                e.getStackTrace()[0].getFileName() + "." + e.getStackTrace()[0].getMethodName() + " (line: " + e.getStackTrace()[0].getLineNumber() + ")",
                logId,
                request.getRequestURL().toString(),
                request.getAttribute("args"),
                request.getHeader("Token"));
        Map<String, Object> req = new HashMap<>();
        req.put("msgtype", "text");
        Map<String, String> content = new HashMap<>();
        content.put("content", msg);
        req.put("text", content);
        return JSON.toJSONString(req);
    }

}
