<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupUWInit.jsp
//�����ܣ������˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
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
    fmQuery.all('GrpProposalContNo').value = '';
//    fmQuery.all('GrpMainProposalNo').value = GrpMainPolNo;
    fmQuery.all('PrtNoHide').value = PrtNo;
    fmQuery.all('Operator').value = '<%= strOperator %>';    
  }
  catch(ex)
  {
    alert("��ContUWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ����������Ϣ��ʾ��ĳ�ʼ��������¼���֣�
function initPolBox()
{ 

  try
  {                                   
  }
  catch(ex)
  {
    alert("��ContUWInit.jsp-->InitPolBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    //initInpBox();
    //initPolBox();
    //initPolGrid();
    fmQuery.all('GrpContNo').value = GrpContNo;
    
		   
	
	  if(isContPlan()==false)
	  {
	    initGrpGrid(); 
		  initGrpPolFeeGrid(); 
	    querygrp();
	  }
    else
    {
    	initPlanGrid(); 
	  	initPlanRiskGrid(); 
      queryContPlan();
    }
    showInfo();
  
    makeWorkFlow();
 
   ctrlButtonDisabled(GrpContNo);
   }
  catch(re)
  {
    alert("ContUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����Ͷ������";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ����";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ְ汾";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="������";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PolGrid = new MulLineEnter( "fmQuery" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpGrid()
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
      iArray[1][0]="����Ͷ������";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="Ͷ����ӡˢ��";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����λ";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;  
      
      iArray[7]=new Array();
      iArray[7][0]="Ͷ������";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0; 
      
      iArray[8]=new Array();
      iArray[8][0]="�ܱ���";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0; 
      
      iArray[9]=new Array();
      iArray[9][0]="��������";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0; 
      
      iArray[10]=new Array();
      iArray[10][0]="����ֹ��";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0; 
     

      iArray[11]=new Array();
      iArray[11][0]="�������ֺ���";         		//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=3;  

      iArray[12]=new Array();
      iArray[12][0]="�������";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[13]=new Array();
      iArray[13][0]="�˱�����";         		//����
      iArray[13][1]="60px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
       
        
      GrpGrid = new MulLineEnter( "fmQuery" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount = 3;   
      GrpGrid.displayTitle = 1;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.selBoxEventFuncName = "getfee";          
      GrpGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //GrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
function initGrpPolFeeGrid()
{
      var iArray = new Array();
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="ʵ������";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="Ӧ������";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�ۿ۷���";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="���������";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="���۷�����";         	//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      GrpPolFeeGrid = new MulLineEnter( "fmQuery" , "GrpPolFeeGrid" ); 
      GrpPolFeeGrid.mulLineCount = 1;   
      GrpPolFeeGrid.displayTitle = 1;
      GrpPolFeeGrid.hiddenPlus = 1;
      GrpPolFeeGrid.hiddenSubtraction = 1;
      GrpPolFeeGrid.locked = 1;
      GrpPolFeeGrid.canSel = 0;
      GrpPolFeeGrid.loadMulLine(iArray);    
}



// ������Ϣ�б�ĳ�ʼ��
function initPlanGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��Ʒ��ϱ���";
		iArray[1][1]="80px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��Ʒ�������";
		iArray[2][1]="160px";
		iArray[2][2]=50;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�����ڼ�";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="Ӧ������";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;

		iArray[5]=new Array();
		iArray[5][0]="�α�����";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="Ԥ�ڱ���";
		iArray[6][1]="0px";
		iArray[6][2]=20;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="���Ѻϼ�";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="Ͷ������";
		iArray[8][1]="140px";
		iArray[8][2]=20;
		iArray[8][3]=0;
		

		PlanGrid = new MulLineEnter( "fmQuery" , "PlanGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PlanGrid.mulLineCount = 0;
		PlanGrid.displayTitle = 1;
		PlanGrid.selBoxEventFuncName = "getPlanRiskDetail"; 
		PlanGrid.canSel =1;
		PlanGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		PlanGrid.hiddenSubtraction=1;
		PlanGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initPlanRiskGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ֱ���";
		iArray[1][1]="80px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="0px";
		iArray[2][2]=20;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���α���";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="100px";
		iArray[4][2]=20;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="���Ѻϼ�";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="����ϼ�";
		iArray[6][1]="60px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		

		PlanRiskGrid = new MulLineEnter("fmQuery","PlanRiskGrid");
		//��Щ���Ա�����loadMulLineǰ
		PlanRiskGrid.mulLineCount = 0;
		PlanRiskGrid.displayTitle = 1;
		PlanRiskGrid.canSel =0; 
		PlanRiskGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		PlanRiskGrid.hiddenSubtraction=1;
		PlanRiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
