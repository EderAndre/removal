CREATE TABLE arquivos (
  id_arquivo INTEGER NOT NULL,
  data_arquivo DATETIME,
  descricao_arquivo VARCHAR(255),
  exibir_arquivo INTEGER,
  nome_arquivo VARCHAR(255),
  id_edital INTEGER,
  PRIMARY KEY (id_arquivo)
);

CREATE TABLE avisos (
  id_aviso INTEGER NOT NULL,
  data_aviso DATETIME,
  data_inclusao DATETIME,
  descricao_aviso VARCHAR(255),
  excluido_aviso INTEGER,
  titulo_aviso VARCHAR(255),
  id_edital INTEGER,
  PRIMARY KEY (id_aviso)
);

CREATE TABLE candidatos (
  id_candidato INTEGER NOT NULL,
  antiguidade INTEGER,
  id_edital INTEGER,
  id_funcao INTEGER,
  id_lotacao INTEGER,
  matricula INTEGER,
  PRIMARY KEY (id_candidato)
);

CREATE TABLE candidaturas (
  id_candidatura INTEGER NOT NULL,
  data DATETIME NOT NULL,
  ordem_candidatura INTEGER,
  id_candidato INTEGER,
  id_vaga_edital INTEGER,
  PRIMARY KEY (id_candidatura)
);

CREATE TABLE comarcas (
  id_comarca INTEGER NOT NULL,
  descricao_comarca VARCHAR(255),
  PRIMARY KEY (id_comarca)
);

CREATE TABLE editais (
  id_edital INTEGER NOT NULL,
  abertura_edital DATETIME NOT NULL,
  apenas_desistencias_permitidas DATETIME,
  assinatura_email_edital VARCHAR(255),
  bloqueado BIT,
  descricao_edital VARCHAR(255),
  email_envio_edital VARCHAR(255),
  email_resposta_edital VARCHAR(255),
  encerramento_edital DATETIME,
  limite_primeira_candidatura DATETIME,
  tipo_edital INTEGER NOT NULL,
  PRIMARY KEY (id_edital)
);

CREATE TABLE fechamentos (
  id_fechamento INTEGER NOT NULL,
  data_fechamento DATETIME,
  relatorio_fechamento VARCHAR(255),
  relatorio_preliminar VARCHAR(255),
  id_edital INTEGER,
  PRIMARY KEY (id_fechamento)
);

CREATE TABLE funcoes (
  id_funcao INTEGER NOT NULL,
  descricao_funcao VARCHAR(255),
  PRIMARY KEY (id_funcao)
);

CREATE TABLE permissoes (
  id_permissao INTEGER NOT NULL,
  permissao VARCHAR(255),
  id_edital INTEGER,
  matricula INTEGER,
  PRIMARY KEY (id_permissao)
);

CREATE TABLE restricoes_candidaturas (
  id_restricao INTEGER NOT NULL,
  id_candidato INTEGER,
  id_unidade INTEGER,
  PRIMARY KEY (id_restricao)
);

CREATE TABLE sequence_table (
  seq_name VARCHAR(255) NOT NULL,
  seq_count BIGINT,
  PRIMARY KEY (seq_name)
);

CREATE TABLE servidores (
  matricula INTEGER NOT NULL,
  email_servidor VARCHAR(255),
  nome_completo VARCHAR(255),
  PRIMARY KEY (matricula)
);

CREATE TABLE snap_locais (
  id_linha INTEGER NOT NULL,
  id_fechamento INTEGER,
  id_local INTEGER,
  nome VARCHAR(255),
  sem_dono BIT,
  PRIMARY KEY (id_linha)
);

CREATE TABLE snap_pretensoes (
  id_pretensao INTEGER NOT NULL,
  cod_fechamento INTEGER,
  cod_local INTEGER,
  matricula INTEGER,
  ordem INTEGER,
  PRIMARY KEY (id_pretensao)
);

CREATE TABLE snap_resultados (
  id_resultado INTEGER NOT NULL,
  cod_fechamento INTEGER,
  cod_local INTEGER,
  matricula INTEGER,
  ordem_ganha INTEGER,
  PRIMARY KEY (id_resultado)
);

CREATE TABLE tipos_edital (
  id BIGINT NOT NULL,
  descricao VARCHAR(50),
  PRIMARY KEY (id)
);

CREATE TABLE unidades (
  id_unidade INTEGER NOT NULL,
  codigo VARCHAR(255),
  descricao VARCHAR(255),
  id_comarca INTEGER,
  PRIMARY KEY (id_unidade)
);

CREATE TABLE vagas_edital (
  id_vaga_edital INTEGER NOT NULL,
  tipo_vaga INTEGER,
  id_edital INTEGER,
  id_unidade INTEGER,
  ordem_vaga INTEGER,
  PRIMARY KEY (id_vaga_edital)
);

INSERT INTO sequence_table (seq_name, seq_count)
       VALUES ('arquivos', 0),
              ('avisos', 0),
              ('candidaturas', 0),
              ('snap_locais', 0),
              ('snap_pretensoes', 0),
              ('snap_resultados', 0);