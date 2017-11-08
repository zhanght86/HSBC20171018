package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
public class BQDiffItemStatdeal {
private static Logger logger = Logger.getLogger(BQDiffItemStatdeal.class);
	

	    /** 错误处理类，每个需要错误处理的类中都放置该类 */
	    public CErrors mErrors = new CErrors();
	    private VData mResult = new VData();
         /** 全局数据 */
	    private GlobalInput mGlobalInput = new GlobalInput();
	    private String mStartDate = ""; // 统计开始时间
	    private String mEndDate = "";   //统计结束日期
	    private String mManageCom = ""; //统计机构
	    private String mStatType = "";  //统计粒度 (4-二级机构 6-三级机构)
	    private String  mEdorType="";  //统计项目

	    public BQDiffItemStatdeal(){ }

	    /**传输数据的公共方法*/
	    public boolean submitData(VData cInputData, String cOperate)
	    {
	        if (!cOperate.equals("PRINT"))
	        {
	            CError.buildErr(this, "不支持的操作字符串");
	            return false;
	        }

	        if (!getInputData(cInputData))
	        {
	            return false;
	        }

	        if (!getPrintData())
	        {
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
	        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",0));
	        TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
	        if (tTransferData == null || mGlobalInput == null)
	        {
	            CError.buildErr(this, "缺少传入后台的参数！");
	            return false;
	        }

	        mStartDate = (String)tTransferData.getValueByName("StartDate");
	        mEndDate = (String)tTransferData.getValueByName("EndDate");
	        mManageCom = (String)tTransferData.getValueByName("ManageCom");
	        mStatType = (String)tTransferData.getValueByName("StatType");
	        mEdorType = (String)tTransferData.getValueByName("EdorType");
	        if(mStartDate.equals("") || mEndDate.equals("")|| mStatType.equals(""))
	        {
	            CError.buildErr(this, "没有得到足够的查询信息！");
	            return false;
     }

	        return true;
	    }

	    /**
	     * 获取保全项目系数表
	     * @return
	     */

	    private boolean getPrintData()
	    {
	        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
	        xmlexport.createDocument("BQDiffItemStat.vts", "");

	        ListTable tlistTable = new ListTable();
	        tlistTable.setName("WorkStat");

	        //指标项：机构代码
	      
	        String ManSql ="";
	       
	        if(mManageCom==null ||mManageCom.equals(""))
	        {ManSql="  and 1=1 and managecom not in('8699')";}	
	        else 
	        {
	        	ManSql= "  and managecom like concat('" + "?mManageCom?" +"','%') and managecom not in('8699')";
	        	
	        } 
	        
	        String EdorSql="";
	        if(mEdorType==null ||mEdorType.equals(""))
	        {EdorSql="and 1=1";}	
	        else 
	        {
	        EdorSql= "and a.edortype='"+"?mEdorType?"+"'";
	        	
	        }
	        
	   
	        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				String asql = "select substr(managecom,1," + "?mStatType?" + ") ,(select edorname from lmedoritem where a.edortype=edorcode and appobj='I' ),"
				        + "  count(distinct edoracceptno ),sum(a.getmoney),(select  (case when sum(convertrate) is not null then sum(convertrate)  else 0 end) from lmedorstatrate where edortype=a.edortype) "
                         + "From lpedoritem  a where edorstate='0' and grpcontno='00000000000000000000'" +
				        		" and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
								+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
				        		+ ")"
							    +	ManSql	+ EdorSql
								+ " having (select count(usercode) from lduser where usercode= min(a.operator))>0"
					            
						    +" group by substr(managecom,1," + "?mStatType?" + "),edortype"  ;
				sqlbv.sql(asql);
				sqlbv.put("mStatType", mStatType);
				sqlbv.put("mStartDate", mStartDate);
				sqlbv.put("mEndDate", mEndDate);
				sqlbv.put("mEdorType", mEdorType);
				sqlbv.put("mManageCom", mManageCom);
				SSRS tssrs = new ExeSQL().execSQL(sqlbv);
				if(tssrs == null || tssrs.getMaxRow() <= 0)
				{
						CError.buildErr(this, "查询到的数据为空！");
						return false;
        }
	        String[] strArr = new String[5];
					double[] tTotal = {0.0,0.0,0.0};
		for (int i = 1 ; i <= tssrs.getMaxRow(); i++)
		{
	            strArr = new String[5];
	            strArr[0] = ReportPubFun.getMngName(tssrs.GetText(i,1));                 //公司机构名称
	            strArr[1] = tssrs.GetText(i,2);;                //保全项目
	            strArr[2] = tssrs.GetText(i,3);     //件数
	            strArr[3] = tssrs.GetText(i,4);; //涉及金额
	            strArr[4] = tssrs.GetText(i,5);;//对应工作量
	   
	        				
	            tlistTable.add(strArr);

							//计算合计值
							tTotal[0]+=PubFun.round(Double.parseDouble(tssrs.GetText(i,3)),2);//Double.parseDouble 转换为double值类型,而Double.valueof()转化为Double类类型
							tTotal[1]+=PubFun.round(Double.parseDouble(tssrs.GetText(i,4)),2);
							tTotal[2]+=PubFun.round(Double.parseDouble(tssrs.GetText(i,5)),2);
	        }			
					String[] tArr = new String[5];
					tArr[0] = "合计";     //"机构名称";
					tArr[1] = "保全项目";    
					tArr[2] = tTotal[0]+"";    
					tArr[3] = tTotal[1]+"";     
					tArr[4] = tTotal[2]+"";    
				
					tlistTable.add(tArr);

	     
	        xmlexport.addListTable(tlistTable, tArr);

	        TextTag texttag = new TextTag(); //新建一个TextTag的实例
	        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
	        texttag.add("StartDate", mStartDate);
	        texttag.add("EndDate", mEndDate);
	        texttag.add("date", PubFun.getCurrentDate());
	        logger.debug("大小" + texttag.size());
	        if (texttag.size() > 0)
	        {
             xmlexport.addTextTag(texttag);
	       
	       
	        }

	        //xmlexport.outputDocumentToFile("e:\\","BQWorkStat.xml");//输出xml文档到文件
	        mResult.clear();
	        mResult.addElement(xmlexport);

	        return true;
	    }

	    public VData getResult()
	    {
	        return this.mResult;
	    }

	    /* 测试 */
	   
	}	
	
	

