<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�VersionRuleQueryForOther.jsp
//�����ܣ��汾��Ϣ��ѯҳ��
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
<SCRIPT src="./VersionRuleQueryForOther.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./VersionRuleQueryForOtherInit.jsp"%>

<title>�汾��Ϣ��ѯ</title>
</head>
<body onload="initForm();initElementtype();">
  <form  method=post name=fm id=fm target="fraSubmit">
  
  <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulesVersion1);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </td>
    </tr>
  </table>
    <Div  id= "divFIRulesVersion1" style= "display: ''">
    <div class="maxbox">
  <table  class= common>
		<tr class= common>
         <TD class= title5>
					  �汾���
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=VersionNo id=VersionNo >
				 </TD>
         <TD  class= title5>
          ��������
         </TD>
         <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=AppDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#AppDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AppDate id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
         </TD>          
		</tr>
				
		<tr class= common>
         <TD  class= title5>
          ��Ч����
         </TD>
         <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=StartDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
         </TD>     
         <TD  class= title5>
          ʧЧ����
         </TD>
         <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=EndDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
         </TD>          
		</tr>		
		
		<tr class= common>
				 <TD class= title5>
					  �汾״̬
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name= VersionState id= VersionState verify="�汾״̬|notnull" CodeData="0|^01|����^02|ά��^03|ɾ��" onMouseDown="return showCodeListEx('VersionState',[this,VersionStateName],[0,1],null,null,null,[1]);"  ondblclick="return showCodeListEx('VersionState',[this,VersionStateName],[0,1],null,null,null,[1]);" onkeyup="return showCodeListKeyEx('VersionState',[this,VersionStateName],[0,1],null,null,null,[1]);"  ><input class=codename name=VersionStateName id=VersionStateName readonly=true></TD>  
				</TD>
		</tr>

      </table>
          
         </div> 
     </div>	
     <!-- <INPUT VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();" class="cssButton"> -->   
      <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>    
   <table>    	
      <tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulesVersion2);">
    		</td>
    		<td class= titleImg>
    			 �汾��Ϣ��ѯ���
    		</td>
    	</tr>
   </table>

    
  	<Div  id= "divFIRulesVersion2" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					 <span id="spanRulesVersionGrid" >
  					 </span>
  			  	</td>
  			</tr>
    	</table>
      
      <!--<INPUT VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton93">-->
  	</div>  
<!-- <INPUT VALUE="��  ��" TYPE=button onclick="returnParent();" class="cssButton">-->
 <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

