<%
//�������ƣ�WFEdorNoscanAppInit.jsp
//�����ܣ���ȫ������-��ȫ��ɨ������
//�������ڣ�2005-04-27 15:13:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//            XinYQ     2006-11-08   ��ʽ����
//
%>


<script language="JavaScript">

//var SelfGrid;

function initForm()
{
    try
    {
       initSearchPool();
        //initSelfGrid();          //��ʼ���ҵ��������
       	//easyQueryClickSelf();    //��ѯ�ҵ��������
    }
    catch (ex)
    {
        alert("�� WFEdorNoscanAppInit.jsp --> initForm �����з����쳣:��ʼ���������111�� ");
    }
}
function initSearchPool(){
   var sql1 = "";
   var sql2 = "";
   if(_DBT==_DBO){
	   sql1 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) ";
   }else if(_DBT==_DBM){
	   sql1 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) ";
   }
   if(_DBT==_DBO){
	   sql2 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1  and rownum = 1) and workdateflag = 'Y') ";
   }else if(_DBT==_DBM){
	   sql2 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1  and rownum = 1) and workdateflag = 'Y') ";
   }
	var config = {
			activityId : '0000000002',
			//functionId : "10020002",
			//operator : operator,
			public : {
				show : false
			},
			private : {
				query : {
					queryButton : {
						"1" : {
							title : "��  ��",
							func : "applyMission"
						}
					},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"��������",//�еı���
								  style : 2,
								  colNo : 2,
								  maxLength:10,//����������󳤶��൱��iArray[i][2]
								  refercode1:"edornotype",
								  colName:"MissionProp3",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							 newcol1:{ 
								  title:"���뷽ʽ",//�еı���
								  style : 2,
								  colNo : 5,
								  maxLength:10,//����������󳤶��൱��iArray[i][2]
								  refercode1:"edorapptype",
								  colName:"MissionProp5",
								   addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol2:{ 
								  title:"¼������",//�еı���
								  style : 8,
								  colNo : 6,
								  colName:"makedate"
								  },
								  
							newcol3:{ 
								  title:"�������",//�еı���
								  colNo : 7,
								  style : 2,
								  colName:"MissionProp2",
								  refercode1:"station",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
								  
							result0  : {title : " ��ȫ�����       ",style : 1,colNo : 1},  
							result1  : {title : " �������         ",style : 3},  
							result2  : {title : " Ͷ����           ",style : 3},            
							result3  : {title : " ���Ѷ�Ӧ��       ",style : 3},            
							result4  : {title : " �ͻ�/������         ",style : 1,colNo : 3},            
							result5  : {title : " �����������     ",style : 3},  
							result6  : {title : " ������       ",style : 1,colNo : 4},  
							result7  : {title : " ���뷽ʽ         ",style : 3},  
							result8  : {title : " ��������         ",style : 3},            
							result9  : {title : " Ĭ�ϴ�����       ",style : 3},           
							result10  : {title : " ��Ҫ���ϱ�����       ",style : 3}           
						}
					}
				},
				resultTitle : "�ҵ�����",
				result : {
					selBoxEventFuncName : "HighlightByRow",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and missionprop18 is null and missionid not in (select missionid from lwmission  where activityid in"+
              		 		  " (select activityid "+
                 			  " from lwactivity "+
                 			  " where functionid = '10020000')) and trim(defaultoperator) ='"+operator+"'"// and OtherNo like'"+ + comcode+ "%'"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "��������",
								style : 1,
								colNo : 3,
								width : "70px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "���뷽ʽ",
								style : 1,
								colNo : 6 ,
								width : "85px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "¼������",
								style : 1,
								colNo : 9 ,
								width : "100px",
								colName : "makedate "
							},
							newCol3 : {
								title : "�������",
								style : 1,
								colNo : 7,
								width : "65px",
								colName : "(select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop2))",
								rename : "p_station"
							},
							newCol4 : {
								title : "¼��Ա",
								style : 1,
								colNo : 8 ,
								width : "60px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "��ȫ����",
								style : 1,
								colNo : 10 ,
								width : "80px",
								colName : "case (select count(0) from lbmission where activityid in (select activityid from lwactivity "+
										  "where functionid = '10020005') and missionid = t.missionid) when 0 then '��ȫ����' "+
         								  " else ' �����޸� ' end ",
         					    rename : "bq_optype"
							},
							newCol6 : {
								title : "��������",
								style : 1,
								colNo : 11 ,
								width : "100px",
								colName : sql1,
								rename : "edor_appdate"
							},
							
							newCol7 : {
								title : "��������",
								style : 1,
								colNo : 12 ,
								width : "50px",
								colName : sql2,
								rename : "pass_day"
							},
							
							newCol8 : {
								title : "����",
								style : 3,
								colNo : 13 ,
								colName : "missionprop18"
							},
							result0  : {title : " ��ȫ�����       ",style : 1,width : "145px",colNo : 1},  
							result1  : {title : " �������         ",style : 3},  
							result2  : {title : " Ͷ����           ",style : 1,width : "60px",colNo : 4},            
							result3  : {title : " ���Ѷ�Ӧ��       ",style : 1,width : "100px",colNo : 5},            
							result4  : {title : " �ͻ�/������         ",style : 1,width : "145px",colNo : 2},            
							result5 : {title : "��������", style : 3 }, 
							result6  : {title : " ������       ",style : 3},  
							result7 : {title : "���뷽ʽ ", style : 3 },  
							result8  : {title : " ��������         ",style : 3,colName : "MissionProp6",rename : "EdorAppDate"},            
							result9  : {title : " Ĭ�ϴ�����       ",style : 3,colName : "DefaultOperator"},
							result10  : {title : " ��Ҫ���ϱ�����       ",style : 3,colName: "MissionProp18",rename : "ICFlag"}          
						}
					}
				}
			}
	};
	jQuery("#NoScanAppInputPool").workpool(config);
}

