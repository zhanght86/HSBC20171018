<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
//�������ƣ�AlreadyActivateCertifyQuery.jsp
//�����ܣ��Ѽ������ѯ
//�������ڣ�2008-03-17
//������  ��zz
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	String strCurDate = PubFun.getCurrentDate();
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {
  	fm.MakeDateB.value = '<%= strCurDate %>';
  	fm.MakeDateE.value = '<%= strCurDate %>';
  }
  catch(ex)
  {
    alert("��AlreadyActivateCertifyInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��AlreadyActivateCertifyInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    	initInpBox();
    	initSelBox();
		initCardInfo();
		initFamilyCardInfo();
		initContInfo();
  }
  catch(re)
  {
    alert("AlreadyActivateCertifyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initCardInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="40px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="����";         		  //����
		iArray[1][1]="130px";            		//�п�
		iArray[1][2]=100;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��������";          		//����
		iArray[2][1]="140px";            		//�п�
		iArray[2][2]=140;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		
		iArray[3]=new Array();
		iArray[3][0]="Ͷ���˿ͻ���";         		    //����
		iArray[3][1]="70px";            		//�п�
		iArray[3][2]=70;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	  	iArray[4]=new Array();
		iArray[4][0]="Ͷ��������";          		  //����
		iArray[4][1]="70px";            		//�п�
		iArray[4][2]=70;            			  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="�����˿ͻ���";         		    //����
		iArray[5][1]="70px";            		//�п�
		iArray[5][2]=70;            			  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	  	iArray[6]=new Array();
		iArray[6][0]="����������";          		  //����
		iArray[6][1]="70px";            		//�п�
		iArray[6][2]=70;            			  //�����ֵ
		iArray[6][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[7]=new Array();
		iArray[7][0]="�б�����"; //����
		iArray[7][1]="70px";            		//�п�
		iArray[7][2]=70;            			  //�����ֵ
		iArray[7][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[8]=new Array();
		iArray[8][0]="��������";            //����
		iArray[8][1]="70px";            		//�п�
		iArray[8][2]=70;            		 	  //�����ֵ
		iArray[8][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[9]=new Array();
		iArray[9][0]="��Ч����";         		//����
		iArray[9][1]="70px";            		//�п�
		iArray[9][2]=70;            			  //�����ֵ
		iArray[9][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[10]=new Array();
		iArray[10][0]="������ֹ����";            //����
		iArray[10][1]="70px";            		//�п�
		iArray[10][2]=70;            			  //�����ֵ
		iArray[10][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[11]=new Array();
		iArray[11][0]="��������";            //����
		iArray[11][1]="60px";            		//�п�
		iArray[11][2]=60;            			  //�����ֵ
		iArray[11][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		CardInfo = new MulLineEnter("fm" , "CardInfo"); 
		// ��Щ���Ա�����loadMulLineǰ
		CardInfo.mulLineCount = 5;   
		CardInfo.displayTitle = 1;
	  	CardInfo.hiddenSubtraction=1;
	  	CardInfo.hiddenPlus=1;
		CardInfo.locked = 1;
		//CardInfo.canSel = 1;
		CardInfo.loadMulLine(iArray);  
		//��Щ����������loadMulLine����
		//CardInfo.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}


// ������Ϣ�б�ĳ�ʼ��
function initFamilyCardInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="40px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="����";         		  //����
		iArray[1][1]="130px";            		//�п�
		iArray[1][2]=100;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��������";          		//����
		iArray[2][1]="140px";            		//�п�
		iArray[2][2]=140;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		
		iArray[3]=new Array();
		iArray[3][0]="Ͷ���˿ͻ���";         		    //����
		iArray[3][1]="70px";            		//�п�
		iArray[3][2]=70;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	  	iArray[4]=new Array();
		iArray[4][0]="Ͷ��������";          		  //����
		iArray[4][1]="70px";            		//�п�
		iArray[4][2]=70;            			  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="�б�����"; //����
		iArray[5][1]="70px";            		//�п�
		iArray[5][2]=70;            			  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="��������";            //����
		iArray[6][1]="70px";            		//�п�
		iArray[6][2]=70;            		 	  //�����ֵ
		iArray[6][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[7]=new Array();
		iArray[7][0]="��Ч����";         		//����
		iArray[7][1]="70px";            		//�п�
		iArray[7][2]=70;            			  //�����ֵ
		iArray[7][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[8]=new Array();
		iArray[8][0]="������ֹ����";            //����
		iArray[8][1]="70px";            		//�п�
		iArray[8][2]=70;            			  //�����ֵ
		iArray[8][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[9]=new Array();
		iArray[9][0]="��������";            //����
		iArray[9][1]="60px";            		//�п�
		iArray[9][2]=60;            			  //�����ֵ
		iArray[9][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������



		FamilyCardInfo = new MulLineEnter("fm" , "FamilyCardInfo"); 
		// ��Щ���Ա�����loadMulLineǰ
		FamilyCardInfo.mulLineCount = 5;
		FamilyCardInfo.displayTitle = 1;
	    FamilyCardInfo.hiddenSubtraction=1;
	    FamilyCardInfo.hiddenPlus=1;
		FamilyCardInfo.locked = 1;
		FamilyCardInfo.selBoxEventFuncName = "getFamilyCont";
		FamilyCardInfo.canSel = 1;
		FamilyCardInfo.loadMulLine(iArray);  
		//��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}



// ������Ϣ�б�ĳ�ʼ��
function initContInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="40px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="����";         		  //����
		iArray[1][1]="130px";            		//�п�
		iArray[1][2]=100;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��ͬ��";          		//����
		iArray[2][1]="140px";            		//�п�
		iArray[2][2]=140;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		
		iArray[3]=new Array();
		iArray[3][0]="��������";         		    //����
		iArray[3][1]="70px";            		//�п�
		iArray[3][2]=70;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	  	iArray[4]=new Array();
		iArray[4][0]="�����˿ͻ���";          		  //����
		iArray[4][1]="70px";            		//�п�
		iArray[4][2]=70;            			  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="����������"; //����
		iArray[5][1]="70px";            		//�п�
		iArray[5][2]=70;            			  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		



		ContInfoGrid = new MulLineEnter("fm" , "ContInfoGrid"); 
		// ��Щ���Ա�����loadMulLineǰ
		ContInfoGrid.mulLineCount = 5;
		ContInfoGrid.displayTitle = 1;
	    ContInfoGrid.hiddenSubtraction=1;
	    ContInfoGrid.hiddenPlus=1;
		ContInfoGrid.locked = 1;
		ContInfoGrid.selBoxEventFuncName = "getFamilyCont";
//		ContInfoGrid.canSel = 1;
		ContInfoGrid.loadMulLine(iArray);  
		//��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}	
</script>
