<%
//�������ƣ�ForceUWInit.jsp
//�����ܣ�
//�������ڣ�2005-05-19 08:49:52
//������  ��HL
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<script language="JavaScript">
//����ʼ��
function initForm(){
	try{
		initSelBox();
		initInpBox();
	}
	catch(e){
	  alert();	
	}
	
}

//��ʼ�������
function initInpBox() { 
  document.all("ContNo").value=ContNo;
}

// ������ĳ�ʼ��
function initSelBox(){
	//alert("aa");
  //��ѯͶ������ǿ�ƽ����˹��˱�״̬
 // var tSQL = "select ForceUWFlag,ForceUWReason from lccont where contno='"+ContNo+"'";
              
     var sqlid1="ForceUWInitSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.ForceUWInitSql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(ContNo);//ָ���������
	 var tSQL = mySql1.getString();
  
  //alert("ContNo="+ContNo);
  var arrResult = easyExecSql(tSQL,1,0);
	//alert("arrResult[0][0]"+arrResult[0][0]);
  if(arrResult[0][0]==null){
    return;
  }
  else
  {
  	//alert("arrResult[0][0]"+arrResult[0][0]);
  	document.all('ForceUWOpt').value=arrResult[0][0];
  	document.all('ForceUWRemark').value=arrResult[0][1];
    return;
  }
  

}

</script>
