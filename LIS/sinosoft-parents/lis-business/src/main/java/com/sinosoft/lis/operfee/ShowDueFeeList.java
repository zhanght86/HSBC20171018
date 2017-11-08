package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author GUOXIANG
 * @version 1.0
 */




























import java.util.*;
import java.text.*;
import java.lang.*;
import java.io.*;

import com.f1j.ss.*;
import com.f1j.util.p;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class ShowDueFeeList {
private static Logger logger = Logger.getLogger(ShowDueFeeList.class);

  public CErrors mErrors=new CErrors();
  private VData mResult = new VData();

  private GlobalInput mGlobalInput =new GlobalInput() ;
  private String mOperate="";
  private String mManageCom="";
  private String mStartDate="";
  private String mEndDate="";
  private String mPolType="";
  private String mOperType = "";     //保单统计范围：全部或者续保件
  private String mSaleChnl = "";      //销售渠道
  private String mSaleChnlSql = "";      //销售渠道SQL
//  private String subOperf="N";     //附加险续期标志
  private TransferData mTransferData=new TransferData();
  private LCPolSet mSubOperfSet = new LCPolSet();  //附加险做续期的保单集合
  private BookModelImpl m_book = new BookModelImpl();
  private String  mFileModeDesc = "DueFeeMode.xls"; //描述的模版名称
  private String  mFilePathDesc = "XSCreatListPath";  //描述的文件路径
  private String mXSExcTemplate = ""; //描述的模版读取路径
  private String  mFilePath     = "";  //通过描述得到的文件路径
  private String  mFileName    = "";  //要生成的文件名
  private String  mRelationFlag="0"; //续期和续保是否关联标记 0-不相关 1-相关
  private int mCurrentRow=1; //行数
  private int mCount=100; //每次循环处理的纪录数
  private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");

  public ShowDueFeeList()
  {
  }

  public static void main(String[] args)
  {
      ShowDueFeeList tShowDueFeeList =new ShowDueFeeList();
      VData tVData=new VData();
      TransferData tTransferData =new TransferData();
      tTransferData.setNameAndValue("ManageCom","86");
      tTransferData.setNameAndValue("StartDate","2004-10-1");
      tTransferData.setNameAndValue("EndDate","2004-10-11");
      tTransferData.setNameAndValue("PolType","0");
      GlobalInput tGI=new GlobalInput();
      tGI.ComCode="86";
      tGI.Operator="001";
      tVData.add(tTransferData);
      tVData.add(tGI);
      tShowDueFeeList.submitData(tVData,"");

  }

  /**
   * 传输数据的公共方法
   * @param cInputData
   * @param cOperate
   * @return
   */
  public boolean submitData(VData cInputData, String cOperate) {

      mOperate = cOperate;

      if( getInputData(cInputData)==false )
          return false;

      if(dealData()==false)
          return false;

      return true;

  }

  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
      mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
      mGlobalInput = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0);

      if(mTransferData==null||mGlobalInput==null)
      {
          buildError("getInputData","没有得到传入的数据");
          return false;
      }
      mManageCom=(String)mTransferData.getValueByName("ManageCom");
      mStartDate=(String)mTransferData.getValueByName("StartDate");
      mEndDate=(String)mTransferData.getValueByName("EndDate");
      mPolType=(String)mTransferData.getValueByName("PolType");//在职-1/孤儿单-0
      mOperType = (String)mTransferData.getValueByName("OperType");  //统计范围：全部或者续保件
      mSaleChnl = (String)mTransferData.getValueByName("SaleChnl");  //销售渠道
      mXSExcTemplate = (String) mTransferData.getValueByName("XSExcTemplate");
      logger.debug("mSaleChnl: "+mSaleChnl);
      if(mSaleChnl!=null && !"".equals(mSaleChnl))
      {
        mSaleChnlSql = "and Salechnl='?mSaleChnl?' ";
      }
      logger.debug("mSaleChnlSql: "+mSaleChnlSql);
      logger.debug("mOperType: "+mOperType);
      if(mStartDate==null||mEndDate==null||mStartDate.equals("")||mEndDate.equals(""))
      {
          buildError("getInputData","没有得到起始日期和终止日期");
          return false;
      }
      int d=PubFun.calInterval(mStartDate,mEndDate,"D");
      if(d<0)
      {
          buildError("getInputData","起始日期不能小于终止日期");
          return false;
      }
      if(mPolType==null||mPolType.equals(""))
      {
          buildError("getInputData","必须录入在职或孤儿单标记");
          return false;
      }
      if(mManageCom==null||mManageCom.equals("")) mManageCom=mGlobalInput.ComCode;

      return true;
  }

  public VData getResult()
  {
      return this.mResult;
  }

  private void buildError(String szFunc, String szErrMsg)
  {
      CError cError = new CError( );
      cError.moduleName = "ShowDueFeeList";
      cError.functionName = szFunc;
      cError.errorMessage = szErrMsg;
      this.mErrors.addOneError(cError);
  }

  /**
   * 准备所有要打印的数据
   * @return
   */
  private boolean dealData()
  {
      if(checkDesc()==false)
          return false;

      try
      {
//          mFilePath = "d://temp//";
          //得到指定路径下的模版文件--其实还是不知道如何直接得到excel文件
          m_book.read(mXSExcTemplate+mFileModeDesc,new com.f1j.ss.ReadParams());
//          m_book.read("E:/temp/"+mFileModeDesc ,new com.f1j.ss.ReadParams());
          m_book.setSheetSelected(0,true);
          m_book.setCol(0);
          //如果统计全部保单
          if("0".equals(mOperType))
          {
            //处理需要续期缴费的主险保单
            if(dealDueMainPol()==false)
              return false;
          }
           //处理需要续保的主险保单
           if(dealRnewMainPol()==false)
              return false;

           if("0".equals(mOperType))
           {
             //处理需要续保的附加险，其主险不需要续期缴费和续保
             if(dealRnewSpecSubPol()==false)
               return false;
           }
           //modify by xiongzh 增加报表生成日期，生成工号
           m_book.setEntry(mCurrentRow,0,"制表时间:"+PubFun.getCurrentDate());//制表时间
           m_book.setEntry(mCurrentRow,1,"制表人:"+this.mGlobalInput.Operator);//制表人
          //生成文件
            // 若该路径不存在则生成路径
		    File tFilePat = new File(mFilePath);
			if (!tFilePat.exists()) {
				tFilePat.mkdirs();
			}
          //mFilePath= "d://temp//";
          m_book.write(mFilePath+mFileName,new com.f1j.ss.WriteParams(com.f1j.ss.BookModelImpl.eFileExcel97));
//          m_book.write("E:/temp/"+mFileName,new com.f1j.ss.WriteParams(com.f1j.ss.BookModelImpl.eFileExcel97));
      }
      catch(Exception ex)
      {
          buildError("FormateFile","操作失败："+ex);
          return false;
      }

      return true;
  }

  /**
   * 得到文件名
   * @return
   */
  private String getFileName()
  {
      String StartDate[]=PubFun.split(mStartDate,"-");
      String EndDate[]=PubFun.split(mEndDate,"-");
      //文件名=DueFeeList+"_"+操作员代码+"_"+起始年月日+终止年月日+.xls.z
      String filename="DueFeeList_";
      filename= filename+StartDate[0];
      if(StartDate[1].length()==1) filename= filename+"0";
      filename= filename+StartDate[1];
      if(StartDate[2].length()==1) filename= filename+"0";
      filename= filename+StartDate[2];
      filename= filename+"_";
      filename= filename+EndDate[0];
      if(EndDate[1].length()==1) filename= filename+"0";
      filename= filename+EndDate[1];
      if(EndDate[2].length()==1) filename= filename+"0";
      filename= filename+EndDate[2];
      filename= filename+"_";
      filename= filename+mManageCom+"_"+mPolType+"_"+mOperType+"_"+mSaleChnl;
      filename= filename+".xls.z";
      logger.debug("生成文件名:"+filename);
      return filename;
  }

  private String GetAgentGroup(LCPolSchema tLCPolSchema){
	  String tAgentGroup="";
	  String tAgentCode =tLCPolSchema.getAgentCode();
	  //查询代理人相应的服务部信息
	  ExeSQL tExeSQL =new ExeSQL();
	  SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	  sqlbv.sql("select agentgroup from laagent where agentcode ='?tAgentCode?'");
	  sqlbv.put("tAgentCode", tAgentCode);
	  tAgentGroup =tExeSQL.getOneValue(sqlbv);
	  if("".equals(tAgentGroup)){
		  tAgentGroup =tLCPolSchema.getAgentGroup();
	  }
	  return tAgentGroup;
  }
  private String getPolState(String tContNo,String tPolNo){
    ExeSQL tExeSQL  = new ExeSQL();
    String tPolState = "select statetype,state from lccontstate where contno='?tContNo?' and (polno='?tPolNo?' or polno='000000') order by startdate desc";
    SSRS tSSRS = new SSRS();
    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    sqlbv1.sql(tPolState);
    sqlbv1.put("tContNo", tContNo);
    sqlbv1.put("tPolNo", tPolNo);
    tSSRS = tExeSQL.execSQL(sqlbv1);
    if (tSSRS.getMaxRow()>0){
      if (tSSRS.GetText(1,2).equals("1")){
        if (tSSRS.GetText(1,1).equals("Available")){
          tPolState = "失效";
          return tPolState;
        }
        if (tSSRS.GetText(1,1).equals("PayPrem")){
          tPolState = "垫交";
          return tPolState;
        }
        if (tSSRS.GetText(1,1).equals("Terminate")){
          tPolState = "终止";
          return tPolState;
        }
        if (tSSRS.GetText(1,1).equals("Loan")){
          tPolState = "有效";
          return tPolState;
        }
        if (tSSRS.GetText(1,1).equals("Lost")){
          tPolState = "有效";
          return tPolState;
        }
        tPolState = "其他";
        return tPolState;
      }else{
        tPolState = "有效";
      }
    }else{
      tPolState = "未知";
    }
    return tPolState;
  }
  /**
   * 添加每个保单的信息-针对续期主险
   * @param tLCPolSchema
   * @param t_book
   * @param n
   * @return
   */
  private boolean dealSinglePol(LCPolSchema tLCPolSchema)
  {
      int t=0;
      int subNum=0;
      String AgentName="";//业务员姓名
      String InsuName="";//被保人姓名
      String AppName="";   //  客户姓名
      String SamePerson="N";
      double tLeavingMoney =0; //原余额
      double tNewLeavingMoney =0;//新余额
      double tActualPayMoney = 0;//实交保费
      boolean tIndFeeFlag = true;//已经存在应收
      double mainPolPrem=0; //主险保费
      try
      {
          if(String.valueOf(tLCPolSchema.getLeavingMoney())!= null)
          {
            tLeavingMoney = tLeavingMoney + tLCPolSchema.getLeavingMoney();
          }
          AgentName=ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode()).trim();
          AppName=tLCPolSchema.getAppntName().trim();
          InsuName=tLCPolSchema.getInsuredName().trim();
          if(AgentName.equals(AppName)||AgentName.equals(InsuName))
          {
              SamePerson="Y";
          }

          LCContSchema tLCContSchema = new LCContSchema();
          LCContDB tLCContDB = new LCContDB();
          tLCContDB.setContNo(tLCPolSchema.getContNo());
          if(!tLCContDB.getInfo())
          {
          	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
          	return false;
          }
          tLCContSchema = tLCContDB.getSchema();

          //如有数据错误，可以写在excel里
          m_book.setEntry(mCurrentRow,t+0,tLCPolSchema.getManageCom());//管理机构
          m_book.setEntry(mCurrentRow,t+1,AgentName);//业务员姓名
          m_book.setEntry(mCurrentRow,t+2,tLCPolSchema.getAgentCode());//业务员代码
          String AgentGroup=GetAgentGroup(tLCPolSchema);
          String AgentBranch=findAgentBranch(AgentGroup);
          m_book.setEntry(mCurrentRow,t+3,AgentBranch);//业务员组别
          m_book.setEntry(mCurrentRow,t+4,AppName);//客户姓名
          
          String SQL_xqremind="select (case xqremindflag when '0' then 'N' when '1' then 'Y' else 'Y' end) from lccont where contno='?contno?'" ;
          ExeSQL mExeSQL = new ExeSQL();
          SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
          sqlbv2.sql(SQL_xqremind);
          sqlbv2.put("contno", tLCPolSchema.getContNo());
          SSRS mSSRS=mExeSQL.execSQL(sqlbv2);
          if(mSSRS!=null&&mSSRS.getMaxRow()>0)
          {
              m_book.setEntry(mCurrentRow,t+5,mSSRS.GetText(1, 1));//续期缴费提示
          }
          else{
              m_book.setEntry(mCurrentRow,t+5,"");//续期缴费提示
              }
          
          m_book.setEntry(mCurrentRow,t+6,tLCPolSchema.getContNo());//保单号
          m_book.setEntry(mCurrentRow,t+7,tLCPolSchema.getPaytoDate());//缴费日期
          String PayMode=findPayMode(tLCPolSchema.getContNo(),tLCContSchema.getPayLocation());
          m_book.setEntry(mCurrentRow,t+8,String.valueOf(PayMode));//缴费方式
          m_book.setEntry(mCurrentRow,t+9,ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode()));//主险险种
          //得到主险保费
          LJSPayDB tLJSPayDB=new LJSPayDB();
          tLJSPayDB.setOtherNo(tLCPolSchema.getContNo());
          tLJSPayDB.setOtherNoType("2");
          LJSPaySet tLJSPaySet=tLJSPayDB.query();
          if(tLJSPaySet==null||tLJSPaySet.size()==0)
          {
             tIndFeeFlag=false; //主险续期未催收
          }
          if(!tIndFeeFlag)
          {
           m_book.setEntry(mCurrentRow,t+10,"主险续保保费未催收："+ getRnewFailReason(tLCPolSchema));//主险催收失败的原因
          }
          else
          {
            mainPolPrem=getMainPolPrem(tLCPolSchema.getPolNo());
            m_book.setEntry(mCurrentRow,t+10,String.valueOf(mainPolPrem));//主险保费
          }

          double sumprem=0.0;//保费合计
          sumprem=sumprem+mainPolPrem;
          //如果主险续期和附加险自动续保关联-查询需要续保的附加险
          if(!mRelationFlag.equals("0"))
          {
              String prem="0";
              LCPolSet tLCPolSet=findSubPol(tLCPolSchema);
              int i=0;
//              int j=0;
              if(tLCPolSet.size()>0)
              {
                  //如果多于一条，则在excel的下一行的同一列位置显示，同时mSubNum增长
                  for(i=1;i<=tLCPolSet.size();i++)
                  {
                      if(String.valueOf(tLCPolSet.get(i).getLeavingMoney())!= null)
                      {
                        tLeavingMoney = tLeavingMoney + tLCPolSet.get(i).getLeavingMoney();
                      }
                      String SQL_SubPol="select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSPayPerson where riskcode='?riskcode?' and PayType='ZC'" ;
                      SQL_SubPol=SQL_SubPol+" and getnoticeno in (select getnoticeno from ljspay where othernotype='2' and otherno='?otherno?')";
                      SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
                      sqlbv3.sql(SQL_SubPol);
                      sqlbv3.put("riskcode", tLCPolSet.get(i).getRiskCode());
                      sqlbv3.put("otherno", tLCPolSchema.getContNo());
                      ExeSQL tExeSQL = new ExeSQL();
                      SSRS tSSRS=tExeSQL.execSQL(sqlbv3);
                      if(tSSRS!=null&&tSSRS.getMaxRow()>0)
                      {
                          prem=tSSRS.GetText(1,1);
                      }
                      m_book.setEntry(mCurrentRow,t+11 +2*(i-1),ChangeCodetoName.getRiskName(tLCPolSet.get(i).getRiskCode()));//附险险种
                      m_book.setEntry(mCurrentRow,t+12 +2*(i-1),prem);//附险保费
                      sumprem=sumprem+Double.parseDouble(prem) ;
                  }

              }
          }

          m_book.setEntry(mCurrentRow,t+25,mDecimalFormat.format(sumprem));//应收保费合计
          m_book.setEntry(mCurrentRow,t+26,mDecimalFormat.format(tLeavingMoney));//预收费合计
          if(tLeavingMoney-sumprem >0 )
          {
           tActualPayMoney = 0;
           tNewLeavingMoney = tLeavingMoney-sumprem;
          }
          else
          {
           tActualPayMoney = sumprem - tLeavingMoney;
           tNewLeavingMoney = 0;
          }
          m_book.setEntry(mCurrentRow,t+27,mDecimalFormat.format(tActualPayMoney));//实交保费合计
          m_book.setEntry(mCurrentRow,t+28,mDecimalFormat.format(tNewLeavingMoney));//余额扣除本次保费合计

          //投保人信息
          LCAppntDB tLCAppntDB = new LCAppntDB();
          LCAddressDB tLCAddressDB = new LCAddressDB();
          tLCAppntDB.setContNo(tLCPolSchema.getContNo());
          tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
          if (tLCAppntDB.getInfo() == true)
          {
        	tLCAddressDB = new LCAddressDB();
        	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
        	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
        	if (tLCAddressDB.getInfo() == true)
            {
    			m_book.setEntry(mCurrentRow, t + 29,
    					tLCAddressDB.getPostalAddress()); //收费地址
    			m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
    			m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
            }
          }


          m_book.setEntry(mCurrentRow,t+32,tLCPolSchema.getInsuredName());//被保人姓名
          String BankInfo[]=findBankInfo(tLCPolSchema);
          m_book.setEntry(mCurrentRow,t+33,BankInfo[0]);//银行帐号
          m_book.setEntry(mCurrentRow,t+34,ChangeCodetoName.getBankCodeName(BankInfo[1]));//银行编码
          m_book.setEntry(mCurrentRow,t+35,BankInfo[2]);//户名

          LAAgentDB tLAAgentDB=new LAAgentDB();
          tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
          String AgentTel="";
          if(tLAAgentDB.getInfo()==true)
          {
              AgentTel=tLAAgentDB.getPhone();
              if(AgentTel==null||AgentTel.equals(""))
                  AgentTel=tLAAgentDB.getMobile();
              if(AgentTel==null)
                  AgentTel="";
          }
          m_book.setEntry(mCurrentRow,t+36,AgentTel);//代理人电话

          ExeSQL tExeSQL = new ExeSQL();
          String tSql = "select max(PayTimes) from LCPrem where PolNo='?PolNo?'";
          SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
          sqlbv4.sql(tSql);
          sqlbv4.put("PolNo", tLCPolSchema.getPolNo());
          String tPayTimes = tExeSQL.getOneValue(sqlbv4);
          m_book.setEntry(mCurrentRow,t+37,tPayTimes);//交费期数
          m_book.setEntry(mCurrentRow,t+38,ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex()));//性别
          m_book.setEntry(mCurrentRow,t+39,ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl()));//销售渠道
          m_book.setEntry(mCurrentRow,t+40,SamePerson);//代理人和投保人和被保人是否同一人标记
          if(tLCPolSchema.getAgentCom()!=null && !"".equals(tLCPolSchema.getAgentCom()))
           {
            tExeSQL  = new ExeSQL();
            String tAgentName = "select Name from LACom where AgentCom = '?AgentCom?'";
            SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
            sqlbv5.sql(tAgentName);
            sqlbv5.put("AgentCom", tLCPolSchema.getAgentCom());
            m_book.setEntry(mCurrentRow,t+41,tExeSQL.getOneValue(sqlbv5));//代理机构
          }
