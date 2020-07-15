<h3>CI & CD 소개</h3>



- `CI` => Continuous Integration - 지속적 통합
  - VCS (코드 버전 관리 시스템)에 PUSH가 되면 자동으로 테스트와 빌드가 수행되어 안정적인 배포 파일을 만드는 과정
- `CD` => Continuous Deployment - 지속적 배포
  - 빌드 결과를 자동으로 운영 서버에 무중단 배포까지 진행되는 과정
    

- CI 4 규칙
  - 모든 소스 코드가 살아있고(현재 실행되고) 누구든 현재의 소스에 접근할 수 있는 단일 지점을 유지할 것
  - 빌드 프로세스를 자동화해서 누구든 소스로부터 시스템을 빌드하는 단일 명령어를 사용할 수 있게 할 것
  - **테스팅을 자동화**해서 단일 명령어로 언제든지 시스템에 대한 건전한 테스트 수트를 실행할 수 있게 할 것
  - 누구나 현재 실행 파일을 얻으면 지금까지 가장 완전한 실행 파일을 얻었다는 확신을 하게 할 것



---



<h3>Travis CI 연동하기</h3>



- 깃허브에서 제공하는 무료 CI 서비스

  - 젠킨스 같은 CI 도구도 있지만, 설치형이기 때문에 이를 위한 EC2 인스턴스가 하나 더 필요

- Travics CI 웹 서비스 설정

  - `https://trvis-ci.org/`에서 깃허브 계정으로 로그인 ~> Settings 클릭
  - 저장소 이름을 찾아서 활성화하기

- 프로젝트 설정

  - `.travis.yml ` 파일을 build.gradle과 같은 위치에 만들기
    - yml => YAML(야믈) ~> JSON에서 괄호를 제거한 것이라고 생각하면 됨

  ```yml
  language: java
  jdk:
    - openjdk8
  
  branches:
    only:
      - master
  
  before_install:
    - chmod +x gradlew
  
  # Travis CI 서버의 Home
  cache:
    directories:
      - '$HOME/.m2/repository'
      - '$HOME/.gradle'
  
  script: "./gradlew clean build"
  
  
  # CI 실행 완료시 메일로 알람
  notifications:
    email:
      recipients:
        - dimple0416@naver.com
  ```

  - branches

    - Travis CI를 어느 브랜치가 푸시될 때 수행할지 지정

  - cache

    - 그레이들을 통해 의존성을 받게 되면 이를 해당 디렉토리에 캐시하여, 같은 의존서ㄴㅇ은 다음 배포 때부터 다시 받지 않도록 설정

  - script

    - master 브랜치에 푸시되었을 때 수행하는 명령어
    - 여기서는 프로젝트 내부에 둔 gradlew을 통해 clean & build를 수행

  - notifications

    - Travis CI 실행 완료 시 자동으로 알람이 가도록 설정

  - 위 과정에서 에러가 한 번 났었는데, gradlew에 실행 권한이 없어서였다. 

    ```yaml
    before_install:
      - chmod +x gradlew
    ```

    이 코드를 통해, 실행 전에 권한을 부여해줘서 해결했다.



---



<h3>Travis CI와 AWS S3 연동하기</h3>

- AWS S3

  - 일종의 파일 서버
  - 이미지 파일을 비롯한 정적 파일들을 관리하거나 배포 파일들을 관리하는 등 기능 지원
  - 보통 이미지 업로드를 구현할 때 S3를 많이 쓴다.
    

- Travis CI와 AWS S3 연동하기

  - 실제 배포는 AWS CodeDeploy를 통해 하지만, CodeDeploy에는 저장 기능이 없다. 그래서, Travis CI가 빌드한 결과물을 S3에 보관해서 CodeDeploy에서 이를 이용해서 배포하도록 설정하기 위해 AWS S3 사용.
    

