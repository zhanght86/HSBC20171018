<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupPolPrintInit.jsp
//�����ܣ�
//�������ڣ�2002-11-26
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*" %>
<%
GlobalInput globalInput = (GlobalInput)session.getValue("GI");
String strManageCom = globalInput.ManageCom;
%>
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox(){
  try{
		 document.all('ManageCom').value = <%=tGI.ComCode%>;
		 if(document.all('ManageCom').value!=86)
     {
    	 document.all("divErrorMInfo").style.display="none";
     }
  }
  catch(ex)
  {
    alert("��GroupPolPrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
		initGrpContGrid();
		manageCom = '<%= strManageCom %>';
  }
  catch(re)
  {
    alert("GroupPolPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpContGrid(){
	var iArray = new Array();

	try{
		iArray[0]=new Array();
		iArray[0][0]="���";	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";	//�п�
		iArray[0][2]=10;	//�����ֵ
		iArray[0][3]=0;	//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[1]=new Array();
		iArray[1][0]="���屣����";
		iArray[1][1]="160px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="ӡˢ��";
		iArray[2][1]="120px";            	
		iArray[2][2]=100;       
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ŵ�����";
		iArray[3][1]="70px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="Ͷ��������";
		iArray[4][1]="260px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="��ӡ����������";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="ǩ������";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		GrpContGrid = new MulLineEnter( "fm" , "GrpContGrid" );
		//��Щ���Ա�����loadMulLineǰ
		GrpContGrid.mulLineCount = 5;
		GrpContGrid.displayTitle = 1;
		GrpContGrid.hiddenPlus = 1;
		GrpContGrid.hiddenSubtraction = 1;
		GrpContGrid.canSel = 0;
		GrpContGrid.canChk = 1;
		GrpContGrid.locked = 1;
		GrpContGrid.canChk = 1;
		GrpContGrid.loadMulLine(iArray);

		//��Щ����������loadMulLine����
		//GrpContGrid.setRowColData(1,1,"asdf");
	}
	catch(ex){
		alert(ex);
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initErrorGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";	//�п�
		iArray[0][2]=10;	//�����ֵ
		iArray[0][3]=0;	//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="140px";           
		iArray[1][2]=100;            	
		iArray[1][3]=0;
		
		
		iArray[2]=new Array();
		iArray[2][0]="������Ϣ";
		iArray[2][1]="400px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="����ʱ��";
		iArray[4][1]="80px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		ErrorGrid = new MulLineEnter( "fm" , "ErrorGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ErrorGrid.mulLineCount = 5;
		ErrorGrid.displayTitle = 1;
		ErrorGrid.hiddenPlus = 1;
		ErrorGrid.hiddenSubtraction = 1;
		ErrorGrid.canSel = 0;
		ErrorGrid.canChk = 0;
		ErrorGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}


</script>
