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
	var managecom = "<%=tGI.ManageCom%>"; //��¼��������
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
  	<SCRIPT src="NewPolQuery.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="NewPolQueryInit.jsp"%>
	</head>

<body  onload="initForm();" >
  	<form action="./NewPolQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- ��ʾ������LLReport1����Ϣ -->
<table class= common border=0 width=100%>
    	<tr>   
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>  		
    		 <td class= titleImg>
        		�����ѯ����
       		 </td>   		      
    	</tr>
    </table>
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
      	<table  class= common>
          <TR  class= common>
        	  <TD  class= title5>��������	</TD>
            <TD  class= input5>
          	<Input class=codeno name=ManageCom 
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" 

            onDblClick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" 
            onKeyUp="return showCodeListKey('ComCode',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" readonly>
          	</TD>
          	<TD  class= title5>����</TD>      
                <TD  class= Input5> <Input class="codeno" CodeData="0|^����|8611^����|8615-8612-8635^�н�|8621^����|8616^����|8624"
                 name=ScanType verify="" 
                 style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('ScanType',[this,ScanTypeName],[0,1]);"

                 onDblClick="showCodeListEx('ScanType',[this,ScanTypeName],[0,1]);"
                  onKeyUp="showCodeListKeyEx('ScanType',[this,ScanTypeName],[0,1]);"><input class=codename name=ScanTypeName readonly=true >
          </TD>  
         </TR>
         <TR  class= common>     
           <TD  class= title5>
              ɨ�迪ʼʱ��	
            </TD>
            <TD  class= input5>
              <input class="coolDatePicker" dateFormat="short" id="IssueStartDate"  name="IssueStartDate" onClick="laydate
({elem:'#IssueStartDate'});" > <span class="icon"><a onClick="laydate({elem: '#IssueStartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>
            <TD  class= title5>
              ɨ�����ʱ��	
            </TD>
            <TD  class= input5>
              <input class="coolDatePicker" dateFormat="short" id="IssueEndDate"  name="IssueEndDate" onClick="laydate
({elem:'#IssueEndDate'});" > <span class="icon"><a onClick="laydate({elem: '#IssueEndDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>	
            </TD>  
         </tr>    
         <TR  class= common>     
           <TD  class= title5>
              �б���ʼʱ��	
            </TD>
            <TD  class= input5>
             
              <input class="coolDatePicker" dateFormat="short" id="SignStartDate"  name="SignStartDate" onClick="laydate
({elem:'#SignStartDate'});" > <span class="icon"><a onClick="laydate({elem: '#SignStartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>
            <TD  class= title5>
              �б�����ʱ��	
            </TD>
            <TD  class= input5>
              <input class="coolDatePicker" dateFormat="short" id="SignEndDate"  name="SignEndDate" onClick="laydate
({elem:'#SignEndDate'});" > <span class="icon"><a onClick="laydate({elem: '#SignEndDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>  
         </tr>   
         <TR  class= common>
        	  <TD  class= title5>��������</TD>
                <TD  class= Input5> <input class="codeno" name=SaleChnl
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 

                 onDblClick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
                 onKeyUp="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName readonly=true>
          </TD>  
         </TR>
         </table>  
     </div></div>
</form>
<form action="" method=post name=fmSave target="f1print">
  
  
  <!--	<p>
      <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onClick="easyQuery();">
      <INPUT VALUE="��  ӡ" class=cssButton TYPE=button onClick="PrintNoticeBill();">
  	</p>
  	-->
    <a href="javascript:void(0);" class="button" onClick="easyQuery();">��   ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="PrintNoticeBill();">��   ӡ</a>

    
  	 <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 �б������嵥����
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''"  align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanPolGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
      	<INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
  	
  	<Div id="OldInfoDiv" style ="display:'none'">
  	<!--���Ӹ��嵥�򱨱���˵��-->
    <Table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivShow);"></TD>
            <TD class= titleImg>��;˵��:</TD>
        </TR>
    </Table>
    
    <Div id= "DivShow" style= "display: ''">
        <Table class=common>
          <TR class= common>
          	���嵥����ͳ��ɨ������е�֤����ϸ�嵥���ɸ����嵥���е�֤�Ĺ鵵����
          </TR>	
        </Table>
    </Div>
    
    <Table>
    <TR>
        <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivRemark);"></TD>
        <TD class= titleImg>��ע��Ϣ:</TD>
    </TR>
    </Table>
    <Div id= "DivRemark" style= "display: ''">
        <Table class=common>
          <TR class= common >
          	<font color=red>�������κŽ��в�ѯ����ӡ</font>
          </TR>	
        </Table>
    </Div>
    </Div>
              <Input Type=hidden name=StrSQL>
    
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>