package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.io.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团单不定期缴费核销回滚核心程序</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author JL
 * @version 1.0
 */
public class NormPayGrpChooseRollBackBL
{
private static Logger logger = Logger.getLogger(NormPayGrpChooseRollBackBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后台传输数据的容器 */
    private VData mOutputData = new VData();

    /** 往前台传输数据的容器 */
    private VData mResult = new VData();

    /** 公共参数 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 数据操作字符串 */
    private String mOperate;
    private String mPayNo = ""; //交费收据号
    private String mTempFeeNo = "";
    private String mGrpPolNo = "";
    private String mGrpContNo = "";
    private String mSQL = "";
    private String mLastConfDate="";//用于查询账户轨迹表

    /** 数据 */
    private double mPayMoney = 0; //暂缴费金额
    private double mSumPayMoney = 0; //不定期保费总金额
    private double mLeavingMoney = 0; //团单余额

    /** 控制标记字符串 */
    private String ignoreTBFlag = "N"; // 个人退保是否忽略，默认为：不忽略
    private String ignoreCAFlag = "Y"; // 账户资金调整是否忽略，默认为：忽略

    /** 传入参数数据表容器 */
    private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
    private LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema();

    /** 提交后台数据表容器 */
    private LCPolSet mLCPolSet = new LCPolSet();
    private LCDutySet mLCDutySet = new LCDutySet();
    private LCPremSet mLCPremSet = new LCPremSet();
    private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
    private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
    private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccFeeSet mLCInsureAccFeeSet = new LCInsureAccFeeSet();
    private LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
    private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
    private LJAPaySet mLJAPaySet = new LJAPaySet();
    private LJAPayGrpSet mLJAPayGrpSet = new LJAPayGrpSet();
    private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
    private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
    private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
    private LCRollBackLogSet mLCRollBackLogSet = new LCRollBackLogSet();

    public NormPayGrpChooseRollBackBL()
    {
    }

    public VData getResult()
    {
        return this.mResult;
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }

        //得到外部传入的数据,将数据备份到本类中
        if (!checkData())
        {
            return false;
        }

        //进行相关查询处理
        if ((mOperate != null)
                && (mOperate.equals("QUERYTB") || mOperate.equals("QUERYCA")
                || mOperate.equals("QUERYPAY") || mOperate.equals("QUERYCASE")))
        {
            if (!queryData())
            {
                return false;
            }
            return true;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }

        PubFun.out(this, "\n提交后台,更新数据库数据!");

        PubSubmit tPubSubmit=new PubSubmit();
        if(!tPubSubmit.submitData(mOutputData)){
        	CError.buildErr(this, "数据提交失败!");
        	return false;
        }
//        AutoConfirmBLS tAutoConfirmBLS = new AutoConfirmBLS();
//        if (!tAutoConfirmBLS.submitData(mOutputData, ""))
//        {
//            CError.buildErr(this, "数据提交失败!", tAutoConfirmBLS.mErrors);
//            return false;
//        }
        PubFun.out(this, "\n团单(" + mGrpPolNo + ")不定期缴费回滚成功!");

        return true;
    }

    /**
      * 从输入数据中得到所有对象
      * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
      */
    private boolean getInputData(VData mInputData)
    {
        String strFlag;
        try
        {
            mLCGrpPolSchema = (LCGrpPolSchema) mInputData.getObjectByObjectName("LCGrpPolSchema",
                    0);
            mLJTempFeeSchema = (LJTempFeeSchema) mInputData
                .getObjectByObjectName("LJTempFeeSchema", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput",
                    0);
            MMap map = (MMap) mInputData.getObjectByObjectName("MMap", 0);

            strFlag = (String) map.get("ignoreTBFlag");
            if (strFlag != null)
            {
                ignoreTBFlag = "Y";
            }

            strFlag = (String) map.get("ignoreCAFlag");
            if (strFlag == null)
            {
                ignoreCAFlag = "N";
            }

            logger.debug("ignoreTBFlag is " + ignoreTBFlag);
            logger.debug("ignoreCAFlag is " + ignoreCAFlag);

            if (mLCGrpPolSchema == null)
            {
                // @@错误处理
                CError.buildErr(this, "团单保单表信息传入失败!");
                return false;
            }

            if ((mLJTempFeeSchema == null)
                    || (mLJTempFeeSchema.getTempFeeNo() == null))
            {
                // @@错误处理
                CError.buildErr(this, "暂缴费表信息传入失败!");
                return false;
            }
        }
        catch (Exception ex)
        {
            CError.buildErr(this, "获取传入数据失败!");
            return false;
        }

        return true;
    }

    //校验续期回滚数据的正确性
    //校验通过返回true, 否则返回false
    private boolean checkData()
    {
        if ((mOperate == null)
                || (!mOperate.equals("GCHROLLBACK")
                && !mOperate.equals("QUERYTB") && !mOperate.equals("QUERYCA")
                && !mOperate.equals("QUERYPAY")
                && !mOperate.equals("QUERYCASE")))
        {
            logger.debug("mOperate = " + mOperate);
            CError.buildErr(this, "操作代码错误!");
            return false;
        }
        PubFun.out(this, "操作员为：  " + mGlobalInput.Operator);
        PubFun.out(this, "管理机构为：" + mGlobalInput.ManageCom);
        PubFun.out(this, "登陆机构为：" + mGlobalInput.ComCode);
        PubFun.out(this, "执行动作为：" + mOperate + "\n");

        mGrpPolNo = mLCGrpPolSchema.getGrpPolNo();
        if ((mGrpPolNo == null) || mGrpPolNo.equals(""))
        {
            CError.buildErr(this, "团单号获取失败!");
            return false;
        }

        mTempFeeNo = mLJTempFeeSchema.getTempFeeNo();
        if ((mTempFeeNo == null) || mTempFeeNo.equals(""))
        {
            CError.buildErr(this, "暂缴费号获取失败!");
            return false;
        }

        //回滚时不能操作保全项目
        mSQL = "select * from lpgrpedormain where grppolno='" + mGrpPolNo
            + "' and edorstate<>'0'";
        LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
        LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.executeQuery(mSQL);
        if ((tLPGrpEdorMainSet != null) && (tLPGrpEdorMainSet.size() > 0))
        {
            CError.buildErr(this, "该团单有保全项目处于未保全确认状态的数据,不能进行\"回滚不定期缴费\"操作!");
            return false;
        }

        return true;
    }

