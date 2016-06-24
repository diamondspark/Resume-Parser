package com.cariclub.Resume_Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
		PDFParser pdfParser = new PDFParser();
		pdfParser.parse(doc.getFileInput(),doc.getBodyContentHandler(),doc.getMetadata(),doc.getParseContext());
		FileWriter fw = new FileWriter("ParsedResume.txt");
		fw.write(doc.getBodyContentHandler().toString());
		fw.flush();
		fw.close();
	
		File file = new File("ParsedResume.txt");
	//	File file = new File("ex2.txt");
		return file;
	
	}
}
