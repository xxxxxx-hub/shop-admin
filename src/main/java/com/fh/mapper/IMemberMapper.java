package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.param.MemberWhere;
import com.fh.po.member.Member;

import java.util.List;

public interface IMemberMapper  extends BaseMapper<Member> {

    Long findCount(MemberWhere memberWhere);

    List<Member> findList(MemberWhere memberWhere);

    Member findMemberByName(String memberName);
}
