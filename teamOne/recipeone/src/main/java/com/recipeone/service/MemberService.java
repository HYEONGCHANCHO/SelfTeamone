package com.recipeone.service;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.dto.MemberMofifyDTO;
import com.recipeone.entity.Member;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface MemberService {
    static class MidExistException extends Exception{
    }
    static class UserEmailExistException extends Exception{
    }
    static class ConfirmedPasswordException extends Exception{
    }

    void join(MemberJoinDTO memberJoinDTO) throws  MidExistException, UserEmailExistException,ConfirmedPasswordException ;
    void modify(MemberMofifyDTO memberMofifyDTO, @ModelAttribute Member member) throws  MidExistException, UserEmailExistException,ConfirmedPasswordException ;

}
