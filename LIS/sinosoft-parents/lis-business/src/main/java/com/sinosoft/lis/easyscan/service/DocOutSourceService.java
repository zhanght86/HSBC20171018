package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.easyscan.outsourcing.DocTBImageMoveRelationService;
import com.sinosoft.lis.easyscan.outsourcing.EsDocMoveMS;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.tb.BPOChoose;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class DocOutSourceService extends BaseService {
private static Logger logger = Logger.getLogger(DocOutSourceService.class);

	public boolean deal() {
		logger.debug("#Start DocOutSourceService...");
		DocTBImageMoveRelationService s = new DocTBImageMoveRelationService();
		VData tVData = new VData();
		ES_DOC_MAINSet tES_DOC_MAINSET = new ES_DOC_MAINSet();
		tES_DOC_MAINSET.add(mES_DOC_MAINSchema);
		TransferData tTransferData = new TransferData();

		tVData.add(tES_DOC_MAINSET);
		tVData.add(mES_DOC_PAGESSet);
		tTransferData.setNameAndValue(EsDocMoveMS.CON_XML_CONTNO,
				mES_DOC_MAINSchema.getDocCode());
		tTransferData.setNameAndValue(EsDocMoveMS.CON_XML_MANAGECOM,
				mES_DOC_MAINSchema.getManageCom());
		String bpo = BPOChoose.chooseBPO(mES_DOC_MAINSchema);
		if (bpo.equals("")) {// 不需外包
			return true;
		}
		logger.debug("#DocOutSourceService:" + bpo);
		tTransferData.setNameAndValue(EsDocMoveMS.CON_XML_BPO, bpo);
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("EasyScanShare");
		if (!tLDSysVarDB.getInfo()) {
			CError.buildErr(this, "缺少EasyScanShare目录");
			return false;
		}

		tTransferData.setNameAndValue(EsDocMoveMS.TEMP_PATH, tLDSysVarDB
				.getSysVarValue());
		tVData.add(tTransferData);
		tVData.add(EsDocMoveMS.class.getName());
		tVData.add("1");
		// BPOServerInfoDB tBPOServerInfoDB=new BPOServerInfoDB();
		// tBPOServerInfoDB.setBPOID(bpo);
		// if(!tBPOServerInfoDB.getInfo()){
		// CError.buildErr(this, "缺少BPO描述信息");
		// return false;
		// }
		// tVData.add(tBPOServerInfoDB.getSuffix());

		if (!s.submitData(tVData, null)) {
			mErrors.copyAllErrors(s.mErrors);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
		tES_DOC_MAINSchema.setDocCode("86110101996511");
		tES_DOC_MAINSchema.setDocID("250836");
		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		tES_DOC_MAINSet.add(tES_DOC_MAINSchema);
		tVData.add(tES_DOC_MAINSet);
		DocOutSourceService s = new DocOutSourceService();
		s.submitData(tVData, null);
	}
}
