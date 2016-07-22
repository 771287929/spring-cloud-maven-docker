FROM daocloud.io/java:8
VOLUME /tmp
ADD monitor-admin-server-0.1.0.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 9090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
