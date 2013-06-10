/*********************
 * PRÉ PROCESSAMENTO *
 *********************/

DROP DATABASE datapet;
CREATE DATABASE datapet;
USE datapet;


/******************************
 * INSTITUIÇÃO, GRUPO E CURSO *
 ******************************/

CREATE TABLE instituicao(
  id int(11) NOT NULL AUTO_INCREMENT,	
  sigla varchar(13),
  nome varchar(80),
  pro_reitor varchar(80),
  email_pro_reitor varchar(254),
  PRIMARY KEY (id)
);

CREATE TABLE grupo(
  id int(11) AUTO_INCREMENT,
  id_instituicao int(11) NOT NULL,
  id_curso int(11),
  sigla varchar(40) NOT NULL,
  tipo int NOT NULL, /* 0: SESU; 1: Institucional; 2: SECAD */
  implantacao date,
  site varchar(100),
  tema varchar(255),
  telefone char(13),
  ativado boolean,
  PRIMARY KEY (id),
  UNIQUE (id_instituicao, sigla),
  FOREIGN KEY (id_instituicao) REFERENCES instituicao (id) ON DELETE CASCADE
);

CREATE TABLE grupo_historico(
  id int(11) AUTO_INCREMENT,
  id_grupo int(11),
  operacao int(1) NOT NULL, /* 0: Ativacao; 1: Desativacao */
  justificativa tinytext,
  data date,
  PRIMARY KEY (id),
  FOREIGN KEY (id_grupo) REFERENCES grupo (id) ON DELETE CASCADE
);

CREATE TABLE curso(
  id int(11) AUTO_INCREMENT,
  id_instituicao int(11),
  nome varchar(80),
  formacao int, /* 0: bacharelado; 1: licenciatura; 2: ambos */
  periodo int, /* 1: semestral; 2: anual */
  PRIMARY KEY (id),
  UNIQUE (id_instituicao, nome),
  FOREIGN KEY (id_instituicao) REFERENCES instituicao (id) ON DELETE CASCADE
);

/**************
 * ATIVIDADES *
 **************/

CREATE TABLE atividade (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_grupo int(11) NOT NULL,
  titulo varchar(200) NOT NULL,
  parceiros text,
  descricao text NOT NULL,
  justificativa text,
  datainicio date,
  datafim date,
  comentario text,
  resultadosesperados text,
  resultadosalcancados text,
  coletiva boolean,
  PRIMARY KEY (id),
  FOREIGN KEY (id_grupo) REFERENCES grupo (id) ON DELETE CASCADE
);

CREATE TABLE natureza (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_grupo int(11) ,
  nome varchar(80) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (id_grupo, nome),
  FOREIGN KEY (id_grupo) REFERENCES grupo (id) ON DELETE CASCADE
);

CREATE TABLE atividadepublica (
  id int(11) NOT NULL,
  publicoalvo tinytext,
  id_natureza int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_natureza) REFERENCES natureza (id) ON DELETE NO ACTION,
  FOREIGN KEY (id) REFERENCES atividade (id) ON DELETE CASCADE
);

CREATE TABLE ensino (
  id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES atividadepublica (id) ON DELETE CASCADE
);

CREATE TABLE extensao (
  id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES atividadepublica (id) ON DELETE CASCADE
);

CREATE TABLE pesquisa (
  id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES atividade (id) ON DELETE CASCADE
);


/***********
 * USUÁRIO *
 ***********/

CREATE TABLE usuario(
  id int(11) NOT NULL AUTO_INCREMENT,
  email varchar(254) NOT NULL,
  senha char(32),
  PRIMARY KEY (id),
  UNIQUE (email)
);
  

CREATE TABLE petiano(
  id int(11) NOT NULL AUTO_INCREMENT,
  id_grupo int(11),
  nome varchar(40),
  telefone char(13),
  celular char(13),
  dataIngressoPet date,
  dataSaidaPet date,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES usuario (id) ON DELETE CASCADE,
  FOREIGN KEY (id_grupo) REFERENCES grupo (id) ON DELETE CASCADE
);


CREATE TABLE aluno(
  id int(11) NOT NULL AUTO_INCREMENT,	
  bolsista boolean,
  periodo int,
  dataIngressoInst date,
  cra_geral float,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES petiano (id) ON DELETE CASCADE
);

