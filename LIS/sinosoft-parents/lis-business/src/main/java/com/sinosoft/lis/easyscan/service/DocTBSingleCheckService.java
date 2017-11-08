package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.easyscan.service.util.PolNumCheck;
import com.sinosoft.utility.CError;

public class DocTBSingleCheckService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBSingleCheckService.class);

	public boolean deal() {
		String doccode = mES_DOC_MAINSchema.getDocCode();
		if (doccode == null || doccode.equals("") || doccode.length() != 14) {
			CError.buildErr(this, "传入数据出错!");
			return false;
		}

		if (!PolNumCheck.hasSaveDoc(doccode, mES_DOC_MAINSchema.getSubType())) {
			CError.buildErr(this, "同一个印刷号只能上载一次,无法上载!");
			return false;
		}

		if (!PolNumCheck.checkPolNum(doccode)) {
			CError.buildErr(this, "单证号码中含特殊字符，无法上载!");
			return false;
		}

		//TODO 屏蔽保单号校验，根据客户实际情况再决定是否启用，2011-08-04
//		if (!PolNumCheck.isPolSingle(doccode)) {
//			CError.buildErr(this, "非个险保单号!");
//			return false;
//		}
		return true;
	}

}
