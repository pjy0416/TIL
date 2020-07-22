<h3>CSS란</h3>



- HTML과 같이 프로그래밍 언어도 아니고 마크업 언어도 아니라, CSS는 Style sheet 언어다. HTML 문서에 있는 요소들에 선택적으로 스타일을 적용할 수 있다는 의미.

  - HTML의 모든 문단 요소들을 선택하고 그 문단 요소들 안에 있는 텍스트를 빨갛게 바꾸려고 한다면 다음과 같이 CSS를 작성할 것이다.

    ```css
    p {
    	color: red;
    }
    ```

  - 위와 같이 작성한 style.css를 styles 디렉토리에 저장한 이후, Index.html의 `<head>` 의 안에 불러와서 적용하기

    ```html
    <link href="styles/style.css" rel="stylesheet" type="text/css">
    ```

    

- CSS의 ruleset 해부

- ![CSS 해부](https://mdn.mozillademos.org/files/9461/css-declaration-small.png)
  - 전체 구조는 **rule set**이라 불린다. (종종 줄여서 "rule"이라고 한다.)

  - 선택자(Selector)

    - rule set의 맨 앞에 있는 HTML 요소 이름. Selector는 꾸밀 요소들을 선택한다. 
      - 이 예제에서는 p 요소

  - 선언

    - `color: red`와 같은 단일 규칙, 꾸미기 원하는 요소의 속성을 명시한다.

  - 속성(property)

    - 주어진 HTML 요소를 꾸밀 수 있는 방법 ( 이 예시에서 color는 p 요소의 속성)
    - CSS에서, rule 내에서 영향을 줄 속성을 선택

  - 속성 값

    - 속성의 오른쪽 콜론 뒤에, 주어진 속성에 선택한 속성 값을 갖는다.

  - 이외의 rule set 보기

    - 각각의 rule set(Selector로 구분)은 반드시 `{ }`로 감싸져야 한다.

    - 각각의 선언 안에, 각 속성을 해당 값과 구분하기 위해 콜론 `:`을 사용해야 한다.

    - 각각의 rule set 안에, 각 선언을 그 다음 선언과 구분하기 위해 세미콜론 `;`을 사용

      ```css
      p {
        color: red;
        width: 500px;
        border: 1px solid black;
      }
      ```



- 여러 요소 선택하기

  ```css
  p,li,h1 {
    color: red;
  }
  ```
  - 여러 요소 타입을 선언하고 하나의 rule set을 적용 가능



- 선택자의 여러 종류

  - | 선택자 이름                                       | 선택하는 것                                                  | 예시                                                         |
    | :------------------------------------------------ | :----------------------------------------------------------- | :----------------------------------------------------------- |
    | 요소 선택자 (때때로 태그 또는 타입 선택자라 불림) | 특정 타입의 모든 HTML 요소.                                  | `p`<br />`<p>` 를 선택                                       |
    | 아이디 선택자                                     | 특정 아이디를 가진 페이지의 요소 (주어진 HTML 페이지에서, 아이디당 딱 하나의 요소만 허용됩니다). | `#my-id`<br /> `<p id="my-id">` 또는 `<a id="my-id">` 를 선택 |
    | 클래스 선택자                                     | 특정 클래스를 가진 페이지의 요소 (한 페이지에 클래스가 여러번 나타날 수 있습니다). | `.my-class`<br /> `<p class="my-class">`와 `<a class="my-class"` 를 선택 |
    | 속성 선택자                                       | 특정 속성을 갖는 페이지의 요소.                              | `img[src]`<br /> `<img src="myimage.png">`를 선택하지만 `<img>`는 선택 안함 |
    | 수도(Pseudo) 클래스 선택자                        | 특정 요소이지만 특정 상태에 있을 때만, 예를 들면, hover over 상태일 때. | `a:hover`<br /> `<a>` 를 선택하지만, 마우스 포인터가 링크위에 있을 때만 선택함 |

  - [선택자 가이드](https://developer.mozilla.org/en-US/docs/Web/Guide/CSS/Getting_started/Selectors)



---



<h3>글꼴과 문자</h3>

- [google font](https://fonts.google.com/)에서 글꼴 선택하고 Embed에 있는 링크 확인하고 적용하기

  - `index.html`의 `<head>` 안에 `<link>` 요소를 추가하기

    ```html
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo&display=swap" rel="stylesheet">
    ```

  - `style.css`파일에 html 전체 페이지에 font 적용하기

    ```css
    html {
      font-size: 10px;
      font-family: placeholder: 'Nanum Myeongjo', serif;
    }
    ```

  - HTML body 안에 문자를 포함하는 요소 `h1`, `li` 및 `p`를 위해 글자 크기를 설정하기

    ```css
    h1 {
      font-size: 60px;
      text-align: center;
    }
    
    p, li {
      font-size: 16px;    
      line-height: 2; 
      letter-spacing: 1px;
    }
    ```

    

---



<h3>Box, Box, Box의 모든 것</h3>



- CSS 레이아웃은 박스모델을 주 기반으로 하고 있다. 페이지의 공간을 차지하고 있는 각각의 블록들은 이와 같은 속성들을 가진다.

  - `padding`
    -  컨텐츠 주위의 공간 (ex: 문단 글자의 주위)
  - `border`
    - padding 의 바깥쪽에 놓인 실선
  - `margin`
    -  요소의 바깥쪽을 둘러싼 공간
  - ![BOX 그림](https://mdn.mozillademos.org/files/9443/box-model.png)
    - `width` = 한 요소의 너비
    - `background-color` = 요소의 콘텐츠와 padding의 배경 색
    - `color` = 한 요소의 콘텐츠 색 (일반적으로 글자색)
    - `text-shadow` = 한 요소 안의 글자에 그림자를 적용
    - `display` = 요소의 표시 형식 설정

- 페이지 색 바꾸기

  ```css
  html {
      background-color:#db2a1a;
  }
  ```



- body 외부 정렬하기

  ```css
  body {
      width: 600px;
      margin: 0 auto;
      background-color : #29f0f0;
      padding: 0 20px 20px 20px;
      border: 5px solid black;
  }
  ```

  - `width: 600px` 
    - body가 항상 600 pixels의 너비를 갖도록 강제 지정
  - `margin: 0 auto;`
    - `margin` or `padding` 처럼 한 속성에 두개의 값을 성정할 때, 첫 번째 값은 요소의 상단과 하단에 영향을, 두 번째 값은 좌우측에 영향을 준다. 또한, 하나 or 셋 or 네개의 값을 사용할 수도 있음
  - `background-color : #29f0f0;`
    - 요소의 배경 색을 설정 ~> 나는 붉은색
  - `padding: 0 20px 20px 20px;`
    - padding 에는 콘텐츠 주위에 약간의 공간을 주기 위한 네 개의 값이 있다. 맨 첫번째는 상단, 두번쨰는 왼쪽, 아래, 오른쪽 (12시부터 시계방향)으로 값을 설정
  - `border: 5px solid black;`
    - body 모든 면의 border를 5pixels 두께의 실선으로 설정
      

- 메인 페이지 제목 배치하고 꾸미기
  ```css
  h1 {
    margin: 0;
    padding: 20px 0;    
    color: #00539F;
    text-shadow: 3px 3px 1px black;
  }
  ```

  - 바디 상단에 틈이 있다는 것을 확인할 수 있다. 브라우저가 `h1`요소에 **초기 스타일링**을 적용했기 때문에 발생, 위에처럼 `margin: 0;` 설정으로 틈을 제거할 수 있다.
  - `text-shadow: 3px 3px 1px black;`
    - 첫 번째 pixel 값은 그림자의 **수평 오프셋**을 설정 - 얼마나 옆으로 이동시킬 것인지
    - 첫 번째 pixel 값은 그림자의 **수직 오프셋**을 설정 - 얼마나 아래로 이동시킬 것인지
    - 세 번째 pixel 값은 그림자의 **흐림 반경**을 설정 - 큰 값은 더 흐릿한 그림자를 의미
    - 네 번째 pixel 값은 그림자의 기본 색상을 설정
      

- 이미지 가운데 정렬

  ```css
  img {
    display: block;
    margin: 0 auto;
  }
  ```

  - 위에 body 에서 했듯이 다시 `margin: 0 auto` 트릭을 사용해 볼 수 있지만, 무언가 더 해야할 필요가 있습니다. `body` 요소는 **block level** 입니다.
  - 이는 페이지의 공간을 차지하고, margin과 여기에 적용된 다른 여백값을 가질 수 있다는 것을 의미, 반면에 이미지는 inline 요소.
  - 따라서, 이미지에 margin을 적용하기 위해서는, `display: block;`을 사용해야 이미지에 block-level 성질을 준다.