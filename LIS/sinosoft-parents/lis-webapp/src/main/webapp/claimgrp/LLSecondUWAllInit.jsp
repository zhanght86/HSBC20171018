<%
	//�������ƣ�LLSecondUWAllInit.jsp
	//�����ܣ������˹��˱���ȡ����
	//�������ڣ�2005-1-28 11:10:36
	//������  ��zhangxing
	//���¼�¼��  ������  yuejw  ��������     ����ԭ��/����
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>            
<script language="JavaScript">

//������ҳ��������� 
function initParam()
{
	fm.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //��¼����Ա
    fm.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //��¼��½����
    fm.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //��¼�������
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

function initForm()
{
	try
	{
		initParam(); //
		initLLCUWBatchGrid();  //
		initSelfLLCUWBatchGrid();
		initSelfLLCUWBatchGridQuery();  //          
	}
	catch(re)
	{
		alert("��UWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

// ������Ϣ�б�ĳ�ʼ��
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
		iArray[1][0]="�ⰸ��";         		//����
		iArray[1][1]="120px";            		//�п�
		iArray[1][2]=100;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[2]=new Array();
		iArray[2][0]="���κ�";         		//����
		iArray[2][1]="120px";            		//�п�
		iArray[2][2]=100;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="�������˺���";         		//����
		iArray[3][1]="120px";            		//�п�
		iArray[3][2]=100;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[4]=new Array();
		iArray[4][0]="������������";         		//����
		iArray[4][1]="120px";            		//�п�
		iArray[4][2]=100;            			//�����ֵ
		iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="�ⰸ��ر�־";         		//����
		iArray[5][1]="100px";            		//�п�
		iArray[5][2]=100;            			//�����ֵ
		iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
	
		iArray[6]=new Array();                                                       
		iArray[6][0]="����";         		//����                                     
		iArray[6][1]="120px";            		//�п�                                   
		iArray[6][2]=100;            			//�����ֵ                                 
		iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
	
	    iArray[7]=new Array();
	    iArray[7][0]="Missionid";             //����
	    iArray[7][1]="0px";                //�п�
	    iArray[7][2]=200;                  //�����ֵ
	    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      
	
	    iArray[8]=new Array();
	    iArray[8][0]="Submissionid";             //����
	    iArray[8][1]="0px";                //�п�
	    iArray[8][2]=200;                  //�����ֵ
	    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      
	    
	    iArray[9]=new Array();
	    iArray[9][0]="Activityid";             //����
	    iArray[9][1]="0px";                //�п�
	    iArray[9][2]=200;                  //�����ֵ
	    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      
	
		LLCUWBatchGrid = new MulLineEnter( "fm" , "LLCUWBatchGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		LLCUWBatchGrid.mulLineCount = 0;   
		LLCUWBatchGrid.displayTitle = 1;
		LLCUWBatchGrid.locked = 1;
		LLCUWBatchGrid.canSel = 1;
		LLCUWBatchGrid.hiddenPlus = 1;
		LLCUWBatchGrid.hiddenSubtraction = 1;
		LLCUWBatchGrid.loadMulLine(iArray);     
//		LLCUWBatchGrid.selBoxEventFuncName = "LLCUWBatchGridClick";
		//��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfLLCUWBatchGrid()
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
		iArray[1][0]="�ⰸ��";         		//����
		iArray[1][1]="120px";            		//�п�
		iArray[1][2]=100;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[2]=new Array();
		iArray[2][0]="���κ�";         		//����
		iArray[2][1]="120px";            		//�п�
		iArray[2][2]=100;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="�������˺���";         		//����
		iArray[3][1]="120px";            		//�п�
		iArray[3][2]=100;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
		iArray[4]=new Array();
		iArray[4][0]="������������";         		//����
		iArray[4][1]="120px";            		//�п�
		iArray[4][2]=100;            			//�����ֵ
		iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="�ⰸ��ر�־";         		//����
		iArray[5][1]="100px";            		//�п�
		iArray[5][2]=100;            			//�����ֵ
		iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
	
		iArray[6]=new Array();                                                       
		iArray[6][0]="����";         		//����                                     
		iArray[6][1]="120px";            		//�п�                                   
		iArray[6][2]=100;            			//�����ֵ                                 
		iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
	
	    iArray[7]=new Array();
	    iArray[7][0]="Missionid";             //����
	    iArray[7][1]="0px";                //�п�
	    iArray[7][2]=200;                  //�����ֵ
	    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      
	
	    iArray[8]=new Array();
	    iArray[8][0]="Submissionid";             //����
	    iArray[8][1]="0px";                //�п�
	    iArray[8][2]=200;                  //�����ֵ
	    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      
	    
	    iArray[9]=new Array();
	    iArray[9][0]="Activityid";             //����
	    iArray[9][1]="0px";                //�п�
	    iArray[9][2]=200;                  //�����ֵ
	    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      
	
		SelfLLCUWBatchGrid = new MulLineEnter( "fm" , "SelfLLCUWBatchGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		SelfLLCUWBatchGrid.mulLineCount =0;   
		SelfLLCUWBatchGrid.displayTitle = 1;
		SelfLLCUWBatchGrid.locked = 1;
		SelfLLCUWBatchGrid.canSel = 1;
		SelfLLCUWBatchGrid.hiddenPlus = 1;
		SelfLLCUWBatchGrid.hiddenSubtraction = 1;
		SelfLLCUWBatchGrid.loadMulLine(iArray);     
		SelfLLCUWBatchGrid.selBoxEventFuncName = "SelfLLCUWBatchGridClick";
		//��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}


</script>