package com.sdm.rdfs.Commons;

public class Utils {

    public static String clean_str_buffer(String str) {
        return str
                .strip()
                .toLowerCase()
                .replaceAll("[\\p{Ps}\\p{Pe}]", "") // To remove all opening & closing brackets (https://stackoverflow.com/a/25853119/6390175)
                ;
    }
}