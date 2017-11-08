/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;

import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:系统号码管理（MSRS核心业务系统）生成系统号码
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 * @version 1.0
 */

import java.math.BigInteger;
import java.sql.Connection;

import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

public class SysMaxNoLifeInsurance implements com.sinosoft.lis.pubfun.SysMaxNo {
    private static Logger logger = Logger.getLogger(SysMaxNoLifeInsurance.class);

    public SysMaxNoLifeInsurance() {
    }

    /**
     * <p>
     * 生成流水号的函数
     * <p>
     * <p>
     * 号码规则：机构编码 日期年 校验位 类型 流水号
     * <p>
     * <p>
     * 1-6 7-10 11 12-13 14-20
     * <p>
     * 
     * @param cNoType
     *            为需要生成号码的类型
     * @param cNoLimit
     *            为需要生成号码的限制条件（要么是SN，要么是机构编码）
     * @param cVData
     *            为需要生成号码的业务相关限制条件
     * @return 生成的符合条件的流水号，如果生成失败，返回空字符串""
     */
    public String CreateMaxNo(String cNoType, String cNoLimit, VData cVData) {
        // 传入的参数不能为空，如果为空，则直接返回
        if ((cNoType == null) || (cNoType.trim().length() <= 0) || (cNoLimit == null)) {
            logger.debug("NoType长度错误或者NoLimit为空");
            return null;
        }

        // 默认流水号位数
        int serialLen = 10;

        String tReturn = null;
        cNoType = cNoType.toUpperCase();
        // logger.debug("-----------cNoType:"+cNoType+"
        // cNoLimit:"+cNoLimit);

        // cNoLimit如果是SN类型，则cNoType只能是下面类型中的一个
        if (cNoLimit.trim().toUpperCase().equals("SN")) {
            if (cNoType.equals("GRPNO") || cNoType.equals("CUSTOMERNO") || cNoType.equals("SUGDATAITEMCODE")
                    || cNoType.equals("SUGITEMCODE") || cNoType.equals("SUGMODELCODE") || cNoType.equals("SUGCODE")
                    || cNoType.equals("ACCNO") || cNoType.equals("RPTONLYNO")) {
                serialLen = 10;
            } else if (cNoType.equals("COMMISIONSN")) {
                serialLen = 10;
            } else if (cNoType.equals("GRPPROPOSALNO")) {
                tReturn = tReturn + "12";
            } else if (cNoType.equals("YBTBATDAYNO")) {
                serialLen = 3;
            }

            else {
                logger.debug("错误的NoLimit");
                return null;
            }
        }

        if (cNoType.equals("YBTBATDAYNO")) {
            tReturn = (String) cVData.get(0);
        }
        // 扫描批次号,Added by niuzj 2006-04-27,每个分公司每天一个批次号
        // 前8位为机构代码,再8位为日期,再1位是业务类型,后3位为大流水号
        if (cNoType.equals("SCANNO")) {
            // cNoType为“SCANNO”
            // cNoLimit为登陆机构代码
            // cVData为业务类型
            String tStr = PubFun.getCurrentDate();
            String tMng = cNoLimit.trim(); // 登录机构代码
            String tBusstype = ""; // 业务类型

            if (cVData.get(0).equals(null)) {
                logger.debug("传入的业务类型cVData为空");
                return null;
            } else if (cVData.get(0).equals("TB")) { // 新契约
                tBusstype = "1";
            } else if (cVData.get(0).equals("BQ")) { // 保全
                tBusstype = "2";
            } else if (cVData.get(0).equals("LP")) { // 理赔
                tBusstype = "3";
            } else if (cVData.get(0).equals("XQ")) { // 续期
                tBusstype = "4";
            } else {
                logger.debug("传入的业务类型cVData错误");
                return null;
            }

            if (tMng.length() == 2) { // 2位管理机构,前面补6个0
                tReturn = "000000" + tMng + tStr.substring(0, 4) + tStr.substring(5, 7) + tStr.substring(8, 10)
                        + tBusstype;
            } else if (tMng.length() == 4) { // 4位管理机构,前面补4个0
                tReturn = "0000" + tMng + tStr.substring(0, 4) + tStr.substring(5, 7) + tStr.substring(8, 10)
                        + tBusstype;
            } else if (tMng.length() == 6) { // 6位管理机构,前面补2个0
                tReturn = "00" + tMng + tStr.substring(0, 4) + tStr.substring(5, 7) + tStr.substring(8, 10) + tBusstype;
            } else if (tMng.length() == 8) { // 8位管理机构,前面不补0
                tReturn = tMng + tStr.substring(0, 4) + tStr.substring(5, 7) + tStr.substring(8, 10) + tBusstype;
            } else {
                logger.debug("传入的机构代码cNoLimit错误");
                return null;
            }
            serialLen = 3; // 3位为大流水号
        }

        String tStr = getMaxNo(cNoType, tReturn);
        tStr = PubFun.LCh(tStr, "0", serialLen);
        if (tReturn.equals("SN") || cNoType.equals("YBTBATDAYNO")) {
            tReturn = tStr.trim();
            // tReturn = tStr.trim() + "0";
        } else {
            tReturn = tReturn.trim() + tStr.trim();
        }
        logger.debug("------tReturn:" + tReturn);
        return tReturn;
    }

