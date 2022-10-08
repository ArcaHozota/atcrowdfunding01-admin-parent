package com.atdaiwa.crowd.test;

import org.junit.Test;

import com.atdaiwa.crowd.util.CrowdUtil;

public class StringTest {

    @Test
    //MD5明文加密測試；
    public void testMD5() {
        String source = "123456";
        String encoded = CrowdUtil.toMD5(source);
        System.out.println(encoded);
    }
}
