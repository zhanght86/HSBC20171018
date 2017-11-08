package com.sinosoft.ibrms;
import org.apache.log4j.Logger;
/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */



import java.io.File;
import java.util.ArrayList;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * ClassName:
 * </p>
 * <p>
 * Description: 生成器
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @author: 
 * @date: 2008-08-26
 */
public class Maker
{
private static Logger logger = Logger.getLogger(Maker.class);

	/**
	 * Maker生成函数 生成BOM的JAVA对象
	 * @param args String[]
	 */
	
	public static void main(String[] args)
	{
		ArrayList arr = new ArrayList();
		JdbcUrl sUrl = new JdbcUrl();
		String s = sUrl.getJdbcUrl();
		logger.debug(s);
		JRJavacCompiler tJRJavacCompiler = new JRJavacCompiler();
		
		// 从sysvarvalue表中取出路径
		String  sqlJava = "select sysvarvalue from ldsysvar where sysvar='ibrmsCreatJava'";
		String sqlClass = "select sysvarvalue from ldsysvar where sysvar='ibrmsCreatClass'";
		String sqlClassPath = "select sysvarvalue from ldsysvar where sysvar='ibrmsClassPath'";
		String sqlDelPath = "select sysvarvalue from ldsysvar where sysvar='ibrmsDelClass'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sqlJava);
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sqlClass);
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sqlClassPath);
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sqlDelPath);
		String jPath = new ExeSQL().getOneValue(sqlbv);
		String cPath = new ExeSQL().getOneValue(sqlbv1);
		String cPPath = new ExeSQL().getOneValue(sqlbv2);
		String sqlDelClassPath = new ExeSQL().getOneValue(sqlbv3);
		//	在生成对象之前首先将文件夹清空
		BOMInitField tBOMInitField = new BOMInitField();
		tBOMInitField.delAllFile(jPath); 
		tBOMInitField.delAllFile(sqlDelClassPath); 

		
		//	生成java对象
		BOMMaker tBOMMaker = new BOMMaker();
		LRBOMDB tLRBOMDB = new LRBOMDB();
		LRBOMSet tLRBOMSet = tLRBOMDB.query();
		for(int i=0;i<tLRBOMSet.size();i++){
			String temp="";
			 temp = tBOMMaker.makeTable(tLRBOMSet.get(i+1),jPath);	
			 arr.add(temp);
		}	
		
		// 编译
		String repeatEdit = "select count(distinct(fbom)) from lrbom";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(repeatEdit);
		int count = Integer.parseInt(new ExeSQL().getOneValue(sqlbv4));
		for(int j= 0;j<=count;j++ ){
			for(int i = 0;i<arr.size();i++){
				String tem = "";
				tem =(String) arr.get(i);	
				File file = new File(jPath + tem);
				logger.debug("file:" + file.getName());
				File[] fileArray = new File[1];
				fileArray[0] = file;
				logger.debug("file1:" + fileArray[0].getName());
				String error = tJRJavacCompiler.compileClasses(fileArray,cPath,cPPath);
				if(error!=null)
					logger.debug("有错误："+error);
			}
		}
		logger.debug("----------工作结束！----------");
		// 退出操作
		System.exit(0);
	}
}
