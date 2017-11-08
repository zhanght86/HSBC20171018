package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */


import com.sinosoft.lis.db.LRBOMItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LRBOMItemSchema;
import com.sinosoft.lis.vdb.LRBOMDBSet;
import com.sinosoft.lis.vschema.LRBOMItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class LDItemBL {
private static Logger logger = Logger.getLogger(LDItemBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	LRBOMItemSchema mLRBOMItemSchema = new LRBOMItemSchema();

	LRBOMItemDB tLRBOMItemDB;
	
	LRBOMItemSet tLRBOMItemSet;

	 /** 往后面传输的数据库操作 */
    private MMap map = new MMap();
//    TransferData mTransferData = new TransferData();
    /** 全局数据 */
    private GlobalInput mG = new GlobalInput();
    
	public LDItemBL() {
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
            CError.buildErr(this, "数据处理失败LDItemBL-->dealData!");			
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

	private boolean prepareOutputData() {
		try
        {
            //后台传送
        	mInputData.clear();
        	mInputData.add(map);
//            mResult.add(mTransferData);
        }catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, "在准备往后层处理所需要的数据时出错");
            return false;
        }
        return true;
	}

	private boolean deleteData() {
		map.put(mLRBOMItemSchema, "DELETE");
		return true;
	}

	private boolean updateData() {		
		map.put(mLRBOMItemSchema, "UPDATE");
		return true;
	}

	private boolean insertData(){
		map.put(mLRBOMItemSchema, "INSERT");
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
		// 检验查询条件
		mLRBOMItemSchema = (LRBOMItemSchema) cInputData.getObjectByObjectName(
				"LRBOMItemSchema", 0);
		if (mLRBOMItemSchema == null) {
			// @@错误处理              
            CError.buildErr(this, "接收的词条信息为空");
			return false;
		}		
		return true;
	}

	private boolean checkInputData(){			
		tLRBOMItemDB = new LRBOMItemDB();
		tLRBOMItemDB.setSchema(mLRBOMItemSchema);
		
		if(mOperate.equals("INSERT")){
			String insertSql = "select * from lrbomitem where name='?name?' and bomname='?bomname?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(insertSql);
			sqlbv.put("name", mLRBOMItemSchema.getName());
			sqlbv.put("bomname", mLRBOMItemSchema.getBOMName());
			tLRBOMItemSet = tLRBOMItemDB.executeQuery(sqlbv);
			if(tLRBOMItemSet.size()>0){
				 CError.buildErr(this, "准备添加的词条已存在");
				 return false;
			}
		}
		
		if (tLRBOMItemDB.mErrors.needDealError()||tLRBOMItemDB == null )
        {
			if(tLRBOMItemDB.getInfo()){
	            CError.buildErr(this, "查询词条数据失败!");
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
