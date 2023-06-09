package com.recipeone.service;

import com.recipeone.dto.MemberJoinDTO;
import com.recipeone.dto.MemberMofifyDTO;
import com.recipeone.entity.Member;
import com.recipeone.security.dto.MemberSecurityDTO;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
    static class MidExistException extends Exception{
    }
    static class UserNickNameExistException extends Exception{
    }
    static class UserEmailExistException extends Exception{
    }
    static class ConfirmedPasswordException extends Exception{
    }
    static class ConfirmedmodifyPasswordException extends Exception{
    }
    static class WrongPasswordException extends Exception{
    }

    void join(MemberJoinDTO memberJoinDTO) throws  MidExistException,UserNickNameExistException, UserEmailExistException,ConfirmedPasswordException ;
    void socialmodify(MemberMofifyDTO memberMofifyDTO, @ModelAttribute Member member) throws  MidExistException, UserNickNameExistException,UserEmailExistException,ConfirmedPasswordException ;
    void membermodify(MemberMofifyDTO memberMofifyDTO, @ModelAttribute Member member) throws  UserNickNameExistException,UserEmailExistException ;
    void passwordmodify(MemberMofifyDTO memberMofifyDTO, @ModelAttribute Member member) throws  ConfirmedmodifyPasswordException,WrongPasswordException ;
    public boolean checkDuplicatedUsername(String username);
    public boolean checkDuplicatedUsernickname(String usernickname) ;
}
