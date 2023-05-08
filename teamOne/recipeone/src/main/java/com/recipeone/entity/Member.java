package com.recipeone.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
@Table(name = "MEMBER")
public class Member extends BaseEntity{

    @Id
    private String mid;

    private String user_password;
    private String user_email;

    private String user_fullname;
    private String user_num;
    private String user_phone;
    private String user_addr;
//    private String user_state="ACTIVE";
    private String user_post;
    private String user_lev;
    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>(); //enum MemberRole에 있는 상수값이 roleSet이라는 변수에 저장

    public void changePassword(String user_password){
        this.user_password = user_password;
    }
    public void changeEmail(String user_email){
        this.user_email = user_email;
    }
    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }
    public void changeSocial(boolean social){
        this.social = social;
    }


}
