<%
//�������ƣ���LLSecondManuUWInit.jsp
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

// ���մ�������
function initParm()
{
	fm.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //��¼����Ա
    fm.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //��¼��½����
    fm.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //��¼�������
    
   	fm.all('tCaseNo').value =nullToEmpty("<%=tCaseNo%>"); //�ⰸ��		
    fm.all('tBatNo').value =nullToEmpty("<%=tBatNo%>"); //���κ�
    fm.all('tInsuredNo').value =nullToEmpty("<%=tInsuredNo%>"); //�����˿ͻ��� 
    fm.all('tClaimRelFlag').value =nullToEmpty("<%=tClaimRelFlag%>"); //�ⰸ��ر�־ 	
    
	 fm.all('tMissionid').value =nullToEmpty("<%=tMissionid%>");   //����ID 
	 fm.all('tSubmissionid').value =nullToEmpty("<%=tSubmissionid%>"); //������ID 
	 fm.all('tActivityid').value =nullToEmpty("<%=tActivityid%>"); //�ڵ�� 	
//	 alert(fm.all('tMissionid').value);
//	 alert(fm.all('tSubmissionid').value);
//	 alert(fm.all('tActivityid').value);
}
//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
	try
	{                                   
	
	}
	catch(ex)
	{
		alert("��LLSecondManuUWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}      
}
// ����������Ϣ��ʾ��ĳ�ʼ��������¼���֣�
function initPolBox()
{ 
  try
  {                                   

  }
  catch(ex)
  {
    alert("����PManuUWInit.jsp-->InitPolBox�����з����쳣:��ʼ���������!");
  }  
} 

function initForm()
{
  try
  {
 	 initLLCUWBatchGrid();
     initParm();
     initLLCUWBatchGridQuery();
  }
  catch(re)
  {
    alert("��PManuUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initLLCUWBatchGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=30;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[1]=new Array();
		iArray[1][0]="��ͬ����";         		//����
		iArray[1][1]="120px";            		//�п�
		iArray[1][2]=100;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
		iArray[2]=new Array();
		iArray[2][0]="���κ�";         		//����
		iArray[2][1]="120px";            		//�п�
		iArray[2][2]=100;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="Ͷ���˿ͻ���";         		//����
		iArray[3][1]="100px";            		//�п�
		iArray[3][2]=200;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
		iArray[4]=new Array();
		iArray[4][0]="Ͷ��������";         		//����
		iArray[4][1]="100px";            		//�п�
		iArray[4][2]=200;            			//�����ֵ
		iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="�����˿ͻ���";
		iArray[5][1]="100px";
		iArray[5][2]=60;
		iArray[5][3]=0;
	
		iArray[6]=new Array();
		iArray[6][0]="����������";
		iArray[6][1]="100px";
		iArray[6][2]=120;
		iArray[6][3]=0;
	
		iArray[7]=new Array();
		iArray[7][0]="�������";
		iArray[7][1]="80px";
		iArray[7][2]=120;
		iArray[7][3]=0;
	
		LLCUWBatchGrid = new MulLineEnter( "fm" , "LLCUWBatchGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		LLCUWBatchGrid.mulLineCount = 1;   
		LLCUWBatchGrid.displayTitle = 1;
		LLCUWBatchGrid.locked = 1;
		LLCUWBatchGrid.canSel = 1;
		LLCUWBatchGrid.hiddenPlus = 1;
		LLCUWBatchGrid.hiddenSubtraction = 1;
		LLCUWBatchGrid.loadMulLine(iArray);       
		LLCUWBatchGrid.selBoxEventFuncName = "LLCUWBatchGridClick";
	}
	catch(ex)
	{
		alert(ex);
	}
}


</script>