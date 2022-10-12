package com.atdaiwa.crowd.mvc.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.atdaiwa.crowd.constant.CrowdConstants;
import com.atdaiwa.crowd.exception.AccountExistedException;
import com.atdaiwa.crowd.exception.AcctExistedForUpdateException;
import com.atdaiwa.crowd.exception.LoginFailedException;
import com.atdaiwa.crowd.exception.NotLoginException;
import com.atdaiwa.crowd.util.CrowdUtil;
import com.atdaiwa.crowd.util.ResultEntity;
import com.google.gson.Gson;

/**
 * @author Administrator
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    /**
     * 通用異常處理類；
     *
     * @param viewName
     * @param exception
     * @param request
     * @param response
     * @return mav
     * @throws IOException
     */
    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        // 1.判斷請求的類型；
        boolean dstRequest = CrowdUtil.discernRequestType(request);
        // 2.對於AJAX請求，返回JSON字符串；
        if (dstRequest) {
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            String json = new Gson().toJson(resultEntity);
            response.getWriter().write(json);
            // 3.已經通過了response返回了JSON字符串，所以就不提供ModelAndView對象；
            return null;
        } else {
            // 4.對於普通請求則返回ModelAndView對象；
            ModelAndView modelAndView = new ModelAndView();
            // 5.將異常命名並存入模型；
            modelAndView.addObject(CrowdConstants.ATTR_NAME_EXCEPTION, exception);
            // 6.設置對應的視圖名稱；
            modelAndView.setViewName(viewName);
            // 7.返回ModelAndView對象；
            return modelAndView;
        }
    }

    /**
     * 算術異常處理方法；
     *
     * @param exception 異常
     * @param request  請求
     * @param response 響應
     * @return 對於AJAX請求返回JSON字符串，普通請求則返回ModelAndView對象；
     * @throws IOException
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView handleArithmeticException(ArithmeticException exception, HttpServletRequest request,
                                                  HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 空指針異常處理方法；
     *
     * @param exception 異常
     * @param request   請求
     * @param response  響應
     * @return 對於AJAX請求返回JSON字符串，普通請求則返回ModelAndView對象；
     * @throws IOException
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView handleNullPointerException(NullPointerException exception, HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 登錄異常處理方法；
     *
     * @param exception 異常
     * @param request   請求
     * @param response  響應
     * @return 對於AJAX請求返回JSON字符串，普通請求則返回ModelAndView對象；
     * @throws IOException
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView handleLoginFailedException(LoginFailedException exception, HttpServletRequest request,
                                                   HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 未登錄異常處理方法；
     *
     * @param exception 異常
     * @param request   請求
     * @param response  響應
     * @return 對於AJAX請求返回JSON字符串，普通請求則返回ModelAndView對象；
     * @throws IOException
     */
    @ExceptionHandler(value = NotLoginException.class)
    public ModelAndView handleNotLoginException(NotLoginException exception, HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 賬號重複異常處理方法；
     *
     * @param exception 異常
     * @param request   請求
     * @param response  響應
     * @return 對於AJAX請求返回JSON字符串，普通請求則返回ModelAndView對象；
     * @throws IOException
     */
    @ExceptionHandler(value = AccountExistedException.class)
    public ModelAndView handleAccountExistedException(AccountExistedException exception, HttpServletRequest request,
                                                      HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 更改賬號重複異常處理方法；
     *
     * @param exception 異常
     * @param request   請求
     * @param response  響應
     * @return 對於AJAX請求返回JSON字符串，普通請求則返回ModelAndView對象；
     * @throws IOException
     */
    @ExceptionHandler(value = AcctExistedForUpdateException.class)
    public ModelAndView handleAcctExistedForUpdateException(AcctExistedForUpdateException exception,
                                                            HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }
}
