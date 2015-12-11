package yourwebproject2.interceptor;

import yourwebproject2.framework.api.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Exception Handler Controller Advice to catch all controller exceptions and respond gracefully to
 * the caller
 *
 * Created by Y.Kamesh on 8/2/2015.
 */
@ControllerAdvice
public class WebAppExceptionAdvice {
    private static Logger LOG = LoggerFactory.getLogger(WebAppExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public APIResponse handleAnyException(Exception e) {
        LOG.error(e.getMessage());
        e.printStackTrace();
        return APIResponse.toErrorResponse(e.getMessage());
    }
}
