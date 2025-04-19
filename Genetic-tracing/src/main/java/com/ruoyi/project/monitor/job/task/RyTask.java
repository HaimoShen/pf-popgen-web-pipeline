package com.ruoyi.project.monitor.job.task;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.project.gdp.service.IGeneSampleService;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    private final IGeneSampleService geneSampleService = SpringUtils.getBean(IGeneSampleService.class);

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }


    public void ryNoParams4() throws IOException {
        File filePath = new File("D:\\suyuan\\deep learning\\20250408\\2000\\transform.txt");
        String filenameX = "D:\\suyuan\\deep learning\\20250408\\2000\\X.txt";
        String filenameY = "D:\\suyuan\\deep learning\\20250408\\2000\\Y.txt";
        try{
            File fileX = new File(filenameX);
            File fileY = new File(filenameY);
            if(fileX.exists()){
                fileX.delete();
            }
            fileX.createNewFile();
            if(fileY.exists()){
                fileY.delete();
            }
            fileY.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        //构造一个BufferedReader类来读取文件
        BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath.toPath()), StandardCharsets.UTF_8));
        String row = null;
        List<String> countryList = new ArrayList<>();
        //使用readLine方法，一次读一行
        while((row = br.readLine()) != null) {
            String[] array = row.split("\\s+");
            String firstCol = array[0];
            String country = geneSampleService.getCountriesBySerialNumber(firstCol);
            if(StringUtils.isBlank(country))
                continue;
            country = formatCountry2(country);
            countryList.add(country);
            row = row.replace(firstCol,country) + "\n";
            Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            System.out.println("writeX"+ countryList.size());
        }
        br.close();

        Set<String> set = new HashSet<>(countryList);
        List<String> uniqueList = new ArrayList<>(set);
        Collections.sort(uniqueList);
        String firstRow = "country";
        for(int i =0;i<uniqueList.size();i++) {
            System.out.println(uniqueList.get(i));
            System.out.println(uniqueList.get(i));
            firstRow = firstRow + "\t" + uniqueList.get(i);
        }
        firstRow = firstRow + "\n";
        Files.write(Paths.get(filenameY), firstRow.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        for(int i =0;i<countryList.size();i++) {
            String row1 = countryList.get(i);
            for(int j =0;j<uniqueList.size();j++) {
                if (countryList.get(i).equals(uniqueList.get(j))){
                    row1 = row1 + "\t" + "1";
                }else{
                    row1 = row1 + "\t" + "0";
                }
            }
            System.out.println("writeY"+i);
            row1 = row1 + "\n";
            Files.write(Paths.get(filenameY), row1.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
    }

    public void ryNoParams() throws IOException {
//        File filePath = new File("D:\\suyuan\\20250122\\transform.txt");
        File filePath = new File("D:\\suyuan\\deep learning\\20250311\\500\\transform.txt");
        String filenameX = "D:\\suyuan\\deep learning\\20250311\\500\\X.txt";
//        String filenameX = "D:\\suyuan\\20250122\\X.txt";
        String filenameY = "D:\\suyuan\\deep learning\\20250311\\500\\Y.txt";
//        String filenameY = "D:\\suyuan\\20250122\\Y.txt";
        try{
            File fileX = new File(filenameX);
            File fileY = new File(filenameY);
            if(fileX.exists()){
                fileX.delete();
            }
            fileX.createNewFile();
            if(fileY.exists()){
                fileY.delete();
            }
            fileY.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        //构造一个BufferedReader类来读取文件
        BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath.toPath()), StandardCharsets.UTF_8));
        String row = null;
        List<String> countryList = new ArrayList<>();
        //使用readLine方法，一次读一行
        while((row = br.readLine()) != null) {
            String[] array = row.split("\\s+");
            String firstCol = array[0];
            String country = geneSampleService.getCountriesByColumnNumber(Integer.parseInt(firstCol.replace("Sample_",""))+4);
            if ("几内亚".equals(country)) {
                country = "Guinea";
                countryList.add(country);
                country = formatCountry2(country);
                row = row.replace(firstCol,country) + "\n";
                Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }else if ("刚果金".equals(country)) {
                country = "Congo-DR";
                countryList.add(country);
                country = formatCountry2(country);
                row = row.replace(firstCol,country) + "\n";
                Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }else if ("加纳".equals(country)) {
                country = "Ghana";
                countryList.add(country);
                country = formatCountry2(country);
                row = row.replace(firstCol,country) + "\n";
                Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }else if ("尼日利亚".equals(country)) {
                country = "Nigeria";
                countryList.add(country);
                country = formatCountry2(country);
                row = row.replace(firstCol,country) + "\n";
                Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
//            String country = firstCol;
//            if (StringUtils.isBlank(country))
//                continue;

//            System.out.println("writeX"+ countryList.size());
        }
        br.close();

        List<String> countryList1 = new ArrayList<>();
        countryList1.add("201");
        countryList1.add("202");
        countryList1.add("203");
        countryList1.add("204");
        countryList1.add("205");
        countryList1.add("206");
        countryList1.add("207");
        countryList1.add("208");
        countryList1.add("209");
        countryList1.add("210");
        countryList1.add("211");
        countryList1.add("212");
//        countryList1.add("213");
        countryList1.add("214");
        countryList1.add("215");
        countryList1.add("216");
        countryList1.add("217");
//        countryList1.add("218");
//        countryList1.add("219");
        countryList1.add("220");
        countryList1.add("221");
        countryList1.add("222");
        countryList1.add("223");
//        countryList1.add("224");
        countryList1.add("225");
        countryList1.add("226");
//        countryList1.add("227");
        countryList1.add("228");
        countryList1.add("229");
        countryList1.add("230");
        countryList1.add("231");
        countryList1.add("232");
//        countryList1.add("233");
//        countryList1.add("234");
        Set<String> set = new HashSet<>(countryList1);
        List<String> uniqueList = new ArrayList<>(set);
        Collections.sort(uniqueList);
        String firstRow = "country";
        for(int i =0;i<uniqueList.size();i++) {
            System.out.println(uniqueList.get(i));
            System.out.println(formatCountry2(uniqueList.get(i)));
            firstRow = firstRow + "\t" + formatCountry2(uniqueList.get(i));
        }
        firstRow = firstRow + "\n";
        Files.write(Paths.get(filenameY), firstRow.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        for(int i =0;i<countryList.size();i++) {
            String row1 = formatCountry2(countryList.get(i));
            for(int j =0;j<uniqueList.size();j++) {
                if (formatCountry2(countryList.get(i)).equals(formatCountry2(uniqueList.get(j)))){
                    row1 = row1 + "\t" + "1";
                }else{
                    row1 = row1 + "\t" + "0";
                }
            }
            System.out.println("writeY"+i);
            row1 = row1 + "\n";
            Files.write(Paths.get(filenameY), row1.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
    }

    public static String formatCountry(String country){
        switch (country)
        {
            case "Ghana":
                return "100";
            case "Bangladesh":
                return "102";
            case "Laos":
                return "103";
            case "Senegal":
                return "105";
            case "Guinea":
                return "106";
            case "Nigeria":
                return "107";
            case "Viet-Nam":
                return "108";
            case "Mali":
                return "109";
            case "Malawi":
                return "110";
            case "Thailand":
                return "112";
            case "Gambia":
                return "115";
            case "Cambodia":
                return "116";
            case "Congo-DR":
                return "117";
            case "Myanmar":
                return "118";
            default:
                return country;
        }
    }

    public static String formatCountry2(String country){
        switch (country)
        {
            case "Mauritania":
                return "201";
            case "Gambia":
                return "202";
            case "Guinea":
                return "203";
            case "Kenya":
                return "204";
            case "Thailand":
                return "205";
            case "Tanzania":
                return "206";
            case "Ghana":
                return "207";
            case "Cambodia":
                return "208";
            case "Indonesia":
                return "209";
            case "Burkina Faso":
                return "210";
            case "Mali":
                return "211";
            case "Papua New Guinea":
                return "212";
            case "Peru":
                return "213";
            case "Bangladesh":
                return "214";
            case "Malawi":
                return "215";
            case "Vietnam":
                return "216";
            case "Colombia":
                return "217";
            case "Venezuela":
                return "218";
            case "Uganda":
                return "219";
            case "Myanmar":
                return "220";
            case "Laos":
                return "221";
            case "Democratic Republic of the Congo":
                return "222";
            case "Nigeria":
                return "223";
            case "Madagascar":
                return "224";
            case "Cameroon":
                return "225";
            case "C么te d'Ivoire":
                return "226";
            case "Ethiopia":
                return "227";
            case "Benin":
                return "228";
            case "Senegal":
                return "229";
            case "Gabon":
                return "230";
            case "India":
                return "231";
            case "Sudan":
                return "232";
            case "Mozambique":
                return "233";
            case "Congo-DR":
                return "234";
            default:
                return country;
        }
    }


    public void ryNoParams3() throws IOException {
        File filePath = new File("D:\\suyuan\\deep learning\\20250311\\163_samples.txt");
        String filenameX = "D:\\suyuan\\deep learning\\20250311\\tq_samples.txt";
        try{
            File fileX = new File(filenameX);
            if(fileX.exists()){
                fileX.delete();
            }
            fileX.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        //构造一个BufferedReader类来读取文件
        BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath.toPath()), StandardCharsets.UTF_8));
        String row = null;
        //使用readLine方法，一次读一行
        while((row = br.readLine()) != null) {
            String[] array = row.split("\\s+");
            row = array[0]+ "\t" + array[1] + "\t" + array[2]+"\t"+ array[3];
            for(int i = 4;i<array.length;i++) {
                String country = geneSampleService.getCountriesByColumnNumber(i+1);
                if ("几内亚".equals(country) || "刚果金".equals(country) || "加纳".equals(country) || "尼日利亚".equals(country)) {
                    row = row + "\t" + array[i];
                }
            }
            row = row + "\n";
            Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
        br.close();
    }


    public void ryNoParams2() throws IOException {
        File filePath = new File("D:\\suyuan\\deep learning\\20250311\\200\\163_samples.txt.cleaned.txt.calculator_step.200.txt.combined.txt");
        String filenameX = "D:\\suyuan\\deep learning\\20250311\\tq_transform.txt";
        try{
            File fileX = new File(filenameX);
            if(fileX.exists()){
                fileX.delete();
            }
            fileX.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        //构造一个BufferedReader类来读取文件
        BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath.toPath()), StandardCharsets.UTF_8));
        String row = null;
        //使用readLine方法，一次读一行
        while((row = br.readLine()) != null) {
            String[] array = row.split("\\s+");
            if(row.startsWith("#CHROM") || row.startsWith("POS") || row.startsWith("REF") || row.startsWith("ALT")){
                row = row + "\n";
                Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }else{
                String firstCol = array[0];
                String country = geneSampleService.getCountriesBySerialNumber(firstCol);
                if ("几内亚".equals(country)) {
                    row = row.replace(firstCol,"Guinea");
                    row = row + "\n";
                    Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }else if ("刚果金".equals(country)) {
                    row = row.replace(firstCol,"Congo-DR");
                    row = row + "\n";
                    Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }else if ("加纳".equals(country)) {
                    row = row.replace(firstCol,"Ghana");
                    row = row + "\n";
                    Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }else if ("尼日利亚".equals(country)) {
                    row = row.replace(firstCol,"Nigeria");
                    row = row + "\n";
                    Files.write(Paths.get(filenameX), row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }
            }
        }
        br.close();
    }


    public void ryNoParams5() throws IOException {
        // 源目录（存放待分类的图片）
        String sourceDir = "F:\\classifyImages\\source\\163";
        // 目标目录（分类后的图片存放位置）
        String targetDir = "F:\\classifyImages\\target\\163";

        File sourceFolder = new File(sourceDir);

        // 获取源目录中的所有文件
        File[] files = sourceFolder.listFiles();

        // 遍历文件
        for (File file : files) {
            if (file.isFile()) {
                // 获取文件扩展名（图片格式）
                String fileName = file.getName();
                String sampleName = fileName.substring(0,fileName.lastIndexOf("."));
                String country = geneSampleService.getCountriesByColumnNumber(Integer.parseInt(sampleName.replace("Sample_", "")) + 4);

                // 根据扩展名创建目标目录
                String categoryDir = targetDir + File.separator + country;
                File categoryFolder = new File(categoryDir);
                if (!categoryFolder.exists()) {
                    categoryFolder.mkdirs(); // 如果目录不存在，则创建
                }

                // 移动文件到目标目录
                Path sourcePath = file.toPath();
                Path targetPath = Paths.get(categoryDir, fileName);
                try {
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("已移动文件: " + fileName + " -> " + categoryDir);
                } catch (IOException e) {
                    System.out.println("移动文件失败: " + fileName);
                    e.printStackTrace();
                }
            }
        }
    }

    public void ryNoParams7() throws IOException {
        String filenameX = "D:\\suyuan\\deep learning\\16203samples\\pf7-QC-pass-list.txt";
        try{
            File fileX = new File(filenameX);
            if(fileX.exists()){
                fileX.delete();
            }
            fileX.createNewFile();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        List<String> list = geneSampleService.randomSample();
        for(String country :list){
             country = country + "\n";
            Files.write(Paths.get(filenameX), country.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }

    }

}
