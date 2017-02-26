package com.crg.linkedlist;

import java.util.Iterator;

public class TestMyLinkedList {

	public static void main(String[] args) {
		MyLinkedList<String> myLinkedList = new MyLinkedList<>();
		myLinkedList.add("today");
		myLinkedList.add("is");
		myLinkedList.add("nice");
		myLinkedList.add("day");
		myLinkedList.add("good");
		
		System.out.println("myLinkedList.size(): " + myLinkedList.size());
		
		Iterator<String> iterator = myLinkedList.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		myLinkedList.set(3, "cup");
		System.out.println("=================================");
		Iterator<String> iterator1 = myLinkedList.iterator();
		while (iterator1.hasNext()) {
			System.out.println(iterator1.next());
			iterator1.remove();
		}
		
		System.out.println("myLinkedList.size(): " + myLinkedList.size());
		myLinkedList.remove(3);
	}

}
