<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDDutyGetClmInit.jsp
  //程序功能：责任给付生存
  //创建日期：2009-3-16
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initGetClaimDetail()
{
		//document.getElementById('GETDUTYCODE').value= '';
		//document.getElementById('GETDUTYNAME').value= '';
		document.getElementById('GETDUTYKIND').value= '';
		document.getElementById('INPFLAG').value= '';
		document.getElementById('STATTYPE').value= '';
		document.getElementById('STATTYPEName').value= '';
		document.getElementById('INPFLAGName').value= '';
		document.getElementById('CALCODE').value= '';
		document.getElementById('ObsPeriod').value= '';
		document.getElementById('DeadValiFlag').value= '';
		document.getElementById('DeadToPValueFlag').value= '';
		document.getElementById('DeadToPValueFlagName').value= '';
		document.getElementById('DeadValiFlagName').value= '';
		//fm.GetByHosDay.value ="";
		fm.STATTYPE.value ="";
		fm.AfterGet.value="";
		fm.STATTYPEName.value ="";
		fm.GetByHosDayName.value ="";
		fm.AfterGetName.value=""; 

		
		initCalCodeMain('','');
		queryMulline10Grid();
}

function initForm()
{
	try{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";
		var riskcode = "<%=request.getParameter("riskcode")%>";
		fm.all("GetDutyCode").value = "<%=request.getParameter("getdutycode2")%>";
		initMulline10Grid();
		queryMulline10Grid();
		//initMulline9Grid();
		queryMulline9Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
		
		initButton(riskcode);
		isshowbutton();
	}
	catch(re){
		myAlert("PDDutyGetClmInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}
function updateDisplayState()
{
	 var selNo = Mulline10Grid.getSelNo();
 	if( selNo == 0 || selNo == null )
 	{
 		return;	
 	}
 	else
 	{
 		//document.getElementById('GETDUTYCODE').value= Mulline10Grid.getRowColData(selNo-1,1);
		//document.getElementById('GETDUTYNAME').value= Mulline10Grid.getRowColData(selNo-1,2);
		//initGetClaimDetail();
		document.getElementById('GETDUTYKIND').value= '';
		document.getElementById('INPFLAG').value= '';
		document.getElementById('STATTYPE').value= '';
		document.getElementById('STATTYPEName').value= '';
		document.getElementById('INPFLAGName').value= '';
		document.getElementById('CALCODE').value= '';
		initCalCodeMain('','');
		document.getElementById('GETDUTYKIND').value= Mulline10Grid.getRowColData(selNo-1,3);
		document.getElementById('STATTYPE').value= Mulline10Grid.getRowColData(selNo-1,4);
		document.getElementById('CALCODE').value= Mulline10Grid.getRowColData(selNo-1,5);
		document.getElementById('ObsPeriod').value= Mulline10Grid.getRowColData(selNo-1,6);
		document.getElementById('GetByHosDay').value= Mulline10Grid.getRowColData(selNo-1,9);
		document.getElementById('AfterGet').value= Mulline10Grid.getRowColData(selNo-1,10);
		
		showOneCodeName('pd_stattype','STATTYPE','STATTYPEName');
		showOneCodeName('pd_getbyhosday','GetByHosDay','GetByHosDayName');
		showOneCodeName('pd_afterget','AfterGet','AfterGetName');
		fm.all("PdFlag").value = "<%=request.getParameter("pdflag")%>";
		initCalCodeMain(document.getElementById('RiskCode').value,document.getElementById('CALCODE').value);
 	}
}

function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="给付代码";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="给付名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="理赔类型";
		iArray[3][1]="50px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="赔付类型";
		iArray[4][1]="50px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="算法编码";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
       	iArray[6]=new Array();
		iArray[6][0]="观察期";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="每日住院给付";
		iArray[7][1]="90px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="给付后动作";
		iArray[8][1]="90px";
		iArray[8][2]=100;
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="每日住院给付";
		iArray[9][1]="0px";
		iArray[9][2]=100;
		iArray[9][3]=0;
		
		iArray[10]=new Array();
		iArray[10][0]="给付后动作";
		iArray[10][1]="0px";
		iArray[10][2]=100;
		iArray[10][3]=0;
		

		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName ="updateDisplayState";

		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="属性名称";
		iArray[1][1]="150px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="属性代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="属性数据类型";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="属性值";
		iArray[4][1]="104px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="官方字段描述";
		iArray[5][1]="500px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="业务人员备注";
		iArray[6][1]="90px";
		iArray[6][2]=100;
		iArray[6][3]=3;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
	Mulline9Grid.selBoxEventFuncName ="initClmCalCode";
		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initClmCalCode()
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert("请选中算法行再点击");
		return;
	}
	else
	{
		var name = Mulline9Grid.getRowColData(selNo-1,1); 
		if(name.indexOf("算法") == -1)
		{
			return;
		}
		name = Mulline9Grid.getRowColData(selNo-1,4); 
		//alert('name:'+name);
			var  tRiskCode=fm.all("RiskCode").value;
		initCalCodeMain(tRiskCode,name);
	}
	
}
function initButton(riskCode){
	/*var sql = "select RiskTypeDetail from pd_lmriskapp where riskcode = '" + riskCode + "'";
	var crr = easyExecSql(sql, 1, 1, 1);
	
	var RiskTypeDetail = crr[0][0];
	if(RiskTypeDetail == 'M'){
		document.getElementById("relaIssueId").style.display = '';
	}else{
		document.getElementById("relaIssueId").style.display = 'none';
	}
	*/
	document.getElementById("relaIssueId").style.display = '';
}
</script>
