<%
//�������ƣ�GrpContPolInit.jsp
//�����ܣ�
//�������ڣ�2006-04-10 17:09
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
function nullToEmpty(string)
{
    if ((string == null) || (string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

function initInpBox()
{
	try 
	{
		document.all('GrpContNo').value = nullToEmpty("<%=request.getParameter("GrpContNo")%>");
		document.all('ContNo').value = nullToEmpty("<%=request.getParameter("ContNo")%>");
		document.all('PrtNo').value = nullToEmpty("<%=request.getParameter("PrtNo")%>");
		document.all('ScanType').value = nullToEmpty("<%=request.getParameter("scantype")%>");
		document.all('ManageCom').value = nullToEmpty("<%=tGI.ManageCom%>");
	}
	catch(ex)
	{
		alert("��GroupPolInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
    	initInpBox();
		initRiskGrid();
		initMultiAgentGrid();		
		initQuery();
	}
	catch(re)
	{
		alert("GroupPolInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re);
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initRiskGrid()
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
		iArray[1][1]="60px";
		iArray[1][2]=20;
		iArray[1][3]=2;
		iArray[1][4]="RiskCode";
		iArray[1][5]="1|2";
		iArray[1][6]="0|1";
		iArray[1][9]="���ֱ���|code:RiskCode&NOTNULL";
		iArray[1][18]=300;
		//iArray[1][19]=1;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="160px";
		iArray[2][2]=20;
		iArray[2][3]=0;
		//iArray[2][9]="��������|NOTNULL";

		iArray[3]=new Array();
		iArray[3][0]="�����ڼ�";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=1;
		iArray[3][9]="���Ѻϼ�|NUM&NOTNULL";

		iArray[4]=new Array();
		iArray[4][0]="Ӧ������";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;
		iArray[4][9]="Ԥ�ƽ��ѽ��|NUM&NOTNULL";

		iArray[5]=new Array();
		iArray[5][0]="�α�����";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		iArray[5][9]="���ѽ��|NUM&NOTNULL";

		iArray[6]=new Array();
		iArray[6][0]="����/����ϼ�";
		iArray[6][1]="120px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		iArray[6][9]="���Ѻϼ�|NUM&NOTNULL";

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" );
		//��Щ���Ա�����loadMulLineǰ
		RiskGrid.mulLineCount = 0;
		RiskGrid.displayTitle = 1;
		RiskGrid.canChk =1;
		RiskGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		RiskGrid. hiddenSubtraction=1;
		RiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initMultiAgentGrid(){
    var iArray = new Array();
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ�
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ҵ��Ա����";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][7]="queryAgentGrid";  ��//˫�����ò�ѯҵ��Ա�ĺ���          

      iArray[2]=new Array();
      iArray[2][0]="ҵ��Ա����";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="�����ֲ�";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[5]=new Array();
      iArray[5][0]="ҵ������(С��)";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=150;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�����ֲ�/Ͻ��";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=150;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=150;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 


      MultiAgentGrid = new MulLineEnter( "fm" , "MultiAgentGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      MultiAgentGrid.mulLineCount = 0;   
      MultiAgentGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MultiAgentGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
	
}	
</script>
