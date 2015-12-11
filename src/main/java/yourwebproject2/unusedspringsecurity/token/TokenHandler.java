package yourwebproject2.unusedspringsecurity.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.User;

/**
 * @author: kameshr
 */
public final class TokenHandler implements InitializingBean {
    private String secret;
    private TokenUserService tokenUserService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Validate.isTrue(StringUtils.isNotBlank(secret), "secret is blank");
        Validate.notNull(tokenUserService, "tokenUserService is null");
    }

    public User parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return tokenUserService.loadUserByUsername(username);
    }

    public String createTokenForUser(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public TokenUserService getTokenUserService() {
        return tokenUserService;
    }

    public void setTokenUserService(TokenUserService tokenUserService) {
        this.tokenUserService = tokenUserService;
    }
}