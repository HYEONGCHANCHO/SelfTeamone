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
    @Column(name = "Id")
    private String Id;

    private String user_password;

    private String user_num;
    private String user_name;
    private String user_phone;
    private String user_addr;
    private String user_email;
    private MemberRole user_state;
    private String user_post;
    private String user_lev;
    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>(); //enum MemberRole에 있는 상수값이 roleSet이라는 변수에 저장

    public void changePassword(String user_password){
        this.user_password = user_password;
    }
    public void addRole(MemberRole user_state){
        this.user_state = user_state;
    }
    public void changeEmail(String user_email){
        this.user_email = user_email;
    }
    public void changeSocial(boolean social){
        this.social = social;
    }


}
