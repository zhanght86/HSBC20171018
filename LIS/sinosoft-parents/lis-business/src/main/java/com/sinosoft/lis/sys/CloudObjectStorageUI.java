package com.sinosoft.lis.sys;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * 表示一个处理接收云对象存储的UI处理服务
 * @author Wang Zhang
 *
 */
public class CloudObjectStorageUI  implements com.sinosoft.service.BusinessService{
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	@SuppressWarnings("unchecked")
	@Override
	public boolean submitData(VData vData, String Operater) {
		// TODO Auto-generated method stub
		try{
			CloudObjectStorageBL cloudObjectStorageBL = new CloudObjectStorageBL();
	 	    boolean invokeResult = cloudObjectStorageBL.submitData(vData, Operater);
	 	    VData tmp = cloudObjectStorageBL.getResult();
	 	    for(Object d : tmp){
	 	    	mResult.add(d);
	 	    }
	 	    this.mErrors.copyAllErrors(cloudObjectStorageBL.getErrors());
		    return invokeResult;
		}catch(Exception e){
			CError.buildErr(this, e.getMessage());
			return false;
		}
	}

	@Override
	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

	@Override
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}    
}
