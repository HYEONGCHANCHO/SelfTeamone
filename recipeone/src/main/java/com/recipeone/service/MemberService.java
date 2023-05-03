package com.recipeone.service;

import com.recipeone.dto.MemberJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception{

    }

    void join(MemberJoinDTO memberJoinDTO) throws  MidExistException ;

}
