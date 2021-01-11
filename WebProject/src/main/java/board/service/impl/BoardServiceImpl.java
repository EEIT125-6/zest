package board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.dao.BoardDao;
import board.service.BoardService;
import xun.model.BoardBean;

@Transactional
@Service
public class BoardServiceImpl implements BoardService {
	
	BoardDao dao;
    
	@Autowired
    public void setDao(BoardDao dao) {
		this.dao = dao;
	}
	
	@Override
	public BoardBean getBoardById(int id) {
		return dao.getBoardById(id);
	}

	@Override
	public int insertBoard(BoardBean bb) {
		return dao.insertBoard(bb);
	}

	@Override
	public int updateBoard(BoardBean bb) {
		return dao.updateBoard(bb);
		
	}

	@Override
	public BoardBean getBoardByName(String name) {
		return  dao.getBoardByName(name);
	}

	@Override
	public List<BoardBean> getAllcomment() {
		return dao.getAllcomment();
	}

	@Override
	public List<BoardBean> getMember(String acount) {
		return dao.getMember(acount);
	}
}
