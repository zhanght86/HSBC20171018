package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import com.sinosoft.lis.fininterface.LogInfoDeal;
import com.sinosoft.lis.fininterface.utility.FinCreateSerialNo;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/*******************************************************************************
 * <Title>校验规则处理引擎类</Title>
 *  <p>同时支持数据质量和差异校验</p>
 * @author lijs
 * @version 1.0
 * @CreatTime 2008-08-26
 */
public class FIRuleEngine {
private static Logger logger = Logger.getLogger(FIRuleEngine.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 校验类型标志 ***/
    private String ms_sOperate = "";

    /** 校验计划的版本号码 * */
    private String ms_VersionNo = null;

    /***事件结点***/
    private String ms_CallPointID = null;

    /** 业务数据 * */
    GlobalInput mGlobalInput = new GlobalInput();

    /** 校验的批次信息 **/
    private String ms_CheckBatchNo = null;

    /** 需要校验的批次号码 * */
    private String ms_BatchNo = null;

    /** 起始日期 * */
    private String ms_StartDate = null;

    /** 结束日期 * */
    private String ms_EndDate = null;

    /**回车换行**/
    private final String enter = "\r\n";

    /**日志记录类**/
    public LogInfoDeal tLogInfoDeal = null;

    public FIRuleEngine() {
    }

    /**
     * 通过取得批次号码和校验计划来针对数据的校验
     * @param cInputData
     * @param cOperate 标记是差异处理还是数据校验
     * cOperate = "quantity" 质量 需要批次号码、校验计划 分为几个过程：采集前、采集后、生成凭证前
     * cOperate = "difference" 差异 需要日期、业务类型、机构
     * @return 处理正常  true  失败 false
     */
    public boolean submitData(VData cInputData, String cOperate) {

        /**
         * 处理类型
         * quantity 采集数据校验标志
         * difference 业财差异校验标志
         **/
        ms_sOperate = cOperate;

        /**得到外部传入的数据,将数据备份到本类中**/
        if (!getInputData(cInputData)) {
            return false;
        }

        /**进行业务处理**/
        if (!dealData()) {
            return false;
        }

        /**准备往后台的数据**/
        if (!prepareOutputData()) {
            return false;
        }

        /**记录正常校验完毕日志，关闭日志读写**/
        tLogInfoDeal.WriteLogTxt("校验正常结束。" + enter);
        tLogInfoDeal.Complete(true);
        return true;

    }

    /**
     * 获取外部传入参数类
     * @param oData
     * @param ms_sOperate 标记是差异处理还是数据校验
     * cOperate = "quantity" 质量 需要批次号码、校验计划 分为几个过程：采集前、采集后、生成凭证前
     * cOperate = "difference" 差异 需要日期、业务类型、机构
     * @return 处理正常  true  失败 false
     */
    private boolean getInputData(VData oData) {

        /**日志存储容器**/
        StringBuffer oStringBuffer = new StringBuffer(1024);

        try {
            if (ms_sOperate != null) {
                /**采集数据质量校验参数获取*-**/
                if (ms_sOperate.equalsIgnoreCase("quantity")) {

                    /** 用户操作信息**/
                    mGlobalInput.setSchema((GlobalInput) oData
                                           .getObjectByObjectName("GlobalInput",
                            0));

                    /** 批次号码 * */
                    ms_BatchNo = (String) oData.get(1);

                    /** 版本号码 * */
                    ms_VersionNo = (String) oData.get(2);

                    /** 事件结点 * */
                    ms_CallPointID = (String) oData.get(3);

                }
                /**数据差异校验参数获取**/
                else if (ms_sOperate.equalsIgnoreCase("difference")) {

                    /** 用户操作信息 **/
                    mGlobalInput.setSchema((GlobalInput) oData
                                           .getObjectByObjectName("GlobalInput",
                            0));

                    /** 差异校验起始时间 **/
                    ms_StartDate = (String) oData.get(1);

                    /** 差异校验终止时间 **/
                    ms_EndDate = (String) oData.get(2);

                    /** 差异校验规则版本号 **/
                    ms_VersionNo = (String) oData.get(3);

                    /** 差异校验节点代码 **/
                    ms_CallPointID = (String) oData.get(4);

                } else {
                    buildError("FIRuleEngine", "getInputData",
                               "处理的类型" + ms_sOperate
                               + "未定义，请核对该类型是否正确！");
                    return false;
                }
            } else {

                buildError("FIRuleEngine", "getInputData",
                           "获取处理类型参数时失败，可能是处理类型参数为空请核对！");
                return false;
            }
        } catch (Exception e) {

            buildError("FIRuleEngine", "getInputData",
                       "获取参数时出现异常！异常信息：" + e.getMessage());
            return false;

        } finally {
            /**清空日志容器**/
            oStringBuffer = null;
        }
        return true;
    }

    /*****
     * 报错处理类
     * @param szModuleName 模块名称
     * @param szFunc 函数名称
     * @param szErrMsg 报错信息
     */
    private void buildError(String szModuleName, String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = szModuleName;
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        logger.debug(szErrMsg);
    }

    /**
     * 数据质量校验引擎启动或者数据差异校验引擎启动
     * @param ms_sOperate 标记是差异处理还是数据校验
     * cOperate = "quantity" 数据校验处理类启动 FIRuleDealQuData
     * cOperate = "difference" 数据差异校验处理类启动 FIRuleDealDifData
     * @return 处理正常  true  失败 false
     */
    private boolean dealData() {

        /**开始校验 -- 启动批次处理**/
        /**定义日志记录容器**/
        StringBuffer oStringBuffer = new StringBuffer(1024);

        try {
            /**校验批次号获取**/
            ms_CheckBatchNo = FinCreateSerialNo.getSerialNo("CheckBatchNo", 1,
                    20)[0];

            /** 开始校验 -- 启动数据质量校验**/
            if (ms_sOperate.equalsIgnoreCase("quantity")) {

                /**
                 * 开始数据校验 日志记录校验批次号，数据校验节点代码，提数批次号，规则版本号
                 * 07 - 采集数据质量校验  08 - 凭证数据质量校验
                 */
                String tEventType = "";
                if (ms_CallPointID.equals("01"))
                    tEventType = "07";
                else
                    tEventType = "08";
                tLogInfoDeal = new LogInfoDeal(mGlobalInput.Operator,
                                               tEventType);
                tLogInfoDeal.AddLogParameter("CheckBatchNo", "校验批次号",
                                             ms_CheckBatchNo);
                tLogInfoDeal.AddLogParameter("CallPointID", "数据校验节点代码",
                                             ms_CallPointID);
                tLogInfoDeal.AddLogParameter("BatchNo", "提数批次号", ms_BatchNo);
                tLogInfoDeal.AddLogParameter("VersionNo", "规则版本号", ms_VersionNo);
                if (!tLogInfoDeal.SaveLogParameter()) {
                    buildError("FIRuleEngine", "dealData",
                               tLogInfoDeal.mErrors.getFirstError());
                    return false;
                }
                /**数据质量校验处理类**/
                FIRuleDealQuData oDealQuData = new FIRuleDealQuData();

                /**传递外部参数**/
                VData oData = new VData();
                MMap oMap = new MMap();
                oMap.put("BatchNo", ms_BatchNo);
                oMap.put("VersionNo", ms_VersionNo);
                oMap.put("CallPointID", ms_CallPointID);
                oMap.put("CheckBatchNo", ms_CheckBatchNo);
                oData.clear();
                oData.addElement(mGlobalInput);
                oData.addElement(tLogInfoDeal);
                oData.addElement(oMap);

                if (!oDealQuData.submitData(oData, "quantity")) {

                    tLogInfoDeal.WriteLogTxt("质量校验结束，但校验失败出现如下问题：" +
                                             oDealQuData.mErrors.getFirstError() +
                                             "\n\r");
                    buildError("FIRuleEngine", "dealData",
                               "质量校验结束，但校验失败出现如下问题：" +
                               oDealQuData.mErrors.getFirstError());
                    return false;
                }
            }

            /** 开始校验 -- 启动业财差异校验 **/
            else if (ms_sOperate.equalsIgnoreCase("difference")) {

                /**
                 * 开始业财校验 记录校验节点，校验开始时间，校验结束时间，规则版本号等信息
                 * 09 - 采集数据差异校验 10 - 凭证数据差异校验
                 */
                String tEventType = "";
                if (ms_CallPointID.equals("01"))
                    tEventType = "09";
                else
                    tEventType = "10";

                tLogInfoDeal = new LogInfoDeal(mGlobalInput.Operator,
                                               tEventType);
                tLogInfoDeal.AddLogParameter("StartDate", "开始日期", ms_StartDate);
                tLogInfoDeal.AddLogParameter("EndDate", "结束日期", ms_EndDate);
                tLogInfoDeal.AddLogParameter("CallPointID", "校验节点",
                                             ms_CallPointID);
                tLogInfoDeal.AddLogParameter("VersionNo", "规则版本号", ms_VersionNo);
                tLogInfoDeal.AddLogParameter("CheckBatchNo", "校验批次号",
                                             ms_CheckBatchNo);
                if (!tLogInfoDeal.SaveLogParameter()) {
                    buildError("FIRuleEngine", "dealData",
                               tLogInfoDeal.mErrors.getFirstError());
                    return false;
                }
                /**
                 * 数据差异校验处理类
                 * 三个参数: 凭证类型、起始日期、结束日期
                 */
                FIRuleDealDifData oDealDifData = new FIRuleDealDifData();

                VData oData = new VData();
                MMap oMap = new MMap();
                oMap.put("StartDate", ms_StartDate);
                oMap.put("EndDate", ms_EndDate);
                oMap.put("VersionNo", ms_VersionNo);
                oMap.put("CheckBatchNo", ms_CheckBatchNo);
                oMap.put("CallPointID", ms_CallPointID);

                oData.clear();
                oData.addElement(mGlobalInput);
                oData.addElement(tLogInfoDeal);
                oData.addElement(oMap);

                oStringBuffer.append("校验批次号为" + ms_CheckBatchNo +
                                     "的业财差异批处理，以下为明细处理过程:" + enter);
                tLogInfoDeal.WriteLogTxt(oStringBuffer.toString());

                /**调用数据差异校验处理类**/
                if (!oDealDifData.submitData(oData, "difference")) {
                    tLogInfoDeal.WriteLogTxt("差异校验" + ms_CheckBatchNo +
                                             "校验失败!异常情况如下：" +
                                             oDealDifData.mErrors.getFirstError() +
                                             "。" + enter);
                    buildError("FIRuleEngine", "dealData",
                               "差异校验" + ms_CheckBatchNo + "校验失败!异常情况如下：" +
                               oDealDifData.mErrors.getFirstError() + "。");
                    return false;
                }

            } else {
                tLogInfoDeal.WriteLogTxt("校验失败，得到操作参数类型" + ms_sOperate +
                                         "不正确，校验结束。" + enter);
                buildError("FIRuleEngine", "dealData",
                           "校验失败，得到操作参数类型" + ms_sOperate + "不正确，校验结束。");
                return false;

            }
            return true;
        } catch (Exception e) {
            buildError("FIRuleEngine", "dealData",
                       "校验具体数据时出现异常！异常信息为：" + e.getMessage());
            tLogInfoDeal.WriteLogTxt("校验具体数据时出现异常！异常信息为：" + enter +
                                     e.getMessage());
            return false;
        } finally {

            oStringBuffer = null;
        }
    }

    /***************************************************************************
     *
     * @return
     */
    private boolean prepareOutputData() {

        return true;
    }

    /**
     *
     * 调试
     *
     * @param args
     */
    public static void main(String[] args) {
        //
        FIRuleEngine oRuleEngine = new FIRuleEngine();
        // oRuleEngine.submitData(cInputData, cOperate)

		 VData tVData = new VData();
		 GlobalInput tG = new GlobalInput();
		 tG.Operator = "001";
		 tG.ManageCom = "86";
		 tVData.clear();
		 tVData.addElement(tG);

		 tVData.addElement("00000000000000000126");
		 tVData.addElement("00000000000000000001");
		 tVData.addElement("01");
		 if(oRuleEngine.submitData(tVData, "quantity")){
		 logger.debug("T");
		 }else{
		 logger.debug("S");
		 }



//        VData tVData = new VData();
//        GlobalInput tG = new GlobalInput();
//        tG.Operator = "001";
//        tG.ManageCom = "86";
//        tVData.clear();
//        tVData.addElement(tG);
//
//        tVData.addElement("2007-01-01");
//        tVData.addElement("2007-01-01");
//        tVData.addElement("00000000000000000001");
//        tVData.addElement("01");
//        if (oRuleEngine.submitData(tVData, "difference")) {
//            logger.debug("T");
//        } else {
//            logger.debug("S");
//        }

        // /*******
         // * 提数SQL脚本测试
         // */
        // // FIRulePlanDefDetail
        // //'Bug' as ruleid,

//		FIAboriginalDataDB oFIAboriginalDataDB = new FIAboriginalDataDB();
//		FIAboriginalDataDBSet oFIAboriginalDataDBSet = new FIAboriginalDataDBSet();
//
//		oFIAboriginalDataDB.setBatchNo("00000000000000000714");
//		// oFIAboriginalDataDBDetailoFIAboriginalDataDBDetail.query();
//
//		String strBatchNoSQLDetail = "select * from fiaboriginaldata batchno = '00000000000000000714' "
//				+ " and checkflag <> '0'";
//		oFIAboriginalDataDBSet = (FIAboriginalDataDBSet) oFIAboriginalDataDB
//				.executeQuery(strBatchNoSQLDetail);
//
//		// oFIAboriginalDataDBSet.set(aSet)
//		// oFIAboriginalDataDBSet
//		for (int i = 1; i <= oFIAboriginalDataDBSet.size(); i++) {
//
//			FIAboriginalDataSchema oAboriginalDataSchema = new FIAboriginalDataSchema();
//			// oAboriginalDataSchema
//
//		}
//
//		//
//		// if(oFIAboriginalDataDB.update(strBatchNoSQLDetail)){
//		// //更新成功;
//		// }
//		//
//
//		// 00000000000000000714

    }

}
