

package com.sinosoft.productdef;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.PD_LBRiskInfoSchema;
import com.sinosoft.lis.vschema.PD_LBRiskInfoSet;

/**
 * @author sunyu 当修改或删除产品信息时，记录操作轨迹
 */
public class PDRiskOperationTraceBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public VData saveResult = new VData();
 
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private String mRiskCode = "";

	private static final String FIELDNAME = "StandByFlag";

	public PDRiskOperationTraceBL() {
	}

	/**
	 * 传输数据的公共方法
	 *  
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * @author sunyu 获取基础数据
	 * @see CommonBase.java, The method getInputData()
	 * @param cInputData
	 * @return
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");

		return true;
	}

	/**
	 * 查出可能修改了那些字段，将之备份到PD_LBRiskInfo表
	 * 
	 * @return
	 */
	private boolean dealData() {
		SSRS tSSRS=new ExeSQL().execSQL("select distinct b.Tablecode,a.standbyflag1 from Pd_Basetable a,PD_TableMap b where a.Tablecode=upper(b.tablecode) and trim(a.standbyflag2) in ('1','101')");
		if(tSSRS!=null){
			PD_LBRiskInfoSet tPD_LBRiskInfoSet=new PD_LBRiskInfoSet();
			SimpleDateFormat sf = new SimpleDateFormat(
			"yyyyMMddHH24mmssSSS");
			String serialNo = sf.format(new Date());
			for(int i=1;i<=tSSRS.MaxRow;i++){
				if(StrTool.cTrim(tSSRS.GetText(i, 2)).equals("")){
					continue;
				}
				String mTableName=tSSRS.GetText(i, 1);
				try {
					PD_LBRiskInfoSchema tPD_LBRiskInfoSchema=new PD_LBRiskInfoSchema();
					tPD_LBRiskInfoSchema.setV("SerialNo", serialNo);
					tPD_LBRiskInfoSchema.setTableCode(mTableName);
					tPD_LBRiskInfoSchema.setCreateOperator(mGlobalInput.Operator);
					tPD_LBRiskInfoSchema.setMakeDate(PubFun.getCurrentDate());
					tPD_LBRiskInfoSchema.setMakeTime(PubFun.getCurrentTime());
					tPD_LBRiskInfoSchema.setRiskCode(mRiskCode);
					tPD_LBRiskInfoSchema.setStandByFlag50(this.mGlobalInput.Operator);
					
//					查询险种的工作流信息
					String findMissionSql = "SELECT MissionID,SubMissionID,ActivityID,LastOperator FROM LWMission WHERE MissionProp2 = '"
							+ mRiskCode + "'";
					SSRS findMission = new ExeSQL().execSQL(findMissionSql);
					if (findMission != null && findMission.MaxRow > 0) {
						tPD_LBRiskInfoSchema.setMissionID(findMission.GetText(1, 1));
						tPD_LBRiskInfoSchema.setSubMissionID(findMission.GetText(1, 2));
						tPD_LBRiskInfoSchema.setActivityID(findMission.GetText(1, 3));
						tPD_LBRiskInfoSchema.setLastOperator(findMission.GetText(1, 4));
					} else {
						System.out.println("查询险种" + mRiskCode + "的工作流信息失败！");
					}
					
					SchemaSet set = getSchemaSet("com.sinosoft.lis.db." + mTableName
							+ "DB", tSSRS.GetText(i, 2).replaceAll("@RISKCODE@", this.mRiskCode));
					int size = set.size();
					if (size > 0) {
						for (int j = 0; j < size; j++) {
							// 遍历每一条责任信息
							Schema schema = (Schema) set.getObj(j+ 1);
							
							String findInfoSql="select displayorder,lower(fieldcode) from Pd_Basefield where isdisplay='1' and tablecode=upper('"+mTableName+"') order by displayorder";
							SSRS findInfo = new ExeSQL().execSQL(findInfoSql);
							if(findInfo!=null){
								for(int index=1;index<=findInfo.MaxRow;index++){
									Class[] coreClass = new Class[2];
									coreClass[0] = String.class;
									coreClass[1] = String.class;
									Method m = tPD_LBRiskInfoSchema.getClass().getMethod("setV", coreClass);

									Object[] coreObject = new Object[2];
									coreObject[0] = FIELDNAME + findInfo.GetText(index,1);
									coreObject[1] = schema.getV(findInfo.GetText(index,2));
									m.invoke(tPD_LBRiskInfoSchema, coreObject);
								}
									
							}else{
								continue;
							}
						}
					}
					tPD_LBRiskInfoSet.add(tPD_LBRiskInfoSchema);
				} catch (Exception ex) {
					System.out.println(ex.toString());
					return false;
				}
			}
			this.saveResult.add(tPD_LBRiskInfoSet);
		}
		
		return true;
	}
	
	private SchemaSet getSchemaSet(String className, String sql)
	throws Exception {
		Class fromDBClass = Class.forName(className);
		Object fromDB = fromDBClass.newInstance();
		
		Class[] c = new Class[1];
		c[0] = String.class;
		Method m = fromDB.getClass().getMethod("executeQuery", c);
		
		Object[] o = new Object[1];
		o[0] = sql;
		SchemaSet set = (SchemaSet) m.invoke(fromDB, o);
		return set;
	}

	public static void main(String[] args) {
	}

}
