package com.rms.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rms.service.PDFGeneratorService;

@RestController
@RequestMapping("/pdf")
@CrossOrigin
public class PDFController {
	@Autowired
	private PDFGeneratorService pdfService;

	public PDFController(PDFGeneratorService pdfService) {
		super();
		this.pdfService = pdfService;
	}
	
	@GetMapping("/generate")
	public void generatePDF(HttpServletResponse response) throws IOException {
		
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey= "content-Disposition";
		String headerValue = "attachment; filename=pdf_"+currentDateTime+".pdf";
		
		response.setHeader(headerKey, headerValue);
		this.pdfService.export(response);
		
		
	}
	
	@GetMapping("/generate/{id}")
	public void generateResumePDFByID(HttpServletResponse response,@PathVariable Long id) throws IOException {
		
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey= "content-Disposition";
		String headerValue = "attachment; filename=pdf_"+currentDateTime+".pdf";
		
		response.setHeader(headerKey, headerValue);
		this.pdfService.export(response,id);
		
		
	}
}
