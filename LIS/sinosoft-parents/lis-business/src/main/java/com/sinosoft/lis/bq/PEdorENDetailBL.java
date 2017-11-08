package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:保全
 * </p>
 * <p>
 * Description:附加险满期不续保申请BL层
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

public class PEdorENDetailBL {
private static Logger logger = Logger.getLogger(PEdorENDetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LCPolSet mLCPolSet = new LCPolSet();

	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections mReflections = new Reflections();

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	public PEdorENDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}
		// logger.debug("After GetInputData...");

		// 校验暂交费
		if (!checkData()) {
			return false;
		}
		// logger.debug("After checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		// logger.debug("after dealData...");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 删除上次保存过的数据
		if (!clearLastData()) {
			return false;
		}
		//投保人  为核保准备数据
//		LCAppntDB tLCAppntDB = new LCAppntDB();
//		LCAppntSchema tLCAppntSchema = null;
//		LPAppntSet tLPAppntSet = new LPAppntSet();
//		LPAppntSchema tLPAppntSchema = null;
//		
//		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());		
//		tLCAppntSchema=tLCAppntDB.query().get(1);
//		tLPAppntSchema = new LPAppntSchema();
//		mReflections.transFields(tLPAppntSchema, tLCAppntSchema);
//		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPAppntSet.add(tLPAppntSchema);
		
		//被保人
		// 查询客户所有相关保单（客户为投保人）
//		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
//		LCInsuredSchema tLCInsuredSchema = null;
//		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
//		LPInsuredSchema tLPInsuredSchema = null;
//		
//		
//		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());		
//		tLCInsuredSchema=tLCInsuredDB.query().get(1);
//		tLPInsuredSchema = new LPInsuredSchema();
//		mReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);
//		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPInsuredSet.add(tLPInsuredSchema);
		
//		LPCSpecSet mLPCSpecSet = new LPCSpecSet();
//		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
//		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
//		tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
//		tLCCSpecSet = tLCCSpecDB.query();
//		if(tLCCSpecSet.size()>0)
//		{
//			for(int k=1;k<=tLCCSpecSet.size();k++)
//			{
//				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
//				mReflections.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
//				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				mLPCSpecSet.add(mLPCSpecSchema);				
//			}
//			mMap.put(mLPCSpecSet, "DELETE&INSERT");
//		}
		
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		// 变动的保费保额
		double tChgPrem = 0.0;
		double tChgAmnt = 0.0;
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败！");
			return false;
		}
		LCContSchema tLCContSchema = tLCContDB.getSchema();
		LCGetDB tLCGetDB = new LCGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		LCGetSet tContLCGetSet = null;
		LPGetSchema tLPGetSchema = null;
		
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LPDutySet tLPDutySet = new LPDutySet();
		LCDutySet tContLCDutySet = null;
		LPDutySchema tLPDutySchema = null;
		// 当前保单的保费保额
		double tLPContPrem = tLCContSchema.getPrem();
		double tLPContAmnt = tLCContSchema.getAmnt();

		LPPolSet tLPPolSet = new LPPolSet();
		LPPremSet tLPPremSet = new LPPremSet();
		for (int i = 1; i <= mLCPolSet.size(); i++) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLCPolSet.get(i).getPolNo());
			if (!tLCPolDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "PEdorENDetailBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保单险种信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LPPolSchema tLPPolSchema = new LPPolSchema();
			mReflections.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			logger.debug("变更之前续保标志为:" + tLPPolSchema.getRnewFlag());
			tLPPolSchema.setRnewFlag(mLCPolSet.get(i).getRnewFlag()); // 修改续保标志为不续保
			logger.debug("续保标志变更为:" + tLPPolSchema.getRnewFlag());
			// 如果保全生效日已经到达或超过交至日期，则把AppFlag置成4，确认时将险种终止
//			FDate aFDate = new FDate();
//			FDate bFDate = new FDate();
			// add by jiaqiangli 2009-07-06 不需要处理状态
//			if (aFDate.getDate(tLPPolSchema.getPaytoDate()).compareTo(
//					bFDate.getDate(mLPEdorItemSchema.getEdorValiDate())) <= 0) {
//				tLPPolSchema.setAppFlag("4");
//				tLPPolSchema.setExpiryFlag("1");
//			}
			tLPPolSchema.setOperator(this.mGlobalInput.Operator);
			tLPPolSchema.setModifyDate(strCurrentDate);
			tLPPolSchema.setModifyTime(strCurrentTime);
			tLPPolSet.add(tLPPolSchema);

			// 修改LPPrem中的UrgePayFlag为“N” Add By QianLy on 2006-09-08----------
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(mLCPolSet.get(i).getPolNo());
			LCPremSet tLCPremSet = tLCPremDB.query();
			LPPremSchema tLPPremSchema = null;
			for (int j = 1; j <= tLCPremSet.size(); j++) {
				tLPPremSchema = new LPPremSchema();
				mReflections.transFields(tLPPremSchema, tLCPremSet.get(j));
				tLPPremSchema.setPolNo(mLCPolSet.get(i).getPolNo());
				tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				if (tLPPolSchema.getRnewFlag() == -1) {
					logger.debug("变为续保,将催收标志置为Y");
					tLPPremSchema.setUrgePayFlag("Y");

				} else {
					logger.debug("变为不续保,将催收标志置为N");
					tLPPremSchema.setUrgePayFlag("N");
				}
				tLPPremSchema.setOperator(this.mGlobalInput.Operator);
				tLPPremSchema.setModifyDate(strCurrentDate);
				tLPPremSchema.setModifyTime(strCurrentTime);
				tLPPremSet.add(tLPPremSchema);
			}

			// 记录变动的保费保额
			if (tLPPolSchema.getRnewFlag() == -1) {
				// 变为续保,将保费保额加入到CONT中;
				tLPContPrem += tLPPolSchema.getPrem();
				tLPContAmnt += tLPPolSchema.getAmnt();
				tChgPrem += tLPPolSchema.getPrem();
				tChgAmnt += tLPPolSchema.getAmnt();
			} else {
				// 变为不续保,将保费从CONT中扣除;
				tLPContPrem -= tLPPolSchema.getPrem();
				tLPContAmnt -= tLPPolSchema.getAmnt();
				tChgPrem -= tLPPolSchema.getPrem();
				tChgAmnt -= tLPPolSchema.getAmnt();
			}
			// tChgPrem += tLPPolSchema.getPrem();
			// tChgAmnt += tLPPolSchema.getAmnt();
			
			// 复制保单险种责任数据到P表，为人工核保
//			tLCDutyDB.setContNo(tLCContSchema.getContNo());
//			tContLCDutySet = new LCDutySet();
//			tContLCDutySet = tLCDutyDB.query();
//			if (tContLCDutySet != null && tContLCDutySet.size() > 0) {
//				for (int k = 1; k <= tContLCDutySet.size(); k++) {
//					tLPDutySchema = new LPDutySchema();
//					mReflections.transFields(tLPDutySchema, tContLCDutySet.get(k));
//					tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//					tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
//					tLPDutySet.add(tLPDutySchema);
//				}
//			}
			
			// 复制保单险种领取责任数据到P表，为人工核保
//			tLCGetDB.setContNo(tLCContSchema.getContNo());
//			tContLCGetSet = new LCGetSet();
//			tContLCGetSet = tLCGetDB.query();
//			if (tContLCGetSet != null && tContLCGetSet.size() > 0) {
//				for (int l = 1; l <= tContLCGetSet.size(); l++) {
//					tLPGetSchema = new LPGetSchema();
//					mReflections.transFields(tLPGetSchema, tContLCGetSet.get(l));
//					tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//					tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//					tLPGetSet.add(tLPGetSchema);
//				}
//			}
		}

		// 更新Cont表
		
		
		LPContBL tLPContBL = new LPContBL();
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPContBL.queryLastLPCont(mLPEdorItemSchema, tLPContSchema)) {
			mErrors.copyAllErrors(tLPContBL.mErrors);
			return false;
		}
		//
		
		tLPContSchema = new LPContSchema();
		//begin zbx 20100516 
		//tLPContSchema.setSchema(tLPContBL.getSchema());
		PubFun.copySchema(tLPContSchema, tLCContSchema);
		
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setContNo(mLPEdorItemSchema.getContNo());

		// 设置当前的保费保额
		tLPContSchema.setPrem(tLPContPrem);
		tLPContSchema.setAmnt(tLPContAmnt);
		// tLPContSchema.setOperator(this.mGlobalInput.Operator);
		tLPContSchema.setModifyDate(strCurrentDate);
		tLPContSchema.setModifyTime(strCurrentTime);
		

		


		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setChgPrem(tChgPrem); // 变动的保费
		mLPEdorItemSchema.setChgAmnt(tChgAmnt); // 变动的保额
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);

		mMap.put(mLPEdorItemSchema, "UPDATE");
		mMap.put(tLPContSchema, "INSERT");
		mMap.put(tLPPolSet, "INSERT");
		mMap.put(tLPPremSet, "INSERT");
