<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorManuUWInsuredInit.jsp
//�����ܣ��˹��˱���������Ϣ
//�������ڣ�2005-06-04 18:32:36
//������  ��liurongxiao
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput tglobalInput = (GlobalInput)session.getValue("GI");
	
	if(tglobalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = tglobalInput.Operator;
	
%>                            

<script language="JavaScript">  
//��ʼ��ҳ��Ԫ��
function initParam()
{
   document.all('ContNo').value = nullToEmpty("<%= tContNo %>");
   document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
   document.all('EdorNo').value = nullToEmpty("<%= tEdorNo %>");
   document.all('PrtNo').value = nullToEmpty("<%= tPrtNo %>");
   document.all('InsuredNo').value = nullToEmpty("<%=tInsuredNo%>");
   document.all('EdorAcceptNo').value = nullToEmpty("<%= tEdorAcceptNo %>");
   document.all('EdorType').value = nullToEmpty("<%= tEdorType %>"); //alert("123"+nullToEmpty("<%= tEdorType %>"));
   document.all('OtherNo').value = nullToEmpty("<%= tOtherNo %>");
   document.all('OtherNoType').value = nullToEmpty("<%= tOtherNoType %>");
   document.all('EdorAppName').value = nullToEmpty("<%= tEdorAppName %>");
   document.all('Apptype').value = nullToEmpty("<%= tApptype %>");
   document.all('ManageCom').value = nullToEmpty("<%= tManageCom %>");
   document.all('UWType').value = "EdorItem";
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
//��ʼ��ҳ��Ԫ��
function initInpBox()
{ 

  try
  {
  	
  	document.all('EdorUWState').value = "";
  	document.all('EdorUWStateName').value = "";
	document.all('UWDelay').value = "";
	document.all('UWPopedomCode').value = "";
	document.all('UWIdea').value = "";

  	

  }
  catch(ex)
  {
    alert("��EdorManuUWInsuredInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!"+ex);
  }      
}
function initForm()
{  
  try
  {  
    
    initInpBox();
    initParam();
    initRiskGrid();
    initInsuredRelatedGrid(); 
    
    queryInsuredInfo();
    
    queryRiskInfo();
	//alert("EDORTYPE=="+fm.EdorType.value);
   
  }
  catch(re)
  {
    alert("��EdorManuUWInsuredInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initRiskGrid()
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
      iArray[1][0]="������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[3]=new Array();
      iArray[3][0]="�������ֺ���";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ձ�������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";         		//����
      iArray[5][1]="70px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="140px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="70px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="��������";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="����ֹ��";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[11]=new Array();
      iArray[11][0]="���Ѽ��(��)";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="��������";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������     
      
       
      
      iArray[13]=new Array();
	  iArray[13][0]="�˱�״̬";         	//����
	  iArray[13][1]="80px";            		//�п�
	  iArray[13][2]=80;            			//�����ֵ
	  iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[14]=new Array();
	  iArray[14][0]="�˱�״̬����";         		//����
	  iArray[14][1]="0px";            		//�п�
	  iArray[14][2]=100;            			//�����ֵ
	  iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskGrid.mulLineCount = 3;   
      RiskGrid.displayTitle = 1;
      RiskGrid.locked = 1;
      RiskGrid.canSel = 1;
      RiskGrid.hiddenPlus = 1;
      RiskGrid.hiddenSubtraction = 1;
      RiskGrid.loadMulLine(iArray);     
      
      RiskGrid.selBoxEventFuncName = "getRiskInfo";
      
      //��Щ����������loadMulLine����
      //RiskGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// ������������Ϣ�б�ĳ�ʼ��
function initInsuredRelatedGrid()
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
      iArray[1][0]="�ͻ���";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[5]=new Array();
      iArray[5][0]="֤������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֤������";         		//����
      iArray[6][1]="140px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�������˿ͻ���";         		//����
      iArray[7][1]="140px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="���������˹�ϵ";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
           
           
      InsuredRelatedGrid = new MulLineEnter( "fm" , "InsuredRelatedGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      InsuredRelatedGrid.mulLineCount = 3;   
      InsuredRelatedGrid.displayTitle = 1;
      InsuredRelatedGrid.locked = 1;
      InsuredRelatedGrid.canSel = 0;
      InsuredRelatedGrid.hiddenPlus = 1;
      InsuredRelatedGrid.hiddenSubtraction = 1;
      InsuredRelatedGrid.loadMulLine(iArray);     
      
      //InsuredRelatedGrid. selBoxEventFuncName = "getInsuredRelatedInfo";
      
      //��Щ����������loadMulLine����
      //InsuredRelatedGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
