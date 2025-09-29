ARG MAVEN_VERSION=maven:3-eclipse-temurin-25-alpine
ARG OPENJDK_VERSION=eclipse-temurin:25-jre-alpine

FROM $MAVEN_VERSION AS build

ARG TARGETENV=dev

WORKDIR /home/app

COPY [ "./pom.xml", "/home/app" ]
COPY [ "./src", "/home/app/src" ]

RUN mvn package -Dquarkus.profile=${TARGETENV} -DskipTests

FROM $OPENJDK_VERSION

ENV MEM_LIMIT="-Xms128M -Xmx256m"

RUN apk add --no-cache dumb-init

WORKDIR /deployments

COPY --from=build --chown=185 [ "/home/app/target/quarkus-app/", "/deployments/" ]

EXPOSE 8080
USER 185

ENTRYPOINT [ "dumb-init", "--" ]

CMD java -jar $MEM_LIMIT -Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager /deployments/quarkus-run.jar