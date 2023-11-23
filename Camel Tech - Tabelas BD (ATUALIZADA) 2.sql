CREATE DATABASE camelTech;
USE camelTech;

-- TABELA provedora
CREATE TABLE provedora (
  idProvedora INT PRIMARY KEY auto_increment,
  razaoSocial VARCHAR(45),
  cnpj VARCHAR(14));
  
  INSERT INTO provedora (razaoSocial, cnpj) 
VALUES
('Camel Tech Cloud Solutions Ltda.', '12345678901234'),
('Tech Data Serviços de Tecnologia Ltda.', '56789012345678'),
('Data Center Solutions Ltda.', '90123456781234');
  
CREATE TABLE representanteProvedora(
idRepresentanteProvedora int primary key auto_increment,
nome VARCHAR(45),
dtNasc DATE,
cpf VARCHAR(16),
email VARCHAR(50),
senha VARCHAR(45),
fkProvedora int,
constraint fkProvRepresentante foreign key(fkProvedora) references
 provedora(idProvedora)
);

INSERT INTO representanteProvedora (nome, dtNasc, cpf, email, senha, fkProvedora)
VALUES
('Ana Silva', '1980-05-15', '12345678901', 'ana.silva@email.com', 'senha123', 1),
('Carlos Oliveira', '1985-08-22', '23456789012', 'carlos.oliveira@email.com', 'senha456', 2),
('Erika Santos', '1990-03-10', '34567890123', 'erika.santos@email.com', 'senha789', 3);

-- TABELA unidadeProvedora
CREATE TABLE unidadeProvedora (
  idunidadeProvedora INT PRIMARY KEY auto_increment,
  nomeUnidade VARCHAR(45),
  cep CHAR(8),
  rua VARCHAR(45),
  complemento VARCHAR(45),
  numero VARCHAR(45),
  fkProvedora INT NOT NULL,
  constraint fkunidProv foreign key(fkProvedora) references provedora(idProvedora));
  
  INSERT INTO unidadeProvedora (nomeUnidade, cep, rua, complemento, numero, fkProvedora)
VALUES
('Unidade São Paulo', '01234567', 'Av. Paulista', 'Andar 10', '123', 1),
('Unidade Rio de Janeiro', '23456789', 'Rua Copacabana', 'Sala 301', '456', 2),
('Unidade Belo Horizonte', '34567890', 'Av. Contorno', 'Bloco B', '789', 3);

CREATE TABLE representanteUnidade(
idRepresentanteUnidade int primary key auto_increment,
nome VARCHAR(45),
dtNasc DATE,
cpf VARCHAR(16),
email VARCHAR(50),
senha VARCHAR(45),
fkUnidade int,
constraint fkUnidRepresentante foreign key(fkUnidade) references
 unidadeProvedora(idUnidadeProvedora)
);

INSERT INTO representanteUnidade (nome, dtNasc, cpf, email, senha, fkUnidade)
VALUES
('Pedro Rodrigues', '1982-11-25', '12345678901', 'pedro.rodrigues@email.com', 'senha123', 1),
('Mariana Costa', '1987-04-18', '23456789012', 'mariana.costa@email.com', 'senha456', 2),
('Lucas Oliveira', '1992-09-03', '34567890123', 'lucas.oliveira@email.com', 'senha789', 3);

-- TABELA servidor
CREATE TABLE servidor (
  idServidor INT PRIMARY KEY AUTO_INCREMENT,
  nomeResponsavel VARCHAR(45),
  numeroRegistro VARCHAR(45),
  frequenciaIdealProcessador VARCHAR(10),
  capacidadeRam VARCHAR(10),
  maxUsoRam VARCHAR(10),
  capacidadeDisco VARCHAR(10),
  maxUsoDisco VARCHAR(10),
  velocidaDeRede VARCHAR(10),
  fkUnidade INT NOT NULL,
  CONSTRAINT fkUnidServ FOREIGN KEY (fkUnidade) REFERENCES unidadeProvedora(idUnidadeProvedora) ON DELETE CASCADE
);

-- Insert 1
INSERT INTO servidor (
  nomeResponsavel,
  numeroRegistro,
  frequenciaIdealProcessador,
  capacidadeRam,
  maxUsoRam,
  capacidadeDisco,
  maxUsoDisco,
  velocidaDeRede,
  fkUnidade
) VALUES (
  'João Silva',
  'SRV001',
  '3.5 GHz',
  '32GB',
  '80%',
  '1TB',
  '60%',
  '1 Gbps',
  1
);

