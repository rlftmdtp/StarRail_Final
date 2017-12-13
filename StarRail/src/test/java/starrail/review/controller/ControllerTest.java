package starrail.review.controller;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import starrail.message.persistence.MessageDAO;
import starrail.review.controller.ControllerTest;
import starrail.review.persistence.ReviewDao;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ControllerTest {
private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);
	
	@Inject
	private WebApplicationContext wac;
	private MessageDAO msgdao;
	private ReviewDao dao;
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		logger.info("Setup..................");
	}
	
		@Test
	    public void testInsertMessage() throws Exception{
/*			MessageVO message = new MessageVO();
			message.setMsg_no(80);
			message.setM_id("rlftmdtp");
			message.setMsg_sendid("thf147");
			message.setMsg_content("ㅋㅋㅋㅋㅋ응안녕");
			message.getMsg_date();
			message.setMsg_hit(0);
			
			msgdao.insertMessage(message);*/
	    }
}
