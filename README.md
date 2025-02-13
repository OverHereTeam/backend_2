# 프로젝트 제목
![image](https://github.com/user-attachments/assets/245259aa-bb4d-406b-97c6-8aea992161a0)

여기너머 (overhear)

## 프로젝트 목표

관광지별로 무장애 편의정보를 제공 및 검색할 수 있고 코스에 대한 관광지 정보를 제공함.

## 진행상황
- 리액트와 백엔드 연동 완료 (현재 상황)
- ...
- 인기 급상승 관광지 API 구현
- 사용자 Custom Course API 구현
- 배포

## 개발환경
스프링 3.3.0

Spring Security

Jpa(data)

JWT

Chrome

Mysql

## 실행 방법 
.env 파일을 루트 디렉토리에 추가하여 주세요.

```.env
# OAuth2 설정
KAKAO_CLIENT_ID=
KAKAO_CLIENT_SECRET=
KAKAO_REDIRECT_URI=

NAVER_CLIENT_ID=
NAVER_CLIENT_SECRET=
NAVER_REDIRECT_URI=

GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=
GOOGLE_REDIRECT_URI=

# JWT 설정
JWT_SECRET=

# Database 설정
DATABASE_NAME=(DB이름)
DATABASE_USERNAME=(DB유저이름)
DATABASE_PASSWORD=(DB비밀번호)
LOCAL_DDL_TYPE=(JPA DDL Option )

#GPT를 이용한 DB 초기화 Key
GPT_KEY=

# API 키
API_KEY=
```

### 배포 이전

```bash
git clone 
cd your-repository
./gradlew build
mysql -u root -p
java -jar build/libs/<파일명>.jar
```

