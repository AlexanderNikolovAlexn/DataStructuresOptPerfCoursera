package test;

import java.util.Arrays;

public class TestClass {

	public static void main(String[] args) {
		String s = "%one%%two%%%three%%%%";
		
		System.out.println(Arrays.toString(s.split("%+")));
		System.out.println(Arrays.toString(s.split("[a-z]+")));
		System.out.println(Arrays.toString(s.split("one|two|three")));
		
	}
	
}
