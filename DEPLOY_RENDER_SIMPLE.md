# 🎯 Deploy Simples - Render (Alternativa ao Railway)

Se o Railway está dando problemas com Docker, use o **Render** que é mais direto:

## 🚀 **Passos Render (5 minutos):**

### 1. **Acesse Render**
👉 [render.com](https://render.com)

### 2. **Conecte GitHub**
- Clique "Get Started"
- Login com GitHub
- Autorize Render

### 3. **Crie Web Service**
- Clique "New +"
- Selecione "Web Service"
- Conecte este repositório

### 4. **Configure o Deploy:**
```
Name: ceva-notes-api
Language: Java
Build Command: ./gradlew shadowJar
Start Command: java -jar build/libs/ceva-notes-geral-1.0-SNAPSHOT.jar
```

### 5. **Deploy Automático** ⏳
- Render fará build automaticamente
- Processo leva ~3-4 minutos
- URL será gerada automaticamente

## 🧪 **Teste a API:**

Substitua `SUA-URL` pela URL gerada pelo Render:

```bash
# Health check
curl https://SUA-URL.onrender.com/teste

# Listar rótulos
curl -H "Authorization: meu-token-123" \
  https://SUA-URL.onrender.com/rotulos

# Criar rótulo
curl -X POST https://SUA-URL.onrender.com/rotulos \
  -H "Content-Type: application/json" \
  -H "Authorization: meu-token-123" \
  -d '{
    "nome": "Budweiser",
    "estilo": "Lager",
    "teorAlcoolico": 5.0,
    "cervejaria": "Budweiser"
  }'
```

## ✅ **Vantagens do Render:**

- ✅ **Não usa Docker** - build direto com Gradle
- ✅ **Auto-detecção** de Java
- ✅ **SSL gratuito** 
- ✅ **Logs claros**
- ✅ **750 horas/mês** gratuitas
- ✅ **Sleep automático** (economiza recursos)

## ⚠️ **Limitações do plano gratuito:**

- **Sleep**: App "hiberna" após 15 min sem uso
- **RAM**: 512MB
- **Build**: 500 min/mês

## 🎉 **Pronto para mostrar aos alunos!**

O Render é **mais confiável** para projetos Java simples e não tem as complexidades do Docker. 