

package com.sinosoft.lis.reinsure.test;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.reinsure.calculate.distill.RIDistill;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description:再保理赔通过可参考提数程序 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author LIJIAN
 * @version 1.0
 */
public class RIDistillCL implements RIDistill {
    String mEventType;
    RIWFLogSchema mRIWFLogSchema ;
    String mAccumulateDefNo ;
    /** 数据批次处理限制数 */
    private int mMaxDealNUm = 5000;

    private String tSql;

    public RIDistillCL() {
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
        	ExeSQL exeSQL = new ExeSQL();
        	//合同分保提数
            tSql = getDistillSQL();
            while (haveDataUnsettled()) {
            	String sql = " insert into RIPolRecord (select * from (" + tSql + "))";
                if (!exeSQL.execUpdateSQL(sql)) {
                    buildError("dealData", " 提取理赔业务数据失败 ");
                    return false;
                }
            }
            //临时分保提数
            tSql = getDistillSQL1();
            while (haveDataUnsettled()) {
            	String sql = " insert into RIPolRecord (select * from (" + tSql + "))";
                if (!exeSQL.execUpdateSQL(sql)) {
                    buildError("dealData", " 提取理赔业务数据失败 ");
                    return false;
                }
            }
            if(this.mErrors.needDealError()){
                return false;
            }
        }catch(Exception ex){
            buildError("dealData", " 提取理赔业务数据失败 "+ex.getMessage());
            return false;
        }
        return true;
    }

    private String getDistillSQL(){
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
        //现价
        strBuffer.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,StandbyString5,");
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
        strBuffer.append(" trim(a.PolNo) || ','||trim(b.clmno)||','||b.confdate as UnionKey, ");
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
        strBuffer.append(" ceil(months_between(b.confdate,a.cvalidate-1)/12) as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" a.CValiDate as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as IDType, ");
        strBuffer.append(" (select b.IDNo from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as OccupationCode , ");
        //追朔出险时点的保费
        strBuffer.append(" (select case when (select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) is null then a.StandPrem else (select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) end from dual ) as StandPrem, ");
        strBuffer.append(" (select case when (select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) is null then a.prem else (select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) end from dual ) as Prem, ");
        //风险保额在理赔的时候没有实际意义所以用什么时点的保额计算不重要
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(months_between(b.confdate,a.cvalidate-1)/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
        //追朔出险时点的保额
        strBuffer.append(" (select case when (select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) is null then a.amnt else (select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) end from dual ) as Amnt,  ");
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
        strBuffer.append(" b.confdate as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        strBuffer.append(" null as StandbyString2, ");
        strBuffer.append(" null as StandbyString3, ");
//      长短险标志  1:短期险 2：长期险
        strBuffer.append(" (select case when riskperiod='M' then '1' else '2' end from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");
        //生日
        strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
        strBuffer.append(" a.managecom as managecom ");
        strBuffer.append(" from lcpol a,(  ");
        		strBuffer.append(" select c.caseno clmno,");//分案号                     *****************
        		strBuffer.append(" c.polno polno,");
        		strBuffer.append(" max(e.endcasedate) endcasedate,");//结案日期
        		strBuffer.append(" sum(c.standpay) standpay,");//核算赔付金额        *****************
        		strBuffer.append(" sum(c.realpay) realpay,");//核赔赔付金额         *****************
        		strBuffer.append(" max(d.AccDate) AccDate,");//出险日期  AccStartDate//出险开始日期  *************
        		strBuffer.append(" max(c.getdutykind) getdutykind,");//付责任类型 100意外医疗101意外伤残102意外死亡103意外高残104意外大病105意外特种疾病106意外失业失能109意外豁免200疾病医疗201疾病伤残
        		strBuffer.append(" max(confdate)   confdate ");    //理赔核销日期                  ****************
        		strBuffer.append(" from LLClaimDetail c, LLcase d, LLClaim e,ljaget f ");
        		strBuffer.append(" where c.CaseNo = d.caseno and c.caseno = e.caseno and f.otherno=c.caseno ");
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
        		//strBuffer.append(" and c.getdutycode='' and c.getdutykind='' and c.dutycode='' ");
        		strBuffer.append(" and f.othernotype='5' ");
        		strBuffer.append(" and f.confDate between '"+mRIWFLogSchema.getStartDate()+"' and '"+mRIWFLogSchema.getEndDate()+"' ");
        		strBuffer.append(" group by c.caseno, c.polno");
        		strBuffer.append(") b");
        strBuffer.append(" where a.conttype='1' and a.polno=b.polno ");
//        String tsql="select distinct trim(c.associatedcode) from riaccumulaterdcode c where trim(c.accumulatedefno)='"+mAccumulateDefNo+"' ";
//
//        ExeSQL tExeSQL=new ExeSQL();
//        SSRS tSSRS=tExeSQL.execSQL(tsql);
//        if(tSSRS==null){
//        	buildError("initInfo", " 业务数据提取时出错，查询累计风险关联的险种失败。" );
//        	return null;
//        }
//        else{
//        	for(int i=1;i<=tSSRS.getMaxRow();i++){
//            	if(i==1){
//            		strBuffer.append(" and (");
//            	}
//            	strBuffer.append("trim(a.riskcode) like '"+tSSRS.GetText(i, 1)+"%' ");
//            	if(i<tSSRS.getMaxRow()){
//            		strBuffer.append(" or ");
//            	}
//            	if(i==tSSRS.getMaxRow()){
//            		 strBuffer.append(")");
//            	}
//
//            }
//        }

        strBuffer.append(" and trim(a.riskcode) in (select distinct trim(r.associatedcode) from riaccumulaterdcode r where trim(r.accumulatedefno)='"+mAccumulateDefNo+"') ");

//      不为临分的保单
        strBuffer.append(" and not exists (select * from ridutystate where proposalno=a.proposalno and state in ('00','02','03','04')) ");
        strBuffer.append(" and not exists (Select * From RIPolRecord m where m.unionkey=(trim(a.PolNo)||','||b.clmno||','||b.confdate)) ");
        strBuffer.append(" and not exists (Select * From RIPolRecordBake n where n.unionkey=(trim(a.PolNo)||','||b.clmno||','||b.confdate)) ");

//      strBuffer.append(" and not exists (select * from RITempContLink where proposalno=a.proposalno )");
        strBuffer.append(" and rownum <=" + mMaxDealNUm+" order by GetDate,polno");
        strBuffer.append(")");
        System.out.println("理赔提数sql: "+strBuffer.toString());
        return strBuffer.toString();
    }

    private String getDistillSQL1(){
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("select BatchNo,EventNo,EventType,RecordType,DataFlag,UnionKey,GrpContNo,ProposalGrpContNo,GrpPolNo,GrpProposalNo,ContNo,PolNo,ProposalNo,ContPlanCode,RiskCode,DutyCode,Years,InsuredYear,SaleChnl,CValiDate,EndDate,InsuredNo,InsuredName,InsuredSex,InsuredAge,IDType,IDNo,OccupationType,OccupationCode,StandPrem,Prem,RiskAmnt,Amnt,Mult,PayIntv,PayYears,PayEndYearFlag,PayEndYear,GetYearFlag,GetYear,InsuYearFlag,InsuYear,AcciYearFlag,AcciYear,AcciEndDate,GetStartDate,GetStartType,");
        //现价
        strBuffer.append("Cv_riskcode(RiskCode,PayEndYear,insuredsex,InsuredAge,InsuredYear,Amnt),");
        strBuffer.append("GetLimit,GetIntv,PayNo,PayCount,PayMoney,LastPayToDate,CurPayToDate,EdorNo,FeeOperationType,FeeFinaType,AccAmnt,PreStandPrem,PrePrem,PreRiskAmnt,PreAmnt,ClmNo,ClmFeeOperationType,ClmFeeFinaType,StandGetMoney,GetRate,ClmGetMoney,AccDate,AccumulateDefNO,RIContNo,RIPreceptNo,NodeState,ReinsreFlag,GetDate,StandbyString1,StandbyString2,StandbyString3,StandbyString4,StandbyString5,");
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
        strBuffer.append(" trim(a.PolNo) || ','||trim(b.clmno)||','||b.confdate as UnionKey, ");
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
        strBuffer.append(" ceil(months_between(b.confdate,a.cvalidate-1)/12) as InsuredYear, ");
        strBuffer.append(" a.SaleChnl as SaleChnl, ");
        strBuffer.append(" a.CValiDate as CValiDate, ");
        strBuffer.append(" a.EndDate as EndDate, ");
        strBuffer.append(" a.InsuredNo as InsuredNo, ");
        strBuffer.append(" a.InsuredName as InsuredName, ");
        strBuffer.append(" a.InsuredSex as InsuredSex, ");
        strBuffer.append(" a.InsuredAppAge as InsuredAge, ");
        strBuffer.append(" (select b.IDType from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as IDType, ");
        strBuffer.append(" (select b.IDNo from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as IDNo, ");
        strBuffer.append(" a.OccupationType as OccupationType, ");
        strBuffer.append(" (select b.OccupationCode from LCInsured b where trim(b.insuredno)=trim(a.insuredno) and trim(b.contno)=trim(a.contno)) as OccupationCode , ");
        //追朔出险时点的保费
        strBuffer.append(" (select case when (select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) is null then a.StandPrem else (select StandPrem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) end from dual ) as StandPrem, ");
        strBuffer.append(" (select case when (select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) is null then a.prem else (select prem from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) end from dual ) as Prem, ");
        //风险保额在理赔的时候没有实际意义所以用什么时点的保额计算不重要
        strBuffer.append(" Risk_Amnt(a.payyears,ceil(decode(months_between(b.confdate,a.cvalidate),0,1,months_between(b.confdate,a.cvalidate))/12),a.amnt,a.InsuredAppAge,a.years,a.InsuredSex,a.prem,a.riskcode,a.payendyear,a.payendyearflag,a.insuyear,a.insuyearflag,a.payintv) as RiskAmnt, ");
        //追朔出险时点的保额
        strBuffer.append(" (select case when (select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) is null then a.amnt else (select amnt from lppol where edorno =(select edorno from lpedoritem l where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=l.edorno and polno=a.polno) and edorvalidate=(select least(edorvalidate) from lpedoritem m where edorstate='0' and contno=a.contno and insuredno=a.insuredno and exists(select 'X' from lppol where edorno=m.edorno and polno=a.polno) and edorvalidate>=b.accdate)) and polno=a.polno) end from dual ) as Amnt,  ");
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
        strBuffer.append(" b.confdate as GetDate, ");
        strBuffer.append(" null as StandbyString1, ");
        strBuffer.append(" null as StandbyString2, ");
        strBuffer.append(" null as StandbyString3, ");
//      长短险标志  1:短期险 2：长期险
        strBuffer.append(" (select case when riskperiod='M' then '1' else '2' end from lmriskapp where riskcode=a.riskcode) as StandbyString4, ");
        strBuffer.append(" null as StandbyString5, ");
        strBuffer.append(" 0 as StandbyDouble1, ");
        strBuffer.append(" 0 as StandbyDouble2, ");
        strBuffer.append(" 0 as StandbyDouble3, ");
        //生日
        strBuffer.append(" a.insuredbirthday as StandbyDate1, ");
        strBuffer.append(" null as StandbyDate2, ");
        strBuffer.append(" '" + PubFun.getCurrentDate() + "' as MakeDate, ");
        strBuffer.append(" '" + PubFun.getCurrentTime() + "' as MakeTime, ");
        strBuffer.append(" a.managecom as managecom ");
        strBuffer.append(" from lcpol a,(  ");
        		strBuffer.append(" select c.caseno clmno,");//分案号                     *****************
        		strBuffer.append(" c.polno polno,");
        		strBuffer.append(" max(e.endcasedate) endcasedate,");//结案日期
        		strBuffer.append(" sum(c.standpay) standpay,");//核算赔付金额        *****************
        		strBuffer.append(" sum(c.realpay) realpay,");//核赔赔付金额         *****************
        		strBuffer.append(" max(d.AccDate) AccDate,");//出险日期  AccStartDate//出险开始日期  *************
        		strBuffer.append(" max(c.getdutykind) getdutykind,");//付责任类型 100意外医疗101意外伤残102意外死亡103意外高残104意外大病105意外特种疾病106意外失业失能109意外豁免200疾病医疗201疾病伤残
        		strBuffer.append(" max(confdate)   confdate ");    //理赔核销日期                  ****************
        		strBuffer.append(" from LLClaimDetail c, LLcase d, LLClaim e,ljaget f ");
        		strBuffer.append(" where c.CaseNo = d.caseno and c.caseno = e.caseno and f.otherno=c.caseno ");
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
        		//strBuffer.append(" and c.getdutycode='' and c.getdutykind='' and c.dutycode='' ");
        		strBuffer.append(" and f.othernotype='5' ");
        		strBuffer.append(" and f.confDate between '"+mRIWFLogSchema.getStartDate()+"' and '"+mRIWFLogSchema.getEndDate()+"' ");
        		strBuffer.append(" group by c.caseno, c.polno");
        		strBuffer.append(") b");
        strBuffer.append(" where a.conttype='1' and a.polno=b.polno ");
        strBuffer.append(" and trim(a.riskcode) in (select distinct trim(r.associatedcode) from riaccumulaterdcode r where trim(r.accumulatedefno)='"+mAccumulateDefNo+"') ");
        strBuffer.append(" and not exists (Select * From RIPolRecord m where m.unionkey=(trim(a.PolNo)||','||b.clmno||','||b.confdate)) ");
        strBuffer.append(" and not exists (Select * From RIPolRecordBake n where n.unionkey=(trim(a.PolNo)||','||b.clmno||','||b.confdate)) ");
//      临分的保单
        strBuffer.append(" and exists (select * from ridutystate b where b.proposalno=a.proposalno and state in ('00','03') and exists (select * from RIGrpState c where trim(c.serialno)=trim(b.standbystring1) and c.state='04') ) ");
        strBuffer.append(" and rownum <=" + mMaxDealNUm+" order by GetDate,polno");
        strBuffer.append(")");
        System.out.println("理赔提数sql: "+strBuffer.toString());
        return strBuffer.toString();
    }

    /**
     * haveDataUnsettled
     * @return boolean
     */
    public boolean haveDataUnsettled() {
        try{
            String sql = "select count(*) from (" + tSql + ")";
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
    	mRIWFLogSchema.setTaskCode("L000000001");
    	cInputData.add(mRIWFLogSchema);
    	RIDistillCL tRIDistillCL = new RIDistillCL();  	
    	tRIDistillCL.test(cInputData, "01");
    }
}
