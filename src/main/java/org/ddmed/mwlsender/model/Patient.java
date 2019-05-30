package org.ddmed.mwlsender.model;


import org.ddmed.mwlsender.utils.TranslitUkEn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Patient {

    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String middleName, String gender, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth(){
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName =  TranslitUkEn.translit(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = TranslitUkEn.translit(lastName);
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = TranslitUkEn.translit(middleName);
    }
}
