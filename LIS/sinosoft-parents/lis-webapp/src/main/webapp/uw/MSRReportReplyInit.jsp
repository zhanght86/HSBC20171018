<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
	String tProposalNo = "";
	String tFlag = "";
%>
<script language="JavaScript">
	function initInpBox() {
		try {
		} catch (ex) {
			alert("��RReportQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initWorkPool();
			jQuery("#publicSearch").click();
		} catch (re) {
			alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
		}
	}
	
	function initWorkPool(){
		var config = {
				//activityId : "0000001120",
				functionId : "10010035",
				//operator : Operator,
				public : {
					query : {
						queryButton : {
						},
						arrayInfo : {
							col : {
								result0 : {
									title : "Ͷ����ӡˢ��",
									style : 3
								},
								result1 : {
									title : "��ͬ��",
									style : 3
								},
								result2 : {
									title : "���֪ͨ����ˮ��",
									style : 3
								},
								result3 : {
									title : "���������",
									style : 3
								},
								result4 : {
									title : "����Ա",
									style : 3
								},
								result5 : {
									title : "���ʱ��",
									style : 3
								},
								result6 : {
									title : "ҵ��Ա����",
									style : 3
								},
								result7 : {
									title : "�������",
									style : 2,
									refercode1 : "station",
									addCondition : function(colName, value) {
										return " and " + colName + " like '" + value+ "%' ";
									},
									colNo : 3
								},
								result8 : {
									title : "Ͷ��������",
									style : 3
								},
								result9 : {
									title : "ԭ������ӡ��ˮ��",
									style : 3
								},
								newCol1 : {
									title : " Ͷ����ӡˢ�� ",
									style : 1,
									colNo : 1,
									addCondition : function(colName, value) {
										return " and t.MissionProp2 = '"+value+"'";
									}
								},
								newCol2 : {
									title : "  ������  ",
									style : 1,
									colNo : 2,
									addCondition : function(colName, value) {
										return " and t.MissionProp9 like '%"+value+"%'";
									}
								}
								
							}	 
						}
					},
					result : {
						selBoxEventFuncName:"easyQueryChoClick",
						selBoxEventFuncParam:"param",
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
								newCol1 : {
									title : "Ͷ����ӡˢ��",
									colNo : 1,
									style : 1,
									colName : "(select lccont.prtno from lccont where lccont.contno =  t.missionprop2)",
									rename : "lcPrtNo" 
								},
								newCol2 : {
									title : "  ������  ",
									colNo : 2,
									style : 1,
									colName : "(select lcrreport.name from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null  and lcrreport.replycontente is null and lcrreport.prtseq is not null and lcrreport.replyflag = '0')",
									rename : "lcName"
								},
								newCol3 : {
									title : "¼����",
									colNo : 3,
									colName : "(select lcrreport.operator from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null  and lcrreport.replycontente is null and lcrreport.prtseq is not null and lcrreport.replyflag = '0')",
									rename : "lcOperator"
								},
								newCol4 : {
									title : "¼������",
									colNo : 4,
									style : 8,
									colName : "(select lcrreport.makedate from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null  and lcrreport.replycontente is null and lcrreport.prtseq is not null and lcrreport.replyflag = '0')",
									rename : "lcMakeDate"
								},
								newCol5 : {
									title : "�ظ���",
									colNo : 5,
									style : 1,
									colName : "(select lcrreport.replyoperator from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null  and lcrreport.replycontente is null and lcrreport.prtseq is not null and lcrreport.replyflag = '0')",
									rename : "lcReplyOperator"
								},
								newCol6 : {
									title : "�ظ�����",
									colNo : 6,
									style : 8,
									colName : "(select lcrreport.replydate from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null  and lcrreport.replycontente is null and lcrreport.prtseq is not null and lcrreport.replyflag = '0')",
									rename : "lcReplyDate"
								},
								newCol7 : {
									title : "�Ƿ�ظ�",
									colNo : 7,
									style : 2,
									colName : "(select lcrreport.replyflag from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null  and lcrreport.replycontente is null and lcrreport.prtseq is not null and lcrreport.replyflag = '0')",
									rename : "lcReplyFlag"
								},
								newCol8 : {
									title : "��ˮ��",
									style : 3,
									colName : "(select lcrreport.prtseq from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null  and lcrreport.replycontente is null and lcrreport.prtseq is not null and lcrreport.replyflag = '0')",
									rename : "lcPrtSeq"
								},
								result0 : {
									title : "Ͷ����ӡˢ��",
									style : 3
								},
								result1 : {
									title : "��ͬ��",
									style : 3
								},
								result2 : {
									title : "���֪ͨ����ˮ��",
									style : 3
								},
								result3 : {
									title : "���������",
									style : 3
								},
								result4 : {
									title : "¼����",
									style : 3
								},
								result5 : {
									title : "���ʱ��",
									style : 3
								},
								result6 : {
									title : "ҵ��Ա����",
									style : 3
								},
								result7 : {
									title : "�������",
									style : 3
								},
								result8 : {
									title : "Ͷ��������",
									style : 3
								},
								result9 : {
									title : "ԭ������ӡ��ˮ��",
									style : 3
								}
							}
						},
						condition : {
							"5" : " and exists (select 1 from lccont d where d.contno = ContNo and d.managecom like '"+comcode+"') and exists (select 1 from lcrreport e where e.contno = ContNo and e.contente is not null and e.replycontente is null and e.prtseq is not null and e.replyflag = '0') "
						}
					}
				},
				private : {
					show : false
				}
			};
				
				if(_DBT==_DBM){   //���ʹ�õ���MYSQL���ݿ⽫sql��串��
					config.public.result.arrayInfo.col.newCol2.colName = "(select lcrreport.name from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null and lcrreport.contente !='' and (lcrreport.replycontente is null or lcrreport.replycontente ='') and lcrreport.prtseq is not null and lcrreport.prtseq !='' and lcrreport.replyflag = '0')";
					
					config.public.result.arrayInfo.col.newCol3.colName = "(select lcrreport.operator from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null and lcrreport.contente !='' and (lcrreport.replycontente is null or lcrreport.replycontente ='') and lcrreport.prtseq is not null and lcrreport.prtseq !='' and lcrreport.replyflag = '0')";
					
					config.public.result.arrayInfo.col.newCol4.colName = "(select lcrreport.makedate from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null and lcrreport.contente !='' and (lcrreport.replycontente is null or lcrreport.replycontente ='') and lcrreport.prtseq is not null and lcrreport.prtseq !='' and lcrreport.replyflag = '0')";
					
					config.public.result.arrayInfo.col.newCol5.colName = "(select lcrreport.replyoperator from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null and lcrreport.contente !='' and (lcrreport.replycontente is null or lcrreport.replycontente ='') and lcrreport.prtseq is not null and lcrreport.prtseq !='' and lcrreport.replyflag = '0')";
					
					config.public.result.arrayInfo.col.newCol6.colName = "(select lcrreport.replydate from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null and lcrreport.contente !='' and (lcrreport.replycontente is null or lcrreport.replycontente ='') and lcrreport.prtseq is not null and lcrreport.prtseq !='' and lcrreport.replyflag = '0')";
					
					config.public.result.arrayInfo.col.newCol7.colName = "(select lcrreport.replyflag from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null and lcrreport.contente !='' and (lcrreport.replycontente is null or lcrreport.replycontente ='') and lcrreport.prtseq is not null and lcrreport.prtseq !='' and lcrreport.replyflag = '0')";
					
					config.public.result.arrayInfo.col.newCol8.colName = "(select lcrreport.prtseq from lcrreport where lcrreport.contno = t.missionprop2 and lcrreport.contente is not null and lcrreport.contente !='' and (lcrreport.replycontente is null or lcrreport.replycontente ='') and lcrreport.prtseq is not null and lcrreport.prtseq !='' and lcrreport.replyflag = '0')";
					
				}
			jQuery("#WorkPool").workpool(config);
	}
</script>


