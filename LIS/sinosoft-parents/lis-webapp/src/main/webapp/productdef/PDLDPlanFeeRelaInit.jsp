<%
  //�������ƣ�PDLDPlanFeeRelaInit.jsp
  //�����ܣ��ۼ��������˵�����
  //�������ڣ�2016-5-22
  //������  ��������
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
function initForm1()
{
	try{
		initBox();
		var riskcode="<%=request.getParameter("riskcode")%>";
		document.all("RiskCode").value=riskcode;
		document.all("RiskCode1").value=riskcode;
		var getdutycode="<%=request.getParameter("getdutycode")%>";
		document.all("GetDutyCode").value=getdutycode;
		document.all("GetDutyCode1").value=getdutycode;
		initMulline1Grid();
		queryFeeInfo();
	}
	catch(re){
		alert("PDLDPlanFeeRelaInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}
/*�ۼ��������˵���Ϣ*/
function initMulline1Grid(){
	var iArray = new Array();
	var i=0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="���ϼƻ�����";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		iArray[++i]=new Array();
		iArray[i][0]="���ϼƻ���������";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="��Ʒ����";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="���δ���";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�������δ���";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;

		iArray[++i]=new Array();
		iArray[i][0]="�˵����������";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�˵��������������";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="���������";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�������������";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�Ƿ�۳��Ը�����";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�Ƿ�۳��Ը���������";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�Ը�����";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�����";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="������Ƚ������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="������ȴ�������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="���������������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="ÿ���⳥�������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="ÿ���⳥��������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="ÿ���⸶�������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="ÿ���⳥�̶����";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�ȴ���";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�ȴ��ڵ�λ";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�ȴ��ڵ�λ����";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		Mulline1Grid = new MulLineEnter( "fm1" , "Mulline1Grid" ); 

		Mulline1Grid.mulLineCount=5;
		Mulline1Grid.displayTitle=1;
		Mulline1Grid.canSel=1;
		Mulline1Grid.canChk=0;
		Mulline1Grid.hiddenPlus=1;
		Mulline1Grid.hiddenSubtraction=1;
		Mulline1Grid.selBoxEventFuncName ="showFeeCodeInfo";
		Mulline1Grid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
