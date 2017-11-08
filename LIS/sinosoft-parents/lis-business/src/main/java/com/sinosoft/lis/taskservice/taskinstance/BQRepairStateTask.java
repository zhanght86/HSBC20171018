package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.operfee.IndiDueFeePartQuery;
import com.sinosoft.lis.operfee.IndiDueFeePartUI;
import com.sinosoft.lis.operfee.RnSpecialAvailiable;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: 核销自动运行程序</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author xiongzh 08-1-30
 * @version 1.0
 */

public class BQRepairStateTask extends TaskThread 
{
private static Logger logger = Logger.getLogger(BQRepairStateTask.class);



        public BQRepairStateTask() {
        }

        public CErrors mErrors = new CErrors();

        
        private void repairAvailableState(){

    		String tSQL = " select distinct a.contno ,(select paytodate from lcpol where polno = a.polno)"
    					+ " from lccontstate a, lccont b"
    					+ " where a.statetype = 'Available'"
    					+ " and a.contno = b.contno "
    					+ " and not exists (select 1"
    					+ "  from lccontstate"
    					+ "  where contno = a.contno"
    					+ "  and statetype = 'PayPrem'"
    					+ " and state = '1'"
    					+ "  and enddate is null)"
    					+ " and a.state = '1'"
    					+ " and a.statereason = '01'"
    					+ " and a.enddate is null"
    					+ " and b.autopayflag = '1'"
    					+ " and a.polno = (select distinct mainpolno from lcpol where contno = b.contno)"
    					+ " and not exists"
    					+ " (select 1"
    					+ "  from lccontstate"
    					+ "  where statetype = 'Terminate'"
    					+ "  and contno = b.contno"
    					+ " and polno ="
    					+ " (select distinct mainpolno from lcpol where contno = b.contno)"
    					+ " and state = '1'"
    					+ " and enddate is null)"
    					+ " and exists"
    					+ " (select 1"
    					+ "  from lcpol"
    					+ " where contno = b.contno"
    					+ "   and polno ="
    					+ " (select distinct mainpolno from lcpol where contno = b.contno)"
    					+ "  and riskcode in"
    					+ " (select riskcode from LMRiskApp where AutoPayFlag = '1'))";
    		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    		sqlbv.sql(tSQL);
    		ExeSQL tExeSQL = new ExeSQL();
    		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
    		for(int i = 1; i<=tSSRS.MaxRow;i++){
    			logger.debug(tSSRS.GetText(i, 1)+"  "+tSSRS.GetText(i, 2));
    			String tContNo = tSSRS.GetText(i, 1);
    			RnSpecialAvailiable tLRNSpecialAvailableBL = new RnSpecialAvailiable();
    			TransferData tTransferData = new TransferData();
    			tTransferData.setNameAndValue("ContNo", tSSRS.GetText(i, 1));
    			tTransferData.setNameAndValue("PayToDate", tSSRS.GetText(i, 2));
    			tTransferData.setNameAndValue("InvalidReason", "非垫交保单应交未交失效");
    			GlobalInput tGlobalInput = new GlobalInput();
    			tGlobalInput.Operator = "SYS";
    			tGlobalInput.ManageCom = "86";
    			//VData
    			VData tVData = new VData();
    			tVData.addElement(tGlobalInput);
    			tVData.addElement(tTransferData);
    			
    			if (!tLRNSpecialAvailableBL.submitData(tVData, "OPERATE")) {
    				continue;
    			}
    			
    			String tOperator = "GQ";
    			String tEndDate = PubFun.calDate(PubFun.getCurrentDate(), -61, "D", null);
    			tTransferData = new TransferData();
    			tTransferData.setNameAndValue("Contno", tContNo);
    			tTransferData.setNameAndValue("StartDate", "");
    			tTransferData.setNameAndValue("EndDate", tEndDate);
    			
    			tVData = new VData();
    			tVData.add(tGlobalInput);
    			tVData.add(tTransferData);
    			IndiDueFeePartQuery tIndiDueFeePartQuery = new IndiDueFeePartQuery();
    			if (tIndiDueFeePartQuery.submitData(tVData, tOperator)) {
    				IndiDueFeePartUI tIndiDueFeePartUI = new IndiDueFeePartUI();
    				if(!tIndiDueFeePartUI.submitData(tVData, tOperator)){
    					continue;
    				}
    			}
    		}
    		
        }
        
