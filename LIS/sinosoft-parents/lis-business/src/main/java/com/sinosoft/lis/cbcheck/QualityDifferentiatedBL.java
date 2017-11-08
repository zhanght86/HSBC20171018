package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LATreeDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDUWDifferenceBSchema;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.vschema.LATreeSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDUWDifferenceBSet;
import com.sinosoft.lis.vschema.LMCalModeSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class QualityDifferentiatedBL {
private static Logger logger = Logger.getLogger(QualityDifferentiatedBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	/** 机构代码 */
	private String mManageCom;
	/** 业务员代码 */
	private String mAgentCode;
	/** 业务员类别 */
	private String mUWClass;
	/** 差异化级别 */
	private String mUWLevel;
	/** 当前操作员 */
	private String mOperate;
	/** 当前日期 */
	private String mCurrentDate = PubFun.getCurrentDate();
	/** 当前时间 */
	private String mCurrentTime = PubFun.getCurrentTime();
	private MMap mMMap = new MMap();

//	private LATreeSet mLATreeSet = new LATreeSet();
	private ExeSQL mExeSQL = new ExeSQL();

	public QualityDifferentiatedBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!checkDate()) {
			return false;
		}
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		// 准备返回的数据
		prepareOutputData();

		
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = tGlobalInput.Operator;
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError.buildErr(this, "传输数据失败！");
			return false;
		}
		mUWLevel = (String) mTransferData.getValueByName("UWLevel");
		mUWClass = (String) mTransferData.getValueByName("UWClass");
		mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		return true;
	}

	/**
	 * 校验数据合法性
	 * */
	private boolean checkDate() {
		if ((mAgentCode == null || "".equals(mAgentCode))
				&& (mManageCom == null || "".equals(mManageCom))) {
			CError.buildErr(this, "管理机构、业务员代码全部为空，无法修改！");
			return false;
		}
		return true;
	}

	/**
	 * 修改业务员差异化核保级别 此处处理逻辑为如果只录了业务员代码则修改该业务员的差异化级别
	 * 如果录入了机构代码，则对该机构下的所有业务员的差异化级别进行修改
	 */
	private boolean dealData() {
		// 先初始化差异化核保数据备份表
		LDUWDifferenceBSet tLDUWDifferenceBSet = new LDUWDifferenceBSet();;
		// 如果机构代码不为空则将该机构下的所有业务员都修改
		//为防止内存溢出  按机构修改时改为1000条数据提交一次
		if (mAgentCode == null || "".equals(mAgentCode)) {
			LATreeDB tLATreeDB = new LATreeDB();
			String querySql = "select agentcode from latree where managecom like concat('"+"?managecom?"+"','%')";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(querySql);
			sqlbv.put("managecom", mManageCom);
			SSRS tSSRS = new SSRS();
			tSSRS = mExeSQL.execSQL(sqlbv);
			for (int j=1;j<=Integer.parseInt(String.valueOf(tSSRS.MaxRow/1000));j++){
				LATreeSet tLATreeSet = new LATreeSet();
				tLDUWDifferenceBSet.clear();
				tLATreeSet.clear();
				for(int k=1;k<=1000;k++){
					tLATreeDB.setAgentCode(tSSRS.GetText(1000*(j-1)+k, 1));
					logger.debug("第"+(1000*(j-1)+k+"条记录"));
					if(!tLATreeDB.getInfo()){
						CError.buildErr(this, "未查询到业务员代码为:"+tLATreeDB.getAgentCode()+"的业务员信息");
						return false;
					}
					LDUWDifferenceBSchema tLDUWDifferenceBSchema = new LDUWDifferenceBSchema();
					String tSerielNo = PubFun1.CreateMaxNo("DiffSerieno", 10);
					tLDUWDifferenceBSchema.setSerialNo(StrTool.getYear()+StrTool.getMonth()+tSerielNo);
					tLDUWDifferenceBSchema.setDiffType("1");
					tLDUWDifferenceBSchema.setManageCom(mManageCom);
					tLDUWDifferenceBSchema.setAgentCode(tLATreeDB.getAgentCode());
					tLDUWDifferenceBSchema.setManaUWType(getManageComLevel(mManageCom));
					tLDUWDifferenceBSchema.setUWClass(tLATreeDB.getUWClass());
					tLDUWDifferenceBSchema.setUWLevel(tLATreeDB.getUWLevel());
					tLDUWDifferenceBSchema.setOperator(mOperate);
					tLDUWDifferenceBSchema.setMakeDate(tLATreeDB.getUWModifyDate());
					tLDUWDifferenceBSchema.setMakeTime(tLATreeDB.getUWModifyTime());
					tLDUWDifferenceBSchema.setMakeDate2(mCurrentDate);
					tLDUWDifferenceBSchema.setMakeTime2(mCurrentTime);
					tLDUWDifferenceBSet.add(tLDUWDifferenceBSchema);
					
					//修改数据
					tLATreeDB.setUWClass(mUWClass);
					tLATreeDB.setUWLevel(mUWLevel);
					tLATreeDB.setUWModifyDate(mCurrentDate);
					tLATreeDB.setUWModifyTime(mCurrentTime);
					tLATreeSet.add(tLATreeDB.getSchema());
				}
				MMap tMMap = new MMap();
				if(tLDUWDifferenceBSet.size()>0){
					tMMap.put(tLDUWDifferenceBSet, "INSERT");
				}
				if(tLATreeSet.size()>0){
					tMMap.put(tLATreeSet, "UPDATE");
				}
				VData tResult = new VData();
				tResult.add(tMMap);
				PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(tResult, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError.buildErr(this, "数据提交失败!");
					return false;
				}
			}
			LDUWDifferenceBSet LDUWDifferenceBSet2 = new LDUWDifferenceBSet();
			tLDUWDifferenceBSet.clear();
			LATreeSet tLATreeSet2 = new LATreeSet();
			tLATreeSet2.clear();
			for(int j=1;j<=(tSSRS.MaxRow%1000);j++){
				tLATreeDB = new LATreeDB();
				tLATreeDB.setAgentCode(tSSRS.GetText(Integer.parseInt(String.valueOf(tSSRS.MaxRow/1000))*1000+j, 1));
				logger.debug("第"+String.valueOf(Integer.parseInt(String.valueOf(tSSRS.MaxRow/1000))*1000+j)+"条记录");
				if(!tLATreeDB.getInfo()){
					CError.buildErr(this, "未查询到业务员代码为:"+tLATreeDB.getAgentCode()+"的业务员信息");
					return false;
				}
				LDUWDifferenceBSchema tLDUWDifferenceBSchema = new LDUWDifferenceBSchema();
				
				String tSerielNo = PubFun1.CreateMaxNo("DiffSerieno", 10);
				tLDUWDifferenceBSchema.setSerialNo(StrTool.getYear()+StrTool.getMonth()+tSerielNo);
				tLDUWDifferenceBSchema.setDiffType("1");
				tLDUWDifferenceBSchema.setManageCom(mManageCom);
				tLDUWDifferenceBSchema.setAgentCode(tLATreeDB.getAgentCode());
				tLDUWDifferenceBSchema.setManaUWType(getManageComLevel(mManageCom));
				tLDUWDifferenceBSchema.setUWClass(tLATreeDB.getUWClass());
				tLDUWDifferenceBSchema.setUWLevel(tLATreeDB.getUWLevel());
				tLDUWDifferenceBSchema.setOperator(mOperate);
				tLDUWDifferenceBSchema.setMakeDate(tLATreeDB.getUWModifyDate());
				tLDUWDifferenceBSchema.setMakeTime(tLATreeDB.getUWModifyTime());
				tLDUWDifferenceBSchema.setMakeDate2(mCurrentDate);
				tLDUWDifferenceBSchema.setMakeTime2(mCurrentTime);
				LDUWDifferenceBSet2.add(tLDUWDifferenceBSchema);
				
				//修改数据
				tLATreeDB.setUWClass(mUWClass);
				tLATreeDB.setUWLevel(mUWLevel);
				tLATreeDB.setUWModifyDate(mCurrentDate);
				tLATreeDB.setUWModifyTime(mCurrentTime);
				tLATreeSet2.add(tLATreeDB.getSchema());
			}
			MMap tMMap = new MMap();
			if(LDUWDifferenceBSet2.size()>0){
				tMMap.put(LDUWDifferenceBSet2, "INSERT");
			}
			if(tLATreeSet2.size()>0){ 
				tMMap.put(tLATreeSet2, "UPDATE");
			}
			VData tResult = new VData();
			tResult.add(tMMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError.buildErr(this, "数据提交失败!");
				return false;
			}
		} else {
			LATreeDB tLATreeDB = new LATreeDB();
			tLATreeDB.setAgentCode(mAgentCode);
			LATreeSet tLATreeSet = new LATreeSet();
			tLATreeSet = tLATreeDB.query();
			if (tLATreeSet.size() != 1) {
				CError.buildErr(this, "未查询到代码为" + mAgentCode + "的业务员信息");
				return false;
			} else {
				LDUWDifferenceBSchema tLDUWDifferenceBSchema = new LDUWDifferenceBSchema();
				String tSerielNo = PubFun1.CreateMaxNo("DiffSerieno", 10);
				tLDUWDifferenceBSchema.setSerialNo(StrTool.getYear()+StrTool.getMonth()+tSerielNo);
				tLDUWDifferenceBSchema.setDiffType("1");
				tLDUWDifferenceBSchema.setManageCom(tLATreeSet.get(1).getManageCom());
				tLDUWDifferenceBSchema.setManaUWType(getManageComLevel(tLATreeSet.get(1).getManageCom()));
				tLDUWDifferenceBSchema.setAgentCode(tLATreeSet.get(1).getAgentCode());
				tLDUWDifferenceBSchema.setUWClass(tLATreeSet.get(1).getUWClass());
				tLDUWDifferenceBSchema.setUWLevel(tLATreeSet.get(1).getUWLevel());
				tLDUWDifferenceBSchema.setOperator(mOperate);
				tLDUWDifferenceBSchema.setMakeDate(tLATreeSet.get(1).getUWModifyDate());
				tLDUWDifferenceBSchema.setMakeTime(tLATreeSet.get(1).getUWModifyTime());
				tLDUWDifferenceBSchema.setMakeDate2(mCurrentDate);
				tLDUWDifferenceBSchema.setMakeTime2(mCurrentTime);
				tLDUWDifferenceBSet.add(tLDUWDifferenceBSchema);
				
				tLATreeSet.get(1).setUWClass(mUWClass);
				tLATreeSet.get(1).setUWLevel(mUWLevel);
				tLATreeSet.get(1).setUWModifyDate(mCurrentDate);
				tLATreeSet.get(1).setUWModifyTime(mCurrentTime);
			}
			MMap tMMap = new MMap();
			if(tLDUWDifferenceBSet.size()>0){
				tMMap.put(tLDUWDifferenceBSet, "INSERT");
			}
			if(tLATreeSet.size()>0){
				tMMap.put(tLATreeSet, "UPDATE");
			}
			VData tResult = new VData();
			tResult.add(tMMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError.buildErr(this, "数据提交失败!");
				return false;
			}
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mResult.clear();
//		mResult.add(mMMap);
	}

	public VData getResult() {
		return mResult;
	}
	
	private String getManageComLevel(String tManageCom){
		String tLevel="";
		String tSql = "select comcode from ldcode where codetype='station' and code='"+"?code?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("code", tManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		tLevel = tExeSQL.getOneValue(sqlbv1);
		return tLevel;
	}
}
