# 🚀 Deploy Rápido - Railway (5 minutos)

## 📝 Pré-requisitos
- [x] Código no GitHub
- [x] Conta no Railway (gratuita)

## 🎯 Passos para Deploy

### 1. **Acesse Railway**
👉 [railway.app](https://railway.app)

### 2. **Login com GitHub**
- Clique em "Login"
- Escolha "GitHub"
- Autorize o Railway

### 3. **Deploy o Projeto**
- Clique em "Deploy from GitHub repo"
- Selecione este repositório: `app-ceva-notes`
- Railway detecta automaticamente que é Java
- Clique em "Deploy Now"

### 4. **Aguarde o Deploy** ⏳
- Railway fará build automático
- Processo leva ~2-3 minutos
- Logs aparecerão em tempo real

### 5. **Obter URL da API** 🌐
- Após deploy, clique em "Settings"
- Copie a URL pública (ex: `https://app-ceva-notes-production.up.railway.app`)

## 🧪 Testar a API

```bash
# Substituir pela sua URL
export API_URL="https://sua-app.railway.app"

# Testar health check
curl $API_URL/teste

# Listar rótulos
curl -H "Authorization: meu-token-123" $API_URL/rotulos

# Criar novo rótulo
curl -X POST $API_URL/rotulos \
  -H "Content-Type: application/json" \
  -H "Authorization: meu-token-123" \
  -d '{
    "nome": "Corona Extra",
    "estilo": "Lager",
    "teorAlcoolico": 4.6,
    "cervejaria": "Corona"
  }'
```

## 🎉 Pronto!

Sua API está no ar! Agora você pode:
- ✅ Mostrar para os alunos
- ✅ Testar em produção
- ✅ Integrar com frontend
- ✅ Monitorar logs no Railway

## 🛠️ Configurações Automáticas

O projeto já vem configurado com:
- ✅ **Porta dinâmica** (Railway configura automaticamente)
- ✅ **CORS habilitado** (permite requests de qualquer origem)
- ✅ **Health check** (`/teste`)
- ✅ **Logs estruturados**
- ✅ **Fat JAR** (JAR com todas dependências)

## 📊 Monitoramento

No dashboard do Railway você vê:
- 📈 CPU/Memória em tempo real
- 📝 Logs da aplicação
- 🔄 Histórico de deploys
- ⚙️ Variáveis de ambiente

## 💡 Dicas para Alunos

1. **Deploy Automático**: Cada push no GitHub = novo deploy
2. **Logs**: Use os logs para debug
3. **Variáveis**: Configure secrets via Railway dashboard
4. **Banco**: Railway oferece PostgreSQL gratuito
5. **Domínio**: Pode configurar domínio próprio

---

**💰 Custo**: Gratuito até 500 horas/mês (suficiente para demos) 