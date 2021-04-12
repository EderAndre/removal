package br.gov.rs.defensoria.removal.core.test.office

import br.gov.rs.defensoria.removal.domain.Office
import br.gov.rs.defensoria.removal.domain.Person

class EditalCenarioBuilder {
	public List<Office> allOffices
	public List<Person> allPersons
	public List<Offices> notChoosableOffices
	public Map<Integer, List<Offices>> allPretensions
	public List<Office> choosableOffices


    static EditalCenarioBuilder builder() {
		return new EditalCenarioBuilder(
				allOffices: new ArrayList<Office>(), allPersons: new ArrayList<Person>()
				, notChoosableOffices: new ArrayList<Offices>(), choosableOffices: new ArrayList<Office>()
				,allPretensions: new HashMap<Integer, ArrayList<Offices>>())
	}

    EditalCenarioBuilder build() {
		allPretensions.each { pret ->
			setPersonIdPretensions(pret.key, pret.value)
		}

		allOffices.each { office ->
			if(notChoosableOffices.count{it.id == office.id} == 0) {
				choosableOffices.add(office)
			}
		}
		return this
	}

    EditalCenarioBuilder appendChoosables(List<Offices> offices) {
		offices.each { o ->
			addToAllOfficeList(o)
			Office office = allOffices.find {it.id == o.id}
			if(choosableOffices.count {it.id == office.id} == 0)
				choosableOffices.add(office)
		}
		return this
	}

    EditalCenarioBuilder setNotChoosableOffices(List<Offices> offices) {
		notChoosableOffices = offices
        return this
	}

    EditalCenarioBuilder addCandidature(Persons person, Offices allocation, List<Offices> pretensions) {
		addToAllPersonList(person)
		addToAllOfficeList(allocation)
		pretensions.each {
			addToAllOfficeList(it)
		}
		setOfficeOccupant(person, allocation)
		addPretensions(person, pretensions)

		return this
	}

    EditalCenarioBuilder addCandidature(Persons person, List<Offices> pretensions) {
		addToAllPersonList(person)
		pretensions.each {
			addToAllOfficeList(it)
		}
		addPretensions(person, pretensions)

		return this
	}

    void addPretensions(Persons p, List<Offices> offices) {
		allPretensions.put(p.id, offices)
	}

    void setPersonIdPretensions(Integer p, List<Offices> o) {
		Person person = allPersons.find { it.id == p }
		o.each { office ->
			person.pretensions.add(allOffices.find { it.id == office.id })
		}
	}

	private void setOfficeOccupant(Persons p, Offices o) {
		Person person = allPersons.find { it.id == p.id }
		person.allocation = allOffices.find { it.id == o.id }
		person.allocation.occupant = person
	}

	private void addToAllPersonList(Persons p) {
		if(allPersons.count {it.id == p.id} == 0)
			allPersons.add(new Person(id: p.id, name: p.name, antiquity: p.antiquity))
	}

	private void addToAllOfficeList(Offices office) {
		if(allOffices.count {it.id == office.id} == 0)
			allOffices.add(new Office(id: office.id, name: office.name))
	}
}
