package com.jda.test.serviceImplementation;

import java.util.Comparator;

import com.jda.test.model.PersonData;

public class CustomComparatorName implements Comparator<PersonData> {
	@Override
	public int compare(PersonData o1, PersonData o2) {
		return o1.getFirstName().compareTo(o2.getFirstName());
	}
}
