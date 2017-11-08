package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author pst
 *      保单交接清单打印 包含“补打保单交接清单”及普通保单的“重打保单交接清单”
        VIP保单的“VIP保单交接清单”应包括VIP保单的“补打保单交接清单”及VIP保单的“重打保单交接清单”
        但是补打保单交接清单，重打保单交接清单内容不区分保单是否是VIP保单
 * @version 1.0
 */

import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class LCPolBillF1PBL {
private static Logger logger = Logger.getLogger(LCPolBillF1PBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors=new CErrors();
    private VData mResult = new VData();
    
	private TransferData mTransferData = new TransferData();
    private String mOperate = "";
    //取得的时间
    private String mDay[]= new String[2];
    private String mTime[] = new String[2];
    private String SaleChnl="";
    private String StartContNo="";
    private String EndContNo="";
    private String BillType="";
    private String ManageCom="";
    private String ManageGrade=""; 
    private String tSumCont="";
    
    private String tStartDateSQL="";
    private String tEndDateSQL="";
    private String tTableMakeDateSQL="";
    private String tDateComPareSQL="";
    private SQLwithBindVariables tDateComPareSQLsqlbv = new SQLwithBindVariables();
    
    private String tSQLNoprint="";
	private String tGradeSQL="";  
	public  String VTS_NAME = ""; // 模板名称
    //业务处理相关变量
    /** 全局数据 */
    private GlobalInput mGlobalInput =new GlobalInput() ;
    public LCPolBillF1PBL() {
    	
    	
    	
    	
    	
    	
    }
    /**
     传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        if("".equals(cOperate)  || cOperate==null)
        {
            buildError("submitData", "不支持的操作字符串");
            return false;
        }
        //保单重新打印
        if("PRINT||RE".equals(cOperate))
        {
        	VTS_NAME="LCPolReBill.vts";
        }else
        {
        	VTS_NAME="LCPolBill.vts";
        }
        mOperate = cOperate;

        // 得到外部传入的数据，将数据备份到本类中
        if( !getInputData(cInputData) )
        {
            return false;
        }

        mResult.clear();

        // 准备所有要打印的数据
        if( !getPrintData() ) {
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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
        mGlobalInput=((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		if (mTransferData == null)
		{
            buildError("getInputData", "没有得到足够的信息！");
            return false;
		}
		//select to_date('2005-01-01 13:14:20','yyyy-MM-dd HH24:mi:ss') from dual;
		String tStartDay = (String) mTransferData.getValueByName("StartDay"); 
		String tEndDay = (String) mTransferData.getValueByName("EndDay"); 
		String tStartTime = (String) mTransferData.getValueByName("StartTime"); 
		String tEndTime = (String) mTransferData.getValueByName("EndTime"); 
        mDay[0]=tStartDay;
        mDay[1]=tEndDay;
        mTime[0]=tStartTime;
        mTime[1]=tEndTime;

		//日期比较，采用ORACEL的 日期比较方式，比较粒度达到秒
//		tStartDateSQL="to_date('"+tStartDay+" "+tStartTime+"','yyyy-MM-dd HH24:mi:ss')";
//		tEndDateSQL="to_date('"+tEndDay+" "+tEndTime+"','yyyy-MM-dd HH24:mi:ss')";
//		tTableMakeDateSQL="to_date(substr(to_char(l.makedate),0,10)||' '||l.maketime,'yyyy-MM-dd HH24:mi:ss')";
//		tDateComPareSQL=" and ("+tTableMakeDateSQL+" >= "+tStartDateSQL+" and " + tTableMakeDateSQL +"<=" + tEndDateSQL+")";
      
        //如果是当天则采用如下逻辑
        if(tStartDay.equals(tEndDay))
        {
    		tDateComPareSQL=" and (l.makedate = '"+"?tStartDay?"+"' and l.maketime >= '"+"?tStartTime?"+"' and l.maketime <= '"+"?tEndTime?"+"') ";     	
        }else
        {	//采取新的方式进行处理，这样数据才会走索引
    		tDateComPareSQL=" and ((l.makedate = '"+"?tStartDay?"+"' and l.maketime >= '"+"?tStartTime?"+"') or "
			+" (l.makedate = '"+"?tEndDay?"+"' and l.maketime <= '"+"?tEndTime?"+"') or "
			+" (l.makedate > '"+"?tStartDay?"+"' and l.makedate < '"+"?tEndDay?"+"') )";       	
        }
        this.tDateComPareSQLsqlbv.put("tStartDay", tStartDay);
        this.tDateComPareSQLsqlbv.put("tStartTime", tStartTime);
        this.tDateComPareSQLsqlbv.put("tEndTime", tEndTime);
        this.tDateComPareSQLsqlbv.put("tEndDay", tEndDay);

		
        SaleChnl=(String) mTransferData.getValueByName("SaleChnl"); 
        StartContNo=(String) mTransferData.getValueByName("StartContNo"); 
        EndContNo=(String) mTransferData.getValueByName("EndContNo"); 
       // BillType=(String) mTransferData.getValueByName("BillType"); 
        ManageCom=(String) mTransferData.getValueByName("ManageCom"); 
        if( mGlobalInput==null ) {
            buildError("getInputData", "没有得到足够的信息！");
            return false;
        }
        ManageGrade=(String) mTransferData.getValueByName("ManageGrade");
        
        if("4".equals(ManageGrade))
        {
        	tGradeSQL="where mg1 is not null ";
        }else if("6".equals(ManageGrade))
        {
        	tGradeSQL="where mg2 is not null ";        	
        }
        else if("8".equals(ManageGrade))
        {
        	tGradeSQL="where mg is not null ";        	
        }
        
        tSQLNoprint= " and exists "
		  + " (select 'x' "
						+" from lcpol d "
						 +" where d.contno = l.contno "
							+" and exists (select 'y' "
										+"	from lmriskapp "
										+" where riskcode = d.riskcode "
											+" and NotPrintPol = '0' "
											 +" and riskcode not in ('311603')))";

        return true;
    }


    public VData getResult()
    {
        return this.mResult;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError( );

        cError.moduleName = "LCPolBillF1PBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    private boolean getPrintData()
    {
        TextTag texttag=new TextTag();//新建一个TextTag的实例
        XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
        xmlexport.createDocument(VTS_NAME,"printer");//最好紧接着就初始化xml文档
        double dSumCount = 0;
        SSRS tSSRS = new SSRS();
        
        

        
        //销售渠道和类型
        String tSType="  and  exists (select 'X' from lccont r where "
    		+"   r.contno = l.contno   "
    		+   BqNameFun.getWherePart("r.salechnl",ReportPubFun.getParameterStr(SaleChnl,"?SaleChnl?"))
    		+	")"  ;
            if("".equals(SaleChnl) || SaleChnl==null)
            {
            	tSType=" ";
            }
        
        String tSQL = "";
        

        
        String  strVIPRiskCodeWhere = " and EXISTS (select 1 from lcpol where contno = l.contno and polno =mainpolno "
        	                          +"and isvipcont(prtno,payintv,payyears,prem)= '1' and  riskcode in (select riskcode 	from lmriskapp  where  NotPrintPol = '0'  and riskcode not in ('311603')) )";
        String  strRiskCodeWhere =   " and EXISTS (select 1 from lcpol where contno = l.contno and polno =mainpolno  "
        	                          +"and isvipcont(prtno,payintv,payyears,prem)= '0' and riskcode in (select riskcode 	from lmriskapp  where  NotPrintPol = '0'  and riskcode not in ('311603')) )";
        // 正常打印
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        if(mOperate.equals("PRINT"))
        {
            texttag.add("Title", "个人保单交接清单");
        	tSQL = " select mg1,mg2, mg, cno,cnt," + " (select a.name "  //普通保单
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and (b.contno = cno )  ) r1, "
			+ " (select  prtno " + "	from lccont c "
			+ " where (c.contno = cno) ) r2, "
			+ " (select d.appntname " + "	from lccont d "
			+ " where (d.contno = cno ) ) r3, "
			+ " (select e.insuredname " + "	from lccont e "
			+ " where (e.contno = cno ) ) r4, "
			+ " (select f.name " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and (b.contno = cno ) ) r5 , "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where (b.contno = cno )" 
			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,substr(l.managecom, 1, 6) mg2, l.contno cno, "
			+ " l.managecom mg,count(1) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate) md  "
			+ "	from lccontprint l "
			+ " where 1=1 and conttype='1' and not exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and  exists (select 'X' from lccont r where "
			+"   r.contno = l.contno and r.printcount='1' )"  //必须已经打印
			+ tSType
			+ strRiskCodeWhere
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")				
			+	" group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno)) "
			+" union "
        	+ " select mg1,mg2,mg, cno,cnt," + " (select max(a.name) "  //家庭单 以及 多主险的情况
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and ( b.familycontno = cno)  ) r1, "
			+ " (select  max(prtno) " + "	from lccont c "
			+ " where ( c.familycontno = cno) ) r2, "
			+ " (select max(d.appntname) " + "	from lccont d "
			+ " where (d.familycontno = cno) ) r3, "
			+ " (select max(e.insuredname) " + "	from lccont e "
			+ " where (e.familycontno = cno) ) r4, "
			+ " (select max(f.name) " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and ( b.familycontno = cno) ) r5, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where ( b.familycontno = cno)" 

			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2, l.contno cno, "
			+ " l.managecom mg,count(1) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate) "
			+ " md " 
			+ "	from lccontprint l "
			+ " where 1=1 and conttype='1' and  exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and  exists (select 'X' from lccont r where "
			+"   r.familycontno = l.contno and r.printcount='1' )"  //必须已经打印
			+ tSType
			+ strRiskCodeWhere
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")					
			+ " group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno))"
			+" union "  //重新打印的保单
			+ " select mg1,mg2, mg, cno,cnt," + "(select a.name "  
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and (b.contno = cno )  ) r1, "
			+ " (select  prtno " + "	from lccont c "
			+ " where (c.contno = cno) ) r2, "
			+ " (select d.appntname " + "	from lccont d "
			+ " where (d.contno = cno ) ) r3, "
			+ " (select e.insuredname " + "	from lccont e "
			+ " where (e.contno = cno ) ) r4, "
			+ " (select f.name " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and (b.contno = cno ) ) r5, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where (b.contno = cno )" 
			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2,l.contno cno, "
			+ " l.managecom mg,count(distinct contno) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate)  "
			+ " md " 
			+ "	from lccontprinttrace l "
			+ " where 1=1 and conttype='1' and RePrintFlag='1' and not exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and  exists (select 'X' from lccont r where "
			+"   r.contno = l.contno and r.printcount='1' )"  //不包括重新的打印的保单
			+ tSType
			+ strRiskCodeWhere
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")			
			+	" group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno) ) "
			+" union "
        	+ " select mg1,mg2, mg, cno,cnt," + " " + " (select max(a.name) "  //家庭单 以及 多主险的情况
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and ( b.familycontno = cno)  ) r1, "
			+ " (select  max(prtno) " + "	from lccont c "
			+ " where ( c.familycontno = cno) ) r2, "
			+ " (select max(d.appntname) " + "	from lccont d "
			+ " where (d.familycontno = cno) ) r3, "
			+ " (select max(e.insuredname) " + "	from lccont e "
			+ " where (e.familycontno = cno) ) r4, "
			+ " (select max(f.name) " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and ( b.familycontno = cno) ) r5, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where ( b.familycontno = cno)" 

			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2,l.contno cno, "
			+ " l.managecom mg,count(distinct contno) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate)  "
			+ " md " 
			+ "	from lccontprinttrace l "
			+ " where 1=1 and conttype='1' and RePrintFlag='1' and  exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and  exists (select 'X' from lccont r where "
			+"   r.familycontno = l.contno and r.printcount='1' )"  //不包括重新的打印的保单
			+ tSType
			+ strRiskCodeWhere
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")				
			+		" group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno) )";

        	
          tSQL=" select mg1, "
			+" mg2, "
			+" mg,  "
			+" cno, "
			+" sum(cnt),"
			+" max(r1), "
			+" max(r2), "
			+" max(r3), "
			+" max(r4), "
			+" max(r5), "
			+" max(r6), "
			+" max(r7), "
			+" '',"
			+" '' "
	      +" from ( "+tSQL+") "+tGradeSQL+" group by mg1, mg2, mg, cno order by mg1, mg2";
        	sqlbv = new SQLwithBindVariables();
        	sqlbv.put(this.tDateComPareSQLsqlbv);
        	sqlbv.sql(tSQL);
        	sqlbv.put("SaleChnl", SaleChnl);
        	sqlbv.put("ManageCom", this.ManageCom);
        	sqlbv.put("StartContNo", StartContNo);
        	sqlbv.put("EndContNo", EndContNo);
         }
        // 保单重打交接清单
        if(mOperate.equals("PRINT||RE"))
        {
            String  tSumSQL="select count(1) from lccontprint  l where 1=1  "
            	+  tSQLNoprint
    			+  tDateComPareSQL;
            SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
            sqlbv1.put(this.tDateComPareSQLsqlbv);
            sqlbv1.sql(tSumSQL);
            ExeSQL yExeSQL = new ExeSQL();
            tSumCont=yExeSQL.getOneValue(sqlbv1);
            if("".equals(tSumCont)||"0".equals(tSumCont))
            {
                buildError("getInputData", "计算总保单量失败！");
                return false;
            }
        	
        	tSQL = " select mg1,mg2,mg,"
			+ " prt,cnt, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where b.prtno = prt  and rownum=1" 

			+ "  ) r1, " 
			+ " (select d.appntname " + "	from lccont d "
			+ " where d.prtno = prt and rownum=1 ) r2, "
			+ "concat(concat(substr(fmd,1,10),' '),fmt) r3,"
			+ "concat(concat(substr(md,1,10),' '),mt) r4,"
			+ "rs r5," 
			+ "np r6," 
			+ " (select  contno " + "	from lccont c "
			+ " where c.prtno = prt and rownum=1 ) r7 "
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2, "
			+ " l.prtno prt, "
			+ " l.managecom mg,count(distinct contno) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.fmakedate)     " 
			+ " fmd,   " 
			+ " max(l.fmaketime)  "
			+ " fmt, " 
			+ " max(l.makedate)  "
			+ " md ," 
			+ " max(l.reason)  "
			+ " rs, " 
			+ " max(l.needpay)  "
			+ " np " 
			+ "	from lccontprinttrace l "
			+ " where 1=1 and RePrintFlag='1' and not exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and exists (select 'X' from lccont e where e.contno=l.contno and e.printcount='1') "
			+ tSType
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")				
			+		"  group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.prtno)) "
        	+ " union "
        	+ " select mg1,mg2,mg,prt,cnt," 
			+ " (select max(d.appntname) " + "	from lccont d "
			+ " where ( d.prtno = prt) ) r1, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where b.prtno = prt and rownum=1" 

			+ "  ) r2, " 
			+ "concat(concat(substr(fmd,1,10),' '),fmt) r3,"
			+ "concat(concat(substr(md,1,10),' '),mt) r4,"
			+ "rs r5," 
			+ "np r6," 
			+ " (select  familycontno " + "	from lccont c "
			+ " where c.prtno = prt and rownum=1 ) r7 "
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2 ,l.prtno prt, "
			+ " l.managecom mg,count(distinct contno) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.fmakedate)     " 
			+ " fmd,   " 
			+ " max(l.fmaketime)  "
			+ " fmt, " 
 
			+ " max(l.makedate)  "
			+ " md, " 
			+ " max(l.reason)  "
			+ " rs, " 
			+ " max(l.needpay)  "
			+ " np " 
			+ "	from lccontprinttrace l "
			+ " where 1=1 and RePrintFlag='1' and  exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and exists (select 'X' from lccont e where e.familycontno=l.contno and e.printcount='1') "
			+ tSType
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")			
			+		" group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.prtno) ) ";

        	tSQL=" select mg1, "
    			+" mg2, "
    			+" mg,  "
    			+" prt, "
    			+" sum(cnt),"
    			+" max(r1), "
    			+" max(r2), "
    			+" max(r3), "
    			+" max(r4), "
    			+" max(r5), "
    			+" max(r6), "
    			+" max(r7) "
    	      +" from ( "+tSQL+") "+tGradeSQL+"  group by mg1, mg2, mg, prt order by mg1, mg2";
        	sqlbv = new SQLwithBindVariables();
        	sqlbv.put(this.tDateComPareSQLsqlbv);
        	sqlbv.sql(tSQL);
        	sqlbv.put("SaleChnl", SaleChnl);
        	sqlbv.put("ManageCom", this.ManageCom);
        	sqlbv.put("StartContNo", StartContNo);
        	sqlbv.put("EndContNo", EndContNo);
        }
        // 保单补发交接清单
        if(mOperate.equals("PRINT||LR"))
        {
            texttag.add("Title", "补打保单交接清单");
        	tSQL = " select mg1,mg2,mg,cno,cnt," +  " (select a.name "
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and (b.contno = cno )  ) r1, "
			+ " (select  prtno " + "	from lccont c "
			+ " where (c.contno = cno) ) r2, "
			+ " (select d.appntname " + "	from lccont d "
			+ " where (d.contno = cno ) ) r3, "
			+ " (select e.insuredname " + "	from lccont e "
			+ " where (e.contno = cno ) ) r4, "
			+ " (select f.name " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and (b.contno = cno ) ) r5, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where (b.contno = cno )" 

			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2,l.contno cno, "
			+ " l.managecom mg,count(1) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate)  "
			+ " md " 
			+ "	from lccontprint l "
			+ " where 1=1 and conttype='1' and not exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and exists (select 'X' from lccont e where e.contno=l.contno and e.printcount='1') "
			+" and exists (select 'X'  from lpedoritem where l.contno=contno and edorstate='0' and edortype='LR')"
			+ tSType
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")			
			+		"  group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno)) "
			+" union "
        	+ " select mg1,mg2,mg,cno," + " cnt, " + " (select max(a.name) "
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and ( b.familycontno = cno)  ) r1, "
			+ " (select  max(prtno) " + "	from lccont c "
			+ " where ( c.familycontno = cno) ) r2, "
			+ " (select max(d.appntname) " + "	from lccont d "
			+ " where (d.familycontno = cno) ) r3, "
			+ " (select max(e.insuredname) " + "	from lccont e "
			+ " where (e.familycontno = cno) ) r4, "
			+ " (select max(f.name) " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and ( b.familycontno = cno) ) r5, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where ( b.familycontno = cno)" 

			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2,l.contno cno, "
			+ " l.managecom mg,count(1) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate)  "
			+ " md " 
			+ "	from lccontprint l "
			+ " where 1=1 and conttype='1' and  exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and exists (select 'X' from lccont e where e.familycontno=l.contno and e.printcount='1') "
			+" and exists (select 'X'  from lpedoritem where l.contno=contno and edorstate='0' and edortype='LR')"
			+ tSType
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")			
			+		" group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno)) ";//order by mg";;
            
        	tSQL=" select mg1, "
    			+" mg2, "
    			+" mg,  "
    			+" cno, "
    			+" sum(cnt),"
    			+" max(r1), "
    			+" max(r2), "
    			+" max(r3), "
    			+" max(r4), "
    			+" max(r5), "
    			+" max(r6), "
    			+" max(r7), "
    			+" '',"
    			+" '' "
    	      +" from ( "+tSQL+") "+tGradeSQL+"  group by mg1, mg2, mg, cno order by mg1, mg2";
        	sqlbv = new SQLwithBindVariables();
        	sqlbv.put(this.tDateComPareSQLsqlbv);
        	sqlbv.sql(tSQL);
        	sqlbv.put("SaleChnl", SaleChnl);
        	sqlbv.put("ManageCom", this.ManageCom);
        	sqlbv.put("StartContNo", StartContNo);
        	sqlbv.put("EndContNo", EndContNo);
        }
        // VIP保单打印交接清单
        if(mOperate.equals("PRINT||VIP"))
        {
            texttag.add("Title", "VIP保单交接清单");

        	
         	tSQL = " select mg1,mg2, mg, cno,cnt," + " (select a.name "  //普通保单
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and (b.contno = cno )  ) r1, "
			+ " (select  prtno " + "	from lccont c "
			+ " where (c.contno = cno) ) r2, "
			+ " (select d.appntname " + "	from lccont d "
			+ " where (d.contno = cno ) ) r3, "
			+ " (select e.insuredname " + "	from lccont e "
			+ " where (e.contno = cno ) ) r4, "
			+ " (select f.name " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and (b.contno = cno ) ) r5 , "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where (b.contno = cno )" 
			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,substr(l.managecom, 1, 6) mg2, l.contno cno, "
			+ " l.managecom mg,count(1) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate) md  "
			+ "	from lccontprint l "
			+ " where 1=1 and conttype='1' and not exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and  exists (select 'X' from lccont r where "
			+"   r.contno = l.contno and r.printcount='1' )"  //必须已经打印
			+ tSType
			+ strVIPRiskCodeWhere
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")				
			+	" group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno)) "
			+" union "
        	+ " select mg1,mg2,mg, cno,cnt," + " (select max(a.name) "  //家庭单 以及 多主险的情况
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and ( b.familycontno = cno)  ) r1, "
			+ " (select  max(prtno) " + "	from lccont c "
			+ " where ( c.familycontno = cno) ) r2, "
			+ " (select max(d.appntname) " + "	from lccont d "
			+ " where (d.familycontno = cno) ) r3, "
			+ " (select max(e.insuredname) " + "	from lccont e "
			+ " where (e.familycontno = cno) ) r4, "
			+ " (select max(f.name) " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and ( b.familycontno = cno) ) r5, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where ( b.familycontno = cno)" 

			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2, l.contno cno, "
			+ " l.managecom mg,count(1) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate) "
			+ " md " 
			+ "	from lccontprint l "
			+ " where 1=1 and conttype='1' and  exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and  exists (select 'X' from lccont r where "
			+"   r.familycontno = l.contno and r.printcount='1' )"  //必须已经打印
			+ tSType
			+ strVIPRiskCodeWhere
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")				
			+ " group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno))"
			+" union "  //重新打印的保单
			+ " select mg1,mg2, mg, cno,cnt," + "(select a.name "  
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and (b.contno = cno )  ) r1, "
			+ " (select  prtno " + "	from lccont c "
			+ " where (c.contno = cno) ) r2, "
			+ " (select d.appntname " + "	from lccont d "
			+ " where (d.contno = cno ) ) r3, "
			+ " (select e.insuredname " + "	from lccont e "
			+ " where (e.contno = cno ) ) r4, "
			+ " (select f.name " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and (b.contno = cno ) ) r5, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where (b.contno = cno )" 
			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2,l.contno cno, "
			+ " l.managecom mg,count(1) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate)  "
			+ " md " 
			+ "	from lccontprinttrace l "
			+ " where 1=1 and conttype='1' and RePrintFlag='1' and not exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and  exists (select 'X' from lccont r where "
			+"   r.contno = l.contno and r.printcount='1' )"  //不包括重新的打印的保单
			+ tSType
			+ strVIPRiskCodeWhere
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")					
			+	" group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno) ) "
			+" union "
        	+ " select mg1,mg2, mg, cno,cnt," + " " + " (select max(a.name) "  //家庭单 以及 多主险的情况
			+ "	from labranchgroup a, lccont b "
			+ " where a.agentgroup = b.agentgroup "
			+ "	and ( b.familycontno = cno)  ) r1, "
			+ " (select  max(prtno) " + "	from lccont c "
			+ " where ( c.familycontno = cno) ) r2, "
			+ " (select max(d.appntname) " + "	from lccont d "
			+ " where (d.familycontno = cno) ) r3, "
			+ " (select max(e.insuredname) " + "	from lccont e "
			+ " where (e.familycontno = cno) ) r4, "
			+ " (select max(f.name) " + "	from laagent f, lccont b "
			+ " where f.agentcode = b.agentcode "
			+ "	 and ( b.familycontno = cno) ) r5, "
			+ "(select max((select codename from ldcode "
			+ "	  where codetype = 'salechnl' "
			+ "		  and ldcode.code = b.salechnl)) " 
			+ "	from lccont b "
			+ " where ( b.familycontno = cno)" 

			+ " ) r6, " 
			+ "mt r7,"
			+ " '', " 
			+ "'' " 
			+ " from (select substr(l.managecom, 1, 4) mg1,	substr(l.managecom, 1, 6) mg2,l.contno cno, "
			+ " l.managecom mg,count(1) cnt," 
			+ " max(l.maketime) " 
			+ " mt,    "
			+ " max(l.makedate)  "
			+ " md " 
			+ "	from lccontprinttrace l "
			+ " where 1=1 and conttype='1' and RePrintFlag='1' and  exists (select 'X' from lccont r where r.familytype in ( '1','2')"
			+"  and r.familycontno is not null  and r.familycontno = l.contno)"
			+"  and  exists (select 'X' from lccont r where "
			+"   r.familycontno = l.contno and r.printcount='1' )"  //不包括重新的打印的保单
			+ tSType
			+ strVIPRiskCodeWhere
			+  BqNameFun.getWherePart("l.managecom", ReportPubFun.getParameterStr(this.ManageCom,"?ManageCom?"), "like")
			+  tDateComPareSQL
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(StartContNo,"?StartContNo?"), ">=")
			+  BqNameFun.getWherePart("l.ContNo",ReportPubFun.getParameterStr(EndContNo,"?EndContNo?"), "<=")					
			+		" group by rollup(substr(l.managecom, 1, 4),	substr(l.managecom, 1, 6),l.managecom,l.contno) )";

        	
          tSQL=" select mg1, "
			+" mg2, "
			+" mg,  "
			+" cno, "
			+" sum(cnt),"
			+" max(r1), "
			+" max(r2), "
			+" max(r3), "
			+" max(r4), "
			+" max(r5), "
			+" max(r6), "
			+" max(r7), "
			+" '',"
			+" '' "
	      +" from ( "+tSQL+") "+tGradeSQL+" group by mg1, mg2, mg, cno order by mg1, mg2";
        sqlbv = new SQLwithBindVariables();
      	sqlbv.put(this.tDateComPareSQLsqlbv);
      	sqlbv.sql(tSQL);
      	sqlbv.put("SaleChnl", SaleChnl);
      	sqlbv.put("ManageCom", this.ManageCom);
      	sqlbv.put("StartContNo", StartContNo);
      	sqlbv.put("EndContNo", EndContNo);
         }
   
        logger.debug("SQL:"+tSQL);
        ListTable tlistTable = new ListTable();
        ExeSQL tExeSQL = new ExeSQL();
        String strArr[] = null;
        tlistTable.setName("LCPol");
        tSSRS=tExeSQL.execSQL(sqlbv);
        //dSumCount = tSSRS.getMaxRow();

        int tEnd=0;
        String tLastContNo="";
        for (int i=1;i<=tSSRS.getMaxRow();i++)
        {
        	
        	String tCurConNo=tSSRS.GetText(i,4);
        	//8位机构
        	String tBranchMg=tSSRS.GetText(i,3);
        	//6位机构
        	String tSubMg=tSSRS.GetText(i,2);
        	//4位机构
        	String tSubBranchMg=tSSRS.GetText(i,1);
            if("".equals(tLastContNo)&&(!"".equals(tCurConNo)))
            {
            	//添加表头
            	if(mOperate.equals("PRINT||RE"))
            	{
                    strArr = new String[9];
        			strArr[0] = "保单号";
        			strArr[1] = "机构代码";
        			strArr[2] = "印刷号";
        			strArr[3] = "销售渠道";
        			strArr[4] = "投保人姓名";
        			strArr[5] = "第一次发送时间";
        			strArr[6] = "重新打印时间";
        			strArr[7] = "重打原因";
        			strArr[8] = "费用";
                    tlistTable.add(strArr);
            	}else
            	{
                   strArr = new String[11];
        			strArr[0] = "保单号";
        			strArr[1] = "机构代码";
        			strArr[2] = "代理人组别";
        			strArr[3] = "印刷号";
        			strArr[4] = "投保人姓名";
        			strArr[5] = "被保人姓名";
        			strArr[6] = "业务员";
        			strArr[7] = "销售渠道";
        			strArr[8] = "发送时间";
        			strArr[9] = "签收人";
        			strArr[10] = "签收日";
                    tlistTable.add(strArr);
            	} 
            }
            tLastContNo=tCurConNo;
            //四级机构汇总
            if(!"".equals(tBranchMg)&&"".equals(tCurConNo))
            {
            	if(mOperate.equals("PRINT||RE"))
            	{  
             		//保单号	机构代码	印刷号	销售渠道	投保人姓名	第一次发送时间	重新打印时间	重打原因

            		strArr = new String[9];
        			strArr[0] = "四级机构代码：";
        			strArr[1] = tBranchMg;
        			strArr[2] = "";
        			strArr[3] = "";
        			strArr[4] = "重打单量";
        			strArr[5] = "共" + String.valueOf(tSSRS.GetText(i,5)) + "件";
        			strArr[6] = "重打保单比率";
        			strArr[7] = String.valueOf(PubFun.round(Double.parseDouble(tSSRS.GetText(i,5))/Double.parseDouble(tSumCont)*100,2)+"%");
        			strArr[8] = "";
                    tlistTable.add(strArr);


            	}else
            	{
            		//保单号	机构代码	代理人组别	印刷号	投保人姓名	被保人姓名	
            		//业务员	销售渠道	发送时间	签收人	签收日

            		strArr = new String[11]; 
        			strArr[0] = "四级机构代码：";
        			strArr[1] = tBranchMg;
        			strArr[2] = "";
        			strArr[3] = "总单量";
        			strArr[4] = "共" + String.valueOf(tSSRS.GetText(i,5)) + "件";
        			strArr[5] = "";
        			strArr[6] = "四级机构签发人";
        			strArr[7] = "";
        			strArr[8] = "";
        			strArr[9] = "签收日期";
        			strArr[10] = "";
                    tlistTable.add(strArr);
            	}
    			continue;
            }

            //三级机构汇总
            if(!"".equals(tSubMg)&&"".equals(tCurConNo))
            {
				if (mOperate.equals("PRINT||RE")) {
					strArr = new String[9];
					strArr[0] = "三级机构代码：";
					strArr[1] = tSubMg;
					strArr[2] = "";
					strArr[3] = "";
					strArr[4] = "重打单量";
					strArr[5] = "共" + String.valueOf(tSSRS.GetText(i,5)) + "件";
	      			strArr[6] = "重打保单比率";
        			strArr[7] = String.valueOf(PubFun.round(Double.parseDouble(tSSRS.GetText(i,5))/Double.parseDouble(tSumCont)*100,2)+"%");
					strArr[8] = "";
        			tlistTable.add(strArr);
				} else {
					strArr = new String[11];
					strArr[0] = "三级机构代码：";
					strArr[1] = tSubMg;
					strArr[2] = "";
					strArr[3] = "总单量";
					strArr[4] = "共" + String.valueOf(tSSRS.GetText(i,5)) + "件";
					strArr[5] = "";
					strArr[6] = "三级机构签发人";
					strArr[7] = "";
					strArr[8] = "";
					strArr[9] = "签收日期";
					strArr[10] = "";
					tlistTable.add(strArr);
				}
     			continue;
            }
            //二级机构汇总
            if(!"".equals(tSubBranchMg)&&"".equals(tCurConNo))
            {
				if (mOperate.equals("PRINT||RE")) {
					strArr = new String[9];
					strArr[0] = "二级机构代码：";
					strArr[1] = tSubBranchMg;
					strArr[2] = "";
					strArr[3] = "";
					strArr[4] = "重打单量";
					strArr[5] = "共" + String.valueOf(tSSRS.GetText(i,5)) + "件";
	      			strArr[6] = "重打保单比率";
        			strArr[7] = String.valueOf(PubFun.round(Double.parseDouble(tSSRS.GetText(i,5))/Double.parseDouble(tSumCont)*100,2)+"%");
					strArr[8] = "";
        			tlistTable.add(strArr);
				} else {
					strArr = new String[11];
					strArr[0] = "二级机构代码：";
					strArr[1] = tSubBranchMg;
					strArr[2] = "";
					strArr[3] = "总单量";
					strArr[4] = "共" + String.valueOf(tSSRS.GetText(i,5)) + "件";
					strArr[5] = "";
					strArr[6] = "二级机构签发人";
					strArr[7] = "";
					strArr[8] = "";
					strArr[9] = "签收日期";
					strArr[10] = "";
					tlistTable.add(strArr);
				}
     			continue;
            }
            if(!"".equals(tCurConNo))
            {
            	if(mOperate.equals("PRINT||RE"))
            	{
                    strArr = new String[9];
            	}else
            	{
                   strArr = new String[11];
            	} 

            	//添加数据
            	if(mOperate.equals("PRINT||RE"))
            	{
                    strArr = new String[9];
        			strArr[0] =tSSRS.GetText(i,12);// "保单号";
        			strArr[1] =tSSRS.GetText(i,3);// "机构代码";
        			strArr[2] =tSSRS.GetText(i,4);// "印刷号";
        			strArr[3] =tSSRS.GetText(i,6);// "销售渠道";
        			strArr[4] =tSSRS.GetText(i,7);// "投保人姓名";
        			strArr[5] =tSSRS.GetText(i,8);// "第一次发送时间";
        			strArr[6] =tSSRS.GetText(i,9);// "重新打印时间";
        			strArr[7] =tSSRS.GetText(i,10);// "重打原因";
        			strArr[8] =tSSRS.GetText(i,11);// "费用";
                    tlistTable.add(strArr);
            	}else
            	{
                   strArr = new String[11];
        			strArr[0] =tSSRS.GetText(i,4);// "保单号";
        			strArr[1] =tSSRS.GetText(i,3);// "机构代码";
        			strArr[2] =tSSRS.GetText(i,6);// "代理人组别";
        			strArr[3] =tSSRS.GetText(i,7);// "印刷号";
        			strArr[4] =tSSRS.GetText(i,8);// "投保人姓名";
        			strArr[5] =tSSRS.GetText(i,9);// "被保人姓名";
        			strArr[6] =tSSRS.GetText(i,10);// "业务员";
        			strArr[7] =tSSRS.GetText(i,11);// "销售渠道";
        			strArr[8] =tSSRS.GetText(i,12);// "发送时间";
        			strArr[9] =tSSRS.GetText(i,13);// "签收人";
        			strArr[10] ="";// "签收日";
                    tlistTable.add(strArr);
            	} 
            
    	        tEnd++;
            }	
        }


   		xmlexport.addDisplayControl("displayBillREAdd");
    	if(mOperate.equals("PRINT||RE"))
    	{ 
	 
    		texttag.add("ManageSubCom", "机构代码："+ManageCom+"          重打单量"+"共" + String.valueOf(tEnd) + "件            本时间段公司所有保单打印量为："+tSumCont+"  重打保单比率"+String.valueOf(PubFun.round(tEnd/Double.parseDouble(tSumCont)*100,2)+"%"));      		
    	}else
    	{
    		texttag.add("ManageSubCom", "机构代码："+ManageCom+"          总单量"+"共" 
    				+ String.valueOf(tEnd) + "件" +"              机构签发人：                 "+"签收日期:           ");
    	}

        texttag.add("StartDate",mDay[0]+" " + mTime[0]);
        texttag.add("EndDate",mDay[1]+" "+mTime[1]);
        texttag.add("ManageCom",ManageCom);
        texttag.add("Operator",mGlobalInput.Operator);
        

        if (texttag.size()>0)
            xmlexport.addTextTag(texttag);
    	if(mOperate.equals("PRINT||RE"))
    	{
            strArr = new String[9];
    	}else
    	{
           strArr = new String[11];
    	} 
    	
        xmlexport.addListTable(tlistTable, strArr);
        //xmlexport.outputDocumentToFile("C://XML","Bill");//输出xml文档到文件
        mResult.addElement(xmlexport);
        return true;

    }
    public static void main(String[] args) {
        LCPolBillF1PBL LCContBillF1PBL1 = new LCPolBillF1PBL();
    }

//  public String replace(
}
