<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RIAccDistillInit.jsp
  //�����ܣ��ֳ����ζ���-���ݲɼ�
  //�������ڣ�2011/6/16
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%
    String tAccDefNo=request.getParameter("AccNo");   
    String tAccDefName=request.getParameter("AccName");
    tAccDefName = new String((tAccDefName).getBytes("ISO-8859-1"),"UTF-8");

%> 

<script type="text/javascript">

function initForm()
{
	try{
		fm.AccDefNo.value ="<%=tAccDefNo%>";
		fm.AccDefName.value ="<%=tAccDefName%>";
		
		initMul9Grid();
	}
	catch(re){
		myAlert("RIAccDistillInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][1]="60px";
		iArray[1][2]=50;
		iArray[1][3]=2;
	    iArray[1][4]="riathfeetype"; 
		iArray[1][5]="1|2"; 	 			//�����ô���ֱ���ڵ�1��2
		iArray[1][6]="0|1";	
		iArray[1][19]=1; 

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		Mul9Grid = new MulLineEnter( "fm" , "Mul9Grid" ); 

		Mul9Grid.mulLineCount=1;
		Mul9Grid.displayTitle=1;
		Mul9Grid.canSel=0;
		Mul9Grid.canChk=1;
		Mul9Grid.hiddenPlus=0;
		Mul9Grid.hiddenSubtraction=0;

		Mul9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

</script>
