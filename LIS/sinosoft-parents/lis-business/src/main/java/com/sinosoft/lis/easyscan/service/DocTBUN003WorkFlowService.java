package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.WorkFlowUI;
/**
 * <p>Title: </p>
 * <p>Description: 生调单证上载工作流处理接口实现类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author tuqiang
 * @version 1.0
 */

public class DocTBUN003WorkFlowService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBUN003WorkFlowService.class);
  public boolean deal(){

    VData tVData = new VData();
    TransferData tTransferData = new TransferData();
    GlobalInput tGlobalInput = new GlobalInput();

    //全局变量
    tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
    tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
    tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

    //对输入数据进行验证,得到生调工作流节点

    LWMissionDB mLWMissionDB = new LWMissionDB();
    LWMissionSet mLWMissionSet = new LWMissionSet();
	/***********工作流升级修改为查询functionid**************/
	String MissionSQL="select * from lwmission where missionprop3='"+"?missionprop3?"+"' and activityid in(select activityid from lwactivity where functionid in('10010029'))";
	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	sqlbv1.sql(MissionSQL);
	sqlbv1.put("missionprop3", mES_DOC_MAINSchema.getDocCode());
	mLWMissionSet = mLWMissionDB.executeQuery(sqlbv1);

    if(mLWMissionSet==null || mLWMissionSet.size()!=1)
    {
    	 CError.buildErr(this, "无发放此扫描单！");
    	 return false;
    }

// 准备传输数据 VData
    String tOperate = new String();
    tTransferData.setNameAndValue("CertifyNo", mES_DOC_MAINSchema.getDocCode());
    tTransferData.setNameAndValue("CertifyCode", PrintManagerBL.CODE_PE);//guomy  modified  20050809
    tTransferData.setNameAndValue("ContNo",
                                  mLWMissionSet.get(1).getMissionProp2());
    tTransferData.setNameAndValue("MissionID",
                                  mLWMissionSet.get(1).getMissionID());
    tTransferData.setNameAndValue("SubMissionID",
                                  mLWMissionSet.get(1).getSubMissionID());
   // tTransferData.setNameAndValue("LZSysCertifySchema", tLZSysCertifySchema);
    tTransferData.setNameAndValue("PrtNo",mLWMissionSet.get(1).getMissionProp1());//add by sunxy 20050619
    //tTransferData.setNameAndValue("flag", "N"); //add by tuqiang for tranction!!
    tTransferData.setNameAndValue("PrtSeq", mLWMissionSet.get(1).getMissionProp3());
     tTransferData.setNameAndValue("OldPrtSeq",
                                       mLWMissionSet.get(1).getMissionProp14());
    tTransferData.setNameAndValue("TakeBackOperator",mES_DOC_MAINSchema.getScanOperator());//add by liukun
    tTransferData.setNameAndValue("TakeBackMakeDate",mES_DOC_MAINSchema.getMakeDate());//add by liukun
	tTransferData.setNameAndValue("ActivityID", mLWMissionSet.get(1).getActivityID());
	tTransferData.setNameAndValue("BusiType", "1001");


    tVData.add(tGlobalInput);
    tVData.add(tTransferData);

    try {
    	WorkFlowUI tWorkFlowUI = new WorkFlowUI();
      if (!tWorkFlowUI.submitData(tVData, "execute"))
      {
        // @@错误处理
    	  CError.buildErr(this, "扫描录入工作流节点生成失败！",tWorkFlowUI.mErrors);
    	  return false;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
// @@错误处理
      CError tError = new CError();
      tError.moduleName = "DocTBRReportWorkFlowService";
      tError.functionName = "Save";
      tError.errorNo = "-500";
      tError.errorMessage = ex.toString();
      this.mErrors.addOneError(tError);
      return false;
    }
    logger.debug("----------------------WorkFlow End----------------------");
   return true;
  }
}
