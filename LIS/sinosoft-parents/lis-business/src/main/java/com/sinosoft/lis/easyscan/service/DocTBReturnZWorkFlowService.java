package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 新契约变更通知书上载工作流处理接口实现类</p>
 * 业务类型号码为11
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author zhangzm
 * @version 1.0
 */

import com.sinosoft.lis.easyscan.service.util.PolNumCheck;
import com.sinosoft.lis.easyscan.service.util.PolStateCheck;
import com.sinosoft.utility.CError;

public class DocTBReturnZWorkFlowService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBReturnZWorkFlowService.class);

	public DocTBReturnZWorkFlowService() {
	}

	public boolean deal() {
		if(!PolNumCheck.checkPolLenNum(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "单证号错误！");
			return false;
		}
		
		if (!PolNumCheck.hasSaveDoc(mES_DOC_MAINSchema.getDocCode(), mES_DOC_MAINSchema.getSubType())) {
			CError.buildErr(this, "同一个印刷号只能上载一次,无法上载!");
			return false;
		}

		if(!PolStateCheck.isPolAvailable(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "无此印刷号保单信息，请核对印刷号");
			return false;
		}

		if(PolStateCheck.isPolHasHZ(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "此保单的回执日期已录入，无法上载扫描页");
			return false;
		}
		
		if(!PolStateCheck.isPrinted(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "此保单尚未打印，无法上载扫描页");
			return false;
		}
		return true;
	}

}
