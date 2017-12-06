package starrail.main.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	//로그인이 되어있는지 안되어 있는지 확인하는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("login") == null) // 로그인이 안되어 있을 때
		{
			response.sendRedirect("/starrail/main/login");
			
			saveDest(request);
			return false; // 원래 요청했던 페이지로 못가게 막는다
		}
		
		return true; // 로그인이 되어있으면 원래 요청한 페이지로 이동
	}
	
	// 원래 갈려고 했던 경로를 임시로 저장하는 메서드
	private void saveDest(HttpServletRequest req){
		String uri = req.getRequestURI();
		
		String query = req.getQueryString();
		
		System.out.println("uri 테스트" + uri);
		
		if(query == null || query.equals("null")){
			query ="";
		}
		else{
			query = "?" + query;
		}
		
		if(req.getMethod().equals("GET")){
			req.getSession().setAttribute("dest", uri+query);
		}
	}
}
