<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//name       ��PNoticeBill
//function   ��Print Notice Bill
//Create Date��2003-04-16
//Creator    ��������

%>
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
     <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="FaultyReportLis.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="FaultyReportLisInit.jsp"%>
	</head>

<body  onload="initForm();" >
  	<form action="./FaultyReportLisSave.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- ��ʾ������LLReport1����Ϣ -->
<table class= common border=0 width=100%>
    	<tr>   
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLLReport1);">
            </TD>  		
    		 <td class= titleImg>
        		�����ѯ����
       		 </td>   		      
    	</tr>
    </table>
    	<Div  id= "divLLReport1" style= "display: ''">
         <div class="maxbox1" >
      	<table  class= common>
         <TR class = common>    
          <TD class=title5>�������</TD>
				<TD class=input5>
					<Input class=codeno name=ManageCom 
                    verify="�������|code:comcode&notnull"
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" 
                     onDblClick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" 
                     onKeyUp="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
				</TD>   
          <TD  class= title5>
            ���ֱ���
          </TD>
          <TD  class= input5>
            <Input class="code" name=RiskCode 
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('RiskInd',[this]);"
            onDblClick="showCodeList('RiskInd',[this]);"
             onKeyUp="return showCodeListKey('RiskInd',[this]);">
          </TD>
           </TR> 
      <TR class=common>
          <TD  class= title5>
            ��������
          </TD>
			<td class="input5" >
				<input class="codeno" name="SaleChnl"
                 verify="��������|notnull&CODE:salechnl"  verifyorder="1"
                 style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeList('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');"
                   ondblclick="showCodeList('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');"
                    onKeyUp="showCodeListKey('salechnl',[this,SaleChnlName],[0,1],null,'1 and code <> #01#',1,null,'150');" ><input class="codename" name="SaleChnlName" readonly>
			</TD>
     
        <td class="title5">�����˱��� </td>
			<td class="input5" COLSPAN="1">
				<input NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="codeno" verifyorder="1" Aonkeyup="return queryAgent();"
                 onBlur="return queryAgent();"
                 style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return queryAgent();"

                  onDblClick="return queryAgent();" ><input name=AgentName class='codename' readonly=true  elementtype=nacessary >
			</td>
             </TR> 
      <TR class=common>
	    <TD  class= title5>
            ����λ��  
          </TD>
          <TD  class="input5">
          <Input class= "codeno" name = OperatePos verify="����λ��|notnull" readonly CodeData="0|^1|�б��������·�^2|�˱�ʦ�·�"; 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeListEx('OperatePos',[this,OperatePosName],[0,1]);"
          onDblClick="return showCodeListEx('OperatePos',[this,OperatePosName],[0,1]);"
           onKeyUp="return showCodeListKeyEx('OperatePos',[this,OperatePosName],[0,1]);"><Input class = codename name=OperatePosName readonly = true>
          </TD> 
        <TD  class= title5>
            ���ض���  
          </TD>
          <TD  class="input5">
          <Input class= "codeno" name = BackObj  CodeData="0|^1|�ͻ�^3|ҵ��Ա";
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeListEx('backobj',[this,BackObjName],[0,1]);"
          onDblClick="return showCodeListEx('backobj',[this,BackObjName],[0,1]);"
           onKeyUp="return showCodeListKeyEx('backobj',[this,BackObjName],[0,1]);"><Input class = codename name=BackObjName readonly = true>
          </TD> 
      </TR>  
      <TR class =common>    
          <TD class= title5>
            ��ʼʱ��
          </TD>          
          <TD class=input5> 
             <input class="coolDatePicker" dateFormat="short" id="StartTime"  name="StartTime" onClick="laydate({elem:'#StartTime'});"> <span class="icon"><a onClick="laydate({elem: '#StartTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
             
          </TD>
          <TD class =title5> 
            ����ʱ��
          </TD>
          <TD class=input5>  

             <input class="coolDatePicker" dateFormat="short" id="EndTime"  name="EndTime" verify="����ʱ��|NOTNULL" onClick="laydate({elem:'#EndTime'});"> <span class="icon"><a onClick="laydate({elem: '#EndTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
           </TR> 
      <TR class=common>
          <TD  class= title5>
            �Ƿ������
          </TD>
          <TD  class="input5">
          <Input class= "codeno" name = IsRecord  CodeData="0|^Y|��^N|��"; 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('IsRecord',[this,IsRecordName],[0,1]);" 
          onDblClick="showCodeListEx('IsRecord',[this,IsRecordName],[0,1]);" 
          onKeyUp="showCodeListKeyEx('IsRecord',[this,IsRecordName],[0,1]);"><Input class = codename name=IsRecordName readonly = true>
          </TD> 
      </TR>
        	</table>
         </div> </div>
     
  
  
  <!--	<p>
      <INPUT VALUE="��   ѯ" class= cssButton TYPE=button onClick="easyQuery();">
      <INPUT VALUE="��ӡ������嵥" class= cssButton TYPE=button onClick="PrintNoticeBill();">
  	</p>
  	-->
     <a href="javascript:void(0);" class="button"onClick="easyQuery();">��   ѯ</a>
      <a href="javascript:void(0);" class="button"onClick="PrintNoticeBill();">��ӡ������嵥</a>
  	<!--��Ӹ��嵥�򱨱��˵��-->
    <Table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);"></TD>
            <TD class= titleImg>��ѯ���:</TD>
        </TR>
    </Table>
    <Div  id= "divCodeGrid" style= "display: ''"  align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
      	<INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
    <br><br><br><br>
    	
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
