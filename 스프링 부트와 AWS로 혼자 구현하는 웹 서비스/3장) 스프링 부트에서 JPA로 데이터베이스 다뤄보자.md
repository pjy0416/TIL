

웹 서비스 개발, 운영에서 이전에는 MyBatis와 같은 SQL 매퍼를 이용해서 DB 쿼리를 작성했었다.

**문제점 **

1) SQL 다루는 시간 > 개발 시간

2) 객체지향 프로그래밍과 관계형 디비 간 상호 호환성 저하(테이블 모델링에 더 집중하는 현상) 발생 (패러다임 불일치)

**객체지향 프로그래밍 : 메세지를 기반으로 기능과 속성을 한 곳에서 관리**

**관계형 데이터베이스 : 어떻게 데이터를 저장할지 초점**

~> JAP가 이 문제를 해결



<h3>JPA</h3>
- 자바 표준 ORM(Objecet Relational Mapping)
- OOP 언어와 RDB 중간에서 패러다임을 일치시켜주는 기술
- Spring Data JPA
  - JPA는 인터페이스로서 자바 표준명세서
    따라서, 사용하기 위해서는 구현체가 필요(Hibernate, Eclipse Link 등이 있지만, Spring에서 JPA를 사용할 때는 이 구현체를 직접 다루지 않음)
  - 구현체를 쉽게 사용하고자 Spring Data JPA 모듈을 사용 ~> JPA를 다룸
    Spring Data JPA -> Hibernate -> JPA
  - **장점**
    - <u>**구현체 교체 용이성**</u> -> Hibernate 외에 다른 구현체로 쉽게 교체하기 위함
    - <u>**저장소 교체 용이성**</u> -> 관계형 DB 외에 다른 저장소로 쉽게 교체하기 위함
- 높은 러닝커브를 요하지만, 잘 이용하면 OOP의 장점을 살리면서 네이티브 쿼리만큼 퍼포먼스를 낼 수 있다.
  

---



<h3>프로젝트에 Spring Data Jpa 적용하기</h3>
- build.gradle에 spring data jpa와 h2 database 의존성 등록

  ```java
  compile('org.springframework.boot:spring-boot-stater-data-jpa')
  compile('com.h2.database:h2')
  ```

  - spring-boot-starter-data-jpa
    - 스프링 부트용 Spring Data Jpa 추상화 라이브러리
    - 스트링 부트 버전에 맞춰 자동으로 JPA관련 라이브러리들의 버전을 관리해 준다.
  - h2
    - 인메모리 관계형 데이터베이스
    - 별도의 설치 없이, 프로젝트 의존성만으로 관리 가능
    - 메모리에서 실행 ~> 애플리케이션 재시작마다 초기화 ~> 테스트 용도로 많이 사용

- domain 패키지 생성

  - 게시글, 댓글, 회원, 결제, 정산 등 SW에 대한 요구사항 or 문제 영역
  - DAO 패키지와는 다르다.

