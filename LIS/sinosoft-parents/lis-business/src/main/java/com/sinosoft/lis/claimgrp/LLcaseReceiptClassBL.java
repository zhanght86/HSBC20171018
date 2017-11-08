package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.LLCaseReceiptClassDB;
import com.sinosoft.lis.db.LLCaseReceiptDB;
import com.sinosoft.lis.vdb.LLCaseReceiptClassDBSet;
import com.sinosoft.lis.vdb.LLCaseReceiptDBSet;
import com.sinosoft.lis.schema.LLCaseReceiptClassSchema;
import com.sinosoft.lis.schema.LLCaseReceiptSchema;
import com.sinosoft.lis.vschema.LLCaseReceiptClassSet;
import com.sinosoft.lis.vschema.LLCaseReceiptSet;
import com.sinosoft.lis.schema.LLFeeMainSchema;
import com.sinosoft.lis.vschema.LLFeeMainSet;
//import com.sinosoft.lis.ebaprt.endorprt.Stringtools;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * 苏建栋
 * 社保帐单  收据审核
 * 2006-08-31
 * @version 1.0
 */
public class LLcaseReceiptClassBL {
private static Logger logger = Logger.getLogger(LLcaseReceiptClassBL.class);
    private String Operator="";
    private String WhereStr="";
    private String WhereStr1="";   //删除llcasereceipt中的记录用
    private String state="";
    private String RgtNo="";
    private String GrpContNo="";
    private String ID="";
    private String InsuredNo="";
    private String Name="";
    private String BillNo="";
    private String FeeItemType="";
    private String StartDate="";
    private String EndDate="";
    private String hospitalcode="";
    private String FeeItemCode="";
    private String FeeItemName="";
    private String TotalFee="";
    private String TotalAdjFee="";
    private String Reason="";
    private String receiptdate="";
    private String ManageCom="";
    private String validflag="";
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private GlobalInput mG = new GlobalInput();
    public LLcaseReceiptClassBL() {
    }
    //删除按钮
    public void DeleteSimpUW(String ID)
    {
        this.ID=ID;
        ExeSQL my=new ExeSQL();
        this.RgtNo = my.getOneValue("select ClmNo from LLCaseReceiptClass where id="+ID);
        this.BillNo = my.getOneValue("select BillNo from LLCaseReceiptClass where id="+ID);
        String strSQL="delete from LLCaseReceiptClass where id="+this.ID;
        if (!my.execUpdateSQL(strSQL))
        {
              this.state="删除LLCaseReceiptClass表信息失败！";
        }
        //修改llcasereceipt表
        this.WhereStr1="where ClmNo='"+this.RgtNo+"' and MainFeeNo='"+this.BillNo+"'";
        this.WhereStr="where ClmNo='"+this.RgtNo+"' and BillNo='"+this.BillNo+"'";
        if (!SaveToLLCaseReceipt())
        {
            this.state="修改LLCaseReceipt表失败！";
        }


    }
    public void SaveSimpUW(VData tData,String [] Data)
    {
        TransferData mTransferData=new TransferData();
        //取出前台传来的数据
        mTransferData     = (TransferData) tData.getObjectByObjectName("TransferData", 0);
        this.RgtNo        = (String) mTransferData.getValueByName("RgtNo");
        this.ID           = (String) mTransferData.getValueByName("ID");
        this.Name         = (String) mTransferData.getValueByName("Name");
        this.BillNo       = (String) mTransferData.getValueByName("BillNo");
        this.GrpContNo    = (String) mTransferData.getValueByName("GrpContNo");
        this.FeeItemType  = (String) mTransferData.getValueByName("FeeItemType");
        this.StartDate    = (String) mTransferData.getValueByName("StartDate");
        this.EndDate      = (String) mTransferData.getValueByName("EndDate");
        this.hospitalcode = (String) mTransferData.getValueByName("hospitalcode");
        this.FeeItemCode  = (String) mTransferData.getValueByName("FeeItemCode");
        this.FeeItemName  = (String) mTransferData.getValueByName("FeeItemName");
        this.TotalFee     = (String) mTransferData.getValueByName("TotalFee");
        this.TotalAdjFee  = (String) mTransferData.getValueByName("TotalAdjFee");
        this.Reason       = (String) mTransferData.getValueByName("Reason");
        this.receiptdate  = (String) mTransferData.getValueByName("receiptdate");
        this.ManageCom    = (String) mTransferData.getValueByName("ManageCom");
                logger.debug("========================ManageCom:==="+this.ManageCom);
        this.validflag    = (String) mTransferData.getValueByName("validflag");

        //打开数组开始拼写SQL
        String strSQL="";
        strSQL = "update LLCaseReceiptClass set Name='"+this.Name+"',";
        strSQL = strSQL + "BillNo='"+this.BillNo+"',";
        strSQL = strSQL + "GrpContNo='"+this.GrpContNo+"',";
        strSQL = strSQL + "FeeItemType='"+FeeItemType+"',";
        strSQL = strSQL + "StartDate=to_date('"+StartDate+"','yyyy-mm-dd'),";
        strSQL = strSQL + "EndDate=to_date('"+EndDate+"','yyyy-mm-dd'),";
        strSQL = strSQL + "hospitalcode='"+hospitalcode+"',";
        strSQL = strSQL + "FeeItemCode='"+FeeItemCode+"',";
        strSQL = strSQL + "FeeItemName='"+FeeItemName+"',";
        strSQL = strSQL + "TotalFee='"+TotalFee+"',";
        strSQL = strSQL + "TotalAdjFee='"+TotalAdjFee+"',";
        strSQL = strSQL + "Reason='"+Reason+"',";
        strSQL = strSQL + "receiptdate=to_date('"+receiptdate+"','yyyy-mm-dd'),";
        strSQL = strSQL + "validflag='"+validflag+"',";
        logger.debug("数组长度==="+Data.length);
        int tmp0=1;
        for(int tmpi=1;tmpi<Data.length;)
        {
            String k=Stringtools.inttostring(tmp0);
            strSQL = strSQL+"Col"+k+"="+Data[tmpi]+",";
            strSQL = strSQL+"Rate"+k+"="+Data[tmpi+1]+",";
            strSQL = strSQL+"AdjFee"+k+"="+Data[tmpi+2]+",";
            strSQL = strSQL+"Reason"+k+"='"+Data[tmpi+3]+"',";
            tmp0++;
            tmpi=tmpi+4;
            logger.debug("======拼SQL循环======");
        }
        strSQL = strSQL.substring(0,strSQL.length()-1);
        strSQL = strSQL+" where id="+this.ID;
        logger.debug("===strSQL==修改=="+strSQL);
        ExeSQL my=new ExeSQL();
        if (!my.execUpdateSQL(strSQL))
        {
            this.state="修改数据失败！";
        }
        //修改审核日期、审核状态、modifydate等
        strSQL = "update LLCaseReceiptClass set Uwflag=1,";
        strSQL = strSQL + "UWDate=to_char(sysdate,'yyyy-mm-dd'),";
        strSQL = strSQL + "UWOperator='"+this.Operator+"',";
        strSQL = strSQL + "ModifyDate=to_char(sysdate,'yyyy-mm-dd'),";
        strSQL = strSQL + "ModifyTime='"+PubFun.getCurrentTime()+"'";
        strSQL = strSQL+" where id="+this.ID;
        logger.debug("strSQL3="+strSQL);
        if (!my.execUpdateSQL(strSQL))
        {
            this.state="修改审核状态、审核日期、审核操作员！失败";
        }
        //修改llcasereceipt表
        this.WhereStr1="where ClmNo='"+this.RgtNo+"' and MainFeeNo='"+this.BillNo+"'";
        this.WhereStr="where ClmNo='"+this.RgtNo+"' and BillNo='"+this.BillNo+"'";
        if (!SaveToLLCaseReceipt())
        {
            this.state="修改LLCaseReceipt表失败！";
        }
        //单张审核也要修改报案表，否则后续操作有问题。
        String reportSql="update llreport set AvaliReason='01' where rptno='"+this.RgtNo+"'  ";
        if (!my.execUpdateSQL(reportSql))
        {
            this.state="修改报案类型失败！";
            logger.debug(this.state);
        }
        String subreportSql="update llsubreport set accdate=(select accdate from llcase where caseno=llsubreport.subrptno and customerno=llsubreport.customerno  ),condoleflag=(select max(to_number(seconduwflag))+1 from llcase where caseno=llsubreport.subrptno and customerno=llsubreport.customerno ) where subrptno='"+this.RgtNo+"'  ";
        if (!my.execUpdateSQL(subreportSql))
        {
            this.state="修改报案类型失败！";
            logger.debug(this.state);
        }

    }
    //批量审核入口方法
    public void SaveBatchUW(String tRgtNo,String [] data,String [] data1,String tOperator,String ManageCom1)
    {
        this.ManageCom=ManageCom1;
        logger.debug("========================ManageCom:==="+this.ManageCom);
        ExeSQL myExeSQL=new ExeSQL();
        this.RgtNo=tRgtNo;
        this.WhereStr=" where ClmNo='"+this.RgtNo+"'";
        this.WhereStr1="  where ClmNo='"+this.RgtNo+"'";
        this.Operator= tOperator;
        String sql1="select id from llcasereceiptclass "+this.WhereStr;
        SSRS tSSRS=myExeSQL.execSQL(sql1);
        for (int tmp1i=1;tmp1i<=tSSRS.getMaxRow();tmp1i++)
        {
            for (int tmpi = 1; tmpi <= 10; tmpi++) {
                String colname = "col" + Stringtools.inttostring(tmpi);
                String tsql = "select " + colname + " from llcasereceiptclass ";
                tsql = tsql + this.WhereStr+"  and id=" + tSSRS.GetText(tmp1i, 1);
                String value = myExeSQL.getOneValue(tsql);
                logger.debug("======value====:"+value);
                if (value.trim().length() == 0) {
                    tsql = "update llcasereceiptclass set " + colname + "=0.00" +
                           this.WhereStr + " and id=" + tSSRS.GetText(tmp1i, 1);
                    logger.debug("====susu===="+tsql);
                    if (!myExeSQL.execUpdateSQL(tsql))
                    {
                        this.state="修改数据失败！ID="+tSSRS.GetText(tmp1i, 1);
                    }
                }
            }
        }
        if (!UpdateLLCaseReceiptClass(data,data1))
        {
           this.state="批量审核操作失败，明细信息："+this.state;
        }
        if (!SaveToLLCaseReceipt())
        {
            logger.debug("数据转置失败!");
            this.state=this.state+"数据转置失败";
        }
    }

