package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
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
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 红利领取逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PEdorBMDetailBL {
private static Logger logger = Logger.getLogger(PEdorBMDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 封装将要提交数据 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private Reflections mRef = new Reflections();
	/**  */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private LPDutySchema mLPDutySchema = new LPDutySchema();//
	// 接受前台传输过来关键数据
	private LPPolSet mLPPolSet = new LPPolSet();
	// 保存新的数据 完成数据
	private LPPolSet tLPPolSet = new LPPolSet();
	private LPDutySet tLPDutySet = new LPDutySet();//
	
	private LPGetSet tLPGetSet = new LPGetSet();
	private LPPremSet tLPPremSet = new LPPremSet();//
	
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private String tPreCode;
	private String tCode;

	public PEdorBMDetailBL() {
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

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);
		// 业务处理
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据查询业务处理(queryData())
	 * 
	 */

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mInputData = (VData) cInputData.clone();

		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPPolSet = (LPPolSet) mInputData.getObjectByObjectName("LPPolSet", 0);
		if (mGlobalInput == null || mLPEdorItemSchema == null
				|| mLPPolSet.size() < 1) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		// 清除上次明细保存的数据
		deleteData(mLPEdorItemSchema.getEdorNo(), mLPEdorItemSchema
				.getEdorType());
		String tSql = "";
		/*
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.1 修改LCPol表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		for (int i = 1; i <= mLPPolSet.size(); i++) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLPPolSet.get(i).getPolNo());
			if (!tLCPolDB.getInfo()) {
				CError.buildErr(this, "查询保单险种代码时产生错误!");
			}
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLCPolSchema = tLCPolDB.getSchema();
			mRef.transFields(tLPPolSchema, tLCPolSchema);
			tLPPolSchema.setBonusGetMode(mLPPolSet.get(i).getBonusGetMode());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyDate(strCurrentDate);
			tLPPolSchema.setModifyTime(strCurrentTime);

			tLPPolSet.add(tLPPolSchema);
			/*
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.2 修改LCDuty表
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tPolNo = mLPPolSet.get(i).getPolNo();
			LCDutyDB tLCDutyDB = new LCDutyDB();
			LCDutySet tLCDutySet = new LCDutySet();

			tSql = "select * from lcduty where polno ='" + "?tPolNo?" + "'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tPolNo", tPolNo);
			tLCDutySet = tLCDutyDB.executeQuery(sqlbv);
			if (tLCDutySet.size() < 1) {
				mErrors.addOneError("查询个人险种号[" + tPolNo + "]的LCDuty表失败！");
				return false;
			}

			for (int j = 1; j <= tLCDutySet.size(); j++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				LPDutySchema tLPDutySchema = new LPDutySchema();
				tLCDutySchema = tLCDutySet.get(j);
				mRef.transFields(tLPDutySchema, tLCDutySchema);
				tLPDutySchema.setBonusGetMode(mLPPolSet.get(i)
						.getBonusGetMode());
				tLPDutySchema.setModifyDate(strCurrentDate);
				tLPDutySchema.setModifyTime(strCurrentTime);
				tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPDutySet.add(tLPDutySchema);
			}
			// 保存分红方式
			tPreCode = tLCPolSchema.getBonusGetMode();
			tCode = mLPPolSet.get(i).getBonusGetMode();
			
			//准备lcprem lcget的p表数据
			LCGetDB tLCGetDB = new LCGetDB();
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("select * from lcget where contno='"+"?contno?"+"' and polno='"+"?polno?"+"' and char_length(dutycode)=6 ");
			sbv.put("contno", mLPEdorItemSchema.getContNo());
			sbv.put("polno", tLCPolSchema.getPolNo());
			LCGetSet tLCGetSet = tLCGetDB.executeQuery(sbv);
			if (tLCGetSet == null || tLCGetSet.size() <= 0) {
				CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")领取责任记录失败!");
				return false;
			}
			LPGetSet tLPGetSet = new LPGetSet();
			LPGetSchema tLPGetSchema = new LPGetSchema();
			tLPGetSet.add(tLPGetSchema);
			mRef.transFields(tLPGetSet, tLCGetSet);
			this.tLPGetSet.add(tLPGetSet);
			
			LCPremDB tLCPremDB = new LCPremDB();
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql("select * from lcprem where contno='"+"?contno?"+"' and polno='"+"?polno?"+"' and char_length(dutycode)=6 ");
			sbv1.put("contno", mLPEdorItemSchema.getContNo());
			sbv1.put("polno", tLCPolSchema.getPolNo());
			LCPremSet tLCPremSet = tLCPremDB.executeQuery(sbv1);
			if (tLCPremSet == null || tLCPremSet.size() <= 0) {
				CError.buildErr(this, "查找保单(" + tLCPolSchema.getPolNo() + ")交费责任记录失败!");
				return false;
			}
			LPPremSet tLPPremSet = new LPPremSet();
			LPPremSchema tLPPremSchema = new LPPremSchema();
			tLPPremSet.add(tLPPremSchema);
			mRef.transFields(tLPPremSet, tLCPremSet);
			this.tLPPremSet.add(tLPPremSet);
		}
		
		for (int i = 1; i <= tLPGetSet.size(); i++) {
			tLPGetSet.get(i).setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSet.get(i).setEdorType(mLPEdorItemSchema.getEdorType());
		}
		
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			tLPPremSet.get(i).setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremSet.get(i).setEdorType(mLPEdorItemSchema.getEdorType());
		}
		
		mMap.put(tLPGetSet, "DELETE&INSERT");
		mMap.put(tLPPremSet, "DELETE&INSERT");

		mMap.put(tLPPolSet, "DELETE&INSERT");
		mMap.put(tLPDutySet, "DELETE&INSERT");
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("update lpedoritem set standbyflag1='" + "?tPreCode?"
				+ "',standbyflag2='" + "?tCode?" + "' where edoracceptno='"
				+ "?edoracceptno?" + "' and edorno='"
				+ "?edorno?" + "' and edortype='BM'");
		sbv2.put("tPreCode", tPreCode);
		sbv2.put("tCode", tCode);
		sbv2.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
		mMap.put(sbv2,"UPDATE");

		mResult.clear();
		mResult.add(mMap);
		
		//为核保准备数据
		//投保人
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = null;
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntSchema tLPAppntSchema = null;
		
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCAppntSchema=tLCAppntDB.query().get(1);
		tLPAppntSchema = new LPAppntSchema();
		mRef.transFields(tLPAppntSchema, tLCAppntSchema);
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
		mRef.transFields(tLPInsuredSchema, tLCInsuredSchema);
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSet.add(tLPInsuredSchema);
		
		mMap.put(tLPInsuredSet, "DELETE&INSERT");
		mMap.put(tLPAppntSet, "DELETE&INSERT");
		
	    //告知
		LPCSpecSet mLPCSpecSet = new LPCSpecSet();
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCCSpecSet = tLCCSpecDB.query();
		if(tLCCSpecSet.size()>0)
		{
			for(int k=1;k<=tLCCSpecSet.size();k++)
			{
				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
				mRef.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);				
			}
			mMap.put(mLPCSpecSet, "DELETE&INSERT");
		}
		
		return true;
	}

	/**
	 * 清除上次明细保存的数据
	 * 
	 * @return: boolean
	 */
	private void deleteData(String edorno, String edortype) {
		// 清除P表中上次保存过的数据
		String sqlWhere = " edorno = '" + "?edorno?" + "' and edortype = '"
				+ "?edortype?" + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("delete from LPPol where" + sqlWhere);
		sbv1.put("edorno", edorno);
		sbv1.put("edortype",edortype );
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("delete from LPDuty where" + sqlWhere);
		sbv2.put("edorno", edorno);
		sbv2.put("edortype",edortype );
		mMap.put(sbv1, "DELETE");
		mMap.put(sbv2, "DELETE");

	}
}
