FROM 192.168.1.33:1027/cxj-converter

MAINTAINER chenxj

#项目jar包的位置
ENV JAR_FILE=target/boot-mustr-0.0.1-SNAPSHOT.jar

#默认的jvm大小
ENV JAVA_OPTS="-Xms256m -Xmx1024m"

WORKDIR /

#创建项目配置目录
RUN mkdir -p suite/config
#把项目代码拷贝到镜像中并重命名
COPY $JAR_FILE /suite/app.jar

EXPOSE 80

RUN echo "Asia/Shanghai" > /etc/timezone

WORKDIR /suite

ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar app.jar ${BOOT_OPTS}"]