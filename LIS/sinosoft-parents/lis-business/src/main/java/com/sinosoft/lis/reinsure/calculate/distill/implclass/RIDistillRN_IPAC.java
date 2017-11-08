

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
 * <p>Description: IPA IPAC ADD ADDC IAPTC APTC 再保续期提数类</p>
 * 其中'IPAC11','IPAC12','ADDC11','ADDC12','IAPTC11','APTC11','APTC11'需要真实反映保单年度,只能按lccont中的cvalidate判断续期数据
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author LIJIAN
 * @version 1.0
 */
public class RIDistillRN_IPAC implements RIDistill {
    String mEventType;
    RIWFLogSchema mRIWFLogSchema ;
    String mAccumulateDefNo ;
    private MMap mMap ;
    private VData mInputData;
    private PubSubmit mPubSubmit = new PubSubmit();
    private String startDate ;
    private String endDate ;

    public RIDistillRN_IPAC() {
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
        startDate = mRIWFLogSchema.getStartDate().substring(5);
        endDate = mRIWFLogSchema.getEndDate().substring(5);
        return true;
    }

    private boolean dealData(){
        try{
        	
        	RIPolRecordSet tRIPolRecordSet = new RIPolRecordSet();
            RSWrapper rsWrapper = new RSWrapper();
            //传入结果集合,SQL ,合同分保提数
            String[] tSql = new String[4];
            tSql[0] = getDistillSQL1();
            tSql[1] = getDistillSQL2();
            tSql[2] = getDistillSQL3();
            tSql[3] = getDistillSQL4();
            
            for(int i=0;i<tSql.length;i++){
            	
            	if (!rsWrapper.prepareData(tRIPolRecordSet, tSql[i])){
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
            }
                    


        }catch(Exception ex){
            buildError("dealData", " 提取续期IPAC业务数据失败 "+ex.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * 续期IPAC 合同提数
     * @return
     */
    private String getDistillSQL1(){
    	StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey||substr(GetDate,0,4),GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
        //现价
        strBuffer.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,(InsuredAge+InsuredYear-1) StandbyString5,StandbyDouble1,");
        //年缴化保费
        strBuffer.append("APE(Prem ,PayIntv) ,");
        //准备金
        strBuffer.append("LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),");
        strBuffer.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");
        
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");        
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,StandbyString1,StandbyString2,StandbyString3,StandbyString4,StandbyString5,StandbyDouble1,");       
        strBuffer.append("ceil(months_between(LogDate,CValiDate-1)/12) as InsuredYear,");
        strBuffer.append("Risk_Amnt(payyears,ceil(months_between(LogDate,CValiDate-1)/12),amnt,InsuredAge,years,InsuredSex,prem,riskcode,payendyear,payendyearflag,insuyear,insuyearflag,payintv) as RiskAmnt,");
        strBuffer.append("add_months(CValiDate,ceil(months_between(LogDate,CValiDate-1)/12-1)*12) as GetDate ,");        
        strBuffer.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");

        strBuffer.append(" select ");
        strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
        strBuffer.append(" '" + mRIWFLogSchema.getEndDate() + "' as LogDate, ");
        strBuffer.append(" getSerialNo as EventNo, ");
        strBuffer.append(" '02' as EventType, ");
        strBuffer.append(" '' as RecordType, ");
        strBuffer.append(" '01' as DataFlag, ");//01:个人险种 02：个人险种责任
        strBuffer.append(" a.PolNo||',' as UnionKey, ");//需要考虑这个联合主键的设置polno+","+保单年度
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
        strBuffer.append(" '' as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" (select min(CValiDate) from lcpol b where b.contno = a.contno and a.riskcode = a.riskcode) as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDType, ");
        strBuffer.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
        strBuffer.append(" a.StandPrem as StandPrem, ");
        strBuffer.append(" a.Prem as Prem, ");
        //风险保额
        strBuffer.append(" '' as RiskAmnt, ");
        strBuffer.append(" a.Amnt as Amnt,  ");
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
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate-1)/12)-1,a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as PreRiskAmnt, ");
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
        strBuffer.append(" '01' as NodeState, ");
        strBuffer.append(" '' as ReinsreFlag, ");
        //业务日期按 保单生效周年日 提取
        strBuffer.append(" '' as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        //如果没有健康加费，提数为空的sql
        //strBuffer.append(" (select case when count(*)>0 then decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') else '' end from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
        //如果没有健康加费，提数为A=0
        strBuffer.append(" (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where l.suppriskscore >0 and l.polno=a.polno ) as StandbyString2, ");
        //计划别
        strBuffer.append(" '' as StandbyString3, ");//a.PLANTYPE
        //长短险标志  1:短期险 2：长期险
        strBuffer.append(" (select decode( riskperiod,'M' , '1' , '2') from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");
        //生日
        strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" date '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
        strBuffer.append(" a.managecom as managecom ");
                
        strBuffer.append(" from lcpol a  ");
        strBuffer.append(" where a.conttype='1' and a.appflag=1");        
        //不为临分的保单
        strBuffer.append(" and not exists (select 'X' from ridutystate where  state in ('00','02','03','04') and proposalno=a.proposalno ) ");
        strBuffer.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','||'"+mRIWFLogSchema.getEndDate().substring(0, 4)+"')) ");
        strBuffer.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','||'"+mRIWFLogSchema.getEndDate().substring(0, 4)+"')) ");       
        //有可能是续保，全部从LCCONT中取，除新增附约
        strBuffer.append(" and not exists (select 'X' from lpedoritem c ,lppol d where c.edortype = 'NS' and c.edorstate = '0' and c.edortype=d.edortype and c.edorno = d.edorno and c.contno = a.contno and d.proposalno = a.proposalno)");       
        strBuffer.append(" and exists(select 'X' from riaccumulaterdcode r where  a.riskcode = r.associatedcode and r.accumulatedefno='"+mAccumulateDefNo+"') ");
        /**当失效(statetype ='Available' and state='1')日期(lccont.startdate)在
         * 续期日期(add_months(a.cvalidate,floor(decode(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate),0,1,months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate))/12)*12))之前
         * 且一直为失效状态(lccontstate.enddate is null)，说明保单在提数的上一个缴费期后就已经失效，这时不提续期数据，
         * 如果失效日期在续期日期之后或失效状态已经结束这时可以提续期数据
         * */ 
        strBuffer.append(" and not exists (select 'X' from lccontstate where polno =a.polno and enddate is null and startdate<add_months(a.cvalidate,(ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate-1)/12)-1)*12) and state='1' and statetype ='Available' )");
        strBuffer.append(" and to_char(a.cvalidate,'MM-DD') between '"+startDate+"' and '"+endDate+"' ");  
        strBuffer.append(")");
        strBuffer.append(")where InsuredYear > 1  order by GetDate,polno");
        System.out.println("续期IPAC合同分保提数sql: "+strBuffer.toString());
        return strBuffer.toString();
    }
    
