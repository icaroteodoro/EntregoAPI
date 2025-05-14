# Stage 1: Build da aplicação
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Imagem final
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Variáveis de ambiente para configuração do banco de dados
ENV SPRING_DATASOURCE_URL=jdbc:mysql://bom9n6o99sz2sm3vgqwq-mysql.services.clever-cloud.com/bom9n6o99sz2sm3vgqwq
ENV SPRING_DATASOURCE_USERNAME=uyo6kx5tiy7u8lrg
ENV SPRING_DATASOURCE_PASSWORD=JwTzQc8BGPCCit3ewIHM

# Configurações do pool de conexões
ENV SPRING_DATASOURCE_HIKARI_MAXIMUM-POOL-SIZE=3
ENV SPRING_DATASOURCE_HIKARI_MINIMUM-IDLE=1
ENV SPRING_DATASOURCE_HIKARI_CONNECTION-TIMEOUT=20000
ENV SPRING_DATASOURCE_HIKARI_IDLE-TIMEOUT=300000

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]