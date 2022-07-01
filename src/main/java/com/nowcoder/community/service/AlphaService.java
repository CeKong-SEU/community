package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

@Service
//@Scope("prototype") //多例设置 不常用
public class AlphaService {

    private static final Logger logger = LoggerFactory.getLogger(AlphaService.class);

    @Autowired
    private AlphaDao alphaDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;


    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化AlphaService");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("销毁AlphaService");
    }

    public String find(){
       return alphaDao.select();
    }

    /**声明式事务
     * REQUIRED         支持当前事务（外部事务），如不存在则创建新事务
     * REQUIRED_NEW     创建一个新事务，并且暂停当前事务（外部事务）
     * NESTED           如果当前存在事务（外部事务），则嵌套在该事务中执行（独立的提交和回滚），
     *                  否则就会和REQUIRED一样
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Object save1(){
        //新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("123"+user.getSalt()));
        user.setEmail("alpah@123.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //新增帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("新人报到！");
        post.setContent("大家好！");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        Integer.valueOf("abc");

        return "ok";
    }

    /**
     * 编程式事务
     * @return
     */
    public  Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {


                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID().substring(0,5));
                user.setPassword(CommunityUtil.md5("123"+user.getSalt()));
                user.setEmail("beta@123.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/999t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);

                //新增帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("新到！");
                post.setContent("好！");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

                Integer.valueOf("abc");

                return "ok";
            }
        });
    }
    //让该方法在多线程环境下被异步调用
    @Async
    public void execute1(){
        logger.debug("测试 execute 1");
    }

//    @Scheduled(initialDelay = 10000, fixedRate = 1000)
    public void execute2() {
        logger.debug("execute2");
    }
}
