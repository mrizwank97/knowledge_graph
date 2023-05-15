package com.sdm.rdfs;

import java.lang.Exception;
import com.sdm.rdfs.Ontology.ABOX;
import com.sdm.rdfs.Ontology.TBOX;

public class Main {
    public static void main(String[] args) {

        try {

            TBOX.createOntlogyTBOX();
            System.out.println("TBox Model Saved Successfully");
            ABOX.createOntlogyABOX();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}