- domain.posts 패키지 생성, posts 클래스 생성

  ```java
  package com.zin0.book.springboot.domain.posts;
  
  import com.zin0.book.springboot.domain.BaseTimeEntity;
  import lombok.Builder;
  import lombok.Getter;
  import lombok.NoArgsConstructor;
  
  import javax.persistence.*;
  
  @Getter
  @NoArgsConstructor
  @Entity
  public class Posts {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
  
      @Column(length =500, nullable = false)
      private String title;
  
      @Column(columnDefinition = "TEXT", nullable = false)
      private String content;
  
      private String author;
  
      @Builder
      public Posts(String title, String content, String author) {
          this.title = title;
          this.content = content;
          this.author = author;
      }
  
      public void update(String title, String content) {
          this.title = title;
          this.content = content;
      }
  }
  ```

  - @Entity

    - 테이블과 링크될 클래스임을 나타낸다
    - 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름 매칭
      ex) SalesManager.java -> sales_manager table

  - @Id

    - 해당 테이블의 PK 필드를 나타냄

  - @GeneratedValue

    - PK의 생성 규칙을 나타냄
    - 스프링부트 2.0 에서는 Generation Type.IDENTITY 옵션을 추가해야만 auto increment

  - @Column

    - 굳이 선언하지 않아도, 해당 클래스의 필드는 모드 칼럼이 된다.
    - 사용하는 이유 ~> 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
    - 문자열 = VARCHAR(255)가 기본값, 사이즈 500으로 변경(ex: title) or TEXT 타입으로 변경(ex: content) 하는 경우에 사용.

  - @NoArgsConstructor

    - 기본 생성자 자동 추가 (public Posts() {}와 같은 효과)

  - @Getter

    - 클래스 내 모든 필드의 Getter 메소드를 자동 생성

  - @Builder

    - 해당 클래스의 빌더 패턴 클래스를 생성
    - **생성자 상단에 선언 시, 생성자에 포함된 필드만 빌더에 포함**

  - 어노테이션 순서를 주요 어노테이션을 클래스에 가깝게 두자

    ~> 새로운 언어 전환 시, 필요 없어진 어노테이션을 쉽게 찾아서 지울 수 있다.

  - Posts 클래스는 실제 DB 테이블과 매칭될 클래스 ~> Entity 클래스라고 칭함

  - Entity의 PK는 Long 타입의 Auto_increment를 추천

    - 복합키로 PK를 잡을 경우 난감한 상황 발생
    - FK 맺을 경우, 다른 테이블에서 복합키를 전부 갖고 있는 경우
    - 인덱스에 좋지 않은 영향
    - 유니크 조건 변경 시, PK 전체 수정 요함
    - 따라서, 주민번호, 복합키 등은 유니크 키를 별도로 추가하는 것을 권장

  - <u>**Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다.**</u>

    - 대신, 해당 필드의 값 변경이 필요하면, 명확히 그 목적과 의도를 나타내는 메소드 추가

    ```java
    ///////////// 잘못된 사용 //////////////
    public class Order {
        public void setStauts(boolean status) {
            this.status = status;
        }
    }
    
    public void 주문서비스_취소이벤트() {
        order.setStatus(false);
    }
    ///////////////////////////////////////
    
    ///////////// 올바른 사용 //////////////
    public class Order {
        public void cancleOrder() {
            this.status = false;
        }
    }
    
    public void 주문서비스_취소이벤트() {
        order.cancleOrder();
    }
    ```

    ▲위의 경우와 같이 필요한 경우만 메소드를 명확하게 제시

    - Setter 없이 DB에 삽입하는 방법
      - 생성자를 통해 최종 값을 채운 후, DB에 삽입
      - 값 변경이 필요한 경우, 해당 이벤트에 맞는 public 메소드를 호출하는 것을 전제

  - 생성자 대신 Builder를 이용

    - 어느 필드에 어떤 값을 채워야 할지 명확하게 인지할 수 있음

      ```java
      Example.builder()
      	.a(a)
      	.b(b)
      	.build();
      ```

- domain.posts 패키지 아래, Posts 클래스로 DB 접근을 해줄 PostsRepository 인터페이스 생성

  ```java
  package com.zin0.book.springboot.domain.posts;
  
  import org.springframework.data.jpa.repository.JpaRepository;
  
  public interface PostsRepository extends JpaRepository<Posts, Long> {}
  ```

  ▲위와 같이, JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본 CRUD 메소드 자동 생성

  - SQL 매퍼에서 DAO라고 불리는 DB Layer 접근자
  - <u>**Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다.**</u>

---

