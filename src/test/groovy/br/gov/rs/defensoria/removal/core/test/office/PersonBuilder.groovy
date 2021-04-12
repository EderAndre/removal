package br.gov.rs.defensoria.removal.core.test.office

import br.gov.rs.defensoria.removal.domain.Office
import br.gov.rs.defensoria.removal.domain.Person

class PersonBuilder {
	private List<Office> pretensions
	private Person person
	private Office allocation

    PersonBuilder(Persons person, Offices office) {
		this.person = person.getPerson()
		this.person.allocation = office.get()
	}

    PersonBuilder(Persons person) {
		this.person = person.getPerson()
	}

	def static builder(Persons person, Offices office) {
		new PersonBuilder(person, office)
    }

	def static builder(Persons person) {
		new PersonBuilder(person)
	}

	def setPretensions(List<Offices> pretensions) {
		pretensions.each {
			person.pretensions.add(it.getNew())
		}
		return this
	}

	def build() {
		return person
	}
}