//          m_book.setEntry(mCurrentRow,t+28,tLCPolSchema.getAgentCom());//代理机构
          m_book.setEntry(mCurrentRow,t+42,tLCAddressDB.getMobile());//投保人手机
          m_book.setEntry(mCurrentRow,t+43,String.valueOf(tLCPolSchema.getPrtNo()));//印刷号
          m_book.setEntry(mCurrentRow,t+44, tLCAddressDB.getHomePhone());//家电
          m_book.setEntry(mCurrentRow,t+45, tLCAddressDB.getCompanyPhone());//公司电
          m_book.setEntry(mCurrentRow,t+46,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
      }
      catch(Exception ex)
      {
          try
          {
              m_book.setEntry(mCurrentRow,t+0,"***处理保单"+tLCPolSchema.getPolNo()+" 出错："+ex);//管理机构
              mCurrentRow=mCurrentRow+1;   //处理完每条纪录后添加一行
          }
          catch(Exception ex2)
          {
              buildError("dealSinglePol",ex.toString());
              return false;
          }
          return true;
      }
//      mCurrentRow=mCurrentRow+subNum;//如果有超过1个以上附加险，添加新的行存放第二个（以上）附加险信息
      mCurrentRow=mCurrentRow+1;   //处理完每条纪录后添加一行
      return true;
  }

  /**
  * 得到催收失败的原因
  * @param tLCPolSchema
  * @return 催收失败的原因
  */
  private String getRnewFailReason(LCPolSchema tLCPolSchema)
  {
    String tReason = "";
    ExeSQL tExeSQL = new ExeSQL();
    String tSQL = "";
    if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    tSQL = "select ErrInfo from LCRnewErrLog where PrtNo='?PrtNo?' and Riskcode='?Riskcode?' and ErrNo=1 and Rownum=1 ";
    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    	tSQL = "select ErrInfo from LCRnewErrLog where PrtNo='?PrtNo?' and Riskcode='?Riskcode?' and ErrNo=1 limit 0,1 ";	
    }
    SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
    sqlbv6.sql(tSQL);
    sqlbv6.put("PrtNo", tLCPolSchema.getPrtNo());
    sqlbv6.put("Riskcode", tLCPolSchema.getRiskCode());
    tReason = tExeSQL.getOneValue(sqlbv6);
    return tReason;
  }
  /**
   * 添加每个保单的信息-针对自动续保主险
   * @param tLCPolSchema
   * @param t_book
   * @param n
   * @return
   */
  private boolean dealSingleRnewPol(LCPolSchema tLCPolSchema)
  {
      int t=0;
      int subNum=0;
      String AgentName="";//业务员姓名
      String InsuName="";//被保人姓名
      String AppName="";   //  客户姓名
      String SamePerson="N";
      double tLeavingMoney =0; //原余额
      double tNewLeavingMoney =0;//新余额
      double tActualPayMoney = 0;//实交保费
      try
      {
          if(String.valueOf(tLCPolSchema.getLeavingMoney())!= null)
          {
            tLeavingMoney = tLeavingMoney + tLCPolSchema.getLeavingMoney();
          }
          AgentName=ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode()).trim();
          AppName=tLCPolSchema.getAppntName().trim();
          InsuName=tLCPolSchema.getInsuredName().trim();
          if(AgentName.equals(AppName)||AgentName.equals(InsuName))
          {
              SamePerson="Y";
          }
          LCContSchema tLCContSchema = new LCContSchema();
          LCContDB tLCContDB = new LCContDB();
          tLCContDB.setContNo(tLCPolSchema.getContNo());
          if(!tLCContDB.getInfo())
          {
          	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
          	return false;
          }
          tLCContSchema = tLCContDB.getSchema();

          //如有数据错误，可以写在excel里
          m_book.setEntry(mCurrentRow,t+0,tLCPolSchema.getManageCom());//管理机构
          m_book.setEntry(mCurrentRow,t+1,AgentName);//业务员姓名
          m_book.setEntry(mCurrentRow,t+2,tLCPolSchema.getAgentCode());//业务员代码
          String AgentGroup=GetAgentGroup(tLCPolSchema);
          String AgentBranch=findAgentBranch(AgentGroup);
          m_book.setEntry(mCurrentRow,t+3,AgentBranch);//业务员组别
          m_book.setEntry(mCurrentRow,t+4,tLCPolSchema.getAppntName());//客户姓名
          
          String SQL_xqremind="select (case xqremindflag when '0' then 'N' when '1' then 'Y' else 'Y' end) from lccont where contno='?contno?'" ;
          SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
          sqlbv7.sql(SQL_xqremind);
          sqlbv7.put("contno", tLCPolSchema.getContNo());
          ExeSQL mExeSQL = new ExeSQL();
          SSRS mSSRS=mExeSQL.execSQL(sqlbv7);
          if(mSSRS!=null&&mSSRS.getMaxRow()>0)
          {
              
              m_book.setEntry(mCurrentRow,t+5,mSSRS.GetText(1, 1));//续期缴费提示
          }
          else{
              m_book.setEntry(mCurrentRow,t+5,"");//续期缴费提示
              }
          
          m_book.setEntry(mCurrentRow,t+6,tLCPolSchema.getContNo());//保单号
          m_book.setEntry(mCurrentRow,t+7,tLCPolSchema.getPaytoDate());//缴费日期
          String PayMode=findPayMode(tLCPolSchema.getContNo(),tLCContSchema.getPayLocation());
          m_book.setEntry(mCurrentRow,t+8,String.valueOf(PayMode));//缴费方式
          m_book.setEntry(mCurrentRow,t+9,ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode()));//主险险种
          //得到主险保费
          double mainPolPrem=0;
          String tGetNoticeNo="";
          String SQL_SubPol="select GetNoticeNo from ljspay where otherno='?otherno?' and othernotype='2'" ;
          SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
          sqlbv8.sql(SQL_SubPol);
          sqlbv8.put("otherno", tLCPolSchema.getContNo());
          ExeSQL tExeSQL = new ExeSQL();
          SSRS tSSRS=tExeSQL.execSQL(sqlbv8);
          if(tSSRS!=null&&tSSRS.getMaxRow()>0)
          {
              tGetNoticeNo=tSSRS.GetText(1,1);
              String tMainPremSql = "select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSpayPerson where RiskCode='?RiskCode?' and GetNoticeNo='?tGetNoticeNo?' and PayType='ZC'";
              SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
              sqlbv9.sql(tMainPremSql);
              sqlbv9.put("RiskCode", tLCPolSchema.getRiskCode());
              sqlbv9.put("tGetNoticeNo", tGetNoticeNo);
              tExeSQL = new ExeSQL();
              mainPolPrem=Double.parseDouble(tExeSQL.getOneValue(sqlbv9));
              m_book.setEntry(mCurrentRow,t+10,String.valueOf(mainPolPrem));//主险保费
          }
          else
          {
            m_book.setEntry(mCurrentRow,t+10,"主险续保保费未催收："+ getRnewFailReason(tLCPolSchema));//主险催收失败的原因
          }

          double sumprem=0.0;//保费合计
          sumprem=sumprem+mainPolPrem;

          LCPolSet tLCPolSet=findSubPol(tLCPolSchema);
          if(tLCPolSet.size()>0)
          {
              String prem="0";
              //如果多于一条，则在excel的下一行的同一列位置显示，同时mSubNum增长
              for(int i=1;i<=tLCPolSet.size();i++)
              {
                  if(String.valueOf(tLCPolSet.get(i).getLeavingMoney())!= null)
                   {
                     tLeavingMoney = tLeavingMoney + tLCPolSet.get(i).getLeavingMoney();
                   }
                  SQL_SubPol="select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSPayPerson where riskcode='?riskcode?' and PayType='ZC'" ;
                  SQL_SubPol=SQL_SubPol+" and GetNoticeNo='?tGetNoticeNo?'";
                  SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
                  sqlbv10.sql(SQL_SubPol);
                  sqlbv10.put("riskcode", tLCPolSet.get(i).getRiskCode());
                  sqlbv10.put("tGetNoticeNo", tGetNoticeNo);
                  tExeSQL = new ExeSQL();
                  tSSRS=tExeSQL.execSQL(sqlbv10);
                  if(tSSRS!=null&&tSSRS.getMaxRow()>0)
                  {
                      prem=tSSRS.GetText(1,1);
                  }
                  m_book.setEntry(mCurrentRow,t+11+2*(i-1),ChangeCodetoName.getRiskName(tLCPolSet.get(i).getRiskCode()));//附险险种
                  m_book.setEntry(mCurrentRow,t+12+2*(i-1),prem);//附险保费
                  sumprem=sumprem+Double.parseDouble(prem) ;
              }

          }

          m_book.setEntry(mCurrentRow,t+25,String.valueOf(sumprem));//应收保费合计
          m_book.setEntry(mCurrentRow,t+26,mDecimalFormat.format(tLeavingMoney));//预收费合计
          if(tLeavingMoney-sumprem >0 )
          {
           tActualPayMoney = 0;
           tNewLeavingMoney = tLeavingMoney-sumprem;
          }
          else
          {
           tActualPayMoney = sumprem - tLeavingMoney;
           tNewLeavingMoney = 0;
          }
          m_book.setEntry(mCurrentRow,t+27,mDecimalFormat.format(tActualPayMoney));//实交保费合计
          m_book.setEntry(mCurrentRow,t+28,mDecimalFormat.format(tNewLeavingMoney));//余额扣除本次保费合计

          //投保人信息
          LCAppntDB tLCAppntDB = new LCAppntDB();
          LCAddressDB tLCAddressDB = new LCAddressDB();
          tLCAppntDB.setContNo(tLCPolSchema.getContNo());
          tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
          if (tLCAppntDB.getInfo() == true)
          {
        	tLCAddressDB = new LCAddressDB();
        	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
        	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
        	if (tLCAddressDB.getInfo() == true)
            {
    			m_book.setEntry(mCurrentRow, t + 29,
    					tLCAddressDB.getPostalAddress()); //收费地址
    			m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
    			m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
            }
          }

          m_book.setEntry(mCurrentRow,t+32,tLCPolSchema.getInsuredName());//被保人姓名
          String BankInfo[]=findBankInfo(tLCPolSchema);
          m_book.setEntry(mCurrentRow,t+33,BankInfo[0]);//银行帐号
          m_book.setEntry(mCurrentRow,t+34,ChangeCodetoName.getBankCodeName(BankInfo[1]));//银行编码
          m_book.setEntry(mCurrentRow,t+35,BankInfo[2]);//户名

          LAAgentDB tLAAgentDB=new LAAgentDB();
          tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
          String AgentTel="";
          if(tLAAgentDB.getInfo()==true)
          {
              AgentTel=tLAAgentDB.getPhone();
              if(AgentTel==null||AgentTel.equals(""))
                  AgentTel=tLAAgentDB.getMobile();
              if(AgentTel==null)
                  AgentTel="";
          }
          m_book.setEntry(mCurrentRow,t+36,AgentTel);//代理人电话

          tExeSQL = new ExeSQL();
          String tSql = "select max(PayTimes) from LCPrem where PolNo='?PolNo?'";
          SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
          sqlbv11.sql(tSql);
          sqlbv11.put("PolNo", tLCPolSchema.getPolNo());
          String tPayTimes = tExeSQL.getOneValue(sqlbv11);
          m_book.setEntry(mCurrentRow,t+37,tPayTimes);//交费期数
          m_book.setEntry(mCurrentRow,t+38,ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex()));//性别
          m_book.setEntry(mCurrentRow,t+39,ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl()));//销售渠道
          m_book.setEntry(mCurrentRow,t+40,SamePerson);//代理人和投保人和被保人是否同一人标记
