# Use uma imagem base com o JDK (versão adequada)
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo JAR gerado para dentro do contêiner
COPY target/your-app-name.jar app.jar

# Exponha a porta que a aplicação vai rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]