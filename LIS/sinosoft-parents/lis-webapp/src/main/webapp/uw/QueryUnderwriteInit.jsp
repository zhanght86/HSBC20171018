<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UnderwriteInit.jsp
//�����ܣ����˺˱���Ϣ��ѯ
//�������ڣ�2006-09-20 11:32
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{      
  try
  {                                  
	// ������ѯ����
    document.all('QPrtNo').value = '';
    document.all('QManageCom').value = '';
    document.all('QAppntName').value = '';
    document.all('QOperator').value = operator;
    document.all('QComCode').value = comcode;
    document.all('QState').value = '0';
    document.all('QProposalNo').value = '';
    //document.all('QRiskVersion').value = '';
    
  }
  catch(ex)
  {
    alert("��PManuUWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox1()
{ 

  try
  {                           
	// ������ѯ����
    document.all('PolNo').value = '';
    //document.all('State').value = '';
    document.all('AppntNo1').value = '';
    document.all('AppntName1').value = '';
    document.all('InsuredNo1').value = '';
    document.all('InsuredName1').value = '';
    document.all('AgentCode').value = '';
    //document.all('RiskCode').value = operator;
    //document.all('ManageCom').value = comcode;
    //��ͬ��Ϣ
    document.all('CPrtNo').value = '';
    document.all('CManageCom').value = '';
    document.all('CSaleChnl').value = '';
    document.all('CAgentCode').value = '';
    document.all('CShouldPayPrem').value = '';
    document.all('CFillPrem').value = '';
    document.all('CHighAmntFlag').value = '';
    document.all('CBlacklistFlag').value = '';
    document.all('CVIPValue').value = '';
    document.all('CRemark').value = '';
    document.all('ReviseReason').value = '';
    document.all('UpperUwReason').value = '';
    document.all('UpperUwUser').value = '';
    //Ͷ������Ϣ
    document.all('AppntName').value = '';
    document.all('AppntSex').value = '';
    document.all('AppntBirthday').value = '';
    document.all('OccupationCode').value = '';
    document.all('OccupationType').value = '';
    document.all('income').value = '';
    document.all('Stature').value = '';
    document.all('Weight').value = '';
    document.all('BMI').value = '';
    document.all('NativePlace').value = '';
    document.all('AppntSumLifeAmnt').value = '';
    document.all('AppntSumHealthAmnt').value = '';
    document.all('AppntMedicalAmnt').value = '';
    document.all('AppntSumLifeAmnt').value = '';
    document.all('AppntSumHealthAmnt').value = '';
    codeSql="code";
    document.all('Button1').disabled='true';
    document.all('Button2').disabled='true';
    document.all('Button3').disabled='true';
    //document.all('Button4').disabled='true';
    document.all('Button5').disabled='true';
    document.all('Button7').disabled='true';
    document.all('Button9').disabled='true';
  }
  catch(ex)
  {
    alert("��UnderwriteInit.jsp-->InitInpBox1�����з����쳣:��ʼ���������!");
  }      
}

// ����������Ϣ��ʾ��ĳ�ʼ��������¼���֣�
function initPolBox()
{ 

  try
  {                                   
	// ������ѯ����
    document.all('ProposalNo').value = '';
    document.all('PolNo').value = '';
    document.all('ManageCom').value = '';
    document.all('RiskCode').value = '';
    document.all('RiskVersion').value = '';
    document.all('AppntNo').value = '';
    document.all('AppntName').value = '';
    document.all('InsuredNo').value = '';
    document.all('InsuredName').value = '';
    document.all('InsuredSex').value = '';
    document.all('Mult').value = '';
    document.all('Prem').value = '';
    document.all('Amnt').value = '';
    document.all('AppGrade').value = '';
    document.all('UWGrade').value = '';
    document.all('Operator').value = '<%= strOperator %>';
   
    codeSql="code";
  }
  catch(ex)
  {
    alert("��UnderwriteInit.jsp-->InitPolBox2�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    //initInpBox();
    //initPolBox();    
    initPolGrid();   
    //easyQueryClick() ;
    //initPolAddGrid();
    //initPolInsuredGrid();
    //initPolRiskGrid();
    
  }
  catch(re)
  {
    alert("UnderwriteInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		        //�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="140px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ��������";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��ͬ״̬";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=80;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������       			
            			
      iArray[8]=new Array();
      iArray[8][0]="��ͬ��";         		//����
      iArray[8][1]="0px";                      //�п�
      iArray[8][2]=100;                         //�����ֵ  
      iArray[8][3]=0;                           //�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="�˱�״̬";         		//����
      iArray[9][1]="0px";                      //�п�
      iArray[9][2]=100;                         //�����ֵ  
      iArray[9][3]=0;                           //�Ƿ���������,1��ʾ����0��ʾ������ 
 
      iArray[10]=new Array();
      iArray[10][0]="��ͬ��(contno)";         		//����
      iArray[10][1]="0px";                      //�п�
      iArray[10][2]=100;                         //�����ֵ  
      iArray[10][3]=0;                           //�Ƿ���������,1��ʾ����0��ʾ������ 

     
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //PolGrid.mulLineCount = 3;   
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);      
      
      PolGrid.selBoxEventFuncName = "easyQueryAddClick";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initPolAddGrid()
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
      iArray[1][0]="�ͻ�����";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   
     	iArray[3]=new Array();
      iArray[3][0]="�Ա�";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[3][4]="sex";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

  		iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��Ͷ���˹�ϵ";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[5][4]="relation";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[5][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

     	iArray[6]=new Array();
      iArray[6][0]="���������˹�ϵ";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[6][4]="relation";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[6][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

    	iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[7][4]="nativeplace";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[7][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��



      iArray[8]=new Array();
      iArray[8][0]="֤������";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="֤������";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolAddGrid.mulLineCount = 5;   
      PolAddGrid.displayTitle = 1;
      PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.hiddenSubtraction = 1;
      PolAddGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
      PolAddGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initPolInsuredGrid()
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
      iArray[1][0]="�ͻ�����";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   
     	iArray[3]=new Array();
      iArray[3][0]="�Ա�";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[3][4]="sex";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

  		iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��Ͷ���˹�ϵ";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[5][4]="relation";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[5][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

     	iArray[6]=new Array();
      iArray[6][0]="���������˹�ϵ";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[6][4]="relation";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[6][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

    	iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	  	iArray[7][4]="nativeplace";              	        //�Ƿ����ô���:null||""Ϊ������
    	iArray[7][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��



      iArray[8]=new Array();
      iArray[8][0]="֤������";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="֤������";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      PolInsuredGrid = new MulLineEnter( "fm" , "PolInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolInsuredGrid.mulLineCount = 5;   
      PolInsuredGrid.displayTitle = 1;
      PolInsuredGrid.locked = 1;
      PolInsuredGrid.canSel = 1;
      PolInsuredGrid.hiddenPlus = 1;
      PolInsuredGrid.hiddenSubtraction = 1;
      PolInsuredGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
      PolInsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initPolRiskGrid()
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
      iArray[1][0]="���ֱ���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   
     	iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

  		iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�����ڼ�";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	
     	iArray[6]=new Array();
      iArray[6][0]="�����ڼ�";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

    	iArray[7]=new Array();
      iArray[7][0]="����Ƶ��";         		//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	


      iArray[8]=new Array();
      iArray[8][0]="��׼����";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="ְҵ�ӷ�";         		//����
      iArray[9][1]="40px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�����ӷ�";         		//����
      iArray[10][1]="40px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="���ֺ�";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PolRiskGrid = new MulLineEnter( "fm" , "PolRiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolRiskGrid.mulLineCount = 5;   
      PolRiskGrid.displayTitle = 1;
      PolRiskGrid.locked = 1;
      PolRiskGrid.canSel = 0;
      PolRiskGrid.hiddenPlus = 1;
      PolRiskGrid.hiddenSubtraction = 1;
      PolRiskGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
   //   PolRiskGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
