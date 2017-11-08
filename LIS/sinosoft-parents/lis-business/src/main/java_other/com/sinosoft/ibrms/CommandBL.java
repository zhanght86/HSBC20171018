package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */



import com.sinosoft.lis.db.LRCommandDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LRCommandSchema;
import com.sinosoft.lis.vschema.LRCommandSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class CommandBL {
private static Logger logger = Logger.getLogger(CommandBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LRCommandSchema mLRCommandSchema = new LRCommandSchema();
	
	private LRCommandSet mLRCommandSet ;

	private LRCommandDB tLRCommandDB;

    /** 往后面传输的数据库操作 */
    private MMap map = new MMap();
//    TransferData mTransferData = new TransferData();
    /** 全局数据 */
//    private GlobalInput mG = new GlobalInput();
	
	private String mName = "";

	public CommandBL() {
		// just for debug
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("cOperate::" + cOperate);
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
	 //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
		// 进行业务处理		
		if (!dealDate()) {			
            CError.buildErr(this, "数据处理失败CommandBL-->dealData!");
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		//数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {        	
        	CError.buildErr(this, "数据提交失败!");
            return false;
        }
        mInputData = null;
		return true;
	}

	 private boolean prepareOutputData(){
        try
        {
            //后台传送        	
        	mInputData.clear();
        	mInputData.add(map);
        	//mResult.add(mTransferData);
        }catch (Exception ex)
        {
            // @@错误处理            
            CError.buildErr(this, "在准备往后层处理所需要的数据时出错");            
            return false;
        }
        return true;
    }

	private boolean deleteData() {
		map.put(mLRCommandSchema, "DELETE");
		return true;
	}

	private boolean updateData() {		
		map.put(mLRCommandSchema, "UPDATE");
		return true;
	}

	private boolean insertData(){	
		map.put(mLRCommandSchema, "INSERT");
		return true;
	}
	
	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		
/*	    mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); 
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);
*/		
		mLRCommandSchema = (LRCommandSchema) cInputData.getObjectByObjectName("LRCommandSchema", 0);
		mName = mLRCommandSchema.getName();
		if (mLRCommandSchema == null) {
			// @@错误处理              
            CError.buildErr(this, "接受的运算符信息为空");
			return false;
		}
		return true;
	}

	private boolean checkInputData(){
		// 检验添加的信息是否存在，返回提示信息给jsp
		tLRCommandDB = new LRCommandDB();
		tLRCommandDB.setSchema(mLRCommandSchema);
		if(mOperate.equals("INSERT")){
			String insertSql = "select * from lrcommand where name='?name?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(insertSql);
			sqlbv.put("name", mLRCommandSchema.getName());
			mLRCommandSet = tLRCommandDB.executeQuery(sqlbv) ;
			if(mLRCommandSet.size()>0){
				 CError.buildErr(this, "准备添加的运算符已存在!");
				 return false;
			}
		}
		
			if (tLRCommandDB.mErrors.needDealError()||tLRCommandDB == null )
	        {
				if(!tLRCommandDB.getInfo()){					
					CError.buildErr(this, "查询运算符数据失败!运算符名字：" + mName);
					return false;
				}
	        }				
		 return true;
	 }
	/**
	 * 查询符合条件的信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealDate() {
		logger.debug("Entering.........dealDate");
		if (mOperate.equals("UPDATE")) {
			updateData();
		}
		if (mOperate.equals("DELETE")) {
			deleteData();
		}
		if (mOperate.equals("INSERT")) {
			insertData();
		}
		return true;
	}
}
