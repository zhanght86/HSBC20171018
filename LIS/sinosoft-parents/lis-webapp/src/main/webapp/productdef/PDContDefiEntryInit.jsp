<%@include file="../i18n/language.jsp"%>
 <%
  //�������ƣ�PDContDefiEntryInit.jsp
  //�����ܣ���Լ��Ϣ�������
  //�������ڣ�2009-3-13
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		fm.all("RequDate").value = "<%=request.getParameter("requdate")%>";		
		fm.all("MissionID").value = "<%=request.getParameter("missionid")%>";
		fm.all("SubMissionID").value = "<%=request.getParameter("submissionid")%>";
		fm.all("ActivityID").value = "<%=request.getParameter("activityid")%>";
		// fm.all("ContOpt").value = "<%=request.getParameter("contopt")%>";
		fm.all("PdFlag").value = "<%=request.getParameter("pdflag")%>";
		
		if("0" == "<%=request.getParameter("pdflag")%>"){
			fm.all("ContOpt").value = "query";
		}
		
		initMulline9Grid();
		queryMulline9Grid();
		
		initLMDutyParamGrid();
		queryLMDutyParam();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		
		var paras = new Array();
		paras[0] = new Array();
		paras[0][0] = "RiskCode"; // sql�����"@@"�м�Ĳ�������
		paras[0][1] = fm.all("RiskCode").value; // ����ֵ
				
		// pageNo:ҳ��ı�ţ���Ψһȷ����ҳ��; eleType:ҪУ���Ԫ�ص����ͣ���Ϊ��; paras:sql����в������ƺ�ֵ�Ķ�ά����
		customDisplay(fm.PageNo.value, "button", paras);
		var riskCode = "<%=request.getParameter("riskcode")%>";
		showButtons(riskCode);	
		isshowbutton();
	}
	catch(re){
		myAlert("PDContDefiEntryInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}

function updateDisplayState()
{
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('LMRisk') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 sql = "select displayorder,selectcode from Pd_Basefield where tablecode = upper('LMRisk') and isdisplay = 1 order by Pd_Basefield.Displayorder";
 var rowcode = easyExecSql(sql,1,1,1);

 var j = 0;
 for(var i = 0; i < rowCount; i++)
 {
  Mulline9Grid.setRowColDataCustomize(j,4,null,null,rowcode[i][1],"11");
  if(rowcode[i][1] != null && rowcode[i][1] != "")
  {
   j++;
  }
 }
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="0px";
		iArray[0][2]=200;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ֱ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���α���";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�ɷ���������";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;


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

function initLMDutyParamGrid()
{
	var iArray = new Array();
	try{
	    iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���δ���";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="����";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		
		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="����λ��";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=2;
		iArray[5][4]="pd_chooseflag";

		iArray[6]=new Array();
		iArray[6][0]="Ĭ��ֵ";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="�Ƿ���޸�";
		iArray[7][1]="50px";
		iArray[7][2]=100;
		iArray[7][3]=2;
		iArray[7][4]="pd_yesno";
		
		iArray[8]=new Array();
		iArray[8][0]="˵��";
		iArray[8][1]="50px";
		iArray[8][2]=100;
		iArray[8][3]=0;
		

		LMDutyParamGrid = new MulLineEnter( "fm" , "LMDutyParamGrid" ); 
            
		LMDutyParamGrid.mulLineCount=0;
		LMDutyParamGrid.displayTitle=1;
		LMDutyParamGrid.canSel=0;
		LMDutyParamGrid.canChk=0;
		LMDutyParamGrid.hiddenPlus=1;
		LMDutyParamGrid.hiddenSubtraction=1;
		//Mulline12Grid.selBoxEventFuncName ="updateDisplayState";
		//LMDutyParamGrid. addEventFuncName="setDutyCode";
		LMDutyParamGrid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
