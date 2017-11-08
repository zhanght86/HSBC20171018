

/**
 * <p>Title: PDScheRateCalFactor</p>
 * <p>Description: 费率表要素库</p>
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


public class PDScheRateCalFactorBL  {
 /** 错误处理类，每个需要错误处理的类中都放置该类 */
 public  CErrors mErrors=new CErrors();
 private VData mResult = new VData();
 /** 往后面传输数据的容器 */
 private VData mInputData= new VData();
 /** 全局数据 */
 private GlobalInput mGlobalInput =new GlobalInput() ;
 /** 数据操作字符串 */
 private String mOperate;
 /** 业务处理相关变量 */
 private MMap map=new MMap();
 
 public PDScheRateCalFactorBL() {
 }

/**
* 传输数据的公共方法
 * @param: cInputData 输入的数据
   *         cOperate 数据操作
* @return:
*/
 public boolean submitData(VData cInputData,String cOperate)
 {
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
 
 private boolean check()
 {
  return true;
 }
 
 private boolean deal(VData cInputData, String cOperate) {
		TransferData transferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		PD_ScheRateCalFactor_LibSchema tPD_ScheRateCalFactor_LibSchema = (PD_ScheRateCalFactor_LibSchema) transferData
				.getValueByName("PD_ScheRateCalFactor_LibSchema");

		GlobalInput tG = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		PD_ScheRateCalFactor_LibDB tPD_ScheRateCalFactor_LibDB = new PD_ScheRateCalFactor_LibDB();
		tPD_ScheRateCalFactor_LibDB.setSchema(tPD_ScheRateCalFactor_LibSchema);

		tPD_ScheRateCalFactor_LibSchema.setModifyDate(PubFun.getCurrentDate());
		tPD_ScheRateCalFactor_LibSchema.setModifyTime(PubFun.getCurrentTime());

		if (cOperate.equals("save")) {
			if (tPD_ScheRateCalFactor_LibDB.getInfo()) {
				this.mErrors.addOneError("数据中已经存在相同记录，请不要重复插入");
				return false;
			}

			tPD_ScheRateCalFactor_LibSchema.setOperator(tG.Operator);
			tPD_ScheRateCalFactor_LibSchema.setMakeDate(PubFun.getCurrentDate());
			tPD_ScheRateCalFactor_LibSchema.setMakeTime(PubFun.getCurrentTime());

			tPD_ScheRateCalFactor_LibDB.setSchema(tPD_ScheRateCalFactor_LibSchema);

			if (!tPD_ScheRateCalFactor_LibDB.insert()) {
				this.mErrors.addOneError(tPD_ScheRateCalFactor_LibDB.mErrors
						.getFirstError());
				return false;
			}
		} else if (cOperate.equals("update")) {
			if (!tPD_ScheRateCalFactor_LibDB.getInfo()) {
				this.mErrors.addOneError("数据中不存在该记录，请先插入再修改");
				return false;
			}

			PD_ScheRateCalFactor_LibSchema oldPD_ScheRateCalFactor_LibSchema = tPD_ScheRateCalFactor_LibDB
					.getSchema();

			tPD_ScheRateCalFactor_LibSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_ScheRateCalFactor_LibSchema.setModifyTime(PubFun.getCurrentTime());
			tPD_ScheRateCalFactor_LibSchema.setMakeDate(oldPD_ScheRateCalFactor_LibSchema
					.getMakeDate());
			tPD_ScheRateCalFactor_LibSchema.setMakeTime(oldPD_ScheRateCalFactor_LibSchema
					.getMakeTime());
			tPD_ScheRateCalFactor_LibSchema.setOperator(oldPD_ScheRateCalFactor_LibSchema
					.getOperator());

			tPD_ScheRateCalFactor_LibDB.setSchema(tPD_ScheRateCalFactor_LibSchema);

			if (!tPD_ScheRateCalFactor_LibDB.update()) {
				this.mErrors.addOneError(tPD_ScheRateCalFactor_LibDB.mErrors
						.getFirstError());
				return false;
			}
		} else if (cOperate.equals("del")) {
			if (!tPD_ScheRateCalFactor_LibDB.getInfo()) {
				this.mErrors.addOneError("数据中不存在该记录，无法删除");
				return false;
			}

			if (!tPD_ScheRateCalFactor_LibDB.delete()) {
				this.mErrors.addOneError(tPD_ScheRateCalFactor_LibDB.mErrors
						.getFirstError());
				return false;
			}
		}

		return true;
	}


 public static void main(String[] args) {
 }
}
