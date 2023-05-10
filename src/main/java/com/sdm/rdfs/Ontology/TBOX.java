package com.sdm.rdfs.Ontology;

import com.sdm.rdfs.Commons.Constants;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

public class TBOX {

    public static void createOntlogyTBOX(){

        OntModel model = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM_RDFS_INF );

        OntClass person = model.createClass(Constants.BASE_URI.concat("Person"));
        OntClass author = model.createClass(Constants.BASE_URI.concat("Author"));
        OntClass chair = model.createClass(Constants.BASE_URI.concat("Chair"));
        OntClass editor = model.createClass(Constants.BASE_URI.concat("Editor"));
        OntClass reviewer = model.createClass(Constants.BASE_URI.concat("Reviewer"));

        person.addSubClass( author );
        person.addSubClass( chair );
        person.addSubClass( editor );
        person.addSubClass( reviewer );

    }
}