<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RICataRiskInit.jsp
  //�����ܣ����ֱ���
  //�������ڣ�2011-6-29
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMul13Grid();
	}
	catch(re){
		myAlert("RICataRiskInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMul13Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ַ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="ά������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="��ˮ��";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=3;


		Mul13Grid = new MulLineEnter( "fm" , "Mul13Grid" ); 

		Mul13Grid.mulLineCount=1;
		Mul13Grid.displayTitle=1;
		Mul13Grid.canSel=1;
		Mul13Grid.hiddenPlus=1;
		Mul13Grid.hiddenSubtraction=1;
		Mul13Grid.selBoxEventFuncName ="showCateRate"; //��Ӧ�ĺ���������������   
		Mul13Grid.loadMulLine(iArray);
		


	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
