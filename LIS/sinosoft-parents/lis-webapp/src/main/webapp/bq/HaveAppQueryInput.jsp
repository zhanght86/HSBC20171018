
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<html>
<%
    //添加页面控件的初始化。
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //记录操作员
    var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
    var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./HaveAppQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="HaveAppQueryInit.jsp"%>
  <title>呈批件查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--业务员查询条件 -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent);">
            </td>
            <td class= titleImg>
                呈批件查询条件
            </td>            
    	</tr>
    </table>
  <Div  id= "divLAAgent" style= "display: ''">
  <div class="maxbox1">
  <table  class= common>
      <TR  class= common> 
        <TD class= title> 呈报序号</TD>
        <TD  class= input>  <Input class="wid" class=common  name=SubNo id=SubNo > </TD>
        <TD class= title> 团体合同号</TD>
        <TD class= input> <Input class="wid" class=common name=GrpContNo id=GrpContNo >  </TD>
			<TD class= title> 呈报人 </TD>
        <TD  class= input>  <Input class="wid" class=common  name=SubPer id=SubPer > </TD>
      </TR>
      <TR  class= common>
      	<TD  class= title>呈报机构</TD>
        <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class="codeno" name=SubManageCom id=SubManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1])" onMouseDown="showCodeList('station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1])" readonly=true><input class=codename name=ManageComName id=ManageComName readonly></TD></TD>
         
      	
        <TD  class= title> 呈报时间(起)  </TD>
        <TD  class= input> 
        <!--<input class="multiDatePicker" dateFormat="short"  name=startdate > --> 
        <Input class="coolDatePicker" onClick="laydate({elem: '#startdate'});" verify="有效开始日期|DATE" dateFormat="short" name=startdate id="startdate"><span class="icon"><a onClick="laydate({elem: '#startdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>   	
        	</TD>
        <TD  class= title>  呈报时间(止)  </TD>
        <TD  class= input>   
        <!--<input class="multiDatePicker"  dateFormat="short"  name=enddate >-->    
         	<Input class="coolDatePicker" onClick="laydate({elem: '#enddate'});" verify="有效开始日期|DATE" dateFormat="short" name=enddate id="enddate"><span class="icon"><a onClick="laydate({elem: '#enddate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>  
         </TD>
      </TR>
       <TR  class= common> 
       <TD  class= title> 承接人 </TD>
        <TD  class= input> 
        <input class="wid" class= common   name=DispPer >     	
        	</TD>
        	    <TD  class= title> 保全项目 </TD>
        <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class="codeno" name="EdorType" id="EdorType" ondblclick="getEdorInfo();return showCodeListEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);" onMouseDown="getEdorInfo();return showCodeListEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);" onkeyup="getEdorInfo();return showCodeListKeyEx('EdorType',[this,EdorTypeName],[0,1],'', '', '', true);"><input class=codename name="EdorTypeName" id="EdorTypeName" readonly=true>  </TD>
          
        <TD  class= title>呈报状态</TD>
        <TD  class= input>   
        <Input style="background:url(../common/images/select--bg_03.png) 	no-repeat right center" class="codeno" name=SubState id=SubState ondblclick="showCodeList('substate',[this,SubStateName],[0,1])" onMouseDown="showCodeList('substate',[this,SubStateName],[0,1])" onkeyup="showCodeListKey('substate',[this,SubStateName],[0,1])"><input class=codename name=SubStateName id=SubStateName readonly>    	 
         </TD>

           	 
         </TD>
        
      </TR>
      
    </table>
        </div>            
	  <!-- <table> 
		    <tr>
		    <td>
		    

			<td>  
			  </td>
		   <td>  </td>
			<td>   </td>
			<td>  
			  </td>
		    </tr> 
	   </table> -->
    </Div>      
      <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">-->
      <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a> </td>    				
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 呈批件查询结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        
    	<table align="center">
    		<tr>
    			<td>
			      <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
  	</div>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
  <br><br><br><br>
</body>
</html>
