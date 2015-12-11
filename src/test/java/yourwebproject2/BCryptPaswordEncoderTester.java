package yourwebproject2;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

/**
 * @author: kameshr
 */
public class BCryptPaswordEncoderTester {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) throws InterruptedException {
        String encoded = passwordEncoder.encode("Test1234");
        Thread.sleep(5L * 1000L);
        Assert.isTrue(passwordEncoder.matches("Test1234", encoded));
        Assert.isTrue(!passwordEncoder.matches("Test123ss", encoded));
        Assert.isTrue(passwordEncoder.matches("Test1234", encoded));

        System.out.println("Test 1: "+passwordEncoder.matches("Test1234", encoded));
        System.out.println("Test 2: "+passwordEncoder.matches("Test123ss", encoded));
        System.out.println("Test 3: "+passwordEncoder.matches("Test1234", encoded));
    }
}
