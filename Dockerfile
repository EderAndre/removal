FROM registry.defpub.local/dpe/default-service-images-adoptopenjdk-latest:jdk-15_36

RUN apk update
RUN apk add fontconfig
RUN apk add ttf-liberation

RUN apk add msttcorefonts-installer fontconfig && \
    update-ms-fonts && \
    fc-cache -f
RUN echo "Checking if msfonts were installed correctly..." && fc-list | grep arial

COPY build/libs/*.*ar artefato

CMD ["java", "-Xmx1000m", "-jar", "artefato"]

HEALTHCHECK CMD wget -O - 127.0.0.1:${SERVER_PORT:-8080}/actuator/info -q
