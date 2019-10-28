package com.arquitectura.eureka.provider.clientagent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class AgentController {

    @Autowired
    private RestTemplate template;

    //@GetMapping(value = "/sendCsvContent/{content}")
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/sendCsvContent/{content}", method = RequestMethod.GET, produces="application/json")
    public String invokeCsvService(@PathVariable String content){
        //De la manera tradicional se envía la URL y el puerto del host donde se encuentra el servicio
        //String url="http://localhost/csv-service/receiveCsvContent" +  content;


        //Con Service Registry y Service Discovery, es posible invocar los servicios con el nmbre de la aplicación registrada
        String url = "http://CSV-SERVICE/csv-service/receiveCsvContent/" +  content;
        return template.getForObject(url, String.class);
    }
}
