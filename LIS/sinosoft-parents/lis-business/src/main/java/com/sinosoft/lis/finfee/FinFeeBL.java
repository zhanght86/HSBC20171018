/*
 * <p>ClassName: LRContManageBL </p>
 * <p>Description: FinFeeBL类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: 张斌
 * @CreateDate：2009-11-15
 */
package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.finfee.finpay.LJTempNewPolicy;
import com.sinosoft.lis.finfee.finpay.LJTempContinuePolicy;
import com.sinosoft.lis.finfee.finpay.LJTempConBefPolicy;
import com.sinosoft.lis.finfee.finpay.LJTempEdorPolicy;
import com.sinosoft.lis.finfee.finpay.LJTempClaimPolicy;
import com.sinosoft.lis.finfee.finpay.LJTempUrgePayPolicy;
import com.sinosoft.lis.bq.PEdorAutoConfirmBL;

public class FinFeeBL
{
private static Logger logger = Logger.getLogger(FinFeeBL.class);


	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private VData mVData = new VData();

	private TransferData mTransferData = new TransferData();

	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();

	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();

	/** 数据操作字符串 */
	private String strOperate;

	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	// 业务处理相关变量
	/** 全局数据 */

	public FinFeeBL()
	{}

	/**
	 * 提交数据处理方法
	 * @param cInputData 传入的数据,VData对象
	 * @param cOperate 数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		this.strOperate = cOperate;
		if (strOperate.equals(""))
		{
			buildError("verifyOperate", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData))
			return false;

		if (!dealData())
		{
			return false;
		}
		return true;
	}

	public static void main(String[] args)
	{
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.Operator = "001";
		VData tVData = new VData();

		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();

		tLJTempFeeClassSchema.setTempFeeNo("001");
		tLJTempFeeClassSchema.setPayMode("1");
		tLJTempFeeClassSchema.setChequeNo("0000");
		tLJTempFeeClassSchema.setPayMoney("10000");
		tLJTempFeeClassSchema.setAppntName("aaa");
		tLJTempFeeClassSchema.setPayDate("2009-01-01");
		tLJTempFeeClassSchema.setConfDate("2009-01-01");
		tLJTempFeeClassSchema.setApproveDate("2009-01-01");
		tLJTempFeeClassSchema.setEnterAccDate("2009-01-01");
		tLJTempFeeClassSchema.setConfFlag("1");
		tLJTempFeeClassSchema.setSerialNo("");
		tLJTempFeeClassSchema.setManageCom("8611");
		tLJTempFeeClassSchema.setPolicyCom("8611");
		tLJTempFeeClassSchema.setBankCode("");
		tLJTempFeeClassSchema.setBankAccNo("");
		tLJTempFeeClassSchema.setAccName("");
		tLJTempFeeClassSchema.setConfMakeDate("");
		tLJTempFeeClassSchema.setConfMakeTime("");
		tLJTempFeeClassSchema.setChequeDate("");
		tLJTempFeeClassSchema.setOperator("");
		tLJTempFeeClassSchema.setMakeDate("");
		tLJTempFeeClassSchema.setMakeTime("");
		tLJTempFeeClassSchema.setModifyDate("");
		tLJTempFeeClassSchema.setModifyTime("");
		tLJTempFeeClassSchema.setInBankCode("");
		tLJTempFeeClassSchema.setInBankAccNo("");
		tLJTempFeeClassSchema.setInAccName("");
		tLJTempFeeClassSchema.setContCom("8610");
		tLJTempFeeClassSchema.setOtherNo("9999");
		tLJTempFeeClassSchema.setOtherNoType("4");
		tLJTempFeeClassSchema.setIDType("");
		tLJTempFeeClassSchema.setIDNo("");
		tLJTempFeeClassSchema.setCurrency("1");
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		tLJTempFeeClassSet.add(tLJTempFeeClassSchema);

		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();

		tLJTempFeeSchema.setTempFeeNo("001");
		tLJTempFeeSchema.setTempFeeType("1");
		tLJTempFeeSchema.setRiskCode("000000");
		tLJTempFeeSchema.setPayIntv("");
		tLJTempFeeSchema.setOtherNo("9999");
		tLJTempFeeSchema.setOtherNoType("4");
		tLJTempFeeSchema.setPayMoney("10000");
		tLJTempFeeSchema.setPayDate("2009-01-01");
		tLJTempFeeSchema.setEnterAccDate("2009-01-01");
		tLJTempFeeSchema.setConfDate("2009-01-01");
		tLJTempFeeSchema.setConfMakeDate("2009-01-01");
		tLJTempFeeSchema.setConfMakeTime("00:00:00");
		tLJTempFeeSchema.setSaleChnl("");
		tLJTempFeeSchema.setManageCom("8611");
		tLJTempFeeSchema.setPolicyCom("8610");
		tLJTempFeeSchema.setAgentCom("8612");
		tLJTempFeeSchema.setAgentType("");
		tLJTempFeeSchema.setAPPntName("");
		tLJTempFeeSchema.setAgentGroup("");
		tLJTempFeeSchema.setAgentCode("200001");
		tLJTempFeeSchema.setConfFlag("1");
		tLJTempFeeSchema.setSerialNo("");
		tLJTempFeeSchema.setOperator("001");
		tLJTempFeeSchema.setState("1");
		tLJTempFeeSchema.setMakeTime("");
		tLJTempFeeSchema.setMakeDate("");
		tLJTempFeeSchema.setModifyDate("");
		tLJTempFeeSchema.setModifyTime("");
		tLJTempFeeSchema.setContCom("");
		tLJTempFeeSchema.setPayEndYear("");
		tLJTempFeeSchema.setStandPrem("");
		tLJTempFeeSchema.setRemark("");
		tLJTempFeeSchema.setDistict("");
		tLJTempFeeSchema.setDepartment("");
		tLJTempFeeSchema.setBranchCode("");
		tLJTempFeeSchema.setCurrency("");
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSet.add(tLJTempFeeSchema);

		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86";
		tGI.Operator = "001";
		tGI.ManageCom = "86";

		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("FinFeeNo", "0000000003");
		tVData.add(tGI);
		tVData.add(tLJTempFeeClassSet);
		tVData.add(tLJTempFeeSet);
		tVData.add(mTransferData);

		try
		{
			FinFeeBL tFinFeeBL = new FinFeeBL();
			if (!tFinFeeBL.submitData(tVData, "INSERT"))
			{
				logger.debug(" error: " + tFinFeeBL.mErrors.getFirstError());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData()
	{
		try
		{
			this.mInputData.clear();
			this.mInputData.add(mMap);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		// 进行收费操作
		if (this.strOperate.equals("INSERT"))
		{
			if (!insertData())
			{
				// @@错误处理
				// buildError("insertData", "添加数据时出现错误!");
				return false;
			}
		}
		return true;
	}

	/**
	 * insertData
	 * @return boolean
	 */
	private boolean insertData()
	{
		try
		{
			for (int i = 1; i <= mLJTempFeeSet.size(); i++)
			{
				// 新单交费
				if ("1".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					boolean flag = false;
					for (int j = 1; j <= mLJTempFeeClassSet.size(); j++)
					{
						// if
						// (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())&&mLJTempFeeSet.get(i).getSerialNo().equals(mLJTempFeeClassSet.get(j).getSerialNo()))
						// {
						if (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())
								&& mLJTempFeeSet.get(i).getOtherNo().equals(mLJTempFeeClassSet.get(j).getOtherNo())
								&& mLJTempFeeSet.get(i).getOtherNoType().equals(mLJTempFeeClassSet.get(j).getOtherNoType())
								&& mLJTempFeeSet.get(i).getCurrency().equals(mLJTempFeeClassSet.get(j).getCurrency()))
						{
							flag = true;
							// 处理新单交费
							mVData.clear();
							mVData.add(globalInput);
							mVData.add(mTransferData);
							mVData.add(mLJTempFeeSet.get(i));
							mVData.add(mLJTempFeeClassSet.get(j));
							LJTempNewPolicy tLJTempNewPolicy = new LJTempNewPolicy();
							if (!tLJTempNewPolicy.submitData(mVData, ""))
							{
								buildError("dealData", tLJTempNewPolicy.getCErrors().getFirstError());
								return false;
							}
						}
					}
					if (!flag)
					{
						buildError("dealData", "财务收费记录与业务信息不匹配");
						return false;
					}
				}
				// //续期催缴交费
				// else if ("2".equals(mLJTempFeeSet.get(i).getTempFeeType())) {
				// boolean flag=false;
				// for (int j=1; j <= mLJTempFeeClassSet.size(); j++) {
				// if
				// (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo()))
				// {
				// flag = true;
				// logger.debug("LJTempFeeSet.get(i).getTempFeeNo: "+mLJTempFeeSet.get(i).getTempFeeNo());
				// logger.debug("mLJTempFeeClassSet.get(j).getTempFeeNo: "+mLJTempFeeClassSet.get(j).getTempFeeNo());
				// mVData.clear();
				// mVData.add(globalInput);
				// mVData.add(mTransferData);
				// mVData.add(mLJTempFeeSet.get(i));
				// mVData.add(mLJTempFeeClassSet.get(i));
				// LJTempContinuePolicy tLJTempContinuePolicy = new
				// LJTempContinuePolicy();
				// if (!tLJTempContinuePolicy.submitData(mVData, "")){
				// buildError("dealData",
				// tLJTempContinuePolicy.getCErrors().getFirstError());
				// return false;
				// }
				//
				// }
				// }
				// if (!flag) {
				// buildError("dealData", "财务收费记录与业务信息不匹配");
				// return false;
				// }
				// }
				// 预交续期保费
				else if ("3".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					boolean flag = false;
					for (int j = 1; j <= mLJTempFeeClassSet.size(); j++)
					{
						// if
						// (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())&&mLJTempFeeSet.get(i).getSerialNo().equals(mLJTempFeeClassSet.get(j).getSerialNo()))
						// {
						if (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())
								&& mLJTempFeeSet.get(i).getOtherNo().equals(mLJTempFeeClassSet.get(j).getOtherNo())
								&& mLJTempFeeSet.get(i).getOtherNoType().equals(mLJTempFeeClassSet.get(j).getOtherNoType())
								&& mLJTempFeeSet.get(i).getCurrency().equals(mLJTempFeeClassSet.get(j).getCurrency()))
						{
							// 预交续期保费
							flag = true;
							mVData.clear();
							mVData.add(globalInput);
							mVData.add(mTransferData);
							mVData.add(mLJTempFeeSet.get(i));
							mVData.add(mLJTempFeeClassSet.get(j));
							LJTempConBefPolicy tLJTempConBefPolicy = new LJTempConBefPolicy();
							if (!tLJTempConBefPolicy.submitData(mVData, ""))
							{
								buildError("dealData", tLJTempConBefPolicy.getCErrors().getFirstError());
								return false;
							}
						}
					}
					if (!flag)
					{
						buildError("dealData", "财务收费记录与业务信息不匹配");
						return false;
					}
				}
				// 保全交费
				else if ("4".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					boolean flag = false;
					for (int j = 1; j <= mLJTempFeeClassSet.size(); j++)
					{
						// if
						// (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())&&mLJTempFeeSet.get(i).getSerialNo().equals(mLJTempFeeClassSet.get(j).getSerialNo()))
						// {
						if (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())
								&& mLJTempFeeSet.get(i).getOtherNo().equals(mLJTempFeeClassSet.get(j).getOtherNo())
								&& mLJTempFeeSet.get(i).getOtherNoType().equals(mLJTempFeeClassSet.get(j).getOtherNoType())
								&& mLJTempFeeSet.get(i).getCurrency().equals(mLJTempFeeClassSet.get(j).getCurrency()))
						{
							flag = true;
							mVData.clear();
							mVData.add(globalInput);
							mVData.add(mTransferData);
							mVData.add(mLJTempFeeSet.get(i));
							mVData.add(mLJTempFeeClassSet.get(j));
							LJTempEdorPolicy tLJTempEdorPolicy = new LJTempEdorPolicy();
							if (!tLJTempEdorPolicy.submitData(mVData, ""))
							{
								buildError("dealData", tLJTempEdorPolicy.getCErrors().getErrContent());
								return false;
							}
						}
					}
					if (!flag)
					{
						buildError("dealData", "财务收费记录与业务信息不匹配");
						return false;
					}
				}
				// 理赔收费
				else if (mLJTempFeeSet.get(i).getTempFeeType().equals("6"))
				{
					boolean flag = false;
					for (int j = 1; j <= mLJTempFeeClassSet.size(); j++)
					{
						// if
						// (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())&&mLJTempFeeSet.get(i).getSerialNo().equals(mLJTempFeeClassSet.get(j).getSerialNo()))
						// {
						if (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())
								&& mLJTempFeeSet.get(i).getOtherNo().equals(mLJTempFeeClassSet.get(j).getOtherNo())
								&& mLJTempFeeSet.get(i).getOtherNoType().equals(mLJTempFeeClassSet.get(j).getOtherNoType())
								&& mLJTempFeeSet.get(i).getCurrency().equals(mLJTempFeeClassSet.get(j).getCurrency()))
						{
							flag = true;
							mVData.clear();
							mVData.add(globalInput);
							mVData.add(mTransferData);
							mVData.add(mLJTempFeeSet.get(i));
							mVData.add(mLJTempFeeClassSet.get(j));
							LJTempClaimPolicy tLJTempClaimPolicy = new LJTempClaimPolicy();
							if (!tLJTempClaimPolicy.submitData(mVData, ""))
							{
								buildError("dealData", tLJTempClaimPolicy.getCErrors().getFirstError());
								return false;
							}
						}
					}
					if (!flag)
					{
						buildError("dealData", "财务收费记录与业务信息不匹配");
						return false;
					}
				}
				// 不定期交费
				/*else if (mLJTempFeeSet.get(i).getTempFeeType().equals("6"))
				{
					boolean flag = false;
					for (int j = 1; j <= mLJTempFeeClassSet.size(); j++)
					{
						// if
						// (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())&&mLJTempFeeSet.get(i).getSerialNo().equals(mLJTempFeeClassSet.get(j).getSerialNo()))
						// {
						if (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())
								&& mLJTempFeeSet.get(i).getOtherNo().equals(mLJTempFeeClassSet.get(j).getOtherNo())
								&& mLJTempFeeSet.get(i).getOtherNoType().equals(mLJTempFeeClassSet.get(j).getOtherNoType()))
						{
							flag = true;
							mVData.clear();
							mVData.add(globalInput);
							mVData.add(mTransferData);
							mVData.add(mLJTempFeeSet.get(i));
							mVData.add(mLJTempFeeClassSet.get(j));
							LJTempUrgePayPolicy tLJTempUrgePayPolicy = new LJTempUrgePayPolicy();
							if (!tLJTempUrgePayPolicy.submitData(mVData, ""))
							{
								buildError("dealData", tLJTempUrgePayPolicy.getCErrors().getFirstError());
								return false;
							}
						}
					}
					if (!flag)
					{
						buildError("dealData", "财务收费记录与业务信息不匹配");
						return false;
					}
				}
				else
				{
					buildError("dealData", "业务类型错误");
					return false;
				}*/

			}
			/**********************************************
			 * //单证核销 FinFeeCertify tFinFeeCertify = new FinFeeCertify(); VData
			 * tVData = new VData(); TransferData tTransferData = new
			 * TransferData(); tTransferData.setNameAndValue("CertifyFlag","");
			 * tVData.add(tTransferData); tVData.add(mLJTempFeeSet.get(1));
			 * if(!tFinFeeCertify.submitData(tVData)){
			 * buildError("","单证核销失败，"+tFinFeeCertify.mErrors.getFirstError());
			 * return false; } MMap certifyMap = tFinFeeCertify.getResult();
			 * mMap.add(certifyMap);
			 *************************************************/

			/***********************************/
			// 添加客户账户处理
			VData nInputData = new VData();
			nInputData.add(mLJTempFeeSet);
			nInputData.add(mLJTempFeeClassSet);
			nInputData.add(mLJAGetSchema);
			nInputData.add(globalInput);
			FICustomerMain tFICustomerMain = new FICustomerMain();
			// 调用客户账户收费接口，传入财务标志FI
			if (tFICustomerMain.submitData(nInputData, "FI"))
			{
				// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
				mMap.add(tFICustomerMain.getMMap());
			}
			else
			{
				mErrors.copyAllErrors(tFICustomerMain.mErrors);
			}
			/***********************************/
			// 注意：必须所有循环都
			// modify zhuwei 现金缴费时确定confmakedate confmaketime
			for (int i = 1; i <= mLJTempFeeSet.size(); i++)
			{

				if (mLJTempFeeSet.get(i).getEnterAccDate() == null)
				{

				}
				else
				{
					mLJTempFeeSet.get(i).setConfMakeDate(CurrentDate);
					mLJTempFeeSet.get(i).setConfMakeTime(CurrentTime);
				}
			}

			for (int i = 1; i <= mLJTempFeeClassSet.size(); i++)
			{
				if (mLJTempFeeClassSet.get(i).getEnterAccDate() == null)
				{}
				else
				{
					mLJTempFeeClassSet.get(i).setConfMakeDate(CurrentDate);
					mLJTempFeeClassSet.get(i).setConfMakeTime(CurrentTime);
				}

			}
			mMap.put(mLJTempFeeSet, "INSERT");
			mMap.put(mLJTempFeeClassSet, "INSERT");

			// 添加暂收据打印记录
			//getLOPrtManager(mLJTempFeeSet);

			if (!prepareOutputData())
			{
				return false;
			}
			if (!tPubSubmit.submitData(this.mInputData, ""))
			{
				if (tPubSubmit.mErrors.needDealError())
				{
					// @@错误处理
					buildError("insertData", "保存财务交费信息时出现错误!");
					return false;
				}
			}

			// 保全核销
			PEdorAutoConfirmBL tPEdorAutoConfirmBL = new PEdorAutoConfirmBL(globalInput);
			for (int i = 1; i <= mLJTempFeeSet.size(); i++)
			{
				if ("4".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					if (!tPEdorAutoConfirmBL.AutoConfirm(mLJTempFeeSet.get(i).getOtherNo()))
					{
						buildError("insertData", "保全核销时出现错误!");
					}
				}
			}
			mMap = null;
			tPubSubmit = null;

			return true;
		}
		catch (Exception ex)
		{
			buildError("dealData", "");
			return false;
		}
	}

	/**
	 * getLOPrtManager
	 * @param tLJAGetSchema LJAGetSchema
	 * @param tLCContSchema LCContSchema
	 * @return LOPRTManagerSchema
	 */
	private void getLOPrtManager(LJTempFeeSet mLJTempFeeSet)
	{
		String tLimit = "";
		String prtSeqNo = "";
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		// 3-准备打印数据,生成印刷流水号
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLimit = PubFun.getNoLimit(mLJTempFeeSet.get(1).getManageCom());
		prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.CODE_URGE_GC); //
		tLOPRTManagerSchema.setOtherNo(mLJTempFeeSet.get(1).getTempFeeNo()); // 暂收据号码
		// tLOPRTManagerSchema.setStandbyFlag1(mLJTempFeeSet.get(i).getOtherNo());
		// tLOPRTManagerSchema.setStandbyFlag2(mLJTempFeeSet.get(i).getOtherNoType());
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		tLOPRTManagerSchema.setManageCom(mLJTempFeeSet.get(1).getManageCom());
		tLOPRTManagerSchema.setAgentCode(mLJTempFeeSet.get(1).getAgentCode());
		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_URGE_GC);
		tLOPRTManagerSchema.setReqCom(globalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(globalInput.Operator);
		tLOPRTManagerSchema.setPrtType("0");
		tLOPRTManagerSchema.setStateFlag("0");
		tLOPRTManagerSchema.setOldPrtSeq(prtSeqNo);
		PrintManagerBL tPrintManagerBL = new PrintManagerBL();
		VData tempVData = new VData();
		tempVData.add(tLOPRTManagerSchema);
		tempVData.add(globalInput);
		if (tPrintManagerBL.submitData(tempVData, "REQ"))
		{ // 打印数据处理
			tempVData = tPrintManagerBL.getResult();
			tLOPRTManagerSchema = (LOPRTManagerSchema) tempVData.getObjectByObjectName("LOPRTManagerSchema", 0);
		}
		tLOPRTManagerSet.add(tLOPRTManagerSchema);
		mMap.put(tLOPRTManagerSet, "INSERT");
	}

	public String createWorkNo(String ags)
	{
		String tWorkNo = "";
		if (ags.equals("FINFEENO"))
		{
			tWorkNo = PubFun1.CreateMaxNo("FINFEENO", 10);
		}
		else
		{
			tWorkNo = PubFun1.CreateMaxNo("FSERIALNO", 10);
		}
		return tWorkNo;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		this.mLJTempFeeSet.set((LJTempFeeSet) cInputData.getObjectByObjectName("LJTempFeeSet", 0));
		this.mLJTempFeeClassSet.set((LJTempFeeClassSet) cInputData.getObjectByObjectName("LJTempFeeClassSet", 0));
		this.mLJAGetSchema = ((LJAGetSchema) cInputData.getObjectByObjectName("LJAGetSchema", 0));
		return true;
	}

	public VData getResult()
	{
		return mResult;
	}

	private void buildError(String szFunc, String szErrMsg)
	{
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
