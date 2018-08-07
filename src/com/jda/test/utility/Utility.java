package com.jda.test.utility;

import java.util.Scanner;

public class Utility {
	Scanner scanner = new Scanner(System.in);
	
	public String inputString(){
		String input = scanner.nextLine();
		while(true){
			if(input.length()!=0){
				break;
			}
			else{
				System.out.println("Empty string. Enter again.");
				input = scanner.nextLine();
			}
		}
		return input;
	}
	
	public Integer inputPositiveInteger(){
		String regex = "^[1-9]\\d*$";
		Integer temp = 0;
		String input = scanner.nextLine();
		if(input.matches(regex)) {
			temp = Integer.parseInt(input);
		}
		else {
			System.out.println("Invalid input.");
		}
		return temp;
	}
	
	public String inputMobNo() {
		String numberRegex = "[0-9]+";
		String input = scanner.nextLine();
		String number = "";
		if(input.matches(numberRegex) && input.length()==10) {
			number = input;
		}
		return number;
	}

	public int inputZipcode() {
		String input = scanner.nextLine();
		while(true){
			if(input.length()==6){
				Integer zipcode = Integer.parseInt(input);
				return zipcode;
			}
			else{
				System.out.println("Zipcode should be of 6 digits.");
				input = scanner.nextLine();
			}
		}
	}
	public long inputLong() {
		long input = scanner.nextLong();
		return input;
	}
	public void emptyInput() {
		scanner.nextLine();
	}
}
