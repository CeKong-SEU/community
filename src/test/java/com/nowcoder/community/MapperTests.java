package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.Date;
import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);


        User zzz = userMapper.selectByName("zzz");
        System.out.println(zzz);

        User user1 = userMapper.selectByEmail("nowcoder124@sina.com");
        System.out.println(user1);
    }

    @Test
    public void testSelectDiscussPost() {
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10,0);
        list.forEach(System.out::println);
//        list.forEach(l->System.out.println(l));
//        for (DiscussPost discussPost : list) {
//            System.out.println(discussPost);
//        }
        int rows = discussPostMapper.selectDiscussPostRows(0);
        System.out.println("rows = " + rows);

    }

    @Test
    public void testLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));//有效时间十分钟
        loginTicket.setStatus(0);
        loginTicket.setTicket("ABC");

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket() {
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("ABC");
        System.out.println("loginTicket = " + loginTicket);
        loginTicketMapper.updateStatus("ABC", 1);
        loginTicket = loginTicketMapper.selectByTicket("ABC");
        System.out.println("loginTicket = " + loginTicket);
    }

    @Test
    public void testSelectLetters() {
        List<Message> list = messageMapper.selectConversations(111, 0, 20);
        for (Message message : list) {
            System.out.println(message);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);


        list = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : list) {
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);
    }


}