    //查询回滚相关信息
    private boolean queryData()
    {
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("RollBackBQList.vts", "printer"); //最好紧接着就初始化xml文档

        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        ListTable tlistTable = new ListTable();
        tlistTable.setName("ROLLBACKBQ");
        String[] strArr = null;

        //提取业务数据
        if (mOperate.equals("QUERYTB"))
        {
            texttag.add("TitleName", "退保清单");
            texttag.add("TitleName1", "批单号");
            texttag.add("TitleName2", "保单号");
            texttag.add("TitleName3", "批改类型");
            texttag.add("TitleName4", "操作日期");
            xmlexport.addTextTag(texttag);

            mSQL = "select a.edorno,a.polno,b.edortype,b.makedate from lbpol a,lpgrpedormain b "
                + "where a.edorno=b.edorno and b.edortype in ('ZT','XT','LT','WT') "
                + "and a.grppolno='" + mGrpPolNo
                + "' and a.polno in (select polno from ljapayperson where getnoticeno='"
                + mTempFeeNo + "')";

            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(mSQL);
            if (tSSRS.getMaxRow() <= 0)
            {
                mResult.clear();
                return true;
            }
            for (int i = 1; i <= tSSRS.getMaxRow(); i++)
            {
                tlistTable.add(tSSRS.getRowData(i));
            }
        }
        else if (mOperate.equals("QUERYCA"))
        {
            texttag.add("TitleName", "账户资金转移清单");
            texttag.add("TitleName1", "批单号");
            texttag.add("TitleName2", "保单号");
            texttag.add("TitleName3", "批改类型");
            texttag.add("TitleName4", "操作日期");
            xmlexport.addTextTag(texttag);

            mSQL = "select a.edorno,a.polno,a.edortype,b.makedate "
                + "from lpinsureacctrace a,lpgrpedormain b "
                + "where a.edorno=b.edorno and a.edortype=b.edortype "
                + "and a.edortype='CA' and b.grppolno='" + mGrpPolNo
                + "' and b.edorstate='0' and polno in (select polno "
                + "from ljapayperson where getnoticeno='" + mTempFeeNo
                + "') and b.makedate>=(select confdate from ljtempfee "
                + "where tempfeeno='" + mTempFeeNo
                + "') group by a.edorno,a.polno,a.edortype,b.makedate";

            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(mSQL);
            if (tSSRS.getMaxRow() <= 0)
            {
                mResult.clear();
                return true;
            }
            for (int i = 1; i <= tSSRS.getMaxRow(); i++)
            {
                tlistTable.add(tSSRS.getRowData(i));
            }
        }
        else if (mOperate.equals("QUERYPAY"))
        {
            texttag.add("TitleName", "生存领取清单");
            texttag.add("TitleName1", "批单号");
            texttag.add("TitleName2", "保单号");
            texttag.add("TitleName3", "批改类型");
            texttag.add("TitleName4", "操作日期");
            xmlexport.addTextTag(texttag);

            mSQL = "select b.edorno,a.polno,b.edortype,b.confdate from lcpol a,lpedormain b "
                + "where a.polno=b.polno and b.edortype = 'GA' and b.edorstate='0' "
                + "and a.grppolno='" + mGrpPolNo
                + "' and a.polno in (select polno from ljapayperson where getnoticeno='"
                + mTempFeeNo + "')";

            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(mSQL);
            if (tSSRS.getMaxRow() <= 0)
            {
                mResult.clear();
                return true;
            }
            for (int i = 1; i <= tSSRS.getMaxRow(); i++)
            {
                tlistTable.add(tSSRS.getRowData(i));
            }
        }
        else if (mOperate.equals("QUERYCASE"))
        {
            texttag.add("TitleName", "账户资金转移清单");
            texttag.add("TitleName1", "立案号");
            texttag.add("TitleName2", "保单号");
            texttag.add("TitleName3", "理赔状态");
            texttag.add("TitleName4", "操作日期");
            xmlexport.addTextTag(texttag);

            mSQL = "select rgtno,polno,polstate,makedate from llcasepolicy "
                + "where grppolno='" + mGrpPolNo
                + "' and polno in (select polno from ljapayperson where getnoticeno='"
                + mTempFeeNo + "')";

            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(mSQL);
            if (tSSRS.getMaxRow() <= 0)
            {
                mResult.clear();
                return true;
            }
            for (int i = 1; i <= tSSRS.getMaxRow(); i++)
            {
                tlistTable.add(tSSRS.getRowData(i));
            }
        }

        strArr = new String[4];
        strArr[0] = "EdorNo";
        strArr[1] = "PolNo";
        strArr[2] = "EdorType";
        strArr[3] = "Confdate";
        xmlexport.addListTable(tlistTable, strArr);

        mResult.clear();
        mResult.addElement(xmlexport);

        //显示XML文件格式
        try
        {
            BufferedReader brin = new BufferedReader(new InputStreamReader(
                        xmlexport.getInputStream()));
            String s = "";

            logger.debug("\n==> List Begin");
            while ((s = brin.readLine()) != null)
            {
                logger.debug(s);
            }
            logger.debug("==> List End\n");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return true;
    }

    //根据前面的输入数据，进行逻辑处理
    //如果在处理过程中出错，则返回false,否则返回true
    private boolean dealData()
    {
        try
        {
        	String tNowDate =PubFun.getCurrentDate();
        	String tNowTime =PubFun.getCurrentTime();
            PubFun.out(this, "\n开始准备团单(" + mGrpPolNo + ")不定期缴费回滚数据!\n");
            ExeSQL tExeSQL=new ExeSQL();
            mGrpContNo =tExeSQL.getOneValue("select grpcontno from lcgrppol where grppolno='"+mGrpPolNo+"'");

            /* 恢复暂缴费表(LJTempFee)数据准备 */
            LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
//            tLJTempFeeDB.setOtherNo(mGrpPolNo);
            tLJTempFeeDB.setOtherNo(mGrpContNo);
            tLJTempFeeDB.setTempFeeNo(mTempFeeNo);
            mLJTempFeeSet = tLJTempFeeDB.query();
            if ((mLJTempFeeSet == null) || (mLJTempFeeSet.size() != 1))
            {
                CError.buildErr(this, "暂缴费记录查询失败!");
                return false;
            }
            if (!mLJTempFeeSet.get(1).getConfFlag().equals("1"))
            {
                CError.buildErr(this, "暂交费未核销!");
                return false;
            }
            mSQL = "select * from ljtempfee where otherno='" + mGrpContNo
                + "' and confdate>'" + mLJTempFeeSet.get(1).getConfDate() + "'";
            tLJTempFeeDB = new LJTempFeeDB();
            LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(mSQL);
            if (tLJTempFeeSet.size() > 1)
            {
                CError.buildErr(this, "本次回滚的不定期缴费后还有不定期缴费核销记录,请先将之后的所有不定期操作回滚!");
                return false;
            }

            mPayMoney = mLJTempFeeSet.get(1).getPayMoney();
            mLJTempFeeSet.get(1).setConfDate("");
            mLJTempFeeSet.get(1).setConfFlag("0");
            //不用修改暂交费表 

            /* 恢复暂缴费分类表(LJTempFeeClass)数据准备 */
            LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
            tLJTempFeeClassDB.setTempFeeNo(mTempFeeNo);
            mLJTempFeeClassSet = tLJTempFeeClassDB.query();
            for (int i = 1; i <= mLJTempFeeClassSet.size(); i++)
            {
                mLJTempFeeClassSet.get(i).setConfDate("");
                mLJTempFeeClassSet.get(i).setConfFlag("0");
            }
            //无须修暂交费分类表

            /* 恢复实收总表(LJAPay)数据准备 */
            LJAPayDB tLJAPayDB = new LJAPayDB();
            tLJAPayDB.setIncomeNo(mGrpContNo);
            tLJAPayDB.setGetNoticeNo(mTempFeeNo);
            mLJAPaySet = tLJAPayDB.query();
            if ((mLJAPaySet == null) || (mLJAPaySet.size() != 1))
            {
                CError.buildErr(this, "实收总表查询失败!");
                return false;
            }
            LJAPaySchema tLJAPaySchema=new LJAPaySchema();
            tLJAPaySchema =mLJAPaySet.get(1);
            mPayNo =tLJAPaySchema.getPayNo();
            mSumPayMoney=tLJAPaySchema.getSumActuPayMoney();
            mLastConfDate =tLJAPaySchema.getConfDate();
            //生成新的交费号 插入金额为负的记录
            String tPayNo = PubFun1.CreateMaxNo("PAYNO", PubFun
    				.getNoLimit(mLJTempFeeSet.get(1).getManageCom()));
			tLJAPaySchema.setPayNo(tPayNo);
			tLJAPaySchema.setSerialNo(tPayNo);
			tLJAPaySchema.setConfDate(tNowDate);
			tLJAPaySchema.setMakeDate(tNowDate);
			tLJAPaySchema.setMakeTime(tNowTime);
			tLJAPaySchema.setModifyDate(tNowDate);
			tLJAPaySchema.setModifyTime(tNowTime);
			tLJAPaySchema.setOperator(this.mGlobalInput.Operator);
			tLJAPaySchema.setSumActuPayMoney(-tLJAPaySchema.getSumActuPayMoney());//金额为负的记录
			mLJAPaySet.clear();
			mLJAPaySet.add(tLJAPaySchema);
			
			
            //mPayNo = mLJAPaySet.get(1).getPayNo();
            //mSumPayMoney = mLJAPaySet.get(1).getSumActuPayMoney(); //应缴保费总和

            /* 恢复集体实收表(LJAPayGrp)数据准备 */
            LJAPayGrpDB tLJAPayGrpDB = new LJAPayGrpDB();
            tLJAPayGrpDB.setGrpPolNo(mGrpPolNo);
            tLJAPayGrpDB.setPayNo(mPayNo);
            mLJAPayGrpSet = tLJAPayGrpDB.query();
            if ((mLJAPayGrpSet == null) || (mLJAPayGrpSet.size() != 1))
            {
                CError.buildErr(this, "实收集体缴费表查询失败!");
                return false;
            }
            LJAPayGrpSchema tLJAPayGrpSchema=new LJAPayGrpSchema();
            tLJAPayGrpSchema=mLJAPayGrpSet.get(1);
            tLJAPayGrpSchema.setPayNo(tPayNo);
            tLJAPayGrpSchema.setMakeDate(tNowDate);
            tLJAPayGrpSchema.setMakeTime(tNowTime);
            tLJAPayGrpSchema.setModifyDate(tNowDate);
            tLJAPayGrpSchema.setModifyTime(tNowTime);
            tLJAPayGrpSchema.setConfDate(tNowDate);
            tLJAPayGrpSchema.setOperator(this.mGlobalInput.Operator);
            tLJAPayGrpSchema.setSumActuPayMoney(-tLJAPayGrpSchema.getSumActuPayMoney());
            tLJAPayGrpSchema.setSumDuePayMoney(-tLJAPayGrpSchema.getSumDuePayMoney());
            mLJAPayGrpSet.clear();
            mLJAPayGrpSet.add(tLJAPayGrpSchema);
            

            /* 恢复实收个人表(LJAPayPerson)数据准备 */
            LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
            tLJAPayPersonDB.setGrpPolNo(mGrpPolNo);
            tLJAPayPersonDB.setPayNo(mPayNo);
            LJAPayPersonSet tempLJAPayPersonSet = tLJAPayPersonDB.query();
            for (int k=1;k<=tempLJAPayPersonSet.size();k++){
            	LJAPayPersonSchema tLJAPayPersonSchema=tempLJAPayPersonSet.get(k);
            	tLJAPayPersonSchema.setPayNo(tPayNo);
            	tLJAPayPersonSchema.setMakeDate(tNowDate);
            	tLJAPayPersonSchema.setMakeTime(tNowTime);
            	tLJAPayPersonSchema.setModifyDate(tNowDate);
            	tLJAPayPersonSchema.setModifyTime(tNowTime);
            	tLJAPayPersonSchema.setOperator(this.mGlobalInput.Operator);
            	tLJAPayPersonSchema.setSumActuPayMoney(-tLJAPayPersonSchema.getSumActuPayMoney());
            	tLJAPayPersonSchema.setSumDuePayMoney(-tLJAPayPersonSchema.getSumDuePayMoney());
            	tLJAPayPersonSchema.setConfDate(tNowDate);
            	mLJAPayPersonSet.add(tLJAPayPersonSchema);
            }

            PubFun.out(this, "需要循环处理" + mLJAPayPersonSet.size() + "条实收个人表数据!");

            //循环实收个人表,处理个人保费和账户信息
            for (int i = 1; i <= mLJAPayPersonSet.size(); i++)
            {
                String tPolNo = mLJAPayPersonSet.get(i).getPolNo();

                PubFun.out(this, "开始处理保单(" + tPolNo + ")实收个人表数据");

                //校验保单数据
                LCPolDB tLCPolDB = new LCPolDB();
                tLCPolDB.setPolNo(tPolNo);
                if (!tLCPolDB.getInfo())
                {
                    if (ignoreTBFlag.equals("N"))
                    {
                        CError.buildErr(this,
                            "保单(" + tPolNo + "等)表查询失败(或有给付记录)!"
                            + "执行\"回滚不定期缴费\"操作前,请根据\"查看退保信息列表\"提示信息进行相关处理!");
                        return false;
                    }
                    else
                    {
                        continue;
                    }
                }

                /* 恢复保费表(LCPrem)数据准备 */
                LCPremDB tLCPremDB = new LCPremDB();
                tLCPremDB.setPolNo(tPolNo);
                tLCPremDB.setDutyCode(mLJAPayPersonSet.get(i).getDutyCode());
                tLCPremDB.setPayPlanCode(mLJAPayPersonSet.get(i).getPayPlanCode());
                if (!tLCPremDB.getInfo())
                {
                    CError.buildErr(this, "保费表查询失败!");
                    return false;
                }
                LCPremSchema tLCPremSchema = tLCPremDB.getSchema();

                tLCPremSchema.setPayTimes(tLCPremSchema.getPayTimes() - 1);
                tLCPremSchema.setSumPrem(tLCPremSchema.getSumPrem()
                    - mLJAPayPersonSet.get(i).getSumActuPayMoney());
                tLCPremSchema.setPaytoDate(mLJAPayPersonSet.get(i)
                                                           .getLastPayToDate());

                /*
                  这里可以增加对保费表的保费进行回滚，现暂不支持
                */

                //校验保费表(LCPrem)恢复后的数据有效性
                if (tLCPremSchema.getPayTimes() < 0)
                {
                    CError.buildErr(this, "保单(" + tPolNo + ")缴费次数错误!");
                    return false;
                }
                if (tLCPremSchema.getSumPrem() < 0)
                {
                    CError.buildErr(this, "保单(" + tPolNo + ")累计保费错误!");
                    return false;
                }
                mLCPremSet.add(tLCPremSchema);

                /* End 恢复保费表(LCPrem)数据准备 */
                /* 恢复账户表(LCInsureAcc)及账户轨迹表(LCInsureAccTrace)数据准备 */
                if ((tLCPremSchema.getNeedAcc() == null)
                        || !tLCPremSchema.getNeedAcc().equals("1"))
                {
                    logger.debug("保单(" + tPolNo + ")保费与账户无关联!");
                    continue;
                }

                //校验是否有账户资金调整
                mSQL = "select * from lpinsureacctrace where edorno in"
                    + " (select edorno from lpgrpedoritem where edortype='CA'"
                    + " and edorstate='0' and makedate >='"
                    + mLJAPaySet.get(1).getConfDate() + "') and polno='"
                    + tPolNo + "'";

                LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
                LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB
                    .executeQuery(mSQL);

                if ((tLPInsureAccTraceSet != null)
                        && (tLPInsureAccTraceSet.size() > 0))
                {
                    if (ignoreCAFlag.equals("N"))
                    {
                        CError.buildErr(this,
                            "保单(" + tPolNo + "等)做过账户资金调整,执行\"回滚不定期缴费\"操作前,"
                            + "请根据\"查看资金转移信息列表\"提示信息进行相关处理!");
                        return false;
                    }
                }

                LCPremToAccDB tLCPremToAccDB = new LCPremToAccDB();
                tLCPremToAccDB.setPolNo(tPolNo);
                tLCPremToAccDB.setDutyCode(mLJAPayPersonSet.get(i).getDutyCode());
                tLCPremToAccDB.setPayPlanCode(mLJAPayPersonSet.get(i)
                                                              .getPayPlanCode());
                LCPremToAccSet tLCPremToAccSet = tLCPremToAccDB.query();

                double rollBackMoney = 0;
                for (int j = 1; j <= tLCPremToAccSet.size(); j++)
                {
                    DealAccount tDealAccount = new DealAccount();
                    rollBackMoney = tDealAccount.calInputMoney(tLCPremToAccSet
                            .get(j), tLCPremSchema.getPrem());
                    if (-1 == rollBackMoney)
                    {
                        CError.buildErr(this, "保单(" + tPolNo + ")保费计算账户专入金额错误!");
                        return false;
                    }
                    rollBackMoney = PubFun.setPrecision(rollBackMoney
                            + 0.00000000001, "0.00"); //格式化!!

                    logger.debug("保单(" + tPolNo + ")的 "
                        + tLCPremToAccSet.get(j).getInsuAccNo()
                        + "类型账户保费回滚金额为:" + rollBackMoney);
                    //回滚trace表
                    LMRiskAccPayDB tLMRiskAccPayDB = new LMRiskAccPayDB();
                    tLMRiskAccPayDB.setRiskCode(tLCPolDB.getRiskCode());
                    tLMRiskAccPayDB.setPayPlanCode(tLCPremToAccSet.get(j)
                                                                  .getPayPlanCode());
                    tLMRiskAccPayDB.setInsuAccNo(tLCPremToAccSet.get(j)
                                                                .getInsuAccNo());
                    if (!tLMRiskAccPayDB.getInfo())
                    {
                        CError.buildErr(this,
                            "险种(" + tLCPolDB.getRiskCode() + ")账户缴费描述表查询失败!");
                        return false;
                    }
                    if (tLMRiskAccPayDB.getPayNeedToAcc().equals("1")
                            && (rollBackMoney != 0))
                    {
                        //准备回滚账户轨迹表
                        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
                        tLCInsureAccTraceDB.setPolNo(tPolNo);
                        tLCInsureAccTraceDB.setInsuAccNo(tLCPremToAccSet.get(j)
                                                                        .getInsuAccNo());
                        tLCInsureAccTraceDB.setPayPlanCode(tLCPremToAccSet.get(j).getPayPlanCode());
                        
                        //tLCInsureAccTraceDB.setMoney(rollBackMoney);//金额是扣除管理费后的金额，所以不是rollBackMoney
                        tLCInsureAccTraceDB.setOtherNo(tPolNo);
                        tLCInsureAccTraceDB.setMakeDate(mLastConfDate);
                        LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB
                            .query();
                        if ((tLCInsureAccTraceSet == null)
                                || (tLCInsureAccTraceSet.size() < 1))
                        {
                            CError.buildErr(this,
                                "保单(" + tPolNo + ")账户轨迹信息查询失败!");
                            return false;
                        }
                        String tCheckSQL=" select * from lcinsureacctrace where (makedate >'"+tLCInsureAccTraceSet.get(1).getMakeDate()+"'"
                                        +" or (makedate ='"+tLCInsureAccTraceSet.get(1).getMakeDate()+"' "
                                        +" and maketime>'"+tLCInsureAccTraceSet.get(1).getMakeTime()+"')) and moneytype ='LX' "
                                        +" and grpcontno ='"+mGrpContNo+"'";
                        SSRS rSSRS =new SSRS();
                        tExeSQL =new ExeSQL();
                        rSSRS = tExeSQL.execSQL(tCheckSQL);
                        if(rSSRS.getMaxRow()>0){
                        	//说明在要回滚的tace记录后进行了结息
                        	CError.buildErr(this, "期间进行了结息！不能回滚");
                        	return false;
                        }
                        /*
                         * 回滚时 只需要向账户轨迹表、账户管理履历表中插入一条金额为负的记录
                         * */
                        //插入一条新的trace记录 金额为负
                        String tSerialNo = PubFun1.CreateMaxNo( "SERIALNO", 
                        		PubFun.getNoLimit( mLJAPayPersonSet.get(i).getManageCom()) );//产生流水号码
                        tLCInsureAccTraceSet.get(1).setSerialNo(tSerialNo);
                        tLCInsureAccTraceSet.get(1).setOperator(this.mGlobalInput.Operator);
                        tLCInsureAccTraceSet.get(1).setMakeDate(tNowDate);
                        tLCInsureAccTraceSet.get(1).setMakeTime(tNowTime);
                        tLCInsureAccTraceSet.get(1).setModifyDate(tNowDate);
                        tLCInsureAccTraceSet.get(1).setModifyTime(tNowTime);
                        tLCInsureAccTraceSet.get(1).setMoney(-tLCInsureAccTraceSet.get(1).getMoney());

                        //保存需要删除的账户轨迹信息
                        mLCInsureAccTraceSet.add(tLCInsureAccTraceSet.get(1));
//                      还需要回滚 账户手续费表 add by liuqh 2009-03-16
                        LCInsureAccFeeTraceDB tLCInsureAccFeeTraceDB=new LCInsureAccFeeTraceDB();
                        LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet=new LCInsureAccFeeTraceSet();
                        String tFeeTraceSQL =" select * from lcinsureaccfeetrace where polno ='"+tPolNo+"' "
                                             +" and insuaccno ='"+tLCPremToAccSet.get(j).getInsuAccNo()+"' "
                                             +" and inerserialno='0' and makedate ='"+mLastConfDate+"' "
                                             +" and payplancode ='"+tLCPremToAccSet.get(j).getPayPlanCode()+"' ";
                        
//                        tLCInsureAccFeeTraceDB.setPolNo(tPolNo);
//                        tLCInsureAccFeeTraceDB.setInsuAccNo(tLCPremToAccSet.get(j)
//                                .getInsuAccNo());
//                        tLCInsureAccFeeTraceDB.setInerSerialNo(0);
//                        tLCInsureAccFeeTraceDB.setMakeDate(mLastConfDate);
//                        tLCInsureAccFeeTraceDB.setPayPlanCode(tLCPremToAccSet.get(j).getPayPlanCode());
                        //Schema BUG : InerSerialNo 类型为Int 而serInerSerialNo(0) 在通过DB.query() 查询时没有用
                        tLCInsureAccFeeTraceSet =tLCInsureAccFeeTraceDB.executeQuery(tFeeTraceSQL);
                        if ((tLCInsureAccFeeTraceSet == null)
                                || (tLCInsureAccFeeTraceSet.size() < 1))
                        {
                            CError.buildErr(this,
                                "保单(" + tPolNo + ")账户轨迹信息查询失败!");
                            return false;
                        }
                        //插入一条新的trace记录 金额为负
                        String tSerialfeeNo =  PubFun1.CreateMaxNo("SerialNo", PubFun.getNoLimit(mGlobalInput.ComCode));//产生流水号码
                        tLCInsureAccFeeTraceSet.get(1).setSerialNo(tSerialfeeNo);
                        tLCInsureAccFeeTraceSet.get(1).setOperator(this.mGlobalInput.Operator);
                        tLCInsureAccFeeTraceSet.get(1).setMakeDate(tNowDate);
                        tLCInsureAccFeeTraceSet.get(1).setMakeTime(tNowTime);
                        tLCInsureAccFeeTraceSet.get(1).setModifyDate(tNowDate);
                        tLCInsureAccFeeTraceSet.get(1).setModifyTime(tNowTime);
                        tLCInsureAccFeeTraceSet.get(1).setFee(-tLCInsureAccFeeTraceSet.get(1).getFee());
                        mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSet.get(1));
                    }
                    /*
//                  更改账户分类表  add by liuqh 2009-03-14
                    LCInsureAccClassDB tLCInsureAccClassDB=new LCInsureAccClassDB();
                    tLCInsureAccClassDB.setPolNo(tPolNo);
                    tLCInsureAccClassDB.setInsuAccNo(tLCPremToAccSet.get(j)
                                                               .getInsuAccNo());
                    tLCInsureAccClassDB.setAccAscription("1");
                    //tLCInsureAccClassDB.setPayPlanCode(tLCPremToAccSet.get(j).getPayPlanCode());
                    //在DealAccount类中 将otherno置为'polno'
                    tLCInsureAccClassDB.setPayPlanCode(tLCPremToAccSet.get(j).getPayPlanCode());
                    tLCInsureAccClassDB.setOtherNo(tPolNo);
                    if(!tLCInsureAccClassDB.getInfo()){
                    	CError.buildErr(this, "查询"+tPolNo+"的账户分类表错误！");
                    	return false;
                    }
                    LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassDB.getSchema();
                    tLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema.getInsuAccBala()+mLCInsureAccTraceSet.get(1).getMoney());
                    //tLCInsureAccClassSchema.setSumPay(tLCInsureAccClassSchema.getSumPay()-rollBackMoney);
                    tLCInsureAccClassSchema.setMakeDate(tNowDate);
                    tLCInsureAccClassSchema.setMakeTime(tNowTime);
                    tLCInsureAccClassSchema.setModifyDate(tNowDate);
                    tLCInsureAccClassSchema.setModifyTime(tNowTime);
                    tLCInsureAccClassSchema.setOperator(this.mGlobalInput.Operator);
                    
//                  校验账户金额
                    if (tLCInsureAccClassSchema.getInsuAccBala() < 0)
                    {
                        CError.buildErr(this,
                            "保单(" + tPolNo + "等)账户分类表("
                            + tLCInsureAccClassSchema.getInsuAccNo()
                            + ")回滚后剩余金额小于零! 执行\"回滚不定期缴费\"操作前,请进行相关处理。");
                        return false;
                    }
                    if (tLCInsureAccClassSchema.getSumPay() < 0)
                    {
                        CError.buildErr(this,
                            "保单(" + tPolNo + ")账户分类表("
                            + tLCInsureAccClassSchema.getInsuAccNo() + ")累计缴费小于零!");
                        return false;
                    }
                    mLCInsureAccClassSet.add(tLCInsureAccClassSchema);
                    //插入手续费分类表
                    LCInsureAccClassFeeDB tLCInsureAccClassFeeDB=new LCInsureAccClassFeeDB();
                    tLCInsureAccClassFeeDB.setPolNo(tPolNo);
                    tLCInsureAccClassFeeDB.setInsuAccNo(tLCPremToAccSet.get(j)
                                                               .getInsuAccNo());
                    tLCInsureAccClassFeeDB.setAccAscription("1");
                    //tLCInsureAccClassDB.setPayPlanCode(tLCPremToAccSet.get(j).getPayPlanCode());
                    //在DealAccount类中 将otherno置为'polno'
                    //tLCInsureAccClassFeeDB.setPayPlanCode(tLCPremToAccSet.get(j).getPayPlanCode());
                    tLCInsureAccClassFeeDB.setOtherNo(mLCInsureAccFeeTraceSet.get(1).getOtherNo());
                    tLCInsureAccClassFeeDB.setFeeCode(mLCInsureAccFeeTraceSet.get(1).getFeeCode());
                    mLCInsureAccClassFeeSet =tLCInsureAccClassFeeDB.query();
                    if ((mLCInsureAccClassFeeSet != null)
                            || (mLCInsureAccClassFeeSet.size() >= 1))
                    {
                        LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema=tLCInsureAccClassFeeDB.getSchema();
                        tLCInsureAccClassFeeSchema.setFee(-tLCInsureAccClassFeeSchema.getFee());
                        tLCInsureAccClassFeeSchema.setMakeDate(tNowDate);
                        tLCInsureAccClassFeeSchema.setMakeTime(tNowTime);
                        tLCInsureAccClassFeeSchema.setModifyDate(tNowDate);
                        tLCInsureAccClassFeeSchema.setModifyTime(tNowTime);
                        tLCInsureAccClassFeeSchema.setOperator(this.mGlobalInput.Operator);
                        mLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
                    }
//                    if(!tLCInsureAccClassFeeDB.getInfo()){
//                    	CError.buildErr(this, "查询"+tPolNo+"的账户分类表错误！");
//                    	return false;
//                    }

                    //修改账户表
                    LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
                    tLCInsureAccDB.setPolNo(tPolNo);
                    tLCInsureAccDB.setInsuAccNo(tLCPremToAccSet.get(j)
                                                               .getInsuAccNo());
//                    tLCInsureAccDB.setOtherNo(tPolNo);
                    if (!tLCInsureAccDB.getInfo())
                    {
                        CError.buildErr(this, "保单(" + tPolNo + ")账户信息查询失败!");
                        return false;
                    }
                    LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccDB
                        .getSchema();
                    tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema
                        .getInsuAccBala() + mLCInsureAccTraceSet.get(1).getMoney());
                    tLCInsureAccSchema.setSumPay(tLCInsureAccSchema.getSumPay()
                        + mLCInsureAccTraceSet.get(1).getMoney());

                    //校验账户金额
                    if (tLCInsureAccSchema.getInsuAccBala() < 0)
                    {
                        CError.buildErr(this,
                            "保单(" + tPolNo + "等)账户("
                            + tLCInsureAccSchema.getInsuAccNo()
                            + ")回滚后剩余金额小于零! 执行\"回滚不定期缴费\"操作前,请进行相关处理。");
                        return false;
                    }
                    if (tLCInsureAccSchema.getSumPay() < 0)
                    {
                        CError.buildErr(this,
                            "保单(" + tPolNo + ")账户("
                            + tLCInsureAccSchema.getInsuAccNo() + ")累计缴费小于零!");
                        return false;
                    }

                    //保存需要更新的账户信息
                    mLCInsureAccSet.add(tLCInsureAccSchema);
                    //像手续分账户表插入记录
                    LCInsureAccFeeDB tLCInsureAccFeeDB=new LCInsureAccFeeDB();
                    tLCInsureAccFeeDB.setPolNo(tPolNo);
                    tLCInsureAccFeeDB.setInsuAccNo(tLCPremToAccSet.get(j)
                                                               .getInsuAccNo());
//                    tLCInsureAccDB.setOtherNo(tPolNo);
                    if (!tLCInsureAccFeeDB.getInfo())
                    {
                        CError.buildErr(this, "保单(" + tPolNo + ")账户信息查询失败!");
                        return false;
                    }
                    LCInsureAccFeeSchema tLCInsureAccFeeSchema = tLCInsureAccFeeDB
                    .getSchema();
                    tLCInsureAccFeeSchema.setFee(-tLCInsureAccFeeSchema.getFee());

//                    if (tLCInsureAccFeeSchema.getFee() < 0)
//                    {
//                    	CError.buildErr(this,
//                    			"保单(" + tPolNo + ")账户("
//                    			+ tLCInsureAccFeeSchema.getInsuAccNo() + ")累手续费小于零!");
//                    	return false;
//                    }
                    mLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
                    
                    
                     */
                }

                /* End 恢复账户表(LCInsureAcc)及账户轨迹表(LCInsureAccTrace)数据准备 */
            }

            /* 恢复个人保单表(LCPol)数据准备 */
            mSQL = "select * from lcpol where grppolno='" + mGrpPolNo
                + "' and polno in (select polno from ljapayperson where payno='"
                + mPayNo + "')";
            LCPolDB tLCPolDB = new LCPolDB();
            mLCPolSet = tLCPolDB.executeQuery(mSQL);
            if ((mLCPolSet == null) || (mLCPolSet.size() <= 0))
            {
                CError.buildErr(this, "保单表查询失败! 可能需要回滚的个人保单都已经退保!");
                return false;
            }
            PubFun.out(this, "需要循环处理" + mLCPolSet.size() + "条个人保单表数据!");

            double sumPayMoney = 0;
            for (int i = 1; i <= mLCPolSet.size(); i++)
            {
                String tPolNo = mLCPolSet.get(i).getPolNo();
                mGrpContNo =mLCPolSet.get(1).getGrpContNo();//回滚表新增团体合同号
                PubFun.out(this, "开始处理保单(" + tPolNo + ")");
                mSQL = "select * from ljapayperson where grppolno='"
                    + mGrpPolNo + "' and payno='" + mPayNo + "' and polno='"
                    + tPolNo + "'";
                LJAPayPersonDB pLJAPayPersonDB = new LJAPayPersonDB();
                LJAPayPersonSet tLJAPayPersonSet = pLJAPayPersonDB.executeQuery(mSQL);
                sumPayMoney = 0;
                for (int j = 1; j <= tLJAPayPersonSet.size(); j++)
                {
                    sumPayMoney += tLJAPayPersonSet.get(j).getSumActuPayMoney();
                }
                PubFun.out(this, "保单(" + tPolNo + ")累计保费减少" + sumPayMoney + "元");

                mLCPolSet.get(i).setSumPrem(mLCPolSet.get(i).getSumPrem()
                    - sumPayMoney);
                mLCPolSet.get(i).setPaytoDate(tLJAPayPersonSet.get(1)
                                                              .getLastPayToDate());
                if (mLCPolSet.get(i).getSumPrem() < 0)
                {
                    CError.buildErr(this, "保单(" + tPolNo + ")累计保费回滚计算错误!");
                    return false;
                }

                /* 记录回滚轨迹表*/
                LCRollBackLogSchema tLCRollBackLogSchema = initLCRollBackLog(tLJAPayPersonSet
                        .get(1), sumPayMoney);
                mLCRollBackLogSet.add(tLCRollBackLogSchema);
            }

            /* 恢复个人责任表(LCPrem)数据准备 */
            mSQL = "select * from lcduty where polno in (select polno from ljapayperson where payno='"
                + mPayNo + "')";
            LCDutyDB tLCDutyDB = new LCDutyDB();
            mLCDutySet = tLCDutyDB.executeQuery(mSQL);
            if ((mLCDutySet == null) || (mLCDutySet.size() <= 0))
            {
                CError.buildErr(this, "保单责任表查询失败!");
                return false;
            }

            PubFun.out(this, "需要循环处理" + mLCDutySet.size() + "条个人责任表数据!");

            for (int i = 1; i <= mLCDutySet.size(); i++)
            {
                String tPolNo = mLCDutySet.get(i).getPolNo();
                String tDutyCode = mLCDutySet.get(i).getDutyCode();
                mSQL = "select * from ljapayperson where grppolno='"
                    + mGrpPolNo + "' and payno='" + mPayNo + "' and polno='"
                    + tPolNo + "' and dutycode='" + tDutyCode + "'";
                LJAPayPersonDB pLJAPayPersonDB = new LJAPayPersonDB();
                LJAPayPersonSet tLJAPayPersonSet = pLJAPayPersonDB.executeQuery(mSQL);
                sumPayMoney = 0;
                for (int j = 1; j <= tLJAPayPersonSet.size(); j++)
                {
                    sumPayMoney += tLJAPayPersonSet.get(j).getSumActuPayMoney();
                }
                mLCDutySet.get(i).setSumPrem(mLCDutySet.get(i).getSumPrem()
                    - sumPayMoney);
                mLCDutySet.get(i).setPaytoDate(tLJAPayPersonSet.get(1)
                                                               .getLastPayToDate());
                if (mLCDutySet.get(i).getSumPrem() < 0)
                {
                    CError.buildErr(this, "保单(" + tPolNo + ")累计保费回滚计算错误!");
                    return false;
                }
            }

            /* 恢复团单表(LCGrpPol)数据准备 */
            LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
            tLCGrpPolDB.setGrpPolNo(mGrpPolNo);
            if (!tLCGrpPolDB.getInfo())
            {
                CError.buildErr(this, "团单(" + mGrpPolNo + ")查询失败!");
                return false;
            }
            if (mPayMoney < mSumPayMoney)
            {
                //需要修改团单余额
                tLCGrpPolDB.setDif(0);//LCGrpPol表的余额不准确
            }
            tLCGrpPolDB.setPaytoDate(mLJAPayGrpSet.get(1).getLastPayToDate());
            mLCGrpPolSet.add(tLCGrpPolDB.getSchema());
            //团体合同下的余额也需要修改 add by liuqh 2009-03-14
            LCGrpContDB tLCGrpContDB=new LCGrpContDB();
            tLCGrpContDB.setGrpContNo(tLCGrpPolDB.getGrpContNo());
            if(!tLCGrpContDB.getInfo()){
            	CError.buildErr(this, "团体合同(" + tLCGrpPolDB.getGrpContNo() + ")查询失败!");
            	return false;
            }
            if (mPayMoney < mSumPayMoney)
            {
            	tLCGrpContDB.setDif(tLCGrpContDB.getDif() + mSumPayMoney
            			- mPayMoney);
            }
            mLCGrpContSet.add(tLCGrpContDB.getSchema());
            PubFun.out(this, "\n团单(" + mGrpPolNo + ")不定期缴费回滚数据准备完成!\n");
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "准备回滚数据失败!");
            return false;
        }

