package com.hellparty.service;

import com.hellparty.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean isExistMemberByEmail(String email){

        return memberRepository.existsMemberByEmail(email);
    }
}
