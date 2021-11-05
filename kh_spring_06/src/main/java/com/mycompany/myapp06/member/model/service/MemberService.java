package com.mycompany.myapp06.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp06.member.model.dao.MemberDao;
import com.mycompany.myapp06.member.model.vo.Member;

@Service("memberService")
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public List<Member> selectMembers(){
		return memberDao.selectMembers();
	}
}
