/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Allegory;

/**
 *
 * @author katrinavanlaan
 */
import PrologDB.*;
import MDELite.RunningBear;
import MDELite.Marquee2Arguments;

public class Gen extends RunningBear {
    static String q = "\"";
    static DBSchema dbs;
    static Table dual;
    static String appName;

    public static void main(String... args) {
        // Step 1: standard marquee processing
        Marquee2Arguments mark = new Marquee2Arguments(Vpl2Schema.class, ".schema.pl", ".java", args);
        String inputFileName = mark.getInputFileName();
        String outputFileName = mark.getOutputFileName();
        appName = mark.getAppName(mark.getInputFileName());
        openOut(outputFileName);
        appName = mark.getAppName(mark.getInputFileName());

        // Step 2: open tables of database and create empty schema
        //         and read dual file
        dbs = DBSchema.readSchema(inputFileName);
        dual = Table.readTable(appName + ".dual.csv");

        // Step 3: generate file
        header();
        mainClass();
        common();
        classes();
        footer();
    }
}
