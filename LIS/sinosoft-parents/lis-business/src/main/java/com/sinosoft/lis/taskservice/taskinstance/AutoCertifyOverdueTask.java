package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LZCardDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZCardTrackSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.lis.vschema.LZCardTrackSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

public class AutoCertifyOverdueTask extends TaskThread {
private static Logger logger = Logger.getLogger(AutoCertifyOverdueTask.class);
private GlobalInput mGlobalInput = new GlobalInput();

	public AutoCertifyOverdueTask() {
	}

	public CErrors mErrors = new CErrors();

	private MMap map;

	private VData mInputData;

	public boolean dealMain() {

		try {
			mGlobalInput = new GlobalInput();
	        mGlobalInput.Operator = "000"; // 系统自动操作
	        mGlobalInput.ManageCom = "86";
	        //日志监控,初始化           	
	        mGlobalInput.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
	        mGlobalInput.LogID[1]=(String)mParameters.get("SerialNo"); 
        	
			String currentDate = PubFun.getCurrentDate();
			String currentTime = PubFun.getCurrentTime();
			logger.debug("开始逾期单证自动处理：" + currentDate + "  " + currentTime);
		      //日志监控,过程监控        
        	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"开始逾期单证自动处理");
        	
			LZCardSet tLZCardSet = new LZCardSet();
			LZCardDB tLZCardDB = new LZCardDB();
			String sql="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				sql = "select * from lzcard a where a.stateflag in ('2', '3')"
					+ "and a.invalidate is not null and a.invalidate < '" + "?currentDate?"
					+ "' and rownum <= 2000";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				sql = "select * from lzcard a where a.stateflag in ('2', '3')"
						+ "and a.invalidate is not null and a.invalidate < '" + "?currentDate?"
						+ "' limit 0,2000";
			}
			logger.debug("逾期单证自动处理查询sql：" + sql);
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("currentDate", currentDate);
			tLZCardSet = tLZCardDB.executeQuery(sqlbv1);

			while (tLZCardSet != null && tLZCardSet.size() > 0) {
				for (int i = 1; i <= tLZCardSet.size(); i++) {
					tLZCardSet.get(i).setStateFlag("8");// 8、逾期,超过使用截止日期的单证，由系统自动置为此状态。
					tLZCardSet.get(i).setOperateFlag("6");// 6：逾期
					tLZCardSet.get(i).setMakeDate(currentDate);
					tLZCardSet.get(i).setMakeTime(currentTime);
					tLZCardSet.get(i).setModifyDate(currentDate);
					tLZCardSet.get(i).setModifyTime(currentTime);
					// 日志监控,状态监控                 		
         			PubFun.logState(mGlobalInput,tLZCardSet.get(i).getStartNo(), "印刷号为"+tLZCardSet.get(i).getStartNo()+"的单证逾期处理成功","1");  
				}
				map = new MMap();
				map.put(tLZCardSet, "UPDATE");

				LZCardTrackSet tLZCardTrackSet = new LZCardTrackSet();
				tLZCardTrackSet.add(new LZCardTrackSchema());
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLZCardTrackSet, tLZCardSet);
				map.put(tLZCardTrackSet, "INSERT");// 保存LZCardTrack

				if (!prepareOutputData()) {
					return false;
				}
				PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(mInputData, "")) {
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError.buildErr(this, "PubSubmit 提交数据时错误！");
					return false;
				}
//				日志监控,结果监控
				PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"本次共处理逾期单证"+tLZCardSet.size()+"笔");
				tLZCardSet = tLZCardDB.executeQuery(sqlbv1);
			}
		      //日志监控,过程监控        
        	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"逾期单证自动处理结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData = new VData();
			mInputData.add(map);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "AutoCertifyOverdueTask";
			tError.functionName = "AutoCertifyOverdueTask";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AutoCertifyOverdueTask tAutoCertifyOverdueTask = new AutoCertifyOverdueTask();

		tAutoCertifyOverdueTask.dealMain();

	}

}
