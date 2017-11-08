package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.easyscan.service.util.DocPageCheck;
import com.sinosoft.lis.easyscan.service.util.PolNumCheck;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.utility.CError;

public class YBTCheckService extends BaseService {
private static Logger logger = Logger.getLogger(YBTCheckService.class);

	public boolean deal() {
		logger.debug("邮保通事后扫描校验接口");
		ES_DOC_MAINSchema tES_DOC_MAINSchema = mES_DOC_MAINSchema;
		if (!PolNumCheck.checkPolNum(tES_DOC_MAINSchema.getDocCode())) {
			CError.buildErr(this, "单证号码中含特殊字符，无法上载!");
			return false;
		}
		if (tES_DOC_MAINSchema.getBussType().equals("TB")
				&& (tES_DOC_MAINSchema.getDocCode().startsWith("861501") || tES_DOC_MAINSchema.getDocCode().startsWith("861502")
						|| tES_DOC_MAINSchema.getDocCode().startsWith("862501") || tES_DOC_MAINSchema
						.getDocCode().startsWith("863501")|| tES_DOC_MAINSchema
						.getDocCode().startsWith("3110"))) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPrtNo(tES_DOC_MAINSchema.getDocCode().trim());
			if (tLCPolDB.getCount() > 0) {
				tES_DOC_MAINSchema.setInputState("1");
				logger.debug("邮保通事后扫描发现"
						+ tES_DOC_MAINSchema.getDocCode());
			} else {
				LBPolDB tLBPolDB = new LBPolDB();
				tLBPolDB.setPrtNo(tES_DOC_MAINSchema.getDocCode().trim());
				if (tLBPolDB.getCount() > 0) {
					tES_DOC_MAINSchema.setInputState("1");
					logger.debug("邮保通事后扫描发现"
							+ tES_DOC_MAINSchema.getDocCode());
				} else {
					CError.buildErr(this, "邮（银）保通保单应事后扫描");
					return false;
				}
			}
			//邮保通事后扫描 【不能少于 7 页】
			//2010-4-29 夜观需求变更 更改为不能少于6页
			if(!DocPageCheck.isMoreNumPage(mES_DOC_MAINSchema.getSubType(), mES_DOC_PAGESSet.size(),6)){
				CError.buildErr(this, "请将投保单、产品说明书、人身投保提示、回执扫描完整！");
				return false;
			}
		} else {
			CError.buildErr(this, "非邮（银）保通保单");
			return false;
		}

		return true;
	}

}