<h3>Spring Data JPA 테스트 코드 작성</h3>
- test 디렉토리에 domain.posts 패키지를 생성, PostsRepositoryTest 클래스 생성

  - save 와 findAll 기능 테스트

  ```java
  package com.zin0.book.springboot.domain.post;
  
  import com.zin0.book.springboot.domain.posts.Posts;
  import com.zin0.book.springboot.domain.posts.PostsRepository;
  import org.junit.After;
  import org.junit.Test;
  import org.junit.runner.RunWith;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.test.context.junit4.SpringRunner;
  
  import java.time.LocalDateTime;
  import java.util.List;
  
  import static org.assertj.core.api.Assertions.assertThat;
  
  @RunWith(SpringRunner.class)
  @SpringBootTest
  public class PostsRepositoryTest {
      @Autowired
      PostsRepository postsRepository;
  
      @After
      public void cleanUp() {
          postsRepository.deleteAll();
      }
  
      @Test
      public void 게시글저장_불러오기() {
          //given
          String title = "테스트 게시글";
          String content = "테스트 본문";
  
          postsRepository.save(Posts.builder()
                          .title(title)
                          .content(content)
                          .author("zin0@zin0.com")
                          .build());
  
          //when
          List<Posts> postList = postsRepository.findAll();
  
          //then
          Posts posts = postList.get(0);
          assertThat(posts.getTitle()).isEqualTo(title);
          assertThat(posts.getContent()).isEqualTo(content);
      }
  }
  ```

  - @After
    - **Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정**
    - 보통 배포 전 전체 테스트를 수행할 때, 테스트간 데이터 침범을 막기 위해 사용
    - 여러 테스트가 동시 실행 ~> 테스트용 DB H2에 데이터가 그대로 남아 다음 테스트 실행 시, 실패할 수 있음
  - postsRepository.save
    - 테이블 posts에 insert/update 쿼리를 실행
    - **id 값이 있다면 update가, 없다면 insert 쿼리가 실행**된다.
  - postsRepository.findAll
    - 테이블 posts에 있는 모든 데이터 조회
  - 별다른 설정 없이 @SpringBootTest를 사용할 경우, H2 데이터베이스를 자동으로 실행
    

- 실행된 쿼리는 어떤 형태인지 확인해보기

  - applications.properties 파일에 옵션 추가하기
    - spring.jpa.show_sql = true
  - 위의 옵션을 추가하면, h2의 쿼리 문법이 적용된 것을 볼 수 있음
  - 출력 로그를 h2 쿼리 -> MySQL 쿼리로 변경하기
    - spring.jpa.proterties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect



---



<h3>등록/수정/조회 API 만들기</h3>


- API를 만들기 위해 총 3개의 클래스가 필요
  - Request 데이터를 받을 Dto
  - API 요청을 받을 Controller
  - 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service
- <u>**Service에서는 트랜잭션, 도메인 간 순서 보장의 역할만 할 뿐, 비즈니스 로직을 처리하지 않아야한다. => Domain에서 처리**</u>
- Spring 웹 계층
  - Web Layer
    - 흔히 사용되는 컨트롤러 등의 뷰 템플릿 영역
    - 외부 요청과 응답에 대한 전반적인 영역
  - Service Layer
    - @Service에 사용되는 서비스 영역
    - Controller와 Dao 중간 영역에서 사용된다.
    - @Transactional이 사용되어야 하는 영역
  - Repository Layer
    - DB와 같이 데이터 저장소에 접근하는 영역
    - SQL Mapper의 DAO 영역으로 이해하면 편함
  - Dtos
    - 계층 간 데이터 교환을 위한 객체(DTO : Data Transfer Object)의 영역
  - **Domain Model**
    - 도메인의 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단순화
    - @Entity가 사용된 영역도 도메인 모델
    - 무조건 DB 테이블과 관계가 있어야 하는 것은 아님(값 객체도 이 영역에 해당)
    - ex) 택시 앱 ~> 배차, 탑승, 요금 등 모두 도메인

