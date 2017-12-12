package starrail.sharetext.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class SharetextPageMaker {

	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int displayPageNum = 5;
	private ShareTextCriteria scri;
	
	public void setCri(ShareTextCriteria scri){
		this.scri = scri;
	}
	
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
		
		calcData();
	}
	
	public String makeQuery(int page){
		
		UriComponents uricomponents = 
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", scri.getPerPageNum())
				.build();
		return uricomponents.toUriString();
		
	}
	
	private void calcData(){
		endPage = (int) (Math.ceil(scri.getPage() / 
				(double)displayPageNum) * displayPageNum);
		
		startPage = (endPage - displayPageNum)+1;
		
		int tempEndPage = (int) (Math.ceil(totalCount / 
				(double) scri.getPerPageNum()));
		
		if(endPage > tempEndPage){
			endPage = tempEndPage;
		}
		
		prev = startPage == 1? false : true;
		
		next = endPage * scri.getPerPageNum() >= totalCount ? false : true;
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

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public ShareTextCriteria getScri() {
		return scri;
	}

	public void setScri(ShareTextCriteria scri) {
		this.scri = scri;
	}

	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public String toString() {
		return "SharetextPageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", prev=" + prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", scri=" + scri + "]";
	}
	public String makeSearch(int page){
	    
	    UriComponents uriComponents =
	              UriComponentsBuilder.newInstance()
	              .queryParam("page", page)
	              .queryParam("perPageNum", scri.getPerPageNum())
	              .queryParam("searchType", ((SearchShareTextCriteria)scri).getSearchType())
	              .queryParam("keyword", ((SearchShareTextCriteria)scri).getKeyword())
	              .build();             
	    
	    return uriComponents.toUriString();
	  } 
	 
	 private String encoding(String keyword){
		 
		 if(keyword == null || keyword.trim().length() ==0){
			 return "";
		 }
		 
		 try {
			return URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	 }
	
}
