package com.sdm.rdfs.Ontology;

import com.sdm.rdfs.Commons.Utils;
import com.sdm.rdfs.Commons.Constants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;

public class ABOX {

    public static void createOntlogyABOX() throws Exception {

        Model m = ModelFactory.createDefaultModel().read(Constants.TBOX_MODEL_PATH);
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, m);

        // Ontology Classes
        OntClass personOntClass = model.getOntClass(Constants.BASE_URI.concat("Person"));
        OntClass authorOntClass = model.getOntClass(Constants.BASE_URI.concat("Author"));
        OntClass chairOntClass = model.getOntClass(Constants.BASE_URI.concat("Chair"));
        OntClass editorOntClass = model.getOntClass(Constants.BASE_URI.concat("Editor"));
        OntClass reviewerOntClass = model.getOntClass(Constants.BASE_URI.concat("Reviewer"));
        OntClass fullPaperOntClass = model.getOntClass(Constants.BASE_URI.concat("Full_paper"));
        OntClass posterOntClass = model.getOntClass(Constants.BASE_URI.concat("Poster"));
        OntClass demoPaperOntClass = model.getOntClass(Constants.BASE_URI.concat("Demo_paper"));
        OntClass shortPaperOntClass = model.getOntClass(Constants.BASE_URI.concat("Short_paper"));
        OntClass journalOntClass = model.getOntClass(Constants.BASE_URI.concat("Journal"));
        OntClass workshopOntClass = model.getOntClass(Constants.BASE_URI.concat("Workshop"));
        OntClass symposiumOntClass = model.getOntClass(Constants.BASE_URI.concat("Symposium"));
        OntClass expertGroupOntClass = model.getOntClass(Constants.BASE_URI.concat("Expert_group"));
        OntClass regularConfOntClass = model.getOntClass(Constants.BASE_URI.concat("Regular_conf"));
        OntClass reviewOntClass = model.getOntClass(Constants.BASE_URI.concat("Review"));
        OntClass publicationOntClass = model.getOntClass(Constants.BASE_URI.concat("Publication"));
        OntClass areaOntClass = model.getOntClass(Constants.BASE_URI.concat("Area"));

        // Properties ontology
        OntProperty writesOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Writes"));
        OntProperty hasAreaOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Has_area"));
        OntProperty relatedToOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Related_to"));
        OntProperty submittedToOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Submitted_to"));
        OntProperty acceptedAsOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Accepted_as"));
        OntProperty belongsToOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Belongs_to"));
        OntProperty headsJournalOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Heads_journal"));
        OntProperty headsConferenceOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Heads_conference"));
        OntProperty assignedByEditorOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Assigned_by_editor"));
        OntProperty assignedByChairOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Assigned_by_chair"));
        OntProperty providesOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Provides"));
        OntProperty approvesOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Approves"));
        OntProperty assignedToOntProperty = model.getOntProperty(Constants.BASE_URI.concat("Assigned_to"));

        //DataType properties ontology aka class attributes
        OntProperty paperTitleOntProperty = model.getOntProperty(Constants.BASE_URI.concat("paper_title"));
        OntProperty paperAbstractOntProperty = model.getOntProperty(Constants.BASE_URI.concat("paper_abstract"));
        OntProperty paperYearOntProperty = model.getOntProperty(Constants.BASE_URI.concat("paper_year"));
        OntProperty areaNameOntProperty = model.getOntProperty(Constants.BASE_URI.concat("area_name"));
        OntProperty venueNameOntProperty = model.getOntProperty(Constants.BASE_URI.concat("venue_name"));
        OntProperty venueCityOntProperty = model.getOntProperty(Constants.BASE_URI.concat("venue_city"));
        OntProperty publicationNameOntProperty = model.getOntProperty(Constants.BASE_URI.concat("publication_name"));
        OntProperty publicationTypeOntProperty = model.getOntProperty(Constants.BASE_URI.concat("publication_type"));
        OntProperty publicationYearOntProperty = model.getOntProperty(Constants.BASE_URI.concat("publication_year"));
        OntProperty personNameOntProperty = model.getOntProperty(Constants.BASE_URI.concat("person_name"));
        OntProperty decisionOntProperty = model.getOntProperty(Constants.BASE_URI.concat("decision"));
        OntProperty reviewTextOntProperty = model.getOntProperty(Constants.BASE_URI.concat("review_text"));

