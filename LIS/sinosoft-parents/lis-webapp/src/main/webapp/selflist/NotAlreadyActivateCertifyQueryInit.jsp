<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
//�������ƣ�NotAlreadyActivateCertifyQuery.jsp
//�����ܣ�δ�������ѯ
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
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="����";         		  //����
		iArray[1][1]="100px";            		//�п�
		iArray[1][2]=100;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��������״̬";          		//����
		iArray[2][1]="60px";            		//�п�
		iArray[2][2]=60;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="��������״̬"; //����
		iArray[3][1]="70px";            		//�п�
		iArray[3][2]=70;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="�ɷ�����";            //����
		iArray[4][1]="70px";            		//�п�
		iArray[4][2]=70;            		 	  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="��������";            //����
		iArray[5][1]="70px";            		//�п�
		iArray[5][2]=70;            		 	  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������


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

</script>
