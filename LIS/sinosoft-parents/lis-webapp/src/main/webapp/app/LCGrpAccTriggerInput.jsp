<%
//�������ƣ�LCGrpAccTriggerInput.jsp
//�����ܣ�
//�������ڣ�2006-07-05
//������  ��chenrong
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">   
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="LCGrpAccTrigger.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>                        
    <%@include file="LCGrpAccTriggerInit.jsp"%>
    <title>���������˻������� </title>
 </head>
 <!--body onload="initForm();initElementtype();"-->
 <body onload="initForm();">         
   <form action="LCGrpAccTriggerSave.jsp" name=fm id="fm" method=post target="fraSubmit">           
    <table>
       <tr>
         <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
         <td class=titleImg>��ͬ��Ϣ</td>
       </tr>
    </table>   
    <div class="maxbox1">
    <div  id= "divFCDay" style= "display: ''">           
    <table class=common align=center>
      <tr class=common>
         <td class=title5>�����ͬ�� </td>
         <td class=input5><input name=GrpContNo id="GrpContNo" class="readonly wid" readonly ></td>        
         <Input type=hidden name=ProposalGrpContNo id="ProposalGrpContNo">
         <td class=title5>�������</td>
         <td class=input5><input name=ManageCom id="ManageCom" class="readonly wid" readonly ></td>
      </tr>
       <tr class=common>
          <td class=title5>Ͷ����λ�ܻ��� </td>
          <td class=input5><input name=AppntNo id="AppntNo" class="readonly wid" readonly ></td>
          <td class=title5>Ͷ����λ����</td>
          <td class=input5><input name=GrpName id="GrpName" class="readonly wid"  readonly ></td>        
       </tr>   
       <tr> 
       	  <td class=title5>���ֱ��� </td>     
          <td class=input5>
            <input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code"  name="RiskCode" id="RiskCode" elementtype=nacessary  onclick="return showCodeList('GrpRisk',[this],null,null,fm.GrpContNo.value,'b.GrpContNo',1);" ondblclick="return showCodeList('GrpRisk',[this],null,null,fm.GrpContNo.value,'b.GrpContNo',1);"  
              onkeyup="return showCodeListKey('GrpRisk',[this],null,null,fm.GrpContNo.value,'b.GrpContNo',1); " verify="���ֱ���|notnull" >
            <input type=hidden name="GrpPolNo" id="GrpPolNo"> </td>	                         
       </tr>  
    </table>
    </div> 
    </div>          
    <table>
      <tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAccTrigger);">
        </td>
        <td class=titleImg>�������䷽ʽѡ��</td>
      </tr>
    </table>
    <Div id="divAccTrigger" style="display:''">        
           <table class=common>
               <tr class=common>                  
                 <td text-align:left colSpan=1>                    
                   <span id="spanAccTriggerGrid">                
                   </span>
                 </td>
               </tr>                  
            </table>
    </Div>
    <a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>
    <!-- <INPUT VALUE="��һ��" class =cssButton  TYPE=button onclick="returnparent();"> -->
    <div id="divTiggerSave" style="display: ''" align=right>     
      <a href="javascript:void(0)" class=button onclick="submitForm();">��������</a>  
       <!-- <input type=button class=cssButton value="��������" onclick="submitForm();">     -->
    </div>
    <input type=hidden name="mOperate" id="mOperate">
    <input type=hidden name="LoadFlag" id="LoadFlag">
    <input type=hidden name="GrpContNoIn" id="GrpContNoIn">
   </form>
   <br>
   <br>
   <br>
   <br>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 </body>
</html>
