//�������ƣ�PD_LMDutyGetClmCal.js
//�����ܣ����θ����⸶������㹫ʽ
//�������ڣ�2009-3-16
//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
function returnParent() {
	top.opener.focus();
	top.close();
}
var yearflag = [
    		    {yearflagid:'��',name:'Y'},
    		    {yearflagid:'��',name:'M'},
    		    {yearflagid:'��',name:'D'}
    		];

function initAllInput() {
	lockPage("���ݼ�����...........");
	$
			.ajax({
				type : "POST",
				dataType : "json",
				async : false,
				url : "../productdef/initAllRiskParams.jsp?Type=Input&RiskCode="
						+ jRiskCode + "&StandbyFlag1=" + jStandbyFlag1,
				contentType : 'application/json;charset=UTF-8',
				error : function() {
					alert("����ʧ��");
				},
				success : function(data) {
					var RuleMapArray = data;
					try {
						$('#DivLCRiskPay').hide();
						$('#DivLCRiskDuty').hide();
						for (var i = 0; i < RuleMapArray.length; i++) {
							var tType = RuleMapArray[i].inputType;
							var detail = RuleMapArray[i].detail;
							var tDutyPayFlag = "";
							if (detail != null && detail != '') {
								if (tType == 'Risk') {
									$('div.divRisk').html(detail);
									$(document.body).append("<hr>");
								} else if (tType == 'Pay') {
									$('#DivLCRiskPay').show();
									initPayForm();
								} else if (tType == 'Duty') {
									initDutyForm();
									$('#DivLCRiskDuty').show();
								}
							}
						}
						$("ul, li").disableSelection();
						unLockPage();
						
					} catch (ex) {
						alert(ex);
					}
				}
			});
}

// ��ʾ�ɷѺ�����ѡ��

function initPayForm() {
	var ProposalContNo=$("#ProposalContNo").val();
	try {
		$.ajax({
			type : "POST",
			dataType : "json",
			async : false,
			url : "../productdef/initDutyPayColSel.jsp?Type=InitPay&RiskCode="
				+ jRiskCode,
			contentType : 'application/json;charset=UTF-8',
			error : function() {
				alert("����ʧ��");
			},
			success : function(data) {
				var RuleMapArray = data;
				initPremGrid(data[0].detail);
				try {
					for (var i = 0; i < RuleMapArray.length; i++) {
						var tType = RuleMapArray[i].inputType;
						var detail = RuleMapArray[i].detail;
						var Currency = eval("["+RuleMapArray[i].currency+"]");
						if (detail != null && detail != '') {
							$("#divPay").height('150px');
							$('#payGrid').datagrid({
								fit : true,
								rownumbers : true,
								pagination : false,
								Height : 500,
								title : '���ֽɷ���Ϣ',
								url : '../productdef/initAllRiskParams.jsp?ProposalContNo='+ProposalContNo,
								queryParams : {
									RiskCode : jRiskCode,
									Type : 'PayGrid'
								},
								dataType : 'json',
								sortName : 'idx',
								sortOrder : 'asc',
								idField : 'idx',
								onClickCell:function(rowIndex, field, value){
						        	$('#payGrid').datagrid('beginEdit', rowIndex);
						        },
						        onSelect:function(rowIndex, rowData){
						        	$('#payGrid').datagrid('endEdit', rowIndex);
						        },
								frozenColumns : [ [ {
									field : 'ck',
									checkbox : true
								} ] ],
								columns:eval("[ ["+
						            detail+
						        "] ]")
							});
						}
					}
				} catch (ex) {
					alert(ex);
				}
			}
		});

	} catch (re) {
		alert("HTPeopServManaInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

function initDutyForm() {
	var ProposalContNo=$("#ProposalContNo").val();
	try {
		$.ajax({
			type : "POST",
			dataType : "json",
			async : false,
			url : "../productdef/initDutyPayColSel.jsp?Type=InitDuty&RiskCode="
				+ jRiskCode,
			contentType : 'application/json;charset=UTF-8',
			error : function() {
				alert("����ʧ��");
			},
			success : function(data) {
				var RuleMapArray = data;
				initDutyGrid(data[0].detail);
				try {
					for (var i = 0; i < RuleMapArray.length; i++) {
						var tType = RuleMapArray[i].inputType;
						var detail = RuleMapArray[i].detail;
						var PayYear = eval("["+RuleMapArray[i].payyear+"]");
						if (detail != null && detail != '') {
							$("#divDuty").height('150px');
							$('#dutyGrid1').datagrid({
								
								fit : true,
								rownumbers : true,
								pagination : false,
								Height : 500,
								title : '����������Ϣ',
								url : '../productdef/initAllRiskParams.jsp?ProposalContNo='+ProposalContNo,
								queryParams : {
									RiskCode : jRiskCode,
									Type : 'DutyGrid'
								},
								dataType : 'json',
								sortName : 'idx',
								sortOrder : 'asc',
								idField : 'idx',
								onClickCell:function(rowIndex, field, value){
						        	$('#dutyGrid1').datagrid('beginEdit', rowIndex);
						        },
						        onSelect:function(rowIndex, rowData){
						        	$('#dutyGrid1').datagrid('endEdit', rowIndex);
						        },
								frozenColumns : [ [ {
									field : 'ck',
									checkbox : true
								} ] ],
								columns:eval("[ ["+
						            detail+
						        "] ]")
							});
						}
					}
				} catch (ex) {
					alert(ex);
				}
			}
		});
	} catch (re) {
		alert("HTPeopServManaInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}
function evalJSON(strJson)
              {
               return eval( "(" + strJson + ")");
              }

function initDutyGrid(data)
{
	if(data!=null){
		
		try {
			var iArray = new Array();
			var s = data.split("title:'");
			for(var i = 1;i<s.length;i++){
				iArray[i-1] = new Array();
				iArray[i-1][0] = s[i].split("',")[0];         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
				iArray[i-1][1] = "50px";            		//�п�
				iArray[i-1][2] = 10;            			//�����ֵ
				iArray[i-1][3] = 0;
				
			}
			DutyGrid = new MulLineEnter( "fm" , "DutyGrid" );
			//��Щ���Ա�����loadMulLineǰ
			DutyGrid.mulLineCount = 0;
			DutyGrid.displayTitle = 1;
			DutyGrid.canChk=1;
			DutyGrid.hiddenPlus = 1;
			DutyGrid.hiddenSubtraction = 1;
			DutyGrid.loadMulLine(iArray);
		}
		catch(ex) {
			alert(ex);
		}
	}
}
function initPremGrid(data)
{
    var iArray = new Array();
if(data!=null){
	
	try {
		var s = data.split("title:'");
		for(var i = 1;i<s.length;i++){
			iArray[i-1] = new Array();
			iArray[i-1][0] = s[i].split("',")[0];         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[i-1][1] = "50px";            		//�п�
			iArray[i-1][2] = 10;            			//�����ֵ
			iArray[i-1][3] = 0;
		}
		
		PremGrid = new MulLineEnter( "fm" , "PremGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PremGrid.mulLineCount = 0;
		PremGrid.displayTitle = 1;
		PremGrid.canChk=1;
		PremGrid.hiddenPlus = 1;
		PremGrid.hiddenSubtraction = 1;
		PremGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert(ex);
	}
}
}