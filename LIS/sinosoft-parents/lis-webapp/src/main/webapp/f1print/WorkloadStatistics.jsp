<%
//程序名称:工作量与时效统计报表打印(初始值）
//程序功能:工作量与时效统计报表打印 
//创建日期：2012-6-26
//创建人  ：zhaojiawei


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
    //String Code = request.getParameter("Code");
    //System.out.println("url的参数为:"+Code);
    String CurrentDate = PubFun.getCurrentDate();
    String mDay[]=PubFun.calFLDate(CurrentDate);
    System.out.println(mDay[0]+"--"+mDay[1]);
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
      if(fm.ManageCom.value.length==""){
               alert("管理机构选择！！！！");
	    	   return false;
      }
      if ((fm.StartTime.value.length ==0)||(fm.EndTime.value.length ==0)){
          alert("时间没有选择！！！！");
          return false;
      }
          fm.target = "f1jprint";
          fm.action="./WorkloadPrint.jsp";
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
             <input class="wid" readonly class=common name=CodeName id=CodeName value="作业量与时效统计">
          </TD>
           <TD class=title5>管理机构</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id=ManageCom verify="管理机构|code:comcode&notnull" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');"><input class=codename name=ManageComName id=ManageComName readonly=true elementtype=nacessary>
				</TD>   
              
      </TR>
     
     <TR class= common> 
          <TD  class= title5>
            所需统计的用户号码
          </TD>  
          <TD class=input5>
		<input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" type="text" class="code" name=UserCode id=UserCode onDblClick="showCodeList('workloaduser',[this],null,null,null,'1',null,250);" onclick="showCodeList('workloaduser',[this],null,null,null,'1',null,250);"  onKeyUp="return showCodeListKey('uwpopedomcode',[this],null,null,null,'1',null,250);">		
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
      </TR>
    </Table>  </div>
    
    <!--<INPUT class=cssButton  VALUE="打 印 报 表" TYPE=button onclick="submitForm()">-->
    <a href="javascript:void(0);" class="button" onClick="submitForm();">打印报表</a>
 </form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"> </span> 
</body>
</html>