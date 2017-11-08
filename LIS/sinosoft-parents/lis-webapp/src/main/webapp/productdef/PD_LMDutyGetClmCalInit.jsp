<%@include file="../i18n/language.jsp"%>
<script type="text/javascript">

var turnPage = new turnPageClass();
function initForm()
{
	try{
		initRiskGrid();
	}catch(re){
		myAlert("HTPeopServManaInit.jsp-->"+"初始化界面错误!");
	}
}

function initRiskGrid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种编码";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种名称";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
		
		RiskGrid.mulLineCount=1;
		RiskGrid.displayTitle=1;
		RiskGrid.canSel=1;
		RiskGrid.canChk=0;
		RiskGrid.hiddenPlus=1;
		RiskGrid.hiddenSubtraction=1;
		
		RiskGrid.selBoxEventFuncName="ShowDetail";

		RiskGrid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}

//查  询按钮
function QueryRisk()
{
   var strSQL = "select distinct RiskNo,'测试' from RiskState a where 1=1 " + getWherePart('a.RiskNo', 'RiskNo');
   turnPage.queryModal(strSQL,RiskGrid);
}
	

function ShowDetail()
{
	var checkFlag = 0;
	checkFlag = RiskGrid.getSelNo();
	
	if (checkFlag>0){
		var RiskNo = RiskGrid.getRowColData(checkFlag-1, 1);
		if(RiskNo == null || RiskNo == "" ){
			myAlert(""+"请先选择一条个人险种信息!");
			return false;
		}
		
		initRiskState("RiskStateGrid",RiskNo);

	}else{
		myAlert(""+"请先选择一条个人险种信息!");
		return false;
	}
}
	
function initRiskState(tGrid,RiskNo)
{
	try{
		var showHTML = "<table>";
		var strSQL = "select NodeCode,ParentNodeCode,NodeName,NodeUrl,NodeState,NodeOrder from RiskState where 1=1 and ParentNodeCode = '0' and RiskNo='" + RiskNo + "'";
		var titleResult = easyExecSql(strSQL);
		
		var titleHTML = "<tr class=common>";
		var colCount = titleResult.length;
		for(var i=0;i<colCount;i++){
			titleHTML = titleHTML + "<td>";
			titleHTML = titleHTML + "<input type=\"checkbox\" name='c" + titleResult[i][0] + "' disabled=true >&nbsp;" ;
			titleHTML = titleHTML + "<input class=readonly4 name=t" + titleResult[i][0]+" readonly="readonly" value='"+titleResult[i][2]+"' style=\"width: 150px;font-weight: bold;\">";
			titleHTML = titleHTML + "</td>";
		}
		titleHTML = titleHTML + "</tr>";
	
		strSQL = "select count(1),ParentNodeCode pc from riskstate where RiskNo='100000' and ParentNodeCode <> '0' group by parentnodecode order by pc desc";
		var rowResult = easyExecSql(strSQL);
		
		var rowCount = rowResult[0][0];
		for(var i=0;i<rowCount;i++){
			titleHTML = titleHTML + "<tr class=common>";
			for(var j=0;j<colCount;j++){
				titleHTML = titleHTML + "<td>";
				titleHTML = titleHTML + "<input type=\"checkbox\" name='c" + titleResult[j][0] + "" + (i+1) + "' disabled=true  style=\"display:none\">&nbsp;" ;
				titleHTML = titleHTML + "<input type=button class=readonly4 name=t" + titleResult[j][0]+ "" + (i+1) + " readonly="readonly" value='' disabled=true style=\"width: 150px;display:none;text-align:left\">";
				titleHTML = titleHTML + "</td>";
			}
			titleHTML = titleHTML + "</tr>";
		}
	
		showHTML = showHTML + titleHTML  +"</table>";
		document.getElementById(tGrid).innerHTML = showHTML;
		
		var strSQL = "select NodeCode,ParentNodeCode,NodeName,NodeUrl,NodeState,NodeOrder,to_char(rownumber() over(partition by ParentNodeCode order by NodeOrder))  "
			+" from RiskState where 1=1 and ParentNodeCode <> '0' and RiskNo='" + RiskNo + "' order by  ParentNodeCode ,NodeOrder";
		var arrResult = easyExecSql(strSQL);
		for(var i=0;i<arrResult.length;i++){
			document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).value = "    "+arrResult[i][2] ;
			if(arrResult[i][4] == "1"){
				document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).checked = true ;
			} else {
				if(arrResult[i][3] != null && arrResult[i][3] != ""){
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).disabled = false;
					var NodeUrl = arrResult[i][3];
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).onclick = function(){OpenRisk(NodeUrl);};   	
				}	
			}
		}		
	
		for(var i=0;i<rowResult.length;i++){
			var flag = true;
			for(var j=0;j<rowResult[i][0];j++){
				if(document.getElementById("c"+arrResult[i][1] + "" + (j+1)).checked == false){
					flag = false;
					break;
				}
			}	
			
			if(flag == true){
				document.getElementById("c"+arrResult[i][1]).checked = true;
			}	
		}
	}catch(re){
		myAlert(re);
	}
}
	
function OpenRisk(NodeUrl)
{
		window.open(NodeUrl, "");
	
}
</script>