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
  }
  catch(ex)
  {
    alert("��FamilyContQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��FamilyContQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
		initFamilyCardInfo();
  }
  catch(re)
  {
    alert("FamilyContQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][0]="ӡˢ��";         		  //����
		iArray[1][1]="120px";            		//�п�
		iArray[1][2]=100;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��ͬ��";          		//����
		iArray[2][1]="120px";            		//�п�
		iArray[2][2]=140;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		
		iArray[3]=new Array();
		iArray[3][0]="Ͷ���˿ͻ���";         		    //����
		iArray[3][1]="80px";            		//�п�
		iArray[3][2]=70;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	  	iArray[4]=new Array();
		iArray[4][0]="Ͷ��������";          		  //����
		iArray[4][1]="80px";            		//�п�
		iArray[4][2]=70;            			  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="�����˿ͻ���"; //����
		iArray[5][1]="80px";            		//�п�
		iArray[5][2]=70;            			  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="����������";            //����
		iArray[6][1]="80px";            		//�п�
		iArray[6][2]=70;            		 	  //�����ֵ
		iArray[6][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		

		FamilyCardInfoGrid = new MulLineEnter("fm" , "FamilyCardInfoGrid"); 
		// ��Щ���Ա�����loadMulLineǰ
		FamilyCardInfoGrid.displayTitle = 5;
	  FamilyCardInfoGrid.hiddenSubtraction=1;
	  FamilyCardInfoGrid.hiddenPlus=1;
		FamilyCardInfoGrid.locked = 1;
		FamilyCardInfoGrid.canSel = 1;
		FamilyCardInfoGrid.loadMulLine(iArray);  
		//��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}




</script>
