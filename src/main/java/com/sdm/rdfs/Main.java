package com.sdm.rdfs;

import java.lang.Exception;
import com.sdm.rdfs.Ontology.ABOX;
import com.sdm.rdfs.Ontology.TBOX;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        try {
            TBOX.createOntlogyTBOX();
            ABOX.buildAndSaveABOX();
            System.out.println("Model Saved Successfully");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}