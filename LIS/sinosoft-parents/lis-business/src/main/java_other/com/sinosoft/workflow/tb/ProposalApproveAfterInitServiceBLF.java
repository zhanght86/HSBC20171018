package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCFamilyInfoSet;
import com.sinosoft.lis.vschema.LCFamilyRelaInfoSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;


/**
 * <p>Title:工作流服务类:新契约新单复核 </p>
 * <p>Description: 新单复核工作流AfterInit服务类 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft </p>
 * @author HYQ
 * @version 1.0
 */

public class ProposalApproveAfterInitServiceBLF implements AfterInitService
{
private static Logger logger = Logger.getLogger(ProposalApproveAfterInitServiceBLF.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往工作流引擎中传输数据的容器 */
    private GlobalInput mGlobalInput = new GlobalInput();

    private TransferData mTransferData = new TransferData();

    /** 数据操作字符串 */
    private String mOperater;
    private String mOperate;

    /** 业务数据操作字符串 */
    private String mContNo;
    private String mApproveFlag;
    private String mApproveDate;
    private String mApproveTime;
    private String mMissionID;
    private String mIssueFlag; //是否有问题件  0-没有 1-有
    private String mOutIssueFlag; //是否有外部问题件 0-没有 1-有
    private String mPrtSeq; //问题件打印流水号
    private String mUWAuthority = " "; //核保权限
    private String reDisMark = ""; //分保标志
    /**保单表*/
    private LCContSchema mLCContSchema = new LCContSchema();
    public LCFamilyRelaInfoSet tLCFamilyRelaInfoSet = new LCFamilyRelaInfoSet();
    public LCFamilyInfoSet tLCFamilyInfoSet = new LCFamilyInfoSet();
    private LCPolSet mLCPolSet = new LCPolSet();
    private MMap mMap;
    private LOPRTManagerSchema mLOPRTManagerSchema;
    private LCIssuePolSchema mLCIssuePolSchema;
    private String mNoticeType;
    private LOPRTManagerSet mLOPRTManagerSet;
    private String mCustomerNoticeType;
    private String mPrtSeq1;
    private String mPrtSeq2;
    private String mSerialNo1;
    private String mSerialNo2;
    private String mPolPassFlag = "0";
    private String mContPassFlag = "0";

    private MMap map = new MMap();
    public ProposalApproveAfterInitServiceBLF()
    {
    }

    /**
     * 传输数据的公共方法
     * @param cInputData 输入的数据
     * @param cOperate 数据操作
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("------------------------------复核服务类ProposalApproveAfterInitService--------开始-------------------------------");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }

        //校验是否有未打印的体检通知书
        if (!checkData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        //为工作流下一节点属性字段准备数据
        if (!prepareTransferData())
        {
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }

        logger.debug("Start  Submit...");

        logger.debug("---------------复核服务类ProposalApproveAfterInitService--------结束---------------");
        return true;
    }


    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     * modify by heyq 20050426 此方法中去掉生成家庭关系代码，若是以后需要此处理，参考平台组代码
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        mResult.clear();


   //     map.add(this.mMap);
        map.put(mLCContSchema, "UPDATE");
        map.put(mLCPolSet, "UPDATE");

        mResult.add(map);
        return true;
    }

    /**
     * 校验业务数据
     * @return boolean
     */
    private boolean checkData()
    {
        //校验保单信息
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mContNo);
        if (!tLCContDB.getInfo())
        {
            CError tError = new CError();
            tError.moduleName = "UWRReportAfterInitService";
            tError.functionName = "checkData";
            tError.errorMessage = "保单" + mContNo + "信息查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mLCContSchema.setSchema(tLCContDB);

        //校验合同单下是否有险种单
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setContNo(mContNo);
        mLCPolSet = tLCPolDB.query();
        if (mLCPolSet == null || mLCPolSet.size() <= 0)
        {
            CError tError = new CError();
            tError.moduleName = "UWRReportAfterInitService";
            tError.functionName = "checkData";
            tError.errorMessage = "险种保单" + mContNo + "信息查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //------------------------------------------------------Beg
        //----------修改人：chenrong
        //----------修改日期：2006-2-24
        //校验险种信息是否正确
        VData tInputData = new VData();
        tInputData.add(mLCContSchema);
        CheckRiskField tCheckRiskField = new CheckRiskField();
        if (!tCheckRiskField.CheckTBField(tInputData,"TBALLINSERT"))
        {
          this.mErrors.copyAllErrors(tCheckRiskField.mErrors);
          return false;
        }
        //-----------------------------------------------------End


        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     * @param cInputData 输入数据
     * @param cOperate 数据操作
     * @return boolean
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {
        //从输入数据中得到所有对象
        //获得全局公共数据
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);

        mInputData = cInputData;
        if (mGlobalInput == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "ProposalApproveAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得操作员编码
        mOperater = mGlobalInput.Operator;
        if (mOperater == null || mOperater.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "ProposalApproveAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据Operate失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mOperate = cOperate;
        //获得当前工作任务的任务ID
        mMissionID = (String) mTransferData.getValueByName("MissionID");
        if (mMissionID == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "ProposalApproveAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输业务数据中MissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得当前工作任务的mCont
        mContNo = (String) mTransferData.getValueByName("ContNo");
        if (mContNo == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "ProposalApproveAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输业务数据中MissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mLCContSchema.getAppFlag() != null &&
            !mLCContSchema.getAppFlag().trim().equals("0"))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ProposalApproveBL";
            tError.functionName = "checkData";
            tError.errorMessage = "此单不是投保单，不能进行复核操作!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mLCContSchema.getUWFlag() != null &&
            !mLCContSchema.getUWFlag().trim().equals("0"))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ProposalApproveBL";
            tError.functionName = "checkData";
            tError.errorMessage = "此投保单已经开始核保，不能进行复核操作!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     * @return boolean
     */
    private boolean dealData()
    {

        mApproveDate = PubFun.getCurrentDate();
        mApproveTime = PubFun.getCurrentTime();
        LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
        LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
        String tSql = "Select * from lcissuepol where 1=1 "
                      + " and ContNo = '" + "?ContNo?" + "'"
//                      + " and backobjtype = '1'"
                      + " and REPLYMAN is null "
                      ;
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(tSql);
        sqlbv1.put("ContNo", mContNo);
        tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv1);
        if (tLCIssuePolSet == null)
        {
            CError tError = new CError();
            tError.moduleName = "ProposalApproveAfterInitService";
            tError.functionName = "dealData";
            tError.errorMessage = "查询问题件失败!";
            this.mErrors.addOneError(tError);
        }
        logger.debug("size==" + tLCIssuePolSet.size());
        if (tLCIssuePolSet.size() > 0)
        {
            logger.debug("ApproveFlag = 1");
            mApproveFlag = "1"; //有已发的操作员问题件,则须复核修改
        }
        else
        {
            logger.debug("ApproveFlag = 9");
            mApproveFlag = "9";
        }

//        //准备保单的复核标志
        mLCContSchema.setApproveFlag(mApproveFlag);
        mLCContSchema.setApproveDate(mApproveDate);
        mLCContSchema.setApproveTime(mApproveTime);
        mLCContSchema.setApproveCode(mOperate);
        mLCContSchema.setModifyDate(mApproveDate);
        mLCContSchema.setModifyTime(mApproveTime);

//        //准备险种保单的复核标志
        for (int i = 1; i <= mLCPolSet.size(); i++)
        {
            mLCPolSet.get(i).setApproveFlag(mApproveFlag);
            mLCPolSet.get(i).setApproveDate(mApproveDate);
            mLCPolSet.get(i).setApproveTime(mApproveTime);
            mLCPolSet.get(i).setApproveCode(mOperate);
            mLCPolSet.get(i).setModifyDate(mApproveDate);
            mLCPolSet.get(i).setModifyTime(mApproveTime);
            //采用客户账户后，leavingmoney字段不再记录余额，需要清空
           // mLCPolSet.get(i).setLeavingMoney(0);
        }

        //查看是否有问题件
        //tongmeng 2007-11-23 modify
        //统一由人工核保发送问题件
        this.mIssueFlag = "0";
        /*
        logger.debug("开始判断是否发问题件");
        LCIssuePolDB aLCIssuePolDB = new LCIssuePolDB();
        
//        aLCIssuePolDB.setContNo(mContNo);
        String aSQL = "select * from LCIssuePol where contno='" + mContNo +
                      "' and replyman is null and replyresult is null";
        logger.debug("111111111111aSQL===" + aSQL);
        LCIssuePolSet aLCIssuePolSet = aLCIssuePolDB.executeQuery(aSQL);
        //有问题件

        if (aLCIssuePolSet.size() > 0)
        {
            this.mIssueFlag = "1";
        }
        else
        {
            this.mIssueFlag = "0";
        }

        this.mNoticeType = "0";
        this.mCustomerNoticeType = "0";

        //有外部问题件
        //tongmeng 2007-10-18 modify
        //增加对发放给业务员的问题件的处理
//        aLCIssuePolDB.setBackObjType("2");     //问题件类型：1-内部 2-外部
        //LCIssuePolSet bLCIssuePolSet = aLCIssuePolDB.query();
        //aSQL = aSQL + " and BackObjType='2'";
        aSQL = aSQL + " and BackObjType in ('2','3') ";
        LCIssuePolSet bLCIssuePolSet = aLCIssuePolDB.executeQuery(aSQL);
        logger.debug("22222222222aSQL===" + aSQL);
        if (bLCIssuePolSet.size() > 0)
        {
            this.mOutIssueFlag = "1";
            //如果是外部问题件，发送外部问题件通知书
            //添加相关业务类
            //为调用打印新契约通知书准备数据
            if (!prepareLCCont())
            {
                CError tError = new CError();
                tError.moduleName = "PrintNBNoticeAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "不存在符合该合同号的合同信息!";
                this.mErrors.addOneError(tError);
                return false;

            }
            UWIssuePolPrintBL aUMIssuePolPrintBL = new UWIssuePolPrintBL();
            VData aVData = new VData();
            aVData.add(this.mGlobalInput);
            aVData.add(this.mLCContSchema);
            boolean tResult = aUMIssuePolPrintBL.submitData(aVData, "");
            logger.debug("after!tResult===" + tResult);
            if (tResult)
            {
                mMap = (MMap) aUMIssuePolPrintBL.getResult().
                       getObjectByObjectName("MMap",
                                             0);
                logger.debug("here!");
                mLOPRTManagerSet = (LOPRTManagerSet) aUMIssuePolPrintBL.
                                   getResult().
                                   getObjectByObjectName(
                                           "LOPRTManagerSet", 0);
                logger.debug("222here!");
//                mLCIssuePolSchema = (LCIssuePolSchema) aUMIssuePolPrintBL.
//                                   getResult().
//                                   getObjectByObjectName(
//                                           "LCIssuePolSchema", 0);

                //判断通知书的种类
                //如果mLOPRTManagerSet中有两条记录则既打印契约变更通知书又打印客户变更通知书
//                if(this.mLOPRTManagerSet.size()==2){
//                  this.mNoticeType="1";
//                  this.mCustomerNoticeType="1";
//                }
//                else if (this.mLOPRTManagerSet.get(1).getCode().equals("14")){
//                  this.mNoticeType="1";
//                  this.mCustomerNoticeType="0";
//                }
//                else if(this.mLOPRTManagerSet.get(1).getCode().equals("17")){
//                  this.mNoticeType="0";
//                  this.mCustomerNoticeType="1";
//                }
                this.mNoticeType = "0";
                this.mCustomerNoticeType = "0";
                logger.debug("----mLOPRTManagerSet.size()===" +
                                   mLOPRTManagerSet.size());
                //将打印序列号赋值
                for (int i = 0; i < this.mLOPRTManagerSet.size(); i++)
                {
                    if (this.mLOPRTManagerSet.get(i + 1).getCode().equals("14"))
                    {
                        this.mPrtSeq1 = this.mLOPRTManagerSet.get(i + 1).
                                        getPrtSeq(); //一般通知书打印序列号
                        this.mNoticeType = "1";
//                        this.mSerialNo1 = this.mLCIssuePolSchema.getSerialNo();
                    }
                    else if (this.mLOPRTManagerSet.get(i + 1).getCode().equals(
                            "17"))
                    {
                        this.mPrtSeq2 = this.mLOPRTManagerSet.get(i + 1).
                                        getPrtSeq(); //客户合并打印序列号
                        this.mCustomerNoticeType = "1";
//                        this.mSerialNo2 = this.mLCIssuePolSchema.getSerialNo();
                    }
                }
//                if (mLCIssuePolSchema.getIssueType().equals("22")) {
//                    mNoticeType = "2";
//                } else {
//                    mNoticeType = "1";
//                }
            }
            else
            {
                this.mErrors.copyAllErrors(aUMIssuePolPrintBL.mErrors);
            }

        }
        else
        {
            this.mOutIssueFlag = "0";
        }
        */
        this.mOutIssueFlag = "0";
        this.mNoticeType = "0";
        this.mCustomerNoticeType = "0";
        logger.debug("==this.mIssueFlag==" + this.mIssueFlag);
        logger.debug("==this.mOutIssueFlag==" + this.mOutIssueFlag);
        logger.debug("==this.mNoticeType==" + this.mNoticeType);
        logger.debug("==this.mCustomerNoticeType==" +
                           this.mCustomerNoticeType);

        logger.debug("---------------------------------------------------------------------");
        logger.debug("-----------复核流程结束----------");
        logger.debug("---------------------------------------------------------------------");

        //客户账户修改
        // 添加客户账户处理 
		try
		{
	
			LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			String querytempfeeSQL = "select * from LJTempFee where OtherNo='" + "?OtherNo?" + "' "
			+ " and ConfFlag='0' and ConfDate is null"
			+ " and (EnterAccDate is not null and EnterAccDate <> '3000-01-01')";
			 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		        sqlbv2.sql(querytempfeeSQL);
		        sqlbv2.put("OtherNo", mLCContSchema.getContNo());
	        tLJTempFeeSet = (tLJTempFeeDB.executeQuery(sqlbv2));
          if(tLJTempFeeSet.size()>0)
    	  {
   
    		  String tOperationType ="";
    		  LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
    		  for (int i = 1; i <= tLJTempFeeSet.size(); i++)
    		  {
    			  LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
    			  tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSet.get(i).getTempFeeNo());
    			  tLJTempFeeClassDB.setOtherNo(mLCContSchema.getPrtNo());
    		      LJTempFeeClassSet tLJTempFeeClassSet1 = tLJTempFeeClassDB.query();
    		  	  int m = tLJTempFeeClassSet1.size();
    		  	  for (int j = 1; j <= m; j++)
    			  {
    				LJTempFeeClassSchema tLJTempFeeClassSchema = tLJTempFeeClassSet1.get(j);
    			
    				mLJTempFeeClassSet.add(tLJTempFeeClassSchema);
    			  }

    		  }
    		 
    	   
    		TransferData tTransferData = new TransferData();
    		tTransferData.setNameAndValue("Nbflag", "1");
    	    VData nInputData = new VData();
  		    nInputData.add(tLJTempFeeSet);
  		    nInputData.add(mLJTempFeeClassSet);
  		    nInputData.add(tTransferData);
  		    FICustomerMain tFICustomerMain = new FICustomerMain();
  		     //调用客户账户收费接口，传入财务标志FI
  		    if(tFICustomerMain.submitData(nInputData, "FI"))
  		    {
              	//获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
              	map.add(tFICustomerMain.getMMap());
              }
              else
              {
              	mErrors.copyAllErrors(tFICustomerMain.mErrors);
              	return false;
              }
    	  }	
       
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

        /*
        //续涛于2005-10-09注销该段脚本，起用UWAutoChkAfterInitService.java文件
        //流程修改为复核-->自动核保
        //如果没有问题件则进行自动核保校验
        if (this.mIssueFlag.equals("0"))
        {
//            ExeSQL aExeSQL = new ExeSQL();
//
//            if(){
                AutoUWCheckBL tAutoUWCheckBL = new AutoUWCheckBL();

                boolean aResult = tAutoUWCheckBL.submitData(this.mInputData, "");

                if (aResult)
                {
                    mMap = (MMap) tAutoUWCheckBL.getResult().
                           getObjectByObjectName("MMap",
                                                 0);
                    TransferData a = (TransferData) tAutoUWCheckBL.getResult().
                                     getObjectByObjectName("TransferData", 0);
                    this.mPolPassFlag = (String) a.getValueByName("PolPassFlag");
                    this.mContPassFlag = (String) a.getValueByName("ContPassFlag");
                    logger.debug("this.mPolPassFlag==" + this.mPolPassFlag);
                    logger.debug("this.mContPassFlag==" + this.mContPassFlag);
                    mUWAuthority = tAutoUWCheckBL.getHierarhy(); //核保级别 add by yaory
                    reDisMark = tAutoUWCheckBL.getReDistribute(); //分保标志 add by yaory
                    logger.debug("核保级别与分保标志======" + reDisMark + "-" +
                                       mUWAuthority);

                }
                else
                {
                    ////add by yaory for autocheck.....
                    mUWAuthority = tAutoUWCheckBL.getHierarhy(); //核保级别
                    reDisMark = tAutoUWCheckBL.getReDistribute(); //分保标志
                    logger.debug("核保级别与分保标志======" + reDisMark + "-" +
                                       mUWAuthority);
                    this.mErrors.copyAllErrors(tAutoUWCheckBL.mErrors);
                    return false;
                }
            //}
        }
        */
        return true;
    }

    /**
     *  通过ContNo查询LCCont表的信息
     *  @return boolean
     *
     **/
    private boolean prepareLCCont()
    {
        LCContDB aLCContDB = new LCContDB();
        aLCContDB.setContNo(this.mContNo);
        LCContSet aLCContSet = aLCContDB.query();
        if (aLCContSet.size() == 0)
        {
            CError tError = new CError();
            tError.moduleName = "PrintNBNoticeAfterInitService";
            tError.functionName = "prepareLCCont";
            tError.errorMessage = "通过合同号查询LCCont表没有数据!";
            this.mErrors.addOneError(tError);
            return false;

        }
        this.mLCContSchema = aLCContSet.get(1);
        return true;
    }


    /**
     * 为公共传输数据集合中添加工作流下一节点属性字段数据
     * @return boolean
     */
    private boolean prepareTransferData()
    {
        logger.debug("-----ApproveFlag==" + mApproveFlag);
        logger.debug("-----this.mIssueFlag==" + this.mIssueFlag);
        logger.debug("-----this.mOutIssueFlag==" + this.mOutIssueFlag);
        logger.debug("-----this.mNoticeType==" + this.mNoticeType);
        mTransferData.setNameAndValue("ApproveFlag", mApproveFlag);
        mTransferData.setNameAndValue("IssueFlag", this.mIssueFlag);
        mTransferData.setNameAndValue("OutIssueFlag", this.mOutIssueFlag);

        mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
        mTransferData.setNameAndValue("ContNo", this.mContNo);
        if (this.mNoticeType.equals("1"))
        {
            mTransferData.setNameAndValue("PrtSeq1", this.mPrtSeq1);
//            mTransferData.setNameAndValue("SerialNo1", this.mSerialNo1);

        }
        if (this.mCustomerNoticeType.equals("1"))
        {
            mTransferData.setNameAndValue("PrtSeq2", this.mPrtSeq2);
//            mTransferData.setNameAndValue("SerialNo2", this.mSerialNo2);

        }
        mTransferData.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
        mTransferData.setNameAndValue("AgentGroup", mLCContSchema.getAgentGroup());
        mTransferData.setNameAndValue("NoticeType", this.mNoticeType);
        mTransferData.setNameAndValue("CustomerNoticeType",
                                      this.mCustomerNoticeType);

//        mTransferData.setNameAndValue("BranchAttr", );
        mTransferData.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
//        mTransferData.setNameAndValue("OldPrtSeq", mApproveFlag);


//        mTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());
//        mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
        mTransferData.setNameAndValue("AppntNo", mLCContSchema.getAppntNo());
        mTransferData.setNameAndValue("AppntName", mLCContSchema.getAppntName());
        mTransferData.setNameAndValue("ProposalContNo",
                                      mLCContSchema.getProposalContNo());
//        mTransferData.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
        LAAgentDB tLAAgentDB = new LAAgentDB();
        LAAgentSet tLAAgentSet = new LAAgentSet();
        tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
        tLAAgentSet = tLAAgentDB.query();
        if (tLAAgentSet == null || tLAAgentSet.size() != 1)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ProposalApproveAfterEndService";
            tError.functionName = "prepareTransferData";
            tError.errorMessage = "代理人表LAAgent查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mTransferData.setNameAndValue("AgentName", tLAAgentSet.get(1).getName());
//        mTransferData.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
        if (mPolPassFlag.equals("5"))
        {
            mContPassFlag = "5";
        }
        mTransferData.setNameAndValue("UWFlag", mContPassFlag);
        mTransferData.setNameAndValue("UWDate", PubFun.getCurrentDate());
        mTransferData.setNameAndValue("ApproveDate", PubFun.getCurrentDate());
        mTransferData.setNameAndValue("ReportType", "0");
        mTransferData.setNameAndValue("UWAuthority", mUWAuthority); //add by yaory 权限 如H1
        mTransferData.setNameAndValue("ReDisMark", reDisMark); //add by yaory 分保标志 1-分保0不分保
        mTransferData.setNameAndValue("SaleChnl",mLCContSchema.getSaleChnl());//add by zhangxing 新契约（客户合并）通知书，增加销售渠道字段
        logger.debug("核保级别与分保标志======" + reDisMark + "-" + mUWAuthority);
        //核保员代码
        mTransferData.setNameAndValue("UserCode", "0000000000");
        //投保日期
        mTransferData.setNameAndValue("PolApplyDate",
                                      mLCContSchema.getPolApplyDate());

        return true;
    }

    /**
     * 返回处理后的结果
     * @return VData
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 返回工作流中的Lwfieldmap所描述的值
     * @return TransferData
     */
    public TransferData getReturnTransferData()
    {
        return mTransferData;
    }

    /**
     * 返回错误对象
     * @return CErrors
     */
    public CErrors getErrors()
    {
        return mErrors;
    }
}
