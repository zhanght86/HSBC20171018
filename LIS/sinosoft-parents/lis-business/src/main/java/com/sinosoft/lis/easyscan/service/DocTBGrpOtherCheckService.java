package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.easyscan.service.util.PolNumCheck;
import com.sinosoft.lis.easyscan.service.util.PolStateCheck;
import com.sinosoft.utility.CError;

public class DocTBGrpOtherCheckService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBGrpOtherCheckService.class);

	protected boolean deal() {
		if (!PolNumCheck.checkPolNum(mES_DOC_MAINSchema.getDocCode())) {
			CError.buildErr(this, "单证号码中含特殊字符，无法上载!");
			return false;
		}

		if (!PolNumCheck.isPolGrp(mES_DOC_MAINSchema.getDocCode())) {
			CError.buildErr(this, "单证号错误!");
			return false;
		}
		
		if(!PolStateCheck.isPolScaned(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "投保单影像件不存在!");
			return false;
		}
		
		return true;
	}
}
