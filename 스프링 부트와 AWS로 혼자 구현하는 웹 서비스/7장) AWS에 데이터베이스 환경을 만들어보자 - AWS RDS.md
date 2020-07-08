<h3>AWS RDS</h3>



- 웹 서비스의 백엔드를 다루려면 애플리케이션 코드를 작성하는 것 만큼 데이터베이스를 다루는 것이 중요하다.
- 모니터링, 알람, 백업, HA 구성 등 모두 직접 구현하려면 시간이 오래 걸리기 때문에, 여기서는 AWS RDS를 이용한다.
- RDS
  - AWS에서 지원하는 관계형 데이터베이스
  - 하드웨어 프로비저닝, DB설정, 패치 및 백업과 같이 잦은 운영 작업을 자동화하여 개발자가 개발에 집중할 수 있게 지원하는 서비스
- 먼저, 이 프로젝트에서는 MaridDB를 선택한다.
  - 가격 
  - Amazon Aurora(오로라)로 교체 용이성
    - MySQL과 PostgreSQL을 클라우드 기반에 맞게 재구성한 데이터베이스
  - MySQL은 단순 쿼리 처리 성능에 압도적이다
    - 썬마이크로시스템과 오라클이 합병되면서 많은 MySQL 개발자들이 떠났다.
    - MySQL의 창시자인 몬티 와이드니어가 만든 프로젝트 = MariaDB
  - MariaDB가 MySQL 보다 좋은 점
    - 동일 하드웨어 샤양에서 MySQL보다 향상된 성능
    - 다양한 기능
    - 활성화된 커뮤니티
    - 다양한 스토리지 엔진
      

---



<h3>RDS 인스턴스 생성하기</h3>



- DB 생성
- MariaDB 선택
  - 프리티어 선택
  - 스토리지 유형 = SSD, 스토리지 = 20
- 상세설정
  - DB 인스턴스 식별자
    - 인스턴스에 붙일 이름
  - 마스터 사용자 이름과 암호
    - DB의 마스터 사용자로 사용할 이름과 암호 기입
  - 네트워크 및 보안
    - 퍼블릭 엑세스 가능 => O
    - 이후 보안그룹에서 지정된 IP만 접근가능하도록 막기
  - 데이터베이스 옵션
    - DB이름 설정 (중요)
      - DB에 이름을 설정하지 않으면 AWS RDS에서 DB를 생성하지 않음
    - 포트 3306
    - 파라미터 그룹, 옵션그룹 => default (추후 수정)



---



<h3>RDS 운영환경에 맞는 파라미터 설정하기</h3>



- RDS를 처음 생성하면 몇 가지 설정을 필수로 해야함 (여기서는 3개)

  - 타임존
  - Character Set
  - Max Connection
    

- 파라미터 그룹

  - 파라미터 그룹 생성

  - 파라미터 그룹 패밀리

    - 방금 생성한 DB와 같은 버전을 맞춰야함

  - 그룹 이름

    - 그룹 식별자 기입 (zin0Study-springboot2-webservice)
    - 설명 기입

  - 파라미터 그룹 편집

    - time_zone 검색
      - Asia/Seoul로 설정 변경
    - utf8mb4 설정하기
      - character_set_client
      - character_set_connection
      - character_set_database
      - character_set_filesystem
      - character_set_results
      - character_set_server
    - utf8mb4_general_ci 설정하기
      - collation_connection
      - collation_server
    - utf8은 이모지(이모티콘)를 저장할 수 없지만, utf8mb4는 가능 (그래서 보편적으로 사용)
    - max_connection 변경하기
      - 프리티어 사양에서는 60개의 커넥션만 가능 ~> 150으로 변경하기

  - 데이터베이스 수정에서 DB 파라미터 그룹을 방금 생성한 그룹으로 변경하기

    - 예약 변경은 새벽 시간대에 진행, 수정사항 반영동안 DB가 작동하지 않을 수 있음
    - 하지만, 지금 단계에서는 서비스 오픈을 하지 않았기 때문에 즉시 적용 선택
    - 이후 DB 재부팅하고 제대로 적용됐는지 확인하기

    

---



<h3>내 PC에서 RDS에 접속해보기</h3>



