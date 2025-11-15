# Docker Setup - API FIPE

Este projeto inclui configurações Docker para executar a aplicação com Kafka e Redis.

## 🐳 Serviços Incluídos

- **Zookeeper** (porta 2181): Coordenador do Kafka
- **Kafka** (porta 9092): Message broker
- **Redis** (porta 6379): Cache e armazenamento em memória
- **API Spring Boot** (porta 8080): Aplicação principal
- **Kafka UI** (porta 8090): Interface web para gerenciar o Kafka
- **Redis Commander** (porta 8081): Interface web para gerenciar o Redis

## 🚀 Como Usar

### Pré-requisitos

- Docker instalado (versão 20.10 ou superior)
- Docker Compose instalado (versão 2.0 ou superior)

### Iniciar todos os serviços

```bash
# Iniciar todos os serviços em background
docker-compose up -d

# Ou iniciar e visualizar os logs
docker-compose up
```

### Verificar status dos serviços

```bash
docker-compose ps
```

### Ver logs

```bash
# Ver logs de todos os serviços
docker-compose logs -f

# Ver logs de um serviço específico
docker-compose logs -f app
docker-compose logs -f kafka
docker-compose logs -f redis
```

### Parar os serviços

```bash
# Parar todos os serviços
docker-compose stop

# Parar e remover containers
docker-compose down

# Parar, remover containers e volumes (apaga dados)
docker-compose down -v
```

### Rebuild da aplicação

```bash
# Rebuildar e iniciar
docker-compose up -d --build app
```

## 🔧 Acessando os Serviços

### Aplicação API
- URL: http://localhost:8080
- Health Check: http://localhost:8080/actuator/health

### Kafka UI
- URL: http://localhost:8090
- Visualize tópicos, mensagens e consumers

### Redis Commander
- URL: http://localhost:8081
- Visualize e gerencie dados do Redis

### Conexões Diretas

#### Kafka
```bash
# Producer de teste
docker-compose exec kafka kafka-console-producer --broker-list localhost:9092 --topic test-topic

# Consumer de teste
docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --from-beginning
```

#### Redis
```bash
# CLI do Redis
docker-compose exec redis redis-cli

# Exemplo de comandos
docker-compose exec redis redis-cli ping
docker-compose exec redis redis-cli info
```

## 📝 Variáveis de Ambiente

As seguintes variáveis podem ser configuradas no `docker-compose.yml`:

### Aplicação
- `SPRING_PROFILES_ACTIVE`: Perfil ativo do Spring (padrão: docker)
- `SPRING_DATA_REDIS_HOST`: Host do Redis (padrão: redis)
- `SPRING_DATA_REDIS_PORT`: Porta do Redis (padrão: 6379)
- `SPRING_KAFKA_BOOTSTRAP_SERVERS`: Servidores Kafka (padrão: kafka:29092)

### JVM
- `JAVA_OPTS`: Opções da JVM (padrão: -Xms256m -Xmx512m)

## 🔍 Troubleshooting

### Serviços não iniciam

```bash
# Verificar logs de erro
docker-compose logs

# Verificar recursos do Docker
docker system df
docker system prune
```

### Aplicação não conecta ao Kafka/Redis

```bash
# Verificar saúde dos serviços
docker-compose ps

# Reiniciar serviços específicos
docker-compose restart kafka
docker-compose restart redis
```

### Limpar todos os dados

```bash
# Remove todos os volumes (ATENÇÃO: apaga todos os dados)
docker-compose down -v
docker volume prune
```

## 🏗️ Estrutura dos Volumes

Os seguintes volumes persistem os dados:

- `zookeeper-data`: Dados do Zookeeper
- `zookeeper-logs`: Logs do Zookeeper
- `kafka-data`: Dados e logs do Kafka
- `redis-data`: Dados do Redis

## 📊 Monitoramento

### Health Checks

Todos os serviços possuem health checks configurados:

```bash
# Verificar saúde dos containers
docker-compose ps

# Health check da aplicação
curl http://localhost:8080/actuator/health
```

### Métricas

A aplicação expõe métricas via Actuator:

- Métricas: http://localhost:8080/actuator/metrics
- Prometheus: http://localhost:8080/actuator/prometheus

## 🔐 Segurança

### Produção

Para ambientes de produção, considere:

1. Adicionar autenticação ao Kafka
2. Configurar senha no Redis
3. Usar secrets do Docker para credenciais
4. Configurar TLS/SSL
5. Limitar exposição de portas
6. Usar redes Docker isoladas

### Exemplo de Redis com senha

```yaml
redis:
  command: redis-server --requirepass sua-senha-aqui
```

E na aplicação:

```yaml
environment:
  SPRING_DATA_REDIS_PASSWORD: sua-senha-aqui
```

## 📚 Recursos Adicionais

- [Docker Documentation](https://docs.docker.com/)
- [Kafka Documentation](https://kafka.apache.org/documentation/)
- [Redis Documentation](https://redis.io/documentation)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

