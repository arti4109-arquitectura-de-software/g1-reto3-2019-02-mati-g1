package com.arquitectura.eureka.provider.csvservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csv-service")
public class CsvServiceController {

    //@RequestMapping(value = "/receiveCsvContent/{content}", method = RequestMethod.POST)
    @GetMapping("/receiveCsvContent/{content}")
    public String receiveCsvContent(String content){
        return "El contenido: " + content + " Fue recibido satisfactoriamente";
    }
}
