package crm.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crm.model.Category;
import crm.model.Hobby;
import crm.model.Member;
import crm.repository.MemberRepository;
import crm.service.MemberService;
import java.util.List;
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository memberDao;
	
	@Override
	public void save(Member member) {
		memberDao.save(member);
	}

	@Override
	public void delete(Integer id) {
		memberDao.delete(id);
	}

	@Override
	public void update(Member member) {
		memberDao.update(member);
	}

	@Override
	public List<Member> getAllMembers() {
		return memberDao.getAll();
	}

	@Override
	public Member get(Integer id) {
		return memberDao.get(id);
		
	}

}
