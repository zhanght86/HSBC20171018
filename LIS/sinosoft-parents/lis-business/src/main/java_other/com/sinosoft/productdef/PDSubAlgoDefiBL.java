

/**
 * <p>Title: PDSubAlgoDefi</p>
 * <p>Description: 子算法定义界面</p>
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

public class PDSubAlgoDefiBL {
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

	public PDSubAlgoDefiBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!check()) {
			return false;
		}

		if (!deal(cInputData, cOperate)) {
			return false;
		}

		return true;
	}

	private boolean deal(VData cInputData, String cOperate) {

		TransferData transferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		PD_LMCalFactorSchema tPD_LMCalFactorSchema = (PD_LMCalFactorSchema) transferData
				.getValueByName("PD_LMCalFactorSchema");

		GlobalInput tG = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		PD_LMCalFactorDB tPD_LMCalFactorDB = new PD_LMCalFactorDB();
		tPD_LMCalFactorDB.setSchema(tPD_LMCalFactorSchema);

		if (cOperate.equals("save") || cOperate.equals("update")) {

			tPD_LMCalFactorSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMCalFactorSchema.setModifyTime(PubFun.getCurrentTime());

			if (!tPD_LMCalFactorDB.getInfo()) {
				this.mErrors.addOneError("数据中不存在该记录，请先插入再修改");
				return false;
			}

			PD_LMCalFactorSchema oldPD_LMCalFactorSchema = tPD_LMCalFactorDB
					.getSchema();

			tPD_LMCalFactorSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMCalFactorSchema.setModifyTime(PubFun.getCurrentTime());
			tPD_LMCalFactorSchema.setMakeDate(oldPD_LMCalFactorSchema
					.getMakeDate());
			tPD_LMCalFactorSchema.setMakeTime(oldPD_LMCalFactorSchema
					.getMakeTime());
			tPD_LMCalFactorSchema.setOperator(oldPD_LMCalFactorSchema
					.getOperator());

			tPD_LMCalFactorDB.setSchema(tPD_LMCalFactorSchema);

			if (!tPD_LMCalFactorDB.update()) {
				this.mErrors.addOneError(tPD_LMCalFactorDB.mErrors
						.getFirstError());
				return false;
			}
		} else if (cOperate.equals("del")) {

			if (!tPD_LMCalFactorDB.getInfo()) {
				this.mErrors.addOneError("数据中不存在该记录，无法删除");
				return false;
			}

			if (!tPD_LMCalFactorDB.delete()) {
				this.mErrors.addOneError(tPD_LMCalFactorDB.mErrors
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
