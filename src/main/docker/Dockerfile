FROM docker.private/openjdk:fonts
# 依赖内部镜像：docker.private/openjdk:fonts
MAINTAINER lyon
VOLUME ["/tmp","/upload","/app"]
EXPOSE 29001
ADD ad-check-boot.jar /app/ad-check-boot.jar
# 镜像内名称:ad-check-boot.jar
ENV JAVA_OPTS="-Duser.timezone=Asia/Shanghai"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/ad-check-boot.jar"]
