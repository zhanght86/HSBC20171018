package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

public class ZipFileFromDir {
private static Logger logger = Logger.getLogger(ZipFileFromDir.class);
	
	//定义缓存大小
	static final int BUFFER = 2048;

	public static void main(String args[]) {
	
		ZipFileFromDir oCreatZipFile = new ZipFileFromDir();
		try {
			//oCreatZipFile.createZipFile(new File("d:\\FilezipTest1111.zip"),new File("d:\\Filezip"));
			oCreatZipFile.createZipFile(new File("f:\\a.jar"),new File("E:\\项目\\iBRMS\\ui\\ibrms\\classes\\com\\sinosoft\\ibrms\\bom\\"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过给定的压包文件名称以及需要压包的文件夹
	 * @param oFile1  压包名称[绝对路径]
	 * @param oFile2  待压包的文件夹[绝对路径]
	 */
	public void createZipFile(File oFile1,File oFile2) throws Exception{
		
		try {
			
			BufferedInputStream oBIS = null;
			//准备压缩包存放压缩后的数据
			FileOutputStream oFOPS = new FileOutputStream(oFile1);
			//用于往压缩包中写数据
			ZipOutputStream oZOS = new ZipOutputStream(new BufferedOutputStream(oFOPS));
			//指定缓存大小
			byte b_sData[] = new byte[BUFFER];
			//指定对应的目录下 得到对应目下的所有文件
			String sFiles[] = oFile2.list();

			/***
			 * 循环取得对应的文件列表并把对应的文件保存到压缩包中
			 * 但对于如果里面有某个文件为文件夹时不支持，其他如文件或者ZIP文件支持。
			 */
			for(int i = 0; i < sFiles.length; i++){
				
				FileInputStream oFIS = new FileInputStream(oFile2 + "\\" + sFiles[i]);
				oBIS = new BufferedInputStream(oFIS, BUFFER);
				//指定jar内部的引用路径
				String jarPathTem = "select sysvarvalue from ldsysvar where sysvar='ibrmsJarPath'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(jarPathTem);
				String jarPath = new ExeSQL().getOneValue(sqlbv);
				ZipEntry oZE = new ZipEntry(jarPath + "\\" + sFiles[i]);
				oZOS.putNextEntry(oZE);
				int nCount;
				//判断文件是否结束
				while((nCount = oBIS.read(b_sData, 0, BUFFER)) != -1){
					oZOS.write(b_sData, 0, nCount);
				}
				oBIS.close();
			}
			oZOS.close();
		} catch (Exception e) {
			//e.printStackTrace();
			throw e;
		}
		logger.debug("压缩完毕 End");
		
	}
}
