//package numble.instagram.common.resolver;
//
//import java.net.http.HttpHeaders;
//import javax.naming.AuthenticationException;
//import lombok.RequiredArgsConstructor;
//import numble.instagram.api.auth.service.AuthService;
//import numble.instagram.common.interceptor.MemberIdentifier;
//import org.springframework.core.MethodParameter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//@Component
//@RequiredArgsConstructor
//public class MemberIdentifierArgumentResolver implements HandlerMethodArgumentResolver {
//
//    private static final String BEARER_PREFIX = "Bearer ";
//    private final AuthService authService;
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(MemberIdentifier.class);
//    }
//
//    @Override
//    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//
//        final String authorizationHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
//
//        checkHeader(authorizationHeader);
//
//        String token = authorizationHeader.substring(BEARER_PREFIX.length());
//        return authService.findIdentifierByToken(token);
//    }
//
//    private void checkHeader(final String authorizationHeader) {
//        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
//            throw new AuthenticationException("인증 헤더가 적절하지 않습니다.");
//        }
//    }
//}
