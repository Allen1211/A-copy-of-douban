package com.allen.douban.bean;

public class PageBean {
	private static final int PERIOD = 5;
	private int totalPage;
	private int currentPage;
	private int rowPerPage;
	private int totalSize;
	private int begin;
	private int end;

	public PageBean() {
		this.setCurrentPage(1); // 默认第一页
		this.setRowPerPage(10); // 默认每页十行
		this.setTotalPage(0);
		this.setTotalSize(0);
	}

	public PageBean(int rowPerPage) {
		this.setCurrentPage(1);
		this.setRowPerPage(rowPerPage);
		this.setTotalPage(0);
		this.setTotalSize(0);
	}

	public PageBean(int rowPerPage, int currentPage) {
		this.setCurrentPage(currentPage);
		this.setRowPerPage(rowPerPage);
		this.setTotalPage(0);
		this.setTotalSize(0);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		if (this.totalSize <= this.rowPerPage) {
			this.totalPage = 1;
		} else {
			if (this.totalSize % this.rowPerPage == 0) {
				this.totalPage = totalSize / rowPerPage;
			} else {
				this.totalPage = totalSize / rowPerPage + 1;
			}
		}
	}

	public int getBegin() {
		if (currentPage <= PERIOD) {
			return 1;
		} else if (currentPage % PERIOD == 0) {
			return ((currentPage - 1) / PERIOD) * PERIOD + 1;
		} else {
			return (currentPage / PERIOD) * PERIOD + 1;
		}
	}

	public int getEnd() {
		if (totalPage < getBegin() + PERIOD) {
			return totalPage;
		} else if (currentPage % PERIOD == 0) {
			return currentPage;
		} else {
			return getBegin() + PERIOD - 1;
		}
	}

	@Override
	public String toString() {
		return "totalSize: " + totalSize + "\t" + "totalPage: " + totalPage + "\t" + "rowPerPage: " + rowPerPage + "\t"
				+ currentPage;
	}

	public static void main(String[] args) {
		PageBean page = new PageBean();
		page.setTotalPage(4);
		page.setCurrentPage(1);
		System.out.println(page.toString());
		System.out.println(page.getBegin());
		System.out.println(page.getEnd());
	}

}
