



/**
 * <p>Title: PDAlgoDefi</p>
 * <p>Description: 算法定义界面</p>
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

public class PDAlgoDefiBL {
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

	public String mTestResult;

	public PDAlgoDefiBL() {
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

		if (!Deal(cInputData, cOperate)) {
			return false;
		}
		if (!submit())
	    {
	        return false;
	    }
		return true;
	}

	private boolean check() {
		return true;
	}
	/**
	  * 提交数据到数据库
	  * @return boolean
	  */
	 private boolean submit()
	 {
	     VData data = new VData();
	     data.add(map);
	     PubSubmit tPubSubmit = new PubSubmit();
	     if (!tPubSubmit.submitData(data, ""))
	     {
	         mErrors.copyAllErrors(tPubSubmit.mErrors);
	         return false;
	     }
	     return true;
	 }

	private boolean Deal(VData cInputData, String cOperate) {
		if (cOperate.equals("test")) {

			PubCalculator tCalculator = new PubCalculator();

			TransferData transferData = new TransferData();

			transferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			String tAlgoCode = (String) transferData.getValueByName("AlgoCode");
			String FactorCodes[] = (String[]) transferData.getValueByName("FactorCodes");
			String FactorValues[] = (String[]) transferData.getValueByName("FactorValues");
			
			ExeSQL exec = new ExeSQL();
			String sql = "select a.factorcode,(select calsql from Pd_Lmcalmode where calcode=a.factorcode and rownum<2) from PD_LMCalFactor a,Pd_Lmcalmode b where b.calcode=a.calcode and b.calcode='"+tAlgoCode+"'";
			SSRS ssrs = exec.execSQL(sql);
			if(ssrs!=null && ssrs.getMaxRow()>0){
				 for(int i=1;i<=ssrs.getMaxRow();i++){
					 PubCalculator temp = new PubCalculator();
					 if (FactorValues != null) {
							for (int j = 0; j < FactorCodes.length; j++) {
								temp.addBasicFactor(FactorCodes[j], FactorValues[j]);
								System.out.println("=="+FactorCodes[j]+"   "+FactorValues[j]);
							}
					 }
					 temp.setCalSql(ssrs.GetText(1, 2));
					 System.out.println(ssrs.GetText(1, 2));
					 System.out.println(temp.calculate());
					 tCalculator.addBasicFactor(ssrs.GetText(1, 1), temp.calculate());
				 }
			}
			
			if (FactorValues != null) {
				for (int i = 0; i < FactorCodes.length; i++) {
					tCalculator.addBasicFactor(FactorCodes[i], FactorValues[i]);
				}
			}
	
			sql = "select calsql from pd_lmcalmode where calcode='"+tAlgoCode+"'";
			ssrs = exec.execSQL(sql);
			mTestResult = "没有测试结果";
			if(ssrs==null || ssrs.getMaxRow()==0){
				this.mErrors.addOneError("没有找到该算法");
				return false;
			}else{
				tCalculator.setCalSql(ssrs.GetText(1, 1));
				mTestResult = tCalculator.calculate();
				return true;
			}
		} else if (cOperate.equals("add")) {
			TransferData transferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);
			PD_LMCalFactorSchema tPD_LMCalFactorSchema = (PD_LMCalFactorSchema) transferData
					.getValueByName("PD_LMCalFactorSchema");

			GlobalInput tG = (GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0);

			PD_LMCalFactorDB tPD_LMCalFactorDB = new PD_LMCalFactorDB();
			tPD_LMCalFactorDB.setSchema(tPD_LMCalFactorSchema);

			tPD_LMCalFactorSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMCalFactorSchema.setModifyTime(PubFun.getCurrentTime());

			if (tPD_LMCalFactorDB.getInfo()) {
				this.mErrors.addOneError("数据中已经存在相同记录，请不要重复插入");
				return false;
			}

			tPD_LMCalFactorSchema.setOperator(tG.Operator);
			tPD_LMCalFactorSchema.setMakeDate(PubFun.getCurrentDate());
			tPD_LMCalFactorSchema.setMakeTime(PubFun.getCurrentTime());

			tPD_LMCalFactorDB.setSchema(tPD_LMCalFactorSchema);

			if (!tPD_LMCalFactorDB.insert()) {
				this.mErrors.addOneError(tPD_LMCalFactorDB.mErrors
						.getFirstError());
				return false;
			}
		} else if(cOperate.equals("updateFac")){
			//-------- add by jucy
			TransferData transferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
			PD_LMCalFactorSchema tPD_LMCalFactorSchema = (PD_LMCalFactorSchema) transferData.getValueByName("PD_LMCalFactorSchema");
			GlobalInput tG = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
			if(tPD_LMCalFactorSchema!=null){
				PD_LMCalFactorDB tPD_LMCalFactorDB = new PD_LMCalFactorDB();
				tPD_LMCalFactorDB.setSchema(tPD_LMCalFactorSchema);
				if (tPD_LMCalFactorDB.getInfo()) {
					this.mErrors.addOneError("关联失败！已存在”子/主“算法的对应关系。");
					return false;
				}
				tPD_LMCalFactorSchema.setMakeDate(PubFun.getCurrentDate());
				tPD_LMCalFactorSchema.setMakeTime(PubFun.getCurrentTime());
				tPD_LMCalFactorSchema.setModifyDate(PubFun.getCurrentDate());
				tPD_LMCalFactorSchema.setModifyTime(PubFun.getCurrentTime());
				tPD_LMCalFactorSchema.setOperator(tG.Operator);
				map.put(tPD_LMCalFactorSchema, "INSERT");
			}
			//-------- end
		}else{
			TransferData transferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);
			PD_LMCalModeSchema tPD_LMCalModeSchema = (PD_LMCalModeSchema) transferData
					.getValueByName("PD_LMCalModeSchema");
			PD_LMCalFactorSchema tPD_LMCalFactorSchema = (PD_LMCalFactorSchema) transferData
			.getValueByName("PD_LMCalFactorSchema");

			GlobalInput tG = (GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0);
			
			PD_LMCalModeDB tPD_LMCalModeDB = new PD_LMCalModeDB();
			tPD_LMCalModeDB.setSchema(tPD_LMCalModeSchema);

			tPD_LMCalModeSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMCalModeSchema.setModifyTime(PubFun.getCurrentTime());

			if (cOperate.equals("save")) {
				if (tPD_LMCalModeDB.getInfo()) {
					this.mErrors.addOneError("数据中已经存在相同记录，请不要重复插入");
					return false;
				}
				tPD_LMCalModeSchema.setOperator(tG.Operator);
				tPD_LMCalModeSchema.setMakeDate(PubFun.getCurrentDate());
				tPD_LMCalModeSchema.setMakeTime(PubFun.getCurrentTime());
				map.put(tPD_LMCalModeSchema, "INSERT");
				if(tPD_LMCalFactorSchema!=null){
					PD_LMCalFactorDB tPD_LMCalFactorDB = new PD_LMCalFactorDB();
					tPD_LMCalFactorDB.setSchema(tPD_LMCalFactorSchema);
					if (tPD_LMCalFactorDB.getInfo()) {
						this.mErrors.addOneError("数据中已经存在相同记录，请不要重复插入");
						return false;
					}
					tPD_LMCalFactorSchema.setModifyDate(PubFun.getCurrentDate());
					tPD_LMCalFactorSchema.setModifyTime(PubFun.getCurrentTime());
					tPD_LMCalFactorSchema.setMakeDate(PubFun.getCurrentDate());
					tPD_LMCalFactorSchema.setMakeTime(PubFun.getCurrentTime());
					tPD_LMCalFactorSchema.setOperator(tG.Operator);
					map.put(tPD_LMCalFactorSchema, "INSERT");
				}
			} else if (cOperate.equals("update")) {
				if (!tPD_LMCalModeDB.getInfo()) {
					this.mErrors.addOneError("数据中不存在该记录，请先插入再修改");
					return false;
				}

				PD_LMCalModeSchema oldPD_LMCalModeSchema = tPD_LMCalModeDB
						.getSchema();

				tPD_LMCalModeSchema.setModifyDate(PubFun.getCurrentDate());
				tPD_LMCalModeSchema.setModifyTime(PubFun.getCurrentTime());
				tPD_LMCalModeSchema.setMakeDate(oldPD_LMCalModeSchema
						.getMakeDate());
				tPD_LMCalModeSchema.setMakeTime(oldPD_LMCalModeSchema
						.getMakeTime());
				tPD_LMCalModeSchema.setOperator(oldPD_LMCalModeSchema
						.getOperator());


				map.put(tPD_LMCalModeSchema, "UPDATE");
				if(tPD_LMCalFactorSchema!=null){
					PD_LMCalFactorDB tPD_LMCalFactorDB = new PD_LMCalFactorDB();
					tPD_LMCalFactorDB.setSchema(tPD_LMCalFactorSchema);
					if (!tPD_LMCalFactorDB.getInfo()) {
						this.mErrors.addOneError("数据中不存在该记录，或不允许修改子/父算法的算法编码。");
						return false;
					}
					tPD_LMCalFactorSchema.setModifyDate(PubFun.getCurrentDate());
					tPD_LMCalFactorSchema.setModifyTime(PubFun.getCurrentTime());
					tPD_LMCalFactorSchema.setOperator(tG.Operator);
					tPD_LMCalFactorSchema.setMakeTime(tPD_LMCalFactorDB.getSchema().getMakeTime());
					tPD_LMCalFactorSchema.setMakeDate(tPD_LMCalFactorDB.getSchema().getMakeDate());
					map.put(tPD_LMCalFactorSchema, "UPDATE");
				}
			} else if (cOperate.equals("del")) {
				if (!tPD_LMCalModeDB.getInfo()) {
					this.mErrors.addOneError("数据中不存在该记录，无法删除");
					return false;
				}
				map.put(tPD_LMCalModeDB.getSchema(), "DELETE");
				map.put("delete from PD_LMCalFactor where factorcode='"+tPD_LMCalModeDB.getSchema().getCalCode()+"'","DELETE");
			}

		}

		return true;
	}

	public static void main(String[] args) {
	}
}

