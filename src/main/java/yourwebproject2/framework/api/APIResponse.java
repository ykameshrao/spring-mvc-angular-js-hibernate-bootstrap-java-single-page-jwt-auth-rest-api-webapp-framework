package yourwebproject2.framework.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Y.Kamesh on 8/2/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    public static final String API_RESPONSE = "apiResponse";
    Object result;
    String time;
    long code;

    public static class ExceptionAPIResponse extends APIResponse {
        Object details;

        public Object getDetails() {
            return details;
        }

        public void setDetails(Object details) {
            this.details = details;
        }
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public static APIResponse toOkResponse(Object data) {
        return toAPIResponse(data, 200);
    }

    public static APIResponse toErrorResponse(Object data) {
        return toAPIResponse(data, 2001);
    }

    public static ExceptionAPIResponse toExceptionResponse(String result, Object details) {
        ExceptionAPIResponse response = new ExceptionAPIResponse();
        response.setResult(result);
        response.setDetails(details);
        response.setCode(2001);
        return response;
    }

    public APIResponse withModelAndView(ModelAndView modelAndView) {
        modelAndView.addObject(API_RESPONSE, this);
        return this;
    }

    public static APIResponse toAPIResponse(Object data, long code) {
        APIResponse response = new APIResponse();
        response.setResult(data);
        response.setCode(code);
        return response;
    }
}
