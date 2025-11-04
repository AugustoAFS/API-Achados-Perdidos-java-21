-- =================================================================
-- Esquema: Sistema de Achados e Perdidos (Convertido para PostgreSQL)
-- Autor: Augusto Farias dos Santos
-- =================================================================


CREATE SCHEMA IF NOT EXISTS ap_achados_perdidos;
SET search_path TO ap_achados_perdidos, public;

-- =================================================================
-- Seção 1: Tabelas Auxiliares
-- =================================================================

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE status_item (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    status_item VARCHAR(100),
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

-- =================================================================
-- Seção 2: Normalização de Endereços
-- =================================================================

CREATE TABLE estados (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL UNIQUE,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE cidades (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    estado_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_cidades_estado FOREIGN KEY (estado_id) REFERENCES estados(id) ON DELETE RESTRICT
);

CREATE TABLE enderecos (
    id SERIAL PRIMARY KEY,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cep VARCHAR(8),
    cidade_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_enderecos_cidade FOREIGN KEY (cidade_id) REFERENCES cidades(id) ON DELETE RESTRICT
);

-- =================================================================
-- Seção 3: Entidades Organizacionais (Normalizadas)
-- =================================================================

CREATE TABLE instituicoes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    codigo VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    cnpj CHAR(14) UNIQUE,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE empresas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    nome_fantasia VARCHAR(255) NOT NULL,
    cnpj CHAR(14) UNIQUE,
    endereco_id INT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_empresas_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos(id) ON DELETE SET NULL
);

CREATE TABLE campus (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    instituicao_id INT NOT NULL,
    endereco_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_campus_instituicao FOREIGN KEY (instituicao_id) REFERENCES instituicoes(id) ON DELETE RESTRICT,
    CONSTRAINT fk_campus_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos(id) ON DELETE RESTRICT
);

CREATE TABLE locais (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    campus_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_locais_campus FOREIGN KEY (campus_id) REFERENCES campus(id) ON DELETE CASCADE
);

-- =================================================================
-- Seção 4: Usuários e Itens
-- =================================================================

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf CHAR(11) UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    hash_senha VARCHAR(255) NOT NULL,
    matricula VARCHAR(50) UNIQUE,
    numero_telefone VARCHAR(20),
    empresa_id INT,
    endereco_id INT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_usuarios_empresa FOREIGN KEY (empresa_id) REFERENCES empresas(id) ON DELETE SET NULL,
    CONSTRAINT fk_usuarios_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos(id) ON DELETE SET NULL
);

CREATE TABLE itens_perdidos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    encontrado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_relator_id INT NOT NULL,
    local_id INT NOT NULL,
    status_item_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_itens_perdidos_usuario_relator FOREIGN KEY (usuario_relator_id) REFERENCES usuarios(id) ON DELETE RESTRICT,
    CONSTRAINT fk_itens_perdidos_local FOREIGN KEY (local_id) REFERENCES locais(id) ON DELETE RESTRICT,
    CONSTRAINT fk_itens_perdidos_status FOREIGN KEY (status_item_id) REFERENCES status_item(id) ON DELETE RESTRICT
);

CREATE INDEX idx_itens_perdidos_nome ON itens_perdidos(nome);

CREATE TABLE itens_reivindicados (
    id SERIAL PRIMARY KEY,
    detalhes_reivindicacao TEXT,
    item_id INT NOT NULL,
    usuario_reivindicador_id INT NOT NULL,
    usuario_achou_id INT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_reivindicacoes_item FOREIGN KEY (item_id) REFERENCES itens_perdidos(id) ON DELETE CASCADE,
    CONSTRAINT fk_reivindicacoes_usuario FOREIGN KEY (usuario_reivindicador_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_reivindicacoes_aprovador FOREIGN KEY (usuario_achou_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE UNIQUE INDEX uk_item_usuario_ativo ON itens_reivindicados (item_id, usuario_reivindicador_id) WHERE Dta_Remocao IS NULL;


-- =================================================================
-- Seção 5: Fotos e Junções N:N
-- =================================================================

CREATE TABLE fotos (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    provedor_armazenamento VARCHAR(100) NOT NULL DEFAULT 'local',
    chave_armazenamento TEXT,
    nome_arquivo_original VARCHAR(255),
    tamanho_arquivo_bytes BIGINT,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT chk_tamanho_arquivo_bytes_positivo CHECK (tamanho_arquivo_bytes >= 0)
);

CREATE TABLE fotos_usuario (
    usuario_id INT NOT NULL,
    foto_id INT NOT NULL,
    PRIMARY KEY (usuario_id, foto_id),
    CONSTRAINT fk_fotos_usuario_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_fotos_usuario_foto FOREIGN KEY (foto_id) REFERENCES fotos(id) ON DELETE CASCADE
);

CREATE TABLE fotos_item (
    item_id INT NOT NULL,
    foto_id INT NOT NULL,
    PRIMARY KEY (item_id, foto_id),
    CONSTRAINT fk_fotos_item_item FOREIGN KEY (item_id) REFERENCES itens_perdidos(id) ON DELETE CASCADE,
    CONSTRAINT fk_fotos_item_foto FOREIGN KEY (foto_id) REFERENCES fotos(id) ON DELETE CASCADE
);

CREATE TABLE usuario_roles (
    usuario_id INT NOT NULL,
    role_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (usuario_id, role_id),
    CONSTRAINT fk_usuarioroles_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuarioroles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE usuario_campus (
    usuario_id INT NOT NULL,
    campus_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (usuario_id, campus_id),
    CONSTRAINT fk_usuariocampus_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuariocampus_campus FOREIGN KEY (campus_id) REFERENCES campus(id) ON DELETE CASCADE
);