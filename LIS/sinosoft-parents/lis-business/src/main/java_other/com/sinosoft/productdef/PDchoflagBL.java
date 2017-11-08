

/**
 * <p>Title: PDAlgoTempLib</p>
 * <p>Description: 算法模板库</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class PDchoflagBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private MMap map = new MMap();

	public PDchoflagBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!check()) {
				return false;
			}

			// 进行业务处理
			if (!deal(cInputData, cOperate)) {
				return false;
			}
		} catch (Exception ex) {
			this.mErrors.addOneError(ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean deal(VData cInputData, String cOperate) {
		TransferData transferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		PD_LMRiskDutySchema tPD_LMRiskDutySchema = (PD_LMRiskDutySchema) transferData
				.getValueByName("PD_LMRiskDutySchema");

		GlobalInput tG = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		PD_LMRiskDutyDB tPD_LMRiskDutyDB = new PD_LMRiskDutyDB();
		tPD_LMRiskDutyDB.setSchema(tPD_LMRiskDutySchema);

		tPD_LMRiskDutySchema.setModifyDate(PubFun.getCurrentDate());
		tPD_LMRiskDutySchema.setModifyTime(PubFun.getCurrentTime());

		if (cOperate.equals("update")) {
			if (!tPD_LMRiskDutyDB.getInfo()) {
				this.mErrors.addOneError("数据中不存在该记录，请先插入再修改");
				return false;
			}

			PD_LMRiskDutySchema oldPD_LMRiskDutySchema = tPD_LMRiskDutyDB
					.getSchema();

			tPD_LMRiskDutySchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMRiskDutySchema.setModifyTime(PubFun.getCurrentTime());
			tPD_LMRiskDutySchema.setMakeDate(oldPD_LMRiskDutySchema
					.getMakeDate());
			tPD_LMRiskDutySchema.setMakeTime(oldPD_LMRiskDutySchema
					.getMakeTime());
			tPD_LMRiskDutySchema.setOperator(oldPD_LMRiskDutySchema
					.getOperator());
			tPD_LMRiskDutySchema
					.setRiskVer(oldPD_LMRiskDutySchema.getRiskVer());
			tPD_LMRiskDutySchema.setSpecFlag(oldPD_LMRiskDutySchema
					.getSpecFlag());
			
			tPD_LMRiskDutyDB.setSchema(tPD_LMRiskDutySchema);

			if (!tPD_LMRiskDutyDB.update()) {
				this.mErrors.addOneError(tPD_LMRiskDutyDB.mErrors
						.getFirstError());
				return false;
			}
		}

		return true;
	}

	private boolean check() {
		return true;
	}

	public static void main(String[] args) {
	}
}
