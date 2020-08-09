package com.fh.controller.member;

import com.fh.biz.member.IMemberService;
import com.fh.common.DataTableResult;
import com.fh.common.ReposeEnum;
import com.fh.common.ServerResponse;
import com.fh.param.MemberWhere;
import com.fh.po.member.Member;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/members")
@CrossOrigin
public class MemberController {

    @Resource(name ="memberService")
    private IMemberService memberService;

   @RequestMapping("index")
    private String toIndex(){
       return "member/index";
   }


    @RequestMapping("findList")
    @ResponseBody
    private DataTableResult findList(MemberWhere memberWhere){

        return memberService.findList(memberWhere);
    }
    @RequestMapping("login")
    @ResponseBody
    public ServerResponse login(Member member, HttpSession session){
        String memberName = member.getMemberName();
        String password = member.getPassword();
        Member member1 = memberService.findMemberByName(memberName);

        if(member1==null){
            return  ServerResponse.error(ReposeEnum.USERNAME_IS_NOT_EXISTS);
        }
        if (StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)) {
                return ServerResponse.error(ReposeEnum.USERNAME_PASSWORD_IS_NULL);
            }

            if (!password.equals(member1.getPassword())) {
                return ServerResponse.error(ReposeEnum.PASSWORD_IS_ERROR);
            }

                    session.setAttribute("member",member1);

                      return ServerResponse.success();


    }


    @RequestMapping("outLogin")
    public  String outLogin(HttpServletRequest request){
        request.getSession().invalidate();
        return  "redirect:/login.jsp";
    }


}
