<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");	  
	String tClmNo = request.getParameter("ClaimNo"); //�ⰸ��
	String tBatNo= request.getParameter("BatNo");	//���κ�
	String tInqDept=request.getParameter("InqDept");	//�������
	String tType = request.getParameter("Type");	//���ܱ�־ 0--�ⰸ�㣬2--������ 
%>
    <title>������Ϣ��ѯ</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
    <script src="./LLInqConclusionQuery.js"></script> 
    <%@include file="LLInqConclusionQueryInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>

    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusionGrid);"></TD>
            <TD class= titleImg> ���������Ϣ </TD>
        </TR>
    </Table>    
    <Div  id= "divLLInqConclusionGrid" style= "display: ''">
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqConclusionGrid" ></span> </TD>
             </TR>
         </Table>
        <!--<table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table> -->    
    </Div>
    <Div  id= "divLLInqConclusionForm" style= "display: ''">    
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqConclusionForm);"></TD>
                <TD class= titleImg> ���������ϸ��Ϣ </TD>
            </TR>
        </Table>
        
         <Table class=common>
             <tr class=common>
                 <td class=title> ������ </td>
                 <td class= input><Input class= "readonly wid" readonly name="ClmNo2" id="ClmNo2"></td>
                 <td class=title> ������� </td>
                 <td class= input><Input class= "readonly wid" readonly name="ConNo" id="ConNo"></td>
                 <td class=title> ������� </td>
                 <td class= input><Input class= "readonly wid" readonly name="InqDept" id="InqDept"></td>                                   
             </tr>        
         </Table>
         <Table class= common>        
             <tr class= common>
                 <td class= title> ������� </td>
             </tr> 
             <tr class= common>       
                 <td colspan="6" style="padding-left:16px"> <textarea name="InqConclusion" id="InqConclusion" cols="226%" rows="4" witdh=25% class="common" readonly ></textarea></td>
             </tr>
             <tr class= common>
                 <td class= title> ��ע </td>
             </tr> 
             <tr class= common>       
                 <td colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="226%" rows="4" witdh=25% class="common" readonly ></textarea></td>
             </tr>       
         </Table>  
    </div>

     <Div  id= "divLLInqApplyGrid" style= "display: ''">
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyGrid);"></TD>
                <TD class= titleImg> ����������Ϣ </TD>
            </TR>
         </Table>
          <Table class= common>
               <TR class= common>
                    <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
               </TR>
          </Table>
	      <!--<table> 
	        <tr>  
	            <td><INPUT class=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
	            <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
	            <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
	            <td><INPUT class=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></td>
	        </tr> 
	      </table> -->    
     </Div>

     <Div  id= "divLLInqApplyForm" style= "display: ''">    
         <Table>
              <TR>
                   <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyForm);"></TD>
                   <TD class= titleImg> ����������ϸ��Ϣ </TD>
              </TR>
         </Table>
         
          <Table class=common>
               <tr class=common>
                    <td class=title> ������ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="ClmNo3" id="ClmNo3"></td>
                    <td class=title> ������� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="InqNo" id="InqNo"></td>
                    <td class=title> �����˿ͻ��� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="CustomerNo" id="CustomerNo"></td>                                   
               </tr>     
          </Table>    
          <Table class=common> 
              <tr class= common>
                   <td class= title> ������Ŀ </td>
              </tr> 
              <tr class= common>       
                  <td colspan="6" style="padding-left:16px"> <textarea name="InqItem" id="InqItem" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></td>
              </tr>
              <tr class= common>
                  <td class= title> �������� </td>
              </tr> 
              <tr class= common>       
                  <td colspan="6" style="padding-left:16px"> <textarea name="InqDesc" id="InqDesc" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></td>
              </tr>       
          </Table>  

     </div>
     

        <Div  id= "divLLInqCourseGrid" style= "display: ''">
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqCourseGrid);"></TD>
                <TD class= titleImg> ���������Ϣ </TD>
            </TR>
        </Table>
             <Table  class= common>
                  <TR  class= common>
                       <TD text-align: left colSpan=1><span id="spanLLInqCourseGrid" ></span> </TD>
                   </TR>
             </Table>
        </Table>
        <!--table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table--> 
        </Div>

        <Div  id= "divLLInqCourseForm" style= "display: ''">    
            <Table>
                 <TR>
                     <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqCourseForm);"></TD>
                     <TD class= titleImg> ���������ϸ��Ϣ </TD>
                 </TR>
            </Table>
           
            <Table class=common>
                <tr class=common>
                    <td class=title> ������ </td>
                    <td class= input><Input class= "readonly wid" readonly name="ClmNo4" id="ClmNo4"></td>
                    <td class=title> ������� </td>
                    <td class= input><Input class= "readonly wid" readonly name="InqNo2" id="InqNo2"></td>
                    <td class=title> ������� </td>
                    <td class= input><Input class= "readonly wid" readonly name="CouNo" id="CouNo"></td>                                   
                </tr>   
            </Table>    
            <Table class=common>              
                <tr class= common>
                    <td class= title> ����ص� </td>
                </tr> 
                <tr class= common>       
                    <td colspan="6" style="padding-left:16px"> <textarea name="InqSite" id="InqSite" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></td>
                </tr>
                <tr class= common>
                    <td class= title> ������� </td>
                </tr> 
                <tr class= common>       
                    <td colspan="6" style="padding-left:16px"> <textarea name="InqCourse" id="InqCourse" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></td>
                </tr>       
            </Table>  
    </div>   
    <Input class=cssButton value=" �� �� " type=button onclick=top.close() align= center>
    <!--�������ݵ����ر�-->
    <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->    
    <Input type=hidden id="tClmNo" name="tClmNo"><!--�ⰸ��-->  
    <Input type=hidden id="tBatNo" name="tBatNo"><!--���κ�-->  
    <Input type=hidden id="tInqDept" name="tInqDept"><!--�������-->  
    <Input type=hidden id="tType" name="tType"><!--��־-->
    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>  <br><br><br><br>        
</Form>
</body>
</html>