- AWS Key 발급

  - <u>**AWS 서비스에 외부 서비스가 접근할 수 없다. 그래서 접근 권한을 가진 Key를 생성해서 이용해야 한다.**</u>

  - IAM 서비스 이용하기

    - AWS에서 제공하는 서비스의 접근 방식과 권한을 관리하는 서비스

  - IAM 검색 후, 사용자 추가 ~> 사용자 이름 기입, 액세스 유형 ~> 프로그래밍 방식 엑세스

    `사용자 이름 : zin0study-travis-deploy` 이런 느낌으로 관리하는 듯 하다

  - 권한 설정 ~> 기본 정책 직접 연결

  - 정책 검색 ~> `AmazonS3FullAccess`, `AWSCodeDeployFullAccess` 추가

  - 태그 등록 ~> 키 = Name, 값 = zin0study-travis-deploy
    

- Travis CI에 키 등록

  - Settings ~> Environment Variables에, 위에서 발급받았던 Key ID와 Secret을 value에 넣기

    `Name => AWS_ACCESS_KEY` , `Value => 키 ID` 

    `Name => SECRET_KEY` , `Value => 키 Secret`  

    이런 방식으로 입력

    - 여기에 등록된 값들은 .travis.yml 에서 `$AWS_ACCESS_KEY`와 같이 변수로 사용 가능
      

- S3 버킷 생성

  - Travis CI에서 생성된 Build 파일을 저장하도록 구성
  - AWS S3 검색 ~> 버킷 만들기
    - 버킷 이름 짓기 => **배포할 Zip 파일이 모여있는 장소임을 의미하도록 짓는 것을 추천**
    - 버전관리 설정 Skip
    - 퍼블릭 엑세스 차단(버킷 설정) => 모든 퍼블릭 엑세스 차단
      - 왜냐하면, IAM 사용자로 발급받은 키를 사용
      - **오픈할 경우, 중요한 정보(코드, 설정값, 주요 키 값 등)가 다 탈취당한다.**

- .travis.yml 추가

  ```yaml
  ...
  
  before_deploy:
    - zip -r study-springboot2-webservice *
    - mkdir -p deploy 
    - mv study-springboot2-webservice.zip deploy/study-springboot2-webservice.zip # deploy로 zip파일 이동
  
  deploy:
    - provider: s3
      access_key_id: $AWS_ACCESS_KEY # Travis repo settings에 설정된 값
      secret_access_key: $AWS_SECRET_KEY # Travis repo settings에 설정된 값
      bucket: zin0study-springboot-build # S3 버킷
      region: ap-northeast-2
      skip_cleanup: true
      acl: private # zip 파일 접근을 private으로
      local_dir: deploy # before_deploy에서 생성한 디렉토리
      wait-until-deployed: true
      
  ...
  ```

  - before_deploy
    - deploy 명령어가 실행되기 전에 수행된다.
    - CodeDeploy는 Jar 파일은 인식하지 못하므로 Jar + 기타 설정 파일들을 모아 압축
  -  zip -r study-springboot2-webservice *
    - 현재 위치의 모든 파일을 study-springboot2-webservice 이름으로 압축
    - 명령어의 마지막 위치는 본인의 프로젝트 이름이어야 한다.
  - mkdir -p deploy
    - deploy라는 디렉토리를 Travis CI가 실행 중인 위치에 생성
  - deploy
    - S3로 파일 업로드 or CodeDeploy로 배포 등 <u>**외부 서비스와 연동될 행위들을 선언**</u>
  - local_dir: deploy
    - 앞에서 생성한 deploy 디렉토리를 지정
    - 해당 위치의  파일들만 S3로 전송
  - 여기서 에러 잡는데 조금 고생했다.
    - 첫째, 공백에 유의할 것
    - 둘째, 오타 확인할것...
    - 그리고 셋째는 이어질 CodeDeploy에서 다루겠다. 왜냐하면, S3까지는 문제가 되지 않기 때문.



---



<h3>Travis CI와 AWS S3, CodeDeploy 연동하기</h3>

- EC2가 CodeDeploy를 연동 받을 수 있게 IAM 역할을 생성

