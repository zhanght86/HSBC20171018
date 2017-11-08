package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.utility.CError;

public class DocOFUploadRelationService extends BaseService {
private static Logger logger = Logger.getLogger(DocOFUploadRelationService.class);
	public boolean deal() {
		try {
			logger.debug("----------------------DocOFUploadRelationService Begin----------------------");
			ES_DOC_RELATIONSchema tES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();

			if((mES_DOC_MAINSchema.getSubType().equals("OF001")&& !mES_DOC_MAINSchema.getDocCode().startsWith("863201"))){
				CError.buildErr(this, "非暂收据");
				return false;
			}
			//zy 2009-10-09 银行划款协议书调整编码为532002开头
			if((mES_DOC_MAINSchema.getSubType().equals("OF002")&& !(mES_DOC_MAINSchema.getDocCode().startsWith("863101")
					|| mES_DOC_MAINSchema.getDocCode().startsWith("532002")))){
				CError.buildErr(this, "非银行转帐授权书");
				return false;
			}
			tES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
			tES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema
					.getBussType());
			tES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema
					.getSubType());
			tES_DOC_RELATIONSchema.setRelaFlag("0");
			tES_DOC_RELATIONSchema.setBussNoType("99");
			tES_DOC_RELATIONSchema.setBussNo(mES_DOC_MAINSchema
					.getDocCode());
			tES_DOC_RELATIONSchema.setDocCode(mES_DOC_MAINSchema
					.getDocCode());
			if (tES_DOC_RELATIONSchema != null) {
				// 返回数据map
				MMap map = new MMap();
				map.put(tES_DOC_RELATIONSchema, "INSERT");
				mResult.add(map);
			} else {
				CError.buildErr(this, "数据生成出错");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