    //批量审核通过后生成工作流
    public boolean CreateMission(String tRgtNo)
    {
        ExeSQL tExeSQL=new ExeSQL();
        SSRS tSSRS=new SSRS();
        String reportSql="update llreport set AvaliReason='01' where rptno='"+tRgtNo+"'  ";
        if (!tExeSQL.execUpdateSQL(reportSql))
        {
            this.state="修改报案类型失败！";
            logger.debug(this.state);
            return false;
        }
        String subreportSql="update llsubreport set accdate=(select accdate from llcase where caseno=llsubreport.subrptno and customerno=llsubreport.customerno  ) where subrptno='"+tRgtNo+"'  ";
        if (!tExeSQL.execUpdateSQL(subreportSql))
        {
            this.state="修改报案出险日期失败！";
            logger.debug(this.state);
            return false;
        }
        String llSql="select grpcontno,appntno,grpname,accidentdate,mngcom,operator from llreport where rptno='"+tRgtNo+"' ";
        tSSRS=tExeSQL.execSQL(llSql);
        String tgrpcontno=tSSRS.GetText(1,1);
        String tappntno=tSSRS.GetText(1,2);
        String tgrpname=tSSRS.GetText(1,3);
        String taccidentdate=tSSRS.GetText(1,4);
        String tmngcom=tSSRS.GetText(1,5);
        String toperator=tSSRS.GetText(1,6);
        String tmissionid=PubFun1.CreateMaxNo("MISSIONID", 20);

        String missionSql="insert into lwmission values('"+tmissionid+"','1','0000000005','0000005015','1','"+tRgtNo+"','10','"+tappntno+"','"+tgrpname+"','','"+taccidentdate+"','"+tgrpcontno+"','','','','0','','','','01','','','','','"+tmngcom+"','','"+toperator+"','"+toperator+"','"+CurrentDate+"','"+CurrentTime+"','"+CurrentDate+"','"+CurrentTime+"','"+CurrentDate+"','"+CurrentTime+"','','') ";
        if (!tExeSQL.execUpdateSQL(missionSql))
        {
            this.state="保存工作流数据失败！";
            logger.debug(this.state);
            return false;
        }

        return true;
    }
    //SaveBatchUW(String RgtNo,String [] data)
    //输入条件修改表LLCaseReceiptClass
    public boolean UpdateLLCaseReceiptClass(String [] data,String [] data1)
    {

        String strSQL="";

        //修改llcasereceiptclass表  比例
        strSQL = "update LLCaseReceiptClass set ";
        int i=0;
        String stri="";
        ExeSQL my=new ExeSQL();
        for (int tmpi=0;tmpi<data.length;tmpi++)
        {
            i=tmpi+1;
            stri=Stringtools.inttostring(i);
            strSQL = strSQL + " Rate"+stri+"="+data[tmpi]+",";
            strSQL = strSQL + "AdjFee"+stri+" = Col"+stri+" * Rate"+stri+",";
        }
        strSQL = strSQL.substring(0,strSQL.length()-1);
        strSQL = strSQL+"  "+this.WhereStr;
        logger.debug("strSQL1="+strSQL);

        if (!my.execUpdateSQL(strSQL))
        {
            this.state="修改合理费用比例失败！";
            return false;
        }
        //修改扣除原因
        strSQL = "update LLCaseReceiptClass set ";
        i=0;
        stri="";

        for (int tmpi=0;tmpi<data.length;tmpi++)
        {
            i=tmpi+1;
            stri=Stringtools.inttostring(i);
            strSQL = strSQL + " reason"+stri+"='"+data1[tmpi]+"',";
        }
        strSQL = strSQL.substring(0,strSQL.length()-1);
        strSQL = strSQL+"  "+this.WhereStr;
        logger.debug("strSQL1="+strSQL);

        if (!my.execUpdateSQL(strSQL))
        {
            this.state="修改扣除原因失败！";
            return false;
        }

       //修改分公司未定义的字段的值为0
        strSQL = "update LLCaseReceiptClass set ";
        for (int tmpi=data.length+1;tmpi<=10;tmpi++)
        {
            stri=Stringtools.inttostring(tmpi);
            strSQL = strSQL + "AdjFee"+stri+" = 0.00,";
            strSQL = strSQL + " Rate"+stri+" = 0.00,";
            strSQL = strSQL + " Col"+stri+" = 0.00,";
        }
        strSQL = strSQL.substring(0,strSQL.length()-1);
        strSQL = strSQL+"  "+this.WhereStr;
        logger.debug("strSQL2="+strSQL);
        if (!my.execUpdateSQL(strSQL))
        {
            this.state="修改未定义字段值为0失败！";
            return false;
        }
        //修改总费用
        strSQL="update LLCaseReceiptClass set TotalFee=";
        for (int tmpi=1;tmpi<=10;tmpi++)
        {
            strSQL = strSQL + "Col"+Stringtools.inttostring(tmpi)+"+";
        }
        strSQL = strSQL.substring(0,strSQL.length()-1);
        strSQL = strSQL+"  "+this.WhereStr;
        logger.debug("strSQL3="+strSQL);
        if (!my.execUpdateSQL(strSQL))
        {
            this.state="修改总费用失败！";
            return false;
        }

        //修改总合理费用
        strSQL="update LLCaseReceiptClass set TotalAdjFee=";
        for (int tmpi=1;tmpi<=10;tmpi++)
        {
            strSQL = strSQL + "AdjFee"+Stringtools.inttostring(tmpi)+"+";
        }
        strSQL = strSQL.substring(0,strSQL.length()-1);
        strSQL = strSQL+"  "+this.WhereStr;
        logger.debug("strSQL3="+strSQL);
        if (!my.execUpdateSQL(strSQL))
        {
            this.state="修改总合理费用失败！";
            return false;
        }
        //修改审核日期、审核状态、modifydate等
        strSQL = "update LLCaseReceiptClass set Uwflag=1,";
        strSQL = strSQL + "UWDate=to_char(sysdate,'yyyy-mm-dd'),";
        strSQL = strSQL + "UWOperator='"+this.Operator+"',";
        strSQL = strSQL + "ModifyDate=to_char(sysdate,'yyyy-mm-dd'),";
        strSQL = strSQL + "ModifyTime='"+PubFun.getCurrentTime()+"'";
        strSQL = strSQL+"  "+this.WhereStr;
        logger.debug("strSQL3="+strSQL);
        if (!my.execUpdateSQL(strSQL))
        {
            this.state="审核状态、审核日期、审核操作员！";
            return false;
        }
        return true;
    }
    public String getState()
    {
        this.state=this.state;
        return this.state;
    }

