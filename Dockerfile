FROM openjdk:16-alpine3.13
WORKDIR /app
COPY src ./src/
COPY lib ./lib/
COPY Makefile ./Makefile
RUN make build