<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--用户校验类-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLCommendHospitalInput.jsp
//程序功能：医院信息维护
//创建日期：2005-7-13
//创建人  ：yuejw
//更新记录：  更新人 yuejw    更新日期     更新原因/内容
%> 
<head >
	<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLCommendHospital.js"></SCRIPT>
    <%@include file="LLCommendHospitalInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm target="fraSubmit">   	
    <!-- 医院信息维护-->    
    <hr>                                
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryHospital);"></TD>
	        <TD class= titleImg>推荐医院信息查询</TD>
	        <TD> <input value="查   询" class= cssButton type=button onclick="InitQueryClick();"></TD>
	     </TR>
	</Table>  
	<Div  id= "QueryHospital" style= "display:''">
	    <Table  class= common>
            <TR class= common>  
					<TD  class= title>医院代码</TD>
					<TD  class= input> <input class=common name=HospitalCodeQ></TD>
	                <TD  class= title>医院名称</TD>      
	                <TD  class= input> <input class=common name=HospitalNameQ></TD>
	                <TD  class= title></TD>
					<TD  class= input></TD>
	       </TR>   
	    </Table>  
	</Div>         	 
    <hr>

	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCommendHospitalGrid);"></TD>
	          <TD class= titleImg>推荐医院列表</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLCommendHospitalGrid" style= "display:''">    
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanLLCommendHospitalGrid"></span></TD>
		    </TR>
		</Table>
		<input class= button value=" 首  页 " type=button onclick="getFirstPage();"> 
	    <input class= button value=" 上一页 " type=button onclick="getPreviousPage();">                   
	    <input class= button value=" 下一页 " type=button onclick="getNextPage();"> 
	    <input class= button value=" 尾  页 " type=button onclick="getLastPage();">  
	</Div>
    <hr>
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCommendHospitalInfo);"></TD>
	          <TD class= titleImg>推荐医院信息</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLCommendHospitalInfo" style= "display:''">       
        <Table>
            <TR>
                <TD><input class=cssButton name="saveHospitalButton"   value="增   加"  type=button onclick="HospitalAddClick();"></TD>
				<TD><input class=cssButton name="editHospitalButton"   value="修   改"  disabled type=button onclick="HospitalEditClick();"></TD>
				<TD><input class=cssButton name="deleteHospitalButton" value="删   除"  disabled type=button onclick="HospitalDeleteClick();"></TD>                      
                <TD><input class=cssButton name="resetHospitalButton"  value="重   置" type=button onclick="HospitalResetClick();"></TD>                
            </TR>
        </Table>    
        <Table  class= common>
            <TR class= common>   
				<TD  class= title>医院代码</TD>
				<TD  class= input> <input class=common name=HospitalCode><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title>医院名称</TD>      
				<TD  class= input> <input class=common name=HospitalName><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title>医院等级</TD>      
				<!--<TD  class= input> <input class=common name=HosAtti></TD>-->         
               <TD class= input><Input class=codeno readonly name="HosAtti" ondblclick="return showCodeList('llhosgrade',[this,HosAttiName],[0,1]);" onkeyup="return showCodeListKey('llhosgrade',[this,HosAttiName],[0,1]);"><input class=codename name="HosAttiName" readonly=true></TD> 	              
           </TR>
            <TR class= common>   
				<TD  class= title>定点标志</TD>
				<!--<TD  class= input> <input class=common name=ConFlag></TD>-->
                <TD  class= input><input class=codeno readonly name=ConFlag CodeData="0|3^0|定点^1|非定点" ondblclick="return showCodeListEx('ConFlag', [this,ConFlagName],[0,1],'','','','',100)"onkeyup="return showCodeListKeyEx('ConFlag', [this,],ConFlagName[0,1],'','','','',100);"><input class=codename name=ConFlagName readonly ></TD>
				<TD  class= title>残疾鉴定资质标志</TD>      
				<!--<TD  class= input> <input class=common name=AppFlag></TD>-->
                <TD  class= input><input class=codeno readonly name=AppFlag CodeData="0|3^0|有资质^1|无资质" ondblclick="return showCodeListEx('AppFlag', [this,AppFlagName],[0,1],'','','','',100)"onkeyup="return showCodeListKeyEx('AppFlag', [this,AppFlagName],[0,1],'','','','',100);"><input class=codename name=AppFlagName readonly ></TD>
				<TD  class= title>医院状态</TD>      
				<!--<TD  class= input> <input class=common name=HosState></TD>-->         
                <TD  class= input><input class=codeno readonly name=HosState CodeData="0|3^0|有效^1|暂停^2|终止" ondblclick="return showCodeListEx('HosState', [this,HosStateName],[0,1],'','','','',100)"onkeyup="return showCodeListKeyEx('HosState', [this,HosStateName],[0,1],'','','','',100);"><input class=codename name=HosStateName readonly ><font size=1 color='#ff0000'><b>*</b></font></TD>
            </TR>            
		</Table>                             
    </Div>   
    <hr>
    
    <!--隐藏表单区域-->	
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--咨询通知号码-->
    
 	<input type=hidden id="tOperator" name=tOperator > 
	<input type=hidden id="tComCode" name=tComCode > 	
	<input type=hidden id="fmtransact" name="fmtransact">        
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