- 트랜잭션 스크립트

  - 기존에 서비스로 처리하던 방식
  - 문제점
    - 모든 로직이 서비스 클래스 내부에서 처리됨
    - 이에 따라, 서비스 계층이 무의미해지고 객체는 단순히 데이터 덩어리 역할만 하게 됨
  - 도메인 모델의 장점
    - 객체 각자가 본인의 이벤트 처리를 하며, **서비스 메소드는 트랜잭션과 도메인 간의 순서만 보장**
      

- 등록,수정,삭제 기능 ~> web 패키지에 PostsApiController를, web.dto 패키지에 PostSaveRequestDto를, service.posts 패키지에 PostsService를 각각 생성한다.

  <h5>1. 등록</h5>
```java
  //PostsApiController
  package com.zin0.book.springboot.web;
  
  import com.zin0.book.springboot.service.posts.PostsService;
  import com.zin0.book.springboot.web.dto.PostsResponseDto;
  import com.zin0.book.springboot.web.dto.PostsSaveRequestDto;
  import com.zin0.book.springboot.web.dto.PostsUpdateRequestDto;
  import lombok.RequiredArgsConstructor;
  import org.springframework.web.bind.annotation.*;
  
  @RequiredArgsConstructor
  @RestController
  public class PostsApiController {
      private final PostsService postsService;
  
      @PostMapping("/api/v1/posts")
      public Long save(@RequestBody PostsSaveRequestDto requestDto) {
          return postsService.save(requestDto);
      }
  
      @PutMapping("/api/v1/posts/{id}")
      public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
          return postsService.update(id, requestDto);
      }
  
      @GetMapping("/api/v1/posts/{id}")
      public PostsResponseDto findById (@PathVariable Long id) {
          return postsService.findById(id);
      }
  }
  ```
  
```java
  //PostService
  package com.zin0.book.springboot.service.posts;
  
  import com.zin0.book.springboot.domain.posts.Posts;
  import com.zin0.book.springboot.domain.posts.PostsRepository;
  import com.zin0.book.springboot.web.dto.PostsResponseDto;
  import com.zin0.book.springboot.web.dto.PostsSaveRequestDto;
  import com.zin0.book.springboot.web.dto.PostsUpdateRequestDto;
  import lombok.RequiredArgsConstructor;
  import org.springframework.stereotype.Service;
  
  import javax.transaction.Transactional;
  
  @RequiredArgsConstructor
  @Service
  public class PostsService {
      private final PostsRepository postsRepository;
  
      @Transactional
      public Long save(PostsSaveRequestDto requestDto) {
          return postsRepository.save(requestDto.toEntity()).getId();
      }
  }
  ```
  
- @RequiredArgsConstructor
  
  - final이 선언된 모든 필드를 인자 값으로 하는 생성자를 롬복이 대신 생성
    - 기존 Spring 이용자 중에는 Contoller와 service에서 @Autowired를 통해 의존 객체를 자동 주입했는데, 이는 권장하지 않는 방법
    - 생성자를 Bean 객체로 받도록 하면 @Autowired를 대체할 수 있음
    - 롬복이 final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해 준다.
    - 의존성 관계가 변경될 때마다 생성자 코드를 계속 수정해야하는 번거로움이 사라짐
  
  
  
```java
  //PostsSaveRequestDto
  package com.zin0.book.springboot.web.dto;
  
  import com.zin0.book.springboot.domain.posts.Posts;
  import lombok.Builder;
  import lombok.Getter;
  import lombok.NoArgsConstructor;
  
  @Getter
  @NoArgsConstructor
  public class PostsSaveRequestDto {
      private String title;
      private String content;
      private String author;
  
      @Builder
      public PostsSaveRequestDto(String title, String content, String author) {
          this.title = title;
          this.content = content;
          this.author = author;
      }
  
      public Posts toEntity() {
          return Posts.builder()
                  .title(title)
                  .content(content)
                  .author(author)
                  .build();
      }
  }
  ```
  
