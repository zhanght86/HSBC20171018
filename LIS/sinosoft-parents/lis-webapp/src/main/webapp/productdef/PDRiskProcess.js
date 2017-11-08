//程序名称：PDRequRisk.js
//程序功能：产品申请与查询
//创建日期：2009-3-12
//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass();
var RiskGridTurnPage = new turnPageClass();
//定义sql配置文件
//var tResourceName = "productdef.PDRiskQueryInputSql";

function ShowDetail(){
	//IE11-Null
	//fm.all('divQuery').style.display="";
	divQuery.style.display="";
	//var checkFlag = 0;
	//checkFlag = Mulline9Grid.getSelNo();
	var selNo = Mulline9Grid.getSelNo();
	//alert(selNo);
		if(selNo < 1){
			//alert('请先选择一条记录');
			return;
	}
		var RiskNo = Mulline9Grid.getRowColData(selNo-1,1);
			initRiskState("RiskStateGrid",RiskNo);
		/*
	if (checkFlag>0){
		var RiskNo = Mulline9Grid.getRowColData(checkFlag-1, 1);
	//	alert(RiskNo);
		if(RiskNo == null || RiskNo == "" ){
			alert("请先选择一条个人险种信息!");
			return false;
		}
	
	

	}else{
		alert("请先选择一条个人险种信息!");
		return false;
	}	*/
}
function initRiskState(tGrid,RiskNo)
{
	try{
		document.getElementById(tGrid).innerHTML = "";
		
		var showHTML = "<table cellpadding='0px' cellspacing='0px' >";
		var mySql=new SqlClass();
		var sqlid = "PDRiskQueryInputSql6";
		mySql.setResourceName("productdef.PDRiskQueryInputSql"); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara(RiskNo);//指定传入的参数
		var strSQL = mySql.getString();
		var titleResult = easyExecSql(strSQL);
		
		var titleHTML = "<tr>";
		var colCount = titleResult.length;
		for(var i=0;i<colCount;i++){
			titleHTML = titleHTML + "<td>";
			titleHTML = titleHTML + "<input type=\"checkbox\" id='c" + titleResult[i][0] + "' name='c" + titleResult[i][0] + "' disabled=true style=\"display:none;width:0px;boder:0px\">&nbsp;" ;
			titleHTML = titleHTML + "<input class=readonly4 id=t" + titleResult[i][0] + " name=t" + titleResult[i][0] + " readonly='readonly' value='"+speedConvert(titleResult[i][2])+"' style=\"width: 120px;font-weight: bold;height:30px;border:0px;text-align:center;line-height:30px\">";
			titleHTML = titleHTML + "</td>";
		}
		titleHTML = titleHTML + "</tr>";
	
		var mySql2=new SqlClass();
		var sqlid2 = "PDRiskQueryInputSql7";
		mySql2.setResourceName("productdef.PDRiskQueryInputSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(RiskNo);//指定传入的参数
		strSQL = mySql2.getString();
		var rowResult = easyExecSql(strSQL);
		
		var rowCount = rowResult[0][0];
		for(var i=0;i<rowCount;i++){
			titleHTML = titleHTML + "<tr>";
			for(var j=0;j<colCount;j++){
				titleHTML = titleHTML + "<td>";
				titleHTML = titleHTML + "<input type=\"checkbox\" id='c" + titleResult[j][0] + "" + (i+1) + "' name='c" + titleResult[j][0] + "" + (i+1) + "' disabled=true  style=\"display:none;width:0px;boder:0px\">&nbsp;" ;
				titleHTML = titleHTML + "<input class=readonly4 id=t" + titleResult[j][0]+ "" + (i+1) + " name=t" + titleResult[j][0]+ "" + (i+1) + " onclick='' readonly='readonly' value='' disabled=true style=\"width:120px;display:none;text-align:center;border:0px;height:35px;line-height:22px\">";
				titleHTML = titleHTML + "</td>";
			}
			titleHTML = titleHTML + "</tr>";
		}
	
		showHTML = showHTML + titleHTML  +"</table>";
		document.getElementById(tGrid).innerHTML = showHTML;
		
		var mySql3=new SqlClass();
		var sqlid3 = "PDRiskQueryInputSql8";
		mySql3.setResourceName("productdef.PDRiskQueryInputSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(RiskNo);//指定传入的参数
		var strSQL = mySql3.getString();
		var arrResult = easyExecSql(strSQL);
		for(var i=0;i<arrResult.length;i++){
			document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).value = speedConvert(arrResult[i][2]) ;
			//完成状态
			if(arrResult[i][4] == "1"){
				document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).checked = true ;
				document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).disabled = false;
//				document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/v-b22.png)";
				if( i == arrResult.length -1 || (i< arrResult.length -1 && arrResult[i][1] != arrResult[i+1][1]) ){
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/juxing2.png)";
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundRepeat = "no-repeat";
				} else {
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/v-b22.png)";
				}
			} else {
				
				if( i == arrResult.length -1 || (i< arrResult.length -1 && arrResult[i][1] != arrResult[i+1][1])){
//					alert("t"+arrResult[i][1] + "" + arrResult[i][6]);
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/juxing1.png)";
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundRepeat = "no-repeat";
				} else {
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/v-b2.png)";
				}
