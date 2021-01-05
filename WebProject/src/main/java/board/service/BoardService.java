package board.service;

import xun.model.BoardBean;

public interface BoardService {
	BoardBean getBoardById(int id);
	
	BoardBean getBoardByName(String name);

	int insertBoard(BoardBean bb);

	int updateBoard(BoardBean bb); 

//	int deleteBoard(Integer id);
}
