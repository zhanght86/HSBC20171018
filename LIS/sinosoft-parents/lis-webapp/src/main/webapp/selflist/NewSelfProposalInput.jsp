<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�NewSelfProposalInput.jsp
//�����ܣ�����������-�ͻ�Ͷ����Ϣ¼�뼤��ҳ��
//�������ڣ�2010-01-25 
//������  ��yanglh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tCertifyNo = "";
	try
	{
		tCertifyNo = request.getParameter("CertifyNo");
		loggerDebug("NewSelfProposalInput","�ͻ���Ϣ¼������ÿ���="+tCertifyNo);
	}
	catch( Exception e )
	{
		tCertifyNo = "";
	}
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

%>
<head >
<script>
    var tCertifyNo="<%=tCertifyNo%>"
	var Operator = "<%=tGI.Operator%>";   //��¼����Ա
	var ManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	
	<%@include file="NewSelfProposalInit.jsp"%>
	<SCRIPT src="NewSelfProposalInput.js"></SCRIPT>
	
</head>

<body  onload="initForm(); ">
  <form action="./NewSelfProposalSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <Div  id= "divButton" style= "display: none">
    <%@include file="../common/jsp/OperateButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
    </DIV>
    
    <!-- ������Ϣ���� -->
    <table>
    	<tr>
		<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
		</td>
    		<td class= titleImg>������Ϣ</td>
    	</tr>
    </table>  
    <Div  id= "divALL0" class=maxbox1 >
	 <table  class= common>
        <TR  class= common>
          <TD  class= title>
            �����������
          </TD>
          <TD  class= input>
            <Input class="common wid" name=CertifyNo id=CertifyNo  readonly>
          </TD>
          <TD  class= title>
             ָ����Ч����     
          </TD>
		  <TD class= input><input type="checkbox" value="01" name="CValiDateType" onclick="callCValiDate(value);">
		  </TD>
          	<TD class= title>��Ч����</TD>
          	<TD class= input>
			<input class="coolDatePicker" dateFormat="short" name=CValiDate  onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
          </TR>  
    </table>

    <!-- Ͷ������Ϣ���� -->
    <table>
    	<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divLCAppnt1)"></td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    	    </td>
    	</tr>
    </table>
    <Div  id= "divLCAppnt1" style= "display: ''" class=maxbox>
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="common wid" name=AppntName id=AppntName verify="Ͷ��������|notnull&len<=20" >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex id=AppntSex  style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="Ͷ�����Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="AppntBirthday" verify="Ͷ���˳�������|notnull&date" onClick="laydate({elem: '#AppntBirthday'});" id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntIDType" id=AppntIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="Ͷ����֤������|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntIDNo" id=AppntIDNo verify="Ͷ����֤������|len<=20" >
          </TD>
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntPhone" id=AppntPhone verify="Ͷ������ϵ�绰|len<=15" >
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class="common3 wid" name="AppntPostalAddress" id=AppntPostalAddress verify="Ͷ������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntZipCode" id=AppntZipCode verify="Ͷ������������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <!--TD class=title>
             �뱻���˹�ϵ
          </TD>
          <TD  class= input>
            <Input class="code" name=RelationToLCInsured readonly CodeData="0|^00|����|^03|����|^04|ĸ��|" ondblclick="return showCodeListEx('RelationToLCInsured',[this]);" onkeyup="return showCodeListKeyEx('RelationToLCInsured',[this]);">
          </TD--> 
          
         <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntEMail" id=AppntEMail verify="Ͷ���˵�������|len<=20" >
          </TD>

       </TR>
        </table>
        <table  class= common>
       	 <tr>
		 
<td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppntOccupation1);">
</td>
       	    <td class= titleImg>
              Ͷ����ְҵ��Ϣ(��һ�еĺ�������ģ����ѯ����)
    	   </td>
    	</tr> 
    	</table>

	<div id="divAppntOccupation1" style="display: ''" class=maxbox1>
       <table  class= common>
         <TR  class= common>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntOccupationType" id=AppntOccupationType readonly >
          </TD>
          
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntOccupationName" id=AppntOccupationName  >
          </TD>
          
          <TD  class= title>
            ��ҵ����
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntWorkName" id=AppntWorkName  >
          </TD>
        </TR>
        
            	
    	<TR class=common>       	
          <td>
         	<INPUT VALUE = "ְҵģ����ѯ" class=cssButton type=button onclick="queryAppntOccupation()">
          </td>
          
          <TD  >
            <Input type=hidden name="AppntOccupationCode" readonly >
          </TD>
       </TR>
       </table>
       
    
    <table>
    <tr>
     <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divAppntCodeGrid)"></td>
    </tr>
    </table>
    
  	<div id="divAppntCodeGrid" style="display: ''; text-align:center;">
      <table class="common">
        <tr class="common">
      	  <td text-align: left colSpan=1><span id="spanAppntCodeGrid"></span></td></tr>
      </table>
      
      <input VALUE=" ��ҳ " TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <input VALUE="��һҳ" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <input VALUE="��һҳ" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <input VALUE=" βҳ " TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>
     </div>
     
    </Div>    


    <!--��������Ϣ,ֻ���뱻���˹�ϵ��ѡ��Ϊ��ĸʱ����ʾ��Ҫ�ͻ�¼�� -->
     <Div  id= "divLCInsure1" style= "display: ''">
      <table>
    	<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divLCInsure2)"></td>
    		<td class= titleImg>
    			��������Ϣ(�밴�ձ�����˳������¼��)
    	    </td>
    	</tr>
      </table>
      
