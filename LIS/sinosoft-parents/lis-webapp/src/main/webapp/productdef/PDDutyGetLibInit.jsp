<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDDutyGetLibInit.jsp
  //�����ܣ��������ο�
  //�������ڣ�2009-3-17
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		var type = "<%=request.getParameter("type")%>";
		if( type != null && type=='1' )
		{
			fm.all("divhiddenParam").style.display = "";
		}else{
			fm.all("divhiddenParam").style.display = "none";
		}
	
		initButtonDisplay();
		initMulline11Grid();
		initMulline10Grid();
		initMulline9Grid();
		queryMulline9Grid();
		updateDisplayState();
	}
	catch(re){
		myAlert("PDDutyGetLibInit.jsp-->"+"��ʼ���������!");
	}
}
function initButtonDisplay()
{
	if(top.opener == undefined)
	{
		fm.all("btnReturn").disabled = true;
		fm.all("btnSave").disabled = false;
	}
	else 
	{
		try
		{
			if(top.opener.location.href != null && top.opener.location.href.indexOf("PDRiskDutyRelaInput") > -1)
			{
				fm.all("btnSave").disabled = true;
				fm.all("btnEdit").disabled = true;
				fm.all("btnDele").disabled = true;
			}
			else
			{
				fm.all("btnReturn").disabled = true;
				if(top.opener.location.href.indexOf("PDRiskDefiInput.jsp") > -1){
					fm.all("btnReturn").disabled = false;
				}
			}
		}
		catch(ex)
		{
			fm.all("btnReturn").disabled = true;
		}
	}
}
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
 	if(fm.all("btnReturn").disabled != true)
 	{
    	Mulline9Grid.setRowColDataCustomize(i,4,null,null,"readonly");
 	}
 	else
 	{
 		if(Mulline9Grid.getRowColData(i,2) == "GETDUTYCODE2")
 		{
 			Mulline9Grid.setRowColDataCustomize(i,4,null,null,"readonly");
 		}
 		else
 		{
	        Mulline9Grid.setRowColDataCustomize(i,4,null,null,rowcode[i][1]);
	    }
    }
    
    if(Mulline9Grid.getRowColData(i,2) == "GETDUTYCODE2")
	{
	    fm.GetDutyCode2RowOfMulLine.value = i;
	}
 }
}

function initMulline11Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���������δ���";
		iArray[1][1]="105px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������������";
		iArray[2][1]="105px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		Mulline11Grid = new MulLineEnter( "fm" , "Mulline11Grid" ); 

		Mulline11Grid.mulLineCount=0;
		Mulline11Grid.displayTitle=1;
		Mulline11Grid.canSel=1;
		Mulline11Grid.canChk=0;
		Mulline11Grid.hiddenPlus=1;
		Mulline11Grid.hiddenSubtraction=1;
		Mulline11Grid.selBoxEventFuncName = "queryLib";		
		
		Mulline11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function queryLib()
{
	updateDisplayState2(Mulline11Grid,Mulline9Grid);
	
	queryRela();
}

function queryLib10()
{
	updateDisplayState2(Mulline10Grid,Mulline9Grid);
}
var dic;
function updateDisplayState2(obj,objTarget)
{
 var sql = "select count(1) from Pd_Basefield where tablecode = upper('" + fm.all("tableName").value + "') and isdisplay = 1";
 var result = easyExecSql(sql,1,1,1);
 var rowCount = result[0][0];
 
 var selNo = obj.getSelNo();
 if(dic == null || dic("GETDUTYCODE2") != obj.getRowColData(selNo - 1, 1))
 {
 	var arr = new Array();
 	arr[0] = new Array();
 	arr[0][0] = "GETDUTYCODE2";
 	arr[0][1] = obj.getRowColData(selNo - 1, 1);
	
	dic = GetDicOfTableByKeys(fm.all("tableName").value, arr);
 }

 for(var i = 0; i < rowCount; i++)
 {
	var tContent = dic((objTarget.getRowColData(i,2)).toUpperCase());

	objTarget.setRowColDataCustomize(i,4,tContent,null);
 }
 
 fm.btnSave.disabled = true;
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
		iArray[1][0]="���������δ���";
		iArray[1][1]="105px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�������ִ���";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="������������";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName = "queryLib10";		
		
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
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��������";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���Դ���";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="������������";
		iArray[3][1]="0px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="����ֵ";
		iArray[4][1]="126px";
		iArray[4][2]=100;
		iArray[4][3]=2;

		iArray[5]=new Array();
		iArray[5][0]="�ٷ��ֶ�����";
		iArray[5][1]="350px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="ҵ����Ա��ע";
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=3;


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
</script>
