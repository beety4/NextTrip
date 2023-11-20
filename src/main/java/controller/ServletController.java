package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// mappingURL.properties를 이용해 *.do 매핑
@WebServlet(urlPatterns = "*.do", 
			initParams = {@WebInitParam(name = "config",
										value = "/WEB-INF/mappingURL.properties")})
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 커맨드, 서비스 인스턴스객체 매핑 정보 저장
    private final Map<String, CommandHandler> handlerMap = new HashMap<>();
	
    public ServletController() {
        super();
    }
    
	// 서블릿을 생성하고 초기화할때 제일 먼저 호출되는 init() 메소드를 이용
	public void init(ServletConfig config) throws ServletException {
		String configFile = config.getInitParameter("config");
		Properties prop = new Properties();
		String configFilePath = config.getServletContext().getRealPath(configFile);
		
		try(FileInputStream fis = new FileInputStream(configFilePath)) {
			prop.load(fis);
		} catch(IOException e) {
			throw new ServletException(e);
		}
		
		Iterator<Object> keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()) {
			String command = (String)keyIter.next();
			String handlerClassName = prop.getProperty(command);
			try {
				Class<?> handlerClass = Class.forName(handlerClassName);
				CommandHandler handlerInstance = (CommandHandler) handlerClass.newInstance();
				handlerMap.put(command, handlerInstance);
			}catch(ClassNotFoundException |
				   InstantiationException |
				   IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
		
	}

	// URL 매핑 작동 메소드
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getRequestURI();
		command = command.substring(request.getContextPath().length() + 1);
		
		CommandHandler handler = handlerMap.get(command);
		String viewORdata = handler.process(request, response);
		
		// Ajax 비동기 통신일 경우 - data 반환
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			response.setContentType("charset=UTF-8");
			response.getWriter().write(viewORdata);
		} else {
			// "redirect:index.do" 와 같이 반환 시 Redirect
			if(viewORdata.startsWith("redirect:")) {
				String redirectURL = viewORdata.substring(9);
				response.sendRedirect(redirectURL);
			} else {
				// 일반 jsp 파일명 반환 시 forward 이동
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + viewORdata + ".jsp");
				rd.forward(request, response);
			}
		}
	}
	
	
}