-- Insert 2
INSERT INTO servidor (
  nomeResponsavel,
  numeroRegistro,
  frequenciaIdealProcessador,
  capacidadeRam,
  maxUsoRam,
  capacidadeDisco,
  maxUsoDisco,
  velocidaDeRede,
  fkUnidade
) VALUES (
  'Maria Oliveira',
  'SRV002',
  '2.8 GHz',
  '16GB',
  '70%',
  '500GB',
  '50%',
  '100 Mbps',
  2
);

-- Insert 3
INSERT INTO servidor (
  nomeResponsavel,
  numeroRegistro,
  frequenciaIdealProcessador,
  capacidadeRam,
  maxUsoRam,
  capacidadeDisco,
  maxUsoDisco,
  velocidaDeRede,
  fkUnidade
) VALUES (
  'Carlos Santos',
  'SRV003',
  '3.0 GHz',
  '64GB',
  '90%',
  '2TB',
  '75%',
  '10 Gbps',
  3
);



-- TABELA tipoComponente
CREATE TABLE tipoComponente (
  idtipoComponente INT PRIMARY KEY auto_increment,
  tipo VARCHAR(45));
  INSERT INTO tipoComponente (tipo) VALUES
('RAM'),
('Disco'),
('Frequência'),
('Rede');

-- TABELA tipoDado
CREATE TABLE tipoDado (
  idtipoDado INT PRIMARY KEY auto_increment,
  tipoDado VARCHAR(45));
  INSERT INTO tipoDado (tipoDado) VALUES
('Uso de RAM'),
('Uso de Disco'),
('Frequência do Processador'),
('Velocidade de Rede');


-- TABELA configuracao
CREATE TABLE configuracao (
  idConfiguracao INT auto_increment,
  fkServidor INT NOT NULL,
  fktipoComponente INT NOT NULL,
  PRIMARY KEY (idConfiguracao, fkServidor, fktipoComponente) ,
  FOREIGN KEY (fkServidor) REFERENCES servidor(idServidor) ON DELETE CASCADE,
  FOREIGN KEY (fktipoComponente) REFERENCES tipoComponente(idtipoComponente)
);


INSERT INTO configuracao values
(1,1,1), -- RAM
(2,1,2), -- DISCO
(3,1,3), -- Frequência
(4,1,4), -- REDE

(5,2,1), -- RAM
(6,2,2), -- DISCO
(7,2,3), -- Frequência
(8,2,4), -- REDE

(9,3,1), -- RAM 
(10,3,2), -- DISCO
(11,3,3), -- Frequência
(12,3,4); -- REDE



  select * from configuracao;

-- TABELA dadosCapturados
CREATE TABLE dadosCapturados (
  iddadosCapturados INT PRIMARY KEY auto_increment,
  dadoCapturado Float,
  dtHora DATETIME,
  fkConfiguracao INT,
  constraint fkConfigCap foreign key(fkConfiguracao) references configuracao(idConfiguracao) ON DELETE CASCADE,
  fkTipoDado INT,
  constraint fkTipoDadoCap foreign key(fkTipoDado) references tipoDado(idTipoDado));
  
select * from dadosCapturados;

truncate table dadoscapturados;





  -- SELECTS 
  
    SELECT c.idConfiguracao
FROM configuracao c
JOIN servidor s ON c.fkServidor = s.idServidor
WHERE c.fktipoComponente = 2;


SELECT dc.iddadosCapturados,
       dc.dadoCapturado,
       dc.dtHora,
       dc.fkConfiguracao,
       tc.tipoDado
FROM dadosCapturados dc
JOIN tipoDado tc ON dc.fkTipoDado = tc.idTipoDado;

  
-- SELECIONAR TODOS OS DADOS CAPTURADOS DE UM SERVIDOR 
SELECT 
    servidor.idServidor AS IDServidor,
    tipoDado.tipoDado AS TipoDadoCapturado,
    dadosCapturados.dadoCapturado AS ValorCapturado
FROM dadosCapturados
INNER JOIN configuracao ON dadosCapturados.fkConfiguracao = configuracao.idConfiguracao
INNER JOIN tipoDado ON dadosCapturados.fkTipoDado = tipoDado.idtipoDado
INNER JOIN tipoComponente ON configuracao.fktipoComponente = tipoComponente.idtipoComponente
INNER JOIN servidor ON configuracao.fkServidor = servidor.idServidor
WHERE servidor.idServidor = 2;

-- SELECT PARA TRAZER OS DADOS DE RAM DE SOMENTE UM SERVIDOR
SELECT
    dc.dadoCapturado
FROM servidor s
JOIN configuracao c ON s.idServidor = c.fkServidor
JOIN dadosCapturados dc ON c.idConfiguracao = dc.fkConfiguracao
JOIN tipoDado td ON dc.fkTipoDado = td.idtipoDado
WHERE s.idServidor = 1 AND td.tipoDado = 'Uso de RAM';




  
  
