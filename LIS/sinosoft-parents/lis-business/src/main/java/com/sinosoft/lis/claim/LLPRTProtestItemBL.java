package com.sinosoft.lis.claim;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：理赔决定通知书－拒付[保项层面]--PCT006,LpjdtzsJfBxcmC000130.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author zhaoy，2005.08.05
 * @version 1.0
 */
public class LLPRTProtestItemBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTProtestItemBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();

    private String mPrtCode  = "PCT006"	;	//打印编码
    private String mClmNo    = "";      	//赔案号
    private String mCusNo    = "";      	//客户号
		private String mPrtSeq = "" ;       //流水号

    public LLPRTProtestItemBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData,cOperate))
        {
            return false;
        }

//        if (!dealData())
//        {
//            return false;
//        }
        if(!getPrintData())
        	return false;
        return true;
    }


    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {
        this.mInputData = cInputData;

        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        //this.mClmNo = (String) mTransferData.getValueByName("ClmNo");    //赔案号
        //this.mCusNo = (String) mTransferData.getValueByName("CustNo");   //出险人编号
        this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
        return true;
    }


    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {

        String mClmNoSQL = "select OtherNo from LOPRTManager where PrtSeq='"+"?PrtSeq?"+"'" ;
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(mClmNoSQL);
        sqlbv.put("PrtSeq", mPrtSeq);
        ExeSQL clmExeSQL = new ExeSQL() ;
        mClmNo = clmExeSQL.getOneValue(sqlbv);
        String mCusNoSQL = "select CustomerNo from LLcase where CaseNo='"+"?clmno?"+"'";
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        sqlbv1.sql(mCusNoSQL);
        sqlbv1.put("clmno", mClmNo);
        ExeSQL cusExeSQL = new ExeSQL() ;
        SSRS cusNoSSRS = new SSRS();
        cusNoSSRS =cusExeSQL.execSQL(sqlbv1);
        if(cusNoSSRS.MaxRow==0)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTProtestClaimBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "查询出险人客户号失败!";
            mErrors.addOneError(tError);
            return false;
        }

//        for(int i=1;i<=cusNoSSRS.getMaxRow();i++){}
        mCusNo = cusNoSSRS.GetText(1,1) ;
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 定义打印
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        XmlExportNew tXmlExport = new XmlExportNew(); //新建一个XmlExport的实例
//        tXmlExport.createDocument("LpjdtzsJfBxcmC000130.vts", "");
        tXmlExport.createDocument("理赔决定通知书－拒付");
        
        String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                         StrTool.getDay() + "日";
        //赔案号
        texttag.add("IndemnityNo", mClmNo);
        //出险人
        String tSql = "select a.name from ldperson a where "
                      + "a.customerno = '" + "?cusno?" + "'";
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(tSql);
        sqlbv2.put("cusno", mCusNo);
        ExeSQL tExeSQL = new ExeSQL();
        String strCusName = tExeSQL.getOneValue(sqlbv2);
        texttag.add("InsuredName", strCusName);

        //理赔类型
        LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
        String strAppClaimReason = tLLPRTPubFunBL.getSLLAppClaimReason(mClmNo,
                mCusNo);
        texttag.add("IndemnityType", strAppClaimReason);

        //申请人、收信人信息
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
        if(tLLRegisterSchema==null)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTProtestClaimBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "查询申请人信息失败!";
            mErrors.addOneError(tError);
            return false;
        }
        String rgtname = tLLRegisterSchema.getRgtantName();//申请人姓名
        String RgtantSex=tLLRegisterSchema.getRgtantSex();//申请人性别
        String zipcode = "邮编: " + tLLRegisterSchema.getPostCode();
        String address = tLLRegisterSchema.getRgtantAddress();
        String mngCom = tLLRegisterSchema.getMngCom();
        String AppntName="";
        if(RgtantSex==null)
        {
            AppntName = rgtname;
        }
        else
        {
            if(RgtantSex.equals("男")||RgtantSex.equals("0"))
            {
                AppntName = rgtname + "先生";
            }
            else if (RgtantSex.equals("女") || RgtantSex.equals("1"))
            {
                AppntName = rgtname + "女士";
            }
            else
            {
                AppntName = rgtname;
            }
        }
        texttag.add("LCCont.AppntName", AppntName);  //申请人、收信人信息


        //理赔决定
        String strcontno = ""; //合同号
        String[] DSTitle = new String[3];
        DSTitle[0] = "";
        DSTitle[1] = "";
        DSTitle[2] = "";
        ListTable tDSListTable = new ListTable();
        tDSListTable.setName("LPJD");
