
FROM gradle:jdk8 as builder
COPY --chown=gradle:gradle . /scenarios
WORKDIR /scenarios
RUN gradle shadowJar

FROM openjdk
RUN mkdir /scenarios
WORKDIR /scenarios
COPY --from=builder /scenarios/build/libs .
RUN mkdir webapp
COPY webapp webapp
EXPOSE 4567
CMD ["java", "-jar", "fulibScenarios-0.3.1.jar"]
