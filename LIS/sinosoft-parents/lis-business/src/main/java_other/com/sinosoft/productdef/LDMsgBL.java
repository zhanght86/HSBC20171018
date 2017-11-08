

package com.sinosoft.productdef;

/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDMsgInfoSchema;
import com.sinosoft.lis.schema.LDMsgInfo_BOMSchema;
import com.sinosoft.lis.schema.LDMsgInfo_MsgSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDMsgBL {

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LDMsgInfoSchema mLDMsgInfoSchema = new LDMsgInfoSchema();

	
	private String mAction = "";
    /** 往后面传输的数据库操作 */
    private MMap map = new MMap();

	public LDMsgBL() {
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
		System.out.println("cOperate::" + cOperate);
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
		if (!dealData()) {			
            CError.buildErr(this, "数据处理失败!");
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
        	//System.out.println("BOM::prepareOutputData");
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
	try {
		this.mAction = (String)mInputData.getObjectByObjectName("String", 0);
		if(mAction!=null&&!mAction.equals(""))
		{
			if(mAction.toUpperCase().equals("SAVE"))
			{
				mAction = "DELETE&INSERT";
			}
			else
			{
				mAction = "DELETE";
			}
		}
		//if(this.mOperate.toUpperCase().equals("MSG"))
		{
			mLDMsgInfoSchema = (LDMsgInfoSchema)mInputData.getObjectByObjectName("LDMsgInfoSchema", 0);
		}
		
	} catch (RuntimeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return true;
	}

	private boolean checkInputData(){
		if(this.mOperate.toUpperCase().equals("MSG"))
		{
			if(mLDMsgInfoSchema.getKeyID()==null||mLDMsgInfoSchema.getKeyID().equals("")
			||mLDMsgInfoSchema.getMsg()==null||mLDMsgInfoSchema.getMsg().equals("")
			||mLDMsgInfoSchema.getLanguage()==null||mLDMsgInfoSchema.getLanguage().equals(""))
			{
				CError.buildErr(this, "录入信息不全!");
				return false;
			}
		}
//		else
//		{
//			if(this.mLDMsgInfo_BOMSchema.getKeyID()==null||mLDMsgInfo_BOMSchema.getKeyID().equals("")
//					||mLDMsgInfo_BOMSchema.getMsg()==null||mLDMsgInfo_BOMSchema.getMsg().equals("")
//					||mLDMsgInfo_BOMSchema.getLanguage()==null||mLDMsgInfo_BOMSchema.getLanguage().equals("")
//					||mLDMsgInfo_BOMSchema.getMsgType()==null||mLDMsgInfo_BOMSchema.getMsgType().equals(""))
//					{
//						CError.buildErr(this, "录入信息不全!");
//						return false;
//					}
//		}
		 return true;
	 }
	/**
	 * 查询符合条件的信息 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		
		
		//if(this.mOperate.toUpperCase().equals("MSG"))
		{
			this.map.put(this.mLDMsgInfoSchema,mAction);
		}
		
		return true;
	}
}
