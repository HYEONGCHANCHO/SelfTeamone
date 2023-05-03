package com.recipeone.repository;

import com.recipeone.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import java.util.Optional;

public interface MemberRepostiry extends JpaRepository<Member,String> {
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.user_id = :user_id and m.social = false")
    Optional<Member> getWithRoles(String user_id);

}
