package com.robi.foodiy.aop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.robi.data.ApiResult;
import com.robi.exception.ApiException;
import com.robi.util.RandomUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Aspect
@Component
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class ControllerAop {

    private final static Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    @Around("execution(* com.robi.foodiy.controller.page..*.*(..))")
    public Object aroundPageController(ProceedingJoinPoint pjp) {
        // Initialize controller parameters
        final long ctrBgnTimeMs = System.currentTimeMillis();
        final String traceId = RandomUtil.genRandomStr(16, RandomUtil.ALPHABET | RandomUtil.NUMERIC);
        final String oldLayer = MDC.get("layer");

        // Logger init
        MDC.put("tId", traceId);
        MDC.put("layer", "CTR");
        final Signature sign = pjp.getSignature();
        logger.info("<<PageCtrBgn>> '{}.{}()'", sign.getDeclaringTypeName(), sign.getName());

        // Logging request
        ServletRequestAttributes servletReqAttr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletReqAttr.getRequest();
        StringBuilder reqSb = new StringBuilder(512);

        String reqInfo = request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol();
        logger.info("<Req> '{}'", reqInfo);
        logger.info(" -> URL: " + request.getRequestURL().toString());

        Enumeration<String> headerEnum = request.getHeaderNames();
        while (headerEnum.hasMoreElements()) {
            String headerKey = headerEnum.nextElement();
            reqSb.append("'").append(headerKey).append("':'").append(request.getHeader(headerKey)).append("', ");
        }

        if (reqSb.length() > 2) reqSb.setLength(reqSb.length() - 2);
        logger.info(" -> Headers: {" + reqSb.toString() + "}");
        reqSb.setLength(0);

        // 이 방식으로 url파라미터의 바디를 체크하면 무한루프를 돌면서 힙으로 터져버린다
        //Enumeration<String> bodyEnum = request.getParameterNames();
        //while (bodyEnum.hasMoreElements()) {
            //String bodyKey = headerEnum.nextElement();
            //reqSb.append("'").append(bodyKey).append("':'").append(request.getParameter(bodyKey)).append("', ");
        //}
        
        //if (reqSb.length() > 2) reqSb.setLength(reqSb.length() - 2);
        //logger.info(" -> Bodies: {" + reqSb.toString() + "}");
        //reqSb.setLength(0);
        
        // Controller works
        Object ctrRtnObj = null;
        
        try {
            ctrRtnObj = pjp.proceed();
            
            if (ctrRtnObj instanceof ModelAndView) {
                // ...
            }
            else {
                throw new RuntimeException("Controller must return 'ModelAndView' class! (ctrRtnObj: " +
                                            ctrRtnObj.getClass().getName() + ")");
            }
        }
        catch (ApiException e) {
            // Rpy exception msg
            logger.error("ApiException in controller! {}", e);
        }
        catch (Throwable e) {
            // Rpy common msg
            logger.error("Exception in controller AOP! {}", e);
        }

        // Logging reply
        final long ctrEndTimeMs = System.currentTimeMillis();
        final long timeElapsedMs = ctrEndTimeMs - ctrBgnTimeMs;
        logger.info("<Rpy> '{}'", ctrRtnObj == null ? "null" : ctrRtnObj.toString());
        logger.info("<<PageCtrEnd>> (Time: {}ms)", timeElapsedMs);

        // Logger deinit
        MDC.put("layer", oldLayer);
        MDC.put("tId", "");

        // Return API result
        return ctrRtnObj;
    }

    @Around("execution(* com.robi.foodiy.controller.api..*.*(..))")
    public Object aroundApiController(ProceedingJoinPoint pjp) {
        // Initialize controller parameters
        final long ctrBgnTimeMs = System.currentTimeMillis();
        final String traceId = RandomUtil.genRandomStr(16, RandomUtil.ALPHABET | RandomUtil.NUMERIC);
        final String oldLayer = MDC.get("layer");

        // Logger init
        MDC.put("tId", traceId);
        MDC.put("layer", "CTR");
        final Signature sign = pjp.getSignature();
        logger.info("<<ApiCtrBgn>> '{}.{}()'", sign.getDeclaringTypeName(), sign.getName());

        // Logging request
        ServletRequestAttributes servletReqAttr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletReqAttr.getRequest();
        StringBuilder reqSb = new StringBuilder(512);

        String reqInfo = request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol();
        logger.info("<Req> '{}'", reqInfo);
        logger.info(" -> URL: " + request.getRequestURL().toString());

        Enumeration<String> headerEnum = request.getHeaderNames();
        while (headerEnum.hasMoreElements()) {
            String headerKey = headerEnum.nextElement();
            reqSb.append("'").append(headerKey).append("':'").append(request.getHeader(headerKey)).append("', ");
        }

        if (reqSb.length() > 2) reqSb.setLength(reqSb.length() - 2);
        logger.info(" -> Headers: {" + reqSb.toString() + "}");
        reqSb.setLength(0);

        Enumeration<String> bodyEnum = request.getParameterNames();
        while (bodyEnum.hasMoreElements()) {
            String bodyKey = bodyEnum.nextElement();
            if (bodyKey == null) {
                continue;
            }
            reqSb.append("'").append(bodyKey).append("':'").append(request.getParameter(bodyKey)).append("', ");
        }
        
        if (reqSb.length() > 2) reqSb.setLength(reqSb.length() - 2);
        logger.info(" -> Bodies: {" + reqSb.toString() + "}");
        reqSb.setLength(0);
        
        // Controller works
        ApiResult apiRst = ApiResult.make(false);
        apiRst.setTraceId(traceId);

        try {
            Object ctrRtnObj = pjp.proceed();
            
            if (ctrRtnObj instanceof ApiResult) {
                ApiResult ctrRtn = (ApiResult) ctrRtnObj;
                apiRst.setResult(ctrRtn.getResult());
                apiRst.setResultCode(ctrRtn.getResultCode());
                apiRst.setResultMsg(ctrRtn.getResultMsg());
                apiRst.addData(ctrRtn.getData());
            }
            else {
                throw new RuntimeException("Controller must return 'ApiResult' class! (ctrRtnObj: " +
                                            ctrRtnObj.getClass().getName() + ")");
            }
        }
        catch (ApiException e) {
            // Rpy exception msg
            logger.error("ApiException in controller!", e);
            apiRst.setResultMsg(e.getMessage());
        }
        catch (Throwable e) {
            // Rpy common msg
            logger.error("Exception in controller AOP!", e);
            apiRst.setResultMsg("CTR_INTERNAL_ERROR");
        }

        // Logging reply
        final long ctrEndTimeMs = System.currentTimeMillis();
        final long timeElapsedMs = ctrEndTimeMs - ctrBgnTimeMs;
        logger.info("<Rpy> '{}'", apiRst.toString());
        logger.info("<<ApiCtrEnd>> (Time: {}ms)", timeElapsedMs);

        // Logger deinit
        MDC.put("layer", oldLayer);
        MDC.put("tId", "");

        // Return API result
        return apiRst;
    }
}