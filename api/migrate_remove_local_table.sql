-- =====================================================
-- MIGRAÇÃO: Remover tabela locais e adicionar campo Desc_Local_Item na tabela itens
-- =====================================================

-- PASSO 1: Adicionar nova coluna Desc_Local_Item na tabela itens
ALTER TABLE ap_achados_perdidos.itens 
ADD COLUMN IF NOT EXISTS Desc_Local_Item TEXT;

-- PASSO 2: Migrar dados existentes (copiar nome do local para o novo campo)
-- Se você quiser preservar os dados existentes, descomente e execute:
-- UPDATE ap_achados_perdidos.itens i
-- SET Desc_Local_Item = l.Nome
-- FROM ap_achados_perdidos.locais l
-- WHERE i.Local_id = l.Id;

-- PASSO 3: Remover a constraint de foreign key
ALTER TABLE ap_achados_perdidos.itens 
DROP CONSTRAINT IF EXISTS fk_itens_local;

-- PASSO 4: Remover a coluna Local_id da tabela itens
ALTER TABLE ap_achados_perdidos.itens 
DROP COLUMN IF EXISTS Local_id;

-- PASSO 5: Tornar a coluna Desc_Local_Item obrigatória (após migração)
-- Descomente após verificar que todos os dados foram migrados:
-- ALTER TABLE ap_achados_perdidos.itens 
-- ALTER COLUMN Desc_Local_Item SET NOT NULL;

-- PASSO 6: Dropar a tabela locais (CUIDADO: isso remove todos os locais cadastrados)
-- Execute apenas se tiver certeza que não precisa mais da tabela:
DROP TABLE IF EXISTS ap_achados_perdidos.locais CASCADE;

-- =====================================================
-- VERIFICAÇÃO: Verificar se a migração foi bem-sucedida
-- =====================================================
-- SELECT 
--     column_name, 
--     data_type, 
--     is_nullable
-- FROM information_schema.columns 
-- WHERE table_schema = 'ap_achados_perdidos' 
--   AND table_name = 'itens'
--   AND column_name IN ('Local_id', 'Desc_Local_Item');

