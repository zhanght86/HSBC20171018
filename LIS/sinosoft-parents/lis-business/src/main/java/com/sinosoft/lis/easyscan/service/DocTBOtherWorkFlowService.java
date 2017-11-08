package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 新契约变更通知书上载工作流处理接口实现类</p>
 * 业务类型号码为11
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author zhangzm
 * @version 1.0
 */

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.easyscan.service.util.DocPageCheck;
import com.sinosoft.lis.easyscan.service.util.PolNumCheck;
import com.sinosoft.lis.easyscan.service.util.PolStateCheck;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

public class DocTBOtherWorkFlowService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBOtherWorkFlowService.class);

	public DocTBOtherWorkFlowService() {
	}

	public boolean deal() {
		if(!DocPageCheck.isOnlyOnePage(mES_DOC_MAINSchema.getSubType(), mES_DOC_PAGESSet.size())){
			CError.buildErr(this, "此类型只允许扫描一页");
			return false;
		}
//PolNumCheck中通过硬代码校验，此处去掉   2011-08-25
//		if(!PolNumCheck.isPolSingle(mES_DOC_MAINSchema.getDocCode())){
//			CError.buildErr(this, "此类型只允许个险扫描");
//			return false;
//		}

		// 如果已扫描却没录入则不需申请
		if (!PolStateCheck.isPolScaned(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "请先上传投保单影像件。");
			return false;
		}
		if(!PolStateCheck.isPolSameManage(mES_DOC_MAINSchema.getDocCode(), mES_DOC_MAINSchema.getManageCom())){
			CError.buildErr(this, "扫描机构与投保单扫描机构不一致，请核实 ");
			return false;
		}

		if(PolStateCheck.isPolSigned(mES_DOC_MAINSchema.getDocCode())){
			CError.buildErr(this, "保单已签单，禁止扫描");
			return false;
		}
		
		// 《要约撤销申请》在保单进入系统后至签发前，无需申请签批即可扫描上传
		if("UR201".equals(mES_DOC_MAINSchema.getSubType())){
			return true;
		}
		
		if (!PolStateCheck.isPolInputed(mES_DOC_MAINSchema.getDocCode()))
			return true;

		// 如果核保4则不需申请
		if (PolStateCheck.isPolAtUW4(mES_DOC_MAINSchema.getDocCode()))
			return true;

		TransferData tTransferData = new TransferData();
		GlobalInput tGlobalInput = new GlobalInput();
		VData tVData = new VData();

		// 通过前台得到最后操作数据
		tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
		tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
		tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

		// 对输入数据进行验证
		LWMissionDB mLWMissionDB = new LWMissionDB();
		LWMissionSet mLWMissionSet = new LWMissionSet();
		mLWMissionDB.setActivityID("0000003002");
		mLWMissionDB.setMissionProp1(mES_DOC_MAINSchema.getDocCode()); // 流水号
		mLWMissionDB.setMissionProp2(mES_DOC_MAINSchema.getSubType()); // 业务子类
		mLWMissionSet = mLWMissionDB.query();
		if (mLWMissionSet == null || mLWMissionSet.size() == 0  ) {
			CError.buildErr(this, "未申请或审批过补扫此单证!");
			return false;
		}

		LWMissionSchema tLWMissionSchema = mLWMissionSet.get(1);

		tTransferData
				.setNameAndValue("BussNo", mES_DOC_MAINSchema.getDocCode());
		tTransferData.setNameAndValue("BussNoType", mES_DOC_MAINSchema
				.getSubType());

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		try {
			ActivityOperator tActivityOpertor = new ActivityOperator();
			if (!tActivityOpertor.ExecuteMission(tLWMissionSchema
					.getMissionID(), tLWMissionSchema.getSubMissionID(),
					tLWMissionSchema.getActivityID(), tVData)) {
				mErrors.copyAllErrors(tActivityOpertor.mErrors);
				return false;
			}
			if (!tActivityOpertor.DeleteMission(
					tLWMissionSchema.getMissionID(), tLWMissionSchema
							.getSubMissionID(), tLWMissionSchema
							.getActivityID(), tVData)) {
				mErrors.copyAllErrors(tActivityOpertor.mErrors);
				return false;
			}
			this.mResult = tActivityOpertor.getResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError.buildErr(this, ex.getMessage());
			return false;
		}
		logger.debug("----------------------DocTBOtherWorkFlowService End----------------------");
		return true;
	}

}
