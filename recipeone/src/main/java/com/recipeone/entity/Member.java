package com.recipeone.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
@Table(name = "MEMBER")
public class Member extends BaseEntity{

    @Id
    private String user_id;

    private String user_num;
    private String user_name;
    private String user_password;
    private String user_phone;
    private String user_addr;
    private String user_email;
    private String user_state;
    private String user_date;
    private String user_post;
    private String user_lev;
    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String mpw){
        this.user_password = user_password;
    }

    public void addRole(MemberRole memberRole){
        this.user_state = user_state;
    }
    public void changeEmail(String user_email){
        this.user_email = user_email;
    }

    public void changeSocial(boolean social){
        this.social = social;
    }


}
