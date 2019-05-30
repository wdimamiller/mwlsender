package org.ddmed.mwlsender.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


public class Observation {

    private String observationTypeCode;
    private String procedureCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime observationDate;

    public Observation() {
    }

    public Observation(String observationTypeCode, String procedureCode, LocalDateTime observationDate) {
        this.observationTypeCode = observationTypeCode;
        this.procedureCode = procedureCode;
        this.observationDate = observationDate;
    }

    public String getObservationTypeCode() {
        return observationTypeCode;
    }

    public void setObservationTypeCode(String observationTypeCode) {
        this.observationTypeCode = observationTypeCode;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public LocalDateTime getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(LocalDateTime observationDate) {
        this.observationDate = observationDate;
    }
}
