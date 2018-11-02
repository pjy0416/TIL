# Babel

- 인터넷익스플로러(IE)를 포함한 대부분의 브라우저들은 `ES5` 이하의 버전을 사용하기 때문에 , `ES6` 이상 버전의 `자바스크립트(JS)` 코드들을 `ES5` 이하의 버전으로 변환해주기 위해 사용. (모든 최신 기능을 변환해주지는 않지만 중요 문법 및 활용 범위가 넓기 때문에 변환해서 씀)

- `npm(모듈 패키지 매니저)`를 통해서 바벨을 install 해줘야 사용! (설정)

  ``` 
  npm install --save dev @babel/cli @babel/core @babel/preset-env @babel/preset-stage-2
  ```

  바벨 7버전부터는 모든 바벨 패키지들이 @babel이라는 네임 스페이스 안에 속해있다.

- <b>.babelrc</b>라는 파일을 만들어 설치한 preset을 연결한다. (바벨에 대한 설정을 하는 파일, 여기에 `preset`이나 `plugin`을 연결하면 된다. preset => 여러 플러그인들 모음집)

  - env 프리셋은 특정 문법 버전 입력 필요 없이, 타겟 브라우저를 입력하면 알아서 환경에 맞춰 최신 `ECMAScript`을 사용할 수 있게 해준다.

  ``` 
  // .babelrc
  {
      "presets": [
          ["@babel/preset-env", { "targets": { "browsers": ["last 2 
          versions", ">= 5% in KR"] } }], 
          ["@babel/preset-stage-2", { "decoratorsLegacy": true }]
      ]
  }
  // env와 stage-2 설정, target 옵션을 보면 브라우저의 최신 두 버전, 한국에서 5% //이상 점유율을 차지하는 브라우저를 모두 지원하라고 설정.
  ```

- `package.json`에는 scripts 부분에 컴파일 하는 부분을 기재한다 !

  ``` 
  {   
      "scripts": {
      "build": "babel src -d lib"
    	}
  } 
  ```

  `npm run build` 명령어를 수행할 때 babel을 실행, src폴더의 최신 js코드를 컴파일~> 결과를 lib 폴더에 넣으라는 뜻. (-d option => 결과를 저장할 경로)

- <b>@babel/polyfill</b>

  바벨을 사용하면 새로운 문법을 구식의 JS 문법으로만 변환해준다. ~> ES2015 이상의 새로운 객체 (`Promise`, `Map`, `Set` 등)과 메소드(`Array.find`, `Object.assign` 등)을 사용할 수 없음.

  이를 사용할 수 있게 해주는 것이 babel-polyfill 이다. (아래는 npm 설정)

  ``` 
  npm install @bable/polyfill
  ```

  ES2015 이상의 문법을 사용하는 파일 가장 윗부분에 임포트를 추가해야하는데,

  <b>`하나의 프로젝트에 한 번만 사용`</b>해야 한다. 그렇지 않으면 충돌이 발생!

  ``` javascript
  // 예시
  import '@babel/polyfill';
  import ...
  const val = Object.assign({ a: '1' }, { a: 'b' });
  export default val;
  ```

  웹팩을 사용한다면 파일에 import 해주는 대신 웹팩 설정 파일 entry에 적어도 된다! (이게 더 좋은 것 같음)

  ``` 
  //webpack.config.js
  {
  	entry: ['@babel/polyfill', './app.js'],
  }
  ```

  위와 같이 설정하면 app.js에 polyfill을 알아서 적용해준다.

  `Polyfill` : `웹 개발에서 기능을 지원하지 않는 웹 브라우저 상의 기능을 구현하는 코드`

  [더 자세히 보기](https://www.zerocho.com/category/ECMAScript/post/57a830cfa1d6971500059d5a)

# Webpack		

##### 											간단하게 요약하면 모듈 번들러!

- `자바스크립트(JS)` 코드가 많아지면 하나의 파일로 관리하는데 한계가 생긴다. 그렇다고 여러개의 파일을 브라우져에서 로딩하는 것은 1) 그 만큼 네트워크 비용을 치뤄야 한다는 단점이 존재하고 2) 각 파일이 서로의 스코프를 침범하는 충돌의 위험성도 존재할 수 있음

