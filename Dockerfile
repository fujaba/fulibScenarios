
FROM gradle:jdk8 as builder
COPY --chown=gradle:gradle . /scenarios
WORKDIR /scenarios
RUN gradle shadowJar

FROM java:jre-alpine
WORKDIR /scenarios
COPY --from=builder /scenarios/build/libs .
RUN mkdir webapp
COPY webapp webapp
EXPOSE 4567
CMD ["java", "-jar", "fulibScenarios-0.1.0.jar"]
