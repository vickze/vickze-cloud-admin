package io.vickze.auth;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptTest {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        String s = BCrypt.hashpw("test", BCrypt.gensalt());
        System.out.println(s);
        System.out.println(System.currentTimeMillis() - start + "ms");

        start = System.currentTimeMillis();
        System.out.println(BCrypt.checkpw(s, s));
        System.out.println(System.currentTimeMillis() - start + "ms");


        start = System.currentTimeMillis();
        System.out.println(BCrypt.checkpw("123", s));
        System.out.println(System.currentTimeMillis() - start + "ms");

        start = System.currentTimeMillis();
        System.out.println(BCrypt.checkpw("test ", s));
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}
