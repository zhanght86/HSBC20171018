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
    fmQuery.GrpProposalContNo.value = '';
//    fmQuery.GrpMainProposalNo.value = GrpMainPolNo;
    fmQuery.PrtNoHide.value = PrtNo;
    fmQuery.Operator.value = '<%= strOperator %>';    
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
    fmQuery.GrpContNo.value = GrpContNo;
    initGrpGrid();
  
		initGrpPolFeeGrid();       
	
    querygrp();
    //tongmeng 2009-04-10 add
		//���ư�ť�Ұ�
		//alert('1')
		ctrlButtonDisabled(GrpContNo,'a');
    showInfo1(); //ԭ����showInfo() ����showInfo�����ͻ,��ʱˢ�»ᱨ�� Ӧ�������ԭ�� 
  
    makeWorkFlow();
    checkPerFHButton();
   
   //alert(fmQuery.SaleChnl.value);
   //if(fmQuery.SaleChnl.value=="04" || fmQuery.SaleChnl.value=="06" || fmQuery.SaleChnl.value=="07")
   //{
   //  fmQuery.fee.disabled='';
   //}else{
   //  fmQuery.fee.disabled=true;
   //}
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
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="Ͷ����ӡˢ��";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����λ";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      //iArray[5]=new Array();
      //iArray[5][0]="�˱�����";         		//����
      //iArray[5][1]="60px";            		//�п�
      //iArray[5][2]=200;            			//�����ֵ
      //iArray[5][3]=2;  
      //iArray[5][10]="condtion";
      //iArray[5][11]="0|^1|�ܱ�^s|�ϸ����ʳб�^x|�¸����ʳб�^r|��Լ�б������ε���^m|��Լ�б���������^p|��Լ�б����⸶��������^q|��Լ�б�����������^9|��׼�б�";               			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�˱�����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=2;  
      iArray[5][10]="condtion";
      iArray[5][11]="0|^1|�ܱ�^4|ͨ�ڳб�^9|�����б�^5|�Ժ˲�ͨ��^s|�ϸ����ʳб�^x|�¸����ʳб�^r|��Լ�б������ε���^m|��Լ�б���������^p|��Լ�б����⸶��������^q|��Լ�б�����������^z|�˱��������";       

      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[7]=new Array();
      iArray[7][0]="�������ֺ���";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
       
        
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
      iArray[1][0]="Ӧ������";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="Ӧ������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�ۿ۷���";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="����";         		    //����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      GrpPolFeeGrid = new MulLineEnter( "fmQuery" , "GrpPolFeeGrid" ); 
      GrpPolFeeGrid.mulLineCount = 1;   
      GrpPolFeeGrid.displayTitle = 1;
      GrpPolFeeGrid.hiddenPlus = 1;
      GrpPolFeeGrid.hiddenSubtraction = 1;
      GrpPolFeeGrid.locked = 1;
      GrpPolFeeGrid.canSel = 0;
      GrpPolFeeGrid.loadMulLine(iArray);    
}

</script>
