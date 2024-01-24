//package numble.instagram.common.interceptor;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.net.http.HttpHeaders;
//import numble.instagram.api.auth.service.AuthService;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class AuthInterceptor implements HandlerInterceptor {
//
//    private static final String BEARER_PREFIX = "Bearer ";
//
//    private final AuthService authService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) {
//        if (handlerMethod.hasMethodAnnotation(Authenticated.class)) {
//            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//            checkHeader(authorizationHeader);
//
//            String token = authorizationHeader.substring(BEARER_PREFIX.length());
//
//            checkTokenCertify(token);
//        }
//
//        return true;
//    }
//
//    private void checkHeader(String authorizationHeader) {
//        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
//            throw new AuthenticationException("인증 헤더가 적절하지 않습니다.");
//        }
//    }
//
//    private void checkTokenVerify(String token) {
//        if (!authService.isCertified(token)) {
//            throw new AuthenticationException("토큰이 유효하지 않습니다.");
//        }
//    }
//}

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException exception) {
//        log.debug("Authentication error occurred: {}", exception.getMessage(), exception);
//
//        ErrorResponse errorResponse = ErrorResponse.of(exception.getMessage());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
//    }