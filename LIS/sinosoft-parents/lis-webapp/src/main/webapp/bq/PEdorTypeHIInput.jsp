<html> 
<% 
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: ����������֪
 ******************************************************************************/
%>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeHI.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeHIInit.jsp"%>
  
  <title>����������֪ </title> 
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeHISubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <div class=maxbox1>
 <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > ��ȫ�����</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title >��������
      </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
      <TD class = title > �ͻ����� </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=CustomerNo id=CustomerNo>
      </TD>   
    </TR>
    <TR class=common>
    	<TD class =title>������������</TD>
    	<TD class = input>    		
    		<Input class= "coolDatePicker" readonly name=EdorAppDate onClick="laydate({elem: '#EdorAppDate'});" id="EdorAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<Input class= "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
  </TABLE>
  </div>
  <!--����������֪�б���Ϣ-->  

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCusBaseGrid);">
            </td>
            <td class= titleImg>
                �ͻ�������Ϣ
            </td>
        </tr>
    </table>
    <Div  id= "divCusBaseGrid" class=maxbox1 style= "display: ''">
        <table  class= common>
        <TR  class=common>        
          <TD  class= title>����
          </TD>
          <TD  class=input >
            <Input class="common wid" readonly="readonly" name=Name id=Name >
          </TD>
          <TD  class= title>�Ա�
          </TD>
          <TD  class= input>
            <Input class="codeno" readonly="readonly" name=Sex id=Sex ><input class=codename name=SexName id=SexName readonly=true >
          </TD>
          <TD  class= title>����״��
          </TD>
          <TD nowrap class= input>
            <Input class="codeno" readonly="readonly" name="Marriage" id=Marriage ><input class=codename name=MarriageName id=MarriageName readonly=true >
          </TD>          
        </TR>
        
        <TR  class= common>
          <TD  class= title>��������
          </TD>
          <TD  class= input>
            <input class="coolDatePicker" readonly="readonly" name="Birthday" onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>֤������
          </TD>
          <TD  class= input>
            <Input class="codeno" readonly="readonly" name="IDType" id=IDType ><input class=codename name=IDTypeName id=IDTypeName readonly=true >
          </TD>
          <TD  class= title>֤������
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly="readonly" name="IDNo" id=IDNo >
          </TD>           
        </TR>            
        </table>					
    </div>  
    
<Div  id= "divLCContInfo" style= "display:">
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContInfo);">
      </td>
      <td class= titleImg>
        ��ر�����Ϣ
      </td>
   </tr>
   </table>
   <Div  id= "divContInfo" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanContGrid" >
                    </span>
                </td>
            </tr>
        </table>
</div>
</div>
   <!--Ͷ����/��������ϸ��Ϣ-->
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      </td>
      <td class= titleImg>
        ԭ�ͻ���֪��Ϣ
      </td>
   </tr>
   </table>
   
  <Div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanImpartGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </div>
    <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNewLCImpart1);">
      </td>
      <td class= titleImg>
        �¿ͻ���֪��Ϣ
      </td>
   </tr>
   </table>
   
  <Div  id= "divNewLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanNewImpartGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </div>   
    <Div id = "divEdorquery" style="display: ''">
	  <table class = common>	
	         <Input class= cssButton type=Button value=" ��  �� " onclick="edorTypeHISave()">	     	 
	       	 <Input class= cssButton type=Button value=" ��  �� " onclick="returnParent()">
	       	 <input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">
     </table>
    </Div>
    <Div  id= "divPayPassNotice" style= "display: ''">
            <br>
            <INPUT VALUE=" ��ӡ��֪�� " class=cssButton TYPE=button onclick="InfoNoticePrint();">
    </Div>  
	  
	 <input type=hidden id="CustomerRole" name = "CustomerRole">
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" value= 1 name="ContType">
	 <input type=hidden id="EdorNo" name="EdorNo">
	 	 <input type=hidden id="ContNo" name="ContNo">
	 	 	 <input type=hidden id="PrtSeq" name="PrtSeq">	 
	 
  </form>
  <Br /> <Br /> <Br /> <Br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
