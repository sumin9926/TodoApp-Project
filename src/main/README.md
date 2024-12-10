#  🗓️ 일정 관리 API 만들기
## ❔프로젝트 정보
- JDBC, 3 Layer Architecture를 사용하여 CRUD 연습을 위해 진행한 '일정 관리 API 만들기' 과제입니다.<br>
  이 프로젝트는 사용자가 일정을 추가, 조회, 수정, 삭제할 수 있는 API를 제공하며, 비밀번호 확인 기능을 통해 일정 삭제 시 보안을 강화하였습니다.
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
<img src="./images/LV2 ERD.png" width="130">

### Lv3 ERD
<img src="./images/LV3 ERD.png" width="330">

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

## ➕부가 설명