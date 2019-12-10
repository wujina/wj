package com.gm.wj.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import com.gm.wj.base.Condition;
import com.gm.wj.dao.CityDao;
import com.gm.wj.dao.IdentityInformationDao;
import com.gm.wj.dao.UserInfoDao;
import com.gm.wj.pojo.IdentityInformation;
import com.gm.wj.pojo.TextPojo;
import com.gm.wj.pojo.User;
import com.gm.wj.result.Result;
import com.gm.wj.result.ResultFactory;
import com.gm.wj.service.CityService;
import com.gm.wj.service.RoominfoService;
import com.gm.wj.service.UserService;
import com.gm.wj.utillity.FtpJSch;


import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




@Controller
public class LoginController {


    @Autowired
    private  IdentityInformationDao identityInformationDao;

    private static LoginController loginController;

    @Autowired
    UserService userService;

    @Autowired
    RoominfoService roominfoService;

    @Autowired
    CityService cityService;

    @PostConstruct
    public void init() {
        loginController = this;
        loginController.identityInformationDao = this.identityInformationDao;
    }

    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException, FileNotFoundException {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User user = userService.get(username, requestUser.getPassword());
        if(user!=null){
            session.setAttribute("user", user);
            return ResultFactory.buildSuccessResult(user);
        }
        return ResultFactory.buildFailResult("账号或者密码错误");
    }


    @RequestMapping(value="/uploadPic",method=RequestMethod.POST)
    @ResponseBody
    public String saveHeaderPic(@RequestParam("file") MultipartFile file) throws Exception {
        // 文件保存路径
        String filePath = ""; //映射的地址

        String filename = file.getOriginalFilename();//获取file图片名称

        FtpJSch ftpJSch=new FtpJSch();
        ftpJSch.getConnect();
        ftpJSch.upload(file,"/var/www/images/904");
        ftpJSch.deletedir("/var/www/images/904/");
        //ftpJSch.upload(file,"/var/www/images/904/");
        ftpJSch.closeFptConnect();

        return "";
    }

