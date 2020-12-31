package com.toilet.vo;

public class BbsVO {
	private String review_idx, 
					toilet_name, 
					userID, 
					content, 
					star, 
					reg_date, 
					status,
					ip,					
					avgStar;

	public String getAvgStar() {
		return avgStar;
	}
	
	public void setAvgStar(String avgStar) {
		this.avgStar = avgStar;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReview_idx() {
		return review_idx;
	}

	public void setReview_idx(String review_idx) {
		this.review_idx = review_idx;
	}

	public String getToilet_name() {
		return toilet_name;
	}

	public void setToilet_name(String toilet_name) {
		this.toilet_name = toilet_name;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
