package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;






import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 投保业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author sunsx 2009-03-30
 * @version 1.0
 */
public class LFGetPayNewBL
{
private static Logger logger = Logger.getLogger(LFGetPayNewBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mResult;
    private MMap mMap = new MMap();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 业务处理相关变量 */
    private LJSGetDrawSet dLJSGetDrawSet = new LJSGetDrawSet();//删除的LJSGetDrawSet
    private LJSGetSet dLJSGetSet = new LJSGetSet();//删除的LJSGetSet
    private LJAGetDrawSet mLJAGetDrawSet = new LJAGetDrawSet();//转储的LJAGetDrawSet
    private LJAGetSet mLJAGetSet = new LJAGetSet();//生成的LJAGet
    private LCGetSet mLCGetSet = new LCGetSet();//更新的

    private String mGrpContNo = "";
    private String mAppName = "";
    private String mPrtSeq = "";
    Reflections mRef = new Reflections();

    



	public void setGlobalInput(GlobalInput globalInput) {
		mGlobalInput = globalInput;
	}
	
	public void setAppName(String appName) {
		mAppName = appName;
	}
	
	public String getPrtSeq() {
		return mPrtSeq;
	}


	public boolean dealLFGet(String tGrpContNo){
		mGrpContNo = tGrpContNo;
		
		if(mGrpContNo == null || mGrpContNo.equals("") ||mAppName == null || mAppName.equals("")){
			CError.buildErr(this, "传入信息不完整，请检查团体保单号和申请人！");
			return false;
		}
		String tSQL = "Select distinct 1 From LjsGet a,LCCont b Where a.OtherNo=b.ContNo and a.OtherNoType='2' "
				    + " and a.GetDate <= now() and b.GrpContNo ='?mGrpContNo?' and  (a.PayMode is null or a.PayMode not in ('1','4'))";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("mGrpContNo", mGrpContNo);
		String tPayModeHasImport = tExeSQL.getOneValue(sqlbv);
		if (tPayModeHasImport != null && tPayModeHasImport.equals("1")) {
			CError.buildErr(this, "该团单下个人领取方式信息不完整，请检查！");
			return false;
		}
		tSQL = "Select distinct ContNo From LjsGetDraw where GrpContNo = '?mGrpContNo?' and lastgettodate<=now() ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("mGrpContNo", mGrpContNo);
		SSRS tSSRS = new SSRS();
		tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			CError.buildErr(this, "该团单暂无可领取的记录");
			return false;
		}
		int tArrLen = tSSRS.MaxRow;
		
		List tBIContList = new ArrayList();
        String tSerialNo = PubFun1.CreateMaxNo("SERIALNO",PubFun.getNoLimit(mGlobalInput.ManageCom));
		for (int i = 1; i <= tArrLen; i++) {
			String tContNo = tSSRS.GetText(i, 1);

			double dGetMoney = 0.0;
			tSQL = "Select * From LjsGet Where OtherNo = '?tContNo?' and OtherNoType = '2' and GetDate <= now() Order By GetDate Desc ";
			LJSGetDB tLJSGetDB = new LJSGetDB();
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tSQL);
			sqlbv2.put("tContNo", tContNo);
			LJSGetSet tLJSGetSet = tLJSGetDB.executeQuery(sqlbv2);
			if(tLJSGetSet == null || tLJSGetSet.size()<1){
				CError.buildErr(this, "查询团单下个单领取的记录失败!");
				return false;
			}
			
	        String tActuGetNo = PubFun1.CreateMaxNo("GETNO",PubFun.getNoLimit(tLJSGetSet.get(1).getManageCom())); //参数为机构代码
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			LJSGetSchema tLJSGetSchema = null;
			for(int j = 1;j<=tLJSGetSet.size();j++){
				tLJSGetSchema = tLJSGetSet.get(j);
				dGetMoney += tLJSGetSchema.getSumGetMoney();
				if(j==1){
					mRef.transFields(tLJAGetSchema, tLJSGetSchema);
					tLJAGetSchema.setActuGetNo(tActuGetNo);
					tLJAGetSchema.setShouldDate(tLJSGetSchema.getGetDate());
					tLJAGetSchema.setOperator(mGlobalInput.Operator);
					tLJAGetSchema.setMakeDate(PubFun.getCurrentDate());
					tLJAGetSchema.setMakeTime(PubFun.getCurrentTime());
					tLJAGetSchema.setModifyDate(PubFun.getCurrentDate());
					tLJAGetSchema.setModifyTime(PubFun.getCurrentTime());
					
				}
				dLJSGetSet.add(tLJSGetSchema);
			}
			tLJAGetSchema.setSerialNo(tSerialNo);
			tLJAGetSchema.setSumGetMoney(dGetMoney);
			mLJAGetSet.add(tLJAGetSchema);
			
			tSQL = "Select * From LjsGetDraw Where ContNo = '?tContNo?' and GetDate <=now()";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tSQL);
			sqlbv3.put("tContNo", tContNo);
			LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
			LJSGetDrawSet tLJSGetDrawSet = tLJSGetDrawDB.executeQuery(sqlbv3);
			if(tLJSGetDrawSet == null || tLJSGetDrawSet.size()<1){
				CError.buildErr(this, "查询团单下个单领取的明细记录失败!");
				return false;
			}
			
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setContNo(tContNo);
			tLCGetDB.setLiveGetType("0");
			tLCGetDB.setCanGet("0");
			tLCGetDB.setUrgeGetFlag("Y");
			LCGetSet tLCGetSet = tLCGetDB.query();
			if(tLCGetSet == null || tLCGetSet.size()<1){
				CError.buildErr(this, "查询团单下个单领取项表失败!");
				return false;
			}
			
