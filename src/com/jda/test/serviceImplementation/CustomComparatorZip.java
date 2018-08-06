package com.jda.test.serviceImplementation;

import java.util.Comparator;

import com.jda.test.model.PersonData;

public class CustomComparatorZip implements Comparator<PersonData> {
	@Override
	public int compare(PersonData o1, PersonData o2) {
		return o1.getAddress().getZipcode().compareTo(o2.getAddress().getZipcode());
	}

}
