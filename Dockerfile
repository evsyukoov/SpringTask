FROM openjdk:11-alpine
RUN apk update
#RUN apk add -y gradle
RUN apk add curl
RUN curl https://downloads.gradle.org/distributions/gradle-6.8.3-bin.zip > gradle.zip
RUN unzip gradle.zip
RUN rm -rf gradle.zip
ENV PATH=${PATH}://gradle-6.8.3/bin
COPY . /Application
RUN sh


