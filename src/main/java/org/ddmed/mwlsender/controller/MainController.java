package org.ddmed.mwlsender.controller;

import org.ddmed.mwlsender.model.Modalities;
import org.ddmed.mwlsender.model.Nodes;
import org.ddmed.mwlsender.model.Observation;
import org.ddmed.mwlsender.model.Patient;
import org.ddmed.mwlsender.service.SendHL7Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

@Controller
public class MainController {

    @Autowired
    SendHL7Message sendHL7Service;

    @Autowired
    private Nodes nodes;

    @Autowired
    private Modalities modalities;


    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/register")
    public String register(){
        return "register";
    }

    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String selectOptionExample1Page(Model model) {

        Map<String, String> mapModalities = combineListsIntoOrderedMap(modalities.getTypeCode(), modalities.getTypeDescription());
        model.addAttribute("modalities", mapModalities);
        return "register";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePatient(final Patient patient, final Observation observation, BindingResult bindingResult, Model model) {

        sendHL7Service.send(patient, observation);
        /*System.out.println("================PATIENT=======================");
        System.out.println(patient.getLastName());
        System.out.println(patient.getFirstName());
        System.out.println(patient.getMiddleName());
        System.out.println(patient.getDateOfBirth());
        System.out.println(patient.getGender());


        System.out.println("================Observation====================");
        System.out.println(observation.getObservationDate());
        System.out.println(observation.getObservationTypeCode());
        System.out.println(observation.getProcedureCode());*/

        return "redirect:/register";
    }

    Map<String,String> combineListsIntoOrderedMap (List<String> keys, List<String> values) {
        if (keys.size() != values.size())
            throw new IllegalArgumentException ("Cannot combine lists with dissimilar sizes");
        Map<String,String> map = new LinkedHashMap<>();
        for (int i=0; i<keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }

}