//        LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();

        SSRS sSSRS = new SSRS();
        ExeSQL sExeSQL = new ExeSQL();
        String SQL = "select ContNo from llclaimdetail where clmno='" +
                     "?clmno?" + "' group by ContNo";
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(SQL);
        sqlbv3.put("clmno", mClmNo);
        sSSRS = sExeSQL.execSQL(sqlbv3);

        if (sSSRS.getMaxRow() != 0)
        {
            for (int index = 1; index <= sSSRS.getMaxRow(); index++)
            {
                //第一层循环:保单层（合同号）
                String[] strCont = new String[3];
                strcontno = sSSRS.GetText(index, 1);
                strCont[0] = "合同号："+strcontno;//保单号
                strCont[1]="";
                strCont[2]="";
                tDSListTable.add(strCont);

                //添加标题（保险责任、拒赔原因、拒赔依据）
                String[] strContTitle = new String[3];
                strContTitle[0] = "保险责任";
                strContTitle[1] = "拒赔原因";
                strContTitle[2] = "拒赔依据";
                tDSListTable.add(strContTitle);

                LLPRTPubFunBL ttLLPRTPubFunBL = new LLPRTPubFunBL();
                //调用公共文件中getLLClaimDetail方法，参数：赔案号，保单号
                LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
                tLLClaimDetailSet = ttLLPRTPubFunBL.getLLClaimDetail(mClmNo,
                        strcontno, "", "", "", "1");
                for (int i = 1; i <= tLLClaimDetailSet.size(); i++)
                {
                    String[] strDS = new String[3];
                    //第二层循环：责任层
                    String strGetDutyCode = tLLClaimDetailSet.get(i).
                                            getGetDutyCode();
                    //从责任给付表中取得给付名称
                    tSql = "select a.GetDutyName from LMDutyGet a where "
                           + "a.GetDutyCode = '" + "?GetDutyCode?" + "'";
                    //ExeSQL tExeSQL = new ExeSQL();
                    SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
                    sqlbv4.sql(tSql);
                    sqlbv4.put("GetDutyCode", strGetDutyCode);
                    String strGetDutyName = tExeSQL.getOneValue(sqlbv4);
//                    strDS[0] = i+". "+strGetDutyName; //保项描述
                    strDS[0] = strGetDutyName; //保项描述
                    ////拒付原因
                    String strcode = tLLClaimDetailSet.get(i).getGiveReason();
                    tSql = "select a.codename from ldcode a where "
                           + "a.codetype = 'llprotestreason' and a.code='"
                           + "?code?" + "'";
                    SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
                    sqlbv5.sql(tSql);
                    sqlbv5.put("code", strcode);
                    strDS[1] = tExeSQL.getOneValue(sqlbv5);

                    strDS[2] = tLLClaimDetailSet.get(i).getGiveReasonDesc(); //拒付依据
                    tDSListTable.add(strDS);
                }
                //加入空行
            }
        }


//    //通知日期
        texttag.add("SysDate", SysDate);
//    //邮编
//        texttag.add("ZipCode", zipcode); //收信人邮编
//    //地址
//        texttag.add("Address", address); //收信人地址
//    //姓名
//        texttag.add("Name", rgtname);
//    //公司名称
//        String tSql = "select a.name from ldcom a where "
//                      + "a.comcode = '" + mngCom + "'";
//        String comname = tExeSQL.getOneValue(tSql);
//        texttag.add("company", comname);
//    //部门
//    //电话

        /**
         * No1.0 加入单个字段的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (texttag.size() > 0)
        {
            tXmlExport.addTextTag(texttag);
        }
        /*********************************************************************
         * No1.0 加入多行数据的打印变量*******************************************
         ********************************************************************/
        tXmlExport.addListTable(tDSListTable, DSTitle); //保存理赔决定信息

