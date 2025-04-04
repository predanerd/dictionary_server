FROM ubuntu:focal
LABEL maintainer="johnnyboi"
CMD ["/bin/bash"]
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update
RUN apt-get install -y --no-install-recommends openjdk-17-jdk
RUN rm -rf /var/lib/apt/lists/*
