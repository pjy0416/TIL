<h3>클라우드</h3>
- 호스팅 서비스나 집 PC를 이용하는 것이 일반적으로 비용이 저렴하지만, 특정 시간에만 트래픽이 몰린다면 유동적으로 사양을 늘릴 수 있는 클라우드가 유리
- 클라우드 서비스는 단순히 물리 장비만 대여하는 것이 아니라, 로그 관리, 모니터링, 하드웨어 교체, 네트워크 관리 등 지원해준다.
- 클라우드의 형태
  - Infrastructure as a Service(IaaS, 아이아스, 이에스)
    - 기존 물리 장비를 미들웨어와 함께 묶어둔 추상화 서비스
    - 가상머신, 스토리지, 네트워크, 운영체제 등의 IT 인프라를 대여해주는 서비스
    - AWS의 EC2, S3 등
  - Platform as a Service (PaaS, 파스)
    - IaaS에서 한번 더 추상화한 서비스
    - 따라서, 많은 기능이 자동화 돼있음
    - AWS의 Beanstalk(빈스톡), Heroku(헤로쿠) 등
  - Software as a Service (Sass, 사스)
    - 소프트웨어 서비스
    - 구글 드라이브, 드랍박스, 와탭 등



---



<h3>AWS 회원가입</h3>


- 전에 AWS를 이용했어서 따로 정리하지 않겠다.
- 다만, 꿀인거는 아이디를 새로 가입하면 프리 티어를 또 1년 이용 가능하다는 점 !!
- 그리고, 리전에 서울이 추가됐다는 것에 감격을 느낀다.. ㅎㅎ



---



<h3>EC2 인스턴스 생성</h3>


- 리전을 서울로 변경
- 인스턴스를 생성하는 첫 단계는 AMI(Amazon Machine Image)를 선택하는 것
  - AMI는 EC2 인스턴스를 시작하는 데 필요한 정보를 이미지로 만들어 둔 것
- Amazon Linux AMI를 선택
  - 아마존리눅스 2가 아닌 1을 이용하는 이유
    - 국내 자료는 리눅스 1이 더 많기 때문
  - 센토스가 아닌 아마존 리눅스 AMI인 이유
    - 아마존이 개발하고 있어서 지원받기가 쉬움
    - 아마존 리눅스는 레드햇 베이스다. (레드햇 이용자가 많음)
    - AWS의 다른 서비스와 상성이 좋다.
    - Amazon 독자적인 개발 리포지터리를 사용하고 있어서 yum이 매우 빠름
- 인스턴스 유형은 기본 t2.micro(프리티어 허용) 선택
- 스토리지 추가에서 크기는 30GiB로 설정(프리티어의 최대 EBE 범용(SSD) -> 30 넘어가면 추가비용)
- 태그 추가 
  - 키 = NAME
  - 값 = Zin0Study-springboot2-webservice
  - 인스턴스가 여러개 있을 때, 태그별 검색 or 그룹짓기 쉽게하기 위함
- 보안그룹
  - 보안그룹 = 방화벽 설정
  - SSH = ssh 접속 ip 관리 ~> 자주 작업하는 곳 (집, 카페 등) ip 추가, 포트범위 22
    - 포트 22 => AWS EC2에 터미널로 접속할 때
    - pem 키 관리와 지정된 IP에서만 ssh 접속이 가능하도록 구성하는 것이 안전
  - 사용자 지정 TCP
    - 포트범위 8080
    - 현재 프로젝트의 기본 포트
  - HTTPS
    - 포트범위 443
- 인스턴스 검토
  - 시작하기를 누르면 보안 그룹 경고를 하는데, 8080가 전체 오픈돼서 발생 ~> 무시
- pem 키 발급
  - 인스턴스에 접근하기 위한 비밀키 ~> 보안 중요 ~> 잘 관리할 수 있는 디렉터리에 저장
- EIP 할당
  - EIP = 고정 IP = Elastic IP(탄력적 IP)
  - 새 주소 할당 ~> Amazon 풀 ~> 페이지 상단에 작업버튼 ~> 주소연결 ~> 생성한 EC2 이름과 IP 선택 ~> 연결
  - **EIP는 생성하고 EC2 서버에 연결하지 않으면 비용 발생 ~> 나중에 안쓸때는 무조건 삭제해야함 (아니면 과금 폭탄맞음)**



---



<h3>EC2 서버에 접속하기</h3>


`Mac & Linux`

