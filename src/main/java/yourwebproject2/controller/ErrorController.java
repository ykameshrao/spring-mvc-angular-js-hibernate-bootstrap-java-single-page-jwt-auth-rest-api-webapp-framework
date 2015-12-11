package yourwebproject2.controller;

import yourwebproject2.framework.api.APIResponse;
import yourwebproject2.framework.controller.BaseController;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: kameshr
 */
@Controller
public class ErrorController extends BaseController {
    private static Logger LOG = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("error")
    public @ResponseBody  APIResponse customError(HttpServletRequest request, HttpServletResponse response) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String exceptionMessage = (String) request.getAttribute("javax.servlet.error.message");

        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        String httpReason = httpStatus.getReasonPhrase();

        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        Map<String, Object> resp = new HashMap<>();
        String message = MessageFormat.format("{0} {1} returned for {2} with message: {3}",
                statusCode, httpReason, requestUri, exceptionMessage);
        resp.put("message", message);
        resp.put("cause", exceptionMessage);
        resp.put("exceptionRootCauseMessage", ExceptionUtils.getRootCauseMessage(throwable));
        resp.put("stacktrace", ExceptionUtils.getRootCauseStackTrace(throwable));

        LOG.error(message);
        ExceptionUtils.printRootCauseStackTrace(throwable);
        LOG.error(ExceptionUtils.getFullStackTrace(throwable));

        return APIResponse.toExceptionResponse(exceptionMessage, resp);
    }
}
