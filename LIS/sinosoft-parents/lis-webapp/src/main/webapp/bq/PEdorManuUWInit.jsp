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
    document.all('QEdorNo').value = '';
    document.all('QContNo').value = '';
    document.all('QManageCom').value = '';
    document.all('Operator').value = operator;
    document.all('State').value = '1';
    codeSql="code";
	fm.EdorNo.value = "";
	fm.ContNo.value = "";
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
    //document.all('EdorNo1').value = '';
    //document.all('ContNo1').value = '';
    //document.all('EdorType').value = '';
    //document.all('RiskCode').value = '';
    //document.all('RiskName').value = '';
    //document.all('PolNo').value = '';
    //document.all('InsuredNo').value = '';
    //document.all('InsuredName').value = '';
    //document.all('AppntNo').value = '';
    //document.all('AppntName').value = '';    
    //document.all('ChgPrem').value = '';
    //document.all('ChgAmnt').value = '';
    //document.all('GetMoney').value = '';
    //document.all('GetInterest').value = '';
    //document.all('ManageCom').value = '';
    //codeSql="code";
  }
  catch(ex)
  {
    alert("����PEdorManuUWInit.jsp-->InitPolBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolBox();    
    initEdorMainGrid();    
    initEdorItemGrid();
    if(edorAcceptNo!=""){
        initQuery();
    }
    
  }
  catch(re)
  {
    alert("��PEdorManuUWInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initEdorMainGrid()
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
      iArray[1][0]="��ȫ����������";         		//����
      iArray[1][1]="110px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���˺�ͬ������";         		//����
      iArray[2][1]="110px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Ч����";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[4]=new Array();
      iArray[4][0]="������������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�䶯����";         	//����
      iArray[5][1]="90px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�䶯����";         	//����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="���˷ѽ��";         	//����
      iArray[7][1]="90px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���˷���Ϣ";         		//����
      iArray[8][1]="70px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�������";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][4]="station";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[9][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[9][9]="�������|code:station&NOTNULL";
      iArray[9][18]=250;
      iArray[9][19]= 0 ;

      iArray[10]=new Array();
      iArray[10][0]="�����������";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="�������������";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
       
      
      EdorMainGrid = new MulLineEnter( "fm" , "EdorMainGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      EdorMainGrid.mulLineCount = 3;   
      EdorMainGrid.displayTitle = 1;
      EdorMainGrid.locked = 1;
      EdorMainGrid.canSel = 1;
      EdorMainGrid.hiddenPlus = 1;
      EdorMainGrid.hiddenSubtraction=1;
      EdorMainGrid.loadMulLine(iArray);     
      
      EdorMainGrid. selBoxEventFuncName = "easyQueryAddClick";
      
      //��Щ����������loadMulLine����
      //EdorMainGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initEdorItemGrid()
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
		  iArray[1][0]="��ȫ������";         		//����
		  iArray[1][1]="120px";            		//�п�
		  iArray[1][2]=100;            			//�����ֵ
		  iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		  iArray[2]=new Array();
		  iArray[2][0]="���˺�ͬ������";         		//����
		  iArray[2][1]="120px";            		//�п�
		  iArray[2][2]=100;            			//�����ֵ
		  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		  iArray[3]=new Array();
		  iArray[3][0]="��ȫ��Ŀ";         		//����
		  iArray[3][1]="70px";            		//�п�
		  iArray[3][2]=100;            			//�����ֵ
		  iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		  iArray[4]=new Array();
		  iArray[4][0]="������";         		//����
		  iArray[4][1]="90px";            		//�п�
		  iArray[4][2]=80;            			//�����ֵ
		  iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		  
		  iArray[5]=new Array();
		  iArray[5][0]="�������ֺ�";         		//����
		  iArray[5][1]="90px";            		//�п�
		  iArray[5][2]=80;            			//�����ֵ
		  iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		  
		  iArray[6]=new Array();
		  iArray[6][0]="�˱�״̬";         	//����
		  iArray[6][1]="120px";            		//�п�
		  iArray[6][2]=80;            			//�����ֵ
		  iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		  
		  EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" ); 
		  //��Щ���Ա�����loadMulLineǰ
		  EdorItemGrid.mulLineCount = 3;   
		  EdorItemGrid.displayTitle = 1;
		  EdorItemGrid.locked = 1;
		  EdorItemGrid.canSel = 1;
		  EdorItemGrid.hiddenPlus = 1;
		  EdorItemGrid.hiddenSubtraction=1;
		  EdorItemGrid.loadMulLine(iArray);       
		  EdorItemGrid.selBoxEventFuncName = "getEdorItemGridCho";
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>