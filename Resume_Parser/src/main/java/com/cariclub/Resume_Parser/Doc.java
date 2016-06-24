package com.cariclub.Resume_Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public abstract class Doc {
	private Metadata metadata;
	private FileInputStream inputstream;
	private ParseContext pcontext; 
	private BodyContentHandler handler;
	
	public Doc (FileInputStream fileName){
		metadata = new Metadata();
		inputstream = fileName;
		pcontext = new ParseContext();
		handler = new BodyContentHandler();
		
	}
	
	public Metadata getMetadata(){
		return metadata;
	}
	
	public ParseContext getParseContext(){
		return pcontext;
	}
	
	public FileInputStream getFileInput(){
		return inputstream;
	}
	
	public BodyContentHandler getBodyContentHandler(){
		return handler;
	}
	
	public abstract File parser(Doc doc) throws IOException, SAXException, TikaException;
}