- Entity와 거의 유사하지만, Dto 클래스를 추가로 생성한 이유
    - Entity 클래스를 Request / Response 클래스로 사용해서는 안된다.
    - Entity 클래스는 DB와 맞닿은 핵심 클래스, Entity를 기준으로 DB 테이블 생성, 스키마 변경, 수많은 서비스 클래스나 비즈니스 로직이 Entity 클래스를 기준으로 동작
    - 반면, 화면 변경은 사소하고 자주 일어나는데, Entity를 사용하면 너무 큰 변경이 된다.
    - **View Layer와 DB Laayer의 역할을 철저하게 분리하는 것이 좋음**
  
- API Test하기 - web 패키지에 PostsApiControllerTest를 생성

  ```java
  package com.zin0.book.springboot.web;
  
  import com.zin0.book.springboot.domain.posts.Posts;
  import com.zin0.book.springboot.domain.posts.PostsRepository;
  import com.zin0.book.springboot.web.dto.PostsSaveRequestDto;
  import com.zin0.book.springboot.web.dto.PostsUpdateRequestDto;
  import org.junit.After;
  import org.junit.Test;
  import org.junit.runner.RunWith;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.boot.test.web.client.TestRestTemplate;
  import org.springframework.boot.web.server.LocalServerPort;
  import org.springframework.http.HttpEntity;
  import org.springframework.http.HttpMethod;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.test.context.junit4.SpringRunner;
  
  import java.util.List;
  
  import static org.assertj.core.api.Assertions.assertThat;
  
  @RunWith(SpringRunner.class)
  @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
  public class PostsApiControllerTest {
      @LocalServerPort
      private int port;
  
      @Autowired
      private TestRestTemplate restTemplate;
  
      @Autowired
      private PostsRepository postsRepository;
  
      @After
      public void tearDown() throws Exception {
          postsRepository.deleteAll();
      }
  
      @Test
      public void Posts_등록된다() throws Exception {
          //given
          String title = "title";
          String content = "content";
          PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                              .title(title)
                                                              .content(content)
                                                              .author("author")
                                                              .build();
  
          String url = "http://localhost:" + port + "/api/v1/posts";
  
          //when
          ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
  
          //then
          assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
          assertThat(responseEntity.getBody()).isGreaterThan(0L);
  
          List<Posts> all = postsRepository.findAll();
          assertThat(all.get(0).getTitle()).isEqualTo(title);
          assertThat(all.get(0).getContent()).isEqualTo(content);
      }
  }
  ```

  - HelloController와 달리 @WebMvcTest를 사용 X
    - @WebMvcTest의 경우 JPA 기능이 작동하지 않기 때문
    - -> Controller와 ControllerAdvice 등 외부 연동과 관련 부분만 활성화
    - 따라서, JPA 기능까지 한번에 테스트할 때는 @SpringBootTest와 TestRestTemplate을 사용
      

  <h5>2.수정/조회</h5>
```java
  //PostsResponseDto
  package com.zin0.book.springboot.web.dto;
  
  import com.zin0.book.springboot.domain.posts.Posts;
  import lombok.Getter;
  
  @Getter
  public class PostsResponseDto {
      private Long id;
      private String title;
      private String content;
      private String author;
  
      public PostsResponseDto(Posts entity) {
          this.id = entity.getId();
          this.title = entity.getTitle();
          this.content = entity.getContent();
          this.author = entity.getAuthor();
      }
  }
  ```
  
- PostsResponseDto는 Entity의 필드 중 일부만 사용 ~> 생성자로 Entity 받아 필드에 넣을 필요 X
  
