package org.ddmed.mwlsender.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.util.List;

@Configuration
@PropertySource("classpath:conf/conf.properties")
@ConfigurationProperties(prefix = "nodes")
public class Nodes {

    private Integer count;
    private List<String> host;
    private List<Integer> port;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getHost() {
        return host;
    }

    public void setHost(List<String> host) {
        this.host = host;
    }

    public List<Integer> getPort() {
        return port;
    }

    public void setPort(List<Integer> port) {
        this.port = port;
    }
}
