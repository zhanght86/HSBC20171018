<%@include file="../i18n/language.jsp"%>

<%@page import="com.sinosoft.lis.certify.SysOperatorNoticeBL"%>
<%
	//�������ƣ�
	//�����ܣ�
	//�������ڣ�
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	String CurrentDate = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
	
	
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//���ҳ��ؼ��ĳ�ʼ����
%>
<script type="text/javascript">
function initForm()
{
  try
  {
 		initMulline9Grid();
 		//initRiskGrid();

  }
  catch(re)
  {
    myAlert(""+"InitForm�����з����쳣:��ʼ���������!");
  }
}

// ���շ���Ϣ�б�ĳ�ʼ��
function initMulline9Grid()
  {                               
    var iArray = new Array();      
    try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="����";      	   		//����
      iArray[1][1]="80px";            			//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="����";          		//����
      iArray[2][1]="200px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
 

      iArray[3]=new Array();
      iArray[3][0]="��������";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="״̬";      	   		//����
      iArray[4][1]="100px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[5]=new Array();
      iArray[5][0]="��ǰ������";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=3; 
      
       iArray[6]=new Array();
      iArray[6][0]="��������";      	   		//����
      iArray[6][1]="100px";            			//�п�
      iArray[6][2]=10;            			//�����ֵ
      iArray[6][3]=3; 
      
       iArray[7]=new Array();
      iArray[7][0]="�Ƿ��������";      	   		//����
      iArray[7][1]="100px";            			//�п�
      iArray[7][2]=10;            			//�����ֵ
      iArray[7][3]=1; 
      
    Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

	Mulline9Grid.mulLineCount=0;
	Mulline9Grid.displayTitle=1;
	Mulline9Grid.canSel=1;
	Mulline9Grid.canChk=0;
	Mulline9Grid.hiddenPlus=1;
	Mulline9Grid.hiddenSubtraction=1;

	Mulline9Grid.selBoxEventFuncName="ShowDetail";
	Mulline9Grid.loadMulLine(iArray);
    }
    catch(ex)
    {
      myAlert(ex);
    }
}

function initRiskGrid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ֱ���";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
		
		RiskGrid.mulLineCount=1;
		RiskGrid.displayTitle=1;
		RiskGrid.canSel=1;
		RiskGrid.canChk=0;
		RiskGrid.hiddenPlus=1;
		RiskGrid.hiddenSubtraction=1;
		
		RiskGrid.selBoxEventFuncName="ShowDetail";

		RiskGrid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
