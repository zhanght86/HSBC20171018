/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCRReportPrtDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author libin
 * @version 1.0
 */
public class BqHbAskPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(BqHbAskPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private VData mInputData = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private XmlExportNew xmlExportAll = null;

	public BqHbAskPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("--------------------start------------------");
		String Content = "";
		String PrtSeq = mLOPRTManagerSchema.getPrtSeq();
		// String tMissionID = request.getParameter("MissionID");
		// String tSubMissionID = request.getParameter("SubMissionID");

		VData tVData = new VData();
		VData mResult = new VData();
		tVData = mInputData;

		EdorAskPrintUI tEdorAskPrintUI = new EdorAskPrintUI();
		XmlExport txmlExport = new XmlExport();
		// ///////////add by yaory for generate distinct .vts then
		// combine-2005-7-5-18:47
		// first query how many questionaires
		// ///现在只有两个问卷一个生调问卷，一个生存问卷，用两个分支实现，如果用户选择一个问卷程序
		// ///就生成一个数据文件，然后生成通知书。最后调用生成的数据文件进行合并。

		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select askcode from LCRReportPrt where PrtSeq='"
				+ "?PrtSeq?" + "'");
		sqlbv.put("PrtSeq", PrtSeq);
		testSSRS = exeSql.execSQL(sqlbv);
		LCRReportPrtDB tLCRReportPrtDB = new LCRReportPrtDB();
		tLCRReportPrtDB.setPrtSeq(PrtSeq);
		
		logger.debug("问卷的行数===" + testSSRS.MaxRow);
		xmlExportAll = new XmlExportNew();
		xmlExportAll.createDocument("保全核保问卷通知书");
		String uLanguage = PrintTool.getCustomerLanguage(tLCRReportPrtDB.query().get(1).getCustomerNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExportAll.setUserLanguage(uLanguage);
		xmlExportAll.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		// 问卷通知书
		if (!tEdorAskPrintUI.submitData(tVData, "PRINT3")) {
			Content = tEdorAskPrintUI.mErrors.getFirstError().toString();
		} else {
			mResult = tEdorAskPrintUI.getResult();
			txmlExport = (XmlExport) mResult.getObjectByObjectName("XmlExport",
					0);
			if (txmlExport == null) {
				Content = "没有得到要显示的数据文件";
			} else {
				xmlExportAll.addDataSet(xmlExportAll.getDocument()
						.getRootElement(), txmlExport.getDocument()
						.getRootElement());
			}
		}

		for (int i = 1; i <= testSSRS.MaxRow; i++) {
			if (testSSRS.GetText(i, 1).equals("87")) {
				logger.debug("进入生调问卷!");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINT1")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			}

			else if (testSSRS.GetText(i, 1).equals("88")) {
				logger.debug("进入财务问卷!");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINT2")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			}

			else if (testSSRS.GetText(i, 1).equals("89")) {
				logger.debug("疾病问卷（癫痫）!");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTa")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("90")) {
				logger.debug("疾病问卷（哮喘）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTb")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("91")) {
				logger.debug("疾病问卷（糖尿病）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTc")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("92")) {
				logger.debug("疾病问卷（甲状腺）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTd")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("93")) {
				logger.debug("疾病问卷（胸痛）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTe")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("94")) {
				logger.debug("疾病问卷（头痛、头晕）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTf")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("95")) {
				logger.debug("女性健康补充问卷  ");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTg")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("96")) {
				logger.debug("疾病问卷（肝炎）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTh")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("97")) {
				logger.debug("疾病问卷（呼吸道）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTi")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("98")) {
				logger.debug("疾病问卷（消化系统）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTj")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("99")) {
				logger.debug("婴幼儿健康问卷");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTk")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("100")) {
				logger.debug("体育问卷");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTl")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("101")) {
				logger.debug("成长无忧健康问卷");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTm")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("102")) {
				logger.debug("病史问卷 ");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTn")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("103")) {
				logger.debug("疾病问卷（高血压）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTo")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("104")) {
				logger.debug("驾驶执照执有者问卷 ");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTp")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("105")) {
				logger.debug("外出人员调查问卷");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTq")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("106")) {
				logger.debug("外地人士投保   ");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTr")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("107")) {
				logger.debug("疾病问卷（消化性溃疡）");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTs")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

			else if (testSSRS.GetText(i, 1).equals("108")) {
				logger.debug("沿海渔业从业人员  ");
				if (!tEdorAskPrintUI.submitData(tVData, "PRINTt")) {
					Content = tEdorAskPrintUI.mErrors.getFirstError()
							.toString();
				} else {
					mResult = tEdorAskPrintUI.getResult();
					txmlExport = (XmlExport) mResult.getObjectByObjectName(
							"XmlExport", 0);
					if (txmlExport == null) {
						Content = "没有得到要显示的数据文件";
					} else {
						xmlExportAll.addDataSet(xmlExportAll.getDocument()
								.getRootElement(), txmlExport.getDocument()
								.getRootElement());
					}
				}
			} // end of if .

		} // end of for.

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {

		// 全局变量
		this.mInputData = (VData) cInputData.clone();
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		return true;
	}

	public VData getResult() {
		mResult.clear();
		mResult.add(xmlExportAll);
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
	}
}
