/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.f1print;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LDSysVarSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * LCGrpContF1PUI
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 保单xml文件生成UI部分
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class LCGrpContF1PUI implements BusinessService {
private static Logger logger = Logger.getLogger(LCGrpContF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String prtSeq = null;
	private String startNo = null;
	private String mOperate = "";
    public String mContent;
	public int tSucc=0,tFail=0;

	public LCGrpContF1PUI() {
	}

	/**
	 * 数据处理主函数
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT") && !cOperate.equals("REPRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			mOperate = cOperate;

			LCGrpContF1PBL tLCGrpContF1PBL = new LCGrpContF1PBL();
			logger.debug("Start LCGrpContF1PUI Submit ...");

			if (!tLCGrpContF1PBL.submitData(cInputData, cOperate)) {
				if (tLCGrpContF1PBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tLCGrpContF1PBL.mErrors);
					return false;
				} else {
					buildError("sbumitData", "LCGrpContF1PBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tLCGrpContF1PBL.getResult();
				mContent=tLCGrpContF1PBL.mContent;
				tSucc=tLCGrpContF1PBL.tSucc;
				tFail=tLCGrpContF1PBL.tFail;
				TransferData dto = new TransferData();
				dto.setNameAndValue("Content", mContent);
				dto.setNameAndValue("Succ", tSucc);
				dto.setNameAndValue("Fail", tFail);
				mResult.add(dto);
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submit", "发生异常");
			return false;
		}
	}

	/**
	 * 返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 出错处理
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCGrpContF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {



		// 用于保单打印用的数据模板，主要用于团单下被保人清单
		String szTemplatePath = "D:/work6/work6.5/ui/f1print/template/"; // 模板路径
		logger.debug("保单团单下被保人清单模板XML文件保存在应用服务器的路径是：" + szTemplatePath);
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		LCGrpContF1PUI tZhuF1PUI = new LCGrpContF1PUI();
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		String tContSQL =" select * "
			+" 	from lcgrpcont a "
			+"  where prtno in ('86120100120180', '86120100120173', '86120100120061',"
							+" '86120100056568', '86120100055946', '86120100056590',         "
							+" '86120100056574', '86120100056572', '86120100120170',         "
							+" '86120100120166', '86120100120176', '86120100120062',         "
							+" '86120100056555', '86120100120196', '86120100120201',         "
							+" '86120100120200', '86120100056553', '86120100056576',         "
							+" '86120100120051', '86120100120054', '86120100056570',         "
							+" '86120100056571', '86120100055936', '86120100056551',         "
							+" '86120100055944', '86120100056550', '86120100056548',         "
							+" '86120100056545',  '86120100120181',         "
							+" '86120100120178', '86120100120177', '86120100120174',         "
							+" '86120100120268', '86120100120171', '86120100120169',         "
							+" '86120100120168', '86120100120167', '86120100056557',         "
							+" '86120100120053') and not exists (select 'X'  from lcgrpcont b where a.prtno=b.prtno  and  printcount='1')  "  ;
		tLCGrpContSet = tLCGrpContDB.executeQuery(tContSQL);

		for (int k=1;k<=tLCGrpContSet.size();k++)
		{
			String tSN = "";
			tSN = PubFun1.CreateMaxNo("SNPolPrint", 10);
			LCGrpContSet mLCGrpContSet = new LCGrpContSet();
			mLCGrpContSet.add(tLCGrpContSet.get(k));			
			VData vData = new VData();
			vData.addElement(tG);
			vData.addElement(mLCGrpContSet);
			vData.add(szTemplatePath);
			if (!tZhuF1PUI.submitData(vData, "PRINT")) {
				logger.debug(tZhuF1PUI.mErrors.getFirstError());
				logger.debug("fail to get print data");
			}
			InputStream ins = (InputStream) (tZhuF1PUI.getResult().get(0));
			// String strTemplatePath = application.getRealPath("xerox/printdata") +
			// "/";
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("printdatanew");
			LDSysVarSet tLDSysVarSet = new LDSysVarSet();
			LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();
			tLDSysVarSet = tLDSysVarDB.query();
			if (tLDSysVarSet.size() > 0) {
				tLDSysVarSchema = tLDSysVarSet.get(1);
			} else {
				logger.debug("无法获取保存XML文件的应用服务器的路径!");
			}
			// 用于保存在应用服务器上某个路径之下
			String strTemplatePath = tLDSysVarSchema.getSysVarValue().trim();
			strTemplatePath = "c:/XML/"; // 便于测试暂时放在C盘
			// It is important to use buffered streams to enhance performance
			BufferedInputStream bufIs = new BufferedInputStream(ins);
			String strTime = PubFun.getCurrentDate() + "_"
					+ PubFun.getCurrentTime();
			strTime = strTime.replace(':', '_').replace('-', '_');
			strTime = strTime + "_" + tSN.trim();
			// add by ssx 2008-04-24 建立分级目录
			String[] step = strTime.split("_");
			String year = step[0];
			String month = step[1];
			String day = step[2];
			strTemplatePath += year + "/" + month + "/" + day + "/";
			File file = new File(strTemplatePath);
			if (!file.exists()) {
				logger.debug("开始新建文件夹!");
				file.mkdirs();
			} else {
				logger.debug("目录已经存在: ");
			}// add end
			logger.debug("保单XML文件保存在应用服务器的路径是：" + strTemplatePath);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(strTemplatePath
						+ "LCGrpPolData" + strTime + ".xml");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedOutputStream bufOs = new BufferedOutputStream(fos);
			int n = 0;

			try {
				while ((n = bufIs.read()) != -1) {
					bufOs.write(n);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bufOs.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bufOs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public CErrors getErrors() {
		return mErrors;
	}
}

