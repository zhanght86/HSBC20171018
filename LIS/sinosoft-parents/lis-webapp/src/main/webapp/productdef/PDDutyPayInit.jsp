<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDDutyPayInit.jsp
  //�����ܣ����νɷѶ���
  //�������ڣ�2009-3-13
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.all("PayPlanCode").value = "<%=request.getParameter("payplancode")%>";
		initMulline9Grid();
		queryMulline9Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDDutyPayInit.jsp-->"+"��ʼ���������!");
	}
}

function initMulline9Grid()
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
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���Դ���";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="������������";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="����ֵ";
		iArray[4][1]="103px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�ٷ��ֶ�����";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="ҵ����Ա��ע";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function updateDisplayState()
{
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('PD_LMDutyPay') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('PD_LMDutyPay') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1);

 for(var i = 0; i < rowCount; i++)
 {
 		if(Mulline9Grid.getRowColData(i,2) == "PAYPLANCODE")
	 	{
			Mulline9Grid.setRowColDataCustomize(i,"4",fm.all("PayPlanCode").value,null,"readonly"); 
	 	}
	 	else
	 	{
 		 	var tContentSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from PD_LMDutyPay where PAYPLANCODE = '"+fm.all("PayPlanCode").value+"'";
   	  var tContent = easyExecSql(tContentSQL);
   	  if(tContent==null||tContent[0][0]=="null")
   	 {
	   	Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1]);
	   }else
	   {
	     Mulline9Grid.setRowColDataCustomize(i,4,tContent[0][0],null,rowcode[i][1]);
	   }
	  }
 }
}
function test()
{
myAlert("OK");
}
</script>
