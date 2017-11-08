package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.easyscan.PathHelper;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageDownloader;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageUploader;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.JPGImageCut;
import com.sinosoft.utility.JimiImage;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class SignatureBL implements BusinessService{
private static Logger logger = Logger.getLogger(SignatureBL.class);
	private VData mInputData = new VData();
	private TransferData mTransferData = new TransferData();
	private Reflections mRef = new Reflections();
	public CErrors mErrors = new CErrors(); 
	private VData mResult = new VData();
	
	private MMap map = new MMap();
	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData cInputData, String Operater) {
		
		mInputData = (VData) cInputData.clone();
		
		if (!getInputData()) {
			return false;
		}
		if(!dealData()){
			return false;
		}
		return true;
	}
	private boolean getInputData() {
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		if(mTransferData == null){
			CError.buildErr(this, "数据传入不完整");
			return false;
		}
		return true;
	}
	private boolean dealData(){
		String sX1 = (String)mTransferData.getValueByName("x1");
		String sY1 = (String)mTransferData.getValueByName("y1");
		String sW = (String)mTransferData.getValueByName("w");
		String sH = (String)mTransferData.getValueByName("h");
		String pageName = (String)mTransferData.getValueByName("pageName");
		String docCode = (String)mTransferData.getValueByName("docCode");
		String realPath = (String)mTransferData.getValueByName("realPath");
		List<String> filesToDelete = new ArrayList<String>();
		int x1,y1,w,h;
		if(sX1 == null || sX1.equals("")||sY1 == null || sY1.equals("")||sW == null || sW.equals("")||sH == null || sH.equals("")){
			CError.buildErr(this, "截图参数不能为空!");
			return false;
		}
		try{
			x1 = Integer.parseInt(sX1);
			y1 = Integer.parseInt(sY1);
			w = Integer.parseInt(sW);
			h = Integer.parseInt(sH);
		}catch(Exception ex){
			CError.buildErr(this, "截图参数不规范!");
			return false;
		}

		if(pageName == null || pageName.equals("") || docCode == null || docCode.equals("")){
			CError.buildErr(this, "传入数据不完整!");
			return false;
		}
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(docCode);
		ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.query();
		if(tES_DOC_MAINSet == null ||tES_DOC_MAINSet.size()<1){

			CError.buildErr(this, "未找到ES_DOC_MAIN相关数据!");
			return false;
		}
		ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);
		ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		tES_DOC_PAGESDB.setDocID(tES_DOC_MAINSchema.getDocID());
		tES_DOC_PAGESDB.setPageName(pageName);
		ES_DOC_PAGESSet tES_DOC_PAGESSet = tES_DOC_PAGESDB.query();
		if(tES_DOC_PAGESSet == null ||tES_DOC_PAGESSet.size()<1){
			CError.buildErr(this, "未找到ES_DOC_Page相关数据!");
			return false;
		}
		ES_DOC_PAGESSchema tPageForCut = tES_DOC_PAGESSet.get(1);
		//1、判断签名是否存在
		boolean osExists = false;
		String tOsPageNamePath = "";
		String tOsPageName = "";
		tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		tES_DOC_PAGESDB.setDocID(tES_DOC_MAINSchema.getDocID());
		tES_DOC_PAGESDB.setPageType("7");//7为签名
		ES_DOC_PAGESSet osES_DOC_PAGESSet = tES_DOC_PAGESDB.query();
		if(osES_DOC_PAGESSet != null && osES_DOC_PAGESSet.size()>0 ){
			//要删除原签名
			osExists = true;
			tOsPageNamePath = PathHelper.autoTrimPath(osES_DOC_PAGESSet.get(1).getPicPath(), false, false, '/') +osES_DOC_PAGESSet.get(1).getPageName()+osES_DOC_PAGESSet.get(1).getPageSuffix();
			tOsPageName = realPath + tOsPageNamePath;
		}
		//开始截图 1、先转成JPG
		String tPicSourceFolderPath = PathHelper.autoTrimPath(tPageForCut.getPicPath(), false, false, '/');
		String tPicSourceFilename = pageName + tPageForCut.getPageSuffix();
		String tPicSourcePath = tPicSourceFolderPath + tPicSourceFilename;
		String tPicSource = realPath + tPicSourcePath;
		String tPicChgDest = tPicSource.substring(0, tPicSource.lastIndexOf(".")) + ".jpg";
		File fPicSource = new File(tPicSource);
		if(!fPicSource.exists()){
			logger.debug("文件 \"" + tPicSource + "\" 不存在，将尝试从云端获取文件。");
			CloudObjectStorageDownloader cosd = CloudObjectStorageFactory.getCloudObjectStorageDownloader();
			if(cosd == null){
				CError.buildErr(this, "无法找到文件\"" + tPicSourcePath + "\".");
				return false;
			}
			InputStream is = cosd.downloadFileFromCloud(cosd.getCloudFilePath(tPicSourceFolderPath, tPicSourceFilename), tPicSource);
			try{
			    is.close();
			}catch(Exception ise){
				
			}
			// 此文件是从云端获取的，待新签名生成后，此文件将要被删除
			filesToDelete.add(tPicSource);
		}
		
		JimiImage tJimiImage = new JimiImage();

		if(!tJimiImage.convertToJPG(tPicSource, tPicChgDest, 100)){
			// 删除从云端获取的页面文件以及在本地的新签名文件
			for(String path : filesToDelete){
				try{
					File pF = new File(path);
					if(pF.exists()){
						pF.delete();
					}
				}catch(Exception e){}
			}
			CError.buildErr(this, "图片格式转换失败!");
			return false;
		}

		File fPicChgDest = new File(tPicChgDest);//临时jpg文件

		String tPageID = PubFun1.CreateMaxNo("PageID", 1);
		String tNewPageName = "F"+tPageID;
		String tPicCutDestPath = PathHelper.autoTrimPath(tPageForCut.getPicPath(), false, false, '/') + tNewPageName + ".jpg";
		String tPicCutDest = realPath + tPicCutDestPath;//裁剪后的图片位置

		JPGImageCut o = new JPGImageCut(x1,y1,w,h); 
		o.setSrcpath(tPicChgDest); 
		o.setSubpath(tPicCutDest); 
		try{
			o.cut();
		}catch(Exception ex){
			// 删除从云端获取的页面文件以及在本地的新签名文件
			for(String path : filesToDelete){
				try{
					File pF = new File(path);
					if(pF.exists()){
						pF.delete();
					}
				}catch(Exception e){}
			}
			CError.buildErr(this, "图片截取失败!");
			return false;
		}
		//删除临时JPG文件
		fPicChgDest.delete();
		
		File fPicCutDest = new File(tPicCutDest);//临时jpg文件
		
		//生成新的page 删除旧的page 
		ES_DOC_PAGESSchema newPage = new ES_DOC_PAGESSchema();
		mRef.transFields(newPage, tPageForCut);
		newPage.setPageID(tPageID);
		String tSql = "Select max(pagecode)+1 from es_doc_pages where docid = '"+"?tes?"+"' and pagetype <> '7'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("tes", tES_DOC_MAINSchema.getDocID());
		String tPageCode = new ExeSQL().getOneValue(sqlbv);
		newPage.setPageCode(tPageCode);
		newPage.setPageName(tNewPageName);
		newPage.setPageSuffix(".jpg");
		newPage.setPageType("7");
		newPage.setMakeDate(PubFun.getCurrentDate());
		newPage.setMakeTime(PubFun.getCurrentTime());
		newPage.setModifyDate(PubFun.getCurrentDate());
		newPage.setModifyTime(PubFun.getCurrentTime());
		if(osExists){
			//删除原数据库签名记录
			map.put(osES_DOC_PAGESSet, "DELETE");
		}
		map.put(newPage, "INSERT");
		VData tVData = new VData();
		tVData.add(map);
		//数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tVData, "")) {
			fPicCutDest.delete();
			CError.buildErr(this, "数据库提交失败!");
			// 删除从云端获取的页面文件
			for(String path : filesToDelete){
				try{
					File pF = new File(path);
					if(pF.exists()){
						pF.delete();
					}
				}catch(Exception e){}
			}
			return false;
		}
		CloudObjectStorageUploader cosu = null;
		
		// 如果之前的文件是从云端获取的，则新生成的文件也要上传至云端。
		if(!filesToDelete.isEmpty()){
			cosu = CloudObjectStorageFactory.getCloudObjectStorageUploader();
		}
		if(cosu != null && fPicCutDest.exists()){
			// 尝试上传新生成的文件
           if( cosu.uploadFileToCloud(tPicCutDest, cosu.getCloudFilePath(tPicCutDestPath))){
        	   // 上传成功，本地的文件将被删除
        	   filesToDelete.add(tPicCutDest);
           }
		}
		//数据库保存成功后, 如果存在原签名删除原签名文件
		if(osExists){
			//删除原数据库签名记录
			File fOsPageName = new File(tOsPageName);
			if(fOsPageName.exists()){
			    try{
				    fOsPageName.delete();
			    }catch(Exception dltE){}
			}
			if(cosu != null){
				try{
					// 尝试从云端删除原签名文件
					cosu.deleteFileFromCloud(cosu.getCloudFilePath( tOsPageNamePath));
				}catch(Exception cosuE){}
			}
		}

		// 删除从云端获取的页面文件以及在本地的新签名文件
		for(String path : filesToDelete){
			try{
				File pF = new File(path);
				if(pF.exists()){
					pF.delete();
				}
			}catch(Exception e){}
		}
		return true;
	}
	
}
