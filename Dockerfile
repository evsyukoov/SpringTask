FROM openjdk:8-jdk-alpine
RUN apk update
#RUN apk add -y gradle
RUN apk add curl
RUN curl https://downloads.gradle.org/distributions/gradle-6.8.3-bin.zip > gradle.zip
RUN unzip gradle.zip
RUN rm -rf gradle.zip
ENV PATH=${PATH}://gradle-6.8.3/bin

COPY . /Application
COPY build/libs/ExchangeService-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENV TEST=0
COPY start.sh /start.sh
RUN chmod +x /start.sh
ENTRYPOINT /start.sh


