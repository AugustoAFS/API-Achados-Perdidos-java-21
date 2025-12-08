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
"url": "http://localhost:8080",
"description": "Servidor de DEV"
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
"name": "Instituições",
"description": "API para gerenciamento de instituições"
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
"name": "Auth",
"description": "API para autenticação e autorização"
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
"name": "Cache",
"description": "API para gerenciamento e monitoramento de cache"
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
},
{
"name": "Chat",
"description": "Endpoints REST e WebSocket para mensagens privadas"
},
{
"name": "Usuários",
"description": "API para gerenciamento de usuários"
}
],
"paths": {
"/api/usuarios/{id}": {
"get": {
"tags": [
"Usuários"
],
"summary": "Buscar usuário por ID",
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
"operationId": "updateUsuario",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID do usuário",
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
"description": "ID do usuário",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "campusId",
"in": "path",
"description": "ID do campus",
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
"description": "ID do usuário",
"required": true,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "campusId",
"in": "path",
"description": "ID do campus",
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
"/api/itens/update/{id}": {
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
}
},
"/api/instituicao/{id}": {
"get": {
"tags": [
"Instituições"
],
"summary": "Buscar instituição por ID",
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
"summary": "Atualizar instituição",
"operationId": "updateInstituicao",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID da instituição",
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
"$ref": "#/components/schemas/InstituicaoDTO"
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
"summary": "Deletar instituição",
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
"/api/fotos/usuario/{id}": {
"put": {
"tags": [
"Fotos"
],
"summary": "Atualizar foto de usuário",
"description": "Atualiza os metadados de uma foto de usuário existente",
"operationId": "updateFotoUsuario",
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
"summary": "Deletar foto de usuário (soft delete)",
"description": "Marca uma foto de usuário como inativa no banco de dados",
"operationId": "deleteFotoUsuario",
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
"/api/fotos/item/{id}": {
"put": {
"tags": [
"Fotos"
],
"summary": "Atualizar foto de item",
"description": "Atualiza os metadados de uma foto de item existente",
"operationId": "updateFotoItem",
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
"summary": "Deletar foto de item (soft delete)",
"description": "Marca uma foto de item como inativa no banco de dados",
"operationId": "deleteFotoItem",
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
"description": "ID do estado",
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
"description": "ID do endereço",
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
"description": "ID do token",
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
"description": "ID da cidade",
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
"/api/chat/mark-unread": {
"put": {
"tags": [
"Chat"
],
"summary": "Marcar como não lidas",
"description": "Marca mensagens como não lidas",
"operationId": "markAsUnRead",
"requestBody": {
"content": {
"application/json": {
"schema": {
"type": "array",
"items": {
"type": "string"
}
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
"/api/chat/mark-read": {
"put": {
"tags": [
"Chat"
],
"summary": "Marcar como lidas",
"description": "Marca mensagens como lidas",
"operationId": "markAsRead",
"requestBody": {
"content": {
"application/json": {
"schema": {
"type": "array",
"items": {
"type": "string"
}
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
"summary": "Buscar campus por ID",
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
"summary": "Atualizar campus",
"operationId": "updateCampus",
"parameters": [
{
"name": "id",
"in": "path",
"description": "ID do campus",
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
"summary": "Deletar campus",
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
"/api/usuarios/aluno": {
"post": {
"tags": [
"Usuários"
],
"summary": "Criar novo aluno",
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
"type": "array",
"items": {
"$ref": "#/components/schemas/UsuarioCampusListDTO"
}
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
"/api/itens/perdidos": {
"post": {
"tags": [
"Itens"
],
"summary": "Criar item perdido com foto(s)",
"description": "Cria um novo item perdido com uma ou mais fotos (opcional). O usuário relator é obtido automaticamente do token JWT.",
"operationId": "createItemPerdido",
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
"description": "Arquivo(s) de imagem do item (opcional)",
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
"/api/itens/achados": {
"post": {
"tags": [
"Itens"
],
"summary": "Criar item achado com foto(s)",
"description": "Cria um novo item achado com uma ou mais fotos. O usuário relator é obtido automaticamente do token JWT.",
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
"description": "Arquivo(s) de imagem do item (obrigatório)",
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
"summary": "Listar todas as instituições",
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
"summary": "Criar nova instituição",
"operationId": "createInstituicao",
"requestBody": {
"content": {
"application/json": {
"schema": {
"$ref": "#/components/schemas/InstituicaoDTO"
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
"/api/fotos/usuario": {
"post": {
"tags": [
"Fotos"
],
"summary": "Criar foto de usuário",
"description": "Cria um novo registro de foto de usuário no banco de dados",
"operationId": "createFotoUsuario",
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
"description": "Upload genérico para o S3. Se itemId for fornecido, associa ao item. Se isProfilePhoto=true, trata como foto de perfil.",
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
"description": "ID do item (opcional)",
"required": false,
"schema": {
"type": "integer",
"format": "int32"
}
},
{
"name": "isProfilePhoto",
"in": "query",
"description": "Indica se é foto de perfil",
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
"$ref": "#/components/schemas/FotosDTO"
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
"/api/fotos/item": {
"post": {
"tags": [
"Fotos"
],
"summary": "Criar foto de item",
"description": "Cria um novo registro de foto de item no banco de dados",
"operationId": "createFotoItem",
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
"Chat"
],
"summary": "Indicar digitação",
"description": "Indica que usuário está digitando",
"operationId": "typing",
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
"Chat"
],
"summary": "Parar digitação",
"description": "Para indicação de digitação",
"operationId": "stopTyping",
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
"Chat"
],
"summary": "Enviar mensagem privada",
"description": "Envia mensagem privada via REST e WebSocket",
"operationId": "sendMessage",
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
"Chat"
],
"summary": "Enviar mensagem privada",
"description": "Envia mensagem privada via REST e WebSocket",
"operationId": "sendPrivateMessage",
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
"Chat"
],
"summary": "Usuário online",
"description": "Notifica que usuário está online",
"operationId": "userOnline",
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
"Chat"
],
"summary": "Usuário offline",
"description": "Notifica que usuário está offline",
"operationId": "userOffline",
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
"summary": "Listar todos os campus",
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
"summary": "Criar novo campus",
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
"/api/cache/log-stats": {
"post": {
"tags": [
"Cache"
],
"summary": "Logar estatísticas detalhadas no console",
"operationId": "logCacheStats",
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
"/api/cache/clear/{cacheName}": {
"post": {
"tags": [
"Cache"
],
"summary": "Limpar um cache específico",
"operationId": "clearCache",
"parameters": [
{
"name": "cacheName",
"in": "path",
"description": "Nome do cache",
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
"type": "string"
}
}
}
}
}
}
},
"/api/cache/clear-all": {
"post": {
"tags": [
"Cache"
],
"summary": "Limpar todos os caches",
"operationId": "clearAllCaches",
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
"/api/auth/redefinir-senha": {
"post": {
"tags": [
"Auth"
],
"summary": "Redefinir senha do usuário",
"description": "Redefine senha usando CPF ou matrícula",
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
"/api/auth/logout": {
"post": {
"tags": [
"Auth"
],
"summary": "Logout de usuário",
"description": "Valida token para logout. Cliente deve descartar o token localmente",
"operationId": "logout",
"parameters": [
{
"name": "Authorization",
"in": "header",
"description": "Token JWT no formato 'Bearer {token}'",
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
"type": "string"
}
}
}
}
}
}
},
"/api/auth/login": {
"post": {
"tags": [
"Auth"
],
"summary": "Login de usuário",
"description": "Autentica usuário com email e senha, retornando token JWT",
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
"description": "Nome da role",
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
}
},
"/api/itens/campus/{campusId}": {
"get": {
"tags": [
"Itens"
],
"summary": "Buscar itens por campus",
"description": "Retorna todos os itens de um campus específico",
"operationId": "getItensByCampus",
"parameters": [
{
"name": "campusId",
"in": "path",
"description": "ID do campus",
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
"/api/instituicao/active": {
"get": {
"tags": [
"Instituições"
],
"summary": "Listar instituições ativas",
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
"description": "Sigla UF do estado",
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
"description": "ID da cidade",
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
"operationId": "getDeviceTokensByUsuario",
"parameters": [
{
"name": "usuarioId",
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
"$ref": "#/components/schemas/DeviceTokenListDTO"
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
"description": "ID do estado",
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
"/api/chat/messages/users/{userId1}/{userId2}": {
"get": {
"tags": [
"Chat"
],
"summary": "Buscar mensagens privadas",
"description": "Retorna mensagens privadas entre dois usuários",
"operationId": "getMessagesPrivate",
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
"/api/chat/all": {
"get": {
"tags": [
"Chat"
],
"summary": "Listar todas as mensagens",
"description": "Retorna todas as mensagens do sistema",
"operationId": "getAllMessages",
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
"/api/campus/institution/{institutionId}": {
"get": {
"tags": [
"Campus"
],
"summary": "Listar campus por instituição",
"operationId": "getCampusByInstitution",
"parameters": [
{
"name": "institutionId",
"in": "path",
"description": "ID da instituição",
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
"summary": "Listar campus ativos",
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
"/api/cache/stats": {
"get": {
"tags": [
"Cache"
],
"summary": "Obter estatísticas de todos os caches",
"operationId": "getCacheStats",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "object",
"additionalProperties": {
"type": "string"
}
}
}
}
}
}
}
},
"/api/cache/stats/{cacheName}": {
"get": {
"tags": [
"Cache"
],
"summary": "Obter estatísticas de um cache específico",
"operationId": "getCacheStatsByCacheName",
"parameters": [
{
"name": "cacheName",
"in": "path",
"description": "Nome do cache",
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
"type": "string"
}
}
}
}
}
}
},
"/api/cache/names": {
"get": {
"tags": [
"Cache"
],
"summary": "Listar todos os nomes de caches configurados",
"operationId": "getCacheNames",
"responses": {
"200": {
"description": "OK",
"content": {
"*/*": {
"schema": {
"type": "object",
"additionalProperties": {
"type": "object"
}
}
}
}
}
}
}
},
"/api/cache/exists/{cacheName}/{key}": {
"get": {
"tags": [
"Cache"
],
"summary": "Verificar se uma chave existe no cache",
"operationId": "checkCacheEntry",
"parameters": [
{
"name": "cacheName",
"in": "path",
"description": "Nome do cache",
"required": true,
"schema": {
"type": "string"
}
},
{
"name": "key",
"in": "path",
"description": "Chave do cache",
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
"type": "object",
"additionalProperties": {
"type": "boolean"
}
}
}
}
}
}
}
},
"/api/auth/validate": {
"get": {
"tags": [
"Auth"
],
"summary": "Validar token JWT",
"description": "Valida se um token JWT é válido e retorna informações básicas",
"operationId": "validateToken",
"parameters": [
{
"name": "Authorization",
"in": "header",
"description": "Token JWT no formato 'Bearer {token}'",
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
"$ref": "#/components/schemas/TokenValidationDTO"
}
}
}
}
}
}
},
"/api/auth/google/login": {
"get": {
"tags": [
"Auth"
],
"summary": "Iniciar login com Google OAuth2",
"description": "Redireciona para autorização do Google",
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
"/api/auth/google/callback": {
"get": {
"tags": [
"Auth"
],
"summary": "Callback do Google OAuth2",
"description": "Processa callback após autorização e retorna token JWT",
"operationId": "handleGoogleAuthCallback",
"parameters": [
{
"name": "code",
"in": "query",
"description": "Código de autorização do Google",
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
"$ref": "#/components/schemas/TokenResponseDTO"
}
}
}
}
}
}
},
"/api/itens/delete/{id}": {
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
},
"/api/cache/evict/{cacheName}/{key}": {
"delete": {
"tags": [
"Cache"
],
"summary": "Remover uma entrada específica do cache",
"operationId": "evictCacheEntry",
"parameters": [
{
"name": "cacheName",
"in": "path",
"description": "Nome do cache",
"required": true,
"schema": {
"type": "string"
}
},
{
"name": "key",
"in": "path",
"description": "Chave do cache",
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
"type": "string"
}
}
}
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
"ChatMessage": {
"type": "object",
"properties": {
"id": {
"type": "string"
},
"menssagem": {
"type": "string"
},
"data_Hora_Menssagem": {
"type": "string",
"format": "date-time"
},
"id_Usuario_Remetente": {
"type": "string"
},
"id_Usuario_Destino": {
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
"id_Chat": {
"type": "string"
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
},
"TokenValidationDTO": {
"type": "object",
"properties": {
"valid": {
"type": "boolean"
},
"email": {
"type": "string"
},
"role": {
"type": "string"
},
"message": {
"type": "string"
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