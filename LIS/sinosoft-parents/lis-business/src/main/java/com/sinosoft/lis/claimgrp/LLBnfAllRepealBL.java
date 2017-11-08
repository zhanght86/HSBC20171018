/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLBnfGatherDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LLBnfGatherSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:清除已保存的全部受益人信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class LLBnfAllRepealBL {
private static Logger logger = Logger.getLogger(LLBnfAllRepealBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private LLBnfSet mLLBnfSet = new LLBnfSet();//受益人明细集合
	
	private LJSGetSet mLJSGetSet = null;
	private LJSGetClaimSet mLJSGetClaimSet = null;
	private LLBnfGatherSet mLLBnfGatherSet = null;

	private String mPolNo = "";
	private String mClmNo = "";
	private String mBnfKind = "";
	private ExeSQL tExeSQL=null;
	private SSRS tSSRS=null;

	public LLBnfAllRepealBL() {
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
		logger.debug("----------LLBnfBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after checkInputData----------");
		
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------LLBnfBL after dealData----------");
		
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after prepareOutputData----------");
		
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		
		mInputData = null;
		return true;
	}
	
	
	/**
	 * 1 循环传入的每个受益人记录,经过js的合并处理,每一条应该是一个受益人,循环查询受益人汇总表,扣减主表金额后如果为0,则删除主表记录,否则只扣减金额,在LLBnf的处理类中会重新执行受益人合并,保存新的合并信息
	 * 2 对于ljsget做同样的处理
	 * 3 对于子表直接删除相应记录即可
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		
		// 更新立案结论
		if (cOperate.equals("REPEAL")) {
			
			String deleteSql1="delete from LLBnfGather where clmno='"+mClmNo+"'" +
				"  and bnfkind='"+mBnfKind+"'";
			map.put(deleteSql1, "DELETE");
				
			// 删除赔付应付表
			String deleteSql4 = " delete from LJSGetClaim where "
								+ "  getnoticeno in(select getnoticeno from ljsget where otherno = '" + mClmNo + "'" + " and othernotype='5' )";
			map.put(deleteSql4, "DELETE");		
						
			String deleteSql2="delete from Ljsget where otherno='"+mClmNo+"'" +
				" and othernotype='5'";
			map.put(deleteSql2, "DELETE");

			// 删除受益人分配表
			String deleteSql3 = " delete from LLBnf where " + " ClmNo = '"
						+ mClmNo + "'" + "  and bnfkind='"+mBnfKind+"'"+ " ";
			map.put(deleteSql3, "DELETE");
				

				
			deleteSql1=null;
			deleteSql2=null;
				
			deleteSql3=null;
			deleteSql4=null;
						
			return true;
		}
		
		return true;
	}
	
	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLBnfBL start getInputData()...");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		mBnfKind = (String) mTransferData.getValueByName("BnfKind");

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		
		if(mClmNo==null||mClmNo.equals("")){
			
			// @@错误处理
			CError.buildErr(this, "传入的赔案号为空!");
			return false;
		}
		
		
		if(mBnfKind==null||mBnfKind.equals("")){
			
			// @@错误处理
			CError.buildErr(this, "传入的受益人分配类型为空!");
			return false;
		}

		return true;
	}

	
	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错.");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
