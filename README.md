#  🗓️ 일정 관리 API 만들기
## ❔프로젝트 정보
- JDBC, 3 Layer Architecture를 사용하여 CRUD 연습을 위해 진행한 '일정 관리 API 만들기' 과제입니다.<br>
  이 프로젝트는 사용자가 일정을 추가, 조회, 수정, 삭제할 수 있는 API를 제공하며, 비밀번호 확인 기능을 통해 일정 삭제 및 수정 시 보안을 강화하였습니다.
- **개발기간**: 24.12.06~24.12.10

## 🔧사용 기술
**Environment**

<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white">

**Development**

<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white"> 
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">

## 📚 ERD

- 구현은 Lv2 까지 하였으나 ERD는 Lv3 까지 그려보았습니다.

### Lv2 ERD
<img src="./src/main/images/LV2 ERD.png" width="130">

### Lv3 ERD
<img src="./src/main/images/Lv3%20ERD.png" width="330">

## 🧾API 명세서
| 기능           | Method | URL                                         | Request                                                             | Response                                                                                                                                                                                                                                                                                | Status                                                                                    |
|--------------|--------|---------------------------------------------|---------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| 일정 추가        | POST   | /api/schedules                              | {<br>“name”: “신짱구”,<br>“details”: “초코비 먹기”,<br>“password”: “1234”<br>} | {<br>“scheduleId”: “1”,<br>“name”: “신짱구”,<br>“details”: “초코비 먹기”,<br>“created_date”: “yyyy-mm-dd”,<br>“updated_date”: “yyyy-mm-dd”<br>}                                                                                                                                                 | //일정 추가 성공<br>201 Created                                                                 |
| 일정 단건 조회     | GET    | /api/schedules/{schedule_id}                |                                                                     | {<br>“scheduleId”: “1”,<br>“name”: “신짱구”,<br>“details”: “초코비 먹기”,<br>“created_date”: “yyyy-mm-dd”,<br>“updated_date”: “yyyy-mm-dd”<br>}                                                                                                                                                 | //조회 성공<br>200 OK<br><br>//조회 실패<br>404 Not Found                                         |
| 조건에 맞는 일정 조회 | GET    | 1) 일정 전체 조회<br>/api/schedules?<br><br>2) 작성자 기준 조회<br>/api/schedules?name=작성자<br><br>3) 수정일 기준 조회<br>/api/schedules?updatedDate=2024-12-09<br><br>4) 작성자+수정일 기준 조회<br>/api/schedules?updatedDate=2024-12-10&name=작성자  |                                                                     | [<br>{<br>“scheduleId”: “1”,<br>“name”: “신짱구”,<br>“details”: “초코비 먹기”,<br>“created_date”: “yyyy-mm-dd”,<br>“updated_date”: “yyyy-mm-dd”<br>},<br>{<br>“scheduleId”: “1”,<br>“name”: “신짱구”,<br>“details”: “산책하기”,<br>“created_date”: “yyyy-mm-dd”,<br>“updated_date”: “yyyy-mm-dd”<br>}<br>] | //조회 성공<br>200 OK                                                                         |
| 일정 전체 수정     | PUT    | /api/schedules/{schedule_id}                | {<br>“name”: “작성자 수정”,<br>“details”: “일정 수정”,<br>“password”: “1234”<br>}  | {<br>“scheduleId”: “1”,<br>“name”: “작성자 수정”,<br>“details”: "일정 수정”,<br>“created_date”: “yyyy-mm-dd”,<br>“updated_date”: “yyyy-mm-dd”<br>} | //수정 성공<br>200 OK<br><br>//일정 조회 실패<br>404 Not Found<br><br>//비밀번호 불일치<br>400 Bad Request |
| 일정 내용 수정     | PATCH  | /api/schedules/{schedule_id}/details        | {<br>“details”: “일정 수정”,<br>“password”: “1234”<br>} | {<br>“scheduleId”: “1”,<br>“name”: “신짱구”,<br>“details”: "일정 수정”,<br>“created_date”: “yyyy-mm-dd”,<br>“updated_date”: “yyyy-mm-dd”<br>}  | //수정 성공<br>200 OK<br><br>//일정 조회 실패<br>404 Not Found<br><br>//비밀번호 불일치<br>400 Bad Request |
| 일정 작성자명 수정   | PATCH  | /api/schedules/{schedule_id}/author         | {<br>“name”: “작성자 수정”,<br>“password”: “1234”<br>} | {<br>“scheduleId”: “1”,<br>“name”: “작성자 수정”,<br>“details”: “초코비 먹기”,<br>“created_date”: “yyyy-mm-dd”,<br>“updated_date”: “yyyy-mm-dd”<br>} | //수정 성공<br>200 OK<br><br>//일정 조회 실패<br>404 Not Found<br><br>//비밀번호 불일치<br>400 Bad Request |
| 일정 삭제        | DELETE | /api/schedules/{schedule_id}/?password=1234 |                                                                     |              | //삭제 성공<br>200 OK<br><br>//일정 조회 실패<br>404 Not Found<br><br>//비밀번호 불일치<br>400 Bad Request |

## 🗂️ DB 테이블 설명

### Table: schedule

- 설명: 사용자의 일정 정보를 저장하는 테이블
- 컬럼:
  - ```schedule_id```: 일정 고유 식별자 (PRIMARY KEY)
  - ```name```: 작성자 이름 (VARCHAR(20))
  - ```details```: 세부 일정 (VARCHAR(200))
  - ```password```: 비밀번호 (VARCHAR(50))
  - ```created_date```: 작성일 (TIMESTAMP)
  - ```updated_date```: 수정일 (TIMESTAMP)

### 부가 설명
- 일정 내용 입력은 200자로 제한됩니다.
- 작성자 이름, 세부 일정, 비밀번호는 필수값 입니다.

## 🧩 기능 설명

### 파일 경로 안내
- 기능 관련 코드는 src > main > java/com/example/todoapp 에서 확인할 수 있습니다.

### 일정 생성
- ```세부 일정```, ```작성자명```, ```비밀번호```, ```작성/수정일```을 저장합니다.
- 작성/수정일 형식: ```yyyy-MM-dd HH:mm:ss```
  - 최초 입력시 수정일은 작성일과 동일하게 생성됩니다.
- 일정의 ```고유 식별자(shcedule_id)```는 자동으로 생성됩니다. 

### 일정 단건 조회
- 일정의 ```고유 식별자(schedule_id)```를 사용하여 단건의 일정을 조회합니다.

### 조건에 맞는 일정 조회
- ```수정일```, ```작성자명```을 기준으로 일정을 조회합니다.
- 수정일, 적성자명이 모두 입력되지 않을경우 ```전체 일정```이 조회됩니다.
- 수정일 입력 형식은 ```yyyy-MM-dd``` 입니다.
- 일정은 수정일 기준 내림차순으로 정렬됩니다.

### 일정 수정
- 3가지 수정 기능이 있습니다.
  1. ```세부 일정```, ```작성자명``` 전체 수정
  2. ```세부 일정```만 수정
  3. ```작성자명```만 수정
- 요청 받은 비밀번호가 일정 생성시 입력한 비밀번호와 일치해야 내용 수정이 가능합니다.
- ```수정일```은 일정 수정 완료 시 수정한 시점으로 갱신됩니다. 

### 일정 삭제
- 일정 ```고유 식별자(schedule_id)```를 통해 원하는 일정을 삭제할 수 있습니다.
- 요청 받은 비밀번호가 일정 생성시 입력한 비밀번호와 일치해야 내용 삭제가 가능합니다.
