<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDTestDeploySave.jsp
//�����ܣ���Ʒ�����뷢��
//�������ڣ�2009-3-18
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
 //������Ϣ������У�鴦��
 //�������
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 
 try
 {
     String tChk[] = request.getParameterValues("InpMulline11GridChk"); 

      for(int index=0;index<tChk.length;index++)
      {
        if(tChk[index].equals("1")) {         
         //����״̬ ����޸��˺�ʱ��
         String[] tClewcontentcodes = request.getParameterValues("Mulline11Grid5");
         String[] tTestplankinds = request.getParameterValues("Mulline11Grid6");
         String[] tTestPersons = request.getParameterValues("Mulline11Grid3");
         String[] tTestTimes = request.getParameterValues("Mulline11Grid4");
         
         String tTestPerson = tTestPersons[index];
         String tClewcontentcode = tClewcontentcodes[index];
         String tTestplankind = tTestplankinds[index];
         String tTestTime = tTestTimes[index];
         
         //�ж�
         if(tTestplankind == null || tTestTime == null || tTestplankind.trim().equals("") || tTestTime.trim().equals("")){
        	 FlagStr = "Fail";
        	 Content = ""+"�����˺Ͳ���ʱ�䲻��Ϊ��"+"";
        %>
        <script type="text/javascript">
		 	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
		</script>
        <% 
        return;
         }
         System.out.println("tClewcontentcode" + tClewcontentcode + "  tTestplankind" + tTestplankind);
         String sql = "update pd_testTrack kk set kk.teststate = '1' , kk.testperson = '" + tTestPerson +"' , kk.testtime = '" + tTestTime +"', kk.modifydate = '" + PubFun.getCurrentDate() + "', kk.modifytime = '" + PubFun.getCurrentTime() +"' where kk.testplankind = '" + tTestplankind + "' and kk.clewcontentcode = '" + tClewcontentcode +"'";
         ExeSQL tExeSQL = new ExeSQL();
         tExeSQL.execUpdateSQL(sql);
        }if(tChk[index].equals("0")) { 
         String[] tClewcontentcodes = request.getParameterValues("Mulline11Grid4");
         String[] tTestplankinds = request.getParameterValues("Mulline11Grid5");
         String tClewcontentcode = tClewcontentcodes[index];
         String tTestplankind = tTestplankinds[index];	
         
         System.out.println("tClewcontentcode" + tClewcontentcode + "  tTestplankind" + tTestplankind);
         //����״̬ ��ɾ���޸��˺�ʱ��
         String sql = "update pd_testTrack kk set kk.teststate = '0' , kk.testperson = '' , kk.testtime = '', kk.modifydate = '" + PubFun.getCurrentDate() + "', kk.modifytime = '" + PubFun.getCurrentTime() +"' where kk.testplankind = '" + tTestplankind + "' and kk.clewcontentcode = '" + tClewcontentcode +"'";
         ExeSQL tExeSQL = new ExeSQL();
         tExeSQL.execUpdateSQL(sql);	        
        }
        
      }         
      Content = ""+"����ɹ�!"+"";
      FlagStr = "Successs";
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {

  if (!tError.needDealError())
  {                          
   Content = " "+"����ɹ�!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

