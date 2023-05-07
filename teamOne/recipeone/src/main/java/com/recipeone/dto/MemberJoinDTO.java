package com.recipeone.dto;

import com.recipeone.entity.MemberRole;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDTO {

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

}
