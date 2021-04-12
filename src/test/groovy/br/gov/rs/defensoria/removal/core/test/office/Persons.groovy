package br.gov.rs.defensoria.removal.core.test.office


enum Persons {

	PEDRO( 2, 'pedro', 11),
	MARIA( 1, 'maria', 10),
	PAULA( 3, 'paula', 9),
	JOSE( 4, 'jose', 8),
	CARLOS( 5, 'carlos', 7),
	JERICO( 6, 'jerico', 6),
	NICLAUS( 7, 'niclaus', 5)

	private int id
	private String name
	private int antiquity

	private Persons(int id, String name, int antiquity) {
		this.id = id
        this.name = name
        this.antiquity = antiquity
    }
}