//          m_book.setEntry(mCurrentRow,t+28,tLCPolSchema.getAgentCom());//代理机构
          if(tLCPolSchema.getAgentCom()!=null && !"".equals(tLCPolSchema.getAgentCom()))
          {
            tExeSQL  = new ExeSQL();
            String tAgentName = "select Name from LACom where AgentCom = '?AgentCom?'";
            SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
            sqlbv12.sql(tAgentName);
            sqlbv12.put("AgentCom", tLCPolSchema.getAgentCom());
            m_book.setEntry(mCurrentRow,t+41,tExeSQL.getOneValue(sqlbv12));//代理机构
          }
          m_book.setEntry(mCurrentRow,t+42,tLCAddressDB.getMobile());//投保人手机
          m_book.setEntry(mCurrentRow,t+43,String.valueOf(tLCPolSchema.getPrtNo()));//印刷号
          m_book.setEntry(mCurrentRow,t+44, tLCAddressDB.getHomePhone());//家电
          m_book.setEntry(mCurrentRow,t+45, tLCAddressDB.getCompanyPhone());//公司电
          m_book.setEntry(mCurrentRow,t+46,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
      }
      catch(Exception ex)
      {
          try
          {
              m_book.setEntry(mCurrentRow,t+0,"***处理保单"+tLCPolSchema.getPolNo()+" 出错："+ex);//管理机构
              mCurrentRow=mCurrentRow+1;   //处理完每条纪录后添加一行
          }
          catch(Exception ex2)
          {
              buildError("dealSinglePol",ex.toString());
              return false;
          }
          return true;
      }
