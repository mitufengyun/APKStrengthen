package com.example.apkstrengthen;

import org.junit.Test;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * time: 2020/3/7 20:01
 * author: xpf
 * describe: AES算法,加密、解密API调用
 */
public class AES {
    public static String ALGORITHM = "AES";

    /**
     * 加密
     * @param content
     * @param password
     * @return
     */
    public static byte[] encrypt(String content, String password) throws Exception {
            //创建AES的key生产者
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            //通过用户密码作为随机数初始化
            keyGenerator.init(128, new SecureRandom(password.getBytes()));
            //得到一个密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //对密钥进行基本的编码
            byte[] enCodedFormat = secretKey.getEncoded();
            //转换成AES专用的密钥
            SecretKeySpec key = new SecretKeySpec(enCodedFormat, ALGORITHM);
            //创建一个解码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            byte[] byteContent = content.getBytes();
            //开始加密了
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            return result;
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] content, String password) throws Exception {

            //创建AES的key生产者
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            //通过用户密码作为随机数初始化
            keyGenerator.init(128, new SecureRandom(password.getBytes()));
            //得到一个密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //对密钥进行基本的编码
            byte[] enCodedFormat = secretKey.getEncoded();
            //转换成AES专用的密钥
            SecretKeySpec key = new SecretKeySpec(enCodedFormat, ALGORITHM);
            //创建一个解码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);


            //解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;


    }

    @Test
    public void test() throws Exception{
        String content = "xpf";
        String password = "123";

        byte[] encryptByte = encrypt(content, password);
        System.out.println("加密的结果：" + new String(encryptByte));
        //加密的结果：�M�xeO_Մ��ja��\

        byte[] decryptByte = decrypt(encryptByte, password);
        System.out.println("解密的结果：" + new String(decryptByte));
        //解密的结果：xpf

    }
}
