<%
//�������ƣ�ProposalApproveInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	// ������ѯ����
    //document.all('ProposalNo').value = '';
    // document.all('ManageCom').value = '';
    //document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
  }
  catch(ex)
  {
    alert("��ProposalApproveInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��ProposalApproveInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
	initInpBox();  
	initSelBox();     
	initWorkPool();
	jQuery("#publicSearch").click();
	jQuery("#privateSearch").click();
  }
  catch(re)
  {
    alert("ProposalApproveInit.jsp-->InitForm�����з����쳣:��ʼ���������!������Ϣ�ǣ�"+re.name + ": " + re.message);
  }
}

function initWorkPool(){
	var config = {
			//activityId : "0000001001",
			functionId : "10010003",
			operator : Operator,
			public : {
				query : {
					queryButton : {
					},
					arrayInfo : {
						col : {
							result0 : {	title : "��ͬ����",style : "3" },//blurfunc : "assignPrtNo",//blurpara : "0"},
							result1 : {	title :"   ӡˢ��   ",	style : "1",colNo : "1"},
							result2 : {	title : "Ͷ���˺���",style : "3"},
							result3 : {	title: "   Ͷ����   ",style : "3"},
							result4 : {	title: "¼��Ա",	style : "3"},
							result5 : {	title:"¼������",style : "8",colNo :3 },
							result6 : { title:"ҵ��Ա����",referfunc1 : "queryAgent",referpara1 : "[0],[4]",colNo : 4 },
							result7 : { title : "�������",	colNo : 2,	refercode1 : "station",addCondition : function(colName, value) {return " and " + colName + " like '" + value+ "%' ";}}, 
							newCol1 : { title :"��������" ,colName : "missionprop1",	addCondition : function(colName, value) {
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
									};
									return " and " + colName + " like '" + value
											+ "%' ";
								},
								maxLength : 0,
								refercode1 : "polproperty",
								style : 2
							}
						}	 
					}
				},//end of arrayInfo
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : false,
						"4" : true
					},
					mulLineCount : 00,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "��ͬ����",
								style : 3
							},
							result1 : {
								title : "   ӡˢ��   ",
								colNo : "1"
							},
							result2 : {
								title : "Ͷ���˺���",
								style : 3
							},
							result3 : {
								title : "   Ͷ����   ",
								colNo : "2"
							},
							result4 : {
								title : "¼��Ա",
								colNo : "3"
							},
							result5 : {
								title:"¼������",
								style : "8",
								colNo : 4
							},
							result6:{
								title:"ҵ��Ա����",
								colNo : 6
							},
							result7:{
								title : "�������",
								colNo : 5,
								refercode1 : "station",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
							}//end of result7
						}//end of col
					}//end of arrayInfo
				}//end of result
			},//end of public
			private : {
				query : {
					queryButton : {
					},
					arrayInfo : {
						col : {
							result0 : {	title : "��ͬ����",style : "3" },//blurfunc : "assignPrtNo",//blurpara : "0"},
							result1 : {	title :"   ӡˢ��   ",	style : "1",colNo : "1"},
							result2 : {	title : "Ͷ���˺���",style : 3},
							result3 : {	title: "   Ͷ����   ",style : "3"},
							result4 : {	title: "¼��Ա",	style : "3"},
							result5 : {	title:"¼������",style : "8",colNo :3 },
							result6 : { title:"ҵ��Ա����",referfunc1 : "queryAgent",referpara1 : "1",colNo : 4 },
							result7 : { title : "�������",	colNo : 2,	refercode1 : "station",addCondition : function(colName, value) {return " and " + colName + " like '" + value+ "%' ";}}, 
							newCol1 : { title :"��������" ,colName : "missionprop1",	addCondition : function(colName, value) {
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
									};
									return " and " + colName + " like '" + value
											+ "%' ";
								},
								maxLength : 0,
								refercode1 : "polproperty",
								style : 2
							}
						}	 
					}
				},//end of arrayInfo
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false
					},
					mulLineCount : 00,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "��ͬ����",
								style : 3
							},
							result1 : {
								title : "   ӡˢ��   ",
								colNo : "1"
							},
							result2 : {
								title : "Ͷ���˺���",
								style : 3
							},
							result3 : {
								title : "   Ͷ����   ",
								colNo : "2"
							},
							result4 : {
								title : "¼��Ա",
								colNo : "3"
							},
							result5 : {
								title:"¼������",
								style : "0",
								colNo : 4
							},
							result6:{
								title:"ҵ��Ա����",
								colNo : 6
							},
							result7:{
								title : "�������",
								colNo : 5,
								refercode1 : "station",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
							}
						}
					}
				}
			},
			midContent :{//the content between two pools
				id : "MidContent",
				show : true,
				html : "<INPUT class=cssButton id=riskbutton VALUE='��  ��' TYPE=button onClick=ApplyUW()>"
			}
		};//end of config
		jQuery("#WorkPool").workpool(config);
}




</script>
