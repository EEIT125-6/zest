package xun.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xun.dao.TraceDao;
import xun.model.TraceBean;
import xun.service.TraceService;

@Transactional
@Service
public class TraceServiceImpl implements TraceService {

	@Autowired
	TraceDao tdao;
	
	@Override
	public void addTrace(Integer memberId, Integer stId) {
		tdao.addTrace(memberId, stId);
	}

	@Override
	public void removeTrace(Integer memberId, Integer stId) {
		tdao.removeTrace(memberId, stId);
	}

	@Override
	public String DoITraceThisStore(Integer memberId, Integer stId) {
		return tdao.DoITraceThisStore(memberId, stId);
	}

	@Override
	public List<TraceBean> StoreBeTrace(Integer stId) {
		return tdao.StoreBeTrace(stId);
	}

	@Override
	public void removeAllBeTraceStore(Integer stId) {
		tdao.removeAllBeTraceStore(stId);
	}

	@Override
	public List<Integer> StoreBeTraceQueryByMemberId(Integer stId) {
		return tdao.StoreBeTraceQueryByMemberId(stId);
	}
	
	

}
