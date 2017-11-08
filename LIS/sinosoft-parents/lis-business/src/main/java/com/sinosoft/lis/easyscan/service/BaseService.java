package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.easyscan.EasyScanService;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public abstract class BaseService implements EasyScanService {
private static Logger logger = Logger.getLogger(BaseService.class);
	/** 传入数据的容器 */
	protected VData mInputData = new VData();

	/** 传出数据的容器 */
	protected VData mResult = new VData();

	/** 数据操作字符串 */
	protected String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	
	protected ES_DOC_MAINSchema mES_DOC_MAINSchema=new ES_DOC_MAINSchema();
	protected ES_DOC_PAGESSet mES_DOC_PAGESSet=new ES_DOC_PAGESSet();

	public final boolean submitData(VData cInputData, String cOperate) {
		mOperate=cOperate;
		mInputData=cInputData;
		ES_DOC_MAINSet tES_DOC_MAINSET = (ES_DOC_MAINSet) cInputData.get(0);
		if (tES_DOC_MAINSET ==null || tES_DOC_MAINSET.size() != 1) {
			CError.buildErr(this, "传入数据出错!");
			return false;
		}
		mES_DOC_MAINSchema=tES_DOC_MAINSET.get(1);
		
		mES_DOC_PAGESSet = (ES_DOC_PAGESSet) cInputData.get(1);
		if (tES_DOC_MAINSET ==null || tES_DOC_MAINSET.size() < 1) {
			CError.buildErr(this, "传入图片数据出错!");
			return false;
		}
		
		try{
			return deal();
		}catch(Exception ex){
			CError.buildErr(this, ex.getMessage());
			return false;
		}
	}

	protected abstract boolean deal();

	public final CErrors getErrors() {
		return mErrors;
	}

	public final VData getResult() {
		return mResult;
	}
}
