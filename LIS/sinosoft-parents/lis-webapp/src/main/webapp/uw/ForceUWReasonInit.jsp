<%
//�������ƣ�ForceUWReasonInit.jsp
//�����ܣ�ǿ���˹��˱�ԭ���ѯҳ���ʼ��
//�������ڣ�2005-10-11 11:10:36
//������  ��guanw
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
  String ContNo = request.getParameter("ContNo");
  loggerDebug("ForceUWReasonInit",ContNo);
%>
 <script language="JavaScript"> 
 	var ContNo = '<%=ContNo%>';
 	</script> 
<%
  loggerDebug("ForceUWReasonInit","11111111111=========="+ContNo);
%>  
<script language="JavaScript">
//����ʼ��
function initForm(){
	//try{
		initSelBox();
		initInpBox();
	//}
	//catch(e){
	  //alert();
	//}	
}
//��ʼ�������
function initInpBox() { 
  //document.all("ContNo").value=ContNo;
}

// ������ĳ�ʼ��
function initSelBox(){                                                                       
 //��ѯͶ������ǿ�ƽ����˹��˱�״̬                                                            
// var tSQL = "select ForceUWReason from lccont where contno='"+ContNo+"'";     
 
 var sqlid1="ForceUWReasonInitSql1";
 var mySql1=new SqlClass();
 mySql1.setResourceName("uw.ForceUWReasonInitSql");
 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
 mySql1.addSubPara(ContNo);//ָ���������
 var tSQL = mySql1.getString();
 
 var arrResult = easyExecSql(tSQL); 
 if(arrResult==null){                                                                 
 return;                                                                                     
 }                                                                                           
 else                                                                                        
 {                                                                                 
 document.all('ForceUWReason').value=arrResult;                                       
     return;
  }
}
</script>
