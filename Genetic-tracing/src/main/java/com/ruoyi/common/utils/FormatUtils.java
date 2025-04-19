package com.ruoyi.common.utils;

import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 样本处理
 */
public class FormatUtils {

      //format转化成0/1 -> 合并 -> 过滤

        //以下main方法进行调用测试
        public static void main(String[] args) {
            //流程最终文件
//            String path = "D:\\suyuan\\20240328\\mergeVcf\\mergedVcf.txt";
//            transform(path);
//            read500(path);
//            format(path);
//            filter(path);
//            for(int i=1;i<15;i++){
//                String path = "";
//                if(i<10) {
//                    path = "F:\\suyuan资料\\standard\\SNP_INDEL_Pf3D7_0" + i + "_v3.combined.filtered.vcf";
////                    path = "/media/sy/ZR_4T/suyuan资料/SNP_INDEL_Pf3D7_0" + i + "_v3.combined.filtered.vcf";
//                }else{
//                    path = "F:\\suyuan资料\\standard\\SNP_INDEL_Pf3D7_" + i + "_v3.combined.filtered.vcf";
////                    path = "/media/sy/ZR_4T/suyuan资料/SNP_INDEL_Pf3D7_" + i + "_v3.combined.filtered.vcf";
//                }
////                merge(path,i);
////                read1000(path);
//            }
            handle37();
        }