- 외부 서버로 ssh 접속하기

  - ssh -i pem 키 위치 EC2 EIP 주소
  - 매 번 하기 복잡함

- SSH 접속을 수월하게 하기

  - cp pem 키를 내려받은 위치 ~/.ssh/
  - epm 파일을 ~/.ssh/ 디렉토리로 복사한다는 뜻이다.
  - ssh 실행 시 epm 키 파일을 자동으로 읽어 접속을 실행한다.
  - cd ~/.ssh/
  - ll
  - 위의 두 명령어를 순차적으로 입력해서 pem키 복사가 됐는지 확인

- pem 키 권한 변경

  - chmod 600 ~/.ssh/pem키 이름

  - ~/.ssh 디렉토리에 config 파일 생성하기

    - vim ~/.ssh/config

      ```
      # zin0Study-springboot2-webservice
      HostName 원하는 서비스 명
      User ec2-user
      IdentityFile ~/.ssh/pem키 이름
      ```

- config파일 실행 권한 설정하기

  - chomd  700 ~/.ssh/config

- ssh 접속

  - ssh config에 등록한 서비스 명
    

`Windows`

- Putty를 이용해야 한다.
- puttygen을 통해 pem키를 ppk파일로 변환하기
  - 상단의 Conversions => Import Key => pem 키 선택
  - Save private key 클릭 -> ppk 이름 등록하고 저장하기
- putty로 ssh 접속하기
  - Session
    - HostName 설정
      - ec2-user@EIP 주소
    - Port 
      - ssh 접속포트 22 입력
    - Connect type
      - SSH 선택
  - Connection->SSH->Auth
    - 하단의 private key file for authentication에 ppk 등록
  - 다시 Session
    - Saved Sessions에 현재 설정을 저장할 이름을 등록하고 SAVE
  - 저장한 뒤 open



---



<h3>아마존 리눅스 1 서버 생성 시 꼭 해야할 설정</h3>


자바 기반의 웹 애플리케이션 (톰캣과 스프링부트)가 작동해야 하는 서버들에선 필수인 설정들



- JAVA 8 설치 
  - 프로젝트의 자바 버전에 따라서 자바 버전 선택하기 (여기서는 JAVA 8)
  - sudo yum install -y java-1.8.0-openjdk-devel.x86_64 로 JAVA 8 설치
  - sudo /usr/sbin/alternatives --config java 로 자바 버전 선택 열기
    - 목록 번호에서 원하는 자바 버전 번호 입력
  - sudo yum remove java-1.7.0-openjdk
    - 사용하지 않는 자바버전 삭제하기 (java 7은 아마존 1의 기본 자바 버전)
  - java -version
    - 버전 확인하기
  
- 타임존 변경 
  - 기본설정은 미국 시간대, 따라서 한국 시간대로 설정해줘야한다.
  - sudo rm /etc/localtime
    - 현재 저장된 기본설정 삭제
    - sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
      - 한국 시간대를 기본 설정으로 저장
      - ln (source) (target)
        - source의 파일을 target에 하드링크 생성
      - ln -s (source) (target)
        - source의 파일을 가리키도록 링크된 파일을 target에 생성
      - **하드링크 vs 심볼릭 링크**
        - 하드링크 : 원본파일과 동일한 내용의 다른 파일
        - 심볼릭링크 : 원본파일을 가리킴 (원본 변경 시 적용됨, 삭제시에는 오류뜸)
  
- 호스트네임 변경
  - 현재 접속한 서버의 별명 등록
  - 실무에서는 수십대의 서버가 작동되는데, IP만으로 구분 힘들다 ~> 호스트네임으로 구분
  - sudo vim /etc/sysconfig/network
    - network 편집파일을 연다
    - HOSTNAME을 원하는 서비스 명으로 변경하기 ~> :wq (저장하고 종료)
  - sudo reboot
    - 재부팅한 뒤 HOSTNAME 변경 확인하기
  - /etc/hosts에 변경한 hostname 등록하기
    - 호스트 주소를 찾을 때 가장 먼저 검색하는 곳
    - 위에 등록되지 않으면 장애 발생
  - sudo vim /etc/hosts
    - hosts 파일 열기
  - 127.0.0.1 등록한 HOSTNAME
    - hostname 등록하기 -> :wq
  - curl 등록한 호스트 네임
    - 등록에 실패한 경우
      - Could not resolve host: hostname이 뜸
    - 성공한 경우
      - Failed to connect to hostname port 80 : Connection refused
      - 아직 80포트로 실행한 서비스가 없다는 의미