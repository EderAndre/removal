insert into fechamentos (id_fechamento, data_fechamento, id_edital) values
(	27	, '	2015-09-10 15:40:00.0000000	', 	100	),
(	28	, '	2015-09-10 18:00:00.0000000	', 	100	),
(	29	, '	2015-09-14 16:00:00.0000000	', 	100	),
(	30	, '	2015-09-14 16:45:00.0000000	', 	100	),
(	31	, '	2015-09-14 16:49:00.0000000	', 	100	);

--SELECT '('
--      ,[id_fechamento]
--      ,', '''
--      ,[data_fechamento]
--      ,''', '
--      ,[id_edital]
--      ,'),'
--  FROM [db_removal_service-testegeral].[dbo].[fechamentos]
-- where id_edital = 100