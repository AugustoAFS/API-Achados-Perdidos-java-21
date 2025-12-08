{
"openapi": "3.0.1",
"info": {
"title": "API Achados e Perdidos",
"description": "API para sistema de achados e perdidos com chat em tempo real",
"contact": {
"name": "Equipe de Desenvolvimento",
"url": "https://api-achadosperdidos.com.br",
"email": "contato@achadosperdidos.com.br"
},
"license": {
"name": "MIT License",
"url": "https://opensource.org/licenses/MIT"
},
"version": "1.0.0"
},
"servers": [
{
"url": "https://api-achadosperdidos.com.br",
"description": "Servidor de PRD"
},
{
"url": "https://api-achadosperdidos.com.br",
"description": "Producao (para testes)"
}
],
"security": [
{
"Bearer Authentication": []
}
],
"tags": [
{
"name": "Chat Híbrido",
"description": "Endpoints REST que também enviam via WebSocket para mensagens privadas"
},
{
"name": "Instituições",
"description": "API para gerenciamento de instituições"
},
{
"name": "Autenticação Google",
"description": "API para autenticação via Google OAuth2"
},
{
"name": "Fotos Item",
"description": "API para gerenciamento de relacionamento entre fotos e itens"
},
{
"name": "Fotos Usuário",
"description": "API para gerenciamento de relacionamento entre fotos e usuários"
},
{
"name": "Usuário Campus",
"description": "API para gerenciamento de relacionamento entre usuários e campus"
},
{
"name": "Fotos",
"description": "API centralizada para gerenciamento de fotos - Upload, download e CRUD de fotos"
},
{
"name": "Campus",
"description": "API para gerenciamento de campus"
},
{
"name": "Itens",
"description": "API para gerenciamento de itens achados/perdidos/doados"
},
{
"name": "Usuários",
"description": "Rota para gerenciamento de usuários"
},
{
"name": "Endereços",
"description": "API para gerenciamento de endereços"
},
{
"name": "Estados",
"description": "API para gerenciamento de estados"
},
{
"name": "Deadlines",
"description": "API para gerenciamento de prazos e doações"
},
{
"name": "Cidades",
"description": "API para gerenciamento de cidades"
},
{
"name": "Roles",
"description": "API para gerenciamento de roles/permissões"
},
{
"name": "Device Tokens",
"description": "API para gerenciamento de tokens de dispositivos para push notifications"
}
],
"paths": {
"/api/usuarios/{id}": {
"get": {
"tags": [
"Usuários"
],
"summary": "Buscar usuário por ID",
"description": "Retorna um usuário específico pelo seu ID",
"operationId": "getUsuarioById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuariosListDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Usuários"
],
"summary": "Atualizar usuário",
"description": "Atualiza os dados de um usuário existente",
"operationId": "updateUsuario",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID do usuário a ser atualizado",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/UsuariosUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuariosUpdateDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Usuários"
],
"summary": "Deletar usuário",
"description": "Remove um usuário do sistema",
"operationId": "deleteUsuario",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/usuario-campus/usuario/{usuarioId}/campus/{campusId}": {
"get": {
"tags": [
"Usuário Campus"
],
"summary": "Buscar relacionamento usuário-campus por IDs",
"operationId": "getUsuarioCampusByUsuarioIdAndCampusId",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "campusId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Usuário Campus"
],
"summary": "Atualizar relacionamento usuário-campus",
"operationId": "updateUsuarioCampus",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "campusId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Usuário Campus"
],
"summary": "Deletar relacionamento usuário-campus",
"operationId": "deleteUsuarioCampus",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "campusId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/itens/{id}": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar item por ID",
"operationId": "getItemById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Itens"
],
"summary": "Atualizar item",
"operationId": "updateItem",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ItemUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Itens"
],
"summary": "Deletar item (soft delete)",
"operationId": "deleteItem",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/instituicao/{id}": {
"get": {
"tags": [
"Instituições"
],
"operationId": "getInstituicaoById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/InstituicaoDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Instituições"
],
"operationId": "updateInstituicao",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/InstituicaoUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/InstituicaoDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Instituições"
],
"operationId": "deleteInstituicao",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/fotos/{id}": {
"get": {
"tags": [
"Fotos"
],
"summary": "Buscar foto por ID",
"description": "Retorna os detalhes de uma foto específica pelo seu ID",
"operationId": "getFotoById",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID da foto a ser buscada",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Fotos"
],
"summary": "Atualizar foto",
"description": "Atualiza os metadados de uma foto existente (URL, nome do arquivo, etc.)",
"operationId": "updateFoto",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID da foto a ser atualizada",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/FotosDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Fotos"
],
"summary": "Deletar foto (soft delete)",
"description": "Marca uma foto como inativa no banco de dados. Não remove o arquivo do S3",
"operationId": "deleteFoto",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID da foto a ser deletada",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/fotos-usuario/usuario/{usuarioId}/foto/{fotoId}": {
"get": {
"tags": [
"Fotos Usuário"
],
"summary": "Buscar relacionamento foto-usuário por IDs",
"operationId": "getFotoUsuarioByUsuarioIdAndFotoId",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "fotoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Fotos Usuário"
],
"summary": "Atualizar relacionamento foto-usuário",
"operationId": "updateFotoUsuario",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "fotoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Fotos Usuário"
],
"summary": "Deletar relacionamento foto-usuário",
"operationId": "deleteFotoUsuario",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "fotoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/fotos-item/item/{itemId}/foto/{fotoId}": {
"get": {
"tags": [
"Fotos Item"
],
"summary": "Buscar relacionamento foto-item por IDs",
"operationId": "getFotoItemByItemIdAndFotoId",
"parameters": [
{
"name": "itemId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "fotoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoItemDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Fotos Item"
],
"summary": "Atualizar relacionamento foto-item",
"operationId": "updateFotoItem",
"parameters": [
{
"name": "itemId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "fotoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/FotoItemUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoItemDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Fotos Item"
],
"summary": "Deletar relacionamento foto-item",
"operationId": "deleteFotoItem",
"parameters": [
{
"name": "itemId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "fotoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/estados/{id}": {
"get": {
"tags": [
"Estados"
],
"summary": "Buscar estado por ID",
"operationId": "getEstadoById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EstadoDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Estados"
],
"summary": "Atualizar estado",
"operationId": "updateEstado",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/EstadoUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EstadoDTO"
}
}
}
}
}
}
},
"/api/enderecos/{id}": {
"get": {
"tags": [
"Endereços"
],
"summary": "Buscar endereço por ID",
"operationId": "getEnderecoById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EnderecoDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Endereços"
],
"summary": "Atualizar endereço",
"operationId": "updateEndereco",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/EnderecoUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EnderecoDTO"
}
}
}
}
}
}
},
"/api/device-tokens/{id}": {
"get": {
"tags": [
"Device Tokens"
],
"summary": "Buscar token de dispositivo por ID",
"operationId": "getDeviceTokenById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Device Tokens"
],
"summary": "Atualizar token de dispositivo",
"operationId": "updateDeviceToken",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Device Tokens"
],
"summary": "Deletar token de dispositivo",
"operationId": "deleteDeviceToken",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/cidades/{id}": {
"get": {
"tags": [
"Cidades"
],
"summary": "Buscar cidade por ID",
"operationId": "getCidadeById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CidadeDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Cidades"
],
"summary": "Atualizar cidade",
"operationId": "updateCidade",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/CidadeUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CidadeDTO"
}
}
}
}
}
}
},
"/api/chat/mark-read": {
"put": {
"tags": [
"Chat Híbrido"
],
"summary": "Marcar como lidas",
"description": "Marca mensagens como lidas",
"operationId": "markAsReadRest",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "string"
}
}
}
}
}
}
},
"/api/campus/{id}": {
"get": {
"tags": [
"Campus"
],
"operationId": "getCampusById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CampusDTO"
}
}
}
}
}
},
"put": {
"tags": [
"Campus"
],
"operationId": "updateCampus",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/CampusUpdateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CampusDTO"
}
}
}
}
}
},
"delete": {
"tags": [
"Campus"
],
"operationId": "deleteCampus",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
},
"/api/usuarios": {
"get": {
"tags": [
"Usuários"
],
"summary": "Listar todos os usuários",
"description": "Retorna uma lista de todos os usuários cadastrados",
"operationId": "getAllUsuarios",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuariosListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Usuários"
],
"summary": "Criar novo usuário",
"description": "Cria um novo usuário no sistema. A instituição será preenchida automaticamente baseada no campus selecionado.",
"operationId": "createUsuario",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/UsuariosCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuariosCreateDTO"
}
}
}
}
}
}
},
"/api/usuarios/servidor": {
"post": {
"tags": [
"Usuários"
],
"summary": "Criar novo servidor",
"description": "Cria um novo usuário do tipo SERVIDOR. O CPF é obrigatório e deve ter 11 dígitos. Não inclui matrícula. A instituição será preenchida automaticamente baseada no campus selecionado.",
"operationId": "createServidor",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ServidorCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuariosCreateDTO"
}
}
}
}
}
}
},
"/api/usuarios/redefinir-senha": {
"post": {
"tags": [
"Usuários"
],
"summary": "Redefinir senha do usuário",
"description": "Redefine a senha de um usuário usando CPF ou matrícula, gerando um novo hash BCrypt. É necessário fornecer pelo menos um dos campos: CPF ou matrícula.",
"operationId": "redefinirSenha",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/RedefinirSenhaDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "string"
}
}
}
}
}
}
},
"/api/usuarios/login": {
"post": {
"tags": [
"Usuários"
],
"summary": "Login de usuário",
"description": "Autentica um usuário com email e senha, retornando um token JWT",
"operationId": "login",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/LoginRequestDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/TokenResponseDTO"
}
}
}
}
}
}
},
"/api/usuarios/aluno": {
"post": {
"tags": [
"Usuários"
],
"summary": "Criar novo aluno",
"description": "Cria um novo usuário do tipo ALUNO. A matrícula é obrigatória. Não inclui CPF. A instituição será preenchida automaticamente baseada no campus selecionado.",
"operationId": "createAluno",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/AlunoCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuariosCreateDTO"
}
}
}
}
}
}
},
"/api/usuario-campus": {
"get": {
"tags": [
"Usuário Campus"
],
"summary": "Listar todos os relacionamentos usuário-campus",
"operationId": "getAllUsuarioCampus",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Usuário Campus"
],
"summary": "Criar novo relacionamento usuário-campus",
"operationId": "createUsuarioCampus",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusDTO"
}
}
}
}
}
}
},
"/api/itens": {
"get": {
"tags": [
"Itens"
],
"summary": "Listar todos os itens",
"operationId": "getAllItens",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Itens"
],
"summary": "Criar novo item",
"description": "Cria um novo item. O usuário relator é obtido automaticamente do token JWT.",
"operationId": "createItem",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ItemCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemDTO"
}
}
}
}
}
}
},
"/api/itens/perdidos": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar itens perdidos",
"description": "Retorna uma lista de todos os itens perdidos ativos",
"operationId": "getItensPerdidos",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Itens"
],
"summary": "Criar item perdido",
"description": "Cria um novo item perdido. O usuário relator é obtido automaticamente do token JWT.",
"operationId": "createItemPerdidoWithPhoto",
"requestBody": {
"content": {
"multipart/form-data": {
"schema": {
"required": [
"item"
],
"type": "object",
"properties": {
"item": {
"$ref": "#/components/schemas/ItemCreateDTO"
},
"files": {
"type": "array",
"description": "Arquivo(s) de imagem do item (pode enviar múltiplas fotos)",
"items": {
"type": "string",
"format": "binary"
}
}
}
}
},
"application/json": {
"schema": {
"$ref": "#/components/schemas/ItemCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemDTO"
}
}
}
}
}
}
},
"/api/itens/achados": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar itens achados",
"description": "Retorna uma lista de todos os itens achados ativos",
"operationId": "getItensAchados",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Itens"
],
"summary": "Criar item achado com foto(s)",
"description": "Cria um novo item achado com uma ou mais fotos. Pelo menos uma foto é obrigatória. O usuário relator é obtido automaticamente do token JWT.",
"operationId": "createItemAchado",
"requestBody": {
"content": {
"multipart/form-data": {
"schema": {
"required": [
"files",
"item"
],
"type": "object",
"properties": {
"item": {
"$ref": "#/components/schemas/ItemCreateDTO"
},
"files": {
"type": "array",
"description": "Arquivo(s) de imagem do item (obrigatório - pode enviar múltiplas fotos)",
"items": {
"type": "string",
"format": "binary"
}
}
}
}
}
}
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemDTO"
}
}
}
}
}
}
},
"/api/instituicao": {
"get": {
"tags": [
"Instituições"
],
"operationId": "getAllInstituicoes",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/InstituicaoListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Instituições"
],
"operationId": "createInstituicao",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/InstituicaoCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/InstituicaoDTO"
}
}
}
}
}
}
},
"/api/fotos": {
"get": {
"tags": [
"Fotos"
],
"summary": "Listar todas as fotos",
"description": "Retorna uma lista com todas as fotos cadastradas no sistema, incluindo inativas",
"operationId": "getAllFotos",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Fotos"
],
"summary": "Criar nova foto",
"description": "Cria um novo registro de foto no banco de dados. Para upload de arquivo, use os endpoints /upload",
"operationId": "createFoto",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/FotosDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosDTO"
}
}
}
}
}
}
},
"/api/fotos/upload": {
"post": {
"tags": [
"Fotos"
],
"summary": "Upload genérico de foto",
"description": "Faz upload genérico de uma foto para o S3. Pode ser usado para foto de perfil ou de item, dependendo dos parâmetros fornecidos. Se itemId for fornecido, a foto será associada ao item. Se isProfilePhoto for true, será tratada como foto de perfil. Aceita formatos: JPEG, PNG, GIF, WEBP",
"operationId": "uploadPhoto",
"parameters": [
{
"name": "userId",
"in": "query",
"description": "ID do usuário proprietário da foto",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "itemId",
"in": "query",
"description": "ID do item (opcional, se fornecido associa a foto ao item)",
"required": false,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "isProfilePhoto",
"in": "query",
"description": "Indica se é foto de perfil (padrão: false)",
"required": false,
"schema": {
"type": "boolean",
"default": false
}
}
],
"requestBody": {
"content": {
"multipart/form-data": {
"schema": {
"required": [
"file"
],
"type": "object",
"properties": {
"file": {
"type": "string",
"description": "Arquivo de imagem a ser enviado",
"format": "binary"
}
}
}
}
}
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "object"
}
}
}
}
}
}
},
"/api/fotos/upload/profile": {
"post": {
"tags": [
"Fotos"
],
"summary": "Upload de foto de perfil",
"description": "Faz upload de uma foto de perfil para o S3 e cria o registro no banco de dados. A foto é automaticamente associada ao usuário como foto de perfil. Aceita formatos: JPEG, PNG, GIF, WEBP",
"operationId": "uploadProfilePhoto",
"parameters": [
{
"name": "userId",
"in": "query",
"description": "ID do usuário proprietário da foto",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"multipart/form-data": {
"schema": {
"required": [
"file"
],
"type": "object",
"properties": {
"file": {
"type": "string",
"description": "Arquivo de imagem a ser enviado",
"format": "binary"
}
}
}
}
}
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "object"
}
}
}
}
}
}
},
"/api/fotos/upload/item": {
"post": {
"tags": [
"Fotos"
],
"summary": "Upload de foto de item",
"description": "Faz upload de uma foto de item para o S3 e cria o registro no banco de dados. A foto é automaticamente associada ao item especificado. Aceita formatos: JPEG, PNG, GIF, WEBP",
"operationId": "uploadItemPhoto",
"parameters": [
{
"name": "userId",
"in": "query",
"description": "ID do usuário que está fazendo o upload",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "itemId",
"in": "query",
"description": "ID do item ao qual a foto será associada",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"requestBody": {
"content": {
"multipart/form-data": {
"schema": {
"required": [
"file"
],
"type": "object",
"properties": {
"file": {
"type": "string",
"description": "Arquivo de imagem a ser enviado",
"format": "binary"
}
}
}
}
}
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "object"
}
}
}
}
}
}
},
"/api/fotos-usuario": {
"get": {
"tags": [
"Fotos Usuário"
],
"summary": "Listar todos os relacionamentos foto-usuário",
"operationId": "getAllFotosUsuario",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Fotos Usuário"
],
"summary": "Criar novo relacionamento foto-usuário",
"operationId": "createFotoUsuario",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioDTO"
}
}
}
}
}
}
},
"/api/fotos-item": {
"get": {
"tags": [
"Fotos Item"
],
"summary": "Listar todos os relacionamentos foto-item",
"operationId": "getAllFotosItem",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoItemListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Fotos Item"
],
"summary": "Criar novo relacionamento foto-item",
"operationId": "createFotoItem",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/FotoItemCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoItemDTO"
}
}
}
}
}
}
},
"/api/estados": {
"get": {
"tags": [
"Estados"
],
"summary": "Listar todos os estados",
"operationId": "getAllEstados",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/EstadoDTO"
}
}
}
}
}
}
},
"post": {
"tags": [
"Estados"
],
"summary": "Criar novo estado",
"operationId": "createEstado",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/EstadoCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EstadoDTO"
}
}
}
}
}
}
},
"/api/estados/{id}/delete": {
"post": {
"tags": [
"Estados"
],
"summary": "Inativar estado (soft delete)",
"operationId": "deleteEstado",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EstadoDTO"
}
}
}
}
}
}
},
"/api/enderecos": {
"get": {
"tags": [
"Endereços"
],
"summary": "Listar todos os endereços",
"operationId": "getAllEnderecos",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/EnderecoDTO"
}
}
}
}
}
}
},
"post": {
"tags": [
"Endereços"
],
"summary": "Criar novo endereço",
"operationId": "createEndereco",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/EnderecoCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EnderecoDTO"
}
}
}
}
}
}
},
"/api/enderecos/{id}/delete": {
"post": {
"tags": [
"Endereços"
],
"summary": "Inativar endereço (soft delete)",
"operationId": "deleteEndereco",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EnderecoDTO"
}
}
}
}
}
}
},
"/api/device-tokens": {
"get": {
"tags": [
"Device Tokens"
],
"summary": "Listar todos os tokens de dispositivos",
"operationId": "getAllDeviceTokens",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Device Tokens"
],
"summary": "Criar novo token de dispositivo",
"operationId": "createDeviceToken",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenDTO"
}
}
}
}
}
}
},
"/api/device-tokens/register": {
"post": {
"tags": [
"Device Tokens"
],
"summary": "Registrar ou atualizar token de dispositivo (endpoint simplificado para app mobile)",
"operationId": "registerOrUpdateDeviceToken",
"parameters": [
{
"name": "Authorization",
"in": "header",
"required": false,
"schema": {
"type": "string"
}
},
{
"name": "token",
"in": "query",
"required": true,
"schema": {
"type": "string"
}
},
{
"name": "plataforma",
"in": "query",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenDTO"
}
}
}
}
}
}
},
"/api/deadline/notify-deadlines": {
"post": {
"tags": [
"Deadlines"
],
"summary": "Notificar prazos",
"description": "Força a execução do processo de notificação de prazos",
"operationId": "notifyDeadlines",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "string"
}
}
}
}
}
}
},
"/api/deadline/mark-expired-donated": {
"post": {
"tags": [
"Deadlines"
],
"summary": "Marcar itens expirados como doados",
"description": "Força a execução do processo de marcação de itens como doados",
"operationId": "markExpiredAsDonated",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "string"
}
}
}
}
}
}
},
"/api/cidades": {
"get": {
"tags": [
"Cidades"
],
"summary": "Listar todas as cidades",
"operationId": "getAllCidades",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/CidadeDTO"
}
}
}
}
}
}
},
"post": {
"tags": [
"Cidades"
],
"summary": "Criar nova cidade",
"operationId": "createCidade",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/CidadeCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CidadeDTO"
}
}
}
}
}
}
},
"/api/cidades/{id}/delete": {
"post": {
"tags": [
"Cidades"
],
"summary": "Inativar cidade (soft delete)",
"operationId": "deleteCidade",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CidadeDTO"
}
}
}
}
}
}
},
"/api/chat/typing": {
"post": {
"tags": [
"Chat Híbrido"
],
"summary": "Indicar digitação",
"description": "Indica que usuário está digitando",
"operationId": "typingRest",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
},
"/api/chat/stop-typing": {
"post": {
"tags": [
"Chat Híbrido"
],
"summary": "Parar digitação",
"description": "Para indicação de digitação",
"operationId": "stopTypingRest",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
},
"/api/chat/send": {
"post": {
"tags": [
"Chat Híbrido"
],
"summary": "Enviar mensagem privada",
"description": "Envia mensagem privada via REST e WebSocket",
"operationId": "sendMessageRest",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
},
"/api/chat/private": {
"post": {
"tags": [
"Chat Híbrido"
],
"summary": "Enviar mensagem privada",
"description": "Envia mensagem privada via REST e WebSocket",
"operationId": "sendPrivateMessageRest",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
},
"/api/chat/online": {
"post": {
"tags": [
"Chat Híbrido"
],
"summary": "Usuário online",
"description": "Notifica que usuário está online",
"operationId": "userOnlineRest",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
},
"/api/chat/offline": {
"post": {
"tags": [
"Chat Híbrido"
],
"summary": "Usuário offline",
"description": "Notifica que usuário está offline",
"operationId": "userOfflineRest",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
},
"/api/campus": {
"get": {
"tags": [
"Campus"
],
"operationId": "getAllCampus",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CampusListDTO"
}
}
}
}
}
},
"post": {
"tags": [
"Campus"
],
"operationId": "createCampus",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/CampusCreateDTO"
}
}
},
"required": true
},
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CampusDTO"
}
}
}
}
}
}
},
"/api/usuario-campus/usuario/{usuarioId}": {
"get": {
"tags": [
"Usuário Campus"
],
"summary": "Buscar campus por ID do usuário",
"operationId": "findByUsuarioId",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusListDTO"
}
}
}
}
}
}
},
"/api/usuario-campus/campus/{campusId}": {
"get": {
"tags": [
"Usuário Campus"
],
"summary": "Buscar usuários por ID do campus",
"operationId": "findByCampusId",
"parameters": [
{
"name": "campusId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusListDTO"
}
}
}
}
}
}
},
"/api/usuario-campus/active": {
"get": {
"tags": [
"Usuário Campus"
],
"summary": "Listar relacionamentos usuário-campus ativos",
"operationId": "getActiveUsuarioCampus",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/UsuarioCampusListDTO"
}
}
}
}
}
}
},
"/api/roles": {
"get": {
"tags": [
"Roles"
],
"summary": "Listar todas as roles",
"operationId": "getAllRoles",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/RoleDTO"
}
}
}
}
}
}
}
},
"/api/roles/{id}": {
"get": {
"tags": [
"Roles"
],
"summary": "Buscar role por ID",
"operationId": "getRoleById",
"parameters": [
{
"name": "id",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/RoleDTO"
}
}
}
}
}
}
},
"/api/roles/nome/{nome}": {
"get": {
"tags": [
"Roles"
],
"summary": "Buscar role por nome",
"description": "Retorna uma role específica pelo seu nome (ex: ALUNO, SERVIDOR, USER)",
"operationId": "getRoleByNome",
"parameters": [
{
"name": "nome",
"in": "path",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/RoleDTO"
}
}
}
}
}
}
},
"/api/roles/active": {
"get": {
"tags": [
"Roles"
],
"summary": "Listar roles ativas",
"operationId": "getActiveRoles",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/RoleDTO"
}
}
}
}
}
}
}
},
"/api/itens/user/{userId}": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar itens por usuário",
"operationId": "getItensByUser",
"parameters": [
{
"name": "userId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
}
},
"/api/itens/tipo/{tipo}": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar itens por tipo",
"description": "Tipos disponíveis: PERDIDO, ACHADO, DOADO",
"operationId": "getItensByTipo",
"parameters": [
{
"name": "tipo",
"in": "path",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
}
},
"/api/itens/search": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar itens por termo",
"operationId": "searchItens",
"parameters": [
{
"name": "term",
"in": "query",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
}
},
"/api/itens/doados": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar itens doados",
"description": "Retorna uma lista de todos os itens doados ativos",
"operationId": "getItensDoados",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
}
},
"/api/itens/campus/{campusId}": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar itens por campus",
"operationId": "getItensByCampus",
"parameters": [
{
"name": "campusId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
}
},
"/api/itens/active": {
"get": {
"tags": [
"Itens"
],
"summary": "Listar itens ativos",
"operationId": "getActiveItens",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ItemListDTO"
}
}
}
}
}
}
},
"/api/instituicao/type/{tipoInstituicao}": {
"get": {
"tags": [
"Instituições"
],
"operationId": "getInstituicoesByType",
"parameters": [
{
"name": "tipoInstituicao",
"in": "path",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/InstituicaoListDTO"
}
}
}
}
}
}
},
"/api/instituicao/active": {
"get": {
"tags": [
"Instituições"
],
"operationId": "getActiveInstituicoes",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/InstituicaoListDTO"
}
}
}
}
}
}
},
"/api/google-auth/login": {
"get": {
"tags": [
"Autenticação Google"
],
"operationId": "loginGoogle",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "string"
}
}
}
}
}
}
},
"/api/google-auth/callback": {
"get": {
"tags": [
"Autenticação Google"
],
"operationId": "handleGoogleAuthCallback",
"parameters": [
{
"name": "code",
"in": "query",
"required": false,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "object"
}
}
}
}
}
}
},
"/api/fotos/user/{userId}": {
"get": {
"tags": [
"Fotos"
],
"summary": "Buscar fotos por usuário",
"description": "Retorna todas as fotos associadas a um usuário específico (perfil e itens)",
"operationId": "getFotosByUser",
"parameters": [
{
"name": "userId",
"in": "path",
"description": "ID do usuário",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosListDTO"
}
}
}
}
}
}
},
"/api/fotos/profile/{userId}": {
"get": {
"tags": [
"Fotos"
],
"summary": "Buscar fotos de perfil do usuário",
"description": "Retorna todas as fotos de perfil de um usuário específico",
"operationId": "getProfilePhotos",
"parameters": [
{
"name": "userId",
"in": "path",
"description": "ID do usuário",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosListDTO"
}
}
}
}
}
}
},
"/api/fotos/profile-photo/{userId}": {
"get": {
"tags": [
"Fotos"
],
"summary": "Buscar foto de perfil principal",
"description": "Retorna a foto de perfil principal (ativa) de um usuário específico",
"operationId": "getProfilePhoto",
"parameters": [
{
"name": "userId",
"in": "path",
"description": "ID do usuário",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosDTO"
}
}
}
}
}
}
},
"/api/fotos/main-item-photo/{itemId}": {
"get": {
"tags": [
"Fotos"
],
"summary": "Buscar foto principal do item",
"description": "Retorna a foto principal (primeira foto) de um item específico",
"operationId": "getMainItemPhoto",
"parameters": [
{
"name": "itemId",
"in": "path",
"description": "ID do item",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosDTO"
}
}
}
}
}
}
},
"/api/fotos/item/{itemId}": {
"get": {
"tags": [
"Fotos"
],
"summary": "Buscar fotos por item",
"description": "Retorna todas as fotos associadas a um item específico",
"operationId": "getFotosByItem",
"parameters": [
{
"name": "itemId",
"in": "path",
"description": "ID do item",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosListDTO"
}
}
}
}
}
}
},
"/api/fotos/item-photos/{itemId}": {
"get": {
"tags": [
"Fotos"
],
"summary": "Buscar fotos de item (alternativo)",
"description": "Método alternativo para buscar fotos de um item. Funcionalidade similar a /item/{itemId}",
"operationId": "getItemPhotos",
"parameters": [
{
"name": "itemId",
"in": "path",
"description": "ID do item",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosListDTO"
}
}
}
}
}
}
},
"/api/fotos/download/{id}": {
"get": {
"tags": [
"Fotos"
],
"summary": "Download de foto",
"description": "Baixa o arquivo de imagem do S3 e retorna os bytes da imagem. O Content-Type é definido automaticamente baseado na extensão do arquivo. Suporta: JPEG, PNG, GIF, WEBP",
"operationId": "downloadPhoto",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID da foto a ser baixada",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "string",
"format": "byte"
}
}
}
}
}
}
},
"/api/fotos/active": {
"get": {
"tags": [
"Fotos"
],
"summary": "Listar fotos ativas",
"description": "Retorna apenas as fotos que estão ativas (não foram deletadas)",
"operationId": "getActiveFotos",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotosListDTO"
}
}
}
}
}
}
},
"/api/fotos-usuario/usuario/{usuarioId}": {
"get": {
"tags": [
"Fotos Usuário"
],
"summary": "Buscar fotos por ID do usuário",
"operationId": "findByUsuarioId",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioListDTO"
}
}
}
}
}
}
},
"/api/fotos-usuario/foto/{fotoId}": {
"get": {
"tags": [
"Fotos Usuário"
],
"summary": "Buscar usuários por ID da foto",
"operationId": "findByFotoId",
"parameters": [
{
"name": "fotoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioListDTO"
}
}
}
}
}
}
},
"/api/fotos-usuario/active": {
"get": {
"tags": [
"Fotos Usuário"
],
"summary": "Listar relacionamentos foto-usuário ativos",
"operationId": "getActiveFotosUsuario",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoUsuarioListDTO"
}
}
}
}
}
}
},
"/api/fotos-item/item/{itemId}": {
"get": {
"tags": [
"Fotos Item"
],
"summary": "Buscar fotos por ID do item",
"operationId": "findByItemId",
"parameters": [
{
"name": "itemId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoItemListDTO"
}
}
}
}
}
}
},
"/api/fotos-item/foto/{fotoId}": {
"get": {
"tags": [
"Fotos Item"
],
"summary": "Buscar itens por ID da foto",
"operationId": "findByFotoId",
"parameters": [
{
"name": "fotoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoItemListDTO"
}
}
}
}
}
}
},
"/api/fotos-item/active": {
"get": {
"tags": [
"Fotos Item"
],
"summary": "Listar relacionamentos foto-item ativos",
"operationId": "getActiveFotosItem",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/FotoItemListDTO"
}
}
}
}
}
}
},
"/api/estados/uf/{uf}": {
"get": {
"tags": [
"Estados"
],
"summary": "Buscar estado por UF",
"operationId": "getEstadoByUf",
"parameters": [
{
"name": "uf",
"in": "path",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/EstadoDTO"
}
}
}
}
}
}
},
"/api/enderecos/cidade/{cidadeId}": {
"get": {
"tags": [
"Endereços"
],
"summary": "Listar endereços por cidade",
"operationId": "getEnderecosByCidade",
"parameters": [
{
"name": "cidadeId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/EnderecoDTO"
}
}
}
}
}
}
}
},
"/api/device-tokens/usuario/{usuarioId}": {
"get": {
"tags": [
"Device Tokens"
],
"summary": "Buscar tokens de dispositivos por ID do usuário",
"operationId": "getDeviceTokensByUsuarioId",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenListDTO"
}
}
}
}
}
}
},
"/api/device-tokens/usuario/{usuarioId}/active": {
"get": {
"tags": [
"Device Tokens"
],
"summary": "Buscar tokens ativos de dispositivos por ID do usuário",
"operationId": "getActiveDeviceTokensByUsuarioId",
"parameters": [
{
"name": "usuarioId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenListDTO"
}
}
}
}
}
}
},
"/api/device-tokens/active": {
"get": {
"tags": [
"Device Tokens"
],
"summary": "Listar todos os tokens de dispositivos ativos",
"operationId": "getActiveDeviceTokens",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeviceTokenListDTO"
}
}
}
}
}
}
},
"/api/deadline/stats": {
"get": {
"tags": [
"Deadlines"
],
"summary": "Estatísticas de prazos",
"description": "Retorna estatísticas sobre prazos e doações",
"operationId": "getDeadlineStats",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/DeadlineStats"
}
}
}
}
}
}
},
"/api/cidades/estado/{estadoId}": {
"get": {
"tags": [
"Cidades"
],
"summary": "Listar cidades por estado",
"operationId": "getCidadesByEstado",
"parameters": [
{
"name": "estadoId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/CidadeDTO"
}
}
}
}
}
}
}
},
"/api/chat/unread/{userId}": {
"get": {
"tags": [
"Chat Híbrido"
],
"summary": "Buscar mensagens não lidas",
"description": "Retorna lista de mensagens não lidas do usuário",
"operationId": "getUnreadMessages",
"parameters": [
{
"name": "userId",
"in": "path",
"description": "ID do usuário",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
}
},
"/api/chat/messages/{chatId}": {
"get": {
"tags": [
"Chat Híbrido"
],
"summary": "Buscar mensagens do chat",
"description": "Retorna mensagens de um chat específico",
"operationId": "getChatMessages",
"parameters": [
{
"name": "chatId",
"in": "path",
"description": "ID do chat",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
}
},
"/api/chat/messages/users/{userId1}/{userId2}": {
"get": {
"tags": [
"Chat Híbrido"
],
"summary": "Buscar mensagens entre usuários",
"description": "Retorna mensagens entre dois usuários",
"operationId": "getMessagesBetweenUsers",
"parameters": [
{
"name": "userId1",
"in": "path",
"description": "ID do primeiro usuário",
"required": true,
"schema": {
"type": "string"
}
},
{
"name": "userId2",
"in": "path",
"description": "ID do segundo usuário",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "array",
"items": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
}
},
"/api/chat/message/{messageId}": {
"get": {
"tags": [
"Chat Híbrido"
],
"summary": "Buscar mensagem por ID",
"description": "Retorna uma mensagem específica",
"operationId": "getMessageById",
"parameters": [
{
"name": "messageId",
"in": "path",
"description": "ID da mensagem",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ChatMessage"
}
}
}
}
}
}
},
"/api/chat/count/{chatId}": {
"get": {
"tags": [
"Chat Híbrido"
],
"summary": "Contar mensagens",
"description": "Retorna número de mensagens em um chat",
"operationId": "getMessageCount",
"parameters": [
{
"name": "chatId",
"in": "path",
"description": "ID do chat",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "integer",
"format": "int64"
}
}
}
}
}
}
},
"/api/chat/chats/{userId}": {
"get": {
"tags": [
"Chat Híbrido"
],
"summary": "Buscar histórico de chats",
"description": "Retorna lista de chats do usuário com última mensagem, contador de não lidas e nome do outro usuário",
"operationId": "getUserChats",
"parameters": [
{
"name": "userId",
"in": "path",
"description": "ID do usuário",
"required": true,
"schema": {
"type": "string"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/ChatSummaryListDTO"
}
}
}
}
}
}
},
"/api/campus/institution/{institutionId}": {
"get": {
"tags": [
"Campus"
],
"operationId": "getCampusByInstitution",
"parameters": [
{
"name": "institutionId",
"in": "path",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CampusListDTO"
}
}
}
}
}
}
},
"/api/campus/active": {
"get": {
"tags": [
"Campus"
],
"operationId": "getActiveCampus",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"$ref": "#/components/schemas/CampusListDTO"
}
}
}
}
}
}
},
"/api/fotos/delete/{id}": {
"delete": {
"tags": [
"Fotos"
],
"summary": "Deletar foto permanentemente",
"description": "Remove a foto tanto do banco de dados quanto do S3 (deleção física). Diferente de DELETE /{id} que apenas marca como inativa (soft delete), este endpoint remove completamente o arquivo do armazenamento",
"operationId": "deletePhoto",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID da foto a ser deletada permanentemente",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
}
],
"responses": {
"200": {
"description": "OK"
}
}
}
}
},
"components": {
"schemas": {
"UsuariosUpdateDTO": {
"type": "object",
"properties": {
"nomeCompleto": {
"type": "string"
},
"cpf": {
"type": "string"
},
"email": {
"type": "string"
},
"matricula": {
"type": "string"
},
"enderecoId": {
"type": "integer",
"format": "int32"
},
"campusId": {
"type": "integer",
"format": "int32"
},
"fotoId": {
"type": "integer",
"format": "int32"
},
"flgInativo": {
"type": "boolean"
}
}
},
"UsuarioCampusUpdateDTO": {
"type": "object",
"properties": {
"usuarioId": {
"type": "integer",
"format": "int32"
},
"campusId": {
"type": "integer",
"format": "int32"
},
"flgInativo": {
"type": "boolean"
}
}
},
"UsuarioCampusDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"usuarioId": {
"type": "integer",
"format": "int32"
},
"campusId": {
"type": "integer",
"format": "int32"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"ItemUpdateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"descricao": {
"type": "string"
},
"tipoItem": {
"type": "string",
"enum": [
"ACHADO",
"PERDIDO"
]
},
"descLocalItem": {
"type": "string"
},
"usuarioRelatorId": {
"type": "integer",
"format": "int32"
},
"flgInativo": {
"type": "boolean"
}
}
},
"FotosDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"description": "ID da foto",
"format": "int32",
"readOnly": true,
"example": 1
},
"url": {
"type": "string",
"description": "URL da foto",
"example": "https://bucket.s3.amazonaws.com/fotos/foto_item_123.jpg"
},
"provedorArmazenamento": {
"type": "string",
"description": "Provedor de armazenamento",
"example": "S3"
},
"chaveArmazenamento": {
"type": "string",
"description": "Chave de armazenamento",
"example": "fotos/foto_item_123.jpg"
},
"nomeArquivoOriginal": {
"type": "string",
"description": "Nome original do arquivo",
"example": "foto_item_123.jpg"
},
"tamanhoArquivoBytes": {
"type": "integer",
"description": "Tamanho em bytes",
"format": "int64",
"example": 1024000
},
"dtaCriacao": {
"type": "string",
"description": "Data de criacao",
"format": "date-time"
},
"flgInativo": {
"type": "boolean",
"description": "Flag de inativacao",
"example": false
},
"dtaRemocao": {
"type": "string",
"description": "Data de remocao logica",
"format": "date-time"
}
},
"description": "DTO completo de foto"
},
"ItemDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"nome": {
"type": "string"
},
"descricao": {
"type": "string"
},
"tipoItem": {
"type": "string",
"enum": [
"ACHADO",
"PERDIDO"
]
},
"descLocalItem": {
"type": "string"
},
"usuarioRelatorId": {
"type": "integer",
"format": "int32"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
},
"fotos": {
"type": "array",
"items": {
"$ref": "#/components/schemas/FotosDTO"
}
}
}
},
"InstituicaoUpdateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"codigo": {
"type": "string"
},
"tipo": {
"type": "string"
},
"cnpj": {
"type": "string"
},
"flgInativo": {
"type": "boolean"
}
}
},
"InstituicaoDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"nome": {
"type": "string"
},
"codigo": {
"type": "string"
},
"tipo": {
"type": "string"
},
"cnpj": {
"type": "string"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"FotoUsuarioUpdateDTO": {
"type": "object",
"properties": {
"usuarioId": {
"type": "integer",
"format": "int32"
},
"fotoId": {
"type": "integer",
"format": "int32"
},
"flgInativo": {
"type": "boolean"
}
}
},
"FotoUsuarioDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"usuarioId": {
"type": "integer",
"format": "int32"
},
"fotoId": {
"type": "integer",
"format": "int32"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"FotoItemUpdateDTO": {
"type": "object",
"properties": {
"itemId": {
"type": "integer",
"format": "int32"
},
"fotoId": {
"type": "integer",
"format": "int32"
},
"flgInativo": {
"type": "boolean"
}
}
},
"FotoItemDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"itemId": {
"type": "integer",
"format": "int32"
},
"fotoId": {
"type": "integer",
"format": "int32"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"EstadoUpdateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"uf": {
"type": "string"
},
"flgInativo": {
"type": "boolean"
}
}
},
"EstadoDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"nome": {
"type": "string"
},
"uf": {
"type": "string"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"EnderecoUpdateDTO": {
"type": "object",
"properties": {
"logradouro": {
"type": "string"
},
"numero": {
"type": "string"
},
"complemento": {
"type": "string"
},
"bairro": {
"type": "string"
},
"cep": {
"type": "string"
},
"cidadeId": {
"type": "integer",
"format": "int32"
},
"flgInativo": {
"type": "boolean"
}
}
},
"EnderecoDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"logradouro": {
"type": "string"
},
"numero": {
"type": "string"
},
"complemento": {
"type": "string"
},
"bairro": {
"type": "string"
},
"cep": {
"type": "string"
},
"cidadeId": {
"type": "integer",
"format": "int32"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"DeviceTokenUpdateDTO": {
"type": "object",
"properties": {
"token": {
"type": "string"
},
"plataforma": {
"type": "string"
},
"flgInativo": {
"type": "boolean"
}
}
},
"DeviceTokenDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"usuarioId": {
"type": "integer",
"format": "int32"
},
"token": {
"type": "string"
},
"plataforma": {
"type": "string"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"dtaAtualizacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"CidadeUpdateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"estadoId": {
"type": "integer",
"format": "int32"
},
"flgInativo": {
"type": "boolean"
}
}
},
"CidadeDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"nome": {
"type": "string"
},
"estadoId": {
"type": "integer",
"format": "int32"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"ChatMessage": {
"type": "object",
"properties": {
"id": {
"type": "string"
},
"id_Usuario_Remetente": {
"type": "string"
},
"id_Usuario_Destino": {
"type": "string"
},
"data_Hora_Menssagem": {
"type": "string",
"format": "date-time"
},
"id_Chat": {
"type": "string"
},
"menssagem": {
"type": "string"
},
"tipo": {
"type": "string",
"enum": [
"CHAT",
"TYPING",
"STOP_TYPING",
"SYSTEM"
]
},
"status": {
"type": "string",
"enum": [
"ENVIADA",
"RECEBIDA",
"LIDA"
]
}
}
},
"CampusUpdateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"instituicaoId": {
"type": "integer",
"format": "int32"
},
"enderecoId": {
"type": "integer",
"format": "int32"
},
"flgInativo": {
"type": "boolean"
}
}
},
"CampusDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"nome": {
"type": "string"
},
"instituicaoId": {
"type": "integer",
"format": "int32"
},
"enderecoId": {
"type": "integer",
"format": "int32"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"UsuariosCreateDTO": {
"type": "object",
"properties": {
"nomeCompleto": {
"type": "string"
},
"cpf": {
"type": "string"
},
"email": {
"type": "string"
},
"senha": {
"type": "string"
},
"matricula": {
"type": "string"
},
"numeroTelefone": {
"type": "string"
},
"enderecoId": {
"type": "integer",
"format": "int32"
},
"campusId": {
"type": "integer",
"format": "int32"
}
}
},
"ServidorCreateDTO": {
"type": "object",
"properties": {
"nomeCompleto": {
"type": "string"
},
"cpf": {
"type": "string"
},
"email": {
"type": "string"
},
"senha": {
"type": "string"
},
"numeroTelefone": {
"type": "string"
},
"enderecoId": {
"type": "integer",
"format": "int32"
},
"campusId": {
"type": "integer",
"format": "int32"
}
}
},
"RedefinirSenhaDTO": {
"type": "object",
"properties": {
"cpf_Usuario": {
"type": "string"
},
"nova_Senha": {
"type": "string"
},
"matricula": {
"type": "string"
}
}
},
"LoginRequestDTO": {
"type": "object",
"properties": {
"Email_Usuario": {
"type": "string"
},
"Senha_Hash": {
"type": "string"
},
"Device_Token": {
"type": "string"
},
"Plataforma": {
"type": "string"
}
}
},
"TokenResponseDTO": {
"type": "object",
"properties": {
"token": {
"type": "string"
}
}
},
"AlunoCreateDTO": {
"type": "object",
"properties": {
"nomeCompleto": {
"type": "string"
},
"email": {
"type": "string"
},
"senha": {
"type": "string"
},
"matricula": {
"type": "string"
},
"numeroTelefone": {
"type": "string"
},
"enderecoId": {
"type": "integer",
"format": "int32"
},
"campusId": {
"type": "integer",
"format": "int32"
}
}
},
"UsuarioCampusCreateDTO": {
"type": "object",
"properties": {
"usuarioId": {
"type": "integer",
"format": "int32"
},
"campusId": {
"type": "integer",
"format": "int32"
}
}
},
"ItemCreateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"descricao": {
"type": "string"
},
"tipoItem": {
"type": "string",
"enum": [
"ACHADO",
"PERDIDO"
]
},
"descLocalItem": {
"type": "string"
},
"usuarioRelatorId": {
"type": "integer",
"format": "int32"
}
},
"description": "Dados do item (JSON)"
},
"InstituicaoCreateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"codigo": {
"type": "string"
},
"tipo": {
"type": "string"
},
"cnpj": {
"type": "string"
}
}
},
"FotoUsuarioCreateDTO": {
"type": "object",
"properties": {
"usuarioId": {
"type": "integer",
"format": "int32"
},
"fotoId": {
"type": "integer",
"format": "int32"
}
}
},
"FotoItemCreateDTO": {
"type": "object",
"properties": {
"itemId": {
"type": "integer",
"format": "int32"
},
"fotoId": {
"type": "integer",
"format": "int32"
}
}
},
"EstadoCreateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"uf": {
"type": "string"
}
}
},
"EnderecoCreateDTO": {
"type": "object",
"properties": {
"logradouro": {
"type": "string"
},
"numero": {
"type": "string"
},
"complemento": {
"type": "string"
},
"bairro": {
"type": "string"
},
"cep": {
"type": "string"
},
"cidadeId": {
"type": "integer",
"format": "int32"
}
}
},
"DeviceTokenCreateDTO": {
"type": "object",
"properties": {
"usuarioId": {
"type": "integer",
"format": "int32"
},
"token": {
"type": "string"
},
"plataforma": {
"type": "string"
}
}
},
"CidadeCreateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"estadoId": {
"type": "integer",
"format": "int32"
}
}
},
"CampusCreateDTO": {
"type": "object",
"properties": {
"nome": {
"type": "string"
},
"instituicaoId": {
"type": "integer",
"format": "int32"
},
"enderecoId": {
"type": "integer",
"format": "int32"
}
}
},
"UsuariosDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"nomeCompleto": {
"type": "string"
},
"cpf": {
"type": "string"
},
"email": {
"type": "string"
},
"matricula": {
"type": "string"
},
"enderecoId": {
"type": "integer",
"format": "int32"
},
"campus": {
"type": "array",
"items": {
"$ref": "#/components/schemas/CampusDTO"
}
},
"fotoPerfil": {
"$ref": "#/components/schemas/FotosDTO"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"UsuariosListDTO": {
"type": "object",
"properties": {
"usuarios": {
"type": "array",
"items": {
"$ref": "#/components/schemas/UsuariosDTO"
}
},
"totalCount": {
"type": "integer",
"format": "int32"
}
}
},
"UsuarioCampusListDTO": {
"type": "object",
"properties": {
"usuarioCampus": {
"type": "array",
"items": {
"$ref": "#/components/schemas/UsuarioCampusDTO"
}
},
"totalCount": {
"type": "integer",
"format": "int32"
}
}
},
"RoleDTO": {
"type": "object",
"properties": {
"id": {
"type": "integer",
"format": "int32"
},
"nome": {
"type": "string"
},
"descricao": {
"type": "string"
},
"dtaCriacao": {
"type": "string",
"format": "date-time"
},
"flgInativo": {
"type": "boolean"
},
"dtaRemocao": {
"type": "string",
"format": "date-time"
}
}
},
"ItemListDTO": {
"type": "object",
"properties": {
"itens": {
"type": "array",
"items": {
"$ref": "#/components/schemas/ItemDTO"
}
},
"totalCount": {
"type": "integer",
"format": "int32"
}
}
},
"InstituicaoListDTO": {
"type": "object",
"properties": {
"instituicoes": {
"type": "array",
"items": {
"$ref": "#/components/schemas/InstituicaoDTO"
}
},
"totalCount": {
"type": "integer",
"format": "int32"
}
}
},
"FotosListDTO": {
"type": "object",
"properties": {
"fotos": {
"type": "array",
"description": "Lista de fotos",
"items": {
"$ref": "#/components/schemas/FotosDTO"
}
},
"totalCount": {
"type": "integer",
"description": "Total de fotos na lista",
"format": "int32"
}
},
"description": "DTO para lista de fotos"
},
"FotoUsuarioListDTO": {
"type": "object",
"properties": {
"fotoUsuarios": {
"type": "array",
"items": {
"$ref": "#/components/schemas/FotoUsuarioDTO"
}
},
"totalCount": {
"type": "integer",
"format": "int32"
}
}
},
"FotoItemListDTO": {
"type": "object",
"properties": {
"fotoItens": {
"type": "array",
"items": {
"$ref": "#/components/schemas/FotoItemDTO"
}
},
"totalCount": {
"type": "integer",
"format": "int32"
}
}
},
"DeviceTokenListDTO": {
"type": "object",
"properties": {
"deviceTokens": {
"type": "array",
"items": {
"$ref": "#/components/schemas/DeviceTokenDTO"
}
},
"total": {
"type": "integer",
"format": "int32"
}
}
},
"DeadlineStats": {
"type": "object",
"properties": {
"itemsPerdidos": {
"type": "integer",
"format": "int32"
},
"itemsAchados": {
"type": "integer",
"format": "int32"
},
"itemsDoados": {
"type": "integer",
"format": "int32"
},
"totalProcessed": {
"type": "integer",
"format": "int32"
}
}
},
"ChatSummaryDTO": {
"type": "object",
"properties": {
"chatId": {
"type": "string"
},
"otherUserId": {
"type": "string"
},
"otherUserName": {
"type": "string"
},
"lastMessage": {
"type": "string"
},
"lastMessageDate": {
"type": "string",
"format": "date-time"
},
"unreadCount": {
"type": "integer",
"format": "int32"
},
"idUltimaMensagem": {
"type": "string"
}
}
},
"ChatSummaryListDTO": {
"type": "object",
"properties": {
"chats": {
"type": "array",
"items": {
"$ref": "#/components/schemas/ChatSummaryDTO"
}
},
"totalCount": {
"type": "integer",
"format": "int32"
}
}
},
"CampusListDTO": {
"type": "object",
"properties": {
"campi": {
"type": "array",
"items": {
"$ref": "#/components/schemas/CampusDTO"
}
},
"totalCount": {
"type": "integer",
"format": "int32"
}
}
}
},
"securitySchemes": {
"Bearer Authentication": {
"type": "http",
"description": "Insira o token JWT no formato: Bearer {token}",
"scheme": "bearer",
"bearerFormat": "JWT"
}
}
}
}