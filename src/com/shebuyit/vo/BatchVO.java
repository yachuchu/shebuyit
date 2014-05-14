/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================   */
package com.shebuyit.vo;

public class BatchVO {

	private String batchName;	
	private int checkStatus;
	private int postStatus;
	private long batchId;
	
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	public int getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(int postStatus) {
		this.postStatus = postStatus;
	}
	
	
	
}