```java
  //PostUpdateRequestDto
  package com.zin0.book.springboot.web.dto;
  
  import lombok.Builder;
  import lombok.Getter;
  import lombok.NoArgsConstructor;
  
  @Getter // Getter 메소드 생성
  @NoArgsConstructor // 기본 생성자를 자동 생성
  public class PostsUpdateRequestDto {
      private String title;
      private String content;
  
      @Builder
      public PostsUpdateRequestDto(String title, String content) {
          this.title = title;
          this.content = content;
      }
  }
  ```
  
```java
  //Posts에 추가
  package com.zin0.book.springboot.domain.posts;
  
  // import 생략
  
  @Getter
  @NoArgsConstructor
  @Entity
  public class Posts extends BaseTimeEntity {
      ...
      public void update(String title, String content) {
          this.title = title;
          this.content = content;
      }
  }
  ```
  
```java
  //PostService
  package com.zin0.book.springboot.service.posts;
  
  // import 생략
  
  @RequiredArgsConstructor
  @Service
  public class PostsService {
      private final PostsRepository postsRepository;
  	...
          
      @Transactional
      public Long save(PostsSaveRequestDto requestDto) {
          return postsRepository.save(requestDto.toEntity()).getId();
      }
  
      @Transactional
      public Long update(Long id, PostsUpdateRequestDto requestDto) {
          Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));
          posts.update(requestDto.getTitle(), requestDto.getContent());
  
          return id;
      }
  
      public PostsResponseDto findById(Long id) {
          Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));
          return new PostsResponseDto(entity);
      }
  }
  ```
  
- JPA의 영속성 컨텍스트
    - 엔터티를 영구 저장하는 환경(JPA의 핵심 ~> Entity가 영속성 컨텍스트에 포함 or X)
    - JPA 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 DB 데이터를 가져오면, 이 데이터는 영속성 컨텍스트가 유지된 상태
    - 해당 데이터 값을 변경하면, 트랜잭션이 끝나는 시점에서 해당 테이블에 변경분을 반영
    - update 기능에서 DB에 쿼리를 직접 날릴 필요 없이, Entity 객체 값만 변경하면 됨
    - 이를 더티 체킹(Dirty Checking) 이라고 한다.
  
```java
  package com.zin0.book.springboot.web;
  
  //import 생략
  
  @RunWith(SpringRunner.class)
  @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
  public class PostsApiControllerTest {
      ...
  
      @Test
      public void Posts_수정된다() throws Exception {
          //given
          Posts savedPosts = postsRepository.save(Posts.builder()
                  .title("title")
                  .content("content")
                  .author("author")
                  .build());
  
          Long updateId = savedPosts.getId();
          String expectedTitle = "title2";
          String expectedContent = "content2";
  
          PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                  .title(expectedTitle)
                  .content(expectedContent)
                  .build();
  
          String url = "http://localhost:"+ port + "/api/v1/posts/" + updateId;
  
          HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
  
          //when
          ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
  
          //then
          assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
          assertThat(responseEntity.getBody()).isGreaterThan(0L);
  
          List<Posts> all = postsRepository.findAll();
          assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
          assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
      }
  }
  ```
  

  
- 톰캣 실행해서 조회하기

  - H2 데이터베이스는 메모리에서 실행하기 때문에 직접 접근하려면 웹 콘솔을 사용
  - application.properties에 옵션 추가하기
    - spring.h2.console.enable=true
  - http://localhost:8080/h2-console로 들어간다.
  - JDBC URL을 jdbc:h2:mem:testdb로 작성한 후, Connect를 누른다
  - insert 쿼리를 날린 , /api/v1/posts/1을 입력해 API 조회 기능 테스트해보면 JSON 형식으로 데이터를 조회할 수 있음
    

---



<h3>JPA Auditing으로 생성시간/수정시간 자동화하기</h3>
JAP Auditing을 사용하면, 단순 반복되는 코드 없이 시간 자동화를 할 수 있다.

- LocalDate 사용

- Java8 부터는 이전 버전에서 문제가 있던 Date 타입 말고, LocalDate와 LocalDateTime이 나옴.

