package com.ruoyi.common.utils.command;

import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用系统命令工具类
 * @author wsw
 * @version 2019-11-08
 */
public class CommandUtils {

	private static Logger logger = LoggerFactory.getLogger(CommandUtils.class);

	public static Map<String, Object> exeCmd(String commandStr, String step){
		Map<String, Object> data = new HashMap<>();
		try{
			Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",commandStr});
			logger.debug("exeCmd()-step"+step+"："+commandStr);
			RunThread rt1 = new RunThread(p.getInputStream(),"info","");
			RunThread rt2 = new RunThread(p.getErrorStream(),"error","");
			Thread t1 = new Thread(rt1);
			t1.start();
			Thread t2 = new Thread(rt2);
			t2.start();
			int value = p.waitFor();
			p.destroy();
			logger.debug("exeCmd()：waitFor = "+value);
			String message = StringUtils.isBlank(rt1.getMsg()) ? rt2.getMsg() :rt1.getMsg();
			if(value == 0){
				data.put("result_"+step, "true");
				data.put("s_msg_"+step, StringUtils.isBlank(rt1.getMsg())?(StringUtils.isBlank(message)?"执行step"+step+"成功":message):rt1.getMsg());
				logger.debug("exeCmd()：success");
			}else{
				data.put("result_"+step, "false");
				data.put("e_msg",message);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return data;
	}

	public static String processComStr(String commandStr, String dataPath, String dirName){
		commandStr = StringEscapeUtils.unescapeHtml4(commandStr);
		//替换分割符
		dataPath = dataPath.replaceAll("/", File.separator);
		dataPath = dataPath.endsWith(File.separator) ? dataPath.substring(0,dataPath.length()-1) : dataPath;
		commandStr = commandStr.trim();
		commandStr = commandStr.replaceAll("/", File.separator);
		//替换路径和文件夹名称
		commandStr = commandStr.replaceAll(":path",dataPath).replaceAll(":name",dirName);
		if(commandStr.contains("CombineGVCFs")){
			String path = dataPath+File.separator+ "original" + File.separator+dirName;
			File file=new File(path);
			File[] files=file.listFiles();
			for (File file2 : files) {
				commandStr += " -V "+file2.getAbsolutePath();
			}
		}

		return commandStr;
	}

//	public static List<GeneProcess> processComList (List<GeneProcess> list,String dataPath, String dirName){
//		dataPath = dataPath.replaceAll("/", File.separator);
//		dataPath = dataPath.endsWith(File.separator) ? dataPath.substring(0,dataPath.length()-1) : dataPath;
//		String path = dataPath+File.separator+dirName;
//		File file=new File(path);
//		File[] files=file.listFiles();
//        int i = 0;
//		for(GeneProcess g : list){
//			String command = g.getCommand();
//			if(command.contains("CombineGVCFs")){
//				for (File file2 : files) {
//					command += " -V "+file2.getAbsolutePath();
//				}
//				g.setCommand(command);
//				list.set(i,g);
//				break;
//			}
//			i++;
//		}
//		return list;
//		}

}
