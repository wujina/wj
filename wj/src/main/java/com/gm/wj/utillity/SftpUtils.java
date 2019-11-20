package com.gm.wj.utillity;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class SftpUtils {
    private static Logger logger = LoggerFactory.getLogger(SftpUtils.class);

    private static String encoding = "UTF-8";

    private ChannelSftp sftp;

    private Session session;

    /** SFTP 登录用户名*/
    private String username;

    /** SFTP 登录密码*/
    private String password;

    /** SFTP 服务器地址IP地址*/
    private String host;

    /** SFTP 端口*/
    private int port;


    public SftpUtils(String username, String password , String host, int port)
    {
        this.port = port;

        this.host = host;

        this.password = password;

        this.username = username;
    }

    /**
     *
     * 连接Ftp服务器
     *
     * @return
     */
    public void connectSFtp()
    {
        try
        {
            JSch jsch = new JSch();

            Session session = jsch.getSession(username, host, port);

            Properties config = new Properties();

            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);

            session.setPassword(password);

            session.connect();

            Channel channel = session.openChannel("sftp");

            channel.connect();

            sftp = (ChannelSftp) channel;
        }
        catch (JSchException e)
        {
            logger.error("SFTP 服务器连接失败！！！ ip:{},port:{},username:{},password:{}",new String[]{host,port+"",username,password});

            logger.error("",e);
        }

    }

    /**
     * 关闭Ftp连接
     *
     * @throws IOException
     */
    public void closeFptConnect()
    {
        if (sftp != null)
        {
            if (sftp.isConnected())
            {
                sftp.disconnect();

                logger.info("sftp 已经关闭");
            }
        }
        if (session != null)
        {
            if (session.isConnected())
            {
                session.disconnect();

                logger.info("sshSession 已经关闭");
            }
        }
    }
    /**
     * 将文件上传 spring
     */
    public void uploadMultipartFile(MultipartFile file, String newFileName, String uploadPath)
            throws SftpException, IOException
    {
        InputStream inputStream = null;

        inputStream = file.getInputStream();

        createDir(uploadPath);

        sftp.put(inputStream, newFileName);

        inputStream.close();
    }


    /**
     * 将本地文件上传
     */
    public  void uploadFile(File file, String newFileName, String uploadPath)
            throws IOException, SftpException
    {
        InputStream inputStream = null;

        inputStream = new FileInputStream(file);

        createDir(uploadPath);

        sftp.put(inputStream, newFileName);

        inputStream.close();
    }



    /**
     * 创建循环创建文件目录
     *
     * @param uploadPath 上传目录
     * @throws SftpException
     */
    public void createDir(String uploadPath)
            throws SftpException
    {
        if(!isExistDir(uploadPath))
        {
            String pathArry[] = uploadPath.split("/");

            StringBuffer filePath = new StringBuffer("/");

            for (String path : pathArry)
            {
                if (path.equals(""))
                {
                    continue;
                }

                filePath.append(path + "/");

                if (isExistDir(filePath.toString()))
                {
                    sftp.cd(filePath.toString());
                }
                else
                {
                    // 建立目录
                    sftp.mkdir(filePath.toString());

                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }
            }
        }
    }

    /**
     * 目录是否存在
     * @param path
     * @return
     */
    private boolean isExistDir(String path)
    {
        try
        {
            sftp.cd(path);

            return true;
        }
        catch (SftpException e)
        {
            return false;
        }
    }
}
