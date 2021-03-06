USE [MEDITICKET2]
GO
/****** Object:  StoredProcedure [dbo].[EliminarPaciente]    Script Date: 07/23/2017 20:27:44 ******/

create proc EliminarPaciente @cedula varchar(10)
as begin
delete from PACIENTE where CEDULAPACIENTE= @cedula
end
GO
/****** Object:  StoredProcedure [dbo].[RegUsuario]    Script Date: 07/23/2017 20:27:44 ******/

create proc RegUsuario (@cedula varchar(10),@nombre varchar(50),@apellido varchar(50),
@telefono varchar(10),@password varchar(50),@especialidad varchar (50),@direccion varchar(50))
as begin
insert into USUARIO values (@cedula,@nombre,@apellido,@telefono,@password,@especialidad,@direccion)
end

/****** Object:  StoredProcedure [dbo].[RegPaciente]    Script Date: 07/23/2017 20:27:44 ******/

create proc RegPaciente @cedula varchar(10),@nombre varchar(50),@apellido varchar(50),
@telefono varchar(10),@direccion varchar(50)
as begin
insert into PACIENTE values (@cedula,@nombre,@apellido,@telefono,@direccion)
end
/****** Object:  StoredProcedure [dbo].[EncontrarUsuario]    Script Date: 07/23/2017 20:27:44 ******/

create proc EncontrarUsuario @cedula varchar(10)
as begin
select * from USUARIO where CEDULAUSUARIO = @cedula
end

/****** Object:  StoredProcedure [dbo].[EncontrarPaciente]    Script Date: 07/23/2017 20:27:44 ******/

create proc EncontrarPaciente @cedula varchar(10)
as begin
select * from PACIENTE where CEDULAPACIENTE = @cedula
end
GO
/****** Object:  StoredProcedure [dbo].[EliminarUsuario]    Script Date: 07/23/2017 20:27:44 ******/

create proc EliminarUsuario @cedula varchar(10)
as begin
delete from USUARIO where CEDULAUSUARIO = @cedula

/****** Object:  StoredProcedure [dbo].[EntradaRolUsuario]    Script Date: 07/23/2017 20:27:44 ******/

create proc EntradaRolUsuario @idrol int,@cedulausuario varchar(10),@fechacreacion char(10)
as begin
insert into ROLUSUARIO values(@idrol,@cedulausuario,@fechacreacion)
end
GO
/****** Object:  StoredProcedure [dbo].[EliminarRolUsuario]    Script Date: 07/23/2017 20:27:44 ******/

create proc EliminarRolUsuario @cedula varchar(10)
as begin
delete from ROLUSUARIO where CEDULAUSUARIO = @cedula
end
GO
