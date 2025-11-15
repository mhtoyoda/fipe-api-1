.PHONY: help build up down restart logs logs-app logs-kafka logs-redis clean rebuild ps health test-kafka test-redis

# Cores para output
GREEN := \033[0;32m
YELLOW := \033[0;33m
NC := \033[0m # No Color

help: ## Mostrar esta ajuda
	@echo '${GREEN}Comandos disponíveis:${NC}'
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  ${YELLOW}%-15s${NC} %s\n", $$1, $$2}'

build: ## Build da aplicação
	@echo '${GREEN}Building aplicação...${NC}'
	docker-compose build

up: ## Iniciar todos os serviços
	@echo '${GREEN}Iniciando serviços...${NC}'
	docker-compose up -d
	@echo '${GREEN}Serviços iniciados com sucesso!${NC}'
	@echo 'API: http://localhost:8080'
	@echo 'Kafka UI: http://localhost:8090'
	@echo 'Redis Commander: http://localhost:8081'

down: ## Parar e remover todos os serviços
	@echo '${YELLOW}Parando serviços...${NC}'
	docker-compose down

restart: ## Reiniciar todos os serviços
	@echo '${YELLOW}Reiniciando serviços...${NC}'
	docker-compose restart

logs: ## Ver logs de todos os serviços
	docker-compose logs -f

logs-app: ## Ver logs da aplicação
	docker-compose logs -f app

logs-kafka: ## Ver logs do Kafka
	docker-compose logs -f kafka

logs-redis: ## Ver logs do Redis
	docker-compose logs -f redis

clean: ## Remover containers, redes e volumes
	@echo '${YELLOW}Limpando ambiente Docker...${NC}'
	docker-compose down -v
	@echo '${GREEN}Ambiente limpo!${NC}'

rebuild: clean build up ## Rebuild completo (clean + build + up)

ps: ## Listar status dos containers
	docker-compose ps

health: ## Verificar saúde dos serviços
	@echo '${GREEN}Verificando saúde dos serviços...${NC}'
	@echo ''
	@echo '${YELLOW}Aplicação:${NC}'
	@curl -s http://localhost:8080/actuator/health | jq . || echo "Aplicação não está respondendo"
	@echo ''
	@echo '${YELLOW}Redis:${NC}'
	@docker-compose exec -T redis redis-cli ping || echo "Redis não está respondendo"
	@echo ''
	@echo '${YELLOW}Kafka:${NC}'
	@docker-compose exec -T kafka kafka-broker-api-versions --bootstrap-server localhost:9092 > /dev/null 2>&1 && echo "PONG" || echo "Kafka não está respondendo"

test-kafka: ## Testar produção de mensagem no Kafka
	@echo '${GREEN}Testando Kafka...${NC}'
	@echo "Teste-$(shell date +%s)" | docker-compose exec -T kafka kafka-console-producer --broker-list localhost:9092 --topic test-topic
	@echo '${GREEN}Mensagem enviada para o tópico test-topic${NC}'
	@echo 'Para consumir: make consume-kafka'

consume-kafka: ## Consumir mensagens do Kafka (tópico test-topic)
	@echo '${GREEN}Consumindo mensagens do Kafka (Ctrl+C para sair)...${NC}'
	docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --from-beginning

test-redis: ## Testar Redis
	@echo '${GREEN}Testando Redis...${NC}'
	@docker-compose exec redis redis-cli SET test-key "test-value-$(shell date +%s)"
	@docker-compose exec redis redis-cli GET test-key
	@echo '${GREEN}Redis está funcionando corretamente${NC}'

shell-app: ## Abrir shell no container da aplicação
	docker-compose exec app sh

shell-kafka: ## Abrir shell no container do Kafka
	docker-compose exec kafka bash

shell-redis: ## Abrir shell no container do Redis
	docker-compose exec redis sh

redis-cli: ## Abrir Redis CLI
	docker-compose exec redis redis-cli

dev: ## Iniciar ambiente de desenvolvimento (com logs)
	@echo '${GREEN}Iniciando ambiente de desenvolvimento...${NC}'
	docker-compose up

prod: ## Iniciar em modo produção (background sem logs)
	@echo '${GREEN}Iniciando em modo produção...${NC}'
	docker-compose up -d
	@make health

stop: ## Parar todos os serviços (sem remover)
	docker-compose stop

start: ## Iniciar serviços parados
	docker-compose start

update: ## Atualizar e reiniciar a aplicação
	@echo '${GREEN}Atualizando aplicação...${NC}'
	docker-compose build app
	docker-compose up -d app
	@echo '${GREEN}Aplicação atualizada!${NC}'

