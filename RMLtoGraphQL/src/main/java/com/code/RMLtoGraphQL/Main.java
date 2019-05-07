package com.code.RMLtoGraphQL;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import be.ugent.rml.DataFetcher;
import be.ugent.rml.Executor;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.RDF4JStore;
import be.ugent.rml.store.QuadStore;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
	public static void main(String[] args) {

        String cwd = "C:\\Users\\Alvaro\\git\\RMLtoGraphQL\\RMLtoGraphQL\\Ejemplos\\test-cases\\RMLTC0001a-JSON\\Prueba;"; //path to default directory for local files
        String mappingFile = "C:\\Users\\Alvaro\\git\\RMLtoGraphQL\\RMLtoGraphQL\\Ejemplos\\test-cases\\RMLTC0001a-JSON\\Prueba\\mapping.ttl"; //path to the mapping file that needs to be executed
        
        try {
            InputStream mappingStream = new FileInputStream(mappingFile);
            Model model = Rio.parse(mappingStream, "", RDFFormat.TURTLE);
            RDF4JStore rmlStore = new RDF4JStore(model);

            Executor executor = new Executor(rmlStore, new RecordsFactory(new DataFetcher(cwd, rmlStore)), mappingFile);
            QuadStore result = executor.execute(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } 
}
