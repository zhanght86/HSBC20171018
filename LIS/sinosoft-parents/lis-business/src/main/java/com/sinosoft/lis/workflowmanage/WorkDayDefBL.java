package com.sinosoft.lis.workflowmanage;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDWorkCalendarSchema;
import com.sinosoft.lis.vschema.LDWorkCalendarSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class WorkDayDefBL
{

	private static Logger logger = Logger.getLogger(WorkDayDefBL.class);

	public WorkDayDefBL()
	{
	}

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private LDWorkCalendarSet mLDWorkCalendarSet = new LDWorkCalendarSet();
	private MMap map = new MMap();
	/**
	 * 往后面传输数据的容器
	 */
	private VData mInputData = new VData();
	private String AmBegin = "";
	private String AmEnd = "";
	private String PmBegin = "";
	private String PmEnd = "";
	private String mYear = "";
	private String CalType = "";
	private String OtherProp = "";
	/**
	 * 全局数据
	 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/**
	 * 数据操作字符串
	 */
	@SuppressWarnings("unused")
	private String mOperate;

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		WorkDayDefBL t = new WorkDayDefBL();
		VData cInputData = new VData();
		LDWorkCalendarSchema tLDWorkCalendarSchema = new LDWorkCalendarSchema();
		tLDWorkCalendarSchema.setYear("2009");
		tLDWorkCalendarSchema.setAmBegin("09:00:00");
		tLDWorkCalendarSchema.setAmEnd("12:00:00");
		tLDWorkCalendarSchema.setPmBegin("13:00:00");
		tLDWorkCalendarSchema.setPmEnd("18:00:00");
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "000000";
		tGlobalInput.ComCode = "000000";
		cInputData.add(tGlobalInput);
		cInputData.add(tLDWorkCalendarSchema);
		t.submitData(cInputData, "");
	}

	/**
	 * 传输数据的公共方法
	 *
	 * @param: cInputData 输入的数据
	 * cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		//将操作数据拷贝到本类中
		this.mOperate = cOperate;
		//得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			CError tError = new CError();
			tError.moduleName = "WorkDayDefBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据处理失败WorkDayDefBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("End getInputData!");
		//进行业务处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkDayDefBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理失败WorkDayDefBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("over dealData");
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理
	 * 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		boolean tReturn = true;
		deleteAll();
		String startDate = mYear + "-01-01 " + AmBegin;
		String endDate = mYear + "-01-01 " + AmEnd;
		int tAmWorkTime = WorkCalendarService.calIntervalTime(startDate, endDate, "S"); //上午工作时间
		startDate = mYear + "-01-01 " + PmBegin;
		endDate = mYear + "-01-01 " + PmEnd;
		int tPmWorkTime = WorkCalendarService.calIntervalTime(startDate, endDate, "S"); //下午工作时间
		FDate fDate = new FDate();
		Date firstDate = fDate.getDate(mYear + "-01-01");
		Date calDate = firstDate;
		String nextDayYear = mYear;
		LDWorkCalendarSchema tLDWorkCalendarSchema = new LDWorkCalendarSchema();
		int i = 0;
		GregorianCalendar tCalendar = new GregorianCalendar();
		int dayOfWeek ;
		while (nextDayYear.equals(mYear))
		{
			i++;
			tLDWorkCalendarSchema = new LDWorkCalendarSchema();
			tLDWorkCalendarSchema.setCalType(CalType);
			tLDWorkCalendarSchema.setYear(mYear);
			tLDWorkCalendarSchema.setCalDate(calDate);
			tLDWorkCalendarSchema.setAmBegin(AmBegin);
			tLDWorkCalendarSchema.setAmEnd(AmEnd);
			tLDWorkCalendarSchema.setPmBegin(PmBegin);
			tLDWorkCalendarSchema.setPmEnd(PmEnd);
			tLDWorkCalendarSchema.setOtherProp(OtherProp);
			String calSn = PubFun1.CreateMaxNo("CalSN", 18);
			tLDWorkCalendarSchema.setCalSN(calSn);
			// 1--周日 2--周一 3--周二 4--周三 5--周四 6--周五 7--周六
			tCalendar.setTime(calDate);
			dayOfWeek = tCalendar.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == 1 || dayOfWeek == 7)
			{
				tLDWorkCalendarSchema.setDateType("N");//节假日
				tLDWorkCalendarSchema.setWorkTime("0");
			}
			else
			{
				tLDWorkCalendarSchema.setDateType("Y");//工作日
				tLDWorkCalendarSchema.setWorkTime(String.valueOf(tAmWorkTime + tPmWorkTime));
				tLDWorkCalendarSchema.setAMWorkTime(String.valueOf(tAmWorkTime));
				tLDWorkCalendarSchema.setPMWorkTime(String.valueOf(tPmWorkTime));
			}
			tLDWorkCalendarSchema.setOperator(mGlobalInput.Operator);
			tLDWorkCalendarSchema.setOperateCom(mGlobalInput.ManageCom);
			tLDWorkCalendarSchema.setMakeDate(PubFun.getCurrentDate());
			tLDWorkCalendarSchema.setMakeTime(PubFun.getCurrentTime());
			tLDWorkCalendarSchema.setModifyOperator(mGlobalInput.Operator);
			tLDWorkCalendarSchema.setModifyDate(PubFun.getCurrentDate());
			tLDWorkCalendarSchema.setModifyTime(PubFun.getCurrentTime());
			//log.debug(""+calDate.toString());
			//log.debug("周几"+calDate.getDay());
			mLDWorkCalendarSet.add(tLDWorkCalendarSchema);
			calDate = PubFun.calDate(calDate, 1, "D", firstDate);//移动到下一天
			tCalendar.setTime(calDate);
			nextDayYear = String.valueOf(tCalendar.get(Calendar.YEAR));
			if (i % 100 == 0)
			{
				//因为informix数据库Statement提交长度限制，只能分段提交，一旦出错，马上删除插入数据
				if (!saveData())
				{
					CError tError = new CError();
					tError.moduleName = "WorkDayDefBL";
					tError.functionName = "dealData";
					tError.errorMessage = "数据提交失败";
					this.mErrors.addOneError(tError);
					deleteAll();
					return false;
				}
				mLDWorkCalendarSet.clear();
			}
		}
		if (!saveData())
		{
			CError tError = new CError();
			tError.moduleName = "WorkDayDefBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败";
			this.mErrors.addOneError(tError);
			deleteAll();
			return false;
		}
		return tReturn;
	}

	/**
	 * 保存数据
	 */
	private boolean saveData()
	{
		map = new MMap();
		map.put(mLDWorkCalendarSet, "INSERT");
		if (!prepareOutputData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkDayDefBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "数据处理失败WorkDayDefBL-->prepareOutputData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mInputData, ""))
		{ //数据提交
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "WorkDayDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 出错回滚
	 */
	private boolean deleteAll()
	{
		String sql = "delete from LDWorkCalendar where year='?mYear?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mYear", mYear);
		map = new MMap();
		map.put(sqlbv, "DELETE");
		if (!prepareOutputData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PWTransitionTimeBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "数据处理失败WorkDayDefBL-->prepareOutputData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mInputData, ""))
		{ //数据提交
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "WorkDayDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		//全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
		AmBegin = String.valueOf(tTransferData.getValueByName("AmBegin"));
		AmEnd = String.valueOf(tTransferData.getValueByName("AmEnd"));
		PmBegin = String.valueOf(tTransferData.getValueByName("PmBegin"));
		PmEnd = String.valueOf(tTransferData.getValueByName("PmEnd"));
		mYear = String.valueOf(tTransferData.getValueByName("Year"));
		CalType = String.valueOf(tTransferData.getValueByName("CalType"));
		OtherProp = String.valueOf(tTransferData.getValueByName("OtherProp"));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkDayDefBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	@SuppressWarnings("unchecked")
	private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(map);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkDayDefBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		return this.mResult;
	}
}
