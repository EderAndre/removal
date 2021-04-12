package br.gov.rs.defensoria.removal.core.util

import br.gov.rs.defensoria.removal.domain.Person

class AntiquityComparator implements Comparator<Person> {

	@Override
    int compare(Person p1, Person p2) {
		return p1.getAntiquity().compareTo(p2.getAntiquity())
	}

}
