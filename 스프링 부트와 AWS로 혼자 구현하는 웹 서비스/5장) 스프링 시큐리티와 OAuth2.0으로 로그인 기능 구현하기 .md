- 스프링 시큐리티 
  - <u>**막강한 인증과 인가 기능**</u>을 가진 프레임워크
  - 스프링 기반의 애플리케이션에서는 보안을 위한 표준이라고 보면 됨

<h3>스프링 시큐리티와 스프링 시큐리티 Oauth2 클라이언트</h3>

- 스프링 부트 1.5 vs 2.0
  - 스프링 부트 1.5
    - 더 이상 신규 기능은 추가 지원이 없다. (기존 기능은 유지)
    - url 주소를 모두 명시해야함
  - 스프링 부트 2.0
    - 스프링 부트용 라이브러리(starter) 출시
    - 신규 라이브러리의 경우 확장 포인트를 고려해서 설계된 상태
    - 1.5버전에서 직접 입력하던 값들은 enum으로 대체되고, client 인증 정보만 입력하면 된다.
    - CommonOAuth2Provide라는 **enum**이 새롭게 추가, 여기서 구글, 깃허브, 페이스북, 옥타 등의 기본 설정값 제공 (네이버나 카카오는 직접 해야함)



---



<h3>구글 서비스 등록</h3>

