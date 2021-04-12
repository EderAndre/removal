INSERT INTO tipos_edital (id)
       VALUES (4);

INSERT INTO editais (id_edital, abertura_edital, encerramento_edital, tipo_edital, bloqueado)
       VALUES (13, '1999-11-20', '1999-11-30', 4, 0),
              (14, '1999-11-19', '1999-11-29', 4, 0),
              (15, '1999-11-05', '1999-11-15', 4, 0),
              (16, '1999-11-16', '1999-11-26', 4, 0);

INSERT INTO fechamentos (id_fechamento, data_fechamento, id_edital)
       VALUES (87, '1999-11-21', 13),
              (88, '1999-11-20', 14),
              (89, '1999-11-06', 15),
              (90, '1999-11-17', 16);