- EC2에 IAM 역할 추가하기

  - IAM 검색 ~> 역할 ~> 역할 만들기
    - 역할 = AWS 서비스에만 할당할 수 있는 권한 (ex : EC2, CodeDeploy)
    - 사용자 = AWS 서비스 외에 사용할 수 있는 권한(ex :  Local PC, IDC 서버 등)
  - `AWS 서비스 , EC2` 차례대로 선택
  - 정책 ~> `EC2RoleForA`를 검색해서 `AmazonEC2RoleforAWS-CodeDeploy`를 선택
  - 태그 등록
    - 키, VALUE에 원하는 이름 짓기
    - `키 => NAME`, `VALUE => ec2-codedeploy-role` 이런 방식으로 짓는 듯 함
  - 역할의 이름 등록하기
    - `역할 이름 => ec2-codedeploy-role` 이런 방식
  - EC2 서비스로 이동해서, 프로젝트 인스턴스에 오른쪽 클릭 ~> 인스턴스 설정 ~> IAM 역할 연결/바꾸기 ~> 방금 생성한 역할 선택 ~> 재부팅
    

- CodeDeploy 에이전트 설치

  - 책에서 나온 코드로는 설치가 되지 않아서, 검색을 통해 찾았다.

    ```
    aws s3 cp s3://aws-codedeploy-ap-northeast-2/latest/install .--region ap-northeast-2
    ```

    ```
    wget https://aws-codedeploy-ap-northeast-2.s3.ap-northeast-2.amazonaws.com/latest/install
    ```

    위에가 책에서 나온 명령어, 아래가 찾은 명령어 => EC에 접속해서 명령어 입력

  - install 파일에 실행 권한 추가 => `chmod +x ./install`

  - intall 파일로 설치 진행 => `sudo ./install auto`

  - Agent 실행 확인하기 => `sudo service codedeploy-agent status`

    => 결과 : `The AWS CodeDeploy agent is running as PID XXX` 면 성공

  

- CodeDeploy를 위한 권한 생성

  - IAM 역할 생성하기 ~> AWS 서비스,  CodeDeploy를 차례대로 선택
  - 정책 필터 ~> AWSCodeDeployRole
  - 태그 작성
    - `키 => NAME`, `VALUE => codedeploy-role` 
  - 역할 이름 작성
    - `역할 이름 => codedeploy-role` 

- CodeDeploy 생성

  - AWS 배포 3형제

    - Code Commit
      - Github과 같은 코드 저장소, 하지만, Github를 가장 많이 이용
    - Code Build
      - Travis CI와 같은 빌드용 서비스
      - 멀티 모듈을 배포하는 경우 사용해 볼만하지만, 규모 있는 서비스는 젠킨스/팀시티 등을 이용
    - CodeDeploy
      - 배포 서비스, 대체재가 없다. ~> 이용

  - CodeDeploy 검색 ~> 애플리케이션 생성

  - 애플리케이션 이름 작성 => `zin0Study-springboot2-webservice`

  - 컴퓨팅 플랫폼 => `EC2/온프레미스`

  - 배포그룹 생성

    - 배포그룹 이름과 서비스 역할 입력

      `배포그룹 이름 => zin0Study-springboot2-webservice-group`

      `서비스 역할 => codedeploy-role`

    - 배포 유형 => 현재 위치
      - 배포할 서비스가 2대 이상이면 블루/그린 선택

  - 환경 구성

    - Amazon EC2 인스턴스 선택
    - 키, 값은 위에서 저장한 값 선택

  - 배포 설정

    - 배포 구성 => CodeDeployDefault.AllAtOnce 선택
      - 한번 배포할 때 몇 대의 서버에 배포할지 결정하는 것
    - 로드 밸런싱 활성화 해제
      

