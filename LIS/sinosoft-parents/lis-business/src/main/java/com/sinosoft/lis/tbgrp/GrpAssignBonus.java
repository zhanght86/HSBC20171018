package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOBonusAssignGrpErrLogDB;
import com.sinosoft.lis.db.LOBonusGrpPolDB;
import com.sinosoft.lis.db.LOBonusGrpPolParmDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LSRunDataInfoDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOBonusGrpPolParmSchema;
import com.sinosoft.lis.schema.LOBonusGrpPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LSRunDataInfoSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LOBonusGrpPolSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: 团体分红</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class GrpAssignBonus implements BusinessService
{
private static Logger logger = Logger.getLogger(GrpAssignBonus.class);

    public CErrors mErrors=new CErrors();

    private int COUNT = 50000;

    private String mFiscalYear = "";
    private String mGrpPolNo = "";
    private String mRiskCode = "";
    private String mBDate = "";
    private String mEDate = "";
    private String mContNo = "";
    private GlobalInput mGlobalInput = new GlobalInput();
    private VData mInputData;
    private MMap map = new MMap();
    private String mGrpContNo ="";
//  统一时间
	String mNowDate = PubFun.getCurrentDate();
	String mNowTime = PubFun.getCurrentTime();


    public GrpAssignBonus()
    {
    }
    public static void main(String[] args)
    {
        GlobalInput tGlobalInput=new GlobalInput();
        tGlobalInput.ComCode = "86";
        tGlobalInput.ManageCom = "86";
        tGlobalInput.Operator = "DEBUG";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("FiscalYear","2004");
        tTransferData.setNameAndValue("GrpPolNo","86110020040220000376");
        tTransferData.setNameAndValue("RiskCode","212401");
        tTransferData.setNameAndValue("BDate","");
        tTransferData.setNameAndValue("EDate","");
        tTransferData.setNameAndValue("PolNo","86110020040210023360");

        VData cInputData = new VData();
        cInputData.add(tGlobalInput);
        cInputData.add(tTransferData);

        GrpAssignBonus tGrpAssignBonus = new GrpAssignBonus();
        logger.debug(tGrpAssignBonus.submitData(cInputData,"CALC||PART"));
    }

    /**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LS0002")) {
			return false;
		}
		return true;
	}
    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        boolean retFlag = true;
        logger.debug("=== GrpAssignBonus begin ===");

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
            return false;

        try {
			//红利计算加锁
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(mGrpContNo);
			tLCGrpPolDB.setAppFlag("1");
			LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
			if(tLCGrpPolSet == null || tLCGrpPolSet.size()<1){
				CError.buildErr(this, "查询团体保单表错误！");
				return false;
			}else{
//        	准备锁表
				if (!lockNo(mGrpContNo)) {
					logger.debug("锁定号码失败!");
					this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
					//mPubLock.unLock();
					return false;
				}
			}
			//进行业务处理
			if(cOperate.equals("CALC||ALL"))
			    retFlag = runCalBonus();
			else if(cOperate.equals("ASSIGN||ALL"))
			    retFlag = runAssignBonus();
			else if(cOperate.equals("CALC||PART"))
			    retFlag = runCalPartGrp();
			else if(cOperate.equals("ASSIGN||PART"))
			    retFlag = runAssignPerson();
			else
			{
			    CError.buildErr(this,"不支持的操作类型 ： " + cOperate);
			    retFlag = false;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			mPubLock.unLock();
		}

        return retFlag;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        mGlobalInput = ((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        TransferData tTransferData = ((TransferData)cInputData.getObjectByObjectName("TransferData",0));
        if (mGlobalInput == null || tTransferData == null)
        {
            CError.buildErr(this, "传入数据缺失！");
            return false;
        }

        mFiscalYear = (String)tTransferData.getValueByName("FiscalYear");
        mGrpContNo = (String)tTransferData.getValueByName("GrpContNo");
        mRiskCode = (String)tTransferData.getValueByName("RiskCode");
        mBDate = (String)tTransferData.getValueByName("BDate");
        mEDate = (String)tTransferData.getValueByName("EDate");
        mContNo = (String)tTransferData.getValueByName("ContNo");
        if(mFiscalYear.equals("") || mRiskCode.equals(""))
        {
            CError.buildErr(this, "会计年度和险种编码为空！");
            return false;
        }
        return true;
    }

    /**
     * 前台传递团体保单号，小批量处理
     * @param cInputData
     * @return
     */
    private boolean runCalBonus()
    {
        String tLimit = PubFun.getNoLimit( mGlobalInput.ManageCom);
        String tSerialNo = PubFun1.CreateMaxNo( "SERIALNO", tLimit );//产生流水号码
        mGrpPolNo=GetGrpPolNo(mGrpContNo);
        boolean retFlag = true;

        String sql = "select grppolno from lobonusgrppolparm a "
                   + "where fiscalyear = '" + mFiscalYear + "' and riskcode = '" + mRiskCode + "' "
                   + "and computestate in ('0','3','4') "     //必须只能是已设置状态和团单下个单已计算和分配的才能修改，已经团单计算和分配的均不能再修改
                   + ReportPubFun.getWherePart("grppolno",mGrpPolNo)
                   //排除已经没有有效保单的团单
                   + " and exists (select '1' from lcpol where grppolno = a.grppolno and appflag = '1')"
                   + " and exists(select 1 from lcgrppol where grppolno = a.grppolno and appflag = '1' "
//                   + "and cvalidate <= '" + mFiscalYear + "-12-31' and payenddate >= '" + mFiscalYear + "-12-31' "
                   + ReportPubFun.getWherePart("cvalidate",mBDate,mEDate,1)
                   + " ) order by grppolno ";
        logger.debug(sql);
        SSRS tssrs = new ExeSQL().execSQL(sql);
        for(int i = 1 ; i <= tssrs.getMaxRow() ; i++)
        {
            String tGrpPolNo = tssrs.GetText(i,1);
            insertOperLog(tSerialNo,tGrpPolNo,tGrpPolNo,"CalAllGrp","批量计算团单" + tGrpPolNo + "的红利");
            if(!CalOneGrpPol(tGrpPolNo))
            {
                insertErrLog(tSerialNo,mGrpContNo,tGrpPolNo,mErrors.getFirstError(),"1");
                retFlag = false;
                mErrors.clearErrors();
            }
        }
        return retFlag;
    }

    private String GetGrpPolNo(String tGrpContNo){
    	String tGrpPolNo="";
        if(tGrpContNo != null && !tGrpContNo.equals(""))
        {
            
        	String tSql = "select grppolno from lcgrppol where grpcontno = '"+tGrpContNo+"' and appflag = '1' and exists (select 1 from lmrisk where InsuAccFlag = 'Y' and riskcode = lcgrppol.riskcode)";
        	
        	ExeSQL tExeSQL = new ExeSQL();
        	tGrpPolNo = tExeSQL.getOneValue(tSql);
        	if (tGrpPolNo == null || tGrpPolNo.equals("")) {
        		tGrpPolNo = "";
        	}
        }
        return tGrpPolNo;
    }
    
    private String GetPolNo(String tContNo){
    	String tPolNo="";
        if(tContNo != null && !tContNo.equals(""))
        {
            
        	String tSql = "select polno from lcpol where contno = '"+tContNo+"' and appflag = '1' and exists (select 1 from lmrisk where InsuAccFlag = 'Y' and riskcode = lcpol.riskcode)";
        	
        	ExeSQL tExeSQL = new ExeSQL();
        	tPolNo = tExeSQL.getOneValue(tSql);
        	if (tPolNo == null || tPolNo.equals("")) {
        		tPolNo = "";
        	}
        }
        return tPolNo;
    }
    /**
     * 前台传递团体保单号，小批量处理
     * @param cInputData
     * @return
     */
    private boolean runCalPartGrp()
    {
        if(mGrpContNo.equals(""))
        {
            CError.buildErr(this,"团体保单号为空，无法计算团单下的个单红利！");
            return false;
        }
        mGrpPolNo = GetGrpPolNo(mGrpContNo);
        String tLimit = PubFun.getNoLimit( mGlobalInput.ManageCom);
        String tSerialNo = PubFun1.CreateMaxNo( "SERIALNO", tLimit );//产生流水号码

        LOBonusGrpPolParmDB tLOBonusGrpPolParmDB = new LOBonusGrpPolParmDB();
        tLOBonusGrpPolParmDB.setGrpPolNo(mGrpPolNo);
        tLOBonusGrpPolParmDB.setFiscalYear(mFiscalYear);
        if(!tLOBonusGrpPolParmDB.getInfo())
        {
            CError.buildErr(this,"无法获得团单"+mGrpContNo+"下的分红参数设置！");
            insertErrLog(tSerialNo,mGrpContNo,mGrpPolNo,"无法获得团单"+mGrpContNo+"下的分红参数设置！","3");
            return false;
        }
        //tongmeng 2008-01-21 add
        //增加对各个会计年度分红比例的校验
        String tSQL_rate = "select count(distinct assignrate) from lobonusgrppolparm "
        	             + " where grppolno = '" + mGrpPolNo + "'"
        		         + " group by grppolno ";
        ExeSQL tExeSQL = new ExeSQL();
        String tRate = "";
        tRate = tExeSQL.getOneValue(tSQL_rate);
        if(tRate!=null&&!tRate.equals("")&&Integer.parseInt(tRate)>1)
        {
            CError.buildErr(this,"团单 : "+mGrpContNo+" 各年分配比例不一致，不允许计算本年红利！");
            insertErrLog(tSerialNo,mGrpContNo,mGrpPolNo,"团单 : "+mGrpContNo+" 各年分配比例不一致，不允许计算本年红利！","3");
            return false;
 
        }
        TransferData tTransferData ;
        String sql = "select polno,cvalidate from lcpol a "
                   + "where grppolno = '" + mGrpPolNo + "' and appflag = '1' and riskcode = '" + mRiskCode + "' "
                   //已经做过年金给付的不再进行计算
                   +" and not exists (select 1 from lpedoritem where contno=a.contno and EdorType = 'GA' and EdorState = '0') "
                   + ReportPubFun.getWherePart("ContNo",mContNo)
//                   + "and cvalidate <= '" + mFiscalYear + "-12-31' and paytodate >= '" + mFiscalYear + "-12-31' "
                   + ReportPubFun.getWherePart("cvalidate",mBDate,mEDate,1)
                   + " and not exists(select 1 from lobonusgrppol where grppolno = '" + mGrpPolNo + "' "
                   + " and polno = a.polno and fiscalyear = '" + mFiscalYear + "') "
                   + " order by polno ";
        logger.debug(sql);
        SSRS tssrs = new ExeSQL().execSQL(sql);
        if(tssrs.getMaxRow() == 0)
        {
            CError.buildErr(this,"没有查到待计算的个人保单信息，请按条件核实保单信息！");
            return false;
        }

        boolean retFlag = true;
        boolean blnDataFlag = false;       //是否有提交的数据，用来标示是否需要改团单红利计算状态
        insertOperLog(tSerialNo,mGrpPolNo,mGrpPolNo,"CalOneGrp","计算团单下" + mContNo + "的红利");
        String sstrSGetDate="";
        for(int i = 1 ; i <= tssrs.getMaxRow() ; i++)
        {
            String tPolNo = tssrs.GetText(i,1);
            String tCvalidate = tssrs.GetText(i,2);
            int PolYear = Integer.parseInt(mFiscalYear) - Integer.parseInt(tCvalidate.substring(0,4)) + 1;
            String strSGetDate = PubFun.calDate(tCvalidate,PolYear,"Y",null);
            sstrSGetDate = strSGetDate;
            int interval = PubFun.calInterval(strSGetDate,PubFun.getCurrentDate(),"D");
            if(interval < 0)
            {
                CError.buildErr(this,"个单 : " + tPolNo + "未到应计算红利日期！");
                return false;
            }

            String tSQL = "select insuaccno,baladate,insuaccbala,payplancode from lcinsureaccClass a "
                        + "where grppolno = '" + mGrpPolNo + "' and polno = '" + tPolNo + "' "
                        //已经做过年金给付的不再进行计算
                        +" and a.state not in ('1','4')"
                        + "and insuaccno in ('000002','000004') "
                        + "and not exists(select 1 from lobonusgrppol where grppolno = a.grppolno "
                        + "and polno = a.polno and insuaccno = a.insuaccno and payplancode = a.payplancode and fiscalyear = '" + mFiscalYear + "') "
                        + "order by payplancode";
            logger.debug(tSQL);
            SSRS cssrs = new ExeSQL().execSQL(tSQL);
            for(int j = 1 ; j <= cssrs.getMaxRow() ; j ++)
            {
                blnDataFlag = true;
                tTransferData = new TransferData();
                tTransferData.setNameAndValue("PolNo",tPolNo);
                tTransferData.setNameAndValue("PolYear",PolYear);
                tTransferData.setNameAndValue("SGetDate",strSGetDate);
                tTransferData.setNameAndValue("InsuAccNo",cssrs.GetText(j,1));
                tTransferData.setNameAndValue("BalaDate",cssrs.GetText(j,2));
                tTransferData.setNameAndValue("InsuAccBala",cssrs.GetText(j,3));
                tTransferData.setNameAndValue("PayPlanCode", cssrs.GetText(j, 4));
                tTransferData.setNameAndValue("LOBonusGrpPolParm",tLOBonusGrpPolParmDB.getSchema());

                if(!CalOnePol(tTransferData))
                {
                    insertErrLog(tSerialNo,mGrpContNo,tPolNo,mErrors.getFirstError(),"3");
                    retFlag = false;
//                    mErrors.clearErrors();
                    break;
                }
            }
        }

        if(blnDataFlag)
        {
        	//判断如果是最后一张,即团单下个单已经全部计算完成,需要将computestate置为‘1’ 并且将sumbonus 置为LOBonusGrppol的bonusmoney总和
        	String tSql="select 1 from lcpol a where polno not in (select polno from lobonusgrppol where grppolno =a.grppolno) and grppolno ='"+mGrpPolNo+"'";
        	ExeSQL tExeSQL1 = new ExeSQL();
        	SSRS tSSRS1 = new SSRS();
        	tSSRS1 =tExeSQL1.execSQL(tSql);
        	if("1".equals(tSSRS1.GetText(1, 1))){
        		//还有未计算的个单
        		tLOBonusGrpPolParmDB.setComputeState("3");
        		tLOBonusGrpPolParmDB.setModifyDate(mNowDate);
        		tLOBonusGrpPolParmDB.setModifyTime(PubFun.getCurrentTime());
        	}else{
        		//团单下个单红利全部计算完成
        		tExeSQL1 = new ExeSQL();
                String strSQL1 = "select sum(BonusMoney) from LOBonusGrpPol where GrpPolNo='"+mGrpPolNo+"' and FiscalYear="+mFiscalYear;
                tSSRS1 = tExeSQL.execSQL(strSQL1);
                String SumBonus = tSSRS1.GetText(1,1);
                tLOBonusGrpPolParmDB.setSumBonus(SumBonus);
                tLOBonusGrpPolParmDB.setComputeState("1");
                tLOBonusGrpPolParmDB.setModifyDate(mNowDate);
                tLOBonusGrpPolParmDB.setModifyTime(PubFun.getCurrentTime());
                tLOBonusGrpPolParmDB.setSGetDate(sstrSGetDate);
                tLOBonusGrpPolParmDB.setRiskCode(mRiskCode);
        		//tLOBonusGrpPolParmDB.setModifyDate(PubFun.getCurrentDate());
        		//tLOBonusGrpPolParmDB.setModifyTime(PubFun.getCurrentTime());
        	}
            //提交数据库
            if(!tLOBonusGrpPolParmDB.update())
            {
                insertErrLog(tSerialNo,mGrpContNo,mGrpPolNo,mErrors.getFirstError(),"3");
                retFlag = false;
            }
        }

        return retFlag;
    }

    /**
     * 处理单个团体保单红利
     * @param tGrpPolNo
     * @return
     */
    private boolean CalOneGrpPol(String tGrpPolNo)
    {
        //查询团体红利参数表
        LOBonusGrpPolParmDB tLOBonusGrpPolParmDB=new LOBonusGrpPolParmDB();
        tLOBonusGrpPolParmDB.setFiscalYear(mFiscalYear);
        tLOBonusGrpPolParmDB.setGrpPolNo(tGrpPolNo);
        if(tLOBonusGrpPolParmDB.getInfo()==false)
        {
            CError.buildErr(this,"没有查询到团单 : "+mGrpContNo+" 对应在团体红利参数表中会计年度为"+mFiscalYear+"的数据！");
            return false;
        }
        LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema=tLOBonusGrpPolParmDB.getSchema();
        //如果该团单不处于待计算-返回
        if(tLOBonusGrpPolParmSchema.getComputeState().equals("1") || tLOBonusGrpPolParmSchema.getComputeState().equals("2"))
        {
            CError.buildErr(this,"团单 : "+mGrpContNo+" 在会计年度 "+mFiscalYear+" 的红利已经计算或者分配，请核实信息！");
            return false;
        }
        if(tLOBonusGrpPolParmSchema.getEnsuRate() == 0 && tLOBonusGrpPolParmSchema.getEnsuRateDefault() == 0)
        {
            CError.buildErr(this,"团单 : "+mGrpContNo+" 在团体红利参数表中保证收益和默认保证收益不能都为0！");
            return false;
        }

        //查询团体保单表
        LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
        tLCGrpPolDB.setGrpPolNo(tGrpPolNo);
        if(tLCGrpPolDB.getInfo()==false)
        {
            CError.buildErr(this,"没有查询到团单:"+mGrpContNo);
            return false;
        }
        LCGrpPolSchema tLCGrpPolSchema=tLCGrpPolDB.getSchema();
        //判断应计算日期是否小于等于当天
        //计算保单年度值--需要校验:即不满一年的需要加 1 --润年问题
        int PolYear = Integer.parseInt(mFiscalYear) - Integer.parseInt(tLCGrpPolSchema.getCValiDate().substring(0,4)) + 1;
        //计算实际红利应该分配日期--校验
        String strSGetDate = PubFun.calDate(tLCGrpPolSchema.getCValiDate(),PolYear,"Y",null);
        int interval=PubFun.calInterval(strSGetDate,PubFun.getCurrentDate(),"D");
        if(interval < 0)
        {
            CError.buildErr(this,"团单:"+mGrpContNo+"未到应计算红利日期");
            return false;
        }

        //加入对上一年红利的判断：如果上一年的红利还未领取，则不允许计算本年的红利
        String tsql = "select computestate from lobonusgrppolparm "
                    + "where grppolno = '" + tGrpPolNo + "' and fiscalyear = " + (Integer.parseInt(mFiscalYear) - 1);
        SSRS tssrs = new ExeSQL().execSQL(tsql);
        if(PolYear > 1 && (tssrs.getMaxRow() <= 0 || !tssrs.GetText(1,1).equals("2")))
        {
            CError.buildErr(this,"团单 : "+mGrpContNo+" 上一年的红利没有计算或者分配，不允许计算本年红利！");
            return false;
        }
        //tongmeng 2008-01-21 add
        //增加对各个会计年度分红比例的校验
        String tSQL_rate = "select count(distinct assignrate) from lobonusgrppolparm "
        	             + " where grppolno = '" + tGrpPolNo + "'"
        		         + " group by grppolno ";
        ExeSQL sExeSQL = new ExeSQL();
        String tRate = "";
        tRate = sExeSQL.getOneValue(tSQL_rate);
        if(tRate!=null&&!tRate.equals("")&&Integer.parseInt(tRate)>1)
        {
            CError.buildErr(this,"团单 : "+mGrpContNo+" 各年分配比例不一致，不允许计算本年红利！");
            return false;
 
        }
        //查询lcinsureaccclass表
        ExeSQL tExeSQL = new ExeSQL();
        String strSQL = "select count(*) from LCInsureAccClass a "
                      + "where grppolno='"+tGrpPolNo+"' and insuaccno in ('000001','000002','000003','000004') "
                      //+ " and insuaccbala > 0 "
                      //已经做过年金给付和退保的不再进行计算
                      +" and state not in ('1','4')"
                      + "and not exists(select 1 from lobonusgrppol where grppolno = a.grppolno and polno = a.polno and insuaccno = a.insuaccno "
                      +" and payplancode = a.payplancode and fiscalyear = '" + mFiscalYear + "')";
        SSRS tSSRS=tExeSQL.execSQL(strSQL);
        String strCount=tSSRS.GetText(1,1);
        int SumCount = Integer.parseInt(strCount);
        if(SumCount==0)
        {
            logger.debug("没有待计算的保单！");
            return true;
        }
        TransferData tTransferData=new TransferData();
        int Num=1;
        //tongmeng 2007-11-21 modify
        //去除帐户金额大于零的判断
        strSQL = "select PolNo,InsuAccNo,BalaDate,InsuAccBala,PayPlanCode from LCInsureAccClass a "
               + "where grppolno='"+tGrpPolNo+"' and insuaccno in ('000001','000002','000003','000004') "
               //+ " and insuaccbala > 0 "
               //已经做过年金给付的不再进行计算
               +" and state not in ('1','4')"
               + "and not exists (select 1 from lobonusgrppol where grppolno = a.grppolno and polno = a.polno and insuaccno = a.insuaccno "
               +" and payplancode = a.payplancode and fiscalyear = '" + mFiscalYear + "') "
               + "order by polno";
        //tongmeng 2007-12-19 add注释
        //此处的程序逻辑有问题,循环会跳过一些团单导致团单不能完全计算完.现把COUNT暂时修改为50000,6.0升级后再做修改
        while(Num<=SumCount)
        {
            tExeSQL = new ExeSQL();
            tSSRS=tExeSQL.execSQL(strSQL,Num,COUNT);
            for( int i = 1; i <=tSSRS.getMaxRow(); i++ )
            {
                //查讯团体分红表是否已经有记录，有则表明处理过了,跳过
                LOBonusGrpPolDB tLOBonusGrpPolDB=new LOBonusGrpPolDB();
                tLOBonusGrpPolDB.setPolNo(tSSRS.GetText(i,1));
                tLOBonusGrpPolDB.setGrpPolNo(tGrpPolNo);
                tLOBonusGrpPolDB.setInsuAccNo(tSSRS.GetText(i,2));
                tLOBonusGrpPolDB.setPayPlanCode(tSSRS.GetText(i, 5));
                tLOBonusGrpPolDB.setFiscalYear(mFiscalYear);
                if(tLOBonusGrpPolDB.getInfo()==true)
                    continue;
                tTransferData=new TransferData();
                tTransferData.setNameAndValue("PolNo",tSSRS.GetText(i,1));
                tTransferData.setNameAndValue("PolYear",PolYear);
                tTransferData.setNameAndValue("SGetDate",strSGetDate);
                tTransferData.setNameAndValue("InsuAccNo",tSSRS.GetText(i,2));
                tTransferData.setNameAndValue("BalaDate",tSSRS.GetText(i,3));
                tTransferData.setNameAndValue("InsuAccBala",tSSRS.GetText(i,4));
                tTransferData.setNameAndValue("PayPlanCode", tSSRS.GetText(i, 5));
                tTransferData.setNameAndValue("LOBonusGrpPolParm",tLOBonusGrpPolParmSchema);

                if(CalOnePol(tTransferData)==false)
                    return false;
            }
            Num=Num+COUNT;
        }

        //求红利总和
        //如果没有计算出红利 比如说分红比例参数为0时,没有向LOBonusGrppol表中插入数据 下面查询得到的结果为空 从而报错;
        tExeSQL = new ExeSQL();
        strSQL = "select sum(BonusMoney) from LOBonusGrpPol where GrpPolNo='"+tGrpPolNo+"' and FiscalYear="+mFiscalYear;
        tSSRS = tExeSQL.execSQL(strSQL);
        String SumBonus = tSSRS.GetText(1,1);
        tLOBonusGrpPolParmSchema.setSumBonus(SumBonus);

        tLOBonusGrpPolParmSchema.setComputeState("1");
        tLOBonusGrpPolParmSchema.setModifyDate(mNowDate);
        tLOBonusGrpPolParmSchema.setModifyTime(PubFun.getCurrentTime());
        tLOBonusGrpPolParmSchema.setSGetDate(strSGetDate);
        tLOBonusGrpPolParmSchema.setRiskCode(mRiskCode);
        //提交数据库
        if(CalGrpsubmitDB(tLOBonusGrpPolParmSchema,tLCGrpPolSchema)==false)
            return false;

        return true;
    }

    /**
     * 处理单个保单帐户
     * @return
     */
    private boolean CalOnePol(TransferData tTransferData)
    {
        try
        {
        	String PolNo = (String)tTransferData.getValueByName("PolNo");
        	int PolYear = ((Integer)tTransferData.getValueByName("PolYear")).intValue();
        	String SGetDate = (String)tTransferData.getValueByName("SGetDate");
        	String InsuAccNo = (String)tTransferData.getValueByName("InsuAccNo");
        	String strBalaDate = (String)tTransferData.getValueByName("BalaDate");
        	//String StrInsuAccBala = (String)tTransferData.getValueByName("InsuAccBala");
        	String PayPlanCode = (String)tTransferData.getValueByName("PayPlanCode");
        	LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema = (LOBonusGrpPolParmSchema)tTransferData.getValueByName("LOBonusGrpPolParm");
        	LOBonusGrpPolSet tLOBonusGrpPolSet =new LOBonusGrpPolSet();

        	//得到红利计算参数
        	double ActuRate=tLOBonusGrpPolParmSchema.getActuRate();
        	double EnsuRate=tLOBonusGrpPolParmSchema.getEnsuRate();
        	double EnsuRateDefault=tLOBonusGrpPolParmSchema.getEnsuRateDefault();
        	double AssignRate=tLOBonusGrpPolParmSchema.getAssignRate();
        	if(EnsuRate==0)
        		EnsuRate=EnsuRateDefault;

        	//查询－会计年度末的应派发的利息是否已经派发－如果是则上面已经计算红利，下面跳过
        	String calDate = String.valueOf(mFiscalYear) + "-12-31";
        	if(PubFun.calInterval(strBalaDate,calDate,"D") > 0)
        	{
        		CError.buildErr(this,"保单 " + PolNo + " 的 "+InsuAccNo+" 帐户还没有计算上一年度的利息，不允许进行分红操作！");
        		return false;
        	}

        	//计算保单上周年日
        	String LastPolDate=PubFun.calDate(SGetDate,-1,"Y",null);
        	//计算保单上周年日到该计算红利这天的天数
        	double TDays=365;
        	//double TDays=PubFun.calInterval(LastPolDate,SGetDate,"D");

        	//求从保单上周年日至今的帐户资金调整总和
        	ExeSQL tExeSQL = new ExeSQL();
        	String strSQL="select sum(Money) from LCInsureAccTrace where InsuAccNo='"+InsuAccNo+"'"
        	+" and PolNo='"+PolNo+"' and PayDate>='"+LastPolDate+"' and PayDate<='"+PubFun.getCurrentDate()+"'"
        	+" and payplancode='"+PayPlanCode+"' group by payplancode" ;
        	logger.debug("Query 1 : " + strSQL);
        	SSRS tSSRS=tExeSQL.execSQL(strSQL);
        	//for(int j=1;j<=tSSRS.getMaxRow();j++){
        	//循环trace表中不通的payPlanCode
        	String strSumMoney=tSSRS.GetText(1,1);
        	
        	// add by sunsx 帐户资金余额 以Sum为准 2010-02-01
        	String tInsuAccBalaSql = "select sum(Money) from LCInsureAccTrace where InsuAccNo='"+InsuAccNo+"'"
            					   + " and PolNo='"+PolNo+"' and payplancode='"+PayPlanCode+"'";
        	
        	String StrInsuAccBala = tExeSQL.getOneValue(tInsuAccBalaSql);
        	if(StrInsuAccBala == null ||StrInsuAccBala.equals("")){
        		CError.buildErr(this,"保单 " + PolNo + " 的 "+PayPlanCode+" 帐户余额查询失败！");
        		return false;
        	}
        	double SumMoney = Double.parseDouble(strSumMoney);//保单上周年日至今的帐户资金调整总和
        	double InsuAccBala=Double.parseDouble(StrInsuAccBala);//账户现在的余额
        	double FoundMoney=InsuAccBala-SumMoney;//上周年日的帐户资金余额

        	//保单上周年日到本周年日间的资金轨迹-对每条轨迹计算
        	tExeSQL = new ExeSQL();
        	strSQL="select Money,PayDate,payplancode from LCInsureAccTrace where InsuAccNo='"+InsuAccNo+"'"
        	+" and PolNo='"+PolNo+"' and PayDate>='"+LastPolDate+"' and PayDate<'"+SGetDate+"'"
        	+" and payplancode = '"+PayPlanCode+"'" ;
        	logger.debug("Query 2 : " + strSQL);
        	SSRS tSSRS1=tExeSQL.execSQL(strSQL);
        	double sumCFMoneyIa=0.0;
        	double sumCFMoneyIg=0.0;
        	//计算每个payPlanCode
        	for( int i = 1; i <=tSSRS1.getMaxRow(); i++ )
        	{
//      		求从保单上周年日至今的帐户资金调整总和
        		String strMoney=tSSRS1.GetText(i,1);
        		double CFMoney = Double.parseDouble(strMoney);
        		String strPayDate=tSSRS1.GetText(i,2);
        		//计算入机时间到保单上周年日的天数
        		double intvDays=PubFun.calInterval(LastPolDate,strPayDate,"D");
        		double powIa=Math.pow((1+ActuRate),((TDays-intvDays)/TDays));
        		double tempResultIa=CFMoney*powIa;
        		sumCFMoneyIa=sumCFMoneyIa+tempResultIa;//实际收益
        		double powIg=Math.pow((1+EnsuRate),((TDays-intvDays)/TDays));
        		double tempResultIg=CFMoney*powIg;
        		sumCFMoneyIg=sumCFMoneyIg+tempResultIg; //保证收益
        	}

        	double sumBonus=FoundMoney*(1+ActuRate)-FoundMoney*(1+EnsuRate)+sumCFMoneyIa-sumCFMoneyIg;
        	sumBonus=sumBonus*AssignRate;
        	if(sumBonus==0)
        		return true;

        	LCPolDB tLCPolDB=new LCPolDB();
        	tLCPolDB.setPolNo(PolNo);
        	if(tLCPolDB.getInfo()==false)
        	{
        		CError.buildErr(this,"保单查询数据失败:"+PolNo+tLCPolDB.mErrors.getFirstError());
        		return false;
        	}
        	LCPolSchema tLCPolSchema=tLCPolDB.getSchema();

        	LOBonusGrpPolSchema tLOBonusGrpPolSchema=new LOBonusGrpPolSchema();
        	tLOBonusGrpPolSet =new LOBonusGrpPolSet();
        	tLOBonusGrpPolSchema.setPolNo(PolNo);
        	tLOBonusGrpPolSchema.setGrpPolNo(tLOBonusGrpPolParmSchema.getGrpPolNo());
        	tLOBonusGrpPolSchema.setSGetDate(SGetDate);
        	tLOBonusGrpPolSchema.setInsuAccNo(InsuAccNo);
        	tLOBonusGrpPolSchema.setFiscalYear(mFiscalYear);
        	tLOBonusGrpPolSchema.setPolYear(PolYear);
        	//团体分红时如果保单类型为空则默认为个人单(0)
        	if (tLCPolSchema.getPolTypeFlag() == null || tLCPolSchema.getPolTypeFlag().trim().equals(""))
        		tLOBonusGrpPolSchema.setPolTypeFlag("0");
        	else
        		tLOBonusGrpPolSchema.setPolTypeFlag(tLCPolSchema.getPolTypeFlag());

        	tLOBonusGrpPolSchema.setBonusFlag("0");
        	tLOBonusGrpPolSchema.setBonusMakeDate(mNowDate);
        	tLOBonusGrpPolSchema.setBonusMoney(sumBonus);
        	tLOBonusGrpPolSchema.setMakeDate(mNowDate);
        	tLOBonusGrpPolSchema.setMakeTime(PubFun.getCurrentTime());
        	tLOBonusGrpPolSchema.setModifyDate(mNowDate);
        	tLOBonusGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
        	tLOBonusGrpPolSchema.setOperator(mGlobalInput.Operator);
        	tLOBonusGrpPolSchema.setPayPlanCode(PayPlanCode);
        	tLOBonusGrpPolSet.add(tLOBonusGrpPolSchema);

        	map =new MMap();
        	map.put(tLOBonusGrpPolSet, "INSERT");
        	this.prepareOutputData();
        	//通过PubSubmit提交数据
        	PubSubmit tPubSubmit = new PubSubmit();

        	if (!tPubSubmit.submitData(mInputData, "")) {
        		// @@错误处理
        		this.mErrors.copyAllErrors(tPubSubmit.mErrors);
        		CError.buildErr(this, "数据提交失败!");
        		return false;
        	}

        }
        catch(Exception ex)
        {
            CError.buildErr(this,"处理单个保单帐户发生错误!"+ex);
            return false;
        }
        return true;
    }

    private void prepareOutputData() {

    	mInputData = new VData();
        mInputData.clear();
		mInputData.add(map);
	}
    /**
     * 纪录错误信息
     * @param tSerialNo
     * @param tPolNo
     * @param errDescribe
     * @param tType    1：计算，2：分配
     * @return
     */
    private boolean insertErrLog(String tSerialNo,String tGrpContNo,String tGrpPolNo,String errDescribe,String tType)
    {
        LOBonusAssignGrpErrLogDB tLOBonusAssignGrpErrLogDB= new LOBonusAssignGrpErrLogDB();
        tLOBonusAssignGrpErrLogDB.setSerialNo(tSerialNo);
        tLOBonusAssignGrpErrLogDB.setGrpContNo(tGrpContNo);
        tLOBonusAssignGrpErrLogDB.setGrpPolNo(tGrpPolNo);
        tLOBonusAssignGrpErrLogDB.setFiscalYear(mFiscalYear);
        tLOBonusAssignGrpErrLogDB.setType(tType);
        tLOBonusAssignGrpErrLogDB.setErrMsg(errDescribe);
        tLOBonusAssignGrpErrLogDB.setMakeDate(mNowDate);
        tLOBonusAssignGrpErrLogDB.setMakeTime(PubFun.getCurrentTime());
        if(tLOBonusAssignGrpErrLogDB.insert()==false)
        {
            CError tError = new CError();
            tError.moduleName = "GrpAssignBonus";
            tError.functionName = "insertErrLog";
            tError.errorMessage = "纪录错误日志时发生错误："+tLOBonusAssignGrpErrLogDB.mErrors.getFirstError()+"；解决该问题后，请再次分配当日红利";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 记录操作轨迹
     * @param cSerialNo
     * @param cGrpPolNo
     * @param cType
     * @param cMsgInfo
     * @return
     */
    private boolean insertOperLog(String cSerialNo,String cBussNo,String cOtherNo,String cType,String cMsgInfo)
    {
        LSRunDataInfoSchema tLSRunDataInfoSchema = new LSRunDataInfoSchema();
        tLSRunDataInfoSchema.setSerialNo(cSerialNo);
        tLSRunDataInfoSchema.setRunType("1");                   //人工执行
        tLSRunDataInfoSchema.setBussNo(cBussNo);
        tLSRunDataInfoSchema.setBussType("GRPBONUS");           //computestate
        tLSRunDataInfoSchema.setAssiType(cType);
        tLSRunDataInfoSchema.setOtherNo(cOtherNo);
        tLSRunDataInfoSchema.setContent(cMsgInfo);
        tLSRunDataInfoSchema.setProgName(this.getClass().getName());            //com.sinosoft.lis.cbcheck.BonusGrpPolParmSaveBL
        tLSRunDataInfoSchema.setOperator(mGlobalInput.Operator);
        tLSRunDataInfoSchema.setStandByFlag1(mFiscalYear);
        tLSRunDataInfoSchema.setStandByFlag2("");
        tLSRunDataInfoSchema.setMakeDate(mNowDate);
        tLSRunDataInfoSchema.setMakeTime(PubFun.getCurrentTime());

        LSRunDataInfoDB tLSRunDataInfoDB = new LSRunDataInfoDB();
        tLSRunDataInfoDB.setSchema(tLSRunDataInfoSchema);
        if(!tLSRunDataInfoDB.insert())
        {
            CError.buildErr(this,tLSRunDataInfoDB.mErrors.getFirstError());
            return false;
        }

        return true;
    }

  //--上面是团体分红计算

  //--下面是团体分红分配

  /**
   * 分配红利
   * @param tVData
   * @return
   */
    private boolean runAssignBonus()
    {
        String tLimit = PubFun.getNoLimit( mGlobalInput.ManageCom);
        String tSerialNo = PubFun1.CreateMaxNo( "SERIALNO", tLimit );//产生流水号码
        //得到团单合同号
        mGrpPolNo = GetGrpPolNo(mGrpContNo);
        boolean retFlag = true;

        String sql = "select grppolno from lobonusgrppolparm a "
                   + "where fiscalyear = '" + mFiscalYear + "' and riskcode = '" + mRiskCode + "' "
                   + "and computestate = '1' "
                   + ReportPubFun.getWherePart("grppolno",mGrpPolNo)
                   + " and exists(select 1 from lcgrppol where grppolno = a.grppolno and appflag = '1' "
//                   + "and cvalidate <= '" + mFiscalYear + "-12-31' and payenddate >= '" + mFiscalYear + "-12-31' "
                   + ReportPubFun.getWherePart("cvalidate",mBDate,mEDate,1)
                   + " ) order by grppolno ";
        SSRS tssrs = new ExeSQL().execSQL(sql);
        logger.debug(sql);
        for(int i = 1 ; i <= tssrs.getMaxRow() ; i++)
        {
            String tGrpPolNo = tssrs.GetText(i,1);
            insertOperLog(tSerialNo,tGrpPolNo,tGrpPolNo,"AssiAllGrp","批量分配团单" + tGrpPolNo + "的红利");
            if(!AssignOneGrpPol(tGrpPolNo))
            {
                insertErrLog(tSerialNo,mGrpContNo,tGrpPolNo,mErrors.getFirstError(),"2");
                retFlag = false;
                mErrors.clearErrors();
            }
        }
        return retFlag;
    }

    /**
     * 分配团单下个人红利
     * @param tVData
     * @return
     */
    private boolean runAssignPerson()
    {
        if(mGrpContNo == null || mGrpContNo.equals(""))
        {
            CError.buildErr(this,"团单号不能为空！");
            return false;
        }
        if(mContNo == null || mContNo.equals(""))
        {
            CError.buildErr(this,"个人保单号不能为空！");
            return false;
        }
        mGrpPolNo = GetGrpPolNo(mGrpContNo);
        String mPolNo = GetPolNo(mContNo);
        boolean retFlag = true;
        String sql="select PolNo,InsuAccNo,payplancode from LOBonusGrpPol a "
                   + "where GrpPolNo = '"+mGrpPolNo+ "' and FiscalYear = "+mFiscalYear
                   + ReportPubFun.getWherePart("polno",mPolNo)
                   + " and BonusFlag = '0' and exists (select 1 from lcpol where polno = a.polno and appflag = '1') ";
        logger.debug(sql);
        SSRS tssrs = new ExeSQL().execSQL(sql);
        if(tssrs == null || tssrs.getMaxRow() == 0)
        {
            CError.buildErr(this,"没有查到条件所对应的红利分配信息，请按条件核实红利是否已经计算！");
            retFlag = false;
        }

        String tLimit = PubFun.getNoLimit( mGlobalInput.ManageCom);
        String tSerialNo = PubFun1.CreateMaxNo( "SERIALNO", tLimit );//产生流水号码
        insertOperLog(tSerialNo,mGrpPolNo,mGrpPolNo,"AssiOneGrp","分配团单下" + mContNo + "的红利");
        TransferData tTransferData = new TransferData();
        for(int i = 1 ; i <= tssrs.getMaxRow() ; i++)
        {
            String tPolNo = tssrs.GetText(i,1);
            tTransferData = new TransferData();
            tTransferData.setNameAndValue("GrpPolNo",mGrpPolNo);
            tTransferData.setNameAndValue("PolNo",tPolNo);
            tTransferData.setNameAndValue("InsuAccNo",tssrs.GetText(i,2));
            tTransferData.setNameAndValue("PayPlanCode", tssrs.GetText(i, 3));
            if(!AssignOnePol(tTransferData))
            {
                insertErrLog(tSerialNo,mGrpContNo,mGrpPolNo,mErrors.getFirstError(),"4");
                logger.debug(mErrors.getFirstError());
                retFlag = false;
                mErrors.clearErrors();
            }
        }
        return retFlag;
    }

    /**
     * 处理单个团体保单红利
     * @param tGrpPolNo
     * @return
     */
    private boolean AssignOneGrpPol(String tGrpPolNo)
    {
        //查询团体红利参数表
        LOBonusGrpPolParmDB tLOBonusGrpPolParmDB=new LOBonusGrpPolParmDB();
        tLOBonusGrpPolParmDB.setFiscalYear(mFiscalYear);
        tLOBonusGrpPolParmDB.setGrpPolNo(tGrpPolNo);
        if(tLOBonusGrpPolParmDB.getInfo()==false)
        {
            CError.buildErr(this,"没有查询到团单:"+tGrpPolNo+"对应在团体红利参数表中会计年度为"+mFiscalYear+"的数据");
            return false;
        }
        LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema=tLOBonusGrpPolParmDB.getSchema();
        //如果该团单不处于待计算-返回
        if(!tLOBonusGrpPolParmSchema.getComputeState().equals("1"))
        {
            CError.buildErr(this,"团单:"+tGrpPolNo+" 对应在团体红利参数表中的状态不是待分配!");
            return false;
        }

        ExeSQL tExeSQL = new ExeSQL();
        String strSQL = "select count(*) from LOBonusGrpPol a "
                      + "where GrpPolNo = '" + tGrpPolNo + "' and FiscalYear = " + mFiscalYear
                      + " and BonusFlag = '0' and exists (select 1 from lcpol where polno = a.polno and appflag = '1') ";
        logger.debug("Query Pol Count : " + strSQL);
        SSRS tSSRS=tExeSQL.execSQL(strSQL);
        String strCount=tSSRS.GetText(1,1);
        int SumCount = Integer.parseInt(strCount);
        if(SumCount > 0)
        {
            TransferData tTransferData=new TransferData();
            strSQL = "select PolNo,InsuAccNo,payplancode from LOBonusGrpPol a "
                   + "where GrpPolNo = '"+tGrpPolNo+ "' and FiscalYear = "+mFiscalYear
                   + " and BonusFlag = '0' and exists (select 1 from lcpol where polno = a.polno and appflag = '1') ";
            logger.debug("Query Lobonusgrppol : " + strSQL);
            tSSRS = tExeSQL.execSQL(strSQL);
            for( int i = 1; i <=tSSRS.getMaxRow(); i++ )
            {
                tTransferData=new TransferData();
                tTransferData.setNameAndValue("GrpPolNo",tGrpPolNo);
                tTransferData.setNameAndValue("PolNo",tSSRS.GetText(i,1));
                tTransferData.setNameAndValue("InsuAccNo",tSSRS.GetText(i,2));
                tTransferData.setNameAndValue("PayPlanCode", tSSRS.GetText(i, 3));
                if(AssignOnePol(tTransferData)==false)
                    return false;
            }
        }

        //更新LOBonusGrpPolParm的ComputeState计算状态
        tLOBonusGrpPolParmSchema.setComputeState("2");
        tLOBonusGrpPolParmSchema.setModifyDate(mNowDate);
        tLOBonusGrpPolParmSchema.setModifyTime(PubFun.getCurrentTime());
        tLOBonusGrpPolParmDB = new LOBonusGrpPolParmDB();
        tLOBonusGrpPolParmDB.setSchema(tLOBonusGrpPolParmSchema);
        if(tLOBonusGrpPolParmDB.update()==false)
        {
            CError.buildErr(this,"团单:"+tGrpPolNo+" 更新团体红利参数表失败");
            return false;
        }

        return true;
    }

    /**
     * 分配单个保单帐户
     * @return
     */
    private boolean AssignOnePol(TransferData tTransferData)
    {
        try
        {
            String GrpPolNo=(String)tTransferData.getValueByName("GrpPolNo");
            String PolNo=(String)tTransferData.getValueByName("PolNo");
            String InsuAccNo=(String)tTransferData.getValueByName("InsuAccNo");
            String PayPlanCode = (String)tTransferData.getValueByName("PayPlanCode");

            LOBonusGrpPolDB tLOBonusGrpPolDB = new LOBonusGrpPolDB();
            tLOBonusGrpPolDB.setPolNo(PolNo);
            tLOBonusGrpPolDB.setGrpPolNo(GrpPolNo);
            tLOBonusGrpPolDB.setInsuAccNo(InsuAccNo);
            tLOBonusGrpPolDB.setFiscalYear(mFiscalYear);
            tLOBonusGrpPolDB.setPayPlanCode(PayPlanCode);
            if(!tLOBonusGrpPolDB.getInfo())
            {
                CError.buildErr(this,"团单下个单:"+PolNo+" 的团体保单红利表查询失败");
                return false;
            }
            LOBonusGrpPolSchema tLOBonusGrpPolSchema = tLOBonusGrpPolDB.getSchema();
            if(tLOBonusGrpPolSchema.getBonusFlag().equals("1"))
            {
                CError.buildErr(this,"团单下个单:"+PolNo+" 的红利已经分配，不允许重复分配！");
                return false;
            }

            double BonusMoney=tLOBonusGrpPolSchema.getBonusMoney();
            String SGetDate=tLOBonusGrpPolSchema.getSGetDate();

            LCInsureAccClassDB tLCInsureAccClassDB =new LCInsureAccClassDB();
            tLCInsureAccClassDB.setPolNo(PolNo);
            tLCInsureAccClassDB.setInsuAccNo(InsuAccNo);
            tLCInsureAccClassDB.setPayPlanCode(PayPlanCode);
            LCInsureAccClassSet tLCInsureAccClassSet  = tLCInsureAccClassDB.query();
            if(tLCInsureAccClassSet==null ||tLCInsureAccClassSet.size()<1)
            {
                CError.buildErr(this,"团单下个单:"+PolNo+" 的帐户表查询失败");
                return false;
            }
            LCInsureAccClassSchema tLCInsureAccClassSchema=tLCInsureAccClassSet.get(1);
            LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
            LOBonusGrpPolSet tLOBonusGrpPolSet = new LOBonusGrpPolSet();

            //添加帐户轨迹信息
            String tLimit = PubFun.getNoLimit( tLCInsureAccClassSchema.getManageCom());
            String tSerialNo = PubFun1.CreateMaxNo( "SERIALNO", tLimit );//产生流水号码
            
            Reflections ref = new Reflections();
            LCInsureAccTraceSchema tLCInsureAccTraceSchema=new LCInsureAccTraceSchema();
            ref.transFields(tLCInsureAccTraceSchema, tLCInsureAccClassSchema);
            tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
            tLCInsureAccTraceSchema.setMoneyType("HL");
            tLCInsureAccTraceSchema.setMoney(BonusMoney);
            tLCInsureAccTraceSchema.setState("0");
            tLCInsureAccTraceSchema.setManageCom(tLCInsureAccClassSchema.getManageCom());
            tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
            tLCInsureAccTraceSchema.setMakeDate(mNowDate);
            //tLCInsureAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
            tLCInsureAccTraceSchema.setMakeTime("00:00:00");
            tLCInsureAccTraceSchema.setModifyDate(mNowDate);
            tLCInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
            tLCInsureAccTraceSchema.setPayDate(SGetDate);
            tLCInsureAccTraceSchema.setFeeCode("000000");
            tLCInsureAccTraceSchema.setOtherType("1");
            tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);

            //更新团体红利分配表
            tLOBonusGrpPolSchema.setBonusFlag("1");
            tLOBonusGrpPolSchema.setAGetDate(SGetDate);
            tLOBonusGrpPolSchema.setModifyDate(mNowDate);
            tLOBonusGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
            tLOBonusGrpPolSet.add(tLOBonusGrpPolSchema);
       
            //通过PubSubmit提交数据
            map = new MMap();
            map.put(tLCInsureAccTraceSet, "INSERT");
            map.put(tLOBonusGrpPolSet, "UPDATE");
            this.prepareOutputData();
            PubSubmit tPubSubmit = new PubSubmit();
    		if (!tPubSubmit.submitData(mInputData, "INSERT")) {
    			// @@错误处理
    			CError.buildErr(this, "数据提交失败!");
    			return false;
    		}

        }
        catch(Exception ex)
        {
            CError.buildErr(this,"分配单个保单帐户发生错误!"+ex);
            return false;
        }
        return true;
    }

    /**
     *
     * @param tLOBonusGrpPolParmSchema
     * @param tLCGrpPolSchema
     * @return
     */
    private boolean CalGrpsubmitDB(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,LCGrpPolSchema tLCGrpPolSchema)
    {
        Connection conn = DBConnPool.getConnection();
        if (conn==null)
        {
            CError.buildErr(this,"数据库连接失败");
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
            LOBonusGrpPolParmDB tLOBonusGrpPolParmDB =new LOBonusGrpPolParmDB(conn);
            tLOBonusGrpPolParmDB.setSchema(tLOBonusGrpPolParmSchema);
            if(tLOBonusGrpPolParmDB.update()==false)
            {
                CError.buildErr(this,"团单:"+tLCGrpPolSchema.getGrpPolNo() +" 更新团体红利参数表fail");
                conn.rollback();
                conn.close();
                return false;
            }

            //添加打印表
            LOPRTManagerSchema tLOPRTManagerSchema=new LOPRTManagerSchema();
            String tLimit=PubFun.getNoLimit(tLCGrpPolSchema.getManageCom());
            String prtSeqNo=PubFun1.CreateMaxNo("PRTSEQNO",tLimit);
            tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
            tLOPRTManagerSchema.setOtherNo(tLCGrpPolSchema.getGrpPolNo());
            tLOPRTManagerSchema.setOtherNoType("01");
            tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRPBONUSPAY);
            tLOPRTManagerSchema.setManageCom(tLCGrpPolSchema.getManageCom());
            tLOPRTManagerSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
            tLOPRTManagerSchema.setReqCom(tLCGrpPolSchema.getManageCom());
            tLOPRTManagerSchema.setReqOperator(tLCGrpPolSchema.getOperator());
            tLOPRTManagerSchema.setPrtType("0");
            tLOPRTManagerSchema.setStateFlag("0");
            tLOPRTManagerSchema.setStandbyFlag3(String.valueOf(tLOBonusGrpPolParmSchema.getFiscalYear()));
            tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
            tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

            LOPRTManagerDB tLOPRTManagerDB=new LOPRTManagerDB(conn);
            tLOPRTManagerDB.setSchema(tLOPRTManagerSchema);
            if(tLOPRTManagerDB.insert()==false)
            {
                CError.buildErr(this, "打印管理表插入失败:"+tLOPRTManagerDB.mErrors.getFirstError());
                conn.rollback();
                conn.close();
                return false;
            }
            conn.commit() ;
            conn.close();
        }
        catch(Exception ex)
        {
            CError.buildErr(this,"插入数据库发生错误!"+ex);
            try
            {
                conn.rollback();
                conn.close();
            }
            catch(Exception e)
            {  }
            return false;
        }
        return true;
    }
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
