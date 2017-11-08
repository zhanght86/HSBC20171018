package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;
import java.awt.SystemColor;

import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.config.CombinationConfigUI;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;
/*******************************************************************************
 * Name     :LLClaimMergeBL.java
 * Function :案件合并
 * Author   :zhangzheng
 * Date     :2009-01-06
 */
public class LLClaimMergeBL implements BusinessService
{
	private static Logger logger = Logger.getLogger(LLClaimMergeBL.class);
   
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	private MMap map = new MMap();/** 往后面传输的数据库操作 */

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	
    public  CErrors mErrors = new CErrors();
    private String mlastClmNo="";//所选已结案案件的赔案号
    private String mlastAccNo="";//所选已结案案件的事件号
    private String mClmNo="";//本次案件的赔案号
    private String mAccNo="";//本次案件的事件号
    private boolean mFlag=false;//两个赔案是否符合案件合并条件标志:false--不符合,true--符合
	
    public LLClaimMergeBL() {}
  
  
	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		
		logger.debug("----------LLClaimMergeBL Begin----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 检查数据合法性
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this,"数据提交失败,原因是"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		mInputData = null;

		logger.debug("----------LLClaimMergeBL End----------");
		return true;
	}
  
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mInputData = cInputData;
		this.mOperate = cOperate;

		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);


		return true;
	}
	
	
	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null||mGlobalInput.equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}
		
		if (mOperate == null||mOperate.equals("")) {
			// @@错误处理
			CError.buildErr(this, "传入的操作类型为空!");
			return false;
		}
		
		
		if(mTransferData==null||mTransferData.equals("")){
			
			//错误处理
			CError.buildErr(this,"传入公共信息失败!");
			return false;
		}

	    mlastClmNo=(String)mTransferData.getValueByName("LastClmNo");//所选已结案案件的赔案号
	    
	    if(mlastClmNo==null||mlastClmNo.equals("")){
	    	
			//错误处理
			CError.buildErr(this,"传入的已结案件的赔案号为空!");
			return false;
	    }
	    
	    mlastAccNo=(String)mTransferData.getValueByName("LastAccNo");//本次案件的事件号		
	    
	    if(mlastAccNo==null||mlastAccNo.equals("")){
	    	
			//错误处理
			CError.buildErr(this,"传入的已结案件的事件号为空!");
			return false;
	    }
	    
	    mClmNo=(String)mTransferData.getValueByName("ClmNo");//本次案件的赔案号		
	    
	    if(mClmNo==null||mClmNo.equals("")){
	    	
			//错误处理
			CError.buildErr(this,"传入的本次案件的赔案号为空!");
			return false;
	    }
	    
	    mAccNo=(String)mTransferData.getValueByName("AccNo");//本次案件的事件号		
	    
	    if(mAccNo==null||mAccNo.equals("")){
	    	
			//错误处理
			CError.buildErr(this,"传入的本次案件的事件号为空!");
			return false;
	    }
	    
	    
	    
	    //案件合并校验两个案件是否合并规则
		if ((this.mOperate.equals("Merge"))) {
			
			ExeSQL tExeSQL=null;
			SSRS tSSRS=null;
			String sql="";
			
			try {
				
				tExeSQL=new ExeSQL();
			    tSSRS=new SSRS();
			    
			    /**
			     * 校验规则
			     * 1 已结案案件的最晚出院日期与本次案件的最早的入院日期间隔小于等于90
			     * 2 两个赔案包含相同的保单
			     */
			    sql=" select distinct 1 from"
			    		  +" ( "
			    		  +"   select (min(c.startdate)-max(d.enddate)+1) intervalDate from LLCaseReceipt d, LLCaseReceipt c"
			    		  +"   where c.feeitemtype='C' and d.feeitemtype='C'"
			    		  +"   and c.clmno='"+"?clmno?"+"'and d.clmno='"+"?clmno2?"+"'"
			    		  +"   and exists(select 1 from  llclaimpolicy a,llclaimpolicy b where a.polno=b.polno and a.clmno=c.clmno and b.clmno=d.clmno)"
			    		  +" ) g"
			    		  +" where (intervalDate+1)<=90";
			    
			    logger.debug("校验是否符合合并规则的sql"+sql);
			    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			    sqlbv.sql(sql);
			    sqlbv.put("clmno1", mClmNo);
			    sqlbv.put("clmno2", mlastClmNo);
			    tSSRS = tExeSQL.execSQL(sqlbv);
			    
			    if(tSSRS.getMaxRow()>0)
			    {
			    	mFlag=true;
			    }
			    
			    
				// mResult.add(mLLAccidentSchema);//供前台界面使用
			} catch (Exception ex) {
				// @@错误处理
				CError.buildErr(this, "校验两个案件是否符合案件合并条件时出现异常!");
				return false;
			}
			finally
			{
				tExeSQL=null;
				tSSRS=null;
				sql=null;
				
				if(mFlag==false)
				{
					CError.buildErr(this, "两个案件不符合案件合并条件,不能执行案件合并!");
					return false;
				}
				
			}
		}
		
		return true;
	}
	
	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		//案件合并
		if (this.mOperate.equals("Merge")) {
			
			if (!UpdateAccNo(mlastAccNo,mAccNo,mClmNo,"Merge")) {
				return false;
			}
		}
		
		//取消案件合并
		if (this.mOperate.equals("CancelMerge")) {
			
			String newAccNo="8"+PubFun1.CreateMaxNo("ACCNO", 10); // 生成事件号流水号;
			
			if (!UpdateAccNo(newAccNo,mAccNo,mClmNo,"CancelMerge")) {
				return false;
			}
		}

		return true;
	}
	
	
	/**
	 * 更新sql
	 * 第一个是参数是要覆盖的事件号,第二个参数是被覆盖的事件号,第三个参数是本次案件的赔案号,第四个参数是操作类型
	 * 
	 * @return boolean
	 */
	private boolean UpdateAccNo(String mergeAccNo,String oldAccNo,String oldClmNo,String pOperate) {
		try {
			
			logger.debug("sql1:"+"update llclaimpolicy set caserelano='"+mergeAccNo+"' where clmno='"+oldClmNo+"'");
			logger.debug("sql2:"+"update llclaimdetail set caserelano='"+mergeAccNo+"' where clmno='"+oldClmNo+"'");
			logger.debug("sql3:"+"update LLPrepayDetail set caserelano='"+mergeAccNo+"' where clmno='"+oldClmNo+"'");
			logger.debug("sql4:"+"update llcaserela set caserelano='"+mergeAccNo+"' where clmno='"+oldClmNo+"'");
			//logger.debug("sql5:"+"update LLAccidentSub set accno='"+mergeAccNo+"' where accno='"+oldAccNo+"'");
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		    sqlbv1.sql("update llclaimpolicy set caserelano='"+"?accno?"+"' where clmno='"+"?clmno?"+"'");
		    sqlbv1.put("accno", mergeAccNo);
		    sqlbv1.put("clmno", oldClmNo);
			map.put(sqlbv1, "UPDATE");
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		    sqlbv2.sql("update llclaimdetail set caserelano='"+"?accno?"+"' where clmno='"+"?clmno?"+"'");
		    sqlbv2.put("accno", mergeAccNo);
		    sqlbv2.put("clmno", oldClmNo);
			map.put(sqlbv2, "UPDATE");
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		    sqlbv3.sql("update LLPrepayDetail set caserelano='"+"?accno?"+"' where clmno='"+"?clmno?"+"'");
		    sqlbv3.put("accno", mergeAccNo);
		    sqlbv3.put("clmno", oldClmNo);
			map.put(sqlbv3, "UPDATE");
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		    sqlbv4.sql("update llcaserela set caserelano='"+"?accno?"+"' where caseno='"+"?clmno?"+"'");
		    sqlbv4.put("accno", mergeAccNo);
		    sqlbv4.put("clmno", oldClmNo);
			map.put(sqlbv4, "UPDATE");
			//map.put("update LLAccidentSub set accno='"+mergeAccNo+"' where accno='"+oldAccNo+"'", "UPDATE");
			
			LLAccidentSubDB tLLAccidentSubDB = new LLAccidentSubDB();
			LLAccidentSubSet tDelLLAccidentSubSet = new LLAccidentSubSet();
			LLAccidentSubSet tInsLLAccidentSubSet = new LLAccidentSubSet();
			tLLAccidentSubDB.setAccNo(oldAccNo);
			tDelLLAccidentSubSet = tLLAccidentSubDB.query();
			tInsLLAccidentSubSet.set(tDelLLAccidentSubSet);
			for(int i=1;i<=tInsLLAccidentSubSet.size();i++){
				tInsLLAccidentSubSet.get(i).setAccNo(mergeAccNo);
			}
			map.put(tDelLLAccidentSubSet, "DELETE");
			map.put(tInsLLAccidentSubSet, "INSERT");
			if(pOperate.equals("CancelMerge"))
			{
				LLAccidentDB tLLAccidentDB = new LLAccidentDB();
				tLLAccidentDB.setAccNo(oldAccNo);
				
				if(tLLAccidentDB.getInfo())
				{
					LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
					tLLAccidentSchema.setSchema(tLLAccidentDB.getSchema());
					tLLAccidentSchema.setAccNo(mergeAccNo);
					map.put(tLLAccidentSchema, "INSERT");
				}
				else
				{
					// @@错误处理
					CError.buildErr(this, "查询不到案件"+oldClmNo+"的事件信息!");
					return false;
				}

			}
			else
			{
				logger.debug("sql6:"+"delete from LLAccident where accno='"+oldAccNo+"'");
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			    sqlbv5.sql("delete from LLAccident where accno='"+"?accno?"+"'");
			    sqlbv5.put("accno", oldAccNo);
				map.put(sqlbv5, "INSERT");
			}
			
			
			mResult.add(mergeAccNo);

		} 
		catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} 
		catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

	/**
	 * 提供返回前台界面的数据
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}  
	
	public static void main(String[] args)
	{
	
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
