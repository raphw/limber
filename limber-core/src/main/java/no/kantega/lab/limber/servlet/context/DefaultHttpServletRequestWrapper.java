package no.kantega.lab.limber.servlet.context;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultHttpServletRequestWrapper implements IHttpServletRequestWrapper {

    private final HttpServletRequest httpServletRequest;
    private final Map<String, String> queryMap;
    private final Map<String, String> headerMap;

    public DefaultHttpServletRequestWrapper(@Nonnull HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        this.queryMap = makeQueryMap(httpServletRequest.getQueryString());
        this.headerMap = makeHeaderMap(httpServletRequest);
    }

    @Nonnull
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

    @Nonnull
    private Map<String, String> makeHeaderMap(@Nonnull HttpServletRequest httpServletRequest) {
        Map<String, String> headerMap = new HashMap<String, String>();
        Enumeration<?> headerEnumeration = httpServletRequest.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String key = headerEnumeration.nextElement().toString();
            headerMap.put(key, httpServletRequest.getHeader(key));
        }
        return headerMap;
    }

    @Nonnull
    @Override
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    @Nonnull
    @Override
    public String getSessionId() {
        return httpServletRequest.getSession(true).getId();
    }

    @Nonnull
    @Override
    public String getRequestUri() {
        return httpServletRequest.getRequestURI();
    }

    @Override
    public String getQueryArgument(@Nonnull CharSequence key) {
        return queryMap.get(key.toString());
    }

    @Nonnull
    @Override
    public Set<String> getQueryArgumentKeys() {
        return queryMap.keySet();
    }

    @Override
    public String getHeadArgument(@Nonnull CharSequence key) {
        return headerMap.get(key.toString());
    }

    @Nonnull
    @Override
    public Set<String> getHeadArgumentKeys() {
        return headerMap.keySet();
    }

    @Nonnull
    @Override
    public String getScheme() {
        return httpServletRequest.getScheme();
    }

    @Nonnull
    @Override
    public String getHost() {
        return httpServletRequest.getRemoteHost();
    }

    @Override
    public int getPort() {
        return httpServletRequest.getLocalPort();
    }

    @Nonnull
    @Override
    public String getMethod() {
        return httpServletRequest.getMethod();
    }

    @Nonnull
    @Override
    public String getServerName() {
        return httpServletRequest.getServerName();
    }
}