//      mCurrentRow=mCurrentRow+subNum;//如果有超过1个以上附加险，添加新的行存放第二个（以上）附加险信息
      mCurrentRow=mCurrentRow+1;   //处理完每条纪录后添加一行
      return true;
  }


  /**
   * 处理单个需要自动续保的附加险的清单数据
   * @param tLCPolSchema
   * @return
   */
  private boolean dealSingleSubPol(LCPolSchema tLCPolSchema)
  {
      int t=0;
      String AgentName="";//业务员姓名
      String InsuName="";//被保人姓名
      String AppName="";   //  客户姓名
      String SamePerson="N";
      try
      {
          AgentName=ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode()).trim();
          AppName=tLCPolSchema.getAppntName().trim();
          InsuName=tLCPolSchema.getInsuredName().trim();
          if(AgentName.equals(AppName)||AgentName.equals(InsuName))
          {
              SamePerson="Y";
          }
          LCContSchema tLCContSchema = new LCContSchema();
          LCContDB tLCContDB = new LCContDB();
          tLCContDB.setContNo(tLCPolSchema.getContNo());
          if(!tLCContDB.getInfo())
          {
          	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
          	return false;
          }
          tLCContSchema = tLCContDB.getSchema();

          //如有数据错误，可以写在excel里
          m_book.setEntry(mCurrentRow,t+0,tLCPolSchema.getManageCom());//管理机构
          m_book.setEntry(mCurrentRow,t+1,AgentName);//业务员姓名
          m_book.setEntry(mCurrentRow,t+2,tLCPolSchema.getAgentCode());//业务员代码
          String AgentGroup=GetAgentGroup(tLCPolSchema);
          String AgentBranch=findAgentBranch(AgentGroup);
          m_book.setEntry(mCurrentRow,t+3,AgentBranch);//业务员组别
          m_book.setEntry(mCurrentRow,t+4,tLCPolSchema.getAppntName());//客户姓名
          
          String SQL_xqremind="select (case xqremindflag when '0' then 'N' when '1' then 'Y' else 'Y' end) from lccont where contno='?contno?'" ;
          SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
          sqlbv13.sql(SQL_xqremind);
          sqlbv13.put("contno", tLCPolSchema.getContNo());
          ExeSQL mExeSQL = new ExeSQL();
          SSRS mSSRS=mExeSQL.execSQL(sqlbv13);
          if(mSSRS!=null&&mSSRS.getMaxRow()>0)
          {
              m_book.setEntry(mCurrentRow,t+5,mSSRS.GetText(1, 1));//续期缴费提示
          }
          else{
              m_book.setEntry(mCurrentRow,t+5,"");//续期缴费提示
              }
          
          m_book.setEntry(mCurrentRow,t+6,tLCPolSchema.getContNo());//保单号
          m_book.setEntry(mCurrentRow,t+7,tLCPolSchema.getPaytoDate());//缴费日期
          String PayMode=findPayMode(tLCPolSchema.getContNo(),tLCContSchema.getPayLocation());
          m_book.setEntry(mCurrentRow,t+8,String.valueOf(PayMode));//缴费方式

          //险种信息
          double sumprem=0.0;//保费合计
          double tSubPrem = 0.0;//附加险保费
          m_book.setEntry(mCurrentRow,t+11,ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode()));//附险险种
          //查询附加险保费
          String tGetNoticeNo="";
          String SQL_SubPol="select GetNoticeNo from ljspay where otherno='?otherno?' and othernotype='2'" ;
          SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
          sqlbv14.sql(SQL_SubPol);
          sqlbv14.put("otherno", tLCPolSchema.getContNo());
          ExeSQL tExeSQL = new ExeSQL();
          SSRS tSSRS=tExeSQL.execSQL(sqlbv14);
          if(tSSRS!=null&&tSSRS.getMaxRow()>0)
          {
            tGetNoticeNo=tSSRS.GetText(1,1);
            String tSubPremSql = "select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSpayPerson where RiskCode='?RiskCode?' and GetNoticeNo='?tGetNoticeNo?' and PayType='ZC'";
            SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
            sqlbv15.sql(tSubPremSql);
            sqlbv15.put("RiskCode", tLCPolSchema.getRiskCode());
            sqlbv15.put("tGetNoticeNo", tGetNoticeNo);
            tExeSQL = new ExeSQL();
            tSubPrem=Double.parseDouble(tExeSQL.getOneValue(sqlbv15));
            m_book.setEntry(mCurrentRow,t+12,String.valueOf(tSubPrem));//主险保费
          }
          else
          {
            m_book.setEntry(mCurrentRow,t+12,"附加险续保保费未催收："+ getRnewFailReason(tLCPolSchema));//主险催收失败的原因
          }

          sumprem=sumprem+tSubPrem;
          m_book.setEntry(mCurrentRow,t+13,String.valueOf(sumprem));//保费合计

          //投保人信息
          LCAppntDB tLCAppntDB = new LCAppntDB();
          LCAddressDB tLCAddressDB = new LCAddressDB();
          tLCAppntDB.setContNo(tLCPolSchema.getContNo());
          tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
          if (tLCAppntDB.getInfo() == true)
          {
        	tLCAddressDB = new LCAddressDB();
        	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
        	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
        	if (tLCAddressDB.getInfo() == true)
            {
    			m_book.setEntry(mCurrentRow, t + 29,
    					tLCAddressDB.getPostalAddress()); //收费地址
    			m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
    			m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
            }
          }

          m_book.setEntry(mCurrentRow,t+32,tLCPolSchema.getInsuredName());//被保人姓名
          String BankInfo[]=findBankInfo(tLCPolSchema);
          m_book.setEntry(mCurrentRow,t+33,BankInfo[0]);//银行帐号
          m_book.setEntry(mCurrentRow,t+34,ChangeCodetoName.getBankCodeName(BankInfo[1]));//银行编码
          m_book.setEntry(mCurrentRow,t+35,BankInfo[2]);//户名

          LAAgentDB tLAAgentDB=new LAAgentDB();
          tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
          String AgentTel="";
          if(tLAAgentDB.getInfo()==true)
          {
              AgentTel=tLAAgentDB.getPhone();
              if(AgentTel==null||AgentTel.equals(""))
                  AgentTel=tLAAgentDB.getMobile();
              if(AgentTel==null)
                  AgentTel="";
          }
          m_book.setEntry(mCurrentRow,t+36,AgentTel);//代理人电话

          tExeSQL = new ExeSQL();
          String tSql = "select max(PayTimes) from LCPrem where PolNo='?PolNo?'";
          SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
          sqlbv16.sql(tSql);
          sqlbv16.put("PolNo", tLCPolSchema.getPolNo());
          String tPayTimes = tExeSQL.getOneValue(sqlbv16);
          m_book.setEntry(mCurrentRow,t+37,tPayTimes);//交费期数
          m_book.setEntry(mCurrentRow,t+38,ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex()));//性别
          m_book.setEntry(mCurrentRow,t+39,ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl()));//销售渠道
          m_book.setEntry(mCurrentRow,t+40,SamePerson);//代理人和投保人和被保人是否同一人标记

