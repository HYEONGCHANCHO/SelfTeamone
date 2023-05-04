package com.recipeone.security.dto;

import com.recipeone.entity.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MemberSecurityDTO extends User {
    private String user_id;
    private String user_password;
    private MemberRole user_state;
    private boolean social;

    public MemberSecurityDTO(String user_id, String user_password, boolean social, Collection<? extends GrantedAuthority> authorities){
        super(user_id,user_password,authorities);

        this.user_password=user_password;
        this.user_id=user_id;
        this.social=social;

    }


}
