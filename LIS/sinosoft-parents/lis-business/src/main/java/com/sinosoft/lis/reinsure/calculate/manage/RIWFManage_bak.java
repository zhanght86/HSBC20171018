

package com.sinosoft.lis.reinsure.calculate.manage;

import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.reinsure.calculate.distill.RIDistillDeal;
import com.sinosoft.lis.reinsure.calculate.riskcal.RIRiskCal;
import com.sinosoft.lis.reinsure.calculate.assign.RIPreceptAssign;
import com.sinosoft.lis.reinsure.calculate.process.RICalPro;
import com.sinosoft.lis.reinsure.tools.RIPubFun;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.RIWFLogSet;
import com.sinosoft.lis.db.RIWFLogDB;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSerialNo;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft</p>
 * @caoshuguo
 * @version 1.0
 */
public class RIWFManage_bak implements RICalMan {
    private String mAutoFlag;
    private RIInitData mRIInitData;
    private TransferData mTransferData;
    private RIInitSplitSeg mRIInitSplitSeg;
    private RIInitSerialNo mRIInitSerialNo;
    public CErrors mErrors = new CErrors();
    private RIWFLogSet mRIWFLogSet ;
    private String[] procOrder = {"01","03","04","08"};
    private VData mInputData = new VData();
    private String mPath = "";
    private GlobalInput mGlobalInput = new GlobalInput();
    public RIWFManage_bak() {
    }

    public boolean submitData(VData cInputData, String cOperate) 
    {
    	
        if (!getInputData(cInputData, cOperate))        	
            return false;

        if (!dealData())
        {       	
			buildError("RIWFManage","再保计算出错！！");
			return false;           
        } 
        return true;
    }
    
    private boolean getInputData(VData cInputData, String cOperate){
    	
        if(cInputData != null)
        {
        	
            mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
            mRIWFLogSet = (RIWFLogSet)cInputData.getObjectByObjectName("RIWFLogSet",0) ;
            mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
            mPath = (String)mTransferData.getValueByName("PATH");
            mAutoFlag = (String)mTransferData.getValueByName("AUTOFLAG");
            if(mGlobalInput==null ||mRIWFLogSet ==null || mTransferData ==null || mPath==null||mAutoFlag==null)
            {
            	buildError("RIWFManage", " 参数获取出错！！ ");
            	return false;
            }
        }
        else
        {        	
            buildError("RIWFManage", " 参数获取出错！！ ");
            return false;
        }
        return true;
    }
    /**
     * 初始化累计风险下的算法
     * @return
     */
    private boolean init(RIWFLogSchema tRIWFLogSchema){
    	
        try {
        	
            String tAccumulateDefNo = tRIWFLogSchema.getTaskCode();           
            if(tAccumulateDefNo !=null && !tAccumulateDefNo.equals("")){
            	
            	mRIInitData = RIInitData.getObject(tAccumulateDefNo);
            	
            }else{            	
            	buildError("initInfo","流程管理程序运行初始化程序时出错: 累计风险编号为空");
                return false ; 
            }
         
            if(!tRIWFLogSchema.getNodeState().equals("01")&&!tRIWFLogSchema.getNodeState().equals("00")){
            	
                mRIInitSplitSeg = RIInitSplitSeg.getObject(tRIWFLogSchema);
            }
            mRIInitSerialNo = RIInitSerialNo.getObject();
            
        } catch (Exception ex) {
            buildError("initInfo","流程管理程序运行初始化程序时出错。"+ex.getMessage());
            return false ;
        }
        return true;
    }

