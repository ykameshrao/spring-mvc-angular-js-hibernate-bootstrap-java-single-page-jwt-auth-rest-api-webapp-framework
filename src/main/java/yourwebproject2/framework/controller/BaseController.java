package yourwebproject2.framework.controller;

import yourwebproject2.model.dto.UserDTO;
import org.apache.commons.lang.Validate;
import org.json.JSONObject;
import org.springframework.security.crypto.codec.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Scanner;

/**
 * All controllers in spring should extend this controller so as to have
 * centralize control for doing any sort of common functionality.
 * e.g. extracting data from post request body
 *
 * @author : Y Kamesh Rao
 */
public abstract class BaseController {
    protected static final String JSON_API_CONTENT_HEADER = "Content-type=application/json";

    public String extractPostRequestBody(HttpServletRequest request) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
        return "";
    }

    public JSONObject parseJSON(String object) {
        return new JSONObject(object);
    }

    public void decorateUserDTOWithCredsFromAuthHeader(String authHeader, UserDTO userDTO) {
        String[] basicAuth = authHeader.split(" ");
        Validate.isTrue(basicAuth.length == 2, "the auth header is not splittable with space");
        Validate.isTrue(basicAuth[0].equalsIgnoreCase("basic"), "not basic auth: "+basicAuth[0]);
        Validate.isTrue(Base64.isBase64(basicAuth[1].getBytes()), "encoded value not base64");

        String decodedAuthHeader = new String(Base64.decode(basicAuth[1].getBytes()));
        String[] creds = decodedAuthHeader.split(":");
        Validate.isTrue(creds.length == 2, "the creds were not concatenated using ':', could not split the decoded header");

        userDTO.setEmail(creds[0]);
        userDTO.setPassword(creds[1]);
    }
}
