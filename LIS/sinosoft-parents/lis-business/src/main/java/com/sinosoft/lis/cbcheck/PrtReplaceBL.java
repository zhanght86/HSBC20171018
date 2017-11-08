package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.certify.CertTakeBackUI;
import com.sinosoft.lis.certify.CertifyFunc;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.LACommisionDB;
import com.sinosoft.lis.db.LBPolDB;
import com.sinosoft.lis.db.LBRemarkDB;
import com.sinosoft.lis.db.LCAddPolDB;
import com.sinosoft.lis.db.LCApplyRecallPolDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDelTraceDB;
import com.sinosoft.lis.db.LCEdorReasonDB;
import com.sinosoft.lis.db.LWNotePadDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPRemarkDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LZCardDB;
import com.sinosoft.lis.db.LZCardTrackDB;
import com.sinosoft.lis.db.LockTableDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LWNotePadSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.LACommisionSet;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LBPolSet;
import com.sinosoft.lis.vschema.LBRemarkSet;
import com.sinosoft.lis.vschema.LCAddPolSet;
import com.sinosoft.lis.vschema.LCApplyRecallPolSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDelTraceSet;
import com.sinosoft.lis.vschema.LCEdorReasonSet;
import com.sinosoft.lis.vschema.LWNotePadSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPRemarkSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.lis.vschema.LZCardTrackSet;
import com.sinosoft.lis.vschema.LockTableSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowUI;

/**
 * <p>Title: Web业务系统印刷号替换功能部分 </p>
 * <p>Description: 数据库功能类</p>
 * <p>Copyright: Copyright (c) 2006 </p>
 * <p>Company: Sinosoft< /p>
 * @author ck
 * @version 1.0
 */
public class PrtReplaceBL
{
private static Logger logger = Logger.getLogger(PrtReplaceBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往界面传输数据的容器 */
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private String OldPrtNo = "";
    private String NewPrtNo = "";
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private VData mResult = new VData();
    private LCPolSet tLCPolSet = new LCPolSet();

    /**需要替换的表*/
    private LCContSchema mLCContSchema = new LCContSchema();
    private LCPolSet mLCPolSet = new LCPolSet();
    private LBPolSet mLBPolSet = new LBPolSet();
    private LPPolSet mLPPolSet = new LPPolSet();
    private LACommisionSet mLACommisionSet = new LACommisionSet();
    private LCEdorReasonSet mLCEdorReasonSet = new LCEdorReasonSet();
    private LCAddPolSet mLCAddPolSet = new LCAddPolSet();
    private LWNotePadSet mLWNotePadSet = new LWNotePadSet();
    private LWNotePadSet tLWNotePadSet = new LWNotePadSet();
    private LCDelTraceSet mLCDelTraceSet = new LCDelTraceSet();
    private LCApplyRecallPolSet mLCApplyRecallPolSet = new LCApplyRecallPolSet();
    private LPRemarkSet mLPRemarkSet = new LPRemarkSet();
    private LBRemarkSet mLBRemarkSet = new LBRemarkSet();
    private LockTableSet mLockTableSet = new LockTableSet();
    /********************* add by ln,2008.02.27***********/
    private String BPOMissionStateStr = "";
    private String BPOErrLogStr = "";
    private String BPOMissionDetailErrorStr = "";
    private String BPOMissionDetailStateStr = "";
    private String BPOPolDataStr = "";
    
    private String LCAppntStr = "";
    private String LCInsuredStr = "";
    private String LCPolOriginalStr = "";
    private String lcuwreportStr = "";
    private String LDContTimeStr = "";
    private String lcdeltraceStr = "";    
    
    private SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
    private SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();

    /**要修改状态的表*/
    private LZCardSet mLZCardSet = new LZCardSet();
    private LZCardTrackSet mLZCardTrackSet = new LZCardTrackSet();
    private ES_DOC_MAINSet mES_DOC_MAINSet = new ES_DOC_MAINSet();
    private Hashtable hashParams = new Hashtable();
    private VData tVData = new VData();
    private String CertifyCode = "";
    
	// 存放需要备份的结点
    private LWMissionSet mLWMissionSet_Delete = new LWMissionSet();
	private LBMissionSet mLBMissionSet_Insert = new LBMissionSet();

    public PrtReplaceBL()
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
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }

        //进行业务数据校验
        if (!checkData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }
        
      //进行业务处理
        if (!dealWorkFlow())
        {
            return false;
        }

        logger.debug("before prepareData");
        logger.debug("————————个单删除:该印刷号下的所有险种都删掉——————————");

