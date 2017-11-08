package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.finfee.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.sql.*;

/**
 * <p> Title: Web业务系统 </p>
 * <p> Description: UI功能类 </p>
 * <p>Copyright: Copyright (c) 2002 </p>
 * <p>Company: Sinosoft </p>
 * @author zy
 * @version 1.0
 * @date 2004-10-26
 */
public class BeforeTBDeal
{
private static Logger logger = Logger.getLogger(BeforeTBDeal.class);

    /** 传入数据的容器 */
    private VData mInputData = new VData();

    /** 数据操作字符串 */
    private String mOperate;
    private String newpolno;
    private int Index = 0;
    private int mPolNum = 0;
    private VData mResult = new VData();

    /** 错误处理类 */
    public CErrors mErrors = new CErrors();

    /** 全局数据 */
    private GlobalInput tGI = new GlobalInput();

    // @Constructor
    public BeforeTBDeal()
    {
    }

    // @Method
    public static void main(String[] args)
    {
        //    LCPolSchema mLCPolSchema = new LCPolSchema();
        //    mLCPolSchema.setPolNo("86510020043100000090");
        //    mLCPolSchema.setPrtNo("86310000000114");
        //    VData aVData = new VData();
        //    aVData.add(mLCPolSchema);
        //    AutoSignUI tAutoSignUI = new AutoSignUI();
        //    tAutoSignUI.submitData(aVData,"INSERT");
    
    }

    public VData getResult()
    {
        return this.mResult;
    }

    /**
     * 数据提交方法
     * @param: 传入数据、数据操作字符串
     * @return: boolean
     **/
    public boolean submitData(VData cInputData, String cOperate)
    {
        // 数据操作字符串拷贝到本类中
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (this.getInputData(cInputData) == false)
        {
            return false;
        }
        return true;
    }

