package com.sdm.rdfs.Ontology;

import java.io.FileOutputStream;
import org.apache.jena.ontology.*;
import com.sdm.rdfs.Commons.Constants;
import org.apache.jena.vocabulary.XSD;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.ReasonerRegistry;

public class TBOX {

    public static void createOntlogyTBOX() throws Exception {

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);

        //Class Ontology
        OntClass person = model.createClass(Constants.BASE_URI.concat("Person"));
        OntClass author = model.createClass(Constants.BASE_URI.concat("Author"));
        OntClass chair = model.createClass(Constants.BASE_URI.concat("Chair"));
        OntClass editor = model.createClass(Constants.BASE_URI.concat("Editor"));
        OntClass reviewer = model.createClass(Constants.BASE_URI.concat("Reviewer"));

        person.addSubClass( author );
        person.addSubClass( chair );
        person.addSubClass( editor );
        person.addSubClass( reviewer );

        OntClass paper = model.createClass(Constants.BASE_URI.concat("Paper"));
        OntClass fullPaper  = model.createClass(Constants.BASE_URI.concat("Full_paper"));
        OntClass poster = model.createClass(Constants.BASE_URI.concat("Poster"));
        OntClass demoPaper = model.createClass(Constants.BASE_URI.concat("Demo_paper"));
        OntClass shortPaper = model.createClass(Constants.BASE_URI.concat("Short_paper"));

        paper.addSubClass( fullPaper );
        paper.addSubClass( poster );
        paper.addSubClass( demoPaper );
        paper.addSubClass( shortPaper );

        OntClass venue = model.createClass(Constants.BASE_URI.concat("Venue"));
        OntClass journal  = model.createClass(Constants.BASE_URI.concat("Journal"));
        OntClass conference = model.createClass(Constants.BASE_URI.concat("Conference"));
        OntClass workshop = model.createClass(Constants.BASE_URI.concat("Workshop"));
        OntClass symposium  = model.createClass(Constants.BASE_URI.concat("Symposium"));
        OntClass expertGroup = model.createClass(Constants.BASE_URI.concat("Expert_group"));
        OntClass regularConf = model.createClass(Constants.BASE_URI.concat("Regular_conf"));

        venue.addSubClass(journal);
        venue.addSubClass(conference);
        conference.addSubClass(workshop);
        conference.addSubClass(symposium);
        conference.addSubClass(expertGroup);
        conference.addSubClass(regularConf);

        OntClass review = model.createClass(Constants.BASE_URI.concat("Review"));
        OntClass publication  = model.createClass(Constants.BASE_URI.concat("Publication"));
        OntClass confProceeding  = model.createClass(Constants.BASE_URI.concat("Conference_proceeding"));
        OntClass jourVolume  = model.createClass(Constants.BASE_URI.concat("Journal_volume"));
        OntClass area = model.createClass(Constants.BASE_URI.concat("Area"));

        publication.addSubClass(confProceeding);
        publication.addSubClass(jourVolume);

        // Properties ontology
        OntProperty writes = model.createOntProperty( Constants.BASE_URI.concat("Writes") );
        writes.addDomain( author );
        writes.addRange( paper );
        writes.addLabel("Author writes paper", "en");

        OntProperty hasArea = model.createOntProperty( Constants.BASE_URI.concat("Has_area") );
        hasArea.addDomain( paper );
        hasArea.addRange( area );
        hasArea.addLabel("Paper has area", "en");

        OntProperty relatedTo = model.createOntProperty( Constants.BASE_URI.concat("Related_to") );
        relatedTo.addDomain( venue );
        relatedTo.addRange( area );
        relatedTo.addLabel("Venue related to area", "en");

        OntProperty submittedTo = model.createOntProperty( Constants.BASE_URI.concat("Submitted_to") );
        submittedTo.addDomain( paper );
        submittedTo.addRange( venue );
        submittedTo.addLabel("Paper submitted to a venue", "en");

        OntProperty acceptedAs = model.createOntProperty( Constants.BASE_URI.concat("Accepted_as") );
        acceptedAs.addDomain( paper );
        acceptedAs.addRange( publication );
        acceptedAs.addLabel("Paper accepted as a publication", "en");

        OntProperty belongsToConf = model.createOntProperty( Constants.BASE_URI.concat("Belongs_to_conf") );
        belongsToConf.addDomain( confProceeding );
        belongsToConf.addRange( conference );
        belongsToConf.addLabel("Conference Proceeding belongs to a conference", "en");

        OntProperty belongsToJour = model.createOntProperty( Constants.BASE_URI.concat("Belongs_to_jour") );
        belongsToJour.addDomain( jourVolume );
        belongsToJour.addRange( journal );
        belongsToJour.addLabel("Journal Volume belongs to a journal", "en");

