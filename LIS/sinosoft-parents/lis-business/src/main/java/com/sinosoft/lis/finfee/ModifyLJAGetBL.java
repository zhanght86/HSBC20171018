package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 付费方式修改功能</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBAGetForPayModeSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.vschema.LBAGetForPayModeSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class ModifyLJAGetBL {
private static Logger logger = Logger.getLogger(ModifyLJAGetBL.class);
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
	// private TransferData mTransferData = null;
	private Reflections mReflections = new Reflections();

	/** 传入的业务数据 */
	private LJAGetSet inLJAGetSet = new LJAGetSet();

	/** 传出的业务数据 */
	private LJAGetSet outLJAGetSet = new LJAGetSet();
	private LBAGetForPayModeSet outLBAGetForPayModeSet = new LBAGetForPayModeSet();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();//并发控制
	public ModifyLJAGetBL() {
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
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		try{
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("---End dealData---");

		// 需要传到后台处理
		if (mOperate.equals("INSERT")) {
			
			// 准备往后台的数据
			if (!prepareOutputData())
				return false;
			logger.debug("---End prepareOutputData---");
			PubSubmit ps = new PubSubmit();
			if(!ps.submitData(mInputData))
			{
				CError.buildErr(this, "提交数据库失败!");
				return false;
			}

//			logger.debug("Start ModifyLJAGet BLS Submit...");
//			ModifyLJAGetBLS tModifyLJAGetBLS = new ModifyLJAGetBLS();
//			if (tModifyLJAGetBLS.submitData(mInputData, cOperate) == false) {
//				// @@错误处理
//				this.mErrors.copyAllErrors(tModifyLJAGetBLS.mErrors);
//				mResult.clear();
//				mResult.add(mErrors.getFirstError());
//				return false;
//			} else {
//				mResult = tModifyLJAGetBLS.getResult();
//			}
			logger.debug("End ModifyLJAGet BLS Submit...");
		}
		// 不需要传到后台处理
		else if (mOperate.equals("")) {
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
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			// mTransferData =
			// (TransferData)mInputData.getObjectByObjectName("TransferData",
			// 0);

			inLJAGetSet = (LJAGetSet) mInputData.getObjectByObjectName(
					"LJAGetSet", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError.buildErr(this, "接收数据失败!!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

			if (mOperate.equals("INSERT")) {
				String[] tOperatedNo = new String[inLJAGetSet.size()];
				LJAGetSchema tLJAGetSchema = new LJAGetSchema();
				for (int i = 0; i < inLJAGetSet.size(); i++) {
					tLJAGetSchema = inLJAGetSet.get(i + 1);
					
					LJAGetDB tLJAGetDB = new LJAGetDB();
					tLJAGetDB.setActuGetNo(tLJAGetSchema.getActuGetNo());
				//xuyunpeng add	
					String tSql = "select currency from LJAGet where 1 =1 "
						+ " and ActuGetNo='"
						+ "?ActuGetNo?'";

				logger.debug("--按保单、理赔类型进行的分组信息，分别进行计算:"+tSql);
				SQLwithBindVariables sqlbv5 =new SQLwithBindVariables();
				sqlbv5.sql(tSql);
				sqlbv5.put("ActuGetNo", tLJAGetSchema.getActuGetNo());
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = tExeSQL.execSQL(sqlbv5);
					
					if(tSSRS!=null){
						String[][] aCurrency =tSSRS.getAllData();
						tLJAGetDB.setCurrency(aCurrency[0][0]);
						
					}
					if (!tLJAGetDB.getInfo())
					{
						CError.buildErr(this, "实付表无数据！");
						return false;
					}

					if (tLJAGetDB.getEnterAccDate() != null)
					{
						CError.buildErr(this, "该笔款项已付清，不能修改付费方式！");
						return false;
					}

					if (tLJAGetDB.getBankOnTheWayFlag() != null
							&& tLJAGetDB.getBankOnTheWayFlag().equals("1"))
					{
						CError.buildErr(this, "该笔付款在送银行途中，不能修改付费方式！");
						return false;
					}
					//zy  2009-04-09 16:45 进行并发控制
					tOperatedNo[i]=tLJAGetSchema.getActuGetNo();
					// 备份
					LBAGetForPayModeSchema tLBAGetForPayModeSchema = new LBAGetForPayModeSchema();
					logger.debug("ljaget----"
							+ tLJAGetDB.getSchema().getActuGetNo());
					mReflections.transFields(tLBAGetForPayModeSchema, tLJAGetDB
							.getSchema());
					tLBAGetForPayModeSchema.setChangeSerialNo(PubFun1
							.CreateMaxNo("LBAGet", 20));
					tLBAGetForPayModeSchema.setOperator(mGlobalInput.Operator);
					tLBAGetForPayModeSchema.setManageCom(mGlobalInput.ComCode);
					tLBAGetForPayModeSchema.setModifyDate(PubFun.getCurrentDate());
					tLBAGetForPayModeSchema.setModifyTime(PubFun.getCurrentTime());
					outLBAGetForPayModeSet.add(tLBAGetForPayModeSchema);
					// <!--zy 2009-04-09 16:45 优化付费方式变更-->
					// 变更
					if(!("".equals(tLJAGetSchema.getPayMode()) ||tLJAGetSchema.getPayMode() ==null ))
					{
						tLJAGetDB.setPayMode(tLJAGetSchema.getPayMode());
					}
					if(!("".equals(tLJAGetSchema.getBankCode()) ||tLJAGetSchema.getBankCode() ==null ))
					{
						tLJAGetDB.setBankCode(tLJAGetSchema.getBankCode());
					}
					if(!("".equals(tLJAGetSchema.getBankAccNo()) ||tLJAGetSchema.getBankAccNo() ==null ))
					{
						tLJAGetDB.setBankAccNo(tLJAGetSchema.getBankAccNo());
					}
					if(!("".equals(tLJAGetSchema.getAccName()) ||tLJAGetSchema.getAccName() ==null) )
					{
						tLJAGetDB.setAccName(tLJAGetSchema.getAccName());
					}
					if(!("".equals(tLJAGetSchema.getDrawerType()) ||tLJAGetSchema.getDrawerType() ==null ))
					{
						tLJAGetDB.setDrawerType(tLJAGetSchema.getDrawerType());
					}
					if(!("".equals(tLJAGetSchema.getDrawerID()) ||tLJAGetSchema.getDrawerID() ==null) )
					{
						tLJAGetDB.setDrawerID(tLJAGetSchema.getDrawerID());
					}
					outLJAGetSet.add(tLJAGetDB.getSchema());
					//进行并发组的控制
					if(!mLock.lock(tOperatedNo, "LF0003", mGlobalInput.Operator))
					{
						CError tError = new CError(mLock.mErrors.getLastError());
						CError.buildErr(this, "该收据号处于其他业务操作中，请稍后再试！");
						this.mErrors.addOneError(tError);
						return false;
				
					}
				}
			} else if (mOperate.equals("")) {
			}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {

			mInputData.clear();
			MMap mp = new MMap();
			mp.put(outLJAGetSet, "UPDATE");
			mp.put(outLBAGetForPayModeSet, "INSERT");
			mInputData.add(mp);

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args) {
		ModifyLJAGetBL ModifyLJAGetBL1 = new ModifyLJAGetBL();
	}
}
