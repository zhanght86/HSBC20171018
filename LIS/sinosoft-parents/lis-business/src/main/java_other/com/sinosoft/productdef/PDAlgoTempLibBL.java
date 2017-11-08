

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

public class PDAlgoTempLibBL {
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

	public PDAlgoTempLibBL() {
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
		PD_AlgoTempLibSchema tPD_AlgoTempLibSchema = (PD_AlgoTempLibSchema) transferData
				.getValueByName("PD_AlgoTempLibSchema");

		GlobalInput tG = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		PD_AlgoTempLibDB tPD_AlgoTempLibDB = new PD_AlgoTempLibDB();
		tPD_AlgoTempLibDB.setSchema(tPD_AlgoTempLibSchema);

		tPD_AlgoTempLibSchema.setModifyDate(PubFun.getCurrentDate());
		tPD_AlgoTempLibSchema.setModifyTime(PubFun.getCurrentTime());

		if (cOperate.equals("save")) {
			if (tPD_AlgoTempLibDB.getInfo()) {
				this.mErrors.addOneError("数据中已经存在相同记录，请不要重复插入");
				return false;
			}

			tPD_AlgoTempLibSchema.setOperator(tG.Operator);
			tPD_AlgoTempLibSchema.setMakeDate(PubFun.getCurrentDate());
			tPD_AlgoTempLibSchema.setMakeTime(PubFun.getCurrentTime());

			tPD_AlgoTempLibDB.setSchema(tPD_AlgoTempLibSchema);

			if (!tPD_AlgoTempLibDB.insert()) {
				this.mErrors.addOneError(tPD_AlgoTempLibDB.mErrors
						.getFirstError());
				return false;
			}
		} else if (cOperate.equals("update")) {
			if (!tPD_AlgoTempLibDB.getInfo()) {
				this.mErrors.addOneError("数据中不存在该记录，请先插入再修改");
				return false;
			}

			PD_AlgoTempLibSchema oldPD_AlgoTempLibSchema = tPD_AlgoTempLibDB
					.getSchema();

			tPD_AlgoTempLibSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_AlgoTempLibSchema.setModifyTime(PubFun.getCurrentTime());
			tPD_AlgoTempLibSchema.setMakeDate(oldPD_AlgoTempLibSchema
					.getMakeDate());
			tPD_AlgoTempLibSchema.setMakeTime(oldPD_AlgoTempLibSchema
					.getMakeTime());
			tPD_AlgoTempLibSchema.setOperator(oldPD_AlgoTempLibSchema
					.getOperator());

			tPD_AlgoTempLibDB.setSchema(tPD_AlgoTempLibSchema);

			if (!tPD_AlgoTempLibDB.update()) {
				this.mErrors.addOneError(tPD_AlgoTempLibDB.mErrors
						.getFirstError());
				return false;
			}
		} else if (cOperate.equals("del")) {
			if (!tPD_AlgoTempLibDB.getInfo()) {
				this.mErrors.addOneError("数据中不存在该记录，无法删除");
				return false;
			}

			if (!tPD_AlgoTempLibDB.delete()) {
				this.mErrors.addOneError(tPD_AlgoTempLibDB.mErrors
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
