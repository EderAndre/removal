INSERT INTO tipos_edital (id, descricao) VALUES (1, 'defensores');

INSERT INTO editais (id_edital, abertura_edital, encerramento_edital, tipo_edital, email_resposta_edital, bloqueado, apenas_desistencias_permitidas) VALUES 
(101, '2015-07-31 18:00:00', '2030-07-20 18:00:00', 1, 'edital101-resposta@defensoria.rs.gov.br', false, NULL),
(202, '2015-07-31 18:00:00', '2030-07-20 18:00:00', 1, 'edital202-resposta@defensoria.rs.gov.br', false, NULL);

INSERT INTO funcoes (id_funcao, descricao_funcao) VALUES (99, 'Funcao Teste');

INSERT INTO servidores (matricula, email_servidor, nome_completo) VALUES 
(12345, 'email@test.com', 'Servidor de Teste'),
(45678, 'email@test.com', 'Servidor de Teste 2');

INSERT INTO candidatos (id_candidato, antiguidade, id_edital, id_funcao, id_lotacao, matricula) VALUES 
(1, 10, 101, 99, 7, 12345),
(2, 7, 202, 99, 7, 45678);

INSERT INTO vagas_edital (id_vaga_edital, tipo_vaga, id_edital, id_unidade, ordem_vaga) VALUES 
(4, 1, 101, 4, 0),
(7, 0, 101, 7, 0),
(8, 1, 101, 8, 0),
(12, 1, 101, 12, 0),
(27, 1, 101, 27, 0);

INSERT INTO restricoes_candidaturas (id_restricao, id_candidato, id_unidade) VALUES
(1, 1, 12);