//	  m_book.setEntry(mCurrentRow,t+28,tLCPolSchema.getAgentCom());//代理机构
          if(tLCPolSchema.getAgentCom()!=null && !"".equals(tLCPolSchema.getAgentCom()))
          {
            tExeSQL  = new ExeSQL();
            String tAgentName = "select Name from LACom where AgentCom = '?AgentCom?'";
            SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
            sqlbv17.sql(tAgentName);
            sqlbv17.put("AgentCom", tLCPolSchema.getAgentCom());
            m_book.setEntry(mCurrentRow,t+41,tExeSQL.getOneValue(sqlbv17));//代理机构

          }
          m_book.setEntry(mCurrentRow,t+42,tLCAddressDB.getMobile());//投保人手机
          m_book.setEntry(mCurrentRow,t+43,String.valueOf(tLCPolSchema.getPrtNo()));//印刷号
          m_book.setEntry(mCurrentRow,t+44, tLCAddressDB.getHomePhone());//家电
          m_book.setEntry(mCurrentRow,t+45, tLCAddressDB.getCompanyPhone());//公司电
          m_book.setEntry(mCurrentRow,t+46,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
      }
      catch(Exception ex)
      {
          try
          {
              m_book.setEntry(mCurrentRow,t+0,"***处理保单"+tLCPolSchema.getPolNo()+" 出错："+ex);//管理机构
              mCurrentRow=mCurrentRow+1;   //处理完每条纪录后添加一行
          }
          catch(Exception ex2)
          {
              buildError("dealSinglePol",ex.toString());
              return false;
          }
          return true;
      }
      mCurrentRow=mCurrentRow+1;   //处理完每条纪录后添加一行
      return true;
  }

