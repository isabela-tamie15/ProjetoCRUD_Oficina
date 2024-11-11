--CRIA BANCO DE DADOS--
create database projetoda;
use projetoda;
--CRIA TABELA--
create table carro (
id serial,
marca varchar (20),
modelo varchar (50),
ano int (4),
motor varchar (20),
placa varchar (7),
dono varchar (50),
cnh varchar (11),
telefone varchar (12),
endereco varchar (100)
);

select * from carro;

--COMANDOS CRUD ÚTEIS:
insert into nome_tabela (categoria, codigo, descricao, preco) values 
("Amortecedor Suspensão", "233XP40", "Amortecedor da suspensão dianteira palio 97/...", 350);
select * from nome_tabela;
alter table nome_tabela change column id_cadastro codigo int;
alter table nome_tabela add column id int;
delete from nome_tabela where id = 1;
truncate table nome_tabela;   --limpa o conteúdo de todos os campos da tabela
drop table nome_tabela;       --deleta a tabela completa
