/*create proc IngresarHistorial 
@id_historial int, 
@diagnostico varchar(60),
@problema varchar(60),
@receta varchar(60)
as	
	SET NOCOUNT ON
	INSERT INTO HISTORIAL VALUES
	(@id_historial, @diagnostico, @problema, @receta)*/



/*create proc IngresarHistorial  
@diagnostico varchar(60),
@problema varchar(60),
@receta varchar(60)
as	
	SET NOCOUNT ON
	INSERT INTO HISTORIAL VALUES
	(@diagnostico, @problema, @receta)
*/	
/*Alter table HISTORIAL alter column IDHISTORIAL int IDENTITY(1,1) primary key not null*/
	
	
