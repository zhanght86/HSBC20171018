<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-16 17:44:40
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="ComInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="ComInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./ComSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    <%@include file="../common/jsp/InputButton.jsp"%>


    <table>
    	<tr>
    		<td class="common">
    		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCom1);">
    		</td>
    		 <td class= titleImg>
        		���������Ϣ
       		 </td>
    	</tr>
    </table>
    <Div  id= "divCom1" style= "display: ''" class="maxbox">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            �����������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ComCode id=ComCode elementtype=nacessary verify="�����������|notnull">
           
          </TD>
          </TD>
          <TD  class= title5>
            ���⹫���Ļ�������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=OutComCode id=OutComCode elementtype=nacessary verify="���⹫���Ļ�������|notnull">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            �����������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Name id=Name elementtype=nacessary verify="�����������|notnull">
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
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComGrade id=ComGrade verify="����ṹ����|notnull" ondblclick="return showCodeList('comlevel',[this,ComGradeName],[0,1]);" onclick="return showCodeList('comlevel',[this,ComGradeName],[0,1]);" onkeyup="return showCodeListKey('comlevel',[this,ComGradeName],[0,1]);" ><input name=ComGradeName id=ComGradeName class="codename" elementtype=nacessary readonly=true> 
          </TD>        	
          <TD  class= title5>
          	�ϼ�����
          </TD>
          <TD  class= input5>
          	<Input class="wid" class=common name=UpComCode id=UpComCode  verify="�ϼ�����|notnull" elementtype=nacessary>
          	 <!--<input style="height:20px" type=button class="cssButton" value="ѡ��" onClick="SelectCom();">-->
             <a href="javascript:void(0);" style="height:20px" class="button" onClick="SelectCom();">ѡ    ��</a>
					</TD>
        </TR>
        <TR  class= common>
        	<TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
            <!--Input class=codeno name=ComAreaType verify="�������|notnull&code:comareatype" ondblclick="return showCodeList('comareatype',[this,ComAreaTypeName],[0,1]);" onkeyup="return showCodeListKey('comareatype',[this,ComAreaTypeName],[0,1]);" ><input name=ComAreaTypeName class="codename" elementtype=nacessary readonly=true--> 
           <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComCitySize id=ComCitySize verify="�������|notnull&code:comcitysize" ondblclick="return showCodeList('comcitysize',[this,ComCitySizeName],[0,1]);" onclick="return showCodeList('comcitysize',[this,ComCitySizeName],[0,1]);" onkeyup="return showCodeListKey('comcitysize',[this,ComCitySizeName],[0,1]);" ><input name=ComCitySizeName id=ComCitySizeName class="codename" elementtype=nacessary readonly=true> 
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
            <Input class="wid" class=common name=EMail id=EMail >
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
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsDirUnder id=IsDirUnder verify="ֱ������|notnull&code:comdirectattr" ondblclick="return showCodeList('comdirectattr',[this,IsDirUnderName],[0,1]);" onclick="return showCodeList('comdirectattr',[this,IsDirUnderName],[0,1]);" onkeyup="return showCodeListKey('comdirectattr',[this,IsDirUnderName],[0,1]);" ><input name=IsDirUnderName id=IsDirUnderName class="codename" elementtype=nacessary readonly=true> 
          </TD>
        </TR>
      </table>
    </Div>
    <input type=hidden id="fmtransact" name="fmtransact"><br>
    <%@include file="../common/jsp/OperateAgentButton.jsp"%>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
