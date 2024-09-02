package com.whai.blog;

import com.alibaba.fastjson.JSONObject;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.constant.RabbitMQConstants;
import com.whai.blog.mapper.MessageMapper;
import com.whai.blog.mapper.SysUserOnlineMapper;
import com.whai.blog.model.BlogComment;
import com.whai.blog.service.IMinioService;
import com.whai.blog.service.ISysUserService;
import com.whai.blog.service.impl.*;
import com.whai.blog.utils.schedule.RefreshMemoryJob;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@Slf4j
class BlogApplicationTests {

    @Autowired
    private FileMainServiceImpl service;
    @Autowired
    private ImgMainServiceImpl imgMainService;
    @Autowired
    private HomeServiceImpl homeService;
    @Autowired
    private BlogMainServiceImpl blogMainService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysUserOnlineServiceImpl onlineService;

    @Autowired
    private HdfsServiceImpl hdfsService;

    @Autowired
    private SysUserOnlineMapper sysUserOnlineMapper;


    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @Resource
    private MinioClient minioClient;
    @Resource
    private IMinioService iMinioService;

    private static final Object lock = new Object();
    private static int count = 0;

    @Test
    public void ccontextLoads()
    {
        iMinioService.uploadFile(
                "D:\\Downloads\\658e5480-bbe3-43ca-aba9-c6838e00f8f9.png",
                null);
    }
    @Test
    public void m() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (count < 10) {
                    System.out.println("Thread 1: " + count++);
                    lock.notify();
                }
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                while (count < 20) {
                    System.out.println("Thread 2: " + count++);
                    lock.notify();
                }
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }



    @Test
    public void miniotest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Iterable<Result<Item>> results =
                // .recursive(true) 为imgs/bVbiOyR   false 为imgs/
                minioClient.listObjects(ListObjectsArgs.builder().bucket("picgo").recursive(true).build());




        for (Result<Item> result : results) {
            Item item = result.get();
            if (item.isDir()) {
                System.out.println("directory " + item.objectName());
                for (Result<Item> picgo : minioClient.listObjects(ListObjectsArgs.builder().bucket("picgo").prefix(item.objectName()).recursive(true).build())) {
                    System.out.println(picgo.get().objectName());
                }
            } else {
                System.out.println("file " + item.objectName());
                String picgo = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket("picgo").method(io.minio.http.Method.GET).object(item.objectName()).build());
                System.out.println(picgo);

            }
            // 获取下载地址
        }
//        iMinioService.uploadFile("D:\\project\\blogVue\\blog\\blog-vue.sql", null, "obj1");
//        System.out.println(minioClient.bucketExists(BucketExistsArgs.builder().bucket("data").build()));
    }
    @Test
    public void testTransaction() throws FileNotFoundException, MessagingException, UnsupportedEncodingException {
        mailService.sendSimpleMail("whaifree@163.com","测试邮件","测试邮件内容",null);
        mailService.sendHtmlMail("whaifree@163.com","测试邮件","<h1>safdgdfag</h1>",null);
        mailService.sendAttachmentsMail("whaifree@163.com", "测试邮件", "<h1>safdgdfag</h1>", "D:\\project\\blogVue\\blog\\file\\moban7133.rar");
    }

    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Test
    void contextLoads3() throws Exception {

    }

    void test111(ExecutorType type) {

        long l = System.currentTimeMillis();
        sqlSessionFactory.getConfiguration().setDefaultExecutorType(type);
        // 获取 SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            System.out.println(sqlSessionFactory.getConfiguration().getDefaultExecutorType().name());
            List<Object> getBlogPages = sqlSession.selectList("getBlogPages");
            getBlogPages.forEach(System.out::println);
        }
        long l1 = System.currentTimeMillis();
        log.info(String.valueOf(l1 - l));

    }


    @Test
    void contextLoads() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.whai.blog.utils");
        RefreshMemoryJob bean = (RefreshMemoryJob) applicationContext.getBean("&refreshMemoryJob");
        //  Bean named 'refreshMemoryJob' is expected to be of type 'org.springframework.beans.factory.FactoryBean' but was actually of type 'com.whai.blog.utils.schedule.RefreshMemoryJob'
        bean.execute();
    }

    @Test
    void contextLoads1() throws Exception {
        BlogComment comment = new BlogComment();
        comment.setBlogCommentId("8317e0eaacc96010dcd73868f5434d22sad");
        rabbitTemplate.convertAndSend(RabbitMQConstants.COMMENT_EXCHANGE, RabbitMQConstants.COMMENT_ROUTER, JSONObject.toJSONString(comment), message -> {
            message.getMessageProperties().setDelay(5000);
            return message;
        });

    }

    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessageServiceImpl messageService;
    @Autowired
    SignInServiceImpl signInService;
    @Test
    void contextLoads22() throws Exception {
//        Integer integer = messageMapper.likeMessage("25b2fa56284d9a31bece5b9093b16966", 1);
//
        for (int i = 0; i < 3; i++) {
            signInService.signIn();
        }

        Collection<String> keys = redisCache.keys(CacheConstants.USER_SIGN_KEY + "*");
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    void contextLoads222() throws Exception {
        Method setBlogCommentId = new BlogComment().getClass().getMethod("setBlogCommentId", String.class);
        BlogComment comment = new BlogComment();
        setBlogCommentId.invoke(comment, "8317e0eaacc96010dcd73868f5434d22sadsdfsdf");
        System.out.println(comment);
    }

    @Test
    void cc() {
        LocalDateTime now = LocalDateTime.now();
        int dayOfMonth = now.getDayOfMonth();
        List<Long> list = redisCache.bitField(
                "whai66",
                BitFieldSubCommands.create().get(
                        BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );

        list.forEach(System.out::println);

        //56789012
        //10100000
        // 将Long转为2进制char列表 1010 8
        Set<Integer> signInDaySet = new HashSet<>();
        char[] chars = Long.toBinaryString(list.get(0)).toCharArray();


        for (int i = chars.length - 1; i >= 0; i--) {
            //如果char[i}内的值==1
            if (Character.getNumericValue(chars[i]) == 1) {
                signInDaySet.add(dayOfMonth - chars.length + i + 1);
            }
        }
        signInDaySet.forEach(System.out::println);

//        List whai = redisCache.getRedisTemplate().opsForValue().bitField(
//
////                "whai", BitFieldSubCommands //在位偏移量0处获取4位无符号整数的值.create().get(
//                        //在位偏移量0处获取30位无符号整数的值
//                        BitFieldSubCommands.BitFieldType.unsigned(30)).valueAt(0));

//        System.out.println(whai);




    }

    public static List<Integer> getBitmapOffsets(String bitmap, int key) {
        List<Integer> offsets = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < bitmap.length(); i++) {
            if (bitmap.charAt(i) == '1') {
                offsets.add(index);
            }
            if (bitmap.charAt(i) == '0') {
                index++;
            }
        }
        return offsets;
    }







}
