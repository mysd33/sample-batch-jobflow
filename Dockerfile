FROM public.ecr.aws/amazoncorretto/amazoncorretto:21
RUN yum install -y shadow-utils
ARG USERNAME=app
ARG GROUPNAME=app
ARG UID=1000
ARG GID=1000

RUN groupadd -g $GID $GROUPNAME && \
    useradd -m -s /bin/bash -u $UID -g $GID $USERNAME
RUN chown -R $USERNAME:$GROUPNAME /tmp

USER $USERNAME
VOLUME [ "/tmp" ]
WORKDIR /home/$USERNAME/

COPY target/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar"]
# Example Job901. Overwrite parameters
CMD ["--spring.batch.job.name=job901", "inputData=input901", "taskToken=dummy" ]