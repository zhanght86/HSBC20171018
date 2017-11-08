package com.sinosoft.lis.pubfun;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.sinosoft.lis.easyscan.ImageToBase64Converter;
import com.sinosoft.lis.easyscan.PathHelper;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageDownloader;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 表示一个签名图呈现BusinessService. 用于从本地或云端获取签名图片并将其转换为base64字符串然后作为结果输出
 * @author Wang Zhang
 *
 */
public class SignatureUI implements BusinessService {

	private VData mInputData = null;
	private TransferData mTransferData = null;
	private String mDocCode = null;
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	@Override
	public boolean submitData(VData vData, String Operater) {
		// TODO Auto-generated method stub
		this.mInputData = (VData)vData.clone();
		if(!getInputData()){
			return false;
		}
		
		return dealData();
	}

	private boolean getInputData() {
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		if(mTransferData == null){
			CError.buildErr(this, "数据传入不完整");
			return false;
		}
		Object tmp = mTransferData.getValueByName("docCode");
		if(tmp == null || !(tmp instanceof String)){
			CError.buildErr(this, "无法从数据中获取到docCode.");
			return false;
		}
		this.mDocCode = (String)tmp;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private boolean dealData(){
		try {
			String tServerBasePath = PathHelper.getServerBasePath(this.mDocCode);
			tServerBasePath = PathHelper.autoTrimPath(tServerBasePath, true, false, '/');
			ES_DOC_PAGESSchema tES_DOC_PAGESSchema = PathHelper.getFirstSignaturePAGESSchemaByDocCode(this.mDocCode);
			if (tServerBasePath == null || tES_DOC_PAGESSchema == null) {
				CError.buildErr(this, "无法从数据库中获取到docCode: " + this.mDocCode + " 的文件路径");
				return false;
			}
			String picPath = PathHelper.autoTrimPath(tES_DOC_PAGESSchema.getPicPath(), false, false, '/');
			String pageName = PathHelper.autoTrimPath(tES_DOC_PAGESSchema.getPageName(), false, true, '/');
			String pageSuffix = tES_DOC_PAGESSchema.getPageSuffix();
			String localfilename = tServerBasePath + picPath
					+ pageName + pageSuffix;
			File localFile = new File(localfilename);
			InputStream is = null;
			if(localFile.exists()){
				// mResult.add(localfilename);
				is = new FileInputStream(localfilename);
			}else{
				CloudObjectStorageDownloader cloudObjectStorageDownloader = CloudObjectStorageFactory.getCloudObjectStorageDownloader();
				if(cloudObjectStorageDownloader == null){
					CError.buildErr(this, "没有找到可用云存储。将放弃从云端获取图片.");
					return false;
				}
				String remotePath = cloudObjectStorageDownloader.getCloudFilePath(picPath, pageName + pageSuffix);
				is = cloudObjectStorageDownloader.downloadFileFromCloud(remotePath);
			}
			if(is == null){
				throw new NullPointerException("无论从云端亦或本地都无法找到文件");
			}
			String result = null;
			ImageToBase64Converter itb64c = new ImageToBase64Converter();
			try{
				result = itb64c.getImageBase64String(is);
				is.close();
			}catch(Exception ce){
				
			}
			if(result == null){
				CError.buildErr(this, "将云端文件转换为base64时出错.");
				return false;
			}
			mResult.add(result);
			return true;
		} catch (Exception e) {
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
