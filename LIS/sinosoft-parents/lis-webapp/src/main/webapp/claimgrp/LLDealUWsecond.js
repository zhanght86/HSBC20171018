//程序名称：LLDealUWsecond.js
//程序功能：二核结论
//创建日期：2003-03-27 15:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var tturnPage = new turnPageClass();
var mySql = new SqlClass();


function clearPage()
{
 	fm.uwContState.value="";
 	fm.uwContStatename.value="";	
	fm.uwContIdea.value="";	
	fm.UWRiskState.value="";
	fm.UWRiskStateName.value="";
	fm.UWRiskIdea.value="";
	fm.HealthImpartNo1.value="";
	fm.HealthImpartNo2.value="";
	fm.NoImpartDesc.value="";
	fm.Remark1.value="";
}

function initQueryLLContGrid()
{
	var tInsuredNo = fm.InsuredNo.value;
	var tCaseNo = fm.CaseNo.value;
	/*var strSQL = "select caseno,batno,contno,appntno,appntname,state,claimrelflag,healthimpartno1,healthimpartno2,noimpartdesc,remark1 from llcuwbatch where 1=1 "
//	         + " and state = '1' "        
	         + " and caseno = '"+tCaseNo+"'" 
	         + " order by  batno,contno";*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLDealUWsecondInputSql");
mySql.setSqlId("LLDealUWsecondSql1");
mySql.addSubPara(tCaseNo); 
//	var arr=easyExecSql(strSQL);
	turnPage.pageLineNum=5;
	turnPage.queryModal(mySql.getString(), LLContGrid);
}

function LLContGridClick()
{
	clearPage();
	//显示 发起时输入的合同信息
	var tSel = LLContGrid.getSelNo()-1;	
	fm.HealthImpartNo1.value=LLContGrid.getRowColData(tSel,8);
	fm.HealthImpartNo2.value=LLContGrid.getRowColData(tSel,9);
	fm.NoImpartDesc.value=LLContGrid.getRowColData(tSel,10);
	fm.Remark1.value=LLContGrid.getRowColData(tSel,11);
	
	var tInsuredNo = fm.InsuredNo.value;
	var tCaseNo = fm.CaseNo.value;
	var tContNo =LLContGrid.getRowColData(tSel,3);
	//查询该合同最近的 核保结果（结论、意见）
	/*var strContSql="select passflag,uwidea from llcuwmaster where 1=1 "
			+" and caseno='"+ tCaseNo +"' "
			+" and contno='"+ tContNo +"'";*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLDealUWsecondInputSql");
mySql.setSqlId("LLDealUWsecondSql2");
mySql.addSubPara(tCaseNo); 		
mySql.addSubPara(tContNo); 	
	var arrCont=easyExecSql(mySql.getString());	
//	alert(arrCont);
    if(arrCont==null)
	{
	 	fm.uwContState.value="";	
		fm.uwContIdea.value="";	
	}
	else
	{
	 	fm.uwContState.value=arrCont[0][0];	
		fm.uwContIdea.value=arrCont[0][1];	
		showOneCodeName('contuwstate','uwContState','uwContStatename');   	
    }
	//[查询 该合同下的险种核保信息]
	/*var strSQL=" select caseno,contno,proposalcontno,uwno,polno,passflag,uwidea from lluwmaster where 1=1"
				+" and caseno='"+tCaseNo+"' "
				+" and contno='"+tContNo+"' "
				+" order by contno";*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLDealUWsecondInputSql");
mySql.setSqlId("LLDealUWsecondSql3");
mySql.addSubPara(tCaseNo); 		
mySql.addSubPara(tContNo); 	
	var arr=easyExecSql(mySql.getString());	          
//	tturnPage.queryModal(strSQL,LLPolGrid);
//    alert(arr);
    if(arr==null)
    {
    	LLPolGrid.clearData();
    }
	else
	{
		displayMultiline(arr, LLPolGrid);
	}
	
}

//险种核保结论具体信息----结论、核保意见
function LLPolGridClick()
{
	fm.UWRiskState.value="";
	fm.UWRiskStateName.value="";
	fm.UWRiskIdea.value="";
	var tSel = LLPolGrid.getSelNo()-1;	
	fm.UWRiskState.value=LLPolGrid.getRowColData(tSel,6);
	fm.UWRiskIdea.value=LLPolGrid.getRowColData(tSel,7);	
	showOneCodeName('uwstate','UWRiskState','UWRiskStateName');   	
}


function turnback()
{
	top.close();
}


