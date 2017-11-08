/**
 * <p>Title: 预付管理确认业务类 </p>
 * <p>Description: </p>
 * <p>Company: SinoSoft</p>
 * @author yuejw
 */

package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSGetClaimDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLClaimUWMDetailDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LLClaimUWMDetailSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLClaimUWMDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LClaimPrepayConfirmBL {
private static Logger logger = Logger.getLogger(LClaimPrepayConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput mG = new GlobalInput();
	private MMap map = new MMap();
	private TransferData mTransferData = new TransferData();
	private Reflections tRef = new Reflections();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mClmNo;
	private LLClaimUWMainSchema tLLClaimUWMainSchema=null;//案件审批信息
	private LLClaimUWMainSchema mLLClaimUWMainSchema=null;//界面传入的审批信息
	private LLClaimUWMDetailSchema tLLClaimUWMDetailSchema=null;//案件审核审批轨迹
	private LLClaimPubFunBL tLLClaimPubFunBL= null;

	public LClaimPrepayConfirmBL() {
	}

	public static void main(String[] args) {
	}

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
		logger.debug("----------LClaimPrepayConfirmBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		
		logger.debug("----------after prepareOutputData----------");
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("RptNo");
		
		mLLClaimUWMainSchema = (LLClaimUWMainSchema)mInputData.getObjectByObjectName(
				"LLClaimUWMainSchema", 0);
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try 
		{
			if (mTransferData == null || mG == null) {
				
				CError.buildErr(this, "在数据传入时出错!");
				return false;
			}
			
			if(mLLClaimUWMainSchema==null||mLLClaimUWMainSchema.equals("")){
				
				CError.buildErr(this, "传入的审批信息为空!");
				return false;
			}
			
			
			// 非空字段检验
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "业务处理过程中出现错误!");
			return false;
		}
		return true;
	}
	
	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		logger.debug("------start dealData-----");
		
		
		// 保存审批结论
		if (!dealClaimUW())
			return false;
		
		
		//审批结论为通过
		if(mLLClaimUWMainSchema.getExamConclusion().equals("0")){		
			
			//权限校验
			tLLClaimPubFunBL = new LLClaimPubFunBL();
//			if(!tLLClaimPubFunBL.getCheckPopedom(mClmNo, mG.Operator,"4")){
			//zy 2010-02-23 预付案件类型的标示为3
			if(!tLLClaimPubFunBL.getCheckPopedom(mClmNo, mG.Operator,"3")){
				
				// @@错误处理
				tLLClaimPubFunBL=null;
				CError.buildErr(this, "操作员"+mG.Operator+"不具备核赔当前金额的预付案件权限!");
				return false;
			}
			
			//生成实付信息
			if(!dealPay()){			
				return false;
			}			
					
		}
		
		
		//审批结论为退回
		if(mLLClaimUWMainSchema.getExamConclusion().equals("1")){		
			
			//清除预付信息
			if (!eliminatePayInformation())
				return false;	
			
		}
		
		//查询案件核赔级别

		mLLClaimUWMainSchema=null;
		return true;
	}
	
	
	/**
	 * 2009-1-4 zhangzheng 
	 * 回退时删除除审核,审批结论外的所有预付信息
	 * @return
	 */
	private boolean eliminatePayInformation(){
		
		
        String ljsgetSql="delete from ljsget where exists(select 1 from ljsgetclaim a where feeoperationtype='B' and a.getnoticeno=ljsget.getnoticeno) and ljsget.otherno='"+"?clmno?"+"'";
        String bnfSql="delete from llbnf where bnfkind='B' and clmno='"+"?clmno?"+"'";
        
        String balanceSql="delete from llbalance where feeoperationtype='B' and clmno='"+"?clmno?"+"'";
        String ljsgetclaimSql="delete from ljsgetclaim where feeoperationtype='B' and otherno='"+"?clmno?"+"'";
        
        String prepayclaimSql="delete from LLPrepayClaim where clmno='"+"?clmno?"+"'";
        String prepaydetaliSql="delete from LLPrepayDetail where clmno='"+"?clmno?"+"'";
        
        String llclaimSql="update llclaim set beforepay='0' where clmno='"+"?clmno?"+"'";
        String llclaimdetailSql="update llclaimdetail set PrepayFlag='0',PrepaySum='0' where clmno='"+"?clmno?"+"'";
        
		//审批回退时删除,轨迹已经保留
		String tLLClaimUWMainSql="delete from llclaimuwmain where clmno='"+"?clmno?"+"' and checktype='1'";			
	    
		// 打包提交数据
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tLLClaimUWMainSql);
		sqlbv.put("clmno", mClmNo);
		map.put(sqlbv, "DELETE");
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(ljsgetSql);
		sqlbv1.put("clmno", mClmNo);
        map.put(sqlbv1, "DELETE");
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(bnfSql);
		sqlbv2.put("clmno", mClmNo);
        map.put(sqlbv2, "DELETE");
        
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(balanceSql);
		sqlbv3.put("clmno", mClmNo);
        map.put(sqlbv3, "DELETE");
        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(ljsgetclaimSql);
		sqlbv4.put("clmno", mClmNo);
        map.put(sqlbv4, "DELETE");
        
        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(prepayclaimSql);
		sqlbv5.put("clmno", mClmNo);
        map.put(sqlbv5, "DELETE");
        SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(prepaydetaliSql);
		sqlbv6.put("clmno", mClmNo);
        map.put(sqlbv6, "DELETE");
        
        SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(llclaimSql);
		sqlbv7.put("clmno", mClmNo);
        map.put(sqlbv7, "UPDATE");
        SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(llclaimdetailSql);
		sqlbv8.put("clmno", mClmNo);
        map.put(sqlbv8, "UPDATE");
        

        
        //释放引用
        llclaimdetailSql=null;
        llclaimSql=null;
        prepaydetaliSql=null;
        prepayclaimSql=null;
        ljsgetclaimSql=null;
        balanceSql=null;
        bnfSql=null;
        ljsgetSql=null;
        
        return true;
	}
	
	/**
	 * 保存审批结论
	 */
	private boolean dealClaimUW() {
		

		LLClaimUWMainDB tLLClaimUWMainDB=new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(mClmNo);
		if(tLLClaimUWMainDB.getInfo())
		{
			tLLClaimUWMainSchema =new LLClaimUWMainSchema ();
			tLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());
			
			
			//更新审批结论
		    tLLClaimUWMainSchema.setExamConclusion(mLLClaimUWMainSchema.getExamConclusion());//审批结论
		    tLLClaimUWMainSchema.setExamIdea(mLLClaimUWMainSchema.getExamIdea());//审批意见
		    tLLClaimUWMainSchema.setExamNoPassReason(mLLClaimUWMainSchema.getExamNoPassReason());//审批不通过原因
		    tLLClaimUWMainSchema.setExamNoPassDesc(mLLClaimUWMainSchema.getExamNoPassDesc());//审批不通过意见	    
		    
		    tLLClaimUWMainSchema.setExamPer(mG.Operator);//审批人
            tLLClaimUWMainSchema.setExamDate(PubFun.getCurrentDate());//审批日期
            tLLClaimUWMainSchema.setExamCom(mG.ManageCom);//审批机构
            
            tLLClaimUWMainSchema.setOperator(mG.Operator);
            tLLClaimUWMainSchema.setModifyDate(PubFun.getCurrentDate());
            tLLClaimUWMainSchema.setModifyTime(PubFun.getCurrentTime());

			
			//更新审批结论
            String	strSQL =" select * from LLClaimUWMDetail where  clmno ='"+"?clmno?"+"'"
				   +" and ExamConclusion is null order by (clmuwno/1) desc";
            
            logger.debug("查询案件"+mLLClaimUWMainSchema.getClmNo()+"的审批轨迹:"+strSQL);
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(strSQL);
			sqlbv13.put("clmno", mLLClaimUWMainSchema.getClmNo());
    	    LLClaimUWMDetailDB tLLClaimUWMDetailDB=new LLClaimUWMDetailDB();
    			
    		LLClaimUWMDetailSet tLLClaimUWMDetailSet=new LLClaimUWMDetailSet();
    			
    		tLLClaimUWMDetailSet=tLLClaimUWMDetailDB.executeQuery(sqlbv13);
    			
    		if(tLLClaimUWMDetailSet.size()==0)
    		{
    		           CError.buildErr(this, "查询不到案件"+mLLClaimUWMainSchema.getClmNo()+"的审核结论轨迹");
    		           return false;
    		}
    		else
    		{
    			LLClaimUWMDetailSet mLLClaimUWMDetailSet=new LLClaimUWMDetailSet();
    				
    			for(int i=1;i<=tLLClaimUWMDetailSet.size();i++){
    					
    				tLLClaimUWMDetailSchema=tLLClaimUWMDetailSet.get(i);
        			tLLClaimUWMDetailSchema.setExamConclusion(mLLClaimUWMainSchema.getExamConclusion());
        			tLLClaimUWMDetailSchema.setExamIdea(mLLClaimUWMainSchema.getExamIdea());
        			tLLClaimUWMDetailSchema.setExamNoPassDesc(mLLClaimUWMainSchema.getExamNoPassDesc());
        			tLLClaimUWMDetailSchema.setExamNoPassReason(mLLClaimUWMainSchema.getExamNoPassReason());
        		    tLLClaimUWMDetailSchema.setExamDate(PubFun.getCurrentDate());
        		    tLLClaimUWMDetailSchema.setExamPer(mG.Operator);
        	        tLLClaimUWMDetailSchema.setOperator(mG.Operator);
        	        tLLClaimUWMDetailSchema.setModifyDate(PubFun.getCurrentDate());
        	        tLLClaimUWMDetailSchema.setModifyTime(PubFun.getCurrentTime());
        	        tLLClaimUWMDetailSchema.setMngCom(mG.ManageCom);
        	            
        	        mLLClaimUWMDetailSet.add(tLLClaimUWMDetailSchema);
        	        tLLClaimUWMDetailSchema=null;
    			}
    	
                map.put(mLLClaimUWMDetailSet, "DELETE&INSERT");
    		}
    		
    		
    		//当审批结论不为通过时,再插入一条新的轨迹
    		if(!mLLClaimUWMainSchema.getExamConclusion().equals("0"))
    		{
     		    tLLClaimUWMainSchema.setExamConclusion("");//审批结论
    		    tLLClaimUWMainSchema.setExamIdea("");//审批意见
    		    tLLClaimUWMainSchema.setExamNoPassReason("");//审批不通过原因
    		    tLLClaimUWMainSchema.setExamNoPassDesc("");//审批不通过意见	
    		    
    		    tLLClaimUWMainSchema.setExamPer("");//审批人
                tLLClaimUWMainSchema.setExamDate("");//审批日期
                tLLClaimUWMainSchema.setExamCom("");//审批机构
                
      			tLLClaimUWMDetailSchema = new LLClaimUWMDetailSchema();
      			this.tRef.transFields(tLLClaimUWMDetailSchema,
    					tLLClaimUWMainSchema);
    			
                //查询LLClaimUWMDetail核赔次数
    			String strSQL2 = "";
    			strSQL2 = " select Max(ClmUWNo/1) from LLClaimUWMDetail where "
    			       + " ClmNO='" + "?ClmNO?" + "'";
    			
    			logger.debug("strSQL2:"+strSQL2);
    			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
    			sqlbv9.sql(strSQL2);
    			sqlbv9.put("ClmNO", tLLClaimUWMainSchema.getClmNo());
    			ExeSQL exesql = new ExeSQL();
    			String tMaxNo = exesql.getOneValue(sqlbv9);
    			if (tMaxNo.length() == 0) {
    					tMaxNo = "1";
    			} 
    			else {
    					int tInt = Integer.parseInt(tMaxNo);
    					tInt = tInt + 1;
    					tMaxNo = String.valueOf(tInt);
    			}
    			
    			tLLClaimUWMDetailSchema.setClmUWNo(tMaxNo);
    			
    			map.put(tLLClaimUWMDetailSchema, "INSERT");
    		}
    		
 			// 打包提交数据
			map.put(tLLClaimUWMainSchema, "UPDATE");
			
			/**
			 * 2008-12-27 zhangzheng
			 * 更改赔案状态为审核
			 */
			String sql1 = " update LLRegister set ClmState = '30' where"
					+ " RgtNo = '" + "?clmno?" + "'";

			String sql2 = " update llclaim set ClmState = '30' where"
					+ " clmno = '" + "?clmno?" + "'";

			String sql3 = " update llclaimpolicy set ClmState = '30' where"
				+ " clmno = '" + "?clmno?" + "'";
			
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(sql1);
			sqlbv10.put("clmno", mClmNo);
			map.put(sqlbv10, "UPDATE");
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(sql2);
			sqlbv11.put("clmno", mClmNo);
			map.put(sqlbv11, "UPDATE");
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(sql3);
			sqlbv12.put("clmno", mClmNo);
			map.put(sqlbv12, "UPDATE");
			
	        sql1=null;
	        sql2=null;
	        sql3=null;
		}
		else
		{
			CError.buildErr(this, "查询不到案件"+mClmNo+"的审核信息");
			return false;
		}
		tLLClaimUWMainDB=null;
		tLLClaimUWMainSchema=null;
		return true;
	}
	
	/**
	 * 生成财务信息
	 */
	private boolean dealPay() {
	
		try {

			// 2、更新（UpDate）LLBnf（受益人账户表）,条件（赔案号，受益性质（BnfKind）==B），
			// 修改保险金支付标志（CasePayFlag=1）
			String strSql = "update LLBnf set CasePayFlag='1' where clmno='"
					+ "?clmno?" + "'";
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(strSql);
			sqlbv14.put("clmno", mClmNo);
			map.put(sqlbv14, "UPDATE");
			
			String strSql2 = "update LLBnfGather set CasePayFlag='1' where clmno='"
				+ "?clmno?" + "'";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(strSql2);
			sqlbv15.put("clmno", mClmNo);
			
			map.put(sqlbv15, "UPDATE");

			// 3、[预付确认]，转移数据,生成实付总表（LJAGet）数据和赔付实付表（LJAGetClaim）数据
			// 赔付应付表（LJSGetClaim）表的相应字段 转入 赔付实付表（LJAGetClaim）
			// 应付总表（LJSGet）表的相应字段 转入 生成实付总表（LJAGet）
			
			LJSGetDB tLJSGetDB = new LJSGetDB();
			tLJSGetDB.setOtherNo(mClmNo);
			tLJSGetDB.setOtherNoType("5");
			LJSGetSet tLJSGetSet = new LJSGetSet();
			tLJSGetSet = tLJSGetDB.query(); // [应付总表（LJSGet）]
			LJAGetSet tLJAGetSet = new LJAGetSet(); // [实付总表（LJAGet)]
			
			Reflections tRef = new Reflections();

			if (tLJSGetSet != null && tLJSGetSet.size() != 0) {
				
				LJAGetSchema tLJAGetSchema = new LJAGetSchema();
				tLJAGetSet.add(tLJAGetSchema);
				tRef.transFields(tLJAGetSet, tLJSGetSet); // [应付总表（LJSGet）----实付总表（LJAGet)]
				
				for (int j = 1; j <= tLJSGetSet.size(); j++) {
					
					String tNo = tLJSGetSet.get(j).getGetNoticeNo(); // 给付通知书号
					String tGetNo = PubFun1.CreateMaxNo("GETNO", PubFun.getNoLimit(mG.ManageCom)); // 生成实付号
					
					tLJAGetSet.get(j).setActuGetNo(tGetNo);//实付号
					tLJAGetSet.get(j).setGetNoticeNo(tNo); // 给付通知书号码
					tLJAGetSet.get(j).setShouldDate(CurrentDate); // 应付日期
					tLJAGetSet.get(j).setOperator(mG.Operator);
					tLJAGetSet.get(j).setMakeDate(CurrentDate);
					tLJAGetSet.get(j).setMakeTime(CurrentTime);
					tLJAGetSet.get(j).setModifyDate(CurrentDate);
					tLJAGetSet.get(j).setModifyTime(CurrentTime);
					SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
					sqlbv16.sql("update llbnfGather set otherno='"+"?tGetNo?"+"' where clmno='"+"?clmno?"+"' and otherno='"+"?tNo?"+"'");
					sqlbv16.put("tGetNo", tGetNo);
					sqlbv16.put("clmno", mClmNo);
					sqlbv16.put("tNo", tNo);
					map.put(sqlbv16, "UPDATE");

					LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB(); // 赔付应付表
					tLJSGetClaimDB.setGetNoticeNo(tLJSGetSet.get(j).getGetNoticeNo());
					LJSGetClaimSet tLJSGetClaimSet = new LJSGetClaimSet();
					tLJSGetClaimDB.setOtherNo(mClmNo);
					tLJSGetClaimDB.setOtherNoType("5");
					tLJSGetClaimSet = tLJSGetClaimDB.query(); // []
					LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet(); // []

					if (tLJSGetClaimSet != null && tLJSGetClaimSet.size() != 0) {
						
						Reflections ttRef = new Reflections();
						
						LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
						tLJAGetClaimSet.add(tLJAGetClaimSchema);
						ttRef.transFields(tLJAGetClaimSet, tLJSGetClaimSet);					
						
						for (int k = 1; k <= tLJSGetClaimSet.size(); k++) {
							tLJAGetClaimSet.get(k).setActuGetNo(tGetNo);
							tLJAGetClaimSet.get(k)
									.setOPConfirmCode(mG.Operator);
							tLJAGetClaimSet.get(k)
									.setOPConfirmDate(CurrentDate);
							tLJAGetClaimSet.get(k)
									.setOPConfirmTime(CurrentTime);
							tLJAGetClaimSet.get(k).setOperator(mG.Operator);
							tLJAGetClaimSet.get(k).setMakeDate(CurrentDate);
							tLJAGetClaimSet.get(k).setMakeTime(CurrentTime);
							tLJAGetClaimSet.get(k).setModifyDate(CurrentDate);
							tLJAGetClaimSet.get(k).setModifyTime(CurrentTime);
							SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
							sqlbv17.sql("update llbnf set otherno='"+"?tGetNo?"+"' where clmno='"+"?clmno?"+"' and otherno='"+"?tNo?"+"' and bnfkind='"+"?bnfkind?"+"'");
							sqlbv17.put("tGetNo", tGetNo);
							sqlbv17.put("clmno", mClmNo);
							sqlbv17.put("tNo", tNo);
							sqlbv17.put("bnfkind", tLJAGetClaimSet.get(k).getFeeOperationType());
							map.put(sqlbv17, "UPDATE");
						}
						
						map.put(tLJAGetClaimSet, "DELETE&INSERT");
						map.put(tLJSGetClaimSet, "DELETE");
						
//						// 如果总给付金额为零,就不添加到实付表
//						if (tLJAGetSet.get(j).getSumGetMoney() != 0) {
//							map.put(tLJAGetSet.get(j), "DELETE&INSERT");
//						}

					}
				}

				
				
				map.put(tLJAGetSet, "DELETE&INSERT");
				map.put(tLJSGetSet, "DELETE");


				// 插入预付打印------------------------------------------------------
				// 首先删除
				String tDSql = "delete from LOPRTManager where 1=1 "
						+ " and OtherNo = '" + "?mClmNo?" + "'"
						+ " and Code = 'PCT014'";
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(tDSql);
				sqlbv18.put("mClmNo", mClmNo);
				map.put(sqlbv18, "DELETE");

				if (!insertLOPRTManager("PCT014")) // 插入预付打印
				{
					return false;
				}

			}
			else
			{
				// @@错误处理
				CError.buildErr(this, "查询不到案件:"+mClmNo+"的应付总表信息!");
				return false;
			}
		} 
		catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "业务处理过程中出现错误!");
			return false;
		}
		
		return true;
	}

	

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// 插入新值
		String strNolimit = PubFun.getNoLimit(mG.ManageCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit); // 生成印刷流水号
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mClmNo); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码
		tLOPRTManagerSchema.setManageCom(mG.ManageCom); // 管理机构
		tLOPRTManagerSchema.setReqCom(mG.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mG.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);

		// 查询立案信息
		LLCaseDB tLLCaseDB = new LLCaseDB();
		String tSSql = "select * from llcase where 1=1 " + " and caseno = '"
				+ "?mClmNo?" + "'";
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(tSSql);
		sqlbv19.put("mClmNo", mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.executeQuery(sqlbv19);
		String tCusNo = tLLCaseSet.get(1).getCustomerNo();
		tLOPRTManagerSchema.setStandbyFlag4(tCusNo); // 客户号码

		// 立案日期
		String tSql2 = "select to_char(rgtdate,'yyyy-mm-dd') from llregister a where "
				+ " a.rgtno = '" + "?mClmNo?" + "'";
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(tSql2);
		sqlbv20.put("mClmNo", mClmNo);
		ExeSQL tExeSQL2 = new ExeSQL();
		String tRgtDate = tExeSQL2.getOneValue(sqlbv20);

		tLOPRTManagerSchema.setStandbyFlag5(tRgtDate); // 立案日期
		tLOPRTManagerSchema.setStandbyFlag6("35"); // 赔案状态

		map.put(tLOPRTManagerSchema, "INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.add(map);
			mResult.add(mTransferData);
			mResult.add(mG);
			return true;
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
	}

}
