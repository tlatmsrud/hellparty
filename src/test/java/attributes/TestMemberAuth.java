package attributes;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-04
 * description  :
 */

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = TestMemberSecurityContextFactory.class)
public @interface TestMemberAuth {
}
