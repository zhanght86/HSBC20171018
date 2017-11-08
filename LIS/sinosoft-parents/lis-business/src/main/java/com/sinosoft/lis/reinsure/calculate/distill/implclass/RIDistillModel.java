

package com.sinosoft.lis.reinsure.calculate.distill.implclass;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.reinsure.calculate.distill.RIDistill;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.CError;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RIDistillModel implements RIDistill {
    String mEventType;
    RIWFLogSchema mRIWFLogSchema ;
    String mAccumulateDefNo ;
    /** 数据批次处理限制数 */
    private int mMaxDealNUm = 5000;

    public RIDistillModel() {
    }

    /**
     * distillData
     *
     * @return boolean
     * @todo Implement this
     *   com.sinosoft.lis.reinsure.calculate.distill.RIDistill method
     */
    public boolean submitData(VData cInputData, String cOperate){
        if(!getInputData(cInputData,cOperate)){
            return false;
        }
        if(!dealData()){
            return false;
        }

        return true;
    }

    private boolean getInputData(VData cInputData, String cOperate){
        mEventType = cOperate;
        mRIWFLogSchema = (RIWFLogSchema)cInputData.getObjectByObjectName("RIWFLogSchema",0) ;
        mAccumulateDefNo = mRIWFLogSchema.getTaskCode();

        return true;
    }

    private boolean dealData(){
        try{
            String sql = getDistillSQL();
            while (haveDataUnsettled()) {
                sql = " insert into RIPolRecord (" + sql + ")";
                ExeSQL exeSQL = new ExeSQL();
                if (!exeSQL.execUpdateSQL(sql)) {
                    buildError("dealData", " 提取新单业务数据失败 ");
                    return false;
                }
            }
            if(this.mErrors.needDealError()){
                return false;
            }
        }catch(Exception ex){
            buildError("dealData", " 提取新单业务数据失败 "+ex.getMessage());
            return false;
        }
        return true;
    }

    private String getDistillSQL(){
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append(" select ");
        strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
        strBuffer.append(" getSerialNo as EventNo, ");
        strBuffer.append(" '01' as EventType, ");
        strBuffer.append(" '' as RecordType, ");
        strBuffer.append(" '02' as DataFlag, ");
        strBuffer.append(" trim(a.PolNo)||','||trim(b.DutyCode) as UnionKey, ");
        strBuffer.append(" a.GrpContNo as GrpContNo, ");
        strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
        strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
        strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
        strBuffer.append(" a.ContNo as ContNo, ");
        strBuffer.append(" a.PolNo as PolNo, ");
        strBuffer.append(" a.ProposalNo as ProposalNo, ");
        strBuffer.append(" '' as ContPlanCode, ");
        strBuffer.append(" a.RiskCode as RiskCode, ");
        strBuffer.append(" b.DutyCode as DutyCode, ");
        strBuffer.append(" a.Years as Years, ");
        strBuffer.append(" a.insuredyear as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" a.CValiDate as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LDPerson b where trim(b.customerno)=trim(a.Insuredno)) as IDType, ");
        strBuffer.append(" (select b.IDNo from LDPerson b where trim(b.customerno)=trim(a.Insuredno)) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as OccupationCode , ");
        strBuffer.append(" b.StandPrem as StandPrem, ");
        strBuffer.append(" b.Prem as Prem, ");
        strBuffer.append(" b.Amnt as RiskAmnt, ");
        strBuffer.append(" b.Amnt as Amnt,  ");
        strBuffer.append(" '' as Mult, ");
        strBuffer.append(" b.PayIntv as PayIntv, ");
        strBuffer.append(" b.PayYears as PayYears, ");
        strBuffer.append(" '' as PayEndYearFlag, ");
        strBuffer.append(" '' as PayEndYear, ");
        strBuffer.append(" '' as GetYearFlag, ");
        strBuffer.append(" '' as GetYear, ");
        strBuffer.append(" '' as InsuYearFlag, ");
        strBuffer.append(" '' as InsuYear, ");
        strBuffer.append(" '' as AcciYearFlag, ");
        strBuffer.append(" '' as AcciYear, ");
        strBuffer.append(" '' as AcciEndDate, ");
        strBuffer.append(" '' as GetStartDate, ");
        strBuffer.append(" '' as GetStartType, ");
        strBuffer.append(" '' as PeakLine, ");
        strBuffer.append(" '' as GetLimit, ");
        strBuffer.append(" '' as GetIntv, ");
        strBuffer.append(" '' as PayNo, ");
        strBuffer.append(" '' as PayCount, ");
        strBuffer.append(" '' as PayMoney, ");
        strBuffer.append(" '' as LastPayToDate, ");
        strBuffer.append(" '' as CurPayToDate, ");
        strBuffer.append(" '' as EdorNo, ");
        strBuffer.append(" '' as FeeOperationType, ");
        strBuffer.append(" '' as FeeFinaType, ");
        //strBuffer.append(" AccumulateAmnt(a.InsuredNo,b.DutyCode,a.ContNo) as AccAmnt, ");
        strBuffer.append(" 0 as AccAmnt, ");
        strBuffer.append(" 0 as PreStandPrem, ");
        strBuffer.append(" 0 as PrePrem, ");
        strBuffer.append(" 0 as PreRiskAmnt, ");
        strBuffer.append(" 0 as PreAmnt, ");
        strBuffer.append(" '' as ClmNo, ");
        strBuffer.append(" '' as ClmFeeOperationType, ");
        strBuffer.append(" '' as ClmFeeFinaType, ");
        strBuffer.append(" 0 as StandGetMoney, ");
        strBuffer.append(" '' as GetRate, ");
        strBuffer.append(" 0 as ClmGetMoney, ");
        strBuffer.append(" '' as AccDate, ");
        strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
        strBuffer.append(" '' as RIContNo, ");
        strBuffer.append(" '' as RIPreceptNo, ");
        strBuffer.append(" '' as NodeState, ");
        strBuffer.append(" '' as ReinsreFlag, ");
        strBuffer.append(" a.signdate as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        strBuffer.append(" null as StandbyString2, ");
        strBuffer.append(" null as StandbyString3, ");
        strBuffer.append(" null as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");
        strBuffer.append(" null as StandbyDate1, ");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime ");
        strBuffer.append(" from lcpol a ,lcduty b  ");
        strBuffer.append(" where a.polno=b.polno and a.conttype='1' ");
        strBuffer.append(" and trim(b.dutycode) in (select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"') ");
        strBuffer.append(" and not exists (select * from ridutystate where proposalno=a.proposalno and dutycode=b.dutycode and state in ('04','03','02')) ");
        strBuffer.append(" and a.signdate between '");
        strBuffer.append(mRIWFLogSchema.getStartDate());
        strBuffer.append("' and '");
        strBuffer.append(mRIWFLogSchema.getEndDate());
        strBuffer.append("' and a.cvalidate<='");
        strBuffer.append(mRIWFLogSchema.getEndDate());
        strBuffer.append("' and not exists (Select * From RIPolRecord m where m.unionkey=(trim(a.PolNo)||','||trim(b.DutyCode))) ");
        strBuffer.append(" and not exists (Select * From RIPolRecordBake n where n.unionkey=(trim(a.PolNo)||','||trim(b.DutyCode))) ");
        strBuffer.append(" and rownum <=" + mMaxDealNUm);
        System.out.println(" sql: "+strBuffer.toString());
        return strBuffer.toString();
    }

    /**
     * haveDataUnsettled
     * @return boolean
     */
    public boolean haveDataUnsettled() {
        try{
            String sql = getDistillSQL();
            sql = "select count(*) from (" + sql + ")";
            ExeSQL tExeSQL = new ExeSQL();
            int countNum = Integer.parseInt(tExeSQL.getOneValue(sql));
            if (countNum > 0) {
                return true;
            } else {
                return false;
            }
        }catch(Exception ex){
            buildError("initInfo", " 业务数据提取时出错，查询业务记录数时报错。" + ex);
            return false;
        }
    }
    /**
     * getCErrors
     * @return CErrors
     * @todo Implement this
     *   com.sinosoft.lis.reinsure.calculate.distill.RIDistill method
     */
    public CErrors getCErrors() {
        return this.mErrors;
    }
    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "RIDataMake";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }
}
