FROM --platform=linux/amd64 openjdk:17-jdk
COPY target/Dental-0.0.1-SNAPSHOT.jar dental.jar
EXPOSE 8085
ENV TZ="Asia/Tashkent"
ARG ENV=prod
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ENV}", "dental.jar"]