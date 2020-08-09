package com.fh.biz.member;

import com.fh.common.DataTableResult;
import com.fh.param.MemberWhere;
import com.fh.po.member.Member;

public interface IMemberService {

     DataTableResult findList(MemberWhere memberWhere);

    Member findMemberByName(String memberName);
}
