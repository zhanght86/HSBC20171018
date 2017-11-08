package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 数据上载预处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang
 * @version 1.0
 */
public class UploadPrepareUI {
private static Logger logger = Logger.getLogger(UploadPrepareUI.class);
	private VData mInputData;
	private GlobalInput tG = new GlobalInput();
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	private String mRtnCode = "0";
	private String mRtnDesc = "";

	public UploadPrepareUI() {
	}

	// 传输数据的公共方法
	// cInputData的构成：
	// ES_DOC_MAINSet Doc集合
	// ES_DOC_PAGESSet Page集合
	// String() Page_URL数组
	// String RtnCode
	// String RtnDesc
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		UploadPrepareBL tUploadPrepareBL = new UploadPrepareBL();
		tReturn = tUploadPrepareBL.submitData(mInputData, cOperate);

		// 如果有需要处理的错误，则返回
		if (!tReturn) {
			mErrors.copyAllErrors(tUploadPrepareBL.mErrors);
			mRtnCode = mErrors.getError(0).errorNo;
			mRtnDesc = mErrors.getError(0).errorMessage;
			tReturn = false;
		}

		// 返回数据处理
		mResult.clear();
		// BL层取得数据
		mResult = tUploadPrepareBL.getResult();
		mResult.setElementAt(mRtnCode, 3);
		mResult.setElementAt(mRtnDesc, 4);

		mInputData = null;

		return tReturn;
	}

	// 返回数据的公共方法
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		UploadPrepareUI uploadPrepareUI1 = new UploadPrepareUI();
		ParameterDataConvert convert = new ParameterDataConvert();

		try {
			VData vData = new VData();
			StringBuffer strBuf = new StringBuffer(1024);
			String strXML = "<?xml version=\"1.0\"?> ";

			strXML = strXML
					+ "<DATA><DOC><DocID>200571221200390</DocID><DocCode>1001010000105688</DocCode><BussType>TB</BussType><SubType>1001</SubType><NumPages>1</NumPages><DocFlag>0</DocFlag><DocRemark></DocRemark><ScanOperator>001</ScanOperator><ManageCom>86010101</ManageCom><MakeDate>2005-07-12</MakeDate><MakeTime>19:17:47</MakeTime><ModifyDate>2005-07-12</ModifyDate><ModifyTime>19:17:47</ModifyTime><DocVersion>01</DocVersion><BussCode>1001010000005688</BussCode><ScanNo></ScanNo></DOC><PAGE><PageID></PageID><DocID>200571221200390</DocID><PageCode>1</PageCode><HostName></HostName><PageName>D:\\EasyScan\\ES_DEV\\EasyScanMinSheng\\Original\\200571221200390RE1.tif</PageName><PageSuffix></PageSuffix><PicPathFTP></PicPathFTP><PageFlag>0</PageFlag><PicPath>0</PicPath><PageType>0</PageType><ManageCom>86010101</ManageCom><Operator>001</Operator><MakeDate>2005-07-12</MakeDate><MakeTime>19:17:47</MakeTime><ModifyDate>2005-07-12</ModifyDate><ModifyTime>19:17:47</ModifyTime><Page_URL>D:\\EasyScan\\ES_DEV\\EasyScanMinSheng\\Original\\200571221200390RE1.tif</Page_URL><Page_BarCode></Page_BarCode><ScanNo/></PAGE><RETURN><NUMBER/><MESSAGE>http://10.1.18.217:8080/easyscan/UploadPrepare.jsp</MESSAGE></RETURN></DATA>";
			convert.xMLToVData(strXML, vData);

			uploadPrepareUI1.submitData(vData, "");
			vData = uploadPrepareUI1.getResult();

			UploadImageFile uploadImageFile = new UploadImageFile(vData);
			logger.debug(uploadImageFile.getSaveAsName("1", "ABC.tif"));

			convert.vDataToXML(strBuf, vData);

			logger.debug(strBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
