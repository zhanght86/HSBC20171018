package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.operfee.RNHangUp;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author xiongzh
 * @author Y
 * @version 1.0
 */
public class CustomerBQSplit {
private static Logger logger = Logger.getLogger(CustomerBQSplit.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private GlobalInput tGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate = "";

	private String mSubmitFlag = "";
	private String ImageFlag = "0";//是否有扫描件判断标志（无，0；有，1），默认没有，0；

	private String CurrentDate = PubFun.getCurrentDate();

	/** 数据表 保存数据 */
	//相关数据
	private String first_EdorAcceptNo = "";  //最原始的保全受理号
	private String first_MissionID = "";  //最原始的工作流任务号
	private String first_EdorNo = "";  //取出受理号下最小的edorno搭配原始受理号
	private String mPrtNo = "";
	private String mReaSonType = "";
	private String mReMark = "";
	private TransferData mTransferData = new TransferData();
	private LPEdorAppSchema firstLPEdorAppSchema = new LPEdorAppSchema();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	
	private LPEdorAppSet Insert_LPEdorAppSet = new LPEdorAppSet();
	private LPEdorItemSet Delete_LPEdorItemSet = new LPEdorItemSet();
	private LPEdorMainSet Delete_LPEdorMainSet = new LPEdorMainSet();
	private LPEdorItemSet Insert_LPEdorItemSet = new LPEdorItemSet();
	private LPEdorMainSet Insert_LPEdorMainSet = new LPEdorMainSet();
	private LWMissionSet Inset_LWMissionSet = new LWMissionSet();
	private LBMissionSet Inset_LBMissionSet = new LBMissionSet();
	private ES_DOC_MAINSet Inset_ES_DOC_MAINSet = new ES_DOC_MAINSet();
	private ES_DOC_PAGESSet Inset_ES_DOC_PAGESSet = new ES_DOC_PAGESSet();
	private ES_DOC_RELATIONSet Inset_ES_DOC_RELATIONSet = new ES_DOC_RELATIONSet();
	private LPContTempInfoSet UPDate_LPContTempInfoSet  = new LPContTempInfoSet();
	
	private Reflections mReflections = new Reflections();
	
	private ExeSQL tExeSQL = new ExeSQL();


	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	//系统日期及时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	private MMap mMMap = new MMap(); //

	private RNHangUp mrn = new RNHangUp(tGI);

	public CustomerBQSplit() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		if (!getInputData(cInputData)) 
		{
			return false;
		}
		try 
		{
			if(!checkData())
			{
				return false;
			}
			logger.debug("Start CustomerBQSplit dealDate~~");
			// 进行业务处理
			if (dealData() == false) 
			{
				return false;
			}
	
			// 准备数据
			if (!prepareData()) 
			{
				return false;
			}
	
			// 公共提交	
			PubSubmit tPubSubmit = new PubSubmit();
			if (tPubSubmit.submitData(mResult, "") == false) 
			{
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				return false;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		} 
		return true;
	}

	//校验函数
	private boolean checkData()
	{
		return true;
	}
	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean prepareData() 
	{
		// 准备Map数据-提交
		mMMap.put(this.Insert_LPEdorAppSet, "INSERT");
		mMMap.put(this.Delete_LPEdorItemSet, "DELETE");
		mMMap.put(this.Delete_LPEdorMainSet, "DELETE");
		mMMap.put(this.Insert_LPEdorItemSet, "DELETE&INSERT");
		mMMap.put(this.Insert_LPEdorMainSet, "DELETE&INSERT");
		mMMap.put(this.Inset_LWMissionSet, "INSERT");
		mMMap.put(this.Inset_LBMissionSet, "INSERT");
		mMMap.put(this.Inset_ES_DOC_MAINSet, "INSERT");
		mMMap.put(this.Inset_ES_DOC_PAGESSet, "INSERT");
		mMMap.put(this.Inset_ES_DOC_RELATIONSet, "INSERT");
		mMMap.put(this.UPDate_LPContTempInfoSet, "UPDATE");
		
		
		mResult.add(mMMap);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		first_EdorAcceptNo =(String)mTransferData.getValueByName("EdorAcceptNo");
		first_MissionID = (String)mTransferData.getValueByName("MissionID");
		if(first_EdorAcceptNo==null||first_EdorAcceptNo.equals(""))
		{
			CError tError = new CError();
			tError.moduleName = "CustomerBQSplit";
			tError.functionName = "getInputData";
			tError.errorMessage = "原始保全受理号获取失败，请您确认!";
			this.mErrors.addOneError(tError);
		}
		if (tGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerBQSplit";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 检查续保续期
	 * 
	 * @return
	 */
	private boolean dealData() 
	{
		//首先根据edoracceptno将原始信息提取出来，这些原始信息将作为拆分复制时的模板。
		firstLPEdorAppSchema = new LPEdorAppSchema();
		LPEdorAppDB xLPEdorAppDB = new LPEdorAppDB();
		xLPEdorAppDB.setEdorAcceptNo(this.first_EdorAcceptNo);
		if(!xLPEdorAppDB.getInfo())
		{
			CError tError = new CError();
			tError.moduleName = "CustomerBQSplit";
			tError.functionName = "dealData";
			tError.errorMessage = "查询初始保全受理信息(lpedorapp)失败!";
			this.mErrors.addOneError(tError);
		}
		firstLPEdorAppSchema=xLPEdorAppDB.getSchema();
		
		//查询出原始工作流信息
		LWMissionSet first_LWMissionSet = new LWMissionSet();
		LWMissionDB xLWMissionDB =new LWMissionDB();
		xLWMissionDB.setMissionID(first_MissionID);
		first_LWMissionSet=xLWMissionDB.query();
		
		LBMissionSet first_LBMissionSet = new LBMissionSet();
		LBMissionDB xLBMissionDB =new LBMissionDB();
		xLBMissionDB.setMissionID(first_MissionID);
		first_LBMissionSet=xLBMissionDB.query();
		
		//查询是否有扫描件，有的话需要复制
		logger.debug("通过原始保全受理号查询es_doc_main数据");
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		String querySql = " select * from es_doc_main " + " where doccode = '"
				+ "?first_EdorAcceptNo?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(querySql);
		sqlbv.put("first_EdorAcceptNo", first_EdorAcceptNo);
		ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.executeQuery(sqlbv);
		ES_DOC_MAINSchema ole_ES_DOC_MAINSchema = new ES_DOC_MAINSchema();
		double oldDocID =0;
		ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		ES_DOC_PAGESSet old_ES_DOC_PAGESSet = new ES_DOC_PAGESSet();
		
		ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();	
		ES_DOC_RELATIONSet old_ES_DOC_RELATIONSet = new ES_DOC_RELATIONSet();

		
		if (tES_DOC_MAINSet.size() >0) 
		{
			ImageFlag="1";
			ole_ES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);
			oldDocID=ole_ES_DOC_MAINSchema.getDocID();
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql("select * from es_doc_pages where docid=" + "?oldDocID?");
			sbv1.put("oldDocID", oldDocID);
			old_ES_DOC_PAGESSet = tES_DOC_PAGESDB.executeQuery(sbv1);
			if (old_ES_DOC_PAGESSet == null || old_ES_DOC_PAGESSet.size() < 1) 
			{
				CError.buildErr(this, "获取DOC_PAGES失败!");
				return false;
			}
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql("select * from es_doc_relation where docid=" + "?oldDocID?");
			sbv2.put("oldDocID", oldDocID);
			old_ES_DOC_RELATIONSet = tES_DOC_RELATIONDB.executeQuery(sbv2);
			if (old_ES_DOC_RELATIONSet == null || old_ES_DOC_RELATIONSet.size() < 1) 
			{
				CError.buildErr(this, "获取DOC_RELATION失败!");
				return false;
			}
		}
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("select min(edorno) from lpedoritem a where a.edoracceptno='"+"?first_EdorAcceptNo?"+"'");
		sbv3.put("first_EdorAcceptNo", first_EdorAcceptNo);
		//取出原始受理号下最小的edorno，将此edorno搭配原始的受理号记录作为原始记录，其他edorno拆分搭配新的受理号
		first_EdorNo=tExeSQL.getOneValue(sbv3);
		
		//修改lpconttempinfo.icedoracceptno字段，置上拆分后的受理号
		LPContTempInfoDB  tLPContTempInfoDB = new LPContTempInfoDB();
		LPContTempInfoSchema tLPContTempInfoSchema  = new LPContTempInfoSchema();
		
		tLPContTempInfoDB.setEdorNo(first_EdorNo);
		tLPContTempInfoSchema=tLPContTempInfoDB.query().get(1);
		tLPContTempInfoSchema.setICEdorAcceptNo(first_EdorAcceptNo);
		tLPContTempInfoSchema.setModifyDate(this.mCurrentDate);
		tLPContTempInfoSchema.setModifyTime(this.mCurrentTime);
		UPDate_LPContTempInfoSet.add(tLPContTempInfoSchema);
			
		LPEdorItemSet xLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemDB xLPEdorItemDB = new LPEdorItemDB();
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql("select * from lpedoritem a where a.edoracceptno='"
				+"?first_EdorAcceptNo?"+"'  and a.edorno<>'"+"?first_EdorNo?"+"'");
		sbv4.put("first_EdorAcceptNo", first_EdorAcceptNo);
		sbv4.put("first_EdorNo", first_EdorNo);
		xLPEdorItemSet = xLPEdorItemDB.executeQuery(sbv4);
		for(int i=1;i<=xLPEdorItemSet.size();i++)
		{
			LPEdorItemSchema xLPEdorItemSchema = new LPEdorItemSchema();
			xLPEdorItemSchema = xLPEdorItemSet.get(i);
			Delete_LPEdorItemSet.add(xLPEdorItemSchema); //删除老记录
			
			String xContno=xLPEdorItemSchema.getContNo();
			String xEdorNo=xLPEdorItemSchema.getEdorNo();
			SQLwithBindVariables sbv5=new SQLwithBindVariables();
			sbv5.sql("select appntname from lccont where contno='"+"?xContno?"+"'");
			sbv5.put("xContno", xContno);
			String xAppntName=tExeSQL.getOneValue(sbv5);
			//复制原始LPEdorApp信息，用新受理号替换原始受理号
			LPEdorAppSchema newLPEdorAppSchema = new LPEdorAppSchema();
			mReflections.transFields(newLPEdorAppSchema, firstLPEdorAppSchema);
			String strLimit = PubFun.getNoLimit(tGI.ManageCom);
			String strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo",strLimit);
			newLPEdorAppSchema.setEdorAcceptNo(strEdorAcceptNo);
			newLPEdorAppSchema.setEdorConfNo(strEdorAcceptNo); //此字段暂时和受理号保持一致
			//还需要修改以下字段，和lpedoritem保持一致
			newLPEdorAppSchema.setChgPrem(xLPEdorItemSchema.getChgPrem());
			newLPEdorAppSchema.setChgAmnt(xLPEdorItemSchema.getChgAmnt());
			newLPEdorAppSchema.setGetMoney(xLPEdorItemSchema.getGetMoney());
			newLPEdorAppSchema.setGetInterest(xLPEdorItemSchema.getGetInterest());
			
			newLPEdorAppSchema.setMakeDate(this.mCurrentDate);
			newLPEdorAppSchema.setMakeTime(this.mCurrentTime);
			newLPEdorAppSchema.setModifyDate(this.mCurrentDate);
			newLPEdorAppSchema.setModifyTime(this.mCurrentTime);
			Insert_LPEdorAppSet.add(newLPEdorAppSchema);
			
			//修改lpedoritem,lpedormain中的受理号
			LPEdorItemSchema newLPEdorItemSchema = new LPEdorItemSchema();
			mReflections.transFields(newLPEdorItemSchema, xLPEdorItemSchema);
			
			newLPEdorItemSchema.setEdorAcceptNo(strEdorAcceptNo);
			newLPEdorItemSchema.setModifyDate(this.mCurrentDate);
			newLPEdorItemSchema.setModifyTime(this.mCurrentTime);
			Insert_LPEdorItemSet.add(newLPEdorItemSchema);
			
			LPEdorMainSchema xLPEdorMainSchema = new LPEdorMainSchema();
			LPEdorMainDB xLPEdorMainDB = new LPEdorMainDB();
			xLPEdorMainDB.setEdorAcceptNo(this.first_EdorAcceptNo);
			xLPEdorMainDB.setEdorNo(xEdorNo);
			if(xLPEdorMainDB.query().size()==0)
			{
				CError tError = new CError();
				tError.moduleName = "CustomerBQSplit";
				tError.functionName = "dealData";
				tError.errorMessage = "查询批单号"+xEdorNo+"下保全主表信息(lpedormain)失败!";
				this.mErrors.addOneError(tError);
			}
			xLPEdorMainSchema=xLPEdorMainDB.query().get(1);
			Delete_LPEdorMainSet.add(xLPEdorMainSchema); //删除老记录
			
			LPEdorMainSchema newLPEdorMainSchema = new LPEdorMainSchema();
			mReflections.transFields(newLPEdorMainSchema, xLPEdorMainSchema);
			
			newLPEdorMainSchema.setEdorAcceptNo(strEdorAcceptNo);
			newLPEdorMainSchema.setModifyDate(this.mCurrentDate);
			newLPEdorMainSchema.setModifyTime(this.mCurrentTime);
			Insert_LPEdorMainSet.add(newLPEdorMainSchema);
			
			//修改lpconttempinfo.icedoracceptno字段，置上拆分后的受理号
			tLPContTempInfoDB = new LPContTempInfoDB();
			tLPContTempInfoSchema  = new LPContTempInfoSchema();
			
			tLPContTempInfoDB.setEdorNo(xEdorNo);
			tLPContTempInfoDB.setEdorType(xLPEdorItemSchema.getEdorType());
			tLPContTempInfoDB.setContNo(xContno);
			tLPContTempInfoSchema=tLPContTempInfoDB.query().get(1);
			tLPContTempInfoSchema.setICEdorAcceptNo(strEdorAcceptNo);
			tLPContTempInfoSchema.setModifyDate(this.mCurrentDate);
			tLPContTempInfoSchema.setModifyTime(this.mCurrentTime);
			UPDate_LPContTempInfoSet.add(tLPContTempInfoSchema);
			
			//ImageFlag="1"时复制扫描件
			if("1".equals(ImageFlag))
			{
				ES_DOC_MAINSchema  new_ES_DOC_MAINSchema = new ES_DOC_MAINSchema();
				mReflections.transFields(new_ES_DOC_MAINSchema, ole_ES_DOC_MAINSchema);
				String tDocID = getMaxNo("DocID");
				new_ES_DOC_MAINSchema.setDocCode(strEdorAcceptNo);
				new_ES_DOC_MAINSchema.setDocID(tDocID);
				new_ES_DOC_MAINSchema.setMakeDate(this.mCurrentDate);
				new_ES_DOC_MAINSchema.setMakeTime(this.mCurrentTime);
				new_ES_DOC_MAINSchema.setModifyDate(this.mCurrentDate);
				new_ES_DOC_MAINSchema.setModifyTime(this.mCurrentTime);
				Inset_ES_DOC_MAINSet.add(new_ES_DOC_MAINSchema);
				
				for(int a=1;a<=old_ES_DOC_PAGESSet.size();a++)
				{
					ES_DOC_PAGESSchema  new_ES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
					mReflections.transFields(new_ES_DOC_PAGESSchema, old_ES_DOC_PAGESSet.get(a));
					new_ES_DOC_PAGESSchema.setDocID(tDocID);
					String tPageID;
					tPageID = getMaxNo("PageID");
					new_ES_DOC_PAGESSchema.setPageID(tPageID);
					new_ES_DOC_PAGESSchema.setMakeDate(this.mCurrentDate);
					new_ES_DOC_PAGESSchema.setMakeTime(this.mCurrentTime);
					new_ES_DOC_PAGESSchema.setModifyDate(this.mCurrentDate);
					new_ES_DOC_PAGESSchema.setModifyTime(this.mCurrentTime);
					Inset_ES_DOC_PAGESSet.add(new_ES_DOC_PAGESSchema);
				}
				
				for(int a=1;a<=old_ES_DOC_RELATIONSet.size();a++)
				{
					ES_DOC_RELATIONSchema  new_ES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
					mReflections.transFields(new_ES_DOC_RELATIONSchema, old_ES_DOC_RELATIONSet.get(a));
					new_ES_DOC_RELATIONSchema.setDocID(tDocID);
					new_ES_DOC_RELATIONSchema.setBussNo(strEdorAcceptNo);
					Inset_ES_DOC_RELATIONSet.add(new_ES_DOC_RELATIONSchema);
				}
			}
			
			//然后处理工作流
			String new_MissionID = PubFun1.CreateMaxNo("MissionID", 20);
			for(int x=1;x<=first_LWMissionSet.size();x++)
			{
				LWMissionSchema new_LWMissionSchema = new LWMissionSchema();
				mReflections.transFields(new_LWMissionSchema, first_LWMissionSet.get(x));
				new_LWMissionSchema.setMissionID(new_MissionID);//替换新的任务号
				new_LWMissionSchema.setMissionProp1(strEdorAcceptNo);//替换新的保全受理号
				new_LWMissionSchema.setMakeDate(this.mCurrentDate);
				new_LWMissionSchema.setMakeTime(this.mCurrentTime);
				new_LWMissionSchema.setModifyDate(this.mCurrentDate);
				new_LWMissionSchema.setModifyTime(this.mCurrentTime);
				//更正投保人信息???
			    new_LWMissionSchema.setMissionProp11(xAppntName);
			    
			    Inset_LWMissionSet.add(new_LWMissionSchema);
			}
			for(int y=1;y<=first_LBMissionSet.size();y++)
			{
				LBMissionSchema new_LBMissionSchema = new LBMissionSchema();
				mReflections.transFields(new_LBMissionSchema, first_LBMissionSet.get(y));
				String new_SerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
				new_LBMissionSchema.setSerialNo(new_SerielNo);
				new_LBMissionSchema.setMissionID(new_MissionID);//替换新的任务号
				new_LBMissionSchema.setMissionProp1(strEdorAcceptNo);//替换新的保全受理号
				new_LBMissionSchema.setMakeDate(this.mCurrentDate);
				new_LBMissionSchema.setMakeTime(this.mCurrentTime);
				new_LBMissionSchema.setModifyDate(this.mCurrentDate);
				new_LBMissionSchema.setModifyTime(this.mCurrentTime);
				//更正投保人信息???
				new_LBMissionSchema.setMissionProp11(xAppntName);
				
				Inset_LBMissionSet.add(new_LBMissionSchema);
			}
		}
		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}
	
	// 生成流水号，包含错误处理
	private String getMaxNo(String cNoType) {
		String strNo = PubFun1.CreateMaxNo(cNoType, 1);

		if (strNo.equals("") || strNo.equals("0")) {
			// @@错误处理
			CError.buildErr(this, "生成流水号失败！ ");
			strNo = "";
		}

		return strNo;
	}
	/**
	 * 返回处理结果
	 * 
	 * @return: MMap
	 */
	public MMap getMap() {
		return mMMap;
	}

	public static void main(String[] args) {
		CustomerBQSplit tCustomerBQSplit = new CustomerBQSplit();
		 TransferData tTransferData = new TransferData();
		 tTransferData.setNameAndValue("GetNoticeNo","86110020090310058707");
	     tTransferData.setNameAndValue("ContNo","86110020040210039433");
	     tTransferData.setNameAndValue("ReaSonType","02");
	     tTransferData.setNameAndValue("ReMark","ceshi");
	   
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		tGI.ComCode = "86";

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGI);
		tCustomerBQSplit.submitData(tVData, "BQApp");
	}
}
