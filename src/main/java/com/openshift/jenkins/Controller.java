package com.openshift.jenkins;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/check")
public class Controller {

    private CheckService checkService;

    public Controller(CheckService checkService) {
        this.checkService = checkService;
    }


    @GetMapping
    public String check() {
        return checkService.check();
    }


    @GetMapping(value = "/openshift", produces = MediaType.APPLICATION_JSON_VALUE)
    public Openshift json() {
        return new Openshift("Apigee-Openshift-Jenkins", "Jenkins does Build, Test, Sonar Analysis, Nexus & Deploys to Openshift & Apigee with Policies");
    }


    public static class Openshift {

        private String title;
        private String value;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Openshift(String title, String value) {
            this.title = title;
            this.value = value;
        }

        public Openshift() {
        }
    }
}
