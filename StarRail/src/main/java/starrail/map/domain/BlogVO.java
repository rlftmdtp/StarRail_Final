package starrail.map.domain;

public class BlogVO {
	private Integer total;
	private String title;
	private String description;
	private String bloggername;
	private String bloggerlink;
	private String postdate;
	
	public BlogVO(){};

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public BlogVO(Integer total, String title, String description, String bloggername, String bloggerlink,
			String postdate) {
		super();
		this.total = total;
		this.title = title;
		this.description = description;
		this.bloggername = bloggername;
		this.bloggerlink = bloggerlink;
		this.postdate = postdate;
	}
	
	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBloggername() {
		return bloggername;
	}

	public void setBloggername(String bloggername) {
		this.bloggername = bloggername;
	}

	public String getBloggerlink() {
		return bloggerlink;
	}

	public void setBloggerlink(String bloggerlink) {
		this.bloggerlink = bloggerlink;
	}
	
	
}
