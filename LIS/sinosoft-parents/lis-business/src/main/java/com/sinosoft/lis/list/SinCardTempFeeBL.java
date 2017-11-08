package com.sinosoft.lis.list;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.ebusiness.*;

  

public class SinCardTempFeeBL {
private static Logger logger = Logger.getLogger(SinCardTempFeeBL.class);

	/**
	 * SinCardTempFeeBL 卡单收费业务逻辑处理
	 * 2008-3-10  zy
	 */
	/** 往界面传输数据的容器 */
	private VData mReslut = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 存储前台传递的暂交费数据 */
	private TransferData tTransferData = new TransferData();

	/** 存储登录数据*/
	private GlobalInput tG = new GlobalInput();
	private String mStartNo = "";          //卡单起始号
	private String mEndNo = "";           //卡单终止号
	public CErrors mErrors = new CErrors();

	private String mRiskCode;              //险种编码
	private double mPayMoney;              //交费金额
	private String mManageCom;            //交费机构
	private String mAgentCode;            //代理人编码
	private String mPayDate;              //交费日期
	private String mPayIntv;              //缴费方式
	private String mPayYears;             //缴费期间
	private String mCPayMode;             //交费方式
	private double mCPayMoney;            //交费金额
	private String mCChequeNo;            //支票号
	private String mCEnterAccDate;        //到帐日期
	private String mBankcode;             //银行编码
	private String mBankaccno;            //银行帐号
	private String mAccname;              //账户
	private String mAgentGroup;           //代理人组别
	private String tLimit = "";          //流水号限制
	private String serNo = ""; //流水号
	private String certifycode = "";       //单证编码
	private String mEnterAccDate = "";    //到账日期
//	private String mOtherNo = "";			//其它号码
//	private String mComment = "";			//备注
	private String mCPayDate = "";			//交费日期
	private String mCManageCom = "";		//管理机构
	private String mChequeDate = "";		//支票日期
	private String mIDType = "";			//证件类型
	private String mIDNo = "";				//证件号码
	//private String mSaleChnl = "";          //销售渠道
	private double prem;                  //卡单保费
	

	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();         //暂交费
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();//暂交费分类
	
	private MMap map=new MMap();//提交数据库的Map

	public boolean submitData(VData cInputData, String cOperate) {
		
		logger.debug("Begin SinCardTempFeeBL------");
		mOperate = cOperate;
       try
       {
    	   //先就支持插入的操作吧
		if (!"INSERT".equals(mOperate)) {
			CError.buildErr(this, "不支持的操作字符");
			return false;
		}
		
		logger.debug("Begin getInputData------");

		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("Begin checkData------");
		if (!checkData()) {
			return false;
		}

		logger.debug("Begin dealData------");
		if (!dealData()) {
			return false;
		}

		logger.debug("Begin prepareOutputData------");
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug(" PubSubmit------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "INSERT")) {
			return false;
		}
       }
       catch (Exception ex)
       {
    	   ex.printStackTrace();
    	   return false;
       }

		return true;
	}

	private boolean getInputData(VData mInputData) {


		tTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		tG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mStartNo = (String) tTransferData.getValueByName("tStartNo");
		mEndNo = (String) tTransferData.getValueByName("tEndNo");
		
		if (mStartNo == null || mStartNo.equals("")) {
			CError.buildErr(this, "卡单起始号为空");
			return false;

		}
		if (mEndNo == null || mEndNo.equals("")) {
			CError.buildErr(this, "卡单终止号为空");
			return false;

		}
		if (tG == null || tG.equals("")) {
			CError.buildErr(this, "没有获取到登录信息");
			return false;

		}

		mRiskCode  = getValue("tRiskCode");
		mPayMoney  = Double.parseDouble((String) getValue("tPayMoney"));
		mManageCom = (String) getValue("tManageCom");
		mAgentCode = (String) getValue("tAgentCode");
		mPayDate   = (String) getValue("tPayDate");
		mPayIntv   = (String) getValue("tPayIntv");
		mPayYears  = (String) getValue("tPayYears");
		mCPayMode  = (String) getValue("tCPayMode");
		mCPayMoney = Double.parseDouble((String) getValue("tCPayMoney"));
		mCChequeNo = (String) getValue("tCChequeNo");
		if(mCChequeNo == null) mCChequeNo = "000000";
		mCEnterAccDate = (String) getValue("tCEnterAccDate");
		mBankcode = (String) getValue("tBankcode");
		mBankaccno = (String) getValue("tBankaccno");
		mAccname = (String) getValue("tAccname");
		//有什么区别
		mEnterAccDate = (String) getValue("tEnterAccDate");
		mAgentGroup = (String) getValue("tAgentGroup");
//		mOtherNo = (String) getValue("tOtherNo");没有使用
//		mComment = (String) getValue("tComment");
		mCPayDate = (String) getValue("tCPayDate");
		mCManageCom = (String) getValue("tCManageCom");
		mChequeDate = (String) getValue("tChequeDate");
		mIDType = (String) getValue("tIDType");
		mIDNo = (String) getValue("tIDNo");
		
		return true;

	}
	
