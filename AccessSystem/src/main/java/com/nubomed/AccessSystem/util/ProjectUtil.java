package com.nubomed.AccessSystem.util;

import com.nubomed.AccessSystem.entity.MsgDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.x509.CertAttrSet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class ProjectUtil {
    public static int CmdToNum(String cmd){
        switch (cmd) {
            case "setPeron":
                return 1;
            case "removePerson":
                return 2;
            case "open":
                return 3;
            case "reboot":
                return 4;
            default:
                return -1;
        }
    }

    public static MsgDetail ToMsgDetail(String msg) {
        String[] s = msg.split("&");
        MsgDetail msgDetail = new MsgDetail();
        for (int i = 0; i < s.length; i++) {
            String[] detail = s[i].split("=");
            String method = "";
            switch (detail[0]) {
                case "key":
                    msgDetail.setMsgKey(detail[1]);
                    break;
                case "id":
                    msgDetail.setMsgid(detail[1]);
                    break;
                case "name":
                    msgDetail.setMsgName(detail[1]);
                    break;
                case "IC_NO":
                    msgDetail.setIcNo(detail[1]);
                    break;
                case "ID_NO":
                    msgDetail.setIdNo(detail[1]);
                    break;
                case "passCount":
                    msgDetail.setPassCount(Double.parseDouble(detail[1]));
                    break;
                case "startTs":
                    msgDetail.setStartTs(Double.parseDouble(detail[1]));
                    break;
                case "endTs":
                    msgDetail.setEndTs(Double.parseDouble(detail[1]));
                    break;
                case "visitor":
                    msgDetail.setVisitor(detail[1] == "false" ? 0 : 1);
                    break;
                case "photo":
                    msgDetail.setPhoto(transformBase64(detail[1]));
                    break;
            }
        }
        return msgDetail;
    }


    // blob转换成为Base64
    public static String convertBlobToBase64String(byte[] blob) {
        String result = "";
        if (null != blob) {
            try {
                InputStream msgContent = new ByteArrayInputStream(blob);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int n = 0;
                while (-1 != (n = msgContent.read(buffer))) {
                    output.write(buffer, 0, n);
                }
                result = new BASE64Encoder().encode(output.toByteArray());
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } else {
            return null;
        }
    }

    // Base64转换成blob
    public static byte[] transformBase64(String str) {
        BASE64Decoder decode = new BASE64Decoder();
        byte[] b = null;
        try {
            b = decode.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }


    public static class ImageToBase64 {
        private static String strNetImageToBase64;

        /**
         * 网络图片转换Base64的方法
         *
         * @param netImagePath
         */
        private static String NetImageToBase64(String netImagePath) {
            final ByteArrayOutputStream data = new ByteArrayOutputStream();
            try {
                // 创建URL
                URL url = new URL(netImagePath);
                final byte[] by = new byte[1024];
                // 创建链接
                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream is = conn.getInputStream();
                            // 将内容读取内存中
                            int len = -1;
                            while ((len = is.read(by)) != -1) {
                                data.write(by, 0, len);
                            }
                            // 对字节数组Base64编码
                            BASE64Encoder encoder = new BASE64Encoder();
                            strNetImageToBase64 = encoder.encode(data.toByteArray());
                            // 关闭流
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return strNetImageToBase64;
        }

        /**
         * 本地图片转换Base64的方法
         *
         * @param imgPath
         */

        private String ImageToBase64(String imgPath) {
            byte[] data = null;
            // 读取图片字节数组
            try {
                InputStream in = new FileInputStream(imgPath);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            // 返回Base64编码过的字节数组字符串
            return encoder.encode(Objects.requireNonNull(data));
        }

    }


}