- Travis CI, S3, DodeDeploy 연동

  - EC2에 S3에서 넘겨줄 zip 파일을 저장할 디렉토리 생성하기

    - `mkdir ~/app/step2 && mkdir ~/app/step2/zip`

  - Local에 Build.gradle과 같은 위치에 appspec.yml 생성하기

    ```yaml
    version: 0.0
    os: linux
    files: # Travis 구동을 위해 주석 수정
      - source : /
        destination: /home/ec2-user/app/step2/zip/
        overwrite: yes
    ```

    - 띄어쓰기 유의할 것
    - version: 0.0
      - CodeDeploy 버전
      - 프로젝트 버전이 아니므로 0.0 외에 다른 버전을 사용하면 오류 발생
    - source
      - CodeDeploy에서 전달해 준 파일 중 destination으로 이동시킬 대상 지정
      - 루트 경로(/)를 지정하면 전체 파일
    - destination
      - source에서 지정된 파일을 받을 위치

  - .tarvis.yml에 CodeDeploy 내용 추가

    ```yaml
    deploy:
      ...
      
      - provider: codedeploy
        access_key_id: $AWS_ACCESS_KEY # Travis repo settings에 설정된 값
        secret_access_key: $AWS_SECRET_KEY # Travis repo settings에 설정된 값
        bucket: zin0study-springboot-build # S3 버킷
        key: study-springboot2-webservice.zip # 빌드 파일을 압축해서 전달
        bundle_type: zip # 압축 확장자
        application: zin0Study-springboot2-webservice # 웹 콘솔에서 등록한 CodeDeploy 어플리케이션
        deployment_group: zin0Study-springboot2-webservice-group # 웹 콘솔에서 등록한 CodeDeploy 배포 그룹
        region: ap-northeast-2
        wait-until-deployed: true
    ```

    - 여기서 에러가 많이 떠서, 시간을 많이 보냈다...
    - 그 이유는 오타나 이런 경우도 있었지만, 가장 중요한 점은 CodeDeploy로 값을 전달할 때, **값이 모두 소문자로 변환돼서 이동한다.**
    - 처음에는 알집 생성을 zin0Study-springboot2-webservice.zip 으로 했었는데, key 오류가 계속 떠서 로그를 따라가다 보니까, CodeDeploy에 zin0study-springboot2-webservice.zip을 요청하고 있었다. ~> **zip 이름을 소문자로 바꿔주고 해결**

  - 배포가 끝났다면, 파일이 잘 도착했는지 EC2에서 확인

    - `cd /home/ec2-user/app/step2/zip` 으로 이동 ~> `ll` ~> build랑 gradle 등 도착 확인



---



<h3>배포 자동화 구성</h3>



- Local 최상단에 scripts 디렉토리 생성 ~> deploy.sh파일 추가

  ```shell
  #!/bin/bash
  
  REPOSITORY=/home/ec2-user/app/step2
  PROJECT_NAME=springboot-webservice
  
  echo "> Build 파일 복사"
  
  cp $REPOSITORY/zip/*.jar $REPOSITORY/
  
  echo "> 현재 구동중인 애플리케이션 pid 확인"
  
  CURRENT_PID=$(pgrep -fl springboot-webservice | grep jar | awk '{print $1}') # 에러가 뜬다면 pgrep으로 잡아올 jar 이름부터 확인
  
  echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"
  
  if [ -z "$CURRENT_PID" ]; then
      echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
  else
      echo "> kill -15 $CURRENT_PID"
      kill -15 $CURRENT_PID
      sleep 5
  fi
  
  echo "> 새 어플리케이션 배포"
  
  JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
  
  echo "> JAR Name: $JAR_NAME"
  
  echo "> $JAR_NAME 에 실행권한 추가"
  
  chmod +x $JAR_NAME
  
  echo "> $JAR_NAME 실행"
  
  nohup java -jar \
      -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
      -Dspring.profiles.active=real \
      $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
  ```

  - CURRENT_PID
    - 현재 수행 중인 스프링 부트 애플리케이션의 프로세스 ID를 탐색
    - 실행 중이면 종료하기 위해서
    - 스프링 부트 애플리케이션 이름으로 된 다른 프로그램들이 있을 수 있어서 jar 프로세스를 찾은 뒤 ID를 찾는다.
  - chmod +x $JAR_NAME
    - nohup으로 실행하도록 Jar파일에 실행 권한 부여
  - $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
    - **nohup 실행 시, CodeDeploy는 무한 대기**한다.
    - 이 이슈를 해결하기 위해 nohup.out 파일을 표준 입출력용으로 별도로 사용
    - 이렇게 하지 않으면 nohup.out 파일이 생기지 않고, **CodeDeploy 로그에 표준 입출력이 출력된다.**
    - nohup이 끝나기 전까지 CodeDeploy도 끝나지 않으니 꼭 이렇게 해야함
      

