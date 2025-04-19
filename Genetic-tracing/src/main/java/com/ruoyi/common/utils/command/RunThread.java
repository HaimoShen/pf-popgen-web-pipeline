package com.ruoyi.common.utils.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(RunThread.class);
    private InputStream is;
    private String type;
    private String msg;

    RunThread(InputStream is, String type, String msg){
        this.is = is;
        this.type = type;
        this.msg = msg;
    }

    public void run(){
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
            logger.debug(this.type + "-start");
            String line;
            while( (line = br.readLine() ) != null ){
                logger.debug(this.type + "：" + line);
                sb.append(line).append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                this.is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        this.msg = sb.toString();
    }

    public String getMsg() {
        return this.msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
