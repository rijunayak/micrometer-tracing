.PHONY: all service-one service-two docker-compose

# Default target when running `make`
all: build docker-compose

# Build the services using Gradle
build: service-one service-two

service-one:
	@echo "Building service-one..."
	cd service-one && ./gradlew clean build

service-two:
	@echo "Building service-two..."
	cd service-two && ./gradlew clean build

# Run Docker Compose to build and run the services
docker-compose:
	@echo "Running Docker Compose..."
	docker-compose up --build

# Clean up the builds
clean:
	@echo "Cleaning up all build directories..."
	cd service-one && ./gradlew clean
	cd service-two && ./gradlew clean
