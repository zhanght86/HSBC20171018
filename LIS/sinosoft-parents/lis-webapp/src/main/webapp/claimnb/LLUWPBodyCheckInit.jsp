<%
//�������ƣ�BodyCheckPrintInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
//���ҳ��ؼ��ĳ�ʼ����
GlobalInput globalInput = (GlobalInput)session.getValue("GI");

if(globalInput == null) {
	out.println("session has expired");
	return;
}

String strOperator = globalInput.Operator;
String strManageCom = globalInput.ComCode;
%>
<script language="JavaScript">
function initInpBox()
{
	try
	{
		document.all('ContNo').value = '';
		document.all('AgentCode').value = '';
		document.all('ManageCom').value = <%=strManageCom%>;
	}
	catch(ex)
	{
		alert("��BodyCheckPrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!"+ex.name +":"+ex.message);
	}
}

function initForm()
{
	try
	{
		initInpBox();
		//initPolGrid();
		initUWPBodyPool();
	}
	catch(re)
	{
		alert("BodyCheckPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.name +":"+re.message);
	}
}

//modify by lzf
function initUWPBodyPool(){
	var config ={
			//activityId : "0000005531",
			functionId : "10030021",
			//operator : strOperator,
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "  ӡˢ��   ",style : 1,colNo : 2},
							result1 : {title : "  ��ͬ��   ",style : 1,colNo : 1},
							result2 : {title : " ��ˮ�� ",style : 3},
							result3 : {
								title : "�����˱���",
								style : 2,
								colNo : 4,
								refercode1 : "agentcode",
								addCondition : function(colName,value){
									return " and t.MissionProp4 = '"+value+"'";
								}
								},
							result4 : {
								title : "���������",
								style : 2,
								colNo : 5,
								refercode1 : "agentgroup",
								addCondition : function(colName ,value){
									return "t.MissionProp5 = '"+value+"'";
								}
								},
							
							result5 : {title : " չҵ����  ",style : 3},
							result6 : {
								title : "�������  ",
								style : 2,
								colNo : 3,
								refercode1 : "station",
								addCondition : function(colName,value){
									return " and t.MissionProp7 like '"+value+"%'";
								}
							},
							result7 : {title : "�ⰸ��",style : 3},
							result8 : {title : "���κ�",style : 3},
							result9 : {title : "�����֪ͨ����ˮ��",style : 3}
							
						}
					}
				},
				resultTitle : "�����Ϣ",
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
							result0 : {title : "  ӡˢ��   ",style : 1,colNo : 7},
							result1 : {title : "  ��ͬ��   ",style : 1,colNo : 2},
							result2 : {title : "  ��ˮ��  ",style : 1,colNo : 1},
							result3 : {title : " �����˱���",style : 1,colNo : 3},
							result4 : {title : " ���������",style : 1,colNo : 4},
							result5 : {title : "  չҵ����  ",style : 1,colNo : 5},
							result6 : {title : " �������",style : 1,colNo : 6},
							result7 : {title : "�ⰸ��",style : 3},
							result8 : {title : "���κ�",style : 3},
							result9 : {title : "�����֪ͨ����ˮ��",style : 3}
						}
					}
					
				}
				
			},
			private : {
				show : false
			}
	};
	jQuery("#UWPBodyPool").workpool(config);
}
//end
//var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
/*function initPolGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��ˮ��";
		iArray[1][1]="140px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="Ͷ��������";
		iArray[2][1]="140px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�����˱���";
		iArray[3][1]="100px";
		iArray[3][2]=100;
		iArray[3][3]=2;
		iArray[3][4]="AgentCode";
		iArray[3][5]="3";
		iArray[3][9]="�����˱���|code:AgentCode&NOTNULL";
		iArray[3][18]=250;
		iArray[3][19]= 0 ;

		iArray[4]=new Array();
		iArray[4][0]="���������";
		iArray[4][1]="100px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="չҵ����";
		iArray[5][1]="160px";
		iArray[5][2]=200;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�������";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=2;
		iArray[6][4]="station";
		iArray[6][5]="3";
		iArray[6][9]="�������|code:station&NOTNULL";
		iArray[6][18]=250;
		iArray[6][19]= 0 ;

		iArray[7]=new Array();
		iArray[7][0]="����ӡˢ��";
		iArray[7][1]="100px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="�������������";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="���������������";
		iArray[9][1]="0px";
		iArray[9][2]=100;
		iArray[9][3]=0;

		PolGrid = new MulLineEnter( "fmSave" , "PolGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PolGrid.mulLineCount = 10;
		PolGrid.displayTitle = 1;
		PolGrid.canSel = 1;
		PolGrid.hiddenPlus=1;
		PolGrid.hiddenSubtraction=1;
		PolGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
*/
</script>
