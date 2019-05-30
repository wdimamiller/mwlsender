package org.ddmed.mwlsender;

import org.ddmed.mwlsender.model.Nodes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Nodes.class)
public class MWLSender {

    public static void main(String[] args) {
        SpringApplication.run(MWLSender.class, args);
    }

}
