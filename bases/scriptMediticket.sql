/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     23/07/2017 22:11:11                          */
/*==============================================================*/


go

/*==============================================================*/
/* Table: AREAS                                                 */
/*==============================================================*/
create table AREAS (
   IDAREA               int                  not null,
   NOMBRE               varchar(50)          not null,
   HORARIO              varchar(50)          not null,
   constraint PK_AREAS primary key nonclustered (IDAREA)
)
go

/*==============================================================*/
/* Table: HISTORIAL                                             */
/*==============================================================*/
create table HISTORIAL (
   IDHISTORIAL          int                  not null,
   DIAGNOSTICO          varchar(60)          not null,
   PROBLEMA             varchar(60)          not null,
   RECETA               varchar(60)          not null,
   constraint PK_HISTORIAL primary key nonclustered (IDHISTORIAL)
)
go

/*==============================================================*/
/* Table: HORARIO                                               */
/*==============================================================*/
create table HORARIO (
   ID_HORARIO           int                  not null,
   HORARIO_USUARIO      datetime             not null,
   constraint PK_HORARIO primary key nonclustered (ID_HORARIO)
)
go

/*==============================================================*/
/* Table: HORARIO_USUARIO                                       */
/*==============================================================*/
create table HORARIO_USUARIO (
   CEDULAUSUARIO        varchar(10)          not null,
   ID_HORARIO           int                  not null,
   constraint PK_HORARIO_USUARIO primary key nonclustered (CEDULAUSUARIO, ID_HORARIO)
)
go

/*==============================================================*/
/* Index: RELATIONSHIP_8_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_8_FK on HORARIO_USUARIO (
ID_HORARIO ASC
)
go

/*==============================================================*/
/* Index: RELATIONSHIP_9_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_9_FK on HORARIO_USUARIO (
CEDULAUSUARIO ASC
)
go

/*==============================================================*/
/* Table: PACIENTE                                              */
/*==============================================================*/
create table PACIENTE (
   CEDULAPACIENTE       varchar(10)          not null,
   IDHISTORIAL          int                  not null,
   NOMBRE               varchar(50)          not null,
   APELLIDO             varchar(50)          not null,
   TELEFONO             varchar(10)          not null,
   DIRECCION            varchar(50)          not null,
   ESTADO               varchar(2)           not null,
   constraint PK_PACIENTE primary key nonclustered (CEDULAPACIENTE)
)
go

/*==============================================================*/
/* Index: TIENE_FK                                              */
/*==============================================================*/
create index TIENE_FK on PACIENTE (
IDHISTORIAL ASC
)
go

/*==============================================================*/
/* Table: ROL                                                   */
/*==============================================================*/
create table ROL (
   IDROL                int                  not null,
   NOMBREROL            varchar(50)          not null,
   constraint PK_ROL primary key nonclustered (IDROL)
)
go

/*==============================================================*/
/* Table: ROLUSUARIO                                            */
/*==============================================================*/
create table ROLUSUARIO (
   IDROL                int                  not null,
   CEDULAUSUARIO        varchar(10)          not null,
   FECHACREACION        char(10)             not null,
   constraint PK_ROLUSUARIO primary key nonclustered (IDROL, CEDULAUSUARIO)
)
go

/*==============================================================*/
/* Index: CONLLEVA_FK                                           */
/*==============================================================*/
create index CONLLEVA_FK on ROLUSUARIO (
CEDULAUSUARIO ASC
)
go

/*==============================================================*/
/* Index: CONLLEVA2_FK                                          */
/*==============================================================*/
create index CONLLEVA2_FK on ROLUSUARIO (
IDROL ASC
)
go

/*==============================================================*/
/* Table: TURNO                                                 */
/*==============================================================*/
create table TURNO (
   IDTURNO              int                  not null,
   IDAREA               int                  not null,
   CEDULAPACIENTE       varchar(10)          not null,
   CEDULAUSUARIO        varchar(10)          null,
   FECHAASIGNACION      datetime             not null,
   constraint PK_TURNO primary key nonclustered (IDTURNO)
)
go

/*==============================================================*/
/* Index: CONTIENE_FK                                           */
/*==============================================================*/
create index CONTIENE_FK on TURNO (
IDAREA ASC
)
go

/*==============================================================*/
/* Index: REALIZA_FK                                            */
/*==============================================================*/
create index REALIZA_FK on TURNO (
CEDULAPACIENTE ASC
)
go

/*==============================================================*/
/* Index: ASIGNA_FK                                             */
/*==============================================================*/
create index ASIGNA_FK on TURNO (
CEDULAUSUARIO ASC
)
go

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO (
   CEDULAUSUARIO        varchar(10)          not null,
   NOMBRE               varchar(50)          not null,
   APELLIDO             varchar(50)          not null,
   PASSWORD             varchar(50)          not null,
   DIRECCION            varchar(50)          not null,
   TELEFONO             varchar(50)          not null,
   ESTADO               varchar(2)           not null,
   constraint PK_USUARIO primary key nonclustered (CEDULAUSUARIO)
)
go

alter table HORARIO_USUARIO
   add constraint FK_HORARIO__RELATIONS_HORARIO foreign key (ID_HORARIO)
      references HORARIO (ID_HORARIO)
go

alter table HORARIO_USUARIO
   add constraint FK_HORARIO__RELATIONS_USUARIO foreign key (CEDULAUSUARIO)
      references USUARIO (CEDULAUSUARIO)
go

alter table PACIENTE
   add constraint FK_PACIENTE_TIENE_HISTORIA foreign key (IDHISTORIAL)
      references HISTORIAL (IDHISTORIAL)
go

alter table ROLUSUARIO
   add constraint FK_ROLUSUAR_CONLLEVA_USUARIO foreign key (CEDULAUSUARIO)
      references USUARIO (CEDULAUSUARIO)
go

alter table ROLUSUARIO
   add constraint FK_ROLUSUAR_CONLLEVA2_ROL foreign key (IDROL)
      references ROL (IDROL)
go

alter table TURNO
   add constraint FK_TURNO_ASIGNA_USUARIO foreign key (CEDULAUSUARIO)
      references USUARIO (CEDULAUSUARIO)
go

alter table TURNO
   add constraint FK_TURNO_CONTIENE_AREAS foreign key (IDAREA)
      references AREAS (IDAREA)
go

alter table TURNO
   add constraint FK_TURNO_REALIZA_PACIENTE foreign key (CEDULAPACIENTE)
      references PACIENTE (CEDULAPACIENTE)
go

