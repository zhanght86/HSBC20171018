<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
	function initInpBox() {
		try {
			// 保单查询条件
			//document.all('ProposalNo').value = '';
			//document.all('ManageCom').value = '';
			//document.all('AgentCode').value = '';
			//document.all('AgentGroup').value = '';
			//document.all('ProposalNoHide').value = '';
		} catch (ex) {
			alert("在UWBackInit.jsp-->InitInpBox函数中发生异常:" + ex.name + ":"
					+ ex.message);
		}
	}

	function initForm() {
		try {
			initInpBox();
			initWorkPool();
		} catch (re) {
			alert("UWBackInit.jsp-->InitForm函数中发生异常:" + re.name + ":"
					+ re.message);
		}
	}

	function initWorkPool() {
		var config = {
			functionId : "10010041",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {
								title : "合同号",
								style : 3
							},
							result1 : {
								title : "管理机构",
								refercode1 : "station",
								style : 2,
								colNo : 2,
								addCondition : function(colName, value) {
									return " and " + colName + " like '"
											+ value + "%' ";
								}
							},
							result2 : {
								title : "代理人编码",
								style : 2,
								refercode1 : "agentcode",
								colNo : 3
							},
							result3 : {
								title : "代理人组别",
								style : 3
							},
							result4 : {
								title : " 印刷号 ",
								style : 1,
								colNo : 1
							},
							result5 : {
								title : "投保人编码",
								style : 3
							},
							result6 : {
								title : " 投保人名称 ",
								style : 3
							},
							result7 : {
								title : "核保员代码",
								style : 3
							},
							result8 : {
								title : "核保权限",
								style : 3
							},
							result9 : {
								title : "核保状态",
								style : 3
							}
						}
					}
				},
				result : {
					selBoxEventFuncName:"easyQueryAddClick",
					selBoxEventFuncParam:"param",
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
								title : "合同号",
								style : 3
							},
							result1 : {
								title : "管理机构",
								refercode1 : "station",
								style : 0,
								colNo : 3,
								addCondition : function(colName, value) {
									return " and " + colName + " like '"
											+ value + "%' ";
								}
							},
							result2 : {
								title : "代理人编码",
								style : 3
							},
							result3 : {
								title : "代理人组别",
								style : 3
							},
							result4 : {
								title : "  印刷号  ",
								style : 0,
								colNo : 1
							},
							result5 : {
								title : "投保人编码",
								style : 3
							},
							result6 : {
								title : "  投保人  ",
								style : 0,
								colNo : 2
							},
							result7 : {
								title : "核保员代码",
								style : 0,
								colNo : 4
							},
							result8 : {
								title : "核保权限",
								style : 3
							},
							result9 : {
								title : "核保状态",
								style : 3
							}
						}
					}
				}
			},
			private : {
				show : false
			},
			midContent : {//the content between two pools
				id : "MidContent",
				show : true,
				html : "<div id='divUWIdea' style='display:none' align='center'><table class='common'><tr class='common'><td class='titleImg'>核保订正原因：    </td></tr><tr class='common'><td class='common'><textarea rows='4' cols='100' name='UWIdea' value=''></textarea> </td>          </tr>	        </table>	    </div><INPUT VALUE='核保订正' class=cssButton TYPE=button onclick='back()'>"
			}
		};
		jQuery("#WorkPool").workpool(config);
	}
</script>
