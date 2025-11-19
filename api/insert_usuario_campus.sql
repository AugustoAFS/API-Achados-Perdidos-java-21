-- Query para adicionar um usuário a um campus na tabela usuario_campus
-- IMPORTANTE: Substitua os valores pelos IDs reais do usuário e campus

-- Primeiro, verifique os IDs disponíveis:
-- SELECT Id, Nome_completo, Email FROM ap_achados_perdidos.usuarios WHERE Flg_Inativo = false;
-- SELECT Id, Nome FROM ap_achados_perdidos.campus WHERE Flg_Inativo = false;

-- Opção 1: Inserção direta (substitua 1 pelo ID do usuário e 2 pelo ID do campus)
INSERT INTO ap_achados_perdidos.usuario_campus (Usuario_id, Campus_id, Dta_Criacao, Flg_Inativo)
VALUES (1, 2, CURRENT_TIMESTAMP, false);

INSERT INTO ap_achados_perdidos.usuario_campus (Usuario_id, Campus_id, Dta_Criacao, Flg_Inativo)
SELECT 1, 2, CURRENT_TIMESTAMP, false
WHERE NOT EXISTS (
    SELECT 1 
    FROM ap_achados_perdidos.usuario_campus 
    WHERE Usuario_id = 1 
      AND Campus_id = 2 
      AND (Flg_Inativo = false OR Flg_Inativo IS NULL)
);