//		
//		mMap.put(tLPDutySet, "INSERT");
//		mMap.put(tLPGetSet, "INSERT");
//		mMap.put(tLPInsuredSet, "DELETE&INSERT");
//		mMap.put(tLPAppntSet, "DELETE&INSERT");

		mResult.clear();
		mResult.add(mMap);

		mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mResult.add(mBqCalBase);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			mLCPolSet = (LCPolSet) mInputData.getObjectByObjectName("LCPolSet",
					0);

			if (mLCPolSet == null || mLCPolSet.size() <= 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorENDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获得输入数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mGlobalInput == null || mLPEdorItemSchema == null) {
				CError tError = new CError();
				tError.moduleName = "PEdorENDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获得输入数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
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
		// 获得mLPEdorItemSchema的其它信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPEdorItemDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorENDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询保全信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorItemSchema = tLPEdorItemDB.getSchema();
		return true;
	}

	/**
	 * 删除上次保存过的数据
	 * 
	 * @return boolean
	 */
	private boolean clearLastData() {
		// 清除P表中上次保存过的数据
		String sqlWhere = " contno = '?contno?' and edorno = '?edorno?' and edortype = '?edortype?'";
        
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql("delete from lpcont where" + sqlWhere);
        sbv1.put("contno", mLPEdorItemSchema.getContNo());
        sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv1.put("edortype", mLPEdorItemSchema.getEdorType());
        SQLwithBindVariables sbv2=new SQLwithBindVariables();
        sbv2.sql("delete from lppol  where" + sqlWhere);
        sbv2.put("contno", mLPEdorItemSchema.getContNo());
        sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
        SQLwithBindVariables sbv3=new SQLwithBindVariables();
        sbv3.sql("delete from lpprem where" + sqlWhere);
        sbv3.put("contno", mLPEdorItemSchema.getContNo());
        sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv3.put("edortype", mLPEdorItemSchema.getEdorType());
		mMap.put(sbv1, "DELETE");
		mMap.put(sbv2, "DELETE");
		mMap.put(sbv3, "DELETE");
//		mMap.put("delete from lpget where" + sqlWhere, "DELETE");
//		mMap.put("delete from lpduty where" + sqlWhere, "DELETE");

		return true;
	}
}