- RDS의 보안 그룹에 내 PC의 IP 추가

- 보안 그룹 

  - 데이터베이스의 VPC 보안 그룹 클릭해서 이동하기
  - EC2에 사용된 보안 그룹의 그룹 ID 복사하기
  - 복사된 보안그룹의  그룹 ID와 본인 IP를 RDS 보안 그룹의 인바운드로 추가
  - 아까 생성할 때, default로 생성했었으므로, default를 복사해서 진행하는게 좋을 것 같음
    - MYSQL/AURORA로 설정하면 알아서 TCP 3306이 잡힘
    - 여기서 시간을 많이 잡아먹었다.
    - 그리고 변경을 마쳤으면 데이터베이스 수정에서 보안그룹을 꼭 변경해주기
  - 위의 과정은 RDS와  EC2 간의 연동 설정을 해주는 것
    - 앞서 EC2와 PC의 연동을 해뒀으니까, PC와 RDS, EC2간 연동이 된 것
      

- Database 플러그인 설치

  - plugins
    - Database navigator 설치하기
  - Database Browser
    - navigator 설치가 끝났으면 `ctrl + shift + a`에서 Database Browser 검색
    - 버튼 클릭 -> MySQL 선택 
      - MariaDB는 MySQL 기반이므로 MySQL 선택하면 됨.
  - RDS 접속 정보 등록
    - Host에 RDS에서 생성한 데이터베이스의 엔드포인트 등록하기
      - RDS 데이터베이스 정보 보면 엔드포인트가 있음
    - Test Connection 확인 -> Apply -> OK
      

- RDS 스키마 노출 후 콘솔창 생성하기

  - New SQL Console 생성

  - use AWS RDS 웹 콘솔에서 지정한 데이터베이스명;

    - RDS 생성 당시 입력한 DB 이름

      ```mysql
      use AWS RDS zin0Study_springboot2_webservice;
      ```

    - DB 이름을 까먹었으면, 인텔리제이 왼쪽의 Schema 항목에 기본 생성되는 스키마 말고 하나 보일건데, 그게 이름

    - 위의 쿼리문에 블록을 씌우고 Execute Statement 진행 (쿼리문 블록을 지정해줘야함)

  - DB가 선택된 상태에서 현재 character_set, collation 설정 확인하기

    ```mysql
    show variables like 'c%';
    ```

    - 위의 결과를 보면, character_set_database, collation_connection 2가지 항목이 latin1로 돼있다.
    - 위의 2개 항목이 MariaDB에서만 RDS 파라미터 그룹으로는 변경이 안됨
    - 직접 변경해주기

    ```mysql
    ALTER DATABASE 데이터베이스명
    CHARACTER SET = 'utf8bm4'
    COLLATE = 'utf8mb4_general_ci';
    ```

    - 다시 설정 확인 쿼리를 날리면 변경이 잘 된 것을 확인

  - 타임존 확인하기

    ```mysql
    select @@time_zone, now();
    ```

  - 한글명이 잘 들어가는지 테이블 생성 및 insert 쿼리 실행하기

    ```mysql
    CREATE TABLE test (
    	id bigint(20) NOT NULL AUTO_INCREMENT,
        content varchar(255) DEFAULT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB;
    ```

    ```mysql
    insert into test(content) values ('테스트');
    ```

    ```mysql
    select * from test;
    ```

    - 위의 세 쿼리를 순차적으로 실행하기
    - 한번에 다 입력했다면, 앞서 한 것 처럼 블록 지정해서 끊어서 실행하기
    - id 1, content에는 테스트가 들어가있는 것을 확인



---



<h3>EC2에서 RDS에서 접근 확인</h3>



- 맥 ssh 서비스명 / 윈도우 putty 접속

  - MySQL CLI 설치하기
    - 명령어 라인만 쓰기 위한 설치
  - sudo yum install mysql
    

- RDS에 접속하기

  ```
  mysql -u 계정 -p -h Host주소
  ```

  ```
  mysql -u zin0 -p -h DB엔드포인트 주소
  ```

  - 이후 패스워드 치라고 하면 치면 된다.

  - 생성한 RDS가 맞는지 확인하기

    ```mysql
    show databases;
    ```

    - RDS DB 이름이 들어가있다면 완료







