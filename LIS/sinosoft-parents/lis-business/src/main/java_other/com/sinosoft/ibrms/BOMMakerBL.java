package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.sinosoft.lis.db.LRBOMDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LRBOMSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class BOMMakerBL {
private static Logger logger = Logger.getLogger(BOMMakerBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	LRBOMSet tLRBOMSet = new LRBOMSet();
	
    /** 往后面传输的数据库操作 */
    private MMap map = new MMap();
	
	public BOMMakerBL() {
		// just for debug
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
	
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 检查数据合法性

		if (!checkInputData()) {
			return false;
		}
		// 进行业务处理
		//logger.debug(this.getClass().getName()+"444");
		if (!dealDate()) {		
			//CError.buildErr(this, "数据处理失败LDBomBL-->dealData!");
			return false;
		}

		if (!prepareOutputData()) {
			logger.debug(this.getClass().getName()+"44");
			return false;
		}
		//数据提交
	
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
        	/*mErrors.addOneError("数据提交失败!");*/
        	CError.buildErr(this, "数据提交失败!");
            return false;
        }
     
        mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try
        {
            //后台传送
        	logger.debug("BOM::prepareOutputData");
        	mInputData.clear();
        	mInputData.add(map);
//            mResult.add(mTransferData);
        }catch (Exception ex)
        {
            // @@错误处理            
            CError.buildErr(this, "在准备往后层处理所需要的数据时出错");            
            return false;
        }
        return true;
	}

	// BOM下载
	private boolean downBOM() {
		
		try {

			// 编译
			String sqlCClass = "select sysvarvalue from ldsysvar where sysvar='ibrmsCreatClass'";
			String sqlClassPath = "select sysvarvalue from ldsysvar where sysvar='ibrmsClassPath'";
			String sqlJava = "select sysvarvalue from ldsysvar where sysvar='ibrmsCreatJava'";	
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sqlCClass);
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sqlClassPath);
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sqlJava);
			String cPath = new ExeSQL().getOneValue(sqlbv);//生成CLASS路径
			String cPPath = new ExeSQL().getOneValue(sqlbv1);//CLASSPATH路径   "F:\\ibms\\ui\\ibrms\\classes;F:\\ibms\\ui\\WEB-INF\\classes;F:\\ibms\\ui\\WEB-INF\\lib;";// 
			logger.debug("classpath:"+cPPath);
			String jPath = new ExeSQL().getOneValue(sqlbv2);//生成JAVA路径
			ArrayList arr = new ArrayList();
			arr.add("AbstractBOM.java");
			LRBOMDB mLRBOMDB = new LRBOMDB();
			LRBOMSet mLRBOMSet = mLRBOMDB.executeQuery("select * from LRBOM");
			logger.debug("bomLength:"+mLRBOMSet.size());
			for (int i = 0; i < mLRBOMSet.size(); i++) {
				logger.debug("bomname:"+mLRBOMSet.get(i+1).getName());				
			}			
			String repeatEdit = "select count(distinct(fbom)) from lrbom";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(repeatEdit);
			JRJavacCompiler tJRJavacCompiler = new JRJavacCompiler();
			int count = Integer.parseInt(new ExeSQL().getOneValue(sqlbv3));
			String sqlOrderByFBOM = "select * from lrbom order by (case when fbom is null then 0 else 1 end),fbom ";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(sqlOrderByFBOM);
			LRBOMSet rLRBOMSet = new LRBOMSet();
			rLRBOMSet = mLRBOMDB.executeQuery(sqlbv4);
			for(int r=0 ;r<rLRBOMSet.size();r++){
				arr.add(rLRBOMSet.get(r+1).getName()+".java");
			}
			//for(int j= 0;j<=count;j++ ){
			
				for(int i = 0;i<arr.size();i++){
					String tem = "";
					tem =(String) arr.get(i);	
					File file = new File(jPath + tem);
					File[] fileArray = new File[1];
					fileArray[0] = file;
					String error = tJRJavacCompiler.compileClasses(fileArray,cPath,cPPath);
					if(error!=null)
						logger.debug("有错误："+error);
				}
			//}
			logger.debug("----------工作结束！----------");
			
			ZipFileFromDir oCreatZipFile = new ZipFileFromDir();
			//下载java文件存放路径
			String sqlClass = "select sysvarvalue from ldsysvar where sysvar='ibrmsDownClass'";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sqlClass);
			String dPath = new ExeSQL().getOneValue(sqlbv5);
			//下载jar包存放路径
			String targetSql = "select sysvarvalue from ldsysvar where sysvar='ibrmsTarget'";
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(targetSql);
			String target = new ExeSQL().getOneValue(sqlbv6);
			oCreatZipFile.createZipFile(new File(target), new File(dPath));
		} catch (FileNotFoundException e) {
			CError.buildErr(this, "下载路径错误或者编译失败！");
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private boolean creatData() {
		// 从sysvarvalue表中取出路径
		String sqlJava = "select sysvarvalue from ldsysvar where sysvar='ibrmsCreatJava'";	
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sqlJava);
		String jPath = new ExeSQL().getOneValue(sqlbv7);
		BOMMaker tBOMMaker = new BOMMaker();
	//	tBOMMaker.createAbstract(jPath);
		for (int i = 0; i < tLRBOMSet.size(); i++) {
			String temp = "";
			temp = tBOMMaker.makeTable(tLRBOMSet.get(i + 1), jPath);
			// 生成JAVA文件名
			String sql = "update lrbom set SOURCE='?SOURCE?' where name='?name?'";
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
			sqlbv8.sql(sql);
			sqlbv8.put("SOURCE", "com.sinosoft.ibrms.bom."+tLRBOMSet.get(i + 1).getName());
			sqlbv8.put("name", tLRBOMSet.get(i + 1).getName());
			map.put(sqlbv8, "UPDATE");			
		}
		if(tBOMMaker.mErrors.getErrorCount()>0)
		{
			this.mErrors =tBOMMaker.mErrors;//  .addOneError(tBOMMaker.mErrors.getError(0))
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {

		tLRBOMSet = (LRBOMSet) cInputData.getObjectByObjectName("LRBOMSet", 0);
		return true;
	}

	/**
	 * 查询符合条件的信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */

	private boolean checkInputData() {
		if (mOperate.equals("DOWNCLASS")) {
			return true;
		} else {
			if (tLRBOMSet == null) {
				CError.buildErr(this, "操作失败，提交的BOM信息为空");
				return false;
			}
		}
		return true;
	}

	/**
	 * 查询符合条件的信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealDate() {
		if (mOperate.equals("CREATEPAET")) {
			return creatData();
		}
		if (mOperate.equals("CREATEALL")) {
			return creatData();
		}
		if (mOperate.equals("DOWNCLASS")) {
			return downBOM();
		}
		return true;
	}
}
