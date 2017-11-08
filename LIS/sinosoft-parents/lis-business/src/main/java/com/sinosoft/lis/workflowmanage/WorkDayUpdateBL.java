package com.sinosoft.lis.workflowmanage;
import org.apache.log4j.Logger;

import org.apache.log4j.*;
/*
 * <p>ClassName:  </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database:
 * @CreateDate：
 */
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.*;

public class WorkDayUpdateBL
{
private static Logger logger = Logger.getLogger(WorkDayUpdateBL.class);

	private static Logger log = Logger.getLogger(WorkDayUpdateBL.class);

	public WorkDayUpdateBL()
	{
	}

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private LDWorkCalendarSchema mLDWorkCalendarSchema = new LDWorkCalendarSchema();
	private LDWorkCalendarSet mLDWorkCalendarSet = new LDWorkCalendarSet();
	private MMap map = new MMap();
	/**
	 * 往后面传输数据的容器
	 */
	private VData mInputData = new VData();
	private String mAccountManagerCode = "";
	private String AmBegin = "";
	private String AmEnd = "";
	private String PmBegin = "";
	private String PmEnd = "";
	private String mYear = "";
	/**
	 * 全局数据
	 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();
	/**
	 * 数据操作字符串
	 */
	private String mOperate;

	public static void main(String[] args)
	{
		WorkDayUpdateBL t = new WorkDayUpdateBL();
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
		log.debug("End getInputData!");
		if (!checkData())
		{
			CError tError = new CError();
			tError.moduleName = "PWTransitionTimeBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据处理失败PDAccountManagertBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		log.debug("End checkData!");
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
		log.debug("over dealData");
		//准备往后台的数据
		if (!prepareOutputData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PWTransitionTimeBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "数据处理失败PWTransitionTimeBL-->prepareOutputData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mInputData, ""))
		{ //数据提交
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PWTransitionTimeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	//校验
	private boolean checkData()
	{
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理
	 * 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		boolean tReturn = true;
		LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarSet();
		for (int i = 1; i <= mLDWorkCalendarSet.size(); i++)
		{
			LDWorkCalendarDB tLDWorkCalendarDB = new LDWorkCalendarDB();
			tLDWorkCalendarDB.setCalSN(mLDWorkCalendarSet.get(i).getCalSN());
			LDWorkCalendarSchema tLDWorkCalendarSchema = new LDWorkCalendarSchema();
			tLDWorkCalendarSchema.setSchema(tLDWorkCalendarDB.query().get(1));
			tLDWorkCalendarSchema.setCalSN(mLDWorkCalendarSet.get(i).getCalSN());
			tLDWorkCalendarSchema.setCalType(mLDWorkCalendarSet.get(i).getCalType());
			tLDWorkCalendarSchema.setYear(mLDWorkCalendarSet.get(i).getYear());
			tLDWorkCalendarSchema.setCalDate(mLDWorkCalendarSet.get(i).getCalDate());
			tLDWorkCalendarSchema.setAmBegin(mLDWorkCalendarSet.get(i).getAmBegin());
			tLDWorkCalendarSchema.setAmEnd(mLDWorkCalendarSet.get(i).getAmEnd());
			tLDWorkCalendarSchema.setPmBegin(mLDWorkCalendarSet.get(i).getPmBegin());
			tLDWorkCalendarSchema.setPmEnd(mLDWorkCalendarSet.get(i).getPmEnd());
			tLDWorkCalendarSchema.setDateType(mLDWorkCalendarSet.get(i).getDateType());
			tLDWorkCalendarSchema.setOtherProp(mLDWorkCalendarSet.get(i).getOtherProp());
			if (tLDWorkCalendarSchema.getDateType().equals("N"))
			{
				tLDWorkCalendarSchema.setWorkTime("0");
			}
			else
			{
				String startDate = mLDWorkCalendarSet.get(i).getYear() + "-01-01 " + mLDWorkCalendarSet.get(i).getAmBegin();
				String endDate = mLDWorkCalendarSet.get(i).getYear() + "-01-01 " + mLDWorkCalendarSet.get(i).getAmEnd();
				int tAmWorkTime = WorkCalendarService.calIntervalTime(startDate, endDate, "S"); //上午工作时间
				startDate = mLDWorkCalendarSet.get(i).getYear() + "-01-01 " + mLDWorkCalendarSet.get(i).getPmBegin();
				endDate = mLDWorkCalendarSet.get(i).getYear() + "-01-01 " + mLDWorkCalendarSet.get(i).getPmEnd();
				int tPmWorkTime = WorkCalendarService.calIntervalTime(startDate, endDate, "S"); //下午工作时间
				tLDWorkCalendarSchema.setAMWorkTime(String.valueOf(tAmWorkTime));
				tLDWorkCalendarSchema.setPMWorkTime(String.valueOf(tPmWorkTime));
				tLDWorkCalendarSchema.setWorkTime(String.valueOf(tAmWorkTime + tPmWorkTime));
			}
			tLDWorkCalendarSchema.setModifyDate(PubFun.getCurrentDate());
			tLDWorkCalendarSchema.setModifyTime(PubFun.getCurrentTime());
			tLDWorkCalendarSchema.setModifyOperator(mGlobalInput.Operator);
			tLDWorkCalendarSet.add(tLDWorkCalendarSchema);
		}
		map.put(tLDWorkCalendarSet, "UPDATE");
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		//全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mLDWorkCalendarSet = (LDWorkCalendarSet) cInputData.getObjectByObjectName("LDWorkCalendarSet", 0);
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
