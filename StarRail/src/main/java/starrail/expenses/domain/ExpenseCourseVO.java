package starrail.expenses.domain;

public class ExpenseCourseVO {
	//예상경비 테이블
		private int e_no;
		private String m_id;
		private String e_title;
		private String e_sdate;
		private String e_edate;
		private int e_total;
		
		
		public int getE_no() {
			return e_no;
		}

		public void setE_no(int e_no) {
			this.e_no = e_no;
		}


		public String getM_id() {
			return m_id;
		}

		public void setM_id(String m_id) {
			this.m_id = m_id;
		}

		public String getE_title() {
			return e_title;
		}

		public void setE_title(String e_title) {
			this.e_title = e_title;
		}

		public String getE_sdate() {
			return e_sdate;
		}

		public void setE_sdate(String e_sdate) {
			this.e_sdate = e_sdate;
		}

		public String getE_edate() {
			return e_edate;
		}

		public void setE_edate(String e_edate) {
			this.e_edate = e_edate;
		}

		public int getE_total() {
			return e_total;
		}

		public void setE_total(int e_total) {
			this.e_total = e_total;
		}

		@Override
		public String toString() {
			return "ExpensesVO [e_no=" + e_no + ", m_id=" + m_id + ", e_title=" + e_title + ", e_sdate="
					+ e_sdate + ", e_edate=" + e_edate + ", e_total=" + e_total + "]";
		}
		
}