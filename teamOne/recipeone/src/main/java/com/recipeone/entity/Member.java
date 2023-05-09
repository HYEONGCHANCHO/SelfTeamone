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

    private String password;
    private String useremail;

    private String userfullname;
    private String usernum;
    private String userphone;
    private String useraddr;
    private String userpost;
    private String userlev;
    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>(); //enum MemberRole에 있는 상수값이 roleSet이라는 변수에 저장

    public void changePassword(String password){
        this.password = password;
    }
    public void changeEmail(String useremail){
        this.useremail = useremail;
    }
    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }
    public void changeSocial(boolean social){
        this.social = social;
    }


}
