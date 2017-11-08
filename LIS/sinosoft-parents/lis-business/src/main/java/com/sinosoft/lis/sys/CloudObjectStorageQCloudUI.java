package com.sinosoft.lis.sys;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>表示一个处理接收 qcloud 腾讯云对象存储的UI处理服务</p>
 * @author Wang Zhang
 *
 */
public class CloudObjectStorageQCloudUI implements BusinessService {
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	
	/**
	 * 提交业务处理
	 * @param vData <span>如果进行 SELECT 操作，可为 null; 若进行 UPDATE 操作, 则需包含一个 <a href="eclipse-javadoc:%E2%98%82=lis65_SourceCodeNew/java%3Ccom.sinosoft.utility%7BTransferData.java%E2%98%83TransferData">TransferData</a>
	 *  对象，其中存有 <i>AppId</i>, <i>SecretId</i>, <i>SecretKey</i> 和 <i>BucketName</i> 这四个键以及对应的数值。</span>
	 * @param Operater 表示SELECT或UPDATE操作
	 * @return 如果操作成功返回 true, 否则返回false.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean submitData(VData vData, String Operater) {
		// TODO Auto-generated method stub
		try{
			CloudObjectStorageQCloudBL cloudObjectStorageQCloudBL = new CloudObjectStorageQCloudBL();
	 	    boolean invokeResult = cloudObjectStorageQCloudBL.submitData(vData, Operater);
	 	    VData tmp = cloudObjectStorageQCloudBL.getResult();
	 	    for(Object d : tmp){
	 	    	mResult.add(d);
	 	    }
	 	    this.mErrors.copyAllErrors(cloudObjectStorageQCloudBL.getErrors());
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
