package com.sinosoft.lis.reagent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.service.ServiceA;
import com.f1j.ss.BookModelImpl;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LACommisionDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LRAdimAscriptionDB;
import com.sinosoft.lis.db.LRAscriptionDB;
import com.sinosoft.lis.pubfun.ChangeCodetoName;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.service.CovBase;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.taskservice.taskinstance.DealReTMThread;
import com.sinosoft.lis.vschema.LACommisionSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LRAdimAscriptionSet;
import com.sinosoft.lis.vschema.LRAscriptionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowBL;
import com.sinosoft.workflow.tb.TbWorkFlowBLS;

/**
 * <p>
 * Title:新契约工作流-多线程版本
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.5
 */

public class LRPolListCDownBLTMThread extends CovBase {
	private static Logger logger = Logger.getLogger(LRPolListCDownBLTMThread.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
    private String mDelFlag = ""; //处理类型标志 0：按机构；1：按个人
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	private String mFilePathDesc = "XSCreatListPath"; //描述的生成结果文件存放路径
	/** 数据操作字符串 */
	private VData CVData = new VData();
	private String mOperate;
	private String mBranchType = "";   //4：续收外勤；99：收展
	private String mFileName = ""; //要生成的文件名
	private String mFilePath = ""; //通过描述得到的文件路径
	private HttpServletRequest httprequest;
    private String mFileModeDesc = "ReAgentAnyDown1.xls"; //描述的模版名称
	private String mXSExcTemplate = ""; //描述的模版读取路径
	private String payintv_sql = "";//交费类型sql
	private int mCount = 10000; //一次循环处理10000条记录
	private int mCurrentRow = 1; //行数
	SSRS mSSRS = new SSRS();  //存放需要生成清单的代理人编码集合
	private BookModelImpl m_book;
	private DecimalFormat mDecimalFormat = new DecimalFormat();
	private String mManageCom = "";
	private String mAgentCode = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mType1 = "";   //1：孤儿单；2：在职单；3：不区分
	private String mType2 = "";   //1：二次；2：三次；3：不区分
	private String mType3 = "";   //1：续期件；2：续保件；3：不区分
	private String mType4 = "";   //1：年交；0：非年交
	private ExeSQL mExeSQL = new ExeSQL();
	private String currentdate = PubFun.getCurrentDate();
	private String currenttime = PubFun.getCurrentTime();
	private int tConutMax = 100;
	public LRPolListCDownBLTMThread() {
	}

	public static void main(String[] args) {

		
	}
	public void setObject(Object tObject) {
		System.out.println(tObject);
		//多线程的外部参数条件
		mInputDataNew = (Vector) tObject;
	}
	
	
	  
	  /**
	   * 生成文件名：ReAgentAnyDown1_86110000_200709.xls
	   * @param cAgentCode
	   * @return
	   */
	  private String getFileName(String mDelFlag)
	  {
	    String filename = "";
	    if(this.mBranchType.equals("4"))
	    {
	    	filename = "ReAgentAnyDown1_";
	    }
	    else
	    {
	    	filename = "ReAgentAnyDown99_";
	    }

	    if(mDelFlag.equals("0"))
	    {
	      filename = filename + mManageCom + "_";
	    }
	    else
	    {
	      filename = filename + mAgentCode + "_";
	    }
	    String  startdate_Sql = " select substr(to_char(to_date('"+"?mStartDate?"+"','yyyy-mm-dd'),'yyyy-mm-dd'),1,10) from dual";
	    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	    sqlbv1.sql(startdate_Sql);
	    sqlbv1.put("mStartDate", mStartDate);
		String  enddate_Sql = " select substr(to_char(to_date('"+"?mEndDate?"+"','yyyy-mm-dd'),'yyyy-mm-dd'),1,10) from dual";
		ExeSQL ttExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	    sqlbv2.sql(enddate_Sql);
	    sqlbv2.put("mEndDate", mEndDate);
		mStartDate=ttExeSQL.getOneValue(sqlbv1);
		mEndDate=ttExeSQL.getOneValue(sqlbv2);

	    filename=filename+mStartDate+"_"+mEndDate+"_"+mType1+"_"+mType2+"_"+mType3+"_"+mType4;
	    filename = filename + ".xls";
	    logger.debug("生成文件名:" + filename);
	    return filename;
	  }
	  /**
	   * 错误处理
	   * @param szFunc
	   * @param szErrMsg
	   */
	  private void buildError(String szFunc, String szErrMsg)
	  {
	    CError cError = new CError();
	    cError.moduleName = "LRPolListCDownBL";
	    cError.functionName = szFunc;
	    cError.errorMessage = szErrMsg;
	    this.mErrors.addOneError(cError);
	  }
	
	  public void run() {
		
		VData mResult = new VData(); 
		MMap tMMap = new MMap();
		
		
		// 如果有描述的话,取描述的数据
		for(int j=0;j<this.mInputDataNew.size();j++)
		{
			
			
			Map tMap = new HashMap();
			tMap = (Map)mInputDataNew.get(j);
			VData mInputData = new VData();
			
			SSRS tSSRS = (SSRS)tMap.get("tSSRS");
			m_book =(BookModelImpl)tMap.get("m_book");
			int ErrCount = 0;
//			mDelFlag=(String)tVData.getObjectByObjectName("LCContSchema", 0);
//			VData mInputData = new VData();
			GlobalInput mGI=(GlobalInput)tMap.get("GlobalInput");
			TransferData mTransferData=(TransferData)tMap.get("TransferData");
			LCPolSchema tLCPolSchema=(LCPolSchema)tMap.get("LCPolSchema");
		    mManageCom = (String) mTransferData.getValueByName("ManageCom");
		    mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		    mStartDate = (String) mTransferData.getValueByName("StartDate");
		    mEndDate = (String) mTransferData.getValueByName("EndDate");
		    mType1 = (String) mTransferData.getValueByName("Type1");
		    mType2 = (String) mTransferData.getValueByName("Type2");
		    mType3 = (String) mTransferData.getValueByName("Type3");
		    mType4 = (String) mTransferData.getValueByName("Type4");
		    mBranchType = (String) mTransferData.getValueByName("BranchType");
		    mXSExcTemplate = (String) mTransferData.getValueByName("XSExcTemplate");
		   
			 SSRS cSSRS =new SSRS();
			 //循环处理每一个代理人
		    logger.debug("需要处理的代理人个数："+cSSRS.getMaxRow());
		    try
		    {

		      mCurrentRow = 1;
		      //确定交费类型
			  

			  String tAgentCode = "";
			  
		      if((mType1.equals("1"))||(mType1.equals("3")))  //孤儿单
		        {
		            if((mType3.equals("1"))||(mType3.equals("3")))  //续期件
		            {

		                 if(!judgeTempFee(tLCPolSchema.getContNo()))
		                 {
		                	 continue;
		                 }

		                if (!dealSinglePol(tLCPolSchema,"0"))
		                {
//		                		  return "";
		                		
		                }
		            }
		            if((mType3.equals("2"))||(mType3.equals("3")))  //续保件
		            {
		                  //3--孤儿单需要续保的主险清单
		                  addPrompt("***以下是孤儿单主险自动续保数据***");
		                  if(tSSRS.GetText(1, 2).equals("9999"))
		                  {
		                	  
		                  
		                	  if(judgeTempFee(tLCPolSchema.getPolNo())==false)
		                	  {
		                		  continue; 
		                	  }
		                	  if (dealSingleRnewPol(tLCPolSchema,"0") == false)
		                	  {
//		            		  return false;
		                	  }
		                  }
		                  else
		                  {
		                   //4--孤儿单需要续保的附加险清单
//		            		判断主险是续期或自动续保-跳过 9999
		                	  if(!judgeMainRisk(tSSRS.GetText(1, 2), tSSRS.GetText(1, 3)))
		                	  {
		                		  continue;
		                	  }
		                	  tLCPolSchema = new LCPolSchema();
		                	  LCPolDB tLCPolDB = new LCPolDB();
		                	  tLCPolDB.setPolNo(tSSRS.GetText(1, 1));
		                	  tLCPolDB.getInfo();
		                	  tLCPolSchema.setSchema(tLCPolDB.getSchema());

		                	  boolean Flag = false;
		                	  for (int t = 0; t < CVData.size(); t++)
		                	  {
		                		  if (tLCPolDB.getPolNo().equals((String) CVData.get(t)))
		                		  {
		                			  Flag = true;
		                		  }
		                	  }
		                	  if (Flag)
		                	  {
		                		  //如果已经处理循环下一条
		                		  continue;
		                	  }
		                	  if(judgeTempFee(tSSRS.GetText(1, 2))==false)
		                	  {
		                		  continue;
		                	  }

		                	  if (dealSingleSubPol(tLCPolSchema,"0") == false)
		                	  {
//		            	   	 	return false;
		                	  }
		                  }
		              }
		        }

		       if((mType1.equals("2"))||(mType1.equals("3")))  //在职单
		        {
		            if((mType3.equals("1"))||(mType3.equals("3")))  //续期件
		              {

		                	  if(!judgeTempFee(tLCPolSchema.getPolNo()))
		              		  {
		                		  continue;
		              		  }

		              		  if (!dealSinglePol(tLCPolSchema,"1"))
		              		  {
//		              			  return false;
		              		  }

		               }
		             if((mType3.equals("2"))||(mType3.equals("3")))  //续保件
		             {
		                   //7--在职单需要续保的主险清单

		            	    addPrompt("***以下是在职单主险自动续保数据***");
		            	if(tSSRS.GetText(1, 2).equals("9999"))
		            	{
		            		if(judgeTempFee(tLCPolSchema.getPolNo())==false)
		            		{
		            			continue;
		            		}
		            		if (dealSingleRnewPol(tLCPolSchema,"1") == false)
		            		{
//		            			return false;
		            		}
		            	}
		            	else{
		                    //8--在职单需要续保的附加险  99999
		            		if (!judgeMainRisk(tSSRS.GetText(1, 2), tSSRS.GetText(1, 3)))
		            		  {
		            		    continue;
		            	      }
		            	      tLCPolSchema = new LCPolSchema();
		            	      LCPolDB tLCPolDB = new LCPolDB();
		            	      tLCPolDB.setPolNo(tSSRS.GetText(1, 1));
		            	      tLCPolDB.getInfo();
		            	      tLCPolSchema.setSchema(tLCPolDB.getSchema());

		            	      boolean Flag = false;
		            	      for (int t = 0; t < CVData.size(); t++)
		            	      {
		            			if (tLCPolDB.getPolNo().equals((String) CVData.get(t)))
		            			{
		            			   Flag = true;
		            		    }
		            	      }
		            	      if (Flag)
		            	      {
		            			//如果已经处理循环下一条
		            			continue;
		            	      }
		            	      if(judgeTempFee(tSSRS.GetText(1, 2))==false)
		            	      {
		            		    continue;
		            	      }

		            	      if (dealSingleSubPol(tLCPolSchema,"1") == false)
		            	      {
//		            		    return false;
		            	      }
		            	}
		            }
		        }
		    }
		    catch(Exception ex)
		    {
		      this.buildError("",ex.toString());
		      logger.debug("@@@ex:"+ex.toString());
//		      return false;
		    }
//		    return true;
			
			

		}

//		TransferData mTransferData = new TransferData();
//		mTransferData = mInputDataNew.getObjectByObjectName("TransferData", 0)==null?null
//				:(TransferData)mInputDataNew.getObjectByObjectName("TransferData", 0);
//		if(mTransferData!=null)
//		{
//			String tActivityid = (String)mTransferData.getValueByName("ActivityID");
//			submitData(mInputDataNew, tActivityid);
//		}
		this.close();
	}
	  
	  /**
	   * 处理单个需要自动续保的附加险的清单数据
	   * @param tLCPolSchema
	   * @return
	   */
	  private boolean dealSingleSubPol(LCPolSchema tLCPolSchema,String Flag)
	  {
	    //Flag 0:孤儿单 ；1:在职单
	    int t = 0;
	    int subNum = 0;
	    String AgentName = ""; //业务员姓名
	    String InsuName = ""; //被保人姓名
	    String AppName = ""; //  客户姓名
	    String SamePerson = "N";
	    String AgentGroup ="";
	    LCContSchema tLCContSchema = new LCContSchema();
	    LCContDB tLCContDB = new LCContDB();
	    tLCContDB.setContNo(tLCPolSchema.getContNo());
	    if(!tLCContDB.getInfo())
	    {
	    	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
	    	return false;
	    }
	    tLCContSchema = tLCContDB.getSchema();

	    try
	    {
	      AgentName = ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode())
			.trim();
	      AppName = tLCPolSchema.getAppntName().trim();
	      InsuName = tLCPolSchema.getInsuredName().trim();
	      if (AgentName.equals(AppName) || AgentName.equals(InsuName))
	      {
		SamePerson = "Y";
	      }

	      //找到该报单的投保单号码，后面用于查询续收员信息
	      logger.debug(
		  "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	      String sqlstrtemp = "select ProposalNo from lcrnewstatehistory where contno='"
				+ "?pay?" + "' and riskcode='"+"?riskcode?"+"' order by paytodate desc";
	      logger.debug("sqlstrtemp:" + sqlstrtemp);
	      SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	      sqlbv3.sql(sqlstrtemp);
	      sqlbv3.put("riskcode", tLCPolSchema.getRiskCode());
	      sqlbv3.put("pay", tLCPolSchema.getContNo());
	      ExeSQL TTexesql = new ExeSQL();
	      String temppolno = TTexesql.getOneValue(sqlbv3);
	      if ((temppolno == null) || temppolno.equals(""))
	      {
		return false;
	      }

	      //如有数据错误，可以写在excel里
	      m_book.setEntry(mCurrentRow, t + 0, tLCPolSchema.getManageCom()); //管理机构
	      m_book.setEntry(mCurrentRow, t + 1, AgentName); //业务员姓名
	      m_book.setEntry(mCurrentRow, t + 2, tLCPolSchema.getAgentCode()); //业务员代码
	      AgentGroup =GetAgentGroup(tLCPolSchema);
	      String AgentBranch = findAgentBranch(AgentGroup);
	      m_book.setEntry(mCurrentRow, t + 3, AgentBranch); //业务员组别
	      m_book.setEntry(mCurrentRow, t + 4, tLCPolSchema.getAppntName()); //客户姓名
	      String AppIDNo="";
	      String InsuredIDNo="";
	      ExeSQL pExeSQL = new ExeSQL();
	      SSRS pssrs_idno =new SSRS();
	      SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	      sqlbv4.sql(" select a.idno,b.idno from lcappnt a,lcinsured b where a.contno=b.contno and a.contno="+"?contno?"
					 +" and a.appntno='"+"?appntno?"+"' and b.InsuredNo='"+"?InsuredNo?"+"'");
	      sqlbv4.put("contno", tLCPolSchema.getContNo());
	      sqlbv4.put("appntno", tLCPolSchema.getAppntNo());
	      sqlbv4.put("InsuredNo", tLCPolSchema.getInsuredNo());
	      pssrs_idno=pExeSQL.execSQL(sqlbv4);
	      AppIDNo=pssrs_idno.GetText(1,1);
	      InsuredIDNo=pssrs_idno.GetText(1,2);
	      m_book.setEntry(mCurrentRow, t + 5, AppIDNo); //客户(投保人)身份证号

	      m_book.setEntry(mCurrentRow, t + 6, tLCPolSchema.getContNo()); //保单号
	      m_book.setEntry(mCurrentRow, t + 7, tLCPolSchema.getPaytoDate()); //缴费日期
	      String PayMode = findPayMode(tLCPolSchema.getContNo(),
	    		  tLCContSchema.getPayLocation());
	      m_book.setEntry(mCurrentRow, t + 8, String.valueOf(PayMode)); //缴费方式

	      m_book.setEntry(mCurrentRow, t + 40,
			      ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl())); //销售渠道
	      m_book.setEntry(mCurrentRow, t + 41, SamePerson); //代理人和投保人和被保人是否同一人标记
	      //--------------------
	      //新增交费年期,交费次数
	      m_book.setEntry(mCurrentRow, t + 55, new Integer(tLCPolSchema.getPayYears()).toString()); //交费年期
	      String tPayCount = "";
	      LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
	      tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
	      tLMRiskAppDB.getInfo();
	      if (tLMRiskAppDB.getRiskPeriod().equals("L")&&(tLCPolSchema.getPayIntv()!=0)){
	        String sqlPayCount = "select  paytimes+1 from lcprem where polno ='" + "?polno?" +"'" ;
	        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	        sqlbv5.sql(sqlPayCount);
	        sqlbv5.put("polno", tLCPolSchema.getPolNo());
	        tPayCount = pExeSQL.getOneValue(sqlbv5);
	      }else{
	        tPayCount = "1";
	      }
	      m_book.setEntry(mCurrentRow, t + 56, tPayCount); //交费次数
	      m_book.setEntry(mCurrentRow, t + 59,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
	      m_book.setEntry(mCurrentRow, t + 60,tLCPolSchema.getContNo());//合同号
	      //--------------------

	      //主险趸缴的也得显示主险名称，保费项填趸缴
	      m_book.setEntry(mCurrentRow, t + 9,
	                      ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode())); //主险险种
	      m_book.setEntry(mCurrentRow, t + 10, String.valueOf("趸交")); //主险保费

	      double sumprem = 0.0; //保费合计
	      String getnoticeno_sql =
		  "select otherno,getnoticeno from ljspay where getnoticeno in ( select getnoticeno from ljspayperson where polno='"
		  + "?temppolno?" + "')";
	      String tGetNoticeNo = "";
	      String tContNo = "";
	      ExeSQL mainpolno_exe = new ExeSQL();
	      SSRS Temp_SSRS = new SSRS();
	      SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
	        sqlbv6.sql(getnoticeno_sql);
	        sqlbv6.put("temppolno", temppolno);
	      Temp_SSRS = mainpolno_exe.execSQL(sqlbv6);
	      if ((Temp_SSRS != null) && (Temp_SSRS.getMaxRow() > 0))
	      {
	    	  tContNo = Temp_SSRS.GetText(1, 1);
		      tGetNoticeNo = Temp_SSRS.GetText(1, 2);
	      }
	      else
	      {
		    String tMainPolNo_Sql="select contno from lcpol where polno ='"+"?polno?"+"'";
		    SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
	        sqlbv7.sql(tMainPolNo_Sql);
	        sqlbv7.put("polno", tLCPolSchema.getPolNo());
		    ExeSQL tExe = new ExeSQL();
		    tContNo = tExe.getOneValue(sqlbv7);
	      }
	      LCPolDB tLCPolDB = new LCPolDB();
	      tLCPolDB.setContNo(tContNo);
	      if (tLCPolDB.query().size()==0)
	      {
		     return false;
	      }

	      LCPolSet tLCPolSet = new LCPolSet();
	      tLCPolSet = this.findSubPol3(tLCPolDB.getSchema());

	      double tLeavingMoney = 0; //原余额
	      double tNewLeavingMoney = 0; //新余额
	      double tActualPayMoney = 0; //实交保费

	      if (tLCPolSet.size() > 0)
		  {
			String prem = "0";

			//如果多于一条，则在excel的下一行的同一列位置显示，同时mSubNum增长
			for (int i = 1; i <= tLCPolSet.size(); i++)
			{
				if (String.valueOf(tLCPolSet.get(i).getLeavingMoney()) != null)
				  {
				    tLeavingMoney = tLeavingMoney + tLCPolSet.get(i).getLeavingMoney();
				  }
				String SQL_SubPol = "";
				ExeSQL tExeSQL = new ExeSQL();
				SQL_SubPol = "select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSPayPerson where riskcode='"
				    + "?riskcode?" + "' and PayType='ZC'";
				SQL_SubPol = SQL_SubPol + " and GetNoticeNo='"
				    + tGetNoticeNo + "'";
				tExeSQL = new ExeSQL();
				SSRS tSSRS = new SSRS();
			    SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		        sqlbv8.sql(SQL_SubPol);
		        sqlbv8.put("riskcode", tLCPolSet.get(i).getRiskCode());
				tSSRS = tExeSQL.execSQL(sqlbv8);
				if ((tSSRS != null) && (tSSRS.getMaxRow() > 0))
				{
				  prem = tSSRS.GetText(1, 1);
				}

			    m_book.setEntry(mCurrentRow , t + 11+2*(i-1),
					ChangeCodetoName.getRiskName(tLCPolSet.get(i)
					.getRiskCode())); //附险险种


			    m_book.setEntry(mCurrentRow , t + 12+2*(i-1), prem); //附险保费

		        if(tSSRS.GetText(1,1).equals("0"))
		        {
		           m_book.setEntry(mCurrentRow ,t+12+2*(i-1),"附加险续保保费未催收："+ getRnewFailReason(tLCPolSet.get(i)));//主险催收失败的原因
		        }
			    sumprem = sumprem + Double.parseDouble(prem);

			    this.CVData.addElement(tLCPolSet.get(i).getPolNo());
			}
		  }

	      m_book.setEntry(mCurrentRow, t + 25, String.valueOf(sumprem)); //保费合计
	      m_book.setEntry(mCurrentRow, t + 26, String.valueOf(tLeavingMoney)); //预收金额
	      if ((tLeavingMoney - sumprem) > 0)
	      {
		tActualPayMoney = 0;
		tNewLeavingMoney = tLeavingMoney - sumprem;
	      }
	      else
	      {
		tActualPayMoney = sumprem - tLeavingMoney;
		tNewLeavingMoney = 0;
	      }
	      m_book.setEntry(mCurrentRow, t + 27,
			      mDecimalFormat.format(tActualPayMoney)); //实交保费合计
	      m_book.setEntry(mCurrentRow, t + 28,
			      mDecimalFormat.format(tNewLeavingMoney)); //余额扣除本次保费合计

	      //投保人信息
	      LCAppntDB tLCAppntDB = new LCAppntDB();
	      tLCAppntDB.setContNo(tLCPolSchema.getContNo());
	      tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
	      if (tLCAppntDB.getInfo() == true)
	      {
	    	LCAddressDB tLCAddressDB = new LCAddressDB();
	    	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
	    	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
	    	if (tLCAddressDB.getInfo() == true)
	        {
				m_book.setEntry(mCurrentRow, t + 29,
						tLCAddressDB.getPostalAddress()); //收费地址
				m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
				m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
				m_book.setEntry(mCurrentRow, t + 39,
						ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex())); //性别
				m_book.setEntry(mCurrentRow, t + 43, tLCAddressDB.getMobile()); //投保人手机
	                        m_book.setEntry(mCurrentRow, t + 57, tLCAddressDB.getHomePhone());//家电
	                        m_book.setEntry(mCurrentRow, t + 58, tLCAddressDB.getCompanyPhone());//公司电
	        }
	      }
	      //此处新加被保人身份证号
	      m_book.setEntry(mCurrentRow, t + 32, tLCPolSchema.getInsuredName()); //被保人姓名
	      String[] BankInfo = findBankInfo(tLCPolSchema);
	      m_book.setEntry(mCurrentRow, t + 33, InsuredIDNo); //被保人身份证号

	      m_book.setEntry(mCurrentRow, t + 34, BankInfo[0]); //银行帐号
	      m_book.setEntry(mCurrentRow, t + 35,
			      ChangeCodetoName.getBankCodeName(BankInfo[1])); //银行编码
	      m_book.setEntry(mCurrentRow, t + 36, BankInfo[2]); //户名

	      LAAgentDB tLAAgentDB = new LAAgentDB();
	      tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
	      String AgentTel = "";
	      if (tLAAgentDB.getInfo() == true)
	      {
		AgentTel = tLAAgentDB.getPhone();
		if ((AgentTel == null) || AgentTel.equals(""))
		{
		  AgentTel = tLAAgentDB.getMobile();
		}
		if (AgentTel == null)
		{
		  AgentTel = "";
		}
	      }
	      m_book.setEntry(mCurrentRow, t + 37, AgentTel); //代理人电话
	      LJAPayDB tLJAPayDB = new LJAPayDB();
	      tLJAPayDB.setIncomeNo(tLCPolSchema.getPolNo());
	      tLJAPayDB.setIncomeType("2");
	      LJAPaySet tLJAPaySet = tLJAPayDB.query();
	      m_book.setEntry(mCurrentRow, t + 38,
			      String.valueOf(tLJAPaySet.size())); //交费期数

	      m_book.setEntry(mCurrentRow, t + 42, tLCPolSchema.getAgentCom()); //代理机构
	      m_book.setEntry(mCurrentRow, t + 44, tLCPolSchema.getPrtNo());

	      LACommisionDB tLACommisionDB = new LACommisionDB();
	      tLACommisionDB.setPolNo(tLCPolSchema.getPolNo());
	      tLACommisionDB.setCurPayToDate(tLCPolSchema.getPaytoDate());
	      LACommisionSet tLACommisionSet = tLACommisionDB.query();
	      if (tLACommisionSet.size() > 0)
	      {
		LAAgentDB oldAgentDB = new LAAgentDB();
		oldAgentDB.setAgentCode(tLACommisionSet.get(1).getAgentCode());
		if (oldAgentDB.getInfo() == true)
		{
		  String oldAgentPhone = oldAgentDB.getPhone();
		  if (oldAgentPhone == null)
		  {
		    oldAgentPhone = "";
		  }
		  String oldAgentMobile = oldAgentDB.getMobile();
		  if (oldAgentMobile == null)
		  {
		    oldAgentMobile = "";
		  }
		  m_book.setEntry(mCurrentRow, t + 46, oldAgentDB.getName()); ///原业务员姓名
		  m_book.setEntry(mCurrentRow, t + 47, oldAgentPhone); ///原业务员电话
		  m_book.setEntry(mCurrentRow, t + 48, oldAgentMobile); ///原业务员手机
		}
	      }
	      if(Flag.equals("1"))  //若为在职单，须加上续收外勤工号与姓名
	      {
	         LRAdimAscriptionDB tlradimascriptionDB = new LRAdimAscriptionDB();
	         tlradimascriptionDB.setPolNo(tLCPolSchema.getPolNo());
	         LRAdimAscriptionSet tLRAdimAscriptionSet = tlradimascriptionDB.query();
	         if (tLRAdimAscriptionSet.size() > 0)
	         {
	            m_book.setEntry(mCurrentRow, t + 49, tLRAdimAscriptionSet.get(1).getAgentCode()); ///原业务员手机
	            String getname="";
	            getname=" select name from laagent where agentcode='"+"?agentcode?"+"' ";
	            SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		        sqlbv9.sql(getname);
		        sqlbv9.put("agentcode", tLRAdimAscriptionSet.get(1).getAgentCode());
	            ExeSQL xExeSQL = new ExeSQL();
	           m_book.setEntry(mCurrentRow, t + 50, xExeSQL.getOneValue(sqlbv9)); ///原业务员手机
	         }

	      }

	      if((tLCPolSchema.getAutoPayFlag()!= null)&&(tLCPolSchema.getAutoPayFlag().equals("1")))
	      {
	         m_book.setEntry(mCurrentRow, t + 51, "是"); ///客户垫交意愿
	      }
	      else
	      {
	         m_book.setEntry(mCurrentRow, t + 51, "否"); ///客户垫交意愿
	      }


	    }
	    catch (Exception ex)
	    {
	      try
	      {
		m_book.setEntry(mCurrentRow, t + 0,
				"***处理保单" + tLCPolSchema.getPolNo() + " 出错：" + ex); //管理机构
		mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
	      }
	      catch (Exception ex2)
	      {
		buildError("dealSinglePol", ex.toString());
		return false;
	      }
	      return true;
	    }

