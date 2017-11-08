package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.ExeSQL;
/**
 * <p>
 * Title: 投保人变更明细
 * </p>
 * 
 * <p>
 * Description:
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
 * @author lizhuo
 * @version 1.0
 */
public class PEdorAEDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorAEDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPAppntSchema mLPAppntSchema = new LPAppntSchema();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPPersonSchema mLPPersonSchema = new LPPersonSchema();
	private BqCalBase mBqCalBase = new BqCalBase();

	public PEdorAEDetailBLF() {
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorICDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPAppntSchema = (LPAppntSchema) mInputData.getObjectByObjectName(
					"LPAppntSchema", 0);
			mLPAddressSchema = (LPAddressSchema) mInputData
					.getObjectByObjectName("LPAddressSchema", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			mLPInsuredSet = (LPInsuredSet) mInputData.getObjectByObjectName(
					"LPInsuredSet", 0);
			if (mLPEdorItemSchema.getEdorReasonCode().equals("01")) {
				mLPPersonSchema = (LPPersonSchema) mInputData
						.getObjectByObjectName("LPPersonSchema", 0);
				if (mLPPersonSchema == null) {
					return false;
				}
			}

			if (mLPAppntSchema == null || mLPEdorItemSchema == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		
	    //add by jiaqiangli 2009-02-18 LCCustomerImpart.ProposalContNo = lccont.prtno 参照新契约置法
		ExeSQL tExeSQL=new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select ProposalContNo from lccont where contno='"+"?contno?"+"'");
		sqlbv.put("contno", mLPAppntSchema.getContNo());
		String ProposalContNo=tExeSQL.getOneValue(sqlbv);
 
        for(int i=1;i<=mLCCustomerImpartSet.size();i++)
        {
        	mLCCustomerImpartSet.get(i).setProposalContNo(ProposalContNo);
        }

		PEdorAEDetailBL tPEdorAEDetailBL = new PEdorAEDetailBL();
		if (!tPEdorAEDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorAEDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorAEDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mResult.clear();
		VData tResult = tPEdorAEDetailBL.getResult();
		mResult = (VData) tResult.clone();
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
