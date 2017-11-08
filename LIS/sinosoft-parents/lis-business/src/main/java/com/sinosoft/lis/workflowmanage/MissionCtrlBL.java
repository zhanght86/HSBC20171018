/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */
package com.sinosoft.lis.workflowmanage;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;

public class MissionCtrlBL
{
private static Logger logger = Logger.getLogger(MissionCtrlBL.class);

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData CommitData = new VData();
	/**
	 * 往后面传输数据的容器
	 */
	private Reflections mReflections = new Reflections();
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	/** 全局数据 */
	// private GlobalInput mGlobalInput =new GlobalInput() ;
	/**
	 * 数据操作字符串
	 */
	private String mOperate;

	public MissionCtrlBL()
	{
	}

	public static void main(String[] args)
	{
	}

	/**
	 * 传输数据的公共方法
	 *
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "COMBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败COMBL-->dealData!";
			this.mErrors.addOneError(tError);
			logger.debug("E-----------------fail");
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(CommitData, ""))
		{
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		if ("MODIFY".equalsIgnoreCase(this.mOperate))
		{
			String UserCode = String.valueOf(mTransferData.getValueByName("UserCode"));
			String MissionID = String.valueOf(mTransferData.getValueByName("MissionID"));
			String SubMissionID = String.valueOf(mTransferData.getValueByName("SubMissionID"));
			String ActivityID = String.valueOf(mTransferData.getValueByName("ActivityID"));
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setMissionID(MissionID);
			tLWMissionDB.setSubMissionID(SubMissionID);
			tLWMissionDB.setActivityID(ActivityID);
			if (tLWMissionDB.query().size() == 0)
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "query data from database fail!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LWMissionSchema tLWMissionSchema = tLWMissionDB.query().get(1);
			if (tLWMissionSchema == null)
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "query data from database fail!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LBMissionSchema tLBMissionSchema = new LBMissionSchema();
			mReflections.transFields(tLBMissionSchema, tLWMissionSchema);
            tLWMissionSchema.setDefaultOperator(UserCode);
            tLWMissionSchema.setLastOperator(mGlobalInput.Operator);
            tLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
            tLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
            tLBMissionSchema.setSerialNo(tSerielNo);
            tLBMissionSchema.setActivityStatus("5"); //再分配
            tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
            tLBMissionSchema.setModifyDate(PubFun.getCurrentDate());
            tLBMissionSchema.setModifyTime(PubFun.getCurrentTime());
            tLBMissionSchema.setOutDate(PubFun.getCurrentDate());
            tLBMissionSchema.setOutTime(PubFun.getCurrentTime());
			map.put(tLWMissionSchema,"UPDATE");
			map.put(tLBMissionSchema,"INSERT");
//			String num = new ExeSQL().getOneValue("select COUNT(MissionID) from LWMission where MissionID='" + MissionID + "' and SubMissionID='" + SubMissionID + "' and ActivityID='" + ActivityID + "'");
//			if ("0".equalsIgnoreCase(num))
//			{
//				CError tError = new CError();
//				tError.moduleName = this.getClass().getName();
//				tError.functionName = "dealdata";
//				tError.errorMessage = "query data from database fail!";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//			map.put("update LWMission set DefaultOperator='"+UserCode+"' where MissionID='" + MissionID + "' and SubMissionID='" + SubMissionID + "' and ActivityID='" + ActivityID + "'","UPDATE");
		}
		else
		{
			CError tError = new CError();
			tError.moduleName = this.getClass().getName();
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mTransferData = ((TransferData) cInputData.getObjectByObjectName("TransferData", 0));
		this.mGlobalInput = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0);
		return true;
	}

	private boolean prepareOutputData()
	{
		try
		{
			mResult.clear();
			CommitData.clear();
			CommitData.add(map);
		}
		catch (Exception ex)
		{
			// @@错误处理
			ex.printStackTrace();
			CError tError = new CError();
			tError.moduleName = this.getClass().getName();
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		return this.mResult;
	}
}
