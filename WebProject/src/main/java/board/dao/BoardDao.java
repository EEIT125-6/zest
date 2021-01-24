package board.dao;

import java.util.List;

//import java.util.List;

import xun.model.BoardBean;
import xun.model.StoreBean;

public interface BoardDao {
	BoardBean getBoardById(int id);
	
//	List<BoardBean>  getBoard(); 

	BoardBean getBoardByName(String name);
	
	int insertBoard(BoardBean bb);

	int updateBoard(BoardBean bb); 

//	int deleteBoard(Integer id);
	
	List<BoardBean> getAllcomment();

	List<BoardBean> getMember(String acount);
	
	List<BoardBean> getStoreStar(StoreBean sb);
}

