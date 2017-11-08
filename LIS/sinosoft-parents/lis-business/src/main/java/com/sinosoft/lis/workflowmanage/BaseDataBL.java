/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */
package com.sinosoft.lis.workflowmanage;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;

public class BaseDataBL
{
private static Logger logger = Logger.getLogger(BaseDataBL.class);

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData CommitData = new VData();
	/**
	 * 往后面传输数据的容器
	 */
	private TransferData mTransferData = new TransferData();
	private MMap map = new MMap();
	/** 全局数据 */
	// private GlobalInput mGlobalInput =new GlobalInput() ;
	/**
	 * 数据操作字符串
	 */
	private String mOperate;

	public BaseDataBL()
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
		if ("INSERT".equalsIgnoreCase(this.mOperate))
		{
			String CodeType = String.valueOf(mTransferData.getValueByName("CodeType"));
			String CodeTypeName = String.valueOf(mTransferData.getValueByName("CodeTypeName"));
			String Code = String.valueOf(mTransferData.getValueByName("Code"));
			String CodeName = String.valueOf(mTransferData.getValueByName("CodeName"));
			String CodeAlias = String.valueOf(mTransferData.getValueByName("CodeAlias"));
			//add by liuyuxiao 2011-05-23
			String OtherSignCode = String.valueOf(mTransferData.getValueByName("OtherSignCode"));
			if (!(DataCheck.CheckNull(CodeType) && DataCheck.CheckNull(CodeTypeName) && DataCheck.CheckNull(Code) && DataCheck.CheckNull(CodeName)))
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealData";
				tError.errorMessage = "从前台获取的数据有误,请检查!";
				this.mErrors.addOneError(tError);
				return false;
			}
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("select COUNT(Code) from LDCode where CodeType='?CodeType?' and Code='?Code?'");
			sqlbv.put("CodeType", CodeType);
			sqlbv.put("Code", Code);
			String num = tExeSQL.getOneValue(sqlbv);
			if ("1".equalsIgnoreCase(num))
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealData";
				tError.errorMessage = "数据库已经存在该记录,请选择修改或删除操作!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LDCodeSchema tLDCodeSchema = new LDCodeSchema();
			tLDCodeSchema.setCodeType(CodeType);
			tLDCodeSchema.setCode(Code);
			tLDCodeSchema.setCodeName(CodeName);
			tLDCodeSchema.setCodeAlias(CodeAlias);
			tLDCodeSchema.setOtherSign(OtherSignCode);
			map.put(tLDCodeSchema, "INSERT");
		}
		else if ("MODIFY".equalsIgnoreCase(this.mOperate))
		{
			String CodeType = String.valueOf(mTransferData.getValueByName("CodeType"));
			String CodeTypeName = String.valueOf(mTransferData.getValueByName("CodeTypeName"));
			String Code = String.valueOf(mTransferData.getValueByName("Code"));
			String CodeName = String.valueOf(mTransferData.getValueByName("CodeName"));
			String CodeAlias = String.valueOf(mTransferData.getValueByName("CodeAlias"));
			//add by liuyuxiao 2011-05-23
			String OtherSignCode = String.valueOf(mTransferData.getValueByName("OtherSignCode"));
			if (!(DataCheck.CheckNull(CodeType) && DataCheck.CheckNull(CodeTypeName) && DataCheck.CheckNull(Code) && DataCheck.CheckNull(CodeName)))
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealData";
				tError.errorMessage = "从前台获取的数据有误,请检查!";
				this.mErrors.addOneError(tError);
				return false;
			}
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("select COUNT(Code) from LDCode where CodeType='?CodeType?' and Code='?Code?'");
			sqlbv1.put("CodeType", CodeType);
			sqlbv1.put("Code", Code);
			String num = tExeSQL.getOneValue(sqlbv1);
			if ("0".equalsIgnoreCase(num))
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealData";
				tError.errorMessage = "数据库不存在该记录!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LDCodeSchema tLDCodeSchema = new LDCodeSchema();
			tLDCodeSchema.setCodeType(CodeType);
			tLDCodeSchema.setCode(Code);
			tLDCodeSchema.setCodeName(CodeName);
			tLDCodeSchema.setCodeAlias(CodeAlias);
			tLDCodeSchema.setOtherSign(OtherSignCode);
			map.put(tLDCodeSchema, "UPDATE");
		}
		else if ("DELETE".equalsIgnoreCase(this.mOperate))
		{
			String CodeType = String.valueOf(mTransferData.getValueByName("CodeType"));
			String CodeTypeName = String.valueOf(mTransferData.getValueByName("CodeTypeName"));
			String Code = String.valueOf(mTransferData.getValueByName("Code"));
			String CodeName = String.valueOf(mTransferData.getValueByName("CodeName"));
			if (!(DataCheck.CheckNull(CodeType) && DataCheck.CheckNull(CodeTypeName) && DataCheck.CheckNull(Code) && DataCheck.CheckNull(CodeName)))
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealData";
				tError.errorMessage = "从前台获取的数据有误,请检查!";
				this.mErrors.addOneError(tError);
				return false;
			}
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql("select COUNT(Code) from LDCode where CodeType='?CodeType?' and Code='?Code?'");
			sqlbv2.put("CodeType", CodeType);
			sqlbv2.put("Code", Code);
			String num = tExeSQL.getOneValue(sqlbv2);
			if ("0".equalsIgnoreCase(num))
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealData";
				tError.errorMessage = "数据库不存在该记录!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LDCodeSchema tLDCodeSchema = new LDCodeSchema();
			tLDCodeSchema.setCodeType(CodeType);
			tLDCodeSchema.setCode(Code);
			tLDCodeSchema.setCodeName(CodeName);
			map.put(tLDCodeSchema, "DELETE");
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
