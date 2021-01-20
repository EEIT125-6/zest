package xun.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GetAllErrorController implements HandlerExceptionResolver {

//     private ExceptionLogDao exceptionLogDao;

//     @Override
//     public ModelAndView resolveException(HttpServletRequest request,
//             HttpServletResponse response, Object handler, Exception ex) {
// 
//         // 异常处理，例如将异常信息存储到数据库
//        exceptionLogDao.save(ex);
// 
//         // 视图显示专门的错误页
//         ModelAndView modelAndView = new ModelAndView("errorPage");
//         return modelAndView;
//     }

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView modelAndView = new ModelAndView("e500");
		return modelAndView;
	}
 }
