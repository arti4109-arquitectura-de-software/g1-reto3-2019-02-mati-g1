package com.xyz;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;


import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class DetectorTiposApplication extends SpringBootServletInitializer {
    
	public static void main(String[] args) {
		SpringApplication.run(DetectorTiposApplication.class, args);
	}
	
	//Carga de archivo
    @RequestMapping(value = "/cargaArchivo", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile archivo, Model model) throws IOException {
    	String contenido = new String(archivo.getBytes());
    	DetectorTiposApplication dt = new DetectorTiposApplication();
		try {
			String[] parrafos = dt.detectorParrafos(contenido);
			String[] nombres = dt.detectorNombres(contenido);
			model.addAttribute("parrafos", parrafos);
			model.addAttribute("nombres", nombres);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "index";
    }

	//Detector de párrafos
	public String[] detectorParrafos(String entrada) throws Exception {
	    InputStream is = getClass().getResourceAsStream("/models/en-sent.bin");
	    SentenceModel model = new SentenceModel(is);
	    SentenceDetectorME sdetector = new SentenceDetectorME(model);
	    String sentences[] = sdetector.sentDetect(entrada);
	    return sentences;
	}
	
	
	//Detector de nombres
	public String[] detectorNombres(String entrada)  throws Exception {
	 
	    SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
	    String[] tokens = tokenizer.tokenize(entrada);
	 
	    InputStream inputStreamNameFinder = getClass().getResourceAsStream("/models/en-ner-person.bin");
	    TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
	    NameFinderME nameFinderME = new NameFinderME(model);
	    Span[] spans = nameFinderME.find(tokens);
	    String[] names = Span.spansToStrings(spans, tokens);
		return names;
	}
	
	
	
	
	


	
	
	
}
