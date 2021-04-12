CREATE TABLE editais(
    id_edital int,
    abertura_edital datetime NOT NULL,
    assinatura_email_edital varchar(255),
    bloqueado bit,
    descricao_edital varchar(255),
    email_envio_edital varchar(255),
    email_resposta_edital varchar(255),
    encerramento_edital datetime,
    limite_primeira_candidatura datetime,
    tipo_edital int NOT NULL,
    apenas_desistencias_permitidas datetime,
    CONSTRAINT PK_editais PRIMARY KEY (id_edital)
);

CREATE TABLE servidores(
    matricula int,
    email_servidor varchar(255),
    nome_completo varchar(255),
    CONSTRAINT PK_servidores PRIMARY KEY (matricula)
);

CREATE TABLE arquivos(
    id_arquivo int,
    data_arquivo datetime,
    descricao_arquivo varchar(255),
    exibir_arquivo int,
    nome_arquivo varchar(255),
    id_edital int,
    CONSTRAINT PK_arquivos PRIMARY KEY (id_arquivo),
    CONSTRAINT FK_arquivos_id_edital FOREIGN KEY (id_edital) REFERENCES editais (id_edital)
);

CREATE TABLE avisos(
    id_aviso int,
    data_aviso datetime,
    data_inclusao datetime,
    descricao_aviso varchar(255),
    excluido_aviso int,
    titulo_aviso varchar(255),
    id_edital int,
    CONSTRAINT PK_avisos PRIMARY KEY (id_aviso),
    CONSTRAINT FK_avisos_id_edital FOREIGN KEY (id_edital) REFERENCES editais (id_edital)
);

CREATE TABLE comarcas(
    id_comarca int,
    descricao_comarca varchar(255),
    CONSTRAINT PK_comarcas PRIMARY KEY (id_comarca)
);

CREATE TABLE fechamentos(
    id_fechamento int,
    data_fechamento datetime,
    relatorio_fechamento varchar(255),
    relatorio_preliminar varchar(255),
    id_edital int,
    CONSTRAINT PK_fechamentos PRIMARY KEY (id_fechamento),
    CONSTRAINT FK_fechamentos_id_edital FOREIGN KEY (id_edital) REFERENCES editais (id_edital)
);

CREATE TABLE funcoes(
    id_funcao int,
    descricao_funcao varchar(255),
    CONSTRAINT PK_funcoes PRIMARY KEY (id_funcao)
);

CREATE TABLE permissoes(
    id_permissao int,
    permissao varchar(255),
    id_edital int,
    matricula int,
    CONSTRAINT PK_permissoes PRIMARY KEY (id_permissao),
    CONSTRAINT FK_permissoes_id_edital FOREIGN KEY (id_edital) REFERENCES editais (id_edital),
    CONSTRAINT FK_permissoes_matricula FOREIGN KEY (matricula) REFERENCES servidores (matricula)
);

CREATE TABLE sequence_table(
    seq_name varchar(255),
    seq_count int,
    CONSTRAINT PK_sequence_table PRIMARY KEY (seq_name)
);

CREATE TABLE snap_locais(
    id_linha int,
    id_fechamento int,
    id_local int,
    nome varchar(255),
    sem_dono bit,
    CONSTRAINT PK_snap_locais PRIMARY KEY (id_linha)
);

CREATE TABLE snap_pretensoes(
    id_pretensao int,
    cod_fechamento int,
    cod_local int,
    matricula int,
    ordem int,
    CONSTRAINT PK_snap_pretensoes PRIMARY KEY (id_pretensao)
);

CREATE TABLE snap_resultados(
    id_resultado int,
    cod_fechamento int,
    cod_local int,
    matricula int,
    ordem_ganha int,
    CONSTRAINT PK_snap_resultados PRIMARY KEY (id_resultado)
);

CREATE TABLE tipos_edital(
    id bigint,
    descricao varchar(50),
    CONSTRAINT PK_tipos_edital PRIMARY KEY (id)
);

CREATE TABLE unidades(
    id_unidade int,
    codigo varchar(255),
    descricao varchar(255),
    id_comarca int,
    CONSTRAINT PK_unidades PRIMARY KEY (id_unidade),
    CONSTRAINT FK_unidades FOREIGN KEY (id_comarca) REFERENCES comarcas (id_comarca)
);

CREATE TABLE vagas_edital(
    id_vaga_edital int,
    tipo_vaga int,
    id_edital int,
    id_unidade int,
    ordem_vaga int,
    CONSTRAINT PK_vagas_edital PRIMARY KEY (id_vaga_edital),
    CONSTRAINT FK_vagas_edital_id_edital FOREIGN KEY (id_edital) REFERENCES editais (id_edital),
    CONSTRAINT FK_vagas_edital_id_unidade FOREIGN KEY (id_unidade) REFERENCES unidades (id_unidade)
);

CREATE TABLE candidatos(
    id_candidato int,
    antiguidade int,
    id_edital int,
    id_funcao int,
    id_lotacao int,
    matricula int,
    CONSTRAINT PK_candidatos PRIMARY KEY (id_candidato),
    CONSTRAINT FK_candidatos_id_edital FOREIGN KEY (id_edital) REFERENCES editais (id_edital),
    CONSTRAINT FK_candidatos_id_funcao FOREIGN KEY (id_funcao) REFERENCES funcoes (id_funcao),
    CONSTRAINT FK_candidatos_id_lotacao FOREIGN KEY (id_lotacao) REFERENCES unidades (id_unidade),
    CONSTRAINT FK_candidatos_matricula FOREIGN KEY (matricula) REFERENCES servidores (matricula)
);

CREATE TABLE restricoes_candidaturas(
    id_restricao int,
    id_candidato int,
    id_unidade int,
    CONSTRAINT PK_restricoes_candidaturas PRIMARY KEY (id_restricao),
    CONSTRAINT FK_restricoes_candidaturas_id_candidato FOREIGN KEY (id_candidato) REFERENCES candidatos (id_candidato),
    CONSTRAINT FK_restricoes_candidaturas_id_unidade FOREIGN KEY (id_unidade) REFERENCES unidades (id_unidade)
);

CREATE TABLE candidaturas(
    id_candidatura int,
    data datetime NOT NULL,
    ordem_candidatura int,
    id_candidato int,
    id_vaga_edital int,
    CONSTRAINT PK_candidaturas PRIMARY KEY (id_candidatura),
    CONSTRAINT FK_candidaturas_id_candidato FOREIGN KEY (id_candidato) REFERENCES candidatos (id_candidato),
    CONSTRAINT FK_candidaturas_id_vaga_edital FOREIGN KEY (id_vaga_edital) REFERENCES vagas_edital (id_vaga_edital)
);

CREATE INDEX idx_candidaturas_data ON candidaturas (data);
CREATE INDEX idx_candidaturas_id_candidato ON candidaturas (id_candidato);
CREATE INDEX idx_candidaturas_id_vaga_edital ON candidaturas (id_vaga_edital);

CREATE INDEX idx_snap_pretensoes_cod_fechamento_cod_local_matricula_ordem
    ON snap_pretensoes (cod_fechamento, cod_local, matricula, ordem);

CREATE INDEX idx_snap_resultados_cod_fechamento_matricula ON snap_resultados (cod_fechamento, matricula);