/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:理赔立案信息提交类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft1
 * </p>
 * 
 * @author zhangzheng
 * @todo 1 提交BL层处理的数据 2 理赔换号处理
 * @version 1.0
 */
public class LLClaimScanRegisterBLF {
private static Logger logger = Logger.getLogger(LLClaimScanRegisterBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 提交数据容器 */
	private MMap map = new MMap();

	private TransferData mTransferData = new TransferData();

	public LLClaimScanRegisterBLF() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------BLF after getInputData----------");
		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------BLF after checkInputData----------");
		// 进行业务处理,得到业务处理返回值
		if (!dealData()) {
			return false;
		}
		logger.debug("----------BLF after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		
		if(cOperate.equals("UPDATE")||cOperate.equals("INSERT")){
			
			 PubSubmit tPubSubmit = new PubSubmit();
		     if (!tPubSubmit.submitData(mResult, ""))
		     {
		         // @@错误处理
		         CError.buildErr(this, "数据提交失败!");
		         return false;
		     }
		}

	     
		//释放强引用
		mInputData = null;
	     
		logger.debug("----------BLF after prepareOutputData----------");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 提交到BL处理返回处理完数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("----------BLF dealData BEGIN----------");

		LLClaimScanRegisterBL tLLClaimScanRegisterBL = new LLClaimScanRegisterBL();
		if (!tLLClaimScanRegisterBL.submitData(mInputData, mOperate)) {
			
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimScanRegisterBL.mErrors);
			mResult.clear();
			mInputData = null;
			return false;
		} 

		logger.debug("-----start BLF getData from BL");
		
		//获得返回结果
		mInputData.clear();
		mInputData= tLLClaimScanRegisterBL.getResult();
			
		map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
		mTransferData = null;
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
			
		logger.debug("BLF getData -- 操作类型:"+mOperate);
			
		//先报案,后立案或直接立案执行理赔换号处理
//		if(mOperate.equals("INSERT")||mOperate.equals("insert||first")){		
//				
//			//调用换号处理类执行理赔换号
//			LLClaimUpdateRptNoBL tLLClaimUpdateRptNoBL=new LLClaimUpdateRptNoBL();
//			if (!tLLClaimUpdateRptNoBL.submitData(mInputData, mOperate)) {
//					
//				// @@错误处理
//				this.mErrors.copyAllErrors(tLLClaimRegisterBL.mErrors);
//				mResult.clear();
//				return false;
//			} 
//			else
//			{
//				//获得处理结果
//				mResult.clear();
//				mResult= tLLClaimUpdateRptNoBL.getResult();
//			}
//		}
//		else
//		{
			//修改记录或新增立案时直接获得处理结果
			mResult.clear();
			mResult= tLLClaimScanRegisterBL.getResult();
//		}
		
		
		return true;
	}
	


	public VData getResult() {
		return mResult;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		return true;
	}

}
