USE [MEDITICKET2]
GO
/****** Object:  Table [dbo].[ROL]    Script Date: 07/02/2017 19:11:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ROL](
	[IDROL] [int] NOT NULL,
	[NOMBREROL] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ROL] PRIMARY KEY NONCLUSTERED 
(
	[IDROL] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PACIENTE]    Script Date: 07/02/2017 19:11:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PACIENTE](
	[CEDULAPACIENTE] [varchar](10) NOT NULL,
	[NOMBRE] [varchar](50) NOT NULL,
	[APELLIDO] [varchar](50) NOT NULL,
	[TELEFONO] [varchar](10) NOT NULL,
	[DIRECCION] [varchar](50) NOT NULL,
 CONSTRAINT [PK_PACIENTE] PRIMARY KEY NONCLUSTERED 
(
	[CEDULAPACIENTE] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[USUARIO]    Script Date: 07/02/2017 19:11:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[USUARIO](
	[CEDULAUSUARIO] [varchar](10) NOT NULL,
	[NOMBRE] [varchar](50) NOT NULL,
	[APELLIDO] [varchar](50) NOT NULL,
	[TELEFONO] [varchar](10) NOT NULL,
	[PASSWORD] [varchar](50) NOT NULL,
	[ESPECIALIDAD] [varchar](50) NOT NULL,
	[DIRECCION] [varchar](50) NOT NULL,
 CONSTRAINT [PK_USUARIO] PRIMARY KEY NONCLUSTERED 
(
	[CEDULAUSUARIO] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[AREAS]    Script Date: 07/02/2017 19:11:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AREAS](
	[IDAREA] [int] NOT NULL,
	[NOMBRE] [varchar](50) NOT NULL,
	[UBICACION] [varchar](50) NOT NULL,
 CONSTRAINT [PK_AREAS] PRIMARY KEY NONCLUSTERED 
(
	[IDAREA] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[EliminarPaciente]    Script Date: 07/02/2017 19:11:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[EliminarPaciente] (@cedula varchar(10))
as begin
delete from PACIENTE where CEDULAPACIENTE= @cedula
end
GO
/****** Object:  Table [dbo].[TURNO]    Script Date: 07/02/2017 19:11:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TURNO](
	[IDTURNO] [int] NOT NULL,
	[IDAREA] [int] NOT NULL,
	[CEDULAPACIENTE] [varchar](10) NOT NULL,
	[CEDULAUSUARIO] [varchar](10) NULL,
	[FECHAASIGNACION] [datetime] NOT NULL,
 CONSTRAINT [PK_TURNO] PRIMARY KEY NONCLUSTERED 
(
	[IDTURNO] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ROLUSUARIO]    Script Date: 07/02/2017 19:11:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ROLUSUARIO](
	[IDROL] [int] NOT NULL,
	[CEDULAUSUARIO] [varchar](10) NOT NULL,
	[FECHACREACION] [char](10) NOT NULL,
 CONSTRAINT [PK_ROLUSUARIO] PRIMARY KEY NONCLUSTERED 
(
	[IDROL] ASC,
	[CEDULAUSUARIO] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[HISTORIAL]    Script Date: 07/02/2017 19:11:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[HISTORIAL](
	[IDHISTORIAL] [int] NOT NULL,
	[CEDULAPACIENTE] [varchar](10) NOT NULL,
	[DIAGNOSTICO] [varchar](60) NOT NULL,
	[PROBLEMA] [varchar](60) NOT NULL,
	[RECETA] [varchar](60) NOT NULL,
 CONSTRAINT [PK_HISTORIAL] PRIMARY KEY NONCLUSTERED 
(
	[IDHISTORIAL] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[RegUsuario]    Script Date: 07/02/2017 19:11:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[RegUsuario] (@cedula varchar(10),@nombre varchar(50),@apellido varchar(50),
@telefono varchar(10),@password varchar(50),@especialidad varchar (50),@direccion varchar(50))
as begin
insert into USUARIO values (@cedula,@nombre,@apellido,@telefono,@password,@especialidad,@direccion)
end
GO
/****** Object:  StoredProcedure [dbo].[RegPaciente]    Script Date: 07/02/2017 19:11:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[RegPaciente] (@cedula varchar(10),@nombre varchar(50),@apellido varchar(50),
@telefono varchar(10),@direccion varchar(50))
as begin
insert into PACIENTE values (@cedula,@nombre,@apellido,@telefono,@direccion)
end
GO
/****** Object:  StoredProcedure [dbo].[EncontrarUsuario]    Script Date: 07/02/2017 19:11:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[EncontrarUsuario] (@cedula varchar(10))
as begin
select * from USUARIO where CEDULAUSUARIO = @cedula
end
GO
/****** Object:  StoredProcedure [dbo].[EncontrarPaciente]    Script Date: 07/02/2017 19:11:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[EncontrarPaciente] (@cedula varchar(10))
as begin
select * from PACIENTE where CEDULAPACIENTE = @cedula
end
GO
/****** Object:  StoredProcedure [dbo].[EliminarUsuario]    Script Date: 07/02/2017 19:11:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[EliminarUsuario] (@cedula varchar(10))
as begin
delete from USUARIO where CEDULAUSUARIO = @cedula
end
GO
/****** Object:  StoredProcedure [dbo].[EliminarRolUsuario]    Script Date: 07/02/2017 19:11:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[EliminarRolUsuario] (@cedula varchar(10))
as begin
delete from ROLUSUARIO where CEDULAUSUARIO = @cedula
end
GO
/****** Object:  StoredProcedure [dbo].[EntradaRolUsuario]    Script Date: 07/02/2017 19:11:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[EntradaRolUsuario] (@idrol int,@cedulausuario varchar(10),@fechacreacion char(10))
as begin
insert into ROLUSUARIO values(@idrol,@cedulausuario,@fechacreacion)
end
GO
/****** Object:  ForeignKey [FK_HISTORIAL_TIENE_PACIENTE]    Script Date: 07/02/2017 19:11:30 ******/
ALTER TABLE [dbo].[HISTORIAL]  WITH CHECK ADD  CONSTRAINT [FK_HISTORIAL_TIENE_PACIENTE] FOREIGN KEY([CEDULAPACIENTE])
REFERENCES [dbo].[PACIENTE] ([CEDULAPACIENTE])
GO
ALTER TABLE [dbo].[HISTORIAL] CHECK CONSTRAINT [FK_HISTORIAL_TIENE_PACIENTE]
GO
/****** Object:  ForeignKey [FK_ROLUSUAR_CONLLEVA_USUARIO]    Script Date: 07/02/2017 19:11:30 ******/
ALTER TABLE [dbo].[ROLUSUARIO]  WITH CHECK ADD  CONSTRAINT [FK_ROLUSUAR_CONLLEVA_USUARIO] FOREIGN KEY([CEDULAUSUARIO])
REFERENCES [dbo].[USUARIO] ([CEDULAUSUARIO])
GO
ALTER TABLE [dbo].[ROLUSUARIO] CHECK CONSTRAINT [FK_ROLUSUAR_CONLLEVA_USUARIO]
GO
/****** Object:  ForeignKey [FK_ROLUSUAR_CONLLEVA2_ROL]    Script Date: 07/02/2017 19:11:30 ******/
ALTER TABLE [dbo].[ROLUSUARIO]  WITH CHECK ADD  CONSTRAINT [FK_ROLUSUAR_CONLLEVA2_ROL] FOREIGN KEY([IDROL])
REFERENCES [dbo].[ROL] ([IDROL])
GO
ALTER TABLE [dbo].[ROLUSUARIO] CHECK CONSTRAINT [FK_ROLUSUAR_CONLLEVA2_ROL]
GO
/****** Object:  ForeignKey [FK_TURNO_ASIGNA_USUARIO]    Script Date: 07/02/2017 19:11:30 ******/
ALTER TABLE [dbo].[TURNO]  WITH CHECK ADD  CONSTRAINT [FK_TURNO_ASIGNA_USUARIO] FOREIGN KEY([CEDULAUSUARIO])
REFERENCES [dbo].[USUARIO] ([CEDULAUSUARIO])
GO
ALTER TABLE [dbo].[TURNO] CHECK CONSTRAINT [FK_TURNO_ASIGNA_USUARIO]
GO
/****** Object:  ForeignKey [FK_TURNO_CONTIENE_AREAS]    Script Date: 07/02/2017 19:11:30 ******/
ALTER TABLE [dbo].[TURNO]  WITH CHECK ADD  CONSTRAINT [FK_TURNO_CONTIENE_AREAS] FOREIGN KEY([IDAREA])
REFERENCES [dbo].[AREAS] ([IDAREA])
GO
ALTER TABLE [dbo].[TURNO] CHECK CONSTRAINT [FK_TURNO_CONTIENE_AREAS]
GO
/****** Object:  ForeignKey [FK_TURNO_REALIZA_PACIENTE]    Script Date: 07/02/2017 19:11:30 ******/
ALTER TABLE [dbo].[TURNO]  WITH CHECK ADD  CONSTRAINT [FK_TURNO_REALIZA_PACIENTE] FOREIGN KEY([CEDULAPACIENTE])
REFERENCES [dbo].[PACIENTE] ([CEDULAPACIENTE])
GO
ALTER TABLE [dbo].[TURNO] CHECK CONSTRAINT [FK_TURNO_REALIZA_PACIENTE]
GO
