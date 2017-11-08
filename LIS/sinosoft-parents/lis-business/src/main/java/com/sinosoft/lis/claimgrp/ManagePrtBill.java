package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;


import com.sinosoft.utility.*;
import java.io.*;
import java.net.*;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

/*******************
 * 本程序主要功能:取打印发票的模板文件名
 *
 * 传入参数:managecom
 */

public class ManagePrtBill
{
private static Logger logger = Logger.getLogger(ManagePrtBill.class);

    //取承保打印发票文件名(管理机构,flag='BQ表示保全'   'TB表示投保单')
    public String getTemplateFileName(String ManageCom,String flag)
    {
        String TemplateFileName = "";
        //新单打印发票
        if (Stringtools.stringequals(flag,"TB"))
        {
             TemplateFileName = "TBill";
        }
        //保全打印发票
        if (Stringtools.stringequals(flag,"BQ"))
        {
             TemplateFileName = "EdorPaybill";
        }
        ManageCom=ManageCom.substring(0,2);
        logger.debug("================"+ManageCom+"=============");
        String [] tManageCom={"07","13","11","14","19","25","21","22","10","32","31"};

            for (int i=0;i<tManageCom.length;i++)
            {
                if (Stringtools.stringequals(ManageCom,tManageCom[i]))
                {
                    TemplateFileName = TemplateFileName + ManageCom;
                }
            }
            TemplateFileName=TemplateFileName+".pdf";
            logger.debug("====得到发票打印模板文件名:==="+TemplateFileName);
        return TemplateFileName;
    }
    //判断目录是否存在，不存在就建立一个目录。并返回文件名。取REPRINT文件路径(关键号码，关键号码类型，realpath,打印次数)
    public String setPrintPath(String OtherNo,int PrtType,String ClientIP,String EXE,String realpath)
    {
        String FileName="";
        String str="";
         switch (PrtType)
         {
                 case 1:   //保单打印
                     str="POL"+OtherNo;                //保单号
                     break;
                 case 2:   //保单封皮
                     str="POL"+OtherNo+"_1";         //保单号
                     break;
                 case 3:   //保单被保人清单
                     str="POL"+OtherNo+"_2";         //保单号
                     break;
                 case 4:   //保单特别约定
                     str="POL"+OtherNo+"_3";         //保单号
                     break;
                 case 5:   //送达书
                     str="POL"+OtherNo+"_4";         //保单号
                     break;

                 case 20:    //保全批单打印
                     str="endor"+OtherNo;              //批单号
                     break;
                 case 21:    //保全新增被保人清单
                     str="batchinsured"+OtherNo+"_1";  //批单号
                     break;
                 case 22:    //保全发票
                     str="endorpaybill"+OtherNo;       //交费收据号
                     break;
                 case 23:    //保全领款收据
                     str="endorGetbill"+OtherNo;       //支付号
                     break;
                 case 24:    //保全收费通知书
                     str="GetNotice"+OtherNo+"_2";     //保全号
                 case 25:   //拒赔批单及正常的批单
                     str="Batchclaim"+OtherNo;           //赔案号
                     break;
                 case 26:   //赔款清单
                     str="claimdetail"+OtherNo+"_1";           //赔案号
                     break;
                 case 27:   //支付清单
                     str="claimdetail"+OtherNo+"_2";           //赔案号
                     break;
                 case 28:   //支付清单EXCEL版
                     str="GetClaim"+OtherNo+"_3";           //赔案号
                     break;
                 case 29:   //费用清单EXCEL版
                     str="CaseReceipt"+OtherNo+"_4";           //赔案号
                     break;
                 case 30:   //赔款清单EXCEL版
                     str="CaseReceipt"+OtherNo+"_5";           //赔案号
                     break;
                 case 31:   //理赔领款收据
                     str="GetBill"+OtherNo+"_6";           //赔案号
                     break;
                 case 32:    //188险种个人对帐单
                     str="180_per"+OtherNo;
                     break;
                 case 33:    //188险种团体对帐单
                     str="180_grp"+OtherNo;
                     break;
                 case 34:    //个人明细对帐单
                     str="Per"+OtherNo;    //个人险种号 PolNo
                     break;
                 case 35:    //个人明细对帐单
                     str="Per"+OtherNo;    //个人险种号 PolNo
                     break;
                 case 36:    //团体明细对帐单
                     str="GRP"+OtherNo;    //保单号 GrpContNo
                     break;
                 case 37:    //团体标准对帐单
                     str="GRP"+OtherNo;    //保单号 GrpContNo
                 case 38:    //团体t简明汇总对帐单
                     str="GRP"+OtherNo;    //保单号 GrpContNo
                     break;
                 case 39:    //个人保单
                     str="GRP"+OtherNo;    //保单号 GrpContNo
                     break;
                 case 40:    //个人保单
                     str="ClaimReceipt"+OtherNo;    //赔案号
                     break;
                 case 41:    //结案报告单
                     str="ClaimOver"+OtherNo;    //赔案号
                     break;
                 case 42:    //通融赔付通知
                     str="ClaimCircu"+OtherNo;    //赔案号
                     break;
                 case 43:    //单证通知书
                     str="ClaimSuoPei"+OtherNo;    //赔案号
                     break;
                 case 44:    //理赔单证补充通知书
                     str="ClaimCertificate"+OtherNo;    //赔案号
                     break;
                 case 45:    //理赔决定通知书
                     str="ClaimDecision"+OtherNo;    //赔案号
                     break;
                 default:
                     str="other"+OtherNo;
         }
         java.util.Date myDate=new java.util.Date();
        String FNDate=String.valueOf(myDate.getTime());
        str=str+FNDate+ClientIP+EXE;
        ExeSQL my=new ExeSQL();
        String strdate=my.getOneValue("select to_char(sysdate,'yyyymmdd') from dual");
        String strpath="Excel/ClaimGrp/"+strdate;
        String strpath1=realpath+strpath;
        File myFile=new File(strpath1);
        logger.debug("====strpath:===="+strpath+"========="+myFile.exists());
        if (!myFile.exists())
        {
            try{
                myFile.mkdir();
            }catch(Exception e)
            {
                 FileName="";
                 return FileName;
            }

        }

        FileName=strpath+"/"+str;
        return FileName;
    }

    //这样做可以少用一个表(把服务器的IP给定下来了)
    public String getServerIP()
    {
          String ServerIP="10.9.41.194";
          return ServerIP;
    }
    public int getMAXPrtTimes(String OtherNo,int PrtType)
    {
          int AXPrtTimes=0;
          ExeSQL myExerSQL=new ExeSQL();
          String sql="select max(PrtTimes) from Print where otherno='"+OtherNo+"' and prttype='"+PrtType+"'";
          AXPrtTimes=Stringtools.stringtoint(myExerSQL.getOneValue(sql));
          
          return AXPrtTimes;
    }
    //判断文件是否存在
    public String havefile(String OtherNo,int PrtType)
    {
        ExeSQL myExeSQL=new ExeSQL();
        String filename=myExeSQL.getOneValue("select strpath from Print where otherno='"+OtherNo+"' and PrtType="+PrtType+" and prtTimes=1");
        if (filename.trim().length()==0)
        {
            return "0未找到原始文件!";
        }
        File myFile=new File(filename);
        if (!myFile.exists())
        {
            return "0原始文件已经丢失！";
        }
        else
        {
            filename=myExeSQL.getOneValue("select filename from Print where otherno='"+OtherNo+"' and PrtType="+PrtType+" and prtTimes=1");
            return filename;
        }
    }

}
