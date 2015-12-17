package test;

import java.util.List;

import document.BasicDocument;
import document.Document;

public class TestClass {

	public static void main(String[] args) {
        Document d = new BasicDocument("this is a test.23,54,390.");
        
        System.out.println(d.getTokens("[a-z0-9 ]+"));
        System.out.println(d.getTokens("[^.,]+"));
        System.out.println(d.getTokens("[a-z0-9]+"));
        System.out.println(d.getTokens("[^ ]+"));
        System.out.println(d.getTokens("[a-z ]+|[0-9]+"));
    }
	
}
