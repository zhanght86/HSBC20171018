<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RIAthSchemeAssInit.jsp
  //�����ܣ������㷨����
  //�������ڣ�2011/6/17
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<% 
   String ArithmeticDefID =request.getParameter("ArithmeticDefID");
   String RISolType = request.getParameter("RISolType");
   
   String ArithmeticDefName=(request.getParameter("ArithmeticDefName")+"").trim();
   String RISolTypeName = (request.getParameter("RISolTypeName")+"").trim();
   
   ArithmeticDefName=new String((ArithmeticDefName).getBytes("ISO-8859-1"),"UTF-8");
   RISolTypeName=new String((RISolTypeName).getBytes("ISO-8859-1"),"UTF-8");
   
   System.out.println(RISolType);
%>

<script type="text/javascript">

function initForm()
{
	try{

		fm.ArithmeticDefName.value = "<%=ArithmeticDefName %>";
		fm.ArithmeticDefID.value = "<%=ArithmeticDefID%>";
		fm.RISolType.value = "<%=RISolType%>";
		fm.RISolTypeName.value = "<%=RISolTypeName %>";

		initMul9Grid();

		if(fm.RISolType.value=="01"||fm.RISolType.value=="02")
		{
			divTitle1.style.display = "";
			divInput1.style.display = "";
			divTitle2.style.display = "none";
			divInput2.style.display = "none";
		}
		else
		{
			divTitle1.style.display = "none";
			divInput1.style.display = "none";
			divTitle2.style.display = "";
			divInput2.style.display = "";
		}
	}
	catch(re){
		myAlert("RIAthSchemeAssInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMul9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��������";
		iArray[1][1]="30px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="����";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="����";
		iArray[3][1]="80px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�㷨��������";
		iArray[4][1]="80px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�㷨��������";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;


		Mul9Grid = new MulLineEnter( "fm" , "Mul9Grid" ); 

		Mul9Grid.mulLineCount=1;
		Mul9Grid.displayTitle=1;
		Mul9Grid.canSel=1;
		Mul9Grid.canChk=0;
		Mul9Grid.hiddenPlus=1;
		Mul9Grid.hiddenSubtraction=1;

		Mul9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
