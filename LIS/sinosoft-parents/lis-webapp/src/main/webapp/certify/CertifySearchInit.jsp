<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
//�������ƣ�CertifySearchInit.jsp
//�����ܣ�
//�������ڣ�2003-06-16
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
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
  	fm.reset();
  	fm.MakeDateB.value = '<%= strCurDate %>';
  	fm.MakeDateE.value = '<%= strCurDate %>';
  }
  catch(ex)
  {
    alert("��CertifySearchInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��CertifySearchInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
		initCardInfo();
		initCardListInfo();
  }
  catch(re)
  {
    alert("CertifySearchInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��֤��Ϣ�б�ĳ�ʼ��
function initCardInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="��֤����";         		//����
		iArray[1][1]="80px";            		//�п�
		iArray[1][2]=100;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="������";          		//����
		iArray[2][1]="85px";            		//�п�
		iArray[2][2]=85;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="������";        		  //����
		iArray[3][1]="85px";            		//�п�
		iArray[3][2]=85;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="��ʼ��";          		//����
		iArray[4][1]="120px";            		//�п�
		iArray[4][2]=100;            		 	  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="��ֹ��";         		  //����
		iArray[5][1]="120px";            		//�п�
		iArray[5][2]=100;            			  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="����";          		  //����
		iArray[6][1]="60px";            		//�п�
		iArray[6][2]=60;            			  //�����ֵ
		iArray[6][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		

		iArray[7]=new Array();
		iArray[7][0]="״̬";         		  //����
		iArray[7][1]="60px";            		//�п�
		iArray[7][2]=100;            			  //�����ֵ
		iArray[7][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[8]=new Array();
		iArray[8][0]="����Ա";       		  //����
		iArray[8][1]="100px";            		//�п�
		iArray[8][2]=100;            			  //�����ֵ
		iArray[8][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[9]=new Array();
		iArray[9][0]="�������";       		  //����
		iArray[9][1]="100px";            		//�п�
		iArray[9][2]=100;            			  //�����ֵ
		iArray[9][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[10]=new Array();
		iArray[10][0]="���ʱ��";       		  //����
		iArray[10][1]="100px";            		//�п�
		iArray[10][2]=100;            			  //�����ֵ
		iArray[10][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		CardInfo = new MulLineEnter("fm" , "CardInfo"); 
		// ��Щ���Ա�����loadMulLineǰ
		// CardInfo.mulLineCount = 2;   
		CardInfo.displayTitle = 1;
		CardInfo.locked = 1;
		CardInfo.canSel = 1;
		CardInfo.selBoxEventFuncName = "boxEventHandler";
		CardInfo.loadMulLine(iArray);  
		//��Щ����������loadMulLine����
		//CardInfo.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

// ��ӡ���еĳ�ʼ��
function initCardListInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="��֤����";         		//����
		iArray[1][1]="80px";            		//�п�
		iArray[1][2]=100;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="������";          		//����
		iArray[2][1]="85px";            		//�п�
		iArray[2][2]=85;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="������";        		  //����
		iArray[3][1]="85px";            		//�п�
		iArray[3][2]=85;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="��ʼ��";          		//����
		iArray[4][1]="120px";            		//�п�
		iArray[4][2]=100;            		 	  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="��ֹ��";         		  //����
		iArray[5][1]="120px";            		//�п�
		iArray[5][2]=100;            			  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="����";          		  //����
		iArray[6][1]="60px";            		//�п�
		iArray[6][2]=60;            			  //�����ֵ
		iArray[6][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[7]=new Array();
		iArray[7][0]="״̬";         		  //����
		iArray[7][1]="60px";            		//�п�
		iArray[7][2]=100;            			  //�����ֵ
		iArray[7][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[8]=new Array();
		iArray[8][0]="����Ա";       		  //����
		iArray[8][1]="100px";            		//�п�
		iArray[8][2]=100;            			  //�����ֵ
		iArray[8][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[9]=new Array();
		iArray[9][0]="�������";       		  //����
		iArray[9][1]="100px";            		//�п�
		iArray[9][2]=100;            			  //�����ֵ
		iArray[9][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	  iArray[10]=new Array();
		iArray[10][0]="���ʱ��";       		  //����
		iArray[10][1]="100px";            		//�п�
		iArray[10][2]=100;            			  //�����ֵ
		iArray[10][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		CardListInfo = new MulLineEnter("fm_print" , "CardListInfo"); 
		CardListInfo.displayTitle = 1;
		CardListInfo.hiddenPlus = 1;
		CardListInfo.canSel = 1;
		CardListInfo.loadMulLine(iArray);  
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
