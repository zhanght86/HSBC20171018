package com.sinosoft.lis.maintain;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LDUnifyCodeSchema;
import com.sinosoft.lis.schema.LDUnifyCodeTraceSchema;
import com.sinosoft.lis.schema.LDUnifyCodeTypeSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>Title：系统编码维护</p>
 * 
 * <p>Description：系统编码维护</p>
 * 
 * <p>Copyright：Copyright (c) 2012</p>
 * 
 * <p>Company: Sinosoft</p>
 * 
 * @author 刘锦祥

 * @version 6.5
 */
public class LDUnifyCodeTypeUI implements BusinessService {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private CErrors mErrors = new CErrors();
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 提交数据的容器 */
	private MMap mMMap = new MMap();
	/** 操作变量 */
	private String mOperate;
	
	/** 全局变量 */
	private GlobalInput mGlobalInput;
	
	private ExeSQL mExeSQL = new ExeSQL();
	private StringBuffer mStrBuff = new StringBuffer();
	
	/** 系统统一编码类型表 */
	private LDUnifyCodeTypeSchema mLDUnifyCodeTypeSchema = new LDUnifyCodeTypeSchema();
	/** 系统统一编码表 */
	private LDUnifyCodeSchema mLDUnifyCodeSchema = new LDUnifyCodeSchema();
	/** 系统统一编码轨迹表 */
	private LDUnifyCodeTraceSchema mLDUnifyCodeTraceSchema = new LDUnifyCodeTraceSchema();
	
	public boolean submitData(VData data, String Operater) {
		// TODO Auto-generated method stub
		
		if (!getInputData(data, Operater)) {
			return false;
		}
		
		if (!checkData()) {
			return false;
		}
		
		if (!dealData()) {
			return false;
		}
		
		if (!saveData()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 从输入数据中得到所有对象


	 * @param cInputData 传入数据对象
	 * @param cOperate 操作类型
	 * @return 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		
		mInputData = (VData)cInputData.clone();
		mOperate = cOperate;
		
		mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
		if (mGlobalInput==null) {
			buildError("getInputData", "传入信息为空！");
			return false;
		}
		
		mLDUnifyCodeTypeSchema = (LDUnifyCodeTypeSchema)mInputData.getObjectByObjectName("LDUnifyCodeTypeSchema", 0);
		mLDUnifyCodeSchema = (LDUnifyCodeSchema)mInputData.getObjectByObjectName("LDUnifyCodeSchema", 0);
		if(mOperate.endsWith("TYPE")) {
			if(mLDUnifyCodeTypeSchema==null) {
				buildError("getInputData", "传入的编码类型信息为空！");
				return false;
			}
		}else {
			if(mLDUnifyCodeSchema==null) {
				buildError("getInputData", "传入的编码信息为空！");
				return false;
			}
		}
		
		
		return true;
	}
	
	/**
	 * 数据校验
	 * @return 如果校验失败，则返回false,否则返回true
	 */
	private boolean checkData() {
		
		if(mOperate.endsWith("TYPE")) {
			String tSysCode = mLDUnifyCodeTypeSchema.getSysCode();
			String tgetCodeType = mLDUnifyCodeTypeSchema.getCodeType();
			if(tSysCode==null||"".equals(tSysCode)) {
				buildError("checkData", "传入的系统编码为空！");
				return false;
			}
			
			if(tgetCodeType==null||"".equals(tgetCodeType)) {
				buildError("checkData", "传入的编码类型为空！");
				return false;
			}
		}else {
			String tSysCode = mLDUnifyCodeSchema.getSysCode();
			String tCodeType = mLDUnifyCodeSchema.getCodeType();
			String tCode = mLDUnifyCodeSchema.getCode();
			if(tSysCode==null||"".equals(tSysCode)) {
				buildError("checkData", "传入的系统编码为空！");
				return false;
			}
			if(tCode==null||"".equals(tCode)) {
				buildError("checkData", "传入的编码为空！");
				return false;
			}
			if(tCodeType==null||"".equals(tCodeType)) {
				buildError("checkData", "传入的编码类型为空！");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 业务处理
	 * @return 如果处理失败，则返回false,否则返回true
	 */
	private boolean dealData() {
		
		LDUnifyCodeTypeBL tLDUnifyCodeTypeBL = new LDUnifyCodeTypeBL();
		if (!tLDUnifyCodeTypeBL.submitData(mInputData, mOperate)) {
			mErrors = tLDUnifyCodeTypeBL.getErrors();
			return false;
		} else {
			mResult = tLDUnifyCodeTypeBL.getResult();
		}
		
		return true;
	}
	
	/**
	 * 提交数据
	 * @return 如果提交数据失败，则返回false,否则返回true
	 */
	private boolean saveData() {

		return true;
	}
	

	/**
	 * 错误构造方法


	 * @param tFunctionName
	 * @param tErrorMessage
	 */
	private void buildError(String tFunctionName, String tErrorMessage) {
		
		CError tCError = new CError();
		tCError.moduleName = "LDUnifyCodeTypeUI";
		tCError.functionName = tFunctionName;
		tCError.errorMessage = tErrorMessage;
		mErrors.addOneError(tCError);
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}
}
