<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ComQueryInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:45
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./ComQuery.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./ComQueryInit.jsp"%>
<title>������Ϣ </title>
</head>
<body  onload="initForm();" >
  <form action="./ComQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComGrid1);">
      </td>
      <td class= titleImg>
        ���������ѯ������
      </td>
    	</tr>
    </table>
  <Div  id= "divComGrid1" style= "display: ''" class="maxbox">
  <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            �����������
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=ComCode id=ComCode ondblclick="return showCodeList('comcode',[this,ComCodeName],[0,1],null,null,null,1);" onclick="return showCodeList('comcode',[this,ComCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this,ComCodeName],[0,1],null,null,null,1);" ><input name=ComCodeName id=ComCodeName class="codename"  readonly=true> 

          </TD>
          <TD  class= title5>
            ���⹫���Ļ�������
          </TD>
          <TD  class= input5>
            <Input class="wid" class=common name=OutComCode id=OutComCode>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            �����������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Name id=Name>
          </TD>
          <TD  class= title5>
            ������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ShortName id=ShortName >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ����
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComGrade id=ComGrade ondblclick="return showCodeList('comlevel',[this,ComGradeName],[0,1]);" onclick="return showCodeList('comlevel',[this,ComGradeName],[0,1]);" onkeyup="return showCodeListKey('comlevel',[this,ComGradeName],[0,1]);" ><input name=ComGradeName id=ComGradeName class="codename" elementtype=nacessary readonly=true> 
          </TD>        	
          <TD  class= title5>
          	�ϼ�����
          </TD>
          <TD  class= input5>
          	<Input class="wid" class=common name=UpComCode id=UpComCode>
					</TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            �������������
          </TD>
          <TD  class= input5>
 					<!--<Input class="coolDatePicker" dateFormat="short" name=ConfirmDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#ConfirmDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ConfirmDate id="ConfirmDate"><span class="icon"><a onClick="laydate({elem: '#ConfirmDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				  </TD>        	
          <TD  class= title5>
          	��Ч����(��:200808)
          </TD>
          <TD  class= input5>
          	<Input class="wid" class=common name=YearMonth id=YearMonth >
					</TD>
        </TR>
         <TR  class= common>
          <TD  class= title5>
            ͣЧ����
          </TD>
          <TD  class= input5>
 					<!--<Input class="coolDatePicker" dateFormat="short" name=EndDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				  </TD>        	
          <TD  class= title5>
          	��Ч״̬
          </TD>
          <TD  class= input5>
            	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ValidFlag id=ValidFlag verify="��Ч״̬|notnull" CodeData="0| ^Y|��Ч ^N|��Ч  "  ondblclick="return showCodeListEx('validflag',[this,ValidFlagName],[0,1]);" onclick="return showCodeListEx('validflag',[this,ValidFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('validflag',[this,ValidFlagName],[0,1]);"><input class=codename name=ValidFlagName id=ValidFlagName readonly=true>
          </TD>
        </TR> 

        <TR  class= common>
        	<TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
            <!--<Input class=codeno name=ComAreaType ondblclick="return showCodeList('comareatype',[this,ComAreaTypeName],[0,1]);" onkeyup="return showCodeListKey('comareatype',[this,ComAreaTypeName],[0,1]);" ><input name=ComAreaTypeName class="codename" elementtype=nacessary readonly=true> --> 
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComCitySize id=ComCitySize ondblclick="return showCodeList('comcitysize',[this,ComCitySizeName],[0,1]);" onclick="return showCodeList('comcitysize',[this,ComCitySizeName],[0,1]);" onkeyup="return showCodeListKey('comcitysize',[this,ComCitySizeName],[0,1]);" ><input name=ComCitySizeName id=ComCitySizeName class="codename" elementtype=nacessary readonly=true> 
            <!--Input class= common name=ComCode -->
          </TD>
          <TD  class= title5>
            �����ʱ�
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ZipCode id=ZipCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ������ַ
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Address id=Address >
          </TD>
          <TD  class= title5>
            �����绰
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Phone id=Phone >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Fax id=Fax >
          </TD>
          <TD  class= title5>
            EMail
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=EMail id=EMail>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ��ַ
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=WebAddress id=WebAddress >
          </TD>
          <TD  class= title5>
            ����������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=SatrapName id=SatrapName >
          </TD>
        </TR>
      	<TR>
          <TD  class= title5>
            ��־
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Sign id=Sign >
          </TD>
          <TD  class= title5>
            ֱ������
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsDirUnder id=IsDirUnder verify="ֱ������|notnull&code:comdirectattr" ondblclick="return showCodeList('comdirectattr',[this,IsDirUnderName],[0,1]);" onclick="return showCodeList('comdirectattr',[this,IsDirUnderName],[0,1]);" onkeyup="return showCodeListKey('comdirectattr',[this,ComAreaTypeName],[0,1]);" ><input name=IsDirUnderName id=IsDirUnderName class="codename" readonly=true> 
          </TD>
        </TR>
      </table>
 </Div>
          <!--<INPUT VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();" class="cssButton">
          <INPUT VALUE="��  ��" TYPE=button onclick="returnParent();" class="cssButton">-->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
          
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComGrid);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ���
    		</td>
    	</tr>
    </table>
  	<Div  id= "divComGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanComGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        <center>
        <INPUT VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton93"></center>
  	</div><a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
