package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLReportDB;
import com.sinosoft.lis.easyscan.service.util.ClaimStateCheck;
import com.sinosoft.utility.CError;

public class DocLPCheckService extends BaseService {
private static Logger logger = Logger.getLogger(DocLPCheckService.class);

	protected boolean deal() {
//		LLReportDB tLLReportDB = new LLReportDB();
//		tLLReportDB.setRptNo(mES_DOC_MAINSchema.getDocCode());
//		if (tLLReportDB.getCount() < 1) {
//			CError.buildErr(this, "没有匹配的报案号或立案号，不能上传！请核对单证号码");
//			return false;
//		}

		if(!ClaimStateCheck.isRgtNoExist(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "没有匹配的报案号或立案号，不能上传！请核对单证号码");
			return false;
		}
		
		if (mES_DOC_MAINSchema.getSubType().equals("CA001")
				|| mES_DOC_MAINSchema.getSubType().equals("CA003")) {
			if (ClaimStateCheck.isClaimAccept(mES_DOC_MAINSchema.getDocCode())) {
				CError.buildErr(this, "案件已签批，不允许上传此类型");
				return false;
			}
		}
//		if (mES_DOC_MAINSchema.getSubType().equals("CA002")) {
//			if (!ClaimStateCheck.isClaimAccept(mES_DOC_MAINSchema.getDocCode())) {
//				CError.buildErr(this, "案件未签批，不允许上传此类型");
//				return false;
//			}
//		}
		return true;
	}

}
