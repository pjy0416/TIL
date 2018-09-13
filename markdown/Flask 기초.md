## [Flask](http://flask.pocoo.org/) 기초

1. 설치 

   ```
   $ sudo pip3 install flask
   ```

2. 기본코드

   ```python
   # /app.rb
   # -*- coding: utf-8 -*-
   from flask import Flask
   app = Flask(__name__)

   @app.route("/")
   def hello():
       return "Hello World!"
   ```

3. 서버 실행 명령어

   ```
   $ flask run --host 0.0.0.0 --port 8080
   ```

   * c9 서버에서는 `flask run` 명령어에 host와 port를 직접 설정해줘야한다. 

   * 로컬에서 구동시에는 `flask run` 만 해도 되며, 접속 url은 `http://localhost:5000`이 기본이다.

   * `flask run` 명령어를 실행할 때, 파일명이 `app.rb`가 아니면 직접 설정해줘야한다. 
     예) 만약에 위의 코드가 `application.rb` 로 설정해두었다면 실행코드는 다음과 같다. 

      `$ FLASK_APP=application.rb flask run ` 혹은 `$ export FLASK_APP=application.rb` 로 환경변수를 설정하고 `$ flask run`을 실행시킨다.

4. `render_template` 모듈 사용하기

   1) render_template 불러오기

   ```python
   # /app.rb
   # -*- coding: utf-8 -*-
   from flask import Flask, render_template
   app = Flask(__name__)

   @app.route("/")
   def hello():
       return render_template("index.html")
   ```

   2) `.html`파일 만들기

   ```html
   <!-- /templates/index.html -->
   <!Doctype html>
   <html>
       <head>
           <title>프로젝트</title>
       </head>
       <body>
           <h1>안녕!!</h1>
       </body>
   </html>
   ```

   3) python 변수 활용하기 

   ```python
   # /app.rb
   def lunch():
       lunch_box = ["멀캠20층", "김밥카페"]
       lunch = random.choice(lunch_box)
       return render_template("lunch.html", lunch=lunch, lunch_box=lunch_box)
   ```

   ```html
   <!-- /templates/lunch.html -->
   {{lunch}} 꺼내먹어요!
   <hr>
   <p>메뉴 리스트</p>
   {% for menu in lunch_box %}
   	<p>{{menu}}</p>
   {% endfor %}
   ```

5. `layout` 활용하기 - `jinja2` 템플릿 활용

   ```html
   <!-- /templates/layout.html -->
   <!Doctype html>
   <html>
       <head>
           <title>{% block title %} {% endblock %}</title>
       </head>
       <body>
           {% block body %}
           {% endblock %}
       </body>
   </html>
   ```

   ```html
   <!-- /templates/index.html -->
   {% extends "layout.html" %}
   {% block title %}홈{% endblock %}
   {% block body %}
   	<h1>Hello, World!</h1>
   {% endblock %}
   ```

6. variable routing

   ```python
   @app.route('/hello/<string:name>')
   def hello(name):
       return render_template("index.html", name=name)

   @app.route('/cube/<int:num>')
   def cube(num):
       return render_template("cube.html", cube=num*num)
   ```




## Flask로 게시판 만들기 (CRUD)

> 만약에 psql 설치 후에 c에서 could not connect to server: Connection refused 에러가 뜬다면
>
> sudo /etc/init.d/postgresql restart

### 환경설정

1. 설치

   ```
   # ubuntu에 postgresql 설치하기
   $ sudo apt-get install postgresql postgresql-contrib libpq-dev

   # python(flask)에서 사용할 수 있도록 도와주는 애들
   $ sudo pip3 install psycopg2 psycopg2-binary 

   # flask에서 import 해서 쓸 것들
   $ sudo pip3 install Flask-SQLAlchemy Flask-Migrate
   ```

2. DB설정

   ```
   $ psql
   ubuntu# CREATE DATABASE databasename WITH template=template0 encoding='UTF8';
   ubuntu# \q
   ```

### 모델 설정

```python
# /models.py
from flask_alchemy import SQLAlchemy

db = SQLAlchemy()

class Post(db.Model):
    # 데이터베이스 테이블설정(테이블명, 컬럼)
    __tablename__ = 'posts' # class이름의 복수형
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String, nullable=False)
    content = db.Column(db.Text)
    created_at = db.Column(db.DateTime)
    
    # 객체 생성자
    def __init__(self, title, content):
        self.title = title
        self.content = content
        self.created_at = datetime.datetime.now()
```

```python
# /app.py
from flask import Flask
# flask ORM
from flask_sqlalchemy import SQLAlchemy
# 마이그레이션 관리
from flask_migrate import Migrate

# 모델 설정파일 import
from models import *

app = Flask(__name__)
# DB 설정과 관련된 코드
app.config["SQLALCHEMY_DATABASE_URI"] = 'postgresql:///app'
app.config["SQLALCHEMY_TRACK_MODIFICATION"] = False
db.init_app(app)

@app.route('/')
def index():
    return "hello world"
```

###  마이그레이션

```python
from flask_migrate import Migrate
```

1. flask db initialize

   ```
   $ flask db init
   ```

   * migrations/ 
     * versions/
       * 12312dsafs_.py

2. models.py 파일의 현재 상태를 반영

   ```
   $ flask db migrate
   ```

3. DB에 반영

   ```
   $ flask db upgrade
   ```

