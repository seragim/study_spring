package common;

public class PageVO {
	private int totalList; //총글건수
	private int totalPage; // 총 페이지수
	private int totalBlock; //총 블록수
	private int pageList; //페이지당 보여질 목록 수
	private int blockPage; // 블럭당 보여질 페이지의 수
	private int curPage; // 현재 페이지
	private int beginList, endList; // 각 페이지에 보여질 목록번호
	private int curBlock; // 현재블럭
	private int beginPage, endPage; //  각 블럭에 보여질 페이지번호
	
	public int getTotalList() {
		return totalList;
	}
	public void setTotalList(int totalList) {
		this.totalList = totalList;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalBlock() {
		return totalBlock;
	}
	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}
	public int getPageList() {
		return pageList;
	}
	public void setPageList(int pageList) {
		this.pageList = pageList;
	}
	public int getBlockPage() {
		return blockPage;
	}
	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getBeginList() {
		return beginList;
	}
	public void setBeginList(int beginList) {
		this.beginList = beginList;
	}
	public int getEndList() {
		return endList;
	}
	public void setEndList(int endList) {
		this.endList = endList;
	}
	public int getCurBlock() {
		return curBlock;
	}
	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
}













