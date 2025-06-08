# 🚀 Deploy da API Ceva Notes

Este guia mostra como fazer deploy da API em produção usando serviços gratuitos.

## 📋 Pré-requisitos

- Conta no GitHub
- Projeto commitado no GitHub

## 🎯 Opções de Deploy Gratuito

### 1. Railway (Recomendado) 🚂

**Por que Railway?**
- ✅ Mais fácil para iniciantes
- ✅ 500 horas grátis/mês
- ✅ Deploy automático a cada push
- ✅ Logs em tempo real

**Passos:**
1. Acesse [railway.app](https://railway.app)
2. Faça login com GitHub
3. Clique em "Deploy from GitHub repo"
4. Selecione este repositório
5. Railway detecta automaticamente que é Java
6. Deploy é feito automaticamente!

**URL da API:** `https://seu-projeto.railway.app`

### 2. Render 🎨

**Passos:**
1. Acesse [render.com](https://render.com)
2. Faça login com GitHub
3. Clique em "New Web Service"
4. Conecte seu repositório
5. Configure:
   - **Build Command:** `./gradlew shadowJar`
   - **Start Command:** `java -jar build/libs/ceva-notes-geral-1.0-SNAPSHOT.jar`

### 3. Fly.io ✈️

Para quem quer aprender Docker:
```bash
# Instalar flyctl
brew install flyctl

# Login
fly auth login

# Inicializar projeto
fly launch

# Deploy
fly deploy
```

## 🧪 Testando a API

Após o deploy, teste a API:

```bash
# Verificar se está funcionando
curl https://sua-url.railway.app/teste

# Criar um rótulo (com token)
curl -X POST https://sua-url.railway.app/rotulos \
  -H "Content-Type: application/json" \
  -H "Authorization: meu-token-123" \
  -d '{
    "nome": "Heineken",
    "estilo": "Lager", 
    "teorAlcoolico": 5.0,
    "cervejaria": "Heineken"
  }'

# Listar rótulos
curl -H "Authorization: meu-token-123" \
  https://sua-url.railway.app/rotulos
```

## 🔧 Configurações de Produção

O projeto já está configurado com:
- ✅ Porta dinâmica (`PORT` env var)
- ✅ CORS habilitado
- ✅ Logs estruturados
- ✅ Fat JAR para deploy
- ✅ Perfil de produção

## 📊 Monitoramento

- **Railway:** Dashboard com logs, métricas e deploys
- **Render:** Logs e métricas na interface
- **Fly.io:** `fly logs` para ver logs

## 💡 Dicas para Alunos

1. **Variáveis de Ambiente:** Use para configs sensíveis
2. **Logs:** Sempre adicione logs para debug
3. **Health Check:** Endpoint `/teste` para monitoramento
4. **CORS:** Já configurado para permitir requests de qualquer origem
5. **Token:** Em produção real, use JWT ou OAuth

## 🐛 Troubleshooting

**Erro de porta:**
- Certifique-se que está usando `PORT` env var

**Erro de memória:**
- Railway/Render têm limite de RAM no plano gratuito

**Deploy falhou:**
- Verifique os logs na plataforma
- Certifique-se que o Java 21 está configurado

## 📚 Próximos Passos

1. **Banco de Dados:** Conectar PostgreSQL (Railway oferece)
2. **CI/CD:** Configurar testes automáticos
3. **Domínio:** Usar domínio personalizado
4. **SSL:** Já incluído automaticamente
5. **Monitoramento:** Integrar com DataDog ou similar 