package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDHospitalDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDHospitalBSchema;
import com.sinosoft.lis.schema.LDHospitalSchema;
import com.sinosoft.lis.vschema.LDHospitalBSet;
import com.sinosoft.lis.vschema.LDHospitalSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 体检医院维护
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 */
public class LDHospitalManageBL {
private static Logger logger = Logger.getLogger(LDHospitalManageBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private TransferData mTransferData = new TransferData();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	
	//界面传入的体检医院信息
	private LDHospitalSet mInputLDHospitalSet = new LDHospitalSet();
	//最终处理的体检医院信息
	private LDHospitalSet mOutLDHospitalSet = new LDHospitalSet();
	//最终处理的需要备份的医院信息
	private LDHospitalBSet mOutLDHospitalBSet = new LDHospitalBSet();
	private GlobalInput mGlobalInput = new GlobalInput();
	public LDHospitalManageBL() {
	}
	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 校验数据
		if (!checkData()) {
			return false;
		}
		logger.debug("--checkData-");
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---LDHospitalManageBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---LDHospitalManageBL prepareOutputData---");
		// 数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			//CError.buildErr(this,"数据提交失败");
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		for(int i=1;i<=this.mInputLDHospitalSet.size();i++)
		{
			LDHospitalSchema tLDHospitalSchema = new LDHospitalSchema();
			tLDHospitalSchema.setSchema(this.mInputLDHospitalSet.get(i));
			
			LDHospitalDB tLDHospitalDB = new LDHospitalDB();
			if(!this.mOperate.equals("INSERT"))
			{
				//如果不是新增的话,需要先查询数据库中的原始数据,并做转储,以便记录轨迹.
				tLDHospitalDB.setSchema(tLDHospitalSchema);
				if(!tLDHospitalDB.getInfo())
				{
					CError.buildErr(this,"没有体检医院信息,不能修改或删除!");
					return false;
				}
				//生成转储号
				String tSerialNo = PubFun1.CreateMaxNo("SerialNo", 20);
				Reflections tReflections = new Reflections();
				LDHospitalBSchema tLDHospitalBSchema = new LDHospitalBSchema();
				tReflections.transFields(tLDHospitalBSchema, tLDHospitalDB.getSchema());
				//设置其他需要保存的信息
				tLDHospitalBSchema.setSerialNo(tSerialNo);
				tLDHospitalBSchema.setModifyDate2(this.mCurrentDate);
				tLDHospitalBSchema.setModifyTime2(this.mCurrentTime);
				tLDHospitalBSchema.setOperator2(this.mGlobalInput.Operator);
				
				this.mOutLDHospitalBSet.add(tLDHospitalBSchema);
				
				//保存原始的修改日期和修改时间
				tLDHospitalSchema.setMakeDate(tLDHospitalDB.getMakeDate());
				tLDHospitalSchema.setMakeTime(tLDHospitalDB.getMakeTime());
				tLDHospitalSchema.setModifyDate(this.mCurrentDate);
				tLDHospitalSchema.setModifyTime(this.mCurrentTime);
			}
			else
			{
				//新增记录生成日期和修改日期
				tLDHospitalSchema.setMakeDate(this.mCurrentDate);
				tLDHospitalSchema.setMakeTime(this.mCurrentTime);
				tLDHospitalSchema.setModifyDate(this.mCurrentDate);
				tLDHospitalSchema.setModifyTime(this.mCurrentTime);
			}
			tLDHospitalSchema.setOperator(this.mGlobalInput.Operator);
			this.mOutLDHospitalSet.add(tLDHospitalSchema);
		}
		return true;
	}


	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0));
//			mTransferData = (TransferData) cInputData.getObjectByObjectName(
//					"TransferData", 0);
//			
		
			this.mInputLDHospitalSet.set((LDHospitalSet)cInputData.getObjectByObjectName(
					"LDHospitalSet", 0));
			if(mGlobalInput==null)
			{
				CError.buildErr(this,"操作超时,请重新登陆!");
				return false;
			}
			if(mInputLDHospitalSet==null||mInputLDHospitalSet.size()<=0)
			{
				CError.buildErr(this,"没有需要处理的体检医院信息!");
				return false;
			}			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this,"发现异常:"+e.toString());
			return false;
		}
	}

	/**
	 * 校验数据
	 * 
	 * @return
	 */
	private boolean checkData() {
				return true;
	}

	/**
	 * a 准备需要保存的数据
	 */
	private boolean prepareOutputData() {

		MMap map = new MMap();
		if(this.mOutLDHospitalBSet.size()>0)
		{
			map.put(this.mOutLDHospitalBSet,"INSERT");
		}
		//体检医院保存采用先删后插方式.
		if(this.mOutLDHospitalSet.size()>0)
		{
			//if(this.mOperate.equals("DELETE"))
			{
				map.put(this.mOutLDHospitalSet,mOperate);
			}
			
//			LDHospitalSet tLDHospitalSet = new LDHospitalSet();
//			tLDHospitalSet.set(mOutLDHospitalSet);
//			if(!this.mOperate.equals("DELETE"))
//			{
//				map.put(tLDHospitalSet,mOperate);
//			}
		}
		mResult.add(map);

		return true;

	}

}