//				document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.backgroundImage = "url(../common/images/v-b2.png)";
				if(arrResult[i][3] != null && arrResult[i][3] != ""){
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).disabled = false;
					//tongmeng 2011-01-30 modify
					//非查询功能注销此属性
					//document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).setAttribute("NodeUrl",arrResult[i][3]);
					//document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).onclick = function(){OpenRisk(this);};   	
				}	
			}
		}		
		
		for(var i=0;i<rowResult.length;i++){
			var flag = true;
			for(var j=0;j<rowResult[i][0];j++){
				if(document.getElementById("c"+rowResult[i][1] + "" + (j+1)).checked == false){
					flag = false;
					break;
				}
			}	
			
			//设置标题行的背景
			if(flag == true){
				document.getElementById("c"+rowResult[i][1]).checked = true;
//				document.getElementById("t"+rowResult[i][1]).style.backgroundImage = "url(../common/images/h-b2.png)";
			}else{
//				alert(rowResult[i][1]);
			}
			document.getElementById("t"+rowResult[i][1]).style.backgroundImage = "url(../common/images/h-w2.png)";
//			document.getElementById("t契约业务控制").style.backgroundImage = "url(../common/images/h-b2.png)";
		}
	}catch(re){
		//alert(re);
	}
}
/*
function initRiskState(tGrid,RiskNo)
{
	try{
		var showHTML = "<table>";
		var strSQL = "select NodeCode,ParentNodeCode,NodeName,NodeUrl,NodeState,NodeOrder from RiskState where 1=1 and ParentNodeCode = '0' and RiskNo='" + RiskNo + "' order by NodeOrder";
		var titleResult = easyExecSql(strSQL);
		
		var titleHTML = "<tr class=common>";
		var colCount = titleResult.length;
		for(var i=0;i<colCount;i++){
			titleHTML = titleHTML + "<td>";
			titleHTML = titleHTML + "<input type=\"checkbox\" name='c" + titleResult[i][0] + "' disabled=true >&nbsp;" ;
			titleHTML = titleHTML + "<input class=readonly4 name=t" + titleResult[i][0] + " readonly='readonly' value='"+titleResult[i][2]+"' style=\"width: 120px;font-weight: bold;\">";
			titleHTML = titleHTML + "</td>";
		}
		titleHTML = titleHTML + "</tr>";
	
		strSQL = "select count(1) pc,ParentNodeCode from riskstate where RiskNo='" + RiskNo + "' and ParentNodeCode <> '0' group by parentnodecode order by pc desc";
		var rowResult = easyExecSql(strSQL);
		
		var rowCount = rowResult[0][0];
		for(var i=0;i<rowCount;i++){
			titleHTML = titleHTML + "<tr class=common>";
			for(var j=0;j<colCount;j++){
				titleHTML = titleHTML + "<td>";
				titleHTML = titleHTML + "<input type=\"checkbox\" name='c" + titleResult[j][0] + "" + (i+1) + "' disabled=true  style=\"display:none\">&nbsp;" ;
				titleHTML = titleHTML + "<input type=button class=readonly4 name=t" + titleResult[j][0]+ "" + (i+1) + " readonly='readonly' value='' disabled=true style=\"width: 120px;display:none;text-align:left\">";
				titleHTML = titleHTML + "</td>";
			}
			titleHTML = titleHTML + "</tr>";
		}
	
		showHTML = showHTML + titleHTML  +"</table>";
		document.getElementById(tGrid).innerHTML = showHTML;
		
		var strSQL = "select NodeCode,ParentNodeCode,NodeName,NodeUrl,NodeState,NodeOrder,to_char(row_number() over(partition by ParentNodeCode order by NodeOrder))  "
			+" from RiskState where 1=1 and ParentNodeCode <> '0' and RiskNo='" + RiskNo + "' order by  ParentNodeCode ,NodeOrder desc";
		var arrResult = easyExecSql(strSQL);
		for(var i=0;i<arrResult.length;i++){
			document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).style.display = "";
			document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).value = "  " + arrResult[i][2] ;
			if(arrResult[i][4] == "1"){
				document.getElementById("c"+arrResult[i][1] + "" + arrResult[i][6]).checked = true ;
			} else {
				if(arrResult[i][3] != null && arrResult[i][3] != ""){
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).disabled = false;
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).setAttribute("NodeUrl",arrResult[i][3]);
					document.getElementById("t"+arrResult[i][1] + "" + arrResult[i][6]).onclick = function(){OpenRisk(this);};
				}	
			}
		}		
		
		for(var i=0;i<rowResult.length;i++){
			var flag = true;
			for(var j=0;j<rowResult[i][0];j++){
				if(document.getElementById("c"+rowResult[i][1] + "" + (j+1)).checked == false){
					flag = false;
					break;
				}
			}	
			
			if(flag == true){
				document.getElementById("c"+rowResult[i][1]).checked = true;
			}	
		}
	}catch(re){
		alert(re);
	}
}*/
function OpenRisk(Node)
{
	window.open(Node.getAttribute("NodeUrl"), "");
	
}

function ShowRiskInfo()
{
	divQuery.style.display = "";
	var selno = Mulline9Grid.getSelNo();
	fm.queryRiskCode.value = Mulline9Grid.getRowColData(selno-1,1);
	QueryRisk();
}