			LJSGetDrawSchema tLJSGetDrawSchema = null;
			LJAGetDrawSchema tLJAGetDrawSchema = null;
			boolean becomeInvalid = false;

			
			for(int j = 1;j<=tLJSGetDrawSet.size();j++){
				tLJSGetDrawSchema = tLJSGetDrawSet.get(j);
				tLJAGetDrawSchema = new LJAGetDrawSchema();
				mRef.transFields(tLJAGetDrawSchema, tLJSGetDrawSchema);
				tLJAGetDrawSchema.setActuGetNo(tActuGetNo);
				tLJAGetDrawSchema.setSerialNo(tSerialNo);
				tLJAGetDrawSchema.setOperator(mGlobalInput.Operator);
				tLJAGetDrawSchema.setMakeDate(PubFun.getCurrentDate());
				tLJAGetDrawSchema.setMakeTime(PubFun.getCurrentTime());
				tLJAGetDrawSchema.setModifyDate(PubFun.getCurrentDate());
		        tLJAGetDrawSchema.setModifyTime(PubFun.getCurrentTime());
				dLJSGetDrawSet.add(tLJSGetDrawSchema);
				mLJAGetDrawSet.add(tLJAGetDrawSchema);
				
				
				for(int k = 1;k<=tLCGetSet.size();k++){
					if(tLCGetSet.get(k).getPolNo().equals(tLJSGetDrawSchema.getPolNo()) &&
							tLCGetSet.get(k).getDutyCode().equals(tLJSGetDrawSchema.getDutyCode())&&
									tLCGetSet.get(k).getGetDutyCode().equals(tLJSGetDrawSchema.getGetDutyCode())){
						
						tLCGetSet.get(k).setSumMoney(tLCGetSet.get(k).getSumMoney() + tLJSGetDrawSchema.getGetMoney());
						if (tLJSGetDrawSchema.getCurGetToDate().compareTo(tLCGetSet.get(k).getGettoDate())>0) {
							tLCGetSet.get(k).setGettoDate(tLJSGetDrawSchema.getCurGetToDate());
							tLCGetSet.get(k).setActuGet(tLJSGetDrawSchema.getGetMoney());
						}
						tLCGetSet.get(k).setModifyDate(PubFun.getCurrentDate());
						tLCGetSet.get(k).setModifyTime(PubFun.getCurrentTime());
						
					}
				}
				String destroyFlag = tLJSGetDrawSchema.getDestrayFlag();
				if("1".equals(destroyFlag)){
					becomeInvalid = true;
				}

			}
			
			mLCGetSet.add(tLCGetSet);
			if(becomeInvalid){
				tBIContList.add(tContNo);
			}
				
		}
		//插入领取清单通知书
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		mPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_GEdorGetNotice); // 生成打印流水号
		tLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		tLOPRTManagerSchema.setOtherNo(mGrpContNo);
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 团体保单号
		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GEdorGetNotice); // 打印类型
		tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom); // 管理机构
		tLOPRTManagerSchema.setAgentCode(""); // 代理人编码
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		tLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);
		tLOPRTManagerSchema.setStandbyFlag1(tSerialNo);//保存好这个号啊,贼TM珍贵!
		tLOPRTManagerSchema.setStandbyFlag2(mAppName);
        mMap.put(dLJSGetDrawSet, "DELETE");
        mMap.put(dLJSGetSet, "DELETE");
        mMap.put(mLJAGetSet, "DELETE&INSERT");
        mMap.put(mLJAGetDrawSet, "DELETE&INSERT");
        mMap.put(mLCGetSet, "DELETE&INSERT");
        mMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
        mResult = new VData();
        mResult.add(mMap);
        //数据提交
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mResult, "")) {
        	CError.buildErr(this, "保存失败原因是:" + tSubmit.mErrors.getFirstError());
        	return false;
        }
        
        Iterator it = tBIContList.iterator();
        while(it.hasNext()){
        	String tContNo = (String)it.next();
        	LCPolDB tLCPolDB = new LCPolDB();
        	tLCPolDB.setContNo(tContNo);
            LCPolSet tLCPolSet = tLCPolDB.query();
            if (tLCPolSet.size() == 1){
            	//MS团体年金险一个合同只有一个LCPol
            	 LCPolSchema tLCPolSchema = new LCPolSchema();
                 VData tVData = new VData();
                 tLCPolSchema.setPolNo(tLCPolSet.get(1).getPolNo());
                 tLCPolSchema.setPolState("0303"
                     + tLCPolSet.get(1).getPolState().substring(0, 3));

                 tVData.add(tLCPolSchema);
                 ChangPolStateBLS tChangPolStateBLS = new ChangPolStateBLS();
                 if (!tChangPolStateBLS.submitData(tVData, "UPDATA")){
                     // @@错误处理
                     CError.buildErr(this,"保单状态数据修改失败!");
                     return false;
                 }
            } else{
                // @@错误处理
                CError.buildErr(this,"保单数据查询出错!");
                return false;
            }
            
            //sxy-2003-09-10
            ContCancel aContCancel = new ContCancel(tContNo,mLJAGetDrawSet.get(1).getSerialNo());
            if (!aContCancel.submitData())
            {
                return false;
            }
            
        }
        
		return true;
	}


	public static void main(String[] args)
    {

    }
}
