<%
//�������ƣ�UWNoticeInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
		document.all('ManageCom').value = <%=strManageCom%>;
	}
	catch(ex)
	{
		alert("��LLUWPAllInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initPolGrid();
		//initUWPAllPool()
	}
	catch(re)
	{
		alert("��LLUWPAllInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.name +":"+re.message);
	}
}
/**
function initUWPAllPool(){
	var config = {
			//activityId : "0000005551",
			functionId : "10030029",
			//operator : operator,
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "ӡˢ��" , style : 3},
							result1 : {title : "   �������� " , style : 1,colNo : 1},
							result2 : {title : "֪ͨ����ˮ��" , style : 3},
							result3 : {title : "�����˱���" , style : 3},
							result4 : {title : "֪ͨ�����" , style : 3},
							result5 : {
								title : "   �������  " , 
								style : 2,
								colNo : 2,
								refercode1 : "station",
							    addCondition : function(colName ,value){
							    	return " and t.missionprop7 like '"+value+"%'";
							    }	
							},
							result6 : {title : "�ⰸ��" , style : 3},
							result7 : {title : "���κ�" , style : 3},
							result8 : {title : "�ͻ���" , style : 3},
							result9 : {title : "�ͻ�����" , style : 3},
							result10 : {title : "��ͬ��������" , style : 3},
							result11 : {title : "�����֪ͨ����ˮ��" , style : 3}
						}
					}
				},
				resultTitle : "Ͷ������Ϣ",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and code like 'LP%' and code ='LP90'"
					},
					mulLineCount :  10,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {title : "ӡˢ��" , style : 3},
							result1 : {title : "  ��������   " , style : 1,colNo : 2},
							result2 : {title : "  ��ˮ��  " , style : 1,colNo : 1},
							result3 : {title : " �����˱��� " , style : 1,colNo : 3},
							result4 : {title : "֪ͨ�����" , style : 3},
							result5 : {title : " ������� " , style : 1,colNo : 4},
							result6 : {title : "�ⰸ��" , style : 3},
							result7 : {title : "���κ�" , style : 3},
							result8 : {title : "�ͻ���" , style : 3},
							result9 : {title : "�ͻ�����" , style : 3},
							result10 : {title : "��ͬ��������" , style : 3},
							result11 : {title : "�����֪ͨ����ˮ��" , style : 3}
						}
					}
				}
			},
			private : {
				show : false
			}
	};
	jQuery("#UWPAllPool").workpool(config);
}
*/
var PolGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
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
		iArray[1][1]="180px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="160px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="ӪҵԱ����";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="֪ͨ������";
		iArray[4][1]="0px";
		iArray[4][2]=200;
		iArray[4][3]=0;
	    
		iArray[5]=new Array();
		iArray[5][0]="�������";
		iArray[5][1]="120px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="����ӡˢ��";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=3;
		
		iArray[7]=new Array();
		iArray[7][0]="�������������";
		iArray[7][1]="0px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="���������������";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="OldPrtSeq";
		iArray[9][1]="0px";
		iArray[9][2]=100;
		iArray[9][3]=3;
	
		iArray[10]=new Array();
		iArray[10][0]="�ⰸ��";
		iArray[10][1]="0px";
		iArray[10][2]=100;
		iArray[10][3]=0;
		
		iArray[11]=new Array();
		iArray[11][0]="���κ�";
		iArray[11][1]="0px";
		iArray[11][2]=100;
		iArray[11][3]=0;

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

</script>
