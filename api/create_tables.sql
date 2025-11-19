CREATE SCHEMA IF NOT EXISTS ap_achados_perdidos;
SET search_path TO ap_achados_perdidos, public;

CREATE TABLE roles ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Nome VARCHAR(50) NOT NULL UNIQUE,
    Descricao TEXT,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE estados ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    UF CHAR(2) NOT NULL UNIQUE,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE cidades ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Estado_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_cidades_estado FOREIGN KEY (Estado_id) REFERENCES estados(Id) ON DELETE RESTRICT
);

CREATE TABLE enderecos ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Logradouro VARCHAR(255) NOT NULL,
    Numero VARCHAR(20),
    Complemento VARCHAR(100),
    Bairro VARCHAR(100),
    CEP VARCHAR(8),
    Cidade_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_enderecos_cidade FOREIGN KEY (Cidade_id) REFERENCES cidades(Id) ON DELETE RESTRICT
);

-- =================================================================
-- Seção: Entidades Organizacionais
-- =================================================================

CREATE TABLE instituicoes ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Codigo VARCHAR(100) NOT NULL,
    Tipo VARCHAR(50) NOT NULL,
    CNPJ CHAR(14) UNIQUE,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE campus ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Nome VARCHAR(150) NOT NULL,
    Instituicao_id INT NOT NULL,
    Endereco_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_campus_instituicao FOREIGN KEY (Instituicao_id) REFERENCES instituicoes(Id) ON DELETE RESTRICT,
    CONSTRAINT fk_campus_endereco FOREIGN KEY (Endereco_id) REFERENCES enderecos(Id) ON DELETE RESTRICT
);

CREATE TABLE locais ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Nome VARCHAR(150) NOT NULL,
    Descricao TEXT,
    Campus_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
    CONSTRAINT fk_campus_local FOREIGN KEY (Campus_id) REFERENCES campus(Id) ON DELETE RESTRICT
);

CREATE TABLE usuarios ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Nome_completo VARCHAR(255) NOT NULL,
    CPF CHAR(11) UNIQUE,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Hash_senha VARCHAR(255) NOT NULL,
    Matricula VARCHAR(50) UNIQUE,
    Numero_telefone VARCHAR(20),
    Endereco_id INT NULL,
    Role_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_usuarios_endereco FOREIGN KEY (Endereco_id) REFERENCES enderecos(Id) ON DELETE SET NULL
);

CREATE TABLE itens ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Descricao TEXT NOT NULL,
    Tipo_item VARCHAR(50) NOT NULL, -- ACHADO, PERDIDO
    Status_item VARCHAR(50) NOT NULL DEFAULT 'ATIVO', -- ATIVO, DEVOLVIDO, RESGATADO, CANCELADO
    Local_id INT NOT NULL,
    Usuario_relator_id INT NOT NULL, -- Quem reportou (achou ou perdeu)
    Usuario_reivindicador_id INT NULL, -- Quem reivindicou/devolveu
    Dta_Reivindicacao TIMESTAMP NULL, -- Data que foi devolvido/resgatado
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_itens_local FOREIGN KEY (Local_id) REFERENCES locais(Id) ON DELETE RESTRICT,
    CONSTRAINT fk_itens_usuario_relator FOREIGN KEY (Usuario_relator_id) REFERENCES usuarios(Id) ON DELETE RESTRICT,
    CONSTRAINT fk_itens_usuario_reivindicador FOREIGN KEY (Usuario_reivindicador_id) REFERENCES usuarios(Id) ON DELETE SET NULL,
    CONSTRAINT chk_tipo_item CHECK (Tipo_item IN ('ACHADO', 'PERDIDO')),
    CONSTRAINT chk_status_item CHECK (Status_item IN ('ATIVO', 'DEVOLVIDO', 'RESGATADO', 'CANCELADO'))
);

CREATE TABLE fotos ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    URL TEXT NOT NULL,
    Provedor_armazenamento VARCHAR(100) NOT NULL DEFAULT 'local', -- Enum local, s3, etc
    Chave_armazenamento TEXT,
    Nome_arquivo_original VARCHAR(255),
    Tamanho_arquivo_bytes BIGINT,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE fotos_usuario ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Usuario_id INT NOT NULL,
    Foto_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_fotos_usuario_usuario FOREIGN KEY (Usuario_id) REFERENCES usuarios(Id) ON DELETE CASCADE,
    CONSTRAINT fk_fotos_usuario_foto FOREIGN KEY (Foto_id) REFERENCES fotos(Id) ON DELETE CASCADE
    );

CREATE TABLE fotos_item ( -- essa ja foi 
    Id SERIAL PRIMARY KEY,
    Item_id INT NOT NULL,
    Foto_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_fotos_item_item FOREIGN KEY (Item_id) REFERENCES itens(Id) ON DELETE CASCADE,
    CONSTRAINT fk_fotos_item_foto FOREIGN KEY (Foto_id) REFERENCES fotos(Id) ON DELETE CASCADE
);

CREATE TABLE usuario_campus (
    Id SERIAL PRIMARY KEY,
    Usuario_id INT NOT NULL,
    Campus_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_usuariocampus_usuario FOREIGN KEY (Usuario_id) REFERENCES usuarios(Id) ON DELETE CASCADE,
    CONSTRAINT fk_usuariocampus_campus FOREIGN KEY (Campus_id) REFERENCES campus(Id) ON DELETE CASCADE
);

CREATE TABLE device_tokens (
    Id SERIAL PRIMARY KEY,
    Usuario_id INT NOT NULL,
    Token VARCHAR(255) NOT NULL,
    Plataforma VARCHAR(20) NOT NULL, -- Enum android ou ios
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Dta_Atualizacao TIMESTAMP NULL DEFAULT NULL,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_device_tokens_usuario FOREIGN KEY (Usuario_id) REFERENCES usuarios(Id) ON DELETE CASCADE
);

-- =================================================================
-- INSERTS DAS TABELAS AUXILIARES
-- =================================================================

-- Inserir Roles
INSERT INTO roles (nome, descricao) VALUES
('ADMIN', 'Administrador do sistema'),
('USUARIO', 'Usuário comum do sistema');

-- Tabela status_item removida - tipo_item já faz esse trabalho

INSERT INTO estados (nome, uf) VALUES
('Paraná', 'PR');

-- Inserir Cidades do Paraná
INSERT INTO cidades (nome, estado_id) VALUES
('Curitiba', (SELECT id FROM estados WHERE uf = 'PR')),
('Colombo', (SELECT id FROM estados WHERE uf = 'PR'));
