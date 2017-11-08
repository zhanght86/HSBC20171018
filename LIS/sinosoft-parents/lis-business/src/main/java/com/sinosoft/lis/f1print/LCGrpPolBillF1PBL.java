package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author zy
 * @version 1.0
 */

import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class LCGrpPolBillF1PBL {
private static Logger logger = Logger.getLogger(LCGrpPolBillF1PBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors=new CErrors();
    private VData mResult = new VData();
    private LCContSchema mLCContSchema = new LCContSchema();
    
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
    private String tStartDateSQL="";
    private String tEndDateSQL="";
    private String tTableMakeDateSQL="";
    private String tDateComPareSQL="";
    
	public  String VTS_NAME = ""; // 模板名称
	
	
	private String tGradeSQL="";
    //业务处理相关变量
    /** 全局数据 */
    private GlobalInput mGlobalInput =new GlobalInput() ;
    public LCGrpPolBillF1PBL() {
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

        VTS_NAME="LCGrpPolBill.vts";
        
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
		String tStartDay = (String) mTransferData.getValueByName("StartDay"); 
		String tEndDay = (String) mTransferData.getValueByName("EndDay"); 
		String tStartTime = (String) mTransferData.getValueByName("StartTime"); 
		String tEndTime = (String) mTransferData.getValueByName("EndTime"); 
        mDay[0]=tStartDay;
        mDay[1]=tEndDay;
        mTime[0]=tStartTime;
        mTime[1]=tEndTime;
//		tStartDateSQL="to_date('"+tStartDay+" "+tStartTime+"','yyyy-MM-dd HH24:mi:ss')";
//		tEndDateSQL="to_date('"+tEndDay+" "+tEndTime+"','yyyy-MM-dd HH24:mi:ss')";
//		tTableMakeDateSQL="to_date(substr(to_char(l.makedate),0,10)||' '||l.maketime,'yyyy-MM-dd HH24:mi:ss')";
//
//		tDateComPareSQL=" and ("+tTableMakeDateSQL+" >= "+tStartDateSQL+" and " + tTableMakeDateSQL +"<=" + tEndDateSQL+")";

		//采取新的方式进行处理，这样数据才会走索引
        //如果是当天则采用如下逻辑
        if(tStartDay.equals(tEndDay))
        {
        	tDateComPareSQL=" and (l.makedate = '"+tStartDay+"' and l.maketime >= '"+tStartTime+"' and l.maketime <= '"+tEndTime+"') ";   	
        }else
        {	//采取新的方式进行处理，这样数据才会走索引
    		tDateComPareSQL=" and ((l.makedate = '"+tStartDay+"' and l.maketime >= '"+tStartTime+"') or "
			+" (l.makedate = '"+tEndDay+"' and l.maketime <= '"+tEndTime+"') or "
			+" (l.makedate > '"+tStartDay+"' and l.makedate < '"+tEndDay+"') )";       	
        }
		
		
        SaleChnl=(String) mTransferData.getValueByName("SaleChnl"); 
        StartContNo=(String) mTransferData.getValueByName("StartContNo"); 
        EndContNo=(String) mTransferData.getValueByName("EndContNo"); 
        //BillType=(String) mTransferData.getValueByName("BillType"); 
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
        return true;
    }


    public VData getResult()
    {
        return this.mResult;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError( );

        cError.moduleName = "LCGrpPolBillF1PBL";
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
         
        String tSType="  and  exists (select 'X' from lcgrpcont r where "
		+"   r.grpcontno = l.contno   "
		+   BqNameFun.getWherePart("r.salechnl",SaleChnl)
		+	")"  ;
        if("".equals(SaleChnl) || SaleChnl==null)
        {
        	tSType=" ";
        }
        String tSQL = "";
        // 正常打印
        if(mOperate.equals("PRINT"))
        {
        	tSQL = 	" select mg1,"
				+" mg2,"
				+" mg, "
				+" cno,"
                +"  cnt,"
				+" (select a.name "
				+"		from labranchgroup a, lcgrpcont b "
				+"	 where a.agentgroup = b.agentgroup "
				+"		 and (b.grpcontno = cno)),       "
				+" (select d.grpname from lcgrpcont d where (d.grpcontno = cno)),"
				+" (select count(*) from lcinsured e where (e.grpcontno = cno)), "
				+" (select f.name "
				+"		from laagent f, lcgrpcont b "
				+"	 where f.agentcode = b.agentcode "
				+"		 and (b.grpcontno = cno)), "
				+" md, "
				+" '' "
	           +"	from (select substr(managecom, 1, 4) mg1,"
								+" substr(managecom, 1, 6) mg2,"
								+" l.managecom mg, "
								+" l.contno cno, "
								+" max(l.makedate) md, "
                +" count(1) cnt "
					+"	from lccontprint l "
					+"  where 1 = 1  and conttype='2'    "
					+  BqNameFun.getWherePart("l.managecom", this.ManageCom, "like")
                    +  tDateComPareSQL
        			+  BqNameFun.getWherePart("l.ContNo",StartContNo, ">=")
        			+  BqNameFun.getWherePart("l.ContNo",EndContNo, "<=")	
					+" group by rollup(substr(l.managecom, 1, 4),"
													+" substr(l.managecom, 1, 6),"
													+" l.managecom,"
													+" l.contno)"				
					+")"
					+tGradeSQL;
         }
        // 保单补发交接清单
        if(mOperate.equals("PRINT||LR"))
        {
        	tSQL=" select mg1,"
			+" mg2,"
			+" mg, "
			+" cno,"
            +"  cnt,"
			+" (select a.name "
			+"		from labranchgroup a, lcgrpcont b "
			+"	 where a.agentgroup = b.agentgroup "
			+"		 and (b.grpcontno = cno)),       "
			+" (select d.grpname from lcgrpcont d where (d.grpcontno = cno)),"
			+" (select count(*) from lcinsured e where (e.grpcontno = cno)), "
			+" (select f.name "
			+"		from laagent f, lcgrpcont b "
			+"	 where f.agentcode = b.agentcode "
			+"		 and (b.grpcontno = cno)), "
			+" md, "
			+" '', "
			+" '' "			
           +"	from (select substr(managecom, 1, 4) mg1,"
							+" substr(managecom, 1, 6) mg2,"
							+" l.managecom mg, "
							+" l.ContNo cno, "
							+" max(l.makedate) md, "
            +" count(1) cnt "
				+"	from lccontprint l "
				+"  where 1 = 1  and conttype='2'    "
				+"  and exists (select 'X' from lcgrpcont e where e.grpcontno=l.contno and e.printcount='1') "
				+" and exists (select 'X'  from lpgrpedoritem where l.contno=grpcontno and edorstate='0' and edortype='LR')"
				+  BqNameFun.getWherePart("l.managecom", this.ManageCom, "like")
                +  tDateComPareSQL
    			+  BqNameFun.getWherePart("l.ContNo",StartContNo, ">=")
    			+  BqNameFun.getWherePart("l.ContNo",EndContNo, "<=")	
				+" group by rollup(substr(l.managecom, 1, 4),"
												+" substr(l.managecom, 1, 6),"
												+" l.managecom,"
												+" l.ContNo)"				
				+")"
				+tGradeSQL;
         }

        logger.debug("SQL:"+tSQL);
        ListTable tlistTable = new ListTable();
        ExeSQL tExeSQL = new ExeSQL();
        String strArr[] = null;
        tlistTable.setName("LCPol");
        tSSRS=tExeSQL.execSQL(tSQL);
        //dSumCount = tSSRS.getMaxRow();

        int tEnd=0;
        

    	
    	String tLastContNo="";
    	
        for (int i=1;i<=tSSRS.getMaxRow();i++)
        {
        	//strArr = new String[8];
        	 //集体保单号	代理人组别	单位名称	被保人数	业务员	发送日期	签收人	签收日

        	String tCurConNo=tSSRS.GetText(i,4);
        	//8位机构
        	String tBranchMg=tSSRS.GetText(i,3);
        	//6位机构
        	String tSubMg=tSSRS.GetText(i,2);
        	//4位机构
        	String tSubBranchMg=tSSRS.GetText(i,1);
            if("".equals(tLastContNo)&&(!"".equals(tCurConNo)))
            {
                strArr = new String[8];
    			strArr[0] = "集体保单号";
    			strArr[1] = "代理人组别";
    			strArr[2] = "单位名称";
    			strArr[3] = "被保人数";
    			strArr[4] = "业务员";
    			strArr[5] = "发送日期";
    			strArr[6] = "签收人";
    			strArr[7] = "签收日";
                tlistTable.add(strArr);
            }
            tLastContNo=tCurConNo;
            //四级机构汇总
            if(!"".equals(tBranchMg)&&"".equals(tCurConNo))
            {
                strArr = new String[8];
     			strArr[0] = "四级机构代码：";
    			strArr[1] = tBranchMg;
    			strArr[2] = "";
    			strArr[3] = "";
    			strArr[4] = "总单量";
    			strArr[5] = "共" + tSSRS.GetText(i,5) + "件";
    			strArr[6] = "";
    			strArr[7] = "";
                tlistTable.add(strArr);
    			continue;
            }

            //三级机构汇总
            if(!"".equals(tSubMg)&&"".equals(tCurConNo))
            {
                strArr = new String[8];
     			strArr[0] = "三级机构代码：";
    			strArr[1] = tSubMg;
    			strArr[2] = "";
    			strArr[3] = "";
    			strArr[4] = "总单量";
    			strArr[5] = "共" + tSSRS.GetText(i,5) + "件";
    			strArr[6] = "";
    			strArr[7] = "";
                tlistTable.add(strArr);
     			continue;
            }
            //二级机构汇总
            if(!"".equals(tSubBranchMg)&&"".equals(tCurConNo))
            {
                strArr = new String[8];
     			strArr[0] = "二级机构代码：";
    			strArr[1] = tSubBranchMg;
    			strArr[2] = "";
    			strArr[3] = "";
    			strArr[4] = "总单量";
    			strArr[5] = "共" + tSSRS.GetText(i,5) + "件";
    			strArr[6] = "";
    			strArr[7] = "";
                tlistTable.add(strArr);
     			continue;
            }
            if(!"".equals(tCurConNo))
            {
                strArr = new String[8];

       			strArr[0] =tSSRS.GetText(i,4);// "集体保单号";     			
    			strArr[1] =tSSRS.GetText(i,6);// "代理人组别"; 
    			strArr[2] =tSSRS.GetText(i,7);// "单位名称"; 
    			strArr[3] =tSSRS.GetText(i,8);// "被保人数"; 
    			strArr[4] =tSSRS.GetText(i,9);// "业务员"; 
    			strArr[5] =tSSRS.GetText(i,10);// "发送日期"; 
    			strArr[6] =tSSRS.GetText(i,11);// "签收人"; 
    			strArr[7] ="";// "签收日"; tSSRS.GetText(i,12)
    	        tlistTable.add(strArr);
    	        tEnd++;
            }	

        }

   		xmlexport.addDisplayControl("displayBillREAdd");
    	texttag.add("ManageSubCom", "机构代码："+ManageCom+"          总单量"+"共" 
    	+ String.valueOf(tEnd) + "件" +"              机构签发人：                 "+"签收日期:           ");
        texttag.add("StartDate",mDay[0]+" " + mTime[0]);
        texttag.add("EndDate",mDay[1]+" "+mTime[1]);
        texttag.add("ManageCom",ManageCom);
        texttag.add("Operator",mGlobalInput.Operator);

        if (texttag.size()>0)
            xmlexport.addTextTag(texttag);
        strArr = new String[8];
        xmlexport.addListTable(tlistTable, strArr);
        //xmlexport.outputDocumentToFile("C:/XML","Bill.XML");//输出xml文档到文件
        mResult.addElement(xmlexport);
        return true;

    }
    public static void main(String[] args) {
        LCGrpPolBillF1PBL LCContBillF1PBL1 = new LCGrpPolBillF1PBL();
    }

//  public String replace(
}
