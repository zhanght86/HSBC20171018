package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.io.File;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
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

public class PEdorCSDetailBL implements BusinessService{
private static Logger logger = Logger.getLogger(PEdorCSDetailBL.class);
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
		String EdorAcceptNo = (String)mTransferData.getValueByName("EdorAcceptNo");
		String realPath = (String)mTransferData.getValueByName("realPath");
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

		if(pageName == null || pageName.equals("") || EdorAcceptNo == null || EdorAcceptNo.equals("")){
			CError.buildErr(this, "传入数据不完整!");
			return false;
		}
		
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(EdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "保全受理信息查询失败!");
			return false;
		}
		String sEdorState = tLPEdorAppDB.getEdorState();
		if (sEdorState.equals("2")) {
			CError.buildErr(this, "保全受理已申请确认，不能修改保全项目!");
			return false;
		}
		if (sEdorState.equals("4")) {
			CError.buildErr(this, "保全受理已逾期终止，不能修改保全项目!");
			return false;
		}
		if (sEdorState.equals("0")) {
			CError.buildErr(this, "保全受理已保全确认，不能修改保全项目!");
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(EdorAcceptNo);
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询批改项目信息失败!");
			return false;
		}
		
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(EdorAcceptNo);
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
		if(tES_DOC_MAINSet == null ||tES_DOC_MAINSet.size()<1){
			CError.buildErr(this, "未找到ES_DOC_Page相关数据!");
			return false;
		}
		ES_DOC_PAGESSchema tPageForCut = tES_DOC_PAGESSet.get(1);
		//1、判断签名是否存在
		boolean osExists = false;
		String tOsPageName = "";
		tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		tES_DOC_PAGESDB.setDocID(tES_DOC_MAINSchema.getDocID());
		tES_DOC_PAGESDB.setPageType("7");//7为签名
		ES_DOC_PAGESSet osES_DOC_PAGESSet = tES_DOC_PAGESDB.query();
		if(osES_DOC_PAGESSet != null && osES_DOC_PAGESSet.size()>0 ){
			//要删除原签名
			osExists = true;
			tOsPageName = realPath + osES_DOC_PAGESSet.get(1).getPicPath()+osES_DOC_PAGESSet.get(1).getPageName()+osES_DOC_PAGESSet.get(1).getPageSuffix();
		}
		//开始截图 1、先转成JPG
		String tPicSource = realPath + tPageForCut.getPicPath()+pageName+tPageForCut.getPageSuffix();
		String tPicChgDest = tPicSource.substring(0, tPicSource.lastIndexOf(".")) + ".jpg";
		JimiImage tJimiImage = new JimiImage();

		if(!tJimiImage.convertToJPG(tPicSource, tPicChgDest, 100)){
			CError.buildErr(this, "图片格式转换失败!");
			return false;
		}

		File fPicChgDest = new File(tPicChgDest);//临时jpg文件

		String tPageID = PubFun1.CreateMaxNo("PageID", 1);
		String tNewPageName = "F"+tPageID;
		String tPicCutDest = realPath + tPageForCut.getPicPath()+tNewPageName+".jpg";//裁剪后的图片位置

		JPGImageCut o = new JPGImageCut(x1,y1,w,h); 
		o.setSrcpath(tPicChgDest); 
		o.setSubpath(tPicCutDest); 
		try{
			o.cut();
		}catch(Exception ex){
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
		String tSql = "Select max(pagecode)+1 from es_doc_pages where docid = '?docid?' and pagetype <> '7'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("docid", tES_DOC_MAINSchema.getDocID());
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
		String tItemSql= "Update LPEdorItem set EdorState = '1' , ModifyDate = '?ModifyDate?',ModifyTime = '?ModifyTime?'"
					   + " Where EdorAcceptNo = '?EdorAcceptNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tItemSql);
		sbv1.put("ModifyDate", PubFun.getCurrentDate());
		sbv1.put("ModifyTime", PubFun.getCurrentTime());
		sbv1.put("EdorAcceptNo", EdorAcceptNo);
		map.put(sbv1, "UPDATE");
		String tMainSql= "Update LPEdorMain set EdorState = '1' , ModifyDate = '?ModifyDate?',ModifyTime = '?ModifyTime?'"
		   + " Where EdorAcceptNo = '?EdorAcceptNo?'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tMainSql);
		sbv2.put("ModifyDate", PubFun.getCurrentDate());
		sbv2.put("ModifyTime", PubFun.getCurrentTime());
		sbv2.put("EdorAcceptNo", EdorAcceptNo);
		map.put(sbv2, "UPDATE");
		VData tVData = new VData();
		tVData.add(map);
		//数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tVData, "")) {
			fPicCutDest.delete();
			CError.buildErr(this, "数据库提交失败!");
			return false;
		}
		
		//数据库保存成功后, 如果存在原签名删除原签名文件
		if(osExists){
			//删除原数据库签名记录
			File fOsPageName = new File(tOsPageName);
			fOsPageName.delete();
		}

		return true;
	}
	
}
