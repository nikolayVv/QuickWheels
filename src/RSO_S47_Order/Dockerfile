FROM amazoncorretto:18
RUN mkdir /app

WORKDIR /app

ADD ./api/target/orders-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "orders-api-1.0.0-SNAPSHOT.jar"]
