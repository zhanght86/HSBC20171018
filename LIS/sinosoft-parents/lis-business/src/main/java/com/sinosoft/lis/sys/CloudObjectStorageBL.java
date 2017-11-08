package com.sinosoft.lis.sys;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.vschema.LDSysVarSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>表示云对象存储的设置的业务逻辑</p>
 * @author Wang Zhang
 *
 */
public class CloudObjectStorageBL implements com.sinosoft.service.BusinessService{
	public CErrors mErrors = new CErrors();
    private static final String SYSVAR = "CloudObjectStorage";
	private TransferData mTransferData = null;
	private String sysvartype = null;
	private String sysvarvalue = null;
	private VData mResult = new VData();
	
	/**
	 * 从输入的vData中获取信息
	 * @param vData 表示一个一维向量，当中应当存储有一个TransferData对象
	 * @param operator 表示SELECT或UPDATE操作
	 * @return 成功获取到数据则返回 true, 否则返回 false.
	 */
	private boolean getData(VData vData, String operator){
		if(operator == null || operator.isEmpty() || operator.equalsIgnoreCase("SELECT"))
			return true;
		Object inputData = vData.getObjectByObjectName("TransferData", 0);
		if(inputData == null || !(inputData instanceof TransferData)){
		    CError.buildErr(this, "无法从所传数据中找到类型为TransferData的对象");
		    return false;
		}
		this.mTransferData = (TransferData)inputData;
		Object inputSysvartype = this.mTransferData.getValueByName("CloudObjectStorage");
		Object inputSysvarvalue = this.mTransferData.getValueByName("CosCloudSwitch");
		if(inputSysvartype == null || inputSysvarvalue == null){
			CError.buildErr(this, "无法从所传数据中找到所需数据。");
		    return false;
		}
		this.sysvartype = inputSysvartype.toString();
		this.sysvarvalue = inputSysvarvalue.toString();
		return true;
	}
	
	/**
	 * 获取一个 LDSysVar的Shema, 其中的 SYSVAR = "CloudObjectStorage"
	 * @return 获取到数据则返回一个 LDSysVarSchema 实例，否则返回 <span style="color:#7F0055;font-weight:bold">null</span>
	 */
	private LDSysVarSchema getSchema(){
		LDSysVarSchema lDSysVarSchema = null;
		LDSysVarDB lDSysVarDB = new LDSysVarDB();
		lDSysVarDB.setSysVar(SYSVAR);
		LDSysVarSet lDSysVarSet = lDSysVarDB.query();
		if(lDSysVarSet == null || lDSysVarSet.size() == 0){
			return null;
		}
		for(int i = 1; i <= lDSysVarSet.size(); i++){
			lDSysVarSchema = lDSysVarSet.get(i);
			if(lDSysVarSchema != null){
				break;
			}
		}
		return lDSysVarSchema;
	}
	
	/**
	 * 根据操作符进行查询或更新操作 (在尝试更新时若数据不存在则尝试插入新数据).
	 * @param operator <span>表示所要进行的操作 SELECT 或 UPDATE</span>
	 * @return 操作成功则返回true, 否则返回false
	 */
	@SuppressWarnings("unchecked")
	private boolean dealData(String operator){
		LDSysVarDB lDSysVarDB = null;
		if(operator == null || operator.isEmpty() || operator.equalsIgnoreCase("SELECT")){			
			try{
				LDSysVarSchema lDSysVarSchema = getSchema();
				if(lDSysVarSchema == null)
					throw new Exception("无法在LDSysVar中找到" + SYSVAR);
				this.mResult.add(lDSysVarSchema);
				return true;
			}catch(Exception e){
				CError.buildErr(this, e.getMessage());
				return false;
			}
		} else if (operator.equalsIgnoreCase("UPDATE")) {
			LDSysVarSchema lDSysVarSchema = getSchema();
			if(lDSysVarSchema == null){
				////
				// 数据不存在，尝试插入新数据
				////
				lDSysVarDB = new LDSysVarDB();
				lDSysVarDB.setSysVar(SYSVAR);
				lDSysVarDB.setSysVarType(this.sysvartype);
			    lDSysVarDB.setSysVarValue(this.sysvarvalue);
			    try{
			    	if(!lDSysVarDB.insert())
			    	    throw new Exception("更新数据时出现错误。");
			    	this.mResult.add("Succ");
			    	return true;
			    }catch(Exception e){
			    	CError.buildErr(this, e.getMessage());
				    return false;
			    }
			} else {
				////
				// 更新现有数据
				////
			    lDSysVarDB = new LDSysVarDB();
			    lDSysVarDB.setSysVar(SYSVAR);
			    lDSysVarDB.setSysVarType(this.sysvartype);
			    lDSysVarDB.setSysVarValue(this.sysvarvalue);
			    try{
				    if(!lDSysVarDB.update())
					    throw new Exception("更新数据时出现错误。");
				    this.mResult.add("Succ");
				    return true;
			    }catch(Exception e){
				    CError.buildErr(this, e.getMessage());
				    return false;
			    }
			}
		} else {
			CError.buildErr(this, "出现未知操作符 " + operator);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean submitData(VData vData, String Operater) {
		// TODO Auto-generated method stub
		if(getData(vData, Operater) && dealData(Operater)){
			return true;
		}else{
			mResult.add("Fail");
			return false;
		}
	}

	@Override
	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

	@Override
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
