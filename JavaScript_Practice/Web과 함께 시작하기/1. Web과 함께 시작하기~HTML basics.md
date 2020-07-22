**[Web과 함께 시작하기](https://developer.mozilla.org/ko/docs/Learn/Getting_started_with_the_web)**




<h3>기본 소프트웨어 설치</h3>
- 텍스트 편집기 설치

  - [Brackets](http://brackets.io/)
    - 무료라는 장점
    - 실시간 미리보기
    - 코드 힌트 제공

- 최신 웹 브라우저 설치

  - Chrome, Opera, Firefox, IE, Microsoft Edge 정도 있지만, IE와 Edge는 기본 내장이고, chrome을 이용하고 있으므로 따로 설치하지는 않겠다. 나중에 진행하다 필요하면 설치

- [로컬 웹 서버 설치하기](https://developer.mozilla.org/ko/docs/Learn/Common_questions/set_up_a_local_testing_server)

  - 예제 확인을 위해 로컬 웹 서버를 설치하라고 하는데, 이것도 나중에 필요하면 하겠다.

    

---



<h3>중요한 것 먼저 하기 : 계획 세우기</h3>
- 웹사이트를 만들기 전, 어떤 서비스를 제공할지, 이 사이트를 통해 무엇을 할 수 있는지 먼저 생각해야한다.
- 하지만, 기본을 시작하는 단계기 때문에, 간단한 웹페이지 제작부터 시작
- 시작에 앞서, 3가지 사항을 체크해야한다.
  - 웹사이트는 무엇에 관한 것인가?
    - 강아지나 뉴욕, 또는 팩맨 좋아하세요? (너무 외국 스럽다.. 서울, 롤로 바꾸자)
  - 주제에 대해 어떤 정보를 나타낼 것인가?
    - 웹페이지에 나타내기 원하는 제목과 몇개의 문단들, 그림에 대한 생각을 작성
      - 화려한 도시 사진, 마음 편해지는 풍경 사진, 기타 등등..
  - 웹사이트의 외관은 어떻게 할건가?
    - in simple high-level terms. 배경색은 무엇으로 할 것인지?어떤 글꼴(폰트)이 적합한지: 딱딱하게, 만화스럽게, 굵고 크게, 얇게?



---



<h3>디자인을 스케치하기</h3>
- 펜과 종이로 웹사이트의 대략적인 모습을 그린다. (대충그려도 상관 없음 ~> 나는 일단 안하겠다..)



---



<h3>자원 선택하기</h3>
- 웹페이지에 최종적으로 나타나게 될 내용을 합치는 것을 시작하기 좋다...??
- 문자
  - 제목이나 문단은 일찍 작성하는 것이 좋다. 항상 신경써야한다.
- 주요 색상
  - [the Color Picker](http://www.w3schools.com/tags/ref_colorpicker.asp)에서 미리 원하는 색을 찾아보기 (404Err뜸, [구글 검색](https://www.google.com/search?q=color+picker&oq=color+picker&aqs=chrome..69i57j0l7.3797j0j4&sourceid=chrome&ie=UTF-8))
  - #660066같은 여섯 문자의 코드를 보게 될 것 ~> hex(adecimal)코드라고 불리고, 색을 나타냄
    - 나는 #29f0f0 ~> 시원한 하늘색

- 그림들
  - [Google Images](https://www.google.com/imghp?gws_rd=ssl)에서 적합한 것 찾기
    - 저작권 침해 가능성을 줄이기 위해, google 검색도구 ~> 사용 권한 선택
- 글꼴
  - [Google Fonts](https://fonts.google.com/)
    - 지금 당장은 안깔아도 될 것 같다.



---



<h3>웹사이트는 컴퓨터의 어디에 두어야 할까요?</h3>


- 원하는 위치에 딕렉토리를 정해서 만들기
  - ex) `web-projects`와 같은 이름의 폴더 만들기 ~> 해당 폴더 안에 `test-site`같은 폴더 만들기



---



<h3>웹사이트는 어떤 구조를 가져야 할까요?</h3>


- 사이트가 가져야할 구조
  - index.html
    - 홈페지지 내용들을 포함, 사이트에 처음 방문했을 때 보는 문자와 이미지들
    - 텍스트 에디터를 사용해서, `index.html`을 생성하고 `test-site`폴더 안에 저장
  - images 폴더
    - 테스트에 사용할 모든 이미지들을 포함
    - `test-site` 폴더안에 `images`라는 폴더 만들기
  - styles 폴더
    - 스타일링(문자와 배경색 세팅 등)을 위한 CSS 코드를 담는다. `test-site`폴더 안에, `styles` 라는 폴더 생성
  - scripts 폴더
    - 사이트에 상호작용하는 기능을 추가할 때 사용되는 모든 JavaScript 코드를 포함
    - `test-site`폴더 안에, `scripts`라는 폴더 ㅅ ㅐㅇ성



---



<h3>파일 경로</h3>


- 파일들이 서로 통하게 하기 위해선, 파일들간의 파일 경로를 제공해야한다.

  - 기본적으로 어떤 파일이 다른 파일이 어디에 있는지 알 수 있게 경로 제공

- `index.html` 에 `HTML` 작성하기

  - `images` 폴더 안에 아까 저장했던 이미지를 복사해두기

  - index.html 파일을 열고, 아래의 코드를 작성하기

    ```html
    <!DOCTYPE html>
    <html>
      <head>
        <meta charset="utf-8">
        <title>My test page</title>
      </head>
      <body>
        <img src="" alt="My test image">
      </body>
    </html> 
    ```

  - `<img src="" alt="My test image">`는 페이지 안으로 이미지를 삽입하는 HTML 코드

    - `images/your-filename.확장자`이 경로가 된다.
    - 나는 `images/Liverpool.jpg`가 된다.
      

- 파일 경로를 위한 일반적인 규칙들

  - 호출하는 HTML 파일과 같은 디렉토리에 있는 파일을 연결하려면 파일이름만 사용하면 된다. (ex: `my-image.jpg`)
  - 하위 폴더에 위치한 파일을 참조하기 위해서는, 디렉토리 이름과 전방향 슬래시(/)를 경로 앞에 추가. (ex: `subdirectory/my-image.jpg`)
  - 호출하는 HTML 파일의 상위 디렉토리에 있는 파일을 연결하려면, `..`을 찍어야 한다. 예를 들면, 만약 `index.html`가 `test-site`의 하위 폴더 안에 있고 `my-image.png`가 `test-site` 안에 있을 때,  `../my-image.png` 경로를 통해 `index.html`에서 `my-image.png`를 참조
  - 여러분이 원하는대로 조합해서 사용할 수 있다, 예를 들면, `../subdirectory/another-subdirectory/my-image.png`



---



<h3>HTML은 무엇인가</h3>


- `HTML != 프로그래밍 언어`, 컨텐츠의 구조를 정의하는 마크업 언어다. 
- HTML은 컨텐츠의 서로 다른 부분들을 씌우거나(wrap) 감싸서(enclose) 다른 형식으로 보이게하거나 특정한 방식으로 동작하도록 일련의 요소(elements)로 이루어져 있다. 
- `tags`로 감싸는 것으로 단어나 이미지를 다른 곳으로 하이퍼링크(hyperlink)하거나 단어들을 이탤릭체로 표시하고, 클씨체를 크게 or 작게 만드는 등의 일을 한다.
  - `나는 Zin0`
    - 위의 문장을 독립적인 구문으로 만드려면, 문단 태크(paragraph tags)로 해결
    - `<p>나는 Zin0<p>`



- HTML 요소 분석

  - ![문단 요소](https://mdn.mozillademos.org/files/9347/grumpy-cat-small.png)

  - 여는 태그(opening tag)
    - 요소의 이름으로 구성되고(여기는 p 태그), 여닫는 꺾쇠괄호`<sth tag>`로 감싸진다. 요소가 시작되는 곳, 효과를 시작하는 곳을 의미, 여기서는 문단이 시작되는 위치
  - 닫는 태그(closing tag)
    - 여는 태그와 같지만, 요소의 이름 앞에 `/`가 포함된다. `</sth tag>` 요소의 끝을 나타낸다. 여기서는 문단의 끝 위치를 나타냄
  - 컨텐츠(content)
    - 요소의 내용(content), 여기서는 예제의 그냥 텍스트
  - 요소(element)
    - 요소는 여는 태그와 닫는 태그, 컨텐츠로 이루어진다.
  - ![요소의 속성](https://mdn.mozillademos.org/files/9345/grumpy-cat-attribute-small.png)
  - 속성은 실제 컨텐츠로 표시되길 원하지 않는 추가적인 정보를 담고 있다. 이 예제에서, class 속성을 이용해 나중에 해당 요소를 특정 스타일이나 다른 정보를 설정할 때 사용할 수 있는 식별자를 지정할 수 있다.
  - 속성이 항상 가져아 하는 것
    - 요소 이름 (or 요소가 하나 이상 속성을 가지고 있다면 이전 속성)과 속성 사이에 공백
    - 속성 이름 뒤에는 등호(=)가 와야한다.
    - 속성 값의 앞 뒤에 열고 닫는 인용부호("" or '')가 있어야 한다.
      

- 요소 중첩

  - 요소 안에 다른 요소를 넣을 수 있다. => `중첩(nesting)` 이라고 함

  - 우리 고양이는 **아주** 고약하다를 표기하려면 `<strong>`요소로 "아주"를 감싸면 된다

    ```html
    <p>내 고양이는 <strong>아주</strong> 고약해.</p>
    ```

    - `<p>` 태그를 먼저 열고 `<strong>`태그를 중첩했기 때문에, `</strong>`먼저 닫고 `</p>`를 닫아야한다.



- 빈 요소

  - 어떤 요소들은 내용을 갖지 않는데, 이것을 **빈 요소(empty elements)**라고 한다.

  - `<img>`요소는 이미 위의 HTML 코드에 작성했다.

    ```html
    <img src="images/Liverpool.jpg" alt="My test image">
    ```

    - 이 요소는 두개의 속성을 포함하고 있으나 닫는`</img>`태그가 없다. 이미지 요소는 효과를 주기 위해 컨텐츠를 감싸지 않기 때문. 이 요소의 목적은 HTML 페이지에서 이미지가 나타날 위치에 이미지를 끼워 넣는 것
      

- HTML 문서 해부

  - 각 HTML 요소의 기본적인 내용들은 살펴봤지만, 별로 유용하지는 않았다. 이제 각 요소들이 어떻게 전체 HTML 페이지를 구성하는지 살펴보자. `index.html` 예제에 넣은 코드를 다시 살펴보자.

    ```html
    <!DOCTYPE html>
    <html>
      <head>
        <meta charset="utf-8">
        <title>My test page</title>
      </head>
      <body>
        <img src="images/Liverpool.jpg" alt="My test image">
      </body>
    </html>
    ```

    - `<!DOCTYPE html>`
      - doctype. 아주 오래전 HTML이 막 나왔을 때, doctype은 good HTML로 인정받기 위해 HTML 페이지가 따라야 할 일련의 규칙으로의 연결통로로써 작동하는 것을 의미했다. 하지만, 최근에는 아무도 그런 것들에 대해 신경쓰지 않으며 모든 것이 올바르게 동작하게 하기 위해 포함되어야 할 역사적인 유물일 뿐.
    - `<html></html>`
      - `<html>`요소(element). 페이지 전체의 컨텐츠를 감싸며, 루트(root) 요소라고도 한다.
    - `<head></head>` 
      - `<head>`요소(element). HTML 페이지에 포함되어 있는 모든 것들(페이지를 조회하는 사람들에게 보여주지 않을 컨텐츠)의 컨테이너 역할. 여기에는 keywords와 검색 결과에 표시되길 원하는 페이지 설명, 컨텐츠를 꾸미기 위한 CSS, 문자 집합 선언 등과 같은 것들이 포함
    - `<body></body>` 
      - `<body>` 요소(element). 페이지에 방문한 모든 웹 사용자들에게 보여주길 원하는 모든 컨텐츠를 담고 있으며, 텍스트, 이미지, 비디오, 게임, 오디오 트랙 등 무엇이든 될 수 있다.
    - `<meta charset="utf-8">`
      - 문서가 사용해야 할 문자 집합을 `utf-8`로 설정 ~> 많은 문자가 포함되어 있기 때문에 어떤 문자 컨텐츠도 다룰 수 있다.
    - `<title></title>`
      - 페이지의 제목을 설정하는 것으로 페이지가 로드되는 브라우저의 탭에 이 제목이 표시. 북마크나 즐겨찾기에서 페이지를 설명하는 것으로도 사용된다.



---



<h3>이미지</h3>


- 위에서 작성한 `<img>` 요소 다시 보기
  - ` <img src="images/Liverpool.jpg" alt="My test image">`
    - 파일 경로를 포함하는 `src`속성을 통해, 이미지가 나타나야 할 위치에 이미지를 끼워 넣는다. 
  - `alt` 속성도 존재한다.
    - 이 속성은 아래 2가지 이유로 이미지를 볼 수 없는 사용자들을 위한 설명문을 지정가능
      - 시각 장애인의 경우, 시각 장애가 심한 사용자들은 alt 텍스트(대체 텍스트)를 읽어주는 스크린 리더라는 도구를 자주 사용
      - 무언가 잘못돼서 이미지를 표시할 수 없는 경우.
        - 예를 들면, `src` 속성 안의 경로를 틀리게 작성된 경우 alt 텍스트가 뜸
    - alt 텍스트에서 핵심 단어는 `설명적인 문자(descriptive text)`
      - 따라서 위 처럼 `My test image` 보다는 이미지에 대한 설명을 써야한다.



---



<h3>문자 나타내기</h3>


- 제목

  - 특정 부분이 제목 or 하위 제목임을 구체화 할 수 있게, `<h1> ~ <h6>`까지 이용

- 문단

  - `<p>` 요소로 문자의 문단을 지정
  - 일반적인 문자 내용을 나타낼 떄 많이 사용
  - `<p>This is a single paragraph</p>`

- 목록

  - 순서가 없는 목록
    - `<ul>`요소로 둘러 쌓여있다. => 쇼핑 목록과 같은 연관이 없는 것
  - 순서가 있는 리스트
    - `<ol>`요소로 둘러 쌓여있다. => 조리법 처럼 항목의 순서가 중요한 목록들

  ```html
  <p>At Mozilla, we’re a global community of</p>
      
  <ul> 
    <li>technologists</li>
    <li>thinkers</li>
    <li>builders</li>
  </ul>
  
  <p>working together ... </p>
  ```



---



<h3>연결</h3>



- **연결은 웹을 웹으로 만들어 주는 아주 중요한 요소**

- `<a>` (anchor(닻)"의 약자) 를 이용해서 링크를 만들어 준다.

  ```html
  <a href="https://www.google.co.kr">Google</a>
  ```

  - `href` => 연결 될 링크 주소
  - 웹 주소 시작 부분에 `프로토콜`(https:// or http://)를 빼먹으면 예상치 못한 결과를 얻는다.
    - 파일을 찾을 수 없다고 뜬다.

