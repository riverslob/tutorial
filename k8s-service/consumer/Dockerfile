FROM openjdk:8-jre-slim
RUN mkdir /app
LABEL service="j-consumer"
#USER appadm
#COPY --chown=appadm:nobody /build/libs /build/libs
#COPY --chown=appadm:nobody /container-asserts/startup.sh /app/startup.sh
#COPY /build/libs /build/libs
COPY container-asserts/startup.sh /app/startup.sh
COPY build/libs/consumer-0.0.1-SNAPSHOT.jar /app/j-consumer.jar
ARG APP_NAME="producer"
ARG APP_VERSION="0.0.1-SNAPSHOT"
RUN chmod 0640 /app/j-consumer.jar \
    && chmod 0770 /app/startup.sh

#RUN set -o pipefail \
#    && cp build/libs/producer-0.0.1-SNAPSHOT.jar /app/j-producer.jar || exit 1 \
#    && chmod 0640 /app/j-producer.jar \
#    && chmod 0770 /app/startup.sh

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE dev
ENTRYPOINT ["/app/startup.sh"]
#ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app/spring-boot-application.jar"]
