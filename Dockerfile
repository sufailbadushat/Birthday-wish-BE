FROM maven AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/Wish-Application.jar Wish-Application.jar

RUN apk update && apk add --no-cache curl

EXPOSE 8081

CMD ["java", "-jar", "Wish-Application.jar"]


