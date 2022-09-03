package com.wjl.citademo.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.SM2;
import lombok.experimental.UtilityClass;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;

@UtilityClass
public class Sm2Utils {

    public static void main(String[] args) {
        //创建sm2 对象
        SM2 sm2 = SmUtil.sm2();
        //这里会自动生成对应的随机秘钥对 , 注意！ 这里一定要强转，才能得到对应有效的秘钥信息
        byte[] privateKey = BCUtil.encodeECPrivateKey(sm2.getPrivateKey());
        //这里公钥不压缩  公钥的第一个字节用于表示是否压缩  可以不要
        byte[] publicKey = ((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false);
        //这里得到的 压缩后的公钥   ((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(true);
        // byte[] publicKeyEc = BCUtil.encodeECPublicKey(sm2.getPublicKey());
        //打印当前的公私秘钥
        System.out.println("私钥: " + HexUtil.encodeHexStr(privateKey));
        System.out.println("公钥: " + HexUtil.encodeHexStr(publicKey));
    }

}
