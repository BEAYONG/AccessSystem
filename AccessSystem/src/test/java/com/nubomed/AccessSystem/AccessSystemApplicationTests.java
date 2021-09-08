package com.nubomed.AccessSystem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nubomed.AccessSystem.entity.MsgDetail;
import com.nubomed.AccessSystem.mapper.InfoMapper;
import com.nubomed.AccessSystem.mapper.MsgDetailMapper;
import com.nubomed.AccessSystem.util.ProjectUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SpringBootTest
class AccessSystemApplicationTests {

    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private MsgDetailMapper msgDetailMapper;
    @Test
    void contextLoads() {
    }

    @Test
    public void Test1(){
        String s = "key=abc&" +
                "id=007d01c8-5ed8-445d-969f-1d8a32a8e793&name=张三 " +
                "&IC_NO=3869160839&ID_NO=1234567890ABCDEF" +
                "&passCount=10000&startTs=-1&endTs=-1&visitor=false" +
                "&photo=/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICA";
        MsgDetail user = ProjectUtil.ToMsgDetail(s);
//        user.setPhoto(ProjectUtil.transformBase64(ImageToBase64("src/test/java/com/nubomed/AccessSystem/pic/pic.png")));
        QueryWrapper<MsgDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("id","1435545333421977601");
        byte[] photo = msgDetailMapper.selectOne(wrapper).getPhoto();
        user.setPhoto(ProjectUtil.transformBase64(ProjectUtil.convertBlobToBase64String(photo)));
//        System.out.println(user);
        msgDetailMapper.insert(user);
    }

    @Test
    public void Test2(){
        QueryWrapper<MsgDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("id","1435545333421977601");
        byte[] photo = msgDetailMapper.selectOne(wrapper).getPhoto();

        System.out.println(ProjectUtil.convertBlobToBase64String(photo));
    }
    @Test
    public void Test3(){
        System.out.println(ProjectUtil.convertBlobToBase64String(ProjectUtil.transformBase64("/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICA")));
    }

    private static String ImageToBase64(String imgPath) {
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
//        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));
        return encoder.encode(Objects.requireNonNull(data));
    }


    @Test
    public void Test4(){
        String image = ImageToBase64("src/test/java/com/nubomed/AccessSystem/pic/pic.png");
        System.out.println(ProjectUtil.transformBase64(image));
    }

}
