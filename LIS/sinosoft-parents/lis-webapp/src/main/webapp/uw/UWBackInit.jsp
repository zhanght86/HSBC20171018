<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
	function initInpBox() {
		try {
			// ������ѯ����
			//document.all('ProposalNo').value = '';
			//document.all('ManageCom').value = '';
			//document.all('AgentCode').value = '';
			//document.all('AgentGroup').value = '';
			//document.all('ProposalNoHide').value = '';
		} catch (ex) {
			alert("��UWBackInit.jsp-->InitInpBox�����з����쳣:" + ex.name + ":"
					+ ex.message);
		}
	}

	function initForm() {
		try {
			initInpBox();
			initWorkPool();
		} catch (re) {
			alert("UWBackInit.jsp-->InitForm�����з����쳣:" + re.name + ":"
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
								title : "��ͬ��",
								style : 3
							},
							result1 : {
								title : "�������",
								refercode1 : "station",
								style : 2,
								colNo : 2,
								addCondition : function(colName, value) {
									return " and " + colName + " like '"
											+ value + "%' ";
								}
							},
							result2 : {
								title : "�����˱���",
								style : 2,
								refercode1 : "agentcode",
								colNo : 3
							},
							result3 : {
								title : "���������",
								style : 3
							},
							result4 : {
								title : " ӡˢ�� ",
								style : 1,
								colNo : 1
							},
							result5 : {
								title : "Ͷ���˱���",
								style : 3
							},
							result6 : {
								title : " Ͷ�������� ",
								style : 3
							},
							result7 : {
								title : "�˱�Ա����",
								style : 3
							},
							result8 : {
								title : "�˱�Ȩ��",
								style : 3
							},
							result9 : {
								title : "�˱�״̬",
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
								title : "��ͬ��",
								style : 3
							},
							result1 : {
								title : "�������",
								refercode1 : "station",
								style : 0,
								colNo : 3,
								addCondition : function(colName, value) {
									return " and " + colName + " like '"
											+ value + "%' ";
								}
							},
							result2 : {
								title : "�����˱���",
								style : 3
							},
							result3 : {
								title : "���������",
								style : 3
							},
							result4 : {
								title : "  ӡˢ��  ",
								style : 0,
								colNo : 1
							},
							result5 : {
								title : "Ͷ���˱���",
								style : 3
							},
							result6 : {
								title : "  Ͷ����  ",
								style : 0,
								colNo : 2
							},
							result7 : {
								title : "�˱�Ա����",
								style : 0,
								colNo : 4
							},
							result8 : {
								title : "�˱�Ȩ��",
								style : 3
							},
							result9 : {
								title : "�˱�״̬",
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
				html : "<div id='divUWIdea' style='display:none' align='center'><table class='common'><tr class='common'><td class='titleImg'>�˱�����ԭ��    </td></tr><tr class='common'><td class='common'><textarea rows='4' cols='100' name='UWIdea' value=''></textarea> </td>          </tr>	        </table>	    </div><INPUT VALUE='�˱�����' class=cssButton TYPE=button onclick='back()'>"
			}
		};
		jQuery("#WorkPool").workpool(config);
	}
</script>