    private boolean dealData() {
    	
    	//每个累计风险循环处理
    	System.out.println("============再保计算流程开始=============");
        for (int i = 1; i <= mRIWFLogSet.size(); i++) 
        {       	
            try 
            {            	
            	RIWFLogSchema mRIWFLogSchema = mRIWFLogSet.get(i);
                if(!calculateService( mRIWFLogSchema))
                {
                	System.out.println("再保计算出错：BatchNo："+mRIWFLogSchema.getBatchNo()+"、taskcode:"+mRIWFLogSchema.getTaskCode());
                	break ;
                }
            } 
            catch (Exception ex) 
            {
                buildError("dealData", "流程管理程序进行数据处理时出错。" + ex.getMessage());
                break;
            }
            finally 
            { //销毁静态对象
                if (!destroy(mRIWFLogSet.get(i))) 
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * 再保计算流程控制
     * @param tRIWFLogSchema
     * @return
     */
    private boolean calculateService(RIWFLogSchema tRIWFLogSchema)
    {
    	
    	if (!init(tRIWFLogSchema)) 
        	return false;
   	
        if (!verify(tRIWFLogSchema))
            return false;
        
        //如果NodeState为"03",则从RIWFLog表中得到当前实际的执行状态
    	String nodeState = tRIWFLogSchema.getNodeState();
        if ("03".equals(nodeState)) 
        {
        	//接口表存在数据
            if(!hasRIPolRecordDate(tRIWFLogSchema))            	
            	return false;

            //如果不是数据校验
            for (int j = 0; j < procOrder.length; j++) 
            {
                if (nodeState.equals( procOrder[j]) && 
                		!nodeState.equals(procOrder[procOrder.length - 1])) 
                {
                    	tRIWFLogSchema.setNodeState(procOrder[j + 1]);
                        break;                       
                }
            }
        }       
        System.out.println("当前任务状态 : " + tRIWFLogSchema.getNodeState());

        // 全部执行
        if (!autoExeWFlow(tRIWFLogSchema)) 
        {
            recordErrLog(tRIWFLogSchema);
            return false;
        }
        
    	return true;
    }
    
    private boolean autoExeWFlow(RIWFLogSchema tRIWFLogSchema) {
    	
        int nodeState = Integer.parseInt(tRIWFLogSchema.getNodeState());
        mInputData.add(tRIWFLogSchema);
        
        switch (nodeState) {
        case 1: //提数
            RIDistillDeal tRIDistillDeal = new RIDistillDeal();
            if (!tRIDistillDeal.submitData(mInputData, "")) {
                buildError("autoExeWFlow", tRIDistillDeal.mErrors.getFirstError());
                return false;
            }
            tRIWFLogSchema.setNodeState("01");
            if (!recordLog(tRIWFLogSchema)) {
                return false;
            }
        case 3: //方案分配
            RIPreceptAssign tRIPreceptAssign = new RIPreceptAssign();
            if (!tRIPreceptAssign.submitData(mInputData, "")) {
                buildError("autoExeWFlow", tRIPreceptAssign.mErrors.getFirstError());
                return false;
            }
            tRIWFLogSchema.setNodeState("03");
            if (!recordLog(tRIWFLogSchema)) {
                return false;
            }
        case 4: //风险计算，分保记录生成
            RIRiskCal tRIRiskCal = new RIRiskCal();
            if (!tRIRiskCal.submitData(mInputData, "")) {
                buildError("autoExeWFlow", tRIRiskCal.mErrors.getFirstError());
                return false;
            }
            tRIWFLogSchema.setNodeState("03");
            if (!recordLog(tRIWFLogSchema)) {
                return false;
            }
        case 8: //再保计算
            RICalPro tRICalPro = new RICalPro();
            if (!tRICalPro.submitData(mInputData, "")) 
            {
                buildError("autoExeWFlow", tRICalPro.mErrors.getFirstError());
                return false;
            }
            //更新再保编号
            RISerialNo tRISerialNo=new RISerialNo();
            if(!tRISerialNo.submitData(mInputData, ""))
            {
            	buildError("autoExeWFlow", tRISerialNo.mErrors.getFirstError());
                return false;
            }
            tRIWFLogSchema.setNodeState("99");
            if (!recordLog(tRIWFLogSchema)) 
            {
                return false;
            }
        case 99:
            return true;
        default:
            buildError("autoExeWFlow", "未定义的工作流节点");
            return false;
        }
    }
    
    private boolean hasRIPolRecordDate(RIWFLogSchema tRIWFLogSchema)
    { 	
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append(" select 'X' from dual where exists(select 'X' from ripolrecord a where a.accumulatedefno='");
        strBuffer.append(tRIWFLogSchema.getTaskCode());
        strBuffer.append("' and a.batchno='");
        strBuffer.append(tRIWFLogSchema.getBatchNo());
        strBuffer.append("')");
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(strBuffer.toString());
        if(tExeSQL.mErrors.needDealError())
        {        	
            buildError("initRISplitSeg","查询再保接口表是否存在数据失败！！");           
            return false; //报错
        }
        if("X".equals(tSSRS.GetText(1,1)))
        {
            return true; //有数据
        }
        else
        {
        	tRIWFLogSchema.setNodeState("99");
        	if (!recordLog(tRIWFLogSchema)) 
                return false;            
            return false;  //无数据
        }
    }

    private boolean verify(RIWFLogSchema tRIWFLogSchema)
    {
        if(tRIWFLogSchema.getBatchNo()==null||tRIWFLogSchema.getBatchNo().equals(""))
        {
            buildError("initInfo"," 流程管理程序出错，批次号为空 ");
            return false;
        }
        if(tRIWFLogSchema.getNodeState()==null||tRIWFLogSchema.getNodeState().equals("")
        		||tRIWFLogSchema.getNodeState().equals("99"))
        {
            buildError("initInfo"," 流程管理程序出错，工作流节点为空或任务已结束 ");
            return false;
        }
        return true;
    }


    private boolean recordLog(RIWFLogSchema tRIWFLogSchema) 
    {

        RIPubFun tRIPubFun = new RIPubFun();
        if (!tRIPubFun.recordWFLog(tRIWFLogSchema,mPath))
        {
            System.out.println(" path: "+mPath);
            buildError("recordLog","流程管理程序记录日志时出错： " + tRIPubFun.mErrors.getFirstError());
            return false;
        }
        return true;
    }

    private boolean destroy(RIWFLogSchema mRIWFLogSchema){
        try {
            System.out.println(" ==================销毁累计风险为："+mRIWFLogSchema.getTaskCode()+"的初始化类 ================ ");
            if(null!=mRIInitData){
                mRIInitData.destory();
                mRIInitData=null;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            try {
                if(mRIInitSerialNo!=null){
                    mRIInitSerialNo.destory();
                    mRIInitSerialNo = null;
                }
            } catch (Exception ex) {
                return false;
            } finally {
                try {
                    if(null!=mRIInitSplitSeg){
                        mRIInitSplitSeg.destory();
                        mRIInitSplitSeg = null;
                    }
                } catch (Exception ex) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    private boolean recordErrLog(RIWFLogSchema tRIWFLogSchema)
    {
        RIRecordErrLog tRIRecordErrLog = new RIRecordErrLog();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("ErrorInfo",mErrors.getFirstError());
        VData tVData = new VData();
        tVData.add(tRIWFLogSchema);
        tVData.add(mGlobalInput);
        tVData.add(tTransferData);
        if(!tRIRecordErrLog.submitData(tVData,"")){
            buildError("recordLog","流程管理程序记录错误日志时出错： " + tRIRecordErrLog.mErrors.getFirstError());
            return false;
        }
        RIPubFun tRIPubFun = new RIPubFun();
        if(!tRIPubFun.recordLog(tRIWFLogSchema,mErrors.getFirstError(),"01")){
            buildError("recordLog","流程管理程序记录错误日志时出错： " + tRIPubFun.mErrors.getFirstError());
            return false;
        }
        return true;
    }
    
    private void buildError(String szFunc, String szErrMsg) 
    {
        CError cError = new CError();
        cError.moduleName = "RIWFManage";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }
    
    public static void main(String[] args) 
    {
        RIWFManage tRIWFManage = new RIWFManage();
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ManageCom="86";
        tGlobalInput.Operator = "001";
        tGlobalInput.ComCode = "86";
        VData tVData = new VData();
        RIWFLogDB tRIWFLogDB = new RIWFLogDB();
        tRIWFLogDB.setBatchNo("0000000160");

        TransferData tTransferData = new TransferData();

        String tSQL = " select * from RIWFLog a where a.nodestate = '00' ";
        RIWFLogSet tRIWFLogSet = tRIWFLogDB.executeQuery(tSQL);
        if(tRIWFLogSet.size()!=0){
            for(int i=1;i<=tRIWFLogSet.size();i++){
                tRIWFLogSet.get(i).setNodeState("01");
            }
            tTransferData.setNameAndValue("AUTOFLAG","2"); //1-全部执行, 2-分布执行
        }else{
            tSQL = " select * from RIWFLog a where a.nodestate <> '99' ";
             tRIWFLogSet = tRIWFLogDB.executeQuery(tSQL);
             for(int i=1;i<=tRIWFLogSet.size();i++){
                 tRIWFLogSet.get(i).setNodeState("03");
             }
             tTransferData.setNameAndValue("AUTOFLAG","1"); //1-全部执行, 2-分布执行
        }
        tTransferData.setNameAndValue("PATH","E:\\ing\\ui");
        tVData.add(tRIWFLogSet);
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        System.out.println(" start: "+PubFun.getCurrentTime());
        tRIWFManage.submitData(tVData, "");
        System.out.println(" end: "+PubFun.getCurrentTime());

    }


}
