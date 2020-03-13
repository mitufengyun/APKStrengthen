package com.example.apkstrengthen;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * time: 2020/3/7 20:58
 * author: xpf
 * describe: RSA算法API调用
 */
public class RSA {
    public static String ALGORITHM = "RSA";
    //指定key的位置
    public static int KEYSIZE = 1024;//最大值65536
    //指定公钥存放的文件
    public static String PUBLIC_KEY_FILE = "public_key.dat";
    //指定私钥存放的文件
    public static String PRIVATE_KEY_FILE = "private_key.dat";

    /**
     * 生成并存放密钥对
     * @throws Exception
     */
    public static void generateKeyPair() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        //需要一个KeyPairGenerator来生成密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        //初始化KeyPairGenerator
        keyPairGenerator.initialize(KEYSIZE, secureRandom);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取公钥
        PublicKey publicKey = keyPair.getPublic();
        //获取私钥
        PrivateKey privateKey = keyPair.getPrivate();

        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));

        //将公钥写入指定的PUBLIC_KEY_FILE文件保存
        objectOutputStream1.writeObject(publicKey);
        //将私钥写入指定的PRIVATE_KEY_FILE文件保存
        objectOutputStream2.writeObject(privateKey);
        //关闭流
        objectOutputStream2.close();
        objectOutputStream1.close();

    }

    /**
     * 加密
     * @param source
     * @return
     * @throws Exception
     */
    public static String encrypt(String source) throws Exception{
        generateKeyPair();
        //读出公钥
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
        Key key = (Key) objectInputStream.readObject();
        objectInputStream.close();
        //开始使用
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] b = source.getBytes();
        byte[] b1 = cipher.doFinal(b);
        //转一下base64，需要导入架包
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(b1);
    }

    /**
     * 解密
     * @param source
     * @return
     * @throws Exception
     */
    public static String decrypt(String source) throws Exception{

        //取出私钥
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
        Key key = (Key) objectInputStream.readObject();
        objectInputStream.close();
        //开始使用
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,key);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(source);
        byte[] b1 = cipher.doFinal(b);
        //转一下base64

        return new String(b1);
    }

    @Test
    public void test() throws Exception{
        //客户端加密
        String content = "xpf";
        String password = encrypt(content);
        System.out.println("密文：" + password);
        /*密文：BeibcXYUBdnBdWVO0lshbizRm6wz/loQY+bH9pPq2V7P41/5IcEivF0o0GombI1XcvalHkBupZ7m
        v7Y994b4yI+OYPNkH8Nx3VGA7JF6j27R3iypEZVx2LPL9HiJIXFhCrFmlfnuzOIRZQluLuNigRKo
        vcCsCm/r3N42vf+J/9E=*/

        //服务端解密
        String target = decrypt(password);
        System.out.println("明文：" + target);
        //明文：xpf
    }
}
