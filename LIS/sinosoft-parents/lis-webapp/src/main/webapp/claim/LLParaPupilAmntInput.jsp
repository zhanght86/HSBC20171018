<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--�û�У����-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLParaPupilAmntInput.jsp
//�����ܣ�δ�����˱����׼ά��
//�������ڣ�2005-9-16
//������  ��zhangyang
//���¼�¼��  ������ zhangyang    ��������     ����ԭ��/����
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
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLParaPupilAmnt.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <%@include file="LLParaPupilAmntInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm id=fm target="fraSubmit">   	
    <!--δ�����˱����׼ά��-->    
                                    
	<Table>
	     <TR>
	     	<TD class=common>
	     		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryHospital);">
	     	</TD>
	        <TD class= titleImg>
	        	δ�����˱����׼��ѯ
	       	</TD>
	    </TR>
	</Table> 
	
	 
	<Div  id= "QueryHospital" style= "display:''" class="maxbox1">
	<Table  class= common>
      <TR class= common>  
				<TD class=title5>�������</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComCodeQ id=ComCodeQ verify="�������|code:comcode&notnull" onDblClick="return showCodeList('comcode',[this,ComCodeNameQ],[0,1]);" onClick="return showCodeList('comcode',[this,ComCodeNameQ],[0,1]);" onKeyUp="return showCodeListKey('comcode',[this,ComCodeNameQ],[0,1]);"><input class=codename name=ComCodeNameQ id=ComCodeNameQ readonly=true elementtype=nacessary>
				</TD>
      <TD class=title5></TD>
      <TD class=input5></TD>
	    </TR>   
	</Table>  
	
	
	</Div>         	 
    <input value="��   ѯ" class= cssButton type=button onclick="InitQueryClick();">
<!--<a href="javascript:void(0);" class="button" onClick="InitQueryClick();">��    ѯ</a>-->
	<Table>
	    <TR>
	     	<TD class=common>
	     		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLParaPupilAmntGrid);">
	     	</TD>
	      <TD class= titleImg>
	      	δ�����˱����׼�б�
	      </TD>
	    </TR>
	</Table>	    
	

<Div id= "DivLLParaPupilAmntGrid" style= "display:''">    
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanLLParaPupilAmntGrid"></span></TD>
		    </TR>
		</Table>
       
	</Div>
    
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></TD>
	          <TD class= titleImg>δ�����˱����׼��Ϣ</TD>
	     </TR>
	</Table>  
    <Div id= "peer" style= "display:''" class="maxbox">       
         
        <Table  class= common>
            <TR class= common>   
				<TD class=title5>�������</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComCode id=ComCode verify="�������|code:comcode&notnull" onDblClick="return showCodeList('comcode',[this,ComCodeName],[0,1]);" onClick="return showCodeList('comcode',[this,ComCodeName],[0,1]);" onKeyUp="return showCodeListKey('comcode',[this,ComCodeName],[0,1]);"><input class=codename name=ComCodeName id=ComCodeName readonly=true elementtype=nacessary>
				</TD>
				<TD CLASS=title5>��׼����</TD>
             <TD CLASS=input5><Input class="wid" NAME=BaseValue id=BaseValue VALUE="" CLASS=common></TD>
				</TR>
            <TR class= common>   
				
        <TD class=title5>��������</TD>
				<TD class=input5>
					<!--<Input class="coolDatePicker"  dateFormat="short" name=StartDate verify="��������|date" onblur="CheckDate(fm.StartDate);">-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��������|date" onBlur="CheckDate(fm.StartDate);" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				<TD class=title5>��������</TD>
				<TD class=input5>
					<!--<Input class="coolDatePicker"  dateFormat="short" name=EndDate verify="��������|date" onblur="CheckDate(fm.EndDate);">-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|date" onBlur="CheckDate(fm.EndDate);" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				</TR>            
		<!--</Table>                             
    </Div> 
    <Div id= "DivLLParaPupilAmntInfo" style= "display:'none'"> -->
     <tr class=common>
     <TD CLASS=title5>�ϼ�����</TD>
             <TD CLASS=input5><Input class="wid" NAME=UpComCode id=UpComCode CLASS=common></TD>
             <TD CLASS=title5></TD>
             <TD CLASS=input5></TD>
             </tr>
    </Table>                             
    </Div>   
   			<!--<input class=cssButton name="saveBaseValueButton"   value="��   ��"  type=button onclick="BaseValueAddClick();">
			<input class=cssButton name="editBaseValueButton"   value="��   ��"  disabled type=button onclick="BaseValueEditClick();">
			<input class=cssButton name="deleteBaseValueButton" value="ɾ   ��"  disabled type=button onclick="BaseValueDeleteClick();">                      
            <input class=cssButton name="resetBaseValueButton"  value="��   ��" type=button onclick="BaseValueResetClick();"> -->
            <a href="javascript:void(0);" name="saveBaseValueButton" id="saveBaseValueButton" class="button"  onClick="BaseValueAddClick();">��    ��</a>
               <a href="javascript:void(0);" name="editBaseValueButton" id="editBaseValueButton" class="button"  disabled="true" onClick="BaseValueEditClick();">��    ��</a>
               <a href="javascript:void(0);" name="deleteBaseValueButton" id="deleteBaseValueButton" class="button"  disabled="true" onClick="BaseValueDeleteClick();">ɾ    ��</a>
               <a href="javascript:void(0);" name="resetBaseValueButton" id = "resetBaseValueButton  "class="button" onClick="BaseValueResetClick();">��    ��</a> 
    
    <!--���ر�����-->	
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--��ѯ֪ͨ����-->
 
 	<input type=hidden id="tOperator" name=tOperator > 
	<input type=hidden id="tComCode" name=tComCode > 	
	<input type=hidden id="fmtransact" name="fmtransact">        
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
