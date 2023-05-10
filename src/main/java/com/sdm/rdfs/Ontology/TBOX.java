package com.sdm.rdfs.Ontology;

import com.sdm.rdfs.Commons.Constants;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileOutputStream;
import java.io.IOException;


public class TBOX {

    public static void createOntlogyTBOX() throws IOException {

        // Classes ontology

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

        OntClass paper = model.createClass(Constants.BASE_URI.concat("Paper"));
        OntClass full_paper  = model.createClass(Constants.BASE_URI.concat("Full_paper"));
        OntClass poster = model.createClass(Constants.BASE_URI.concat("Poster"));
        OntClass demo_paper = model.createClass(Constants.BASE_URI.concat("Demo_paper"));
        OntClass short_paper = model.createClass(Constants.BASE_URI.concat("Short_paper"));

        paper.addSubClass( full_paper );
        paper.addSubClass( poster );
        paper.addSubClass( demo_paper );
        paper.addSubClass( short_paper );

        OntClass venue = model.createClass(Constants.BASE_URI.concat("Venue"));
        OntClass journal  = model.createClass(Constants.BASE_URI.concat("Journal"));
        OntClass conference = model.createClass(Constants.BASE_URI.concat("Conference"));
        OntClass workshop = model.createClass(Constants.BASE_URI.concat("Workshop"));
        OntClass symposium  = model.createClass(Constants.BASE_URI.concat("Symposium"));
        OntClass expert_group = model.createClass(Constants.BASE_URI.concat("Expert_group"));
        OntClass regular_conf = model.createClass(Constants.BASE_URI.concat("Regular_conf"));

        venue.addSubClass( journal );
        venue.addSubClass( conference );
        conference.addSubClass( workshop );
        conference.addSubClass( symposium );
        conference.addSubClass( expert_group );
        conference.addSubClass( regular_conf );


        OntClass review = model.createClass(Constants.BASE_URI.concat("Review"));
        OntClass publication  = model.createClass(Constants.BASE_URI.concat("Publication"));
        OntClass area = model.createClass(Constants.BASE_URI.concat("Area"));

        // Properties ontology

        OntProperty writes = model.createOntProperty( Constants.BASE_URI.concat("Writes") );
        writes.addDomain( author );
        writes.addRange( paper );
        writes.addLabel("Author writes paper", "en");

        OntProperty has_area = model.createOntProperty( Constants.BASE_URI.concat("Has_area") );
        has_area.addDomain( paper );
        has_area.addRange( area );
        has_area.addLabel("Paper has area", "en");

        OntProperty submitted_to = model.createOntProperty( Constants.BASE_URI.concat("Submitted_to") );
        submitted_to.addDomain( paper );
        submitted_to.addRange( venue );
        submitted_to.addLabel("Paper submitted to a venue", "en");

        OntProperty accepted_as = model.createOntProperty( Constants.BASE_URI.concat("Accepted_as") );
        accepted_as.addDomain( paper );
        accepted_as.addRange( publication );
        accepted_as.addLabel("Paper accepted as a publication", "en");

        OntProperty belongs_to = model.createOntProperty( Constants.BASE_URI.concat("Belongs_to") );
        belongs_to.addDomain( publication );
        belongs_to.addRange( venue );
        belongs_to.addLabel("Publication belongs to a venue", "en");

        OntProperty heads_journal = model.createOntProperty( Constants.BASE_URI.concat("Heads_journal") );
        heads_journal.addDomain( editor );
        heads_journal.addRange( journal );
        heads_journal.addLabel("Editor heads a journal", "en");

        OntProperty heads_conference = model.createOntProperty( Constants.BASE_URI.concat("Heads_conference") );
        heads_conference.addDomain( chair );
        heads_conference.addRange( conference );
        heads_conference.addLabel("Chair heads a conference", "en");

        OntProperty assigned_by_editor = model.createOntProperty( Constants.BASE_URI.concat("Assigned_by_editor") );
        assigned_by_editor.addDomain( editor );
        assigned_by_editor.addRange( reviewer );
        assigned_by_editor.addLabel("Editor assigns reviewers", "en");

        OntProperty assigned_by_chair = model.createOntProperty( Constants.BASE_URI.concat("Assigned_by_chair") );
        assigned_by_chair.addDomain( chair );
        assigned_by_chair.addRange( reviewer );
        assigned_by_chair.addLabel("Chair assigns reviewers", "en");

        OntProperty provides = model.createOntProperty( Constants.BASE_URI.concat("Provides") );
        provides.addDomain( reviewer );
        provides.addRange( review );
        provides.addLabel("Reviewer provides a review", "en");

        OntProperty approves = model.createOntProperty( Constants.BASE_URI.concat("Approves") );
        approves.addDomain( review );
        approves.addRange( paper );
        approves.addLabel("Paper is approved by reviews", "en");

        OntProperty assigned_to = model.createOntProperty( Constants.BASE_URI.concat("Assigned_to") );
        assigned_to.addDomain( reviewer );
        assigned_to.addRange( paper );
        assigned_to.addLabel("Reviewer is assigned to a paper", "en");



        FileOutputStream writerStream = new FileOutputStream(Constants.TBOX_MODEL_PATH );
        model.write(writerStream, "RDF/XML");
        writerStream.close();
    }
}
