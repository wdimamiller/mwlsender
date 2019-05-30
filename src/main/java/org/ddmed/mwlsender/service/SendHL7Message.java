package org.ddmed.mwlsender.service;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
//ca.uhn.hl7v2.model.v23.group.ORM_O01_OBSERVATION
import ca.uhn.hl7v2.parser.Parser;
import org.ddmed.mwlsender.model.Nodes;
import org.ddmed.mwlsender.model.Observation;
import org.ddmed.mwlsender.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.Stream;

@Component
public class SendHL7Message {

        @Autowired
        Nodes nodes;


        public void send(Patient patient, Observation observation){
            String templateMessage = getHL7template();

            //input pattern
            System.out.println(templateMessage);
            System.out.println("=>");

            //replace /n to /r
            templateMessage =  templateMessage.replace("\n", "\r" );

            //replace MSG_ID
            String MSG_ID = "MSG" + System.currentTimeMillis();
            templateMessage =  templateMessage.replace("(MSG_ID)", MSG_ID );

            //replace observation date
            String DATE_FORMATTER= "yyyyMMddHHmm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
            String observationDate = observation.getObservationDate().format(formatter);
            templateMessage = templateMessage.replace("(OBSERVATION_DATE)", observationDate);

            //replace patient first name
            templateMessage = templateMessage.replace("(PATIENT_FIRST_NAME)", patient.getFirstName());

            //replace patient last name
            templateMessage = templateMessage.replace("(PATIENT_LAST_NAME)", patient.getLastName());

            //replace patient id
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String dateOfBirth = format.format(patient.getDateOfBirth());

            String PATIENT_ID = patient.getLastName().substring(0,3)
                    + patient.getFirstName().substring(0,1)
                    + patient.getMiddleName().substring(0,1)
                    + dateOfBirth;

            templateMessage = templateMessage.replace("(PATIENT_ID)", PATIENT_ID);

            //replace patient date of birth
            templateMessage = templateMessage.replace("(PATIENT_DATE_OF_BIRTH)", dateOfBirth);

            //replace patient gender
            templateMessage = templateMessage.replace("(PATIENT_GENDER)", patient.getGender());

            //replace modality code
            templateMessage = templateMessage.replaceAll("(MODALITY)", observation.getObservationTypeCode());

            //replace procedure code
            templateMessage = templateMessage.replace("(PROCEDURE)", observation.getProcedureCode());

            //replace date sending to none
            templateMessage = templateMessage.replace("(DATE_SENDING)", "");

            //replace some number to none
            templateMessage = templateMessage.replace("(SOME_NUMBER)", "");

            System.out.println(templateMessage);

            send(templateMessage);

        }

        public String getHL7template(){
            String messageTemplate = null;
            try {
                File file = new ClassPathResource("conf/template.hl7").getFile();

                messageTemplate = readLineByLineJava8(file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return messageTemplate;

        }

    private static String readLineByLineJava8(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }


    public void send(String message){

            String host = null;
            Integer port = null;
            Boolean useTls = false;


            HapiContext context = new DefaultHapiContext();
            Parser p = context.getPipeParser();
            Message adt;

            Integer nodesCount = nodes.getCount();
            for(int i = 0; i < nodesCount; i++ ) {

                host = nodes.getHost().get(i);
                port = nodes.getPort().get(i);

                try {
                    adt = p.parse(message);
                    System.out.println(adt.printStructure());

                    Connection connection;
                    try {

                        connection = context.newClient(host, port, useTls);

                        // The initiator is used to transmit unsolicited messages
                        Initiator initiator = connection.getInitiator();
                        Message response = null;
                        try {
                            response = initiator.sendAndReceive(adt);
                        } catch (LLPException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String responseString = p.encode(response);
                        System.out.println("Received response:\n" + responseString);

                        connection.close();
                    } catch (HL7Exception e) {
                        e.printStackTrace();
                    }

                } catch (HL7Exception e) {
                    e.printStackTrace();
                }
            }



        }

}
