package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * Title: Web业务系统
 * Description: 万能缓缴期解除回退确认类
 * Copyright: Copyright (c) 2010-07-05
 * Company: Sinosoft
 * @author QianLy
 * @version 1.0
 * @date 2010-07-05
 */













import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.utility.*;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.*;

public class PEdorTSBackConfirmBL implements EdorBack
{
private static Logger logger = Logger.getLogger(PEdorTSBackConfirmBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    private MMap mMap = new MMap();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    /** 业务数据 */
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private GlobalInput mGlobalInput = new GlobalInput();

    private Reflections mReflections = new Reflections();

    public PEdorTSBackConfirmBL()
    {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串，主要包括""和""
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()){
            return false;
        }

        //数据校验
        if (!checkData()){
            return false;
        }

        //进行业务处理
        if (!dealData()){
            return false;
        }

        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData(){
        try{
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
        }catch (Exception e){
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorCPConfirmBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败！";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    /**
     * 数据校验
     * @return: boolean
     */
    private boolean checkData(){
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
        tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
        tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
        tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
        if (!tLPEdorItemDB.getInfo()){
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorCPConfirmBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "查询批改项目信息失败！";
            this.mErrors.addOneError(tError);
            return false;
        }
        mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
	{
		try
		{
			//获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			//LCCont表数据交换-->
			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
			if (!tLPContDB.getInfo()){
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorCPConfirmBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保全保单记录信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LPContSchema tLPContSchema = tLPContDB.getSchema();
			LCContSchema tLCContSchema = new LCContSchema();
			this.mReflections.transFields(tLCContSchema, tLPContSchema);
			tLCContSchema.setOperator(this.mGlobalInput.Operator);
			tLCContSchema.setModifyDate(strCurrentDate);
			tLCContSchema.setModifyTime(strCurrentTime);
			mMap.put(tLCContSchema, "DELETE&INSERT");
			
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
			if (!tLCContDB.getInfo()){
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorCPConfirmBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保单记录信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCContSchema = new LCContSchema();
			tLCContSchema = tLCContDB.getSchema();
			tLPContSchema = new LPContSchema();
			this.mReflections.transFields(tLPContSchema, tLCContSchema);
			tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContSchema.setOperator(this.mGlobalInput.Operator);
			tLPContSchema.setModifyDate(strCurrentDate);
			tLPContSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPContSchema, "DELETE&INSERT");
			
			//LCPol表数据交换-->
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
			LPPolSet chgLPPolSet = tLPPolDB.query();
			if (tLPPolDB.mErrors.needDealError()){
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorCPConfirmBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保全保单险种记录信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			for (int k = 1; k <= chgLPPolSet.size(); k++){
				//P表到C表
				LPPolSchema tLPPolSchema = chgLPPolSet.get(k);
				LCPolSchema tLCPolSchema = new LCPolSchema();
				this.mReflections.transFields(tLCPolSchema, tLPPolSchema);
				tLCPolSchema.setOperator(this.mGlobalInput.Operator);
				tLCPolSchema.setModifyDate(strCurrentDate);
				tLCPolSchema.setModifyTime(strCurrentTime);
				mMap.put(tLCPolSchema, "DELETE&INSERT");
				//C表到P表
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
				if (!tLCPolDB.getInfo()){
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorCPConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保单险种记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolDB.getSchema();
				tLPPolSchema = new LPPolSchema();
				this.mReflections.transFields(tLPPolSchema, tLCPolSchema);
				tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPolSchema.setOperator(this.mGlobalInput.Operator);
				tLPPolSchema.setModifyDate(strCurrentDate);
				tLPPolSchema.setModifyTime(strCurrentTime);
				mMap.put(tLPPolSchema, "DELETE&INSERT");
				
				//LCDuty表数据交换-->
				LPDutyDB tLPDutyDB = new LPDutyDB();
				tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPDutyDB.setContNo(mLPEdorItemSchema.getContNo());
				tLPDutyDB.setPolNo(tLPPolSchema.getPolNo());
				LPDutySet tempLPDutySet = new LPDutySet();
				tempLPDutySet = tLPDutyDB.query();
				if (tempLPDutySet == null || tempLPDutySet.size() <= 0){
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorCPConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保全责任记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LCDutySet tLCDutySet = new LCDutySet();
				for (int i = 1; i <= tempLPDutySet.size(); i++){
					LCDutySchema tLCDutySchema = new LCDutySchema();
					this.mReflections.transFields(tLCDutySchema, tempLPDutySet.get(i));
					tLCDutySchema.setOperator(this.mGlobalInput.Operator);
					tLCDutySchema.setModifyDate(strCurrentDate);
					tLCDutySchema.setModifyTime(strCurrentTime);
					tLCDutySet.add(tLCDutySchema);
				}

				mMap.put(tLCDutySet, "DELETE&INSERT");
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
				LCDutySet tempLCDutySet = tLCDutyDB.query();
				if (tempLCDutySet == null || tempLCDutySet.size() <= 0){
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorCPConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询责任记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LPDutySet tLPDutySet = new LPDutySet();
				for (int i = 1; i <= tempLCDutySet.size(); i++){
					LPDutySchema tLPDutySchema = new LPDutySchema();
					this.mReflections.transFields(tLPDutySchema, tempLCDutySet.get(i));
					tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPDutySchema.setOperator(this.mGlobalInput.Operator);
					tLPDutySchema.setModifyDate(strCurrentDate);
					tLPDutySchema.setModifyTime(strCurrentTime);
					tLPDutySet.add(tLPDutySchema);
				}
				mMap.put(tLPDutySet, "DELETE&INSERT");

				//LCPrem表数据交换-->
				LPPremDB tLPPremDB = new LPPremDB();
				tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
				tLPPremDB.setPolNo(tLPPolSchema.getPolNo());
				LPPremSet tempLPPremSet = new LPPremSet();
				tempLPPremSet = tLPPremDB.query();
				if (tempLPPremSet == null || tempLPPremSet.size() <= 0){
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorCPConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保全保费项记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LCPremSet tLCPremSet = new LCPremSet();
				for (int i = 1; i <= tempLPPremSet.size(); i++){
					LCPremSchema tLCPremSchema = new LCPremSchema();
					this.mReflections.transFields(tLCPremSchema, tempLPPremSet.get(i));
					tLCPremSchema.setOperator(this.mGlobalInput.Operator);
					tLCPremSchema.setModifyDate(strCurrentDate);
					tLCPremSchema.setModifyTime(strCurrentTime);
					tLCPremSet.add(tLCPremSchema);
				}
				mMap.put(tLCPremSet, "DELETE&INSERT");
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
				LCPremSet tempLCPremSet = tLCPremDB.query();
				if (tempLCPremSet == null || tempLCPremSet.size() <= 0)
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorCPConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保费项记录信息失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				LPPremSet tLPPremSet = new LPPremSet();
				for (int i = 1; i <= tempLCPremSet.size(); i++){
					LPPremSchema tLPPremSchema = new LPPremSchema();
					this.mReflections.transFields(tLPPremSchema, tempLCPremSet.get(i));
					tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPPremSchema.setOperator(this.mGlobalInput.Operator);
					tLPPremSchema.setModifyDate(strCurrentDate);
					tLPPremSchema.setModifyTime(strCurrentTime);
					tLPPremSet.add(tLPPremSchema);
				}
				mMap.put(tLPPremSet, "DELETE&INSERT");
			}

            //帐户表处理
            //扣费轨迹表删除
			String delFeeTraceSql = "Delete from LCInsureAccFeeTrace where contno='?contno?' and paydate=(select distinct paydate from lcinsureacctrace where contno = '?contno?' and busytype = '?busytype?' and accalterno = '?accalterno?') and feecode like '%00'"; 
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(delFeeTraceSql);
			sqlbv1.put("contno", mLPEdorItemSchema.getContNo());
			sqlbv1.put("busytype", mLPEdorItemSchema.getEdorType());
			sqlbv1.put("accalterno", mLPEdorItemSchema.getEdorNo());
			mMap.put(sqlbv1,"DELETE");
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
	        String sTSDate = BqNameFun.getAnother(sqlbv,"LCInsureaccTrace","contno='?ContNo?' and accalterno"
                      ,mLPEdorItemSchema.getEdorNo(),"min(PayDate)");
            sTSDate = BqNameFun.delTime(sTSDate);
			
	        String sql = "delete from lcinsureaccfeetrace where contno = '?contno?'"
                       + " and otherno not in( select otherno from lcinsureacctrace where busytype = 'RN' and polno = lcinsureaccfeetrace.polno)"
                       + " and paydate > '?sTSDate?'";
	        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(sql);
	        sqlbv2.put("contno", mLPEdorItemSchema.getContNo());
	        sqlbv2.put("sTSDate", sTSDate);
	        mMap.put(sqlbv2,"DELETE");

            sql = "delete from lcinsureacctrace where contno = '?contno?'"
                       + " and moneytype in ('GL','LX') and busytype not in ('RN','IP','OP')"
                       + " and paydate > '?sTSDate?'";
            SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
            sqlbv3.sql(sql);
            sqlbv3.put("contno", mLPEdorItemSchema.getContNo());
            sqlbv3.put("sTSDate", sTSDate);
            mMap.put(sqlbv3,"DELETE");

			//LCInsureAccTrace中的记录删除
			String delAccTraceSql = "Delete from LCInsureAccTrace where contno = '?contno?' and busytype = '?busytype?' and accalterno = '?accalterno?'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(delAccTraceSql);
			sqlbv4.put("contno", mLPEdorItemSchema.getContNo());
			sqlbv4.put("busytype", mLPEdorItemSchema.getEdorType());
			sqlbv4.put("accalterno", mLPEdorItemSchema.getEdorNo());
			mMap.put(sqlbv4,"DELETE");
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.put("paydate",sTSDate);
	        String lastbaladate = BqNameFun.getAnother(sqlbv15,"LCInsureaccTrace","paydate <='?paydate?' and moneytype = 'LX' and contno",mLPEdorItemSchema.getContNo(),"max(paydate)");
            boolean sFirstFlag = false;
            if(lastbaladate == null || "".equals(lastbaladate)){
                sFirstFlag = true;
                lastbaladate = BqNameFun.getAnother(null,"LCInsureaccTrace","contno",mLPEdorItemSchema.getContNo(),"min(paydate)");
            }
            lastbaladate = BqNameFun.delTime(lastbaladate);

            String beforeLastBalaDate = "";
            if(!sFirstFlag){
            	SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
            	sqlbv16.put("lastbaladate",lastbaladate);
                beforeLastBalaDate = BqNameFun.getAnother(sqlbv16,"LCInsureaccTrace","paydate < '?lastbaladate?' and moneytype = 'LX' and contno" ,mLPEdorItemSchema.getContNo(),"max(paydate)");
            }else{
                beforeLastBalaDate = lastbaladate;
            }
            if(beforeLastBalaDate == null || "".equals(beforeLastBalaDate)){
                beforeLastBalaDate = lastbaladate;
            }
            beforeLastBalaDate = BqNameFun.delTime(beforeLastBalaDate);

            sql = "update lcinsureacc c set "
                + " baladate = '?lastbaladate?',"
                + " lastaccbala = (select sum(money) from lcinsureacctrace where polno =c.polno and paydate <= '?beforeLastBalaDate?'),"
                + " insuaccbala = (select sum(money) from lcinsureacctrace where polno =c.polno and paydate <= '?lastbaladate?')"
                + " where contno = '?contno?'";
            SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
            sqlbv5.sql(sql);
            sqlbv5.put("lastbaladate", lastbaladate);
            sqlbv5.put("beforeLastBalaDate", beforeLastBalaDate);
            sqlbv5.put("contno", mLPEdorItemSchema.getContNo());
            mMap.put(sqlbv5,"UPDATE");

            sql = "update lcinsureaccclass d set"
                + " baladate = '?lastbaladate?',"
                + " lastaccbala = (select sum(money) from lcinsureacctrace where polno =d.polno and payplancode = d.payplancode and paydate <= '?beforeLastBalaDate?'),"
                + " insuaccbala = (select sum(money) from lcinsureacctrace where polno =d.polno and payplancode = d.payplancode and paydate <=d.baladate)"
                + " where contno = '?contno?'";
            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
            sqlbv6.sql(sql);
            sqlbv6.put("lastbaladate", lastbaladate);
            sqlbv6.put("beforeLastBalaDate", beforeLastBalaDate);
            sqlbv6.put("contno", mLPEdorItemSchema.getContNo());
            mMap.put(sqlbv6,"UPDATE");

            sql = "update lcinsureaccfee e set"
                + " baladate = (select max(paydate) from lcinsureaccfeetrace where polno = e.polno),"
                + " fee = (select sum(fee) from lcinsureaccfeetrace where polno = e.polno)"
                + " where contno = '?contno?'";
            SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
            sqlbv7.sql(sql);
            sqlbv7.put("contno", mLPEdorItemSchema.getContNo());
            mMap.put(sqlbv7,"UPDATE");

            sql = "update lcinsureaccclassfee g set"
                + " baladate = (case when (select max(paydate) from lcinsureaccfeetrace where polno = g.polno and payplancode = g.payplancode and feecode = g.feecode) is not null then (select max(paydate) from lcinsureaccfeetrace where polno = g.polno and payplancode = g.payplancode and feecode = g.feecode) else (select min(paydate) from lcinsureaccfeetrace where polno = g.polno) end),"
                + " fee = (case when (select sum(fee) from lcinsureaccfeetrace where polno = g.polno and payplancode = g.payplancode and feecode = g.feecode) is not null then (select sum(fee) from lcinsureaccfeetrace where polno = g.polno and payplancode = g.payplancode and feecode = g.feecode) else 0 end)"
                + " where contno = '?contno?'";
            SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
            sqlbv8.sql(sql);
            sqlbv8.put("contno", mLPEdorItemSchema.getContNo());
            mMap.put(sqlbv8,"UPDATE");

            String polno = BqNameFun.getAnother(null,"LCPol","polno = mainpolno and contno",mLPEdorItemSchema.getContNo(),"PolNo");
            sql = "delete from loprtmanager where otherno = '?polno?' and code = '?code?' and standbyflag2 >='?lastbaladate?'";
            SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
            sqlbv9.sql(sql);
            sqlbv9.put("polno", polno);
            sqlbv9.put("code", PrintManagerBL.CODE_EdorINSUACC);
            sqlbv9.put("lastbaladate", lastbaladate);
            mMap.put(sqlbv9,"DELETE");

            sql = "delete from rebala where polno = '?polno?' and flag = '0'";
            SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
            sqlbv10.sql(sql);
            sqlbv10.put("polno", polno);
            mMap.put(sqlbv10,"DELETE");

            String bd = PubFun.calDate(lastbaladate,1,"D",null);
            sql = "insert into rebala values ('?polno?','?bd?','?strCurrentDate?','0','','缓缴期解除回退后重新结算','TSBackRe')";
            SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
            sqlbv11.sql(sql);
            sqlbv11.put("polno", polno);
            sqlbv11.put("bd", bd);
            sqlbv11.put("strCurrentDate", strCurrentDate);
            mMap.put(sqlbv11,"INSERT");
			
//			撤销续期
	        LCContDB canLCContDB = new LCContDB();
	        canLCContDB.setContNo(mLPEdorItemSchema.getContNo());
	        LCContSet canLCContSet = new LCContSet();
	        canLCContSet = canLCContDB.query();
			LJSPayDB tLJSPayDB = new LJSPayDB();
			String StrSQL = "select * from ljspay where otherno = '?otherno?' and othernotype in ('2','3')";
			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
			sqlbv12.sql(StrSQL);
			sqlbv12.put("otherno", mLPEdorItemSchema.getContNo());
			LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv12);
			if (tLJSPayDB.mErrors.needDealError()){
				CError.buildErr(this, "续期应收信息查询失败!");
				return false;
			}
			if (tLJSPaySet != null && tLJSPaySet.size() > 0){
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("SubmitFlag", "noSubmit");
				VData tVData = new VData();
				tVData.add(canLCContSet.get(1));
				tVData.add(tTransferData);
				tVData.add(mGlobalInput);

				IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
				if (!tIndiDueFeeCancelBL.submitData(tVData, "BQApp")){
					mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
					return false;
				}
				tVData.clear();
				tVData = tIndiDueFeeCancelBL.getResult();
				MMap tMMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
				if (tMMap != null){
					mMap.add(tMMap);
				}
			}
			
			mResult.clear();
			mResult.add(mMap);
		}catch (Exception e){
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorTSBackConfirmBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！ " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
