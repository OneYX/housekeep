package com.connxun.config.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GzipJsFilter implements Filter {
	@SuppressWarnings("rawtypes")
	Map headers = new HashMap();

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			doFilter((HttpServletRequest) req, (HttpServletResponse) res, chain);
		} else {
			chain.doFilter(req, res);
		}
	}

	@SuppressWarnings("rawtypes")
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		for (Iterator it = headers.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			response.addHeader((String) entry.getKey(), (String) entry.getValue());
		}
		chain.doFilter(request, response);
	}

	@SuppressWarnings("unchecked")
	public void init(FilterConfig config) throws ServletException {
		String headersStr = config.getInitParameter("headers");
		String[] headers = headersStr.split(",");
		for (int i = 0; i < headers.length; i++) {
			String[] temp = headers[i].split("=");
			this.headers.put(temp[0].trim(), temp[1].trim());
		}
	}
}
