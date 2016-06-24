package com.cariclub.Resume_Parser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.LanguageAnalyser;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.util.GateException;
import gate.util.Out;
import gate.util.persistence.PersistenceManager;
import static gate.Utils.*;

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
	// load ANNIE plugin - you must do this before you can create tokeniser
	// or JAPE transducer resources.
	Gate.getCreoleRegister().registerDirectories(
	new File(Gate.getPluginsHome(), "ANNIE").toURI().toURL());

	 // Build the pipeline
	SerialAnalyserController pipeline = (SerialAnalyserController)Factory.createResource("gate.creole.SerialAnalyserController");
	LanguageAnalyser tokeniser = (LanguageAnalyser)Factory.createResource("gate.creole.tokeniser.DefaultTokeniser");
	//LanguageAnalyser jape = (LanguageAnalyser)Factory.createResource("gate.creole.Transducer", gate.Utils.featureMap("grammarURL", new     
	//File("SportsCategory.jape").toURI().toURL(),"encoding", "UTF-8")); // ensure this matches the file
	pipeline.add(tokeniser);
	//pipeline.add(jape);
		
		
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
	 pipeline.setCorpus(corpus);
	 setCorpus(corpus);
	 annieExecute();
	 pipeline.execute();
	    
	 System.out.println("Found annotations of the following types: " +
	 doc.getAnnotations().getAllTypes());
	 
	 // for the document, get an XML document with the
	 // person and location names added   
	 Iterator iter = corpus.iterator();
	 if(iter.hasNext()){
	    	Document doc1 = (Document) iter.next();
	    	AnnotationSet defaultAnnotSet = doc1.getAnnotations();
	    	AnnotationSet curAnnSet;
	    	Annotation currAnnot;		
	    
	    	//Get Name
				curAnnSet = defaultAnnotSet.get("Person");
				if(curAnnSet.iterator().hasNext() ){
					currAnnot = (Annotation) curAnnSet.iterator().next();
					System.out.println("Name " + stringFor(doc1, currAnnot));
					}
	    	
	    	// Get Email
				curAnnSet = defaultAnnotSet.get("Address");
				if(curAnnSet.iterator().hasNext() && curAnnSet.iterator().next().getFeatures().get("kind").equals("email")){
					currAnnot = (Annotation) curAnnSet.iterator().next();
					System.out.println("Email " + stringFor(doc1, currAnnot));
					}
				
				//Location
				curAnnSet = defaultAnnotSet.get("Location");
			//getAllInAnnSet(curAnnSet,doc1);
				if(curAnnSet.iterator().hasNext() ){
					currAnnot = (Annotation) curAnnSet.iterator().next();
					System.out.println("Location " + stringFor(doc1, currAnnot));
					}
				
				curAnnSet = defaultAnnotSet.get("ProfileSection");
				//getAllInAnnSet(curAnnSet,doc1);
					if(curAnnSet.iterator().hasNext() ){
						currAnnot = (Annotation) curAnnSet.iterator().next();
						System.out.println("Location " + stringFor(doc1, currAnnot));
						}
				
				
			}
	  }
  
	
	private void getAllInAnnSet(AnnotationSet as, Document doc){
		for (Annotation a : as){
			System.out.println(a );
			System.out.println(stringFor(doc,a));
		}
		
	}
	
	public static void main(String args[]) throws GateException{
		//loadGateAndAnnie();
		
	}

}
