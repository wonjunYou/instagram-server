//package numble.instagram.common.config;
//
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configurations
//@RequiredArgsConstructor
//public class WebConfig implements WebMvcConfigurer {
//
//    private final AuthInteceptor authInteceptor;
//    private final MemberIdentifierArgumentResolver memberIdentifierArgumentResolver;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
//        interceptorRegistry.addInterceptor(authInteceptor);
//    }
//
//    @Override
//    public void addInterceptors(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(memberIdentifierArgumentResolver);
//    }
//}
