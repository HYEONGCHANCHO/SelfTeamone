package com.recipeone.security.dto;

import com.recipeone.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {
    private String mid;
    private String user_password;
    private String user_email;
    private boolean social;

    public MemberSecurityDTO(String username, String user_password, String user_email, boolean social, Collection<? extends GrantedAuthority> authorities){
        super(username,user_password,authorities);

        this.mid=username;
        this.user_password=user_password;
        this.user_email=user_email;
        this.social=social;

    }


}
