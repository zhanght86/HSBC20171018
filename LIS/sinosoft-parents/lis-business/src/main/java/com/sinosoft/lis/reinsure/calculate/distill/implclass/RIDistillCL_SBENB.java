

package com.sinosoft.lis.reinsure.calculate.distill.implclass;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.calculate.distill.RIDistill;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description:再保提取SBENB的公共交通意外身故理赔数据 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author LIJIAN
 * @version 1.0
 */
public class RIDistillCL_SBENB implements RIDistill {
    String mEventType;
    RIWFLogSchema mRIWFLogSchema ;
    String mAccumulateDefNo ;
    private MMap mMap ;
    private VData mInputData;
    private PubSubmit mPubSubmit = new PubSubmit();

    private String tSql;

    public RIDistillCL_SBENB() {
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
    /**
     * 理赔SBENB合同提数
     * @return
     */
    private boolean dealData(){
        try{
        	RIPolRecordSet tRIPolRecordSet = new RIPolRecordSet();
            RSWrapper rsWrapper = new RSWrapper();
        	//合同分保提数
            tSql = getDistillSQL();

            if (!rsWrapper.prepareData(tRIPolRecordSet, tSql)){
                mErrors.copyAllErrors(rsWrapper.mErrors);
                System.out.println(rsWrapper.mErrors.getFirstError());
                return false;
            }
            do{
            	 mMap= new MMap();
            	 mInputData = new VData();
            	 rsWrapper.getData();
                 if (tRIPolRecordSet != null && tRIPolRecordSet.size() > 0){
                	 mMap.put(tRIPolRecordSet, "INSERT");
                	 mInputData.add(mMap);
                	 mPubSubmit.submitData(mInputData, "");
                 }
                 mMap = null;
                 mInputData = null;
                 
            }while(tRIPolRecordSet != null && tRIPolRecordSet.size() > 0);

            rsWrapper.close();
            
            //临时分保提数
            tSql = getDistillSQL1();
            if (!rsWrapper.prepareData(tRIPolRecordSet, tSql)){
                mErrors.copyAllErrors(rsWrapper.mErrors);
                System.out.println(rsWrapper.mErrors.getFirstError());
                return false;
            }
            do{
            	 mMap= new MMap();
            	 mInputData = new VData();
            	 rsWrapper.getData();
                 if (tRIPolRecordSet != null && tRIPolRecordSet.size() > 0){
                	 mMap.put(tRIPolRecordSet, "INSERT");
                	 mInputData.add(mMap);
                	 mPubSubmit.submitData(mInputData, "");
                 }
                 mMap = null;
                 mInputData = null;
                 
            }while(tRIPolRecordSet != null && tRIPolRecordSet.size() > 0);

            rsWrapper.close();
            
        }catch(Exception ex){
            buildError("dealData", " 提取理赔SBENB业务数据失败 "+ex.getMessage());
            return false;
        }
        return true;
    }
    /**
     * 理赔SBENB临分提数
     * @return
     */
    private String getDistillSQL(){
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
        //现价
        strBuffer.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4, (InsuredAge+InsuredYear-1) StandbyString5,");
        //最大死亡保额,如果没有最大死亡保额用保额代替
        strBuffer.append("(select case when most_deadamnt(payyears,insuredyear,amnt,insuredage,years,insuredsex,prem,riskcode,payendyear,payintv)=0 then amnt else most_deadamnt(payyears,insuredyear,amnt,insuredage,years,insuredsex,prem,riskcode,payendyear,payintv) end from dual ) as StandbyDouble1,");
        //年缴化保费
        strBuffer.append("APE(Prem ,PayIntv) ,");
        //准备金
        strBuffer.append("LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),");
        strBuffer.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");
        
        strBuffer.append(" select ");
        strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
        strBuffer.append(" getSerialNo as EventNo, ");
        strBuffer.append(" '04' as EventType, ");
        strBuffer.append(" '' as RecordType, ");
        strBuffer.append(" '01' as DataFlag, ");//01:个人险种 02：个人险种责任
        strBuffer.append(" a.PolNo || ','||b.clmno||','||b.endcasedate as UnionKey, ");
        strBuffer.append(" a.GrpContNo as GrpContNo, ");
        strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
        strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
        strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
        strBuffer.append(" a.ContNo as ContNo, ");
        strBuffer.append(" a.PolNo as PolNo, ");
        strBuffer.append(" a.ProposalNo as ProposalNo, ");
        strBuffer.append(" '' as ContPlanCode, ");
        strBuffer.append(" a.RiskCode as RiskCode, ");
        strBuffer.append(" '000000' as DutyCode, ");
        strBuffer.append(" a.Years as Years, ");
        strBuffer.append(" ceil(months_between(b.endcasedate,a.cvalidate-1)/12) as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" a.CValiDate as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
        strBuffer.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
//      追朔出险时点的保费
        strBuffer.append(" nvl((select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno), a.StandPrem ) as StandPrem, ");
        strBuffer.append(" nvl((select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.prem) as Prem, ");
        //风险保额在理赔的时候没有实际意义所以用什么时点的保额计算不重要
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between(b.endcasedate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
//      追朔出险时点的保额
        strBuffer.append(" nvl((select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.amnt) as Amnt,  ");
        strBuffer.append(" '' as Mult, ");
        strBuffer.append(" a.PayIntv as PayIntv, ");
        strBuffer.append(" a.PayYears as PayYears, ");
        strBuffer.append(" a.PayEndYearFlag as PayEndYearFlag, ");
        strBuffer.append(" a.PayEndYear as PayEndYear, ");
        strBuffer.append(" '' as GetYearFlag, ");
        strBuffer.append(" '' as GetYear, ");
        strBuffer.append(" a.InsuYearFlag as InsuYearFlag, ");
        strBuffer.append(" a.InsuYear as InsuYear, ");
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
        strBuffer.append(" b.clmno as ClmNo, ");
        strBuffer.append(" '' as ClmFeeOperationType, ");
        strBuffer.append(" '' as ClmFeeFinaType, ");
        strBuffer.append(" b.standpay as StandGetMoney, ");
        strBuffer.append(" '' as GetRate, ");
        strBuffer.append(" b.realpay as ClmGetMoney, ");
        strBuffer.append(" b.accdate as AccDate, ");
        strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
        strBuffer.append(" '' as RIContNo, ");
        strBuffer.append(" '' as RIPreceptNo, ");
        strBuffer.append(" '01' as NodeState, ");
        strBuffer.append(" '' as ReinsreFlag, ");
        strBuffer.append(" b.endcasedate as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        strBuffer.append(" null as StandbyString2, ");
        strBuffer.append(" null as StandbyString3, ");
//      长短险标志  1:短期险 2：长期险
        strBuffer.append(" (select decode( riskperiod,'M' , '1' , '2' ) from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        //年缴化保费
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");
        //生日
        strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" date '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
        strBuffer.append(" a.managecom as managecom ");
        strBuffer.append(" from lcpol a,(  ");
        		strBuffer.append(" select c.caseno clmno,");//分案号                     *****************
        		strBuffer.append(" c.polno polno,");
        		strBuffer.append(" max(e.endcasedate) endcasedate,");//结案日期
        		strBuffer.append(" sum(c.standpay) standpay,");//核算赔付金额        *****************
        		strBuffer.append(" sum(c.realpay) realpay,");//核赔赔付金额         *****************
        		strBuffer.append(" max(d.AccDate) AccDate,");//出险日期  AccStartDate//出险开始日期  *************
        		strBuffer.append(" max(c.getdutykind) getdutykind ");//付责任类型 100意外医疗101意外伤残102意外死亡103意外高残104意外大病105意外特种疾病106意外失业失能109意外豁免200疾病医疗201疾病伤残
        		//strBuffer.append(" max(confdate)   confdate ");    //理赔核销日期                  ****************
        		strBuffer.append(" from LLcase d, LLClaim e,LLClaimDetail c ");
        		strBuffer.append(" where c.CaseNo = d.caseno and c.caseno = e.caseno ");
        		/*如果提数是到给付责任增加下面的查询条件,
        		LLClaimDetail.getdutykind类型：
        		X00--医疗
        		X01--残疾金
        		X02--身故金
        		X03--全残
        		X04--重大疾病
        		X05--女性特种疾病（描述女性妊娠期疾病保险金和婴儿身故保险金等）
        		X06--失业和失能
        		X07--生命末期
        		X09--豁免
        		X为1，指意外；X为2，指疾病
        		001--临时保单意外死亡
        		如：102为意外身故*/
        		strBuffer.append(" and c.getdutycode='BENB50' ");//提取SBENB的公共交通意外身故理赔数据getdutycode='BENB50'
        		//strBuffer.append(" and f.othernotype='5' ");
        		strBuffer.append(" and c.givetype = '0'"); //非拒赔
        		strBuffer.append(" and e.clmstate = '60'");
        		strBuffer.append(" and e.endcasedate between '"+mRIWFLogSchema.getStartDate()+"' and '"+mRIWFLogSchema.getEndDate()+"' ");
        		strBuffer.append(" group by c.caseno, c.polno,AccDate");
        		strBuffer.append(") b");
        strBuffer.append(" where a.conttype='1' ");
//        String tsql="select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"' ";
   
//      不为临分的保单
        strBuffer.append(" and not exists (select 'X' from ridutystate where proposalno=a.proposalno and state in ('00','02','03','04')) ");
     //   strBuffer.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','||b.clmno||','||b.endcasedate)) ");
     //   strBuffer.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','||b.clmno||','||b.endcasedate)) ");

        strBuffer.append(" and exists(select 'X' from riaccumulaterdcode r where a.riskcode = r.associatedcode and r.accumulatedefno='"+mAccumulateDefNo+"') ");
        strBuffer.append(" and a.polno=b.polno ");
        strBuffer.append(") order by GetDate,polno");
        System.out.println("理赔SBENB提数sql: "+strBuffer.toString());
        return strBuffer.toString();
    }

    private String getDistillSQL1(){
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
        //现价
        strBuffer.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,(InsuredAge+InsuredYear-1) StandbyString5,");
        //最大死亡保额,如果没有最大死亡保额用保额代替
        strBuffer.append("(select case when most_deadamnt(payyears,insuredyear,amnt,insuredage,years,insuredsex,prem,riskcode,payendyear,payintv)=0 then amnt else most_deadamnt(payyears,insuredyear,amnt,insuredage,years,insuredsex,prem,riskcode,payendyear,payintv) end from dual ) as StandbyDouble1,");
        //年缴化保费
        strBuffer.append("APE(Prem ,PayIntv) ,");
        //准备金       
        strBuffer.append("LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),");
        strBuffer.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");
        
        strBuffer.append(" select ");
        strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
        strBuffer.append(" getSerialNo as EventNo, ");
        strBuffer.append(" '04' as EventType, ");
        strBuffer.append(" '' as RecordType, ");
        strBuffer.append(" '01' as DataFlag, ");//01:个人险种 02：个人险种责任
        strBuffer.append(" a.PolNo || ','||b.clmno||','||b.endcasedate as UnionKey, ");
        strBuffer.append(" a.GrpContNo as GrpContNo, ");
        strBuffer.append(" '00000000000000000000' as ProposalGrpContNo, ");
        strBuffer.append(" a.GrpPolNo as GrpPolNo, ");
        strBuffer.append(" '00000000000000000000' as GrpProposalNo, ");
        strBuffer.append(" a.ContNo as ContNo, ");
        strBuffer.append(" a.PolNo as PolNo, ");
        strBuffer.append(" a.ProposalNo as ProposalNo, ");
        strBuffer.append(" '' as ContPlanCode, ");
        strBuffer.append(" a.RiskCode as RiskCode, ");
        strBuffer.append(" '000000' as DutyCode, ");
        strBuffer.append(" a.Years as Years, ");
        //保单年度
        strBuffer.append(" ceil(months_between(b.endcasedate,a.cvalidate-1)/12) as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" a.CValiDate as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
        strBuffer.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
        //追朔出险时点的保费
        strBuffer.append(" nvl((select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno),a.StandPrem  ) as StandPrem, ");
        strBuffer.append(" nvl((select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno) ,a.prem) as Prem, ");
        //风险保额在理赔的时候没有实际意义所以用什么时点的保额计算不重要
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between(b.endcasedate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
        //追朔出险时点的保额
        strBuffer.append(" nvl((select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate and rownum='1') and rownum='1') and polno=a.polno) , a.amnt) as Amnt,  ");
        strBuffer.append(" '' as Mult, ");
        strBuffer.append(" a.PayIntv as PayIntv, ");
        strBuffer.append(" a.PayYears as PayYears, ");
        strBuffer.append(" a.PayEndYearFlag as PayEndYearFlag, ");
        strBuffer.append(" a.PayEndYear as PayEndYear, ");
        strBuffer.append(" '' as GetYearFlag, ");
        strBuffer.append(" '' as GetYear, ");
        strBuffer.append(" a.InsuYearFlag as InsuYearFlag, ");
        strBuffer.append(" a.InsuYear as InsuYear, ");
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
        strBuffer.append(" b.clmno as ClmNo, ");
        strBuffer.append(" '' as ClmFeeOperationType, ");
        strBuffer.append(" '' as ClmFeeFinaType, ");
        strBuffer.append(" b.standpay as StandGetMoney, ");
        strBuffer.append(" '' as GetRate, ");
        strBuffer.append(" b.realpay as ClmGetMoney, ");
        strBuffer.append(" b.accdate as AccDate, ");
        strBuffer.append(" '" + mAccumulateDefNo + "' as AccumulateDefNO, ");
        strBuffer.append(" '' as RIContNo, ");
        strBuffer.append(" (select RIPreceptNo from RITempContLink where proposalno=a.proposalno ) as RIPreceptNo, ");
        strBuffer.append(" '01' as NodeState, ");
        strBuffer.append(" (select State from ridutystate where proposalno=a.proposalno) as ReinsreFlag, ");
        strBuffer.append(" b.endcasedate as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        strBuffer.append(" null as StandbyString2, ");
        strBuffer.append(" null as StandbyString3, ");
        //长短险标志  1:短期险 2：长期险
        strBuffer.append(" (select decode( riskperiod,'M' , '1' , '2' ) from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        //年缴化保费
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");
        //生日
        strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" date '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
        strBuffer.append(" a.managecom as managecom ");
        strBuffer.append(" from lcpol a,(  ");
        		strBuffer.append(" select c.caseno clmno,");//分案号                     *****************
        		strBuffer.append(" c.polno polno,");
        		strBuffer.append(" max(e.endcasedate) endcasedate,");//结案日期
        		strBuffer.append(" sum(c.standpay) standpay,");//核算赔付金额        *****************
        		strBuffer.append(" sum(c.realpay) realpay,");//核赔赔付金额         *****************
        		strBuffer.append(" max(d.AccDate) AccDate,");//出险日期  AccStartDate//出险开始日期  *************
        		strBuffer.append(" max(c.getdutykind) getdutykind ");//付责任类型 100意外医疗101意外伤残102意外死亡103意外高残104意外大病105意外特种疾病106意外失业失能109意外豁免200疾病医疗201疾病伤残
        		//strBuffer.append(" max(confdate)   confdate ");    //理赔核销日期                  ****************
        		strBuffer.append(" from  LLcase d, LLClaim e, LLClaimDetail c ");
        		strBuffer.append(" where c.CaseNo = d.caseno and c.caseno = e.caseno ");
        		/*如果提数是到给付责任增加下面的查询条件,
        		LLClaimDetail.getdutykind类型：
        		X00--医疗
        		X01--残疾金
        		X02--身故金
        		X03--全残
        		X04--重大疾病
        		X05--女性特种疾病（描述女性妊娠期疾病保险金和婴儿身故保险金等）
        		X06--失业和失能
        		X07--生命末期
        		X09--豁免
        		X为1，指意外；X为2，指疾病
        		001--临时保单意外死亡
        		如：102为意外身故*/
        		strBuffer.append(" and c.getdutycode='BENB50' ");//提取SBENB的公共交通意外身故理赔数据getdutycode='BENB50'
        		//strBuffer.append(" and f.othernotype='5' ");
        		strBuffer.append(" and c.givetype = '0'"); //非拒赔
        		strBuffer.append(" and e.clmstate = '60'");
        		strBuffer.append(" and e.endcasedate between '"+mRIWFLogSchema.getStartDate()+"' and '"+mRIWFLogSchema.getEndDate()+"' ");
        		strBuffer.append(" group by c.caseno, c.polno,AccDate");
        		strBuffer.append(") b");
        strBuffer.append(" where a.conttype='1' ");      
 //       strBuffer.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','||b.clmno||','||b.endcasedate)) ");
   //     strBuffer.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','||b.clmno||','||b.endcasedate)) ");
//      临分的保单
        strBuffer.append(" and exists (select 'X' from ridutystate b where b.proposalno=a.proposalno and state in ('00','03') and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.standbystring1) ) ");
       
        strBuffer.append(" and exists(select 'X' from riaccumulaterdcode r where a.riskcode = r.associatedcode and r.accumulatedefno='"+mAccumulateDefNo+"') ");       
        strBuffer.append(" and a.polno=b.polno ");
        strBuffer.append(")order by GetDate,polno");
        System.out.println("理赔SBENB临分提数sql: "+strBuffer.toString());
        return strBuffer.toString();
    }

    /**
     * getCErrors
     * @return CErrors
     * @todo Implement this
     *   com.sinosoft.lis.reinsure.calculate.distill.RIDistill method
     */
    public CErrors getCErrors() {
        return mErrors;
    }
    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "RIDataMake";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }
    public void test(VData cInputData, String cOperate){
        getInputData( cInputData,  cOperate);
        getDistillSQL();
        getDistillSQL1();   	
    }    
    public static void main(String[] args){
    	VData cInputData = new VData();
    	RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
    	mRIWFLogSchema.setBatchNo("test00001");
    	mRIWFLogSchema.setStartDate("2009-3-1");
    	mRIWFLogSchema.setEndDate("2009-3-31");
    	mRIWFLogSchema.setTaskCode("L000000004");
    	cInputData.add(mRIWFLogSchema);
    	RIDistillCL_SBENB tRIDistillCL_SBENB = new RIDistillCL_SBENB();  	
    	tRIDistillCL_SBENB.test(cInputData, "01");
    }
}
