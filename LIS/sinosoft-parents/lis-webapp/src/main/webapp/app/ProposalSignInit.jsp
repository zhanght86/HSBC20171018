<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
function initInpBox() {
	try {
		// 保单查询条件
		//document.all('ContNo').value = '';
		//document.all('ManageCom').value = '';
		//document.all('AgentCode').value = '';
		//document.all('RiskCode').value = '';
		//document.all('PrtNo').value = '';
		//document.all('EnterAccFlag').value = '';
		//document.all('EnterAccFlagName').value = '';
	} catch (ex) {
		alert("在ProposalQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm() {
	try {
		initInpBox();
		initPublicPool();
	} catch (re) {
		alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

function initPublicPool(){
		var config = {
				functionId : "10010042",
				public : {
					query : {
						queryButton : {
						},
						arrayInfo : {
							col : {
								result0 : {
									title : "合同号",
									style : 3
								},
								result1 : {
									title : "印刷号",
									colNo : 1,
									style : 1
								},
								result2 : {
									title : "业务员编码",
									style : 2,
									refercode1 : "agentcode",
									addCondition : function(colName, value) {
										return " and exists (select 1 from lccont where lccont.contno = missionprop2 and lccont.agentcode = '"+value+"')";
										},
									colNo : 4
								},
								result3 : {
									title : "投保人编码",
									style : 3
								},
								result4 : {
									title : "投保人",
									style : 3
								},
								result5 : {
									title : "核保完成日期",
									style : 8,
									colNo : 5
								},
								result6 : {
									title : "管理机构",
									colNo : 2,
									refercode1 : "station",
									addCondition : function(colName, value) {return " and " + colName + " like '" + value+ "%' ";}
								},
								result7 : {
									title : "上报流向",
									style : 3
								},
								newColSaleChnl : {
									title : "销售渠道",
									colNo : 3,
									style : 2,
									refercode1 : "salechnl",
									addCondition : function(colName,value){
										return " and exists (select 1 from lccont where lccont.contno = missionprop2 and lccont.salechnl = '"+value+"')";
									}
								},
								newColRiskCode : {
									title : "险种编码",
									refercode1 : "riskcode",
									style : 2,
									addCondition : function(colName,value){
										return " and exists (select 1  from lcpol where  lcpol.contno = missionprop1  and lcpol.riskcode = '"+value+"')";
									},
									colNo : 6
								},
								newColArrival : {
									title : "是否到账",
									refercode1 : "yesno",
									style : 2,
									addCondition : function (colName,value){
										if(value == "N"){
											return " and not exists (select 1 from ljtempfee where otherno = missionprop1 and enteraccdate is not null and confflag = '0')";
										}else if(value == "Y"){
											return " and exists (select 1 from ljtempfee where otherno = missionprop1 and enteraccdate is not null and confflag = '0')";
										}
									},
									colNo : 7
								}
								
							}	 
						}
					},
					result : {
						condition : {
							"0" : false,
							"1" : false,
							"2" : false
						},
						canSel : 0,
						canChk : 1,
						mulLineCount : 1,
						arrayInfo : {
							col : {
								col5 : "0",
								col6 : "0",
								col7 : "0",
								result0 : {
									title : " 合同号 ",
									style : 3
								},
								result1 : {
									title : " 印刷号 ",
									style : 0,
									colNo : 1
								},
								result2 : {
									title : "业务员编码",
									colNo : 5,
									style : 0
								},
								result3 : {
									title : "投保人编码",
									style : 3
								},
								result4 : {
									title : " 投保人 ",
									colNo : 2
								},
								result5 : {
									title : "核保完成日期",
									colNo : 3,
									style : 0
								},
								result6 : {
									title : " 管理机构 ",
									colNo : 4
								},
								result7 : {
									title : " 上报流向 ",
									style : 3
								}
							}
						}
					}
				},
				private : {
					show : false
				},
				midContent :{
					id : "MidContent",
					show : true,
					html : "<INPUT VALUE='签发保单' class= cssButton name=signbutton TYPE=button onclick='signPol()'>"
				}
			};
			jQuery("#PublicPool").workpool(config);
}
</script>
