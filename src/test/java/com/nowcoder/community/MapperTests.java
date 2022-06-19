package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);


        User zzz = userMapper.selectByName("zzz");
        System.out.println(zzz);

        User user1 = userMapper.selectByEmail("nowcoder124@sina.com");
        System.out.println(user1);
    }

    @Test
    public void testSelectDiscussPost(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10);
        list.forEach(System.out::println);
//        list.forEach(l->System.out.println(l));
//        for (DiscussPost discussPost : list) {
//            System.out.println(discussPost);
//        }
        int rows = discussPostMapper.selectDiscussPostRows(0);
        System.out.println("rows = " + rows);

    }

}


