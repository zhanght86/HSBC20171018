<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDIssueQueryInit.jsp
  //�����ܣ������¼��
  //�������ڣ�2009-4-3
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm() 
{
	try{
	    isshowbutton();
		fm.all('RiskCode').value ="<%=request.getParameter("riskcode")%>";
		showOneCodeName("pdriskdefing","RiskCode","RiskCodeName");
		//���ݽ���Ľ����ṩ���������ĸ�λ
		fm.all('BackPost').value ="<%=request.getParameter("postcode")%>";
		showOneCodeName("pd_issuepost","BackPost","BackPostName1");
		fm.all('SerialNo').value ='';
		fm.all('FindFlag').value="<%=request.getParameter("findflag")%>";
		showCodeName();
		initMulline10Grid();
		QueryIssue();
		fm.all('hiddenRiskCode').value =fm.all('RiskCode').value;
		fm.all('hiddenBackPost').value =fm.all('BackPost').value;
		if(fm.all('FindFlag').value!='1'){
			fm.all('RiskCode').readonly="readonly";
			fm.all('BackPost').readonly="readonly";	
		}
	}
	catch(re){
		myAlert("PDIssueQueryInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��������к�";
		iArray[1][1]="0px";
		iArray[1][2]=100;
		iArray[1][3]=3;
		
		iArray[2]=new Array();
		iArray[2][0]="���ظ�λ";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���������";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�����״̬";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="���������ʱ��";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="����·����";
		iArray[6][1]="75px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="�ظ���";
		iArray[7][1]="60px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="�ظ�����";
		iArray[8][1]="100px";
		iArray[8][2]=100;
		iArray[8][3]=0;

		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
