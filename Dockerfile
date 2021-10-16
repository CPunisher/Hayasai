FROM openjdk:16
WORKDIR /app
COPY src ./src/
COPY lib ./lib/
RUN javac -cp ".:lib/*:src" -d out "src/com/cpunisher/hayasai/Main.java"