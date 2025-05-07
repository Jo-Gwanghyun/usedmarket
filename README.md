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

## 주요기능
### 1.회원가입 및 로그인
 - 회원가입시 이메일중복체크 및 이메일인증을 통해 회원가입가능.
 - 이메일 전송은 JavaMailSender를 이용하여 인증번호를 전송을함
 - 인증번호는 Redis를 이용하여 랜덤으로 생성된 인증번호 6자리를 3분간 저장을함. 3분이 지나면 해당인증번호를 저장한 데이터가 삭제됨
 - 로그인은 Spring Security 를 이용하여 로그인
