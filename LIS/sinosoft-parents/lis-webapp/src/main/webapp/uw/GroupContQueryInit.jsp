<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UnderwriteInit.jsp
//�����ܣ������˹��˱�
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<%
//���ҳ��ؼ��ĳ�ʼ����
String tGrpContNo="";
tGrpContNo=request.getParameter("GrpContNo");

GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

if(tGI == null) {
	out.println("session has expired");
	return;
}

String strOperator = tGI.Operator;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    document.all('QContNo').value = '';
    document.all('QManageCom').value = '';
    document.all('QInsuredName').value = '';
    codeSql="code";
  }
  catch(ex)
  {
    alert("��PEdorManuUWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ����������Ϣ��ʾ��ĳ�ʼ��������¼���֣�
function initPolBox()
{ 

  try
  {                                   
	// ������ѯ����
  }
  catch(ex)
  {
    alert("��ManuUWInit.jsp-->InitPolBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm(cGrpContNo)
{
  try
  {
    initInpBox();
    initPolBox();    
    initPolGrid();   
    initPolAddGrid();
		document.all("GrpContNo").value = cGrpContNo;
		document.all("ContNo").value = "";
		document.all("PrtNo").value = "";
		document.all("ProposalContNo").value = "";
		easyQueryClick(cGrpContNo);
		//ctrlButtonDisabled();
  }
  catch(re)
  {
    alert("��ManuUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����Ͷ������";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[3]=new Array();
      iArray[3][0]="��ͬ����";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=200;                 	//�����ֵ
      iArray[3][3]=2;                     	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][10]="aaa";
      iArray[3][11]="0|^1|���˺�ͬ��^2|�����ͬ��";
      

      iArray[4]=new Array();
      iArray[4][0]="�˱�����";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[4][10]="condition";              			
//	  iArray[4][11]="0|^1|�ܱ�^4|ͨ�ڳб�^9|�����б�^5|�Ժ˲�ͨ��^z|�˱��������";   

      iArray[5]=new Array();
      iArray[5][0]="������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][4]="Station";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[6][5]="6";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

      iArray[7]=new Array();
      iArray[7][0]="�����������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
     
      iArray[8]=new Array();
      iArray[8][0]="�������������";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
     
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);     
      
      PolGrid. selBoxEventFuncName = "easyQueryAddClick";
      
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
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="����Ͷ������";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ӡˢ��";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ֱ���";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=80;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�˱�����";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[5][10]="condition";              			
//	  iArray[5][11]="0|^1|�ܱ�^4|ͨ�ڳб�^9|�����б�^5|�Ժ˲�ͨ��";   

      iArray[6]=new Array();
      iArray[6][0]="Ͷ����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="������";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="����";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="����";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PolAddGrid = new MulLineEnter( "fm" , "PolAddGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolAddGrid.mulLineCount = 3;   
      PolAddGrid.displayTitle = 1;
      PolAddGrid.locked = 1;
      PolAddGrid.canSel = 1;
      PolAddGrid.hiddenPlus = 1;
      PolAddGrid.hiddenSubtraction=1;
      PolAddGrid.loadMulLine(iArray);       
      //PolAddGrid.selBoxEventFuncName = "makeWorkFlow";
      PolAddGrid.selBoxEventFuncName = "InitUWFlag";
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
function initPlanAddGrid()
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
      iArray[1][0]="��Ʒ��ϱ���";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="�ƻ�����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������";         		//����
      iArray[4][1]="80px";            		//�п�
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
      iArray[7][0]="����";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PlanAddGrid = new MulLineEnter("fm","PlanAddGrid"); 
      //��Щ���Ա�����loadMulLineǰ
      PlanAddGrid.mulLineCount = 3;   
      PlanAddGrid.displayTitle = 1;
      PlanAddGrid.locked = 1;
      PlanAddGrid.canSel = 1;
      PlanAddGrid.hiddenPlus = 1;
      PlanAddGrid.hiddenSubtraction=1;
      PlanAddGrid.loadMulLine(iArray);       
      PlanAddGrid.selBoxEventFuncName = "InitPlanUWFlag";
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