//        tXmlExport.outputDocumentToFile("d:\\", "testprt"); //输出xml文档到文件
        mResult.clear();
        mResult.addElement(tXmlExport);
        mResult.add(mTransferData);
        logger.debug("xmlexport=" + tXmlExport);
        
        String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+"?clmno?"+"' and code='PCT006'";
        logger.debug("更新打印管理表的sql:"+updateStateSql);
        SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
        sqlbv6.sql(updateStateSql);
        sqlbv6.put("clmno", mClmNo);
        mMMap.put(sqlbv6, "UPDATE");
        mResult.add(mMMap);

        return true;
    }
    public CErrors getErrors()
    {
        return mErrors;
    }
		//得到结果集
    public VData getResult()
    {
        return mResult;
    }

	private boolean getPrintData()
	{

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String mSQL = "";


		//赔案号
		mSQL = "select OtherNo,managecom from LOPRTManager where PrtSeq='" + "?PrtSeq?"
				+ "'";
		logger.debug("根据打印管理表的流水号" + mPrtSeq + "查询赔案号的sql=" + mSQL);
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
	    sqlbv7.sql(mSQL);
	    sqlbv7.put("PrtSeq", mPrtSeq);
		SSRS pSsrs = new SSRS();
		pSsrs=tExeSQL.execSQL(sqlbv7);
		String mComcode="";
		if(pSsrs.MaxRow>0)
		{
			mClmNo = pSsrs.GetText(1, 1);
			mComcode = pSsrs.GetText(1, 2);
		}
		if(mComcode.length()>4)
			mComcode=mComcode.substring(0,4);

		// 理赔立案信息 
		mSQL = "select rgtantname,postcode,rgtantaddress,accidentdate from llregister  where rgtno='" + "?clmno?" + "'";
		logger.debug("根据赔案号" + mPrtSeq + "查询立案信息的sql=" + mSQL);
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
	    sqlbv8.sql(mSQL);
	    sqlbv8.put("clmno", mClmNo);
		tSSRS = tExeSQL.execSQL(sqlbv8);
		String tRgtantName=tSSRS.GetText(1, 1);   //申请人姓名
		String tRgtantAddress=tSSRS.GetText(1, 3);// 申请人地址
		String tPostCode=tSSRS.GetText(1, 2);     // 申请人邮编


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义打印
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("LpjdtzsJfBxcmC000130.vts", "");
		 tXmlExport.createDocument("理赔决定通知书－拒付");
		//----------------------------------------------------------------------
		tTextTag.add("RptorName", tRgtantName);// 赔案号
		tTextTag.add("RptorZip", tPostCode);// 事件号
		tTextTag.add("RptorAddress", tRgtantAddress);// 通讯地址


		String tComSql="select name from ldcom where trim(comcode)='"+"?comcode?"+"'";
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
	    sqlbv15.sql(tComSql);
	    sqlbv15.put("comcode", mComcode);
		String tComName=tExeSQL.getOneValue(sqlbv15);
		if(tComName.indexOf("MSRS保险股份公司")!=-1)
		{
			tComName=tComName.substring(tComName.indexOf("MSRS保险股份公司"));
		}
		tTextTag.add("MngCom", tComName);// 分公司机构名称
		tTextTag.add("Year", StrTool.getYear());
		tTextTag.add("Month", StrTool.getMonth());
		tTextTag.add("Day", StrTool.getDay());

		//拒付保单
		String nSql ="select distinct contno from llclaimdetail where clmno='"+"?clmno?"+"'  and givetype='1' ";
		String nContNo="";//拒付保单
		double nTotalMoney=0.00;//总退还金额
		String nConclusion=""; //退还结论
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
	    sqlbv9.sql(nSql);
	    sqlbv9.put("clmno", mClmNo);
		SSRS nSSRS = tExeSQL.execSQL(sqlbv9);
		for (int i=1;i<=nSSRS.MaxRow;i++)
		{
//			nTotalMoney=nTotalMoney+Double.parseDouble(nSSRS.GetText(i, 2));
			if(i==1)
				nContNo=nSSRS.GetText(i, 1);
			else
				nContNo=nContNo+","+nSSRS.GetText(i, 1);
		}
		//退还金额
		String nTotalMoneySql ="select (case when sum(pay) is null then 0 else sum(pay) end) from llbalance where clmno='"+"?clmno?"+"' and feefinatype='TF'  ";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
	    sqlbv10.sql(nTotalMoneySql);
	    sqlbv10.put("clmno", mClmNo);
		String nTotalM=tExeSQL.getOneValue(sqlbv10);
		if(nTotalM!=null)
			
		{
			nTotalMoney= Double.parseDouble(nTotalM);
			if(nTotalMoney<=0)
			{
				nConclusion="不予退还保险费";
			}
			else
			{
				nConclusion="退还保险费"+new DecimalFormat("0.00").format(nTotalMoney)+"元";
			}
		}


		String mNoPassReason="";//拒付原因
		String mNoPassDesc="";//拒付依据
		//拒付结论以及依据
		String uSql="select (select codename from ldcode where codetype='llprotestreason' and code=a.auditnopassreason),auditnopassdesc "
				   +" from llclaimuwmain a where clmno='"+"?clmno?"+"' and auditconclusion='1'";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
	    sqlbv11.sql(uSql);
	    sqlbv11.put("clmno", mClmNo);
		SSRS uSSRS = tExeSQL.execSQL(sqlbv11);
		if(uSSRS.MaxRow>0)
		{
			mNoPassReason=uSSRS.GetText(1, 1);
			mNoPassDesc=uSSRS.GetText(1, 2);
		}
		else
		{
			String rowStr = "";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				rowStr = " and rownum='1'";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				rowStr = " limit 1";
			}
			
			uSql="select (select codename from ldcode where codetype='llprotestreason' and code=a.givereason),givereasondesc from llclaimdetail  a "
				+" where clmno='"+"?clmno?"+"' and givetype='1' and contno=polno "+rowStr;
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		    sqlbv12.sql(uSql);
		    sqlbv12.put("clmno", mClmNo);
			uSSRS= new SSRS();
			uSSRS= tExeSQL.execSQL(sqlbv12);
			if(uSSRS.MaxRow>0)
			{
				mNoPassReason=uSSRS.GetText(1, 1);
				mNoPassDesc=uSSRS.GetText(1, 2);
			}
			else
			{
				uSql="select (select codename from ldcode where codetype='llprotestreason' and code=a.givereason),givereasondesc from llclaimdetail a "
					+" where clmno='"+"?clmno?"+"' and givetype='1' "+rowStr;
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			    sqlbv13.sql(uSql);
			    sqlbv13.put("clmno", mClmNo);
				uSSRS= new SSRS();
				uSSRS= tExeSQL.execSQL(sqlbv13);
				if(uSSRS.MaxRow>0)
				{
					mNoPassReason=uSSRS.GetText(1, 1);
					mNoPassDesc=uSSRS.GetText(1, 2);
				}
			}
		}
		tTextTag.add("ContNo",nContNo );
		tTextTag.add("Conclusion",nConclusion );
		tTextTag.add("NoPassReason", mNoPassReason);
		tTextTag.add("NoPassDesc", mNoPassDesc);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入单个字段的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

        String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+"?clmno?"+"' and code='PCT006'";
        logger.debug("更新打印管理表的sql:"+updateStateSql);
        SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
	    sqlbv14.sql(updateStateSql);
	    sqlbv14.put("clmno", mClmNo);
        mMMap.put(sqlbv14, "UPDATE");


		return true;
	
	}
		//主函数
    public static void main(String[] args)
    {


//        int tYear = PubFun.calInterval("2005-05-12","2006-05-14", "Y");
//
//        String tSNewDate = PubFun.calDate("2004-02-03",tYear,"Y","2004-02-03");
//        logger.debug(tSNewDate);
//        int tNewYear = PubFun.calInterval("1990-01-01",tSNewDate, "Y");
//        logger.debug(tNewYear);
//
//
//
//        String tClaimFeeCode = "123456789ABC";
//        tClaimFeeCode = tClaimFeeCode.substring(0,10);
//        logger.debug(tClaimFeeCode);
//        String tDutyFeeCode = "109".substring(2,3);//co
//        logger.debug(tDutyFeeCode);


        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";


        String tAccNo = "80000001336";      /*事件号*/
        String tClmNo = "90000001392";      /*赔案号*/
        String tAccDate = "2005-08-03";     /*意外事故时间*/
		String tCusno = "0000535120";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("AccNo",tAccNo);
        tTransferData.setNameAndValue("ClmNo",tClmNo);
		tTransferData.setNameAndValue("CustNo",tCusno);
       // tTransferData.setNameAndValue("AccDate",tAccDate);
        //tTransferData.setNameAndValue("ContType","1");   //总单类型,1-个人总投保单,2-集体总单
        //tTransferData.setNameAndValue("ClmState","20");  //赔案状态，20立案，30审核

        //VData tVData = new VData();
        //tVData.addElement(tG);
        //tVData.addElementtTransferData);

        //LLPRTProtestClaimBL tLLPRTProtestClaimBL = new LLPRTProtestClaimBL();
		//if (tLLPRTProtestClaimBL.submitData(tVData,"") == false)
		//{
	//		logger.debug("-------false-------");
	//	}
      //  int n = tLLPRTProtestClaimBL.mErrors.getErrorCount();
        //for (int i = 0; i < n; i++)
        //{
         //   String Content = "";
          //  Content = Content + "原因是: " + tLLPRTProtestClaimBL.mErrors.getError(i).errorMessage;
           // logger.debug(Content);
        //}


    }


}