/*
function initSelfGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȫ�����";
        iArray[1][1]="145px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�/������";
        iArray[2][1]="145px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="Ͷ����";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="�´ν��Ѷ�Ӧ��";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=8;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="���뷽ʽ";
        iArray[6][1]="85px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="�������";
        iArray[7][1]="65px";
        iArray[7][2]=100;
        iArray[7][3]=2;
        iArray[7][4]="currency";

        iArray[8]=new Array();
        iArray[8][0]="¼��Ա";
        iArray[8][1]="60px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="¼������";
        iArray[9][1]="100px";
        iArray[9][2]=100;
        iArray[9][3]=8;
        iArray[9][21]=3;

        iArray[10]=new Array();
        iArray[10][0]="�����������";
        iArray[10][1]="0px";
        iArray[10][2]=0;
        iArray[10][3]=3;

        iArray[11]=new Array();
        iArray[11][0]="�������������";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;

        iArray[12]=new Array();
        iArray[12][0]="�������ID";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="Ĭ�ϴ�����";
        iArray[13][1]="0px";
        iArray[13][2]=0;
        iArray[13][3]=3;
        
        iArray[14]=new Array();
        iArray[14][0]="��ȫ����";
        iArray[14][1]="80px";
        iArray[14][2]=0;
        iArray[14][3]=0;
        
        iArray[15]=new Array();
        iArray[15][0]="��������";
        iArray[15][1]="100px";
        iArray[15][2]=0;
        iArray[15][3]=8;
        
        iArray[16]=new Array();
        iArray[16][0]="��������";
        iArray[16][1]="50px";
        iArray[16][2]=0;
        iArray[16][3]=0;
        
        SelfGrid = new MulLineEnter("fm", "SelfGrid");
        //��Щ���Ա�����loadMulLineǰ
        SelfGrid.mulLineCount = 0;
        SelfGrid.displayTitle = 1;
        SelfGrid.locked = 1;
        SelfGrid.canSel = 1;
        SelfGrid.canChk = 0;
        SelfGrid.selBoxEventFuncName = "HighlightByRow";
        SelfGrid.hiddenPlus = 1;
        SelfGrid.hiddenSubtraction = 1;
        SelfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert("�� WFEdorNoscanAppInit.jsp --> initSelfGrid �����з����쳣:��ʼ��������� ");
    }
}
*/
</script>