        //将数据写入csv文件方法
        public static void format(String path) {
            System.out.println(path);
            File file = new File(path);
            String name = file.getName();
//            String filename = "F:\\suyuan资料\\standard\\format"+"\\"+name;
            String filename = "standard/"+name;
            try{
                File file1 = new File(filename);
                if(file1.exists()){
                    file1.delete();
                }
                file1.createNewFile();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }

            StringBuilder result = new StringBuilder();
            try{
                //构造一个BufferedReader类来读取文件
                BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
                String row = null;
                int sampleCount = 0;
                int n = 1;
                //使用readLine方法，一次读一行
                while((row = br.readLine()) != null) {
                    String sampleFormat = "";
                    //从非注释行开始读取
                    if (!row.startsWith("##")) {

                        String[] array = row.split("\t");
                        String aa3 = array[3].replace(",*","").replace("*,","");
                        String aa4 = array[4].replace(",*","").replace("*,","");

                        if((aa3.equals("REF") || aa3.length() == 1) && (aa4.equals("ALT") || aa4.length() == 1)) {
                            //读取 CHROM POS REF ALT
                            sampleFormat = array[0] + "\t" + array[1] + "\t" + array[3] + "\t" + array[4];
                            if (row.startsWith("#")) {
                                //标题行
                                for (int i = 9; i < array.length; i++) {
                                    String arrStr = array[i];
                                    arrStr = "\t" + arrStr;
                                    //行数据
                                    sampleFormat = sampleFormat + arrStr;
                                }
                            } else {
                                //从第10列开始是样本数据，读取样本数据列并转化成0和1
                                for (int i = 9; i < array.length; i++) {
                                    String arrStr = array[i];
                                    //读取数据行 0/0和./.转化成0,其他的转化成1
                                    if (arrStr.contains("0/0") || arrStr.contains("./.")) {
                                        arrStr = "\t" + "0";
                                    } else {
                                        arrStr = "\t" + "1";
                                    }
                                    //行数据
                                    sampleFormat = sampleFormat + arrStr;
                                }
                            }
                            //换行
                            sampleFormat = sampleFormat + "\n";
                            //样本数量
                            sampleCount = sampleFormat.split("\t").length - 9;
                            //追加写模式
                            Files.write(Paths.get(filename), sampleFormat.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                        }
                    }

//                    n++;
//                    if(n>500){
//                        break;
//                    }
                }
                br.close();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    public static void merge(String path,int index) {
        System.out.println(path);
        File file = new File(path);

        String filename = "F:\\suyuan资料\\standard\\SNP_INDEL_Pf3D7_all_v3.combined.filtered.vcf";

        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;
            int sampleCount = 0;
            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {
                //换行
                if(index == 1) {
                    row = row + "\n";
//                System.out.println(row);
                    //追加写模式
                    Files.write(Paths.get(filename), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }else{
                    if(!row.startsWith("#")) {
                        row = row + "\n";
//                System.out.println(row);
                        //追加写模式
                        Files.write(Paths.get(filename), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                    }
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void filter(String path) {
        System.out.println(path);
        File file = new File(path);

        String filename = "F:\\suyuan资料\\standard\\SNP_INDEL_Pf3D7_all_final_v3.combined.filtered.vcf";
        try{
            File file1 = new File(filename);
            if(file1.exists()){
                file1.delete();
            }
            file1.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;

            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {
                    int sum = 0;

                    if(row.startsWith("#")) {
                        row = row + "\n";
                        //追加写模式
                        Files.write(Paths.get(filename), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                    }else{
                        String[] array = row.split("\t");
                        int count = array.length - 9;
                        for (int i = 9; i < array.length; i++) {
                            String arrStr = array[i];
                            sum += Integer.parseInt(arrStr);
                        }
//                        System.out.println(sum);
//                        System.out.println(count);
//                        System.out.println((float)sum/count);
//                        System.out.println((float)sum/count > 0.01);
                        if((float)sum/count >= 0.01) {
                            row = row.replace(",*","").replace("*,","");
                            row = row + "\n";
                            //追加写模式
                        Files.write(Paths.get(filename), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                        }
                    }
            }

            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void transform(String path) {
        System.out.println(path);
        File file = new File(path);

        String filename = "D:\\suyuan\\20240328\\mergeVcf\\transform.txt";
        try{
            File file1 = new File(filename);
            if(file1.exists()){
                file1.delete();
            }
            file1.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;
            int length = 0;
            List<String> list = new ArrayList<>();

            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {

                String[] array = row.split("\t");
                length = array.length-4;
                String str = "";
                if(row.startsWith("#")) {
                    for (int i = 4; i < array.length; i++) {
                        str = str + ">" + array[i] + "@" + "\t";
                    }
                    list.add(str);
                    //追加写模式
//                    Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }else {
                    String ref = array[2];
                    String alt = array[3];
                    for (int i = 4; i < array.length; i++) {
                        str = str + array[i] + "\t";
                    }
                    str = str.replace("0",ref).replace("1",alt);
                    list.add(str);
                    //追加写模式
//                    Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }
            }
            br.close();

            Files.write(Paths.get(filename), "# STOCKHOLM 1.0\n".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            for(int i =0;i<length;i++){
                String str = "";
                for(String rowStr:list) {
                    String[] array = rowStr.split("\t");
                        str = str + array[i];
                        str = str.replace("@","\t");
                }
                //追加写模式
                str = str + "\n";
                Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
            Files.write(Paths.get(filename), "//\n".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        }catch(Exception e){
            e.printStackTrace();
        }
    }



    public static void read() {
        File file = new File("");
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;
            int length = 0;
            List<String> list = new ArrayList<>();

            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {
//                String[] array = row.split("\t");
                String[] array = row.split("\\s+");
                String str = "";
                for (int i = 4; i < array.length; i++) {
                    str = str + array[i] + "\t";
                }
//                list.add(str);
//                System.out.println("read"+list.size());
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void handle163() {
        File file = new File("D:\\suyuan\\deep learning\\163_samples\\163_samples.vcf");
        String filename = "D:\\suyuan\\deep learning\\163_samples\\163_samples.txt";
        try{
            File file1 = new File(filename);
            if(file1.exists()){
                file1.delete();
            }
            file1.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;
            List<String> list = new ArrayList<>();

            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {
                String[] array = row.split("\\s+");
                String str = array[0]+"\t"+array[1]+"\t"+array[3]+"\t"+array[4];
                for (int i = 9; i < array.length; i++) {
                    str = str + "\t" + array[i];
                }
                list.add(str);
                str = str + "\n";
                Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void handle37() {
        File file = new File("D:\\suyuan\\deep learning\\20250311\\37_samples_1.txt");
        String filename = "D:\\suyuan\\deep learning\\20250311\\37_samples_v2.txt";
        try{
            File file1 = new File(filename);
            if(file1.exists()){
                file1.delete();
            }
            file1.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;

            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {
                String[] array = row.split("\\s+");
                String str = array[0];
                for (int i = 1; i < array.length; i++) {
                    str = str + "\t" + array[i];
                }
                str = str + "\n";
                Files.write(Paths.get(filename), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //读取500行
    public static void read1000(String path) {
        System.out.println(path);
        File file = new File(path);
        String name = file.getName();
//        String filename = "F:\\suyuan资料\\standard\\500row"+"\\"+name;
        String filename = "";
//        String filename = "standard/500row/"+name;
        try{
            File file1 = new File(filename);
            if(file1.exists()){
                file1.delete();
            }
            file1.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            String row = null;
            int sampleCount = 0;
            int n =1;
            //使用readLine方法，一次读一行
            while((row = br.readLine()) != null) {
                    //换行
                    row = row + "\n";
//                    System.out.println(row);
                    //追加写模式
                Files.write(Paths.get(filename), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

                n++;
                if (n > 1000) {
                    break;
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
