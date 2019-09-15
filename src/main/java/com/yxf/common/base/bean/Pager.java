package com.yxf.common.base.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on May 14, 2012
 * <p>Description: 不使用datatable情况下的分页bean</p>
 * @version        1.0
 */
public class Pager<T> implements Serializable {

	private int pageSize = 10;
	
	private long totalSize;
	private int currentPage = 1;
	private int totalPage = -1;
	
	private List<T> data = new ArrayList();
//    private Object collections = new ArrayList();
    
    private int startRd; //开始记录
    private int endRd;  //结束记录
	
	public void setStartRd(int startRd) {
		this.startRd = startRd;
	}

	public void setEndRd(int endRd) {
		this.endRd = endRd;
	}

	/**
	 * 
	 */
	public Pager() {
	}
	
	public Pager(long totalSize, int pageSize, List data) {
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.data = data;
	}

	public int getTotalPage(){
		if(totalPage == -1){
			totalPage = (int)Math.ceil((double)this.totalSize/this.pageSize);
		}
		
		return totalPage;
	}
	
	public boolean hasNext(){
		long lastPage = getTotalPage();
		if(this.currentPage >= lastPage)
			return false;
		return true;
	}


	/**
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}


	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public  int getStartOfPage() {
		return (currentPage - 1) * this.pageSize;
	}
//
//	/**
//	 * @return the curPageNo
//	 */
//	public int getCurPageNo() {
//		return curPageNo;
//	}

//	/**
//	 * @param curPageNo the curPageNo to set
//	 */
//	public void setCurPageNo(int curPageNo) {
//		this.curPageNo = curPageNo;
//	}

	public void rebuild(int totalSize) {	
		this.totalSize = totalSize;
		setTotalPage( ( totalSize - 1 ) / getPageSize() + 1);
		if(currentPage < 1)
			currentPage = 1;
		if(currentPage > totalPage)
			currentPage = totalPage;
	}


	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRd() {
		startRd = getStartOfPage() + 1;
		return startRd;
	}

	public int getEndRd() {
		int endRd = getStartOfPage() + this.pageSize;
		endRd = endRd > this.totalSize ? (int)this.totalSize : endRd;
		return endRd;
	}
	

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public static Pager init(Pager pager){
		return pager==null?new Pager():pager;
	}

}