    //当传递的参数值为空，则不进行强制转换，否则会报错滴！
	private String getValue(String str) {
		if(tTransferData.getValueByName(str)==null || tTransferData.getValueByName(str).equals(""))
			return null;
		return (String) tTransferData.getValueByName(str);
	}

	private boolean checkData() {


		//如果是现金交费-那么存入当天系统时间,如果是支票、转账，则置空；此外虽然界面的到帐日期可以录入，但是目前是不起作用的，忽悠
		if ("1".equals(mCPayMode)||"2".equals(mCPayMode)) {            //现金支票即时到帐
			mCEnterAccDate = PubFun.getCurrentDate();
		} else if ( "3".equals(mCPayMode) || "4".equals(mCPayMode)) {
			mCEnterAccDate = "";
		}

		//金额不能为负
		if (mPayMoney < 0 || mCPayMoney < 0) {
			CError.buildErr(this, "交费金额不能小于0");

			return false;
		}
		
		if ((mPayMoney - mCPayMoney) != 0) {
			CError.buildErr(this, "暂交费表录入的金额与暂交费分类表录入的金额不符！");
			return false;
		}

//		logger.debug("业务到帐日期---------" + mCEnterAccDate);

		LAAgentDB tAgentDB = new LAAgentDB();
		tAgentDB.setAgentCode(mAgentCode);
		if (!tAgentDB.getInfo()) {
			CError.buildErr(this, "不存在该代理人相关信息");
			return false;
		}

		if(!getPrem())
		{
			return false;
		}

		return true;

	}

	
	//业务逻辑处理
	private boolean dealData() {

		String Symbol = mStartNo.substring(0, 12);
		logger.debug("卡单流水号之前的标识-----" + Symbol);
		int b = Integer.parseInt(mStartNo.substring(12));
		int e = Integer.parseInt(mEndNo.substring(12));
		logger.debug("该批起始号--------" + mStartNo);
		logger.debug("该批终止号---------" + mEndNo);
		logger.debug("该批起始流水号--------" + b);
		logger.debug("该批终止流水号---------" + e);
		String polno = "";
		String s = "";
        //一批卡单需要一张一张的记录交费信息！
		for (int i = b; i <= e; i++) 
		{
			s = PubFun.LCh(String.valueOf(i), "0", 8);
			polno = Symbol + s;
			tLimit = PubFun.getNoLimit(mManageCom);
			serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			LJTempFeeSchema mFeeSchema = new LJTempFeeSchema();
			LJTempFeeClassSchema mClassSchema = new LJTempFeeClassSchema();
			logger.debug("正在处理的卡单---------" + polno);
			
			if (!isExist(polno)) {
				return false;
			}
         
			mFeeSchema = setLjtempfee(polno);
			mClassSchema = setLjtempfeeClass(polno);

			logger.debug("交费赋值完成啦---------");
			mLJTempFeeSet.add(mFeeSchema);
			mLJTempFeeClassSet.add(mClassSchema);
		}
		
		
//		/**
//		 * 2008-06-19 1期增加在交完费后对单证的自动回收处理，后改为调用自动运行程序进行回收
//		 **/
//		if(!takeBackCertify())
//		{
//			return false;
//		}


		return true;

	}
	
	
	/**
	 * 2008-06-19 1期增加在交完费后对单证的自动回收处理
	 **/
	private boolean takeBackCertify()
	{
		
		
		/**
		 * 2008-06-19 1期增加在交完费后对单证的自动回收处理
		 **/
		//从卡单费率表中根据保费和险种编码取得ProductPlan(产品计划)
		RateCardDB tRateCardDB=new RateCardDB();
		logger.debug("卡单"+certifycode+":查询费率表的RiskCode:"+mLJTempFeeSet.get(1).getRiskCode());
		logger.debug("卡单"+certifycode+":查询费率表的Prem:"+prem);
		tRateCardDB.setRiskCode(mLJTempFeeSet.get(1).getRiskCode());
		tRateCardDB.setPrem(prem);
		if (!tRateCardDB.getInfo()) 
		{
			CError.buildErr(this, "卡单费率表描述有误，请核对");
			return false;
        }
		
		logger.debug("卡单"+certifycode+":查询费率表的ProuductPlan:"+tRateCardDB.getProductPlan());
		
		String tSendoutCom="";//卡单发放机构
		String tReceiveCom="";//卡单接收机构
		int tSumCount=0;//记录数量
		String tTackBackNo="";//回收清算单号

		
		//1--表示该产品形态目前处以一期状态,即单证只发放到四级机构，由于一期单证只发放到四级机构，所以用sql直接修改数据库的方式修改单证状态
		if(tRateCardDB.getProductPlan().equals("1"))
		{
			String sql="select sendoutcom,receivecom,sumcount,takebackno from lzcard where certifycode='?certifycode?' "
			           +" and startno='?mStartNo?' and endno='?mEndNo?' and stateflag='0' and operateflag='0'" ;
			logger.debug("查询起始号是"+mStartNo+",终止号是"+mEndNo+"在数据库中的记录的sql:"+sql);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("certifycode", certifycode);
			sqlbv.put("mStartNo", mStartNo);
			sqlbv.put("mEndNo", mEndNo);
			ExeSQL tExeSQL=new ExeSQL();
			SSRS tSSRS=new SSRS();
			tSSRS=tExeSQL.execSQL(sqlbv);
			if(tSSRS.getMaxRow()==1)
			{
				tSendoutCom=tSSRS.GetText(1,1);
				tReceiveCom=tSSRS.GetText(1,2);
				tSumCount=Integer.parseInt(tSSRS.GetText(1,3));
				tTackBackNo=tSSRS.GetText(1,4);
			}
			else
			{
				CError.buildErr(this, "卡单单证记录在数据库中出现多条记录,请核对再进行缴费");
				return false;
			}
			
			logger.debug("查询起始号是"+mStartNo+",终止号是"+mEndNo+"在数据库中的发放机构是:"+tSendoutCom);
			logger.debug("查询起始号是"+mStartNo+",终止号是"+mEndNo+"在数据库中的接收机构是:"+tReceiveCom);
			logger.debug("查询起始号是"+mStartNo+",终止号是"+mEndNo+"在数据库中的数量是:"+tSumCount);
			logger.debug("查询起始号是"+mStartNo+",终止号是"+mEndNo+"在数据库中的回收清算单号是:"+tTackBackNo);
			
			//更新状态表中信息
			String updateLzcard="update lzcard set stateflag='1',operateflag='1',sendoutcom='?tReceiveCom?',receivecom='?tSendoutCom?',operator='KD',modifydate='?modifydate?',modifytime='?modifytime?'"
			                   +" where certifycode='?certifycode?'"
			                   +" and startno='?mStartNo?'"
			                   +" and endno='?mEndNo?'"
			                   +" and sendoutcom='?tSendoutCom?'"
			                   +" and receivecom='?tReceiveCom?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(updateLzcard);
			sqlbv1.put("tReceiveCom", tReceiveCom);
			sqlbv1.put("tSendoutCom", tSendoutCom);
			sqlbv1.put("modifydate", PubFun.getCurrentDate());
			sqlbv1.put("modifytime", PubFun.getCurrentTime());
			sqlbv1.put("certifycode", certifycode);
			sqlbv1.put("mStartNo", mStartNo);
			sqlbv1.put("mEndNo", mEndNo);
			sqlbv1.put("tReceiveCom", tReceiveCom);
			
			
			logger.debug("查询起始号是"+mStartNo+",终止号是"+mEndNo+"在卡单缴费后更新lzcard的SQL:"+updateLzcard);
			
			//插入一条新的轨迹
			LZCardTrackSchema tLZCardTrackSchema=new LZCardTrackSchema();
			tLZCardTrackSchema.setCertifyCode(certifycode);
			tLZCardTrackSchema.setSubCode("0");
			tLZCardTrackSchema.setRiskCode("0");
			tLZCardTrackSchema.setRiskVersion("0");
			tLZCardTrackSchema.setStartNo(mStartNo);
			tLZCardTrackSchema.setEndNo(mEndNo);
			tLZCardTrackSchema.setSendOutCom(tReceiveCom);
			tLZCardTrackSchema.setReceiveCom(tSendoutCom);
			tLZCardTrackSchema.setSumCount(tSumCount);
			tLZCardTrackSchema.setPrem("0.00");
			tLZCardTrackSchema.setAmnt("0.00");
			tLZCardTrackSchema.setHandleDate(PubFun.getCurrentDate());
			tLZCardTrackSchema.setTakeBackNo(tTackBackNo);
			tLZCardTrackSchema.setStateFlag("1");//缴销(正常回收)
			tLZCardTrackSchema.setOperateFlag("1");//回收
			tLZCardTrackSchema.setOperator("KD");//操作员:自助卡丹
			tLZCardTrackSchema.setMakeDate(PubFun.getCurrentDate());
			tLZCardTrackSchema.setMakeTime(PubFun.getCurrentTime());
			tLZCardTrackSchema.setModifyDate(PubFun.getCurrentDate());
			tLZCardTrackSchema.setModifyTime(PubFun.getCurrentTime());
			
			map.put(sqlbv1, "UPDATE");
			map.put(tLZCardTrackSchema, "INSERT");
		}
		
		
		return true;
	}

	
	/**
	 * 获取卡单的保费及对交费金额与保费是否一致的校验，钱交多了对卡单是不行的！
	 **/
	private boolean getPrem() {
		
		prem = 0; //获取卡单保费
		int n = Integer.parseInt(mEndNo.substring(12)) - Integer.parseInt(mStartNo.substring(12));
//		logger.debug("Integer.parseInt(mEndNo.substring(12,19))"+ Integer.parseInt(mEndNo.substring(12)));
//		logger.debug("Integer.parseInt(mStartNo.substring(12,19))"+ Integer.parseInt(mStartNo.substring(12)));
		//获取卡单的单证类型
//		ActivateBL tActivateBL = new ActivateBL();
		certifycode = this.GetCertifyType(mStartNo);
		LMCardRiskDB tLMCardRiskDB = new LMCardRiskDB();
		LMCardRiskSet tLMCardRiskSet = new LMCardRiskSet();
		tLMCardRiskDB.setRiskCode(mRiskCode.trim());
		tLMCardRiskDB.setCertifyCode(certifycode);
		tLMCardRiskDB.setPortfolioFlag("1");
		logger.debug("mRiskCode---"+mRiskCode);
		logger.debug("certifycode---"+certifycode);
		if (!tLMCardRiskDB.getInfo()) {
			CError.buildErr(this, "险种描述表中有关该险种的保费描述有问题，请核对!");
			return false;
		}
		tLMCardRiskSet = tLMCardRiskDB.query();
		if(tLMCardRiskSet.size()<=0)
		{
			CError.buildErr(this, "险种描述表中有关该险种的保费描述有问题，请核对!");
			return false;
		}
		prem = tLMCardRiskSet.get(1).getPrem();
		if (prem <= 0) {
			CError.buildErr(this, "险种描述表中有关该险种的保费<=0，请核对");
			return false;
		}
		if (Math.abs(mPayMoney - (prem * (n+1))) >= 0.001) //金额保存为2位小数
		{
			CError.buildErr(this, "录入的交费金额为"+mPayMoney+"与该批卡单的保费之和"+(prem * (n+1))+"不一致，请核对！");
			return false;
		}
		return true;
	}

	
/**
 * 对暂交费表进行赋值
**/	
	private LJTempFeeSchema setLjtempfee(String tempfeeno) {

		LJTempFeeSchema tempFeeSchema = new LJTempFeeSchema();
		tempFeeSchema.setTempFeeNo(tempfeeno);
		tempFeeSchema.setTempFeeType("1"); //卡单的暂收费类型为新单交费
		tempFeeSchema.setPayMoney(String.valueOf(prem)); //一张卡单的收费金额为该卡单的保费
		tempFeeSchema.setOtherNo(tempfeeno); //暂收记录中的其他号码存放为暂收收据号，也是保单号
//		tempFeeSchema.setOtherNoType("4");
		tempFeeSchema.setOtherNoType("0");//注意该号码为合同号码，也为印刷号码
		tempFeeSchema.setRiskCode(mRiskCode);
		tempFeeSchema.setAgentCode(mAgentCode);
		tempFeeSchema.setAgentGroup(mAgentGroup);
		tempFeeSchema.setConfDate("");
		tempFeeSchema.setConfFlag("0");
		tempFeeSchema.setMakeDate(PubFun.getCurrentDate());
		tempFeeSchema.setMakeTime(PubFun.getCurrentTime());
		tempFeeSchema.setManageCom(tG.ManageCom);
		tempFeeSchema.setModifyDate(PubFun.getCurrentDate());
		tempFeeSchema.setModifyTime(PubFun.getCurrentTime());
		tempFeeSchema.setOperator(tG.Operator);
		tempFeeSchema.setPayDate(mPayDate);
		tempFeeSchema.setPayIntv(mPayIntv);
		tempFeeSchema.setPayYears(mPayYears);
//		tempFeeSchema.setSaleChnl("02");//销售渠道:个险营销
		//2010-01-15 调整销售渠道为09 综拓渠道
		tempFeeSchema.setSaleChnl("09");
		tempFeeSchema.setSerialNo(serNo);
		tempFeeSchema.setEnterAccDate(mCEnterAccDate);
		//升级追加
		tempFeeSchema.setOperState("0");
		tempFeeSchema.setTempFeeNoType("3");
		tempFeeSchema.setPolicyCom(mManageCom);
//		tempFeeSchema.setEnterAccDate(mEnterAccDate);
//		tempFeeSchema.setRemark(mComment);没有意义
		if (mCEnterAccDate != null && (!mCEnterAccDate.equals(""))) {
			tempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
			tempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
		}
		return tempFeeSchema;

	}

	
	/**
	 * 对暂交费分类表进行赋值
	**/		
	
