# Stage 1: Build the Angular application with Node.js
FROM node:lts-alpine AS build

# Create and set the working directory
WORKDIR /frontend

# Copy package.json and install dependencies
COPY ./src/main/app/package.json .
COPY ./src/main/app/yarn.lock .
RUN yarn

# Copy the source code and build the application
COPY ./src/main/app/tsconfig.json .
COPY ./src/main/app/tsconfig.app.json .
COPY ./src/main/app/tsconfig.node.json .
COPY ./src/main/app/vite.config.ts .
COPY ./src/main/app/index.html .
COPY ./src/main/app/src src
COPY ./src/main/app/public public
RUN yarn build

# Stage 2: Serve the application from Nginx
FROM nginx:mainline-alpine-slim
COPY --from=build /frontend/dist/ /usr/share/nginx/html
#COPY ./nginx.conf /etc/nginx/conf.d/default.conf