	 //   mCurrentRow = mCurrentRow + subNum; //如果有超过1个以上附加险，添加新的行存放第二个（以上）附加险信息
	    mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
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
	    	tSQL = "select ErrInfo from LCRnewErrLog where PrtNo='"+"?PrtNo?"+"' and Riskcode='"+"?Riskcode?"+"' and ErrNo=1 and Rownum=1 ";
	    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	    	tSQL = "select ErrInfo from LCRnewErrLog where PrtNo='"+"?PrtNo?"+"' and Riskcode='"+"?Riskcode?"+"' and ErrNo=1 limit 0,1 ";
	    }
	    SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
	    sqlbv10.sql(tSQL);
	    sqlbv10.put("PrtNo", tLCPolSchema.getPrtNo());
	    sqlbv10.put("Riskcode", tLCPolSchema.getRiskCode());
	    tReason = tExeSQL.getOneValue(sqlbv10);
	    return tReason;
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
	  /**
	   * 判断主险是否符合条件：非续期，非续保，未到期
	   * @param tPolNo   主险号
	   * @param tPayEndDate  附加险终交日期
	   * @return
	   */
	  private boolean judgeMainRisk(String tPolNo, String tPayEndDate)
	  {
	    LCPolDB tLCPolDB = new LCPolDB();
	    String strSQL = "select * from lcpol where polno='" + "?tPolNo?" + "' ";
	    strSQL = strSQL + " and appflag='1'";
	    strSQL = strSQL + " and RnewFlag!=-1 "; //不是自动续保的
	    strSQL = strSQL + " and payintv=0 "; //不是续期的
	    strSQL = strSQL + " and payenddate>'" + "?tPayEndDate?" + "'"; //未到期的

	    //如果不符合条件-则返回false
	    SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
	    sqlbv11.sql(strSQL);
	    sqlbv11.put("tPolNo", tPolNo);
	    sqlbv11.put("tPayEndDate", tPayEndDate);
	    LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv11);
	    if (tLCPolSet.size() == 0)
	    {
	      return false;
	    }

	    return true;
	  }

	  /**
	   * 找银行信息
	   * @param tPolNo
	   * @return
	   */
	  private String[] findBankInfo(String tContNo)
	  {
	    String[] bankInfo = new String[3];
	    String SQL = "select tempfeeno from LJTempFee where otherno='" + "?tContNo?"
		       + "' and confflag='1' order by makedate desc";
	    SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
	    sqlbv12.sql(SQL);
	    sqlbv12.put("tContNo", tContNo);
	    ExeSQL tExeSQL = new ExeSQL();
	    SSRS tSSRS = tExeSQL.execSQL(sqlbv12);
	    if (tSSRS == null)
	    {
	      return bankInfo;
	    }
	    String tempfeeno = tSSRS.GetText(1, 1);

	    LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
	    tLJTempFeeClassDB.setTempFeeNo(tempfeeno);
	    LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
	    if (tLJTempFeeClassSet.size() == 0)
	    {
	      return bankInfo;
	    }
	    else
	    {
	      bankInfo[0] = tLJTempFeeClassSet.get(1).getBankAccNo();
	      bankInfo[1] = tLJTempFeeClassSet.get(1).getBankCode();
	      bankInfo[2] = tLJTempFeeClassSet.get(1).getAccName();
	    }
	    return bankInfo;
	  }
	  /**
	   * 特殊处理主险趸交,查询附加险
	   * @param tLCPolSchema
	   * @return
	   */
	  private LCPolSet findSubPol3(LCPolSchema tLCPolSchema)
	  {
	    logger.debug("in findSubPol()");
	    LCPolDB tLCPolDB = new LCPolDB();
	    String strSQL = "select * from lcpol where mainpolno='"
			  + "?mainpolno?" + "' ";
	    strSQL = strSQL + "and polno!='" + "?mainpolno?" + "' ";
	    strSQL = strSQL + "and AppFlag='1'  ";
	    strSQL = strSQL
		   + "and ((PaytoDate = PayEndDate and RnewFlag = '-1' and exists (select 1 from lmrisk where riskcode=lcpol.riskcode and rnewflag='Y')) or PayToDate < PayEndDate)";
	    SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
	    sqlbv13.sql(strSQL);
	    sqlbv13.put("mainpolno", tLCPolSchema.getPolNo());
	    LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv13);

	    return tLCPolSet;
	  }

	  
	  private String getPolState(String tContNo,String tPolNo){
		    ExeSQL tExeSQL  = new ExeSQL();
		    String tPolState = "select statetype,state from lccontstate where contno='"+"?tContNo?"+"' and (polno='"+"?tPolNo?"+"' or polno='000000') order by startdate desc";
		    SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
		    sqlbv14.sql(tPolState);
		    sqlbv14.put("tContNo", tContNo);
		    sqlbv14.put("tPolNo", tPolNo);

		    SSRS tSSRS = new SSRS();
		    tSSRS = tExeSQL.execSQL(sqlbv14);
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
	   * 交费方式
	   * @param tPolNo
	   * @param PayLocation
	   * @return
	   */
	  private String findPayMode(String tContNo, String PayLocation)
	  {
	    if (PayLocation == null)
	    {
	      return findPayMode2(tContNo);
	    }
	    else
	    {
	      return ChangeCodetoName.getPayLocationName(PayLocation);
	    }
	  }
	  /**
	   * 找交费方式
	   * @param tPolNo
	   * @return
	   */
	  private String findPayMode2(String tContNo)
	  {
	    String SQL = "select tempfeeno from LJTempFee where otherno='" + "?tContNo?"
		       + "' and confflag='1' order by makedate desc";
	    SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
	    sqlbv15.sql(SQL);
	    sqlbv15.put("tContNo", tContNo);

	    ExeSQL tExeSQL = new ExeSQL();
	    SSRS tSSRS = tExeSQL.execSQL(sqlbv15);
	    if (tSSRS == null)
	    {
	      return "unknow";
	    }
	    String tempfeeno = tSSRS.GetText(1, 1);

	    LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
	    tLJTempFeeClassDB.setTempFeeNo(tempfeeno);
	    LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
	    if (tLJTempFeeClassSet.size() == 0)
	    {
	      return "unknow";
	    }
	    else
	    {
	      return ChangeCodetoName.getPayModeName(tLJTempFeeClassSet.get(1)
		  .getPayMode());
	    }
	  }
	  /**
	   * 得到展业机构外部编码--中文名称
	   * @param tAgentGroup
	   * @return
	   */
	  private String findAgentBranch(String tAgentGroup)
	  {
	    LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
	    tLABranchGroupDB.setAgentGroup(tAgentGroup);
	    if (tLABranchGroupDB.getInfo() == false)
	    {
	      return "unknow";
	    }
	    else
	    {
	      return tLABranchGroupDB.getName();
	    }
	  }
	  private String GetAgentGroup(LCPolSchema tLCPolSchema){
		  String tAgentGroup="";
		  String tAgentCode =tLCPolSchema.getAgentCode();
		  //查询代理人相应的服务部信息
		  ExeSQL tExeSQL =new ExeSQL();
		  SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		    sqlbv16.sql("select agentgroup from laagent where agentcode ='"+"?tAgentCode?"+"'");
		    sqlbv16.put("tAgentCode", tAgentCode);
		  tAgentGroup =tExeSQL.getOneValue(sqlbv16);
		  if("".equals(tAgentGroup)){
			  tAgentGroup =tLCPolSchema.getAgentGroup();
		  }
		  return tAgentGroup;
	  }
	  /**
	   * 添加每个保单的信息-针对续期主险
	   * @param tLCPolSchema
	   * @param t_book
	   * @param n
	   * @return
	   */
	  private boolean dealSinglePol(LCPolSchema tLCPolSchema,String Flag)
	  {
	    //Flag 0:孤儿单 ；1:在职单
	    logger.debug("开始填充数据");
	    int t = 0;
	    int subNum = 0;
	    String AgentName = ""; //业务员姓名
	    String InsuName = ""; //被保人姓名
	    String AppName = ""; //  客户姓名
	    String SamePerson = "N";
	    String AgentGroup ="";
	    LCContSchema tLCContSchema = new LCContSchema();
	    LCContDB tLCContDB = new LCContDB();
	    tLCContDB.setContNo(tLCPolSchema.getContNo());
	    if(!tLCContDB.getInfo())
	    {
	    	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
	    	return false;
	    }
	    tLCContSchema = tLCContDB.getSchema();

	    try
	    {
	      AgentName = ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode())
			.trim();
	      AppName = tLCPolSchema.getAppntName().trim();
	      InsuName = tLCPolSchema.getInsuredName().trim();
	      if (AgentName.equals(AppName) || AgentName.equals(InsuName))
	      {
		    SamePerson = "Y";
	      }

	      //如有数据错误，可以写在excel里
	      m_book.setEntry(mCurrentRow, t + 0, tLCPolSchema.getManageCom()); //管理机构
	      m_book.setEntry(mCurrentRow, t + 1, AgentName); //业务员姓名
	      m_book.setEntry(mCurrentRow, t + 2, tLCPolSchema.getAgentCode()); //业务员代码
	      AgentGroup =GetAgentGroup(tLCPolSchema);
	      String AgentBranch = findAgentBranch(AgentGroup);
	      m_book.setEntry(mCurrentRow, t + 3, AgentBranch); //业务员组别
	      m_book.setEntry(mCurrentRow, t + 4, AppName); //客户姓名
	      //增加客户身份证号,被投保人身份证号
	      String AppIDNo="";
	      String InsuredIDNo="";
	      ExeSQL pExeSQL = new ExeSQL();
	      SSRS pssrs_idno =new SSRS();
	      SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
	      sqlbv16.sql(" select a.idno,b.idno from lcappnt a,lcinsured b where a.contno=b.contno and a.contno="+"?contno?"
					 +" and a.appntno='"+"?appntno?"+"' and b.InsuredNo='"+"?InsuredNo?"+"'");
	      sqlbv16.put("contno", tLCPolSchema.getContNo());
	      sqlbv16.put("appntno", tLCPolSchema.getAppntNo());
	      sqlbv16.put("InsuredNo", tLCPolSchema.getInsuredNo());
	      
	      pssrs_idno=pExeSQL.execSQL(sqlbv16);
	      AppIDNo=pssrs_idno.GetText(1,1);
	      InsuredIDNo=pssrs_idno.GetText(1,2);
	      m_book.setEntry(mCurrentRow, t + 5, AppIDNo); //客户(投保人)身份证号

	      m_book.setEntry(mCurrentRow, t + 6, tLCPolSchema.getContNo()); //保单号
	      m_book.setEntry(mCurrentRow, t + 7, tLCPolSchema.getPaytoDate()); //缴费日期
	      String PayMode = findPayMode(tLCPolSchema.getContNo(),
	    		  tLCContSchema.getPayLocation());
	      m_book.setEntry(mCurrentRow, t + 8, String.valueOf(PayMode)); //缴费方式

	      m_book.setEntry(mCurrentRow, t + 40,
			      ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl())); //销售渠道
	      m_book.setEntry(mCurrentRow, t + 41, SamePerson); //代理人和投保人和被保人是否同一人标记
	      m_book.setEntry(mCurrentRow, t + 42, tLCPolSchema.getAgentCom()); //代理机构
	      //--------------------
	      //新增交费年期,交费次数
	      m_book.setEntry(mCurrentRow, t + 55, new Integer(tLCPolSchema.getPayYears()).toString()); //交费年期
	      String tPayCount = "";
	      LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
	      tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
	      tLMRiskAppDB.getInfo();
	      if (tLMRiskAppDB.getRiskPeriod().equals("L")&&(tLCPolSchema.getPayIntv()!=0)){
	        String sqlPayCount = "select  paytimes+1 from lcprem where polno ='" + "?polno?" +"'" ;
	        SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
	        sqlbv17.sql(sqlPayCount);
	        sqlbv17.put("polno", tLCPolSchema.getPolNo());
	        tPayCount = pExeSQL.getOneValue(sqlbv17);
	      }else{
	        tPayCount = "1";
	      }
	      m_book.setEntry(mCurrentRow, t + 56, tPayCount); //交费次数
	      m_book.setEntry(mCurrentRow, t + 59,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
	      m_book.setEntry(mCurrentRow, t + 60,tLCPolSchema.getContNo());//合同号
	      //--------------------

	      double mainPolPrem = 0;
	      mainPolPrem = getMainPolPrem(tLCPolSchema.getPolNo());
	      LJSPayDB tLJSPayDB = new LJSPayDB();
	      tLJSPayDB.setOtherNo(tLCPolSchema.getContNo());
	      tLJSPayDB.setOtherNoType("2");
	      LJSPaySet tLJSPaySet = tLJSPayDB.query();
	      m_book.setEntry(mCurrentRow, t + 9,
			      ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode())); //主险险种
	      double sumprem = 0.0; //保费合计
	      if ((tLJSPaySet == null) || (tLJSPaySet.size() == 0))
	      {
	         m_book.setEntry(mCurrentRow,t+10,"主险续保保费未催收："+ getRnewFailReason(tLCPolSchema));//主险催收失败的原因
	      }
	      else
	      {
	         m_book.setEntry(mCurrentRow, t + 10, String.valueOf(mainPolPrem)); //主险保费
	         sumprem = sumprem + mainPolPrem;
	      }



	      String prem = "0";

	      double mLeavingMoney = tLCPolSchema.getLeavingMoney(); //预收金额
	      double tActualPayMoney = 0; //实缴金额
	      double tNewLeavingMoney = 0; //余额

	      LCPolSet tLCPolSet = findSubPol(tLCPolSchema);
	      int i = 0;
	      int j = 0;
	      if (tLCPolSet.size() > 0)
	      {
		//如果多于一条，则在excel的下一行的同一列位置显示，同时mSubNum增长
		for (i = 1; i <= tLCPolSet.size(); i++)
		{
		  mLeavingMoney += tLCPolSet.get(i).getLeavingMoney(); //合计所有险种下的余额

		  String SQL_SubPol =
		      "select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSPayPerson where riskcode='"
		      + "?riskcode?"
		      + "' and PayType='ZC'  ";
		  SQL_SubPol = SQL_SubPol
		      + "and getnoticeno in (select getnoticeno from ljspay where othernotype='2' and otherno='"
		      + "?otherno?" + "')";
		  ExeSQL tExeSQL = new ExeSQL();
		  SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
	        sqlbv18.sql(SQL_SubPol);
	        sqlbv18.put("riskcode", tLCPolSet.get(i).getRiskCode());
	        sqlbv18.put("otherno", tLCPolSchema.getContNo());
		  SSRS tSSRS = tExeSQL.execSQL(sqlbv18);
		  if ((tSSRS != null) && (tSSRS.getMaxRow() > 0))
		  {
		    prem = tSSRS.GetText(1, 1);
		  }
		  m_book.setEntry(mCurrentRow , t + 11 +2*(i-1),
				  ChangeCodetoName.getRiskName(tLCPolSet.get(i)
				  .getRiskCode())); //附险险种
		  m_book.setEntry(mCurrentRow, t + 12 +2*(i-1), prem); //附险保费
		  sumprem = sumprem + Double.parseDouble(prem);
		}
		subNum = (subNum + tLCPolSet.size()) - 1;
	      }

	      m_book.setEntry(mCurrentRow, t + 25, String.valueOf(sumprem)); //保费合计

	      m_book.setEntry(mCurrentRow, t + 26, String.valueOf(mLeavingMoney)); //预收金额
	      if ((mLeavingMoney - sumprem) > 0)
	      {
		tActualPayMoney = 0;
		tNewLeavingMoney = mLeavingMoney - sumprem;
	      }
	      else
	      {
		tActualPayMoney = sumprem - mLeavingMoney;
		tNewLeavingMoney = 0;
	      }
	      m_book.setEntry(mCurrentRow, t + 27,
			      mDecimalFormat.format(tActualPayMoney)); //实交保费合计
	      m_book.setEntry(mCurrentRow, t + 28,
			      mDecimalFormat.format(tNewLeavingMoney)); //余额扣除本次保费合计

	      //投保人信息
	      LCAppntDB tLCAppntDB = new LCAppntDB();
	      tLCAppntDB.setContNo(tLCPolSchema.getContNo());
	      tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
	      if (tLCAppntDB.getInfo() == true)
	      {
	    	LCAddressDB tLCAddressDB = new LCAddressDB();
	    	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
	    	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
	    	if (tLCAddressDB.getInfo() == true)
	        {
				m_book.setEntry(mCurrentRow, t + 29,
						tLCAddressDB.getPostalAddress()); //收费地址
				m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
				m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
				m_book.setEntry(mCurrentRow, t + 39,
						ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex())); //性别
				m_book.setEntry(mCurrentRow, t + 43, tLCAddressDB.getMobile()); //投保人手机
	                        m_book.setEntry(mCurrentRow, t + 57, tLCAddressDB.getHomePhone());//家电
	                        m_book.setEntry(mCurrentRow, t + 58, tLCAddressDB.getCompanyPhone());//公司电
	        }
	      }
	      //此处新加被保人身份证号
	      m_book.setEntry(mCurrentRow, t + 32, tLCPolSchema.getInsuredName()); //被保人姓名
	      String[] BankInfo = findBankInfo(tLCPolSchema);
	      m_book.setEntry(mCurrentRow, t + 33, InsuredIDNo); //被保人身份证号

	      m_book.setEntry(mCurrentRow, t + 34, BankInfo[0]); //银行帐号
	      m_book.setEntry(mCurrentRow, t + 35,
			      ChangeCodetoName.getBankCodeName(BankInfo[1])); //银行编码
	      m_book.setEntry(mCurrentRow, t + 36, BankInfo[2]); //户名

	      LAAgentDB tLAAgentDB = new LAAgentDB();
	      tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
	      String AgentTel = "";
	      if (tLAAgentDB.getInfo() == true)
	      {
		AgentTel = tLAAgentDB.getPhone();
		if ((AgentTel == null) || AgentTel.equals(""))
		{
		  AgentTel = tLAAgentDB.getMobile();
		}
		if (AgentTel == null)
		{
		  AgentTel = "";
		}
	      }
	      m_book.setEntry(mCurrentRow, t + 37, AgentTel); //代理人电话

	      ExeSQL tExeSQL = new ExeSQL();
	      String tSql = "select max(PayTimes) from LCPrem where PolNo='"
			  + "?PolNo?" + "'";
	      SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	      sqlbv.sql(tSql);
	      sqlbv.put("PolNo", tLCPolSchema.getPolNo());
	      String tPayTimes = tExeSQL.getOneValue(sqlbv);
	      m_book.setEntry(mCurrentRow, t + 38, tPayTimes); //缴费次数
	      m_book.setEntry(mCurrentRow, t + 44,
			      String.valueOf(tLCPolSchema.getPrtNo())); //印刷号
	      tSql = " select (case count(*) when 0 then '正常' else '垫交' end) from lccontstate where  contno ='"+"?contno?"+"' and (polno='"
		   + "?polno?" + "' or polno='000000') and statetype='PayPrem' and state='1' and enddate is null ";
	      SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
	        sqlbv19.sql(tSql);
	        sqlbv19.put("contno", tLCPolSchema.getContNo());
	        sqlbv19.put("polno", tLCPolSchema.getPolNo());
	      String djFlag = tExeSQL.getOneValue(sqlbv19);
	      m_book.setEntry(mCurrentRow, t + 45, djFlag); //垫交标记

	      LACommisionDB tLACommisionDB = new LACommisionDB();
	      tLACommisionDB.setPolNo(tLCPolSchema.getPolNo());
	      tLACommisionDB.setCurPayToDate(tLCPolSchema.getPaytoDate());
	      LACommisionSet tLACommisionSet = tLACommisionDB.query();
	      if (tLACommisionSet.size() > 0)
	      {
		LAAgentDB oldAgentDB = new LAAgentDB();
		oldAgentDB.setAgentCode(tLACommisionSet.get(1).getAgentCode());
		if (oldAgentDB.getInfo() == true)
		{
		  String oldAgentPhone = oldAgentDB.getPhone();
		  if (oldAgentPhone == null)
		  {
		    oldAgentPhone = "";
		  }
		  String oldAgentMobile = oldAgentDB.getMobile();
		  if (oldAgentMobile == null)
		  {
		    oldAgentMobile = "";
		  }
		  m_book.setEntry(mCurrentRow, t + 46, oldAgentDB.getName()); ///原业务员姓名
		  m_book.setEntry(mCurrentRow, t + 47, oldAgentPhone); ///原业务员电话
		  m_book.setEntry(mCurrentRow, t + 48, oldAgentMobile); ///原业务员手机
		}
	      }
	      if(Flag.equals("1"))  //若为在职单，须加上续收外勤工号与姓名
	      {
	         LRAdimAscriptionDB tlradimascriptionDB = new LRAdimAscriptionDB();
	         tlradimascriptionDB.setPolNo(tLCPolSchema.getPolNo());
	         LRAdimAscriptionSet tLRAdimAscriptionSet = tlradimascriptionDB.query();
	         if (tLRAdimAscriptionSet.size() > 0)
	         {
	            m_book.setEntry(mCurrentRow, t + 49, tLRAdimAscriptionSet.get(1).getAgentCode()); ///续收外勤工号
	            String getname="";
	            getname=" select name from laagent where agentcode='"+"?agentcode?"+"' ";
	            SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
		        sqlbv20.sql(getname);
		        sqlbv20.put("agentcode", tLRAdimAscriptionSet.get(1).getAgentCode());
	            ExeSQL xExeSQL = new ExeSQL();
	            m_book.setEntry(mCurrentRow, t + 50, xExeSQL.getOneValue(sqlbv20)); ///续收外勤姓名
	            m_book.setEntry(mCurrentRow, t + 53, tLRAdimAscriptionSet.get(1).getZipCode());
	            m_book.setEntry(mCurrentRow, t + 54, tLRAdimAscriptionSet.get(1).getAddCode());
	         }
	      }else{
	         LRAscriptionDB tlrascriptionDB = new LRAscriptionDB();
	         tlrascriptionDB.setPolNo(tLCPolSchema.getPolNo());
	         LRAscriptionSet tLRAscriptionSet = tlrascriptionDB.query();
	         if (tLRAscriptionSet.size() > 0)
	         {
	           m_book.setEntry(mCurrentRow, t + 53, tLRAscriptionSet.get(1).getZipCode());
	           m_book.setEntry(mCurrentRow, t + 54, tLRAscriptionSet.get(1).getAddCode());
	         }
	      }
	      if((tLCPolSchema.getAutoPayFlag()!= null)&&(tLCPolSchema.getAutoPayFlag().equals("1")))
	      {
	         m_book.setEntry(mCurrentRow, t + 51, "是"); ///客户垫交意愿
	      }
	      else
	      {
	         m_book.setEntry(mCurrentRow, t + 51, "否"); ///客户垫交意愿
	      }

	      //加上交费类型
	      if(tLCPolSchema.getPayIntv()==12)
	      {
	    	  m_book.setEntry(mCurrentRow, t + 52, "年交"); ///交费类型
	      }
	      else if (tLCPolSchema.getPayIntv()==6)
	      {
	    	  m_book.setEntry(mCurrentRow, t + 52, "半年交"); ///交费类型
	      }
	      else if (tLCPolSchema.getPayIntv()==3)
	      {
	    	  m_book.setEntry(mCurrentRow, t + 52, "季交"); ///交费类型
	      }
	      else
	      {
	    	  m_book.setEntry(mCurrentRow, t + 52, "月交"); ///交费类型
	      }
	    }
	    catch (Exception ex)
	    {
	      try
	      {
		m_book.setEntry(mCurrentRow, t + 0,
				"***处理保单" + tLCPolSchema.getPolNo() + " 出错：" + ex); //管理机构
		mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
	      }
	      catch (Exception ex2)
	      {
		buildError("dealSinglePol", ex.toString());
		return false;
	      }
	      return true;
	    }
	    //mCurrentRow = mCurrentRow + subNum; //如果有超过1个以上附加险，添加新的行存放第二个（以上）附加险信息
	    mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
	    return true;
	  }
	  /**
	   * 查询续期需要自动续保缴费的附加险
	   * @param tLCPolSchema
	   * @return
	   */
	  private LCPolSet findSubPol(LCPolSchema tLCPolSchema)
	  {

	    LCPolDB tLCPolDB = new LCPolDB();
	    String strSQL = "select * from lcpol where mainpolno='"
			  + "?mainpolno?" + "' ";
	    strSQL = strSQL + "and polno!='" + "?polno?" + "' ";
	    strSQL = strSQL + "and AppFlag='1' and paytodate='"
		   + "?polno?" + "' ";
	    strSQL = strSQL
		   + "and ((PaytoDate = PayEndDate and RnewFlag = '-1' and exists (select 1 from lmrisk where riskcode=lcpol.riskcode and rnewflag='Y')) or PayToDate < PayEndDate)";
	    SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
        sqlbv21.sql(strSQL);
        sqlbv21.put("mainpolno", tLCPolSchema.getPolNo());
        sqlbv21.put("polno", tLCPolSchema.getPolNo());
	    LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv21);

	    return tLCPolSet;
	  }

	  
	  /**
	   * 得到续期保单对应某个日期应该交纳的费用--从保费项查询--因为保单纪录所有阶段应该缴纳的总和
	   * @param tPolNo   主险号
	   * @return
	   */
	  private double getMainPolPrem(String tPolNo)
	  {
	    String sql = "select sum(prem) from LCPrem where polno='" + "?polno?"
		       + "' and PayEndDate>'" + "?mEndDate?" + "' "
	               + "and to_char(PayStartDate,'yyyymm')<=to_char(to_date('"+"?mStartDate?"+"','yyyy-mm-dd'),'yyyymm') and char_length(trim(dutycode))<>10";
	    SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
        sqlbv22.sql(sql);
        sqlbv22.put("mEndDate", mEndDate);
        sqlbv22.put("mStartDate", mStartDate);
	    ExeSQL tExeSQL = new ExeSQL();
	    SSRS tSSRS = tExeSQL.execSQL(sqlbv22);
	    double sumPrem = Double.parseDouble(tSSRS.GetText(1, 1));
	    return sumPrem;
	  }

	  /**
	   * 添加每个保单的信息-针对自动续保主险
	   * @param tLCPolSchema
	   * @param t_book
	   * @param n
	   * @return
	   */
	  private boolean dealSingleRnewPol(LCPolSchema tLCPolSchema,String Flag)
	  {
	    //Flag 0:孤儿单 ；1:在职单
	    int t = 0;
	    int subNum = 0;
	    String AgentName = ""; //业务员姓名
	    String InsuName = ""; //被保人姓名
	    String AppName = ""; //  客户姓名
	    String SamePerson = "N";
	    String AgentGroup ="";
	    LCContSchema tLCContSchema = new LCContSchema();
	    LCContDB tLCContDB = new LCContDB();
	    tLCContDB.setContNo(tLCPolSchema.getContNo());
	    if(!tLCContDB.getInfo())
	    {
	    	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
	    	return false;
	    }
	    tLCContSchema = tLCContDB.getSchema();

	    try
	    {
	      AgentName = ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode())
			.trim();
	      AppName = tLCPolSchema.getAppntName().trim();
	      InsuName = tLCPolSchema.getInsuredName().trim();
	      if (AgentName.equals(AppName) || AgentName.equals(InsuName))
	      {
		SamePerson = "Y";
	      }

	      //如有数据错误，可以写在excel里
	      m_book.setEntry(mCurrentRow, t + 0, tLCPolSchema.getManageCom()); //管理机构
	      m_book.setEntry(mCurrentRow, t + 1, AgentName); //业务员姓名
	      m_book.setEntry(mCurrentRow, t + 2, tLCPolSchema.getAgentCode()); //业务员代码
	      AgentGroup =GetAgentGroup(tLCPolSchema);
	      String AgentBranch = findAgentBranch(AgentGroup);
	      m_book.setEntry(mCurrentRow, t + 3, AgentBranch); //业务员组别
	      m_book.setEntry(mCurrentRow, t + 4, tLCPolSchema.getAppntName()); //客户姓名
	      String AppIDNo="";
	      String InsuredIDNo="";
	      ExeSQL pExeSQL = new ExeSQL();
	      SSRS pssrs_idno =new SSRS();
	      SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
	        sqlbv23.sql(" select a.idno,b.idno from lcappnt a,lcinsured b where a.contno=b.contno and a.contno="+"?contno?"
					 +" and a.appntno='"+"?appntno?"+"' and b.InsuredNo='"+"?InsuredNo?"+"'");
	        sqlbv23.put("contno", tLCPolSchema.getContNo());
	        sqlbv23.put("appntno", tLCPolSchema.getAppntNo());
	        sqlbv23.put("InsuredNo", tLCPolSchema.getInsuredNo());
	      pssrs_idno=pExeSQL.execSQL(sqlbv23);
	      AppIDNo=pssrs_idno.GetText(1,1);
	      InsuredIDNo=pssrs_idno.GetText(1,2);
	      m_book.setEntry(mCurrentRow, t + 5, AppIDNo); //客户(投保人)身份证号

	      m_book.setEntry(mCurrentRow, t + 6, tLCPolSchema.getContNo()); //保单号
	      m_book.setEntry(mCurrentRow, t + 7, tLCPolSchema.getPaytoDate()); //缴费日期
	      String PayMode = findPayMode(tLCPolSchema.getContNo(),
	    		  tLCContSchema.getPayLocation());
	      m_book.setEntry(mCurrentRow, t + 8, String.valueOf(PayMode)); //缴费方式

	      m_book.setEntry(mCurrentRow, t + 40,
			      ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl())); //销售渠道
	      m_book.setEntry(mCurrentRow, t + 41, SamePerson); //代理人和投保人和被保人是否同一人标记
	      m_book.setEntry(mCurrentRow, t + 42, tLCPolSchema.getAgentCom()); //代理机构

	      double mainPolPrem = 0;
	      String tGetNoticeNo = "";
	      //查询出主险自己的保费信息
	      String SQL_SubPol =
		  "select b.GetNoticeNo,sum(a.SumDuePayMoney) from ljspayperson a,ljspay b, lcpol c "
		  + " where a.GetNoticeNo = b.GetNoticeNo  "
		  + " and a.polno = c.polno " + " and c.polno = c.mainpolno "
		  + " and b.otherno='" +"?otherno?"
		  + "' and  b.othernotype='2' group by b.GetNoticeNo";
	      ExeSQL tExeSQL = new ExeSQL();
	      SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
	      sqlbv24.sql(SQL_SubPol);
	      sqlbv24.put("otherno",  tLCPolSchema.getContNo());
	      SSRS tSSRS = tExeSQL.execSQL(sqlbv24);
	      double sumprem = 0.0; //保费合计
	      if ((tSSRS != null) && (tSSRS.getMaxRow() > 0))
	      {
			tGetNoticeNo = tSSRS.GetText(1, 1);
			mainPolPrem = Double.parseDouble(tSSRS.GetText(1, 2));
			sumprem = sumprem + mainPolPrem;
	        m_book.setEntry(mCurrentRow, t + 10, String.valueOf(mainPolPrem)); //主险保费
	      }
	      else
	      {
	        m_book.setEntry(mCurrentRow,t+10,"主险续保保费未催收："+ getRnewFailReason(tLCPolSchema));//主险催收失败的原因
	      }
	      m_book.setEntry(mCurrentRow, t + 9,
			      ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode())); //主险险种




	      double tLeavingMoney = 0; //预收金额
	      double tNewLeavingMoney = 0; //余额
	      double tActualPayMoney = 0; //实缴金额

	      LCPolSet tLCPolSet = findSubPol(tLCPolSchema);
	      if (tLCPolSet.size() > 0)
	      {
		String prem = "0";

		//如果多于一条，则在excel的下一行的同一列位置显示，同时mSubNum增长
		for (int i = 1; i <= tLCPolSet.size(); i++)
		{
		  tLeavingMoney += tLCPolSet.get(i).getLeavingMoney();
		  SQL_SubPol = "select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSPayPerson where riskcode='"
		      + "?riskcode?"
		      + "' and PayType='ZC'  ";
		  SQL_SubPol = SQL_SubPol + " and GetNoticeNo='"
		      + tGetNoticeNo + "'";
		  tExeSQL = new ExeSQL();
		  SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
	      sqlbv25.sql(SQL_SubPol);
	      sqlbv25.put("riskcode",  tLCPolSet.get(i).getRiskCode());
		  tSSRS = tExeSQL.execSQL(sqlbv25);
		  if ((tSSRS != null) && (tSSRS.getMaxRow() > 0))
		  {
		    prem = tSSRS.GetText(1, 1);
		  }
		  m_book.setEntry(mCurrentRow , t + 11 +2*(i-1),
				  ChangeCodetoName.getRiskName(tLCPolSet.get(i)
				  .getRiskCode())); //附险险种

		  m_book.setEntry(mCurrentRow , t + 12 +2*(i-1), prem); //附险保费
		  sumprem = sumprem + Double.parseDouble(prem);
		}
	      }

	      m_book.setEntry(mCurrentRow, t + 25, String.valueOf(sumprem)); //保费合计
	      m_book.setEntry(mCurrentRow, t + 26, String.valueOf(tLeavingMoney)); //预收金额
	      if ((tLeavingMoney - sumprem) > 0)
	      {
		tActualPayMoney = 0;
		tNewLeavingMoney = tLeavingMoney - sumprem;
	      }
	      else
	      {
		tActualPayMoney = sumprem - tLeavingMoney;
		tNewLeavingMoney = 0;
	      }
	      m_book.setEntry(mCurrentRow, t + 27,
			      mDecimalFormat.format(tActualPayMoney)); //实交保费合计
	      m_book.setEntry(mCurrentRow, t + 28,
			      mDecimalFormat.format(tNewLeavingMoney)); //余额扣除本次保费合计

	      //投保人信息
	      LCAppntDB tLCAppntDB = new LCAppntDB();
	      tLCAppntDB.setContNo(tLCPolSchema.getContNo());
	      tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
	      if (tLCAppntDB.getInfo() == true)
	      {
	    	LCAddressDB tLCAddressDB = new LCAddressDB();
	    	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
	    	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
	    	if (tLCAddressDB.getInfo() == true)
	        {
				m_book.setEntry(mCurrentRow, t + 29,
						tLCAddressDB.getPostalAddress()); //收费地址
				m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
				m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
				m_book.setEntry(mCurrentRow, t + 39, ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex())); //性别
				m_book.setEntry(mCurrentRow, t + 43, tLCAddressDB.getMobile()); //投保人手机
	                        m_book.setEntry(mCurrentRow, t + 57, tLCAddressDB.getHomePhone());//家电
	                        m_book.setEntry(mCurrentRow, t + 58, tLCAddressDB.getCompanyPhone());//公司电
	        }
	      }
	      //被保人身份证号
	      m_book.setEntry(mCurrentRow, t + 32, tLCPolSchema.getInsuredName()); //被保人姓名
	      String[] BankInfo = findBankInfo(tLCPolSchema);
	      m_book.setEntry(mCurrentRow, t + 33, InsuredIDNo); //被保人身份证号

	      m_book.setEntry(mCurrentRow, t + 34, BankInfo[0]); //银行帐号
	      m_book.setEntry(mCurrentRow, t + 35,
			      ChangeCodetoName.getBankCodeName(BankInfo[1])); //银行编码
	      m_book.setEntry(mCurrentRow, t + 36, BankInfo[2]); //户名

	      LAAgentDB tLAAgentDB = new LAAgentDB();
	      tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
	      String AgentTel = "";
	      if (tLAAgentDB.getInfo() == true)
	      {
		AgentTel = tLAAgentDB.getPhone();
		if ((AgentTel == null) || AgentTel.equals(""))
		{
		  AgentTel = tLAAgentDB.getMobile();
		}
		if (AgentTel == null)
		{
		  AgentTel = "";
		}
	      }
	      m_book.setEntry(mCurrentRow, t + 37, AgentTel); //代理人电话

	      m_book.setEntry(mCurrentRow, t + 38, String.valueOf("1")); //交费期数
	      m_book.setEntry(mCurrentRow, t + 44, tLCPolSchema.getPrtNo());
	      //--------------------
	      //新增交费年期,交费次数
	      m_book.setEntry(mCurrentRow, t + 55, new Integer(tLCPolSchema.getPayYears()).toString()); //交费年期
	      String tPayCount = "";
	      LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
	      tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
	      tLMRiskAppDB.getInfo();
	      if (tLMRiskAppDB.getRiskPeriod().equals("L")&&(tLCPolSchema.getPayIntv()!=0)){
	        String sqlPayCount = "select  paytimes+1 from lcprem where polno ='" + "?polno?" +"'" ;
	        SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
		      sqlbv26.sql(sqlPayCount);
		      sqlbv26.put("polno", tLCPolSchema.getPolNo());
		      
	        tPayCount = tExeSQL.getOneValue(sqlbv26);
	      }else{
	        tPayCount = "1";
	      }
	      m_book.setEntry(mCurrentRow, t + 56, tPayCount); //交费次数
	      m_book.setEntry(mCurrentRow, t + 59,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
	      m_book.setEntry(mCurrentRow, t + 60,tLCPolSchema.getContNo());//合同号
	      //--------------------

	      LACommisionDB tLACommisionDB = new LACommisionDB();
	      tLACommisionDB.setPolNo(tLCPolSchema.getPolNo());
	      tLACommisionDB.setCurPayToDate(tLCPolSchema.getPaytoDate());
	      LACommisionSet tLACommisionSet = tLACommisionDB.query();
	      if (tLACommisionSet.size() > 0)
	      {
		LAAgentDB oldAgentDB = new LAAgentDB();
		oldAgentDB.setAgentCode(tLACommisionSet.get(1).getAgentCode());
		if (oldAgentDB.getInfo() == true)
		{
		  String oldAgentPhone = oldAgentDB.getPhone();
		  if (oldAgentPhone == null)
		  {
		    oldAgentPhone = "";
		  }
		  String oldAgentMobile = oldAgentDB.getMobile();
		  if (oldAgentMobile == null)
		  {
		    oldAgentMobile = "";
		  }
		  m_book.setEntry(mCurrentRow, t + 46, oldAgentDB.getName()); ///原业务员姓名
		  m_book.setEntry(mCurrentRow, t + 47, oldAgentPhone); ///原业务员电话
		  m_book.setEntry(mCurrentRow, t + 48, oldAgentMobile); ///原业务员手机
		}
	      }
	      if(Flag.equals("1"))  //若为在职单，须加上续收外勤工号与姓名
	      {
	         LRAdimAscriptionDB tlradimascriptionDB = new LRAdimAscriptionDB();
	         tlradimascriptionDB.setPolNo(tLCPolSchema.getPolNo());
	         LRAdimAscriptionSet tLRAdimAscriptionSet = tlradimascriptionDB.query();
	         if (tLRAdimAscriptionSet.size() > 0)
	         {
	            m_book.setEntry(mCurrentRow, t + 49, tLRAdimAscriptionSet.get(1).getAgentCode()); ///原业务员手机
	            String getname="";
	            getname=" select name from laagent where agentcode='"+"?agentcode?"+"' ";
	            SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
			      sqlbv27.sql(getname);
			      sqlbv27.put("agentcode", tLRAdimAscriptionSet.get(1).getAgentCode());
	            ExeSQL xExeSQL = new ExeSQL();
	            m_book.setEntry(mCurrentRow, t + 50, xExeSQL.getOneValue(sqlbv27)); ///原业务员手机
	            m_book.setEntry(mCurrentRow, t + 53, tLRAdimAscriptionSet.get(1).getZipCode());
	            m_book.setEntry(mCurrentRow, t + 54, tLRAdimAscriptionSet.get(1).getAddCode());
	         }
	      }else{
	         LRAscriptionDB tlrascriptionDB = new LRAscriptionDB();
	         tlrascriptionDB.setPolNo(tLCPolSchema.getPolNo());
	         LRAscriptionSet tLRAscriptionSet = tlrascriptionDB.query();
	         if (tLRAscriptionSet.size() > 0)
	         {
	            m_book.setEntry(mCurrentRow, t + 53, tLRAscriptionSet.get(1).getZipCode());
	            m_book.setEntry(mCurrentRow, t + 54, tLRAscriptionSet.get(1).getAddCode());
	         }
	      }

	      if((tLCPolSchema.getAutoPayFlag()!= null)&&(tLCPolSchema.getAutoPayFlag().equals("1")))
	      {
	         m_book.setEntry(mCurrentRow, t + 51, "是"); ///客户垫交意愿
	      }
	      else
	      {
	         m_book.setEntry(mCurrentRow, t + 51, "否"); ///客户垫交意愿
	      }

	    }
	    catch (Exception ex)
	    {
	      try
	      {
		m_book.setEntry(mCurrentRow, t + 0,
				"***处理保单" + tLCPolSchema.getPolNo() + " 出错：" + ex); //管理机构
		mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
	      }
	      catch (Exception ex2)
	      {
		buildError("dealSinglePol", ex.toString());
		return false;
	      }
	      return true;
	    }
	 //   mCurrentRow = mCurrentRow + subNum; //如果有超过1个以上附加险，添加新的行存放第二个（以上）附加险信息
	    mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
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
	      m_book.setEntry(mCurrentRow, 0, sPrompt);
	      mCurrentRow = mCurrentRow + 1;
	    }
	    catch (Exception ex)
	    {
	      buildError("addPrompt","添加说明“"+sPrompt+"失败");
	      return false;
	    }
	    return true;
	  }

//	判断该保单是否存在暂交费，存在不在清单中显示
	  public boolean judgeTempFee(String  tContNo)
	  {
	    String sql = " select 1 from ljspay a,ljtempfee b where a.getnoticeno=b.tempfeeno and a.otherno='"+"?tContNo?"+"'";
	    SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
	      sqlbv28.sql(sql);
	      sqlbv28.put("tContNo", tContNo);
	    ExeSQL tExe = new ExeSQL();
	    if(tExe.getOneValue(sqlbv28).equals("1"))
	    {
	      return false;
	    }
	    return true;
	  }

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}
