<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDDutyPayAddFeeInit.jsp
  //�����ܣ��������μӷѶ���
  //�������ڣ�2009-3-13
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		//fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.RISKCODE.value="<%=request.getParameter("riskcode")%>";
		fm.all("DUTYCODE").value = "<%=request.getParameter("payplancode")%>";
		fm.all("PayPlanCode").value = "<%=request.getParameter("payplancode")%>";
		//alert('1');
		queryDutyCode();
		//initMulline9Grid();
		queryMulline9Grid();
		//alert('2');
		initMulline10Grid();
	//	alert('3');
		queryMulline10Grid();
	//	alert('4');
	//	updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		isshowbutton();
	}
	catch(re){
		myAlert("PDDutyPayAddFeeInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}
/*
function updateDisplayState()
{
 // rowCount:��ʾ���ֶ�����
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0]; 
 
 // rowcode:�������Ӧ��selectcode������
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('"+fm.all("tableName").value+"') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1); 

 var j = 0;
 
 for(var i = 0; i < rowCount; i++)
 {
     if(Mulline9Grid.getRowColData(i,2) == "RISKCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("RiskCode").value,null,"readonly"); 		 
	 }else if(Mulline9Grid.getRowColData(i,2) == "DUTYCODE")
	 {
		 Mulline9Grid.setRowColDataCustomize(i,4,fm.all("DutyCode").value,null,"readonly"); 		 
	 }
	 else 
	 {
	 	var selNo = Mulline10Grid.getSelNo();
 		if(selNo==0 || selNo == null)
 		{
 			Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1],"11");    
 		}else
 		{
 			if(Mulline9Grid.getRowColData(i,2) == "ADDFEETYPE")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,3),null,"readonly"); 		 
	 		}else if(Mulline9Grid.getRowColData(i,2) == "ADDFEEOBJECT")
	 		{
		 		Mulline9Grid.setRowColDataCustomize(i,4,Mulline10Grid.getRowColData(selNo-1,4),null,"readonly"); 		 
	 		}
	 		else
	 		{ 			
	 	 		var tDefaultValueSQL = "select "+Mulline9Grid.getRowColData(i,2)+" from "+fm.all("tableName").value+" where dutycode = '"+fm.all('DutyCode').value +"' and riskcode = '"+fm.all('RiskCode').value+"' and ADDFEETYPE = '"+Mulline10Grid.getRowColData(selNo-1,3)+"' and ADDFEEOBJECT = '"+Mulline10Grid.getRowColData(selNo-1,4)+"'";
   	 		var tContent = easyExecSql(tDefaultValueSQL);
   	 
   	 		var cData = null;
   	 		if(tContent!=null&&tContent[0][0]!="null")
   	 		{
   	 	 		cData = tContent[0][0];
   	 		}
     		Mulline9Grid.setRowColDataCustomize(i,4,cData,null,rowcode[i][1]);
     	}
    }
  }
  }
}*/
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
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=2;

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
		Mulline9Grid.canSel=0;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
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
		iArray[1][0]="���ֱ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���α���";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ӷ����";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�ӷѷ�ʽ";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�ӷ��㷨";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�ӷ��������ֵ";
		iArray[6][1]="0px";
		iArray[6][2]=0;
		iArray[6][3]=3;


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName ="getOneData";

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
