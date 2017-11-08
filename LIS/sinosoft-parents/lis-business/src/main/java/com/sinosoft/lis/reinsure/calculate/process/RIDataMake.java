

package com.sinosoft.lis.reinsure.calculate.process;

import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.db.RIPolRecordDB;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.lis.vschema.RIRiskDivideSet;
import com.sinosoft.lis.vschema.RIDivisionLineDefSet;
import com.sinosoft.lis.vschema.RIIncomeCompanySet;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.reinsure.calculate.init.RIInitSplitSeg;
import com.sinosoft.lis.reinsure.calculate.manage.RICalMan;
import com.sinosoft.lis.reinsure.tools.RIAnalyzeTable;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.db.RIRecordTraceDB;
import com.sinosoft.lis.vschema.RIRecordTraceSet;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @zhangbin
 * @version 1.0
 */
public class RIDataMake implements RICalMan{
    private int rowNum=1000;
    private RIInitData mRIInitData ;
    private RIInitSplitSeg mRIInitSplitSeg;
    public CErrors mErrors = new CErrors();
    private RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
    private String mAccumulateDefNo = "";
    private String mEventType;
    private VData mInputData ;
    private VData mVData = new VData();
    private MMap mMap;
    private PubSubmit mPubSubmit = new PubSubmit();
    private boolean mEndFlag=false;
    private String[][] mSeg;

    public RIDataMake() {
    }

    public boolean submitData(VData cInputData, String cOperate){
        if (!getInputData(cInputData, cOperate)){
            return false;
        }
        if(!init()){
            return false;
        }
        if (!dealData()) {
            return false;
        }
        //记录工作流程日志
        if(!recordLog()){
            return false;
        }
        return true;
    }

    private boolean getInputData(VData cInputData,String cOperate){
        mEventType = cOperate;
        mRIWFLogSchema = (RIWFLogSchema)cInputData.getObjectByObjectName("RIWFLogSchema",0) ;
        mAccumulateDefNo = mRIWFLogSchema.getTaskCode();
        return true;
    }

    private boolean init() {
        try {
            mRIInitData = RIInitData.getObject(mAccumulateDefNo);
            mRIInitSplitSeg = RIInitSplitSeg.getObject(mRIWFLogSchema);
            mSeg=mRIInitSplitSeg.getValue();
        } catch (Exception ex) {
            buildError("initInfo", " 生成分保记录时初始化参数失败: "+ex);
            return false;
        }
        return true;
    }

