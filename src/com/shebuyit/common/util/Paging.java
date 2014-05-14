/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================ */
package com.shebuyit.common.util;

public class Paging {

	/**
	 * the current page number but NOT record number
	 */
	private int current = 1;

	/**
	 * the total page number
	 */
	private int total = 1;

	/**
	 *  how many records in one page
	 */
	private int size = 0;

	/**
	 * the total record number
	 */
	private int totalRecord = 0;
	
	/**
	 * queryCondition
	 */
	private String queryCondition = "";

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size > 0)
			this.size = size;
	}
	
	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		if (this.size > 0) {
			int totalPage = totalRecord / this.size;
			if (totalRecord % this.size > 0) {
				totalPage += 1;
			}
			
			if (totalPage == 0) {
				totalPage = 1;
			}
			this.total = totalPage;
		}
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	@Override
	public String toString() {
		return "Paging [current=" + current + ", size=" + size + ", total=" + total + ", totalRecord="+totalRecord+ "]";
	}

}
