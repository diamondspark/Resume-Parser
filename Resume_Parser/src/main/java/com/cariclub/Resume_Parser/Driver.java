package com.cariclub.Resume_Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import gate.util.GateException;

public class Driver {

	public Driver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, SAXException, TikaException, GateException {
    /*Parse the Resume */
		FileInputStream inputstream = new FileInputStream(new File("My Resume.pdf"));
		PdfDocument pdfDoc = new PdfDocument(inputstream);
		File file = pdfDoc.parser(pdfDoc);

		/*Load GATE Framework and AANIE Pipeline*/
		LanguageProcess lp = new LanguageProcess();
		lp.loadGateAndAnnie();
		lp.processDocument(file);
	}

}
