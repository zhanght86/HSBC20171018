package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.io.*;

import javax.xml.parsers.*;
import java.util.*;
import org.w3c.dom.*;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import com.sinosoft.service.BusinessService;


public class CertifyBatchReportIn implements BusinessService {
private static Logger logger = Logger.getLogger(CertifyBatchReportIn.class);

	// 错误处理类
	public CErrors logErrors = new CErrors();
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private VData mInputData = new VData();// 保存界面传入的信息,以及解析后的数据
	private VData tVData = null;// 保存解析后的数据
	private MMap tMap = null;

	// 文件路径
	private String FileName;
	private String StateFlag = "";// 单证状态
	private String SaveExcelPath = "";// 放上传Excel的绝对路径


	public CertifyBatchReportIn() {

	}

	public boolean submitData(VData cInputData, String cOperate) {

		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;

		logger.debug("----------CertifyBatchReportIn after getInputData ----------");

		// 检查数据
		if (!checkData()) {
			return false;
		}

		logger.debug("----------CertifyBatchReportIn after checkData----------");
		logger.debug("开始时间:" + PubFun.getCurrentTime());

		try {

			// 把Excel转换为Schema,数据处理
			if (!dealData()) {
				return false;
			}

			logger.debug("----------CertifyBatchReportIn after ParseXml----------");
		}

		catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			CError.buildErr(this, "导入文件格式有误!");
			return false;
		}

		logger.debug("结束时间:" + PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {

		logger.debug("--start getInputData()");
		// 获取输入信息

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mTransferData == null && mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "数据传输失败!");
			return false;
		}

		SaveExcelPath = (String) mTransferData.getValueByName("SaveExcelPath");
		logger.debug("后台获得的SaveExcelPath:" + SaveExcelPath);

		FileName = (String) mTransferData.getValueByName("FileName");
		logger.debug("后台获得的FileName:" + FileName);
		
		StateFlag = (String) mTransferData.getValueByName("StateFlag");
		logger.debug("后台获得的StateFlag:" + StateFlag);

