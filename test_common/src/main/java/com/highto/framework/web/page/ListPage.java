package com.highto.framework.web.page;

import java.util.List;

/**
 * 用于展现层的列表页,主要是对于展现层分页逻辑的封装。
 * 
 * @author neo
 *
 */
public class ListPage {

	/**
	 * 当前页数,从1开始
	 */
	private int pageNum;

	/**
	 * 数据总条数
	 */
	private int totalItemsCount;

	/**
	 * 每页数据条数
	 */
	private int pageItemsCount;

	private List<?> items;

	public ListPage(List<?> items, int pageNum, int pageItemsCount, int totalItemsCount) {
		this.items = items;
		this.pageItemsCount = pageItemsCount;
		this.pageNum = pageNum;
		this.totalItemsCount = totalItemsCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getTotalItemsCount() {
		return totalItemsCount;
	}

	public int getPageItemsCount() {
		return pageItemsCount;
	}

	public List<?> getItems() {
		return items;
	}

	/**
	 * 计算总页数
	 * 
	 * @return
	 */
	public int getPageCount() {
		int count = totalItemsCount / pageItemsCount;
		if (totalItemsCount % pageItemsCount > 0) {
			return count + 1;
		} else {
			return count;
		}
	}

	/**
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return pageNum > 1;
	}

	/**
	 * 是否有下一页
	 * 
	 * @return
	 */
	public boolean isHasNextPage() {
		return pageNum < getPageCount();
	}

}
