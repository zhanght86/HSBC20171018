<%@page contentType="text/html;charset=GBK" %>

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
//    loggerDebug("LLColQueryContDealInput",tCustNo);
//==========END	
%>      
    <title>��ͬ����</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <script src="./LLColQueryContDeal.js"></script>     
    <%@include file="LLColQueryContDealInit.jsp"%>

</head>

<body  onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
         
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
      
    
    <!--=========================================================================
        �ⰸ�漰�ĺ�ͬ
        =========================================================================
    -->    
     
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
    <Div  id= "divContDeal" style= "display:none">
                       
        <table  class= common>
            <tr class= common>          
                <TD  class= title>��ֹ����</TD>                       
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=StateReason id=StateReason  verify="��ͬ��ֹԸ��|code:llcontdealtype" onDblClick="return showCodeList('llcontdealtype',[this,StateReasonName],[0,1]);" onClick="return showCodeList('llcontdealtype',[this,StateReasonName],[0,1]);" onKeyUp="return showCodeListKey('llcontdealtype',[this,StateReasonName],[0,1]);"><input class=codename name=StateReasonName id=StateReasonName readonly=true></TD>                
               <!-- <td><INPUT class=cssButton name="saveClinicButton" VALUE="������۲�����"  TYPE=button onclick="saveAndCalClick()" ></td>-->
               <TD  class= title></TD> <TD  class= input></TD> <TD  class= title></TD> <TD  class= input></TD>
            </tr>
        </table>                                       
    </div>
      <a href="javascript:void(0);" name="saveClinicButton" class="button" onClick="saveAndCalClick();">������۲�����</a>          
 
    
    <!--=========================================================================
        �ⰸ�漰�ĺ�ͬ����
        =========================================================================
    -->     
                 
    <Div  id= "divContPolRefer" style= "display:none">
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
    </Div> </Div>
    
    <!--=========================================================================
        �Ժ�ͬ���ֽ��в���
        =========================================================================
    -->         
    <Div  id= "divContPolDeal" style= "display:none">
                       
        <table  class= common>
            <tr class= common>          
                <TD  class= title>��ֹ����</TD>                       
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=PolStateReason id=PolStateReason  verify="������ֹ|code:llcontpoldealtype" onDblClick="return showCodeList('llcontpoldealtype',[this,PolStateReasonName],[0,1]);" onClick="return showCodeList('llcontpoldealtype',[this,PolStateReasonName],[0,1]);" onKeyUp="return showCodeListKey('llcontdealtype',[this,PolStateReasonName],[0,1]);"><input class=codename name=PolStateReasonName id=PolStateReasonName readonly=true></TD> <TD  class= title></TD> <TD  class= input></TD> <TD  class= title></TD> <TD  class= input></TD>               
                <!--<td><INPUT class=cssButton name="saveClinicButton" VALUE="�������"  TYPE=button onclick="saveContPolClick()" ></td> -->               
            </tr>
        </table>                                       
    </div>
      <a href="javascript:void(0);" name="saveClinicButton" class="button" onClick="saveContPolClick();">�������</a>          
             
    <!--=========================================================================
        ��ͬ��ֹ��Ϣ
        =========================================================================
    -->         
    
    <Div  id= "divLLContState" style= "display:none">   
        <table>
            <tr><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cvv);"></td>
                <td class= titleImg> ��ͬ��ֹ��Ϣ </td>
            </tr>
        </table>   <Div  id= "cvv" style= "display: ''">                     
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanLLContStateGrid"></span>
            </td></tr>
        </Table>
    </div></div>
    
    <!--=========================================================================
        ��ͬ��ֹ����Ľ��
        =========================================================================
    -->         
    
    <Div  id= "divContCalResult" style= "display:none">   
        <table>
            <tr><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,asd);"></td>
                <td class= titleImg> �����ͬ������ </td>
            </tr>
        </table>  <Div  id= "asd" style= "display: ''">                         
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanContCalResultGrid"></span>
            </td></tr>
        </Table>
    </div></div>
    <!--=========================================================================
        ��ͬ��ֹ����Ľ�����
        =========================================================================
    -->         
    
    <Div  id= "divContCalResultAdjust" style= "display:none">   
        <table>
            <tr><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
                <td class= titleImg> ��ͬ��������� </td>
            </tr>
        </table>  <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">                      
        <Table  class= common>
            <TR  class= common>
                <TD  class= title> ����ǰ��� </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=OldPay id=OldPay ></TD>
                 <TD  class= title> �������� </TD>
                <TD  class= input> <Input class="wid" class= readonly readonly name=NewPay id=NewPay ></TD>
                <td><INPUT=hidden class=cssButton disabled name="saveClinicButton" VALUE="���������������"  TYPE=button onclick="saveContCalResultGridAdjust()" ></td>
            </TR>
           
         </Table>
         <Table  class= common>
                      
            <TR  class= common>                        
                <TD class= input>
                    <input=hidden name="NewAdjRemark" id="NewAdjRemark" cols="226" rows="4" witdh=25% class="common"></textarea>
                </TD>                                  
            </TR>          
            <TR  class= common>
                <TD  class= title> ����ԭ�� </TD>
            </tr>
            <TR  class= common>                        
                <TD colspan="6" style="padding-left:16px">
                    <textarea disabled name="OldAdjRemark" id="OldAdjRemark" cols="226" rows="4" witdh=25% class="common"></textarea>
                </TD>                        
            </TR>                     
        </Table>
    </div>   </div> 
    

</Div>         
    
	<input type=hidden name=ClmNo value=<%=tClmNo%>>      <!--�ⰸ��-->
	<input type=hidden name=CustNo value=<%=tCustNo%>>    <!--�ͻ���-->
	<input type=hidden name=ConType value=<%=tConType%>>  <!--��������1���գ�2����-->
    <input type=hidden name=ContNo value="">              <!--��ͬ��-->
    <input type=hidden name=PolNo value="">               <!--���ֺ�-->    
    <input type=hidden name=ContEndDate value="">         <!--��ͬ��ֹ����-->
    <input type=hidden name=FeeOperationType value="">    <!--ҵ������-->    
    <input type=hidden name=SubFeeOperationType value=""> <!--��ҵ������-->   
                
    <!--ϵͳ����-->
    <input type=hidden name=hideOperate value=''>         <!--�����Ĳ�������-->

    <!--
    <textarea name="memo" cols="100%" rows="10" witdh=50% class="common"></textarea>
    -->
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>          
</Form>
</body>
</html>
