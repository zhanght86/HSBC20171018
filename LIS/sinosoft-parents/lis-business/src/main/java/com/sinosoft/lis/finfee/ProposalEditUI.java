package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author zhangli
 * @version 1.0
 */
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ProposalEditUI {
private static Logger logger = Logger.getLogger(ProposalEditUI.class);
	/** 传入数据容器* */
	private VData mInputDate = new VData();
	/** 传出数据容器* */
	private VData mResult = new VData();
	/** 数据操作字符串* */
	private String mOperate;
	/* 数据错误处理类 */
	public CErrors mErrors = new CErrors();

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputDate = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---ModifyLJAGet BL BEGIN---");
		ProposalEditBL tProposalEditBL = new ProposalEditBL();
		if (tProposalEditBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tProposalEditBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tProposalEditBL.getResult();
		}
		logger.debug("---ModifyLJCertifyGet END---");
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

	/**
	 * 主函数，测试用
	 */

	public ProposalEditUI() {

	}

}