    private boolean dealData() {
        if(mEventType.equals("01")){
            while (1 == 1) {
                if (!dealPolData()) {
                    return false;
                }
                if (mEndFlag) {
                    break;
                }
            }
        }
        if(mEventType.equals("02")){
            while (1 == 1) {
                if (!dealContinueData()) {
                    return false;
                }
                if (mEndFlag) {
                    break;
                }
            }
        }
        if(mEventType.equals("03")){
            while (1 == 1) {
                if (!dealEdorData()) {
                    return false;
                }
                if (mEndFlag) {
                    break;
                }
            }
        }
        if(mEventType.equals("04")){
            while (1 == 1) {
                if (!dealClaimData()) {
                    return false;
                }
                if (mEndFlag) {
                    break;
                }
            }
        }
        if(!analyzeTable()){
            //不论分析表是否成功，都继续执行
        }
        System.out.println(" delete start time: "+PubFun.getCurrentDate());
        if(!delRedunRecord()){
            return false;
        }
        System.out.println(" delete end time: "+PubFun.getCurrentDate());
        return true;
    }
    /**
     * 新单数据
     * @return boolean
     */
    private boolean dealPolData() {
        mMap = new MMap();
        mVData = new VData();
        try{
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append(" select * from ripolrecord a where a.AccumulateDefNO='");
            strBuffer.append(mRIInitData.getRIAccumulateDefNo() + "' and a.NodeState <> '08' and a.EventType='01' and a.batchno='");
            strBuffer.append(mRIWFLogSchema.getBatchNo());
            strBuffer.append("' and not exists (select * from rirecordtrace c where a.eventno=c.eventno) and rownum<'");
            strBuffer.append(rowNum);
            strBuffer.append("' order by a.InsuredNo,a.GetDate ");

            RIPolRecordDB tRIPolRecordDB = new RIPolRecordDB();
            System.out.println(" 新单再保数据: "+strBuffer.toString());
            RIPolRecordSet tRIPolRecordSet = tRIPolRecordDB.executeQuery(strBuffer.toString());
            if (tRIPolRecordDB.mErrors.needDealError()) {
                this.mErrors.copyAllErrors(tRIPolRecordDB.mErrors);
                return false;
            }
            if (tRIPolRecordSet.size() == 0) {
                System.out.println(" 累计风险："+mRIInitData.getRIAccumulateDefNo()+" 的新单分保记录生成完毕。");
                mEndFlag = true;
                return true;
            }
            RIProcessType[] tRIProcessType ;
            VData tVData;
            String tSQL;
            for (int i = 1; i <= tRIPolRecordSet.size(); i++) {
                if(tRIPolRecordSet.get(i).getRIPreceptNo()==null||tRIPolRecordSet.get(i).getRIPreceptNo().equals("")){
                    buildError("initInfo", "新单生成分保记录时发生错误: "+tRIPolRecordSet.get(i).getEventNo()+" 没有再保方案号");
                    return false;
                }
                tRIProcessType = (RIProcessType[])mRIInitData.getRIProcessCalClassTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());

                RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet)mRIInitData.getRIRiskDivideTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet)mRIInitData.getRIIncomeCompanyTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet)mRIInitData.getRIDivisionLineDefTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                if(!tRIProcessType[0].dealData(tRIPolRecordSet.get(i),tRIIncomeCompanySet,tRIDivisionLineDefSet,tRIRiskDivideSet)){
                    buildError("initInfo"," 生成累积风险为： "+tRIPolRecordSet.get(i).getAccumulateDefNO()+" 事件号为： "+tRIPolRecordSet.get(i).getEventNo()+" 的再保记录时出现错误： "+tRIProcessType[0].getCErrors());
                    return false ;
                }
                tVData = new VData();
                tVData = tRIProcessType[0].getValue();
                if (!getMapInfo(tVData)) {
                    return false;
                }
                tSQL = "update RIPolRecord set nodestate='08' where eventno='"+tRIPolRecordSet.get(i).getEventNo()+"'";
                mMap.put(tSQL,"UPDATE");
            }
            mVData.add(mMap);

            if(!saveData()){
                return false;
            }

        }catch(Exception ex){
            buildError("initInfo", "新单生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()+"  "+ex);
            return false;
        }
        return true;
    }

    /**
     * 续期
     * @param tVData VData
     * @return boolean
     */
    private boolean dealContinueData(){
        mVData = new VData();
        mMap = new MMap();
        try{
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append(" select * from ripolrecord a where a.AccumulateDefNO='");
            strBuffer.append(mRIInitData.getRIAccumulateDefNo() + "' and a.NodeState <> '08' and a.EventType='02' and a.batchno='");
            strBuffer.append(mRIWFLogSchema.getBatchNo());
            strBuffer.append("' and not exists (select * from rirecordtrace c where a.eventno=c.eventno) and rownum<'");
            strBuffer.append(rowNum);
            strBuffer.append("' order by a.InsuredNo,a.GetDate ");

            RIPolRecordDB tRIPolRecordDB = new RIPolRecordDB();
            RIPolRecordSet tRIPolRecordSet = tRIPolRecordDB.executeQuery(strBuffer.toString());
            if (tRIPolRecordDB.mErrors.needDealError()) {
                this.mErrors.copyAllErrors(tRIPolRecordDB.mErrors);
                return false;
            }
            if (tRIPolRecordSet.size() == 0) {
                System.out.println(" 累计风险："+mRIInitData.getRIAccumulateDefNo()+" 的续期分保记录生成完毕。");
                mEndFlag = true;
                return true;
            }
            RIProcessType[] tRIProcessType;
            VData tVData;
            String tSQL;
            for (int i = 1; i <= tRIPolRecordSet.size(); i++) {
                if(tRIPolRecordSet.get(i).getRIPreceptNo()==null||tRIPolRecordSet.get(i).getRIPreceptNo().equals("")){
                    buildError("initInfo", "新单生成分保记录时发生错误: "+tRIPolRecordSet.get(i).getEventNo()+" 没有再保方案号");
                    return false;
                }
                tRIProcessType = (RIProcessType[])mRIInitData.getRIProcessCalClassTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet)mRIInitData.getRIRiskDivideTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet)mRIInitData.getRIIncomeCompanyTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet)mRIInitData.getRIDivisionLineDefTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                if(!tRIProcessType[1].dealData(tRIPolRecordSet.get(i),tRIIncomeCompanySet,tRIDivisionLineDefSet,tRIRiskDivideSet)){
                    buildError("initInfo"," 生成累积风险为： "+tRIPolRecordSet.get(i).getAccumulateDefNO()+" 事件号为： "+tRIPolRecordSet.get(i).getEventNo()+" 的再保记录时出现错误： "+tRIProcessType[0].getCErrors());
                    return false ;
                }

                tVData = tRIProcessType[1].getValue();
                if (!getMapInfo(tVData)) {
                    return false;
                }
                tSQL = "update RIPolRecord set nodestate='08' where eventno='"+tRIPolRecordSet.get(i).getEventNo()+"'";
                mMap.put(tSQL,"UPDATE");
            }

            if(!saveData()){
                return false;
            }

        }catch(Exception ex){
            buildError("initInfo", "生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()+"  "+ex);
            return false;
        }
        return true;
    }
    /**
     * 保全
     * @return boolean
     */
    private boolean dealEdorData(){
        mVData = new VData();
        mMap = new MMap();
        try{
            mMap = new MMap();
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append(" select * from ripolrecord a where a.AccumulateDefNO='");
            strBuffer.append(mRIInitData.getRIAccumulateDefNo() + "' and a.NodeState <> '08' and a.EventType='03' and a.batchno='");
            strBuffer.append(mRIWFLogSchema.getBatchNo());
            strBuffer.append("' and not exists (select * from rirecordtrace c where a.eventno=c.eventno) and rownum<'");
            strBuffer.append(rowNum);
            strBuffer.append("' order by a.InsuredNo,a.GetDate ");

            RIPolRecordDB tRIPolRecordDB = new RIPolRecordDB();
            RIPolRecordSet tRIPolRecordSet = tRIPolRecordDB.executeQuery(strBuffer.toString());
            if (tRIPolRecordDB.mErrors.needDealError()) {
                this.mErrors.copyAllErrors(tRIPolRecordDB.mErrors);
                return false;
            }
            if (tRIPolRecordSet.size() == 0) {
                System.out.println(" 累计风险："+mRIInitData.getRIAccumulateDefNo()+" 的保全分保记录生成完毕。");
                mEndFlag = true;
                return true;
            }
            RIProcessType[] tRIProcessType;
            VData tVData;
            String tSQL;
            for (int i = 1; i <= tRIPolRecordSet.size(); i++) {
                if(tRIPolRecordSet.get(i).getRIPreceptNo()==null||tRIPolRecordSet.get(i).getRIPreceptNo().equals("")){
                    buildError("initInfo", "新单生成分保记录时发生错误: "+tRIPolRecordSet.get(i).getEventNo()+" 没有再保方案号");
                    return false;
                }
                tRIProcessType = (RIProcessType[])mRIInitData.getRIProcessCalClassTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet)mRIInitData.getRIRiskDivideTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet)mRIInitData.getRIIncomeCompanyTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet)mRIInitData.getRIDivisionLineDefTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                if(!tRIProcessType[2].dealData(tRIPolRecordSet.get(i),tRIIncomeCompanySet,tRIDivisionLineDefSet,tRIRiskDivideSet)){
                    buildError("initInfo"," 生成累积风险为： "+tRIPolRecordSet.get(i).getAccumulateDefNO()+" 事件号为： "+tRIPolRecordSet.get(i).getEventNo()+" 的再保记录时出现错误： "+tRIProcessType[0].getCErrors());
                    return false ;
                }

                tVData = tRIProcessType[2].getValue();
                if (!getMapInfo(tVData)) {
                    return false;
                }
                tSQL = "update RIPolRecord set nodestate='08' where eventno='"+tRIPolRecordSet.get(i).getEventNo()+"'";
                mMap.put(tSQL,"UPDATE");
            }
            if(!saveData()){
                return false;
            }
        }catch(Exception ex){
            buildError("initInfo", "生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()+"  "+ex);
        }
        return true;
    }
    /**
     * 理赔
     * @return boolean
     */
    private boolean dealClaimData(){
        mVData = new VData();
        mMap = new MMap();
        try{
            mMap = new MMap();
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append(" select * from ripolrecord a where a.AccumulateDefNO='");
            strBuffer.append(mRIInitData.getRIAccumulateDefNo() + "' and a.NodeState <> '08' and a.EventType='04' and a.batchno='");
            strBuffer.append(mRIWFLogSchema.getBatchNo());
            strBuffer.append("' and not exists (select * from rirecordtrace c where a.eventno=c.eventno) and rownum<'");
            strBuffer.append(rowNum);
            strBuffer.append("' order by a.InsuredNo,a.GetDate ");

            RIPolRecordDB tRIPolRecordDB = new RIPolRecordDB();
            RIPolRecordSet tRIPolRecordSet = tRIPolRecordDB.executeQuery(strBuffer.toString());
            if (tRIPolRecordDB.mErrors.needDealError()) {
                this.mErrors.copyAllErrors(tRIPolRecordDB.mErrors);
                return false;
            }
            if (tRIPolRecordSet.size() == 0) {
                System.out.println(" 累计风险："+mRIInitData.getRIAccumulateDefNo()+" 的理赔分保记录生成完毕。");
                mEndFlag = true;
                return true;
            }
            RIProcessType[] tRIProcessType;
            VData tVData;
            String tSQL;
            for (int i = 1; i <= tRIPolRecordSet.size(); i++) {
                if(tRIPolRecordSet.get(i).getRIPreceptNo()==null||tRIPolRecordSet.get(i).getRIPreceptNo().equals("")){
                    buildError("initInfo", "新单生成分保记录时发生错误: "+tRIPolRecordSet.get(i).getEventNo()+" 没有再保方案号");
                    return false;
                }
                tRIProcessType = (RIProcessType[])mRIInitData.getRIProcessCalClassTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIRiskDivideSet tRIRiskDivideSet = (RIRiskDivideSet)mRIInitData.getRIRiskDivideTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIIncomeCompanySet tRIIncomeCompanySet = (RIIncomeCompanySet)mRIInitData.getRIIncomeCompanyTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                RIDivisionLineDefSet tRIDivisionLineDefSet = (RIDivisionLineDefSet)mRIInitData.getRIDivisionLineDefTD().getValueByName(tRIPolRecordSet.get(i).getRIPreceptNo());
                if(!tRIProcessType[3].dealData(tRIPolRecordSet.get(i),tRIIncomeCompanySet,tRIDivisionLineDefSet,tRIRiskDivideSet)){
                    buildError("initInfo"," 生成累积风险为： "+tRIPolRecordSet.get(i).getAccumulateDefNO()+" 事件号为： "+tRIPolRecordSet.get(i).getEventNo()+" 的再保记录时出现错误： "+tRIProcessType[0].getCErrors());
                    return false ;
                }

                tVData = tRIProcessType[3].getValue();
                if (!getMapInfo(tVData)) {
                    return false;
                }
                tSQL = "update RIPolRecord set nodestate='08' where eventno='"+tRIPolRecordSet.get(i).getEventNo()+"'";
                mMap.put(tSQL,"UPDATE");
            }

            if(!saveData()){
                return false;
            }
        }catch(Exception ex){
            buildError("initInfo", "生成分保记录时发生错误: " + mRIInitData.getRIAccumulateDefNo()+"  "+ex);
        }
        return true;
    }

    /**
     * 删除多余的记录
     * @return boolean
     */
    private boolean delRedunRecord(){
        for(int i=0;i<mSeg.length;i++){
            if (mEventType.equals("01")) {
                return true;
            }
            StringBuffer strBuffer;
            strBuffer = new StringBuffer();

            strBuffer.append(" select * from rirecordtrace a where not exists (select 1 from rirecordtrace x where x.ridate<a.ridate and x.RIPreceptNo=a.RIPreceptNo and x.ContNo=a.contno and x.OtherNo=a.Otherno and x.AreaID=a.AreaID and exists (select 1 from ripolrecordbake y where x.eventno=y.eventno)");
            strBuffer.append(" and x.eventno between '");
            strBuffer.append(mSeg[i][0]);
            strBuffer.append("' and '");
            strBuffer.append(mSeg[i][1]);
            strBuffer.append("')");
            if (mEventType.equals("02")) {
                strBuffer.append(" and a.Eventtype='02'");
            }
            if (mEventType.equals("03")) {
                strBuffer.append(" and ((a.Eventtype='03' and a.Addsubflag='3') or (a.Eventtype='03' and a.Addsubflag='4')) ");
            }
            if (mEventType.equals("04")) {
                strBuffer.append(" and a.Eventtype='04'");
            }
            strBuffer.append(" and a.BatchNo='");
            strBuffer.append(this.mRIWFLogSchema.getBatchNo());
            strBuffer.append("' and a.AccumulateDefNO='");
            strBuffer.append(this.mAccumulateDefNo);
            strBuffer.append("' and a.eventno between '");
            strBuffer.append(mSeg[i][0]);
            strBuffer.append("' and '");
            strBuffer.append(mSeg[i][1]);
            strBuffer.append("' ");
            System.out.println(" del trace sql: "+strBuffer.toString());
            RIRecordTraceDB tRIRecordTraceDB = new RIRecordTraceDB();
            RIRecordTraceSet tRIRecordTraceSet = tRIRecordTraceDB.executeQuery(strBuffer.toString());
            if(tRIRecordTraceDB.mErrors.needDealError()){
                buildError("initInfo", "累计风险编码："+mAccumulateDefNo+",查询需要删除的分保记录时出错: ");
                return false;
            }
            mMap = new MMap();
            mMap.put(tRIRecordTraceSet, "DELETE");
            if (!saveData()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 分保记录添加到MMap
     * @param tVData VData
     * @return boolean
     */
    private boolean getMapInfo(VData tVData){
        try
        {
            MMap tmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
            mMap.add(tmap);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    /**
     * 保存结果
     * @return boolean
     */
    private boolean saveData() {
        try {
            this.mVData.clear();
            this.mVData.add(mMap);
            if (!mPubSubmit.submitData(this.mVData, "")) {
                if (mPubSubmit.mErrors.needDealError()) {
                    buildError("saveResult", "保存再保计算结果时出现错误!");
                    return false;
                }
            }
            mMap = null;
        } catch (Exception ex) {
            CError tError = new CError();
            tError.moduleName = "RICalItemProc";
            tError.functionName = "saveResult";
            tError.errorMessage = "保存再保结果时出错：" + ex.getMessage();
            System.out.println(" ex.getMessage() " + ex.getMessage());
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    private boolean analyzeTable(){
        RIAnalyzeTable tRIAnalyzeTable = new RIAnalyzeTable();
        tRIAnalyzeTable.analyzeTable("RIRECORDTRACE");
        return true;
    }

    private boolean recordLog(){

        return true;
    }

    public static void main(String[] args) {
        RIDataMake tRIDataMake = new RIDataMake();
        VData tVData = new VData();
        RIWFLogSchema tRIWFLogSchema = new RIWFLogSchema();
        tRIWFLogSchema.setTaskCode("L001000001");
        tRIWFLogSchema.setBatchNo("0000000031");
        tRIWFLogSchema.setNodeState("");
        tVData.add(tRIWFLogSchema);
        tRIDataMake.submitData(tVData,"");
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "CalItemDeal";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }
}