- domain 패키지에 BaseTimeEntity 클래스를 생성한다.

  ```java
  package com.zin0.book.springboot.domain;
  
  import lombok.Getter;
  import org.springframework.data.annotation.CreatedDate;
  import org.springframework.data.annotation.LastModifiedDate;
  import org.springframework.data.jpa.domain.support.AuditingEntityListener;
  
  import javax.persistence.EntityListeners;
  import javax.persistence.MappedSuperclass;
  import java.time.LocalDateTime;
  
  @Getter
  @MappedSuperclass
  @EntityListeners(AuditingEntityListener.class)
  public class BaseTimeEntity {
  
      @CreatedDate
      private LocalDateTime createdDate;
  
      @LastModifiedDate
      private LocalDateTime modifiedDate;
  }
  ```

  - BaseTimeEntity 클래스는 모든 Entity의 상위 클래스가 되어 Entity들의 생성/수정 시간을 자동으로 관리하는 역할을 한다.
  - @MappedSuperclass
    - JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 칼럼으로 인식하게 함
  - @EntityListeners(AuditingEuntityListener.class)
    - BaseTimeEntity 클래스에 Auditing 기능을 포함시킴
    - Auditing이란, 감사 기능을 뜻한다. 의심가는 DB 작업을 모니터링 하면서 기록 정보를 수집하는 기능
  - 클래스 내부 어노테이션은 직관적으로 이해 가능

- Posts 클래스가 BaseTimeEntity 클래스를 상속받도록 변경

  ```java
  public class Posts extends BaseTimeEntity {...}
  ```

- JPA Auditing 어노테이션들을 모두 활성화할 수 있도록 Application 클래스에 활성화 어노테이션 추가

  ```java
  @EnableJpaAuditing // JPA Auditing 활성화
  ```

- JPA Auditing 테스트 코드 작성

  ```java
  //PostRepositoryTest에 추가
  package com.zin0.book.springboot.domain.post;
  
  // import 생략
  
  @RunWith(SpringRunner.class)
  @SpringBootTest
  public class PostsRepositoryTest {
      ...
  
      @Test
      public void BaseTimeEntity_등록() {
          //given
          LocalDateTime now = LocalDateTime.of(2020,6,28,0,0,0);
          postsRepository.save(Posts.builder()
              .title("title")
              .content("content")
              .author("author")
              .build());
  
          //when
          List<Posts> postsList = postsRepository.findAll();
  
          //then
          Posts posts = postsList.get(0);
  
          System.out.println(">>>>>>>>> createDate="+posts.getCreatedDate()+", modifiedDate ="+posts.getModifiedDate());
          assertThat(posts.getCreatedDate()).isAfter(now);
          assertThat(posts.getModifiedDate()).isAfter(now);
      }
  }
  ```

  - 앞으로 추가될 Entity들은 BaseTimeEntity를 상속받기만 하면, 생성/변경 시간 자동 저장됨



---


이미 내가 알고있는 생각은 아주 먼 옛날 기술이 되어버렸다.

Domain Model을 이해하는데 시간이 오래 걸렸고, 아직도 완벽하게는 이해하지 못했다.

이와 관련해서, 명확하게 머리에 남은 내용은 Service에는 트랜잭션, 도메인 간 순서 보장의 역할만 맡기고 도메인 모델에서 처리를 해야한다는 것 정도..

또한, Setter는 만들지 않고, 필요한 경우에 따라서만 update 메소드를 명확하게 남긴다는 것이 충격이었다. Getter/Setter 메소드를 일일이 작성하다가, Getter 자동생성을 보고 좋다라고 생각했는데 Setter도 비효율적이라 사용하지 않는다니.. 개발하기 더욱 좋아지는 것 같다.

그 밖에는, JPA 자체에 대한 이해나 어노테이션은 계속 공부를 하면서 익혀나가야할 것 같다.