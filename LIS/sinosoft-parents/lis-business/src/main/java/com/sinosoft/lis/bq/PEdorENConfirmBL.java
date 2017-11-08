package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:保全
 * </p>
 * <p>
 * Description:附加险满期不续保ConfirmBL层
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author WangWei
 * @reWrite Nicholas
 * @modify zhangtao 2007-01-08
 * @version 2.0
 */

public class PEdorENConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorENConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	
	private ValidateEdorData2 mValidateEdorData = null;
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;

	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 传输数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private Reflections mReflections = new Reflections();
	private LCContStateSet mLCContStateSet = new LCContStateSet();
	private LPContStateSet mLPContStateSet = new LPContStateSet();

	public PEdorENConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		try {
			
			//采用新的方式进行 CP 互换
//		    String[] chgTables = {"LCDuty","LCGet","LCAppnt","LCInsured","LCCSpec"};
//		    mValidateEdorData.changeData(chgTables);
//		    map.add(mValidateEdorData.getMap());
		    
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();
			Reflections tRef = new Reflections();
			// SSRS tSSRS = null;
			// ExeSQL tExeSQL = new ExeSQL();

			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
			if (!tLPContDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "PEdorENConfirmBL";
				tError.functionName = "exchangCPData";
				tError.errorMessage = "查询保全保单数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LCContSchema updLCContSchema = new LCContSchema();
			tRef.transFields(updLCContSchema, tLPContDB.getSchema());
			updLCContSchema.setOperator(this.mGlobalInput.Operator);
			updLCContSchema.setModifyDate(strCurrentDate);
			updLCContSchema.setModifyTime(strCurrentTime);

			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
			if (!tLCContDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "PEdorENConfirmBL";
				tError.functionName = "exchangCPData";
				tError.errorMessage = "查询保单数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}

			LPContSchema updLPContSchema = new LPContSchema();
			tRef.transFields(updLPContSchema, tLCContDB.getSchema());
			updLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			updLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			// updLPContSchema.setOperator(this.mGlobalInput.Operator);
			updLPContSchema.setModifyDate(strCurrentDate);
			updLPContSchema.setModifyTime(strCurrentTime);

			map.put(updLCContSchema, "DELETE&INSERT");
			map.put(updLPContSchema, "DELETE&INSERT");

			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
			LPPolSet tempLPPolSet = new LPPolSet();
			tempLPPolSet = tLPPolDB.query();
			if (tempLPPolSet == null || tempLPPolSet.size() <= 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorENConfirmBL";
				tError.functionName = "exchangCPData";
				tError.errorMessage = "查询保全保单险种信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LPPolSet updLPPolSet = new LPPolSet();
			LCPolSet updLCPolSet = new LCPolSet();
			LPPremSet updLPPremSet = new LPPremSet();// Add By QianLy on
														// 2006-09-08
			LCPremSet updLCPremSet = new LCPremSet();// Add By QianLy on
														// 2006-09-08
			// double tChgActualPrem = 0.0; //记录总共变化的实际的保费
			// double ChgPrem = 0;//记录总共变化的保费 add by lizhuo at 2005-10-29
			for (int i = 1; i <= tempLPPolSet.size(); i++) {
				// 先交换CP表数据
				LCPolSchema updLCPolSchema = new LCPolSchema();
				tRef.transFields(updLCPolSchema, tempLPPolSet.get(i));
				updLCPolSchema.setOperator(this.mGlobalInput.Operator);
				updLCPolSchema.setModifyDate(strCurrentDate);
				updLCPolSchema.setModifyTime(strCurrentTime);
				updLCPolSet.add(updLCPolSchema);

				// 查询C表对应数据交换到P表
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(updLCPolSchema.getPolNo());
				if (tLCPolDB.getInfo()) {
					LPPolSchema updLPPolSchema = new LPPolSchema();
					tRef.transFields(updLPPolSchema, tLCPolDB.getSchema());
					updLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					updLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					updLPPolSchema.setOperator(this.mGlobalInput.Operator);
					updLPPolSchema.setModifyDate(strCurrentDate);
					updLPPolSchema.setModifyTime(strCurrentTime);
					updLPPolSet.add(updLPPolSchema);
				}

				// Add By QianLy on 2006-09-08----------
				// 对LPPrem表和LCPrem表进行互换
				LPPremDB tLPPremDB = new LPPremDB();
				tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPremDB.setPolNo(updLCPolSchema.getPolNo());
				LPPremSet tempLPPremSet = tLPPremDB.query();
				if (tempLPPremSet == null || tempLPPremSet.size() <= 0) {
					CError tError = new CError();
					tError.moduleName = "PEdorENConfirmBL";
					tError.functionName = "exchangCPData";
					tError.errorMessage = "查询保全保费项表（LPPrem）信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 先交换CP表数据
				LCPremSchema tLCPremSchema = new LCPremSchema();
				updLCPremSet.add(tLCPremSchema);
				tRef.transFields(updLCPremSet, tempLPPremSet);

				// 查询C表对应数据交换到P表
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(updLCPolSchema.getPolNo());
				LCPremSet tLCPremSet = tLCPremDB.query();
				if (tLCPremSet == null || tLCPremSet.size() <= 0) {
					CError tError = new CError();
					tError.moduleName = "PEdorENConfirmBL";
					tError.functionName = "exchangCPData";
					tError.errorMessage = "查询保全保费项表（LCPrem）信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int j = 1; j <= tLCPremSet.size(); j++) {
					LPPremSchema tLPPremSchema = new LPPremSchema();
					tRef.transFields(tLPPremSchema, tLCPremSet.get(j));
					tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPPremSchema.setOperator(this.mGlobalInput.Operator);
					tLPPremSchema.setModifyDate(strCurrentDate);
					tLPPremSchema.setModifyTime(strCurrentTime);
					updLPPremSet.add(tLPPremSchema);
				}

				// 如果险种终止了，则改变险种状态为终止
				// add by jiaqiangli 2009-07-06 不需要处理状态
//				if ("4".equals(updLCPolSchema.getAppFlag())) {
//					if (!changeContState(updLCPolSchema.getContNo(),
//							updLCPolSchema.getInsuredNo(), updLCPolSchema
//									.getPolNo(), "Terminate", "1",
//							mLPEdorItemSchema.getEdorValiDate())) {
//						return false;
//					}
//				}
				// 处理要做不续保处理的险种
				// 先是否续期已抽档但无暂交费记录
				// boolean tOperFeeFlag = false; //默认续期未抽档
				// //查询是否有应收且无暂交费
				// String tSql =
				// " SELECT * FROM LJSPay WHERE OtherNoType in ('1','2','3') " +
				// "and OtherNo = '" + mLPEdorItemSchema.getContNo() + "'";
				// LJSPayDB tLJSPayDB = new LJSPayDB();
				// LJSPaySet tLJSPaySet = new LJSPaySet();
				// tLJSPaySet = tLJSPayDB.executeQuery(tSql);
				// if (tLJSPaySet != null && tLJSPaySet.size() > 0)
				// {
				// String tGetNoticeNo = tLJSPaySet.get(1).getGetNoticeNo();
				//
				// //有续期应收抽档记录
				// //============================= add by lizhuo at 2005-10-29
				// =============Begin
				// //过渡期使用,当在LCPol表中没有续期抽档记录时使用
				// LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
				// LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
				// tLJSPayPersonDB.setPolNo(updLCPolSchema.getPolNo());
				// tLJSPayPersonSet = tLJSPayPersonDB.query();
				// if (tLJSPayPersonSet.size() > 0)
				// {
				// for(int k = 1; k <= tLJSPayPersonSet.size(); k++){
				// ChgPrem += tLJSPayPersonSet.get(k).getSumDuePayMoney();
				// }
				// map.put(tLJSPayPersonSet,"DELETE");
				// }
				// //============================= add by lizhuo at 2005-10-29
				// =============End
				// //续期已抽档但无暂交费
				// //查询LCPol里面与之对应的那条续期抽档记录
				// tSql = "SELECT PolNo FROM LCPol WHERE ContNo='" +
				// updLCPolSchema.getContNo()
				// + "' and RiskCode='" + updLCPolSchema.getRiskCode() +
				// "' and PolNo<>'" + updLCPolSchema.getPolNo() +
				// "' and AppFlag='9'";
				// tSSRS = new SSRS();
				// tSSRS = tExeSQL.execSQL(tSql);
				// if (tSSRS != null && tSSRS.MaxRow > 0)
				// {
				// //有临时数据，需删除
				// String sPolNo_new = tSSRS.GetText(1, 1);
				// tSql = "DELETE FROM LCPol WHERE ContNo='" +
				// updLCPolSchema.getContNo() + "' and PolNo='" +
				// sPolNo_new + "'";
				// map.put(tSql, "DELETE");
				// tSql = "DELETE FROM LCDuty WHERE ContNo='" +
				// updLCPolSchema.getContNo() + "' and PolNo='" +
				// sPolNo_new + "'";
				// map.put(tSql, "DELETE");
				// tSql = "DELETE FROM LCPrem WHERE ContNo='" +
				// updLCPolSchema.getContNo() + "' and PolNo='" +
				// sPolNo_new + "'";
				// map.put(tSql, "DELETE");
				// tSql = "DELETE FROM LCGet WHERE ContNo='" +
				// updLCPolSchema.getContNo() + "' and PolNo='" +
				// sPolNo_new + "'";
				// map.put(tSql, "DELETE");
				// //获得险种应收金额，这里的PolNo是续期新生成的
				// tSql = "SELECT nvl(sum(SumDuePayMoney),0) FROM LJSPayPerson
				// WHERE ContNo='" +
				// updLCPolSchema.getContNo() + "' and PolNo='" +
				// sPolNo_new + "'";
				// SSRS tempSSRS = new SSRS();
				// tempSSRS = tExeSQL.execSQL(tSql);
				// if (tempSSRS != null && tempSSRS.MaxRow > 0)
				// {
				// tChgActualPrem +=
				// Double.parseDouble(tempSSRS.GetText(1, 1));
				// }
				// //卸磨杀驴
				// //续期LJSPayPerson里的PolNo存的是新的PolNo，因此改成下面的写法
				// tSql = "DELETE FROM LJSPayPerson WHERE ContNo='" +
				// updLCPolSchema.getContNo() + "' and PolNo='" +
				// sPolNo_new + "'";
				// //续期LJSPayPerson里的PolNo存的是老的PolNo，因此改成下面的写法
				// // tSql = "DELETE FROM LJSPayPerson WHERE ContNo='" +
				// // updLCPolSchema.getContNo() + "' and PolNo='" +
				// // updLCPolSchema.getPolNo() + "'";
				// map.put(tSql, "DELETE");
				// //删除打印管理子表数据
				// tSql = "DELETE FROM LOPRTManagerSub WHERE GetNoticeNo='" +
				// tGetNoticeNo +
				// "' and OtherNoType='00' and OtherNo='" +
				// updLCPolSchema.getContNo() + "' and RiskCode='" +
				// updLCPolSchema.getRiskCode() + "'";
				// map.put(tSql, "DELETE");
				// }
				// }
			}// end for();
			// 更新LJSPay表
			// 判断去掉不续保的保费后金额是否为0，如果是则将LJSPay里的数据删掉。
			// 注意：删掉后以后做暂交费退费时也许有影响，到时在做处理
			// String tSql = "SELECT SumDuePayMoney FROM LJSPay"
			// + " WHERE OtherNo='" + mLPEdorItemSchema.getContNo() +
			// "' and OtherNoType in ('1','2','3')";
			// tSSRS = new SSRS();
			// tSSRS = tExeSQL.execSQL(tSql);
			// if (tSSRS != null && tSSRS.MaxRow>0)
			// {
			// double tSumDuePayMoney = Double.parseDouble(tSSRS.GetText(1,1));
			// if (tSumDuePayMoney > tChgActualPrem)
			// {
			// //还有其它交费，更新LJSPay表
			// if(ChgPrem != 0){
			// tSql = "UPDATE LJSPay SET SumDuePayMoney=" +
			// // String.valueOf(tSumDuePayMoney-tChgActualPrem) + ","
			// String.valueOf(tSumDuePayMoney-ChgPrem) + "," // modify by lizhuo
			// at 2005-10-29
			// + " Operator='" + this.mGlobalInput.Operator + "',"
			// + " ModifyDate=to_date('" + strCurrentDate +
			// "','YYYY-MM-DD'),"
			// + " ModifyTime='" + strCurrentTime + "'"
			// + " WHERE OtherNo='" + mLPEdorItemSchema.getContNo() +
			// "' and OtherNoType in ('1','2','3')";
			//
			// }
			// else{
			// tSql = "UPDATE LJSPay SET SumDuePayMoney=" +
			// String.valueOf(tSumDuePayMoney-tChgActualPrem) + ","
			// + " Operator='" + this.mGlobalInput.Operator +
			// "',"
			// + " ModifyDate=to_date('" + strCurrentDate +
			// "','YYYY-MM-DD'),"
			// + " ModifyTime='" + strCurrentTime + "'"
			// + " WHERE OtherNo='" + mLPEdorItemSchema.getContNo() +
			// "' and OtherNoType in ('1','2','3')";
			// }
			// map.put(tSql, "UPDATE");
			// }
			// else
			// {
			// //没有其它交费，删除LJSPay表记录
			// tSql = "DELETE FROM LJSPay"
			// + " WHERE OtherNo='" + mLPEdorItemSchema.getContNo()
			// + "' and OtherNoType in ('1','2','3')";
			// map.put(tSql, "DELETE");
			// }
			// }

			map.put(updLCPolSet, "DELETE&INSERT");
			map.put(updLPPolSet, "DELETE&INSERT");
			map.put(updLCPremSet, "DELETE&INSERT");
			map.put(updLPPremSet, "DELETE&INSERT");

			map.put(mLCContStateSet, "DELETE&INSERT");
			map.put(mLPContStateSet, "DELETE&INSERT");

			mResult.clear();
			mResult.add(map);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorENConfirmBL";
			tError.functionName = "exchangCPData";
			tError.errorMessage = "数据处理错误！ " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

	    mEdorNo = mLPEdorItemSchema.getEdorNo();
	    mEdorType = mLPEdorItemSchema.getEdorType();
	    mContNo = mLPEdorItemSchema.getContNo();
	    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
	    
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	// private boolean prepareOutputData() {
	// try {
	// mResult.clear();
	// mResult.add(map);
	// }
	// catch (Exception ex) {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "PEdorENConfirmBL";
	// tError.functionName = "prepareOutputData";
	// tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
	// mErrors.addOneError(tError);
	// return false;
	// }
	//
	// return true;
	// }
	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 改变保单险种的状态
	 * 
	 * @param tContNo
	 * @param tInsuredNo
	 * @param tPolNo
	 * @param tStateType
	 * @param tState
	 * @param tNewStateDate
	 * @return boolean
	 *         true--成功，false--失败。结果放在mLCContStateSet和mLPContStateSet变量中（累计）
	 */
	private boolean changeContState(String tContNo, String tInsuredNo,
			String tPolNo, String tStateType, String tState,
			String tNewStateDate) {
		try {
			// 当前日期时间
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			LCContStateSchema tLCContStateSchema = null;
			LPContStateSchema tLPContStateSchema = null;
			// 先查询当前状态是否是要改变的状态，如果是，则保持
			String tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='?tContNo?'" + " and InsuredNo='?tInsuredNo?'"
					+ " and PolNo='?tPolNo?'" + " and StateType='?tStateType?'" + " and State='?tState?'"
					+ " and EndDate is null";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSql);
			sbv1.put("tContNo", tContNo);
			sbv1.put("tInsuredNo", tInsuredNo);
			sbv1.put("tPolNo", tPolNo);
			sbv1.put("tStateType", tStateType);
			sbv1.put("tState", tState);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sbv1);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}

			// 按张容所说，如果新旧状态主键重复（指在C表中重复），则直接用新状态把旧状态替掉。2005-09-07日修改。
			LCContStateDB tLCContStateDB = new LCContStateDB();
			tLCContStateDB.setContNo(tContNo);
			tLCContStateDB.setInsuredNo(tInsuredNo);
			tLCContStateDB.setPolNo(tPolNo);
			tLCContStateDB.setStateType(tStateType);
			tLCContStateDB.setStartDate(tNewStateDate);
			if (!tLCContStateDB.getInfo()) {
				// 查询现在状态，将此状态结束
				tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='?tContNo?'" + " and InsuredNo='?tInsuredNo?'"
						+ " and PolNo='?tPolNo?'" + " and StateType='?tStateType?'" + " and EndDate is null";
				sbv1=new SQLwithBindVariables();
				sbv1.sql(tSql);
				sbv1.put("tContNo", tContNo);
				sbv1.put("tInsuredNo", tInsuredNo);
				sbv1.put("tPolNo", tPolNo);
				sbv1.put("tStateType", tStateType);
				tLCContStateDB = new LCContStateDB();
				LCContStateSet tLCContStateSet = new LCContStateSet();
				tLCContStateSet = tLCContStateDB.executeQuery(sbv1);
				if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
					// 备份状态到P表
					tLPContStateSchema = new LPContStateSchema();
					this.mReflections.transFields(tLPContStateSchema,
							tLCContStateSet.get(1));
					tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPContStateSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPContStateSchema.setOperator(mGlobalInput.Operator);
					tLPContStateSchema.setModifyDate(tCurrentDate);
					tLPContStateSchema.setModifyTime(tCurrentTime);
					mLPContStateSet.add(tLPContStateSchema);

					// 更新C表
					tLCContStateSchema = new LCContStateSchema();
					tLCContStateSchema = tLCContStateSet.get(1);
					tLCContStateSchema.setEndDate(PubFun.calDate(tNewStateDate,
							-1, "D", null)); // 状态在前一天结束
					tLCContStateSchema.setOperator(mGlobalInput.Operator);
					tLCContStateSchema.setModifyDate(tCurrentDate);
					tLCContStateSchema.setModifyTime(tCurrentTime);
					mLCContStateSet.add(tLCContStateSchema);
				}
			} else {
				// 把主键重复的那条记录换到P表
				tLPContStateSchema = new LPContStateSchema();
				this.mReflections.transFields(tLPContStateSchema,
						tLCContStateDB.getSchema());
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPContStateSchema.setOperator(mGlobalInput.Operator);
				tLPContStateSchema.setModifyDate(tCurrentDate);
				tLPContStateSchema.setModifyTime(tCurrentTime);
				mLPContStateSet.add(tLPContStateSchema);
			}

			// 新状态信息
			tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setContNo(tContNo);
			// tLCContStateSchema.setInsuredNo(tInsuredNo);
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setPolNo(tPolNo);
			tLCContStateSchema.setStateType(tStateType);
			tLCContStateSchema.setState(tState);
			if (tStateType != null && tStateType.equals("Terminate")) {
				tLCContStateSchema.setStateReason("01");
			}
			tLCContStateSchema.setStartDate(tNewStateDate);
			tLCContStateSchema.setOperator(mGlobalInput.Operator);
			tLCContStateSchema.setMakeDate(tCurrentDate);
			tLCContStateSchema.setMakeTime(tCurrentTime);
			tLCContStateSchema.setModifyDate(tCurrentDate);
			tLCContStateSchema.setModifyTime(tCurrentTime);
			mLCContStateSet.add(tLCContStateSchema);
			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorENDetailBL";
			tError.functionName = "changePolState";
			tError.errorMessage = "修改保单险种状态时产生错误！保单号：" + tContNo + "，险种号："
					+ tPolNo;
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	// 此函数测试用：
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo("6120060911000023");
		tLPEdorItemSchema = tLPEdorItemDB.query().get(1);

		PEdorENConfirmBL tPEdorENConfirmBL = new PEdorENConfirmBL();

		CErrors tError = null;
		// 后面要执行的动作：删除，添加

		String tRela = "";
		String FlagStr = "";
		String Content = "";
		String Result = "";

		// 添加“保全保单险种责任表”记录
		String theCurrentDate = PubFun.getCurrentDate();
		String theCurrentTime = PubFun.getCurrentTime();

		try {
			// 准备传输数据 VData
			VData tVData = new VData();

			// 保存个人保单信息(保全)
			tVData.addElement(tGlobalInput);
			tVData.addElement(tLPEdorItemSchema);
			// tVData.addElement(tLJSGetDrawSet);
			// tVData.addElement(tLPDutySchema);
			// tVData.addElement(tLPGetSchema);
			boolean tag = tPEdorENConfirmBL.submitData(tVData, "");
			if (tag) {
				logger.debug("Successful");
			} else {
				logger.debug("Fail");
			}
		} catch (Exception ex) {
			Content = "失败，原因是:" + ex.toString();
			logger.debug(Content);
			FlagStr = "Fail";
		}
		// 如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr.equals("")) {
			logger.debug("------success");
			tError = tPEdorENConfirmBL.mErrors;
			if (!tError.needDealError()) {
				Content = " 保存成功";
				FlagStr = "Success";
			} else {
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
		logger.debug("OK!");
	}

}
