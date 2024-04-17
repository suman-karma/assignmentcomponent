package io.mhe.assignmentcomponent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class IntegrationRestService implements IIntegrationRestService {
    @Autowired
    RestTemplate restTemplate;



}
