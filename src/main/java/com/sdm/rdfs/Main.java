package com.sdm.rdfs;

import java.io.IOException;
import com.sdm.rdfs.Ontology.ABOX;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        try {

            ABOX.buildAndSaveAbox();
            System.out.println("Model Saved Successfully");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}