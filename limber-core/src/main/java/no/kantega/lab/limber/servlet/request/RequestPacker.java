package no.kantega.lab.limber.servlet.request;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestPacker {

    public RawRequest pack(HttpServletRequest httpServletRequest) {

        return new RawRequest(
                httpServletRequest.getSession(true).getId(),
                httpServletRequest.getRequestURI(),
                makeQueryMap(httpServletRequest.getQueryString()),
                makeHeaderMap(httpServletRequest),
                httpServletRequest.getScheme(),
                httpServletRequest.getServerName(),
                httpServletRequest.getLocalPort(),
                httpServletRequest.getMethod(),
                httpServletRequest.getServerName());

    }

    private Map<String, String> makeQueryMap(String queryString) {
        Map<String, String> queryMap = new HashMap<String, String>();
        if (queryString == null) {
            return queryMap;
        }
        for (String segment : queryString.split("&")) {
            int argumentIndex = segment.indexOf('=');
            if (argumentIndex == -1 || argumentIndex == segment.length() - 1) {
                queryMap.put(segment, null);
            } else {
                queryMap.put(segment.substring(0, argumentIndex), segment.substring(argumentIndex + 1));
            }
        }
        return queryMap;
    }

    private Map<String, String> makeHeaderMap(HttpServletRequest httpServletRequest) {
        Map<String, String> headerMap = new HashMap<String, String>();
        Enumeration<?> headerEnumeration = httpServletRequest.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String key = headerEnumeration.nextElement().toString();
            headerMap.put(key, httpServletRequest.getHeader(key));
        }
        return headerMap;
    }
}
