package com.cmcc.zysoft.spring_mvc.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

public class CustomMultipartHttpServletRequest extends DefaultMultipartHttpServletRequest{

	public CustomMultipartHttpServletRequest(HttpServletRequest request) {
		super(request);
	}
	
	public CustomMultipartHttpServletRequest(HttpServletRequest request, MultiValueMap<String, MultipartFile> mpFiles,
			Map<String, String[]> mpParams, Map<String, String> mpParamContentTypes) {
		super(request);
		setMultipartFiles(mpFiles);
		setMultipartParameters(mpParams);
		setMultipartParameterContentTypes(mpParamContentTypes);
	}
	
	@Override
	public String getParameter(String name) {
		String[] values = getMultipartParameters().get(name);
		if (values != null) {
			return (values.length > 0 ? stripXSS(values[0]) : null);
		}
		return stripXSS(super.getParameter(name));
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = getMultipartParameters().get(name);
		if (values != null) {
			return stripXSS(values);
		}
		return stripXSS(super.getParameterValues(name));
	}
	
	
	private String[] stripXSS(String values[]) {
		if(values != null) {
			for(int i=0;i<values.length;i++) {
				values[i] = stripXSS(values[i]);
			}
		}
		return values;
	}
	
	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符.
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	private String stripXSS(String value) {
		if (value != null) {

			value = htmlEncode(value);

			// // NOTE: It's highly recommended to use the ESAPI library and
			// // uncomment the following line to
			// // avoid encoded attacks.
			// // value = encoder.canonicalize(value);
			// value = value.replaceAll("\0", "");
			//
			// // Avoid anything between script tags
			// Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",
			// Pattern.CASE_INSENSITIVE);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// // Avoid anything in a src='...' type of expression
			// scriptPattern =
			// Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
			// Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
			// | Pattern.DOTALL);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// scriptPattern =
			// Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
			// Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
			// | Pattern.DOTALL);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// // Remove any lonesome </script> tag
			// scriptPattern = Pattern.compile("</script>",
			// Pattern.CASE_INSENSITIVE);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// // Remove any lonesome <script ...> tag
			// scriptPattern = Pattern.compile("<script(.*?)>",
			// Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
			// | Pattern.DOTALL);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// // Avoid eval(...) expressions
			// scriptPattern = Pattern.compile("eval\\((.*?)\\)",
			// Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
			// | Pattern.DOTALL);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// // Avoid expression(...) expressions
			// scriptPattern = Pattern.compile("expression\\((.*?)\\)",
			// Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
			// | Pattern.DOTALL);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// // Avoid javascript:... expressions
			// scriptPattern = Pattern.compile("javascript:",
			// Pattern.CASE_INSENSITIVE);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// // Avoid vbscript:... expressions
			// scriptPattern = Pattern.compile("vbscript:",
			// Pattern.CASE_INSENSITIVE);
			// value = scriptPattern.matcher(value).replaceAll("");
			//
			// // Avoid onload= expressions
			// scriptPattern = Pattern.compile("onload(.*?)=",
			// Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
			// | Pattern.DOTALL);
			// value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符.
	 * 
	 * @param source
	 *            String
	 * @return String
	 */
	public static String htmlEncode(String source) {
		if (source == null) {
			return "";
		}
		String html = "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			switch (c) {
			case '<':
				buffer.append("&lt;");
				break;
			case '>':
				buffer.append("&gt;");
				break;
			case '&':
				buffer.append("&amp;");
				break;
			case '"':
				buffer.append("&quot;");
				break;
			case 10:
			case 13:
				break;
			default:
				buffer.append(c);
			}
		}
		html = buffer.toString();
		return html;
	}
}
