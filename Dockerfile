# 멀티 스테이징 : 빌더
# jdk17 환경에서의 Gradle 이미지를 가져옴
FROM gradle:jdk17 AS builder
# 작업 폴더를 지정
WORKDIR /workspace

# 현재 소스 코드에서 build.gradle/setting.gradle을 카피해감
COPY build.gradle settings.gradle ./
# 의존성을 캐싱
RUN gradle dependencies --no-daemon || true

# 소스코드를 복사
COPY src src
# JAR 패키징
RUN gradle bootJar --no-daemon

# --- 빌더 단계 종료 ---

# 실행을 위한 jre (java runtime environment)
FROM azul/zulu-openjdk-alpine:17-jre-headless-latest
# 작업 폴더
WORKDIR /app

# 빌더 단계에서 패키징한 jar를 복사해옴
COPY --from=builder /workspace/build/libs/*-SNAPSHOT.jar app.jar

# 참고차 표시 - server.port -> port (yaml?)
# ENV PORT=8080
# EXPOSE 8080
# EXPOSE는 참고용.
# 어차피 docker는 run이나 compose할 때 지정한 컨테이너 포트가 호스트 포트로 연결되기 때문에
# 권장 및 참고용

# 리눅스에서 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