4. 실제로 DB에서 확인하기

   ```
   $ psql app
   ubuntu# \d posts
   ```



### CRUD

1. Create (**route 2개 필요**)

   ```html
   <!-- index.html -->
   <a href="/posts/new">글 쓰러가기</a>
   ```

   ```python
   # app.rb
   @app.route("/posts/new")
   def new():
       return render_template("new.html")
   ```

   ```html
   <!-- new.html -->
   <form action="/posts" method="POST">
       <input type="text" name="title">
       <textarea name="content"></textarea>
       <input type="submit">
   </form>
   ```

   ```python
   # app.rb
   @app.route("/posts", methods=["POST"])
   def create():
       title = request.form.get('title')
       content = request.form.get('content')
       post = Post(title=title, content=content)
       db.session.add(post)
       db.session.commit()
       # INSERT INTO posts (title, content)
       # VALUES ('1번글', '1번내용');
       return render_template("create.html", post=post)
   ```

   ```html
   <!-- create.html -->
   {{post.id}}
   {{post.title}}
   {{post.content}}
   {{post.created_at}}
   ```

2. Index

   ```python
   # app.rb
   @app.route("/")
   def index():
       posts = Post.query.order_by(Post.id.desc()).all()
       # SELECT * FROM posts;
       # SELECT * FROM posts ORDER BY id DESC;
       return render_template("index.html", posts=posts)
   ```

   ```html
   <!--index.html-->
       {% for post in posts %}
         <th scope="row">{{post.id}}</p>
         <p>{{post.title}}</p>
         <p>{{post.content}}</p>
         <p>{{post.created_at.strftime("%Y년 %m월 %d일 %H:%M")}}</p>
         <p><a href="/posts/{{post.id}}">글 보기</a></p>
       {% endfor %}
   ```

3. R

   ```python
   # app.rb
   @app.route("/posts/<int:id>")
   def read(id):
       post = Post.query.get(id)
       # SELECT * FROM posts WHERE id=1;
       return render_template("read.html", post=post)
   ```

   ```Html
   <!--read.html-->
   <h1>{{post.id}}번째 글!!!</h1>
   <p>제목 : {{post.title}}</p>
   <p>내용 : {{post.content}}</p>
   <p>작성시간 : {{post.created_at}}</p>
   ```

   ​

4. D

   ```html
   <!-- read.html -->
   <a href="/posts/{{post.id}}/delete">글 삭제</a>
   ```

   ```python
   # app.py
   @app.route("/posts/<int:id>/delete")
   def delete(id):
       post = Post.query.get(id)
       db.session.delete(post)
       db.session.commit()
       # DELETE FROM posts WHERE id=2;
       return redirect('/')
   ```

   ​

5. U (**route 2개 필요**)

   ```html
   <!-- read.html -->
   <a href="/posts/{{post.id}}/edit">글 수정</a>
   ```

   ```python
   # app.py
   @app.route("/posts/<int:id>/edit")
   def edit(id):
       post = Post.query.get(id)
       return render_template("edit.html", post=post)
   ```

   ```html
   <!-- edit.html -->
   {% extends "layout.html" %}
   {% block body %}
   <form action="/posts/{{post.id}}/update" method="POST">
       제목 : <input type="text" name="title" value="{{post.title}}"><br>
       내용 : <textarea name="content">{{post.content}}</textarea><br>
       <input type="submit" value="뿅!">
   </form>
   {% endblock %}
   ```

   ```python
   # app.py
   @app.route("/posts/<int:id>/update", methods=["POST"])
   def update(id):
       post = Post.query.get(id)
       post.title = request.form.get("title")
       post.content = request.form.get("content")
       db.session.commit()
       # UPDATE posts SET title = "hihi"
       # WHERE id = 2;
       return redirect("/posts/{}".format(post.id))
   ```

   ​


### 1:N Database Association(Relation)

1. 모델설정

   ```python
   def Post(db.Model):
       #...
       comments = db.relationship('Comment', backref='post')
   	#..

   def Comment(db.Model):
       # ..
       post_id = db.Column(db.Integer, db.ForeignKey('posts.id'), nullable=False)
   ```

2. 댓글 저장하기

   ```python
   # app.rb
   def comment(post_id):
       content = request.form.get('content')
   	comment = Comment(content)
   	post = Post.query.get(post_id)
       post.comments.append(comment)
       db.session.add(comment)
       db.session.commit()
       return render_template()
   ```

3. 활용 예시

  ```
  $ flask shell 
  >> from app import *
  >> comment = Comment.query.first
  >> comment.post
  >> post = Post.query.get(6)
  >> post.comments

  ```

  ​


### 기타 query 문

```python
Post.query.filter_by(title="1").count()
# SELECT COUNT(*) FROM posts
# WHERE title = '1';

Post.query.filter_by(title="1").all()
# SELECT * FROM posts
# WHERE title = '1';

Post.query.filter_by(title="1").first()
# SELECT * FROM posts
# WHERE title = '1' LIMIT 1;

Post.query.filter(Post.title != "1").all()
# SELECT * FROM posts
# WHERE title != '1';

Post.query.filter(Post.title.like("%1%")).all()
# SELECT * FROM posts
# WHERE title LIKE '%1%'; 

from sqlarchemy import and_, or_
Post.query.filter(and_(Post.title == "1", Post.content == "1"))
# SELECT * FROM posts
# WHERE title ="1" AND content="1"
```

