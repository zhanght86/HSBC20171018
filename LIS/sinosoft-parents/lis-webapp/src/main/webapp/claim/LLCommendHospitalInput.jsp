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
    String tCode = request.getParameter("codeno");
    String tName = request.getParameter("codename");
    %>

    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLCommendHospital.js"></SCRIPT>
    <%@include file="LLCommendHospitalInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm id=fm target="fraSubmit">   	
    <!-- 医院信息维护-->    
                                    
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryHospital);"></TD>
	        <TD class= titleImg>推荐医院信息查询</TD>
	        <TD></TD>
	     </TR>
	</Table>  
	<Div  id= "QueryHospital" style= "display:''" class="maxbox">
	    <Table  class= common>
            <TR class= common>  
 					<TD  class= title5>管理机构</TD>
					<TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=MngComQ id=MngComQ ondblclick="return showCodeList('station',[this,MngComNameQ],[0,1]);" onclick="return showCodeList('station',[this,MngComNameQ],[0,1]);" onkeyup="return showCodeListKey('station',[this,MngComNameQ],[0,1]);"><input class="codename" name="MngComNameQ" id="MngComNameQ" readonly><font size=1 color='#ff0000'><b>*</b></font></TD>		
					<TD  class= title5>医院代码</TD>
					<TD  class= input5> <input class="wid" class=common name=HospitalCodeQ id=HospitalCodeQ></TD></TR>
                    <TR class= common>
	                <TD  class= title5>医院名称</TD>      
	                <TD  class= input5> <input class="wid" class=common name=HospitalNameQ id=HospitalNameQ></TD>
                    <TD  class= title5>定点标志</TD>
					<TD  class= input5> <input class="wid" class=common name=ConFlagQ id=ConFlagQ></TD>
	       </TR> 
	       <TR class= common>  
					
	                <TD  class= title5>医院状态</TD>      
	                <TD  class= input5> <input class="wid" class=common name=HosStateQ id=HosStateQ></TD>
	                <TD  class= title5>医院等级</TD>      
	                <TD  class= input5> <input class="wid" class=common name=HosAttiQ id=HosAttiQ></TD>
	               </TR>  
	    </Table>  
	</Div>         	 
    
 <input value="查  询" class= cssButton type=button onclick="InitQueryClick();">
 <!--<a href="javascript:void(0);" class="button" onClick="InitQueryClick();">查    询</a>-->
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCommendHospitalGrid);"></TD>
	          <TD class= titleImg>推荐医院列表</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLCommendHospitalGrid" style= "display:''">    
		<Table  class= common>
		    <TR>
		    	<TD text-align: lift colSpan=1><span id="spanLLCommendHospitalGrid"></span></TD>
		    </TR>
		</Table>
        <center>
		<input class=cssButton90 value=" 首  页 " type=button onclick="getFirstPage();"> 
	    <input class=cssButton91 value=" 上一页 " type=button onclick="getPreviousPage();">                   
	    <input class=cssButton92 value=" 下一页 " type=button onclick="getNextPage();"> 
	    <input class=cssButton93 value=" 尾  页 " type=button onclick="getLastPage();">  </center>
	</Div>
    
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCommendHospitalInfo);"></TD>
	          <TD class= titleImg>推荐医院信息</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLCommendHospitalInfo" style= "display:''" class="maxbox">       
       
        <Table  class= common>
            <TR class= common>   
				<TD  class= title5>医院代码</TD>
				<TD  class= input5> <input class="wid" class=common name=HospitalCode id=HospitalCode><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title5>医院名称</TD>      
				<TD  class= input5> <input class="wid" class=common name=HospitalName id=HospitalName><font size=1 color='#ff0000'><b>*</b></font></TD></TR>
                <TR class= common>
				<TD  class= title5>医院等级</TD>      
				<!--<TD  class= input> <input class=common name=HosAtti></TD>-->         
               <TD class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="HosAtti" id="HosAtti" ondblclick="return showCodeList('llhosgrade',[this,HosAttiName],[0,1]);" onclick="return showCodeList('llhosgrade',[this,HosAttiName],[0,1]);" onkeyup="return showCodeListKey('llhosgrade',[this,HosAttiName],[0,1]);"><input class=codename name="HosAttiName" id="HosAttiName" readonly=true></TD> 	    
               <TD  class= title5>定点标志</TD>
				<!--<TD  class= input> <input class=common name=ConFlag></TD>-->
                <TD  class= input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=ConFlag id=ConFlag CodeData="0|3^0|定点^1|非定点" ondblclick="return showCodeListEx('ConFlag', [this,ConFlagName],[0,1],'','','','',100)" onclick="return showCodeListEx('ConFlag', [this,ConFlagName],[0,1],'','','','',100)" onkeyup="return showCodeListKeyEx('ConFlag', [this,],ConFlagName[0,1],'','','','',100);"><input class=codename name=ConFlagName id=ConFlagName readonly ></TD>          
           </TR>
            <TR class= common>   
				
				<TD  class= title5>残疾鉴定资质标志</TD>      
				<!--<TD  class= input5> <input class=common name=AppFlag></TD>-->
                <TD  class= input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=AppFlag id=AppFlag CodeData="0|3^0|有资质^1|无资质" ondblclick="return showCodeListEx('AppFlag', [this,AppFlagName],[0,1],'','','','',100)" onclick="return showCodeListEx('AppFlag', [this,AppFlagName],[0,1],'','','','',100)" onkeyup="return showCodeListKeyEx('AppFlag', [this,AppFlagName],[0,1],'','','','',100);"><input class=codename name=AppFlagName id=AppFlagName readonly ></TD>
				<TD  class= title5>医院状态</TD>      
				<!--<TD  class= input> <input class=common name=HosState></TD>-->         
                <TD  class= input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=HosState id=HosState CodeData="0|3^0|有效^1|暂停^2|终止" ondblclick="return showCodeListEx('HosState', [this,HosStateName],[0,1],'','','','',100)" onclick="return showCodeListEx('HosState', [this,HosStateName],[0,1],'','','','',100)" onkeyup="return showCodeListKeyEx('HosState', [this,HosStateName],[0,1],'','','','',100);"><input class=codename name=HosStateName id=HosStateName readonly ><font size=1 color='#ff0000'><b>*</b></font></TD>
            </TR>            
		</Table>                             
    </Div>   

               <input class=cssButton name="saveHospitalButton"   value="增   加"  type=button onclick="HospitalAddClick();">
				<input class=cssButton name="editHospitalButton"   value="修   改"  disabled type=button onclick="HospitalEditClick();">
				<input class=cssButton name="deleteHospitalButton" value="删   除"  disabled type=button onclick="HospitalDeleteClick();">                      
                <input class=cssButton name="resetHospitalButton"  value="重   置" type=button onclick="HospitalResetClick();">
               <!-- <a href="javascript:void(0);" name="saveHospitalButton" class="button" onClick="HospitalAddClick();">增    加</a>
               <a href="javascript:void(0);" name="editHospitalButton" class="button" disabled disabled=true onClick="HospitalEditClick();">修    改</a>
               <a href="javascript:void(0);" name="deleteHospitalButton" class="button" disabled disabled=true onClick="HospitalDeleteClick();">删    除</a>
               <a href="javascript:void(0);" name="resetHospitalButton" class="button" onClick="HospitalResetClick();">重    置</a>-->
    
    <!--隐藏表单区域-->	
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--咨询通知号码-->
    
 	<input type=hidden id="tOperator" name=tOperator > 
	<input type=hidden id="tComCode" name=tComCode > 	
	<input type=hidden id="fmtransact" name="fmtransact">        
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
