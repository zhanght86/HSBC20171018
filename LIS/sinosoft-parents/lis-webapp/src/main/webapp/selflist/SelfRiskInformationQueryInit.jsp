<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
//�������ƣ�SelfRiskInformationQueryInit.jsp
//�����ܣ���������������Ϣ��ѯ
//�������ڣ�2008-03-14
//������  ��zz
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�                                      
function initForm()
{
  try
  {
		initRiskInfo();
		
  }
  catch(re)
  {
    alert("SelfRiskInformationQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
  
  easyQueryClick();
}

// ������Ϣ�б�ĳ�ʼ��
function initRiskInfo()
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
		iArray[1][0]="���ֱ���";         		  //����
		iArray[1][1]="30px";            		//�п�
		iArray[1][2]=30;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��������";          		//����
		iArray[2][1]="80px";            		//�п�
		iArray[2][2]=80;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="��������";          		//����
		iArray[3][1]="30px";            		//�п�
		iArray[3][2]=30;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="������������";          		//����
		iArray[4][1]="30px";            		//�п�
		iArray[4][2]=30;            			  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="����"; //����
		iArray[5][1]="30px";            		//�п�
		iArray[5][2]=30;            			  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		RiskInfo = new MulLineEnter("fm" , "RiskInfo"); 
		// ��Щ���Ա�����loadMulLineǰ
		// CardInfo.mulLineCount = 2;   
		RiskInfo.displayTitle = 1;
	  	RiskInfo.hiddenSubtraction=1;
	  	RiskInfo.hiddenPlus=1;
		RiskInfo.locked = 1;
		//RiskInfo.canSel = 1;
		RiskInfo.loadMulLine(iArray);  
		//��Щ����������loadMulLine����
		//RiskInfo.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
