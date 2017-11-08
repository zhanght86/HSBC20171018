package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPCSpecDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
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
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 特别约定内容变更业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 1.0 2.0
 */
public class PEdorSCDetailBL {
private static Logger logger = Logger.getLogger(PEdorSCDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**
	 * 保全项目表
	 */
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
	private LPCSpecSet mLPCSpecSet = new LPCSpecSet();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSet outLPEdorItemSet = new LPEdorItemSet();
	private MMap mMap = new MMap();

	private Reflections mReflections = new Reflections();
	
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorSCDetailBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("==> PEdorSCDetailBL : Start Submit");
		mOperate=cOperate;
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}
		// 数据查询业务处理
		if (!dealData()) {
			return false;
		}

		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean dealData() {

		



		// 为了人工核保能查到数据，这里将被告知人涉及的所有保单和险种数据复制到P表
		LPContSet tLPContSet = new LPContSet();
		LCPolDB tLCPolDB = new LCPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		LCPolSet tContLCPolSet = null;
		LPPolSchema tLPPolSchema = null;
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LPDutySet tLPDutySet = new LPDutySet();
		LCDutySet tContLCDutySet = null;
		LPDutySchema tLPDutySchema = null;
		LCPremDB tLCPremDB = new LCPremDB();
		LPPremSet tLPPremSet = new LPPremSet();
		LCPremSet tContLCPremSet = null;
		LPPremSchema tLPPremSchema = null;
		LCGetDB tLCGetDB = new LCGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		LCGetSet tContLCGetSet = null;
		LPGetSchema tLPGetSchema = null;

	
		// 定义LPCSpec表操作变量
		LPCSpecSchema tLPCSpecSchema = new LPCSpecSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCContSet = tLCContDB.query();
		if (tLCContSet.size() < 1) {
			CError.buildErr(this, "保单数据查询失败!");
			return false;
		} else {
			tLCContSchema = tLCContSet.get(1);
		}
		Reflections tReflections = new Reflections();		
		if (mOperate.equals("insert")) // 说明是首次增加特约
		{
			String strNoLimit = PubFun.getNoLimit(tLCContSchema.getManageCom());
			String aSerialNo = PubFun1.CreateMaxNo("SN", strNoLimit); // 生成打印流水号
			String aPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			tReflections.transFields(tLPCSpecSchema, mLPCSpecSchema);
			// tLPCSpecSchema.setPrtSeq(sPrtSeq);
			tLPCSpecSchema.setSerialNo(aSerialNo);
			tLPCSpecSchema.setContNo(tLCContSchema.getContNo());
			tLPCSpecSchema.setGrpContNo(tLCContSchema.getGrpContNo());
			tLPCSpecSchema.setPrtFlag("0");
			tLPCSpecSchema.setPrtSeq(aPrtSeq);
			tLPCSpecSchema.setProposalContNo(tLCContSchema.getProposalContNo());
			tLPCSpecSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			tLPCSpecSchema.setMakeDate(mCurrentDate);
			tLPCSpecSchema.setMakeTime(mCurrentTime);
			tLPCSpecSchema.setModifyDate(mCurrentDate);
			tLPCSpecSchema.setModifyTime(mCurrentTime);
			tLPCSpecSchema.setOperator(mGlobalInput.Operator);
		    tLPCSpecSchema.setBackupType("ins");
//			mMap.put("delete  from LPCSpec where edorno='"
//					+ mLPEdorItemSchema.getEdorNo() + "' and ContNo='"
//					+ mLPEdorItemSchema.getContNo() + "' and SerialNo='"+mLPCSpecSchema.getSerialNo()+"'", "DELETE");

			//mLPCSpecSet.add(tLPCSpecSchema);
		} else if (mOperate.equals("modify")) // 本次是修改特约
		{


			LPCSpecDB tLPCSpecDB = new LPCSpecDB();
			LPCSpecSet tLPCSpecSet = new LPCSpecSet();
			tLPCSpecDB.setSerialNo(mLPCSpecSchema.getSerialNo());
			tLPCSpecDB.setContNo(mLPCSpecSchema.getContNo());
			tLPCSpecDB.setEdorNo(mLPCSpecSchema.getEdorNo());
			tLPCSpecDB.setEdorType(mLPCSpecSchema.getEdorType());
			tLPCSpecSet=tLPCSpecDB.query();
			if(tLPCSpecSet.size()>0)
			{   //本次是重复修改
				LPCSpecSchema rLPCSpecSchema = new LPCSpecSchema();
				rLPCSpecSchema=tLPCSpecSet.get(1);
				tReflections.transFields(tLPCSpecSchema, rLPCSpecSchema);
			}else
			{
				//本次是直接修改
				LCCSpecDB tLCCSpecDB = new LCCSpecDB();
				LCCSpecSet tLCCSpecSet = new LCCSpecSet();
				tLCCSpecDB.setSerialNo(mLPCSpecSchema.getSerialNo());
				tLCCSpecDB.setContNo(mLPCSpecSchema.getContNo());
				tLCCSpecSet=tLCCSpecDB.query();
				if(tLCCSpecSet.size()<1 || tLCCSpecSet==null)
				{
					CError.buildErr(this, "保单特约数据查询失败!");
					return false;
				}else
				{
					LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
					tLCCSpecSchema=tLCCSpecSet.get(1);
					tReflections.transFields(tLPCSpecSchema, tLCCSpecSchema);
				}
			}
		    tLPCSpecSchema.setBackupType("mod");
			tLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPCSpecSchema.setSpecContent(mLPCSpecSchema.getSpecContent());
			tLPCSpecSchema.setModifyDate(mCurrentDate);
			tLPCSpecSchema.setModifyTime(mCurrentTime);
			//mLPCSpecSet.add(tLPCSpecSchema);
		}else if (mOperate.equals("delete")) 
		{

			LPCSpecDB tLPCSpecDB = new LPCSpecDB();
			LPCSpecSet tLPCSpecSet = new LPCSpecSet();
			tLPCSpecDB.setSerialNo(mLPCSpecSchema.getSerialNo());
			tLPCSpecDB.setContNo(mLPCSpecSchema.getContNo());
			tLPCSpecDB.setEdorNo(mLPCSpecSchema.getEdorNo());
			tLPCSpecDB.setEdorType(mLPCSpecSchema.getEdorType());
			tLPCSpecSet=tLPCSpecDB.query();
			if(tLPCSpecSet.size()>0)
			{
				//本次是重复删除
				LPCSpecSchema rLPCSpecSchema = new LPCSpecSchema();
				rLPCSpecSchema=tLPCSpecSet.get(1);
				tReflections.transFields(tLPCSpecSchema, rLPCSpecSchema);
			}else
			{
		    //本次是直接删除
			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			tLCCSpecDB.setSerialNo(mLPCSpecSchema.getSerialNo());
			tLCCSpecDB.setContNo(mLPCSpecSchema.getContNo());
			tLCCSpecSet=tLCCSpecDB.query();
			if(tLCCSpecSet.size()<1 || tLCCSpecSet==null)
			{
				CError.buildErr(this, "保单特约数据查询失败!");
				return false;
			}else
			{
				LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
				tLCCSpecSchema=tLCCSpecSet.get(1);
				tReflections.transFields(tLPCSpecSchema, tLCCSpecSchema);
			}
			}
	    tLPCSpecSchema.setBackupType("del");
		tLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		//tLPCSpecSchema.setSpecContent(mLPCSpecSchema.getSpecContent());
		tLPCSpecSchema.setModifyDate(mCurrentDate);
		tLPCSpecSchema.setModifyTime(mCurrentTime);
		//mLPCSpecSet.add(tLPCSpecSchema);
		}

		
		// 复制保单数据到P表，为人工核保
		
		//投保人
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = null;
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntSchema tLPAppntSchema = null;
		
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCAppntSchema=tLCAppntDB.query().get(1);
		tLPAppntSchema = new LPAppntSchema();
		mReflections.transFields(tLPAppntSchema, tLCAppntSchema);
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSet.add(tLPAppntSchema);
		
		//被保人
		// 查询客户所有相关保单（客户为投保人）
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = null;
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		LPInsuredSchema tLPInsuredSchema = null;
		
		
		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCInsuredSchema=tLCInsuredDB.query().get(1);
		tLPInsuredSchema = new LPInsuredSchema();
		mReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSet.add(tLPInsuredSchema);
		
		LPContSchema tLPContSchema = new LPContSchema();
		mReflections.transFields(tLPContSchema, tLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSet.add(tLPContSchema);
		// 复制保单险种数据到P表，为人工核保
		tLCPolDB.setContNo(tLCContSchema.getContNo());
		tLCPolDB.setAppFlag("1");
		tContLCPolSet = new LCPolSet();
		tContLCPolSet = tLCPolDB.query();
		if (tContLCPolSet != null && tContLCPolSet.size() > 0) {
			for (int m = 1; m <= tContLCPolSet.size(); m++) {
				tLPPolSchema = new LPPolSchema();
				mReflections.transFields(tLPPolSchema, tContLCPolSet.get(m));
				tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPolSet.add(tLPPolSchema);
				
				// 复制保单险种责任数据到P表，为人工核保
				tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
				tContLCDutySet = new LCDutySet();
				tContLCDutySet = tLCDutyDB.query();
				if (tContLCDutySet != null && tContLCDutySet.size() > 0) {
					for (int k = 1; k <= tContLCDutySet.size(); k++) {
						tLPDutySchema = new LPDutySchema();
						mReflections.transFields(tLPDutySchema, tContLCDutySet.get(k));
						tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
						tLPDutySet.add(tLPDutySchema);
					}
				}
				// 复制保单险种给付责任数据到P表，为人工核保
				tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
				tContLCPremSet = new LCPremSet();
				tContLCPremSet = tLCPremDB.query();
				if (tContLCPremSet != null && tContLCPremSet.size() > 0) {
					for (int i = 1; i <= tContLCPremSet.size(); i++) {
						tLPPremSchema = new LPPremSchema();
						mReflections.transFields(tLPPremSchema, tContLCPremSet.get(i));
						tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
						tLPPremSet.add(tLPPremSchema);
					}
				}
				// 复制保单险种领取责任数据到P表，为人工核保
				tLCGetDB.setPolNo(tLPPolSchema.getPolNo());
				tContLCGetSet = new LCGetSet();
				tContLCGetSet = tLCGetDB.query();
				if (tContLCGetSet != null && tContLCGetSet.size() > 0) {
					for (int l = 1; l <= tContLCGetSet.size(); l++) {
						tLPGetSchema = new LPGetSchema();
						mReflections.transFields(tLPGetSchema, tContLCGetSet.get(l));
						tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
						tLPGetSet.add(tLPGetSchema);
					}
				}
			}
		}
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		LPCSpecSet rLPCSpecSet = new LPCSpecSet();
		//准备健康特约和儿童特约
        String tSQL="select * from LCCSpec where  SpecType  in  ('hx','mn','nf','qt','sj','xi','xu','zx','ch') and contno='?contno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
        tLCCSpecSet=tLCCSpecDB.executeQuery(sqlbv);
		if(tLCCSpecSet.size()>=0 )
		{
           for(int k=0;k<tLCCSpecSet.size();k++)
           {
   			LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
   			LPCSpecSchema rLPCSpecSchema = new LPCSpecSchema();
			tLCCSpecSchema=tLCCSpecSet.get(1);
			tReflections.transFields(rLPCSpecSchema, tLCCSpecSchema);
			rLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			rLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			rLPCSpecSchema.setModifyDate(mCurrentDate);
			rLPCSpecSchema.setModifyTime(mCurrentTime);
			rLPCSpecSchema.setOperator(mGlobalInput.Operator);
			rLPCSpecSet.add(rLPCSpecSchema);
           }
           //为核保准备数据
   		mMap.put(rLPCSpecSet, "DELETE&INSERT");
		}

		mMap.put(tLPContSet, "DELETE&INSERT");
		mMap.put(tLPPolSet, "DELETE&INSERT");
		mMap.put(tLPDutySet, "DELETE&INSERT");
		mMap.put(tLPPremSet, "DELETE&INSERT");
		mMap.put(tLPGetSet, "DELETE&INSERT");
		mMap.put(tLPCSpecSchema, "DELETE&INSERT");
		mMap.put(tLPInsuredSet, "DELETE&INSERT");
		mMap.put(tLPAppntSet, "DELETE&INSERT");
		
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPCSpecSchema = (LPCSpecSchema) cInputData.getObjectByObjectName(
				"LPCSpecSchema", 0);
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		// LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		// tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		//
		// if (!tLPEdorItemDB.getInfo()) {
		// // @@错误处理
		// CError.buildErr(this, "无保全申请数据!");
		//
		// return false;
		// }
		// mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		// if(mLPCSpecSchema==null)
		// {
		// CError.buildErr(this, "无申请数据!");
		//
		// return false;
		// }
		//

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		logger.debug("==> PEdorSCDetailBL : Start Prepare Data");

		mMap.put(mLPEdorItemSchema, "UPDATE");
		mResult.clear();

		mResult.add(mMap);
		return true;
	}

}
