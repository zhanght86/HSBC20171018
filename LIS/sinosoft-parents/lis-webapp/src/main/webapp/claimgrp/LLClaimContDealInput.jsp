<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
<html>
<head>
<%
//==========BGN
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI"); 
    String tClmNo = request.getParameter("ClmNo");           //�ⰸ��
    String tCustNo = request.getParameter("CustNo");         //�ͻ���
    String tConType = request.getParameter("ConType");       //��������1���գ�2����
//    loggerDebug("LLClaimContDealInput",tCustNo);
//==========END	
%>      
    <title>��ͬ����</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <script src="./LLClaimContDeal.js"></script>     
    <%@include file="LLClaimContDealInit.jsp"%>

</head>

<body  onload="initForm();">
<form name=fm target=fraSubmit method=post>
         
<Div  id= "divMain" style= "display:''">
                                          
    <!--=========================================================================
        �ⰸδ�漰�ĺ�ͬ
        =========================================================================
    -->      
    <table>
         <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ContNoRefer);"></td>
              <td class= titleImg> �ⰸδ�漰�ĺ�ͬ </td>
         </tr>
    </table>
                
    <Div  id= "ContNoRefer" style= "display:''">
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContNoReferGrid"></span>
            </td></tr>
        </Table>
    </Div>
    <hr>    
    
    <!--=========================================================================
        �ⰸ�漰�ĺ�ͬ
        =========================================================================
    -->    
    <INPUT class=cssButton name="saveContAutoClickButton" VALUE="��ͬ״̬�Զ�����"  TYPE=button onclick="saveContAutoClick()" >
     
    <table>
         <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,ContRefer);"></td>
              <td class= titleImg> �ⰸ�漰�ĺ�ͬ </td>
         </tr>
    </table>                   
    <Div  id= "ContRefer" style= "display:''">
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContReferGrid"></span>
            </td></tr>
        </Table>
    </Div>        
        
    <!--=========================================================================
        �Ժ�ͬ���в���
        =========================================================================
    -->         
    <Div  id= "divContDeal" style= "display:'none'">
                       
        <table  class= common>
            <tr class= common>          
                <TD  class= title>��ֹ����</TD>                       
                <TD  class= input> <Input class=codeno name=StateReason  verify="��ͬ��ֹԸ��|code:llcontdealtype" ondblclick="return showCodeList('llcontdealtype',[this,StateReasonName],[0,1]);" onkeyup="return showCodeListKey('llcontdealtype',[this,StateReasonName],[0,1]);"><input class=codename name=StateReasonName readonly=true></TD>                
                <td><INPUT class=cssButton name="saveClinicButton" VALUE="������۲�����"  TYPE=button onclick="saveAndCalClick()" ></td>
                
            </tr>
        </table>                                       
    </div>
                
 
    <hr> 
    <!--=========================================================================
        �ⰸ�漰�ĺ�ͬ����
        =========================================================================
    -->     
                   
    <Div  id= "divContPolRefer" style= "display:'none'">
	    <table>
	         <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContPolRefer);"></td>
	              <td class= titleImg> �ⰸ�漰������ </td>
	         </tr>
	    </table>    
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContPolReferGrid"></span>
            </td></tr>
        </Table>
    </Div>
    
    <!--=========================================================================
        �Ժ�ͬ���ֽ��в���
        =========================================================================
    -->         
    <Div  id= "divContPolDeal" style= "display:'none'">
                       
        <table  class= common>
            <tr class= common>          
                <TD  class= title>��ֹ����</TD>                       
                <TD  class= input> <Input class=codeno name=PolStateReason  verify="������ֹ|code:llcontpoldealtype" ondblclick="return showCodeList('llcontpoldealtype',[this,PolStateReasonName],[0,1]);" onkeyup="return showCodeListKey('llcontdealtype',[this,PolStateReasonName],[0,1]);"><input class=codename name=PolStateReasonName readonly=true></TD>                
                <td><INPUT class=cssButton name="saveClinicButton" VALUE="�������"  TYPE=button onclick="saveContPolClick()" ></td>                
            </tr>
        </table>                                       
    </div>
                
    <hr>
    
         
    <!--=========================================================================
        ��ͬ��ֹ��Ϣ
        =========================================================================
    -->         
    
    <Div  id= "divLLContState" style= "display:'none'">   
        <table>
            <tr><td class=common>
                <td class= titleImg> ��ͬ��ֹ��Ϣ </td>
            </tr>
        </table>                        
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanLLContStateGrid"></span>
            </td></tr>
        </Table>
    </div>
    
    <!--=========================================================================
        ��ͬ��ֹ����Ľ��
        =========================================================================
    -->         
    
    <Div  id= "divContCalResult" style= "display:'none'">   
        <table>
            <tr><td class=common>
                <td class= titleImg> �����ͬ������ </td>
            </tr>
        </table>                        
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContCalResultGrid"></span>
            </td></tr>
        </Table>
    </div>
    
    

</Div>         
    
	<input type=hidden name=ClmNo value=<%=tClmNo%>>      <!--�ⰸ��-->
	<input type=hidden name=CustNo value=<%=tCustNo%>>    <!--�ͻ���-->
	<input type=hidden name=ConType value=<%=tConType%>>  <!--��������1���գ�2����-->
    <input type=hidden name=ContNo value="">              <!--��ͬ��-->
    <input type=hidden name=PolNo value="">               <!--���ֺ�-->    
    <input type=hidden name=ContEndDate value="">         <!--��ͬ��ֹ����-->
            
    <!--ϵͳ����-->
    <input type=hidden name=hideOperate value=''>         <!--�����Ĳ�������-->

    <!--
    <textarea name="memo" cols="100%" rows="10" witdh=50% class="common"></textarea>
    -->
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
