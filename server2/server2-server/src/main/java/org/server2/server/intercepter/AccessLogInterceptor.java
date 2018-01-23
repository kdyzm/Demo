package org.server2.server.intercepter;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author t_zhengrj
 * @date 2018-01-23
 */
@WebServlet
@EnableWebMvc
@Component
public class AccessLogInterceptor implements HandlerInterceptor, WebMvcConfigurer {

	@Resource(name = "charsetConverter")
	private HttpMessageConverter<String> converter;

	private static final String REQUEST_START_TIME = "REQUEST_START_TIME";

	private static final String HEADER_USER_AGENT      = "User-Agent";

    private static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";

    private static final String REMOTE_ADDR            = "remoteAddress";
    
    private static final String ACCESS_SEPERATOR       = " ";
    
    private static final String TRACE_ID = "traceId";

    private static final String TIME_UNIT              = "ms";
    
	private static final Logger logger = LoggerFactory.getLogger("access");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Object startTimestamp = request.getAttribute(REQUEST_START_TIME);
		long requestCostTime = 0L;
		if (startTimestamp != null) {
			requestCostTime = System.currentTimeMillis() - (long) startTimestamp;
		}
		
		logger.info(constructAccessLog(null, request.getRemoteAddr(),
				request.getHeader(HEADER_X_FORWARDED_FOR), request.getMethod(),
				request.getRequestURL().toString(),
				response.getStatus(),
	            request.getQueryString(),
	            request.getHeader(HEADER_USER_AGENT), requestCostTime));
	}
	
	/**
     * construct access log content
     * @param method
     * @param uri
     * @param queryString
     * @param userAgent
     * @param cost
     * @return
     */
    private String constructAccessLog(String traceId, String remoteAddr, String forwardip, String method,
                                      String uri, int status, String queryString, String userAgent,
                                      long cost) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(method);
        stringBuilder.append(ACCESS_SEPERATOR);
        stringBuilder.append(uri);
        if (queryString != null && queryString != "") {
            stringBuilder.append("?");
            stringBuilder.append(queryString);
        }
        
        if (traceId != null && !"".equals(traceId)) {
            stringBuilder.append(ACCESS_SEPERATOR);
            stringBuilder.append("[");
            stringBuilder.append(TRACE_ID);
            stringBuilder.append(":");
            stringBuilder.append(traceId);
            stringBuilder.append("]");
        }
        
        if (remoteAddr != null && !"".equals(remoteAddr)) {
            stringBuilder.append(ACCESS_SEPERATOR);
            stringBuilder.append("[");
            stringBuilder.append(REMOTE_ADDR);
            stringBuilder.append(":");
            stringBuilder.append(remoteAddr);
            stringBuilder.append("]");
        }

        if (forwardip != null && !"".equals(forwardip)) {
            stringBuilder.append(ACCESS_SEPERATOR);
            stringBuilder.append("[");
            stringBuilder.append(HEADER_X_FORWARDED_FOR);
            stringBuilder.append(":");
            stringBuilder.append(forwardip);
            stringBuilder.append("]");
        }
        stringBuilder.append(ACCESS_SEPERATOR);
        stringBuilder.append("[");
        stringBuilder.append(userAgent);
        stringBuilder.append("]");
        stringBuilder.append(ACCESS_SEPERATOR);
        stringBuilder.append(status);
        stringBuilder.append(ACCESS_SEPERATOR);
        stringBuilder.append(String.valueOf(cost));
        stringBuilder.append(String.valueOf(TIME_UNIT));
        return stringBuilder.toString();

    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this).addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		WebMvcConfigurer.super.configureMessageConverters(converters);
		converters.add(converter);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		WebMvcConfigurer.super.configureContentNegotiation(configurer);
		configurer.favorPathExtension(false);
	}
}
