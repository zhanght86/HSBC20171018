<%
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//         
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<!--�û�У����--> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>  
  <%@page import="com.sinosoft.service.*" %>
<%
  
  //���汣����  
  String ContNo = request.getParameter("QueryNo");
  String ContNoType = request.getParameter("QueryType"); 
  
// �������
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";   
   String RiskCode = "";
      
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("TempFeeTypeQuery","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {   
     VData tVData = new VData();
     if(ContNoType.equals("2")) //���˱���
     {
        loggerDebug("TempFeeTypeQuery","GGGGGGGGG################3"+ContNo);
        LCContSchema  tLCContSchema = new LCContSchema();
        tLCContSchema.setContNo(ContNo);
        //tLCContSchema.setManageCom(tGI.ComCode);
        loggerDebug("TempFeeTypeQuery","GGGGGGGGG"+tGI.ComCode);
        tVData.add(tLCContSchema);
     }
     else
     {
        LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
        tLCGrpContSchema.setGrpContNo(ContNo);
        tLCGrpContSchema.setManageCom(tGI.ComCode);
        tVData.add(tLCGrpContSchema);
     }  
     //TempFeeTypeQueryUI tTempFeeTypeQueryUI = new TempFeeTypeQueryUI();
     
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     
     tBusinessDelegate.submitData(tVData,"QUERY","TempFeeTypeQueryUI");
     tError = tBusinessDelegate.getCErrors();
     if (!tError.needDealError())
     {                          
        Content = " ��ѯ�ɹ�! ";
       	FlagStr = "Succ";
        tVData.clear();
        if(ContNoType.equals("2"))
        {
          loggerDebug("TempFeeTypeQuery","������ѯ�ɹ�1");
          LCPolSet pLCPolSet = new LCPolSet();
          tVData = tBusinessDelegate.getResult();               
          pLCPolSet=(LCPolSet)tVData.getObjectByObjectName("LCPolSet",0);
          
          //RiskCode=pLCPolSet.getRiskCode();         
          loggerDebug("TempFeeTypeQuery","������ѯ�ɹ�2");
          for(int i=1;i<=pLCPolSet.size();i++)
          {
        	  RiskCode=pLCPolSet.get(i).getRiskCode();
 %>
 <script language="javascript">		 
		       var RiskCode="";  
		       var RiskName = new Array();
		       var row=<%=i%>;
		       if("<%=RiskCode%>"==null)
		       {
		         RiskName[0]= new Array();
		         RiskName[0][0]= "";
		       }
		       else
		       {
		         RiskCode="<%=RiskCode%>";
		         //var strSql = "select RiskName from LMRisk where RiskCode='"+"<%=RiskCode%>"+"'"; 
		         var tResourceName="finfee.TempFeeTypeQuerySql";
				var strSql = wrapSql(tResourceName,"querysqldes1",["<%=RiskCode%>"]);         
		         var strResult=easyQueryVer3(strSql, 1, 1, 1);
		         if(!strResult)
		         {
		           alert("���ֱ������û�в�ѯ�����ֱ����Ӧ���������ƣ�");
		           RiskName[0]= new Array();
		           RiskName[0][0]= "";
		         }
		         else
		         {       
		          RiskName=decodeEasyQueryResult(strResult);
		         }
		       } 
		       if(row==1)
		       {
			      parent.fraInterface.initTempGridYS();
			      parent.fraInterface.TempGrid.lock();
		       }
		         parent.fraInterface.TempGrid.addOne("TempGrid");
		         parent.fraInterface.TempGrid.setRowColData(row-1,1,RiskCode);
		         parent.fraInterface.TempGrid.setRowColData(row-1,2,RiskName[0][0]);
		         parent.fraInterface.TempGrid.setRowColData(row-1,5,parent.fraInterface.document.all('InputNo5').value);             
 </script>
 <%
          }
%>
<script>
                 parent.fraInterface.document.all('TempFeeNo').value=parent.fraInterface.document.all('InputNo5').value;  
                // alert(parent.fraInterface.document.all('TempFeeNo').value);
</script>
<%
        }
        else
        {
           LCGrpPolSet pLCGrpPolSet = new LCGrpPolSet();
           tVData = tBusinessDelegate.getResult();               
           pLCGrpPolSet=(LCGrpPolSet)tVData.getObjectByObjectName("LCGrpPolSet",0);
           loggerDebug("TempFeeTypeQuery","�ŵ���ѯ�ɹ�");
           //RiskCode=pLCGrpPolSchema.getRiskCode();   
           for(int i=1;i<=pLCGrpPolSet.size();i++)
           {
             RiskCode=pLCGrpPolSet.get(i).getRiskCode();
             loggerDebug("TempFeeTypeQuery","RiskCode"+RiskCode);
%>
<script language="javascript">		 
	 		 var RiskCode="";  
	         var RiskName = new Array();
	         var row=<%=i%>;
             if("<%=RiskCode%>"==null)
	         {
		         RiskName[0]= new Array();
		         RiskName[0][0]= "";
	         }
	         else
	         {
		         RiskCode="<%=RiskCode%>";
		         //var strSql = "select RiskName from LMRisk where RiskCode='"+"<%=RiskCode%>"+"'";
		         var tResourceName="finfee.TempFeeTypeQuerySql";
				var strSql = wrapSql(tResourceName,"querysqldes1",["<%=RiskCode%>"]);          
		         var strResult=easyQueryVer3(strSql, 1, 1, 1);
		         if(!strResult)
		         {
		           alert("���ֱ������û�в�ѯ�����ֱ����Ӧ���������ƣ�");
		           RiskName[0]= new Array();
		           RiskName[0][0]= "";
		         }
		         else
		         {       
		          RiskName=decodeEasyQueryResult(strResult);
		         }
	        } 
	        if(row==1)
	         {
		         parent.fraInterface.initTempGridYS(); 
		         parent.fraInterface.TempGrid.lock();
	         }
	         parent.fraInterface.TempGrid.addOne("TempGrid");
	         parent.fraInterface.TempGrid.setRowColData(row-1,1,RiskCode);
	         parent.fraInterface.TempGrid.setRowColData(row-1,2,RiskName[0][0]);           
	         parent.fraInterface.TempGrid.setRowColData(row-1,5,parent.fraInterface.document.all('InputNo5').value);                              	           
 </script>
<%  
          }
%>
<script>
           parent.fraInterface.document.all('TempFeeNo').value=parent.fraInterface.document.all('InputNo5').value;  
//           alert(parent.fraInterface.document.all('TempFeeNo').value);
</script>
<%
        }     	
      }
      else                                                                           
      {
     	Content = " ʧ�ܣ�ԭ��:" + tError.getFirstError();
     	FlagStr = "Fail";
      }     

  } //ҳ����Ч
     
%>


<script language="javascript"> 
    parent.fraInterface.afterQuery("<%=FlagStr%>","<%=Content%>");       
</script>