    /**
     * <p>
     * 生成流水号的函数
     * <p>
     * <p>
     * 号码规则：机构编码 日期年 校验位 类型 流水号
     * <p>
     * <p>
     * 1-6 7-10 11 12-13 14-20
     * <p>
     * 
     * @param cNoType
     *            为需要生成号码的类型
     * @param cNoLimit
     *            为需要生成号码的限制条件（要么是SN，要么是机构编码）
     * @return 生成的符合条件的流水号，如果生成失败，返回空字符串""
     */
    public String CreateMaxNo(String cNoType, String cNoLimit) {
        // 传入的参数不能为空，如果为空，则直接返回
        if ((cNoType == null) || (cNoType.trim().length() <= 0) || (cNoLimit == null)) {
            logger.error("NoType长度错误或者NoLimit为空");

            return null;
        }

        // 默认流水号位数
        int serialLen = 10;
        if (cNoType.equals("OTOF")) {
            serialLen = 7;
        }

        String tReturn = null;
        cNoType = cNoType.toUpperCase();
        // logger.debug("-----------cNoType:"+cNoType+"
        // cNoLimit:"+cNoLimit);

        // cNoLimit如果是SN类型，则cNoType只能是下面类型中的一个
        if (cNoLimit.trim().toUpperCase().equals("SN")) { // modify by yt
                                                            // 2002-11-04
            // if (cNoType.equals("GRPNO") || cNoType.equals("CUSTOMERNO") ||
            // cNoType.equals("SugDataItemCode") ||
            // cNoType.equals("SugItemCode") || cNoType.equals("SugModelCode")
            // || cNoType.equals("SugCode"))
            if (cNoType.equals("GRPNO") || cNoType.equals("CUSTOMERNO") || cNoType.equals("SUGDATAITEMCODE")
                    || cNoType.equals("SUGITEMCODE") || cNoType.equals("SUGMODELCODE") || cNoType.equals("SUGCODE")
                    || cNoType.equals("ACCNO") || cNoType.equals("RPTONLYNO") || cNoType.equals("APPNTNO")
                    || cNoType.equals("BANKCODE") || cNoType.equals("LCPOSITION")
                    || cNoType.equals("LCACCASCRIPTION")) {
                serialLen = 10;
            }
            // add by renhl 修改为相当与个位加1 2008-10-14
            else if (cNoType.equals("COMMISIONSN")) {
                serialLen = 10;
            } else if (cNoType.equals("GRPPROPNO") || cNoType.equals("GRPPROPNO2")) {
                serialLen = 14;
            }

            else {
                logger.error("错误的NoLimit");

                return null;
            }
        }

        // 航意险6位流水号
        if (cNoType.equals("AIRPOLNO")) { // modify by yt 2002-11-04
            serialLen = 6;
        }
        // 特殊险种流水号长度处理
        if (cNoType.equals("AGENTCODE") || cNoType.equals("MANAGECOM")) {
            serialLen = 4;
        }

        tReturn = cNoLimit.trim();
        // logger.debug("tReturn:"+tReturn);

        String tCom = ""; // 两位机构
        String tCenterCom = ""; // 中支公司

        if (tReturn.length() >= 4 && !(cNoType.toUpperCase().equals("CONTNO") || cNoType.toUpperCase().equals("PAYNO")
                || cNoType.equals("PRTSEQNO") || cNoType.equals("GETNOTICENO") || cNoType.equals("GETNO")
                || cNoType.equals("PAYNOTICENO") || cNoType.equals("GRPPROPOSALNO") || cNoType.equals("GRPRPTNO")
                || cNoType.equals("GRPRGTNO02") || cNoType.equals("GRPRGTNO11") || cNoType.equals("RPTNO")
                || cNoType.equals("RGTNO") || cNoType.equals("GRPRGTNO03") || cNoType.equals("GRPCONTNO")
                // || cNoType.equals("PROPOSALNO")
                || cNoType.equals("SPECNO"))) {
            tCom = tReturn.substring(2, 4);
            tCom = "0" + tCom; // 加一位较验位
            if (tReturn.length() >= 6) {
                tCenterCom = tReturn.substring(4, 6);
            } else {
                tCenterCom = "00";
            }
            tReturn = tReturn.substring(0, 4);
        }
        //////////////////////////////////////// 工作流升级////////////////////////////////////////////////////////
        if (cNoType.equals("PROCESSID")) {
            tReturn = "6" + tReturn;
            serialLen = 10 - tReturn.length();
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////

        // 生成各种号码
        // 投保单险种号码
        if (cNoType.equals("PROPOSALNO")) {
            tReturn = "11" + tCom;
            // serialLen = 7;
        }

        // 集体投保单险种号码
        else if (cNoType.equals("GRPPROPOSALNO")) {
            tReturn = tReturn + "12";
            serialLen = 7;
        }

        // 总单投保单号码
        else if (cNoType.equals("PROPOSALCONTNO")) {
            tReturn = "13" + tCom;
        }

        // 集体投保单号码ProposalGrpContNo,LDMaxNo里最大长度为15，所以用ProGrpContNo代替
        else if (cNoType.equals("PROGRPCONTNO")) {
            tReturn = "14" + tCom;
        }

        // 保单险种号码
        else if (cNoType.equals("POLNO")) {
            tReturn = "21" + tCom;
        }

        // 集体保单险种号码
        else if (cNoType.equals("GRPPOLNO")) {
            tReturn = "22" + tCom;
        }

        // 合同号码
        else if (cNoType.equals("CONTNO")) {
            // logger.debug("进入我的底盘");
            // tReturn = "00" + tCom;
            // tReturn = PubFun1.creatVerifyDigit(tReturn);

            tReturn = tReturn + "21";

            // tReturn = "0";
            serialLen = 7;
        } else if (cNoType.equals("PRTSEQNO")) {
            // tReturn = tReturn + "91";
            tReturn = tReturn + "81";
            serialLen = 7;// 参考5.3
        }

        // 集体合同号码 修改成5.3 GRPPOLNO的生成逻辑 modify on 2009-01-09
        else if (cNoType.equals("GRPCONTNO")) {
            tReturn = tReturn + "22";
            serialLen = 7;
        }

        // 交费通知书号码
        else if (cNoType.equals("PAYNOTICENO")) {
            // tReturn = "31" + tCom;
            // tReturn = tCom.substring(1);
            tReturn = tReturn + "31";
            serialLen = 7;
        }

        // 交费收据号码
        /*
         * else if (cNoType.equals("PAYNO")) { tReturn = "32" + tCom; }
         */
        // 交费通知书号码
        else if (cNoType.equals("PAYNO")) {
            tReturn = tReturn + "32";
            serialLen = 7; // 参考5.3 modify on 2009-01-09
        }
        /*
         * //给付通知书号码 else if (cNoType.equals("GETNOTICENO")) { tReturn = "36" +
         * tCom; serialLen = 8; }
         */
        // 交费收据号
        else if (cNoType.equals("GETNOTICENO")) {
            tReturn = tReturn + "36";
            serialLen = 7;
        }

        // 给付通知书号码
        else if (cNoType.equals("GETNO")) {
            // tReturn = "37" + tCom;
            tReturn = tReturn + "37";
            serialLen = 7; // 参考5.3
        }

        // 保全受理号码 //6+4+1+2+1+7（机构+年份 +校验位 + 特殊字符 +防充字符 +流水号）
        else if (cNoType.equals("EDORACCEPTNO")) { // changed by pst for

            String tStr = PubFun.getCurrentDate();
            tReturn = cNoLimit.substring(0, 6) + tStr.substring(0, 4) + "41";
            // tStr.substring(8, 10);
            serialLen = 7;
        }
        // 申请归档号，用于MS保全项目连续归档，在保全确认时生成
        else if (cNoType.equals("EDORCONFNO")) { // 连续
            String tStr = PubFun.getCurrentDate();
            tReturn = cNoLimit.substring(0, 6) + tStr.substring(0, 4) + "42";
            // tStr.substring(8, 10);
            serialLen = 7;
        }

        // 批改申请号码
        else if (cNoType.equals("EDORAPPNO")) {
            String tStr = PubFun.getCurrentDate();
            tReturn = cNoLimit.substring(0, 6) + tStr.substring(0, 4) + "46";
            // tStr.substring(8, 10);
            serialLen = 7;
        }

        // 批单号码
        else if (cNoType.equals("EDORNO")) {
            String tStr = PubFun.getCurrentDate();
            tReturn = cNoLimit.substring(0, 6) + tStr.substring(0, 4) + "46";
            // tStr.substring(8, 10);
            serialLen = 7;
        }

        // 集体批单申请号码
        else if (cNoType.equals("EDORGRPAPPNO")) {
            String tStr = PubFun.getCurrentDate();
            tReturn = cNoLimit.substring(0, 6) + tStr.substring(0, 4) + "43";
            // tStr.substring(8, 10);
            serialLen = 7;
        }

        // 集体批单号码
        else if (cNoType.equals("EDORGRPNO")) {
            String tStr = PubFun.getCurrentDate();
            tReturn = cNoLimit.substring(0, 6) + tStr.substring(0, 4) + "44";
            // tStr.substring(8, 10);
            serialLen = 7;
        }
        // 保全试算流水号
        else if (cNoType.equals("EDORTESTNO")) {
            String tStr = PubFun.getCurrentDate();
            tReturn = cNoLimit.substring(0, 6) + tStr.substring(0, 4) + "45";
            // tStr.substring(8, 10);
            serialLen = 7;
        } // end changed
            // 核保测算号码
        else if (cNoType.equals("ASKPRTNO")) {
            String tStr = PubFun.getCurrentDate();
            // 登录机构为86，则补两位！
            if (tReturn.length() == 2) {
                tReturn = "00" + tReturn;
            }
            tReturn = tReturn + tStr.substring(2, 4) + tStr.substring(5, 7) + tStr.substring(8, 10);
            cNoLimit = tReturn;
            serialLen = 3;
        }
        // 套餐编码
        else if (cNoType.equals("CONTPLANCODE")) {
            // 登录机构为86，则补两位！
            if (tReturn.length() == 2) {
                tReturn = tReturn + "00";
            } else {
                tReturn = tCom.substring(1, 3) + tCenterCom;
            }
            cNoLimit = tReturn;
            serialLen = 4;
        }

        // 单证扫描批次号,Added by niuzj 2005-10-22,每个分公司每天一个批次号
        // 前8位为机构代码,再8位为日期,后4位为大流水号
        else if (cNoType.equals("SCANNO")) {
            String tStr = PubFun.getCurrentDate();
            String tMng = cNoLimit.trim(); // 登录机构代码
            if (tMng.length() == 2) // 2位管理机构,前面补6个0
            {
                tReturn = "000000" + tMng + tStr.substring(0, 4) + tStr.substring(5, 7) + tStr.substring(8, 10);
            } else if (tMng.length() == 4) // 4位管理机构,前面补4个0
            {
                tReturn = "0000" + tMng + tStr.substring(0, 4) + tStr.substring(5, 7) + tStr.substring(8, 10);
            } else if (tMng.length() == 6) // 6位管理机构,前面补2个0
            {
                tReturn = "00" + tMng + tStr.substring(0, 4) + tStr.substring(5, 7) + tStr.substring(8, 10);
            } else // 8位管理机构,前面不补0
            {
                tReturn = tMng + tStr.substring(0, 4) + tStr.substring(5, 7) + tStr.substring(8, 10);
            }
            serialLen = 4; // 4位为大流水号
        }

        // else if (cNoType.equals("ACCNO"))
        // {
        // tReturn = "8" + tCom;
        // }
        //

        // else if (cNoType.equals("RPTONLYNO"))
        // {
        // tReturn = "9" + tCom;
        // }

        // 报案编号
        else if (cNoType.equals("RPTNO")) {
            tReturn = tReturn + "50";
            serialLen = 7;
        }

        // 立案编号
        else if (cNoType.equals("RGTNO")) {
            // tReturn = "51";
            tReturn = tReturn + "51";
            serialLen = 7;
        }

        // 团体报案编号
        else if (cNoType.equals("GRPRPTNO")) {
            tReturn = tReturn + "60";
            serialLen = 7;
        }

        // 账户案件团体立案编号
        else if (cNoType.equals("GRPRGTNO02")) {
            tReturn = tReturn + "63";
            serialLen = 7;
        }

        // 批量案件 团体立案编号
        else if (cNoType.equals("GRPRGTNO03")) {
            tReturn = tReturn + "62";
            serialLen = 7;
        }

        // 普通案件团体立案编号
        else if (cNoType.equals("GRPRGTNO11")) {
            tReturn = tReturn + "61";
            serialLen = 7;
        }

        // 赔案编号
        else if (cNoType.equals("CLMNO")) {
            tReturn = "52" + tCom;
        }

        // 拒案编号
        else if (cNoType.equals("DECLINENO")) {
            tReturn = "53" + tCom;
        }

        // 报案分案编号
        else if (cNoType.equals("SUBRPTNO")) {
            tReturn = "54" + tCom;
        }

        // 立案分案编号
        else if (cNoType.equals("CASENO")) {
            tReturn = "55" + tCom;
        }

        // 合同号
        else if (cNoType.equals("PROTOCOLNO")) {
            tReturn = "71" + tCom;
        }

        // 单证印刷号码
        else if (cNoType.equals("PRTNO")) {
            tReturn = "80" + tCom;
        }
        /*
         * //打印管理流水号 else if (cNoType.equals("PRTSEQNO")) { tReturn = "81" +
         * tCom; serialLen = 8; }
         */

        // 打印管理流水号
        else if (cNoType.equals("PRTSEQ2NO")) {
            tReturn = "82" + tCom;
        }
        // 保单打印批次号
        else if (cNoType.equals("CONTPRTSEQ")) {
            tCom = tCom.substring(1, 3);
            tReturn = "83" + tCom;
            serialLen = 7;

        }
        // 银保通对帐批次号
        else if (cNoType.equals("YBTNO")) {
            tReturn = "10";
            serialLen = 7;
        }

        // 回收清算单号
        else if (cNoType.equals("TAKEBACKNO")) {
            tReturn = "61" + tCom;
        }

        // 银行代扣代付批次号
        else if (cNoType.equals("BATCHNO")) {
            tReturn = "62" + tCom;
        }

        // 接口凭证id号
        else if (cNoType.equals("VOUCHERIDNO")) {
            tReturn = "63" + tCom;
        }

        // 佣金号码
        else if (cNoType.equals("WAGENO")) {
            tReturn = "90" + tCom;
        }

        // 卡单结算号
        else if (cNoType.equals("BALANCENO")) {
            String tStr = PubFun.getCurrentDate();
            tReturn = tReturn + tStr.substring(2, 4) + tStr.substring(5, 7) + tStr.substring(8, 10);
            serialLen = 4;
        }
        // 流水号
        else if (cNoType.equals("SERIALNO")) {
            // 万能险用的是99，5.3用的是98，目前6.5保持和5.3一致，用98 //modify by weikai
            tReturn = "98" + tCom;
            // tReturn = "99" + tCom; // 与5.3区别
        } else if (cNoType.equals("SPECNO")) {
            tReturn = tReturn + "99";
            serialLen = 7;
        }

        // tongmeng 2007-11-29 modify

        // if (tReturn.length() >= 12
        // && (cNoType.toUpperCase().equals("CONTNO")
        // || cNoType.toUpperCase().equals("PAYNO")
        // || cNoType.equals("PRTSEQNO")
        // || cNoType.equals("GETNOTICENO") || cNoType
        // .equals("GETNO")
        // || cNoType.equals("GRPPROPOSALNO")
        // || cNoType.equals("GRPCONTNO")
        // //|| cNoType.equals("PROPOSALNO")
        // || cNoType.equals("SPECNO"))) {
        // tReturn = tReturn.substring(0, 10) + "0"
        // + tReturn.substring(10, 12);
        // serialLen =7;
        // }
        // else
        // {
        // tReturn += "99";
        // }

        int tMaxNo = 0;
        cNoLimit = tReturn;
        // logger.debug("cNoLimit:"+cNoLimit);
        // tongmeng 2009-02-25 modify
        // 增加核保通知书流水号生成，并且使用SEQRENCE。
        if (cNoType.equals("UWPRTSEQ")) {
            serialLen = 8;
            tReturn = cNoLimit;
        }
        // tongmeng 2008-03-26 modify
        // 对于DOCID 和PAGEID 使用序列方式
        // JiangJian 2016-04-11 仅ORACLE数据库使用序列方式生成
        if ((cNoType.equals("DOCID") || cNoType.equals("PAGEID") || cNoType.equals("UWPRTSEQ"))
                && "ORACLE".equalsIgnoreCase(SysConst.DBTYPE)) {
            try {
                String tSQL_SEQ = " select " + "SEQ_" + cNoType + ".nextval from dual ";
                logger.debug(tSQL_SEQ);
                ExeSQL tExeSQL = new ExeSQL();
                String tValue = "";
                SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
                sqlbv4.sql(tSQL_SEQ);
                sqlbv4.put("cNotype", cNoType);
                tValue = tExeSQL.getOneValue(sqlbv4);
                logger.debug("tValue" + tValue);
                // return tValue;
                // System.
                tMaxNo = Integer.parseInt(tValue);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        } else {
            // 其他
            tMaxNo = Integer.valueOf(getMaxNo(cNoType, cNoLimit));
        }

        // 按照5.3的模式修改
        if (tReturn.length() >= 12) {
            tReturn = tReturn.substring(0, 10) + "0" + tReturn.substring(10, 12);
        }

        // if (cNoType.toUpperCase().equals("CONTNO")
        // //|| cNoType.toUpperCase().equals("PAYNO") 修改成5.3 的生成逻辑 modify on
        // 2009-01-09
        // //|| cNoType.equals("PRTSEQNO") 参考 5.3
        // //|| cNoType.equals("GETNOTICENO")
        // //|| cNoType.equals("GETNO") 修改成5.3 的生成逻辑 modify on 2009-01-09
        // ) {
        // tReturn = tReturn + "9";
        // }
        String tStr = String.valueOf(tMaxNo);
        tStr = PubFun.LCh(tStr, "0", serialLen);
        // logger.debug("tStr:"+tStr+" serialLen:"+serialLen);
        // logger.debug("---------tStr:"+tStr);
        if (cNoType.equals("CONTNO") && tReturn.equals("0")) {
            tReturn = "88";
        }

        if (tReturn.equals("SN")) {
            if (cNoType.toUpperCase().equals("CUSTOMERNO") || cNoType.toUpperCase().equals("APPNTNO")
                    || cNoType.toUpperCase().equals("BANKCODE") || cNoType.toUpperCase().equals("LCPOSITION")
                    || cNoType.toUpperCase().equals("LCACCASCRIPTION") || cNoType.toUpperCase().equals("GRPPROPNO")
                    || cNoType.toUpperCase().equals("GRPPROPNO2")) {
                // tReturn = "9" + tStr.trim();
                tReturn = tStr.trim();
            }

            // add by renhl 修改为相当与个位加1 2008-10-14
            else if (cNoType.equals("COMMISIONSN")) {
                tReturn = tStr.trim();
            } else {
                tReturn = tStr.trim() + "0";
            }
        } else if (tReturn.equals("RECO")) {
            tReturn = tStr.trim();
        } else {
            tReturn = tReturn.trim() + tStr.trim();
        }
        // logger.debug("------tReturn:"+tReturn);
        return tReturn;
    }

    /**
     * 功能：产生指定长度的流水号，一个号码类型一个流水
     * 
     * @param cNoType
     *            String 流水号的类型
     * @param cNoLength
     *            int 流水号的长度
     * @return String 返回产生的流水号码
     */
    public String CreateMaxNo(String cNoType, int cNoLength) {
        if ((cNoType == null) || (cNoType.trim().length() <= 0) || (cNoLength <= 0)) {
            logger.error("NoType长度错误或NoLength错误");

            return null;
        }
        cNoType = cNoType.toUpperCase();
        // 对上面那种cNoLimit为SN的数据做一个校验，否则会导致数据干扰
        if (cNoType.equals("COMMISIONSN") || cNoType.equals("GRPNO") || cNoType.equals("CUSTOMERNO")
                || cNoType.equals("SUGDATAITEMCODE") || cNoType.equals("SUGITEMCODE") || cNoType.equals("SUGMODELCODE")
                || cNoType.equals("SUGCODE")) {

            logger.error("该类型流水号，请采用CreateMaxNo('" + cNoType + "','SN')方式生成");
            return null;
        }

        // tongmeng 2008-03-26 modify
        // 对于DOCID 和PAGEID 使用序列方式
        // tongmeng 2009-06-01 modify
        // MissionSerielNo 使用序列
        // JiangJian 2016-04-11 仅ORACLE数据库使用序列方式生成流水号
        if ((cNoType.equals("DOCID") || cNoType.equals("PAGEID") || cNoType.equals("MISSIONID")
                || cNoType.equals("MISSIONSERIELNO")) && "ORACLE".equalsIgnoreCase(SysConst.DBTYPE)) {
            try {
                String tSQL_SEQ = " select " + "SEQ_" + cNoType + ".nextval from dual ";
                logger.debug(tSQL_SEQ);
                ExeSQL tExeSQL = new ExeSQL();
                String tValue = "";
                SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
                sqlbv8.sql(tSQL_SEQ);
                sqlbv8.put("cNoType", cNoType);
                tValue = tExeSQL.getOneValue(sqlbv8);
                logger.debug("tValue" + tValue);
                if (cNoType.equals("MISSIONID")) {
                    tValue = PubFun.LCh(tValue, "0", cNoLength);
                }
                return tValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        // logger.debug("type:"+cNoType+" length:"+cNoLength);
        String tStr = getMaxNo(cNoType, "SN");
        tStr = PubFun.LCh(tStr, "0", cNoLength);
        String tReturn = tStr.trim();

        return tReturn;
    }

    private static int step = 5;

    private String getMaxNo(String cNoType, String cNoLimit, int server) {
        Connection conn = DBConnPool.getConnection();

        if (conn == null) {
            logger.error("CreateMaxNo : fail to get db connection");

            return null;
        }

        BigInteger tMaxNo = new BigInteger("0");

        try {
            // 开始事务
            conn.setAutoCommit(false);

            // String strSQL = "select MaxNo from LDMaxNo where notype='" +
            // cNoType + "' and nolimit='" + cNoLimit +
            // "' for update";
            // //如果数据库类型是ORACLE的话，需要添加nowait属性，以防止锁等待
            // if (SysConst.DBTYPE.compareTo("ORACLE") == 0)
            // {
            // strSQL = strSQL + " nowait";
            // }
            StringBuffer tSBql = new StringBuffer(256);
            tSBql.append("select MaxNo from LDMaxNo");
            tSBql.append((server == 0 ? "" : String.valueOf(server)));
            tSBql.append(" where notype='");
            tSBql.append(cNoType);
            tSBql.append("' and nolimit='");
            tSBql.append(cNoLimit);
            tSBql.append("' for update");
            // 如果数据库类型是ORACLE的话，需要添加nowait属性，以防止锁等待
            if (SysConst.DBTYPE.compareTo("ORACLE") == 0) {
                // tSBql.append(" nowait");
            }
            SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
            sqlbv9.sql(tSBql.toString());

            ExeSQL exeSQL = new ExeSQL(conn);
            String result = null;
            result = exeSQL.getOneValue(sqlbv9);

            if ((result == null) || exeSQL.mErrors.needDealError()) {
                logger.error("CreateMaxNo 资源忙，请稍后!");
                conn.rollback();
                conn.close();

                return null;
            }

            if (result.equals("")) {
                // 如果存在多服务方式，则不能出现没有初始值的情况
                if (server > 0) {
                    logger.error("CreateMaxNo 插入失败，请重试!");
                    return null;
                }
                // strSQL = "insert into ldmaxno(notype, nolimit, maxno)
                // values('" +
                // cNoType + "', '" + cNoLimit + "', 1)";
                tSBql = new StringBuffer(256);
                tSBql.append("insert into ldmaxno");
                tSBql.append((server == 0 ? "" : String.valueOf(server)));
                tSBql.append("(notype, nolimit, maxno) values('");
                tSBql.append(cNoType);
                tSBql.append("', '");
                tSBql.append(cNoLimit);
                tSBql.append("', 1)");
                SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
                sqlbv10.sql(tSBql.toString());
                exeSQL = new ExeSQL(conn);
                if (!exeSQL.execUpdateSQL(sqlbv10)) {
                    logger.error("CreateMaxNo 插入失败，请重试!");
                    conn.rollback();
                    conn.close();

                    return null;
                } else {
                    tMaxNo = new BigInteger("1");
                }
            } else {
                // strSQL = "update ldmaxno set maxno = maxno + 1 where notype =
                // '" + cNoType
                // + "' and nolimit = '" + cNoLimit + "'";
                tSBql = new StringBuffer(256);
                tSBql.append("update ldmaxno");
                tSBql.append((server == 0 ? "" : String.valueOf(server)));
                tSBql.append(" set maxno = maxno + ");
                tSBql.append((server == 0 ? "1" : String.valueOf(step)));
                tSBql.append(" where notype = '");
                tSBql.append(cNoType);
                tSBql.append("' and nolimit = '");
                tSBql.append(cNoLimit);
                tSBql.append("'");
                SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
                sqlbv11.sql(tSBql.toString());
                exeSQL = new ExeSQL(conn);
                if (!exeSQL.execUpdateSQL(sqlbv11)) {
                    logger.error("CreateMaxNo 更新失败，请重试!");
                    conn.rollback();
                    conn.close();

                    return null;
                } else {
                    tMaxNo = (new BigInteger(result))
                            .add(new BigInteger(String.valueOf((server == 0 ? "1" : String.valueOf(step)))));
                }
            }

            conn.commit();
            conn.close();
        } catch (Exception Ex) {
            try {
                conn.rollback();
                conn.close();

                return null;
            } catch (Exception e1) {
                e1.printStackTrace();

                return null;
            }
        }

        return tMaxNo.toString();
    }

    private String getMaxNo(String cNoType, String cNoLimit) {
        return getMaxNo(cNoType, cNoLimit, 0);
    }

    public static void main(String[] args) {
        // SysMaxNoNCL sysMaxNoZhongYing1 = new SysMaxNoNCL();
        // logger.debug(sysMaxNoZhongYing1.CreateMaxNo("EdorNo", "SN"));

        // String strLimit = PubFun.getNoLimit("86");
        //
        //
        // String newContNo = PubFun1.CreateMaxNo("CONTNO", strLimit);//
        // String tReturn = PubFun1.creatVerifyDigit(newContNo);
        // logger.debug("EDORACCEPTNO : " + newContNo);
        // logger.debug("EDORACCEPTNO : " + tReturn);
        // logger.debug("EDORACCEPTNO : " +
        // newContNo.substring(6,newContNo.length())+newContNo.substring(5,6));
        // VData nVData = new VData();
        // nVData.setSize(5);
        // nVData.setElementAt("BQ", 0);
        // String tSCANNO = PubFun1.CreateMaxNo("OTOF", "RECORD");
        // logger.debug("SCANNO:" + tSCANNO);
        // String tLimit = PubFun.getNoLimit("86");
        // String tPolNo = PubFun1.CreateMaxNo("ContNo", tLimit);
        // String newContNo = PubFun1.creatVerifyDigit(tPolNo);
        // String tLimit = PubFun.getNoLimit("86110000");
        // String tPaySerialNo = PubFun1.CreateMaxNo("PAYNOTICENO",
        // "8611002009");
        String tSerielNo = PubFun1.CreateMaxNo("MissionSlNo", 10);
        logger.debug("@@:" + tSerielNo);
        System.out.print(tSerielNo);
        // logger.debug("##:"+newContNo);
    }

}
