package com.hellparty.oauth;

import com.hellparty.domain.MemberEntity;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.jwt.JwtProvider;
import com.hellparty.repository.MemberRepository;
import com.hellparty.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

/**
 * title        : Custom AuthenticationSuccessHandler
 * author       : sim
 * date         : 2023-07-02
 * description  : authorizationCode, accessToken, refreshToken 취득 및 accessToken을 통한 유저 정보 조회 후
 * 최종적으로 호출되는 Handler
 */

@AllArgsConstructor
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    private final TokenService tokenService;

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Map<String,Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        Long memberId = memberRepository.findMemberIdByEmail(email);

        if(memberId == null){
            MemberEntity memberEntity = attributesToMemberEntity(attributes);
            memberId = memberRepository.save(memberEntity).getId();
        }

        String accessToken = jwtProvider.generateAccessToken(memberId);
        String refreshToken = jwtProvider.generateRefreshToken(memberId);

        tokenService.saveRefreshToken(memberId, refreshToken);
        responseRedirectUrl(request, response, accessToken, refreshToken);
    }

    public void responseRedirectUrl(HttpServletRequest request, HttpServletResponse response,
                                    String accessToken, String refreshToken) throws IOException {
        String targetUrl = UriComponentsBuilder.fromUriString("/login?status=success")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public MemberEntity attributesToMemberEntity(Map<String,Object> attributes){

        return MemberEntity.builder()
                    .email(attributes.get("email").toString())
                    .nickname(attributes.get("nickname").toString())
                    .profileUrl(attributes.get("profileUrl").toString())
                    .execStatus(ExecStatus.W)
                    .findStatus(PartnerFindStatus.N)
                    .build();
    }

}
