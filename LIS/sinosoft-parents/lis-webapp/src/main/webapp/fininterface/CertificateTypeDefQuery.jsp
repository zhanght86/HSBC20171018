<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�CertificateTypeDefQuery.jsp
//�����ܣ�ƾ֤���Ͳ�ѯ����
//�������ڣ�2008-08-11
//������  ��ZhongYan
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./CertificateTypeDefQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./CertificateTypeDefQueryInit.jsp"%>

<script>
	var VersionNo = <%=request.getParameter("VersionNo")%>;
	var VersionState = <%=request.getParameter("VersionState")%>;
</script>

<title>ƾ֤���Ͷ���</title>
</head>
<body onload="initForm();initElementtype();">
  <form  method=post name=fm id=fm target="fraSubmit">
  
  <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFICertTypeDef1);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </td>
    </tr>
  </table>
    <Div  id= "divFICertTypeDef1" style= "display: ''" class="maxbox1">
      <table  class= common>

			<tr class= common>
				<TD class= title5>
		   	   ƾ֤���ͱ��
		    </TD>
		    <TD class= input5>
		  		<Input class="wid" class=common name=CertificateID  id=CertificateID>
		    </TD>
				<TD class= title5>
					  ƾ֤��������
				</TD>
				<TD class=input5>
					<Input class="wid" class=common name=CertificateName id=CertificateName >
				</TD>				
			</tr>
			   
			<tr class= common>
				<TD class= title5>
		   	   ��ϸ��������
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DetailIndexID id=DetailIndexID verify="��ϸ��������|NOTNULL" ondblclick="return showCodeList('DetailIndexID',[this,DetailIndexName],[0,1],null,'detailindexid','codetype');" onMouseDown="return showCodeList('DetailIndexID',[this,DetailIndexName],[0,1],null,'detailindexid','codetype');" onkeyup="return showCodeListKey('DetailIndexID',[this,DetailIndexName],[0,1],null,'detailindexid','codetype');"><input class=codename name=DetailIndexName id=DetailIndexName readonly=true elementtype=nacessary> 
		    </TD>		
		    <!--TD class= title>
		   	   ���ݲɼ�����
		    </TD>
		    <TD class= input>
		  		<Input class=codeno name=AcquisitionType verify="���ݲɼ�����|NOTNULL" CodeData="0|^01|ֱ��ƾ֤ҵ������^02|���ƾ֤ҵ������" ondblClick="showCodeListEx('AcquisitionType',[this,AcquisitionTypeMame],[0,1]);" onkeyup="showCodeListKeyEx('AcquisitionType',[this,AcquisitionTypeMame],[0,1]);"><input class=codename name=AcquisitionTypeMame readonly=true >
		    </TD-->	
				<TD class= title5>
		   	   ������Դ
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AcquisitionType id=AcquisitionType verify="������Դ|NOTNULL"  CodeData="0|^01|ֱ������^02|���δ�������" ondblClick="showCodeListEx('AcquisitionType',[this,AcquisitionTypeMame],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('AcquisitionType',[this,AcquisitionTypeMame],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('AcquisitionType',[this,AcquisitionTypeMame],[0,1],null,null,null,[1]);"><input class=codename name=AcquisitionTypeMame id=AcquisitionTypeMame readonly=true >
		    </TD>			    		 		    
			</tr>
			
			<tr class= common>		    
			</tr>			

      </table>
      </div>	
          <!--<INPUT VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();" class="cssButton">-->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
     
          
          
    <table>    	
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFICertTypeDef2);">
    		</td>
    		<td class= titleImg>
    			 ƾ֤���Ͷ����ѯ���
    		</td>
    	</tr>
    </table>

    
  	<Div  id= "divFICertTypeDef2" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					 <span id="spanCertTypeDefGrid" >
  					 </span>
  			  	</td>
  			</tr>
    	</table>
        
      <!--<INPUT VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton">
      <INPUT VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton">-->
  	</div>  

    	<INPUT type=hidden name=VersionNo value=''> 
<a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

