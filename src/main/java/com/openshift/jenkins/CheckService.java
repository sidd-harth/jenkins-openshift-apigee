package com.openshift.jenkins;

import org.springframework.stereotype.Component;

@Component
public class CheckService {


    public String check() {
        return "Yeah, This service is deployed & it is running on both Openshift & Apigee!! Try Hitting /check/openshift endpoint";
    }
    
    /* uncomment this to get zero code smells in Sonar Code Analysis 
     
    String response = "Yeah, This service is deployed & it is running!!!!!!!!!!!!!!";
    public String check() {
        return response;
    }
     
    */
    
    
}
