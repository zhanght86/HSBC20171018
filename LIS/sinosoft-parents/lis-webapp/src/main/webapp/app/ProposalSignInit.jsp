<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
function initInpBox() {
	try {
		// ������ѯ����
		//document.all('ContNo').value = '';
		//document.all('ManageCom').value = '';
		//document.all('AgentCode').value = '';
		//document.all('RiskCode').value = '';
		//document.all('PrtNo').value = '';
		//document.all('EnterAccFlag').value = '';
		//document.all('EnterAccFlagName').value = '';
	} catch (ex) {
		alert("��ProposalQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm() {
	try {
		initInpBox();
		initPublicPool();
	} catch (re) {
		alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
									title : "��ͬ��",
									style : 3
								},
								result1 : {
									title : "ӡˢ��",
									colNo : 1,
									style : 1
								},
								result2 : {
									title : "ҵ��Ա����",
									style : 2,
									refercode1 : "agentcode",
									addCondition : function(colName, value) {
										return " and exists (select 1 from lccont where lccont.contno = missionprop2 and lccont.agentcode = '"+value+"')";
										},
									colNo : 4
								},
								result3 : {
									title : "Ͷ���˱���",
									style : 3
								},
								result4 : {
									title : "Ͷ����",
									style : 3
								},
								result5 : {
									title : "�˱��������",
									style : 8,
									colNo : 5
								},
								result6 : {
									title : "�������",
									colNo : 2,
									refercode1 : "station",
									addCondition : function(colName, value) {return " and " + colName + " like '" + value+ "%' ";}
								},
								result7 : {
									title : "�ϱ�����",
									style : 3
								},
								newColSaleChnl : {
									title : "��������",
									colNo : 3,
									style : 2,
									refercode1 : "salechnl",
									addCondition : function(colName,value){
										return " and exists (select 1 from lccont where lccont.contno = missionprop2 and lccont.salechnl = '"+value+"')";
									}
								},
								newColRiskCode : {
									title : "���ֱ���",
									refercode1 : "riskcode",
									style : 2,
									addCondition : function(colName,value){
										return " and exists (select 1  from lcpol where  lcpol.contno = missionprop1  and lcpol.riskcode = '"+value+"')";
									},
									colNo : 6
								},
								newColArrival : {
									title : "�Ƿ���",
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
									title : " ��ͬ�� ",
									style : 3
								},
								result1 : {
									title : " ӡˢ�� ",
									style : 0,
									colNo : 1
								},
								result2 : {
									title : "ҵ��Ա����",
									colNo : 5,
									style : 0
								},
								result3 : {
									title : "Ͷ���˱���",
									style : 3
								},
								result4 : {
									title : " Ͷ���� ",
									colNo : 2
								},
								result5 : {
									title : "�˱��������",
									colNo : 3,
									style : 0
								},
								result6 : {
									title : " ������� ",
									colNo : 4
								},
								result7 : {
									title : " �ϱ����� ",
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
					html : "<INPUT VALUE='ǩ������' class= cssButton name=signbutton TYPE=button onclick='signPol()'>"
				}
			};
			jQuery("#PublicPool").workpool(config);
}
</script>
