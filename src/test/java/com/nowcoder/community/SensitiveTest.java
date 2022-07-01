package com.nowcoder.community;

import com.nowcoder.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public  void testSensitiveFilter(){
        String text = "拒绝+嫖)娼，赌..博，吸^毒，严禁违法$$开%%票@@！！！";
        text = sensitiveFilter.filter(text);
        System.out.println("text = " + text);

    }
}
