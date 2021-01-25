package xun.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Trace")
public class TraceBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer traceId;
	
	Integer memberId;
	
	Integer storeId;
	
	Timestamp timestamp;

	
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "TraceBean [traceId=" + traceId + ", memberId=" + memberId + ", storeId=" + storeId + "]";
	}

	public TraceBean() {
		super();
	}

	public Integer getTraceId() {
		return traceId;
	}

	public void setTraceId(Integer traceId) {
		this.traceId = traceId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}


	public TraceBean(Integer traceId, Integer memberId, Integer storeId) {
		super();
		this.traceId = traceId;
		this.memberId = memberId;
		this.storeId = storeId;
	}

	
	
}
