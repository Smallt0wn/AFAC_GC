package AFAC.GC.service;


import AFAC.GC.entity.Member;
import AFAC.GC.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateUser(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateUser(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("already exist User");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{


        return null;
    }
}