        public void repairTerminateState(){

    		
    		String tSql = "select distinct a.contno "
    					+ " from lccontstate a, lccont b "
    					+ "  where a.statetype = 'Available' "
    					+ "   and a.contno = b.contno "
    					+ "   and not exists "
    					+ "  (select 1 "
    					+ "           from lccontstate "
    					+ "         where contno = a.contno "
    					+ "           and statetype = 'PayPrem' "
    					+ "          and state = '1' "
    					+ "           and enddate is null) "
    					+ "   and a.state = '1' "
    					+ "   and a.statereason = '01' "
    					+ "   and a.polno = "
    					+ "       (select distinct mainpolno from lcpol where contno = b.contno) "
    					+ "   and a.enddate is null "
    					+ "   and b.autopayflag = '1' "
    					+ "  and exists "
    					+ " (select 1 "
    					+ "           from lccontstate "
    					+ "         where statetype = 'Terminate' "
    					+ "           and contno = b.contno "
    					+ "           and polno = a.polno "
    					+ "           and state = '1' "
    					+ " and statereason = '07' "
    					+ " and enddate is null) "
    					+ "  and exists "
    					+ "  (select 1 "
    					+ "           from lcpol "
    					+ "         where contno = b.contno "
    					+ "           and polno = a.polno "
    					+ "           and riskcode in"
    					+ "               (select riskcode from LMRiskApp where AutoPayFlag = '1')) "
    					+ "   and not exists (select 1 from lpedoritem where contno = a.contno and edorstate = '0' and edortype in ('CT','XT'))";
    		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    		sqlbv.sql(tSql);
    		ExeSQL tExeSQL = new ExeSQL();
    		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
    		logger.debug(tSSRS.getMaxRow());
    		MMap tMap = new MMap();

    		for(int i = 1;i<=tSSRS.getMaxRow();i++){
    			String tContNo = tSSRS.GetText(i, 1);
    			String tSQL = "Select distinct mainpolno from lcpol where contno = '"+"?tContNo?"+"'";
    			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        		sqlbv1.sql(tSQL);
        		sqlbv1.put("tContNo", tContNo);
    			String tMainPolNo = tExeSQL.getOneValue(sqlbv1);//主险险种
    			tSQL = "select * from lccontstate where contno = '"+"?tContNo?"+"' and statetype in ('Available', 'Terminate') " +
    					"and state = '1' and enddate is null and polno in (select polno from lcpol where paytodate = " +
    					"(select paytodate from lcpol where polno = '"+"?tMainPolNo?"+"') and contno = '"+"?tContNo?"+"')";
    			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        		sqlbv2.sql(tSQL);
        		sqlbv2.put("tContNo", tContNo);
        		sqlbv2.put("tMainPolNo", tMainPolNo);
        		sqlbv2.put("tContNo", tContNo);
    			LCContStateDB tLCContStateDB = new LCContStateDB();
    			LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(sqlbv2);
    			List it = new ArrayList();
    			for(int j=1;j<=tLCContStateSet.size();j++){
    				LCContStateSchema tLCContStateSchema = tLCContStateSet.get(j);
    				String tPolNo = tLCContStateSchema.getPolNo();
    				String tInsuredNo = tLCContStateSchema.getInsuredNo();
    				String tStateType = tLCContStateSchema.getStateType();
    				String tStartDate = tLCContStateSchema.getStartDate();
    				if(!tPolNo.equals("000000")){
    					//删除当前终止和失效状态
    					String tDelSQL = "Delete from lccontstate where contno = '"+"?tContNo?"+"' and polno = '"+"?tPolNo?"+"'" +
    							" and insuredno = '"+"?tInsuredNo?"+"'" +
    							" and StateType = '"+"?tStateType?"+"' and StartDate = '"+"?tStartDate?"+"'";
    					SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
    	        		sqlbv3.sql(tDelSQL);
    	        		sqlbv3.put("tContNo", tContNo);
    	        		sqlbv3.put("tPolNo", tPolNo);
    	        		sqlbv3.put("tInsuredNo", tInsuredNo);
    	        		sqlbv3.put("tStateType", tStateType);
    	        		sqlbv3.put("tStartDate", tStartDate);
    					tMap.put(sqlbv3, "DELETE");
    					//更新当前险种为有效
    					if(!it.contains(tPolNo)){
    						String tUpPolSql = "Update LCPol set appflag = '1' where polno = '"+"?tPolNo?"+"'";
    						SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        	        		sqlbv4.sql(tUpPolSql);
        	        		sqlbv4.put("tPolNo", tPolNo);
    						tMap.put(sqlbv4, "UPDATE");
    						it.add(tPolNo);
    					}
    					//还原上一条状态,将上一条状态的EndDate 置空
    					String tLastEndDate=PubFun.calDate(tStartDate, -1, "D", null);
    					String tUpStateSql = "Update lccontstate set enddate =null where contno = '"+"?tContNo?"+"'" +
    							" and polno = '"+"?tPolNo?"+"' and insuredno = '"+"?tInsuredNo?"+"' " +
    									"and StateType = '"+"?tStateType?"+"' and EndDate = '"+"?tLastEndDate?"+"'";
    					SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
    	        		sqlbv5.sql(tUpStateSql);
    	        		sqlbv5.put("tContNo", tContNo);
    	        		sqlbv5.put("tPolNo", tPolNo);
    	        		sqlbv5.put("tInsuredNo", tInsuredNo);
    	        		sqlbv5.put("tStateType", tStateType);
    	        		sqlbv5.put("tLastEndDate", tLastEndDate);
    					tMap.put(sqlbv5, "UPDATE");
    				}
    			}
    			String tDelPrtSql ="delete from loprtmanager where otherno='" + "?tContNo?" + "' and code in ('42','BQ29')";
    			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
        		sqlbv6.sql(tDelPrtSql);
        		sqlbv6.put("tContNo", tContNo);
    			tMap.put(sqlbv6, "DELETE");
    			String tUpDateContSql = "Update LCCont set appflag = '1' where ContNo = '"+"?tContNo?"+"'";
    			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
        		sqlbv7.sql(tUpDateContSql);
        		sqlbv7.put("tContNo", tContNo);
    			tMap.put(sqlbv7, "UPDATE");
    		}
    		
    		VData tVData = new VData();
    		tVData.add(tMap);
    		PubSubmit tPubSubmit = new PubSubmit();
    		tPubSubmit.submitData(tVData, "");
    		
    		for(int i = 1;i<=tSSRS.getMaxRow();i++){
    			String tContNo = tSSRS.GetText(i, 1);
    			GlobalInput tGlobalInput = new GlobalInput();
    			tGlobalInput.Operator = "SYS";
    			tGlobalInput.ManageCom = "86";
    			String tOperator = "GQ";
    			String tEndDate = PubFun.calDate(PubFun.getCurrentDate(), -61, "D", null);
    			TransferData tTransferData = new TransferData();
    			tTransferData.setNameAndValue("Contno", tContNo);
    			tTransferData.setNameAndValue("StartDate", "");
    			tTransferData.setNameAndValue("EndDate", tEndDate);
    			
    			VData nVData = new VData();
    			nVData.add(tGlobalInput);
    			nVData.add(tTransferData);
    			IndiDueFeePartQuery tIndiDueFeePartQuery = new IndiDueFeePartQuery();
    			if (tIndiDueFeePartQuery.submitData(nVData, tOperator)) {
    				IndiDueFeePartUI tIndiDueFeePartUI = new IndiDueFeePartUI();
    				if(!tIndiDueFeePartUI.submitData(nVData, tOperator)){
    					continue;
    				}
    			}
    		}

    	
        }


        public boolean dealMain()
        {
        	//先处理失效未终止的
        	repairAvailableState();
        	//再处理失效终止的
        	repairTerminateState();
        	return true;
        }
        
        public static void main(String[] args)
        {
//          CalCommisionTask tCalCommisionTask = new CalCommisionTask();
//          tCalCommisionTask.run();
        	BQRepairStateTask tBQRepairStateTask = new BQRepairStateTask();
        	tBQRepairStateTask.dealMain();
        }
        

}
