package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.easyscan.service.util.PolNumCheck;
import com.sinosoft.utility.CError;

public class DocTBGrpCheckService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBGrpCheckService.class);

	protected boolean deal() {
		if (!PolNumCheck.checkPolNum(mES_DOC_MAINSchema.getDocCode())) {
			CError.buildErr(this, "单证号码中含特殊字符，无法上载!");
			return false;
		}

		if (!PolNumCheck.hasSaveDoc(mES_DOC_MAINSchema.getDocCode(), mES_DOC_MAINSchema.getSubType())) {
			CError.buildErr(this, "同一个印刷号只能上载一次,无法上载!");
			return false;
		}
		
		if (!PolNumCheck.checkPolNum(mES_DOC_MAINSchema.getDocCode())) {
			CError.buildErr(this, "单证号码中含特殊字符，无法上载!");
			return false;
		}
		
		if (!PolNumCheck.isPolGrp((mES_DOC_MAINSchema.getDocCode()))) {
			CError.buildErr(this, "单证号错误!");
			return false;
		}
		return true;
	}
}
