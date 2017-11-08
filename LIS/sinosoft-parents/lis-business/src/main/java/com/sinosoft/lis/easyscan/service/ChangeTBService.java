package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.utility.CError;

public class ChangeTBService extends BaseService {
private static Logger logger = Logger.getLogger(ChangeTBService.class);

	protected boolean deal() {
		if (!mES_DOC_MAINSchema.getBussType().startsWith("TB")) {
			CError.buildErr(this, "ChangeTBService not allowed.");
			return false;
		}
		mES_DOC_MAINSchema.setBussType("TB");
//		for (int i = 0; i < mInputData.size(); i++) {
//			Object o = mInputData.getObject(i);
//			if (o instanceof ES_DOC_RELATIONSchema) {
//				if (((ES_DOC_RELATIONSchema) o).getBussType().startsWith("TB"))
//					((ES_DOC_RELATIONSchema) o).setBussType("TB");
//			}
//			if (o instanceof ES_DOC_RELATIONSet) {
//				ES_DOC_RELATIONSet set = (ES_DOC_RELATIONSet) o;
//				for (int j = 1; j <= set.size(); j++) {
//					if (set.get(j).getBussType().startsWith("TB"))
//						set.get(j).setBussType("TB");
//				}
//			}
//		}
		return true;
	}

}
