package com.cariclub.Resume_Parser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.creole.ResourceInstantiationException;
import gate.util.GateException;
import gate.util.Out;
import gate.util.persistence.PersistenceManager;

public class LanguageProcess {

	private static CorpusController annieController;
	public LanguageProcess() {
		// TODO Auto-generated constructor stub
	}
	
	private static void initAnnie() throws GateException, IOException{
		Out.prln("Initialising ANNIE...");
	  // load the ANNIE application from the saved state in plugins/ANNIE
		File pluginsHome = Gate.getPluginsHome();
		File anniePlugin = new File(pluginsHome, "ANNIE");
		File annieGapp = new File(anniePlugin, "ANNIE_with_defaults.gapp");
		annieController = (CorpusController) PersistenceManager.loadObjectFromFile(annieGapp);
	  Out.prln("...ANNIE loaded");	
	}
	
	/* Set the corpus for ANNIE controller with which you want it to run*/
	private void setCorpus(Corpus corpus){
		annieController.setCorpus(corpus);
	}
	
	/*Run ANNIE instance*/
	private void annieExecute() throws GateException{
		Out.prln("Running ANNIE...");
		annieController.execute();
		Out.prln("...ANNIE complete");
	}
	
	public void loadGateAndAnnie() throws GateException, IOException{
		Out.prln("Initialising basic system...");
		Gate.init();
		Out.prln("...basic system initialised");
	  initAnnie();
	}
	
	public void processDocument(File file) throws MalformedURLException, GateException{
		// create a GATE corpus and add a document for each command-line argument
	    Corpus corpus = Factory.newCorpus("StandAloneAnnie corpus");
	    URL u = file.toURI().toURL();
	    FeatureMap params = Factory.newFeatureMap();
	    params.put("sourceUrl", u);
	    params.put("preserveOriginalContent", new Boolean(true));
	    params.put("collectRepositioningInfo", new Boolean(true));
	    Out.prln("Creating doc for " + u);
	    Document doc = (Document) Factory.createResource("gate.corpora.DocumentImpl", params);
	    corpus.add(doc);
	    
	 // tell the pipeline about the corpus and run it
	    setCorpus(corpus);
	    annieExecute();
	   
	 // for the document, get an XML document with the
	 // person and location names added   
	    Iterator iter = corpus.iterator();
	    String startTagPart_1 = "<span GateID=\"";
	    String startTagPart_2 = "\" title=\"";
	    String startTagPart_3 = "\" style=\"background:Red;\">";
	    String endTag = "</span>";
	    if(iter.hasNext()){
	    	Document doc1 = (Document) iter.next();
	    	AnnotationSet defaultAnnotSet = doc1.getAnnotations();
	    	
	    	
	    }
  }
	
	public static void main(String args[]) throws GateException{
		//loadGateAndAnnie();
		
	}

}
