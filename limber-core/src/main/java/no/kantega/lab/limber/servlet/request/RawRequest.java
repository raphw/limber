package no.kantega.lab.limber.servlet.request;

import java.util.Map;
import java.util.Set;

public class RawRequest {

    private final String sessionId;
    private final String requestURI;
    private final Map<String, String> queryArguments;
    private final Map<String, String> headArguments;
    private final String scheme;
    private final String host;
    private final int port;
    private final String method;
    private final String serverName;

    public RawRequest(String sessionId,
                      String requestURI,
                      Map<String, String> queryArguments,
                      Map<String, String> headArguments,
                      String scheme,
                      String host,
                      int port,
                      String method,
                      String serverName) {

        this.sessionId = sessionId;
        this.requestURI = requestURI;
        this.queryArguments = queryArguments;
        this.headArguments = headArguments;
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.method = method;
        this.serverName = serverName;

    }

    public String getSessionId() {
        return sessionId;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getQueryArgument(String key) {
        return queryArguments.get(key);
    }

    public Set<String> getQueryArgumentKeys() {
        return queryArguments.keySet();
    }

    public String getHeadArgument(String key) {
        return headArguments.get(key);
    }

    public Set<String> getHeadArgumentKeys() {
        return headArguments.keySet();
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getMethod() {
        return method;
    }

    public String getServerName() {
        return serverName;
    }
}
