package com.sinosoft.workflow.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.workflowengine.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.claimgrp.LLReportBL;
import com.sinosoft.lis.claimgrp.LLClaimAuditBL;
import com.sinosoft.lis.claimgrp.LLClaimConfirmBL;
import com.sinosoft.lis.claimgrp.LLClaimConfirmPassBL;
import com.sinosoft.lis.claimgrp.LLLcContReleaseBL;
import com.sinosoft.lis.claimgrp.LLBnfBL;
import com.sinosoft.lis.claimgrp.LLClaimPubFunBL;

/**
 * <p>Title: 审核确认服务类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SinoSoft</p>
 * @author zl
 * @version 1.0
 */

public class LLClaimConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLClaimConfirmAfterInitService.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 往工作流引擎中传输数据的容器 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 提交数据容器 */
    private MMap map = new MMap();
    //private VData mIputData = new VData();
    private TransferData mTransferData = new TransferData();
    private TransferData tTransferData = new TransferData();
    private TransferData mReturnData = new TransferData();
    AccountManage tAccountManage = new AccountManage();

    private LLBnfSet mLLBnfSet = new LLBnfSet();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    /** 数据操作字符串 */
    private String mOperater;
    private String mManageCom;
    private String mOperate;
    private String mMissionID;
    private String mSubMissionID;
    private String mClmNo = "";
    private String mReject = "";
    private LLClaimPubFunBL tLLClaimPubFunBL= null;

    public LLClaimConfirmAfterInitService()
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
            return false;

        //校验传入数据
        if (!checkData())
            return false;

        logger.debug("Start  dealData...");

        //进行业务处理
        if (!dealData())
            return false;

        logger.debug("dealData successful!");

        //为工作流下一节点属性字段准备数据
        if (!prepareTransferData())
            return false;

        //准备往后台的数据
        if (!prepareOutputData())
            return false;

        logger.debug("Start  Submit...");

        return true;
    }

    /**
     * 校验业务数据
     * @return
     */
    private boolean checkData()
    {
		// 分公司的审批人拥有上报和退回案件的权利，所以只有审批人选择审批通过的时候才进行审批案件权限的校验
		if (mReject.equals("0")) {
			
			 ExeSQL tExeSQL = new ExeSQL();
		        SSRS tSSRS = new SSRS();
		        String tavalireason = null;
		        String triskcode = null;
		        String tgrpcontno = null;
		        String trgtobj = null;
		        String rSql = "select rgtstate,riskcode,grpcontno,rgtobj from llregister where rgtno='" + mClmNo + "' ";

		         tSSRS=tExeSQL.execSQL(rSql);
		         if (tSSRS.getMaxRow()>0)
		         {
		             tavalireason = tSSRS.GetText(1,1);
		             triskcode= tSSRS.GetText(1,2);
		             tgrpcontno = tSSRS.GetText(1,3);
		             trgtobj = tSSRS.GetText(1,4);
		        }

		         logger.debug("报案类型：" + tavalireason);

		         if (tavalireason.equals("02") || tavalireason == "02") {
		             String tConclusion = mReject;//审批结论
		             if (tConclusion.equals("0") || tConclusion == "0") {
		                 double tSumPay1 = 0.0; //团体帐户金额
		                 double tSumPay2 = 0.0; //个人帐户金额
		                 String tBalaDate = PubFun.getCurrentDate(); //当前截息日期（系统日期）
		                 String tRateType = "Y"; //原始利率类型（）
		                 String tIntvType = "D"; //目标利率类型（日利率）
		                 int tPerio = 0; //银行利率期间
		                 String tType = "F"; //截息计算类型（单利还是复利）
		                 String tDepst = "D"; //贷存款标志（贷款还是存款）
		//-----------公共帐户结息部分
		                 LCInsureAccClassSchema aLCInsureAccClassSchema = new
		                         LCInsureAccClassSchema();
		                 LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		                 LCInsureAccClassSet tLCInsureAccClassSet = new
		                         LCInsureAccClassSet();

		                 String tSql =
		                         "select nvl(sum(AccPayMoney),0) from llclaimaccount where clmno='" +
		                         mClmNo + "' and OtherType='P' ";
		                 String tAccPayMoney = tExeSQL.getOneValue(tSql);

		                 String aSql =
		                         "select * from lcinsureaccclass where grpcontno='" +
		                         tgrpcontno + "' and riskcode='"+triskcode+"' and acctype='001' ";

		                 tLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(aSql);
		                 aLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);

		                 mReturnData = tAccountManage.getAccClassInterestNew(
		                         aLCInsureAccClassSchema,
		                         tBalaDate,
		                         tRateType,
		                         tIntvType,
		                         tPerio,
		                         tType,
		                         tDepst);
		                 if (mReturnData != null) {
		                     String tempmoney = String.valueOf(mReturnData.
		                             getValueByName(
		                                     "aAccClassSumPay"));
		                     tSumPay1 = Double.parseDouble(tempmoney);
		                     tSumPay1 = Arith.round(tSumPay1, 2);
		                     if (tSumPay1 < 0) {
		                         tSumPay1 = 0.00;
		                     }

		                     logger.debug("===团体帐户结息余额==" + tSumPay1);
		                     logger.debug("===团体帐户赔付金额==" +
		                                        Double.parseDouble(tAccPayMoney));
		                 } else {
		                     tSumPay1 = 0.0;
		                 }
		                 if (Double.parseDouble(tAccPayMoney) > tSumPay1) {
		                     CError tError = new CError();
		                     tError.moduleName = "LLGrpClaimSimple";
		                     tError.functionName = "checkInputData";
		                     tError.errorMessage = "团体帐户赔付金额大于团体帐户结息余额，不能结案！";
		                     this.mErrors.addOneError(tError);
		                     return false;
		                 }


		//-----------个人帐户结息部分
		                 String accSSql = "select polno,AccPayMoney,declineno from llclaimaccount where clmno='" + mClmNo +
		                                 "' and othertype='S' ";
		                 tSSRS = tExeSQL.execSQL(accSSql);
		                 LCInsureAccClassSchema aaLCInsureAccClassSchema = new
		                         LCInsureAccClassSchema();
		                 LCInsureAccClassDB ttLCInsureAccClassDB = new LCInsureAccClassDB();
		                 LCInsureAccClassSet ttLCInsureAccClassSet = new LCInsureAccClassSet();
		                 for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
		                     aaLCInsureAccClassSchema = new LCInsureAccClassSchema();
		                     String tPolNo2=tSSRS.GetText(k,1);
		                     String tAccPayMoney2=tSSRS.GetText(k,2);
		                     String tInsuredNo2=tSSRS.GetText(k,3);

		                     ttLCInsureAccClassDB.setPolNo(tPolNo2);
		                     ttLCInsureAccClassSet=ttLCInsureAccClassDB.query();
		                     aaLCInsureAccClassSchema = ttLCInsureAccClassSet.get(1);
		                     mReturnData = tAccountManage.getAccClassInterestNew(
		                              aaLCInsureAccClassSchema,
		                              tBalaDate,
		                              tRateType,
		                              tIntvType,
		                              tPerio,
		                              tType,
		                              tDepst);
		                      if (mReturnData != null) {
		                          String tempmoney = String.valueOf(mReturnData.
		                                  getValueByName(
		                                          "aAccClassSumPay"));
		                          tSumPay2 = Double.parseDouble(tempmoney);
		                          tSumPay2 = Arith.round(tSumPay2, 2);
		                          if (tSumPay2 < 0) {
		                              tSumPay2 = 0.00;
		                          }

		                          logger.debug("===个人帐户结息余额==" + tSumPay2);
		                          logger.debug("===个人帐户赔付金额==" +
		                                             Double.parseDouble(tAccPayMoney2));
		                      } else {
		                          tSumPay2 = 0.0;
		                      }
		                      if (Double.parseDouble(tAccPayMoney2) > tSumPay2) {
		                          CError tError = new CError();
		                          tError.moduleName = "LLClaimAuditBL";
		                          tError.functionName = "checkInputData";
		                          tError.errorMessage = "客户号"+tInsuredNo2+"的个人帐户赔付金额大于个人帐户结息余额，不能结案！";
		                          this.mErrors.addOneError(tError);
		                          return false;
		                    }

		                }
		             }
		        }
		         
		         //校验权限 09-04-20
		         tLLClaimPubFunBL = new LLClaimPubFunBL();
					if(!tLLClaimPubFunBL.getCheckPopedom(mClmNo, mOperater)){
						
						// @@错误处理
						tLLClaimPubFunBL=null;
						CError.buildErr(this, "本次赔案金额不在操作员"+mOperater+"权限范围内!");
						return false;
					}
					
					tLLClaimPubFunBL=null;
		}
       
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {
        //从输入数据中得到所有对象
        //获得全局公共数据
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
        mClmNo = (String) mTransferData.getValueByName("RptNo");
        mReject = (String) mTransferData.getValueByName("Reject");
        mInputData = cInputData;
        mOperate = cOperate;

        if (mGlobalInput == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmAfterInitService";
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
            tError.moduleName = "LLClaimConfirmAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据Operate失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得当前工作任务的任务ID
        mMissionID = (String) mTransferData.getValueByName("MissionID");
        if (mMissionID == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输业务数据中MissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        logger.debug("----------Service dealData BEGIN----------");
        boolean tReturn = false;

        //----------------------------------------------------------------------BEG
        //功能：处理业务层面数据
        //处理：1 审批结论保存
        //     2 审批不通过时向案件核赔履历表中写入数据
        //     3 赔案状态更改
        //----------------------------------------------------------------------
        LLClaimConfirmBL tLLClaimConfirmBL = new LLClaimConfirmBL();
        if (!tLLClaimConfirmBL.submitData(mInputData,"INSERT"))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimConfirmBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmAfterInitService";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError);
            mResult.clear();
            mInputData = null;
            return false;
        }
        else
        {
            VData tVDate = new VData();
            tVDate = tLLClaimConfirmBL.getResult();
            logger.debug("-----start Service getData from BL");
            MMap tMap = new MMap();
            tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
            map.add(tMap);
            tReturn = true;
        }
        //----------------------------------------------------------------------END

        //----------------------------------------------------------------------BEG
        //功能：根据审批结论分别提交处理
        //处理：1 审批不通过时,为审核节点增加"来自"属性,分为B审批进入审核因为赔案金额为正数，
        //       C审批进入审核因为赔案金额为负数
        //     2 审批通过时,处理财务数据，详细见[功能设计--审批--根据结论的处理方式.doc]
        //----------------------------------------------------------------------
        if (mReject.equals("1"))
        {
            //审批不通过
            String strSQL1 = "";
            strSQL1 = " select (RealPay + BalancePay - BeforePay) from LLClaim where "
                    + " ClmNO='" + mClmNo + "'";
            ExeSQL exesql = new ExeSQL();
            String tPay = exesql.getOneValue(strSQL1);
            Double tDouble = new Double(tPay);
            double tConfirmPay = tDouble.doubleValue();
            //为公共传输数据集合中添加工作流下一节点属性字段数据
            mTransferData.removeByName("RptorState");
            mTransferData.setNameAndValue("RptorState", "30");
            //if (tConfirmPay >= 0)
            //{
                //mTransferData.setNameAndValue("ComeWhere", "B");
            //}
            //else
            //{
                mTransferData.setNameAndValue("ComeWhere", "C");
            //}

            //更改赔案状态为审核
            String sql1 = " update LLRegister set ClmState = '30' where"
                        + " RgtNo = '" + mClmNo + "'";
            map.put(sql1, "UPDATE");
            String sql2 = " update llclaim set ClmState = '30' where"
                        + " clmno = '" + mClmNo + "'";
            map.put(sql2,"UPDATE");
            String sql3 = " update llclaimpolicy set ClmState = '30' where"
                        + " clmno = '" + mClmNo + "'";
            map.put(sql3,"UPDATE");


            //重置保单结算和合同处理状态
            String tSql2 = "update llclaim set conbalflag = '0' where "
                         + " clmno = '" + mClmNo + "'";
            map.put(tSql2, "UPDATE");

            String tSql3 = "update llclaim set ConDealFlag = '0' where "
                         + " clmno = '" + mClmNo + "'";
            map.put(tSql3, "UPDATE");

            String sql6 = "update LLClaimUWMain set Operator='"+mOperater+"',ExamPer = '"+ mOperater + "',ExamDate='"+CurrentDate+"',AppPhase=nvl(AppPhase,0)+1 "
                           +" where Clmno = '" + mClmNo + "'";//保存审批操作员和审批日期,AppPhase存审批回退次数。
            map.put(sql6, "UPDATE");
            
            //删除受益人信息
            String sql7 = " delete from llbnf "
            		    +" where Clmno = '" + mClmNo + "'";//删除受益人分配信息
            map.put(sql7, "DELETE");
            
            String sql8 = " delete from llbnfGather "
    		    +" where Clmno = '" + mClmNo + "'";//删除受益人分配信息
            map.put(sql8, "DELETE");
            //zy 2009-11-05 删除财务应付数据
            String sql9 = " delete from ljsgetclaim "
    		    +" where otherno = '" + mClmNo + "'";//删除应付子表信息
		    map.put(sql9, "DELETE");
		    
		    String sql10 = " delete from ljsget "
			    +" where otherno = '" + mClmNo + "'";//删除应付总表信息
		    map.put(sql10, "DELETE");
        }
        else
        {
        	ExeSQL tExeSQL = new ExeSQL();
//          //因为拒付项已经去掉,所以赔付结论只能从detail里取
//            String DetailSql="select distinct givetype from llclaimdetail where clmno='"+mClmNo+"' ";
//            
//            String tGiveType = tExeSQL.getOneValue(DetailSql);
//           if (!tGiveType.equals("1"))
//           {
//                   /*加锁，准备挂起，防止中间点2次结案按钮*/
//                   LLClaimDB mLLClaimDB = new LLClaimDB();
//                   LLClaimSet mLLClaimSet = new LLClaimSet();
//                   LLClaimSchema mLLClaimSchema = new LLClaimSchema();
//                   mLLClaimDB.setClmNo(mClmNo);
//                   mLLClaimSet=mLLClaimDB.query();
//                   mLLClaimSchema=mLLClaimSet.get(1);
//                   String tGetDutyKind = mLLClaimSchema.getGetDutyKind();
//                   if (tGetDutyKind == null) {
//                       tGetDutyKind = "";
//                   }
//                   if (tGetDutyKind.equals("1")) {
//                       CError tError = new CError();
//                       tError.moduleName = "CardBL";
//                       tError.functionName = "getInputData";
//                       tError.errorMessage = "该赔案正在做复核结案，请稍等!";
//                       this.mErrors.addOneError(tError);
//                       return false;
//                   } else {
//                       map.put("update LLClaim set GetDutyKind='1' where clmno='"+mLLClaimSchema.getClmNo()+"' ", "UPDATE");
//                       VData tInputData = new VData();
//                       tInputData.add(map);
//                       PubSubmit tPubSubmit = new PubSubmit();
//                       if (!tPubSubmit.submitData(tInputData, mOperate)) {
//                           // @@错误处理
//                           this.mErrors.copyAllErrors(tPubSubmit.mErrors);
//                           CError tError = new CError();
//                           tError.moduleName = "LCNewCardChcekBL";
//                           tError.functionName = "submitData";
//                           tError.errorMessage = "数据提交失败!";
//                           this.mErrors.addOneError(tError);
//                           return false;
//                       }
//                 }
//               /**
//                *受益人分配和写入LJSGET表
//                */
//               if (!getLLBnf()) {
//                   return false;
//               }
//               //准备传输数据 VData
//               VData tVData = new VData();
//               tVData.add(mGlobalInput);
//               tVData.add(tTransferData);
//               tVData.add(mLLBnfSet);
//               LLBnfBL tLLBnfBL = new LLBnfBL();
//               if (!tLLBnfBL.submitData(tVData, "INSERT")) {
//                   // @@错误处理
//                   this.mErrors.copyAllErrors(tLLBnfBL.mErrors);
//                   CError tError = new CError();
//                   tError.moduleName = "LLClaimConfirmAfterInitService";
//                   tError.functionName = "submitData";
//                   tError.errorMessage = "数据提交失败!";
//                   this.mErrors.addOneError(tError);
//                   mResult.clear();
//                   mInputData = null;
//                   return false;
//               } else {
//                   VData tVDate = new VData();
//                   tVDate = tLLBnfBL.getResult();
//                   logger.debug("-----start Service getData from BL");
//                   MMap tMap = new MMap();
//                   tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
//                   map.add(tMap);
//               }
//           }
        	/**
        	 * 2009-02-10 zhangzheng 
        	 * 财务给付,生效是在结案保存时,不在此处生效
        	 */
//            //审批通过
//            LLClaimConfirmPassBL tLLClaimConfirmPassBL = new LLClaimConfirmPassBL();
//            if (!tLLClaimConfirmPassBL.submitData(mInputData,""))
//            {
//                // @@错误处理
//                this.mErrors.copyAllErrors(tLLClaimConfirmPassBL.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "LLClaimConfirmAfterInitService";
//                tError.functionName = "dealData";
//                tError.errorMessage = "数据提交失败!";
//                this.mErrors .addOneError(tError);
//                mResult.clear();
//                mInputData = null;
//                return false;
//            }
//            else
//            {
//                VData tVDate = new VData();
//                tVDate = tLLClaimConfirmPassBL.getResult();
//                logger.debug("-----start Service getData from BL");
//                MMap tMap = new MMap();
//                tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
//                map.add(tMap);
//                tReturn = true;
//            }

            /**
             * 2009-02-10 zhangzheng
             * 拒付只是案件结论,不是案件状态
             */
           // if (!tGiveType.equals("1"))
           //{
               //更改赔案状态为结案
               String sql1 =
                       " update LLRegister set ClmState = '50' ,EndCaseFlag = '1' , EndCaseDate = '" +
                       PubFun.getCurrentDate() + "' where"
                       + " RgtNo = '" + mClmNo + "'";
               map.put(sql1, "UPDATE");
               
               String sql2 = " update llclaim set ClmState = '50' ,EndCaseFlag = '1', EndCaseDate = '" +
                             PubFun.getCurrentDate() + "',ClmUWer = '" + mOperater + "'"
                             + " where clmno = '" + mClmNo + "'";
               map.put(sql2, "UPDATE");
               
               String sql33 = " update llclaimpolicy set ClmState = '50' , EndCaseDate = '" +
                              PubFun.getCurrentDate() + "' "
                              + " where clmno = '" + mClmNo + "'";
               map.put(sql33, "UPDATE");

               String sql6 = "update LLClaimUWMain set Operator='"+mOperater+"',ExamPer = '"+ mOperater + "',ExamDate='"+CurrentDate+"' "
                              +" where Clmno = '" + mClmNo + "'";//保存审批操作员和审批日期
               map.put(sql6, "UPDATE");
 //          }
//        else
//           {
//               String sql3 =
//                       " update LLRegister set ClmState = '80', EndCaseFlag = '1', EndCaseDate = '"
//                       + PubFun.getCurrentDate() + "'" //拒赔日期
//                       + " where RgtNo = '" + mClmNo + "'";
//               map.put(sql3, "UPDATE");
//
//               String sql4 =
//                       " update llclaim set ClmState = '80', EndCaseDate = '"
//                       + PubFun.getCurrentDate() + "'" //拒赔日期
//                       + " , ClmUWer = '" + mOperater + "'" //理赔员
//                       + " where clmno = '" + mClmNo + "'";
//               map.put(sql4, "UPDATE");
//
//               String sql5 =
//                       " update LLCase set RgtState = '80' , EndCaseFlag = '1', EndCaseDate = '" +
//                       PubFun.getCurrentDate() + "'" //拒赔日期
//                       + " where caseno = '" + mClmNo + "'";
//                map.put(sql5, "UPDATE");
//
//                String sql6 = "update LLClaimUWMain set Operator='"+mOperater+"',ExamPer = '"+ mOperater + "',ExamDate='"+CurrentDate+"' "
//                               +" where Clmno = '" + mClmNo + "'";//保存审批操作员和审批日期
//               map.put(sql6, "UPDATE");
//           }
               //删除账单表帐户理赔生成的mainfeeno = '0000000000'的数据
               String sql7 =
                       "delete llcasereceipt where mainfeeno = '0000000000' and clmno = '" +
                       mClmNo + "'";
               String sql8 =
                       "delete llfeemain where mainfeeno = '0000000000' and clmno = '" +
                       mClmNo + "'";
               map.put(sql7, "DELETE");
               map.put(sql8, "DELETE");

            /*
            //解除保单挂起
            LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
            if (!tLLLcContReleaseBL.submitData(mInputData,""))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLLcContReleaseBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "解除保单挂起失败!";
                this.mErrors .addOneError(tError);
                return false;
            }
            else
            {
                VData tempVData = new VData();
                tempVData = tLLLcContReleaseBL.getResult();
                MMap tMap = new MMap();
                tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
                map.add(tMap);
            }

            //更改死亡标志
            if (!dealDeath())
            {
                return false;
            }

            //写入销售渠道统计表 2006-02-17 P.D
            LLClaimPubFunBL tLLClaimPubFunBL = new LLClaimPubFunBL();
            LLClaimSaleChnlSet tLLClaimSaleChnlSet = new LLClaimSaleChnlSet();
            tLLClaimSaleChnlSet = tLLClaimPubFunBL.insertLLClaimSaleChnl(mClmNo,
                    mGlobalInput.Operator, mGlobalInput.ManageCom);
            if (tLLClaimSaleChnlSet.size() > 0) {
                map.put(tLLClaimSaleChnlSet, "DELETE&INSERT");
            } else {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLClaimPubFunBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "写入销售渠道统计表失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            */

            //为公共传输数据集合中添加工作流下一节点属性字段数据,结案要回到立案机构
            String sql3 = " select MngCom from LLRegister where"
                        + " RgtNo = '" + mClmNo + "'";

            String tMngCom = tExeSQL.getOneValue(sql3);

            if (tMngCom != null)
            {
                mTransferData.removeByName("MngCom");
                mTransferData.setNameAndValue("MngCom", tMngCom);
            }
        }
        //----------------------------------------------------------------------END
        return tReturn;
    }

    /**
     *取到LLBNF表相关数据
     * @return boolean
     */
    private boolean getLLBnf() {
        LLCaseDB mLLCaseDB = new LLCaseDB();
        mLLCaseDB.setCaseNo(mClmNo);
        LLCaseSet tLLCaseSet = mLLCaseDB.query();
        if (tLLCaseSet == null && tLLCaseSet.size() < 1) {
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmAfterInitService";
            tError.functionName = "getLLBnf";
            tError.errorMessage = "查询分案信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        String strSql =
                " select polno,sum(pay),GrpContNo,GrpPolNo,ContNo from LLBalance where"
                + " ClmNo = '" + mClmNo +
                "' group by polno,GrpContNo,GrpPolNo,ContNo";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(strSql);
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimAuditBL";
            tError.functionName = "getLLBnf";
            tError.errorMessage = "未查询到LLBNF表的信息!";
            logger.debug(
                    "------------------------------------------------------");
            logger.debug("--LLClaimCalAutoBL.dealData()--理赔计算时发生错误!" +
                               strSql);
            logger.debug(
                    "------------------------------------------------------");
            this.mErrors.addOneError(tError);
            return false;
        }
        for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
            String PolNo = tSSRS.GetText(a, 1);
            String pay = tSSRS.GetText(a, 2);
            String GrpContNo = tSSRS.GetText(a, 3);
            String GrpPolNo = tSSRS.GetText(a, 4);
            String ContNo = tSSRS.GetText(a, 5);

            String bnfsql="select distinct insuredno from llclaimpolicy where clmno='"+mClmNo+"' and polno='"+PolNo+"' ";
            //for (int i = 1; i <= tLLCaseSet.size(); i++) {//这样取客户号效率太低
                LDPersonSchema mLDPersonSchema = new LDPersonSchema();
                LDPersonDB mLDPersonDB = new LDPersonDB();
                LLBnfSchema mLLBnfSchema = new LLBnfSchema();
                //String tCNo = tLLCaseSet.get(i).getCustomerNo();
                String tCNo = tExeSQL.getOneValue(bnfsql);
                mLDPersonDB.setCustomerNo(tCNo);
                if (mLDPersonDB.getInfo()) {
                    mLDPersonSchema = mLDPersonDB.getSchema();
                }
//                String strSql2 = " select * from lcpol where polno = '"+PolNo+"' and  insuredno = '"+tCNo+"'";
                String strSql2 = " select * from lcpol where polno = '" + PolNo + "'" ;
                SSRS tSSRS2 = tExeSQL.execSQL(strSql2);
                if(tSSRS2.getMaxRow() > 0 ){
               mLLBnfSchema.setClmNo(mClmNo);
               mLLBnfSchema.setCaseNo(mClmNo);
               mLLBnfSchema.setBatNo("0");
               mLLBnfSchema.setGrpContNo(GrpContNo);
               mLLBnfSchema.setGrpPolNo(GrpPolNo);
               mLLBnfSchema.setContNo(ContNo);
               mLLBnfSchema.setBnfKind("A");
               mLLBnfSchema.setPolNo(PolNo);
               mLLBnfSchema.setInsuredNo(tCNo);
               mLLBnfSchema.setBnfNo("1");
               mLLBnfSchema.setCustomerNo(tCNo);
               mLLBnfSchema.setName(mLDPersonSchema.getName());
               mLLBnfSchema.setPayeeNo(tCNo);
               mLLBnfSchema.setPayeeName(mLDPersonSchema.getName());
               mLLBnfSchema.setBnfType("0");
               mLLBnfSchema.setBnfGrade("0");
               mLLBnfSchema.setRelationToInsured("00");
               mLLBnfSchema.setSex(mLDPersonSchema.getSex());
               mLLBnfSchema.setBirthday(mLDPersonSchema.getBirthday());
               mLLBnfSchema.setIDType(mLDPersonSchema.getIDType());
               mLLBnfSchema.setIDNo(mLDPersonSchema.getIDNo());
               mLLBnfSchema.setRelationToPayee("00");
               mLLBnfSchema.setPayeeSex(mLDPersonSchema.getSex());
               mLLBnfSchema.setPayeeBirthday(mLDPersonSchema.getBirthday());
               mLLBnfSchema.setPayeeIDType(mLDPersonSchema.getIDType());
               mLLBnfSchema.setPayeeIDNo(mLDPersonSchema.getIDNo());
               mLLBnfSchema.setGetMoney(pay);
               mLLBnfSchema.setBnfLot("100");
               mLLBnfSchema.setCasePayMode("");
               mLLBnfSchema.setCasePayFlag("0"); //保险金支付标志
               mLLBnfSchema.setBankCode("");
               mLLBnfSchema.setBankAccNo("");
               mLLBnfSchema.setAccName("");
               mLLBnfSet.add(mLLBnfSchema);
           }
           // }
            //String使用TransferData打包后提交
            tTransferData.setNameAndValue("ClmNo", mClmNo);
            tTransferData.setNameAndValue("PolNo", PolNo);
            tTransferData.setNameAndValue("BnfKind", "A");
        }

        return true;
    }


    /**
     * 针对死亡案件更改死亡日期和标志
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean dealDeath()
    {
        LLCaseDB tLLCaseDB = new LLCaseDB();
        tLLCaseDB.setCaseNo(mClmNo);
        LLCaseSet tLLCaseSet = tLLCaseDB.query();
        if (tLLCaseSet == null && tLLCaseSet.size() < 1)
        {
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmAfterInitService";
            tError.functionName = "dealData";
            tError.errorMessage = "查询分案信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        for (int i = 1; i <= tLLCaseSet.size(); i++)
        {
            String tCNo = tLLCaseSet.get(i).getCustomerNo();
            LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
            tLLAppClaimReasonDB.setCaseNo(mClmNo);
            tLLAppClaimReasonDB.setRgtNo(mClmNo);
            tLLAppClaimReasonDB.setCustomerNo(tCNo);
            LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.
                    query();
            if (tLLAppClaimReasonSet == null && tLLAppClaimReasonSet.size() < 1)
            {
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "查询赔案理赔类型信息失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++)
            {
                String tCode = tLLAppClaimReasonSet.get(j).getReasonCode().
                               substring(1, 3);
                if (tCode.equals("02")) //死亡
                {
                    //更改立案分案表
                    String sql3 = " update llcase set DieFlag = '1',"
                                  + " DeathDate = AccDate where"
                                  + " CaseNo = '" + mClmNo + "'"
                                  + " and CustomerNo = '" + tCNo + "'";
                    map.put(sql3, "UPDATE");
                    
                    //更改报案分案表
                    String sql4 = " update LLSubReport set DieFlag = '1',"
                                  + " DieDate = AccDate where"
                                  + " SUBRPTNO = '" + mClmNo + "'"
                                  + " and CustomerNo = '" + tCNo + "'";
                    map.put(sql4, "UPDATE");
                    
                    //更改客户表
                    String sql5 = " update LDPerson set DeathDate = to_date('"
                                  + tLLCaseSet.get(i).getAccDate()
                                  + "','yyyy-mm-dd') where"
                                  + " CustomerNo = '" + tCNo + "'";
                    map.put(sql5, "UPDATE");

                    break;
                }
            }
        }
        return true;
    }

    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        mResult.clear();
        mResult.add(map);
        return true;
    }

    /**
     * 为公共传输数据集合中添加工作流下一节点属性字段数据
     * @return
     */
    private boolean prepareTransferData()
    {
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    public TransferData getReturnTransferData()
    {
        return mTransferData;
    }

    public CErrors getErrors()
    {
        return mErrors;
    }

}
