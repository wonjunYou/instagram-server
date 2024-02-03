//package numble.instagram.common.jwt;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import lombok.Getter;
//import lombok.Value;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//@Slf4j
//@Component
//public class JwtTokenProvider {
//
//    public static final String BEARER_TYPE = "Bearer";
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    public static final String REFRESH_TOKEN_HEADER = "Refresh";
//    public static final String BEARER_PREFIX = "Bearer ";
//
//    @Getter
//    @Value("${jwt.secret-key}")
//    private String secretKey;
//
//    @Getter
//    @Value("${jwt.access-token-expiration-millis}")
//    private long accessTokenExpirationMillis;
//
//    @Getter
//    @Value("${jwt.refresh-token-expiration-millis}")
//    private long refreshTokenExpirationMillis;
//
//    private Key key;
//
//    @PostConstruct
//    public void init() {
//        String base64EncodedSecretKey = encodeBase64SecretKey(this.secretKey);
//        this.key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
//    }
//
//    public String encodeBase64SecretKey(String secretKey) {
//        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
//
//    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public TokenDto generateTokenDto(CustomUserDetails customUserDetails) {
//        Date accessTokenExpiresIn = getTokenExpiration(accessTokenExpirationMillis);
//        Date refreshTokenExpiresIn = getTokenExpiration(refreshTokenExpirationMillis);
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", customUserDetails.getRole());
//
//        String accessToken = Jwts.builder()
//            .setClaims(claims)
//            .setSubject(customUserDetails.getEmail())
//            .setExpiration(accessTokenExpiresIn)
//            .setIssuedAt(Calendar.getInstance().getTime())
//            .signWith(key, SignatureAlgorithm.HS256)
//            .compact();
//
//        String refreshToken = Jwts.builder()
//            .setClaims(claims)
//            .setSubject(customUserDetails.getEmail())
//            .setExpiration(accessTokenExpiresIn)
//            .setIssuedAt(Calendar.getInstance().getTime())
//            .signWith(key, SignatureAlgorithm.HS256)
//            .compact();
//
//        return TokenDto.builder()
//            .grantType(BEARER_TYPE)
//            .authorizationType(AUTHORIZATION_HEADER)
//            .accessToken(accessToken)
//            .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
//            .refreshToken(refreshToken)
//            .build();
//    }
//
//    public Authentication getAuthentication(String accessToken) {
//        Claims claims = parseClaims(accessToken);
//
//        if (claims.get("role") == null) {
//            throw new Exception("액세스 토큰이 존재하지 않습니다.");
//        }
//
//        String authority = claims.get("role").toString();
//
//        CustomUserDetails.of(claims.getSubject(), authority);
//
//        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//    }
//
//    public boolean validateToken(String token, HttpServletResponse response) {
//        try {
//            parseClaims(token);
//        } catch (MalformedJwtException e) {
//            log.info("Invalid JWT token");
//            log.trace("Invalid JWT token trace = {}", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT token");
//            log.trace("Expired JWT token trace = {}", e);
//            Responder.sendErrorResponse(response, ExceptionCode.TOKEN_EXPIRED);
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT token");
//            log.trace("Unsupported JWT token trace = {}", e);
//            Responder.sendErrorResponse(response, ExceptionCode.TOKEN_UNSUPPORTED);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty.");
//            log.trace("JWT claims string is empty trace = {}", e);
//            Responder.sendErrorResponse(response, ExceptionCode.TOKEN_ILLEGAL_ARGUMENT);
//        }
//        return true;
//    }
//
//    private Date getTokenExpiration(long expirationMillisecond) {
//        Date date = new Date();
//
//        return new Date(date.getTime() + expirationMillisecond);
//    }
//
//    // Token 복호화 및 예외 발생(토큰 만료, 시그니처 오류)시 Claims 객체가 안만들어짐.
//    public Claims parseClaims(String token) {
//        return Jwts.parserBuilder()
//            .setSigningKey(key)
//            .build()
//            .parseClaimsJws(token)
//            .getBody();
//    }
//
//    public void accessTokenSetHeader(String accessToken, HttpServletResponse response) {
//        String headerValue = BEARER_PREFIX + accessToken;
//        response.setHeader(AUTHORIZATION_HEADER, headerValue);
//    }
//
//    public void refresshTokenSetHeader(String refreshToken, HttpServletResponse response) {
//        response.setHeader("Refresh", refreshToken);
//    }
//
//    // Request Header에 Access Token 정보를 추출
//    public String resolveAccessToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    // Request Header에 Refresh Token 정보를 추출
//    public String resolveRefreshToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(REFRESH_TOKEN_HEADER);
//        if (StringUtils.hasText(bearerToken)) {
//            return bearerToken;
//        }
//        return null;
//    }
//
//}
