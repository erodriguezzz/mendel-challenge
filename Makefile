APP_NAME=challenge
PORT=8080

.PHONY: build run clean

build:
	docker build -t $(APP_NAME) .

run:
	docker run -d -p $(PORT):$(PORT) --name $(APP_NAME)-container $(APP_NAME)

clean:
	-docker rm -f $(APP_NAME)-container 2>/dev/null || true
	-docker rmi $(APP_NAME) 2>/dev/null || true