/**
  * 根据条件判断：
  * 孤儿单的定义：指初始业务员已经离司的保单，包括离司个人业务员代码下的保单和离司续收员曾经招揽的保单
  * 在职单的定义：初始业务员在职的保单，包括在职个人业务员下的保单，和在职续收员自行招揽的保单
  * @param agentCode
  * @return
  */
   private boolean judgePolType(String tAgentCode,String tContNo)
 {
     if(!mPolType.equals("1")&&!mPolType.equals("0"))
     {
         return true;
     }

     LAAgentDB tLAAgentDB=new LAAgentDB();
     tLAAgentDB.setAgentCode(tAgentCode);
     if(tLAAgentDB.getInfo()==false)
     {
         return false;
     }
     //String AgentState=tLAAgentDB.getAgentState();
     //如果需要是在职
     if(mPolType.equals("1"))
     {
//          if(AgentState.equals("01")||AgentState.equals("02"))
//          {
//              return true;
//          }
//          return false;
         if(tLAAgentDB.getAgentState().equals("01")||tLAAgentDB.getAgentState().equals("02"))
         {
             if(!tLAAgentDB.getBranchType().equals("4"))
             {
                 return true;
             }
             else
             {
                 String sql =
                     " select 'exists' from dual where exists (select polno from lrascription where contno='?tContNo?'" + " union all "
                     + " select polno from lrascriptionb where contno='?tContNo?' ) ";
                 SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
                 sqlbv18.sql(sql);
                 sqlbv18.put("tContNo", tContNo);
                 ExeSQL tExeSQL = new ExeSQL();
                 //如果存在归属表或者是归属备份表，表明是孤儿单，否则是续收员的新单，列入在职单
                 if (tExeSQL.getOneValue(sqlbv18).equals("exists"))
                 {
                     return false;
                 }
                 else
                 {
                     return true;
                 }
             }
         }
         else
         {
             return false;
         }
     }
     else //如果需要是不在职
     {
//          if(AgentState.equals("01")||AgentState.equals("02"))
//          {
//              return false;
//          }
//          return true;
         if(tLAAgentDB.getAgentState().equals("01")||tLAAgentDB.getAgentState().equals("02"))
         {
             if(!tLAAgentDB.getBranchType().equals("4"))
             {
                 return false;
             }
             else
             {
                 String sql =
                     " select 'exists' from dual where exists (select polno from lrascription where contno='?tContNo?'" + " union all "
                     + " select polno from lrascriptionb where contno='?tContNo?' ) ";
                 SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
                 sqlbv19.sql(sql);
                 sqlbv19.put("tContNo", tContNo);
                 ExeSQL tExeSQL = new ExeSQL();
                 //如果存在归属表或者是归属备份表，表明是孤儿单，否则是续收员的新单，列入在职单
                 if (tExeSQL.getOneValue(sqlbv19).equals("exists"))
                 {
                     return true;
                 }
                 else
                 {
                     return false;
                 }
             }
         }
         else
         {
             return true;
         }
     }
  }

  /**
   *查询续期需要自动续保缴费的附加险
   */
  private LCPolSet findSubPol(LCPolSchema tLCPolSchema)
  {

      LCPolDB tLCPolDB=new LCPolDB();
      String strSQL="select * from lcpol where mainpolno='?mainpolno?' ";
      strSQL=strSQL+"and polno!='?polno?' ";
      strSQL=strSQL+"and AppFlag='1' and paytodate='?paytodate?' ";
      strSQL=strSQL+"and ((PaytoDate = PayEndDate and RnewFlag = '-1' and exists (select 1 from LMRisk where RiskCode=LCPol.RiskCode and rnewflag='Y')) or PayToDate < PayEndDate)";
      SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
      sqlbv20.sql(strSQL);
      sqlbv20.put("mainpolno", tLCPolSchema.getPolNo());
      sqlbv20.put("polno", tLCPolSchema.getPolNo());
      sqlbv20.put("paytodate", tLCPolSchema.getPaytoDate());
      LCPolSet tLCPolSet=tLCPolDB.executeQuery(sqlbv20);

      return tLCPolSet;
  }


  /**
   * 取描述信息
   * @return
   */
  private boolean checkDesc()
  {
      //查询系统变量-是否主险续期和附加险的自动续保关联-如果关联，则续期应收保费和续保应收保费数据存放在同一个清单
      LDSysVarDB tLDSysVarDB=new LDSysVarDB();
      tLDSysVarDB.setSysVar("relationflag");
      if(tLDSysVarDB.getInfo()==false)
      {
      }
      else
      {
          mRelationFlag=tLDSysVarDB.getSysVarValue();
      }
      //取清单模版文件存放路径（即要生成文件的存放路径）
      tLDSysVarDB=new LDSysVarDB();
      tLDSysVarDB.setSysVar(mFilePathDesc);
      if(tLDSysVarDB.getInfo()==false)
      {
          buildError("checkDesc","LDSysVar取文件路径("+mFilePathDesc+")描述失败");
          return false;
      }
      mFilePath=tLDSysVarDB.getSysVarValue();
      mFileName=getFileName();
      return true;
  }

  /**
   * 处理需要续期缴费的主险保单--如果续期和续保关联，则取附加险信息
   * @return
   */
  private boolean dealDueMainPol()
  {
      String sqlhead="select count(*) from LCPol";
      String sqlStr=" where (PaytoDate>='?mStartDate?' and PaytoDate<='?mEndDate?' and PaytoDate<PayEndDate ) ";
      sqlStr=sqlStr+" and PayIntv>0";
      sqlStr=sqlStr+" and AppFlag='1'";
      sqlStr=sqlStr+" and PolNo=MainPolNo";
      //sqlStr=sqlStr+" and RiskCode  in (select RiskCode from LMRiskPay where UrgePayFlag='Y')" ;
      sqlStr=sqlStr+" and (StopFlag='0' or StopFlag is null) and conttype='1' " +mSaleChnlSql;
      sqlStr=sqlStr+" and exists(select 1 from lccontstate where polno=lcpol.polno and statetype='Available' and enddate is null)";  //垫交件，终止件，死亡报案的不能在清单中显示
      sqlStr=sqlStr+" and exists(select 1 from lcduty b where b.contno=lcpol.contno and (b.FreeFlag != '1' or b.FreeFlag is null))";  //对于豁免赔付的保单,不再应收清单中显示
      //sqlStr=sqlStr+" and not exists(select * from ldsystrace a where a.polno=LCPol.polno and polstate='4001')";
      String MaxmManageCom=PubFun.RCh(mManageCom,"9",8);
      String MinmManageCom=PubFun.RCh(mManageCom,"0",8);
      sqlStr=sqlStr+" and ManageCom>='?MinmManageCom?' and ManageCom<='?MaxmManageCom?'";
      sqlStr=sqlStr+" order by PaytoDate,AgentGroup";
      String SQL_Count=sqlhead+sqlStr;
      SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
      sqlbv21.sql(SQL_Count);
      sqlbv21.put("mStartDate", mStartDate);
      sqlbv21.put("mEndDate", mEndDate);
      sqlbv21.put("mSaleChnl", mSaleChnl);
      sqlbv21.put("MinmManageCom", MinmManageCom);
      sqlbv21.put("MaxmManageCom", MaxmManageCom);
   
      logger.debug("查询续期主险:"+SQL_Count);

      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS=tExeSQL.execSQL(sqlbv21);
      String strCount=tSSRS.GetText(1,1);
      int SumCount = Integer.parseInt(strCount);
      int CurrentCounter=1;
      String SQL_PolNo="select PolNo from lcpol"+sqlStr;
      SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
      sqlbv22.sql(SQL_PolNo);
      sqlbv22.put("mStartDate", mStartDate);
      sqlbv22.put("mEndDate", mEndDate);
      sqlbv22.put("MinmManageCom", MinmManageCom);
      sqlbv22.put("MaxmManageCom", MaxmManageCom);
      LCPolDB tLCPolDB = new LCPolDB();
      LCPolSchema tLCPolSchema = new LCPolSchema();
      //如果基数大与个人保单纪录数，跳出循环
      while(CurrentCounter<=SumCount)
      {
          tExeSQL = new ExeSQL();
          tSSRS=tExeSQL.execSQL(sqlbv22,CurrentCounter,mCount);
          if(tSSRS==null)
          {
              buildError("dealDueMainPol","保单读取失败");
              return false;
          }

          for( int i = 1; i <=tSSRS.getMaxRow(); i++ )
          {
              tLCPolSchema = new LCPolSchema();
              tLCPolDB = new LCPolDB();
              tLCPolDB.setPolNo(tSSRS.GetText(i,1));
              tLCPolDB.getInfo();
              tLCPolSchema.setSchema(tLCPolDB.getSchema());
              //判断在职/孤儿单--如果不符合条件-继续下一条
              if(judgePolType(tLCPolSchema.getAgentCode(),tLCPolSchema.getContNo())==false)
                  continue;
              if(dealSinglePol(tLCPolSchema)==false)
                  continue;
          }

          CurrentCounter=CurrentCounter+mCount; //计数器增加
          //考虑是否有方法减少内存的大数据量，可以将m_book数据写到excel文件,m_book清空
          //打开excel文件，从mCurrentRow位置继续写数据
      }
      return true;
  }

  /**
   * 处理需要自动续保的主险保单
   * @return
   */
  private boolean dealRnewMainPol()
  {
      String sqlhead="select count(*) from LCPol ";
      String sqlStr=" where (PaytoDate>='?mStartDate?' and PaytoDate<='?mEndDate?' and PaytoDate=PayEndDate ) ";
      sqlStr=sqlStr+" and AppFlag='1'";
      sqlStr=sqlStr+" and PolNo=MainPolNo";
      sqlStr=sqlStr+" and exists(select 1 from lmrisk where RiskCode=LCPol.RiskCode and rnewflag='Y')";
      sqlStr=sqlStr+" and (StopFlag='0' or StopFlag is null) and conttype='1' " +mSaleChnlSql;
      sqlStr=sqlStr+" and RnewFlag=-1  ";    //排除已续保确认，但还未转储保单
      String MaxmManageCom=PubFun.RCh(mManageCom,"9",8);
      String MinmManageCom=PubFun.RCh(mManageCom,"0",8);
      sqlStr=sqlStr+" and ManageCom>='?MinmManageCom?' and ManageCom<='?MaxmManageCom?' order by PaytoDate,AgentGroup";
      String SQL_Count=sqlhead+sqlStr;
      SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
      sqlbv23.sql(SQL_Count);
      sqlbv23.put("mStartDate", mStartDate);
      sqlbv23.put("mEndDate", mEndDate);
      sqlbv23.put("mSaleChnl", mSaleChnl);
      sqlbv23.put("MinmManageCom", MinmManageCom);
      sqlbv23.put("MaxmManageCom", MaxmManageCom);
     
      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS=tExeSQL.execSQL(sqlbv23);
      String strCount=tSSRS.GetText(1,1);
      int SumCount = Integer.parseInt(strCount);
      int CurrentCounter=1;
      String SQL_PolNo="select PolNo from lcpol "+sqlStr;
      SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
      sqlbv24.sql(SQL_PolNo);
      sqlbv24.put("mStartDate", mStartDate);
      sqlbv24.put("mEndDate", mEndDate);
      sqlbv24.put("MinmManageCom", MinmManageCom);
      sqlbv24.put("MaxmManageCom", MaxmManageCom);
      //当统计全部保单时，显示此提示，如果只统计续保件，则不用提示
      if("0".equals(mOperType))
      {
          addPrompt("***以下是主险自动续保数据***");
      }
      LCPolDB tLCPolDB = new LCPolDB();
      LCPolSchema tLCPolSchema = new LCPolSchema();
      //如果基数大与个人保单纪录数，跳出循环
      while(CurrentCounter<=SumCount)
      {
          tExeSQL = new ExeSQL();
          tSSRS=tExeSQL.execSQL(sqlbv24,CurrentCounter,mCount);
          if(tSSRS==null)
          {
              buildError("dealDueMainPol","保单读取失败");
              return false;
          }

          for( int i = 1; i <=tSSRS.getMaxRow(); i++ )
          {
              tLCPolSchema = new LCPolSchema();
              tLCPolDB = new LCPolDB();
              tLCPolDB.setPolNo(tSSRS.GetText(i,1));
              tLCPolDB.getInfo();
              tLCPolSchema.setSchema(tLCPolDB.getSchema());
              //判断在职/孤儿单--如果不符合条件-继续下一条
              if(judgePolType(tLCPolSchema.getAgentCode(),tLCPolSchema.getContNo())==false)
                  continue;
              if(dealSingleRnewPol(tLCPolSchema)==false)
                  continue;
          }

          CurrentCounter=CurrentCounter+mCount; //计数器增加
          //考虑是否有方法减少内存的大数据量，可以将m_book数据写到excel文件,m_book清空
          //打开excel文件，从mCurrentRow位置继续写数据
      }
      return true;
  }

  /**
   * 处理需要续保的附加险，其主险不需要续期缴费和续保
   * @return
   */
  private boolean dealRnewSpecSubPol()
  {
    try
    {
      String sqlhead="select count(*) from LCPol a";
      String sqlStr=" where PaytoDate>='?mStartDate?' and PaytoDate<='?mEndDate?' and PaytoDate=PayEndDate ";
      sqlStr=sqlStr+" and AppFlag='1'";
      sqlStr=sqlStr+" and PolNo!=MainPolNo";
      sqlStr=sqlStr+" and (StopFlag='0' or StopFlag is null) and conttype='1' " +mSaleChnlSql;
      sqlStr=sqlStr+" and RnewFlag=-1 and exists(select 1 from lmrisk where RiskCode=a.RiskCode and rnewflag='Y') ";
      sqlStr=sqlStr+" and exists(select 1 from LCPol b where b.AppFlag='1' and b.PolNo=a.MainPolNo and b.PolNo=b.MainPolNo and b.PayIntv=0 and b.PayToDate=b.PayendDate and RnewFlag<>'-1' and b.PaytoDate>a.PaytoDate) ";
      String MaxmManageCom=PubFun.RCh(mManageCom,"9",8);
      String MinmManageCom=PubFun.RCh(mManageCom,"0",8);
      sqlStr=sqlStr+" and ManageCom>='?MinmManageCom?' and ManageCom<='?MaxmManageCom?' order by ManageCom,Cvalidate,PrtNo,RiskCode";

      String SQL_Count=sqlhead+sqlStr;
      SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
      sqlbv25.sql(SQL_Count);
      sqlbv25.put("mStartDate", mStartDate);
      sqlbv25.put("mEndDate", mEndDate);
      sqlbv25.put("mSaleChnl", mSaleChnl);
      sqlbv25.put("MinmManageCom", MinmManageCom);
      sqlbv25.put("MaxmManageCom", MaxmManageCom);
      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS=tExeSQL.execSQL(sqlbv25);
      String strCount=tSSRS.GetText(1,1);
      int SumCount = Integer.parseInt(strCount);
      int CurrentCounter=1;
      String SQL_PolNo="select PolNo from lcpol a "+sqlStr;
      SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
      sqlbv26.sql(SQL_PolNo);
      sqlbv26.put("mStartDate", mStartDate);
      sqlbv26.put("mEndDate", mEndDate);
      sqlbv26.put("MinmManageCom", MinmManageCom);
      sqlbv26.put("MaxmManageCom", MaxmManageCom);

      if(SumCount>0)
      {
          addPrompt("***以下是附加险自动续保数据(其主险不需要续期缴费和续保)***");
      }
      LCPolDB tLCPolDB = new LCPolDB();
      LCPolSchema tLCPolSchema = new LCPolSchema();
      //如果基数大与个人保单纪录数，跳出循环
      while(CurrentCounter<=SumCount)
      {
          tExeSQL = new ExeSQL();
          tSSRS=tExeSQL.execSQL(sqlbv26,CurrentCounter,mCount);
          if(tSSRS==null)
          {
              buildError("dealDueMainPol","保单读取失败");
              return false;
          }

          for( int i = 1; i <=tSSRS.getMaxRow(); i++ )
          {
              //判断主险是续期或自动续保-跳过
//              if(judgeMainRisk(tSSRS.GetText(i,2),tSSRS.GetText(i,3))==false)
//                  continue;
              tLCPolSchema = new LCPolSchema();
              tLCPolDB = new LCPolDB();
              tLCPolDB.setPolNo(tSSRS.GetText(i,1));
              tLCPolDB.getInfo();
              tLCPolSchema.setSchema(tLCPolDB.getSchema());
              //判断在职/孤儿单--如果不符合条件-继续下一条
              if(judgePolType(tLCPolSchema.getAgentCode(),tLCPolSchema.getContNo())==false)
                  continue;
              if(dealSingleSubPol(tLCPolSchema)==false)
                  return false;
          }

          CurrentCounter=CurrentCounter+mCount; //计数器增加
          //考虑是否有方法减少内存的大数据量，可以将m_book数据写到excel文件,m_book清空
          //打开excel文件，从mCurrentRow位置继续写数据
      }
    }catch(Exception ex)
    {
      ex.printStackTrace();
      buildError("dealRnewSpecSubPol","产生主险趸交，附加险续保时发生异常");
      return false;
    }
      return true;
  }

  /**
   * 不同类型的数据之间添加说明
   * @return
   */
  private boolean addPrompt(String sPrompt)
  {
      try
      {
          //如有数据错误，可以写在excel里
          m_book.setEntry(mCurrentRow,0,sPrompt);//管理机构
          mCurrentRow=mCurrentRow+1;
      }
      catch(Exception ex)
      {
//          buildError("addPrompt","添加说明“"+sPrompt+"失败");
//          return false;
      }
      return true;
  }

  /**
   * 判断主险是否符合条件：非续期，非续保，未到期
   * @param tPolNo   主险号
   * @param tPayEndDate  附加险终交日期
   * @return
   */
  private boolean judgeMainRisk(String tPolNo,String tPayEndDate)
  {
      LCPolDB tLCPolDB=new LCPolDB();
      String strSQL="select * from lcpol where polno='?tPolNo?' ";
      strSQL=strSQL+" and appflag='1'";
      strSQL=strSQL+" and RnewFlag!=-1 "; //不是自动续保的
      strSQL=strSQL+" and payintv=0 ";//不是续期的
      strSQL=strSQL+" and payenddate>'?tPayEndDate?'";//未到期的
      //如果不符合条件-则返回false
      SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
      sqlbv27.sql(strSQL);
      sqlbv27.put("tPolNo", tPolNo);
      sqlbv27.put("tPayEndDate", tPayEndDate);
      LCPolSet tLCPolSet=tLCPolDB.executeQuery(sqlbv27);
      if(tLCPolSet.size()==0)
          return false;

      return true;
  }

  /**
   * 得到续期保单对应某个日期应该交纳的费用--从保费项查询--因为保单纪录所有阶段应该缴纳的总和
   * @param tPolNo   主险号
   * @return
   */
  private double getMainPolPrem(String tPolNo)
  {
      String sql="select sum(prem) from LCPrem where polno='?tPolNo?' and PayEndDate>'?mEndDate?' and PayStartDate<='?mStartDate?' and char_length(trim(dutycode))<>10";
      SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
      sqlbv28.sql(sql);
      sqlbv28.put("tPolNo", tPolNo);
      sqlbv28.put("mEndDate", mEndDate);
      sqlbv28.put("mStartDate", mStartDate);
      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS=tExeSQL.execSQL(sqlbv28);
      double sumPrem = Double.parseDouble(tSSRS.GetText(1,1));
      return sumPrem;
  }

  /**
   * 得到展业机构外部编码--中文名称
   * @param tAgentGroup
   * @return
   */
  private String findAgentBranch(String tAgentGroup)
  {
      LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
      tLABranchGroupDB.setAgentGroup(tAgentGroup) ;
      if(tLABranchGroupDB.getInfo()==false)
          return "unknow";
      else
          //return tLABranchGroupDB.getBranchAttr();
          return tLABranchGroupDB.getName();
  }

  /**
   * 找交费方式
   * @param tPolNo
   * @return
   */
  private String findPayMode2(String tContNo)
  {

      String SQL="select tempfeeno from LJTempFee where otherno='?tContNo?' and confflag='1' order by makedate desc";
      SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
      sqlbv29.sql(SQL);
      sqlbv29.put("tContNo", tContNo);
      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS=tExeSQL.execSQL(sqlbv29);
      if(tSSRS==null)
          return "unknow";
      String tempfeeno=tSSRS.GetText(1,1);

      LJTempFeeClassDB tLJTempFeeClassDB=new LJTempFeeClassDB();
      tLJTempFeeClassDB.setTempFeeNo(tempfeeno);
      LJTempFeeClassSet tLJTempFeeClassSet=tLJTempFeeClassDB.query();
      if(tLJTempFeeClassSet.size()==0)
          return "unknow";
      else
          return ChangeCodetoName.getPayModeName(tLJTempFeeClassSet.get(1).getPayMode());
  }

  /**
   * 找银行信息
   * @param tPolNo
   * @return
   */
  private String[] findBankInfo(String tContNo)
  {
      String bankInfo[]=new String[3];
      String SQL="select tempfeeno from LJTempFee where otherno='?tContNo?' and confflag='1' order by makedate desc";
      SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
      sqlbv30.sql(SQL);
      sqlbv30.put("tContNo", tContNo);
      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS=tExeSQL.execSQL(sqlbv30);
      if(tSSRS==null)
          return bankInfo;
      String tempfeeno=tSSRS.GetText(1,1);

      LJTempFeeClassDB tLJTempFeeClassDB=new LJTempFeeClassDB();
      tLJTempFeeClassDB.setTempFeeNo(tempfeeno);
      LJTempFeeClassSet tLJTempFeeClassSet=tLJTempFeeClassDB.query();
      if(tLJTempFeeClassSet.size()==0)
          return bankInfo;
      else
      {
          bankInfo[0]=tLJTempFeeClassSet.get(1).getBankAccNo();
          bankInfo[1]=tLJTempFeeClassSet.get(1).getBankCode();
          bankInfo[2]=tLJTempFeeClassSet.get(1).getAccName();
      }
          return bankInfo;
  }

  /**
   * 找交费方式
   * @param tPolNo
   * @return
   */
  private String findPayMode(String tContNo,String PayLocation)
  {
      if(PayLocation==null)
          return findPayMode2(tContNo);
      else
          return ChangeCodetoName.getPayLocationName(PayLocation) ;
  }


  /**
   * 查找银行信息
   * @param tLCPolSchema
   * @return
   */
  private String[] findBankInfo(LCPolSchema tLCPolSchema)
  {
    if (tLCPolSchema.getPayLocation() == null)
    {
      return findBankInfo(tLCPolSchema.getContNo());
    }
    else
    {
      LCContDB tLCContDB = new LCContDB();
      tLCContDB.setContNo(tLCPolSchema.getContNo());

      String[] bankInfo = new String[3];
      bankInfo[0] = tLCContDB.query().get(1).getBankAccNo();
      bankInfo[1] = tLCContDB.query().get(1).getBankCode();
      bankInfo[2] = tLCContDB.query().get(1).getAccName();
      return bankInfo;
    }
  }



}

