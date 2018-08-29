## python

###### 																	파이썬은 일급 객체

[파이썬 언더스코어](https://mingrammer.com/underscore-in-python/)

* `flask 실행 명령어` = `flask run --host 0.0.0.0 --port 8080`
  실행중인 `flask` 죽이는 명령어 => `killall flask`

* `데코레이터` = 함수 안에서 함수를 호출할 때 사용(종속의 느낌으로 사용)

``` python
def hello(f) :
    def greeting() :
        print("^0^")
        f()
        print("ㅠㅠ")
    return greeting

@hello
def korean() :
    print("안녕하세요")
    
@hello
def english() :
    print("hello")
    
def test() :
    print("테스트입니다")
    
korean()
english()
hello(test())

#-----------------------------구분선------------------------------#
#a = hello(test)
#a()
```

``` 결과창
jin0:~/workspace $ python3 decorators.py
^0^
안녕하세요
ㅠㅠ
^0^
hello
ㅠㅠ
테스트입니다

#-----------------------------구분선------------------------------#
^0^
테스트입니다
ㅠㅠ
```



> **render_template**

```python
import render_template
```

* 파이썬에서 웹문서 자체를 return해 줄 수 있음

> import vs from import

[import ~ 와 from ~ import ~](https://wikidocs.net/3105)

