package numble.instagram.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import numble.instagram.api.auth.service.AuthService;

import numble.instagram.common.exception.AuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";

    private final AuthService authService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (!(handler instanceof final HandlerMethod handlerMethod)) {
            return true;
        }
        if (handlerMethod.hasMethodAnnotation(Authenticated.class)) {
            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            checkHeader(authorizationHeader);
            final String token = authorizationHeader.substring(BEARER_PREFIX.length());
            checkTokenCertify(token);
        }
        return true;
    }

    private void checkHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            throw new AuthenticationException("인증 헤더가 적절하지 않습니다.");
        }
    }

    private void checkTokenCertify(String token) {
        if (!authService.isCertified(token)) {
            throw new AuthenticationException("토큰이 유효하지 않습니다.");
        }
    }
}