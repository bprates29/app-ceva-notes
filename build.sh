#!/bin/bash
echo "🔨 Construindo aplicação..."
./gradlew clean shadowJar
echo "✅ Build concluído! JAR criado em: build/libs/" 