package attributes;

import com.hellparty.factory.AuthenticationFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * title        : SecurityContextFactory
 * author       : sim
 * date         : 2023-07-04
 * description  : @TestMemberAuth 가 붙은 메서드에 대해 SecurityContext 객체를 생성해준다.
 */
public class TestMemberSecurityContextFactory
        implements WithSecurityContextFactory<TestMemberAuth>, TestFixture {

    @Override
    public SecurityContext createSecurityContext(TestMemberAuth annotation) {
        Authentication authentication = AuthenticationFactory.getAuthentication(LOGIN_MEMBER_ID);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return securityContext;
    }
}