        if (!prepareData())
        {
            return false;
        }

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //从输入数据中得到所有对象
        //获得全局公共数据
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
                                                                              0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",
                                                                        0);
        OldPrtNo = ((String) mTransferData.getValueByName("OldPrtNo")).trim();
        NewPrtNo = ((String) mTransferData.getValueByName("NewPrtNo")).trim();
        mInputData = cInputData;

        return true;
    }

    /**
      * 校验业务数据
      * @return
      */
    private boolean checkData()
    {
        if (mGlobalInput == null)
        {
            CError.buildErr(this, "请重新登录!");

            return false;
        }
        
        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = new LCContSet();
        tLCContDB.setPrtNo(OldPrtNo);
        tLCContSet = tLCContDB.query();
        if(tLCContSet == null || tLCContSet.size()<1)
        {
        	CError.buildErr(this, "查询合同表失败！");
        	return false;
        }
        mLCContSchema = tLCContSet.get(1).getSchema();

//        LCPolBL tLCPolBL = new LCPolBL();
//        tLCPolBL.setPrtNo(OldPrtNo);
//        tLCPolSet = tLCPolBL.executeQuery("select * from v_lcpol_lbpol where prtno='" +
//                                          OldPrtNo + "' and mainpolno=polno");
//
//        if (tLCPolSet.size() == 0)
//        {
//            CError.buildErr(this, "未找到该印刷号的保单!");
//
//            return false;
//        }

        ES_DOC_MAINSet tempES_DOC_MAINSet = new ES_DOC_MAINSet();
        ES_DOC_MAINDB tempES_DOC_MAINDB = new ES_DOC_MAINDB();
        tempES_DOC_MAINDB.setInputState("1");
        tempES_DOC_MAINDB.setDocCode(NewPrtNo);
        tempES_DOC_MAINSet = tempES_DOC_MAINDB.query();

        if (tempES_DOC_MAINSet.size() > 0)
        {
            CError.buildErr(this, "印刷号" + NewPrtNo + "已经进行扫描，请用其它印刷号进行替换!");
            return false;
        } 
        
        LWMissionDB tLWMissionDB = new LWMissionDB();
        tLWMissionDB.setMissionProp1(NewPrtNo);
        LWMissionSet tLWMissionSet = tLWMissionDB.query();

        if (tempES_DOC_MAINSet.size() > 0 || tLWMissionSet.size() > 0 )
        {
        	CError.buildErr(this, "印刷号" + NewPrtNo + "已经进行扫描，请用其它印刷号进行替换!");
            return false;
        }

        if (mLCContSchema.getCustomGetPolDate()==null || mLCContSchema.getCustomGetPolDate().equals(""))
        {
            CError.buildErr(this, "请先将该单证" + OldPrtNo + "进行回收!");
            return false;
        }

        /*LZCardSet tLZCardSet = new LZCardSet();
        LZCardDB tLZCardDB = new LZCardDB();
        tLZCardSet = tLZCardDB.executeQuery("select * from lzcard where trim(receivecom) = 'D" +
        									mLCContSchema.getAgentCode() +
                                            "' and startno<='" + NewPrtNo +
                                            "' and endno>='" + NewPrtNo + "'");

        if (tLZCardSet.size() == 0)
        {
            CError.buildErr(this, "印刷号所属代理人不同或未发放该单证!");

            return false;
        }*/        

        /*
                LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
                LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
                tLPEdorMainSet = tLPEdorMainDB.executeQuery("select * from lpedormain where polno='" +
                                                            tLCPolSet.get(1)
                                                                     .getMainPolNo() +
                                                            "' and edortype in('WT','XT')");

                if (tLPEdorMainSet.size() == 0)
                {
                    CError tCError = new CError();
                    tCError.buildErr(this, "请先做保全犹豫期撤单或者过失退保操作再进行印刷号替换!");

                    return false;
                }
        */
        return true;
    }

    /**
    * 根据前面的输入数据，进行BL逻辑处理
    * 如果在处理过程中出错，则返回false,否则返回true
    */
    private boolean dealData()
    {
    	/*********************add by ln,2009.1.9***********/
    	//2009-1-9 ln add --新增需要替换的表
    	/**更新个人合同表*/
    	mLCContSchema.setProposalContNo(NewPrtNo);
    	mLCContSchema.setPrtNo(NewPrtNo);
    	mLCContSchema.setModifyDate(CurrentDate);
    	mLCContSchema.setModifyTime(CurrentTime);
    	
    	/**更新投保人表*/
    	LCAppntStr = 
        	"update LCAppnt set prtno='"+"?prtno1?"
        	+ "',ModifyDate='"+"?ModifyDate?"+"',ModifyTime='"+"?ModifyTime?"+"' "
        	+ "where prtno='"+"?prtno2?"+"'";
    	sqlbv1.sql(LCAppntStr);
    	sqlbv1.put("prtno1", NewPrtNo);
    	sqlbv1.put("ModifyDate", CurrentDate);
    	sqlbv1.put("ModifyTime", CurrentTime);
    	sqlbv1.put("prtno2", OldPrtNo);
    	
    	/**更新被保人表*/
    	LCInsuredStr = 
        	"update LCInsured set prtno='"+"?prtno1?"
        	+ "',ModifyDate='"+"?ModifyDate?"+"',ModifyTime='"+"?ModifyTime?"+"' "
        	+ "where prtno='"+"?prtno2?"+"'";
    	sqlbv2.sql(LCInsuredStr);
    	sqlbv2.put("prtno1", NewPrtNo);
    	sqlbv2.put("ModifyDate", CurrentDate);
    	sqlbv2.put("ModifyTime", CurrentTime);
    	sqlbv2.put("prtno2", OldPrtNo);
    	
    	/**更新保单原始信息表*/
    	LCPolOriginalStr = 
        	"update LCPolOriginal set prtno='"+"?prtno1?"
        	+ "',ModifyDate='"+"?ModifyDate?"+"',ModifyTime='"+"?ModifyTime?"+"' "
        	+ "where prtno='"+"?prtno2?"+"'";
    	sqlbv3.sql(LCPolOriginalStr);
    	sqlbv3.put("prtno1", NewPrtNo);
    	sqlbv3.put("ModifyDate", CurrentDate);
    	sqlbv3.put("ModifyTime", CurrentTime);
    	sqlbv3.put("prtno2", OldPrtNo);
    	
    	/**更新核保分析报告表*/
    	lcuwreportStr = 
        	"update lcuwreport set otherno='"+"?prtno1?"
        	+ "',ModifyDate='"+"?ModifyDate?"+"',ModifyTime='"+"?ModifyTime?"+"' "
        	+ "where otherno='"+"?prtno2?"+"' and othernotype='1'";
    	sqlbv4.sql(lcuwreportStr);
    	sqlbv4.put("prtno1", NewPrtNo);
    	sqlbv4.put("ModifyDate", CurrentDate);
    	sqlbv4.put("ModifyTime", CurrentTime);
    	sqlbv4.put("prtno2", OldPrtNo);
    	
    	/**更新新契约工作时间记录表*/
    	LDContTimeStr = 
        	"update LDContTime set prtno='"+"?prtno1?"+"' "
        	+ "where prtno='"+"?prtno2?"+"'";
    	sqlbv5.sql(LDContTimeStr);
    	sqlbv5.put("prtno1", NewPrtNo);
    	//sqlbv4.put("ModifyDate", CurrentDate);
    	//sqlbv4.put("ModifyTime", CurrentTime);
    	sqlbv5.put("prtno2", OldPrtNo);
    	
    	/*********************end add by ln,2008.02.27***********/
    	
        /**更新个人险种保单表*/
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPrtNo(OldPrtNo);
        mLCPolSet = tLCPolDB.query();

        for (int i = 1; i <= mLCPolSet.size(); i++)
        {
            mLCPolSet.get(i).setPrtNo(NewPrtNo);
            mLCPolSet.get(i).setModifyDate(CurrentDate);
            mLCPolSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新个人保单备份表*/
        LBPolDB tLBPolDB = new LBPolDB();
        tLBPolDB.setPrtNo(OldPrtNo);
        mLBPolSet = tLBPolDB.query();

        for (int i = 1; i <= mLBPolSet.size(); i++)
        {
            mLBPolSet.get(i).setPrtNo(NewPrtNo);
            mLBPolSet.get(i).setModifyDate(CurrentDate);
            mLBPolSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新个人保全备份表*/
        LPPolDB tLPPolDB = new LPPolDB();
        tLPPolDB.setPrtNo(OldPrtNo);
        mLPPolSet = tLPPolDB.query();

        for (int i = 1; i <= mLPPolSet.size(); i++)
        {
            mLPPolSet.get(i).setPrtNo(NewPrtNo);
            mLPPolSet.get(i).setModifyDate(CurrentDate);
            mLPPolSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新销售扎帐表*/
        LACommisionDB tLACommisionDB = new LACommisionDB();
        tLACommisionDB.setP14(OldPrtNo);
        mLACommisionSet = tLACommisionDB.query();

        for (int i = 1; i <= mLACommisionSet.size(); i++)
        {
            mLACommisionSet.get(i).setP14(NewPrtNo);
            mLACommisionSet.get(i).setModifyDate(CurrentDate);
            mLACommisionSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新保全批改原因表*/
        LCEdorReasonDB tLCEdorReasonDB = new LCEdorReasonDB();
        tLCEdorReasonDB.setEdorNo(OldPrtNo);
        tLCEdorReasonDB.setEdorType("10");
        tLCEdorReasonDB.setType("11");
        mLCEdorReasonSet = tLCEdorReasonDB.query();

        for (int i = 1; i <= mLCEdorReasonSet.size(); i++)
        {
            mLCEdorReasonSet.get(i).setEdorNo(NewPrtNo);
            mLCEdorReasonSet.get(i).setModifyDate(CurrentDate);
            mLCEdorReasonSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新补充附加险表*/
        LCAddPolDB tLCAddPolDB = new LCAddPolDB();
        tLCAddPolDB.setPrtNo(OldPrtNo);
        mLCAddPolSet = tLCAddPolDB.query();

        for (int i = 1; i <= mLCAddPolSet.size(); i++)
        {
            mLCAddPolSet.get(i).setPrtNo(NewPrtNo);
            mLCAddPolSet.get(i).setModifyDate(CurrentDate);
            mLCAddPolSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新记事本表*/
        LWNotePadDB tLWNotePadDB = new LWNotePadDB();
        tLWNotePadDB.setOtherNo(OldPrtNo);
        mLWNotePadSet = tLWNotePadDB.query();

        for (int i = 1; i <= mLWNotePadSet.size(); i++)
        {
            LWNotePadSchema tLWNotePadSchema = new LWNotePadSchema();
            tLWNotePadSchema = mLWNotePadSet.get(i);
            tLWNotePadSchema.setOtherNo(NewPrtNo);
            tLWNotePadSchema.setModifyDate(CurrentDate);
            tLWNotePadSchema.setModifyTime(CurrentTime);
            tLWNotePadSet.add(tLWNotePadSchema);
        }

        /**更新投保单删除记录表*/
        LCDelTraceDB tLCDelTraceDB = new LCDelTraceDB();
        tLCDelTraceDB.setPrtNo(OldPrtNo);
        mLCDelTraceSet = tLCDelTraceDB.query();

        for (int i = 1; i <= mLCDelTraceSet.size(); i++)
        {
            mLCDelTraceSet.get(i).setPrtNo(NewPrtNo);
        }
        
        lcdeltraceStr = 
        	"update LCDelTrace set prtno='"+"?prtno1?"+"' "
        	+ "where prtno='"+"?prtno2?"+"' ";
        sqlbv11.sql(lcdeltraceStr);
        sqlbv11.put("prtno1", NewPrtNo);
        sqlbv11.put("prtno2", OldPrtNo);

        /**更新保户撤单申请表*/
        LCApplyRecallPolDB tLCApplyRecallPolDB = new LCApplyRecallPolDB();
        tLCApplyRecallPolDB.setPrtNo(OldPrtNo);
        mLCApplyRecallPolSet = tLCApplyRecallPolDB.query();

        for (int i = 1; i <= mLCApplyRecallPolSet.size(); i++)
        {
            mLCApplyRecallPolSet.get(i).setPrtNo(NewPrtNo);
            mLCApplyRecallPolSet.get(i).setModifyDate(CurrentDate);
            mLCApplyRecallPolSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新保全备注表*/
        LPRemarkDB tLPRemarkDB = new LPRemarkDB();
        tLPRemarkDB.setPrtNo(OldPrtNo);
        mLPRemarkSet = tLPRemarkDB.query();

        for (int i = 1; i <= mLPRemarkSet.size(); i++)
        {
            mLPRemarkSet.get(i).setPrtNo(NewPrtNo);
            mLPRemarkSet.get(i).setModifyDate(CurrentDate);
            mLPRemarkSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新保全备注B表*/
        LBRemarkDB tLBRemarkDB = new LBRemarkDB();
        tLBRemarkDB.setPrtNo(OldPrtNo);
        mLBRemarkSet = tLBRemarkDB.query();

        for (int i = 1; i <= mLBRemarkSet.size(); i++)
        {
            mLBRemarkSet.get(i).setPrtNo(NewPrtNo);
            mLBRemarkSet.get(i).setModifyDate(CurrentDate);
            mLBRemarkSet.get(i).setModifyTime(CurrentTime);
        }

        /**更新扫描件相关表*/
        ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
        tES_DOC_MAINDB.setDocCode(OldPrtNo);
        mES_DOC_MAINSet = tES_DOC_MAINDB.query();
        logger.debug("OldPrtNo:" + OldPrtNo);
        logger.debug("mES_DOC_MAINSet.size():" + mES_DOC_MAINSet.size());

        for (int i = 1; i <= mES_DOC_MAINSet.size(); i++)
        {
            mES_DOC_MAINSet.get(i).setInputState("0");
            mES_DOC_MAINSet.get(i).setOperator("");

            //            mES_DOC_MAINSet.get(i).setModifyDate(CurrentDate);
            //            mES_DOC_MAINSet.get(i).setModifyTime(CurrentTime);
        }
        
        /*********************add by ln,2008.02.27***********/
        /**更新任务处理状态主表*/
        BPOMissionStateStr = 
        	"update BPOMissionState set bussno='"+"?bussno1?"
        	+ "',ModifyDate='"+"?ModifyDate?"+"',ModifyTime='"+"?ModifyTime?"+"' "
        	+ "where bussno='"+"?bussno2?"+"'";
        sqlbv6.sql(BPOMissionStateStr);
        sqlbv6.put("bussno1", NewPrtNo);
        sqlbv6.put("ModifyDate", CurrentDate);
        sqlbv6.put("ModifyTime", CurrentTime);
        sqlbv6.put("bussno2", OldPrtNo);
        
        /**更新差错记录表*/
        BPOErrLogStr = 
        	"update BPOErrLog set bussno='"+"?bussno1?" +"'"
        	+ "where bussno='"+"?bussno2?"+"'";
        sqlbv7.sql(BPOMissionStateStr);
        sqlbv7.put("bussno1", NewPrtNo);
        sqlbv7.put("bussno2", OldPrtNo);
        
        /**更新任务处理错误信息表*/
        BPOMissionDetailErrorStr = 
        	"update BPOMissionDetailError set bussno='"+"?bussno1?" +"'"
        	+ "where bussno='"+"?bussno2?"+"'";
        sqlbv8.sql(BPOMissionStateStr);
        sqlbv8.put("bussno1", NewPrtNo);
        sqlbv8.put("bussno2", OldPrtNo);
        
        /**更新任务处理状态子表*/
        BPOMissionDetailStateStr = 
        	"update BPOMissionDetailState set bussno='"+"?bussno1?"
        	+ "',ModifyDate='"+"?ModifyDate?"+"',ModifyTime='"+"?ModifyTime?"+"' "
        	+ "where bussno='"+"?bussno2?"+"'";
        sqlbv9.sql(BPOMissionStateStr);
        sqlbv9.put("bussno1", NewPrtNo);
        sqlbv9.put("ModifyDate", CurrentDate);
        sqlbv9.put("ModifyTime", CurrentTime);
        sqlbv9.put("bussno2", OldPrtNo);
        
        /**更新外包投保数据表*/
        BPOPolDataStr = 
        	"update BPOPolData set bussno ='" + "?bussno1?" +"'"
        	+ "where bussno='" + "?bussno2?" + "'";   
        sqlbv10.sql(BPOMissionStateStr);
        sqlbv10.put("bussno1", NewPrtNo);
        sqlbv10.put("bussno2", OldPrtNo);
        
        /*********************end add by ln,2008.02.27***********/

//        /**更新单证管理表*/
//        LZCardDB tLZCardDB = new LZCardDB();
//
//        //        tLZCardDB.setCertifyCode("1101");
//        tLZCardDB.setStartNo(OldPrtNo);
//        tLZCardDB.setEndNo(OldPrtNo);
//        tLZCardDB.setOperateFlag("1");
//        mLZCardSet = tLZCardDB.query();
//
//        if (mLZCardSet.size() == 0)
//        {
//            CError.buildErr(this, "请先将该单证" + OldPrtNo + "进行回收!");
//            return false;
//        }
//
//        CertifyCode = mLZCardSet.get(1).getCertifyCode();
//
//        for (int i = 1; i <= mLZCardSet.size(); i++)
//        {
//            String SendOutCom = mLZCardSet.get(i).getSendOutCom();
//            String ReceiveCom = mLZCardSet.get(i).getReceiveCom();
//            mLZCardSet.get(i).setOperateFlag("0");
//            mLZCardSet.get(i).setStateFlag("0");
//            mLZCardSet.get(i).setSendOutCom(ReceiveCom);
//            mLZCardSet.get(i).setReceiveCom(SendOutCom);
//        }
//
//        /**删除单证轨迹表OldPrtNo*/
//        LZCardTrackDB tLZCardTrackDB = new LZCardTrackDB();
//        tLZCardTrackDB.setCertifyCode(CertifyCode);
//        tLZCardTrackDB.setStartNo(OldPrtNo);
//        tLZCardTrackDB.setEndNo(OldPrtNo);
//        tLZCardTrackDB.setOperateFlag("1");
//        mLZCardTrackSet = tLZCardTrackDB.query();
//
//        if (mLZCardTrackSet.size() == 0)
//        {
//            CError.buildErr(this, "请先将该单证" + OldPrtNo + "进行回收!");
//            return false;
//        }
//
//        /**回收单证NewPrtNo*/
//        LZCardSet tempLZCardSet = new LZCardSet();
//        LZCardDB tempLZCardDB = new LZCardDB();
//        tempLZCardSet = tempLZCardDB.executeQuery("select * from lzcard where certifycode='" +
//                                                  CertifyCode +
//                                                  "' and startno<='" +
//                                                  NewPrtNo + "' and endno>='" +
//                                                  NewPrtNo +
//                                                  "' and stateflag='0' and operateflag='0'");
//
//        if (tempLZCardSet.size() == 0)
//        {
//            CError.buildErr(this, "单证" + NewPrtNo + "不能进行印刷号替换操作!");
//            return false;
//        }
//
//        LZCardSchema tLZCardSchema = new LZCardSchema();
//        tLZCardSchema.setCertifyCode(tempLZCardSet.get(1).getCertifyCode().trim());
//        tLZCardSchema.setSubCode("");
//        tLZCardSchema.setRiskCode("");
//        tLZCardSchema.setRiskVersion("");
//
//        tLZCardSchema.setStartNo(NewPrtNo);
//        tLZCardSchema.setEndNo(NewPrtNo);
//        tLZCardSchema.setStateFlag("1");
//
//        tLZCardSchema.setSendOutCom(tempLZCardSet.get(1).getReceiveCom());
//        tLZCardSchema.setReceiveCom(tempLZCardSet.get(1).getSendOutCom());
//        tLZCardSchema.setSumCount(1);
//        tLZCardSchema.setPrem("");
//        tLZCardSchema.setAmnt("");
//        tLZCardSchema.setHandler(mGlobalInput.Operator);
//        tLZCardSchema.setHandleDate(CurrentDate);
//        tLZCardSchema.setInvaliDate("");
//
//        tLZCardSchema.setTakeBackNo("");
//        tLZCardSchema.setSaleChnl("");
//        tLZCardSchema.setOperateFlag("");
//        tLZCardSchema.setPayFlag("");
//        tLZCardSchema.setEnterAccFlag("");
//        tLZCardSchema.setReason("");
//        tLZCardSchema.setState("");
//        tLZCardSchema.setOperator("");
//        tLZCardSchema.setMakeDate("");
//        tLZCardSchema.setMakeTime("");
//        tLZCardSchema.setModifyDate("");
//        tLZCardSchema.setModifyTime("");
//        tempLZCardSet.clear();
//        tempLZCardSet.add(tLZCardSchema);
//
//        tVData.add(mGlobalInput);
//        tVData.add(tempLZCardSet);
//
//        hashParams.put("CertifyClass", CertifyFunc.CERTIFY_CLASS_CERTIFY);
//        tVData.addElement(hashParams);

        return true;
    }
    
    /**
     * 替换成功后需要删除并备份当前工作流，并新增特殊投保单录入工作流
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealWorkFlow()
    {
    	/**删除并备份当前工作流*/
        LWMissionDB tLWMissionDB = new LWMissionDB();
        tLWMissionDB.setMissionProp1(OldPrtNo);
        LWMissionSet tLWMissionSet = tLWMissionDB.query();

        if (tLWMissionSet == null )
        {
            logger.debug("工作流数据查询失败！");
            CError.buildErr(this, "工作流数据查询失败！");
            return false;
        }
        Reflections mReflections = new Reflections();
        String ManageCom ="86";
        String BussNoType = "";
        String ScanMakeDate = "";
        String ScanMakeTime = "";
        ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
        ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
        tES_DOC_MAINDB.setDocCode(OldPrtNo);
        tES_DOC_MAINSet = tES_DOC_MAINDB.query();
        if(tES_DOC_MAINSet.size() == 1)
        {
        	ManageCom = tES_DOC_MAINSet.get(1).getManageCom();
        	BussNoType = tES_DOC_MAINSet.get(1).getBussType();
        	ScanMakeDate = tES_DOC_MAINSet.get(1).getMakeDate();
        	ScanMakeTime = tES_DOC_MAINSet.get(1).getMakeTime();
        }        	

        for(int i=1; i<=tLWMissionSet.size(); i++)
        {
        	logger.debug("tLWMissionSet.size():"+tLWMissionSet.size());
        	LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			LBMissionSchema tLBMissionSchema = new LBMissionSchema();
        	tLWMissionSchema = tLWMissionSet.get(i);       
        	
        	String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
			mReflections.transFields(tLBMissionSchema,
					tLWMissionSchema);
			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
			tLBMissionSchema.setMakeDate(CurrentDate);
			tLBMissionSchema.setMakeTime(CurrentTime);
			this.mLWMissionSet_Delete.add(tLWMissionSchema);
			this.mLBMissionSet_Insert.add(tLBMissionSchema);
        	
        }      
        
        //新增特殊投保单录入工作流
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("PrtNo", OldPrtNo);
        tTransferData.setNameAndValue("ContNo", OldPrtNo);
        tTransferData.setNameAndValue("ManageCom", ManageCom);
        tTransferData.setNameAndValue("Operator", mGlobalInput.Operator);
        tTransferData.setNameAndValue("BussNoType", BussNoType);
        tTransferData.setNameAndValue("DealType", "");
        tTransferData.setNameAndValue("State", "");
        tTransferData.setNameAndValue("BPOID", "");
        tTransferData.setNameAndValue("AppntNo", "");
        tTransferData.setNameAndValue("AppntName", "");
        tTransferData.setNameAndValue("InsuredNo", "");
        tTransferData.setNameAndValue("InsuredName", "");
        tTransferData.setNameAndValue("AgentCode", "");
        tTransferData.setNameAndValue("RiskCode", "");
        tTransferData.setNameAndValue("DefaultOperator", "");
        tTransferData.setNameAndValue("ScanMakeDate", ScanMakeDate);
        tTransferData.setNameAndValue("ScanMakeTime", ScanMakeTime);
        tTransferData.setNameAndValue("SubType", "TB1001");
        VData tVData1 = new VData();
        tVData1.add(mGlobalInput);
        tVData1.add(tTransferData);
        TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
        if(!tTbWorkFlowUI.submitData(tVData1, "7199999910"))
        {
        	CError.buildErr(this, "新增特殊投保单录入工作流失败！");
        	return false;
        }

        return true;
    }

    /**
     * 打印信息表
     * @return
     */
    private boolean prepareData()
    {
        MMap tMMap = new MMap();
        tMMap.put(mLCContSchema, "UPDATE");
        logger.debug("mLCPolSet:" + mLCPolSet.size());
        tMMap.put(mLCPolSet, "UPDATE");
        logger.debug("mLBPolSet:" + mLBPolSet.size());
        tMMap.put(mLBPolSet, "UPDATE");
        logger.debug("mLPPolSet:" + mLPPolSet.size());
        tMMap.put(mLPPolSet, "UPDATE");
        logger.debug("mLACommisionSet:" + mLACommisionSet.size());
        tMMap.put(mLACommisionSet, "UPDATE");
        logger.debug("mLCEdorReasonSet:" + mLCEdorReasonSet.size());
        tMMap.put(mLCEdorReasonSet, "UPDATE");
        logger.debug("mLCAddPolSet:" + mLCAddPolSet.size());
        tMMap.put(mLCAddPolSet, "UPDATE");
        logger.debug("mLWNotePadSet:" + mLWNotePadSet.size());
        tMMap.put(mLWNotePadSet, "DELETE");
        tMMap.put(tLWNotePadSet, "INSERT");
        logger.debug("mLCDelTraceSet:" + mLCDelTraceSet.size());
        tMMap.put(mLCDelTraceSet, "UPDATE");
        tMMap.put(sqlbv11,"UPDATE");
        logger.debug("mLCApplyRecallPolSet:" +
                           mLCApplyRecallPolSet.size());
        tMMap.put(mLCApplyRecallPolSet, "UPDATE");
        logger.debug("mLPRemarkSet:" + mLPRemarkSet.size());
        tMMap.put(mLPRemarkSet, "UPDATE");
        logger.debug("mLBRemarkSet:" + mLBRemarkSet.size());
        tMMap.put(mLBRemarkSet, "UPDATE");
        logger.debug("mLZCardSet:" + mLZCardSet.size());
//        tMMap.put(mLZCardSet, "UPDATE");
//        logger.debug("mLZCardTrackSet:" + mLZCardTrackSet.size());
//        tMMap.put(mLZCardTrackSet, "DELETE");
        logger.debug("mES_DOC_MAINSet:" + mES_DOC_MAINSet.size());
        tMMap.put(mES_DOC_MAINSet, "UPDATE");
        /*********************add by ln,2008.02.27***********/
        logger.debug("BPOMissionState update sql:" + BPOMissionStateStr);
        tMMap.put(sqlbv6,"UPDATE");
        logger.debug("BPOErrLog update sql:" + BPOErrLogStr);
        tMMap.put(sqlbv7,"UPDATE");
        logger.debug("BPOMissionDetailError update sql:" + BPOMissionDetailErrorStr);
        tMMap.put(sqlbv8,"UPDATE");
        logger.debug("BPOMissionDetailState update sql:" + BPOMissionDetailStateStr);
        tMMap.put(sqlbv9,"UPDATE");
        logger.debug("BPOPolData update sql:" + BPOPolDataStr);
        tMMap.put(sqlbv10,"UPDATE");
        /**********************end add by ln************/  
        //修改双岗录入相关表 2009-3-2 ln add
        SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
        sqlbv12.sql("update lbpocont set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv12.put("prtno1", NewPrtNo);
        sqlbv12.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv12,"UPDATE");
        SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
        sqlbv13.sql("update lbpoappnt set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv13.put("prtno1", NewPrtNo);
        sqlbv13.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv13,"UPDATE");
        SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
        sqlbv14.sql("update lbpoinsured set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv14.put("prtno1", NewPrtNo);
        sqlbv14.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv14,"UPDATE");
        SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
        sqlbv15.sql("update lbpopol set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv15.put("prtno1", NewPrtNo);
        sqlbv15.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv15,"UPDATE");
        SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
        sqlbv16.sql("update lbpocustomerimpart set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv16.put("prtno1", NewPrtNo);
        sqlbv16.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv16,"UPDATE");
        SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
        sqlbv17.sql("update lbpocustomerimpartdetail set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv17.put("prtno1", NewPrtNo);
        sqlbv17.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv17,"UPDATE");
        
        //修改辅助核保相关表 2009-7-24 ln add
        SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
        sqlbv18.sql("update lccspec set proposalcontno='"+"?prtno1?"+"' where proposalcontno='"+"?prtno2?"+"'");
        sqlbv18.put("prtno1", NewPrtNo);
        sqlbv18.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv18,"UPDATE");
        SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
        sqlbv19.sql("update lcpenotice set proposalcontno='"+"?prtno1?"+"' where proposalcontno='"+"?prtno2?"+"'");
        sqlbv19.put("prtno1", NewPrtNo);
        sqlbv19.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv19,"UPDATE");
        SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
        sqlbv20.sql("update lcpenoticeitem set proposalcontno='"+"?prtno1?"+"' where proposalcontno='"+"?prtno2?"+"'");
        sqlbv20.put("prtno1", NewPrtNo);
        sqlbv20.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv20,"UPDATE");
        SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
        sqlbv21.sql("update lcpenoticereply set proposalcontno='"+"?prtno1?"+"' where proposalcontno='"+"?prtno2?"+"'");
        sqlbv21.put("prtno1", NewPrtNo);
        sqlbv21.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv21,"UPDATE");
        SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
        sqlbv22.sql("update lcpenoticeresult set proposalcontno='"+"?prtno1?"+"' where proposalcontno='"+"?prtno2?"+"'");
        sqlbv22.put("prtno1", NewPrtNo);
        sqlbv22.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv22,"UPDATE");
        SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
        sqlbv23.sql("update lcrreport set proposalcontno='"+"?prtno1?"+"' where proposalcontno='"+"?prtno2?"+"'");
        sqlbv23.put("prtno1", NewPrtNo);
        sqlbv23.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv23,"UPDATE");
        SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
        sqlbv24.sql("update lcissuepol set proposalcontno='"+"?prtno1?"+"' where proposalcontno='"+"?prtno2?"+"'");
        sqlbv24.put("prtno1", NewPrtNo);
        sqlbv24.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv24,"UPDATE");
        
       // tMMap.put("update LCInsureAccClassFee set prtno='"+NewPrtNo+"' where prtno='"+OldPrtNo+"'","UPDATE");
        SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
        sqlbv25.sql("update LCCustomerImpart set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv25.put("prtno1", NewPrtNo);
        sqlbv25.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv25,"UPDATE");
        SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
        sqlbv26.sql("update LCCustomerImpartParams set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv26.put("prtno1", NewPrtNo);
        sqlbv26.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv26,"UPDATE");
        SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
        sqlbv27.sql("update LCCustomerImpartDetail set prtno='"+"?prtno1?"+"' where prtno='"+"?prtno2?"+"'");
        sqlbv27.put("prtno1", NewPrtNo);
        sqlbv27.put("prtno2", OldPrtNo);
        tMMap.put(sqlbv27,"UPDATE");
        tMMap.put(sqlbv1,"UPDATE");
        tMMap.put(sqlbv2,"UPDATE");
        tMMap.put(sqlbv3,"UPDATE");
        tMMap.put(sqlbv4,"UPDATE");
        tMMap.put(sqlbv5,"UPDATE");
        
        //更新工作流数据
        if(mLWMissionSet_Delete!=null)
        {
        	logger.debug("mLWMissionSet_Delete:" + mLWMissionSet_Delete.size());
        	tMMap.put(mLWMissionSet_Delete,"DELETE");
        }
        if(mLBMissionSet_Insert!=null)
        {
        	logger.debug("mLBMissionSet_Insert:" + mLBMissionSet_Insert.size());
        	tMMap.put(mLBMissionSet_Insert,"INSERT");
        }        	

        /**删除承保锁表*/
        LockTableDB tLockTableDB = new LockTableDB();
        String strsql =
            "select * from LockTable where notype='LD' and nolimit like " +
            "concat('?nolimit?','%')" ;
        SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
        sqlbv28.sql(strsql);
        sqlbv28.put("nolimit", OldPrtNo);
        mLockTableSet = tLockTableDB.executeQuery(sqlbv28);

        if (mLockTableSet.size() > 0)
        {
            tMMap.put(mLockTableSet, "DELETE");
        }

        mInputData.add(tMMap);

        PubSubmit tPubSubmit = new PubSubmit();

        if (!tPubSubmit.submitData(mInputData, "PrtReplace"))
        {
            logger.debug("tPubSubmit.submitData错误 ");
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            this.mErrors.addOneError("操作数据库出现错误!");

            return false;
        }
//        else
//        {
//            CertTakeBackUI tCertTakeBackUI = new CertTakeBackUI();
//
//            if (!tCertTakeBackUI.submitData(tVData, "INSERT"))
//            {
//                CError.buildErr(this, "印刷号替换成功但用于替换的单证回收失败!");
//
//                return false;
//            }
//        }

        return true;
    }

    public VData getResult()
    {
        return mResult;
    }
}
