package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.easyscan.service.util.ClaimStateCheck;
import com.sinosoft.utility.CError;

public class DOCCheckClaimService extends BaseService {
private static Logger logger = Logger.getLogger(DOCCheckClaimService.class);

	public DOCCheckClaimService(){}
	
	protected boolean deal() {
		if(mES_DOC_MAINSchema.getSubType().equals("CA001")){
			if (ClaimStateCheck.isClaimExist(mES_DOC_MAINSchema.getDocCode(), "CA001")) {
				CError.buildErr(this, "本次立案已存在，不允许再次上传");
				return false;
			}
		}
		if(mES_DOC_MAINSchema.getSubType().equals("CA001")){
			if (ClaimStateCheck.isRelationExist(mES_DOC_MAINSchema.getDocCode(), "CA001")){
				CError.buildErr(this, "该申请书号已经上传过");
				return false;
			}
		}
		return true;
	}

}
