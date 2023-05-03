package com.recipeone.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MemberSecurityDTO extends User {
    private String user_id;
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

    public MemberSecurityDTO(String user_name, String user_password,String user_email, boolean social, Collection<? extends GrantedAuthority> authorities){
        super(user_name,user_password,authorities);

        this.user_password=user_password;
        this.user_name=user_name;
        this.social=social;
        this.user_email=user_email;
    }


}
