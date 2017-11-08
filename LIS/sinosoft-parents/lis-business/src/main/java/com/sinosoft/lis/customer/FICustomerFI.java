package com.sinosoft.lis.customer;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.finfee.LJAGetQueryUI;
import com.sinosoft.lis.finfee.OperFinFeeGetBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FICustomerFI extends FICustomer
{
private static Logger logger = Logger.getLogger(FICustomerFI.class);


	private TransferData mTransferData = new TransferData();

	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();

	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();

	private GlobalInput globalInput = new GlobalInput();

	private String mNbflag = "";

	protected boolean getInputData(VData cInputData)
	{
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		this.globalInput = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mLJTempFeeSet.set((LJTempFeeSet) cInputData.getObjectByObjectName("LJTempFeeSet", 0));
		this.mLJTempFeeClassSet.set((LJTempFeeClassSet) cInputData.getObjectByObjectName("LJTempFeeClassSet", 0));
		this.mLJAGetSchema = ((LJAGetSchema) cInputData.getObjectByObjectName("LJAGetSchema", 0));

		return true;
	}

	protected boolean dealData()
	{
		try
		{
			String tHXFlag = "0";
			
			for (int i = 1; i <= mLJTempFeeSet.size(); i++)
			{
				String aAccType = "0";// 普通账户
				String aContNo = "";
				String aOperationNo = "";
				String aOperationType = "";
				String aPayMode = "";
				String aOperType = "1";// 收费
				String aOtherNo = "";
				String aDCFlag = "D";// 借
				String aCurrency = "";
				double aMoney = 0.0;
				String aState = "00";// 未使用
				String ConfFlag = "0";// 未核销
				if (mLJTempFeeSet.get(i).getEnterAccDate() == null)
				{
					return true;
				}
				String EnteraccFalg = "1";// 已到帐

				for (int j = 1; j <= mLJTempFeeClassSet.size(); j++)
				{
					if (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())
							&& mLJTempFeeSet.get(i).getOtherNo().equals(mLJTempFeeClassSet.get(j).getOtherNo()))
					{
						// 传入的交费方式5 为内部转账，即业务应付转入
						if (mLJTempFeeClassSet.get(j).getPayMode().equals("5"))
						{
							// mLJTempFeeSet.get(i).setTempFeeType("8");
							aOperType = "2";
							if (mLJAGetSchema == null || mLJAGetSchema == null)
							{
								continue;
							}
							else
							{
								aOtherNo = mLJAGetSchema.getActuGetNo();
							}

							if (j == 1)
							{
								// 业务应付转入到帐确认
								LJFIGetSchema tLJFIGetSchema; // 财务给付表
								LJAGetSchema tLJAGetSchema; // 实付总表
								LJAGetSet tLJAGetSet;

								// 准备传输数据 VData
								VData tVData = new VData();
								tLJAGetSchema = new LJAGetSchema();
								tLJAGetSchema.setActuGetNo(aOtherNo);
								tVData.clear();
								tVData.add(tLJAGetSchema);
								tVData.add(globalInput);
								String CurrentDate = PubFun.getCurrentDate();

								LJAGetQueryUI tLJAGetQueryUI = new LJAGetQueryUI();
								if (!tLJAGetQueryUI.submitData(tVData, "QUERY"))
								{
									return false;
								}
								else
								{
									tVData.clear();
									tLJAGetSet = new LJAGetSet();
									tLJAGetSchema = new LJAGetSchema();
									tVData = tLJAGetQueryUI.getResult();
									tLJAGetSet.set((LJAGetSet) tVData.getObjectByObjectName("LJAGetSet", 0));
									tLJAGetSchema = (LJAGetSchema) tLJAGetSet.get(1);
									// 置核销日期为当天
									tLJAGetSchema.setEnterAccDate(CurrentDate);
									tLJAGetSchema.setConfDate(CurrentDate);
									// 2-构造财务给付表的新纪录
									tLJFIGetSchema = new LJFIGetSchema();

									tLJFIGetSchema.setEnterAccDate(CurrentDate);
									tLJFIGetSchema.setConfDate(CurrentDate);
									tLJFIGetSchema.setActuGetNo(aOtherNo);
									tLJFIGetSchema.setPayMode("5");
									tLJFIGetSchema.setOtherNo(tLJAGetSchema.getOtherNo());
									tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
									tLJFIGetSchema.setGetMoney(tLJAGetSchema.getSumGetMoney());
									tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
									tLJFIGetSchema.setSaleChnl(tLJAGetSchema.getSaleChnl());
									tLJFIGetSchema.setAgentGroup(tLJAGetSchema.getAgentGroup());
									tLJFIGetSchema.setAgentCode(tLJAGetSchema.getAgentCode());
									tLJFIGetSchema.setSerialNo(tLJAGetSchema.getSerialNo());
									tLJFIGetSchema.setOperator(globalInput.Operator);
									tLJFIGetSchema.setCurrency(tLJAGetSchema.getCurrency());
									// 入机时间等在BL层设置
									// 3-事务操作 插入纪录到财务给付表 更新实付总表

									tVData.clear();
									tVData.add(tLJFIGetSchema);
									tVData.add(tLJAGetSchema);
									tVData.add(globalInput);
									OperFinFeeGetBL tOperFinFeeGetBL = new OperFinFeeGetBL();
									if (tOperFinFeeGetBL.submitData(tVData, "VERIFY"))
									{
										super.mMap.add((MMap) (tOperFinFeeGetBL.getResult().getObjectByObjectName("MMap", 0)));
									}
									else
									{
										return false;
									}

								}
							}
						}
					}
				}

				// 根据不同的业务类型获取合同号
				// 新单交费
				if ("1".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					LCContDB tLCContDB = new LCContDB();
					tLCContDB.setPrtNo(mLJTempFeeSet.get(i).getOtherNo());
					LCContSet tLCContSet = tLCContDB.query();

					if (tLCContSet == null || tLCContSet.size() < 1)
					{
						continue;
					}
					else
					{
						LCContSchema tLCContSchema = tLCContSet.get(1);
						if (mTransferData != null)
						{
							mNbflag = String.valueOf(mTransferData.getValueByName("Nbflag"));
						}
						if (mNbflag.equals("1"))
						{
							// 新契约新单复核会调用多次，造成客户账户记录多次，在此修复
							aContNo = tLCContSchema.getContNo();
							String sql = "select 1 from lbmission a  where a.MISSIONPROP2= '" + "?aContNo?"
//									+ "' and activityid='0000001001'";
									+ "' and activityid  in (select activityid from lwactivity  where functionid ='10010003')";
							SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
							sqlbv1.sql(sql);
							sqlbv1.put("aContNo",aContNo);
							ExeSQL tExeSQL = new ExeSQL();
							String result = tExeSQL.getOneValue(sqlbv1);
							if (result.equals("1"))
							{
								continue;
							}
						}
						else
						{
							if (tLCContSchema.getApproveDate() != null)
							{
								aContNo = tLCContSchema.getContNo();
							}
							else
							{
								continue;
							}
						}
					}
					aOperationType = "1";
				}
				// 续期催收交费//预交续期保费
				if ("2".equals(mLJTempFeeSet.get(i).getTempFeeType())
						|| "3".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					LCContDB tLCContDB = new LCContDB();
					tLCContDB.setContNo(mLJTempFeeSet.get(i).getOtherNo());
					LCContSet tLCContSet = tLCContDB.query();
					LCContSchema tLCContSchema = tLCContSet.get(1);
					if (tLCContSet == null || tLCContSet.size() < 1)
					{
						continue;
					}
					else
					{
						aContNo = tLCContSchema.getContNo();
					}
					aOperationType = "2";
				}
				// 保全交费
				if ("4".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
					tLPEdorItemDB.setEdorAcceptNo(mLJTempFeeSet.get(i).getOtherNo());
					LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
					LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(1);
					if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1)
					{
						continue;
					}
					else
					{
						aContNo = tLPEdorItemSchema.getContNo();
					}
					aOperationType = "3";
				}
				// 理赔收费
				if ("6".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					aOperationType = "4";
					//获取合同号
					//理赔加费
					ExeSQL tExeSQL = new ExeSQL();
					if(mLJTempFeeSet.get(i).getTempFeeNoType().equals("1"))
					{
						String tSQL = "select distinct contno from ljspayperson where getnoticeno='"+"?getnoticeno?"+"'";
						SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						sqlbv2.sql(tSQL);
						sqlbv2.put("getnoticeno",mLJTempFeeSet.get(i).getTempFeeNo());
						aContNo = tExeSQL.getOneValue(sqlbv2);
					}
					//理赔回退收费
					else
					{
						//理赔回退收费不做客户账户管理
						return true;
					}
					
				}
				//红利抵交续期收费,增额缴清处理
				if ("7".equals(mLJTempFeeSet.get(i).getTempFeeType()))
				{
					aOperationType = "2";//给续期使用
					aOperType = "2"; //应付业务转入
					aContNo = mLJTempFeeClassSet.get(1).getOtherNo();
					if(this.mTransferData!=null)
					{
						tHXFlag = this.mTransferData.getValueByName("HXFlag")==null?"0":(String)this.mTransferData.getValueByName("HXFlag");
						if(tHXFlag.equals("1"))
						{
							ConfFlag =  "1";
							aState = "01";//已核销
						}
					}
				}

				for (int j = 1; j <= mLJTempFeeClassSet.size(); j++)
				{
					if (mLJTempFeeSet.get(i).getTempFeeNo().equals(mLJTempFeeClassSet.get(j).getTempFeeNo())
							&& mLJTempFeeSet.get(i).getOtherNo().equals(mLJTempFeeClassSet.get(j).getOtherNo())
							&&mLJTempFeeSet.get(i).getCurrency().equals(mLJTempFeeClassSet.get(j).getCurrency()))
					{
						aOperationNo = mLJTempFeeClassSet.get(j).getOtherNo();
						aPayMode = mLJTempFeeClassSet.get(j).getPayMode();
						aOtherNo = mLJTempFeeClassSet.get(j).getTempFeeNo();
						aCurrency = mLJTempFeeClassSet.get(j).getCurrency();
						aMoney = mLJTempFeeClassSet.get(j).getPayMoney();
						
						dealAccount(aAccType, aContNo, aOperationNo, aOperationType, aPayMode, aOperType, aOtherNo, aDCFlag, aCurrency, aMoney, aState, ConfFlag, EnteraccFalg);
						if(tHXFlag.equals("1"))
						{
							aOperType = "4"; //业务使用
							aDCFlag = "C";//贷
							dealAccount(aAccType, aContNo, aOperationNo, aOperationType, aPayMode, aOperType, aOtherNo, aDCFlag, aCurrency, aMoney, aState, ConfFlag, EnteraccFalg);
						}
					}
				}
			}
		}
		catch (Exception ex)
		{
			buildError("FICustomerFI", "dealdata", "");
			return false;
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
	// TODO 自动生成方法存根

	}

}
