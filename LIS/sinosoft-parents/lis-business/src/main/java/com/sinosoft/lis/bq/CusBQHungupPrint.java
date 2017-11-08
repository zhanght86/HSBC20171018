package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 */
public class CusBQHungupPrint
{
private static Logger logger = Logger.getLogger(CusBQHungupPrint.class);

    public VData mResult = new VData();
    public CErrors mCErrors = new CErrors();
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mManagecom = "";
    private String currentdate = PubFun.getCurrentDate();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private ExeSQL tExeSQL = new ExeSQL();
    /**
     */
    public CusBQHungupPrint()
    {
    }

    public static void main(String[] args)
    {
    	CusBQHungupPrint rePlanPremRateBL1 = new CusBQHungupPrint();
        LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema(); 
        tLPEdorAppSchema.setEdorAcceptNo(""); //保全受理号
        tLPEdorAppSchema.setOtherNo(""); //申请号码
        tLPEdorAppSchema.setOtherNoType(""); //申请号码类型
        tLPEdorAppSchema.setEdorAppName(""); //申请人名称
        tLPEdorAppSchema.setAppType(""); //申请方式    
    	tLPEdorAppSchema.setEdorAppDate(""); //批改申请日期

    	GlobalInput tG = new GlobalInput();
        tG.Operator="001";

        VData tVData = new VData();

        tVData.addElement(tLPEdorAppSchema);
        tVData.addElement(tG);
        rePlanPremRateBL1.submitData(tVData,"");
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!queryData())
        {
            return false;
        }
        return true;
    }

    /**
     *
     */
    public boolean queryData()
    {
        XmlExport xmlexport = new XmlExport();
        TextTag texttag = new TextTag();
        ListTable tListTable = new ListTable();
        tListTable.setName("INFO");
        

        texttag.add("ManageCom", this.mGlobalInput.ManageCom);
        texttag.add("PrintDate", this.currentdate);
        texttag.add("Operator", this.mGlobalInput.Operator);
        
        texttag.add("ApplyCode", this.mLPEdorAppSchema.getUWOperator());
        texttag.add("ApplyName", this.mLPEdorAppSchema.getEdorAppName());
        texttag.add("ApplyDate", this.mLPEdorAppSchema.getEdorAppDate());
        
        texttag.add("NoticeNo", "");
        texttag.add("Address", "MSRS");
        texttag.add("EdorDate", this.currentdate);
        
        //处理保单挂起信息
        //查出该客户下所有保单
        BqContHangUpBL tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
		LCContSet tLCContSet = getCustomerCont(
				mLPEdorAppSchema.getUWOperator(), mLPEdorAppSchema
						.getEdorAppDate(), mLPEdorAppSchema.getAppType(),mLPEdorAppSchema.getOtherNo());
		if (tLCContSet == null || tLCContSet.size() < 1) {
			CError.buildErr(this, "该客户没有允许变更的保单!");
			return false;
		}
		String[] strr = new String[2];
		for (int k = 1; k <= tLCContSet.size(); k++) 
		{
			strr =new String[2];
			String tContNo =tLCContSet.get(k).getContNo();
			String sql=" select (select codealias from ldcode where codetype = 'conthanguptype' and code=a.hanguptype) from " 
				+" lcconthangupstate a where a.contno='"+"?tContNo?"+"' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("tContNo", tContNo);
			String tReason=tExeSQL.getOneValue(sqlbv);
			if(tReason!=null && !"".equals(tReason))
			{
				strr[0]=tContNo;
				strr[1]=tReason+"挂起";
				tListTable.add(strr);
			}
           //增加校验，看是否存在续期银行在途
			String sql2=" select 1 from ljspay a where a.othernotype='2' and a.otherno='"+"?tContNo?"+"' and a.bankonthewayflag='1'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql2);
			sqlbv2.put("tContNo", tContNo);
			String tReason2=tExeSQL.getOneValue(sqlbv2);
			if(tReason2!=null && "1".equals(tReason2))
			{
				strr[0]=tContNo;
				strr[1]="续期转帐在途";
				tListTable.add(strr);
			}
		}
        logger.debug("---------------------------------------------");

        xmlexport.createDocument("CusBQPrint.vts", "printer");
        xmlexport.addTextTag(texttag);
        strr =new String[2];
        xmlexport.addListTable(tListTable, strr);
        mResult.addElement(xmlexport);
        return true;
    }

    /**
     *
     */
    private boolean getInputData(VData cInputData)
    {
        try
        {
        	mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
    				"GlobalInput", 0);
    		mLPEdorAppSchema = (LPEdorAppSchema) cInputData.getObjectByObjectName(
    				"LPEdorAppSchema", 0);
    		return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     *
     */
    public VData getResult()
    {
        return this.mResult;
    }

	public LCContSet getCustomerCont(String sCustomerNo, String sDate,
			String sAppType ,String sContno) {
		LCContDB tLCContDB = new LCContDB();
		// 查询出客户以投保人和被保人身份相关的所有保单
		String sql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		 sql = "  select /*+RULE*/ * from lccont c "
				+ " where 1 = 1  and c.contno <>'"+"?sContno?"+"'"
				+ "and (c.GrpContNo is null or c.GrpContNo = '00000000000000000000') "
				+ "and c.contno in ( select contno from lcinsured where insuredno = '"
				+ "?sCustomerNo?"
				+ "' "
				+ " union " // 以投保人或被保人身份相关的保单
				+ " select contno from lcappnt where appntno = '"
				+ "?sCustomerNo?"
				+ "' ) "
				+ " and "
				+ " ( " // 保单未终止，并且主险未失效（失效保单不参与客户层变更）
				+ " ( appflag = '1' and not exists "
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Available') and trim(state) = '1' "
				+ " and ((startdate <= '"
				+ "?sDate?"
				+ "' and '"
				+ "?sDate?"
				+ "' <= enddate) or (startdate <= '"
				+ "?sDate?"
				+ "' and enddate is null))"
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno and rownum = 1) "
				+ " ) "
				+ " ) "
				+ " or "
				+ " ( appflag = '4' and exists " // 01-满期终止、05-自垫终止、06-贷款终止
													// 可以参与客户层变更
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Terminate') and trim(state) = '1' and trim(statereason) is not null and trim(statereason) in ('01', '05', '06', '09') "
				+ " and ((startdate <= '"
				+ "?sDate?"
				+ "' and '"
				+ "?sDate?"
				+ "' <= enddate) or (startdate <= '"
				+ "?sDate?"
				+ "' and enddate is null)) "
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno and rownum = 1) "
				+ " ) " + " ) " + " ) "
		// + " and CustomGetPolDate <= '" + sDate + "' "
		;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 sql = "  select * from lccont c "
					+ " where 1 = 1  and c.contno <>'"+"?sContno?"+"'"
					+ "and (c.GrpContNo is null or c.GrpContNo = '00000000000000000000') "
					+ "and c.contno in ( select contno from lcinsured where insuredno = '"
					+ "?sCustomerNo?"
					+ "' "
					+ " union " // 以投保人或被保人身份相关的保单
					+ " select contno from lcappnt where appntno = '"
					+ "?sCustomerNo?"
					+ "' ) "
					+ " and "
					+ " ( " // 保单未终止，并且主险未失效（失效保单不参与客户层变更）
					+ " ( appflag = '1' and not exists "
					+ " ( select 'X' from lccontstate s "
					+ " where trim(statetype) in ('Available') and trim(state) = '1' "
					+ " and ((startdate <= '"
					+ "?sDate?"
					+ "' and '"
					+ "?sDate?"
					+ "' <= enddate) or (startdate <= '"
					+ "?sDate?"
					+ "' and enddate is null))"
					+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno limit 0,1) "
					+ " ) "
					+ " ) "
					+ " or "
					+ " ( appflag = '4' and exists " // 01-满期终止、05-自垫终止、06-贷款终止
													// 可以参与客户层变更
					+ " ( select 'X' from lccontstate s "
					+ " where trim(statetype) in ('Terminate') and trim(state) = '1' and trim(statereason) is not null and trim(statereason) in ('01', '05', '06', '09') "
					+ " and ((startdate <= '"
					+ "?sDate?"
					+ "' and '"
					+ "?sDate?"
					+ "' <= enddate) or (startdate <= '"
					+ "?sDate?"
					+ "' and enddate is null)) "
					+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno limit 0,1) "
					+ " ) " + " ) " + " ) "
			// + " and CustomGetPolDate <= '" + sDate + "' "
			;	
		}
		if (sAppType == null
				|| (!sAppType.trim().equals("6") && !sAppType.trim()
						.equals("7"))) // 客户层保全对部门转办取消客户签收日期限制 2006-01-21
		{
			sql += " and CustomGetPolDate <= '" + "?sDate?" + "' ";
		}

		logger.debug(sql);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sContno", sContno);
		sqlbv.put("sCustomerNo", sCustomerNo);
		sqlbv.put("sDate", sDate);
		// =====UPD=====zhangtao=======2006-01-19========客户相关保单查询修改======END======================
		LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv);
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询客户相关保单失败!");
			return null;
		}

		return tLCContSet;
	}
	
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "AutoAscriptionBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mCErrors.addOneError(cError);
    }
}