    private boolean getInputData(VData cInputData)
    {
        mPolNum = Integer.parseInt((String) cInputData.get(1));
        logger.debug("导入保单个数为:" + mPolNum);

        if (mPolNum < 0)
        {
            return false;
        }

        for (int a = 0; a < mPolNum; a++)
        {
            if ((cInputData.get(2).equals("^|") && (Index == 0)))
            {
                Index = 2;
                if (dealdata(cInputData, a) == false)
                {
                    return false;
                }
            }
            else if (cInputData.get(Index + 1).equals("^|"))
            {
                Index += 1;
                if (dealdata(cInputData, a) == false)
                {
                    mResult.add((String) cInputData.get(Index + 3));
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealdata(VData cInputData, int num)
    {
        ProposalUI tProposalUI = new ProposalUI();
        GlobalInput tG = new GlobalInput();

        tG.Operator = "8699";
        tG.ComCode = "86";
        tG.ManageCom = "86";

        String tEnterAccDate = (String) cInputData.get(Index + 20);
        newpolno = (String) cInputData.get(Index + 1);
        
        //合同
        LCContSchema tLCContSchema = new LCContSchema();
        tLCContSchema.setContNo(newpolno);  //合同号
        tLCContSchema.setGrpContNo("00000000000000000000"); //集体合同号
        tLCContSchema.setProposalContNo(newpolno);       //投保单合同号
        tLCContSchema.setInsuredNo((String) cInputData.get(Index + 18));
        tLCContSchema.setAgentCode("EC" + (String) cInputData.get(Index + 4));
        tLCContSchema.setAgentGroup(""); //投保程序会置值
        tLCContSchema.setAgentType("");
        tLCContSchema.setContType("1");
        tLCContSchema.setPrtNo((String) cInputData.get(Index + 2)); //印刷号
        tLCContSchema.setManageCom((String) cInputData.get(Index + 4)); //保单管理机构
        tLCContSchema.setPrem((String) cInputData.get(Index + 5)); //保费
        tLCContSchema.setSaleChnl("04"); //单独销售渠道：网上承保
        tLCContSchema.setPolApplyDate((String) cInputData.get(Index + 19));
        tLCContSchema.setCValiDate((String) cInputData.get(Index + 19)); //保单生效日期
        tLCContSchema.setMult((String) cInputData.get(Index + 7)); //份数
//        tLCContSchema.setAmnt((String) cInputData.get(Index + 6)); //保额
        tLCContSchema.setRnewFlag("-1");//自动续保
        tLCContSchema.setPolType("0"); //保单类型标记
        tLCContSchema.setPayLocation("4"); //单独销售渠道：网上承保
        tLCContSchema.setSellType("12");//

        // 投保人信息部分
        // 个人投保人
        LCAppntSchema tLCAppntSchema = new LCAppntSchema();
        tLCAppntSchema.setContNo(newpolno);
        tLCAppntSchema.setAppntNo((String) cInputData.get(Index + 18)); //客户号
        tLCAppntSchema.setAppntName((String) cInputData.get(Index + 8)); //姓名
        tLCAppntSchema.setAppntSex((String) cInputData.get(Index + 9)); //性别
        tLCAppntSchema.setAppntBirthday((String) cInputData.get(Index + 10)); //出生日期
        tLCAppntSchema.setGrpContNo("00000000000000000000");
        //年龄，不提交
        tLCAppntSchema.setIDType((String) cInputData.get(Index + 11)); //证件类型
        tLCAppntSchema.setIDNo((String) cInputData.get(Index + 12)); //证件号码
        tLCAppntSchema.setWorkType((String) cInputData.get(Index + 17)); //职业（工种）
        tLCAppntSchema.setPrtNo((String) cInputData.get(Index + 2));//印刷号

         // 告知信息
        LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
        logger.debug("end set schema 告知信息...");

        //个人客户地址表
        LCAddressSchema tLCAddressSchema = new LCAddressSchema();
        tLCAddressSchema.setCustomerNo((String) cInputData.get(Index + 18)); //客户号
        tLCAddressSchema.setPostalAddress((String) cInputData.get(Index + 15)); //联系地址
        tLCAddressSchema.setZipCode((String) cInputData.get(Index + 16)); //邮政编码
        tLCAddressSchema.setPhone((String) cInputData.get(Index + 13)); //联系电话（1）
        tLCAddressSchema.setMobile((String) cInputData.get(Index + 14)); //移动电话
        
        //客户账户表
        LCAccountSchema tLCAccountSchema = new LCAccountSchema();
        
        //多业务员佣金比例---
        LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
        
        LDPersonSet tLDPersonSet = new LDPersonSet();               
        TransferData tTransferData = new TransferData();
       
        tTransferData.setNameAndValue("GrpNo", ""); 
        tTransferData.setNameAndValue("GrpName", ""); 
        tTransferData.setNameAndValue("mark", ""); 
        tTransferData.setNameAndValue("KDCheckFlag", "0"); 
        
        VData tVData = new VData();
        tVData.addElement(tLCContSchema);
        tVData.addElement(tLCAppntSchema);
        tVData.addElement(tLCAddressSchema);
        tVData.addElement(tLCAccountSchema);
        tVData.addElement(tLCCustomerImpartSet);
        tVData.addElement(tLACommisionDetailSet);
        tVData.addElement(tTransferData);
        tVData.addElement(tG);
        
        ContBL tContBL = new ContBL();
        if(!tContBL.submitData(tVData,"INSERT||CONT")){
			this.mErrors.copyAllErrors(tContBL.mErrors);
			return false;
        }

        tLCContSchema.setSchema((LCContSchema) tContBL.getResult()
				.getObjectByObjectName("LCContSchema", 0));
        tLCAddressSchema.setSchema((LCAddressSchema) tContBL.getResult()
				.getObjectByObjectName("LCAddressSchema", 0));
        tLDPersonSet = ((LDPersonSet) tContBL.getResult()
				.getObjectByObjectName("LDPersonSet", 0));
        tLCCustomerImpartSet = ((LCCustomerImpartSet) tContBL.getResult()
				.getObjectByObjectName("LCCustomerImpartSet", 0));
        tLACommisionDetailSet = ((LACommisionDetailSet) tContBL.getResult()
				.getObjectByObjectName("LACommisionDetailSet", 0));
        
        // 主被保人/********投保人与被保人为同一个***************/
        LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
        tLCInsuredSchema.setContNo(newpolno);
        tLCInsuredSchema.setAppntNo((String) cInputData.get(Index + 18));
        tLCInsuredSchema.setInsuredNo((String) cInputData.get(Index + 18));
        tLCInsuredSchema.setName((String) cInputData.get(Index + 8)); //姓名
        tLCInsuredSchema.setSex((String) cInputData.get(Index + 9)); //性别
        tLCInsuredSchema.setBirthday((String) cInputData.get(Index + 10)); //出生日期
        tLCInsuredSchema.setGrpContNo("00000000000000000000");
        tLCInsuredSchema.setContPlanCode("");
        //年龄，不提交
        tLCInsuredSchema.setIDType((String) cInputData.get(Index + 11)); //证件类型
        tLCInsuredSchema.setIDNo((String) cInputData.get(Index + 12)); //证件号码
        tLCInsuredSchema.setWorkType((String) cInputData.get(Index + 17)); //职业（工种）

        tTransferData = new TransferData();
        tTransferData.setNameAndValue("ISPlanRisk", ""); 
        tTransferData.setNameAndValue("FamilyType", "0");    
        tTransferData.setNameAndValue("mark", ""); 
        tTransferData.setNameAndValue("KDCheckFlag", "0"); 
        tTransferData.setNameAndValue("DiskImportFlag", ""); 
        tTransferData.setNameAndValue("PolTypeFlag", "0"); 
        tTransferData.setNameAndValue("ContType", "1"); 
        tTransferData.setNameAndValue("SequenceNo", "1"); 
        tTransferData.setNameAndValue("InsuredPeoples", ""); 
        tTransferData.setNameAndValue("InsuredAppAge", ""); 
        tTransferData.setNameAndValue("LoadFlag", ""); 

        LLAccountSchema tLLAccountSchema = new LLAccountSchema();
        tVData = new VData();
        tVData.addElement(tLCContSchema);
        tVData.addElement(tLCAddressSchema);
        tVData.addElement(tLCAccountSchema);
        tVData.addElement(tLCCustomerImpartSet);
        tVData.addElement(tLACommisionDetailSet);
        tVData.addElement(tTransferData);
        tVData.addElement(tG);
        tVData.addElement(tLLAccountSchema);
        tVData.addElement(tLCInsuredSchema);
        tVData.addElement(tLDPersonSet.getObj(1));//一个客户信息，所以采用获取第一条记录
        
        ContInsuredBL tContInsuredBL = new ContInsuredBL();
        if(!tContInsuredBL.submitData(tVData,"INSERT||CONTINSURED")){
        	this.mErrors.copyAllErrors(tContInsuredBL.mErrors);
        	return false;       	
        }
        
        logger.debug("end ContInsuredBL...");
        tLCContSchema.setSchema((LCContSchema) tContInsuredBL.getResult().getObjectByObjectName("LCContSchema", 0));
        tLCInsuredSchema.setSchema((LCInsuredSchema) tContInsuredBL.getResult().getObjectByObjectName("LCInsuredSchema", 0));
//        tLCAddressSchema.setSchema((LCAddressSchema)tContInsuredBL.getResult().getObjectByObjectName("LCAddressSchema", 0));

        // 保单信息部分
        LCPolSchema tLCPolSchema = new LCPolSchema();
        LCDutySchema tLCDutySchema = new LCDutySchema();
        logger.debug("设置保单基本信息...");
       
        tLCPolSchema.setGrpContNo("00000000000000000000");
        tLCPolSchema.setGrpPolNo("00000000000000000000");
        tLCPolSchema.setContNo(newpolno);
        tLCPolSchema.setProposalNo(newpolno); //保单号
        tLCPolSchema.setAgentCode("EC" + (String) cInputData.get(Index + 4));
        tLCPolSchema.setAgentGroup(""); //投保程序会置值
        tLCPolSchema.setPrtNo((String) cInputData.get(Index + 2)); //印刷号
        tLCPolSchema.setRiskCode((String) cInputData.get(Index + 3));//险种编码
        tLCPolSchema.setManageCom((String) cInputData.get(Index + 4)); //保单管理机构
        tLCPolSchema.setPrem((String) cInputData.get(Index + 5)); //保费
        tLCPolSchema.setSaleChnl("04"); //单独销售渠道：网上承保
        tLCPolSchema.setPolTypeFlag("0"); //保单类型标记
        tLCPolSchema.setContType("1");//合同类型
        tLCPolSchema.setPolApplyDate((String) cInputData.get(Index + 19));
        tLCPolSchema.setCValiDate((String) cInputData.get(Index + 19)); //保单生效日期
        tLCPolSchema.setSpecifyValiDate("Y");//生效日期不能变更，为参数指定
        tLCPolSchema.setMult((String) cInputData.get(Index + 7)); //份数
        tLCPolSchema.setRnewFlag("-1");//自动续保
        tLCPolSchema.setPayLocation("4"); //收费方式
        tLCPolSchema.setRiskSequence("1");
        if (tLCPolSchema.getRiskCode().equals("241801"))
        {
            tLCPolSchema.setStandbyFlag1("B"); //内部分类
        }
        if (tLCPolSchema.getRiskCode().equals("241802"))
        {
            tLCPolSchema.setStandbyFlag1("0"); //内部分类
        }
        if (tLCPolSchema.getRiskCode().equals("311603"))
        {
            //tLCPolSchema.setStandbyFlag1("0"); //内部分类
           tLCDutySchema.setInsuYear(1);
           tLCDutySchema.setInsuYearFlag("Y");
        }

        logger.debug("==> 导入保单(" + newpolno + ")；印刷号为"
            + tLCPolSchema.getPrtNo());
        // 连带被保险人(不处理)
        // 受益人信息
        if (((String) cInputData.get(Index + 21) == null)
                || cInputData.get(Index + 21).equals(""))
        {
        	CError.buildErr(this, "没有受益人信息");
            return false;
        }
        //如果没有受益人信息，则不执行下列程序
        double flo = 0;
        LCBnfSet tLCBnfSet = new LCBnfSet();
        int Bnfnum = Integer.parseInt((String) cInputData.get(Index + 21));
        for (int i = 0; i < Bnfnum; i++)
        {
            LCBnfSchema tLCBnfSchema;
            tLCBnfSchema = new LCBnfSchema();
            tLCBnfSchema.setBnfType((String) cInputData.get(Index + 21
                    + (i * 7) + 6));
            tLCBnfSchema.setName((String) cInputData.get(Index + 21 + (i * 7)
                    + 1));
            tLCBnfSchema.setIDType("0");
            tLCBnfSchema.setIDNo((String) cInputData.get(Index + 21 + (i * 7)
                    + 2));
            tLCBnfSchema.setBnfGrade("1");//依次传递受益人的级别
            tLCBnfSchema.setPostalAddress((String) cInputData.get(Index + 21
                    + (i * 7) + 4));
            tLCBnfSchema.setRelationToInsured((String) cInputData.get(Index
                    + 21 + (i * 7) + 7));
            tLCBnfSchema.setBnfLot((String) cInputData.get(Index + 21 + (i * 7)
                    + 5));
            flo = flo + Double.parseDouble((String)cInputData.get(Index + 21 + (i * 7)+ 5));
            tLCBnfSet.add(tLCBnfSchema);
        }
        
        // 受益人比率之和不能超过100%
        if (flo>1)
        {
        	CError.buildErr(this, "受益人收益比例和为"+flo+"。大于100%，不能提交！");
            return false;
        }
        
        Index = Index + 21 + (Bnfnum * 7);
        logger.debug("end set schema 受益人信息...");
        logger.debug("============INdex" + Index + "==========");
        // 特别约定
        LCSpecSet tLCSpecSet = new LCSpecSet();

        //传递非SCHEMA信息
        tTransferData = new TransferData();
        tTransferData.setNameAndValue("GetDutyKind", ""); //年金开始领取年龄
        tTransferData.setNameAndValue("samePersonFlag", "1"); //投保人同被保人标志
        tTransferData.setNameAndValue("deleteAccNo", "1");
        tTransferData.setNameAndValue("ChangePlanFlag", "1");
 

        tVData = new VData();

        // 准备传输数据 VData
        tVData.addElement(tLCPolSchema);
        tVData.addElement(tLCInsuredSchema);
        tVData.addElement(tLCBnfSet);
        tVData.addElement(tLCCustomerImpartSet);
        tVData.addElement(tLCSpecSet);
        tVData.addElement(tG);
        tVData.addElement(tTransferData);
        tVData.addElement(tLCDutySchema);
        tVData.addElement(tLCContSchema);
        tVData.addElement(tLCAppntSchema);
      
        if (tProposalUI.submitData(tVData, mOperate))
        {
            LCPolSchema tempLCPolSchema = (LCPolSchema)tProposalUI.getResult().getObjectByObjectName("LCPolSchema", 0);
          //返回被保人客户号

            VData nVData = new VData();
            nVData.addElement(tempLCPolSchema);//由于是一个合同下就一张保单，所以只传递保单信息就可以了
            nVData.addElement(tEnterAccDate);
            AutoSignUI tAutoSignUI = new AutoSignUI();
            if (tAutoSignUI.submitData(nVData, "INSERT"))
            {
                mResult.add(newpolno+"|"+tempLCPolSchema.getAppntNo()); 
//                mResult.add(tempLCPolSchema.getInsuredNo());
                return true;
            }
            else
            {
                if (tAutoSignUI.mErrors.needDealError())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(tAutoSignUI.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "AdjustBankPolBL";
                    tError.functionName = "submitDat";
                    tError.errorMessage = "数据提交失败!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
        	this.mErrors.copyAllErrors(tProposalUI.mErrors);
        	return false;
        }
    }

}
