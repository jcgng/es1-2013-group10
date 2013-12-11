package moodish.securityfilter.testsuite;

import static org.junit.Assert.*;
import moodish.securityfilter.records.buffer.SecurityFilterMoodBuffer;
import moodish.securityfilter.records.models.SecurityFilterMood;

import org.junit.Test;

public class SecurityFilterMoodBufferTestCase {

	@Test
	public void testSecurityFilterMoodBuffer() {
		SecurityFilterMoodBuffer buffer = new SecurityFilterMoodBuffer(11);
		// insert 3
		buffer.insert(new SecurityFilterMood("A"));
		buffer.insert(new SecurityFilterMood("B"));
		buffer.insert(new SecurityFilterMood("C"));
		// test iterator
		for(SecurityFilterMood mood : buffer) {
			System.out.println(mood.getMood());
		}
		// test get last
		System.out.println(buffer.getLast(0).getMood());
		assertEquals("C",buffer.getLast(0).getMood());
		System.out.println(buffer.getLast(2).getMood());
		assertEquals("A",buffer.getLast(2).getMood());
		if(buffer.getLast(4)!=null)
			fail("This was not supposed!");
		
		// insert + 7
		buffer.insert(new SecurityFilterMood("D"));
		buffer.insert(new SecurityFilterMood("E"));
		buffer.insert(new SecurityFilterMood("F"));
		buffer.insert(new SecurityFilterMood("G"));
		buffer.insert(new SecurityFilterMood("H"));
		buffer.insert(new SecurityFilterMood("I"));
		buffer.insert(new SecurityFilterMood("J"));		
		// test iterator
		for(SecurityFilterMood mood : buffer) {
			System.out.println(mood.getMood());
		}
		// insert + 3
		buffer.insert(new SecurityFilterMood("K"));
		buffer.insert(new SecurityFilterMood("L"));
		buffer.insert(new SecurityFilterMood("M"));
		// test iterator
		for(SecurityFilterMood mood : buffer) {
			System.out.println(mood.getMood());
		}
		// test get last
		assertEquals("M",buffer.getLast(0).getMood());
		assertEquals("K",buffer.getLast(2).getMood());
		assertEquals("I",buffer.getLast(4).getMood());
		assertEquals("G",buffer.getLast(6).getMood());
		assertEquals("E",buffer.getLast(8).getMood());
		assertEquals("D",buffer.getLast(10).getMood());
	}	
}
