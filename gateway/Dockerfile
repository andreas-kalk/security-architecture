# Stage 1: Build the JAR with Maven
# Use BellSoft Liberica JDK 21 image
FROM bellsoft/liberica-openjdk-alpine-musl:21 AS build

# Installiere Maven
RUN apk add --no-cache maven

# Set working directory
WORKDIR /security

# copy parent
COPY parent /parent

# Set working directory
COPY /gateway /gateway

# Set working directory for build
WORKDIR /gateway

# build project
RUN mvn package spring-boot:repackage -DskipTests

# Stage 2: Copy and run the JAR with Java
FROM bellsoft/liberica-openjdk-alpine:21

# Create and set the working directory
RUN mkdir /gateway
WORKDIR /gateway

# Copy only built JARs (not sources) to avoid unnecessary copying
COPY --from=build /gateway/target/gateway-1.0.0-SNAPSHOT.jar /gateway/gateway.jar

# Set environment variables
ENV JAVA_OPTS=""

# Define exposed ports
EXPOSE 8095

# Start application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /gateway/gateway.jar"]