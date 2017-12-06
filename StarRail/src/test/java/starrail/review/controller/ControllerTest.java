package starrail.review.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import starrail.review.controller.ControllerTest;
import starrail.review.domain.ReviewVO;
import starrail.review.persistence.ReviewDao;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ControllerTest {
private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);
	
	@Inject
	private WebApplicationContext wac;
	private ReviewDao dao;
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		logger.info("Setup..................");
	}
	
		@Test
	    public void testInsertReview() throws Exception{
			Date date = new Date();

		    Timestamp timestamp = new Timestamp(date.getTime());
	        ReviewVO review = new ReviewVO();
	        review.setR_no(88);
	        review.setM_id("rlftmdtp");
	        review.setR_title("�̾� �����̴�!!!!!");
	        review.setR_content("����������������");
	        review.setR_date(timestamp);
	        review.setR_hit(0);
	       

	        dao.insertReview(review);
	    }
}
