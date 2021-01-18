package xun.dao;

import java.util.List;

import xun.model.TraceBean;

public interface TraceDao {
	
	void addTrace(Integer memberId,Integer stId);
	
	void removeTrace(Integer memberId,Integer stId);
	
	String DoITraceThisStore(Integer memberId,Integer stId);
	
	List<TraceBean> StoreBeTrace(Integer stId);
}