    //数据转存至llcasereceipt表
    public boolean SaveToLLCaseReceipt()
    {
        String strSQL="";
                    ExeSQL my = new ExeSQL();
          //修改改总合理费用(无效保单的)
            strSQL="update LLCaseReceiptClass set TotalAdjFee=0";
            strSQL = strSQL+"  "+this.WhereStr;
            strSQL = strSQL+"  and validflag=0";
            if (!my.execUpdateSQL(strSQL))
            {
                this.state="修改无效收据总合理费用为0失败！";
                return false;
            }
            //修改扣除原因(无效保单的)====>改为保险期外
              strSQL="update LLCaseReceiptClass set Reason='保单有效期外',Reason1='',";
              strSQL = strSQL+"Reason2='',";
              strSQL = strSQL+"Reason3='',";
              strSQL = strSQL+"Reason4='',";
              strSQL = strSQL+"Reason5='',";
              strSQL = strSQL+"Reason6='',";
              strSQL = strSQL+"Reason7='',";
              strSQL = strSQL+"Reason8='',";
              strSQL = strSQL+"Reason9='',";
              strSQL = strSQL+"Reason10=''";
              strSQL = strSQL+"  "+this.WhereStr;
              strSQL = strSQL+"  and validflag=0";
              if (!my.execUpdateSQL(strSQL))
              {
                  this.state="修改无效收据总合理费用为0失败！";
                  return false;
              }
              //修改费用类型
              strSQL="update llcasereceiptclass ";
              strSQL=strSQL+"set feeitemname=(select codename from ldcode where codetype='llfeeitemtype' and llcasereceiptclass.feeitemcode=code )";
              strSQL=strSQL +this.WhereStr ;
              strSQL=strSQL +"  and feeitemcode is not null" ;
              if (!my.execUpdateSQL(strSQL))
              {
                  this.state="修改费用类型(feeitemname)失败！";
                  return false;
              }


        try{
            strSQL="delete from llcasereceipt "+this.WhereStr1;
            my.execUpdateSQL(strSQL);     //删除上次批审核的记录
            strSQL="";
            strSQL =
                    "select ClmNo,BillNo,FeeItemType,FeeItemCode,FeeItemName,TotalFee, ";
            strSQL = strSQL +
                    "TotalAdjFee,StartDate,EndDate,id,MngCom,Operator,MakeDate,MakeTime";
            strSQL = strSQL +
                     ",ModifyDate,ModifyTime,totalFee-totaladjfee,InsuredNo";
            strSQL = strSQL + "         from LLCaseReceiptClass " +
                     this.WhereStr;
            strSQL = strSQL + " and UWflag='1' ";//and ValidFlag=1";

            SSRS tSSRS = my.execSQL(strSQL);
            try{
                int Row=tSSRS.getMaxRow();
                logger.debug("需要转存的记录数："+Row);
            }catch(Exception e)
            {

                return false;
            }
            LLCaseReceiptSet mLLCaseReceiptset = new  LLCaseReceiptSet();
            LLFeeMainSet mLLFeeMainSet = new  LLFeeMainSet();

                for (int tmpi = 1; tmpi <= tSSRS.getMaxRow(); tmpi++) {
                    LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
                    LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();

                    String id = tSSRS.GetText(tmpi, 10);
                    tLLCaseReceiptSchema.setClmNo(tSSRS.GetText(tmpi, 1));
                    tLLCaseReceiptSchema.setCaseNo(tSSRS.GetText(tmpi, 1));
                    tLLCaseReceiptSchema.setMainFeeNo(tSSRS.GetText(tmpi, 2));
                    tLLCaseReceiptSchema.setFeeDetailNo(PubFun1.CreateMaxNo(
                            "FeeDetailNo", 10));
                    tLLCaseReceiptSchema.setRgtNo(tSSRS.GetText(tmpi, 1));
                    tLLCaseReceiptSchema.setFeeItemType(tSSRS.GetText(tmpi, 3));
                    String FeeItemCode = tSSRS.GetText(tmpi, 4);
                    if (FeeItemCode == null || FeeItemCode.equals("")) {
                        FeeItemCode = "01";
                    }
                    String FeeItemName = tSSRS.GetText(tmpi, 5);
                    if (FeeItemName == null || FeeItemName.equals("")) {
                        FeeItemName = "西药费";
                    }
                    tLLCaseReceiptSchema.setFeeItemCode(FeeItemCode);
                    tLLCaseReceiptSchema.setFeeItemName(FeeItemName);
                    tLLCaseReceiptSchema.setFee(tSSRS.GetText(tmpi, 6));
                    tLLCaseReceiptSchema.setAdjSum(tSSRS.GetText(tmpi, 7));
                    tLLCaseReceiptSchema.setAdjReason("1");
                    //计算一下扣除原因的串
                    int colcount=Stringtools.stringtoint(my.getOneValue("select count(*) from ldcode1 where codetype='colname' and code='"+this.ManageCom+"'"));
                    logger.debug("========================ManageCom:==="+this.ManageCom);
                    String ReasonSQL="select ";
                     for (int tmpk=1;tmpk<=colcount;tmpk++)
                     {
                         ReasonSQL=ReasonSQL+"Reason"+Stringtools.inttostring(tmpk)+",";
                     }
                      ReasonSQL=ReasonSQL+"Reason  from LLCaseReceiptClass ";
                       ReasonSQL= ReasonSQL+" where id="+id;
                     SSRS ReasonSSRS=my.execSQL(ReasonSQL);
                     String strReason="";
                     for (int k=1;k<=ReasonSSRS.getMaxCol();k++)
                     {
                         strReason=strReason+"  "+ReasonSSRS.GetText(1,k);
                     }
                    tLLCaseReceiptSchema.setAdjRemark(strReason);
                    logger.debug("========1===========");
                    tLLCaseReceiptSchema.setStartDate(tSSRS.GetText(tmpi, 8));
                    logger.debug("========2===========");
                    tLLCaseReceiptSchema.setEndDate(tSSRS.GetText(tmpi, 9));
                    String DayCount = my.getOneValue(
                            "select enddate-startdate from llcasereceiptclass where id=" +
                            id);
                    if (DayCount == null)
                    {
                        DayCount = "1";
                    }
                    logger.debug("==============3==============");
                    tLLCaseReceiptSchema.setDayCount(DayCount);
                    tLLCaseReceiptSchema.setDealFlag("1");//全部为1，已经由开发商确认
                    tLLCaseReceiptSchema.setAvaliFlag("1"); //不需要给值，已经由开发商确认
                    tLLCaseReceiptSchema.setMngCom(tSSRS.GetText(tmpi, 11));
                    tLLCaseReceiptSchema.setOperator(tSSRS.GetText(tmpi, 12));
                    tLLCaseReceiptSchema.setMakeDate(tSSRS.GetText(tmpi, 13));
                    String MakeTime=tSSRS.GetText(tmpi, 14);
                    if (MakeTime==null)
                    {
                        MakeTime="";
                    }
                    if (MakeTime.equals(""))
                    {
                        MakeTime="00:00:00";
                    }

                    tLLCaseReceiptSchema.setMakeTime(MakeTime);
                    tLLCaseReceiptSchema.setModifyDate(tSSRS.GetText(tmpi, 15));
                    String ModifyTime=tSSRS.GetText(tmpi, 16);
                    if (ModifyTime==null)
                    {
                        ModifyTime="";
                    }
                    if (ModifyTime.equals(""))
                    {
                        ModifyTime="00:00:00";
                    }
                    tLLCaseReceiptSchema.setModifyTime(ModifyTime);
                    logger.debug("==============3==============");
                    tLLCaseReceiptSchema.setPreAmnt(0.00);
                    tLLCaseReceiptSchema.setSelfAmnt(0.00);
                    tLLCaseReceiptSchema.setRefuseAmnt(tSSRS.GetText(tmpi, 17));
                    tLLCaseReceiptSchema.setDiseaseCode("");
                    tLLCaseReceiptSchema.setDiseaseName("");
                    tLLCaseReceiptSchema.setSecurityAmnt(0.00);
                    tLLCaseReceiptSchema.setCutApartAmnt(0.00);
                    tLLCaseReceiptSchema.setCustomerNo(tSSRS.GetText(tmpi, 18));
                    tLLCaseReceiptSchema.setHospLineAmnt(0.00);
                    tLLCaseReceiptSchema.setHospNum(0);
                    logger.debug("===========第"+tmpi+"个循环=================");
                    mLLCaseReceiptset.add(tLLCaseReceiptSchema);

                    //写llfeemain,立案初始化时会用
                    tLLFeeMainSchema.setClmNo(tSSRS.GetText(tmpi, 1));
                    tLLFeeMainSchema.setCaseNo(tSSRS.GetText(tmpi, 1));
                    tLLFeeMainSchema.setMainFeeNo(tSSRS.GetText(tmpi, 2));
                    tLLFeeMainSchema.setCustomerNo(tSSRS.GetText(tmpi, 18));
                    tLLFeeMainSchema.setMngCom(tSSRS.GetText(tmpi, 11));
                    tLLFeeMainSchema.setOperator(tSSRS.GetText(tmpi, 12));
                    tLLFeeMainSchema.setMakeDate(tSSRS.GetText(tmpi, 13));
                    tLLFeeMainSchema.setMakeTime(MakeTime);
                    tLLFeeMainSchema.setModifyDate(tSSRS.GetText(tmpi, 13));
                    tLLFeeMainSchema.setModifyTime(MakeTime);
                    mLLFeeMainSet.add(tLLFeeMainSchema);


                }
                  logger.debug("==============4==============");
                logger.debug("==============5==============");
                MMap map = new MMap();
                map.put(mLLCaseReceiptset, "DELETE&INSERT");
                map.put(mLLFeeMainSet, "DELETE&INSERT");
                VData  mvdata=new VData();
                mvdata.add(map);
                PubSubmit tPubSubmit = new PubSubmit();
                if (!tPubSubmit.submitData(mvdata, "DELETE&INSERT"))
                {
                    logger.debug("==========22222222====false=========");
                    this.state = " 数据提交失败！";
                    return false;
                }

                logger.debug("==============6==============");
        }catch(Exception e)
        {
            this.state="写表LLCaseReceipt出错,信息"+e.getMessage();
            return false;
        }
        return true;
    }

}