<Div  id= "divLCInsure2" style= "display: ''" class=maxbox>
     
        <table  class= common>
         <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="common wid" name=LCInsuredName id=LCInsuredName verify="����������|notnull&len<=20" >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=LCInsuredSex id=LCInsuredSex verify="�������Ա�|notnull&code:Sex" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="LCInsuredBirthday" verify="�����˳�������|notnull&date"  onClick="laydate({elem: '#LCInsuredBirthday'});" id="LCInsuredBirthday"><span class="icon"><a onClick="laydate({elem: '#LCInsuredBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name="LCInsuredIDType" style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="������֤������|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredIDNo" id=LCInsuredIDNo verify="������֤������|len<=20" >
          </TD>
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredPhone" id=LCInsuredPhone verify="��������ϵ�绰|len<=15" >
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= "common3 wid" name="LCInsuredPostalAddress" verify="��������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredZipCode" id=LCInsuredZipCode verify="��������������|zipcode" >
          </TD>

        </TR>
        
       <TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredEMail" id=LCInsuredEMail verify="�����˵�������|len<=20" >
          </TD>
          <TD  class= title>��Ͷ���˹�ϵ</TD>
					<TD  class= input>
							 <Input class=code name=RelationToAppnt id=RelationToAppnt style="background:url(../common/images/select--bg_03.png) no-repeat right center" CodeData="0|^00|����^01|�ɷ�^02|����^03|����^04|ĸ��^05|����^06|Ů��" 
					  				ondblClick="showCodeListEx('RelationToAppnt',[this],[0,1]);"onkeyup="showCodeListKeyEx('RelationToAppnt',[this],[0,1]);">
					
				</TD> 
       </TR>
        </div>
        <table  class= common>
       	<tr>
		<td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsuredOccupation1);">
</td>
         <td class= titleImg>
            ������ְҵ��Ϣ(��һ�еĺ�������ģ����ѯ����)
    	 </td>  
    	</tr> 
    	</table>
    	
    	
       <div id="divLCInsuredOccupation1" style="display: ''" class=maxbox1>
        <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredOccupationType" id=LCInsuredOccupationType readonly >
          </TD>
          
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredOccupationName" id=LCInsuredOccupationName  >
          </TD>
          
          <TD  class= title>
            ��ҵ����
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredWorkName" id=LCInsuredWorkName  >
          </TD>
        </TR>
        
           </table> 	
    	<TR class=common>       	
          <td>
         	<INPUT VALUE = "ְҵģ����ѯ" class=cssButton type=button onclick="queryLcInsuredOccupation()">
          </td>
          
          <TD  >
            <Input type=hidden name="LCInsuredOccupationCode" id=LCInsuredOccupationCode readonly >
          </TD>
       </TR>
       
       </table>
       
    <table>
    <tr>
     <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divLCInsuredCodeGrid)"></td>
    </tr>
    </table>
    
  	<div id="divLCInsuredCodeGrid" style="display: ''; text-align:center;" >
      <table class="common">
        <tr class="common">
      	  <td text-align: left colSpan=1><span id="spanLCInsuredCodeGrid"></span></td></tr>
      </table>
      
      <input VALUE=" ��ҳ " TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <input VALUE="��һҳ" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <input VALUE="��һҳ" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <input VALUE=" βҳ " TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>
  	<table class=common>
  		   <tr>
		   <td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
			</td>
         	<td class=titleImg>
            	�������б�
    	 	</td>  
    	</tr> 
 </table>
<div class=maxbox1 > 
<table class=common> 
    <tr class=common>
     <td class=title> ����</td>
	 <td class=input><input value="0" class="common" id="Peoples" style="width=60" readonly >��������,</td>
	</tr>
		<tr class=common>
     	 <td class=title>>�Ѿ�¼��</td>
		 <td class=input><input value="0" class="common" id="EditPeoples" style="width=60" readonly >����������Ϣ��</td>
		</tr>
		<tr class=common>
     	 <td class=title>����������Ϊ</td>
		 <td class=input><input value="0" class="common" id="AmntRate" style="width=140" readonly >
     	</td>
    </tr>
    <tr>
     	<td class=common><input value="���ӱ�����" TYPE="button" class="cssButton" onclick="AddInsured()">
     	</td>
     	<td class=common><input value="ɾ��������" TYPE="button" class="cssButton" onclick="RemoveInsured()">
     	</td>
     	<td class=common><input value="���±�������Ϣ" TYPE="button" class="cssButton" onclick="UpdateInsured()">
     	</td>
    </tr>
    </table>
  	<div id="divLCInsuredGrid" style="display: ''">
      <table class="common">
        <tr class="common">
      	  <td text-align: left colSpan=1><span id="spanLCInsuredGrid"></span></td></tr>
      </table>
			
  	</div>
  	
  	</div>
  	
  
		<input type=hidden id="fmAction" name="fmAction">
	
  </Div>
  
  <!--ȷ�ϰ�ť-->
	<Div  id= "divConfirmButton" style= "display: ''">
      <table class= common>  
        <tr>
		<td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
</td>
         	<td class= titleImg>
            	����ȷ��
    	 	</td>  
    	</tr>  
</table>	
<table class= common> 	
     	<TR  class= common>          
          <TD  class= input>
           <Input type=button class=cssButton name="confirmActivate" id=confirmActivate value="����ȷ��" onclick="submitForm();">
           <!--Input type=button class= common name="confirmActivate" value="������Ϣ��ѯ" onclick="RiskSubmitForm();"-->
          </TD>                                           
       </TR>
    </table>
	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