		return true;

	}

	/**
	 * 解析处理
	 * 
	 * @return
	 */
	private boolean dealData() {

		String ttID = "";
		int I = 0;
		String ttcustomerNo = "";
		String ttcustomerName = "";
		String ttflagRgtNo = "";

		// Vector vec=new Vector();

		// 校验待导入的文件是否存在
		if (!initImportFile())
			return false;

		logger.debug("*************结束校验待导入的文件*************");

		// 解析Excel中的数据生成记录
		if (!parseData())
			return false;

		logger.debug("*************结束执行Excel文件的解析*************");


		// 准备数据
		if (!prepareData()) {
			return false;
		}
		logger.debug("*************准备数据结束*************");



		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(tVData, "")) {
			// @@错误处理
			CError.buildErr(this, "批量导入失败！");
			tPubSubmit = null;
			return false;

		}

		tPubSubmit = null;
		return true;


	}



	

	/**
	 * 准备传往工作流处理的数据
	 * 
	 */
	private boolean prepareData() {

		tVData = new VData();
		tVData.add(tMap);

		return true;

	}

	/**
	 * 根据Excel的内容生成出险人数据
	 * 
	 */
	private boolean parseData() {



		BookModelImpl bmTemplate = new com.f1j.ss.BookModelImpl();
		try {

			bmTemplate.read(SaveExcelPath,
					new com.f1j.ss.ReadParams());
			int mSumCount = bmTemplate.getLastRow();
			logger.debug("Excel文件一共有" + mSumCount + "行");
			
			if (mSumCount <= 0) {
				CError.buildErr(this, "上传文件记录为空，请填写后重新上传!");
				return false;
			} 
			else if(mSumCount>3000)
			{
				CError.buildErr(this, "上传文件记录条数超过3000条，请减少上传文件记录的条数!");
				return false;
			}
			else 
			{
				
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				
				LZCardBSet tLZCardBSet = new LZCardBSet();
				LZCardTrackBSet tLZCardTrackBSet = new LZCardTrackBSet();
				LZCardBatchSet tLZCardBatchSet = new LZCardBatchSet();

				logger.debug("界面传入的单证目标状态:" + StateFlag);
				
				tMap = new MMap();

				for (int i = 1; i <= mSumCount; i++) {

					logger.debug("循环第" + i + "条记录的单证编码："
							+ bmTemplate.getText(i, 0));
					logger.debug("循环第" + i + "条记录的单证号码: "
							+ bmTemplate.getText(i, 1));
		
					//如果该行记录为空，则不进行后续处理
					if(bmTemplate.getText(i, 0)==null || "".endsWith(bmTemplate.getText(i, 0)) )
						continue;
					//add by duanyh 2009-08-19 对于批量销毁的单证都计入LZCardBatch表中
					//重复导入的，修改时间信息，第一次导入的则新增一条记录
					String sql1="select * from LZCardBatch where CertifyCode='"+"?CertifyCode?"+"' and StartNo='"+"?StartNo?"+"'";
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql(sql1);
					sqlbv.put("CertifyCode", bmTemplate.getText(i, 0));
					sqlbv.put("StartNo", bmTemplate.getText(i, 1));
					LZCardBatchDB tLZCardBatchDB = new LZCardBatchDB();
					LZCardBatchSchema tLZCardBatchSchema = new LZCardBatchSchema();
					if(tLZCardBatchDB.executeQuery(sqlbv).size()==0)
					{
						tLZCardBatchSchema.setCertifyCode(bmTemplate.getText(i, 0));
						tLZCardBatchSchema.setStartNo(bmTemplate.getText(i, 1));
						tLZCardBatchSchema.setStateFlag(StateFlag);
						tLZCardBatchSchema.setMakeDate(PubFun.getCurrentDate());
						tLZCardBatchSchema.setMakeTime(PubFun.getCurrentTime());						
					}
					else
						tLZCardBatchSchema = tLZCardBatchDB.executeQuery(sqlbv).get(1);
					
					String sql="select k1,k2,k3,k4,k5 from ("
							  +" select distinct sendoutcom k1 ,receivecom k2,0 k3,modifydate k4, modifytime k5 from lzcard where certifycode='"+"?certifycode?"+"' and startno<='"+"?startno?"+"'"+" and endno>='"+"?endno?"+"' and makedate<='2008-12-31'"
					          +" union all"
					          +" select distinct sendoutcom k1,receivecom k2,1 k3,modifydate k4, modifytime k5 from lzcardb where certifycode='"+"?certifycode?"+"' and startno<='"+"?startno?"+"'"+" and endno>='"+"?endno?"+"' and makedate<='2008-12-31'"
					          +" ) a order by k4,k5 desc ";
					
					logger.debug("第"+i+"条记录校验:"+sql);				
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(sql);
					sqlbv1.put("certifycode", bmTemplate.getText(i, 0));
					sqlbv1.put("startno", bmTemplate.getText(i, 1).trim());
					sqlbv1.put("endno", bmTemplate.getText(i, 1).trim());
					tSSRS = tExeSQL.execSQL(sqlbv1);
					
					if(tSSRS.getMaxRow()>0)
					{
						//add by duanyh 2009-08-19 只能对本机构内的单证进行作废、销毁及遗失处理
						String tReceiveCom = tSSRS.GetText(1, 2);//当前单证所在的接收者
						String comcode = mGlobalInput.ComCode;//登录机构
						if (tReceiveCom.charAt(0) == 'A') 
						{
							if (!tReceiveCom.startsWith('A' + comcode)) 
							{
								tLZCardBatchSchema.setFlag("N");
								tLZCardBatchSchema.setReason("该单证所属机构与登录机构不符");
								tLZCardBatchSchema.setModifyDate(PubFun.getCurrentDate());
								tLZCardBatchSchema.setModifyTime(PubFun.getCurrentTime());							
								tLZCardBatchSet.add(tLZCardBatchSchema);
								continue;
							}
						}
						else if(tReceiveCom.charAt(0) == 'D')
						{
							ExeSQL exeSQL = new ExeSQL();
							SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
							sqlbv2.sql("select managecom from laagent a where a.agentcode='"+"?agentcode?"+"'");
							sqlbv2.put("agentcode", tReceiveCom.substring(1));
							tReceiveCom = "A"+exeSQL.getOneValue(sqlbv2);
							if (!tReceiveCom.startsWith('A' + comcode)) 
							{
								tLZCardBatchSchema.setFlag("N");
								tLZCardBatchSchema.setReason("该单证所属机构与登录机构不符");
								tLZCardBatchSchema.setModifyDate(PubFun.getCurrentDate());
								tLZCardBatchSchema.setModifyTime(PubFun.getCurrentTime());	
								tLZCardBatchSet.add(tLZCardBatchSchema);
								continue;
							}
						}
						else if(tReceiveCom.charAt(0) == 'B')
						{
							tReceiveCom = "A" + tReceiveCom.substring(1, tReceiveCom.length() - 2);							
							if (!tReceiveCom.startsWith('A' + comcode)) 
							{
								tLZCardBatchSchema.setFlag("N");
								tLZCardBatchSchema.setReason("该单证所属机构与登录机构不符");
								tLZCardBatchSchema.setModifyDate(PubFun.getCurrentDate());
								tLZCardBatchSchema.setModifyTime(PubFun.getCurrentTime());	
								tLZCardBatchSet.add(tLZCardBatchSchema);
								continue;
							}
						}			
							
						LZCardBSchema tLZCardBSchema = new LZCardBSchema();
						tLZCardBSchema.setCertifyCode(bmTemplate.getText(i, 0));
						tLZCardBSchema.setSubCode("20");
						tLZCardBSchema.setRiskCode("0");
						tLZCardBSchema.setRiskVersion("0");
						tLZCardBSchema.setStartNo(bmTemplate.getText(i, 1).trim());
						tLZCardBSchema.setEndNo(bmTemplate.getText(i, 1).trim());
						tLZCardBSchema.setSumCount("1");
						tLZCardBSchema.setPrem("0.00");
						tLZCardBSchema.setAmnt("0.00");
						tLZCardBSchema.setTakeBackNo("86110020090610027239");
						tLZCardBSchema.setStateFlag(StateFlag);
						tLZCardBSchema.setOperateFlag("1");
						tLZCardBSchema.setOperator(mGlobalInput.Operator);
						tLZCardBSchema.setMakeDate(PubFun.getCurrentDate());
						tLZCardBSchema.setMakeTime(PubFun.getCurrentTime());
						tLZCardBSchema.setModifyDate(PubFun.getCurrentDate());
						tLZCardBSchema.setModifyTime(PubFun.getCurrentTime());
						
						if(tSSRS.GetText(1, 3).equals("0"))
						{
							tLZCardBSchema.setSendOutCom(tSSRS.GetText(1, 2));
							tLZCardBSchema.setReceiveCom(tSSRS.GetText(1, 1));
						}
						else
						{
							tLZCardBSchema.setSendOutCom(tSSRS.GetText(1, 1));
							tLZCardBSchema.setReceiveCom(tSSRS.GetText(1, 2));
						}
						
						
						//如果是作废,则状态改为销毁
						if(StateFlag.equals("6")||StateFlag.equals("7"))
						{
							tLZCardBSchema.setStateFlag("11");
						}

						tLZCardBSet.add(tLZCardBSchema);
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql("delete from lzcard where certifycode='"+"?certifycode?"+"' and startno='"+"?startno?"+"' and endno='"+"?endno?"+"'");
						sqlbv3.put("certifycode", bmTemplate.getText(i, 0));
						sqlbv3.put("startno", bmTemplate.getText(i, 1));
						sqlbv3.put("endno", bmTemplate.getText(i, 1));
						tMap.put(sqlbv3, "DELETE");
						SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
						sqlbv4.sql("delete from lzcardb where certifycode='"+"?certifycode?"+"' and startno='"+"?startno?"+"' and endno='"+"?endno?"+"'");
						sqlbv4.put("certifycode", bmTemplate.getText(i, 0));
						sqlbv4.put("startno", bmTemplate.getText(i, 1));
						sqlbv4.put("endno", bmTemplate.getText(i, 1));
						tMap.put(sqlbv4, "DELETE");
				
						//添加轨迹
						
						LZCardTrackBSchema tLZCardTrackBSchema = new LZCardTrackBSchema();
						tLZCardTrackBSchema.setCertifyCode(tLZCardBSchema.getCertifyCode());
						tLZCardTrackBSchema.setSubCode("20");
						tLZCardTrackBSchema.setRiskCode("0");
						tLZCardTrackBSchema.setRiskVersion("0");
						tLZCardTrackBSchema.setStartNo(tLZCardBSchema.getStartNo());
						tLZCardTrackBSchema.setEndNo(tLZCardBSchema.getEndNo());
						tLZCardTrackBSchema.setSendOutCom(tLZCardBSchema.getSendOutCom());
						tLZCardTrackBSchema.setReceiveCom(tLZCardBSchema.getReceiveCom());
						tLZCardTrackBSchema.setSumCount("1");
						tLZCardTrackBSchema.setPrem("0.00");
						tLZCardTrackBSchema.setAmnt("0.00");
						tLZCardTrackBSchema.setTakeBackNo("86110020090610027239");
						tLZCardTrackBSchema.setStateFlag(StateFlag);
						tLZCardTrackBSchema.setOperateFlag("1");
						tLZCardTrackBSchema.setOperator(mGlobalInput.Operator);
						tLZCardTrackBSchema.setMakeDate(PubFun.getCurrentDate());
						tLZCardTrackBSchema.setMakeTime(PubFun.getCurrentTime());
						tLZCardTrackBSchema.setModifyDate(PubFun.getCurrentDate());
						tLZCardTrackBSchema.setModifyTime(PubFun.getCurrentTime());
				
						tLZCardTrackBSet.add(tLZCardTrackBSchema);
						
						tLZCardTrackBSchema= null;
						
						//如果选择状态为作废,则再插入一条销毁的轨迹
						if(StateFlag.equals("6")||StateFlag.equals("7"))
						{
							tLZCardTrackBSchema = new LZCardTrackBSchema();
							tLZCardTrackBSchema.setCertifyCode(tLZCardBSchema.getCertifyCode());
							tLZCardTrackBSchema.setSubCode("30");
							tLZCardTrackBSchema.setRiskCode("0");
							tLZCardTrackBSchema.setRiskVersion("0");
							tLZCardTrackBSchema.setStartNo(tLZCardBSchema.getStartNo());
							tLZCardTrackBSchema.setEndNo(tLZCardBSchema.getEndNo());
							tLZCardTrackBSchema.setSendOutCom(tLZCardBSchema.getSendOutCom());
							tLZCardTrackBSchema.setReceiveCom(tLZCardBSchema.getReceiveCom());
							tLZCardTrackBSchema.setSumCount("1");
							tLZCardTrackBSchema.setPrem("0.00");
							tLZCardTrackBSchema.setAmnt("0.00");
							tLZCardTrackBSchema.setTakeBackNo("86110020090610027239");
							tLZCardTrackBSchema.setStateFlag("11");
							tLZCardTrackBSchema.setOperateFlag("1");
							tLZCardTrackBSchema.setOperator(mGlobalInput.Operator);
							tLZCardTrackBSchema.setMakeDate(PubFun.getCurrentDate());
							tLZCardTrackBSchema.setMakeTime(PubFun.getCurrentTime());
							tLZCardTrackBSchema.setModifyDate(PubFun.getCurrentDate());
							tLZCardTrackBSchema.setModifyTime(PubFun.getCurrentTime());

						}
						
						tLZCardTrackBSet.add(tLZCardTrackBSchema);
						
						tLZCardBatchSchema.setFlag("Y");
						tLZCardBatchSchema.setReason("操作成功");
						tLZCardBatchSchema.setModifyDate(PubFun.getCurrentDate());
						tLZCardBatchSchema.setModifyTime(PubFun.getCurrentTime());	
						tLZCardBatchSet.add(tLZCardBatchSchema);
						
						tLZCardBSchema=null;
						tLZCardTrackBSchema=null;								
					}
					else
					{
						logger.debug("该单证不在单证表中或者该单证是2009-01-01后进行系统操作的单证"+bmTemplate.getText(i, 1));
						tLZCardBatchSchema.setFlag("N");
						tLZCardBatchSchema.setReason("该单证不在单证表中或者该单证是2009-01-01后进行系统操作的单证");
						tLZCardBatchSchema.setModifyDate(PubFun.getCurrentDate());
						tLZCardBatchSchema.setModifyTime(PubFun.getCurrentTime());
						tLZCardBatchSet.add(tLZCardBatchSchema);
					}					
				}
				
				tMap.put(tLZCardBSet, "INSERT");
				tMap.put(tLZCardTrackBSet, "INSERT");
				tMap.put(tLZCardBatchSet, "DELETE&INSERT");
				
				tExeSQL=null;
				tSSRS=null;
				tLZCardTrackBSet=null;
				tLZCardBSet=null;
			}
			
			
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, "解析上传文件错误!");
			return false;

		} catch (F1Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, "解析上传文件错误!");
			return false;

		}

		return true;
	}

	/**
	 * 初始化上传文件
	 * 
	 * @return
	 */
	private boolean initImportFile() {

		logger.debug("SaveExcelPath-----"
				+ SaveExcelPath);

		logger.debug("开始导入文件-----");

		File tFile = new File(SaveExcelPath);
		if (!tFile.exists()) {
			// @@错误处理
			CError.buildErr(this, "未上传文件到指定路径" + SaveExcelPath);
			return false;
		}

		logger.debug("-----导入文件结束!");
		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "全局信息传输失败!");
			return false;
		}

		if (StateFlag == null && StateFlag == null) {

			// @@错误处理
			CError.buildErr(this, "单证状态传输失败!");
			return false;
		}

		return true;

	}
	
	public VData getResult() {
		tVData.clear();
		return this.tVData;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String[] args) {
		CertifyBatchReportIn tPGI = new CertifyBatchReportIn();
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FileName", "20050426.xls");
		tTransferData.setNameAndValue("FilePath", "E:");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "claim";
		tG.ManageCom = "86";

		long a = System.currentTimeMillis();
		logger.debug(a);
		tVData.add(tTransferData);
		tVData.add(tG);
		tPGI.submitData(tVData, "");
		logger.debug(System.currentTimeMillis() - a);
	}

}
