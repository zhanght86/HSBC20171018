package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.text.*;
/**
 * <p>Title: </p>
 * <p>Description: 团体自动计息 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class GrpInterestBL
{
private static Logger logger = Logger.getLogger(GrpInterestBL.class);

    private int COUNT = 1000;
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mRunType = "";                      //0-自动运行； 1－人工执行
    private String mFiscalYear = "";
    private String mGrpContNo = "";
    private String mRiskCode = "";
    private String mCvalidateB = "";
    private String mCvalidateE = "";

    /** 往后面传输数据的容器 */
    private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private VData mInputData = new VData();

    /*转换精确位数的对象*/
    private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");           //数字转换对象

    public CErrors mErrors = new CErrors();
    public GrpInterestBL() {}

    public static void main(String[] args)
    {
        GlobalInput tGlobalInput=new GlobalInput();
        tGlobalInput.ManageCom = "86";
        tGlobalInput.Operator = "DEBUG";
        tGlobalInput.ComCode = "86";

        String tFiscalYear = "2008" , tGrpPolNo = "86110020030220000470";
        if(args.length == 1)
        {
            tFiscalYear = args[0];
        }
        if(args.length == 2)
        {
            tFiscalYear = args[0];
            tGrpPolNo = args[1];
        }

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("FiscalYear",tFiscalYear);
        tTransferData.setNameAndValue("GrpPolNo",tGrpPolNo);
        tTransferData.setNameAndValue("RiskCode","212401");
        tTransferData.setNameAndValue("CvalidateB","");
        tTransferData.setNameAndValue("CvalidateE","");

        String tRunType = "0";              //0-自动运行
        VData tVData = new VData();
        tVData.add(tRunType);
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        logger.debug("---------------  begin  ---------------");
        GrpInterestBL tGrpInterestBL = new GrpInterestBL();
        tGrpInterestBL.submitData(tVData,tGrpPolNo);
        logger.debug("----------------  end  ----------------");
    }
	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LS0001")) {
			return false;
		}
		return true;
	}
    /**
     传输数据的公共方法
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        try
        {
            if(!getInputData(cInputData))
            {
                logger.debug(mErrors.getFirstError());
                return false;
            }

            //查找基金型险种的代码
            String sql = "select trim(sysvarvalue) from ldsysvar where sysvar = 'FundRisk'";
            ExeSQL tExeSQL = new ExeSQL();
            logger.debug("查找基金型险种的代码 " + sql);
            SSRS tssrs = (SSRS)tExeSQL.execSQL(sql);
            if(tssrs.getMaxRow() <= 0)
            {
                logger.debug("无法获取基金型险种的代码！");
                buildError("submitData","无法获取基金型险种的代码！");
                return false;
            }
            String [] tFundRisk = PubFun.split(tssrs.GetText(1,1),";");
            if(tFundRisk.length == 0)
            {
                logger.debug("无法获取基金型险种的代码！");
                buildError("submitData","无法获取基金型险种的代码！");
                return false;
            }

            String tRiskCode = "('";
            for(int i = 0 ; i < tFundRisk.length - 1 ; i ++)
                tRiskCode += tFundRisk[i] + "','";
            tRiskCode += tFundRisk[tFundRisk.length - 1] + "') ";
            logger.debug("FundRisk ： " + tRiskCode);
            String tBeginDate = String.valueOf(Integer.parseInt(mFiscalYear)-1) + "-12-31";
            String tEndDate = mFiscalYear + "-12-31";

            sql = "select a.GrpContNo,a.CValiDate,a.PrtNo from lcgrppol a "
                + "where  a.appflag = '1' and a.riskcode in " + tRiskCode + " "
                //  排除已经没有有效保单的团单
                + " and exists (select '1' from LCPol Where GrpPolNo = a.GrpPolNo and Appflag = '1')"
                + ReportPubFun.getWherePart("GrpContNo",mGrpContNo)
                + ReportPubFun.getWherePart("RiskCode",mRiskCode)
                + ReportPubFun.getWherePart("CValidate",mCvalidateB,mCvalidateE,1)
                //tongmeng 2008-02-25 modify
                //结息少一天的问题.
//                + " and exists(select 1 from lcinsureacc where grppolno = a.grppolno and baladate >= '"+tBeginDate+"' and baladate < '"+tEndDate+"') "
                + " and exists(select 1 from lcinsureacc where grppolno = a.grppolno and state not in ('1','4') and baladate >= '"+tBeginDate+"' and baladate <= '"+tEndDate+"') "
                + "order by grppolno ";
            logger.debug(sql);
            tssrs = new ExeSQL().execSQL(sql);
            if(tssrs == null || tssrs.mErrors.needDealError() == true)
            {
                buildError("submitData",tssrs.mErrors.getFirstError());
                return false;
            }
            logger.debug("共 ： " + tssrs.getMaxRow() + " 条待计算团单！");
            for(int i = 1 ; i <= tssrs.getMaxRow() ; i ++)
            {
            	//准备锁表
            	boolean tLockFlag =true;
            	if (!lockNo(tssrs.GetText(i, 3))) {
            		logger.debug("锁定号码失败!");
            		this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
            		tLockFlag = false;
            		//mPubLock.unLock();
            		return false;
            	}
                // 准备所有要打印的数据
                if( !CalOneGrpPol(tssrs.GetText(i,1),tssrs.GetText(i,2)))
                {
                    logger.debug("Error : " + mErrors.getFirstError());
                    continue;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            buildError("submitData", "发生异常:"+ex.toString());
            return false;
        }
        finally {
        	mPubLock.unLock();
        }
        return true;
    }

    private boolean getInputData(VData cInputData)
    {
        mRunType = (String)cInputData.get(0);
        mGlobalInput = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0);
        TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
        if(mRunType == "" || mGlobalInput == null || tTransferData == null)
        {
            buildError("getInputData","传入的参数缺失！");
            return false;
        }

        mFiscalYear = (String)tTransferData.getValueByName("FiscalYear");
        mGrpContNo = (String)tTransferData.getValueByName("GrpContNo");
        mRiskCode = (String)tTransferData.getValueByName("RiskCode");
        mCvalidateB = (String)tTransferData.getValueByName("CvalidateB");
        mCvalidateE = (String)tTransferData.getValueByName("CvalidateE");

        return true;
    }

    /**
     * 处理单个团体保单红利
     * @param tGrpPolNo
     * @return
     */
    private boolean CalOneGrpPol(String cGrpContNo,String cCvaliDate)
    {
        ExeSQL tExeSQL = new ExeSQL();
        String sql="";
        SSRS tssrs = new SSRS();
        String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
        String tSerialNo = PubFun1.CreateMaxNo( "SERIALNO", tLimit );           //产生流水号码
//    	 校验保单保全是否被挂起
        BqContHangUpBL tBqContHangUpBL=null;
        tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
//      校验团单保全是否被挂起
		if (!tBqContHangUpBL.checkGrpHangUpState(
				cGrpContNo, "2")) {
			mErrors.copyAllErrors(tBqContHangUpBL.mErrors);
			insertErrLog(tSerialNo,cGrpContNo,cGrpContNo,"该团单正在做保全，此时不能进行结息操作！");
			return false;
		}
        int tPolYear = Integer.parseInt(mFiscalYear) - Integer.parseInt(cCvaliDate.substring(0,4)) + 1;
        //tongmeng 2008-01-28 add
        //增加对是否做过上一年度分红的限制
                     
        String tSQL = "select '1' from lcgrppol a where GrpContNo='"+cGrpContNo+"' and ('"+tPolYear+"'<=1 or "
                    //首年或者非分红险不做校验
                    + " exists (select '1' from lmriskapp where bonusflag='0' and riskcode=a.riskcode)) "
                    + " union "
                    //次年以后并且是分红险需要校验上一期是否做过红利分配
                    + " select '1' from lcgrppol a where GrpContNo='"+cGrpContNo+"' and '"+tPolYear+"'>1 "
                    + " and exists (select '1' from lmriskapp where bonusflag='1' and riskcode=a.riskcode) "
                    + " and exists (select '1' from lobonusgrppolparm where grppolno=a.grppolno and fiscalyear="+mFiscalYear+"-1 "
                    + " and computestate='2' ) ";
        logger.debug(cGrpContNo+":"+tPolYear+":"+"tSQL : "+tSQL);
        String tFlag= "";
        tExeSQL = new ExeSQL();
        tFlag = tExeSQL.getOneValue(tSQL);

         if(tFlag==null||tFlag.equals(""))
        {
        	buildError("CalOneGrpPol","该团单没有做过上一年度分红，此时不能进行结息操作！");
            insertErrLog(tSerialNo,cGrpContNo,cGrpContNo,"该团单没有做过上一年度分红，此时不能进行结息操作！");
            return false;
        }
      
        //查询待结息的个单－结息日期为上一会计年度末到本年底的数据
        //tongmeng 2007-11-21 modify
        //修改查询需要结息的保单时判断帐户可用金额需大于零的条件
        sql = "select polno,insuaccno,baladate from lcinsureacc "
            + "where GrpContNo = '" + cGrpContNo + "' "
           //  duanyh 2008-5-8 modify 已经做过年金给付的不再进行计算
            // sunsx 2009- 6-8 失效和转年金的不参与结息和分红
            +" and state not in ('1','4')"
            + " and baladate >= '" + String.valueOf(Integer.parseInt(mFiscalYear)-1) + "-12-31' and baladate <= '" + mFiscalYear + "-12-31' "
            + "order by polno"  ;
        tExeSQL = new ExeSQL();
        tssrs = tExeSQL.execSQL(sql);

        
        TransferData tTransferData = new TransferData();
        for( int i = 1; i <= tssrs.getMaxRow(); i++ )
        {
            LCPolDB tLCPolDB = new LCPolDB();
            tLCPolDB.setPolNo(tssrs.GetText(i,1));
            if(!tLCPolDB.getInfo())
            {
                logger.debug("保单 " + tssrs.GetText(i,1) + " 不存在！");
                buildError("CalOneGrpPol","保单 " + tssrs.GetText(i,1) + " 不存在！");
                insertErrLog(tSerialNo,cGrpContNo,tssrs.GetText(i,1),"保单 " + tssrs.GetText(i,1) + " 不存在！");
                continue;
            }
            LCPolSchema tLCPolSchema = (LCPolSchema)tLCPolDB.getSchema();

            tTransferData = new TransferData();
            tTransferData.setNameAndValue("PolYear",tPolYear);
            tTransferData.setNameAndValue("PolNo",tssrs.GetText(i,1));
            tTransferData.setNameAndValue("InsuAccNo",tssrs.GetText(i,2));
            String FiscalYearEnd = mFiscalYear + "-12-31";
            tTransferData.setNameAndValue("BalaDate",FiscalYearEnd); //年度末结息
            tTransferData.setNameAndValue("ContNo", tLCPolSchema.getContNo());
            
            String tBalaDate = tssrs.GetText(i,3);
	        int tIntv = PubFun.calInterval(tBalaDate, FiscalYearEnd, "D");
	        if(tIntv<=0){
	        	//结过的就不结息了
	        	continue;
	        }
            if(!CalPolInterest(tLCPolSchema,tTransferData))
            {
                insertErrLog(tSerialNo,cGrpContNo,tLCPolSchema.getPolNo(),mErrors.getLastError());
                continue;
            }
        }
        return true;
    }

    /**
     * 计算会计年度末的账户利息
     * @param tLCPolSchema
     * @param tTransferData
     * @return
     */
    private boolean CalPolInterest(LCPolSchema tLCPolSchema,TransferData tTransferData)
    {
    	VData tVData = new VData();
    	VData ttInputData=new VData();
        String tInsuAccNo = (String)tTransferData.getValueByName("InsuAccNo");
        String tContNo = (String)tTransferData.getValueByName("ContNo");

        BqContHangUpBL tBqContHangUpBL;
        tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
//      校验保个单保全是否被挂起
		if (!tBqContHangUpBL.checkHangUpState(tContNo, "2")) {
			mErrors.copyAllErrors(tBqContHangUpBL.mErrors);
			return false;
		}

        //1-求会计年度初到会计年度末帐户每条资金轨迹的利息

        LMRiskInsuAccDB tLMRiskInsuAccDB=new LMRiskInsuAccDB();
        tLMRiskInsuAccDB.setInsuAccNo(tInsuAccNo);
        if(tLMRiskInsuAccDB.getInfo()==false)
        {
            buildError("CalPolInterest","账户描述表查询失败:"+tInsuAccNo+tLMRiskInsuAccDB.mErrors.getFirstError());
            return false;
        }
        //调用新的结息程序InsuAccBala.java
        tVData.add(tTransferData);
        tVData.add(mGlobalInput);
        InsuAccBala tInsuAccBala= new InsuAccBala();
        if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
			logger.debug("error is=="
					+ tInsuAccBala.mErrors.getErrContent());
			CError.buildErr(this, "结息计算失败！");
		}else{
			//取出计算数据并提交
			ttInputData = tInsuAccBala.getResult();
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(ttInputData, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "SelAutoAssignDutyBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
        return true;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError( );
        cError.moduleName = "AutoInterestGrpBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    private boolean insertErrLog(String cSerialNo,String cBussNo,String cOtherNo,String cMsgInfo)
    {
        LSRunDataInfoSchema tLSRunDataInfoSchema = new LSRunDataInfoSchema();
        tLSRunDataInfoSchema.setSerialNo(cSerialNo);
        tLSRunDataInfoSchema.setRunType(mRunType);
        tLSRunDataInfoSchema.setBussNo(cBussNo);
        tLSRunDataInfoSchema.setBussType("FiscalLX");                           //便于查询本程序中提示出错的数据
        tLSRunDataInfoSchema.setAssiType("");                                   //日志信息：0－运行日志、1－错误日志
        tLSRunDataInfoSchema.setOtherNo(cOtherNo);
        tLSRunDataInfoSchema.setContent(cMsgInfo);
        tLSRunDataInfoSchema.setProgName(this.getClass().getName());
        tLSRunDataInfoSchema.setOperator(mGlobalInput.Operator);
        tLSRunDataInfoSchema.setStandByFlag1(mFiscalYear);
        tLSRunDataInfoSchema.setStandByFlag2("");
        tLSRunDataInfoSchema.setMakeDate(PubFun.getCurrentDate());
        tLSRunDataInfoSchema.setMakeTime(PubFun.getCurrentTime());

        LSRunDataInfoDB tLSRunDataInfoDB = new LSRunDataInfoDB();
        tLSRunDataInfoDB.setSchema(tLSRunDataInfoSchema);
        if(!tLSRunDataInfoDB.insert())
        {
            buildError("insertErrLog",tLSRunDataInfoDB.mErrors.getFirstError());
            logger.debug("Error : " + tLSRunDataInfoDB.mErrors.getFirstError());
            return false;
        }

        return true;
    }
}
