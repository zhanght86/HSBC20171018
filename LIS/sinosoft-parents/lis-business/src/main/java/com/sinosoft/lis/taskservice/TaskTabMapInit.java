/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import java.util.Timer;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDTaskPlanSchema;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 初始化批处理引擎的Tab
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * @version 1.0
 */
public class TaskTabMapInit implements BusinessService{
private static Logger logger = Logger.getLogger(TaskTabMapInit.class);
	private static TaskServiceEngine mTaskServiceEngine;
	private static Timer mTaskServiceTimer;
	private static boolean mTimerRunning;
	private String mOperator;
	private LDTaskPlanSchema mLDTaskPlanSchema;
	private LDTaskParamSet mLDTaskParamSet;
	private VData mResult = new VData();

	// private MMap mMap = new MMap();
	public CErrors mErrors = new CErrors();

	public TaskTabMapInit() {
	
	}

	public boolean submitData(VData aInputData, String aOperate) {
		String tUserCode = (aInputData.get(0)==null?(new GlobalInput()):(GlobalInput)aInputData.get(0)).Operator;
		String outjson = "";
		if(tUserCode!=null&&!tUserCode.equals(""))
		{
			String tSQL = "select tasktabid,tasktabname,type,otherproperty,tabsrc,note,iframeid from LDTaskTabConfig a "
				        + " where 1=1 and exists(select 1 from LDUSERToTaskTab where tasktabid=a.tasktabid and userid='"+"?tUserCode?"+"')"
						+ " order by showorder";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("tUserCode", tUserCode);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			outjson = "[";
			for(int i = 1;i<=tSSRS.MaxRow;i++){
				String tTaskTabID = tSSRS.GetText(i,1);
				String tTaskTabName = tSSRS.GetText(i,2);
				String tType = tSSRS.GetText(i,3);
				String tOtherProperty = tSSRS.GetText(i,4);
				String tTabSrc = tSSRS.GetText(i,5);
				String tNote = tSSRS.GetText(i,6);
				String tIFrameID = tSSRS.GetText(i,7);
    
				outjson += "{ \"TaskTabID\":\""+tTaskTabID+"\",\"TaskTabName\":\""+tTaskTabName+"\",\"Type\":\""+tType+"\",\"OtherProperty\":\""+tOtherProperty+"\",\"TabSrc\":\""+tTabSrc+"\",\"Note\":\""+tNote+"\",\"IFrameID\":\""+tIFrameID+"\"}";
				if(i<tSSRS.MaxRow){
					outjson+=",";
				}
			}

			outjson += "]";
		}
      this.mResult.add(outjson);
		return true;
  }

	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}
}
