package bbs.util;

//등록된 전체리뷰 페이징기능
public class PagingForTP {
	private int nowPage, //현재페이지 값
				rowTotal, // 총 게시물 수
				blockList, // 한 페이지에 표현할 게시물 수
				blockPage, //한 블럭당 표현할 페이지 수
				totalPage, // 총 페이지수
				startPage, // 시작 페이지
				endPage, // 끝 페이지
				begin, //시작 게시물의 행 번호
				end; //끝 게시물의 행 번호
	private boolean isPrePage; // 이전 기능 가능여부
	private boolean isNextPage; //다음 기능 가능여부
	
	//JSP에서 표현할 페이징 HTML코드를 저장할 곳!
	private StringBuffer sb;

	public PagingForTP(int nowPage, int rowTotal, int blockList, int blockPage) {
		//인자는 지역변수 이므로 유실될 수 있으니 멤버변수에 저장하자!
		this.nowPage = nowPage;
		this.rowTotal = rowTotal;
		this.blockList = blockList;
		this.blockPage = blockPage;
		
		// 이전기능과 다음기능을 초기화 한다.
		//isPrePage = isNextPage = false;
		isPrePage = false;
		isNextPage = false;
		
		//입력된 전체 게시물의 수를 통해 전체 페이지 수를 구한다.
		/*if(rowTotal%blockList != 0) {
			totalPage = rowTotal/blockList + 1;
		}else {
			totalPage = rowTotal/blockList;
		}*/
		/*
		totalPage = rowTotal/blockList;
		if(rowTotal%blockList != 0) 
			++totalPage;
		*/
		totalPage = (int)Math.ceil((double)rowTotal/blockList);
		
		//현재 페이지의 값이 전체페이지의 값보다 크다면 
		//전체 페이지값을 현재 페이지 값으로 지정한다.
		if(nowPage > totalPage)
			nowPage = totalPage;
		
		//현재 블럭의 시작페이지 값과 끝페이지 값을 구한다.
		startPage = ((nowPage-1)/blockPage*blockPage+1);
		endPage = startPage + blockPage - 1;
		
		//끝페이지의 값이 전체페이지의 값보다 크면 끝페이지의 값을 전체페이지 값으로 지정!
		if(endPage > totalPage)
			endPage = totalPage;
		
		//현재 페이지 값에 의해 시작 게시물의 행번호와
		//끝 게시물의 행번호를 지정하여 현재 페이지에 보여질
		//게시물 목록을 얻을 준비를 하자!
		begin = (nowPage-1)*blockList+1;
		end = begin+blockList-1;
		
		//이전기능 가능여부 확인 (startPage가 1이 아닌 경우)
		if(startPage > 1)
			isPrePage = true;
		
		//다음기능 가능여부 확인 (endPage가 totalPage보다 작은 경우)
		if(endPage < totalPage)
			isNextPage = true;
		
		//이제 현재페이지 값도 알고, 시작페이지와 끝페이지 값을 알았으니
		//페이징 기법에 사용할 HTML코드를 작성하여 StringBuffer에 저장하자!
		sb = new StringBuffer("<ol class='paging'>");
		
		if(isPrePage) {
			sb.append("<li><a href='totalPost?cPage=");
			sb.append(startPage-blockPage);
			sb.append("'> &lt; </a></li>");
		}else {
			// 이전기능 비활성화(startPage가 1인 경우)
			sb.append("<li class='disable'> &lt; </li>");
		}
		
		//페이지 번호 출력하는 반복문(현재 페이지에는 now라는 css클래스를 적용하자)
		for(int i=startPage; i<=endPage; i++) {
			// i의 값이 현재페이지(nowPage)와 같을 때를 구별하여 css클래스를 적용!
			if(i == nowPage) {
				sb.append("<li class='now'>");
				sb.append(i);
				sb.append("</li>");
			} else {
				sb.append("<li><a href='totalPost?cPage=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a></li>");
			}
		}// for의 끝!
		
		//다음기능 가능여부 확인
		if(isNextPage) {
			sb.append("<li><a href='totalPost?cPage=");
			sb.append(startPage+blockPage);
			sb.append("'> &gt; </a></li>");
		}else {
			// 이전기능 비활성화(startPage가 1인 경우)
			sb.append("<li class='disable'> &gt; </li>");
		}
		sb.append("</ol>");
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
	}

	public int getBlockList() {
		return blockList;
	}

	public void setBlockList(int blockList) {
		this.blockList = blockList;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public boolean isPrePage() {
		return isPrePage;
	}

	public void setPrePage(boolean isPrePage) {
		this.isPrePage = isPrePage;
	}

	public boolean isNextPage() {
		return isNextPage;
	}

	public void setNextPage(boolean isNextPage) {
		this.isNextPage = isNextPage;
	}

	public StringBuffer getSb() {
		return sb;
	}

	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}
}
