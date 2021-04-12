package br.gov.rs.defensoria.removal.core.test.office


enum Offices {
	CAMPO_BOM(1, 'campo bom'),
	SAPIRANGA(2,  'sapiranga'),
	CAXIAS_DO_SUL(3,  'caxias do sul'),
	NOVO_HAMBURGO(4,  'novo hamburgo'),
	PORTO_ALEGRE(5,  'porto alegre'),
	ALVORADA(6,  'alvorada'),
	GRAVATAI(7,  'gravatai'),
	SEBERI(8,  'seberi'),
	POUSO_NOVO(9,  'pouso novo'),
	GARIBALDI(10,  'garibaldi'),
	DOIS_IRMAOS(11,  'dois irmaos'),
	MORRO_REUTER(12,  'morro reuter')

	private Offices(Integer id, String name) {
		this.id = id
		this.name = name
	}

	private Integer id
    private String name
}
