package br.gov.rs.defensoria.removal.domain

class Office {
	int id
	int rhe
	String name
	Person occupant
	Person pretender
	List<Role> availableRoles
	boolean blocked
	boolean swapResult
	boolean hasOwner = false

    String toCSV() {
		def occupantstr = occupant == null ? ", , " : occupant.name + ', ' + occupant.id + ', ' + occupant.antiquity
		def pretenderstr = pretender == null ? "" : pretender.name + ', ' + pretender.id + ', ' + pretender.antiquity
		String result = "${id}, ${name}, ${occupantstr}, ${pretenderstr}"
		return result
	}
}