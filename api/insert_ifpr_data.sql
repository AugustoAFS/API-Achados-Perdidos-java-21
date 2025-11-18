-- =================================================================
-- Script de Inserção de Dados do IFPR (Campus Curitiba e Colombo)
-- Esquema: ap_achados_perdidos
-- =================================================================

DO $$
DECLARE
    -- Variáveis para os IDs principais
    v_estado_id INT;
    v_instituicao_id INT;
    
    -- Variáveis para as Cidades
    v_cidade_curitiba_id INT;
    v_cidade_colombo_id INT;
    
    -- Variáveis para os Endereços
    v_addr_curitiba_id INT;
    v_addr_colombo_id INT;
    
    -- Variáveis para os Campus
    v_campus_curitiba_id INT;
    v_campus_colombo_id INT;

BEGIN
    -- Define o schema padrão para este bloco
    SET search_path TO ap_achados_perdidos, public;

    -- =================================================================
    -- Seção 1: Estado (Paraná)
    -- =================================================================
    SELECT id INTO v_estado_id FROM estados WHERE uf = 'PR';

    IF v_estado_id IS NULL THEN
        INSERT INTO estados (nome, uf)
        VALUES ('Paraná', 'PR')
        RETURNING id INTO v_estado_id;
    END IF;

    -- =================================================================
    -- Seção 2: Instituição (IFPR)
    -- =================================================================
    SELECT id INTO v_instituicao_id 
    FROM instituicoes 
    WHERE cnpj = '10652179000115';

    IF v_instituicao_id IS NULL THEN
        INSERT INTO instituicoes (nome, codigo, tipo, cnpj)
        VALUES ('Instituto Federal do Paraná', 'IFPR', 'Federal', '10652179000115')
        RETURNING id INTO v_instituicao_id;
    ELSE
        UPDATE instituicoes
           SET nome = 'Instituto Federal do Paraná',
               codigo = 'IFPR',
               tipo = 'Federal'
         WHERE id = v_instituicao_id;
    END IF;

    -- =================================================================
    -- Seção 3: Cidades (Curitiba e Colombo)
    -- =================================================================
    SELECT id INTO v_cidade_curitiba_id 
    FROM cidades 
    WHERE nome = 'Curitiba' AND estado_id = v_estado_id;

    IF v_cidade_curitiba_id IS NULL THEN
        INSERT INTO cidades (nome, estado_id) 
        VALUES ('Curitiba', v_estado_id)
        RETURNING id INTO v_cidade_curitiba_id;
    END IF;
    
    SELECT id INTO v_cidade_colombo_id 
    FROM cidades 
    WHERE nome = 'Colombo' AND estado_id = v_estado_id;
    
    IF v_cidade_colombo_id IS NULL THEN
        INSERT INTO cidades (nome, estado_id) 
        VALUES ('Colombo', v_estado_id)
        RETURNING id INTO v_cidade_colombo_id;
    END IF;
    
    -- =================================================================
    -- Seção 4: Endereços
    -- =================================================================
    -- Endereço Campus Curitiba
    SELECT id INTO v_addr_curitiba_id
    FROM enderecos
    WHERE logradouro = 'Rua João Negrão'
      AND numero = '1285'
      AND bairro = 'Rebouças'
      AND cep = '80230150'
      AND cidade_id = v_cidade_curitiba_id;

    IF v_addr_curitiba_id IS NULL THEN
        INSERT INTO enderecos (logradouro, numero, complemento, bairro, cep, cidade_id) 
        VALUES ('Rua João Negrão', '1285', NULL, 'Rebouças', '80230150', v_cidade_curitiba_id)
        RETURNING id INTO v_addr_curitiba_id;
    END IF;
    
    -- Endereço Campus Colombo
    SELECT id INTO v_addr_colombo_id
    FROM enderecos
    WHERE logradouro = 'Rua Antonio Chemin'
      AND numero = '28'
      AND bairro = 'São Gabriel'
      AND cep = '83407100'
      AND cidade_id = v_cidade_colombo_id;

    IF v_addr_colombo_id IS NULL THEN
        INSERT INTO enderecos (logradouro, numero, complemento, bairro, cep, cidade_id) 
        VALUES ('Rua Antonio Chemin', '28', NULL, 'São Gabriel', '83407100', v_cidade_colombo_id)
        RETURNING id INTO v_addr_colombo_id;
    END IF;
    
    -- =================================================================
    -- Seção 5: Campus (Curitiba e Colombo)
    -- =================================================================
    SELECT id INTO v_campus_curitiba_id
    FROM campus
    WHERE nome = 'Campus Curitiba'
      AND instituicao_id = v_instituicao_id
      AND endereco_id = v_addr_curitiba_id;

    IF v_campus_curitiba_id IS NULL THEN
        INSERT INTO campus (nome, instituicao_id, endereco_id) 
        VALUES ('Campus Curitiba', v_instituicao_id, v_addr_curitiba_id)
        RETURNING id INTO v_campus_curitiba_id;
    END IF;
    
    SELECT id INTO v_campus_colombo_id
    FROM campus
    WHERE nome = 'Campus Colombo'
      AND instituicao_id = v_instituicao_id
      AND endereco_id = v_addr_colombo_id;

    IF v_campus_colombo_id IS NULL THEN
        INSERT INTO campus (nome, instituicao_id, endereco_id) 
        VALUES ('Campus Colombo', v_instituicao_id, v_addr_colombo_id)
        RETURNING id INTO v_campus_colombo_id;
    END IF;

    RAISE NOTICE 'Dados do IFPR inseridos/atualizados com sucesso!';
    RAISE NOTICE 'Campus Curitiba ID: %', v_campus_curitiba_id;
    RAISE NOTICE 'Campus Colombo ID: %', v_campus_colombo_id;

END;
$$;

