<%
//程序名称:保全报表打印(初始值）
//程序功能:各模块的清单类型的报表打印  
//创建日期：2003-11-17
//创建人  ：郭翔
//更新记录：  更新人    更新日期     更新原因/内容
//其中：
// * <p>bq1：保全日结(费用类-个险)</p>
// * <p>bq2: 保全月结(综合类-个险)</p>
// * <p>bq3: 保全日结(费用类-法人明细)</p>
// * <p>bq4: 保全月结(综合类-法人明细)</p>
// * <p>bq5: 保全日结(费用类-银代)</p>
// * <p>bq6: 保全月结(综合类-银代)</p>
//add 
// * <p>bq7: 保全日结清单（费用类--团险合计）</p>
// * <p>bq8: 保全月结清单（综合类--团险合计）</p>
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.*" %>
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="com.sinosoft.lis.vschema.*" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.utility.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch =tG1.ComCode;
    String strCurTime = PubFun.getCurrentDate();   
    String Code = request.getParameter("Code");
    String CodeName ="";
    loggerDebug("BQNewcalculate","bq的参数为:"+Code);
    String CurrentDate = PubFun.getCurrentDate();
    String mDay[]=PubFun.calFLDate(CurrentDate);
    String name="../xreport/BQNewcalculate.jsp?Code="+Code;
    
    String busiName="ExeSQLUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    VData cInputData = new VData();
    TransferData tTransferData = new TransferData();
    String querySql = "select NodeName from LDMenu where RunScript='"+name+"'";
    tTransferData.setNameAndValue("SQL",querySql);
    cInputData.add(tTransferData);
    boolean dealFlag = tBusinessDelegate.submitData(cInputData,"execSQL",busiName);
    if(dealFlag){
    	VData responseVData = tBusinessDelegate.getResult();
    	SSRS resultSSRS =(SSRS) responseVData.getObjectByObjectName("SSRS", 0);
    	for(int i=1;i<=resultSSRS.MaxRow;i++){
    		CodeName = resultSSRS.GetText(i,1);
    	}
    }
    
    
    
//    LDMenuDB tLDMenuDB = new LDMenuDB();
//    tLDMenuDB.setRunScript(name);
//    LDMenuSet mLDMenuSet=tLDMenuDB.query();
//    for (int i=1;i<=mLDMenuSet.size();i++){
//      LDMenuSchema tLDMenuSchema = mLDMenuSet.get(i);
//       CodeName=tLDMenuSchema.getNodeName();
//    }  
      
%>
<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script language="javascript">
   function submitForm(){
      var code="<%=Code%>";
      if (fm.StationCode.value.length ==0){
	 alert("统计区域没选择！！！！");
	 return false;
      }     
      fm.target = "f1jprint";
      fm.action="./BQF1Print.jsp";
      fm.submit();
   }  
   function getEdorInfo()
	{
	var turnPage = new turnPageClass();
	
	var sqlid1="DSHomeContSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("xreport.BQNewcalculateSql"); //指定使用的properties文件名
	mySql1.addSubPara(); //指定传入的参数
	mySql1.setSqlId(sqlid1); //指定使用的Sql的id

	var sql=mySql1.getString();
	
//	var sql = "select  EdorCode, EdorName "
// 												+ " from lmedoritem"
// 												+ "	where appobj = 'G'"
  var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
  {
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	m = turnPage.arrDataCacheSet.length;
  	for (i = 0; i < m; i++)
  	{
  		j = i + 1;
  		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
  	}
  }  
  document.all("bqcode").CodeData = tCodeData;	
}
  </script>
</head>
<body>
<form method=post name=fm>
<div class="maxbox">
   <Table class= common>
     <TR class= common> 
          <TD  class= title5>
            报表名称
          </TD>  
          <TD class= input5>
             <input class="wid" readonly class=common name=CodeName id=CodeName value=<%=CodeName%>>
          </TD>
          <TD class= input style="display: none">
            <Input class="wid" class=common name=Code id=Code value = <%=Code%> >
          </TD>
         <TD class=title5 >
            申请人
          </TD>          
          <TD class=input5 >    
             <input class="wid" class = common name=AppOperator id=AppOperator>
          </TD>  </TR>
          <TR class= common>
         <TD class=title5 >
            复核人
          </TD>          
          <TD class=input5>    
             <input class="wid" class = common name=ConfOperator id=ConfOperator>
          </TD>  
          <TD class=title5>
            统计区域
          </TD>          
          <TD class=input5>    
              <input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right" type="text" class="code" name=StationCode id=StationCode onDblClick="showCodeList('station',[this],null,null,codeSql,'1',null,250);" onMouseDown="showCodeList('station',[this],null,null,codeSql,'1',null,250);"  onKeyUp="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);">
          </TD>  
      </TR>
      <TR class = common>    
          
          <TD class=title5>
            保全项目
          </TD>          
          <TD class=input5>  
          <% 
             if(Code.equals("bq7")||Code.equals("bq10")){ //团险日结和合计共同使用
           %>
             <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="bqcode" id="bqcode"  readonly=true ondblclick="getEdorInfo();return showCodeListEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);" onMouseDown="getEdorInfo();return showCodeListEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);" onkeyup="getEdorInfo();return showCodeListKeyEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);"><input class=codename name="EdorTypeName" id="EdorTypeName" readonly=true>
           <% 
             }
           %>      
           </TD>
           <TD class= title5>
            开始时间
          </TD>          
          <TD class=input5> 
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartTime'});" verify="起始时间|NOTNULL" dateFormat="short" name=StartTime id="StartTime"><span class="icon"><a onClick="laydate({elem: '#StartTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        <TR class =common>    
         
          <TD class =title5> 
            结束时间
          </TD>
          <TD class=input5>  
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndTime'});" verify="结束时间|NOTNULL" dateFormat="short" name=EndTime id="EndTime"><span class="icon"><a onClick="laydate({elem: '#EndTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD class =title5></TD>
          <TD class=input5></TD>
         <!-- <TD class=title colspan=2>
          	<INPUT class=cssButton  VALUE="打 印 报 表" TYPE=button onclick="submitForm()">     
          </TD> -->         
 
      </TR>
    </Table> 
    <!--<INPUT class=cssButton  VALUE="打 印 报 表" TYPE=button onclick="submitForm()"> --> 
    <a href="javascript:void(0);" class="button" onClick="submitForm();">打印报表</a>
	</div>
       
    
    <input type=hidden id="opt" name="opt">
 </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"> </span> 
</body>
</html>
<script>
var codeSql = "1  and code like #"+<%=Branch%>+"%#";
var codeSql1 = "1 and othersign = #1#";
</script>
