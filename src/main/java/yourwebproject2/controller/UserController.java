package yourwebproject2.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import yourwebproject2.auth.AuthenticationFailedException;
import yourwebproject2.auth.JWTTokenAuthFilter;
import yourwebproject2.framework.api.APIResponse;
import yourwebproject2.framework.controller.BaseController;
import yourwebproject2.model.dto.UserDTO;
import yourwebproject2.model.entity.User;
import yourwebproject2.service.MailJobService;
import yourwebproject2.service.UserService;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Date;
import java.util.HashMap;

/**
 * @author: kameshr
 *
 * Referred: https://github.com/mpetersen/aes-example, http://niels.nu/blog/2015/json-web-tokens.html
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    private @Autowired UserService userService;
    private @Autowired MailJobService mailJobService;

    /**
     * Authenticate a user
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, headers = {JSON_API_CONTENT_HEADER})
    public @ResponseBody APIResponse authenticate(@RequestBody UserDTO userDTO,
                                                  HttpServletRequest request, HttpServletResponse response) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, AuthenticationFailedException {
        Validate.isTrue(StringUtils.isNotBlank(userDTO.getEmail()), "Email is blank");
        Validate.isTrue(StringUtils.isNotBlank(userDTO.getEncryptedPassword()), "Encrypted password is blank");
        String password = decryptPassword(userDTO);

        LOG.info("Looking for user by email: "+userDTO.getEmail());
        User user = userService.findByEmail(userDTO.getEmail());

        HashMap<String, Object> authResp = new HashMap<>();
        if(userService.isValidPass(user, password)) {
            LOG.info("User authenticated: "+user.getEmail());
            userService.loginUser(user, request);
            createAuthResponse(user, authResp);
        } else {
            throw new AuthenticationFailedException("Invalid username/password combination");
        }

        return APIResponse.toOkResponse(authResp);
    }

    /**
     * Register new user
     * POST body expected in the format - {"user":{"displayName":"Display Name", "email":"something@somewhere.com"}}
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = {JSON_API_CONTENT_HEADER})
    public @ResponseBody APIResponse register(@RequestBody UserDTO userDTO,
                                              HttpServletRequest request) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        Validate.isTrue(StringUtils.isNotBlank(userDTO.getEmail()), "Email is blank");
        Validate.isTrue(StringUtils.isNotBlank(userDTO.getEncryptedPassword()), "Encrypted password is blank");
        Validate.isTrue(StringUtils.isNotBlank(userDTO.getDisplayName()), "Display name is blank");
        String password = decryptPassword(userDTO);

        LOG.info("Looking for user by email: "+userDTO.getEmail());
        if(userService.isEmailExists(userDTO.getEmail())) {
            return APIResponse.toErrorResponse("Email is taken");
        }

        LOG.info("Creating user: "+userDTO.getEmail());
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setDisplayName(userDTO.getDisplayName());
        user.setPassword(password);
        user.setEnabled(true);
        user.setRole(User.Role.USER);
        userService.registerUser(user, request);

        HashMap<String, Object> authResp = new HashMap<>();
        createAuthResponse(user, authResp);

        return APIResponse.toOkResponse(authResp);
    }

    private void createAuthResponse(User user, HashMap<String, Object> authResp) {
        String token = Jwts.builder().setSubject(user.getEmail())
                .claim("role", user.getRole().name()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, JWTTokenAuthFilter.JWT_KEY).compact();
        authResp.put("token", token);
        authResp.put("user", user);
    }

    /**
     * Logs out a user by deleting the session
     *
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public @ResponseBody APIResponse logout(@RequestBody UserDTO userDTO) {
        return APIResponse.toOkResponse("success");
    }

    private String decryptPassword(UserDTO userDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        String passPhrase = "biZndDtCMkdeP8K0V15OKMKnSi85";
        String salt = userDTO.getSalt();
        String iv = userDTO.getIv();
        int iterationCount = userDTO.getIterations();
        int keySize = userDTO.getKeySize();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), hex(salt), iterationCount, keySize);
        SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(hex(iv)));
        byte[] decrypted = cipher.doFinal(base64(userDTO.getEncryptedPassword()));

        return new String(decrypted, "UTF-8");
    }

    private String base64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    private byte[] base64(String str) {
        return Base64.decodeBase64(str);
    }

    private String hex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    private byte[] hex(String str) {
        try {
            return Hex.decodeHex(str.toCharArray());
        }
        catch (DecoderException e) {
            throw new IllegalStateException(e);
        }
    }
}
