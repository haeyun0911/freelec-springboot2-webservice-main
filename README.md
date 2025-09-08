# Chapter01 
## 테스트 코드 쓰는 이유 
1. 초기에 문제를 발견 
2. 업그래이드 등에서 기존 기능이 올바르게 작동하는지 확인
3. 불확실성을 감소 
4. 문서화 

## 테스트 코드 
1. 빠른 피드백 
2. 자동 검증 
3. 개발자가 만든 기능을 안전하게 보호 

## 테스트 코드 프레임워크
- JUnit5 - java


## 프로젝트구조
- build.gradle : 
  - 역할 : 프로젝트 빌드 설정, 의존성 관리 , 롬북 포함 
- HelloController.java 
  - 역할 : HTTP 요청 처리, API 엔트포인트 제공 
  - 동작의 흐름: 
    - 브라우저 요청: GET/ hello
    - helloController.hello() 실행
    - hello 문자열 반환 
    - 브라우져에 hello 표기 
- HelloResponseDto.java
  - 역할: API 응답시 데이터를 담는 객체 

## 어노테이션이란
어노테이션(Annotation)은 Java 코드에 메타데이터(metadata)를 추가하는 방법입니다. @ 기호로 시작하며, 컴파일러나 프레임워크에게 특별한 정보를 제공합니다.

# Chapter02
## 데이터 베이스 
: Posts 관리 시스템을 JPA를 사용해서 구현했습니다. 

### Post.java 
: 게시슬 엔티티 클래스

### BaseTimeEntity.java 
: 공통 시간 관리 클래스 


참고링크

- [교재 깃헙 레포지토리](https://github.com/jojoldu/freelec-springboot2-webservice) 
- [교재 저자 블로그 ](https://jojoldu.tistory.com/)