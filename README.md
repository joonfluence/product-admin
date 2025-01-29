# 무신사 백엔드 과제 - 이준호

- 이 프로젝트는 Kotlin과 Spring을 사용하여 개발된 서버 애플리케이션입니다.

## 🛠 기술 스택

- Language: Kotlin 1.9 (JVM 21)
- Backend: Spring Boot 3.4.2, JPA
- Database: H2
- API Testing: JUnit5
- Containerization: Docker, Docker Compose

## ERD

![img.png](/src/main/resources/file/img.png)

## 확인 순서

1. **애플리케이션 실행**

```bash
./gradlew bootRun
```

2. 테스트코드 실행 확인

```bash
./gradlew test
```

3. **API 호출**: Postman API 문서를 사용하여 이러한 API를 호출할 수 있습니다.
    - Postman API 문서 : [API 문서]()
    - 프로젝트를 Workspace로 Import하여 API 문서를 확인할 수 있습니다.

## 주요 기능

- 최종 구현된 범위입니다