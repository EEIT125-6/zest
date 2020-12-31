 package board.dao;

//import java.util.List;

import xun.model.BoardBean;

public interface BoardDao {
	BoardBean getBoardById(int id);
	
//	List<BoardBean>  getBoard(); 

	BoardBean getBoardByName(String name);
	
	int insertBoard(BoardBean bb);

	int updateBoard(BoardBean bb); 

	int deleteBoard(Integer id);

}