        return true;
    }

    private boolean prepareOutputData()
    {
        try
        {
            MMap map1 = new MMap();

            map1.put(mLCPolSet, "UPDATE");
            map1.put(mLCDutySet, "UPDATE");
            map1.put(mLCPremSet, "UPDATE");
            map1.put(mLCGrpPolSet, "UPDATE");
            map1.put(mLCGrpContSet, "UPDATE");
//            map1.put(mLCInsureAccSet, "UPDATE");
            //6.5新增LCInsureAccClass 表
//            map1.put(mLCInsureAccSet, "UPDATE");
//            map1.put(mLCInsureAccFeeSet, "UPDATE");
//            map1.put(mLCInsureAccClassSet, "UPDATE");
//            map1.put(mLCInsureAccClassFeeSet, "UPDATE");
//            map1.put(mLCInsureAccTraceSet, "DELETE");
            //6.5 新增负记录
            map1.put(mLCInsureAccTraceSet, "INSERT");
            map1.put(mLCInsureAccFeeTraceSet, "INSERT");
//            map1.put(mLJAPaySet, "DELETE");
//            map1.put(mLJAPayGrpSet, "DELETE");
//            map1.put(mLJAPayPersonSet, "DELETE");
            //6.5新增负记录
            map1.put(mLJAPaySet, "INSERT");
            map1.put(mLJAPayGrpSet, "INSERT");
            map1.put(mLJAPayPersonSet, "INSERT");
            //回滚时不需要修改咱交费表、分类表
//            map1.put(mLJTempFeeSet, "UPDATE");
//            map1.put(mLJTempFeeClassSet, "UPDATE");
            //map1.put("update a set a.b=5;", "INSERT");
            map1.put(mLCRollBackLogSet, "INSERT");

            mOutputData.clear();
            mOutputData.add(map1);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "准备传入后台数据失败!");
            return false;
        }

        return true;
    }

    /* 初始化回滚日志表 */
    private LCRollBackLogSchema initLCRollBackLog(
        LJAPayPersonSchema tLJAPayPersonSchema, double money)
    {
        LCRollBackLogSchema tLCRollBackLogSchema = new LCRollBackLogSchema();
        String tSerialNo = PubFun1.CreateMaxNo("SERIALNO",
                PubFun.getNoLimit(mGlobalInput.ManageCom));
        PubFun.out(this, "==> serialNo:" + tSerialNo);

        tLCRollBackLogSchema.setSerialNo(tSerialNo);
        tLCRollBackLogSchema.setGrpPolNo("00000000000000000000");
        tLCRollBackLogSchema.setGrpContNo(mGrpContNo);
        tLCRollBackLogSchema.setPolNo(tLJAPayPersonSchema.getPolNo());
        tLCRollBackLogSchema.setOtherNo(tLJAPayPersonSchema.getGetNoticeNo()); //暂交费号
        tLCRollBackLogSchema.setOtherNoType("1"); //1--暂交费号
        tLCRollBackLogSchema.setType("1"); //1--团单不定期缴费回滚
        tLCRollBackLogSchema.setStandbyFlag1(mPayNo);
        tLCRollBackLogSchema.setStandbyFlag2(mLJAPaySet.get(1).getPayNo());
        tLCRollBackLogSchema.setMoney(money); //不定期缴费金额
        tLCRollBackLogSchema.setOldMakeDate(tLJAPayPersonSchema.getMakeDate());
        tLCRollBackLogSchema.setOldMakeTime(tLJAPayPersonSchema.getMakeTime());
        tLCRollBackLogSchema.setOldOperator(tLJAPayPersonSchema.getOperator());
        tLCRollBackLogSchema.setMakeDate(PubFun.getCurrentDate());
        tLCRollBackLogSchema.setMakeTime(PubFun.getCurrentTime());
        tLCRollBackLogSchema.setOperator(mGlobalInput.Operator);
        tLCRollBackLogSchema.setManageCom(mGlobalInput.ManageCom); 

        return tLCRollBackLogSchema;
    }

    /* 测试 */
    public static void main(String[] args)
    {
        GlobalInput mGI = new GlobalInput();
        mGI.Operator = "001";
        mGI.ComCode = "86";
        mGI.ManageCom = "86";

        LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
        tLCGrpPolSchema.setGrpPolNo("86110020040220000830");

        LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
        tLJTempFeeSchema.setTempFeeNo("86110020050360012884");

        MMap map = new MMap();
        map.put("ignoreTBFlag", "on");
        map.put("ignoreCAFlag", "on");

        VData tVData = new VData();
        tVData.add(mGI);
        tVData.add(tLCGrpPolSchema);
        tVData.add(tLJTempFeeSchema);
        tVData.add(map);

        NormPayGrpChooseRollBackBL tNormPayGrpChooseRollBackBL = new NormPayGrpChooseRollBackBL();

        tNormPayGrpChooseRollBackBL.submitData(tVData, "GCHROLLBACK");

        //tNormPayGrpChooseRollBackBL.submitData(tVData, "QUERYCA");
    }
}