- .travis.yml 수정

  - 위에까지 진행했으면, 프로젝트의 모든 파일을 zip 파일로 만드는데, 실제로 필요한 파일들은 Jar, appspec,yml, 배포를 위한 스크립트들이다. 이 외에는 필요하지 않으니 포함 X

    ```yaml
    ...
    
    before_deploy:
      - mkdir -p before-deploy # zip에 포함시킬 파일들을 담을 디렉토리 생성
      - cp scripts/*.sh before-deploy/
      - cp appspec.yml before-deploy/
      - cp build/libs/*.jar before-deploy/
      - cd before-deploy && zip -r before-deploy * # before-deploy로 이동후 전체 압축
      - cd ../ && mkdir -p deploy # 상위 디렉토리로 이동후 deploy 디렉토리 생성
      - mv before-deploy/before-deploy.zip deploy/study-springboot2-webservice.zip # deploy로 zip파일 이동
    
    deploy:
      ...
    ```

    - `mkdir -p before-deploy`
      - **Travis CI는 S3로 특정 파일만 업로드가 안된다.**
      - 디렉토리 단위만 업로드할 수 있기 때문에, deploy 디렉토리는 항상 생성
    - `cp scripts/*.sh before-deploy/`
      - **before-deploy에는 zip 파일에 포함시킬 파일들을 저장**
    - `cd before-deploy && zip -r before-deploy`
      - **zip -r 명령어를 통해 before-deploy 디렉토리 전체 파일을 압축**

- appspec.yml 수정

  ```yaml
  ...
  
  permissions:
    - object: /
      pattern: "**"
      owner: ec2-user
      group: ec2-user
  
  hooks:
    ApplicationStart:
      - location: deploy.sh
        timeout: 60
        runas: ec2-user
  ```

  - permissions
    - CodeDeploy에서 EC2 서버로 넘겨준 파일들을 모두 ec2-user 권한을 갖도록 함.
  - hooks
    - CodeDeploy 배포 단계에서 실행할 명령어를 지정
    - ApplicationStart라는 단계에서 deploy.sh를 ec2-user 권한으로 실행
    - timeout: 60으로 스크립트 실행 60초 이상 수행되면 실패가 된다. (시간제한을 두지 않을 경우 무한정 기다릴 수 있음)
      

- 실제 배포 과정 체험

  - build.gradle에서 프로젝트 버전 변경
    - `version '1.0.1-SNAPSHOT'`
  - index.mustache 수정
    - `<h1>스프링 부트로 시작하는 웹 서비스 Ver.2</h1>`
  - 커밋 - 푸시 하면 반영이 잘 되는 것을 확인



---



<h3>CodeDeploy 로그 확인</h3>



- AWS가 지원하는 서비스에서는 오류가 발생할 때 로그 찾는 법을 모르면 오류 해결하기가 어려움, 그래서 배포가 실패하면 어떤 로그를 봐야할 지 정리
  
- CodeDeploy에 관한 대부분 내용은 /opt/codedeploy-agent/deployment-root에 있다.
  - `cd/opt/codedeploy-agent/deployment-root`로 이동 ~> `ll`
    - 여러 목록이 존재
    - **최상단의 영문과 대시(-)가 있는 디렉토리명은 CodeDeploy ID**
      - 해당 디렉토리에 들어가 보면 **배포한 단위별로 배포 파일들** 존재
      - 배포 파일이 정상적으로 왔는지 확인할 수 있다.
    - **/opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log**
      - CodeDeploy 로그 파일
      - CodeDeploy로 이루어지는 배포 내용 중 표준 입/출력 내용이 담겨있다.
      - 작성한 echo 내용도 모두 표기됨





