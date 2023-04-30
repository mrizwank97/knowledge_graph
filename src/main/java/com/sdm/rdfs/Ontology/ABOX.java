package com.sdm.rdfs.Ontology;

import com.sdm.rdfs.Commons.Constants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ABOX {

    public static void buildAndSaveAbox() throws IOException {

        Model m = ModelFactory.createDefaultModel().read(Constants.TBOX_MODEL_PATH);
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, m);

        OntClass person = model.getOntClass(Constants.BASE_URI.concat("Person"));
        OntClass author = model.getOntClass(Constants.BASE_URI.concat("Author"));
        OntClass reviewer = model.getOntClass(Constants.BASE_URI.concat("Reviewer"));
        OntClass head = model.getOntClass(Constants.BASE_URI.concat("Head"));
        OntClass chair = model.getOntClass(Constants.BASE_URI.concat("Chair"));
        OntClass Editor = model.getOntClass(Constants.BASE_URI.concat("Editor"));
        OntClass venue = model.getOntClass(Constants.BASE_URI.concat("Venue"));
        OntClass journal = model.getOntClass(Constants.BASE_URI.concat("Journal"));
        OntClass conference = model.getOntClass(Constants.BASE_URI.concat("Conference"));
        OntClass workshop = model.getOntClass(Constants.BASE_URI.concat("Workshop"));
        OntClass expertGroup = model.getOntClass(Constants.BASE_URI.concat("Expert_group"));
        OntClass regularConf = model.getOntClass(Constants.BASE_URI.concat("Regular_conf"));
        OntClass symphosium = model.getOntClass(Constants.BASE_URI.concat("Symphosium"));
        OntClass paper = model.getOntClass(Constants.BASE_URI.concat("Paper"));
        OntClass fullPaper = model.getOntClass(Constants.BASE_URI.concat("Full_paper"));
        OntClass shortPaper = model.getOntClass(Constants.BASE_URI.concat("Short_paper"));
        OntClass demoPaper = model.getOntClass(Constants.BASE_URI.concat("Demo_paper"));
        OntClass poster = model.getOntClass(Constants.BASE_URI.concat("Poster"));
        OntClass year = model.getOntClass(Constants.BASE_URI.concat("Year"));
        OntClass area = model.getOntClass(Constants.BASE_URI.concat("Area"));
        OntClass review = model.getOntClass(Constants.BASE_URI.concat("Review"));
        OntClass decision = model.getOntClass(Constants.BASE_URI.concat("Decision"));
        OntClass decisionText = model.getOntClass(Constants.BASE_URI.concat("Decision_Text"));
        OntClass publication = model.getOntClass(Constants.BASE_URI.concat("Publication"));
        OntClass conferenceProceeding = model.getOntClass(Constants.BASE_URI.concat("Conference_proceeding"));
        OntClass journalVolume = model.getOntClass(Constants.BASE_URI.concat("Journal_volume"));

        OntProperty acceptedAs = model.getOntProperty(Constants.BASE_URI.concat("Accepted_as"));
        OntProperty acceptedOrNot = model.getOntProperty(Constants.BASE_URI.concat("Accepted_or_not"));
        OntProperty assignedByHead = model.getOntProperty(Constants.BASE_URI.concat("Assigned_by_head"));
        OntProperty assignedTo = model.getOntProperty(Constants.BASE_URI.concat("Assigned_to"));
        OntProperty hasArea = model.getOntProperty(Constants.BASE_URI.concat("Has_area"));
        OntProperty headsConference = model.getOntProperty(Constants.BASE_URI.concat("Heads_conference"));
        OntProperty headsJournal = model.getOntProperty(Constants.BASE_URI.concat("Heads_journal"));
        OntProperty provide = model.getOntProperty(Constants.BASE_URI.concat("Provide"));
        OntProperty providesText = model.getOntProperty(Constants.BASE_URI.concat("Provides_text"));
        OntProperty publishedIn = model.getOntProperty(Constants.BASE_URI.concat("Published_in"));
        OntProperty submit = model.getOntProperty(Constants.BASE_URI.concat("Submit"));
        OntProperty submittedTo = model.getOntProperty(Constants.BASE_URI.concat("Submitted_to"));
        OntProperty writtenIn = model.getOntProperty(Constants.BASE_URI.concat("Written_in"));

        BufferedReader csvReader = new BufferedReader(new FileReader(Constants.DATA_FILE_PATH));
        CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(csvReader);
        Integer rowCount = 0;

        for(CSVRecord record : parser){

            rowCount += 1;

        }

    }

}