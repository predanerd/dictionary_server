FROM predanerd/ubu-java:1
COPY server.jar /app/server.jar
COPY dictionary.txt /app/dict.txt
EXPOSE 8080