	private LJTempFeeClassSchema setLjtempfeeClass(String tempfeeno) {

		LJTempFeeClassSchema tempFeeClassSchema = new LJTempFeeClassSchema();
		tempFeeClassSchema.setTempFeeNo(tempfeeno);
		tempFeeClassSchema.setPayMode(mCPayMode);
		tempFeeClassSchema.setPayMoney(String.valueOf(prem));
		tempFeeClassSchema.setPayDate(mPayDate);
		tempFeeClassSchema.setConfDate("");
		tempFeeClassSchema.setConfFlag("0");
		tempFeeClassSchema.setEnterAccDate(mCEnterAccDate);
		tempFeeClassSchema.setManageCom(tG.ManageCom);
		tempFeeClassSchema.setBankCode(mBankcode);
		tempFeeClassSchema.setBankAccNo(mBankaccno);
		tempFeeClassSchema.setAccName(mAccname);
		tempFeeClassSchema.setChequeNo(mCChequeNo);
		tempFeeClassSchema.setSerialNo(serNo);
		tempFeeClassSchema.setOperator(tG.Operator);
		tempFeeClassSchema.setMakeDate(PubFun.getCurrentDate());
		tempFeeClassSchema.setMakeTime(PubFun.getCurrentTime());
		tempFeeClassSchema.setModifyDate(PubFun.getCurrentDate());
		tempFeeClassSchema.setModifyTime(PubFun.getCurrentTime());
		//升级追加
		tempFeeClassSchema.setPayDate(mCPayDate);	  
		tempFeeClassSchema.setPolicyCom(mCManageCom);
		tempFeeClassSchema.setChequeDate(mChequeDate);
		tempFeeClassSchema.setIDType(mIDType);
		tempFeeClassSchema.setIDNo(mIDNo);
		if (mCEnterAccDate != null && (!mCEnterAccDate.equals(""))) {
			tempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
			tempFeeClassSchema.setConfMakeTime(PubFun.getCurrentTime());
		}
		return tempFeeClassSchema;
	}
	
	
	
/**
 * 卡单交费的校验，只要已核销为实收或者已交费还没核销不能再次交费
 **/
	private boolean isExist(String tempfeeno) {

		String tSql = "select * from ljtempfee where tempfeeno='?tempfeeno?' and confflag='0' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("tempfeeno", tempfeeno);
		logger.debug("校验是否交费且未核销--------" + tSql);
		ExeSQL tExeSQL = new ExeSQL();
		if (tExeSQL.execSQL(sqlbv2).MaxRow > 0) {
			this.mErrors.clearErrors();
			CError.buildErr(this, "号码为" +tempfeeno	+"的卡单已经交费并且未核销，该批卡单的单证发放有问题，请核对!");
			return false;
		}

		// 注意保单号的升级
		String cSql = "select * from ljapayperson where contno='?tempfeeno?' ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(cSql);
		sqlbv3.put("tempfeeno", tempfeeno);
		logger.debug("校验是否已转实收了--------" + cSql);
		if (tExeSQL.execSQL(sqlbv3).MaxRow > 0) 
		{
			this.mErrors.clearErrors();
			CError.buildErr(this, "号码为" +tempfeeno+"的卡单已经交费并且已经核销为实收，该批卡单的单证发放有问题，请核对!");
			return false;
		}
		return true;

	}

