## Node.js 와 Express

> <b>Node.js</b>

`Node.js` 는  `자바스크립트`를 브라우저 이외에서도 실행하는데 사용되는 <b>자바스크립트 플랫폼</b>.

이 특징으로 JS 문법으로 '서버'를 구축할 수 있다. (노드 이전에는 서로 다른 기술과 패러다임, 라이브러리 등을 함께 써야 했음). 이러한 특징으로 자바스크립트의 백엔드와 프론트엔드의 경계가 무뎌짐.



> <b><I>Why use a Node.js ??</I></b>

1.  JS 엔진이 빠르다 !

   - 구글 크롬 엔진이 기반이기 때문에 수천 개의 명령어를 1초만에 처리 가능.

2.  비동기 작업 !

   - 클라이언트의 요청을 응답하면서 또 다른 요청을 받을 수 있다. (여러 요청 받을 수 있음)


> <b>Express</b>

	`Express`는 Node.js의 웹 서버 기능 위에 올라가는 `프레임 워크`로서, 핵심 모듈인 `http`와 

	Connect 컴포넌트를 기반으로 하는 `웹 프레임 워크`.

	`컴포넌트` => `독립적인 단위 모듈`

Node.js의 API를 단순화 + 유용한 새로운 기능 추가 (JQuery와 역할이 비슷하다고 생각하면 편함)

-> Node.js로 웹 어플리케이션을 더 쉽게 만들기 위해 Express가 탄생했다. 따라서, 프레임워크에서는 최소한의 것들만 지원! 필요한 부분은 라이브러리를 찾아서 사용. (확장성 고려 + 유연성)



> <b>Express의 핵심</b>

1. 미들웨어(Middle Ware)

   Node.js는 하나의 요청에 handler 함수를 제공. 요청이 handler를 통해 들어오고 응답을 보냄.

   여러 작업의 작은 덩어리로 다루는 몇 개의 요청 핸들러 함수를 호출하는데, 이 더 작은 요청 핸들러 함수를 <b>`미들웨어`</b>라고 한다. Express를 위해 적절한 미들웨어가 만들어져있어서 핵심 장점으로 봄.

2. 라우팅 (Routing)

   - `middle ware`처럼 요청 핸들러 함수를 작게 나누지만, 조건에 따라 요청 핸들러가 실행.

   (URL과 [Get, Post, Delete 등] 방식 메소드 ) ~> 어플리케이션 동작을 라우터로 분할, 응답.

   - Express의 규모가 커졌을 때, 여러개의 폴더와 파일로 나눌 때 사용. (서브 어플리케이션)

> <b>Express의 사용</b>

	Express는 SPA(Single Page Application) 개발에 주로 사용된다.

	SPA에서 필요한 html과 다른 파일들을 서비스 하는데 좋으며, API를 만들 때도 좋다.

	( ※ Node.js는 싱글 쓰레드 기반이기 때문에, CPU작업이 많이 소요되는 어플리케이션에는 

	부적합함)



 ``` 
$ npm install express@specific-version 
 ```

specific-version에는 3.3.5와 같은 말 그대로 특정 버전이 들어감.

``` 
//pakage.json, example for an express.js v3.3.5
{
  "name": "expressjsguide",
  "version": "0.0.1",
  "description": "",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "dependencies": {
    "express": "3.3.5"
  },
  "author": "",
  "license": "BSD"
}
```

``` 
$ npm install
```

기존 프로젝트에 Express.js를 설치해서 이미 존재하는 package.json 파일에 의존 모듈을 저장하고 싶다면

``` 
$ npm install express --save
```

를 이용하여 저장! 검사는 `$ npm ls`를 실행

> #### 단점

- `Single Thread` 모델이기 때문에 하나의 작업이 시간 소요가 심하다면, 전체 시스템 성능이 급격히 저하된다.

- `Event call-back`을 기준으로 하기 때문에 call back의 많은 중첩(call back hell)이 있으면 가독성이 급격히 저하된다.

- 멀티 코어 머신의 CPU 사용을 최적화 할 수 없다. (왜냐하면 Single Thread....)
  따라서, `Cluster` 모듈등을 이용하여 하나의 서버에서 여러개의 노드 프로세스를 사용하는 모델을 가지는 설계가 필요함. (즉, 여러대의 컴퓨터를 써야한다)
  `Cluster` => 여러 대의 컴퓨터들이 연결되어 하나의 시스템처럼 동작하는 컴퓨터들의 집합.

- V8 엔진을 기반으로 하는데, V8 엔진은 Garbage collection  기반의 메모리 관리 ~> 순간 서버 다운 가능성 존재

- 운영하고자 하는 OS에 따라서 OS 설정이 다리고, 또한 multiplexing 라이브러리에 따라서 성능이 차이가 나기 때문에, node.js 운영전에, 운영할 OS에서 테스트 및 튜닝을 해보기를 권장









  [참고 1](http://jeong-pro.tistory.com/57)				[참고 2](http://wikibook.co.kr/article/what-is-expressjs/)					[단점 참고](http://bcho.tistory.com/876)




