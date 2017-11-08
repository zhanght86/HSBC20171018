package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 领取形式变更回退处理确认类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @CreateDate 2005-10-10
 * @version 1.0
 */
public class PEdorGCBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorGCBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorGCBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("=== 领取形式变更回退确认生效 ===");
		Reflections tRef = new Reflections();

		LPPolSchema tLPPolSchema = new LPPolSchema();
		LCPolSchema tLCPolSchema = new LCPolSchema();

		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPPolDB.getInfo()) {
			CError.buildErr(this, "查询原始险种信息失败!");
			return false;
		}
		tRef.transFields(tLCPolSchema, tLPPolDB.getSchema());
		tLCPolSchema.setModifyDate(CurrDate);
		tLCPolSchema.setModifyTime(CurrTime);
		
		//保全回退直接那回退项目的P表数据覆盖C表，无需对P表进行任何操作
		//因为MS不需要回退的回退
//		LCPolDB tLCPolDB = new LCPolDB();
//		tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
//		if (!tLCPolDB.getInfo()) {
//			CError.buildErr(this, "查询险种信息失败!");
//			return false;
//		}
//		tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
//		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPPolSchema.setModifyDate(CurrDate);
//		tLPPolSchema.setModifyTime(CurrTime);

		map.put(tLCPolSchema, "DELETE&INSERT");
		map.put(tLPPolSchema, "DELETE&INSERT");

		LJSGetSet tLJSGetSet = new LJSGetSet();
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setOtherNoType("2");
		tLJSGetDB.setOtherNo(mLPEdorItemSchema.getContNo());
		tLJSGetSet = tLJSGetDB.query();
		if (tLJSGetSet.size() > 0) {
			for (int j = 1; j <= tLJSGetSet.size(); j++) {
				tLJSGetSet.get(j).setBankCode(tLCPolSchema.getGetBankCode());
				tLJSGetSet.get(j).setBankAccNo(tLCPolSchema.getGetBankAccNo());
				tLJSGetSet.get(j).setAccName(tLCPolSchema.getGetAccName());
				tLJSGetSet.get(j).setModifyDate(CurrDate);
				tLJSGetSet.get(j).setModifyTime(CurrTime);
			}
			map.put(tLJSGetSet, "UPDATE");
		}
		
		/*add by jiaqiangli 2008-08-26 lcbnf与lpbnf的互换*/
        logger.debug("start to deal lcbnf and lpbnf in back confirm");
		
		String DeleteSQL = null;
        
		//add by jiaqiangli 2008-08-26 first to dual lcbnf->lpbnf
		// 删除 LcBnf
		DeleteSQL = "delete from lcbnf " + "where 1 = 1 " + "and ContNo = '?ContNo?' " + "and PolNo = '?PolNo?' and bnftype = '0' ";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(DeleteSQL);
		sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv1.put("PolNo", mLPEdorItemSchema.getPolNo());
		map.put(sbv1, "DELETE");
		
		LPBnfDB tLPBnfDB = new LPBnfDB();
		tLPBnfDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPBnfDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPBnfDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPBnfDB.setPolNo(mLPEdorItemSchema.getPolNo());
		//此处BnfType("0")这个查询条件
		tLPBnfDB.setBnfType("0");
		LPBnfSet tLPBnfSet = new LPBnfSet();
		try {
			tLPBnfSet = tLPBnfDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全受益人表发生异常！");
			return false;
		}
		if (tLPBnfSet != null && tLPBnfSet.size() > 0) {
			// 拷贝 LPBnf 到 LCBnf
			LCBnfSet tLCBnfSetNew = new LCBnfSet();
			for (int i = 1; i <= tLPBnfSet.size(); i++) {
				LPBnfSchema tLPBnfSchema = new LPBnfSchema();
				tLPBnfSchema = tLPBnfSet.get(i);
				LCBnfSchema tLCBnfSchemaNew = new LCBnfSchema();
				PubFun.copySchema(tLCBnfSchemaNew, tLPBnfSchema);
				tLCBnfSchemaNew.setOperator(this.mGlobalInput.Operator);
				tLCBnfSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCBnfSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCBnfSetNew.add(tLCBnfSchemaNew);
				tLCBnfSchemaNew = null;
				tLPBnfSchema = null;
			}
			map.put(tLCBnfSetNew, "INSERT");
			tLCBnfSetNew = null;
		}
		tLPBnfDB = null;
		tLPBnfSet = null;
		
		//add by jiaqiangli 2008-08-26 then to dual lpbnf->lcbnf
		// 删除 LPBnf
		DeleteSQL = "delete from LPBnf " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " + "and ContNo = '?ContNo?' " + "and PolNo = '?PolNo?' and bnftype = '0'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(DeleteSQL);
		sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
		sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv2.put("PolNo", mLPEdorItemSchema.getPolNo());
		map.put(sbv2, "DELETE");
		
		// 查询 LCBnf
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCBnfDB.setPolNo(mLPEdorItemSchema.getPolNo());
		//只是互换生存金分配信息
		//生存受益人，这里必须要加这个条件
		tLCBnfDB.setBnfType("0");
		
		LCBnfSet tLCBnfSet = new LCBnfSet();
		try {
			tLCBnfSet = tLCBnfDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询契约受益人表发生异常！");
			return false;
		}
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			// 拷贝 LCBnf 到 LPBnf
			LPBnfSet tLPBnfSetNew = new LPBnfSet();
			for (int i = 1; i <= tLCBnfSet.size(); i++) {
				LCBnfSchema tLCBnfSchema = new LCBnfSchema();
				LPBnfSchema tLPBnfSchemaNew = new LPBnfSchema();
				tLCBnfSchema = tLCBnfSet.get(i);
				PubFun.copySchema(tLPBnfSchemaNew, tLCBnfSchema);
				tLPBnfSchemaNew.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPBnfSchemaNew.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPBnfSchemaNew.setOperator(this.mGlobalInput.Operator);
				tLPBnfSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLPBnfSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLPBnfSetNew.add(tLPBnfSchemaNew);
				tLPBnfSchemaNew = null;
				tLCBnfSchema = null;
			}
			map.put(tLPBnfSetNew, "INSERT");
			tLPBnfSetNew = null;
		}
		tLCBnfDB = null;
		tLCBnfSet = null;
				
		DeleteSQL = null;
		/*add by jiaqiangli 2008-08-26 lcbnf与lpbnf的互换*/

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorGCBackConfirmBL pedorgcbackconfirmbl = new PEdorGCBackConfirmBL();
	}
}
