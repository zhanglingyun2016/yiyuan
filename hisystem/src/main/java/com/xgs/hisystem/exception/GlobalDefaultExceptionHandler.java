package com.xgs.hisystem.exception;

import com.xgs.hisystem.config.ServerConfig;
import com.xgs.hisystem.pojo.bo.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author XueGuiSheng
 * @date 2020/4/6
 * @description:
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @Autowired
    private ServerConfig serverConfig;

    private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    /**
     * 捕获系统未知异常
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception ex) {
        logger.error("系统错误！uri:{},错误信息:{}", req.getRequestURI(), ex);

        ModelAndView mv=new ModelAndView();
        mv.addObject("url", serverConfig.getUrl());
        mv.setViewName("error");

        return mv;
    }

    /**
     * 捕获参数校验失败异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse handleParamCheckExcepion(HttpServletRequest req, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder paramErrorMsg = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String msg = objectError.getDefaultMessage();
                if (paramErrorMsg.length() == 0) {
                    paramErrorMsg.append(msg);
                } else {
                    paramErrorMsg.append(',');
                    paramErrorMsg.append(msg);
                }
            }
        }
        logger.error("参数校验失败! uri:{},错误信息:{}", req.getRequestURI(), paramErrorMsg.toString());
        return BaseResponse.error(paramErrorMsg.toString());
    }
}
