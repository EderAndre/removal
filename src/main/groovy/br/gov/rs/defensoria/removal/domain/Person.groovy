package br.gov.rs.defensoria.removal.domain

class Person {
	int id
	int antiquity
	String name
	Office allocation
	Role role
	List<Office> pretensions
	boolean analyzed
	boolean blocked

    Person() {
		pretensions = new ArrayList()
	}

	@Override
    String toString() {
		return "Person [id= ${id}, antiquity=${antiquity}, name=${name}, allocation=${allocation}]"
	}	
}