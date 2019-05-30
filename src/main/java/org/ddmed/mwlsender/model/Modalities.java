package org.ddmed.mwlsender.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:conf/conf.properties")
@ConfigurationProperties(prefix = "modalities" )
public class Modalities {

    private List<String> typeCode;
    private List<String> typeDescription;
    private List<String> procedureCode;
    private List<String> procedureDescription;

    public Modalities() {

    }

    public List<String> getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(List<String> typeCode) {
        this.typeCode = typeCode;
    }

    public List<String> getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(List<String> typeDescription) {
        this.typeDescription = typeDescription;
    }

    public List<String> getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(List<String> procedureCode) {
        this.procedureCode = procedureCode;
    }

    public List<String> getProcedureDescription() {
        return procedureDescription;
    }

    public void setProcedureDescription(List<String> procedureDescription) {
        this.procedureDescription = procedureDescription;
    }
}
