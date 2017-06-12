package com.chair.manager.bean;

import java.util.List;

public class EasyUIResult {

    private Long total;

    private List<?> rows;
    
    private Long status=200L;
    
    private int type;
    

    public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public EasyUIResult() {

    }
	
    public EasyUIResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }
	
	
    public EasyUIResult(Long total, List<?> rows, int type) {
        this.total = total;
        this.rows = rows;
        this.type = type;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
    

}
