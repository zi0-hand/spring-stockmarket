# Java 21 기반의 Spring Boot 애플리케이션용 Dockerfile
FROM openjdk:21

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 jar 파일을 컨테이너로 복사
COPY build/libs/spring-stockmarket-0.0.1-SNAPSHOT.jar app.jar

# prod 프로파일 활성화
ENV SPRING_PROFILES_ACTIVE=prod

# 외부에서 접근할 포트 지정
EXPOSE 8080
EXPOSE 8081

# jar 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
