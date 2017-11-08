package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.easyscan.CallService;
import com.sinosoft.lis.easyscan.service.util.TZSNumCheck;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.utility.VData;

public class DocTBTZSService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBTZSService.class);

	protected boolean deal() {
		String target=TZSNumCheck.checkTZSNum(mES_DOC_MAINSchema.getDocCode());
		mES_DOC_MAINSchema.setSubType(target);
		ES_DOC_MAINSet tES_DOC_MAINSet=new ES_DOC_MAINSet();
		tES_DOC_MAINSet.add(mES_DOC_MAINSchema);
		VData tVData=new VData();
		tVData.add(tES_DOC_MAINSet);
		tVData.add(mES_DOC_PAGESSet);
		CallService tCallService=new CallService();
		if(!tCallService.submitData(tVData, "", "1")){
			this.mErrors=tCallService.mErrors;
			return false;
		}
		this.mResult.add(tCallService.getResult().getObjectByObjectName("MMap", 0));
		return true;
	}

}
