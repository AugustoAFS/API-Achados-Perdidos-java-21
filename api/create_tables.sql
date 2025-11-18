CREATE SCHEMA IF NOT EXISTS ap_achados_perdidos;
SET search_path TO ap_achados_perdidos, public;

CREATE TABLE roles ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

-- Tabela status_item removida - tipo_item já faz esse trabalho

-- =================================================================
-- Seção: Normalização de Endereços
-- =================================================================

CREATE TABLE estados ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL UNIQUE,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE cidades ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    estado_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_cidades_estado FOREIGN KEY (estado_id) REFERENCES estados(id) ON DELETE RESTRICT
);

CREATE TABLE enderecos ( -- essa ja foi 
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
-- Seção: Entidades Organizacionais
-- =================================================================

CREATE TABLE instituicoes ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    codigo VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    cnpj CHAR(14) UNIQUE,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL
);

CREATE TABLE campus ( -- essa ja foi 
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

CREATE TABLE locais ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    campus_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_locais_campus FOREIGN KEY (campus_id) REFERENCES campus(id) ON DELETE CASCADE
);

CREATE TABLE usuarios ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    cpf CHAR(11) UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    hash_senha VARCHAR(255) NOT NULL,
    matricula VARCHAR(50) UNIQUE,
    numero_telefone VARCHAR(20),
    endereco_id INT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_usuarios_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos(id) ON DELETE SET NULL
);

CREATE TABLE itens ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    tipo_item VARCHAR(50) NOT NULL,
    local_id INT NOT NULL,
    usuario_relator_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_itens_local FOREIGN KEY (local_id) REFERENCES locais(id) ON DELETE RESTRICT,
    CONSTRAINT fk_itens_usuario_relator FOREIGN KEY (usuario_relator_id) REFERENCES usuarios(id) ON DELETE RESTRICT,
    CONSTRAINT chk_tipo_item CHECK (UPPER(tipo_item) IN ('PERDIDO', 'ACHADO', 'DOADO'))
);

CREATE TABLE itens_achados ( -- essa ja foi 
    id INT PRIMARY KEY,
    encontrado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_itens_achados_item FOREIGN KEY (id) REFERENCES itens(id) ON DELETE CASCADE
);

CREATE TABLE itens_perdidos ( -- essa ja foi 
    id INT PRIMARY KEY,
    perdido_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_itens_perdidos_item FOREIGN KEY (id) REFERENCES itens(id) ON DELETE CASCADE
);

CREATE TABLE itens_devolvido ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    detalhes_devolucao TEXT NOT NULL,
    item_id INT NOT NULL,
    usuario_devolvedor_id INT NOT NULL,
    usuario_achou_id INT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_devolvido_item FOREIGN KEY (item_id) REFERENCES itens(id) ON DELETE CASCADE,
    CONSTRAINT fk_devolvido_usuario FOREIGN KEY (usuario_devolvedor_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_devolvido_aprovador FOREIGN KEY (usuario_achou_id) REFERENCES usuarios(id) ON DELETE SET NULL,
    CONSTRAINT chk_detalhes_devolucao_min_length CHECK (LENGTH(TRIM(detalhes_devolucao)) >= 20)
);

CREATE TABLE itens_doados ( -- essa ja foi 
    id INT PRIMARY KEY,
    doado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_itens_doados_item FOREIGN KEY (id) REFERENCES itens(id) ON DELETE CASCADE
);

CREATE TABLE fotos ( -- essa ja foi 
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

CREATE TABLE fotos_usuario ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    foto_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_fotos_usuario_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_fotos_usuario_foto FOREIGN KEY (foto_id) REFERENCES fotos(id) ON DELETE CASCADE,
    CONSTRAINT uk_fotos_usuario UNIQUE (usuario_id, foto_id)
);

CREATE TABLE fotos_item ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    item_id INT NOT NULL,
    foto_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_fotos_item_item FOREIGN KEY (item_id) REFERENCES itens(id) ON DELETE CASCADE,
    CONSTRAINT fk_fotos_item_foto FOREIGN KEY (foto_id) REFERENCES fotos(id) ON DELETE CASCADE,
    CONSTRAINT uk_fotos_item UNIQUE (item_id, foto_id)
);

CREATE TABLE usuario_roles ( -- essa ja foi 
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    role_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_usuarioroles_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuarioroles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT uk_usuario_role UNIQUE (usuario_id, role_id)
);

CREATE TABLE usuario_campus (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    campus_id INT NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_usuariocampus_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuariocampus_campus FOREIGN KEY (campus_id) REFERENCES campus(id) ON DELETE CASCADE,
    CONSTRAINT uk_usuario_campus UNIQUE (usuario_id, campus_id)
);

CREATE TABLE device_tokens (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    plataforma VARCHAR(20) NOT NULL,
    Dta_Criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Dta_Atualizacao TIMESTAMP NULL DEFAULT NULL,
    Flg_Inativo BOOLEAN NOT NULL DEFAULT FALSE,
    Dta_Remocao TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT fk_device_tokens_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT chk_plataforma CHECK (UPPER(plataforma) IN ('ANDROID', 'IOS')),
    CONSTRAINT uk_device_token_usuario UNIQUE (usuario_id, token)
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
