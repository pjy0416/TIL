

## 헤로쿠를 위한 소스코드 수정

**반드시 에러없이 실행되는 flask 프로젝트를 들고 와야함**

1. DB 경로 변경
```python
import os
app.config["SQLALCHEMY_DATABASE_URI"] = os.environ.get('DATABASE_URL')

```

* Heroku 에서는 연결되는 DB가 DATABASE_URL이라는 환경변수로 지정되어 있음. 

* 만약 이렇게 변경하고 내 로컬 작업환경(혹은 C9)에서 문제가 되지 않으려면 환경변수 지정을 해줘야함.

```
export DATABASE_URL='postgresql:///app'
```
위의 경우에는 새로운 터미널마다 입력해야함. 

귀찮으니까 아래의 방법 추천!

1) bashrc 열기

```
$ vi ~/.bashrc
```
2) vim 편집기에서 수정(i) 누르고 아래와 같이 입력후 상단에 입력후  :wq
```
export DATABASE_URL='postgresql:///app'
```



## 헤로쿠에 배포하기

**반드시 에러없이 실행되는 flask 프로젝트를 들고 와야함**

### 1) gunicorn 환경설정

1. gunicorn 설치(heroku에서 서버 실행)
```
$ sudo pip3 install gunicorn

```
2. Procfile 파일 만들기 (**확장자 없음 주의**)
```
# /Procfile
web: gunicorn app:app
```
3. 서버 실행 테스트
```
$ gunicorn app:app
```

### 2) heroku 환경설정을 위한 셋팅

1. runtime.txt 파일 만들기
```
# /runtime.txt
python-3.6.6
```

2. requirements.txt 파일 만들기
```
$ pip3 freeze > requirements.txt
```

3. requirements.txt에서 해당 내용 삭제
  * pygobject
  * python-apt
  * python-editor
  * unattended-upgrades

### 3) git commit

```
$ git add .
$ git commit -m "heroku setting"
```

### 4) heroku 배포

> {프로젝트명} 은 헤로쿠에 이미 있는 프로젝트명은 설정이 불가함. unique하게!

```
$ heroku login
$ heroku create {프로젝트명}
$ heroku git:clone -a {프로젝트명}
$ git push heroku master
```

### 5) heroku postgresql addon 설정

```
$ heroku addons:create heroku-postgresql:hobby-dev
```

### 6) heroku 에서 마이그레이션 파일 반영

```
$ heroku run flask db upgrade -a {프로젝트명}
```
들어가보자!

https://{프로젝트명}.herokuapp.com

# 끝!
