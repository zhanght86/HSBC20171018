package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 满期降低保额续保回退确认BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorERBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorERBackConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorERBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

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

	public CErrors getErrors() {
		return this.mErrors;
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
			// 需要回退的保全项目
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return this.makeError("getInputData", "接收前台数据失败！");
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			return this.makeError("getInputData", "传入数据有误！");
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			return this.makeError("checkData", "无保全数据！");
		}

		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		if (!"0".equals(tLPEdorItemDB.getEdorState())) {
			// @@错误处理
			return this.makeError("checkData", "此项目尚未确认生效！");
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			// 取消本次操作的结果
			String tSql = "UPDATE LPRNPolAmnt set State='0'," + " Operator='?Operator?',"
					+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD')," + " ModifyTime='?strCurrentTime?'" + " WHERE ContNo='?ContNo?' and EdorNo='?EdorNo?'"
					+ " and EdorType='?EdorType?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSql);
			sbv1.put("Operator", this.mGlobalInput.Operator);
			sbv1.put("strCurrentDate", strCurrentDate);
			sbv1.put("strCurrentTime", strCurrentTime);
			sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
			mMap.put(sbv1, "UPDATE");
			// 恢复上次操作结果（注：这里如果没有上次的结果，则恢复到C表的结果了）
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = "UPDATE LPRNPolAmnt c set State='1'," + " Operator='?Operator?',"
					+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD'),"
					+ " ModifyTime='?strCurrentTime?'"
					+ " WHERE exists(select 'x' from ("
					+ " select aEdorNo,aEdorType from("
					+ " select a.EdorNo as aEdorNo,a.EdorType as aEdorType"
					+ " from LPRNPolAmnt a,LPEdorItem b"
					+ " where a.EdorNo=b.EdorNo and a.EdorType=b.EdorType and a.ContNo=b.ContNo and a.ContNo='?ContNo?'"
					+ " and exists(select 'x' from LPEdorItem where EdorValiDate>=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')>to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='?EdorAcceptNo?' and EdorNo='?EdorNo?' and EdorType='?EdorType?' and ContNo='?ContNo?' and InsuredNo='?InsuredNo?' and PolNo='?PolNo?')"
					+ " and b.EdorState='0'"
					+ " order by b.EdorValiDate desc,b.ModifyDate desc,b.ModifyTime desc) f"
					+ " where rownum=1) g where aEdorNo=c.EdorNo and aEdorType=c.EdorType)";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "UPDATE LPRNPolAmnt c set State='1'," + " Operator='?Operator?',"
						+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD'),"
						+ " ModifyTime='?strCurrentTime?'"
						+ " WHERE exists(select 'x' from ("
						+ " select aEdorNo,aEdorType from("
						+ " select a.EdorNo as aEdorNo,a.EdorType as aEdorType"
						+ " from LPRNPolAmnt a,LPEdorItem b"
						+ " where a.EdorNo=b.EdorNo and a.EdorType=b.EdorType and a.ContNo=b.ContNo and a.ContNo='?ContNo?'"
						+ " and exists(select 'x' from LPEdorItem where EdorValiDate>=b.EdorValiDate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')>to_date(concat(to_char(b.ModifyDate,'YYYY-MM-DD'),b.ModifyTime),'YYYY-MM-DD hh24:mi:ss') and EdorAcceptNo='?EdorAcceptNo?' and EdorNo='?EdorNo?' and EdorType='?EdorType?' and ContNo='?ContNo?' and InsuredNo='?InsuredNo?' and PolNo='?PolNo?')"
						+ " and b.EdorState='0'"
						+ " order by b.EdorValiDate desc,b.ModifyDate desc,b.ModifyTime desc) f"
						+ " limit 0,1) g where aEdorNo=c.EdorNo and aEdorType=c.EdorType)";	
			}
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tSql);
			sbv2.put("Operator", this.mGlobalInput.Operator);
			sbv2.put("strCurrentDate", strCurrentDate);
			sbv2.put("strCurrentTime", strCurrentTime);
			sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv2.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
			sbv2.put("InsuredNo", mLPEdorItemSchema.getInsuredNo());
			sbv2.put("PolNo", mLPEdorItemSchema.getPolNo());
			mMap.put(sbv2, "UPDATE");
			// 如果续期已抽档，则撤消
			// 查询是否有应收且无暂交费
			tSql = "SELECT * FROM LJSPay WHERE OtherNoType in ('1','2','3') and OtherNo='?OtherNo?'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(tSql);
			sbv3.put("OtherNo", mLPEdorItemSchema.getContNo());
			LJSPayDB tLJSPayDB = new LJSPayDB();
			LJSPaySet tLJSPaySet = new LJSPaySet();
			tLJSPaySet = tLJSPayDB.executeQuery(sbv3);
			if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
				// 有应收记录，撤消抽档
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("SubmitFlag", "noSubmit"); // 不提交标志
				VData tVData = new VData();
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
				if (tLCContDB.getInfo()) {
					tVData.add(tLCContDB.getSchema());
					tVData.add(tTransferData);
					tVData.add(mGlobalInput);
					IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
					if (!tIndiDueFeeCancelBL.submitData(tVData, "BQApp")) {
						mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
						return false;
					}
					tVData.clear();
					tVData = tIndiDueFeeCancelBL.getResult();
					MMap tMMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
					if (tMMap != null) {
						mMap.add(tMMap);
					}
				}
			}
			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
		}
		return true;
	}

	/**
	 * 创建一个错误
	 * 
	 * @param vFuncName
	 *            发生错误的函数名
	 * @param vErrMsg
	 *            错误信息
	 * @return 布尔值（false--永远返回此值）
	 */
	private boolean makeError(String vFuncName, String vErrMsg) {
		CError tError = new CError();
		tError.moduleName = "PEdorERBackConfirmBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorERBackConfirmBL tPEdorERBackConfirmBL = new PEdorERBackConfirmBL();
	}
}
