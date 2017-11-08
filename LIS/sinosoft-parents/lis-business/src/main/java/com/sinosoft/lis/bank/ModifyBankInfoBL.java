package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 修改客户的银行信息</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

//import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.ModifyBankInfoLogSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.ModifyBankInfoLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ModifyBankInfoBL
{
private static Logger logger = Logger.getLogger(ModifyBankInfoBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 额外传递的参数 */
	private TransferData mTransferData = new TransferData();

	/** 传入的业务数据 */
	// private LCPolSet inLCPolSet = new LCPolSet();
	/** 传出的业务数据 */
//	private LCContSet outLCContSet = new LCContSet();
//	private LCContSchema outLCContSchema = new LCContSchema();
	private LJTempFeeClassSet outLJTempFeeClassSet = new LJTempFeeClassSet();
	// private LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
	private LJSPaySet outLJSPaySet = new LJSPaySet();
	private String mBankCode = "";
	private String mAccName = "";
	private String mAccNo = "";
//	private String mPrtNo2 = "";
//	private String mGetnoticeNo = "";
	//	private String mOthernoType = "";
	private String mPayType = "";
//	private String mPrtNo = "";
//	private String mGetNoticeNo = "";
//	private String mPayMode = "";
	private String mTempfeeNo="";
	private LPEdorAppSet outLPEdorAppSet = new LPEdorAppSet();
	private ModifyBankInfoLogSet mModifyBankInfoLogSet = new ModifyBankInfoLogSet();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();//并发控制

	// MMap mMMap = new MMap();
	public ModifyBankInfoBL()
	{
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mInputData = (VData)cInputData.clone();
		this.mOperate = cOperate;

		try
		{
		// 得到外部传入的数据,将数据备份到本类中
			if(!getInputData())
			{
				return false;
			}
			logger.debug("---End getInputData---");
			if(!mLock.lock(mTempfeeNo, "LB0001", mGlobalInput.Operator))
			{
				CError tError = new CError(mLock.mErrors.getLastError());
				CError.buildErr(this, "该收据号处于其他业务操作中，请稍后再试！");
				this.mErrors.addOneError(tError);
				return false;
		
			}
	
			// 进行业务处理
			if(!dealData())
			{
				return false;
			}
			logger.debug("---End dealData---");
	
			// 需要传到后台处理
			if(mOperate.equals("INSERT"))
			{
				// 准备往后台的数据
				if(!prepareOutputData())
				{
					return false;
				}
	
				logger.debug("End ModifyBankInfo BLS Submit...");
			}
			// 不需要传到后台处理
			else if(mOperate.equals(""))
			{
			}
		}
		catch(Exception ex)
		{
			CError.buildErr(this, ex.toString());
			return false;
		}
		finally
		{
			mLock.unLock();
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData()
	{

		try
		{
			mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
			TransferData mTransferData = new TransferData();
			mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
//			mPayType = (String)mTransferData.getValueByName("FeeType");
			// 获得所有需要修改的值
			//如果是新单收费
			//zy  取消保全的处理，保全相关变更在保全的转账问题件相关功能中调整
//			if("2".equals(mPayType))
//			{
				mBankCode = (String)mTransferData.getValueByName("BankCode");
				mAccName = (String)mTransferData.getValueByName("AccName");
				mAccNo = (String)mTransferData.getValueByName("AccNo");
//				mPrtNo2 = (String)mTransferData.getValueByName("PrtNo2");
				mTempfeeNo = (String)mTransferData.getValueByName("TempfeeNo");
//			}

			//如果为保全收费修改
//			else if("1".equals(mPayType))
//			{
//				mGetNoticeNo = (String)mTransferData.getValueByName("GetNoticeNo");
//				mPayMode = (String)mTransferData.getValueByName("PayMode");
//				mBankCode = (String)mTransferData.getValueByName("BankCode");
//				mAccNo = (String)mTransferData.getValueByName("AccNo");
//				mAccName = (String)mTransferData.getValueByName("AccName");
//			}
//			else
//			{
//
//			}

		}
		catch(Exception e)
		{
			// @@错误处理
			CError.buildErr(this, "接收数据失败!!");
			return false;
		}

		return true;
	}

	/**
	 * 修改保单表
	 * 
	 * @param inLCPolSchema
	 */
	//	private void modifyLCCont(String str) {
	//		// 搜索出该印刷号对应的所有投保单
	//		LCContSet tLCContSet = new LCContSet();
	//		LCContDB tLCContDB = new LCContDB();
	//		tLCContDB.setPrtNo(mPrtNo2);
	//		tLCContSet = tLCContDB.query();
	//		// 如果缴费方式为银行转帐，就要把首期的缴费银行的值设的和续期的值相同
	//		logger.debug("===============" + str);
	//		if (tLCContSet.size() > 0) {
	//			if (str.equals("7")) {
	//				tLCContSet.get(1).setNewAccName(mAccName);
	//				tLCContSet.get(1).setNewBankAccNo(mAccNo);
	//				tLCContSet.get(1).setNewBankCode(mBankCode);
	//				tLCContSet.get(1).setBankCode(mBankCode);
	//				tLCContSet.get(1).setBankAccNo(mAccNo);
	//				tLCContSet.get(1).setAccName(mAccName);
	//			} else if (str.equals("6")) {
	//				if (toChargeStr(tLCContSet.get(1).getNewAccName()).equals(
	//						toChargeStr(tLCContSet.get(1).getAccName()))
	//						&& toChargeStr(tLCContSet.get(1).getBankAccNo())
	//								.equals(
	//										toChargeStr(tLCContSet.get(1)
	//												.getBankAccNo()))
	//						&& toChargeStr(tLCContSet.get(1).getNewBankCode())
	//								.equals(
	//										toChargeStr(tLCContSet.get(1)
	//												.getBankCode()))) {
	//					tLCContSet.get(1).setBankCode(mBankCode);
	//					tLCContSet.get(1).setBankAccNo(mAccNo);
	//					tLCContSet.get(1).setAccName(mAccName);
	//				}
	//
	//				tLCContSet.get(1).setNewAccName(mAccName);
	//				tLCContSet.get(1).setNewBankAccNo(mAccNo);
	//				tLCContSet.get(1).setNewBankCode(mBankCode);
	//			}
	//			// 修改该印刷号对应的所有投保单的银行信息
	//
	//			outLCContSchema = tLCContSet.get(1).getSchema();
	//			outLCContSet.add(outLCContSchema);
	//		}
	//	}
	/**
	 * 对需要提供的银行代码等信息进行校验，如果为null则返回相应的" "值
	 */
	public String toChargeStr(String str)
	{
		if(str == null || str.equals("null") || str.equals(""))
		{
			return " ";
		}
		else
		{
			return str;
		}
	}

	/**
	 * 修改暂交费分类表
	 * 
	 * @param inLCPolSchema
	 */
	private boolean modifyLJTempFeeClass()
	{
		// 搜索出未到帐的该印刷号对应的暂交费信息
		//		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		//		String strSql = "select * from LJTempFeeClass where tempfeeno='"
		//				+ mGetnoticeNo + "' and EnterAccDate is null";
		//		logger.debug(strSql);
		//		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassSchema.getDB()
		//				.executeQuery(strSql);
		//		if ((tLJTempFeeClassSet == null || tLJTempFeeClassSet.size() == 0)
		//				&& (mOthernoType.equals("6") || mOthernoType.equals("7"))) {
		//			CError tError = new CError();
		//			tError.moduleName = "ModifyBankInfoBL";
		//			tError.functionName = "modifyLJTempFeeClass";
		//			tError.errorMessage = "此笔数据出现异常，请联系管理员";
		//			this.mErrors.addOneError(tError);
		//			return false;
		//		}

		//		for (int i = 0; i < tLJTempFeeClassSet.size(); i++) {
		//
		//			tLJTempFeeClassSet.get(i + 1).setBankCode(mBankCode);
		//			tLJTempFeeClassSet.get(i + 1).setBankAccNo(mAccNo);
		//			tLJTempFeeClassSet.get(i + 1).setAccName(mAccName);
		//			outLJTempFeeClassSet.add(tLJTempFeeClassSet);
		//		}
//		String strSql = "select * from ljtempfeeclass a where  EnterAccDate is null and paymode='4' and exists (select 1 from ljtempfee where "
//				+ "otherno='" + mPrtNo2 + "' and tempfeeno=a.tempfeeno and EnterAccDate is null)";
		String strSql = "select * from ljtempfeeclass where enteraccdate is null  and paymode='4' and tempfeeno='"+"?tempfeeno?"+"'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("tempfeeno", mTempfeeNo);
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv1);
		if(tLJTempFeeClassSet==null || tLJTempFeeClassSet.size()<=0)
		{
			CError.buildErr(this, "收据号为"+mTempfeeNo+"的暂收费信息不存在，请核实！");
			return false;
		}
		for(int i = 1; i <= tLJTempFeeClassSet.size(); i++)
		{
			//增加修改轨迹
			mModifyBankInfoLogSet.add(setModifyBankInfoLog(mTempfeeNo,"","",tLJTempFeeClassSet.get(i).getBankCode(),tLJTempFeeClassSet.get(i).getAccName(),tLJTempFeeClassSet.get(i).getBankAccNo(),mPayType,""));

			tLJTempFeeClassSet.get(i).setBankCode(mBankCode);
			tLJTempFeeClassSet.get(i).setBankAccNo(mAccNo);
			tLJTempFeeClassSet.get(i).setAccName(mAccName);
		}
		outLJTempFeeClassSet.add(tLJTempFeeClassSet);
		return true;
	}

	/**
	 * 修改应收总表
	 * 
	 * @param inLCPol
	 *            Schema
	 */
	private boolean modifyLJSPay(String tGetNoticeNo)
	{

		// 根据暂交费分类表的tempfeeno，搜索应收总表的getnoticeno，数据唯一
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(tGetNoticeNo);
		if(tLJSPayDB.getInfo())
		{
			// 如果有银行在途数据，不允许修改
			if(tLJSPayDB.getBankOnTheWayFlag() != null && tLJSPayDB.getBankOnTheWayFlag().equals("1"))
			{
				CError.buildErr(this, "银行在途，无法修改!!");
				return false;

			}
			else
			{
				//				mOthernoType = tLJSPayDB.getOtherNoType();
				tLJSPayDB.setBankCode(mBankCode);
				tLJSPayDB.setBankAccNo(mAccNo);
				tLJSPayDB.setAccName(mAccName);

				outLJSPaySet.add(tLJSPayDB.getSchema());

			}

		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		//		ExeSQL tExeSQL = new ExeSQL();
		//		SSRS tSSRS = new SSRS();
		//		String tSql = "select getnoticeno , othernotype from ljspay where otherno = '"
		//				+ mPrtNo2 + "'";
		//		tSSRS = tExeSQL.execSQL(tSql);
		//		mGetnoticeNo = tSSRS.GetText(1, 1);
		//		// 修改LCCont，
		//
		//		// 修改暂交费分类表
		//		if (tSSRS.GetText(1, 2).equals("6") || tSSRS.GetText(1, 2).equals("7")) {
		//			modifyLCCont(tSSRS.GetText(1, 2));
		//		} else if (tSSRS.GetText(1, 2).equals("2")
		//				|| tSSRS.GetText(1, 2).equals("3")) {
		//			CError tError = new CError();
		//			// tError.moduleName = "ModifyBankInfoBL";
		//			// tError.functionName = "prepareOutputData";
		//			tError.errorMessage = "此保单修改是续期帐户修改，请确认后去做保全修改! ";
		//			this.mErrors.addOneError(tError);
		//			return false;
		//		}

		// 修改应收总表

		//		if (!modifyLJSPay()) {
		//			return false;
		//		}
		//		if (!modifyLJTempFeeClass()) {
		//			return false;
		//		}
		if(mOperate.equals("INSERT"))
		{
			//新单收费的处理
//			if("2".equals(mPayType))
//			{
				if(!modifyLJTempFeeClass())
				{
					CError.buildErr(this, "修改暂收费分类表失败！");
					return false;
				}
				String tGetNo="";
				for(int j = 1; j <= outLJTempFeeClassSet.size(); j++)
				{
					if(tGetNo.equals(outLJTempFeeClassSet.get(j).getTempFeeNo()))
						continue;
					tGetNo = outLJTempFeeClassSet.get(j).getTempFeeNo();
					if(!modifyLJSPay(tGetNo))
					{
						CError.buildErr(this, "修改应收总表失败！");
						return false;
					}
				}
				
//			}
			//保全收费的处理
//			if("1".equals(mPayType))
//			{
//				//修改应收总表ljspay
//				String tSql = "select * from ljspay where getnoticeno='" + mGetNoticeNo + "'";
//				LJSPayDB jLJSPayDB = new LJSPayDB();
//				LJSPaySet jLJSPaySet = jLJSPayDB.executeQuery(tSql);
//				if(jLJSPaySet.size() <= 0)
//				{
//					CError.buildErr(this, "不存在通知书号为" + mGetNoticeNo + "的应收数据，请核实！");
//					return false;
//				}
//				for(int i = 1; i <= jLJSPaySet.size(); i++)
//				{
//					if(!modifyLJSPay(jLJSPaySet.get(i).getGetNoticeNo()))
//					{
//						CError.buildErr(this, "修改应收总表失败！");
//						return false;
//					}
//					if(!modifyLPEdorApp(jLJSPaySet.get(i).getOtherNo(),jLJSPaySet.get(i).getGetNoticeNo()))
//					{
//						CError.buildErr(this, "修改保全申请表失败！");
//						return false;
//					}
//				}
//
//			}
		}
		else if(mOperate.equals(""))
		{
		}
		return true;
	}

	/*
	 * 修该保全申请表中的字段*/
//	private boolean modifyLPEdorApp(String tEdorAcceptNo,String tNo)
//	{
//
//		// 根据暂交费分类表的tempfeeno，搜索应收总表的getnoticeno，数据唯一
//		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
//		String tSql = "select * from lpedorapp where edoracceptno='" + tEdorAcceptNo + "'";
//		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.executeQuery(tSql);
//		if(tLPEdorAppSet.size() <= 0)
//		{
//			CError.buildErr(this, "批改号为" + tEdorAcceptNo + "对应的批改申请信息不存在，请核实！");
//			return false;
//		}
//		else
//		{
//
//			for(int i = 1; i <= tLPEdorAppSet.size(); i++)
//			{
//				//增加修改轨迹
//				mModifyBankInfoLogSet.add(setModifyBankInfoLog(tNo,tEdorAcceptNo,"",tLPEdorAppSet.get(i).getBankCode(),tLPEdorAppSet.get(i).getAccName(),tLPEdorAppSet.get(i).getBankAccNo(),mPayType,tLPEdorAppSet.get(i).getPayForm()));
//
//				tLPEdorAppSet.get(i).setPayForm(mPayMode);
//				tLPEdorAppSet.get(i).setBankCode(mBankCode);
//				tLPEdorAppSet.get(i).setBankAccNo(mAccNo);
//				tLPEdorAppSet.get(i).setAccName(mAccName);
//			}
//		}
//
//		outLPEdorAppSet.add(tLPEdorAppSet);
//		return true;
//	}
	
	/**
	 * 修改账户轨迹表，记录修改账户操作*/
	
	private ModifyBankInfoLogSchema setModifyBankInfoLog(String aGetNoticeNo,String aEdorAcceptNo,String aOtherNo,String aBankCode,String aAccName,String aBankAccNo ,String aChangeType,String aPayMode)
	{
		ModifyBankInfoLogSchema tModifyBankInfoLogSchema =  new ModifyBankInfoLogSchema();
		tModifyBankInfoLogSchema.setGetNoticeNo(aGetNoticeNo);
		tModifyBankInfoLogSchema.setEdorAcceptNo(aEdorAcceptNo);
		tModifyBankInfoLogSchema.setOtherNo(aOtherNo);
		tModifyBankInfoLogSchema.setChangeSerialNo(PubFun1.CreateMaxNo("Serialno", 20));
		tModifyBankInfoLogSchema.setBankCode(aBankCode);
		tModifyBankInfoLogSchema.setAccName(aAccName);
		tModifyBankInfoLogSchema.setBankAccNo(aBankAccNo);
		tModifyBankInfoLogSchema.setChangeType(aChangeType);
		tModifyBankInfoLogSchema.setPayMode(aPayMode);
		tModifyBankInfoLogSchema.setMakeDate(PubFun.getCurrentDate());
		tModifyBankInfoLogSchema.setMakeTime(PubFun.getCurrentTime());
		tModifyBankInfoLogSchema.setModifyDate(PubFun.getCurrentDate());
		tModifyBankInfoLogSchema.setModifyTime(PubFun.getCurrentTime());
		tModifyBankInfoLogSchema.setManageCom(mGlobalInput.ComCode);
		tModifyBankInfoLogSchema.setOperator(mGlobalInput.Operator);
		return tModifyBankInfoLogSchema;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData()
	{

			mInputData.clear();
			MMap tMMap = new MMap();
			//			tMMap.put(outLCContSet, "UPDATE");
			tMMap.put(outLJTempFeeClassSet, "UPDATE");
			tMMap.put(outLJSPaySet, "UPDATE");
			tMMap.put(outLPEdorAppSet, "UPDATE");
			tMMap.put(mModifyBankInfoLogSet, "INSERT");
			mInputData.add(tMMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if(!tPubSubmit.submitData(mInputData, "UPDATE"))
			{
				CError.buildErr(this, "在提交数据时出错! ");
				return false;

			}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult()
	{
		return mResult;
	}

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args)
	{
		GlobalInput tGlobalInput = new GlobalInput();

		String BankCode = "2100050";
		String AccName = "H";
		String AccNo = "111111111";
		String PrtNo2 = "00000000203295";
		logger.debug("BankCode:============================" + BankCode);
		logger.debug("AccName:=============================" + AccName);
		logger.debug("AccNo:===============================" + AccNo);
		logger.debug("=================" + PrtNo2);
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("PrtNo2", PrtNo2);
		mTransferData.setNameAndValue("BankCode", BankCode);
		mTransferData.setNameAndValue("AccName", AccName);
		mTransferData.setNameAndValue("AccNo", AccNo);
		VData inVData = new VData();
		inVData.add(mTransferData);
		inVData.add(tGlobalInput);

		ModifyBankInfoBL ModifyBankInfoBL1 = new ModifyBankInfoBL();
		if(!ModifyBankInfoBL1.submitData(inVData, "INSERT"))
		{

		}
	}
}