- 구글 클라우드 플랫폼에서 프로젝트 생성하기

  - [구글 클라우드 플랫폼](https://console.cloud.google.com)으로 이동
  - 새 프로젝트 생성
  - 프로젝트 이름에는 구글 로그인시 이용자에게 보일 이름을 설정하면 된다.
    - 모바일 게임 구글 로그인에 피파온라인 같이 게임 이름이 뜨는 것을 정한다고 생각
    - API 및 서비스 카테고리 -> 사용자 인증 정보 만들기
    - OAuth 클라이언트 ID 선택 -> 동의 화면 구성
    - OAuth 동의 화면에 애플리케이션 이름(위와 동일), API 범위 설정
    - OAuth 클라이언트 ID 만들기 -> 웹 애플리케이션 선택
    - 승인된 리디렉션 URI에 주소 남기기 -> 생성
  - 승인된 리디렉션 URI
    - 서비스에서 파라미터로 인증 정보를 주었을 때, 인증이 성공하면 구글에서 리다이렉트할 URL
    - 스프링 부트 2 버전의 시큐리티에서는 기본적으로 **{도메인}/login/oauth2/code/{소셜서비스코드}**로 리다이렉트 URL을 지원

- 프로젝트에 적용하기

  - application-oauth 등록

    - src/main/resources 디렉토리에 application-oauth.properties 파일 생성

      ```properties
      spring.security.oauth2.client.registration.google.client-id=구글클라이언트ID
      spring.security.oauth2.client.registration.google.client-secret=구글클라이언트시크릿
      spring.security.oauth2.client.registration.google.scope=profile,email
      ```

    - scope=profile,email

      - 기본 값이 openid,profile,email이다.
      - 강제로 profile,email을 등록한 이유 => openid라는 scope가 있으면 Open Id Provide로 인식하기 때문
      - **이렇게 되면, OpenId Provider인 서비스(위에서 언급)과 그렇지 않은 서비스(구글, 카카오 등)로 나눠서 각각 OAuth2Service를 생성해야함**

  - .gitignore에 application-oauth.properties 추가하기

    - 구글 로그인을 위한 클라이언트 ID와 보안 비밀은 중요한 사안.
    - 외부에 노출을 방지하기 위해서 github에 올라가는거 방지하기



---



<h3>구글 로그인 연동하기</h3>

- domain 패키지에 user 패키지 생성 -> User 클래스 생성하기

  - User 클래스는 사용자 정보를 담당할 도메인.

  ```java
  package com.zin0.book.springboot.domain.user;
  
  import com.zin0.book.springboot.domain.BaseTimeEntity;
  import lombok.Builder;
  import lombok.Getter;
  import lombok.NoArgsConstructor;
  
  import javax.persistence.*;
  
  @Getter
  @NoArgsConstructor
  @Entity
  public class User extends BaseTimeEntity {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
  
      @Column(nullable = false)
      private String name;
  
      @Column(nullable = false)
      private String email;
  
      @Column
      private String picture;
  
      @Enumerated(EnumType.STRING)
      @Column(nullable = false)
      private Role role;
  
      @Builder
      public User(String name, String email, String picture, Role role) {
          this.name = name;
          this.email = email;
          this.picture = picture;
          this.role = role;
      }
  
      public User update(String name, String picture) {
          this.name =name;
          this.picture = picture;
  
          return this;
      }
  
      public String getRoleKey() {
          return this.role.getKey();
      }
  }
  ```

  - @Enumerated(EnumType.STRING)
    - JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정
    - 기본적으로 int로 숫자가 저장
    - 숫자 저장 -> DB 확인할 때, 그 값이 무슨 의미인지 알기 힘들다.
    - 그래서 문자열(EnumType.STRING)로 저장될 수 있게 선언
      

- 사용자의 권한을 관리할 Enum 클래스 Role 생성

  ```java
  package com.zin0.book.springboot.domain.user;
  
  import lombok.Getter;
  import lombok.RequiredArgsConstructor;
  
  @Getter
  @RequiredArgsConstructor
  public enum Role {
      GUEST("ROLE_GUEST", "손님"),
      USER("ROLE_USER", "일반 사용자");
  
      private final String key;
      private final String title;
  }
  ```

  - 스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야만 한다.
  - 그래서 코드별 키 값 ~> ROLE_GUEST, ROLE_USER 등으로 지정
    

- User의 CRUD를 책임질 UserRepository 생성

  ```java
  package com.zin0.book.springboot.domain.user;
  
  import org.springframework.data.jpa.repository.JpaRepository;
  
  import java.util.Optional;
  
  public interface UserRepository extends JpaRepository<User,Long> {
      Optional<User> findByEmail(String email);
  }
  ```

  - findByEmail
    - 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입인지 판단하기 위한 메소드
      

- 스프링 시큐리티 설정

  - build.gradle에 스프링 시키류티 관련 의존성 추가하기
    - compile('org.springframework.boot:spring-boot-starter-oauth2-client')
  - spring-boot-starter-oauth2-client
    - **소셜 로그인 등 클라이언트 입장에서 소셜 기능 구현 시 필요한 의존성**
    - spring-boot-starter-oauth2-client와 spring-boot-starter-oauth2-jose를 기본으로 관리해줌
      

- config.auth 패키지 생성

  - 시큐리티 관련 클래스를 담을 패키지
    

- config.auth 패키지에 SecurityConfig 클래스 생성

  ```java
  package com.zin0.book.springboot.config.auth;
  
  import com.zin0.book.springboot.domain.user.Role;
  import lombok.RequiredArgsConstructor;
  import org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
  import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
  
  @RequiredArgsConstructor
  @EnableWebSecurity
  public class SecurityConfig extends WebSecurityConfigurerAdapter {
      private final CustomOAuth2UserService customOAuth2UserService;
  
      @Override
      protected void configure(HttpSecurity http) throws Exception {
          http.csrf().disable().headers().frameOptions().disable()
                  .and()
                      .authorizeRequests()
                      .antMatchers("/", "/css/**", "/images/**", "/js/**","/h2-console/**").permitAll()
                      .antMatchers("/api/v    1/**").hasRole(Role.USER.name())
                      .anyRequest().authenticated()
                  .and()
                      .logout()
                          .logoutSuccessUrl("/")
                  .and()
                      .oauth2Login()
                          .userInfoEndpoint()
                              .userService(customOAuth2UserService);
      }
  }
  ```

  - @EnableWebSecurity
    - Spring Security 설정을 활성화시켜준다.
  - csrf().disable().headers().frameOptions()
    - h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
  - authorizeRequests
    - URL별 권한 관리를 설정하는 옵션의 시작점
    - authorizeRequests가 선언돼야 antMatchers 옵션을 사용 가능
  - antMatchers
    - 권한 관리 대상을 지정하는 옵션
    - URL, HTTP 메소드별로 관리 가능
    - "/" 등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 줌
    - "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 설정
  - anyRequest
    - 설정된 값들 이외 나머지 URL
    - authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용(로그인 된 유저들)
  - oauth2Login
    - OAuth 2 로그인 기능에 대한 여러 설정의 진입점
  - userInfoEndpoint
    - OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
  - userService
    - 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음.
      

- CustomOAuth2UserService 클래스 생성

  ```java
  package com.zin0.book.springboot.config.auth;
  
  import com.zin0.book.springboot.config.auth.dto.OAuthAttributes;
  import com.zin0.book.springboot.config.auth.dto.SessionUser;
  import com.zin0.book.springboot.domain.user.User;
  import com.zin0.book.springboot.domain.user.UserRepository;
  import lombok.RequiredArgsConstructor;
  import org.springframework.security.core.authority.SimpleGrantedAuthority;
  import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
  import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
  import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
  import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
  import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
  import org.springframework.security.oauth2.core.user.OAuth2User;
  import org.springframework.stereotype.Service;
  
  import javax.servlet.http.HttpSession;
  import java.util.Collections;
  
  @RequiredArgsConstructor
  @Service
  public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
      private final UserRepository userRepository;
      private final HttpSession httpSession;
  
      @Override
      public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
          OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
          OAuth2User oAuth2User = delegate.loadUser(userRequest);
  
          String registrationId = userRequest.getClientRegistration().getRegistrationId();
          String userNameAttributeName = 	                            userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
          
          OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
          User user = saveOrUpdate(attributes);
  
          httpSession.setAttribute("user", new SessionUser(user));
  
          return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                  attributes.getAttributes(),
                  attributes.getNameAttributeKey());
      }
  
      private User saveOrUpdate(OAuthAttributes attributes) {
          User user = userRepository.findByEmail(attributes.getEmail())
                  .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                  .orElse(attributes.toEntity());
  
          return userRepository.save(user);
      }
  }
  ```

  - registrationId
    - 현재 로그인 진행 중인 서비스를 구분하는 코드
    - 네이버, 구글 등 구분하기 위함
  - userNameAttributeName
    - OAuth2 로그인 진행 시 키가 되는 필드값 (Primary Key와 같은 의미)
    - 구글 -> "sub" 기본 코드 제공, 네이버 카카오는 지원 X
    - 네이버 로그인과 구글 로그인을 동시 지원할 때 쓰임
  - OAuthAttributes
    - OAuth2UserService를 통해 가져온 OAuthUser의 attribute를 담을 클래스
    - 소셜 로그인에서 이 클래스 사용
  - SessionUser
    - 세션에 사용자 정보를 저장하기 위한 Dto 클래스
    - User 클래스를 쓰지 않고 새로 만들어 쓰는 이유
      - User 클래스를 사용하는 경우 User 클래스에 직렬화를 구현하지 않았다는 에러가 발생
      - User 클래스는 엔티티기 때문에, 언제 다른 엔티티와 관계가 형성될지 모른다.
      - 만약, **User 클래스에 직렬화를 설정한다면**, 자식 엔티티도 직렬화 대상이 되어 **성능 이슈와 부수효과**가 발생할 확률이 높음
      - 그래서 **직렬화 기능을 가진 세션 Dto를 추가**해서 운영 및 유지보수에 도움
        

- config.auth.dto 패키지 생성 -> OAuthAttributes 클래스 생성

  ```java
  package com.zin0.book.springboot.config.auth.dto;
  
  import com.zin0.book.springboot.domain.user.Role;
  import com.zin0.book.springboot.domain.user.User;
  import lombok.Builder;
  import lombok.Getter;
  
  import java.util.Map;
  
  @Getter
  public class OAuthAttributes {
      private Map<String, Object> attributes;
      private String nameAttributeKey;
      private String name;
      private String email;
      private String picture;
  
      @Builder
      public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
          this.attributes = attributes;
          this.nameAttributeKey = nameAttributeKey;
          this.name = name;
          this.email = email;
          this.picture = picture;
      }
  
      public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
          if("naver".equals(registrationId)) {
              return ofNaver("id", attributes);
          }
          return ofGoogle(userNameAttributeName, attributes);
      }
  
      private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
          return OAuthAttributes.builder()
                  .name((String)attributes.get("name"))
                  .email((String)attributes.get("email"))
                  .picture((String)attributes.get("picture"))
                  .attributes(attributes)
                  .nameAttributeKey(userNameAttributeName)
                  .build();
      }
  
      private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
          Map<String, Object> response = (Map<String, Object>)attributes.get("response");
          return OAuthAttributes.builder()
                  .name((String)response.get("name"))
                  .email((String)response.get("email"))
                  .picture((String)response.get("profile_image"))
                  .attributes(response)
                  .nameAttributeKey(userNameAttributeName)
                  .build();
      }
  
      public User toEntity() {
          return User.builder()
                  .name(name)
                  .email(email)
                  .picture(picture)
                  .role(Role.GUEST)
                  .build();
      }
  }
  ```

  - of()
    - OAuthUser에서 반환하는 사용자 정보는 Map이기 때문에, 값 하나하나를 변환해야만 한다.
  - toEntity()
    - User 엔티티를 생성
    - OAuthAttributes에서 엔티티를 생성하는 시점 -> 처음 가입
    - 가입할 때의 기본 권한을 GUEST로 주기 위해서 role 빌더값에 Role.GUEST 사용
      

- config.auth.dto 패키지에 SessionUser 클래스 추가

  ```java
  package com.zin0.book.springboot.config.auth.dto;
  
  import com.zin0.book.springboot.domain.user.User;
  import lombok.Getter;
  
  import java.io.Serializable;
  
  @Getter
  public class SessionUser implements Serializable { // Serializable 직렬화
      private String name;
      private String email;
      private String picture;
  
      public SessionUser(User user) {
          this.name = user.getName();
          this.email = user.getEmail();
          this.picture = user.getPicture();
      }
  }
  ```

  - SessionUser에는 인증된 사용자 정보만 필요, 따라서 name,email,picture만 저장 (이외의 정보는 필요 X)
    

- index.mustache에 로그인 버튼 추가

  ```
  ...
      <h1>스프링 부트로 시작하는 웹 서비스</h1>
      <div class = "col-md-12">
          <!-- 로그인 기능 영역 -->
          <div class = "row">
              <div class = "col-md-6">
                  <a href = "/posts/save" role ="button" class = "btn btn-primary">글 등록</a>
                  {{#userName}}
                      Logged in as : <span id = "user">{{username}}</span>
                      <a href="/logout" class = "btn btn-info active" role ="button">Logout</a>
                  {{/userName}}
                  {{^userName}}
                      <a href="/oauth2/authorization/google" class = "btn btn-success active" role = "button">Google Login</a>
                  {{/userName}}
              </div>
          </div>
  
          <!-- 목록 출력 Area-->
  ...
  ```

  - {{#userName}}
    - 머스테치는 if문 제공 X
    - true / false만 판단한다.
    - 그래서 항상 최종값을 넘겨줘야한다.
    - userName이 있다면 userName을 노출
  - a href="/logout"
    - 스프링 시큐리티에서 기본적으로 제공하는 로그아웃 URL
    - 로그아웃 컨트롤러를 따로 만들 필요 없음
    - SecurityConfig 클래스에서 URL을 변경할 수는 있음
  - {{^userName}}
    - ^ - 해당 값이 존재하지 않는 경우
    - userName이 없는 경우 ~> 로그인 버튼 노출
  - a href="/oauth2/authorization/google"
    - 스프링 시큐리티에서 기본적으로 제공하는 로그인 URL
    - 컨트롤러 따로 만들 필요 X
      

- IndexController에서 userName을 model에 저장하는 코드 추가하기

  ```java
  // import ...
  
  @RequiredArgsConstructor
  @Controller
  public class IndexController {
      private final PostsService postsService;
      private final HttpSession httpSession;
  
      @GetMapping("/")
      public String index(Model model) {
          model.addAttribute("posts", postsService.findAllDesc());
          SessionUser user = (SessionUser)httpSession.getAttribute("user");
          if(user != null) {
              model.addAttribute("userName", user.getName());
          }
          return "index";
      }
  }
  ```

  - (SessionUser)httpSession.getAttribute("user")
    - CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장
    - 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있음
  - if(user != null)
    - 세션에 저장된 값이 있을 때만 model에 userName으로 등록
    - 세션에 저장된 값 X -> model 값 X -> 로그인 버튼 노출



---



<h3>어노테이션 기반으로 개선하기</h3>

- 세션 값을 가져오는 부분이 반복 -> 메소드 인자로 세션 값을 바로 받도록 설정

- config.auth에 @LoginUser 어노테이션 생성

  ```java
  package com.zin0.book.springboot.config.auth;
  
  import java.lang.annotation.ElementType;
  import java.lang.annotation.Retention;
  import java.lang.annotation.RetentionPolicy;
  import java.lang.annotation.Target;
  
  @Target(ElementType.PARAMETER) 
  @Retention(RetentionPolicy.RUNTIME) // 컴파일 이후에도 JVM에 의해서 참조가 가능
  //@Retention(RetentionPolicy.CLASS) // 컴파일러가 클래스를 참조할 때 까지 유효
  //@Retention(RetentionPolicy.SOURCE) // 컴파일 이후 어노테이션 정보 사라짐
  public @interface LoginUser {
  }
  ```

  - @Target(ElementType.PARAMETER) 
    - 이 어노테이션이 생성될 수 있는 위치를 지정
    - 이에 따라 메소드의 파라미터로 선언된 객체에서만 사용 가능
  - @Retention(RetentionPolicy.RUNTIME)
    - 컴파일 이후에도 JVM에 의해서 참조가 가능
    - @Retention(RetentionPolicy.CLASS) -> 컴파일러가 클래스를 참조할 때 까지 유효
    - @Retention(RetentionPolicy.SOURCE) -> 컴파일 이후 어노테이션 정보 사라짐
  - @interface
    - 어노테이션 클래스로 지정
      

- LoginUserArgumentResolver 생성 
  -> HandlerMethodArgumentResolver 인터페이스를 구현한 클래스 
  -> HandlerMethodArgumentResolver의 구현체가 지정한 값으로 해당 메소드의 파리미터로 바로 넘길 수 있음

  ```java
  package com.zin0.book.springboot.config.auth;
  
  import com.zin0.book.springboot.config.auth.dto.SessionUser;
  import lombok.RequiredArgsConstructor;
  import org.springframework.core.MethodParameter;
  import org.springframework.stereotype.Component;
  import org.springframework.web.bind.support.WebDataBinderFactory;
  import org.springframework.web.context.request.NativeWebRequest;
  import org.springframework.web.method.support.HandlerMethodArgumentResolver;
  import org.springframework.web.method.support.ModelAndViewContainer;
  
  import javax.servlet.http.HttpSession;
  
  @RequiredArgsConstructor
  @Component
  public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
      private final HttpSession httpSession;
  ;
      @Override
      public boolean supportsParameter(MethodParameter parameter) {
          boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
          boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
  
          return isLoginUserAnnotation && isUserClass;
      }
  
      @Override
      public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory) throws Exception {
          return httpSession.getAttribute("user");
      }
  }
  ```

  - supportsParameter()
    - 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
    - 여기는 @LoginUser 어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true 반환
  - resolveArgument()
    - 파라미터에 전달할 객체 생성
    - 여기서는 세션에서 객체를 가져온다.



- config 패키지에 WebConfig 생성
  -> 스프링에서 LoginUserArgumentResolver가 스프링에서 인식될 수 있도록 WebMvcConfigurer에 추가하는 역할

  ```java
  package com.zin0.book.springboot.config;
  
  import com.zin0.book.springboot.config.auth.LoginUserArgumentResolver;
  import lombok.RequiredArgsConstructor;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.method.support.HandlerMethodArgumentResolver;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
  
  import java.util.List;
  
  @RequiredArgsConstructor
  @Configuration
  public class WebConfig implements WebMvcConfigurer {
      private final LoginUserArgumentResolver loginUserArgumentResolver;
  
      @Override
      public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
          argumentResolvers.add(loginUserArgumentResolver);
      }
  }
  ```

  - **HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentResolvers()를 통해 추가해야한다.**

- IndexController의 코드에서 반복되는 부분 @LoginUser로 개선

  ```java
  // import ...
  @RequiredArgsConstructor
  @Controller
  public class IndexController {
      private final PostsService postsService;
  
      @GetMapping("/")
      public String index(Model model, @LoginUser SessionUser user) {
          model.addAttribute("posts", postsService.findAllDesc());
          if(user != null) {
              model.addAttribute("userName", user.getName());
          }
          return "index";
      }
  }
  ```

  - @LoginUser SessionUser user
    - 기존에 (User)httpSession.getAttribute("uesr")로 가져오던 세션 정보 값이 개선
    - 어떤 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있음



---



<h3>세션 저장소로 데이터베이스 사용하기</h3>



- 세션이 내장 톰캣의 메모리에 저장되기 때문에, 애플리케이션 재실행 시 로그인이 풀림
- 세션 저장소 3가지 방법
  - 톰캣 세션을 사용
    - default 방식
    - 이 경우 톰캣(WAS)에 세션이 저장되기 때문에 **2대 이상의 WAS가 구동되는 환경에서는 톰캣들 간의 세션 공유를 위한 추가 설정이 필요**
  - MySQL과 같은 데이터베이스를 세션 저장소로 사용
    - 여러 WAS간의 공용 세션을 사용할 수 있는 가장 쉬운 방법
    - 많은 설정이 필요 X, But, 로그인 요청마다 DB IO가 발생하여 성능상 이슈가 발생
    - 로그인 요청이 많이 없는 백오피스, 사내 시스템 용도에서 사용
    - 이번 프로젝트에서는 이 방식을 사용
  - Redis, Memcached와 같은 메모리 DB를 세션 저장소로 사용
    - EC2 서비스에서 가장 많이 사용
    - 실제 서비스 사용은 Embedded Redis와 같은 방식이 아닌 외부 메모리 서버가 필요
- spring-session-jdbc 등록
  - build.gradle에 의존성 등록하기
  - compile('org.springframework.session:spring-session-jdbc')
  - JPA로 인해 세션 테이블이 자동 생성.



---



<h3>네이버 로그인</h3>



- 네이버 API 등록
  - [네이버 오픈 API](https://developers.naver.com/apps/#/register?api=nvlogin)로 이동
  - 애플리케이션 이름(표시될 이름) 지정
  - 사용 API에 원하는 권한 추가
    - 회원이름, 이메일, 프로필 사진은 필수
  - 로그인 Open Api 환경 -> PC 웹 -> 서비스 URL과 Callback URL 작성하기
    - Callback은 domain/login/oauth2/code/naver로 등록



- application-oauth.properties에 키 값 등록하기

  -> naver에서는 스프링 시큐리티를 공식 지원하지 않기 때문에, 직접 값 입력

  ```properties
  # registration
  spring.security.oauth2.client.registration.naver.client-id=네이버클라이언트ID
  spring.security.oauth2.client.registration.naver.client-secret=네이버클라이언트시크릿
  spring.security.oauth2.client.registration.naver.redirect-uri={baseUrl}/{action}/oauth2/code/{registrationId}
  spring.security.oauth2.client.registration.naver.authorization-grant-type=authorization_code
  spring.security.oauth2.client.registration.naver.scope=name,email,profile_image
  spring.security.oauth2.client.registration.naver.client-name=Naver
  
  # provider
  spring.security.oauth2.client.provider.naver.authorization-uri=https://nid.naver.com/oauth2.0/authorize
  spring.security.oauth2.client.provider.naver.token-uri=https://nid.naver.com/oauth2.0/token
  spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me
  spring.security.oauth2.client.provider.naver.user-name-attribute=response
  ```

  - user_name_attribute=response
    - 기준이 되는 user_name의 이름을 네이버에서는 response로 해야한다.
    - 네이버 회원 조회 시 반환되는 JSON의 최상위 필드가 resultCode, message, response
    - **스프링 시큐리티에서는 하위 필드 명시 불가능**, 따라서 user_name의 상위 필드인 response를 입력

- 스프링 시큐리티 설정 등록

  - OAuthAttributes에 네이버 판단 코드 추가

    ```java
    //import ...
    @Getter
    public class OAuthAttributes {
        // ...
        
        public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
            if("naver".equals(registrationId)) {
                return ofNaver("id", attributes);
            }
            return ofGoogle(userNameAttributeName, attributes);
        }
    
        private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
            // ...
        }
    
        private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
            Map<String, Object> response = (Map<String, Object>)attributes.get("response");
            return OAuthAttributes.builder()
                    .name((String)response.get("name"))
                    .email((String)response.get("email"))
                    .picture((String)response.get("profile_image"))
                    .attributes(response)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }
       // ...
    }
    ```

  - index.mustache에 네이버 로그인 버튼 추가

    ```
    ...
    {{^userName}}
    	<a href="/oauth2/authorization/google" class = "btn btn-success active" role = "button">Google Login</a>
    	<a href="/oauth2/authorization/naver" class="btn btn-secondary active" role="button">Naver Login</a>
    {{/userName}}
    ...
    ```

    - /oauth2/authorization/naver
      - 네이버 로그인 URL은 application-oauth.properties에 등록한 redirect-uri 값에 맞춰 자동으로 등록된다
      - /oauth2/authorization/ 까지는 고정, 마지막 Path만 각 소셜 로그인 필드를 사용



---



<h3>기존 테스트에 시큐리티 적용하기</h3>

 

- 기존에는 바로 API를 호출할 수 있어서 Test 코드도 API를 호출하도록 했었다.
- 시큐리티 옵션이 활성화되면 인증된 사용자만 API 호출 가능
- 따라서 인증한 사용가 호출한 것 처럼 변경해야함



- 문제 1 : CustomOAuth2UserService을 찾을 수 없음
  -> hello가_리턴된다 메소드 에러

  - CustomeOAuth2UserService를 생성하는데 필요한 소셜 로그인 관련 설정값들이 없어서 발생

  - src/main 환경과 src/test환경의 차이 때문에 발생

  - test에 application.properties가 없으면 main의 설정을 그대로 가져오는데, application-oauth.properties는 test에 파일이 없다고 가져오는 파일이 아님

  - **test/resources에 application.properties 생성**

    ```properties
    spring.jpa.show_sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
    spring.h2.console.enabled=true
    spring.session.store-type=jdbc
    
    # Test OAuth
    
    spring.security.oauth2.client.registration.google.client-id=test
    spring.security.oauth2.client.registration.google.client-secret=test
    spring.security.oauth2.client.registration.google.scope=profile,email
    ```

    -> 롬복기능테스트, 메인페이지로딩, 게시글저장불러오기,BaseTime 테스트 통과



- 문제 2 : 302 Status Code

  -> Posts_등록된다 에러

  - 스프링 시큐리티 설정 때문에 인증되지 않은 사용자의 요청은 이동시키기 때문에 302

  - 임의로 인증된 사용자 추가하기

    - build.gradle에 spring-security-test 추가
    - **testCompile("org.springframework.secuirty:spring-security-test")**

  - **PostsApiControllerTest의 메소드에 임의 사용자 인증 추가하기**

    ```java
    // import ...
    public class PostsApiControllerTest {
        // ...
        @Test
        @WithMockUser(roles="USER")
        public void Posts_등록된다() throws Exception {
            // ...
        }
    
        @Test
        @WithMockUser(roles="USER")
        public void Posts_수정된다() throws Exception {
            // ...
        }
    }
    ```

    - @WithMockUser(roles="USER")
      - 인증된 모의 사용자를 만들어서 사용한다
      - roles에 권한을 추가할 수 있음

  - @SpringBootTest에서 MockMvc 사용하기

    - **@WithMockUser가 MockMvc에서만 작동하기 때문에, @SpringBootTest에서 사용해야함**

    ```java
    package com.zin0.book.springboot.web;
    
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.zin0.book.springboot.domain.posts.Posts;
    import com.zin0.book.springboot.domain.posts.PostsRepository;
    import com.zin0.book.springboot.web.dto.PostsSaveRequestDto;
    import com.zin0.book.springboot.web.dto.PostsUpdateRequestDto;
    import org.junit.After;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.web.client.TestRestTemplate;
    import org.springframework.boot.web.server.LocalServerPort;
    import org.springframework.http.*;
    import org.springframework.security.test.context.support.WithMockUser;
    import org.springframework.test.context.junit4.SpringRunner;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.setup.MockMvcBuilders;
    import org.springframework.web.context.WebApplicationContext;
    
    import java.util.List;
    
    import static org.assertj.core.api.Assertions.assertThat;
    import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
    
    @RunWith(SpringRunner.class)
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class PostsApiControllerTest {
        // ...
        @Autowired
        private WebApplicationContext context;
    
        private MockMvc mvc;
    
        @Before
        public void setup() {
            mvc = MockMvcBuilders.webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
        }
    
        @Test
        @WithMockUser(roles="USER")
        public void Posts_등록된다() throws Exception {
            //given ...
            //when
            mvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new ObjectMapper().writeValueAsString(requestDto)))
                    .andExpect(status().isOk());
    
            //then
            List<Posts> all = postsRepository.findAll();
            assertThat(all.get(0).getTitle()).isEqualTo(title);
            assertThat(all.get(0).getContent()).isEqualTo(content);
        }
    
        @Test
        @WithMockUser(roles="USER")
        public void Posts_수정된다() throws Exception {
            //given ...
            //when
            mvc.perform(put(url)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new ObjectMapper().writeValueAsString(requestDto)))
                    .andExpect(status().isOk());
    
            //then
            List<Posts> all = postsRepository.findAll();
            assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
            assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
        }
    }
    ```

    - @Before
      - 매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성
    - mvc.perform
      - 생성된 MockMvc를 통해 API를 테스트
      - 본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환

    -> Posts 등록 수정 해결


- 문제 3 : @WebMvcTest에서 CustomOAuth2UserService을 찾을 수 없음
  -> hello가 리턴된다 에러

  - HelloControllerTest는 @WebMvcTest를 사용한다.

  - 1번을 통해 스프링 시큐리티 설정은 잘 작동했지만, **@WebMvcTest CustomOAuth2UserService를 스캔하지 않기 때문에 발생**

  - @WebMvcTest는 WebSecurityConfigurerAdapter, WebMvcConfigurer를 비롯한 @ControllerAdvice, @Controller를 읽는다. 즉, **@Repository, @Service, @Component는 스캔 대상이 아님**

  - **SecurityConfig는 읽었지만, 생성하는데 필요한 CustomOAuth2UserService는 읽을 수가 없어서 에러 발생한 것.**

  - 스캔 대상에서 SecurityConfig를 제거하기

    ```java
    // import ...
    
    @RunWith(SpringRunner.class)
    @WebMvcTest(controllers = HelloController.class, excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = SecurityConfig.class)
            }
    )
    public class HelloControllerTest {
        @Autowired
        private MockMvc mvc;
    
        @WithMockUser("USER")
        @Test
        public void hello가_리턴된다() throws Exception {
           // ...
        }
    
        @WithMockUser("USER")
        @Test
        public void HelloDto가_리턴된다() throws Exception {
            // ...
        }
    }
    ```

    - 위의 설정을 하고나면, java.lang.IllegalArgumentException: At least one JPA metamodel must be present! 에러가 뜬다.

    - @EnableJpaAuditing으로 인해 발생하는 에러

    - **@EnableJpaAuditing를 사용하기 위해서는 최소 하나의 @Entity 클래스가 필요 -> @WebMvcTest보니까 없다.**

    - **@EnableJpaAuditing가 @SpringBootApplication과 함께있어서 @WebMvcTest에서도 스캔하게 되어있다.**

    - @EnableJpaAuditing와 @SpringBootApplication 분리하기 
      -> Application.java 에서 @EnableJpaAuditing 제거하기

      ```java
      package com.zin0.book.springboot;
      
      import org.springframework.boot.SpringApplication;
      import org.springframework.boot.autoconfigure.SpringBootApplication;
      
      @SpringBootApplication
      public class Application {
          public static void main(String[] args) {
              SpringApplication.run(Application.class, args);
          }
      }
      ```

    - config 패키지에 JpaConfig를 생성하여 @EnableJpaAuditing 추가하기

      ```java
      package com.zin0.book.springboot.config;
      
      import org.springframework.context.annotation.Configuration;
      import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
      
      @Configuration
      @EnableJpaAuditing // JPA Auditing 활성화
      public class JpaConfig { }
      ```