    /**
     * 续期IPAC 合同提数(新增附约)
     * @return
     */
    private String getDistillSQL2(){
    	
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey||substr(GetDate,0,4),GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
        //现价
        strBuffer.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,(InsuredAge+InsuredYear-1) StandbyString5,StandbyDouble1,");
        //年缴化保费
        strBuffer.append("APE(Prem ,PayIntv) ,");
        //准备金
        strBuffer.append("LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),");
        strBuffer.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");
        
        strBuffer.append(" select ");
        strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
        strBuffer.append(" getSerialNo as EventNo, ");
        strBuffer.append(" '02' as EventType, ");
        strBuffer.append(" '' as RecordType, ");
        strBuffer.append(" '01' as DataFlag, ");//01:个人险种 02：个人险种责任
        strBuffer.append(" a.PolNo||',' as UnionKey, ");//需要考虑这个联合主键的设置polno+","+保单年度
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
        strBuffer.append(" ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',b.edorvalidate-1)/12) as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" b.edorvalidate as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno ) as IDType, ");
        strBuffer.append(" (select b.IDNo from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
        strBuffer.append(" a.StandPrem as StandPrem, ");
        strBuffer.append(" a.Prem as Prem, ");
        //风险保额
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',b.edorvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
        strBuffer.append(" a.Amnt as Amnt,  ");
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
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate-1)/12)-1,a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as PreRiskAmnt, ");
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
        strBuffer.append(" '01' as NodeState, ");
        strBuffer.append(" '' as ReinsreFlag, ");
        //业务日期
        strBuffer.append(" add_months(b.edorvalidate,ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',b.edorvalidate-1)/12-1)*12) as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        //如果没有健康加费，提数为空的sql
        //strBuffer.append(" (select case when count(*)>0 then decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') else '' end from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
        //如果没有健康加费，提数为A=0
        strBuffer.append(" (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where  l.suppriskscore >0 and l.polno=a.polno ) as StandbyString2, ");
        //计划别
        strBuffer.append(" '' as StandbyString3, ");//a.PLANTYPE
        //长短险标志  1:短期险 2：长期险
        strBuffer.append(" (select decode( riskperiod,'M' , '1' , '2' ) from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");       
        //生日
        strBuffer.append(" a.insuredbirthday as StandbyDate1,");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" date '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
        strBuffer.append(" a.managecom as managecom ");
               
        strBuffer.append(" from lcpol a,lpedoritem b ");
        strBuffer.append(" where a.conttype='1' and a.appflag=1");       
        /**当失效(statetype ='Available' and state='1')日期(lccont.startdate)在
         * 续期日期(add_months(a.cvalidate,floor(decode(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate),0,1,months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate))/12)*12))之前
         * 且一直为失效状态(lccontstate.enddate is null)，说明保单在提数的上一个缴费期后就已经失效，这时不提续期数据，
         * 如果失效日期在续期日期之后或失效状态已经结束这时可以提续期数据
         * */  
        strBuffer.append(" and not exists (select 'X' from lccontstate where polno =a.polno and enddate is null and startdate<add_months(b.edorvalidate,(ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',b.edorvalidate-1)/12)-1)*12) and state='1' and statetype ='Available' )");
        strBuffer.append(" and months_between('"+mRIWFLogSchema.getEndDate()+"', b.edorvalidate-1) >12");   
        //不为临分的保单
        strBuffer.append(" and not exists (select 'X' from ridutystate where  state in ('00','02','03','04') and proposalno=a.proposalno ) ");
        strBuffer.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','||'"+mRIWFLogSchema.getEndDate().substring(0, 4)+"')) ");
        strBuffer.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','||'"+mRIWFLogSchema.getEndDate().substring(0, 4)+"')) ");                                    
        
        strBuffer.append(" and exists(select 'X' from riaccumulaterdcode r where  a.riskcode = r.associatedcode and r.accumulatedefno='"+mAccumulateDefNo+"') ");        
        strBuffer.append(" and to_char(b.edorvalidate,'MM-DD') between '"+startDate+"' and '"+endDate+"' ");
        //	新增附约 
        strBuffer.append(" and exists( select 'X' from lppol c where  c.edortype=b.edortype and c.edorno = b.edorno and c.contno = b.contno and c.proposalno = a.proposalno)");
        strBuffer.append(" and b.edortype = 'NS' and b.edorstate = '0'");
        strBuffer.append(" and a.contno=b.contno ");
        strBuffer.append(")");
        strBuffer.append("  order by GetDate,polno");
        System.out.println("续期IPAC合同分保提数(新增附约)sql: "+strBuffer.toString());
        return strBuffer.toString();
    }
    
    /**
     * 续期IPAC 临分提数
     * @return
     */
    private String getDistillSQL3(){
    	StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey||substr(GetDate,0,4),GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
        //现价
        strBuffer.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,(InsuredAge+InsuredYear-1) StandbyString5,StandbyDouble1,");
        //年缴化保费
        strBuffer.append("APE(Prem ,PayIntv) ,");
        //准备金
        strBuffer.append("LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),");
        strBuffer.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");
        
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");        
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,StandbyString1,StandbyString2,StandbyString3,StandbyString4,StandbyString5,StandbyDouble1,");       
        strBuffer.append("ceil(months_between(LogDate,CValiDate-1)/12) as InsuredYear,");
        strBuffer.append("Risk_Amnt(payyears,ceil(months_between(LogDate,CValiDate-1)/12),amnt,InsuredAge,years,InsuredSex,prem,riskcode,payendyear,payendyearflag,insuyear,insuyearflag,payintv) as RiskAmnt,");
        strBuffer.append("add_months(CValiDate,ceil(months_between(LogDate,CValiDate-1)/12-1)*12) as GetDate ,");        
        strBuffer.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");

        
        strBuffer.append(" select ");
        strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
        strBuffer.append(" '" + mRIWFLogSchema.getEndDate() + "' as LogDate, ");
        strBuffer.append(" getSerialNo as EventNo, ");
        strBuffer.append(" '02' as EventType, ");
        strBuffer.append(" '' as RecordType, ");
        strBuffer.append(" '01' as DataFlag, ");//01:个人险种 02：个人险种责任
        strBuffer.append(" a.PolNo||',' as UnionKey, ");//需要考虑这个联合主键的设置polno+","+保单年度
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
        strBuffer.append(" '' as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" (select min(CValiDate) from lcpol b where b.contno = a.contno and a.riskcode = a.riskcode) as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LCInsured b where b.insuredno = a.insuredno and b.contno = a.contno) as IDType, ");
        strBuffer.append(" (select b.IDNo from LCInsured b where b.insuredno = a.insuredno and b.contno = a.contno) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where b.insuredno=a.insuredno and b.contno=a.contno) as OccupationCode , ");
        strBuffer.append(" a.StandPrem as StandPrem, ");
        strBuffer.append(" a.Prem as Prem, ");
        //风险保额
        strBuffer.append(" '' as RiskAmnt, ");
        strBuffer.append(" a.Amnt as Amnt,  ");
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
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate-1)/12)-1,a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as PreRiskAmnt, ");
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
        strBuffer.append(" (select RIPreceptNo from RITempContLink where proposalno=a.proposalno ) as RIPreceptNo, ");
        strBuffer.append(" '01' as NodeState, ");
        strBuffer.append(" (select max(State) from ridutystate where proposalno=a.proposalno) as ReinsreFlag, ");
        //业务日期
        strBuffer.append(" '' as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        // 如果没有健康加费，提数为空的sql
        //strBuffer.append(" (select case when count(*)>0 then decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') else '' end from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
        //如果没有健康加费，提数为A=0
        strBuffer.append(" (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where l.suppriskscore >0 and l.polno=a.polno ) as StandbyString2, ");
        //计划别
        strBuffer.append(" '' as StandbyString3, ");//a.PLANTYPE
        //长短险标志  1:短期险 2：长期险
        strBuffer.append(" (select decode( riskperiod,'M' , '1' , '2' ) from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");
        //生日
        strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" date '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
        strBuffer.append(" a.managecom as managecom ");
       
        strBuffer.append(" from lcpol a  ");
        strBuffer.append(" where a.conttype='1' and a.appflag=1");
        
        // strBuffer.append(" and exists (select * from ridutystate where proposalno=a.proposalno and dutycode=a.riskcode and state in ('04','03','02')) ");
        strBuffer.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','||'"+mRIWFLogSchema.getEndDate().substring(0, 4)+"')) ");
        strBuffer.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','||'"+mRIWFLogSchema.getEndDate().substring(0, 4)+"')) ");
        strBuffer.append(" and exists (select 'X' from ridutystate b where b.proposalno=a.proposalno and state in ('00','03') and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.standbystring1 ) ) ");      
        // 有可能是续保，全部从LCCONT中取，除新增附约
        strBuffer.append(" and not exists (select 'X' from lpedoritem c ,lppol d where c.edortype = 'NS' and c.edorstate = '0' and c.edortype=d.edortype and c.edorno = d.edorno and c.contno = a.contno and d.proposalno = a.proposalno)");       
        strBuffer.append(" and to_char(a.cvalidate,'MM-DD') between '"+startDate+"' and '"+endDate+"' ");
        strBuffer.append(" and not exists (select 'X' from lccontstate where polno =a.polno and enddate is null and startdate<add_months(a.cvalidate,(ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate-1)/12)-1)*12) and state='1' and statetype ='Available' )");
        strBuffer.append(" and exists(select 'X' from riaccumulaterdcode r where  a.riskcode = r.associatedcode and r.accumulatedefno='"+mAccumulateDefNo+"') ");
        strBuffer.append(")");
        strBuffer.append(")where InsuredYear > 1  order by GetDate,polno");
        System.out.println("续期IPAC临分提数sql: "+strBuffer.toString());
        return strBuffer.toString();
    }
    
    /**
     * 续期IPAC 临分提数(新增附约)
     * @return
     */
    private String getDistillSQL4(){
    	
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey||substr(GetDate,0,4),GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
        //现价
        strBuffer.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,(InsuredAge+InsuredYear-1) StandbyString5,StandbyDouble1,");
        //年缴化保费
        strBuffer.append("APE(Prem ,PayIntv) ,");
        //准备金
        strBuffer.append("LastYear_PrepareMoney(RiskCode,InsuredSex,InsuredAge,InsuredYear,PayEndYear,PayEndYearFlag,InsuYear,InsuYearFlag,PayIntv,Amnt),");
        strBuffer.append("StandbyDate1,StandbyDate2,MakeDate,MakeTime,ManageCom from( ");
                      
        strBuffer.append(" select ");
        strBuffer.append(" '" + mRIWFLogSchema.getBatchNo() + "' as BatchNo, ");
        strBuffer.append(" getSerialNo as EventNo, ");
        strBuffer.append(" '02' as EventType, ");
        strBuffer.append(" '' as RecordType, ");
        strBuffer.append(" '01' as DataFlag, ");//01:个人险种 02：个人险种责任
        strBuffer.append(" a.PolNo||',' as UnionKey, ");//需要考虑这个联合主键的设置polno+","+保单年度
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
        strBuffer.append(" ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',b.edorvalidate-1)/12) as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" b.edorvalidate as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LCInsured b where b.insuredno = a.insuredno and b.contno = a.contno) as IDType, ");
        strBuffer.append(" (select b.IDNo from LCInsured b where b.insuredno = a.insuredno and b.contno = a.contno) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where b.insuredno = a.insuredno and b.contno = a.contno) as OccupationCode , ");
        strBuffer.append(" a.StandPrem as StandPrem, ");
        strBuffer.append(" a.Prem as Prem, ");
        //风险保额
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',b.edorvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
        strBuffer.append(" a.Amnt as Amnt,  ");
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
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate-1)/12)-1,a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as PreRiskAmnt, ");
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
        strBuffer.append(" (select RIPreceptNo from RITempContLink where proposalno=a.proposalno ) as RIPreceptNo, ");
        strBuffer.append(" '01' as NodeState, ");
        strBuffer.append(" (select max(State) from ridutystate where proposalno=a.proposalno) as ReinsreFlag, ");
        //业务日期
        strBuffer.append(" add_months(b.edorvalidate,ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',b.edorvalidate-1)/12-1)*12) as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        //如果没有健康加费，提数为空的sql
        //strBuffer.append(" (select case when count(*)>0 then decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') else '' end from lcprem l where l.polno=a.polno and l.suppriskscore <>0 ) as StandbyString2, ");
        //如果没有健康加费，提数为A=0
        strBuffer.append(" (select decode(nvl(max(suppriskscore),0),0,'A',50,'B',75,'C',100,'D',125,'E',150,'F',200,'H',250,'J',300,'L',350,'N',400,'P') from lcprem l where  l.suppriskscore >0 and l.polno=a.polno ) as StandbyString2, ");
        //计划别
        strBuffer.append(" '' as StandbyString3, "); //a.PLANTYPE
        //长短险标志  1:短期险 2：长期险
        strBuffer.append(" (select decode( riskperiod,'M' , '1' , '2' ) from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");
        //生日
        strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" date '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
        strBuffer.append(" a.managecom as managecom ");
         
        strBuffer.append(" from lcpol a,lpedoritem b ");        
        strBuffer.append(" where a.conttype='1' and a.appflag=1");      
        strBuffer.append(" and to_char(b.edorvalidate,'MM-DD') between '"+startDate+"' and '"+endDate+"' ");
        strBuffer.append(" and months_between('"+mRIWFLogSchema.getEndDate()+"', b.edorvalidate-1) >12 ");
        strBuffer.append(" and not exists (Select 'X' From RIPolRecord m where m.unionkey=(a.PolNo||','||'"+mRIWFLogSchema.getEndDate().substring(0, 4)+"')) ");
        strBuffer.append(" and not exists (Select 'X' From RIPolRecordBake n where n.unionkey=(a.PolNo||','||'"+mRIWFLogSchema.getEndDate().substring(0, 4)+"')) ");
        //新增附约 
        strBuffer.append(" and exists( select 'X' from lppol c where  c.edortype=b.edortype and c.edorno = b.edorno and c.contno = b.contno and c.proposalno = a.proposalno)");
        strBuffer.append(" and b.edortype = 'NS' and b.edorstate = '0'");
        strBuffer.append(" and a.contno=b.contno ");       
        // 临分的保单
        strBuffer.append(" and exists (select 'X' from ridutystate b where b.proposalno=a.proposalno and state in ('00','03') and exists (select 'X' from RIGrpState c where c.state='04' and c.serialno = b.standbystring1 ) ) ");                     
        strBuffer.append(" and not exists (select 'X' from lccontstate where polno =a.polno and enddate is null and startdate<add_months(a.cvalidate,(ceil(months_between('"+mRIWFLogSchema.getEndDate()+"',a.cvalidate-1)/12)-1)*12) and state='1' and statetype ='Available' )");
        strBuffer.append(" and exists (select 'X' from riaccumulaterdcode r where  r.associatedcode = a.riskcode and r.accumulatedefno ='"+mAccumulateDefNo+"') ");        
        strBuffer.append(")");
        strBuffer.append(" order by GetDate,polno");
        System.out.println("续期IPAC临分提数(新增附约)sql: "+strBuffer.toString());
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
    public static void main(String[] args){
    	VData cInputData = new VData();
    	RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
    	mRIWFLogSchema.setBatchNo("test00001");
    	mRIWFLogSchema.setStartDate("2008-10-1");
    	mRIWFLogSchema.setEndDate("2008-10-31");
    	mRIWFLogSchema.setTaskCode("L000000007");
    	cInputData.add(mRIWFLogSchema);
    	RIDistillRN_IPAC tRIDistillRN_IPAC = new RIDistillRN_IPAC();  	
    	tRIDistillRN_IPAC.submitData(cInputData,"01");
    }
}