        System.out.println("TBOX Model Loaded Successfully");

        BufferedReader csvReader = new BufferedReader(new FileReader(Constants.DATA_FILE_PATH));
        CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(csvReader);
        Integer rowCount = 0;

        for (CSVRecord record : parser) {
            rowCount += 1;

            String venueStr = URLEncoder.encode(record.get("venue"));
            String publicationStr = URLEncoder.encode(record.get("publication"));
            String keywordStr = URLEncoder.encode(record.get("keyword"));
            String authorStr = Utils.clean_str_buffer(record.get("author"));
            String titleStr = URLEncoder.encode(record.get("title"));
            String yearStr = URLEncoder.encode(record.get("year"));
            String venueTypeStr = Utils.clean_str_buffer(record.get("venue_type"));
            String confTypeStr = Utils.clean_str_buffer(record.get("conf_type"));
            String reviewTextStr = URLEncoder.encode(record.get("review_text"));
            String abstractStr = URLEncoder.encode(record.get("abstract"));
            String venueCityStr = URLEncoder.encode(record.get("venue_city"));
            String headStr = URLEncoder.encode(record.get("head"));
            String reviewer1Str = URLEncoder.encode(record.get("reviewer1"));
            String reviewer2Str = URLEncoder.encode(record.get("reviewer2"));
            String decisionStr = Utils.clean_str_buffer(record.get("decision"));
            String pubTypeStr = URLEncoder.encode(record.get("pub_type"));
            String paperTypeStr = Utils.clean_str_buffer(record.get("paper_type"));

            Individual paperIndividual = null;
            if (paperTypeStr.equals(Utils.clean_str_buffer("Full Paper"))) {
                paperIndividual = fullPaperOntClass.createIndividual(Constants.BASE_URI.concat(titleStr));
            } else if (paperTypeStr.equals(Utils.clean_str_buffer("Short Paper"))) {
                paperIndividual = shortPaperOntClass.createIndividual(Constants.BASE_URI.concat(titleStr));
            } else if (paperTypeStr.equals(Utils.clean_str_buffer("Demo Paper"))) {
                paperIndividual = demoPaperOntClass.createIndividual(Constants.BASE_URI.concat(titleStr));
            } else if (paperTypeStr.equals(Utils.clean_str_buffer("Poster"))) {
                paperIndividual = posterOntClass.createIndividual(Constants.BASE_URI.concat(titleStr));
            }
            paperIndividual.addProperty(paperTitleOntProperty, titleStr);
            paperIndividual.addProperty(paperYearOntProperty, yearStr);
            paperIndividual.addProperty(paperAbstractOntProperty, abstractStr);

            Individual publicationIndividual = null;
            if (decisionStr.equals(Utils.clean_str_buffer("ACCEPTED"))) {
                publicationIndividual = publicationOntClass.createIndividual(Constants.BASE_URI.concat(publicationStr));
                publicationIndividual.addProperty(publicationNameOntProperty, publicationStr);
                publicationIndividual.addProperty(publicationTypeOntProperty, pubTypeStr);
                publicationIndividual.addProperty(publicationYearOntProperty, yearStr);
                paperIndividual.addProperty(acceptedAsOntProperty, publicationIndividual);
            }

            Individual venueIndividual = null;
            if (venueTypeStr.equals(Utils.clean_str_buffer("Conference"))) {
                if (confTypeStr.equals(Utils.clean_str_buffer("Expert Group"))) {
                    venueIndividual = expertGroupOntClass.createIndividual(Constants.BASE_URI.concat(venueStr));
                } else if (confTypeStr.equals(Utils.clean_str_buffer("Workshop"))) {
                    venueIndividual = workshopOntClass.createIndividual(Constants.BASE_URI.concat(venueCityStr));
                } else if (confTypeStr.equals(Utils.clean_str_buffer("Symposium"))) {
                    venueIndividual = symposiumOntClass.createIndividual(Constants.BASE_URI.concat(venueStr));
                } else {
                    venueIndividual = regularConfOntClass.createIndividual(Constants.BASE_URI.concat(venueStr));
                }
            } else {
                venueIndividual = journalOntClass.createIndividual(Constants.BASE_URI.concat(venueStr));
            }
            venueIndividual.addProperty(venueNameOntProperty, venueStr);
            venueIndividual.addProperty(venueCityOntProperty, venueCityStr);

            paperIndividual.addProperty(submittedToOntProperty, venueIndividual);
            if (publicationIndividual != null) {
                publicationIndividual.addProperty(belongsToOntProperty, venueIndividual);
            }

            if (keywordStr.contains("|")) {
                String[] keywords = keywordStr.split("\\|");
                for (String keyword : keywords) {
                    Individual areaIndividual = areaOntClass.createIndividual(Constants.BASE_URI.concat(keyword));
                    areaIndividual.addProperty(areaNameOntProperty, URLEncoder.encode(keyword));
                    venueIndividual.addProperty(relatedToOntProperty, areaIndividual);
                    paperIndividual.addProperty(hasAreaOntProperty, areaIndividual);
                }
            }

            Individual personIndividual = null;
            if (venueTypeStr.equals(Utils.clean_str_buffer("Conference"))) {
                personIndividual = chairOntClass.createIndividual(Constants.BASE_URI.concat(headStr));
                personIndividual.addProperty(personNameOntProperty, headStr);
                personIndividual.addProperty(headsConferenceOntProperty, venueIndividual);
            } else {
                personIndividual = editorOntClass.createIndividual(Constants.BASE_URI.concat(headStr));
                personIndividual.addProperty(personNameOntProperty, headStr);
                personIndividual.addProperty(headsJournalOntProperty, venueIndividual);
            }
            if (authorStr.contains("|")) {
                String[] authors = authorStr.split("\\|");
                for (String author : authors) {
                    System.out.println(author.toString());
                    Individual authorIndidual = authorOntClass.createIndividual(Constants.BASE_URI.concat(URLEncoder.encode(author)));
                    authorIndidual.addProperty(personNameOntProperty, URLEncoder.encode(author));
                    authorIndidual.addProperty(writesOntProperty, paperIndividual);
                }
            }
            else{
                Individual authorIndidual = authorOntClass.createIndividual(Constants.BASE_URI.concat(URLEncoder.encode(authorStr)));
                authorIndidual.addProperty(personNameOntProperty, URLEncoder.encode(authorStr));
                authorIndidual.addProperty(writesOntProperty, paperIndividual);
            }

            Individual reviewIndividual = reviewOntClass.createIndividual(Constants.BASE_URI.concat(reviewTextStr));
            reviewIndividual.addProperty(decisionOntProperty, URLEncoder.encode(decisionStr));
            reviewIndividual.addProperty(reviewTextOntProperty, reviewTextStr);
            reviewIndividual.addProperty(approvesOntProperty, paperIndividual);

            if (venueTypeStr.equals(Utils.clean_str_buffer("Conference"))) {
                Individual reviewer1Individual = reviewerOntClass.createIndividual(Constants.BASE_URI.concat(reviewer1Str));
                reviewer1Individual.addProperty(personNameOntProperty, reviewer1Str);
                reviewer1Individual.addProperty(assignedToOntProperty, paperIndividual);
                reviewer1Individual.addProperty(providesOntProperty, reviewIndividual);
                personIndividual.addProperty(assignedByChairOntProperty, reviewer1Individual);
            } else {
                Individual reviewer2Individual = reviewerOntClass.createIndividual(Constants.BASE_URI.concat(reviewer2Str));
                reviewer2Individual.addProperty(personNameOntProperty, reviewer1Str);
                reviewer2Individual.addProperty(assignedToOntProperty, paperIndividual);
                reviewer2Individual.addProperty(providesOntProperty, reviewIndividual);
                personIndividual.addProperty(assignedByEditorOntProperty, reviewer2Individual);
            }

        }
        System.out.println("ABox Model Saved Successfully");
        System.out.println("Validating ABOX model ..........");

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner.bindSchema(model);
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);
        if (infModel.validate().isValid()) {
            FileOutputStream writerStream = new FileOutputStream(Constants.ABOX_MODEL_PATH);
            model.write(writerStream, "ttl");
            writerStream.close();
        } else {
            throw new Exception("Invalid Ontology");
        }
        System.out.println("ABOX model validated");

    }
}