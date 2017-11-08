<%
//程序名称:承保相关工作量统计(初始值）
//程序功能:承保工作量报表打印  
//创建日期：2009-1-24
//创建人  ：liuqh
//更新记录：  更新人    更新日期     更新原因/内容
//其中：UnderWirte 指承保模块  app 指录入 uw 指核保
// * <p>uw1：保险体检件统计报表</p>
// * <p>uw2: 撤单件统计报表</p>
// * <p>uw3: 拒保延期件统计报表</p>
// * <p>uw4: 职业分布统计报表</p>
// * <p>uw5: 高保额件分布状况统计报表</p>
// * <p>uw6: 高保额件明细清单</p>
// * <p>uw7: 承保工作效率统计表</p>
// * <p>uw8: 新单统计表</p>
// * <p>uw9: 问题件统计表</p>
// * <p>uw10: 核保师工作量统计表</p>
// * <p>app1: 契约工作量统计报表</p>
// * <p>app2: 初审扫描工作量统计</p>
// * <p>app3: 投保单财务未录入信息查询</p>

%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.db.*" %>
<%@page import="com.sinosoft.lis.vdb.*" %>
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="com.sinosoft.lis.vschema.*" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch =tG1.ComCode;
    String Code = request.getParameter("Code");
    String CodeName ="";
    System.out.println("url的参数为:"+Code);
    String CurrentDate = PubFun.getCurrentDate();
    String mDay[]=PubFun.calFLDate(CurrentDate);
    System.out.println(mDay[0]+"--"+mDay[1]);
    LDMenuDB tLDMenuDB = new LDMenuDB();
    String name="../f1print/UnderWritecalculate.jsp?Code="+Code;
    tLDMenuDB.setRunScript(name);
    LDMenuSet mLDMenuSet=tLDMenuDB.query();
    for (int i=1;i<=mLDMenuSet.size();i++){
      LDMenuSchema tLDMenuSchema = mLDMenuSet.get(i);
       CodeName=tLDMenuSchema.getNodeName();
       System.out.println("CodeName:"+CodeName);
       System.out.println("Code:"+Code);
    }  
      
%>

<html> 
<head>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <script language="javascript">
   function submitForm(){
      var code="<%=Code%>";
      //if (fm.StationCode.value.length ==0){
	  //     alert("统计区域没选择！！！！");
	  //     return false;
      //}
      if ((fm.StartTime.value.length ==0)||(fm.EndTime.value.length ==0)){
          alert("时间没有选择！！！！");
          return false;
      }
      if(code=="claim1"||code=="claim2"||code=="claim3"||code=="claim4"
         ||code=="claim5"||code=="claim6"||code=="claim7"
         ||code=="claim8"||code=="claim9"||code=="claim12"
         ||code=="claim13"||code=="claim14"){
	  
	     fm.target = "f1jprint";
	     fm.action="./ClaimReportF1Print.jsp";
      }
      if(code=="claim10"||code=="claim15"){
	     fm.target = "f1jprint";
	     fm.action="./ClaimStatisticF1Print.jsp";
      }
      if(code=="uw1"||code=="uw2"||code=="uw3"||code=="uw4"||code=="uw5"||code=="uw6"||code=="uw7"||code=="uw8"||code=="uw9"||code=="uw10"||code=="uw11"||code=="uw12"){
          fm.target = "f1jprint";
          fm.action="./UWF1Print.jsp";
      }
      if(code=="app1"||code=="app2"){
      	if ((fm.ManageCom.value.length ==0)){
      	    if(confirm("建议您选择管理机构，否则数据量过大！是否选择管理机构")==true){
      	       return false;
      	    }
     	 }
          fm.target = "f1jprint";
          fm.action="./AppF1Print.jsp";
      }
      fm.submit();
   }  
   function changeMode(objCheck){

	if(objCheck.checked == true) 
	   fm.RiskCode.disabled = true;
    else 
	   fm.RiskCode.disabled = false;
   }
  
</script>
</head>
<body>
<form method=post name=fm id=fm>
<div class="maxbox">
   <Table class= common>
     <TR class= common> 
          <TD  class= title5>
            报表名称
          </TD>  
          <TD class=input5>
             <input readonly class="wid" name=CodeName id=CodeName value=<%=CodeName%>>
          </TD>
          <TD class= input style="display: none">
            <Input class=common class="wid" name=Code value = <%=Code%> >
          </TD>
      </TR>
