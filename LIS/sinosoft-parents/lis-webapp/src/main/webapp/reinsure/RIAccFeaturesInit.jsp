<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RIAccFeaturesInit.jsp
  //�����ܣ��ֳ����ζ���-�ֱ�����
  //�������ڣ�2011/6/16
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%
    String tAccDefNo=request.getParameter("AccNo");  
    String tRIRiskCode=request.getParameter("RiskCode");    
    String tRIDutyCode=request.getParameter("DutyCode"); 
    String tAccDefName=request.getParameter("AccName");
    tAccDefName = new String((tAccDefName).getBytes("ISO-8859-1"),"UTF-8");
    
    String tRIRiskName=request.getParameter("RiskName");
    tRIRiskName = new String((tRIRiskName).getBytes("ISO-8859-1"),"UTF-8");
    
    String tRIDutyName=request.getParameter("DutyName");
    tRIDutyName = new String((tRIDutyName).getBytes("ISO-8859-1"),"UTF-8");
%> 

<script type="text/javascript">

function initForm()
{
	try
	{

		fm.AccDefNo.value ="<%=tAccDefNo%>";
		fm.RIRiskCode.value ="<%=tRIRiskCode%>";
		fm.RIDutyCode.value ="<%=tRIDutyCode%>";
		
		fm.RIRiskName.value ="<%=tRIRiskName%>";
		fm.AccDefName.value ="<%=tAccDefName%>";
		fm.RIDutyName.value ="<%=tRIDutyName%>";
		
		initMul10Grid();

		initData();
		
	}
	catch(re){
		myAlert("RIAccFeaturesInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMul10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ձ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=2;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��Ʒ���α���";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=2;

		iArray[4]=new Array();
		iArray[4][0]="��Ʒ��������";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		Mul10Grid = new MulLineEnter( "fm" , "Mul10Grid" ); 

		Mul10Grid.mulLineCount=0;
		Mul10Grid.displayTitle=1;
		Mul10Grid.canSel=0;
		Mul10Grid.canChk=0;
		Mul10Grid.hiddenPlus=0;
		Mul10Grid.hiddenSubtraction=0;

		Mul10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
