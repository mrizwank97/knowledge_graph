package com.sdm.rdfs.Ontology;

import com.sdm.rdfs.Commons.Utils;
import com.sdm.rdfs.Commons.Constants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ABOX {

    public static void buildAndSaveABOX() throws IOException {

        Model m = ModelFactory.createDefaultModel().read(Constants.TBOX_MODEL_PATH);
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF, m);

        OntClass reviewerOntClass = model.getOntClass(Constants.BASE_URI.concat("Reviewer"));
        OntClass reviewOntClass = model.getOntClass(Constants.BASE_URI.concat("Review"));
        OntClass decisionOntClass = model.getOntClass(Constants.BASE_URI.concat("Decision"));
        OntClass decisionTextOntClass = model.getOntClass(Constants.BASE_URI.concat("Decision_Text"));

        OntProperty acceptedAsOntProp = model.getOntProperty(Constants.BASE_URI.concat("Accepted_as"));
        OntProperty acceptedOrNotOntProp = model.getOntProperty(Constants.BASE_URI.concat("Accepted_or_not"));
        OntProperty assignedByHeadOntProp = model.getOntProperty(Constants.BASE_URI.concat("Assigned_by_head"));
        OntProperty assignedToOntProp = model.getOntProperty(Constants.BASE_URI.concat("Assigned_to"));
        OntProperty hasAreaOntProp = model.getOntProperty(Constants.BASE_URI.concat("Has_area"));
        OntProperty headsConfOntProp = model.getOntProperty(Constants.BASE_URI.concat("Heads_conference"));
        OntProperty headsJourOntProp = model.getOntProperty(Constants.BASE_URI.concat("Heads_journal"));
        OntProperty provideOntProp = model.getOntProperty(Constants.BASE_URI.concat("Provide"));
        OntProperty providesTextOntProp = model.getOntProperty(Constants.BASE_URI.concat("Provides_text"));
        OntProperty publishedInOntProp = model.getOntProperty(Constants.BASE_URI.concat("Published_in"));
        OntProperty submitOntProp = model.getOntProperty(Constants.BASE_URI.concat("Submit"));
        OntProperty submittedToOntProp = model.getOntProperty(Constants.BASE_URI.concat("Submitted_to"));
        OntProperty writtenInOntProp = model.getOntProperty(Constants.BASE_URI.concat("Written_in"));

        BufferedReader csvReader = new BufferedReader(new FileReader(Constants.DATA_FILE_PATH));
        CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(csvReader);
        Integer rowCount = 0;

        for(CSVRecord record : parser){
            rowCount += 1;

            String venueStr = Utils.clean_str_buffer(record.get("venue"));
            String publicationStr = Utils.clean_str_buffer(record.get("publication"));
            String keywordStr = Utils.clean_str_buffer(record.get("keyword"));
            String authorStr = Utils.clean_str_buffer(record.get("author"));
            String titleStr = Utils.clean_str_buffer(record.get("title"));
            String yearStr = Utils.clean_str_buffer(record.get("year"));
            String venueTypeStr = Utils.clean_str_buffer(record.get("venue_type"));
            String confTypeStr = Utils.clean_str_buffer(record.get("conf_type"));
            String reviewTextStr = Utils.clean_str_buffer(record.get("review_text"));
            String headStr = Utils.clean_str_buffer(record.get("head"));
            String reviewer1Str = Utils.clean_str_buffer(record.get("reviewer1"));
            String reviewer2Str = Utils.clean_str_buffer(record.get("reviewer2"));
            String decisionStr = Utils.clean_str_buffer(record.get("decision"));
            String paperTypeStr = Utils.clean_str_buffer(record.get("paper_type"));

            Individual paperIndividual = createPaperIndividual(model, paperTypeStr, titleStr);
            Individual yearIndividual = createYearIndividual(model, yearStr);
            paperIndividual.addProperty(writtenInOntProp, yearIndividual);

            if (decisionStr.equals("ACCEPTED")){
                Individual publicationIndividual = createPublicationIndividual(model, venueTypeStr, publicationStr);
                publicationIndividual.addProperty(publishedInOntProp, yearIndividual);
                paperIndividual.addProperty(acceptedAsOntProp, publicationIndividual);
            }

            Individual venueIndividual = createVenueIndividual(model, venueTypeStr, venueStr, confTypeStr);
            paperIndividual.addProperty(submittedToOntProp,venueIndividual);
            createAreaIndividual(model, hasAreaOntProp, venueIndividual, keywordStr);
            createAuthorIndividual(model, submitOntProp, paperIndividual, authorStr);

            Individual decisionIndividual = decisionOntClass.createIndividual(Constants.BASE_URI.concat(decisionStr));
            Individual decisionTextIndividual = decisionTextOntClass.createIndividual(Constants.BASE_URI.concat(reviewTextStr));

            Individual reviewIndividual = reviewOntClass.createIndividual();
            reviewIndividual
                    .addProperty(acceptedOrNotOntProp, decisionIndividual)
                    .addProperty(providesTextOntProp, decisionTextIndividual);

            Individual reviewer1Individual = reviewerOntClass.createIndividual(Constants.BASE_URI.concat(reviewer1Str));
            reviewer1Individual
                    .addProperty( assignedToOntProp, paperIndividual)
                    .addProperty( provideOntProp, reviewIndividual);

            Individual reviewer2Individual = reviewerOntClass.createIndividual(Constants.BASE_URI.concat(reviewer2Str) );
            reviewer2Individual
                    .addProperty( assignedToOntProp, paperIndividual)
                    .addProperty( provideOntProp, reviewIndividual);

            Individual headIndividual = createHeadIndividual(model, venueTypeStr, headStr);
            if(venueTypeStr.equals("Conference")){
                headIndividual
                        .addProperty(headsConfOntProp, venueIndividual)
                        .addProperty(assignedByHeadOntProp, reviewer1Individual)
                        .addProperty(assignedByHeadOntProp, reviewer2Individual);
            }
            else {
                headIndividual
                        .addProperty(headsJourOntProp, venueIndividual)
                        .addProperty(assignedByHeadOntProp, reviewer1Individual)
                        .addProperty(assignedByHeadOntProp, reviewer2Individual);
            }
        }
        FileOutputStream writerStream = new FileOutputStream(Constants.ABOX_MODEL_PATH);
        model.write(writerStream, "N-TRIPLE");
        writerStream.close();
    }

    public static Individual createPaperIndividual(OntModel model, String paperType, String title){
        Individual paperIndividual =  null;
        OntClass fullPaperOntClass = model.getOntClass(Constants.BASE_URI.concat("Full_paper"));
        OntClass shortPaperOntClass = model.getOntClass(Constants.BASE_URI.concat("Short_paper"));
        OntClass demoPaperOntClass = model.getOntClass(Constants.BASE_URI.concat("Demo_paper"));
        OntClass posterOntClass = model.getOntClass(Constants.BASE_URI.concat("Poster"));
        if (paperType.equals(Utils.clean_str_buffer("Full Paper"))){
            paperIndividual = fullPaperOntClass.createIndividual(Constants.BASE_URI.concat(title));
        }
        else if (paperType.equals(Utils.clean_str_buffer("Short Paper".toLowerCase()))){
            paperIndividual = shortPaperOntClass.createIndividual(Constants.BASE_URI.concat(title));
        }
        else if (paperType.equals(Utils.clean_str_buffer("Demo Paper".toLowerCase()))){
            paperIndividual = demoPaperOntClass.createIndividual(Constants.BASE_URI.concat(title));
        }
        else if (paperType.equals(Utils.clean_str_buffer("Poster".toLowerCase()))){
            paperIndividual = posterOntClass.createIndividual(Constants.BASE_URI.concat(title));
        }
        return paperIndividual;
    }

    public static Individual createYearIndividual(OntModel model, String year){
        OntClass yearOntClass = model.getOntClass(Constants.BASE_URI.concat("Year"));
        return yearOntClass.createIndividual(Constants.BASE_URI.concat(year));
    }

    public static Individual createPublicationIndividual(OntModel model, String venueType, String publication){
        Individual publicationIndividual = null;
        OntClass conferenceProceedingOntClass = model.getOntClass(Constants.BASE_URI.concat("Conference_proceeding"));
        OntClass journalVolumeOntClass = model.getOntClass(Constants.BASE_URI.concat("Journal_volume"));
        if (venueType.equals(Utils.clean_str_buffer("Conference"))){
            publicationIndividual = conferenceProceedingOntClass.createIndividual(Constants.BASE_URI.concat(publication));
        }
        else{
            publicationIndividual = journalVolumeOntClass.createIndividual(Constants.BASE_URI.concat(publication));
        }
        return publicationIndividual;
    }

    public static Individual createVenueIndividual(OntModel model, String venueType, String venue, String confType){
        Individual venueIndividual = null;
        OntClass journalOntClass = model.getOntClass(Constants.BASE_URI.concat("Journal"));
        OntClass workshopOntClass = model.getOntClass(Constants.BASE_URI.concat("Workshop"));
        OntClass symphosiumOntClass = model.getOntClass(Constants.BASE_URI.concat("Symphosium"));
        OntClass expertGroupOntClass = model.getOntClass(Constants.BASE_URI.concat("Expert_group"));
        OntClass regularConfOntClass = model.getOntClass(Constants.BASE_URI.concat("Regular_conf"));
        if(venueType.equals(Utils.clean_str_buffer("Conference"))) {
            if(confType.equals(Utils.clean_str_buffer("Expert Group"))){
                venueIndividual = expertGroupOntClass.createIndividual(Constants.BASE_URI.concat(venue));
            }
            else if(confType.equals(Utils.clean_str_buffer("Workshop"))){
                venueIndividual = workshopOntClass.createIndividual(Constants.BASE_URI.concat(venue));
            }
            else if (confType.equals(Utils.clean_str_buffer("Symposium"))){
                venueIndividual = symphosiumOntClass.createIndividual(Constants.BASE_URI.concat(venue));
            }
            else{
                venueIndividual = regularConfOntClass.createIndividual(Constants.BASE_URI.concat(venue));
            }
        }
        else{
            venueIndividual = journalOntClass.createIndividual(Constants.BASE_URI.concat(venue) );
        }
        return venueIndividual;
    }

    public static void createAreaIndividual(OntModel model, OntProperty hasAreaOntProp, Individual venueIndividual, String areas){
        OntClass areaOntClass = model.getOntClass(Constants.BASE_URI.concat("Area"));
        if (areas.contains("|")) {
            Arrays.stream(areas.split("|")).forEach(area -> venueIndividual.addProperty(hasAreaOntProp, areaOntClass.createIndividual(Constants.BASE_URI.concat(area))));
        }
    }

    public static void createAuthorIndividual(OntModel model, OntProperty submitOntProp, Individual paperIndividual, String authors){
        OntClass authorOntClass = model.getOntClass(Constants.BASE_URI.concat("Author"));
        if (authors.contains("|")) {
            Arrays.stream(authors.split("|")).map(author -> authorOntClass.createIndividual(Constants.BASE_URI.concat(author))).forEach(authorIndividual -> authorIndividual.addProperty(submitOntProp, paperIndividual));
        }
    }

    public static Individual createHeadIndividual(OntModel model, String venueType, String head){
        Individual headIndividual = null;
        OntClass chairOntClass = model.getOntClass(Constants.BASE_URI.concat("Chair"));
        OntClass editorOntClass = model.getOntClass(Constants.BASE_URI.concat("Editor"));
        if(venueType.equals(Utils.clean_str_buffer("Conference"))) {
            headIndividual = chairOntClass.createIndividual(Constants.BASE_URI.concat(head));
        }
        else{
            headIndividual = editorOntClass.createIndividual(Constants.BASE_URI.concat(head));
        }
        return headIndividual;
    }


}