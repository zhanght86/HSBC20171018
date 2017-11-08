/*
 * <p>ClassName: LDCode1ConfigBL </p>
 * <p>Description: LDCode1ConfigBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:  </p>
 * @Database:
 * @CreateDate：2005-01-24 18:15:01
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCode1Schema;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LDCode1ConfigBL {
private static Logger logger = Logger.getLogger(LDCode1ConfigBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LDCode1Set mLDCode1Set = new LDCode1Set();
	private LDCode1Set mInsertLDCode1Set = new LDCode1Set();
	private TransferData mTransferData = new TransferData();
	
	public LDCode1ConfigBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError.buildErr(this,"数据处理失败OLDUWUserBL-->dealData!");
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError.buildErr(this,"数据提交失败");
			return false;
		}

			logger.debug("---commitData---");

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		// tLDUWUserDB.setUserCode(mLDUWUserSchema.getUserCode());
		String tCodeType = (String)this.mTransferData.getValueByName("CodeType");
		String tDeleteSQL = "delete from ldcode1 where  codetype='"+"?tCodeType?"+"' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tDeleteSQL);
		sqlbv1.put("tCodeType",tCodeType);
		for(int i=1;i<=this.mLDCode1Set.size();i++)
		{
			LDCode1Schema tempLDCode1Schema = new LDCode1Schema();
			tempLDCode1Schema.setSchema(mLDCode1Set.get(i));
			
			this.mInsertLDCode1Set.add(tempLDCode1Schema);
		}
		this.map.put(sqlbv1, "DELETE");
		this.map.put(mInsertLDCode1Set, "INSERT");
		return true;
	}

	
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			this.mLDCode1Set.set((LDCode1Set) cInputData.getObjectByObjectName(
					"LDCode1Set", 0));
			logger.debug("mLDCode1Set():"+mLDCode1Set.size());
			this.mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			
			this.mTransferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);
		} catch (Exception ex) {
			CError.buildErr(this, "数据读取错误！" + ex.getMessage());
			return false;
		}
		return true;
	}

	

	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this,"准备保存数据出错!");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
