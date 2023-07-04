package auth;

import com.hellparty.factory.AuthenticationFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-04
 * description  :
 */
public class TestMemberSecurityContextFactory implements WithSecurityContextFactory<TestMemberAuth> {

    private static final Long TEST_MEMBER_ID = 1L;
    @Override
    public SecurityContext createSecurityContext(TestMemberAuth annotation) {
        Authentication authentication = AuthenticationFactory.getAuthentication(TEST_MEMBER_ID);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return securityContext;
    }
}