- `JS`는 즉시호출함수(IIFE)를 사용해 모듈을 만들 수 있고, `CommonJS` or `AMD` 등 모듈 시스템으로 파일별 모듈 관리가 가능하다. 하지만 브라우저에서 사용하기에는 어려움.

- 모듈을 IIFE 스타일로 변경 + 하나의 파일로 묶어(Bundled) 네트워크 비용을 최소화 하는 방법이 Webpack이다.


> <b>엔트리(Entry) + 아웃풋</b>

- `웹팩`에서는 모든것이 모듈, JS가 로딩하는 모듈이 많아질 수록 모듈간 의존성 ↑. 
- 의존성 그래프의 시작점을 `Entry`라 칭함.
- 웹팩은 `Entry`를  통해 필요한 모듈 로딩 및 하나의 파일로 묶음(Bundled)
- 설정

```javascript
//webpack.config.js 를 통해서 설정
module.exports = {
	entry : {
    	main: '경로+파일이름', // Entry
	},
    output : {	// 
    	filename: 'bundle.js', // 주로 bundle.js로 naming을 함
        path : '원하는 경로'		// front 개발 시 이 bundled된 파일(bundle.js)를
        					   // 불러와서 사용하면 됨
    } 
} ;
```



> <b> 로더 </b>

- 웹팩은 모든 파일(js파일,이미지,폰트 등...)을 모듈로 관리, but 웹팩은 JS밖에 모른다.
  따라서 JS가 아닌 파일에서 웹팩을 이해하도록 변경 ~> `로더`가 그 역할을 해줌

- test와 use키로 구성된 객체로 설정
  <b>test</b>에  로딩할 파일을 지정, <b>use</b>에 적용할 로더를 설정 하면 끝!

- <b>Babel-loader</b>
  `바벨`로 설명하면 <b>test</b>에 ES6로 작성한 JS파일을 지정하고, <b>use</b>에 변환할 바벨 로더를 설정

  ```javascript
  //webpack.config.js
  module.exports = {
    module: {
      rules: [{
        test: /\.js$/,	//.js로 끝나는(js 확장자인)파일을 정규식을 통해 명령
        exclude: 'node_modules',	// node_module은 패키지 폴더, 따라서 제외
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['env']
          }
        }
      }]
    }
  };
  ```



> <b>플러그인</b>

- `로더` => 파일단위로 처리.		`플러그인` => 번들된 결과물들 처리.

- `UglifyJsPlugin ` => 로더로 처리된 JS결과물들을 난독화.
  웹팩 설정 객체의 plugins 배열에 추가시킴.

  ```javascript
  // 사용 예시 !!
  const webpack = require('webpack')
  
  module.exports = {
      plugins: [
          new webpack.optimize.UglifyJsPlugin(),
      ]
  };
  ```

- `ExtractTextPlugin` => CSS파일을 처리할 경우 `사스(SASS)`로 변환하여 웹팩에 사스로더(`sass-loader`)만 추가하면 된다. 하지만 크 파일이 커진다면 분리하는 것이 효율적이다.

  이 때 사용하는 것이 extract-text-webpack-plugin이다. (style.css파일로 따로 번들링)

  ```javascript
  // webpack.config.js
  const ExtractTextPlugin = require('extract-text-webpack-plugin')
  
  module.exports = {
    module: {
      rules: [{
        test: /\.scss$/,
        use: ExtractTextPlugin.extract({
          fallback: 'style-loader',
          use: ['css-loader', 'sass-loader']
        })
      }]
    },
    plugins: [
      new ExtractTextPlugin('style.css')
    ]
  };
  ```

[더 자세한 설명 보기](http://blog.jeonghwan.net/js/2017/05/15/webpack.html)