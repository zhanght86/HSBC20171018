<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�FinItemDefQuery.jsp
//�����ܣ���Ŀ���Ͷ����ѯ����
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
<SCRIPT src="./FinItemDefQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./FinItemDefQueryInit.jsp"%>

<script>
	var VersionNo = <%=request.getParameter("VersionNo")%>;
	var VersionState = <%=request.getParameter("VersionState")%>;
</script>

<title>��Ŀ���Ͷ���</title>
</head>
<body onload="initForm();initElementtype();">
  <form  method=post name=fm target="fraSubmit">
  
  <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIFinItemDef1);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </td>
    </tr>
  </table>
    <Div  id= "divFIFinItemDef1" style= "display: ''" class="maxbox1">
      <table  class= common>

			<tr class= common>
				<TD class= title5>
					  ��Ŀ���
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=FinItemID id=FinItemID >
				</TD>
				<TD class= title5>
		   	   ��Ŀ����
		    </TD>
		    <TD class= input5>
		  		 <Input class="wid" class=common name=FinItemName id=FinItemName >
		    </TD>				
			</tr>
			   
			<tr class= common>
				<TD class= title5>
		   	   ��Ŀ����
		    </TD>
		    <TD class= input5>
		    	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= FinItemType id= FinItemType verify="��Ŀ����|NOTNULL" CodeData="0|^1|�ʲ�^2|��ծ^6|����" ondblClick="showCodeListEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('FinItemType',[this,FinItemTypeMame],[0,1],null,null,null,[1]);"><input class=codename name=FinItemTypeMame id=FinItemTypeMame readonly=true >		  		 
		    </TD>
				<TD class= title5>
					  ��Ŀ���루һ����
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=ItemMainCode id=ItemMainCode >
				</TD>		    		    
			</tr>
			
			<tr class= common>
				<!--TD class= title>
		   	   ��Ŀ����ʽ
		    </TD>
		    <TD class= input>		    	
		  		<Input class=codeno name= DealMode verify="��Ŀ����ʽ|NOTNULL" CodeData="0|^1|��������^2|���⴦��" ondblClick="showCodeListEx('DealMode',[this,DealMame],[0,1]);" onkeyup="showCodeListKeyEx('DealMode',[this,DealMame],[0,1]);"><input class=codename name=DealMame readonly=true >
		    </TD-->		    		    
			</tr>			

      </table>
      </div>	
          <!--<INPUT VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();" class="cssButton">-->
         <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
     
          
          
    <table>    	
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIFinItemDef2);">
    		</td>
    		<td class= titleImg>
    			 ��Ŀ���Ͷ����ѯ���
    		</td>
    	</tr>
    </table>

    
  	<Div  id= "divFIFinItemDef2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					 <span id="spanFinItemDefGrid" >
  					 </span>
  			  	</td>
  			</tr>
    	</table>
        <!--<div align="left"><INPUT VALUE="��  ��" TYPE=button onclick="returnParent();" class="cssButton"></div>-->
         <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
        <br>
     <!-- <INPUT VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton93">-->
  	</div>  

    	<INPUT type=hidden name=VersionNo value=''>
    	<INPUT type=hidden name=DealMode value=''>    	

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>

