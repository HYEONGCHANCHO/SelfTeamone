package com.recipeone.repository;

import com.recipeone.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles(String mid);

    Optional<Member> findById(String mid);

    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByUseremail(String useremail);

    @Modifying
    @Transactional
//    @Query("update Member m set m.password =:password where m.mid =:mid")
    @Query("update Member m set m.password = :password, m.social = false where m.mid = :mid")
    void updatePassword(@Param("password")String password,@Param("mid") String mid);

}
