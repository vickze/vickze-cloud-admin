package io.vickze.auth;

import org.junit.Test;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-23 16:52
 */
public class UriTest {

    @Test
    public void test() {
        String uri = "/menu/tree/";
        uri = uri.replaceAll("[a-zA-Z\\\\d\\/]+", "");
        System.out.println(uri);
    }
}
