<%
//�������ƣ�ProposalApproveInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml
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
        document.all('ProposalNo').value = '';
        document.all('ManageCom').value = '';
        document.all('AgentCode').value = '';
        //document.all('AgentGroup').value = '';
    }
    catch(ex)
    {
        alert("��ProposalQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

// ������ĳ�ʼ��
function initSelBox()
{
    try
    {
        //    setOption("t_sex","0=��&1=Ů&2=����");
        //    setOption("sex","0=��&1=Ů&2=����");
        //    setOption("reduce_flag","0=����״̬&1=�����");
        //    setOption("pad_flag","0=����&1=�潻");
    }
    catch(ex)
    {
        alert("��ProposalQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();
        initUWUpReportPool();
        //initPolGrid();
        //initSelfPolGrid();

        //easyQueryClickSelf();

    }
    catch(re)
    {
        alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.name +":"+re.message);
    }
}

//add by lzf 2013-03-19
function initUWUpReportPool(){
	var config = {
			functionId : "10010040",
			//activityId : "0000001140",
			operator : Operator,
			public : {
				query : {
					queryButton : {
						"1" : {title : "��  ��" , func : "ApplyUW"}
					},
					arrayInfo : {
						col : {
							newcol0 : {
								title : " �ʱ����� ",
								style : "8",
								colNo : 4,
								refercode1 : "makedate",
								addCondition : function(colName,value){
									return " and t.makedate = to_date('"+ value +"','yyyy-mm-dd')";
								}
							},
							result0 : {
								title : " ӡˢ��  ",
								verify : "ӡˢ��|num&len=14",
								style :"1",
								colNo : 1
								},
							result1 : {
								title : "ҵ��Ա����",
								style : "2",
								colNo : 3,
								referfunc1 : "queryAgent",
								referpara1 : "[0],[3]"
								},
							result2 : {title : "������������",style :"3"},
							result3 : {title : "������չҵ��������",style :"3"},
							result4 : {
								title : " �������",
								colNo : 2,
								style : "2",
								refercode1 : "station",
								colName : "MissionProp6",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								   }
								}
			
						}
					}
				},
				resultTitle : "��������",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : false,
						"4" : true,
						"5" : "and managecom like'"
							+ ComCode
							+ "%'  order by contno"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newcol0 : {
								title : " �ʱ����� ",
								style : "1",
								colNo : 3,
								colName : "makedate",
								rename : "makedate"
							},
							newcol1 : {
								title : "�˱�Ա����",
								style : "1",
								colNo : 4,
								colName : "(select usercode from LCReinsureReport where contno=t.missionprop1)",
								rename : "usercode"
							},
							newcol2 : {
								title : " ����������",
								style : "1",
								colNo : 6,
								colName : "(select insuredname from lccont where contno = t.missionprop1)",
								rename : "insuredname"
							},
							newcol3 : {
								title : " ɨ������ ",
								style : "1",
								colNo : 7,
								colName : "(select (case min(c.makedate) when null then '��ɨ���' else to_char(min(c.makedate),'yyyy-mm-dd') end) from es_doc_main c where c.doccode= t.missionprop1)",
								rename : "scandate"
							},
							result0 : {
								title : " ӡˢ��  ",
								verify : "ӡˢ��|num&len=14",
								style :"1",
								colNo : 1
								},
							result1 : {
								title : " ҵ��Ա����",
								style : "1",
								colNo : 2
								},
							result2 : {title : "������������",style :"3"},
							result3 : {title : "������չҵ��������",style :"3"},
							result4 : {
								title : " �������",
								colNo : 5
								}
						}
					}
					
				}
			},
			private : {
				query : {
					arrayInfo : {
						col : {
							newcol0 : {
								title : " �ʱ�����   ",
								style : "8",
								colNo : 4,
								refercode1 : "makedate",
								addCondition : function(colName,value){
									return " and t.makedate = to_date('"+ value +"','yyyy-mm-dd')";
								}
							},
							result0 : {
								title : "  ӡˢ��   ",
								verify : "ӡˢ��|num&len=14",
								style :"1",
								colNo : 1
								},
							result1 : {
								title : " ҵ��Ա���� ",
								style : "2",
								colNo : 3,
								referfunc1 : "queryAgent",
								referpara1 : "[0],[3]"
								},
							result2 : {title : "������������",style :"3"},
							result3 : {title : "������չҵ��������",style :"3"},
							result4 : {
								title : " �������  ",
								colNo : 2,
								style : "2",
								refercode1 : "station",
								colName : "MissionProp6",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								   }
								}
						}
					}
				},
				resultTitle : "��������",
				result : {
					selBoxEventFuncName:"InitshowApproveDetail",
					selBoxEventFuncParam:"",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : "and managecom like'"
							+ ComCode
							+ "%'  order by contno"
					},
					mulLineCount : 10,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newcol0 : {
								title : " �ʱ����� ",
								style : "1",
								colNo : 3,
								colName : "makedate",
								rename : "makedate"
							},
							newcol1 : {
								title : "�˱�Ա����",
								style : "1",
								colNo : 4,
								colName : "(select usercode from LCReinsureReport where contno=t.missionprop1)",
								rename : "usercode"
							},
							newcol2 : {
								title : " ����������",
								style : "1",
								colNo : 6,
								colName : "(select insuredname from lccont where contno = t.missionprop1)",
								rename : "insuredname"
							},
							newcol3 : {
								title : " ɨ������  ",
								style : "1",
								colNo : 7,
								colName : "(select (case min(c.makedate) when null then '��ɨ���' else to_char(min(c.makedate),'yyyy-mm-dd') end) from es_doc_main c where c.doccode= t.missionprop1)",
								rename : "scandate"
							},
							result0 : {
								title : "  ӡˢ��   ",
								verify : "ӡˢ��|num&len=14",
								style :"1",
								colNo : 1
								},
							result1 : {
								title : " ҵ��Ա����",
								style : "1",
								colNo : 2
								},
							result2 : {title : "������������",style :"3"},
							result3 : {title : "������չҵ��������",style :"3"},
							result4 : {
								title : " ������� ",
								colNo : 5
								}
						}
					}
				}
				
			}
	};
	jQuery("#UWUpReportPool").workpool(config);
	jQuery("#privateSearch").click();
}
//end by lzf
// ������Ϣ�б�ĳ�ʼ��
/*function initPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "ӡˢ��";
        //����
        iArray[1][1] = "150px";
        //�п�
        iArray[1][2] = 100;
        //�����ֵ
        iArray[1][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������


        iArray[2] = new Array();
        iArray[2][0] = "�������˿ͻ�����";
        //����
        iArray[2][1] = "80px";
        //�п�
        iArray[2][2] = 100;
        //�����ֵ
        iArray[2][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3] = new Array();
        iArray[3][0] = "ҵ��Ա����";
        //����
        iArray[3][1] = "80px";
        //�п�
        iArray[3][2] = 100;
        //�����ֵ
        iArray[3][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4] = new Array();
        iArray[4][0] = "�ʱ�����";
        //����
        iArray[4][1] = "80px";
        //�п�
        iArray[4][2] = 100;
        //�����ֵ
        iArray[4][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5] = new Array();
        iArray[5][0] = "�����������";
        //����
        iArray[5][1] = "0px";
        //�п�
        iArray[5][2] = 200;
        //�����ֵ
        iArray[5][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6] = new Array();
        iArray[6][0] = "�������������";
        //����
        iArray[6][1] = "0px";
        //�п�
        iArray[6][2] = 200;
        //�����ֵ
        iArray[6][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7] = new Array();
        iArray[7][0] = "�������Id";
        //����
        iArray[7][1] = "0px";
        //�п�
        iArray[7][2] = 60;
        //�����ֵ
        iArray[7][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8] = new Array();
        iArray[8][0] = "�˱�Ա����";
        //����
        iArray[8][1] = "100px";
        //�п�
        iArray[8][2] = 60;
        //�����ֵ
        iArray[8][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9] = new Array();
        iArray[9][0] = "�������";
        //����
        iArray[9][1] = "60px";
        //�п�
        iArray[9][2] = 60;
        //�����ֵ
        iArray[9][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[10] = new Array();
        iArray[10][0] = "����������";
        //����
        iArray[10][1] = "60px";
        //�п�
        iArray[10][2] = 60;
        //�����ֵ
        iArray[10][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[11] = new Array();
        iArray[11][0] = "���մ���";
        //����
        iArray[11][1] = "0px";
        //�п�
        iArray[11][2] = 60;
        //�����ֵ
        iArray[11][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[12] = new Array();
        iArray[12][0] = "ɨ������";
        //����
        iArray[12][1] = "80px";
        //�п�
        iArray[12][2] = 100;
        //�����ֵ
        iArray[12][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[13] = new Array();
        iArray[13][0] = "Ͷ������";
        //����
        iArray[13][1] = "0px";
        //�п�
        iArray[13][2] = 100;
        //�����ֵ
        iArray[13][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������


        PolGrid = new MulLineEnter("fm", "PolGrid");
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 5;
        PolGrid.displayTitle = 1;
        PolGrid.locked = 1;
        PolGrid.canSel = 1;
        PolGrid.hiddenPlus = 1;
        PolGrid.hiddenSubtraction = 1;
        PolGrid.loadMulLine(iArray);

        //��Щ����������loadMulLine����
        //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfPolGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "ӡˢ��";
        //����
        iArray[1][1] = "150px";
        //�п�
        iArray[1][2] = 100;
        //�����ֵ
        iArray[1][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������


        iArray[2] = new Array();
        iArray[2][0] = "�������˿ͻ�����";
        //����
        iArray[2][1] = "80px";
        //�п�
        iArray[2][2] = 100;
        //�����ֵ
        iArray[2][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3] = new Array();
        iArray[3][0] = "ҵ��Ա����";
        //����
        iArray[3][1] = "80px";
        //�п�
        iArray[3][2] = 100;
        //�����ֵ
        iArray[3][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4] = new Array();
        iArray[4][0] = "�ʱ�����";
        //����
        iArray[4][1] = "80px";
        //�п�
        iArray[4][2] = 100;
        //�����ֵ
        iArray[4][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5] = new Array();
        iArray[5][0] = "�����������";
        //����
        iArray[5][1] = "0px";
        //�п�
        iArray[5][2] = 200;
        //�����ֵ
        iArray[5][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6] = new Array();
        iArray[6][0] = "�������������";
        //����
        iArray[6][1] = "0px";
        //�п�
        iArray[6][2] = 200;
        //�����ֵ
        iArray[6][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[7] = new Array();
        iArray[7][0] = "�������Id";
        //����
        iArray[7][1] = "0px";
        //�п�
        iArray[7][2] = 60;
        //�����ֵ
        iArray[7][3] = 3;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[8] = new Array();
        iArray[8][0] = "�˱�Ա����";
        //����
        iArray[8][1] = "100px";
        //�п�
        iArray[8][2] = 60;
        //�����ֵ
        iArray[8][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9] = new Array();
        iArray[9][0] = "�������";
        //����
        iArray[9][1] = "60px";
        //�п�
        iArray[9][2] = 60;
        //�����ֵ
        iArray[9][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[10] = new Array();
        iArray[10][0] = "����������";
        //����
        iArray[10][1] = "60px";
        //�п�
        iArray[10][2] = 60;
        //�����ֵ
        iArray[10][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[11] = new Array();
        iArray[11][0] = "���մ���";
        //����
        iArray[11][1] = "0px";
        //�п�
        iArray[11][2] = 60;
        //�����ֵ
        iArray[11][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[12] = new Array();
        iArray[12][0] = "ɨ������";
        //����
        iArray[12][1] = "80px";
        //�п�
        iArray[12][2] = 100;
        //�����ֵ
        iArray[12][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[13] = new Array();
        iArray[13][0] = "Ͷ������";
        //����
        iArray[13][1] = "0px";
        //�п�
        iArray[13][2] = 100;
        //�����ֵ
        iArray[13][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������
        


        SelfPolGrid = new MulLineEnter("fm", "SelfPolGrid");
        //��Щ���Ա�����loadMulLineǰ
        SelfPolGrid.mulLineCount = 5;
        SelfPolGrid.displayTitle = 1;
        SelfPolGrid.locked = 1;
        SelfPolGrid.canSel = 1;
        SelfPolGrid.hiddenPlus = 1;
        SelfPolGrid.hiddenSubtraction = 1;
        SelfPolGrid.selBoxEventFuncName = "InitshowApproveDetail";

        SelfPolGrid.loadMulLine(iArray);

        //��Щ����������loadMulLine����
        //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
// ��������Ϣ�б�ĳ�ʼ��
function initSubInsuredGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "���������˿ͻ���";
        //����
        iArray[1][1] = "180px";
        //�п�
        iArray[1][2] = 100;
        //�����ֵ
        iArray[1][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[1][7] = "showSubInsured";
        iArray[1][8] = "['SubInsured']";
        //�Ƿ�ʹ���Լ���д�ĺ���

        iArray[2] = new Array();
        iArray[2][0] = "����";
        //����
        iArray[2][1] = "160px";
        //�п�
        iArray[2][2] = 100;
        //�����ֵ
        iArray[2][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3] = new Array();
        iArray[3][0] = "�Ա�";
        //����
        iArray[3][1] = "140px";
        //�п�
        iArray[3][2] = 60;
        //�����ֵ
        iArray[3][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4] = new Array();
        iArray[4][0] = "��������";
        //����
        iArray[4][1] = "140px";
        //�п�
        iArray[4][2] = 100;
        //�����ֵ
        iArray[4][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5] = new Array();
        iArray[5][0] = "�뱻���˹�ϵ";
        //����
        iArray[5][1] = "160px";
        //�п�
        iArray[5][2] = 100;
        //�����ֵ
        iArray[5][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[5][4] = "Relation";

        SubInsuredGrid = new MulLineEnter("document", "SubInsuredGrid");
        //��Щ���Ա�����loadMulLineǰ
        SubInsuredGrid.mulLineCount = 5;
        SubInsuredGrid.displayTitle = 1;
        //SubInsuredGrid.tableWidth = 200;
        SubInsuredGrid.loadMulLine(iArray);

        //��Щ����������loadMulLine����
        //SubInsuredGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ��������Ϣ�б�ĳ�ʼ��
function initBnfGrid() {
    var iArray = new Array();

    try {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "���������";
        //����
        iArray[1][1] = "80px";
        //�п�
        iArray[1][2] = 40;
        //�����ֵ
        iArray[1][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[1][4] = "BnfType";
        iArray[1][9] = "���������|notnull&code:BnfType";

        iArray[2] = new Array();
        iArray[2][0] = "����������";
        //����
        iArray[2][1] = "80px";
        //�п�
        iArray[2][2] = 40;
        //�����ֵ
        iArray[2][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[2][9] = "����������|len<=20";
        //У��

        iArray[3] = new Array();
        iArray[3][0] = "�Ա�";
        //����
        iArray[3][1] = "30px";
        //�п�
        iArray[3][2] = 100;
        //�����ֵ
        iArray[3][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[3][4] = "sex";
        iArray[3][9] = "�Ա�|code:sex";
        //У��

        iArray[4] = new Array();
        iArray[4][0] = "֤������";
        //����
        iArray[4][1] = "60px";
        //�п�
        iArray[4][2] = 80;
        //�����ֵ
        iArray[4][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[4][4] = "IDType";
        iArray[4][9] = "֤������|code:IDType";

        iArray[5] = new Array();
        iArray[5][0] = "֤������";
        //����
        iArray[5][1] = "150px";
        //�п�
        iArray[5][2] = 80;
        //�����ֵ
        iArray[5][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[5][9] = "֤������|len<=20";

        iArray[6] = new Array();
        iArray[6][0] = "��������";
        //����
        iArray[6][1] = "80px";
        //�п�
        iArray[6][2] = 80;
        //�����ֵ
        iArray[6][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[6][9] = "��������|date";


        iArray[7] = new Array();
        iArray[7][0] = "�뱻���˹�ϵ";
        //����
        iArray[7][1] = "90px";
        //�п�
        iArray[7][2] = 100;
        //�����ֵ
        iArray[7][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[7][4] = "Relation";
        iArray[7][9] = "�뱻���˹�ϵ|code:Relation";

        iArray[8] = new Array();
        iArray[8][0] = "�������";
        //����
        iArray[8][1] = "60px";
        //�п�
        iArray[8][2] = 80;
        //�����ֵ
        iArray[8][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[8][9] = "�������|num&len<=10";

        iArray[9] = new Array();
        iArray[9][0] = "����˳��";
        //����
        iArray[9][1] = "60px";
        //�п�
        iArray[9][2] = 80;
        //�����ֵ
        iArray[9][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[9][4] = "OccupationType";
        iArray[9][9] = "����˳��|code:OccupationType";

        iArray[10] = new Array();
        iArray[10][0] = "��ϵ��ַ";
        //����
        iArray[10][1] = "400px";
        //�п�
        iArray[10][2] = 240;
        //�����ֵ
        iArray[10][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[10][9] = "ͨѶ��ַ|len<=80";

        iArray[11] = new Array();
        iArray[11][0] = "�ʱ�";
        //����
        iArray[11][1] = "60px";
        //�п�
        iArray[11][2] = 80;
        //�����ֵ
        iArray[11][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[11][9] = "�ʱ�|zipcode";

        iArray[12] = new Array();
        iArray[12][0] = "�绰";
        //����
        iArray[12][1] = "100px";
        //�п�
        iArray[12][2] = 80;
        //�����ֵ
        iArray[12][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[12][9] = "�绰|len<=18";

        BnfGrid = new MulLineEnter("document", "BnfGrid");
        //��Щ���Ա�����loadMulLineǰ
        BnfGrid.mulLineCount = 5;
        BnfGrid.displayTitle = 1;
        BnfGrid.loadMulLine(iArray);

        //��Щ����������loadMulLine����

    } catch(ex) {
        alert(ex);
    }
}

// ��֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid() {
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "��֪�ͻ�����";
        //����
        iArray[1][1] = "90px";
        //�п�
        iArray[1][2] = 100;
        //�����ֵ
        iArray[1][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[1][4] = "CustomerType";
        iArray[1][9] = "��֪�ͻ�����|len<=18";

        iArray[2] = new Array();
        iArray[2][0] = "��֪����";
        //����
        iArray[2][1] = "60px";
        //�п�
        iArray[2][2] = 100;
        //�����ֵ
        iArray[2][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[2][4] = "ImpartCode";
        iArray[2][9] = "��֪����|len<=4";

        iArray[3] = new Array();
        iArray[3][0] = "��֪���";
        //����
        iArray[3][1] = "60px";
        //�п�
        iArray[3][2] = 200;
        //�����ֵ
        iArray[3][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[3][4] = "ImpartVer";
        iArray[3][9] = "��֪���|len<=5";

        iArray[4] = new Array();
        iArray[4][0] = "��֪����";
        //����
        iArray[4][1] = "360px";
        //�п�
        iArray[4][2] = 200;
        //�����ֵ
        iArray[4][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5] = new Array();
        iArray[5][0] = "��д����";
        //����
        iArray[5][1] = "200px";
        //�п�
        iArray[5][2] = 200;
        //�����ֵ
        iArray[5][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[5][9] = "��д����|len<=200";

        ImpartGrid = new MulLineEnter("document", "ImpartGrid");
        //��Щ���Ա�����loadMulLineǰ
        ImpartGrid.mulLineCount = 5;
        ImpartGrid.displayTitle = 1;
        //ImpartGrid.tableWidth   ="500px";
        ImpartGrid.loadMulLine(iArray);

        //��Щ����������loadMulLine����
        //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}

// �ر�Լ����Ϣ�б�ĳ�ʼ��
function initSpecGrid() {
    var iArray = new Array();
    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "��Լ����";
        //����
        iArray[1][1] = "60px";
        //�п�
        iArray[1][2] = 100;
        //�����ֵ
        iArray[1][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[1][4] = "SpecType";
        iArray[1][9] = "��Լ����|len<=5";

        iArray[2] = new Array();
        iArray[2][0] = "��Լ����";
        //����
        iArray[2][1] = "60px";
        //�п�
        iArray[2][2] = 100;
        //�����ֵ
        iArray[2][3] = 2;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[2][4] = "SpecCode";
        iArray[2][9] = "��Լ����|len<=5";

        iArray[3] = new Array();
        iArray[3][0] = "��Լ����";
        //����
        iArray[3][1] = "540px";
        //�п�
        iArray[3][2] = 200;
        //�����ֵ
        iArray[3][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������
        iArray[3][9] = "��Լ����|len<=255";

        SpecGrid = new MulLineEnter("document", "SpecGrid");
        //��Щ���Ա�����loadMulLineǰ
        SpecGrid.mulLineCount = 5;
        SpecGrid.displayTitle = 1;
        SpecGrid.loadMulLine(iArray);
        //��Щ����������loadMulLine����
        //SpecGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}

//�����б�
function initDutyGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "���";
        //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1] = "30px";
        //�п�
        iArray[0][2] = 10;
        //�����ֵ
        iArray[0][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1] = new Array();
        iArray[1][0] = "���α���";
        //����
        iArray[1][1] = "100px";
        //�п�
        iArray[1][2] = 100;
        //�����ֵ
        iArray[1][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2] = new Array();
        iArray[2][0] = "��������";
        //����
        iArray[2][1] = "160px";
        //�п�
        iArray[2][2] = 100;
        //�����ֵ
        iArray[2][3] = 0;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[3] = new Array();
        iArray[3][0] = "����";
        //����
        iArray[3][1] = "80px";
        //�п�
        iArray[3][2] = 100;
        //�����ֵ
        iArray[3][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4] = new Array();
        iArray[4][0] = "����";
        //����
        iArray[4][1] = "80px";
        //�п�
        iArray[4][2] = 100;
        //�����ֵ
        iArray[4][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[5] = new Array();
        iArray[5][0] = "��������";
        //����
        iArray[5][1] = "80px";
        //�п�
        iArray[5][2] = 100;
        //�����ֵ
        iArray[5][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[6] = new Array();
        iArray[6][0] = "��ȡ����";
        //����
        iArray[6][1] = "80px";
        //�п�
        iArray[6][2] = 100;
        //�����ֵ
        iArray[6][3] = 1;
        //�Ƿ���������,1��ʾ����0��ʾ������

        DutyGrid = new MulLineEnter("document", "DutyGrid");
        //��Щ���Ա�����loadMulLineǰ
        DutyGrid.mulLineCount = 5;
        DutyGrid.displayTitle = 1;
        DutyGrid.canChk = 1;
        DutyGrid.loadMulLine(iArray);

        //��Щ����������loadMulLine����
        //DutyGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
        alert(ex);
    }
}


function emptyForm() {
    //emptyFormElements();     //���ҳ�������������COMMON��JS��ʵ��

    initSubInsuredGrid();
    initBnfGrid();
    initImpartGrid();
    initSpecGrid();
    initDutyGrid();

    //SubInsuredGrid.clearData();
    //BnfGrid.clearData();
    //ImpartGrid.clearData();
    //SpecGrid.clearData();
    //DutyGrid.clearData();
    spanDutyGrid.innerHTML = "";
}


</script>
