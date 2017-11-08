package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.BqBill;
import com.sinosoft.lis.f1print.BqNameFun;

//******************************************************************************


public class TLReportBoxTransferBL implements BqBill
{
private static Logger logger = Logger.getLogger(TLReportBoxTransferBL.class);

    //public EdorPayGetFormChangePrintBL() {}

    //==========================================================================

    //错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();

	// 输入数据
	private VData rInputData;

	private String rOperation;

	private String mRiskCode;

	private String mInsuAccNo;

	private String mStartdate;

	private GlobalInput rGlobalInput;

	private LOAccUnitPriceSchema rLOAccUnitPriceSchema = new LOAccUnitPriceSchema();

	private TransferData mTransferData = new TransferData();

	// 本类变量
	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private BqNameFun mBqNameFun = new BqNameFun();

	// 输出数据
	private XmlExport rXmlExport;

	private VData rResultData;
    //FormulaOne 打印模板文件
    public static final String FormulaOneVTS = "TLReportBoxTransfer.vts";

    //==========================================================================

    /**
     * 外部调用本类的业务处理接口
     * @param    VData
     * @param    String
     * @return   boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //logger.debug("\t@> EdorPayGetFormChangePrintBL.submitData() 开始");

        //接收参数
        if (cInputData == null)
        {
            CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
            logger.debug(
                    "\t@> EdorPayGetFormChangePrintBL.submitData() : 无法获取 InputData 数据！");
            return false;
        }
        else
        {
            rInputData = new VData();
            rInputData = (VData) cInputData.clone();
        }
        rOperation = (cOperate != null) ? cOperate.trim() : "";

        //----------------------------------------------------------------------

        //业务处理
        if (!getInputData())
        {
            return false;
        }
        if (!checkData())
        {
            return false;
        }
        if (!dealData())
        {
            return false;
        }
        if (!outputData())
        {
            return false;
        }
        /*chenliang zhushi
        //存入LOREPORTMANAGER
        if (!saveoutputData())
        {
            return false;
        }
end chenliang zhushi */
        //垃圾处理
        collectGarbage();

