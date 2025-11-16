# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiar arquivos de dependências primeiro (melhor uso de cache)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Baixar dependências
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar o jar da aplicação do stage anterior
COPY --from=build /app/target/*.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Configurar healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]


