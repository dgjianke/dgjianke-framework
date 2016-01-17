package com.cmcc.zysoft.spring_mvc.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;



/**
 * @author 李三来
 * @date 2013-1-10
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 构造方法.
	 * 
	 * @param request
	 *            请求
	 */
	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 获取参数值.
	 * 
	 * @param parameter
	 *            String
	 * @return String[]
	 */
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = stripXSS(values[i]);
		}

		return encodedValues;
	}

	/**
	 * 覆盖getParameter方法.
	 * 
	 * <pre>
	 * 将参数名和参数值都做xss过滤.<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 * </pre>
	 * 
	 * @param parameter
	 *            String
	 * @return String
	 */
	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		return stripXSS(value);
	}

	/**
	 * 覆盖getHeader方法.
	 * 
	 * <pre>
	 * 将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 * </pre>
	 * 
	 * @param name
	 *            String
	 * @return String
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		return stripXSS(value);
	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return super.getAttribute(name);
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