package com.ruoyi.common.utils;

import com.opencsv.CSVWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

        //以下main方法进行调用测试
        public static void main(String[] args) {
            //装业务数据集合
            List<String[]> data = new ArrayList<>();
            //表格标题头
            String[] head = {"姓名", "手机号", "性别"};
            data.add(head);
            //表格数据
            String[] sample = {"张三", "15810097855", "男"};
            data.add(sample);
            //设置路径及文件名称
            String fileName = "D:\\test.csv";
            //写入数据
            writeCSV(fileName, data);
        }

        //将数据写入csv文件方法
        public static void writeCSV(final String fileName, final List<String[]> data) {
            CSVWriter writer = null;
            try {
                // 创建文件所在目录
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                fileOutputStream.write(0xef); //加上这句话
                fileOutputStream.write(0xbb); //加上这句话
                fileOutputStream.write(0xbf); //加上这句话
                writer = new CSVWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8.name()), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
                writer.writeAll(data);
            } catch (Exception e) {
                System.out.println("将数据写入CSV出错："+e);
            } finally {
                if (null != writer) {
                    try {
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("关闭文件输出流出错："+e);
                    }
                }
            }
        }
}
