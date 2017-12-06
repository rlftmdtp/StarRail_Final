package starrail.main.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final String LOGIN ="login";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null) // 기존에 다른 아이디로 로그인 한 적이 있을 때
		{
			session.removeAttribute(LOGIN);
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		ModelMap modelmap = modelAndView.getModelMap();
		Object userVO = modelmap.get("userVO");
		
		if(userVO != null) // 로그인에 성공 했을 때
		{
			session.setAttribute(LOGIN, userVO);
			
			Object dest = session.getAttribute("dest");
			
			response.sendRedirect(dest != null ? (String)dest: "/starrail");
		}
		else
		{
			System.out.println("틀림");
			response.sendRedirect("/starrail/main/login");
		}
	}

	
	
}
