package com.toilet.vo;

public class ReportVO {
				private String report_idx,
				toilet_name,
				content,
				reason,
				report_date,
				reporter,
				ip,
				status,			// 0 - 신고new, 1 - 신고처리완료, 2 - 신고철회
				writer,
				review_idx;

				public String getReport_idx() {
					return report_idx;
				}

				public void setReport_idx(String report_idx) {
					this.report_idx = report_idx;
				}

				public String getToilet_name() {
					return toilet_name;
				}

				public void setToilet_name(String toilet_name) {
					this.toilet_name = toilet_name;
				}

				public String getContent() {
					return content;
				}

				public void setContent(String content) {
					this.content = content;
				}

				public String getReason() {
					return reason;
				}

				public void setReason(String reason) {
					this.reason = reason;
				}

				public String getReport_date() {
					return report_date;
				}

				public void setReport_date(String report_date) {
					this.report_date = report_date;
				}

				public String getReporter() {
					return reporter;
				}

				public void setReporter(String reporter) {
					this.reporter = reporter;
				}

				public String getIp() {
					return ip;
				}

				public void setIp(String ip) {
					this.ip = ip;
				}

				public String getStatus() {
					return status;
				}

				public void setStatus(String status) {
					this.status = status;
				}

				public String getWriter() {
					return writer;
				}

				public void setWriter(String writer) {
					this.writer = writer;
				}

				public String getReview_idx() {
					return review_idx;
				}

				public void setReview_idx(String review_idx) {
					this.review_idx = review_idx;
				}
				
				
}
