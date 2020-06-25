**Http vs Https**

- 일반적으로 사용하는 Hypertext Transfer Protocol (하이퍼텍스트 통신 프로토콜, HTTP)에서 발전해서 보안을 더욱 강화한게 HTTPS(Hypertext Transfer Protocol Secure)다.
  
- HTTPS는 서버에서 브라우저로 전송되는 데이터에 SSL(보안 소켓 계층)을 사용해서 암호화된 연결 및 정보를 보호해준다. 또한, 전송계층보안(TLS)을 이용해서 데이터 보안을 유지한다.
  
- HTTP 요청 메소드
  - GET - 정보 요청(SELECT)
  - POST  - 정보 저장(INSERT)
  - PUT - 정보 업데이트(Update)
  - Delete - 정보 삭제(Delete)
  - HEAD - 헤더 정보만 요청, 자원 존재 여부 확인하거나 서버 문제 확인 시 사용.
  - OPTION - 웹서버가 지원하는 메소드 종류 요청(필요는 없어보임)
  - TRACE - 클라이언트 요청 그대로 반환

---

**MAVEN vs Gradle**

- 빌드, 패키징, 문서화, 테스트, 리포팅, 의존성 관리, git, svn(서브버전) 등과 같은 형상관리서버와 연동, 배포 작업을 쉽게 할 수 있도록 도와준다.
- CoC (Convention over Configuration)
  - 일종의 관습 ~> 소스파일 위치와 컴파일 파일 위치 등 미리 정해놨다는 말
- Gradle은 2012년 출시되었으며, Ant와 Maven의 장점을 모았다
- Maven의 단점
  - 빌드는 동적 요소기 때문에 XML로 정의하는데 한계가 있다.
  - 설정 내용이 길고 가독성이 떨어진다.
  - 의존관계가 복잡한 프로젝트에 쓰이기에 부적절하다.
  - 특정 설정을 모듈에 공유하기 위해서는 부모 프로젝트를 생성해서 상속하게 해야한다.
- Gradle 장점
  - Groovy 사용 -> JVM에서 실행되는 스크립트 언어
  - 동적인 빌드는 Groovy를 이용해서 스크립트로 플러그인 호출 or 코드를 작성하면 된다.
  - 설정 주입 시 프로젝트 조건을 체크할 수 있어서 프로젝트 별로 다르게 설정할 수 있다.

