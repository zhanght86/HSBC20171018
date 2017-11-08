<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
function initInpBox() {
	try {
		document.all('ManageCom').value = '';
		document.all('AgentCode').value = '';
		document.all('AgentGroup').value = '';
		document.all('PrtNo').value = '';
	} catch (ex) {
		alert("在UWAutoInit.jsp-->InitInpBox函数中发生异常:"+ex.name+":"+ex.message);
	}
}

function initForm() {
	try {
		initInpBox();
		initPublicPool();
	} catch (re) {
		alert("UWAutoInit.jsp-->InitForm函数中发生异常:"+re.name+":"+re.message);
	}
}

function initPublicPool(){
	var config = {
			functionId : "10010005",
			public : {
				query : {
					queryButton : {
					},
					arrayInfo : {
						col : {
							result0 : {
								title : "合同号码",
								style : 3
								
							},
							result1 : {
								title : "印刷号",
								style : 1,
								colNo : 1
							},
							result2 : {
								title : "投保人",
								style : 3
							},
							result3 : {
								title : "代理人编码",
								style : 2,
								colNo : 4 ,
								referfunc1 : "queryAgent",
								referpara1 : "[0],[4]"
							},
							result4 : {
								title : "管理机构",
								style : 2,
								colNo : 2,
								refercode1 : "station",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
							},
							newCol : {
								title :"代理人组别",
								addCondition : function(colName,value){
									return " and exists (select 1 from laagent where agentgroup = '"+ value +"' and agentcode = missionprop4)";
								},
								colNo : 3
							}
						}
					}
				},
				result : {
					canSel : 0,
					canChk : 1,
					condition : {
						"0" : false,
						"1" : false,
						"2" : false
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "  合同号码  ",
								style : 0,
								colNo : 1
							},
							result1 : {
								title : "  印刷号码  ",
								colNo : 2
							},
							result2 : {
								title : "  投保人  ",
								colNo : 3
							},
							result3 : {
								title : "  代理人  ",
								colNo : 4
							},
							result4 : {
								title : "管理机构",
								colNo : 5
							},
							col1 : {
								title : "工作流任务号",
								colNo : 6,
								style : 3
							},
							col2 : {
								title : "工作流子任务号",
								colNo : 7,
								style : 3
							},
							col4 : {
								title : "工作流活动id",
								colNo : 8,
								style : 3
							},
							newCol0 : {
								title : "初审日期",
								colNo : 9,
								colName : "  (select firsttrialdate from lccont where prtno = missionprop2) " ,
								rename : " firsttrialdate ",
								style : 0
							},
							newCol1 : {
								title : "扫描日期",
								colName : "(select (case when to_char(min(makedate),'yyyy-mm-dd') is not null then to_char(min(makedate),'yyyy-mm-dd') else ' ' end) from es_doc_main where subtype = 'UA001'  and DocCode = missionprop2)",
								rename : " scandate ",
								style : 0,
								colNo : 10
							},
							newCol2 : {
								title : "自核日期",
								colName : "makedate",
								rename : "makedate",
								style : 0,
								colNo : 11
							},
							newCol3 : {
								title : "初审经过天数",
								colName : "case when trim(CAST(datediff(makedate, (select firsttrialdate from lccont where prtno = missionprop2)) as char(20))) is not null then trim(CAST(datediff(makedate , (select firsttrialdate from lccont where prtno = missionprop2)) as char(20))) else ' ' end",
								rename : " pastdays",
								style : 0,
								colNo : 12
							}
						}
					}
				}
			},
			midContent :{//the content between two pools
				id : "MidContent",
				show : true,
				html : "<INPUT VALUE='自动核保' class=cssButton TYPE=button onclick = 'autochk()'>"
			},
			private : {
				show : false
			}
		};
		jQuery("#PublicPool").workpool(config);
}
</script>
