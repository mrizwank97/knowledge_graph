package com.sdm.rdfs.ontology;

import com.sdm.rdfs.commons.Utils;
import com.sdm.rdfs.commons.Constants;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TBOX {

    public static void buildAndPersistTBox() {

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);

        OntClass person = model.createClass(Constants.URI.concat("Person"));
        OntClass author = model.createClass(Constants.URI.concat("Author"));
        OntClass chair = model.createClass(Constants.URI.concat("Chair"));
        OntClass editor = model.createClass(Constants.URI.concat("Editor"));
        OntClass reviewer = model.createClass(Constants.URI.concat("Reviewer"));

        person.addSubClass(author);
        person.addSubClass(chair);
        person.addSubClass(editor);
        person.addSubClass(reviewer);

        OntClass paper = model.createClass(Constants.URI.concat("Paper"));
        OntClass fullPaper = model.createClass(Constants.URI.concat("Full_Paper"));
        OntClass shortPaper = model.createClass(Constants.URI.concat("Short_Paper"));
        OntClass demoPaper = model.createClass(Constants.URI.concat("Demo_Paper"));
        OntClass posterPaper = model.createClass(Constants.URI.concat("Poster_Paper"));

        paper.addSubClass(fullPaper);
        paper.addSubClass(shortPaper);
        paper.addSubClass(demoPaper);
        paper.addSubClass(posterPaper);

        OntClass year = model.createClass(Constants.URI.concat("Year"));

        OntClass venue = model.createClass(Constants.URI.concat("Venue"));
        OntClass conference = model.createClass(Constants.URI.concat("Conference"));
        OntClass journal = model.createClass(Constants.URI.concat("Journal"));

        venue.addSubClass(conference);
        venue.addSubClass(journal);

        OntClass workshop = model.createClass(Constants.URI.concat("Workshop"));
        OntClass symposium = model.createClass(Constants.URI.concat("Symposium"));
        OntClass expertGroup = model.createClass(Constants.URI.concat("Expert_Group"));
        OntClass regularConference = model.createClass(Constants.URI.concat("Regular_Conference"));

        conference.addSubClass(workshop);
        conference.addSubClass(symposium);
        conference.addSubClass(expertGroup);
        conference.addSubClass(regularConference);

        OntClass decision = model.createClass(Constants.URI.concat("Decision"));
        OntClass acceptOrRejected = model.createClass(Constants.URI.concat("Accepted_Or_Rejected"));
        OntClass reviewText = model.createClass(Constants.URI.concat("Decision_Text"));

        decision.addSubClass(acceptOrRejected);
        decision.addSubClass(reviewText);

        OntClass areas = model.createClass(Constants.URI.concat("Areas"));

        OntClass publications = model.createClass(Constants.URI.concat("Publications"));

        OntProperty submit = model.createOntProperty(Constants.URI.concat("submit"));
        submit.addDomain(author);
        submit.addRange(paper);
        submit.addLabel("Author submits paper", "en");

        OntProperty submittedToVenue = model.createOntProperty(Constants.URI.concat("submitted_to"));
        submittedToVenue.addDomain(paper);
        submittedToVenue.addRange(venue);
        submittedToVenue.addLabel("Author submits paper to a venue", "en");

        OntProperty handlesConferences = model.createOntProperty(Constants.URI.concat("handles_conference"));
        handlesConferences.addDomain(chair);
        handlesConferences.addRange(conference);
        handlesConferences.addLabel("Chair(s) handles a conference", "en");

        OntProperty handlesJournals = model.createOntProperty(Constants.URI.concat("handles_journal"));
        handlesJournals.addDomain(editor);
        handlesJournals.addRange(journal);
        handlesJournals.addLabel("Editor(s) handles a journal", "en");

        OntProperty assignedByChairs = model.createOntProperty(Constants.URI.concat("assigned_by_chairs"));
        assignedByChairs.addDomain(chair);
        assignedByChairs.addRange(reviewer);
        assignedByChairs.addLabel("Chairs assign reviewers", "en");

        OntProperty assignedByEditors = model.createOntProperty(Constants.URI.concat("assigned_by_editors"));
        assignedByEditors.addDomain(editor);
        assignedByEditors.addRange(reviewer);
        assignedByEditors.addLabel("Editors assign reviewers", "en");

        OntProperty assignedTo = model.createOntProperty(Constants.URI.concat("assigned_to"));
        assignedTo.addDomain(reviewer);
        assignedTo.addRange(paper);
        assignedTo.addLabel("Reviewers are assigned to a paper", "en");

        OntProperty takesDecision = model.createOntProperty(Constants.URI.concat("takes_decision"));
        takesDecision.addDomain(reviewer);
        takesDecision.addRange(decision);
        takesDecision.addLabel("Reviewer takes a decision", "en");

        OntProperty reviewIsGiven = model.createOntProperty(Constants.URI.concat("is_paper_accepted"));
        reviewIsGiven.addDomain(decision);
        reviewIsGiven.addRange(acceptOrRejected);
        reviewIsGiven.addLabel("Paper is accepted or rejected ?", "en");

        OntProperty hasReviewComments = model.createOntProperty(Constants.URI.concat("has_review_comments"));
        hasReviewComments.addDomain(decision);
        hasReviewComments.addRange(reviewText);
        hasReviewComments.addLabel("Comments added by the Reviewer", "en");

        OntProperty hasArea = model.createOntProperty(Constants.URI.concat("has_area"));
        hasArea.addDomain(venue);
        hasArea.addRange(areas);
        hasArea.addLabel("Venue has area", "en");

        OntProperty paperHasPublication = model.createOntProperty(Constants.URI.concat("has_publication"));
        paperHasPublication.addDomain(paper);
        paperHasPublication.addRange(publications);
        paperHasPublication.addLabel("Paper is published", "en");

        OntProperty paperYear = model.createOntProperty(Constants.URI.concat("published_year"));
        paperYear.addDomain(paper);
        paperYear.addRange(year);
        paperYear.addLabel("Paper published in year ", "en");

        try {

            Utils.line_separator();
            Utils.log("Saving ontology model to '" + Constants.TBOX_MODEL_PATH + "'");

            FileOutputStream writerStream = new FileOutputStream( Constants.TBOX_MODEL_PATH );
            model.write(writerStream, "RDF/XML");
            writerStream.close();

            Utils.log("Ontology model saved!");
            Utils.line_separator();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}