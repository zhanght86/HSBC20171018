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
 * Description: EasyScan上载图象索引处理
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
public class UploadIndexUI {
private static Logger logger = Logger.getLogger(UploadIndexUI.class);
	private VData mInputData;
	private GlobalInput tG = new GlobalInput();
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	private String mRtnCode = "0";
	private String mRtnDesc = "";

	public UploadIndexUI() {
	}

	// 传输数据的公共方法
	// cInputData的构成：
	// ES_DOC_MAINSet Doc集合
	// ES_DOC_PAGESSet Page集合
	// String() Page_URL数组
	// String RtnCode
	// String RtnDesc
	public boolean submitData(VData cInputData, String cOperate) {
		boolean breturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		mResult = cInputData;
		logger.debug("UploadIndexUI------begin");
		UploadIndexBL tUploadIndexBL = new UploadIndexBL();
		// 提交数据处理
		if (!tUploadIndexBL.submitData(mInputData, cOperate)) {
			this.mErrors.clearErrors();
			this.mErrors.copyAllErrors(tUploadIndexBL.mErrors);
			mRtnCode = mErrors.getError(0).errorNo;
			mRtnDesc = mErrors.getError(0).errorMessage;
			mResult.setElementAt(mRtnCode, 3);
			mResult.setElementAt(mRtnDesc, 4);
			return false;
		}

		// 返回数据处理
		if (tUploadIndexBL.getResult() != null) {
			mResult.clear();
			mResult = tUploadIndexBL.getResult();
			breturn = true;
		} else {
			this.mErrors.clearErrors();
			this.mErrors.copyAllErrors(tUploadIndexBL.mErrors);
			mRtnCode = mErrors.getError(0).errorNo;
			mRtnDesc = mErrors.getError(0).errorMessage;
			breturn = false;
		}
		mResult.setElementAt(mRtnCode, 3);
		mResult.setElementAt(mRtnDesc, 4);
		mInputData = null;
		return breturn;
	}

	// 返回数据的公共方法
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		UploadIndexUI ui = new UploadIndexUI();
		VData tVData = new VData();
		// ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
		// tES_DOC_MAINSchema.setDocID("577");
		// tES_DOC_MAINSchema.setDocFlag("0");
		// tES_DOC_MAINSchema.setBussType("TB");
		// tES_DOC_MAINSchema.setSubType("TB01");
		// tES_DOC_MAINSchema.setScanOperator("001");
		// tES_DOC_MAINSchema.setOperator("001");
		// tES_DOC_MAINSchema.setNumPages("2");
		// tES_DOC_MAINSchema.setManageCom("86");
		// tES_DOC_MAINSchema.setDocCode("200505057");
		// tES_DOC_MAINSchema.setMakeDate("2005-03-13");
		// ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		// tES_DOC_MAINSet.add(tES_DOC_MAINSchema);
		// tVData.add(tES_DOC_MAINSet);
		// ES_DOC_PAGESSchema tES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
		// ES_DOC_PAGESSet tES_DOC_PAGESSet = new ES_DOC_PAGESSet();
		// tES_DOC_PAGESSet.add(tES_DOC_PAGESSchema);
		// tVData.add(tES_DOC_PAGESSet);
		// tVData.add(tES_DOC_PAGESSet);
		// tVData.add(tES_DOC_PAGESSet);
		// tVData.add(tES_DOC_PAGESSet);
		StringBuffer strBuf = new StringBuffer(1024);
		String strXML = "<?xml version=\"1.0\"?> ";
		strXML = strXML
				+ "<DATA><DOC><DocID>325</DocID><DocCode>1001010000015688</DocCode><BussType>TB</BussType><SubType>1001</SubType><NumPages>2</NumPages><DocFlag>0</DocFlag><DocRemark /><ScanOperator>001</ScanOperator><ScanOperator>001</ScanOperator><ManageCom>86010101</ManageCom><MakeDate>2005-07-12</MakeDate><MakeTime>15:02:30</MakeTime><ModifyDate>2005-07-12</ModifyDate><ModifyTime>15:02:30</ModifyTime></DOC><PAGE><PageID>488</PageID><DocID>325</DocID><PageCode>1</PageCode><HostName>10.1.18.98</HostName><PageName>F488</PageName><PageSuffix /><PicPathFTP>10.1.18.98:7001/86010101/2005/07/12/</PicPathFTP><PageFlag>0</PageFlag><PicPath>xerox/EasyScan/86010101/2005/07/12/</PicPath><PageType>0</PageType><ManageCom>86010101</ManageCom><Operator>001</Operator><MakeDate>2005-07-12</MakeDate><MakeTime>15:02:33</MakeTime><ModifyDate>2005-07-12</ModifyDate><ModifyTime>15:02:33</ModifyTime><Page_URL>http://10.1.18.98:7001/xerox/EasyScan/</Page_URL></PAGE><PAGE><PageID>489</PageID><DocID>325</DocID><PageCode>2</PageCode><HostName>10.1.18.98</HostName><PageName>F489</PageName><PageSuffix /><PicPathFTP>10.1.18.98:7001/86010101/2005/07/12/</PicPathFTP><PageFlag>0</PageFlag><PicPath>xerox/EasyScan/86010101/2005/07/12/</PicPath><PageType>0</PageType><ManageCom>86010101</ManageCom><Operator>001</Operator><MakeDate>2005-07-12</MakeDate><MakeTime>15:02:37</MakeTime><ModifyDate>2005-07-12</ModifyDate><ModifyTime>15:02:37</ModifyTime><Page_URL>http://10.1.18.98:7001/xerox/EasyScan/</Page_URL></PAGE><RETURN><NUMBER>0</NUMBER><MESSAGE /></RETURN></DATA>";
		ParameterDataConvert convert = new ParameterDataConvert();
		convert.xMLToVData(strXML, tVData);
		ui.submitData(tVData, "");
	}
}
