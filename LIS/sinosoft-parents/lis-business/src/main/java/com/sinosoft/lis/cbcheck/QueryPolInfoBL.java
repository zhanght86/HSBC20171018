package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 查询险种在修改前的初始信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 6.0
 */

public class QueryPolInfoBL implements com.sinosoft.service.BusinessService{
private static Logger logger = Logger.getLogger(QueryPolInfoBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 保单 */
	private LCPolSchema inLCPolSchema = new LCPolSchema();
	private LCPolSchema outCPolSchema = new LCPolSchema();

	//返回的参数集合
	private TransferData mOutTransferData = new TransferData();
	public QueryPolInfoBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("cOperate:" + cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		if(this.mOperate.equals("INSERT"))
		{
			MMap map = new MMap();
			mInputData.clear();
			map.put(this.outCPolSchema, "UPDATE");
			mInputData.add(map);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(this.mInputData, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				return false;
			}
		}
		logger.debug("---End dealData---");
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
					"LCPolSchema", 0);
			this.mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError.buildErr(this,"接收数据失败!!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		int i;

		try {
			LCPolDB tLCPolDB = new LCPolDB();

			// 查询出投保单信息
			tLCPolDB.setPolNo(inLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				return false;
			}

			// 查询投保单，获取两个状态
			if (mOperate.equals("QUERY")) {
				mResult.add(tLCPolDB.getApproveFlag());
				mResult.add(tLCPolDB.getUWFlag());
				mResult.add(tLCPolDB.getApproveCode());
				mResult.add(tLCPolDB.getApproveDate());

				// 初始化两个状态，使投保单能够更新
				//tLCPolDB.setApproveFlag("0");
				//tLCPolDB.setUWFlag("0");
			
			// 更新两个状态
			this.mOutTransferData.setNameAndValue("ApproveFlag", tLCPolDB.getAppFlag());
			this.mOutTransferData.setNameAndValue("UWFlag", tLCPolDB.getUWFlag());
			this.mOutTransferData.setNameAndValue("ApproveCode", tLCPolDB.getApproveCode());
			this.mOutTransferData.setNameAndValue("ApproveDate", tLCPolDB.getApproveDate());
			this.mResult.add(mOutTransferData);
			}
			else if(this.mOperate.equals("INSERT"))
			{
				tLCPolDB.setApproveFlag(inLCPolSchema.getApproveFlag());
				tLCPolDB.setUWFlag(inLCPolSchema.getUWFlag());
				tLCPolDB.setApproveCode(inLCPolSchema.getApproveCode());
				tLCPolDB.setApproveDate(inLCPolSchema.getApproveDate());
				outCPolSchema.setSchema(tLCPolDB.getSchema());
			}
			//if()
		} catch (Exception e) {
			// @@错误处理
			CError.buildErr(this,"数据处理错误");
			return false;
		}
		
		

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

	public static void main(String[] args) {
		ChangePlanBL ChangePlanBL1 = new ChangePlanBL();
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
