<%
//�������ƣ�LLSecondUWRiskInit.jsp
//�����ܣ����ֺ˱���Ϣ�����ʼ��-------���ⲿ��
//�������ڣ�2005-01-06 11:10:36
//������  ��HYQ
//���¼�¼��  ������ yuejw   ��������     ����ԭ��/����
%>
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
    fm.all('ContNo').value = nullToEmpty("<%=tContNo%>");
	fm.all('InsuredNo').value =nullToEmpty("<%=tInsuredNo%>");
	fm.all('CaseNo').value = nullToEmpty("<%=tCaseNo%>");
	fm.all('BatNo').value = nullToEmpty("<%=tBatNo%>");
	fm.all('SendFlag').value = nullToEmpty("<%=tSendFlag%>");
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
//[��ʼ������]---��Inputҳ�����
function initForm()
{
	try
	{ 
		initLLRiskGrid();
		initParam();
		queryLLRiskGridInfo();
	}
	catch(re)
	{
		alert("��LLSecondUWRiskInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

// ����������Ϣ�б�ĳ�ʼ��
function initLLRiskGrid()
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
      iArray[1][0]="�������ֺ���";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ձ�������";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����ֹ��";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[9]=new Array();
      iArray[9][0]="���Ѽ��(��)";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��������";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
           
      LLRiskGrid = new MulLineEnter( "fm","LLRiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLRiskGrid.mulLineCount = 3;   
      LLRiskGrid.displayTitle = 1;
      LLRiskGrid.locked = 1;
      LLRiskGrid.canSel = 1;
      LLRiskGrid.hiddenPlus = 1;
      LLRiskGrid.hiddenSubtraction = 1;
      LLRiskGrid.loadMulLine(iArray);     
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>