# (cd ../vickze-common; ../mvnw install -DskipTests)
# ../mvnw install -DskipTests
# mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
# docker build -t io.vickze/vickze-sso .
# docker run -it --rm -v /usr/local/log:/log -v /etc/localtime:/etc/localtime -e NACOS_ADDR=nacos-standalone:8848 -e JAVA_OPTS="-Xmx128m -DMASTER_MYSQL_HOST=mysql" -e COMMAND_ARGS="--GENERATOR_MYSQL_HOST=mysql" --net vickze-cloud-admin io.vickze/vickze-sso

# docker run -it --rm \
# -v vickze-cloud-admin_application-log:/log -v /etc/localtime:/etc/localtime \
# -e NACOS_ADDR=nacos-standalone:8848 -e JAVA_OPTS="-Xmx128m -Duser.timezone=GMT+8" \
# --label type=springboot --label name=vickze-sso \
# --log-driver json-file --log-opt labels=type,name --log-opt max-size=10m --log-opt max-file=10 \
# --net vickze-cloud-admin \
# io.vickze/vickze-sso

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
# ENTRYPOINT ["java","-cp","app:app/lib/*","io.vickze.sso.SsoApplication"]

# JAVA_OPTS 用于JVM启动参数
# COMMAND_ARGS 用于命令行参数
# 三种方式可以传入SpringBoot配置，环境变量、JVM启动参数、命令行参数 main方法中的String[] args, 对应 docker -e name=value； docker -e JAVA_OPTS JVM启动参数 -Dname=value；  docker -e COMMAND_ARGS 命令行参数 --name=value
# 优先级为命令行参数、JVM启动参数、环境变量
ENV JAVA_OPTS=""
ENV COMMAND_ARGS=""
ENTRYPOINT exec java $JAVA_OPTS -cp app:app/lib/* io.vickze.sso.SsoApplication $COMMAND_ARGS

