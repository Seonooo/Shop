package project.shop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.shop.user.domain.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
//    Optional<Member> findByMemberId(String name);
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
}