CREATE TABLE nota(
  id int(11) NOT NULL AUTO_INCREMENT,
  id_aluno int(11),
  valor float,
  ano int(4),
  semestre int(1),
  PRIMARY KEY (id),
  UNIQUE (id_aluno, ano, semestre),
  FOREIGN KEY (id_aluno) REFERENCES aluno (id) ON DELETE CASCADE
);

CREATE TABLE tutor(
  id int(11) NOT NULL AUTO_INCREMENT,	
  titulacao varchar(20),
  area varchar(80),
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES petiano (id) ON DELETE CASCADE
);

CREATE TABLE cla(
  id int(11) NOT NULL AUTO_INCREMENT,
  id_instituicao int(11),	
  interlocutor varchar(40),
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES usuario (id) ON DELETE CASCADE,
  FOREIGN KEY (id_instituicao) REFERENCES instituicao (id) ON DELETE CASCADE
);

CREATE TABLE usuario_cla(
	id_usuario int(11) NOT NULL,
	id_cla int(11) NOT NULL,
	nome varchar(50),
	data_ingresso_cla date,
  data_saida_cla date,
	KEY (id_usuario, id_cla),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
	FOREIGN KEY (id_cla) REFERENCES cla(id) ON DELETE CASCADE
);

CREATE TABLE administrador(
  id int(11),
  FOREIGN KEY (id) REFERENCES usuario (id) ON DELETE CASCADE
);


/**************************
 * DEMAIS FUNCIONALIDADES *
 **************************/

CREATE TABLE anotacao(
  id int(11) NOT NULL AUTO_INCREMENT,
  id_aluno int(11) NOT NULL,
  data date NOT NULL,
  carater boolean,
  texto tinytext NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_aluno) REFERENCES aluno (id) ON DELETE CASCADE
);

CREATE TABLE atividade_aluno(
  id_atividade int(11) NOT NULL,
  id_aluno int(11) NOT NULL,
  primary key (id_atividade, id_aluno),
  FOREIGN KEY (id_atividade) REFERENCES atividade (id) ON DELETE CASCADE,
  FOREIGN KEY (id_aluno) REFERENCES aluno (id) ON DELETE CASCADE
);

/* A tabela abaixo grava os tickets criados para a recuperação de
 * senhas
 */
CREATE TABLE ticket(
  id int AUTO_INCREMENT,
  id_usuario int(11) NOT NULL,
  codigo char(32),
  horario datetime,
  PRIMARY KEY (id),
  UNIQUE(codigo),
  UNIQUE(id_usuario, horario),
  FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE
); 
  


/*****************************
 * PREENCHIMENTO PARA TESTES *
 *****************************/

insert into usuario values(NULL, 'adm@ufu.br', MD5('admin'));
insert into administrador values(1);

insert into instituicao values (NULL, 'UFU', 'Universidade Federal de Uberlândia', 'Pró-reitor da UFU', 'E-mail do pró-reitor da UFU');

insert into curso values (NULL, 1, 'Ciência da Computação', 0, 1);
insert into grupo values (NULL, 1, 1, 'CompPET', 0, '1998-03-01', 'www.comppet.ufu.br', NULL, NULL, true);

insert into curso values (NULL, 1, 'Engenharia Mecânica', 0, 1);
insert into grupo values (NULL, 1, 2, 'PETMEC', 0, '1998-03-01', 'www.comppet.ufu.br', NULL, NULL, true);

insert into usuario values(null, 'cla@ufu.cr', MD5('cla'));
insert into cla values(2, 1, 'abc');
insert into usuario_cla values(2, 2, 'abc', NULL, NULL);

insert into usuario values(null, 'autran@ufu.cr', MD5('autran'));
insert into petiano values(3, 1, 'Autran Macêdo', NULL, NULL, '2009-01-01', null);
insert into tutor values(3, 'doutor', 'computacao');

insert into usuario values(null, 'abigailson@ufu.cl', MD5('abigailson'));
insert into petiano values(4, 2, 'Abigailson Junior', NULL, NULL, '2009-01-01', null);
insert into tutor values(4, 'doutor', 'computacao');

insert into natureza values(1, null, 'Outra');
