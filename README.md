## 중고마켓
 중고거래 사이트를 구현한 서비스

## 기술스텍

<div align=left>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=coffeescript&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
<img src="https://img.shields.io/badge/mongoDB-47A248?style=for-the-badge&logo=MongoDB&logoColor=white">
 <br>
 <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
 <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
 <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
 <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
 <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
 <br>
 <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
 <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
 <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white">
</div>

### ERD
![erd완성](https://github.com/user-attachments/assets/d26394a9-4833-4220-a2f8-eb9cc6bb057a)

## 주요기능
### 1.회원가입 및 로그인
 - 회원가입시 이메일중복체크 및 이메일인증을 통해 회원가입가능.
 - 이메일 전송은 JavaMailSender를 이용하여 인증번호를 전송을함
 - 인증번호는 Redis를 이용하여 랜덤으로 생성된 인증번호 6자리를 3분간 저장을함. 3분이 지나면 해당인증번호를 저장한 데이터가 삭제됨
 - 로그인은 Spring Security 를 이용하여 로그인

### 2. 상품판매 및 구매 게시글 작성
 - 로그인 후 상품판매 및 구매글 작성이 가능함.
 - 게시글 작성시 대표이미지 및 상품의 이미지 등록이 가능함. 이미지등록은 최대 7개까지가능
 - 대표이미지 등록을 하지않을시 기본 섬네일 이미지로 등록됨.

### 3. 상품 검색기능
 - Querydsl 을 이용한 상품 검색기능 구현
 - 작성자 및 상품명으로 검색이 가능함.

### 4. 마이페이지
 - 사용자 정보 수정 가능 (닉네임, 비밀번호, 이름 등)
 - 회원탈퇴 가능

### 5. 채팅기능
 - 거래하고싶은 상품의 등록자에게 채팅을 보낼수있음
 - 채팅은 Stomp를 이용하여 구현함.
 - 채팅기록, 채팅룸은 몽고DB에 저장됨.

### 6. 거래기능
 - 상품 게시글에 거래 신청을하면 게시글 등록자에게 거래내역창에 해당 거래가 등록됨.
 - 등록자는 거래내역창에서 거래승인 및 거절을 할수 있음.
 - 거래 승인시 거래신청자와 게시글 등록자는 서로 채팅이 가능하고 거래종료시 양쪽에서 거래확정을 누르면 최종거래가 완료됨.
 - 거래중에 거래취소를 할 시 게시글등록자와 거래신청자 양측에서 거래취소를 해야 취소가됨.

