<%
//�������ƣ�BqCheckPrintInit.jsp.jsp
//�����ܣ�֪ͨ���ӡ
//�������ڣ�2005-08-03 15:39:06
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	String OtherNo = request.getParameter("OtherNo");
	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
	if(comcode.length < 6)
	{
	    alert("��½����Ϊ�ܹ�˾��ֹ�˾�����ܽ��з�Ʊ��ӡ��");
	    return false;
	}
	document.all('ManageCom').value = comcode.substring(0,6);	
//	var QueryCode = "select code from ldcode where codetype = 'bqcheck' and trim(comcode) = '" + comcode.substring(1,4) + "'";
	
	 var  comcode7 = comcode.substring(1,4);
	 var sqlid7="BqCheckPrintSql7";
	 var mySql7=new SqlClass();
	 mySql7.setResourceName("bq.BqCheckPrintSql");
	 mySql7.setSqlId(sqlid7);//ָ��ʹ��SQL��id
	 mySql7.addSubPara(comcode7);//ָ���������
	 var QueryCode = mySql7.getString();
	
	var tResult = easyExecSql(QueryCode);
	if(tResult == null)
	{
	    alert("��ѯ�õ�½�������ñ�ȫ��Ʊ����ʧ�ܣ�");
	    return false;
	}
	document.all('CertifyCode').value = tResult[0][0];
	
	document.all('InvoiceType').value ="BQ35";            //Ĭ�ϴ���˷�Ʊ
	document.all('InvoiceName').value ="��ȫ�����վ�";     
	document.all('ChequeType').value ="BQ35";               
  }
  catch(ex)
  {
    alert("��BqCheckPrintInit.jsp.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
     document.all('EdorAcceptNo').value = '<%=OtherNo%>';
     if (document.all('EdorAcceptNo').value == 'null')
     document.all('EdorAcceptNo').value = '';
  }
  catch(ex)
  {
    alert("��BqCheckPrintInit.jsp.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initNoticeGrid();
    initManageCom(); //��ʼ��������������ȡ6λ   
  }
  catch(re)
  {
    alert("BqCheckPrintInit.jsp.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
 var NoticeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
function initNoticeGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            	//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="֪ͨ���";         		//����
	  iArray[1][1]="0px";            	//�п�
	  iArray[1][2]=100;            			//�����ֵ
	  iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
	  iArray[2]=new Array();
    iArray[2][0]="��ȫ�����";       		//����
	  iArray[2][1]="120px";            	//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

    iArray[3]=new Array();
	  iArray[3][0]="������";        //����
	  iArray[3][1]="120px";            	//�п�
	  iArray[3][2]=100;            			//�����ֵ
	  iArray[3][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
	    
	    
	  iArray[4]=new Array();
	  iArray[4][0]="������Ŀ";        //����
	  iArray[4][1]="80px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
	  iArray[5][0]="�������";        //����
	  iArray[5][1]="80px";            	//�п�
	  iArray[5][2]=100;            		//�����ֵ
	  iArray[5][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
	    
	  iArray[6]=new Array();
	  iArray[6][0]="ҵ��Ա����";        //����
	  iArray[6][1]="80px";            	//�п�
    iArray[6][2]=100;            		//�����ֵ
    iArray[6][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������

	
      NoticeGrid = new MulLineEnter( "fm" , "NoticeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      NoticeGrid.mulLineCount = 10;   
      NoticeGrid.displayTitle = 1;
      NoticeGrid.canSel = 1;
      NoticeGrid.hiddenPlus=1;
      NoticeGrid.hiddenSubtraction=1;
      NoticeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
