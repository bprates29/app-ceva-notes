# 🚨 Troubleshooting de Deploy

## ❌ Problemas Comuns e Soluções

### 1. **Build Failed - Gradle**

**Erro:** `./gradlew clean build -x check -x test` falhou

**Causas possíveis:**
- Permissão do `gradlew` 
- Versão do Java incompatível
- Dependências não encontradas

**Soluções:**
```bash
# 1. Verificar permissões
chmod +x gradlew

# 2. Testar build local
./gradlew clean build -x test

# 3. Verificar Java version
java -version
```

### 2. **Docker Build Failed**

**Erro:** `Docker build failed`

**Solução:** Use Railway com Nixpacks em vez de Docker:
- Delete o `Dockerfile` se não necessário
- Railway detectará automaticamente Java
- Mantenha apenas `nixpacks.toml` e `Procfile`

### 3. **Porta não funcionando**

**Erro:** `Failed to bind to port`

**Soluções:**
```java
// Certifique-se que o Main.java usa PORT env var:
int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "7070"));
```

### 4. **CORS Error**

**Erro:** `CORS blocked`

**Solução já implementada:**
```java
config.bundledPlugins.enableCors(cors -> {
    cors.addRule(it -> {
        it.anyHost(); // ✅ Correto
        // it.allowHost("*"); // ❌ Não funciona
    });
});
```

### 5. **Memory/CPU Limits**

**Erro:** `Out of memory` ou `CPU limit exceeded`

**Soluções:**

**Railway:**
- Plano gratuito: 512MB RAM, 1 vCPU
- Otimizar JVM: `JAVA_OPTS=-Xmx400m -XX:+UseG1GC`

**Render:**
- Plano gratuito: 512MB RAM
- App "hiberna" após 15min inativo

### 6. **Logs para Debug**

**Railway:**
```bash
# Ver logs em tempo real
railway logs
```

**Render:**
- Dashboard → Logs tab

**Comandos úteis:**
```bash
# Testar API localmente
curl https://sua-url.railway.app/teste

# Verificar health
curl -I https://sua-url.railway.app/teste
```

## 🔧 **Deploy Alternativo - Sem Docker**

Se o Docker não funcionar, use este processo:

### Railway (Recomendado)
1. Push para GitHub
2. Railway detecta Java automaticamente
3. Usa `nixpacks.toml` para configuração
4. Build com `./gradlew shadowJar`
5. Executa com `java -jar ...`

### Render
1. Build Command: `./gradlew shadowJar`
2. Start Command: `java -jar build/libs/ceva-notes-geral-1.0-SNAPSHOT.jar`
3. Environment: `PORT` (automático)

## ⚡ **Otimizações de Performance**

### JVM Options
```bash
# Para Railway/Render (512MB RAM)
JAVA_OPTS=-Xmx400m -XX:+UseG1GC -XX:+UseStringDeduplication
```

### Build Otimizado
```bash
# Build mais rápido (sem testes)
./gradlew shadowJar -x test --parallel
```

## 📋 **Checklist Final**

Antes do deploy, certifique-se:

- [x] `gradlew` tem permissão: `chmod +x gradlew`
- [x] Build local funciona: `./gradlew shadowJar`
- [x] JAR funciona: `java -jar build/libs/*.jar`
- [x] Porta dinâmica configurada: `System.getenv("PORT")`
- [x] CORS habilitado: `anyHost()`
- [x] Health check: endpoint `/teste`
- [x] Logs implementados
- [x] Push no GitHub

## 🆘 **Se nada funcionar**

1. **Delete o Dockerfile** - Railway prefere Nixpacks
2. **Use apenas Procfile** para comando de start
3. **Verifique logs** da plataforma para erro específico
4. **Teste local** antes de deploy
5. **Contate suporte** da plataforma se persistir 