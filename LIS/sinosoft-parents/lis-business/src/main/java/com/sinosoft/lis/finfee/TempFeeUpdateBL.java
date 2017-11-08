package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.schema.LBTempFeeClassSchema;
import com.sinosoft.lis.schema.LBTempFeeSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LBTempFeeClassSet;
import com.sinosoft.lis.vschema.LBTempFeeSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 暂收费修改
 * </p>
 * <p>
 * @version 6.0
 */
public class TempFeeUpdateBL {
private static Logger logger = Logger.getLogger(TempFeeUpdateBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	

	/** 业务处理相关变量 */
	/** 暂交费表 */
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJTempFeeSet OldLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet OldLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJSPaySet mLJSPaySet = new LJSPaySet();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private LBTempFeeSet mLBTempFeeSet = new LBTempFeeSet();
	private LBTempFeeClassSet mLBTempFeeClassSet = new LBTempFeeClassSet();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();

	public TempFeeUpdateBL() {
	}

	public static void main(String[] args) {

		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86";
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		// 1st record:
		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setTempFeeNo("000000001951");
		tLJTempFeeSchema.setTempFeeType("1");
		tLJTempFeeSchema.setPayDate("2006-03-02");
		tLJTempFeeSchema.setRiskCode("00225000");
		tLJTempFeeSchema.setPayMoney(90.0);
		tLJTempFeeSchema.setAgentCode("02200364");
		tLJTempFeeSchema.setAgentGroup("360000000210");
		tLJTempFeeSchema.setManageCom("863200");
		tLJTempFeeSchema.setOperator("001");
		tLJTempFeeSchema.setOtherNo("00000000406334");
		tLJTempFeeSchema.setOtherNoType("6");
		tLJTempFeeSchema.setPayEndYear("1000");
		tLJTempFeeSchema.setPayIntv("0");
		tLJTempFeeSet.add(tLJTempFeeSchema);
		logger.debug("=========================================");

		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setTempFeeNo("000000001951");
		tLJTempFeeSchema.setTempFeeType("1");
		tLJTempFeeSchema.setPayDate("2006-03-02");
		tLJTempFeeSchema.setRiskCode("00238000");
		tLJTempFeeSchema.setPayMoney(312.0);
		tLJTempFeeSchema.setAgentCode("02200364");
		tLJTempFeeSchema.setAgentGroup("360000000210");
		tLJTempFeeSchema.setManageCom("863200");
		tLJTempFeeSchema.setOperator("001");
		tLJTempFeeSchema.setOtherNo("00000000406334");
		tLJTempFeeSchema.setOtherNoType("6");
		tLJTempFeeSchema.setPayEndYear("1000");
		tLJTempFeeSchema.setPayIntv("0");
		tLJTempFeeSet.add(tLJTempFeeSchema);
		logger.debug("=========================================");

		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setTempFeeNo("000000001951");
		tLJTempFeeSchema.setTempFeeType("1");
		tLJTempFeeSchema.setPayDate("2006-03-02");
		tLJTempFeeSchema.setRiskCode("00265000");
		tLJTempFeeSchema.setPayMoney(2000.0);
		tLJTempFeeSchema.setAgentCode("02200364");
		tLJTempFeeSchema.setAgentGroup("360000000210");
		tLJTempFeeSchema.setManageCom("863200");
		tLJTempFeeSchema.setOperator("001");
		tLJTempFeeSchema.setOtherNo("00000000406334");
		tLJTempFeeSchema.setOtherNoType("6");
		tLJTempFeeSchema.setPayEndYear("20");
		tLJTempFeeSchema.setPayIntv("12");
		tLJTempFeeSet.add(tLJTempFeeSchema);
		logger.debug("=========================================");

		tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		tLJTempFeeClassSchema.setTempFeeNo("000000001951");
		tLJTempFeeClassSchema.setPayMode("7");
		tLJTempFeeClassSchema.setPayMoney(2402.0);
		tLJTempFeeClassSchema.setChequeNo("000000");
		tLJTempFeeClassSchema.setBankCode("320101");
		tLJTempFeeClassSchema.setPayDate("2006-03-02");
		tLJTempFeeClassSchema.setBankAccNo("9558800402113528036");
		tLJTempFeeClassSchema.setAccName("刘雅彬");
		tLJTempFeeClassSchema.setOtherNo("00000000406334");
		tLJTempFeeClassSchema.setOtherNoType("6");
		// tLJTempFeeClassSchema.setPayMoney(1000);
		// tLJTempFeeClassSchema.setEnterAccDate("2004-02-23");
		tLJTempFeeClassSchema.setManageCom("863200");
		tLJTempFeeClassSchema.setOperator("001");
		tLJTempFeeClassSchema.setManageCom("863200");
		tLJTempFeeClassSet.add(tLJTempFeeClassSchema);

		VData tVData = new VData();
		tVData.addElement(tLJTempFeeSet);
		tVData.addElement(tLJTempFeeClassSet);
		tVData.addElement(tGI);

		TempFeeUpdateUI tTempFeeUpdateUI = new TempFeeUpdateUI();

		tTempFeeUpdateUI.submitData(tVData, "UPDATE");

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();
		try
		{
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("---getInputData---");
		if (!checkdata()) 
		{
			return false;
		}
		// 进行业务处理
		if (!dealdata())
			return false;
		logger.debug("---updateLJTempFee---");

		 if (!prepareOutputData())
		 return false;

		logger.debug("tempfeeupdateBl END");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			CError.buildErr(this, ex.toString());
			return false;
		}
		finally
		{
			mLock.unLock();
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mLJTempFeeSet = (LJTempFeeSet) cInputData.getObjectByObjectName("LJTempFeeSet", 0);
		mLJTempFeeClassSet = (LJTempFeeClassSet) cInputData.getObjectByObjectName("LJTempFeeClassSet", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);


		return true;
	}

	private boolean checkdata() {
		// 查询的主SQL为的是保证前后台的数据前后顺序一致
		// 2007-12-29 zy
		LJTempFeeSchema tLJTempFeeSchema = mLJTempFeeSet.get(1);
		String mTempFeeNo = tLJTempFeeSchema.getTempFeeNo();
		if (mTempFeeNo == null || mTempFeeNo.equals("")) 
		{
			CError.buildErr(this, "暂收据号不能为空!");
			return false;
		}

		String tSql = "select * from LJTempFee  where tempfeeno='?mTempFeeNo?' and managecom like concat('?managecom?','%')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("mTempFeeNo", mTempFeeNo);
		sqlbv.put("managecom", mGlobalInput.ManageCom);

		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();

		OldLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);

		if (OldLJTempFeeSet.size() == 0) 
		{
			CError.buildErr(this, "无此暂收费纪录，请检查暂收据号是否录入正确!");
			return false;
		}
		String[] tOperatedNo = new String[2];
		tOperatedNo[0]=mTempFeeNo;
		tOperatedNo[1]=OldLJTempFeeSet.get(1).getOtherNo();
		
		if(!mLock.lock(tOperatedNo, "LF0001", mGlobalInput.Operator))
		{
			CError tError = new CError(mLock.mErrors.getLastError());
			this.mErrors.addOneError(tError);
			return false;

		}

		for (int i = 1; i <= OldLJTempFeeSet.size(); i++) 
		{
			LJTempFeeSchema nLJTempFeeSchema = OldLJTempFeeSet.get(i);
			String Confflag = nLJTempFeeSchema.getConfFlag();
			// String TempFeeType = nLJTempFeeSchema.getTempFeeType();
			//MS规则，需要排除预收交费 zy 2008-10-17
			if (Confflag.equals("1") && !nLJTempFeeSchema.getTempFeeType().equals("8")) 
			{
				CError.buildErr(this, "已经核销或退费，不能进行修改!");
				return false;
			}
			String tjSql = "select * from ljspay where getnoticeno='?mTempFeeNo?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tjSql);
			sqlbv1.put("mTempFeeNo", mTempFeeNo);
			LJSPayDB tLJSPayDB = new LJSPayDB();
			mLJSPaySet = tLJSPayDB.executeQuery(sqlbv1);
			if (mLJSPaySet != null && mLJSPaySet.size() > 0 && "1".equals(OldLJTempFeeSet.get(i).getTempFeeType())) 
			{				
			   CError.buildErr(this, "新单交费产生了银行发盘数据，请先取消该数据再进行数据修改！");
			   return false;
			}

		}

		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		tLJTempFeeClassDB.setTempFeeNo(mTempFeeNo);
		OldLJTempFeeClassSet = tLJTempFeeClassDB.query();

		String tEnterAccDate = "1900-01-01";
		boolean isAllEnterAcc = true;
		for (int i = 1; i <= OldLJTempFeeClassSet.size(); i++) 
		{
			String Paymode = OldLJTempFeeClassSet.get(i).getPayMode();
			double tSumBankMoney = 0;
			if (OldLJTempFeeClassSet.get(i).getEnterAccDate() == null) 
			{			
				isAllEnterAcc = false; // 没有全部都到账
				if (Paymode.equals("4")) 
				{
					String LJSPaySql = "select * from ljspay where getnoticeno='?mTempFeeNo?'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(LJSPaySql);
					sqlbv2.put("mTempFeeNo", mTempFeeNo);
					LJSPayDB tLJSPayDB = new LJSPayDB();
					mLJSPaySet = tLJSPayDB.executeQuery(sqlbv2);
					if (mLJSPaySet != null && mLJSPaySet.size() > 0) 
					{
						LJSPaySchema tLJSPaySchema = new LJSPaySchema();
						tLJSPaySchema = mLJSPaySet.get(1);

						if (OldLJTempFeeClassSet.get(i).getEnterAccDate() != null) {
							CError.buildErr(this, "银行处理成功，不能进行修改!");
							return false;
						}
						if (tLJSPaySchema.getBankOnTheWayFlag().equals("1")) 
						{
							CError.buildErr(this, "已送银行处理，不能进行修改!");
							return false;
						}
						
					}

				}
			} 
			else 
			{
				if (tEnterAccDate.compareTo(OldLJTempFeeClassSet.get(i).getEnterAccDate()) < 0) 
				{
					tEnterAccDate = OldLJTempFeeClassSet.get(i).getEnterAccDate();
				}

			}
			if (OldLJTempFeeClassSet.get(i).getPayMode().equals("4")) 
			{
				tSumBankMoney = tSumBankMoney+ OldLJTempFeeClassSet.get(i).getPayMoney();
			}
		}

		for (int i = 1; i <= OldLJTempFeeClassSet.size(); i++) 
		{
			if (isAllEnterAcc) // 已经全部到账
			{
				if (!(PubFun.getCurrentDate().equals(OldLJTempFeeClassSet.get(1).getMakeDate()))) 
				{
					CError.buildErr(this, "该暂收已经全部到账，只能修改当天数据!");
					mLJTempFeeSet.clear();
					return false;
				}
			}
		}

		// add by jiaqiangli 2007-12-25 增加校验
		logger.debug("jiaqiangli");
		for (int i = 1; i <= mLJTempFeeSet.size(); i++) 
		{
			LJTempFeeSchema tpLJTempFeeSchema = new LJTempFeeSchema();
			tpLJTempFeeSchema.setSchema(mLJTempFeeSet.get(i));
			logger.debug("aaaa" + tpLJTempFeeSchema.getPayDate());
			if (tpLJTempFeeSchema.getPayDate() != null) 
			{
				logger.debug(tpLJTempFeeSchema.getPayDate());
				if (!isValidDate(tpLJTempFeeSchema.getPayDate())) 
				{
					CError.buildErr(this, "缴费日期输入不正确");
					mLJTempFeeSet.clear();
					return false;
				}
				if (tpLJTempFeeSchema.getPayDate().compareTo(PubFun.getCurrentDate()) > 0) 
				{
					CError.buildErr(this, "缴费日期不能超过今天!");
					mLJTempFeeSet.clear();
					return false;
				}
			}
			
		      //对于没有全部都到账确认的暂收费记录置空到账日期，否则取最晚的到账日期为暂收费到账日期
		      if (isAllEnterAcc)
		      {
		    	  tpLJTempFeeSchema.setEnterAccDate(tEnterAccDate);
		      }
		      else
		      {
		    	  tpLJTempFeeSchema.setEnterAccDate("");
		      }
		      mLJTempFeeSet.set(i,tpLJTempFeeSchema);
			
		    LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		    tLMRiskAppDB.setRiskCode(tpLJTempFeeSchema.getRiskCode());
		    if (!tLMRiskAppDB.getInfo())
		    {
		      CError.buildErr(this, "无此险种编码:"+tpLJTempFeeSchema.getRiskCode());
		      mLJTempFeeSet.clear();
		      return false;
		    }
		    
		    for (int j=i+1;j<=mLJTempFeeSet.size();j++)
		    {
		      LJTempFeeSchema ttLJTempFeeSchema = new LJTempFeeSchema();
		      ttLJTempFeeSchema.setSchema(mLJTempFeeSet.get(j));
		      if (ttLJTempFeeSchema.getRiskCode().equals(tpLJTempFeeSchema.getRiskCode()))
		      {
		          CError.buildErr(this, "不能录入重复的险种编码!");
		          mLJTempFeeSet.clear();
		          return false;
		      }
		    }
	      if (tpLJTempFeeSchema.getOtherNo()==null ||tpLJTempFeeSchema.getOtherNo().length()==0)
	          {
	            CError.buildErr(this, "请录入投保单号印刷号/保单号!");
	            mLJTempFeeSet.clear();
	            return false;
	          }
		    		    
		}
		
	    if (OldLJTempFeeSet.size()!=mLJTempFeeSet.size() && !mLJTempFeeSet.get(1).getTempFeeType().equals("1"))
	    {
	      CError.buildErr(this, "续期不能增加条数!");
	      mLJTempFeeSet.clear();
	      return false;
	    }
	    if (OldLJTempFeeSet.get(1).getOtherNo()!=null && mLJTempFeeSet.get(1).getOtherNo()!=null)
	    {
	      if (!OldLJTempFeeSet.get(1).getOtherNo().equals(mLJTempFeeSet.get(1).getOtherNo()) && !mLJTempFeeSet.get(1).getTempFeeType().equals("1") )
	      {
	        CError.buildErr(this, "续期不能更改其它号码!");
	        mLJTempFeeSet.clear();
	        return false;
	      }
	    }
		double tOldBankMoney = 0;
		for (int i = 1; i <= mLJTempFeeClassSet.size(); i++) 
		{
			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
			tLJTempFeeClassSchema.setSchema(mLJTempFeeClassSet.get(i));
			if (tLJTempFeeClassSchema.getEnterAccDate() != null) 
			{
				if (tLJTempFeeClassSchema.getEnterAccDate().compareTo(PubFun.getCurrentDate()) > 0) 
				{
					CError.buildErr(this, "到帐日期不能超过今天!");
					mLJTempFeeSet.clear();
					return false;
				}
			}
			if (tLJTempFeeClassSchema.getPayMode() == null	|| tLJTempFeeClassSchema.getPayMode().length() == 0) 
			{
				CError.buildErr(this, "请录入交费方式!");
				mLJTempFeeSet.clear();
				return false;
			} 
			else if (tLJTempFeeClassSchema.getPayMode().equals("4")) 
			{
				if ((tLJTempFeeClassSchema.getAccName() == null || tLJTempFeeClassSchema.getAccName().length() == 0)
					|| (tLJTempFeeClassSchema.getBankAccNo() == null || tLJTempFeeClassSchema.getBankAccNo().length() == 0)
					|| (tLJTempFeeClassSchema.getBankCode() == null || tLJTempFeeClassSchema.getBankCode().length() == 0)) 
				{
					CError.buildErr(this, "请录入开户银行,银行账号,户名!");
					mLJTempFeeSet.clear();
					return false;
				}
				if (tLJTempFeeClassSchema.getEnterAccDate() != null) 
				{
					CError.buildErr(this, "交费方式银行转账，到帐日期必须为空");
					mLJTempFeeSet.clear();
					return false;
				}
				tOldBankMoney = tOldBankMoney+ tLJTempFeeClassSchema.getPayMoney();
			} 
			else if (tLJTempFeeClassSchema.getPayMode().equals("5")) 
			{
				if ((tLJTempFeeClassSchema.getChequeNo() == null || tLJTempFeeClassSchema.getChequeNo().length() == 0)
					|| (tLJTempFeeClassSchema.getEnterAccDate() == null || tLJTempFeeClassSchema.getEnterAccDate().length() == 0)) 
				{
					CError.buildErr(this, "内部转账时，票据号码和到帐日期不能为空");
					mLJTempFeeSet.clear();
					return false;
				}
				if("000000".equals(tLJTempFeeClassSchema.getChequeNo()))
				{
					CError.buildErr(this, "内部转账时，票据号码为实付号，请填写实付号");
					mLJTempFeeSet.clear();
					return false;
				}
			} 
			else if (tLJTempFeeClassSchema.getPayMode().equals("6")) 
			{
				if (tLJTempFeeClassSchema.getBankCode() == null|| tLJTempFeeClassSchema.getBankCode().length() == 0) 
				{
					CError.buildErr(this, "银行托收时，开户银行不能为空");
					mLJTempFeeSet.clear();
					return false;
				}
			} 
			else if (tLJTempFeeClassSchema.getPayMode().equals("2")|| tLJTempFeeClassSchema.getPayMode().equals("3")) 
			{
				if ((tLJTempFeeClassSchema.getChequeNo() == null || tLJTempFeeClassSchema.getChequeNo().length() == 0)
					|| (tLJTempFeeClassSchema.getBankCode() == null || tLJTempFeeClassSchema.getBankCode().length() == 0)) 
				{
					CError.buildErr(this, "交费方式为支票时，票据号码和银行不能为空");
					mLJTempFeeSet.clear();
					return false;
				}
			}

			for (int j = i + 1; j <= mLJTempFeeClassSet.size(); j++) 
			{
				LJTempFeeClassSchema ttLJTempFeeClassSchema = new LJTempFeeClassSchema();
				ttLJTempFeeClassSchema.setSchema(mLJTempFeeClassSet.get(j));
				if (ttLJTempFeeClassSchema.getPayMode() == null)
					continue;
				if (ttLJTempFeeClassSchema.getPayMode().equals(tLJTempFeeClassSchema.getPayMode())) 
				{
					CError.buildErr(this, "不能录入重复的交费方式!");
					mLJTempFeeSet.clear();
					return false;
				}
			}
		}
		 if (tOldBankMoney>0)
		 {
			 LJSPayDB tLJSPayDB = new LJSPayDB();
			 tLJSPayDB.setGetNoticeNo(mLJTempFeeClassSet.get(1).getTempFeeNo());
			 //DB.getCount()相当于DB.getInfo()
			 if (tLJSPayDB.getCount()>0)
			 {
				 CError.buildErr(this, "该暂收费已经生成送银行数据!");
				 mLJTempFeeSet.clear();
				 return false;
			 }
		 }
		 
		// 缴费日期 <= 到帐日期
//		 MS不存在该校验关系  zy 2008-10-17
//		for (int i = 1; i <= mLJTempFeeSet.size(); i++) {
//			if (mLJTempFeeSet.get(i).getPayDate() != null&& !mLJTempFeeSet.get(i).getPayDate().equals("")) 
//			{
//				for (int j = 1; j <= mLJTempFeeClassSet.size(); j++) 
//				{
//					if (mLJTempFeeClassSet.get(j).getEnterAccDate() != null&& !mLJTempFeeClassSet.get(j).getEnterAccDate().equals("")) 
//					{
//						// ljtempfee.paydate > ljtempfeeclass.enteraccdate
//						if (mLJTempFeeSet.get(i).getPayDate().compareTo(mLJTempFeeClassSet.get(j).getEnterAccDate()) > 0) 
//						{
//							CError.buildErr(this, "缴费日期应该小于等于到帐日期!");
//							mLJTempFeeSet.clear();
//							return false;
//						}
//					}
//				}
//			}
//		}
		// 校验修改金额
		double tLJTempFeeSum = 0;
		double tLJTempFeeClassSum = 0;
		for (int k = 1; k <= mLJTempFeeSet.size(); k++) 
		{
			if (mLJTempFeeSet.get(k).getPayMoney() < 0) 
			{
				CError.buildErr(this,  "暂收费的金额不能为负!");
				mLJTempFeeSet.clear();
				return false;
			}
			tLJTempFeeSum = tLJTempFeeSum + mLJTempFeeSet.get(k).getPayMoney();
		}
		
		for (int k = 1; k <= mLJTempFeeClassSet.size(); k++) 
		{
			if (mLJTempFeeClassSet.get(k).getPayMoney() < 0) 
			{
				CError.buildErr(this,  "暂收分类的金额不能为负!");
				mLJTempFeeSet.clear();
				return false;
			}
			tLJTempFeeClassSum = tLJTempFeeClassSum+ mLJTempFeeClassSet.get(k).getPayMoney();
		}
		logger.debug("tLJTempFeeSum:" + tLJTempFeeSum);
		logger.debug("tLJTempFeeClassSum:" + tLJTempFeeClassSum);
		if (Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001) 
		{
			CError.buildErr(this, "暂收费和暂收分类的金额不符!");
			mLJTempFeeSet.clear();
			return false;
		}

		// add by jiaqiangli 2007-12-25

		return true;
	}

	public static boolean isValidDate(String dateStr) {
		try {
			if (dateStr == null) {
				return false;
			}
			if (dateStr.indexOf("-") == -1)
				return false;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(dateStr);
			String dateStr2 = df.format(date);
			logger.debug("dateStr2[" + dateStr2 + "]");
			if (!dateStr.equals(dateStr2)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询暂交费表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean dealdata() 
	{
		Reflections mReflections = new Reflections();
	    LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
	    tLJTempFeeClassDB.setTempFeeNo(OldLJTempFeeSet.get(1).getTempFeeNo());
	    LJTempFeeClassSet OldLJTempFeeClassSet = new LJTempFeeClassSet();
	    OldLJTempFeeClassSet=tLJTempFeeClassDB.query();
	    LJTempFeeClassSchema tLJTempFeeClassSchemaOld = new LJTempFeeClassSchema();
	    tLJTempFeeClassSchemaOld=OldLJTempFeeClassSet.get(1);
	    
	    String tEnterAccFlag="1";
	    String tConfMakeFlag="1";
	    String tEnterDate="1900-01-01";
	    //备份原来的暂收费分类数据   2008-10-18 zy
		for(int i=1;i<=OldLJTempFeeClassSet.size();i++)
		{
	        LBTempFeeClassSchema tLBTempFeeClassSchema = new LBTempFeeClassSchema();
	        mReflections.transFields(tLBTempFeeClassSchema, OldLJTempFeeClassSet.get(i));
	        tLBTempFeeClassSchema.setBackUpSerialNo(PubFun1.CreateMaxNo("LBTFClass", 20));
	        mLBTempFeeClassSet.add(tLBTempFeeClassSchema);
		}
		
	    for (int i=1;i<=mLJTempFeeClassSet.size();i++)
	    {
	      LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
	      tLJTempFeeClassSchema.setSchema(tLJTempFeeClassSchemaOld);
	      LJTempFeeClassSchema mLJTempFeeClassSchema = mLJTempFeeClassSet.get(i);
	      tLJTempFeeClassSchema.setPayDate(mLJTempFeeClassSchema.getPayDate());
	      tLJTempFeeClassSchema.setPayMode(mLJTempFeeClassSchema.getPayMode());
	      tLJTempFeeClassSchema.setPayMoney(mLJTempFeeClassSchema.getPayMoney());
	      tLJTempFeeClassSchema.setChequeNo(mLJTempFeeClassSchema.getChequeNo());
	      tLJTempFeeClassSchema.setBankCode(mLJTempFeeClassSchema.getBankCode());
	      tLJTempFeeClassSchema.setBankAccNo(mLJTempFeeClassSchema.getBankAccNo());
	      tLJTempFeeClassSchema.setAccName(mLJTempFeeClassSchema.getAccName());
	      tLJTempFeeClassSchema.setIDNo(mLJTempFeeClassSchema.getIDNo());
	      tLJTempFeeClassSchema.setIDType(mLJTempFeeClassSchema.getIDType());
	      tLJTempFeeClassSchema.setOperator(mGlobalInput.Operator);
	      tLJTempFeeClassSchema.setModifyDate(PubFun.getCurrentDate());
	      tLJTempFeeClassSchema.setModifyTime(PubFun.getCurrentTime());
	      tLJTempFeeClassSchema.setEnterAccDate(mLJTempFeeClassSchema.getEnterAccDate());
	      if (tLJTempFeeClassSchema.getPayMode().equals("1"))
	      {
	        tLJTempFeeClassSchema.setEnterAccDate(PubFun.getCurrentDate());
	        tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
	        tLJTempFeeClassSchema.setConfMakeTime(PubFun.getCurrentTime());
	      }
	      else
	      {
	        tConfMakeFlag="0"; //凡是有非现金交费方式，主表都不置到账确认日期
	      }
	      if (tLJTempFeeClassSchema.getEnterAccDate()!=null && tLJTempFeeClassSchema.getEnterAccDate().length()!=0 )
	      {
		     if (tLJTempFeeClassSchema.getEnterAccDate().compareTo(tEnterDate)>0)
	        {
	          tEnterDate=tLJTempFeeClassSchema.getEnterAccDate();
	        }
	      }
	      else
	      {
	        tEnterAccFlag="0";
	      }
		      mLJTempFeeClassSet.set(i,tLJTempFeeClassSchema);
	      

	    }
	    
	  //备份原来的暂收费数据   2008-10-18 zy
		for(int i=1;i<=OldLJTempFeeSet.size();i++)
		{
	        LBTempFeeSchema tLBTempFeeSchema = new LBTempFeeSchema();
	        mReflections.transFields(tLBTempFeeSchema, OldLJTempFeeSet.get(i));
	        tLBTempFeeSchema.setBackUpSerialNo(PubFun1.CreateMaxNo("LBTempFee", 20));
	        tLBTempFeeSchema.setBackUpReason("暂收费修改");
	        mLBTempFeeSet.add(tLBTempFeeSchema);
		}
		
		LJTempFeeSchema mLJTempFeeSchemaOld = OldLJTempFeeSet.get(1);
	    for (int i=1;i<=mLJTempFeeSet.size();i++)
	    {
	      LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	      tLJTempFeeSchema.setSchema(mLJTempFeeSchemaOld);
	      LJTempFeeSchema mLJTempFeeSchema = mLJTempFeeSet.get(i);
	      tLJTempFeeSchema.setPayDate(mLJTempFeeSchema.getPayDate());
	      tLJTempFeeSchema.setRiskCode(mLJTempFeeSchema.getRiskCode());
	      tLJTempFeeSchema.setOtherNo(mLJTempFeeSchema.getOtherNo());
	      tLJTempFeeSchema.setPayMoney(mLJTempFeeSchema.getPayMoney());
	      tLJTempFeeSchema.setPayIntv(mLJTempFeeSchema.getPayIntv());
	      tLJTempFeeSchema.setPayYears(mLJTempFeeSchema.getPayYears());
	      tLJTempFeeSchema.setAgentCode(mLJTempFeeSchema.getAgentCode());
	      tLJTempFeeSchema.setAgentGroup(mLJTempFeeSchema.getAgentGroup());
	      tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
	      tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
	      tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());

	      if (tEnterAccFlag.equals("1"))
	      {
	        tLJTempFeeSchema.setEnterAccDate(tEnterDate);
	      }
	      if (tConfMakeFlag.equals("1"))
	      {
	        tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
	        tLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
	      }	
	      mLJTempFeeSet.set(i,tLJTempFeeSchema);
	      
	    }

//	    mInputData.clear();
//	    mInputData.add(mLJTempFeeSet);
//	    mInputData.add(mLJTempFeeClassSet);
	    return true;
	  
	}



	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	 private boolean prepareOutputData()
	 {
		 TransferData tTransferData = new TransferData();
		 tTransferData.setNameAndValue("FeeUpdate", "1");
		 VData tVData = new VData();
		 tVData.addElement(mLJTempFeeSet);
		 tVData.addElement(mLJTempFeeClassSet);
		 tVData.addElement(mGlobalInput);
		 tVData.addElement(tTransferData);//增加一个操作符标识为暂收修改功能
		 
		 //增加对备份表的处理  2008-10-18 zy
		 MMap mp = new MMap();
		 mp.put(mLBTempFeeSet, "INSERT");
		 mp.put(mLBTempFeeClassSet, "INSERT");
		 mp.put(OldLJTempFeeSet, "DELETE");
		 mp.put(OldLJTempFeeClassSet, "DELETE");
		 VData bVData = new VData();
		 bVData.add(mp);
		 PubSubmit ps = new PubSubmit();
		 if(!ps.submitData(bVData,""))
		 {
			 mErrors.copyAllErrors(ps.mErrors);
			 CError.buildErr(this, "提交备份数据失败！");
			 return false;
		 }
		 
		 //如果增加新的校验规则只需在暂收处理类中增加即可，所以采用调用TempfeeBL类 zy 2008-10-17
		 TempFeeUI tTempFeeBL   = new TempFeeUI();  //注意如果收费类对TempFeeBLF作调整，该处也需要作调整
		 if (!tTempFeeBL.submitData(tVData,mOperate))
		 {
		     // @@错误处理
			 //回滚删除暂收的操作
			 MMap hmp = new MMap();
			 hmp.put(OldLJTempFeeSet, "INSERT");
			 hmp.put(OldLJTempFeeClassSet, "INSERT");
			 hmp.put(mLBTempFeeSet, "DELETE");
			 hmp.put(mLBTempFeeClassSet, "DELETE");
			 VData hVData = new VData();
			 hVData.add(hmp);
			 PubSubmit hps = new PubSubmit();
			 if(!hps.submitData(hVData,""))
			 {
				 mErrors.copyAllErrors(ps.mErrors);
				 CError.buildErr(this, "提交备份数据失败！");
				 return false;
			 }
			 mErrors.copyAllErrors(tTempFeeBL.mErrors);
		     CError.buildErr(this, "数据提交失败!");
		     return false;
		 }
		    return true;
	 }
}
