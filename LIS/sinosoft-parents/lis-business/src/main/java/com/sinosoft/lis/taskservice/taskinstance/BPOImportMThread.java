package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;
/**
 * <p>
 * Title: BPODealInputDataBL
 * </p>
 * <p>
 * Description: 录入外包多线程批处理服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.5
 */
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.sinosoft.lis.db.BPOServerInfoDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.tb.TBPubFun;
import com.sinosoft.lis.vschema.BPOServerInfoSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BPOImportMThread extends TaskThread {
private static Logger logger = Logger.getLogger(BPOImportMThread.class);
	private String[] mBPOInputPaths;
	public BPOImportMThread() {
	}

	public boolean dealMain() {
		logger.debug("start to BPOImportMThread");
		//BPODealInputMThreadBL.main(null);
		int tMaxDealCount = 0;
		// 2 查询系统定义处理外包方数据文件数量限制
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL_MaxDealCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='BPOMaxCount'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_MaxDealCount);
		String tBPOMaxCount = tExeSQL.getOneValue(sqlbv1);
		if (tBPOMaxCount != null && !"".equals(tBPOMaxCount)
				&& Integer.parseInt(tBPOMaxCount) > 1) {
			tMaxDealCount = Integer.parseInt(tBPOMaxCount);
		}
		else
		{
			//默认每次最大处理100个
			tMaxDealCount = 100;
		}
		//tongmeng 2009-12-22 add
		//增加分组机构级别，如果没有描述默认为6位
		String tSQL_Group = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='BPOGroup'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSQL_Group);
		String tGroupL = tExeSQL.getOneValue(sqlbv2);
		int tComGroup = 6;//默认为6位
		if(tGroupL!=null&&!tGroupL.equals(""))
		{
			try {
				tComGroup = Integer.parseInt(tGroupL);
			} catch (Exception e) {
				tComGroup = 6;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//tongmeng 2009-12-22 add
		//机构分组后，每个机构线程组中的任务最多任务数控制。默认为50
		String tSQL_GroupMax = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='BPOGroupMax'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSQL_GroupMax);
		String tGroupM = tExeSQL.getOneValue(sqlbv3);
		int tGroupMax = 50;
		if(tGroupM!=null&&!tGroupM.equals(""))
		{
			try {
				tGroupMax = Integer.parseInt(tGroupM);
			} catch (Exception e) {
				tGroupMax = 50;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		///
		String tOperator = (String)this.mParameters.get("WBOperator");
		//test
//		tOperator = "WB0001";
		
		BPOServerInfoDB tBPOServerInfoDB = new BPOServerInfoDB();
		if(tOperator!=null&&!tOperator.equals(""))
		{
			tBPOServerInfoDB.setLisOperator(tOperator);
		}
		tBPOServerInfoDB.setBussNoType("TB");
		BPOServerInfoSet tBPOServerInfoSet = new BPOServerInfoSet();
		tBPOServerInfoSet = tBPOServerInfoDB.query();
		//tongmeng 2009-12-22 modify
		//为了避免多线程造成的客户号问题，先修改为按照6位机构分组。
		for(int i=1;i<=tBPOServerInfoSet.size();i++)
		{
			try {
				//tongmeng 2009-02-09 add
				//增加自动运行时间限制
				if(!PubFun.checkAutoRunControl(""))
				{
					break;
				}
				
				Vector mTaskWaitList = new Vector();
				String tBackDataPath = tBPOServerInfoSet.get(i).getBackDataPath();
				//test
//				tBackDataPath = "D:/test/";
//				tMaxDealCount = 5;
				/** 需要处理的数据文件集合 */
				String[] mNeedDealFiles;
				mNeedDealFiles = TBPubFun.getFilesList(tBackDataPath, tMaxDealCount);
				if (mNeedDealFiles == null) {
					CError.buildErr(this, "没有需要处理的数据文件或者系统定义外包方数据返回的路径错误！");
					continue;
				}

				if (mNeedDealFiles.length == 0) {
					CError.buildErr(this, "没有需要处理的数据！");
					continue;
				}
				//tongmeng 2009-12-22 modify
				//按照机构分组。
				//select MissionProp7 from lwmission where missionprop1='' and activityid='0000001089'
				Hashtable tComMap = new Hashtable();//机构分组Map
				Hashtable tDataMap = new Hashtable();//数据Map
				Hashtable tErrDataMap = new Hashtable();//错误数据Map
				for(int n=0;n<mNeedDealFiles.length;n++)
				{
					String tFileName = mNeedDealFiles[n];
					String tPrtNo = "";
					tPrtNo = tFileName.substring(0,tFileName.lastIndexOf(".xml"));
					//tongmeng 2009-12-23 modify
					//华道有可能文件名在印刷号后加标记，所以，取文件名前14位
					if(tPrtNo.length()>=14)
					{
						tPrtNo = tPrtNo.substring(0,14);
					}
					else
					{
						logger.debug("tPrtNo:"+tPrtNo+"length < 14");
					}
					
					logger.debug("FileName:"+tFileName+":PrtNo:"+tPrtNo);
					//查询待外包导入或者特殊投保单录入的数据。
					String tSQL_Com = "select MissionProp7 from lwmission where missionprop1='"+"?tPrtNo?"+"' and activityid='0000001089'"
								    + " union "
								    + " select MissionProp13 from lwmission where missionprop1='"+"?tPrtNo?"+"' and activityid='0000001094' ";
					SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
					sqlbv4.sql(tSQL_Com);
					sqlbv4.put("tPrtNo", tPrtNo);
					String tManageCom = tExeSQL.getOneValue(sqlbv4);
					//tComGroup
					if(tManageCom!=null&&!tManageCom.equals("")&&tManageCom.length()>=tComGroup)
					{
						String tCurrentComGroup = tManageCom.substring(0,tComGroup);
					
						tComMap.put(tManageCom,tManageCom);
						String tKey = "";
						tKey = tCurrentComGroup + ":"+tFileName;
						TransferData tTransferData = new TransferData();
						tTransferData.setNameAndValue("FilePath", tBackDataPath);
						tTransferData.setNameAndValue("FileName", tFileName);
						tTransferData.setNameAndValue("Operator", tBPOServerInfoSet.get(i).getLisOperator());
					
						tDataMap.put(tKey, tTransferData);
					}
					//错误的数据为了防止一直无法处理。将他们一起存放在tErrDataMap中，由一个线程处理。
					else
					{
						String tKey = "";
						tKey = tFileName;
						TransferData tTransferData = new TransferData();
						tTransferData.setNameAndValue("FilePath", tBackDataPath);
						tTransferData.setNameAndValue("FileName", tFileName);
						tTransferData.setNameAndValue("Operator", tBPOServerInfoSet.get(i).getLisOperator());
						tErrDataMap.put(tKey, tTransferData);
					}
				}
				Enumeration eKey=tComMap.keys(); 
				while(eKey.hasMoreElements()) 
				{ 
					String key=(String)eKey.nextElement();
					VData tVData = new VData();
					Enumeration eDataKey=tDataMap.keys(); 
					while(eDataKey.hasMoreElements())
					{
						String tDataKey=(String)eDataKey.nextElement();
						if(tDataKey.substring(0,tDataKey.indexOf(":")).equals(key))
						{
							//如果是同一个机构的，获取TransferData;
							//tongmeng 2009-12-22 add
							//如果每个任务组最大数大于限制数，不做处理。
							if(tVData.size()<tGroupMax)
							{	
								TransferData tCurrentTransferData = new TransferData();
								tCurrentTransferData = (TransferData)tDataMap.get(tDataKey);
								tVData.add(tCurrentTransferData);
							}
						}
					}
					mTaskWaitList.add(tVData);
				}
				//处理错误的Map
				Enumeration eErrKey=tErrDataMap.keys(); 
				while(eErrKey.hasMoreElements()) 
				{
					VData tVData = new VData();
					String tDataKey=(String)eErrKey.nextElement();
					//tongmeng 2009-12-22 add
					//如果每个任务组最大数大于限制数，不做处理。
					if(tVData.size()<tGroupMax)
					{	
						TransferData tCurrentTransferData = new TransferData();
						tCurrentTransferData = (TransferData)tErrDataMap.get(tDataKey);
						tVData.add(tCurrentTransferData);
					}
					mTaskWaitList.add(tVData);
				}
				
				
//				for(int n=0;n<mNeedDealFiles.length;n++)
//				{
//					//tongmeng 2009-02-09 add
//					//增加自动运行时间限制
//					if(!PubFun.checkAutoRunControl(""))
//					{
//						break;
//					}
//					
//					String tFileName = mNeedDealFiles[n];
//					TransferData tTransferData = new TransferData();
//					tTransferData.setNameAndValue("FilePath", tBackDataPath);
//					tTransferData.setNameAndValue("FileName", tFileName);
//					tTransferData.setNameAndValue("Operator", tBPOServerInfoSet.get(i).getLisOperator());
//					VData tVData = new VData();
//					tVData.add(tTransferData);
//					mTaskWaitList.add(tVData);
//				}
				
				int tThreadCount = 50;
				tExeSQL = new ExeSQL();
				String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='BPOThreadCount'";
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(tSQL_ThreadCount);
				String tBPOThreadCount = tExeSQL.getOneValue(sqlbv5);
				if (tBPOThreadCount != null && !"".equals(tBPOThreadCount)
						&& Integer.parseInt(tBPOThreadCount) > 0) {
					tThreadCount = Integer.parseInt(tBPOThreadCount);
				}
				if(mTaskWaitList.size()<tThreadCount)
				{
					tThreadCount = mTaskWaitList.size();
				}
				logger.debug("BPO tThreadCount:"+tThreadCount);
				ServiceA tService = new ServiceA("com.sinosoft.lis.tb.BPOInputDataMThreadBL", mTaskWaitList, tThreadCount, 10);
				tService.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		logger.debug("end to BPOImportMThread");
		return true;
	}

	
	public static void main(String[] args) {
		BPOImportMThread tBPOImportMThread = new BPOImportMThread();
		tBPOImportMThread.dealMain();
//		String test = "1234.xml";
//		logger.debug(test.substring(0,test.lastIndexOf(".xml")));
//		logger.debug(test.substring(0,test.lastIndexOf(".xml")).length()+":"+test.substring(0,test.lastIndexOf(".xml")).substring(0,3));
//		logger.debug(test.substring(0,test.indexOf(".")));
	}
}
