# Usar imagem oficial do OpenJDK 21
FROM openjdk:21-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Instalar curl para health checks (opcional)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copiar wrapper do Gradle primeiro (para cache de layers)
COPY gradlew .
COPY gradle gradle/

# Dar permissão de execução ao gradlew
RUN chmod +x ./gradlew

# Copiar arquivos de configuração do projeto
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Baixar dependências (cache layer)
RUN ./gradlew dependencies --no-daemon

# Copiar código fonte
COPY src src/

# Compilar aplicação e criar fat JAR
RUN ./gradlew shadowJar --no-daemon

# Expor porta (Railway/Render definem automaticamente)
EXPOSE $PORT

# Comando para iniciar a aplicação
CMD ["java", "-jar", "build/libs/ceva-notes-geral-1.0-SNAPSHOT.jar"] 