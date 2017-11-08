package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: UI功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-17
 */
public class ProposalApproveUI {
private static Logger logger = Logger.getLogger(ProposalApproveUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public ProposalApproveUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "aaa";
		LCPolSchema p = new LCPolSchema();
		p.setProposalNo("86130020020110000001");
		p.setApproveFlag("1");
		VData tVData = new VData();
		tVData.add(p);
		tVData.add(tG);
		ProposalApproveUI ui = new ProposalApproveUI();
		if (ui.submitData(tVData, "") == true)
			logger.debug("---ok---");
		else
			logger.debug("---NO---");
	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		ProposalApproveBL tProposalApproveBL = new ProposalApproveBL();

		logger.debug("---UI BEGIN---");
		if (tProposalApproveBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tProposalApproveBL.mErrors);
			return false;
		}

		return true;
	}

}
