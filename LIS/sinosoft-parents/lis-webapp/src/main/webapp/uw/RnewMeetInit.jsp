<%
//�������ƣ�MeetInit.jsp
//�����ܣ�
//�������ڣ�2003-03-04
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
GlobalInput globalInput = (GlobalInput)session.getValue("GI");
String strManageCom = globalInput.ComCode;
%>
<script language="JavaScript">
var PolGrid;	//����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		fm.reset();
	}
	catch(ex)
	{
		alert("��MeetInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initPolGrid();
		document.all('ManageCom').value = <%=strManageCom%>;
	}
	catch(re)
	{
		alert("MeetInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

// Ͷ������Ϣ�б��ĳ�ʼ��
function initPolGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="֪ͨ���";
		iArray[1][1]="140px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="ӡˢ��";
		iArray[2][1]="140px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�����˱���";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=2;
		iArray[3][4]="AgentCode";
		iArray[3][5]="3";
		iArray[3][9]="�����˱���|code:AgentCode&NOTNULL";
		iArray[3][18]=250;
		iArray[3][19]= 0 ;
	

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;
		iArray[4][4]="station";
		iArray[4][5]="3";
		iArray[4][9]="��������|code:station&NOTNULL";
		iArray[4][18]=250;
		iArray[4][19]= 0 ;
		
		iArray[5]=new Array();
	  iArray[5][0]="��������";         	//����
	  iArray[5][1]="100px";            	//�п�
	  iArray[5][2]=100;            			//�����ֵ
	  iArray[5][3]=2; 
	  iArray[5][4]="SaleChnl";              	        //�Ƿ����ô���:null||""Ϊ������
    iArray[5][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
    iArray[5][9]="��������|code:SaleChnl&NOTNULL";
    iArray[5][18]=250;
    iArray[5][19]= 0 ;	
	
		
		iArray[6]=new Array();
		iArray[6][0]="�������������";  
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="���������������";
		iArray[7][1]="0px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="OldPrtSeq";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=3;


		PolGrid = new MulLineEnter( "fmSave" , "PolGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PolGrid.mulLineCount = 10;
		PolGrid.displayTitle = 1;
		PolGrid.canSel = 1;
		PolGrid.hiddenPlus = 1;
		PolGrid.hiddenSubtraction = 1;
		PolGrid.locked = 1;
		PolGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>