	/**
	 * 准备提交数据库的数据
	**/		
	private boolean prepareOutputData() {
		mInputData = new VData();
		map.put(mLJTempFeeSet,"INSERT");
		map.put(mLJTempFeeClassSet,"INSERT");
//		logger.debug("mInputData:   "+mInputData.size());
//		logger.debug("校验一下---"+mLJTempFeeSet.get(1).getAgentCode());
//		mInputData.add(mLJTempFeeSet);
//		mInputData.add(mLJTempFeeClassSet);
		mInputData.add(map);

		return true;
	}

	public VData getResult() {
		return mReslut;
	}

	  /**
	   * 得到自助卡单的单证类型
	   * parem:CertifyNo
	   * return :CertityCode
	   * 卡号的3-8是24+四位版本号,从0015开始; 
	   * 如果5,6的数字都还是0,则表示还没有产生借位,则系统组合出的单证类型为24+7-8位的两位数字,如2415,2416;
	   * 如果第5位是0,而第6位不是0,表明第6位的数字被借用,则系统组合出的单证类型为24+6-8位的三位数字,如24100,24101;
	   * 如果第5位不是0,不管第6位是否为0,系统组合出的单证就是卡号的3-8位数字,如241000,241001
	   */
	  public String GetCertifyType(String x) 
	  {
		  logger.debug("ActivateBL:开始根据卡号得到对应的单证类型");
		  String inputNo2=x;
		  logger.debug("需要拼出卡单类型的卡号:"+inputNo2);

		  String certifycode="";//单证类型
		  //zy 2010-01-15 新单证采用新规则，老单证采用原有规则，通过单证号码第三位判断，如果为7则认为是新单证
		  if(inputNo2.substring(2,3).equals("7"))
		  {
			  
			  certifycode=inputNo2.substring(2,8);
		  }
		  else
		  {
			  //没有借位
			  if(inputNo2.substring(4,5).equals("0")&&inputNo2.substring(5,6).equals("0"))
			  {
				  certifycode=inputNo2.substring(2,4)+inputNo2.substring(6,8);
			  }
			  //两位都被借,即四位版本号的第1位被借用
			  else if(inputNo2.substring(5,6).equals("0"))
			  {
				  certifycode=inputNo2.substring(2,8);
			  }
			  //四位版本号的第2位被借用
			  else
			  {
				  certifycode=inputNo2.substring(2,4)+inputNo2.substring(5,8);
			  }
		  }

		  logger.debug("卡号"+inputNo2+"对应的单证类型:"+certifycode);
		  
		  return certifycode;
	  }
	    
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
