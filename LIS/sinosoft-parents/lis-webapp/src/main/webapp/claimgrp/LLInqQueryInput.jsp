<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
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
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT> 
    <script src="./LLInqQuery.js"></script> 
    <%@include file="LLInqQueryInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>
	
	<!--����ӵĽ��棬by niuzj 20050829--> 
    <Div  id= "DivLLInqPayclusionGrid" style= "display: ''">
    	<Table>
	        <TR>
	            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"OnClick= "showPage(this,ass);" ></TD>
	            <TD class= titleImg> �ⰸ������Ϣ </TD>
	        </TR>
    	</Table>  <Div  id= "ass" style= "display: ''" > 
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqPayclusionGrid" ></span> </TD>
             </TR>
         </Table>
        <!--<table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table>    --> 
    </Div></Div>
       
        <Div  id= "DivLLInqPayclusionForm" style= "display: ''"> 
			<Table>
				<TR>
				    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cvv);"></td>
				    <TD class= titleImg> �ⰸ������ϸ��Ϣ </TD>
				</TR>
			</Table><Div  id= "cvv" style= "display: ''" class="maxbox1">
			 <Table class=common>
			     <tr class=common>
			         <td class=title> ������ </td>
			         <td class= input><Input class= "readonly wid" readonly name="ClmNo1" id="ClmNo1"></td>
			         <td class=title> ������� </td>
			         <td class= input><Input class= "readonly wid" readonly name="ConNo1" id="ConNo1"></td>
			         <td class=title> ������� </td>
			         <td class= input><Input class= "readonly wid" readonly name="InqDept1" id="InqDept1"></td>                                   
			     </tr>        
			 </Table>
	         <Table class= common>        
	             <tr class= common>
	                 <td class= title> ������� </td>
	             </tr> 
	             <tr class= common>       
	                 <td colspan="6" style="padding-left:16px"> <textarea name="InqConclusion1" cols="230%" rows="4" witdh=25% class="common" readonly ></textarea></td>
	             </tr>
	             <tr class= common>
	                 <td class= title> ��ע </td>
	             </tr> 
	             <tr class= common>       
	                 <td colspan="6" style="padding-left:16px"> <textarea name="Remark1" cols="230%" rows="4" witdh=25% class="common" readonly ></textarea></td>
	             </tr>       
	         </Table> </Div>
         
    </Div>

	<!--ԭ���Ľ���-->

     
    <Div  id= "DivLLInqConclusionGrid" style= "display: ''">
    	<Table>
	        <TR>
	            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"  OnClick= "showPage(this,zxc);" ></TD>
	            <TD class= titleImg> �������������Ϣ </TD>
	        </TR>
    	</Table> <Div  id= "zxc" style= "display: ''">  
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqConclusionGrid" ></span> </TD>
             </TR>
         </Table></Div>
       <!-- <table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table>  -->   
    </Div>
    <Div  id= "DivLLInqConclusionForm" style= "display: ''">    
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ;"  OnClick= "showPage(this,xcv);"></TD>
                <TD class= titleImg> �������������ϸ��Ϣ </TD>
            </TR>
        </Table><Div  id= "xcv" style= "display: ''" class="maxbox1">
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
    </Div>

     <Div  id= "DivLLInqApplyGrid" style= "display: ''">
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,vbn);" ></TD>
                <TD class= titleImg> ����������Ϣ </TD>
            </TR>
         </Table><Div  id= "vbn" style= "display: ''">
          <Table class= common>
               <TR class= common>
                    <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
               </TR>
          </Table></div>
	      <!--<table> 
	        <tr>  
	            <td><INPUT class=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
	            <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
	            <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
	            <td><INPUT class=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></td>
	        </tr> 
	      </table>    --> 
     </Div>

     <Div  id= "DivLLInqApplyForm" style= "display: ''">    
         <Table>
              <TR>
                   <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"  OnClick= "showPage(this,ert);" ></TD>
                   <TD class= titleImg> ����������ϸ��Ϣ </TD>
              </TR>
         </Table><Div  id= "ert" style= "display: ''" class="maxbox1">
          <Table class=common>
               <tr class=common>
                    <td class=title> ������ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="ClmNo3" id="ClmNo3"></td>
                    <td class=title> ������� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="InqNo" id="InqNo"></td>
                    <td class=title> �����˱��� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name="customerNo" id="customerNo"></td>                                   
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
          </Table> </div> 
          <Table>
              <tr>  
          	      <td><input class=cssButton value="�鿴���������ϸ"  TYPE=button onclick="InqCourseQueryClick()";"></td>
          	      <td><input class=cssButton value="�鿴���������ϸ"  TYPE=button onclick="QueryInqFeeClick()";"></td>
          	      <td><input class=cssButton value="��ӡ��������֪ͨ��"  TYPE=button onclick="PRTInteInqTask()";"></td>
          	      <td><input class=cssButton value=" ��ӡ������鱨�� "  TYPE=button onclick="LLPRTInteInqReport()";"></td>
          	  </td>
          </Table>
          
     </Div>
     
        <!-- <Div  id= "DivLLInqCourseGrid" style= "display: ''">
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></TD>
                <TD class= titleImg> ���������Ϣ </TD>
            </TR>
        </Table>
             <Table  class= common>
                  <TR  class= common>
                       <TD text-align: left colSpan=1><span id="spanLLInqCourseGrid" ></span> </TD>
                   </TR>
             </Table>
        </Table> -->
        <!--table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table--> 
        </Div>

        <!-- <Div  id= "DivLLInqCourseForm" style= "display: ''">    
            <Table>
                 <TR>
                     <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></TD>
                     <TD class= titleImg> ���������ϸ��Ϣ </TD>
                 </TR>
            </Table>
            <Table class=common>
                <tr class=common>
                    <td class=title> �ⰸ�� </td>
                    <td class= input><Input class= "readonly" readonly name="ClmNo4"></td>
                    <td class=title> ������� </td>
                    <td class= input><Input class= "readonly" readonly name="InqNo2"></td>
                    <td class=title> ������� </td>
                    <td class= input><Input class= "readonly" readonly name="CouNo"></td>                                   
                </tr>   
            </Table>    
            <Table class=common>              
                <tr class= common>
                    <td class= title> ����ص� </td>
                </tr> 
                <tr class= common>       
                    <td class= input> <textarea name="InqSite" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></td>
                </tr>
                <tr class= common>
                    <td class= title> ������� </td>
                </tr> 
                <tr class= common>       
                    <td class= input> <textarea name="InqCourse" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></td>
                </tr>       
            </Table>  
            
    </Div>  -->   
	
    <Input class=cssButton value=" �� �� " type=button onclick=top.close() align= center>
    <%
    //******************
    //�������ݵ����ر�
    //******************
    %>
    <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->  
    <Input type=hidden id="Sign" name="Sign"><!--��־--> 
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>    <br><br><br><br>      
</Form>
</body>
</html>
