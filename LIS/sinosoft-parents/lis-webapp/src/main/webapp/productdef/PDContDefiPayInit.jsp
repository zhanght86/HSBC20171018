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
	
		initMulline9Grid();
		queryMulline9Grid();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		
		var paras = new Array();
		paras[0] = new Array();
		paras[0][0] = "RiskCode"; // sql�����"@@"�м�Ĳ�������
		paras[0][1] = fm.all("RiskCode").value; // ����ֵ
				
		// pageNo:ҳ��ı�ţ���Ψһȷ����ҳ��; eleType:ҪУ���Ԫ�ص����ͣ���Ϊ��; paras:sql����в������ƺ�ֵ�Ķ�ά����
		customDisplay(fm.PageNo.value, "button", paras);
	}
	catch(re){
		myAlert("PDContDefiEntryInit.jsp-->"+"��ʼ���������!");
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
		iArray[3][0]="�ɷ����α���";
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
</script>
