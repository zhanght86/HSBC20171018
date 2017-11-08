package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 投保单上传后对于关联表的的操作</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author tuqiang
 * @version 1.0
 */

import com.sinosoft.lis.db.LBPolDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.utility.CError;

public class DocTBYBTRelationService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBYBTRelationService.class);

	public DocTBYBTRelationService() {
	}

	protected boolean deal() {
		MMap map = new MMap();

		/** 得到具体信息* */
		if (mES_DOC_MAINSchema.getBussType().equals("TB")) {
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setPrtNo(mES_DOC_MAINSchema.getDocCode().trim());
			if (tLCContDB.getCount() > 0) {
				mES_DOC_MAINSchema.setInputState("1");
				logger.debug("邮保通事后扫描发现"
						+ mES_DOC_MAINSchema.getDocCode());
			} else {
				LBPolDB tLBPolDB = new LBPolDB();
				tLBPolDB.setPrtNo(mES_DOC_MAINSchema.getDocCode().trim());

				if (tLBPolDB.getCount() > 0) {
					mES_DOC_MAINSchema.setInputState("1");
					logger.debug("邮保通事后扫描发现"
							+ mES_DOC_MAINSchema.getDocCode());
				} else {
					CError.buildErr(this, "邮（银）保通保单应事后扫描");
					return false;
				}
			}
		} else {
			CError.buildErr(this, "非邮（银）保通保单");
			return false;
		}

		ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(mES_DOC_MAINSchema.getDocCode());
		mES_DOC_RELATIONSchema.setDocCode(mES_DOC_MAINSchema.getDocCode());
		mES_DOC_RELATIONSchema.setBussNoType("11");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema
				.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		if (mES_DOC_RELATIONSchema != null) {
			map.put(mES_DOC_RELATIONSchema, "INSERT");
		}
		if (map != null) {
			// 返回数据map
			mResult.add(map);
		} else {
			CError.buildErr(this, "数据生成出错!");
			return false;
		}
		return true;

	}

}