<%if(Code.equals("uw12")){
%>
<TR class= common> 
          <TD  class= title5>
            个人工号
          </TD>  
          <TD class=input5>
		<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" type="text" class="code" name=UserCode id=UserCode onDblClick="showCodeList('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);" onclick= "showCodeList('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);" onKeyUp="return showCodeListKey('uwpopedomcode',[this],null,null,codeSql1,'1',null,250);">		
          </TD>          
     </TR>
<%}%>
<%     
if(Code.equals("claim10")||Code.equals("claim11")||Code.equals("claim15")){  
%>      
     <TR class = common disabled>    
          <TD class=title5>
            统计区域
          </TD>          
          <TD class=input5>    
              <input type="text" class="common wid" name=StationCode id=StationCode value="86" disabled>
          </TD>
      </TR>
      <TR class =common>    
          <TD class= title5>
            开始时间
          </TD>          
          <TD class=input5> 
           
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|notnull" dateFormat="short" name=StartDate value="<%=mDay[0]%>" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD class =title5> 
            结束时间
          </TD>
          <TD class=input5>  
            
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="截止日期|notnull" dateFormat="short" name=EndDate value="<%= mDay[1]%>" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
      </TR>
<%
}else{
     if(Code.equals("app1")){
%>    
     <TR class=common>
           <!--TD  class= title>统计类型</TD>      
                <TD  class= Input> <Input class="codeno" name=StatisticType CodeData="0|^1|统计类型|^2|补扫类|^3|通知书类|^4|扫描资料申请类|^5|回执类|^6|管理中心问题件|" ondblclick="showCodeListEx('StatisticType',[this,StatisticTypeName],[0,1]);" onkeyup="showCodeListEx('StatisticType',[this,StatisticTypeName],[0,1]);"><input class=codename name=StatisticTypeName readonly=true  elementtype=nacessary>
          </TD-->
           <TD  class= title5>
            操作员工号
          </TD>
          <TD  class= input5><Input class='common wid' name=OperaterNo id=OperaterNo></TD>
           <TD  class= title5>管理机构</TD>      
                <TD  class= Input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom verify="管理机构|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true  elementtype=nacessary>
          </TD>
     </TR>


<%
    }else{
         if(Code.equals("app2")){    
            GlobalInput tG = new GlobalInput();
            tG = (GlobalInput)session.getValue("GI"); 
%>
      <TR class = common>    
          <TD  class= title5>
            操作员工号
          </TD>
          <TD  class= input5><Input class='common wid' name=OperaterNo id=OperaterNo></TD>
          <TD  class= title5>管理机构</TD>      
                <TD  class= Input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom verify="管理机构|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true  elementtype=nacessary>
          </TD>
      </TR>
      
      
      
<%
         }else{
        	 if(Code.equals("app3")){
        		 
%>

<%
        	 }else{
%>     
     <TR class = common>    
          <TD class=title5>
            统计区域
          </TD>          
          <TD class=input5>    
              <input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" type="text" class="code" name=StationCode id=StationCode onDblClick="showCodeList('station',[this],null,null,codeSql,'1',null,250);" onclick="showCodeList('station',[this],null,null,codeSql,'1',null,250);"  onKeyUp="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);">
          </TD>       
      </TR>
<%     
      }
   }
}
}
%>    
	<TR class =common>    
          <TD class= title5>
            开始时间
          </TD>          
          <TD class=input5> 
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始时间|notnull" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> 
          </TD>
          <TD class =title5> 
           结束时间
          </TD>
          <TD class=input5>  
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束时间|notnull" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
      </TR>
    </Table>  </div>

       <!--<INPUT class=cssButton  VALUE="打 印 报 表" TYPE=button onclick="submitForm()"> -->
       <a href="javascript:void(0);" class="button" onClick="submitForm();">打印报表</a>   

    
 </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"> </span> 
</body>
</html>
<script>
var codeSql = "1  and code like #"+<%=Branch%>+"%#";
var codeSql1="1 and  comcode=#86# and (userstate=#0# or userstate is null) and EdorPopedom is null and uwpopedom>#A# ";
</script>