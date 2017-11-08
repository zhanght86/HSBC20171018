

/**
 * <p>Title: PDAlgoTempLib</p>
 * <p>Description: 算法模板库</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */

package com.sinosoft.productdef;

import java.util.ArrayList;
import java.util.Date;

import com.sinosoft.lis.db.LPRiskToRateDB;
import com.sinosoft.lis.pubfun.ExtPubSubmit;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LPRiskToRateSchema;
import com.sinosoft.lis.schema.LPRiskToRateTraceSchema;
import com.sinosoft.lis.vschema.LPRiskToRateSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LDRiskToRateAuditBL implements BusinessService{
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LPRiskToRateSchema mRiskToRateSchema = new LPRiskToRateSchema();
	
	private MMap map = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mPD_TableName = "";

	private String mTableName = "";

	private String mAuditConclusion = "";

	private String mRiskCode = "";

	private String mRiskName = "";

	private String mRateType = "";

	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	public LDRiskToRateAuditBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			this.mInputData = cInputData;
			this.mOperate = cOperate;
			
			if (!getInputData()) {
				return false;
			}

			if (!check()) {
				return false;
			}
			
			long l = new Date().getTime();
			// 进行业务处理
			if (!deal(cInputData, cOperate)) {
				return false;
			}
			System.out.println("运行时间：" + (new Date().getTime() - l) / 1000);
		} catch (Exception ex) {
			this.mErrors.addOneError(ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean deal(VData cInputData, String cOperate) throws Exception {
		dealLPRiskToRate();
		dealLPRiskToRateTrace();
		if("2".equals(mOperate)){
			this.map.put("delete from LPRiskToRate where riskcode='"+mRiskCode+"' and ratetype='"+mRateType+"' and ratetable='"+mTableName+"'", "DELETE");
		}else{
			dealChangeData();
		}
		mInputData.clear();
		mInputData.add(map);
		if (!pubSubmit()) {
			this.mErrors.addOneError("提交数据库操作失败");
			return false;
		}
		return true;
	}

	private void dealChangeData(){
		String preTableName = mTableName;
		ExeSQL tExeSQL = new ExeSQL();
//		String sql1 = "select count(*) from user_tab_columns where upper(table_name)='"+mTableName+"'";
//		int tCol = Integer.parseInt(tExeSQL.getOneValue(sql1));
		String sql2 = "select * from "+preTableName+"";
		SSRS tSsrs1 = tExeSQL.execSQL(sql2);
		String sql3 = "select * from "+mPD_TableName+"";
		SSRS tSsrs2 = tExeSQL.execSQL(sql3);
		this.map.put("delete from "+mPD_TableName+"", "DELETE");
		this.map.put("delete from "+preTableName+"", "DELETE");
		for(int i=1;i<=tSsrs1.MaxRow;i++){
			StringBuffer sqlInfo = new StringBuffer("'");
			for(int j=1;j<=tSsrs1.MaxCol;j++){
				if(j==tSsrs1.MaxCol){
					sqlInfo.append(tSsrs1.GetText(i, j)+"'");
				}else
					sqlInfo.append(tSsrs1.GetText(i, j)+"','");
			}
			this.map.put("insert into "+mPD_TableName+" values("+sqlInfo.toString()+")", "INSERT");
		}
		for(int i=1;i<=tSsrs2.MaxRow;i++){
			StringBuffer sqlInfo = new StringBuffer("'");
			for(int j=1;j<=tSsrs2.MaxCol;j++){
				if(j==tSsrs1.MaxCol){
					sqlInfo.append(tSsrs2.GetText(i, j)+"'");
				}else
					sqlInfo.append(tSsrs2.GetText(i, j)+"','");
			}
			this.map.put("insert into "+preTableName+" values("+sqlInfo.toString()+")", "INSERT");
		}
	}
	
	private boolean check() {
		LPRiskToRateDB tRiskToRateDB = new LPRiskToRateDB();
		tRiskToRateDB.setRateTable(this.mTableName);
		tRiskToRateDB.setRateType(this.mRateType);
		tRiskToRateDB.setRiskCode(this.mRiskCode);
		if(!tRiskToRateDB.getInfo()){
			this.mErrors.addOneError("传入数据错误，无该记录信息!");
			return false;
		}else
			return true;
	}

	private void dealLPRiskToRateTrace(){
		LPRiskToRateTraceSchema tRiskToRateTraceSchema = new LPRiskToRateTraceSchema();
		String sql = "select nvl(max(SerialNo),'0') from LPRiskToRateTrace where RiskCode='"+mRiskCode+"'and RateType='"+mRateType+"' and RateTable='"+mTableName+"'";
		ExeSQL tExeSQL = new ExeSQL();
		String aSerialNo = tExeSQL.getOneValue(sql);
		String tSerialNo = "1";
		if(aSerialNo!=null&&!"".equals(tSerialNo)){
			tSerialNo = String.valueOf((Integer.parseInt(aSerialNo)+1));
		}
		tRiskToRateTraceSchema.setRiskCode(mRiskCode);
		tRiskToRateTraceSchema.setRiskName(mRiskName);
		tRiskToRateTraceSchema.setRateType(mRateType);
		tRiskToRateTraceSchema.setRateTable(mTableName);
		tRiskToRateTraceSchema.setRemark(mRiskToRateSchema.getRemark());
		tRiskToRateTraceSchema.setOperator(mRiskToRateSchema.getOperator());
		tRiskToRateTraceSchema.setSerialNo(tSerialNo);
		tRiskToRateTraceSchema.setMakeDate(mRiskToRateSchema.getMakeDate());
		tRiskToRateTraceSchema.setMakeTime(mRiskToRateSchema.getMakeTime());
		tRiskToRateTraceSchema.setModifyDate(mRiskToRateSchema.getModifyDate());
		tRiskToRateTraceSchema.setModifyTime(mRiskToRateSchema.getMakeTime());
		tRiskToRateTraceSchema.setAuditFlag(mRiskToRateSchema.getAuditFlag());
		tRiskToRateTraceSchema.setAuditor(mRiskToRateSchema.getAuditor());
		tRiskToRateTraceSchema.setAuditConclusion(mAuditConclusion);
		this.map.put(tRiskToRateTraceSchema, "DELETE&INSERT");
	}
	
	private void dealLPRiskToRate() {
		LPRiskToRateDB tRiskToRateDB = new LPRiskToRateDB();
		tRiskToRateDB.setRateTable(this.mTableName);
		tRiskToRateDB.setRateType(this.mRateType);
		tRiskToRateDB.setRiskCode(this.mRiskCode);
		LPRiskToRateSet tRiskToRateSet = tRiskToRateDB.query();
		mRiskToRateSchema = tRiskToRateSet.get(1);
		mRiskToRateSchema.setModifyDate(mCurrentDate);
		mRiskToRateSchema.setModifyTime(mCurrentTime);
		mRiskToRateSchema.setAuditor(mGlobalInput.Operator);
		mRiskToRateSchema.setAuditFlag(mOperate);
		mRiskToRateSchema.setAuditConclusion(this.mAuditConclusion);
		this.map.put(mRiskToRateSchema, "DELETE&INSERT");
	}

	/**
	 * 提交数据，进行数据库操作
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		ExtPubSubmit tPubSubmit = new ExtPubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			this.mErrors.addOneError("数据提交失败!");
			return false;
		}
		return true;
	}

	/**
	 * 得到传入数据
	 */
	private boolean getInputData() throws Exception {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			this.mErrors.addOneError("无操作员信息，请重新登录!");
			return false;
		}

		if (mTransferData == null) {
			this.mErrors.addOneError("前台传输业务数据失败!");
			return false;
		}

		this.mTableName = (String) mTransferData.getValueByName("TableName");
		mAuditConclusion = (String) mTransferData.getValueByName("AuditConclusion");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		mRiskName = (String) mTransferData.getValueByName("RiskName");
		mRateType = (String) mTransferData.getValueByName("RateType");
		mPD_TableName = "PD_"+mTableName;
		if (mTableName == null || mTableName.equals("")||mRiskCode == null || mRiskCode.equals("")||mRateType == null || mRateType.equals("")) {
			this.mErrors.addOneError("前台传输业务数据失败");
			return false;
		}

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		System.out.println("//////////////" + mErrors.getFirstError());
		return mErrors;
	}

	public VData getResult() {
		return this.mResult;
	}
}

