package com.xh.wechat.company.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 17:41
 */
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {
    ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            startTimeThreadLocal.set(currentTimeMillis);
            String requestURI = request.getRequestURI();
            String ipAddr = this.getIpAddr(request);
            String param = JSON.toJSONString(request.getParameterMap());
            String method = request.getMethod();

            MDC.put("uri", requestURI);
            MDC.put("ip", ipAddr);
            MDC.put("param", param);
            MDC.put("method", method);

            log.info("Request access info: uri=[{}], ip=[{}], param=[{}], method=[{}]", requestURI, ipAddr, param, method);
        } catch (Exception e) {
            log.error("request.log ", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            String requestURI = request.getRequestURI();
            String ipAddr = this.getIpAddr(request);
            String param = JSON.toJSONString(request.getParameterMap());
            String method = request.getMethod();
            int responseCode = response.getStatus();
            Long startTime = startTimeThreadLocal.get();
            Long cost = startTime == null ? 0L : System.currentTimeMillis() - startTime;

            MDC.put("code", responseCode + "");
            MDC.put("cost", cost + "");

            log.info("Request response info: uri=[{}], ip=[{}], param=[{}], method=[{}], response code=[{}] cost=[{}ms]", requestURI, ipAddr, param, method, responseCode, cost);
        } catch (Exception e) {
            log.error("request.log ", e);
        }
    }

    /**
     * 获取请求的ip地址
     *
     * @param request HttpServletRequest对象
     * @return ip地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ipAddr;
        try {
            ipAddr = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ipAddr) || "unknown".equalsIgnoreCase(ipAddr)) {
                ipAddr = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipAddr) || "unknown".equalsIgnoreCase(ipAddr)) {
                ipAddr = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipAddr) || "unknown".equalsIgnoreCase(ipAddr)) {
                ipAddr = request.getRemoteAddr();
            }
            if (Objects.equals(ipAddr, "0:0:0:0:0:0:0:1")) {
                ipAddr = "127.0.0.1";
            }
            if (ipAddr != null && ipAddr.length() > 15 && ipAddr.indexOf(",") > 0) {
                ipAddr = ipAddr.substring(0, ipAddr.indexOf(","));
            }
        } catch (Exception e) {
            log.error("获取请求ip地址发生异常: ", e);
            ipAddr = "0.0.0.0";
        }

        return ipAddr;
    }
}
