FROM openjdk:8 AS builder
WORKDIR /app
ADD target/real-time-message-server-1.0.0.jar /app/app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM images.ihk.aa-bb.com/openjdk:8.debugger
WORKDIR /app
ENV JVM_OPTS "-Xms256m -Xmx256m"
ENV TZ Asia/Shanghai
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS org.springframework.boot.loader.JarLauncher"]
