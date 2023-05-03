package com.recipeone.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {

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

}
