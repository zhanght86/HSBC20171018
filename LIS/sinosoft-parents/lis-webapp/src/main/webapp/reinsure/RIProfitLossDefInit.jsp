<%@include file="/i18n/language.jsp"%>
 <%
  //�������ƣ�RIProfitLossDefInit.jsp
  //�����ܣ�ӯ��Ӷ����
  //�������ڣ�2011/8/18
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm(){
	try{
		fm.RIProfitNo.value="";
		fm.RIProfitName.value="";
		fm.RIcomCode.value="";
		fm.RIcomName.value="";
		fm.ContNo.value="";
		fm.ContName.value="";
		fm.RIProfitDes.value="";
		fm.RelaType.value="1";
		fm.RelaTypeName.value="�ٱ���������";
		
		initMul9Grid();
	}
	catch(re){
		myAlert("RIProfitLossDefInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMul9Grid(){
	var iArray = new Array();
	try{

		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�ٱ���������";
		iArray[1][1]="90px";
		iArray[1][2]=100;
		iArray[1][3]=2;
		iArray[1][4]="riprecept"; 
		iArray[1][5]="1|2"; 	 			//�����ô���ֱ���ڵ�1��2
		iArray[1][6]="0|1";	
		iArray[1][19]=1; 

		iArray[2]=new Array();
		iArray[2][0]="�ٱ���������";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;


		Mul9Grid = new MulLineEnter( "fm" , "Mul9Grid" ); 

		Mul9Grid.mulLineCount=0;
		Mul9Grid.displayTitle=1;
		Mul9Grid.canSel=1;
		Mul9Grid.canChk=0;
		Mul9Grid.hiddenPlus=0;
		Mul9Grid.hiddenSubtraction=0;

		Mul9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
