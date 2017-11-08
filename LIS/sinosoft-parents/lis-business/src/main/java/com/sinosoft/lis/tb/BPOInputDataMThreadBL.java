package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import org.jdom.Document;

import com.sinosoft.lis.db.BPOServerInfoDB;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.vschema.BPOServerInfoSet;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: BPODealInputDataBL
 * </p>
 * <p>
 * Description: 录入外包多线程导入
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.5
 */

public class BPOInputDataMThreadBL extends CovBase {
private static Logger logger = Logger.getLogger(BPOInputDataMThreadBL.class);
	/** 数据处理类 */
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private VData mInputDataNew;
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	public BPOInputDataMThreadBL() {
	}
	
	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (VData) tObject;
	}
	
	public void run() {
		try {
			//tongmeng 2009-12-22 modify
			//按照机构分组处理外包数据
			for(int i=0;i<mInputDataNew.size();i++)
			{
				try {
					TransferData mTransferData = new TransferData();
					mTransferData = mInputDataNew.getObjectByObjectName("TransferData", i)==null?null
							:(TransferData)mInputDataNew.getObjectByObjectName("TransferData", i);
					
					if(mTransferData!=null)
					{
						String tFilePath = (String)mTransferData.getValueByName("FilePath");
						String tFileName = (String)mTransferData.getValueByName("FileName");
						String tOperator = (String)mTransferData.getValueByName("Operator");
						BPODealInputDataBL tBPODealInputDataBL = new BPODealInputDataBL();
						String tBPOInputPath = "";
						tBPODealInputDataBL.setBPOInputPath(tFilePath);
						if (tFilePath == null || "".equals(tFilePath)) {
							//continue;
						}
						else
						{
							//防止文件处理出现并发
							if (!this.mPubLock.lock(tFileName, "LC0031")) {
								this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
							}
							else
							{
								Document tOneFileData = TBPubFun.produceXmlDoc(tFilePath+tFileName);
								if (tOneFileData != null) {
									if(!tBPODealInputDataBL.DealOneFile(tOneFileData, tFileName))
									{
										this.mErrors.copyAllErrors(tBPODealInputDataBL.mErrors);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.debug("BPOInputDataMThreadBL Err:"+e.toString());
					continue;
				}
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.mPubLock.unLock();
			this.close();
		}
	}

}
