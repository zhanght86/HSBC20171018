<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ScanContInit.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		// ������ѯ����
		document.all('PrtNo').value = '';
	}
	catch(ex)
	{
		alert("��GroupUWAutoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initSelfPool();
	}
	catch(re)
	{
		alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!������Ϣ�ǣ�"+re.name + ": " + re.message);
	}
}

var config;
var fId;
if(type == "1"){//������ɨ
	fId = "10010001";
}else if(type == "2"){//�ŵ���ɨ
	fId = "20010010";
}else if(type == "5"){//����Լ����ȷ�Ͻڵ��functionid��ʱΪ��
	fId = "";
}
function initSelfPool() {
	config = {
		//activityId : "0000001098,0000001099",
		//activityId : aId,
		//functionId : "10010001",
		functionId : fId,
		operator : operator,
		public : {
			show : false
		},
		private : {
			id : "SelfPool",
			query : {
				queryButton : {
					"1" : {
						title : "��  ��",
						func : "ApplyInput"
					}
				},
				arrayInfo : {
					col : {
						newcol0 : {
							title : "��������",
							colName : "missionprop1",
							addCondition : function(colName, value) {
								switch (value) {
								case "11":
									value = "8611";
									break;
								case "16":
									value = "8691";
									break;
								case "21":
									value = "8671";
									break;
								case "35":
									value = "8635";
									break;
								}
								;
								return " and " + colName + " like '" + value
										+ "%' ";
							},
							maxLength : 10,
							refercode1 : "polproperty",
							style : 2
						},
						result0 : {
							title : "Ͷ������",
							verify : "Ͷ������|num&len=14",
							colNo : 2,
							style : "1",
							blurfunc : "assignPrtNo",
							blurpara : "0"     //��֪�����������ɶ��
						},
						result1 : {
							title: "��������",
							style : 8,
							colNo : 3
						},
						result2 : {
							title : "�������",
							colNo : 1,
							refercode1 : "station",
							addCondition : function(colName, value) {
								return " and " + colName + " like '" + value
										+ "%' ";
							}
						},
						result3 : {
							style : 3
						},
						result4 : {
							style : 3,
							defaultvalue : "TB01"
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
				mulLineCount : 10,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",
						result0 : {
							title : "Ͷ������"
						},
						result1 : {
							title : "��������",
							style : 0,
							colNo : 3
						},
						result2 : {
							title : "�������",
							colNo : 2
						},
						result4 : {
							style : "3"
						}
					}
				},
				condition : {
					"5" : "and managecom like'"
							+ comcode
							+ "%'  order by prtno"
				}
			}
		}
	};
	//config.activityId = "0000001061";
	//config = {functionId:"10010001",operator:operator};
	jQuery("#SelfPool").workpool(config);
}
</script>
