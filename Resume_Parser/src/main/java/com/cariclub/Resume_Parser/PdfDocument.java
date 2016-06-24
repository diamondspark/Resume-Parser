package com.cariclub.Resume_Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import org.apache.tika.parser.pdf.PDFParser;

public class PdfDocument extends Doc {

	public PdfDocument( FileInputStream inputstream) {
		super(inputstream);
	}
	

	@Override
	public File parser(Doc doc) throws IOException, SAXException, TikaException  {
		File file = new File("ParsedResume.txt");
		PDFParser pdfParser = new PDFParser();
		pdfParser.parse(doc.getFileInput(),doc.getBodyContentHandler(),doc.getMetadata(),doc.getParseContext());
		System.out.println("Contents of the PDF :" + doc.getBodyContentHandler().toString());
		return null;
	
	}
}
