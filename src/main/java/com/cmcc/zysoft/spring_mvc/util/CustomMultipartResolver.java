package com.cmcc.zysoft.spring_mvc.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 用于解决针对上传类型XSSfilter过滤失败的情况问题解决
 * @author xiao.yu@ustcinfo.com
 *
 */
public class CustomMultipartResolver extends CommonsMultipartResolver {

	private boolean resolveLazily = false;
	
	/**
	 * Set whether to resolve the multipart request lazily at the time of
	 * file or parameter access.
	 * <p>Default is "false", resolving the multipart elements immediately, throwing
	 * corresponding exceptions at the time of the {@link #resolveMultipart} call.
	 * Switch this to "true" for lazy multipart parsing, throwing parse exceptions
	 * once the application attempts to obtain multipart files or parameters.
	 */
	public void setResolveLazily(boolean resolveLazily) {
		this.resolveLazily = resolveLazily;
	}
	
	
	@Override
	public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) throws MultipartException {
		Assert.notNull(request, "Request must not be null");
		if (this.resolveLazily) {
			return new CustomMultipartHttpServletRequest(request) {
				@Override
				protected void initializeMultipart() {
					MultipartParsingResult parsingResult = parseRequest(request);
					setMultipartFiles(parsingResult.getMultipartFiles());
					setMultipartParameters(parsingResult.getMultipartParameters());
					setMultipartParameterContentTypes(parsingResult.getMultipartParameterContentTypes());
				}
			};
		}
		else {
			MultipartParsingResult parsingResult = parseRequest(request);
			return new CustomMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(),
					parsingResult.getMultipartParameters(), parsingResult.getMultipartParameterContentTypes());
		}
	}



}
