package com.sinosoft.lis.workflowmanage;

import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;

public class LdwfMulLineBL {

	private static Logger logger = Logger.getLogger(LdwfMulLineBL.class);

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData CommitData = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
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

	public LdwfMulLineBL()
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
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mTransferData = ((TransferData) cInputData.getObjectByObjectName("TransferData", 0));
		this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{   
		String tFunctionID=(String)mTransferData.getValueByName("FunctionID");
		String tBusiNessTable=(String)mTransferData.getValueByName("BusiNessTable");
		String tFieldCode=(String)mTransferData.getValueByName("FieldCode");
		String tColWidth=(String)mTransferData.getValueByName("ColWidth");
		String tColSerialNo=(String)mTransferData.getValueByName("ColSerialNo");
		String tIsShow=(String)mTransferData.getValueByName("IsShow");
		if(tFunctionID == null || tFunctionID =="")
		{
			CError tError = new CError();
			tError.moduleName = this.getClass().getName();
			tError.functionName = "dealdata";
			tError.errorMessage = "传入功能名称为空";
			this.mErrors.addOneError(tError);
			return false;			
		}
		if(tBusiNessTable == null || tBusiNessTable =="")
		{
			
			CError tError = new CError();
			tError.moduleName = this.getClass().getName();
			tError.functionName = "dealdata";
			tError.errorMessage = "传入业务表为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if(tFieldCode == null || tFieldCode =="")
		{
			
			CError tError = new CError();
			tError.moduleName = this.getClass().getName();
			tError.functionName = "dealdata";
			tError.errorMessage = "传入的业务字段为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if(mOperate.compareToIgnoreCase("insert")==0)
		{
			LDWFMulLineSchema tLDWFMulineSchema=new LDWFMulLineSchema();
			tLDWFMulineSchema.setFunctionID(tFunctionID);
			tLDWFMulineSchema.setBusinessTable(tBusiNessTable);
			tLDWFMulineSchema.setFieldCode(tFieldCode);
			tLDWFMulineSchema.setColWidth(tColWidth);
			tLDWFMulineSchema.setColSerialno(tColSerialNo);
			tLDWFMulineSchema.setIsShow(tIsShow);
			map.put(tLDWFMulineSchema, "INSERT");		
		}
		if(mOperate.compareToIgnoreCase("update")==0 ||mOperate.compareToIgnoreCase("delete")==0)
		{   
			LDWFMulLineDB tLDWFMulLineDB= new LDWFMulLineDB();
			tLDWFMulLineDB.setFunctionID(tFunctionID);
			tLDWFMulLineDB.setBusinessTable(tBusiNessTable);
			tLDWFMulLineDB.setFieldCode(tFieldCode);
			if(!tLDWFMulLineDB.getInfo())
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "查询表LDWFMulLine出错!";
				this.mErrors.addOneError(tError);
				return false;				
			}
			LDWFMulLineSchema tudLDWFMulLineSchema = tLDWFMulLineDB.getSchema();
			if(mOperate.compareToIgnoreCase("update")==0)
			{
				tudLDWFMulLineSchema.setColWidth(tColWidth);
				tudLDWFMulLineSchema.setColSerialno(tColSerialNo);
				tudLDWFMulLineSchema.setIsShow(tIsShow);
				map.put(tudLDWFMulLineSchema, "UPDATE");
			}
			else
			{
				map.put(tudLDWFMulLineSchema, "DELETE");
			}
		}
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
}