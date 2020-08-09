package com.fh.biz.member;


import com.fh.common.DataTableResult;
import com.fh.mapper.IMemberMapper;
import com.fh.param.MemberWhere;
import com.fh.po.member.Member;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service("memberService")
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;


    @Override
    public DataTableResult findList(MemberWhere memberWhere) {
        String realName = memberWhere.getRealName();
        String memberName = memberWhere.getMemberName();
        if (StringUtils.isNotEmpty(realName) || StringUtils.isNotEmpty(memberName)) {
            try {
                realName = new String(realName.getBytes("iso-8859-1"), "utf-8");
                memberName = new String(memberName.getBytes("iso-8859-1"), "utf-8");
                memberWhere.setRealName(realName);
                memberWhere.setMemberName(memberName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
            Long totalCount = memberMapper.findCount(memberWhere);

            List <Member> memberList = memberMapper.findList(memberWhere);

            return new DataTableResult(memberWhere.getDraw(), totalCount, totalCount, memberList);
        }

    @Override
    public Member findMemberByName(String memberName) {

        return memberMapper.findMemberByName(memberName);
    }

}
