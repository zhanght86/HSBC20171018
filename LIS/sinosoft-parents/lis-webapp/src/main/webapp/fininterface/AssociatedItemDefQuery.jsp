<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�AssociatedItemDefQuery.jsp
//�����ܣ���Ŀר����ѯ����
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
<SCRIPT src="./AssociatedItemDefQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./AssociatedItemDefQueryInit.jsp"%>

<script>
	var VersionNo = <%=request.getParameter("VersionNo")%>;
	var VersionState = <%=request.getParameter("VersionState")%>;
</script>

<title>��Ŀר���</title>
</head>
<body onload="initForm();initElementtype();">
  <form  method=post name=fm target="fraSubmit">
  
  <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssociatedItemDef1);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </td>
    </tr>
  </table>
    <Div  id= "divAssociatedItemDef1" style= "display: ''" class="maxbox1">
      <table  class= common>

			<tr class= common>
				<TD class= title5>
		   	   ר����
		    </TD>
		    <TD class= input5>
		  		<Input class="wid" class=common name=AssociatedID id=AssociatedID >
		    </TD>
				<TD class= title5>
					 ר������
				</TD>
				<TD class=input5>
					<Input class="wid" class=common name=AssociatedName id=AssociatedName >
				</TD>		
			</tr>
			   
			<tr class= common>
				<TD class= title5>
		   	   ר����ֶα�ʶ
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ColumnID id=ColumnID verify="ר����ֶα�ʶ|NOTNULL" ondblClick="showCodeList('columnid',[this,columnidName],[0,1]);" onMouseDown="showCodeList('columnid',[this,columnidName],[0,1]);" onkeyup="showCodeListKey('columnid',[this,columnidName],[0,1]);"><input class=codename name=columnidName id=columnidName readonly=true >
		    </TD>
        <!--TD  class= title>
          ����������Դ����
        </TD>
        <TD  class= input>
        	<Input class=codeno name=SourceTableID verify="����������Դ����|NOTNULL" CodeData="0|^FIAboriginalData|ƾ֤ҵ�����ݱ�" ondblClick="showCodeListEx('SourceTableID',[this,SourceTableName],[0,1]);" onkeyup="showCodeListKeyEx('SourceTableID',[this,SourceTableName],[0,1]);"><input class=codename name=SourceTableName readonly=true >
        </TD-->	
				<TD class= title5>
		   	   ����������Դ�ֶ�
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=SourceColumnID id=SourceColumnID verify="����������Դ�ֶ�|NOTNULL" ondblClick="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1]);" onMouseDown="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1]);" onkeyup="showCodeListKey('sourcecolumnid',[this,sourcecolumnName],[0,1]);"><input class=codename name=sourcecolumnName id=sourcecolumnName readonly=true >
		    </TD>                	    		    
			</tr>
			
			<tr class= common>		    				
        <TD  class= title5>
          ת����־
        </TD>
        <TD  class= input5>
        	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=TransFlag id=TransFlag verify="ת����־|NOTNULL" CodeData="0|^N|��ת��^S|SQLת��^C|����ת��" ondblClick="showCodeListEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('TransFlag',[this,TransFlagName],[0,1],null,null,null,[1]);"><input class=codename name=TransFlagName id=TransFlagName readonly=true>           
        </TD>    					
			</tr>				
							

      </table>
      </div>	
          <!--<INPUT VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();" class="cssButton">-->
           <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a> 
     
          
          
    <table>    	
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssociatedItemDef2);">
    		</td>
    		<td class= titleImg>
    			 ��Ŀר����ѯ���
    		</td>
    	</tr>
    </table>

    
  	<Div  id= "divAssociatedItemDef2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					 <span id="spanAssociatedItemDefGrid" >
  					 </span>
  			  	</td>
  			</tr>
    	</table>
       <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
      <!--<INPUT VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton">
      <INPUT VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton">-->
  	</div>  

    	<INPUT type=hidden name=VersionNo value=''> 
    	<INPUT type=hidden name=SourceTableID value=''> 

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

