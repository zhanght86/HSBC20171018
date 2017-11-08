/*
 * <p>ClassName: CodeBL </p>
 * <p>Description: CodeBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-11-04
 */
package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vdb.FICodeTransDBSet;
import com.sinosoft.lis.vschema.FICodeTransSet;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FICodeTransSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.*;

public class CodeBL
{
private static Logger logger = Logger.getLogger(CodeBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	// private GlobalInput mGlobalInput =new GlobalInput() ;
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private FICodeTransSet mFICodeTransSet = new FICodeTransSet();

	/** 业务处理相关变量 */
	private FICodeTransSchema mFICodeTransSchema = new FICodeTransSchema();
	
	private MMap mMMap = new MMap();

	// private FICodeTransSet mFICodeTransSet=new FICodeTransSet();
	public CodeBL()
	{}

	public static void main(String[] args)
	{}

	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		// 进行业务处理
		// 进行插入数据
		if (mOperate.equals("INSERT||CODE"))
		{
			if (!dealData())
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "CodeBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败CodeBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		// 对数据进行修改操作
		if (mOperate.equals("UPDATE||CODE"))
		{
			if (!updateData())
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "CodeBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败CodeBL-->updateData!";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("---updateData---");
		}
		// 对数据进行删除操作
		if (mOperate.equals("DELETE||CODE"))
		{
			if (!deleteData())
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "CodeBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败CodeBL-->deleteData!";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("----deleteData---");
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
//		if (this.mOperate.equals("QUERY||MAIN"))
//		{
//			this.submitquery();
//		}
//		else
//		{
//			logger.debug("Start CodeBL Submit...");
//			CodeBLS tCodeBLS = new CodeBLS();
//			tCodeBLS.submitData(mInputData, mOperate);
//			logger.debug("End CodeBL Submit...");
//			// 如果有需要处理的错误，则返回
//			if (tCodeBLS.mErrors.needDealError())
//			{
//				// @@错误处理
//				this.mErrors.copyAllErrors(tCodeBLS.mErrors);
//				CError tError = new CError();
//				tError.moduleName = "CodeBL";
//				tError.functionName = "submitDat";
//				tError.errorMessage = "数据提交失败!";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
//		}
//		mInputData = null;
		if (!pubSubmit())
		{
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		for (int i=1;i<=mFICodeTransSet.size();i++)
		{
		   FICodeTransSchema tFICodeTransSchema = new FICodeTransSchema();	
		   mFICodeTransSchema.setSchema(mFICodeTransSet.get(i));
		   mMMap.put(mFICodeTransSchema, "INSERT");
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean updateData()
	{
		
		for (int i=1;i<=mFICodeTransSet.size();i++)
		{
		   FICodeTransSchema tFICodeTransSchema = new FICodeTransSchema();	
		   mFICodeTransSchema.setSchema(mFICodeTransSet.get(i));
		   mMMap.put(mFICodeTransSchema, "UPDATE");
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean deleteData()
	{
		// FICodeTransSet tFICodeTransSet = new FICodeTransSet();
		// tFICodeTransSchema.setCodeType(mFICodeTransSchema.getCodeType());
		// tFICodeTransSchema.setCode(mFICodeTransSchema.getCode());
		// mFICodeTransSet.set(tFICodeTransSet);
		for (int i=1;i<=mFICodeTransSet.size();i++)
		{
		   FICodeTransSchema tFICodeTransSchema = new FICodeTransSchema();	
		   mFICodeTransSchema.setSchema(mFICodeTransSet.get(i));
		   mMMap.put(mFICodeTransSchema, "DELETE");
		}
		return true;
	}
 
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mFICodeTransSet.set((FICodeTransSet) cInputData.getObjectByObjectName("FICodeTransSet", 0));
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		if (this.mFICodeTransSet == null)
		{
			CError tError = new CError();
			tError.moduleName = "CODEBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "mFICodeTransSet为空！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery()
	{
		this.mResult.clear();
		logger.debug("Start CodeBLQuery Submit...");
		FICodeTransDBSet tFICodeTransDBSet = new FICodeTransDBSet();
		tFICodeTransDBSet.set(this.mFICodeTransSet);
		// this.mFICodeTransSet=tFICodeTransDB.query();
		// this.mResult.add(this.mFICodeTransSet);
		logger.debug("End CodeBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tFICodeTransDBSet.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFICodeTransDBSet.mErrors);
			CError tError = new CError();
			tError.moduleName = "CodeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData()
	{
		try
		{
			// this.mInputData=new VData();
			// this.mInputData.add(this.mGlobalInput);
//			this.mInputData.clear();
//			this.mInputData.add(this.mFICodeTransSet);
//			mResult.clear();
//			mResult.add(this.mFICodeTransSet);
			this.mInputData.add(mMMap);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CodeBL";
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
	/**
	 * 提交数据
	 * @return
	 */
	private boolean pubSubmit()
	{
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, ""))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "CodeBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败CodeBL-->pubSubmit!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}
}
