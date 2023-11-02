//package com.sillimfive.mymap.config.jwt;
//
//import com.sillimfive.mymap.domain.users.User;
//import com.sillimfive.mymap.repository.UserRepository;
//import io.jsonwebtoken.Jwts;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.Duration;
//import java.util.Date;
//import java.util.Map;
//
//@SpringBootTest
//public class TokenProviderTest {
//
//
//    @Autowired
//    private TokenProvider tokenProvider;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private JwtProperties jwtProperties;
//
//
//    /**
//     * generatedToken() 검증 테스트
//     */
//    @DisplayName("generatedToken() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
//    @Test
//    void generateToken() {
//        //given
//        User testUser = userRepository.save(
//                User.builder()
//                        .email("user@gmail.com")
//                        .build());
//
//        //when
//        //  generateToken 메서드를 호출하여 토큰 생성
//        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
//
//        //then
//        // jjwt 라이브러리를 사용하여 토큰 복호화 - 토큰 생성시 클레임으로 넣어둔 id 값이 testUser id 값과 동일한지 확인
//        Long userId = Jwts.parser()
//                .setSigningKey(jwtProperties.getSecretKey())
//                .parseClaimsJws(token)
//                .getBody()
//                .get("id", Long.class);
//
//        Assertions.assertThat(userId).isEqualTo(testUser.getId());
//    }
//
//    //validation() 검증 테스트
//    @DisplayName("validToken() : 만료된 토큰인 때에 유효성 검증에 실패한다.")
//    @Test
//    void validToken_invalidToken(){
//        //given
//        //JwtFactory 목업 클래스 로 jwt 토큰 생성
//        // 만료시간 1970년 1월 1일 부터 현재 시간을 밀리초로 치환한 new Date().getTime() 에
//        // 1000을 빼(Duration.ofDays(7).toMillis()) 만료된 토큰으로 생성
//        String token = JwtFactory.builder()
//                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
//                .build()
//                .createToken(jwtProperties);
//
//        //when
//        //유효성 검증
//        boolean result = tokenProvider.validToken(token);
//
//        //then
//        Assertions.assertThat(result).isFalse();
//    }
//
//    @DisplayName("validToken() : 유효한 토큰인 때에 유효성 검증에 성공한다.")
//    @Test
//    void validToken_validToken(){
//        //given
//        //만료값 없으면 기본값으로 생성 - 14일 뒤 만료
//        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);
//
//        //when
//        boolean result = tokenProvider.validToken(token);
//
//        //then
//        Assertions.assertThat(result).isTrue();
//    }
//
//    /**
//     * getAuthentication() 검증 테스트
//     * */
//    @DisplayName("getAuthentication() : 토큰 기반으로 인증 정보를 가져올 수 있다.")
//    @Test
//    void getAuthentication() {
//        //given -
//        String userEmail = "user@email.com";
//        //jjwt 라이브러리 사용하여 토큰 생성 토큰 제목(sub) 만 넣어준다
//        String token = JwtFactory.builder()
//                .subject(userEmail)
//                .build()
//                .createToken(jwtProperties);
//
//        //when
//        // 토큰 을 복호화해 인증 객체를 반환받는다
//        Authentication authentication = tokenProvider.getAuthentication(token);
//
//        //then
//        //인증 객체의 유저 이름을 가져와 subject 값인 user@email.com 과 같은지 비교
//        Assertions.assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
//    }
//
//    /**
//     * getUserId() 검증 테스트
//     * */
//    @DisplayName("getUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
//    @Test
//    void getUserId(){
//        //given
//        Long userId = 1L;
//        //jjwt 라이브러리 사용하여 토큰 생성 클레임 키,값(id) 만 넣어준다
//        String token = JwtFactory.builder()
//                .claims(Map.of("id", userId))
//                .build()
//                .createToken(jwtProperties);
//
//        //when - 토큰 제공자의 getUserId() 메서드를 호출해 유저 ID 반환
//        Long userIdByToken = tokenProvider.getUserId(token);
//
//        // then
//        Assertions.assertThat(userIdByToken).isEqualTo(userId);
//    }
//}