        //logger.debug("\t@> EdorPayGetFormChangePrintBL.submitData() 成功");
        return true;
    } //function submitData end

    //==========================================================================
    /**
     * 获取外部传入数据和校验必录字段的合法性
     * @param    null
     * @return   boolean
     */
    private boolean getInputData()
    {
    	rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> AccountDelayPrintBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		rLOAccUnitPriceSchema = (LOAccUnitPriceSchema) rInputData
				.getObjectByObjectName("LOAccUnitPriceSchema", 0);
		if (rLOAccUnitPriceSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mInsuAccNo = rLOAccUnitPriceSchema.getInsuAccNo();
		//mRiskCode = rLOAccUnitPriceSchema.getRiskCode();
		mStartdate = rLOAccUnitPriceSchema.getStartDate();
		return true;
    } //function getInputData end

    //==========================================================================
    /**
     * 根据传入的数据进行业务逻辑层的合法性校验
     * @param    null
     * @return   boolean
     */
    private boolean checkData()
    {
        logger.debug(
                "\t@> BoxTransferPrintBL.checkData() 开始-------------");

        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        //查询 划款记录
        String sql = " select * from LOBoxTransferTrace where " +
                     " insuaccno = '" + "?mInsuAccNo?" +
                     "' and valuedate = '" + "?mStartdate?" + "' and state <> '9'";
        sqlbv.sql(sql);
        sqlbv.put("mInsuAccNo", mInsuAccNo);
        sqlbv.put("mStartdate", mStartdate);
        /*chenliang zhushi
        LOBoxTransferTraceDB tLOBoxTransferTraceDB = new LOBoxTransferTraceDB();
        LOBoxTransferTraceSet tLOBoxTransferTraceSet = new
                LOBoxTransferTraceSet();

        try
        {
            tLOBoxTransferTraceSet = tLOBoxTransferTraceDB.executeQuery(sqlbv);
        }
        catch (Exception ex)
        {
            CError.buildErr(this, "查询BOX划款轨迹表出错！");
            return false;
        }
        if (tLOBoxTransferTraceSet == null ||
            tLOBoxTransferTraceSet.size() <= 0)
        {
            CError.buildErr(this, "未查询到" + mValueDate + "的已生效的划款记录！");
            return false;
        }

        //垃圾处理
        tLOBoxTransferTraceSet = null;
        tLOBoxTransferTraceDB = null;
end chenliang zhushi */
        logger.debug("\t@> EdorPayGetFormChangePrintBL.checkData() 成功");
        return true;
    } //function checkData end

        /**
	     * 本类的核心业务处理过程
	     * @param    null
	     * @return   boolean
	     */
	    private boolean dealData()
	    {
	        double tNBCount;
	        double tRNCount;
	        double tGPCount;
	        double tPGCount;
	        double tSRCount;
	        double tCLCount;
	        double tAGCount;
	        double tRFCount;
	        double tAMFCount;
	        double tMFCount;
	        double tADCount;
	        double tTransferAmnt;
	        double tUnitPriceSell;
	
	        logger.debug("\t@> BoxTransferPrintBL.dealData() 开始");
	        ExeSQL tExeSQL = new ExeSQL();
	        String QuerySQL = new String("");
	        SSRS tssrs = new SSRS();
	        TextTag tTextTag = new TextTag();
	
	        //----------------------------------------------------------------------
	        //输出 XML 文档
	        rXmlExport = new XmlExport();
	        try
	        {
	            rXmlExport.createDocument(FormulaOneVTS, "printer"); //初始化 XML 文档
	        }
	        catch (Exception ex)
	        {
	            CError.buildErr(this, "初始化打印模板错误！");
	            logger.debug(
	                    "\t@> BoxTransferPrintBL.dealData() : 设置 FormulaOne VTS 文件异常！");
	            return false;
	        }
	
	        //----------------------------------------------------------------------
	        rXmlExport.addDisplayControl("divReportTitle");
	        rXmlExport.addDisplayControl("divReportInfo");
	        //表头：计价日期、帐户名称、单位价格
	        QuerySQL = " select m.insuaccname, r.unitpricebuy, r.unitpricesell " +
	                   "  from loaccunitprice r, lmriskinsuacc m " +
	                   " where m.insuaccno = r.insuaccno and r.state = '0' " +
	                   "  and r.insuaccno = '" + "?mInsuAccNo?" +
	                   "' and startdate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd')";
	        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	        sqlbv.sql(QuerySQL);
	        sqlbv.put("mInsuAccNo", mInsuAccNo);
	        sqlbv.put("mStartdate", mStartdate);
	        
	        tssrs = tExeSQL.execSQL(sqlbv);
	        if (tExeSQL.mErrors.needDealError())
	        {
	            CError.buildErr(this, "单位价格查询失败!");
	            return false;
	        }
	        if (tssrs == null || tssrs.getMaxRow() < 1)
	        {
	            CError.buildErr(this, "单位价格查询失败!");
	            return false;
	        }
	        tTextTag.add("ValueDate", mStartdate); //计价日
	        tTextTag.add("InsuAccName", tssrs.GetText(1, 1));//账户名称
	        tTextTag.add("UnitPriceBuy", //买入价
	                     mBqNameFun.getRound(tssrs.GetText(1, 2), "0.000000"));
	        tTextTag.add("UnitPriceSell", //卖出价
	                     mBqNameFun.getRound(tssrs.GetText(1, 3), "0.000000"));
	        
	        QuerySQL = "Select codealias From ldcurrency a Where Exists(Select 1 From lmriskinsuacc Where insuaccno = '"+"?mInsuAccNo?"+"' And currency =a.currcode)";
	        SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
	        sqlbv12.sql(QuerySQL);
	        sqlbv12.put("mInsuAccNo", mInsuAccNo);
	        
	        tTextTag.add("Currency", tExeSQL.getOneValue(sqlbv12));
	        
	        //----------------------------------------------------------------------
	        //账户信息
	
	        QuerySQL = " select (case when sum(unitcount) is not null then sum(unitcount)  else 0 end)"
	        			+ " from  LCInsureAcctrace  "
	        			+ " where 1=1  and state <> 0 and insuaccno = '" + "?mInsuAccNo?" + "'  "
	        			+ " and valuedate < to_date('" + "?mStartdate?" + "','yyyy-mm-dd')";
	        SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
	        sqlbv11.sql(QuerySQL);
	        sqlbv11.put("mInsuAccNo", mInsuAccNo);
	        sqlbv11.put("mStartdate", mStartdate);
	        	
	        tssrs = tExeSQL.execSQL(sqlbv11);
	        double LastCustomerUnitCount=0;
	        if (tssrs != null && tssrs.getMaxRow() > 0)
	        {
	        	LastCustomerUnitCount=Double.parseDouble(tssrs.GetText(1, 1));
	        	tTextTag.add("LastCustomerUnitCount",
	                    mBqNameFun.getRound(tssrs.GetText(1, 1), "0.000000"));
	        }
	        else
	        {
	        	tTextTag.add("LastCustomerUnitCount","0.000000");
	        }
	        tssrs = null;
	        
	        QuerySQL = " select (case when sum(unitcount) is not null then sum(unitcount)  else 0 end)"
				+ " from  LCInsureAcctrace  "
				+ " where 1=1  and state <> 0 and insuaccno = '" + "?mInsuAccNo?" + "'  "
				+ " and valuedate <= to_date('" + "?mStartdate?" + "','yyyy-mm-dd')";
	        SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
	        sqlbv13.sql(QuerySQL);
	        sqlbv13.put("mInsuAccNo", mInsuAccNo);
	        sqlbv13.put("mStartdate", mStartdate);
	        tssrs = tExeSQL.execSQL(sqlbv13);
	        double CustomerUnitCount=0;
	        if (tssrs != null && tssrs.getMaxRow() > 0)
	        {
	        	CustomerUnitCount=Double.parseDouble(tssrs.GetText(1, 1));
	        	tTextTag.add("CustomerUnitCount",
	                    mBqNameFun.getRound(tssrs.GetText(1, 1), "0.000000"));
	        }
	        else
	        {
	        	tTextTag.add("CustomerUnitCount","0.000000");
	        }            
	        tssrs = null;
	        
	        //----------------------------------------------------------------------
	        //box信息
	        //select companyunitcount,comchgunitcount from loaccunitprice 
	        QuerySQL="select companyunitcount,comchgunitcount from loaccunitprice "
	        		+ " where 1=1  and state = 0 and insuaccno = '" + "?mInsuAccNo?" + "'  "
	        		+ " and startdate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd')";
	        SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
	        sqlbv14.sql(QuerySQL);
	        sqlbv14.put("mInsuAccNo", mInsuAccNo);
	        sqlbv14.put("mStartdate", mStartdate);
	        tssrs = tExeSQL.execSQL(sqlbv14);
	        double LastBoxUnitCount=0;
	        double BoxUnitCountChg=0;
	        double BoxUnitCount=0;
	        
	        if (tssrs != null && tssrs.getMaxRow() > 0)
	        {
	        	LastBoxUnitCount=Double.parseDouble(tssrs.GetText(1, 1));
	        	BoxUnitCountChg=Double.parseDouble(tssrs.GetText(1, 2));
	
	        	tTextTag.add("LastBoxUnitCount",
	                    mBqNameFun.getRound(LastBoxUnitCount, "0.000000"));
	        	tTextTag.add("BoxUnitCountChg",
	                    mBqNameFun.getRound(BoxUnitCountChg, "0.000000"));
	        	BoxUnitCount=LastBoxUnitCount+BoxUnitCountChg;
	        	tTextTag.add("BoxUnitCount",
	                    mBqNameFun.getRound(BoxUnitCount, "0.000000"));
	        }
	        else
	        {
	        	tTextTag.add("LastBoxUnitCount","0.000000");
	        	tTextTag.add("BoxUnitCountChg","0.000000");
	        	tTextTag.add("BoxUnitCount","0.000000");
	        } 
	//-----------------------------------------------------------------------------------------------------------------
	        //客户单位数统计
	        QuerySQL = "select  "
	                   + " (case when sum((case busytype when 'NB' then unitcount else 0 end)) is not null then sum((case busytype when 'NB' then unitcount else 0 end))  else 0 end) BF_NB , " //新契约买入
	                   + " (case when sum((case busytype when 'RN' then (case moneytype when 'BF' then unitcount else 0 end) else 0 end)) is not null then sum((case busytype when 'RN' then (case moneytype when 'BF' then unitcount else 0 end) else 0 end))  else 0 end) TF_RN," //续期保费买入 
	                   + " (case when sum((case busytype when 'IP' then (case moneytype when 'BF' then unitcount else 0 end) else 0 end)) is not null then sum((case busytype when 'IP' then (case moneytype when 'BF' then unitcount else 0 end) else 0 end))  else 0 end) BF_IP," //追加保费
	                   + " 0,"//持续奖金买入
	                   + " (case when sum((case busytype when 'TI' then (case moneytype when 'IN' then unitcount else 0 end) else 0 end)) is not null then sum((case busytype when 'TI' then (case moneytype when 'IN' then unitcount else 0 end) else 0 end))  else 0 end) BF_in ," //其他账户转入
	                   + " (case when sum((case busytype when 'IT' then (case moneytype when 'TF' then unitcount else 0 end) else 0 end)) is not null then sum((case busytype when 'IT' then (case moneytype when 'TF' then unitcount else 0 end) else 0 end))  else 0 end) TF_IT," //犹豫期退费赎回
	                   + " (case when sum((case busytype when 'AR' then (case moneytype when 'AR' then unitcount else 0 end) else 0 end)) is not null then sum((case busytype when 'AR' then (case moneytype when 'AR' then unitcount else 0 end) else 0 end))  else 0 end) AR_AR ," //部分领取赎回
	                   + " (case when sum((case busytype when 'TI' then (case moneytype when 'TI' then unitcount else 0 end) else 0 end)) is not null then sum((case busytype when 'TI' then (case moneytype when 'TI' then unitcount else 0 end) else 0 end))  else 0 end) TI_TI," //转出赎回
	                   + " (case when sum((case busytype when 'CT' then (case moneytype when 'TB' then unitcount else 0 end) else 0 end)) is not null then sum((case busytype when 'CT' then (case moneytype when 'TB' then unitcount else 0 end) else 0 end))  else 0 end) TB_CT, " //退保赎回
	                   + " (case when sum((case busytype when 'CL' then unitcount else 0 end)) is not null then sum((case busytype when 'CL' then unitcount else 0 end))  else 0 end) PK_CL," //理赔赎回
	                   + " (case when sum((case busytype when 'AG' then (case moneytype when 'EF' then unitcount else 0 end) else 0 end)) is not null then sum((case busytype when 'AG' then (case moneytype when 'EF' then unitcount else 0 end) else 0 end))  else 0 end) EF_AG," //满期赎回
	                   + " (case when sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when 'RPUL07' then unitcount else 0 end) else 0 end) else 0 end)) is not null then sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when 'RPUL07' then unitcount else 0 end) else 0 end) else 0 end))  else 0 end) GL_001," //风险保费扣除
	                   + " (case when sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when '890000' then unitcount else 0 end) else 0 end) else 0 end)) is not null then sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when '890000' then unitcount else 0 end) else 0 end) else 0 end))  else 0 end) GL_002," //保单管理费扣除
	                   //+ " sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when '890002' then unitcount else 0 end) else 0 end) else 0 end)) GL_003," //资产管理费扣除
	                   + " (case when sum((case when shouldvaluedate <> valuedate then unitcount else 0 end)) is not null then sum((case when shouldvaluedate <> valuedate then unitcount else 0 end))  else 0 end) AD , " //延迟处理（投资单位调整数）
	                   + " (case when sum((case busytype when 'EB' then unitcount else 0 end)) is not null then sum((case busytype when 'EB' then unitcount else 0 end))  else 0 end) EB," // //签报纠错（差错回退）
	                   + " (case when sum(UnitCount) is not null then sum(UnitCount)  else 0 end) " //单位数净流量
	                   + " from  LCInsureAcctrace  a"
	                   + " where 1=1  and state <> 0 and a.insuaccno = '" + "?mInsuAccNo?" + "'  "
	                   + " and a.valuedate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd')";
	        SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
	        sqlbv15.sql(QuerySQL);
	        sqlbv15.put("mInsuAccNo", mInsuAccNo);
	        sqlbv15.put("mStartdate", mStartdate);
	        tssrs = tExeSQL.execSQL(sqlbv15);
	        if (tExeSQL.mErrors.needDealError())
	        {
	            CError.buildErr(this, "数据查询失败!");
	            return false;
	        }
	        if (tssrs == null || tssrs.getMaxRow() < 1)
	        {
	            CError.buildErr(this, "数据查询失败!");
	            return false;
	        }
	
	        //新契约保费买入
	        tTextTag.add("NBCount", mBqNameFun.getRound(tssrs.GetText(1, 1), "0.000000"));
	        //续期保费买
	        tTextTag.add("RNCount", mBqNameFun.getRound(tssrs.GetText(1, 2), "0.000000"));
	        //追加保费买入
	        tTextTag.add("IPCount", mBqNameFun.getRound(tssrs.GetText(1, 3), "0.000000"));
	        //持续金买入
	        tTextTag.add("CXCount", mBqNameFun.getRound(tssrs.GetText(1, 4), "0.000000"));
	        //其他账户转入
	        tTextTag.add("INCount", mBqNameFun.getRound(tssrs.GetText(1, 5), "0.000000"));
	        //犹豫期退费赎回
	        tTextTag.add("TFCount", mBqNameFun.getRound(tssrs.GetText(1, 6), "0.000000"));
	        //部分领取赎回
	        tTextTag.add("ARCount", mBqNameFun.getRound(tssrs.GetText(1, 7), "0.000000"));
	        //转出赎回
	        tTextTag.add("TICount", mBqNameFun.getRound(tssrs.GetText(1, 8), "0.000000"));
	        //退保赎回
	        tTextTag.add("TBCount", mBqNameFun.getRound(tssrs.GetText(1, 9), "0.000000"));
	        //理赔赎回
	        tTextTag.add("CLCount", mBqNameFun.getRound(tssrs.GetText(1, 10), "0.000000"));
	        //满期赎回
	        tTextTag.add("EFCount", mBqNameFun.getRound(tssrs.GetText(1, 11), "0.000000"));
	        //风险保费扣除
	        tTextTag.add("GL1Count", mBqNameFun.getRound(tssrs.GetText(1, 12), "0.000000"));
	        //保单管理费扣除
	        tTextTag.add("GL3Count", mBqNameFun.getRound(tssrs.GetText(1, 13), "0.000000"));
	        //投资单位调整数
	        tTextTag.add("ADCount", mBqNameFun.getRound(tssrs.GetText(1, 14), "0.000000"));
	        //纠错回退
	        tTextTag.add("EBCount", mBqNameFun.getRound(tssrs.GetText(1, 15), "0.000000"));
	        //净流量
	        tTextTag.add("CustomUnitCountChg", mBqNameFun.getRound(tssrs.GetText(1, 16), "0.000000"));
	
	        double LastUnitCount=LastCustomerUnitCount+LastBoxUnitCount;
	        double CurUnitCount=CustomerUnitCount+BoxUnitCount;
	        tTextTag.add("LastUnitCount", mBqNameFun.getRound(LastUnitCount, "0.000000"));
	        tTextTag.add("CurUnitCount", mBqNameFun.getRound(CurUnitCount, "0.000000"));
	        
	        //输出数据
	        rXmlExport.addTextTag(tTextTag);
	        //垃圾处理
	        tTextTag = null;
	        tExeSQL = null;
	
	        //logger.debug("\t@> EdorPayGetFormChangePrintBL.dealData() 成功");
	        return true;
	    }

    //==========================================================================

    /**
     * 准备经过本类处理的输出数据
     * @param    null
     * @return   boolean
     */
    private boolean outputData()
    {
        //logger.debug("\t@> EdorPayGetFormChangePrintBL.outputData() 开始");

        rResultData = new VData();
        rResultData.addElement(rXmlExport);

        //logger.debug("\t@> EdorPayGetFormChangePrintBL.outputData() 成功");
        return true;
    } //function prepareOutputData end

    //==========================================================================

    /**
     * 将输出数据保存在LOREPORTMANAGER表中
     * @param    null
     * @return   boolean
     */
    private boolean saveoutputData()
    {
        //LOReportManagerSchema
        LOReportManagerSchema tLOReportManagerSchema = new
                LOReportManagerSchema();
        String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", 20); //生成印刷流水号
        tLOReportManagerSchema.setPrtSeq(tPrtSeq);
        tLOReportManagerSchema.setReportType("1");
        tLOReportManagerSchema.setReportCode("tl_101"); //等代码定了以后再改
        tLOReportManagerSchema.setPrtFlag("0");
        tLOReportManagerSchema.setPrtTimes(0);
        tLOReportManagerSchema.setFilters("86");
        tLOReportManagerSchema.setCreateManageCom("86"); //可能需要修改，定义成变量
        tLOReportManagerSchema.setManageCom("86");
        tLOReportManagerSchema.setTemplate(FormulaOneVTS);
        tLOReportManagerSchema.setOperator("001");
        tLOReportManagerSchema.setMakeDate(CurrentDate);
        tLOReportManagerSchema.setMakeTime(CurrentTime);
        tLOReportManagerSchema.setModifyDate(CurrentDate);
        tLOReportManagerSchema.setModifyTime(CurrentTime);
        tLOReportManagerSchema.setReportInfo(rXmlExport.getInputStream());
        //LOReportParamSchema
        LOReportParamSchema tLOReportParamSchema = new LOReportParamSchema();
        tLOReportParamSchema.setPrtSeq(tPrtSeq);
        tLOReportParamSchema.setParamName("InsuAccNo");
        tLOReportParamSchema.setParamValue(mInsuAccNo);
        tLOReportParamSchema.setOperator("001");
        tLOReportParamSchema.setMakeDate(CurrentDate);
        tLOReportParamSchema.setMakeTime(CurrentTime);
        tLOReportParamSchema.setModifyDate(CurrentDate);
        tLOReportParamSchema.setModifyTime(CurrentTime);

        LOReportParamSchema tLOReportParamSchema1 = new LOReportParamSchema();
        tLOReportParamSchema1.setPrtSeq(tPrtSeq);
        tLOReportParamSchema1.setParamName("ValueDate");
        tLOReportParamSchema1.setParamValue(mStartdate);
        tLOReportParamSchema1.setOperator("001");
        tLOReportParamSchema1.setMakeDate(CurrentDate);
        tLOReportParamSchema1.setMakeTime(CurrentTime);
        tLOReportParamSchema1.setModifyDate(CurrentDate);
        tLOReportParamSchema1.setModifyTime(CurrentTime);

        //提交后台
        MMap tmap = new MMap();
        tmap.put(tLOReportParamSchema, "INSERT");
        tmap.put(tLOReportParamSchema1, "INSERT");
        tmap.put(tLOReportManagerSchema, "BLOBINSERT");
        VData pVData = new VData();
        PubSubmit tPubSubmit = new PubSubmit();
        pVData.add(tmap);
        if (!tPubSubmit.submitData(pVData, "PRINT"))
        {
            logger.debug("信息提交失败！");
            return false;
        }

        return true;
    } //function saveoutputData end

    //==========================================================================

    /**
     * 返回经过本类处理的数据结果
     * @param    null
     * @return   VData
     */
    public VData getResult()
    {
        return rResultData;
    } //function getResult end

    //==========================================================================

    /**
     * 返回传入本类的操作类型
     * @param    null
     * @return   String
     */
    public String getOperation()
    {
        return rOperation;
    } //function getResult end

    //==========================================================================

    /**
     * 返回本类运行时产生的错误信息
     * @param    null
     * @return   CErrors
     */
    public CErrors getErrors()
    {
        return mErrors;
    } //function getErrors end

    //==========================================================================

    /**
     * 处理本类运行时产生的垃圾
     * @param    null
     */
    private void collectGarbage()
    {
        if (rInputData != null)
        {
            rInputData = null;
        }
        if (rGlobalInput != null)
        {
            rGlobalInput = null;
        }
        if (rXmlExport != null)
        {
            rXmlExport = null;
        }
    } //function collectGarbage end

    //==========================================================================

    /**
     * 调试主程序业务方法
     * @param    String[]
     */
    /*public static void main(String[] args)
    {
        logger.debug("test start:");
        VData tVData = new VData();

        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ManageCom = "86110000";
        tGlobalInput.Operator = "001";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("InsuAccNo", "890000");
        tTransferData.setNameAndValue("ValueDate", "2007-06-15");
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);
        TLReportBoxTransferBL tInsuAccReportBoxTransferPrintBL = new TLReportBoxTransferBL();
        if (!tInsuAccReportBoxTransferPrintBL.submitData(tVData, ""))
        {
            logger.debug("test failed:" +
                               tInsuAccReportBoxTransferPrintBL.mErrors.getFirstError().
                               toString());
        }
        logger.debug("test end");

    } //function main end
*/
    //==========================================================================

} //class EdorPayGetFormChangePrintBL end
