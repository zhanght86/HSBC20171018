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
	document.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //��¼����Ա
    document.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //��¼��½����
    document.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //��¼�������
    document.all('ContNo').value = nullToEmpty("<%=tContNo%>");
	document.all('InsuredNo').value =nullToEmpty("<%=tInsuredNo%>");
	document.all('CaseNo').value = nullToEmpty("<%=tCaseNo%>");
	document.all('BatNo').value = nullToEmpty("<%=tBatNo%>");
	document.all('SendFlag').value = nullToEmpty("<%=tSendFlag%>");
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
		
		initLLUWSubGrid();
		initParam();
		queryLLRiskGridInfo();
		queryInsuredInfo();
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
      iArray[0][0]="���";         			//���� 
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������		 

      iArray[1]=new Array();
      iArray[1][0]="�������ֺ���";         		 
      iArray[1][1]="10px";            		 
      iArray[1][2]=100;            			 
      iArray[1][3]=3;              			 

      iArray[2]=new Array();
      iArray[2][0]="������";         		 
      iArray[2][1]="140px";            		 
      iArray[2][2]=100;            			 
      iArray[2][3]=0;              			 

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		 
      iArray[3][1]="80px";            		 
      iArray[3][2]=100;            			 
      iArray[3][3]=0;              			 
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		 
      iArray[4][1]="200px";            		 
      iArray[4][2]=100;            			 
      iArray[4][3]=0;              			       

      iArray[5]=new Array();
      iArray[5][0]="����";         		 
      iArray[5][1]="60px";            		 
      iArray[5][2]=100;            			 
      iArray[5][3]=0;              			 

      iArray[6]=new Array();
      iArray[6][0]="����";         		 
      iArray[6][1]="60px";            		 
      iArray[6][2]=100;            			 
      iArray[6][3]=0;              			 

      iArray[7]=new Array();
      iArray[7][0]="��������";         		 
      iArray[7][1]="80px";            		 
      iArray[7][2]=100;            			 
      iArray[7][3]=0;              			 
      
      iArray[8]=new Array();
      iArray[8][0]="����ֹ��";         		 
      iArray[8][1]="80px";            		 
      iArray[8][2]=100;            			 
      iArray[8][3]=0;              			 
      
      
      iArray[9]=new Array();
      iArray[9][0]="���Ѽ��(��)";         		 
      iArray[9][1]="80px";            		 
      iArray[9][2]=100;            			 
      iArray[9][3]=0;              			 
      
      iArray[10]=new Array();
      iArray[10][0]="��������";         		 
      iArray[10][1]="80px";            		 
      iArray[10][2]=100;            			 
      iArray[10][3]=0;              			       
      
      iArray[11]=new Array();
      iArray[11][0]="Ͷ�������ֺ���";         		 
      iArray[11][1]="80px";            		 
      iArray[11][2]=100;            			 
      iArray[11][3]=3;  
      
      iArray[12]=new Array(); //Modify by zhaorx 2006-09-23
      iArray[12][0]="�����ձ��";         		 
      iArray[12][1]="80px";            		 
      iArray[12][2]=100;            			 
      iArray[12][3]=3;      
            
      LLRiskGrid = new MulLineEnter( "fm","LLRiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLRiskGrid.mulLineCount = 0;   
      LLRiskGrid.displayTitle = 1;
      LLRiskGrid.locked = 1;
      LLRiskGrid.canSel = 1;
      LLRiskGrid.hiddenPlus = 1;
      LLRiskGrid.hiddenSubtraction = 1;
      LLRiskGrid.selBoxEventFuncName="LLRiskGridClick";
      LLRiskGrid.loadMulLine(iArray);     
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�����������ֺ˱��˱��켣��
function initLLUWSubGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//���� 
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������		 

      iArray[1]=new Array();
      iArray[1][0]="�ְ���";         		 
      iArray[1][1]="0px";            		 
      iArray[1][2]=100;            			 
      iArray[1][3]=3;              			 

      iArray[2]=new Array();
      iArray[2][0]="���κ�";         		 
      iArray[2][1]="100px";            		 
      iArray[2][2]=100;            			 
      iArray[2][3]=0;              			 

      iArray[3]=new Array();
      iArray[3][0]="������";         		 
      iArray[3][1]="120px";            		 
      iArray[3][2]=100;            			 
      iArray[3][3]=0;              			 
      
      iArray[4]=new Array();
      iArray[4][0]="�������ֺ���";         		 
      iArray[4][1]="0px";            		 
      iArray[4][2]=100;            			 
      iArray[4][3]=3;              			       

      iArray[5]=new Array();
      iArray[5][0]="�˱�˳���";         		 
      iArray[5][1]="60px";            		 
      iArray[5][2]=100;            			 
      iArray[5][3]=0;              			 

      iArray[6]=new Array();
      iArray[6][0]="�˱���־";         		 
      iArray[6][1]="60px";            		 
      iArray[6][2]=100;            			 
      iArray[6][3]=0;              			 

      iArray[7]=new Array();
      iArray[7][0]="�˱�����";         		 
      iArray[7][1]="80px";            		 
      iArray[7][2]=100;            			 
      iArray[7][3]=0;              			 
      
      iArray[8]=new Array();
      iArray[8][0]="�˱����";         		 
      iArray[8][1]="250px";            		 
      iArray[8][2]=100;            			 
      iArray[8][3]=0;              			   
      
      iArray[9]=new Array();
      iArray[9][0]="�˱�����";         		 
      iArray[9][1]="80px";            		 
      iArray[9][2]=100;            			 
      iArray[9][3]=0;             
      
		iArray[10]=new Array();
		iArray[10][0]="�˱�ʱ��";         		 
		iArray[10][1]="80px";            		 
		iArray[10][2]=100;            			 
		iArray[10][3]=0;    			      
           
      LLUWSubGrid = new MulLineEnter( "fm","LLUWSubGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLUWSubGrid.mulLineCount = 0;   
      LLUWSubGrid.displayTitle = 1;
      LLUWSubGrid.locked = 1;
      LLUWSubGrid.canSel = 0;
      LLUWSubGrid.hiddenPlus = 1;
      LLUWSubGrid.hiddenSubtraction = 1;
      LLUWSubGrid.loadMulLine(iArray);     
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
