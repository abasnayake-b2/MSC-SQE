package com.rms.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface PDFGeneratorService {

	public void export(HttpServletResponse response) throws IOException ;
	public void export(HttpServletResponse response,long id) throws IOException ;
}
