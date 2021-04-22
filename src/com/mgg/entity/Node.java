package com.mgg.entity;

/**
 * This class represents a generic node utilized by the LinkedList class,
 * and allows for modification and access of nodes.
 * 
 * Date: 04/05/2021
 * CSCE 156 Spring 2021
 * @author Eric Le & Brock Melvin
 * 
 */

public class Node <T> {
	private final T element;
	private Node<T> next;
	
	public Node(T element) {
		this.element = element;
		this.next = null;
	}
	
	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public T getElement() {
		return element;
	}
	
	public boolean hasNext() {
		return (this.next != null);
	}
}