    public static String httpURLConectionGET(String address) {
        //"http://restapi.amap.com/v3/geocode/geo?address=上海市东方明珠&output=JSON&key=xxxxxxxxx";
        String geturl = "http://restapi.amap.com/v3/geocode/geo?key=bb761abbc2a70d8449cccd9fb82825ff&address="+address;
        String location = "";
        try {
            URL url = new URL(geturl);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            System.out.println("Get=="+sb.toString());
            JSONObject a = JSON.parseObject(sb.toString());
            System.out.println(a.get("geocodes"));
            JSONArray sddressArr = JSON.parseArray(a.get("geocodes").toString());
            System.out.println(sddressArr.get(0));
            JSONObject c = JSON.parseObject(sddressArr.get(0).toString());
            location = c.get("location").toString();
            System.out.println(location);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
        return location;
    }

    @PostMapping(value = "/api/dir")
    @ResponseBody
    public  String run_dir() throws IOException, ParseException {

      IdentityInformation da =new IdentityInformation();

      Condition<IdentityInformation>condition=new Condition(IdentityInformation.class);


      condition.where();


      long sda=identityInformationDao.count(condition);




//        LoginController loginController=new LoginController();
//
//        loginController.myPrint("http://51porn.uk");

//        File srcFile = new File("F:\\BaiduNetdiskDownload\\黑名单-手持"); //这里填源文件夹路径
//
//        copyFolder(srcFile);

        return null;
    }


    public  static void copyFolder(File srcFile)
            throws IOException, ParseException {

// 判断该File是文件夹还是文件
        File[] ff=srcFile.listFiles();
        for(File f:ff){

//如果是文件夹，则递归调用
            if(f.isDirectory()){
                copyFolder(f);
                System.out.println(f.listFiles().length+"     "+f.getPath());

            }else{ ///11111
                if(f.getName().equals("1.html")){
                   // run_list(f.getPath());
                   run(f.getPath());
                }
            }
        }
    }



    public  static String run_list(String url) throws FileNotFoundException, UnsupportedEncodingException {

        StringBuilder sb = new StringBuilder();

        try {
            String encoding = "UTF-8";
//读取本地的html文件方法
            File file = new File(url);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                // 进行body元素提取
                Document document = null;
                document = Jsoup.parse(file, encoding);
                Element e = document.body();
                Elements e1 = e.getElementsByTag("span");
                int count=0;
                for (int i=10;i<e1.size();i=i+8){
                        String name=e1.get(i).toString();
                        String id_card=e1.get(i+1).toString();
                        String phone=e1.get(i+2).toString();
                    IdentityInformation user=new IdentityInformation();
                    user.setNAME(carve(name));
                    user.setID_CARD_NUMBER(carve(id_card));
                    user.setPHONE(carve(phone));
                    IdentityInformation temp=loginController.identityInformationDao.select_id_card_num(carve(id_card));
                    if(temp!=null){
                        user.setId((long)temp.getID());
                        System.out.println(user.getNAME()+"----------"+user.getPHONE()+"---------------"+user.getID_CARD_NUMBER()+"------"+url+"--------"+ count);
                        loginController.identityInformationDao.update(user);
                        count++;

                    }
                    else {
                        count++;
                        System.out.println(user.getNAME()+"----------"+user.getPHONE()+"---------------"+user.getID_CARD_NUMBER()+"------"+url+"--------"+count);
                        loginController.identityInformationDao.saveOne(user);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static String run(String url) throws FileNotFoundException, UnsupportedEncodingException {

        StringBuilder sb = new StringBuilder();

        try {
            String encoding = "UTF-8";
//读取本地的html文件方法
            File file = new File(url);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                // 进行body元素提取
                Document document = null;
                document = Jsoup.parse(file, encoding);
                Element e = document.body();
                Elements e1 = e.getElementsByTag("td");
                Elements e2 =e.getElementsByClass("bordered");
                Element tr = e2.get(1);
                Elements td=tr.getElementsByTag("td");
                for (int i=3;i<td.size();i=i+3){
                    String name=td.get(i).toString();
                    String sex=td.get(i+1).toString();
                    String phone=td.get(i+2).toString();
                    IdentityInformation user=new IdentityInformation();
                    user.setNAME(carve(name));
                    user.setSEX(carve(sex));
                    user.setPHONE(carve(phone));
                    user.setNATION(carve(e1.get(1).toString()));
                    IdentityInformation temp=loginController.identityInformationDao.select_phone(carve(phone));
                    if(temp!=null){
                        user.setId((long)temp.getID());
                        System.out.println(user.getNAME()+"----------"+user.getPHONE()+"------"+url+"--------");
                        loginController.identityInformationDao.update(user);
                    }
                    else {
                        System.out.println(user.getNAME()+"----------"+user.getPHONE()+"------"+url+"--------");
                        loginController.identityInformationDao.saveOne(user);
                    }
                }
                String name=e1.get(1).toString();
                String id_card_num=e1.get(3).toString();
                String iphone=e1.get(5).toString();
                String wechat=e1.get(7).toString();
                String alipay=e1.get(9).toString();
                String address=e1.get(11).toString();
                IdentityInformation user=new IdentityInformation();
                user.setNAME(carve(name));
                user.setID_CARD_NUMBER(carve(id_card_num));
                user.setPHONE(carve(iphone));
                user.setWECHAT(carve(wechat));
                user.setALIPAY(carve(alipay));
                user.setADDRESS(carve(address));
                IdentityInformation temp=loginController.identityInformationDao.select_id_card_num(carve(id_card_num));
                if(temp!=null){
                    user.setId((long)temp.getID());
                    System.out.println(user.getNAME()+"----------"+user.getPHONE()+"---------"+user.getID_CARD_NUMBER()+"------"+url+"--------");
                    loginController.identityInformationDao.update(user);
                }
                else {
                    System.out.println(user.getNAME()+"----------"+user.getPHONE()+"----------"+user.getID_CARD_NUMBER()+"------"+url+"--------");
                    loginController.identityInformationDao.saveOne(user);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String carve(String string){
        String[]str=string.split("<|>");
        return str[2];
    }


    public  void myPrint(String baseUrl) {
        Map<String, Boolean> oldMap = new LinkedHashMap<String, Boolean>(); // 存储链接-是否被遍历
        // 键值对
        String oldLinkHost = "";  //host

        Pattern p = Pattern.compile("(https?://)?[^/\\s]*"); //比如：http://www.zifangsky.cn
        Matcher m = p.matcher(baseUrl);
        if (m.find()) {
            oldLinkHost = m.group();
        }

        oldMap.put(baseUrl, false);
        oldMap = crawlLinks(oldLinkHost, oldMap);
        for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
            System.out.println("链接：" + mapping.getKey());

        }

    }

    /**
     * 抓取一个网站所有可以抓取的网页链接，在思路上使用了广度优先算法
     * 对未遍历过的新链接不断发起GET请求，一直到遍历完整个集合都没能发现新的链接
     * 则表示不能发现新的链接了，任务结束
     *
     * @param oldLinkHost  域名，如：http://www.zifangsky.cn
     * @param oldMap  待遍历的链接集合
     *
     * @return 返回所有抓取到的链接集合
     * */
    private Map<String, Boolean> crawlLinks(String oldLinkHost,
                                            Map<String, Boolean> oldMap) {
        Map<String, Boolean> newMap = new LinkedHashMap<String, Boolean>();
        String oldLink = "";

        for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
            System.out.println("link:" + mapping.getKey() + "--------check:"
                    + mapping.getValue());
            // 如果没有被遍历过
            if (!mapping.getValue()) {
                oldLink = mapping.getKey();
                // 发起GET请求
                try {
                    URL url = new URL(oldLink);
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(2000);
                    connection.setReadTimeout(2000);

                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(inputStream, "UTF-8"));
                        String line = "";
                        Pattern pattern = Pattern
                                .compile("<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>");
                        Matcher matcher = null;
                        while ((line = reader.readLine()) != null) {
                            matcher = pattern.matcher(line);
                            if (matcher.find()) {
                                String newLink = matcher.group(1).trim(); // 链接
                                // String title = matcher.group(3).trim(); //标题
                                // 判断获取到的链接是否以http开头
                                if (!newLink.startsWith("http")) {
                                    if (newLink.startsWith("/"))
                                        newLink = oldLinkHost + newLink;
                                    else
                                        newLink = oldLinkHost + "/" + newLink;
                                }
                                //去除链接末尾的 /
                                if(newLink.endsWith("/"))
                                    newLink = newLink.substring(0, newLink.length() - 1);
                                //去重，并且丢弃其他网站的链接
                                if (!oldMap.containsKey(newLink)
                                        && !newMap.containsKey(newLink)
                                        && newLink.startsWith(oldLinkHost)) {
                                    // System.out.println("temp2: " + newLink);
                                    newMap.put(newLink, false);
                                }
                            }
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                oldMap.replace(oldLink, false, true);
            }
        }
        //有新链接，继续遍历
        if (!newMap.isEmpty()) {
            oldMap.putAll(newMap);
            oldMap.putAll(crawlLinks(oldLinkHost, oldMap));  //由于Map的特性，不会导致出现重复的键值对
        }
        return oldMap;
    }






}
