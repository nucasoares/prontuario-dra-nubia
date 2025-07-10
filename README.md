#Projeto desenvolvido na disciplina de POO

Um sistema simples para gerenciar pacientes e exames médicos.

## Tecnologias utilizadas
- Java
- MySQL
- JDBC
- Eclipse

## Como rodar o projeto
###1. Crie o banco de dados MySQL e tableas com o script:


```sql
CREATE DATABASE IF NOT EXISTS prontuario_database;
USE prontuario_database;

CREATE TABLE pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE exames (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    data_exame DATETIME NOT NULL,
    paciente_id BIGINT NOT NULL,
    CONSTRAINT fk_exames_paciente
        FOREIGN KEY (paciente_id)
        REFERENCES pacientes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
```
###2. Configure a conexão com o banco.

	Arquivo `config.properties`
	
	DB_ADDRESS = localhost
	DB_PORT = SUAPORTA
	DB_USER = SEUUSUARIO
	DB_PASSWORD = SUASENHA
	DB_SCHEMA = prontuario_database
	
	> Substitua `seu_usuario` e `sua_senha` pelos dados corretos do seu MySQL

###3. Execute o `MenuPrincipalApp`.



## Autora

Nubia Catarina – Aluna de TSI (Tecnologia em Sistemas para Internet)  
Instituto Federal de Pernambuco – Campus Igarassu

