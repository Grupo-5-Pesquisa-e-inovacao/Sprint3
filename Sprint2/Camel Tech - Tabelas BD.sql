CREATE DATABASE camelTech;
USE camelTech;

-- TABELA PROVEDORA
CREATE TABLE provedora (
  idProvedora INT PRIMARY KEY AUTO_INCREMENT,
  nomeFantasia VARCHAR(45) NULL,
  razaoSocial VARCHAR(45) NULL,
  email VARCHAR(45) NULL,
  senha VARCHAR(45) NULL,
  CNPJ VARCHAR(14) NULL
);

INSERT INTO provedora (nomeFantasia, razaoSocial, email, senha, CNPJ)
VALUES ('Nome Fantasia 1', 'Razão Social 1', 'email1@example.com', 'senha123', '123456789');

INSERT INTO provedora (nomeFantasia, razaoSocial, email, senha, CNPJ)
VALUES ('Nome Fantasia 2', 'Razão Social 2', 'email2@example.com', 'senha456', '987654321');

-- TABELA unidadeProvedora
CREATE TABLE unidadeProvedora (
  idunidadeProvedora INT PRIMARY KEY AUTO_INCREMENT,
  nomeUsuario VARCHAR(45) NULL,
  cep CHAR(8) NULL,
  rua VARCHAR(45) NULL,
  complemento VARCHAR(45) NULL,
  numero VARCHAR(45) NULL,
  senha VARCHAR(45) NULL,
  fkProvedora INT NOT NULL,
  CONSTRAINT fkProv FOREIGN KEY (fkProvedora) REFERENCES provedora (idProvedora)
);

INSERT INTO unidadeProvedora (nomeUsuario, cep, rua, complemento, numero, senha, fkProvedora)
VALUES ('Usuario1', '12345678', 'Rua 1', 'Complemento 1', '123', 'senha123', 1);

INSERT INTO unidadeProvedora (nomeUsuario, cep, rua, complemento, numero, senha, fkProvedora)
VALUES ('Usuario2', '87654321', 'Rua 2', 'Complemento 2', '456', 'senha456', 2);

-- TABELA servidor
CREATE TABLE servidor (
  idServidor INT PRIMARY KEY AUTO_INCREMENT,
  Nome VARCHAR(45) NULL,
  fkUnidade INT NOT NULL,
  CONSTRAINT fkUnid FOREIGN KEY (fkUnidade) REFERENCES unidadeProvedora (idunidadeProvedora)
);

INSERT INTO servidor (Nome, fkUnidade)
VALUES ('Servidor 1', 1);

INSERT INTO servidor (Nome, fkUnidade)
VALUES ('Servidor 2', 2);

SELECT * FROM servidor;

-- TABELA dadosHardware
CREATE TABLE dadosHardware (
  idDadosHardware INT AUTO_INCREMENT PRIMARY KEY,
  nomeProcessador VARCHAR(255),
  usoDisco DECIMAL(10, 2),
  tamanhoDisco DECIMAL(10, 2),
  nomeDisco VARCHAR(255),
  rede DECIMAL(10, 2),
  qtdEmUso DOUBLE,
  frequencia BIGINT,
  totalRam DECIMAL(17, 15),
  emUsoRam DECIMAL(17, 15),
  hostName VARCHAR(255),
  numIpv4 VARCHAR(255),
  bytesRecebidos BIGINT,
  bytesEnviados BIGINT
);

-- Inserir um registro com valores para todas as colunas
INSERT INTO dadosHardware (nomeProcessador, usoDisco, tamanhoDisco, nomeDisco, rede, qtdEmUso, frequencia, totalRam, emUsoRam, hostName, numIpv4, bytesRecebidos, bytesEnviados)
VALUES ('Intel Core i7', 30.50, 500.00, 'Disco 1', 100.25, 3.5, 3200, 16.00, 6.50, 'Server1', '192.168.1.1', 1000, 2000);

-- Inserir um registro com valores nulos para algumas colunas
INSERT INTO dadosHardware (nomeProcessador, rede, qtdEmUso, frequencia, numIpv4)
VALUES ('AMD Ryzen 5', 200.75, 4.0, 3600, '192.168.1.2');

-- Inserir outro registro com valores diferentes
INSERT INTO dadosHardware (nomeProcessador, usoDisco, tamanhoDisco, nomeDisco, rede, qtdEmUso, frequencia, totalRam, emUsoRam, hostName, numIpv4, bytesRecebidos, bytesEnviados)
VALUES ('Intel Core i5', 15.75, 256.00, 'Disco 2', 50.00, 2.8, 2800, 8.00, 4.25, 'Server2', '192.168.1.3', 1500, 2500);

SELECT * FROM dadosHardware;

-- TABELA captura
CREATE TABLE captura (
  idCaptura INT PRIMARY KEY AUTO_INCREMENT,
  dataHora DATETIME NULL,
  fkServidor INT NOT NULL,
  fkDados INT NOT NULL,
  CONSTRAINT fkServ FOREIGN KEY (fkServidor) REFERENCES servidor (idServidor),
  CONSTRAINT fkDadosHard FOREIGN KEY (fkDados) REFERENCES dadosHardware (idDadosHardware)
);

INSERT INTO captura (dataHora, fkServidor, fkDados)
VALUES (NOW(), 1, 1);

INSERT INTO captura (dataHora, fkServidor, fkDados)
VALUES (NOW(), 2, 2);

SELECT * FROM captura;

-- TABELA alertas
CREATE TABLE alertas (
  idAlerta INT PRIMARY KEY AUTO_INCREMENT,
  tipoAlerta VARCHAR(45) NULL,
  descricao VARCHAR(45) NULL,
  dataHora DATETIME NULL,
  severidade VARCHAR(45) NULL,
  fkServidor INT NOT NULL,
  CONSTRAINT fkServAlert FOREIGN KEY (fkServidor) REFERENCES servidor (idServidor)
);

INSERT INTO alertas (tipoAlerta, descricao, dataHora, severidade, fkServidor)
VALUES ('Erro', 'Erro crítico no servidor 1', NOW(), 'Alto', 1);

INSERT INTO alertas (tipoAlerta, descricao, dataHora, severidade, fkServidor)
VALUES ('Aviso', 'Aviso de baixo espaço em disco no servidor 2', NOW(), 'Médio', 2);

SELECT * FROM alertas;

-- SELECTS de teste

-- Select para mostrar dados do disco de um servidor específico
SELECT dh.usoDisco, dh.tamanhoDisco
FROM dadosHardware dh
INNER JOIN captura c ON dh.idDadosHardware = c.fkDados
INNER JOIN servidor s ON c.fkServidor = s.idServidor
WHERE s.idServidor = 1;

-- Select todos os dados do servidor1
SELECT dh.*
FROM dadosHardware dh
INNER JOIN captura c ON dh.idDadosHardware = c.fkDados
INNER JOIN servidor s ON c.fkServidor = s.idServidor
WHERE s.idServidor = 1;