        OntProperty headsJournal = model.createOntProperty( Constants.BASE_URI.concat("Heads_journal") );
        headsJournal.addDomain( editor );
        headsJournal.addRange( journal );
        headsJournal.addLabel("Editor heads a journal", "en");

        OntProperty headsConference = model.createOntProperty( Constants.BASE_URI.concat("Heads_conference") );
        headsConference.addDomain( chair );
        headsConference.addRange( conference );
        headsConference.addLabel("Chair heads a conference", "en");

        OntProperty assignedByEditor = model.createOntProperty( Constants.BASE_URI.concat("Assigned_by_editor") );
        assignedByEditor.addDomain( editor );
        assignedByEditor.addRange( reviewer );
        assignedByEditor.addLabel("Reviewer Assigned by Editor", "en");

        OntProperty assignedByChair = model.createOntProperty( Constants.BASE_URI.concat("Assigned_by_chair") );
        assignedByChair.addDomain( chair );
        assignedByChair.addRange( reviewer );
        assignedByChair.addLabel("Reviewer Assigned by Editor", "en");

        OntProperty provides = model.createOntProperty( Constants.BASE_URI.concat("Provides") );
        provides.addDomain( reviewer );
        provides.addRange( review );
        provides.addLabel("Reviewer provides a review", "en");

        OntProperty approves = model.createOntProperty( Constants.BASE_URI.concat("Approves") );
        approves.addDomain( review );
        approves.addRange( paper );
        approves.addLabel("Review approves the paper", "en");

        OntProperty assignedTo = model.createOntProperty( Constants.BASE_URI.concat("Assigned_to") );
        assignedTo.addDomain( reviewer );
        assignedTo.addRange( paper );
        assignedTo.addLabel("Reviewer is assigned to a paper", "en");

        //DataType properties ontology aka class attributes
        OntProperty paperTitle = model.createOntProperty(Constants.BASE_URI.concat("paper_title"));
        paperTitle.addDomain(paper);
        paperTitle.addRange(XSD.xstring);
        paperTitle.addLabel("Paper has a title", "en");

        OntProperty paperAbstract = model.createOntProperty(Constants.BASE_URI.concat("paper_abstract"));
        paperAbstract.addDomain(paper);
        paperAbstract.addRange(XSD.xstring);
        paperAbstract.addLabel("Paper has a title", "en");

        OntProperty paperYear = model.createOntProperty(Constants.BASE_URI.concat("paper_year"));
        paperYear.addDomain(paper);
        paperYear.addRange(XSD.xstring);
        paperYear.addLabel("Paper written in year", "en");

        OntProperty areaName = model.createOntProperty(Constants.BASE_URI.concat("area_name"));
        areaName.addDomain(area);
        areaName.addRange(XSD.xstring);
        areaName.addLabel("Area has a name", "en");

        OntProperty venueName = model.createOntProperty(Constants.BASE_URI.concat("venue_name"));
        venueName.addDomain(venue);
        venueName.addRange(XSD.xstring);
        venueName.addLabel("Venue has a name", "en");

        OntProperty venueCity = model.createOntProperty(Constants.BASE_URI.concat("venue_city"));
        venueCity.addDomain(venue);
        venueCity.addRange(XSD.xstring);
        venueCity.addLabel("Venue organized in a city", "en");

        OntProperty publicationName = model.createOntProperty(Constants.BASE_URI.concat("publication_name"));
        publicationName.addDomain(publication);
        publicationName.addRange(XSD.xstring);
        publicationName.addLabel("Publication has a name", "en");
        publicationName.addLabel("Publication has a name", "en");

        OntProperty publicationYear = model.createOntProperty(Constants.BASE_URI.concat("publication_year"));
        publicationYear.addDomain(publication);
        publicationYear.addRange(XSD.xstring);
        publicationYear.addLabel("Publication has a type", "en");

        OntProperty personName = model.createOntProperty(Constants.BASE_URI.concat("person_name"));
        personName.addDomain(person);
        personName.addRange(XSD.xstring);
        personName.addLabel("Person has a name", "en");

        OntProperty decision = model.createOntProperty(Constants.BASE_URI.concat("decision"));
        decision.addDomain(review);
        decision.addRange(XSD.xstring);
        decision.addLabel("Review provides a decision", "en");

        OntProperty reviewText = model.createOntProperty(Constants.BASE_URI.concat("review_text"));
        reviewText.addDomain(review);
        reviewText.addRange(XSD.xstring);
        reviewText.addLabel("Review has comments", "en");

        //save ontology if valid
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner.bindSchema(model);
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);
        if(infModel.validate().isValid()){
            FileOutputStream writerStream = new FileOutputStream(Constants.TBOX_MODEL_PATH);
            model.write(writerStream, "RDF/XML");
            writerStream.close();
        }
        else{
            throw new Exception("Invalid Ontology");
        }
    }
}
