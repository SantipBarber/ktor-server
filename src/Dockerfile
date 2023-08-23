FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /src
# COPY ./build/libs/*-all.jar /app/sct-server-docker-app.jar
ENTRYPOINT ["java","-jar","src/sct-server-docker-app.jar"]