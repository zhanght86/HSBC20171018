<%
//�������ƣ�WFEdorCancelInit.jsp
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>

<script language="JavaScript">

function initForm()
{
  try
  {
  	initCancelPool();
   // initAllGrid();
   // initSelfGrid();
   // querySelfGrid();
  }
  catch(re)
  {
    alert("WFEdorCancelInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initCancelPool(){
	var sql1 = "";
	var sql2 = "";
	var sql3 = "";
	var sql4 = "";
	var sql5 = "";
	var sql6 = "";
	if(_DBT==_DBO){
		sql1 = " and defaultoperator is null  and createoperator = '"  + operator + "' and manageCom like '"  + manageCom + "%%'"+
		  "  and not exists (select ean, es from (select EdorAcceptNo ean, EdorState es from LPEdorApp) where 1 = 1 and ean = EdorAcceptNo and es in ('4', '8', '9'))" +
			  " order by (select edorappdate "+
			  " from lpedoritem p where p.EdorAcceptNo = EdorAcceptNo and rownum = 1), makedate, maketime ";
	}else if(_DBT==_DBM){
		sql1 = " and defaultoperator is null  and createoperator = '"  + operator + "' and manageCom like '"  + manageCom + "%%'"+
		  "  and not exists (select ean, es from (select EdorAcceptNo ean, EdorState es from LPEdorApp) l where 1 = 1 and ean = EdorAcceptNo and es in ('4', '8', '9'))" +
			  " order by (select edorappdate "+
			  " from lpedoritem p where p.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime ";
	}
	if(_DBT==_DBO){
		sql2 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '''' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1)";
	}else if(_DBT==_DBM){
		sql2 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '''' end) from lpedoritem where edoracceptno = t.missionprop1 limit 0,1)";
	}
	if(_DBT==_DBO){
		sql3 = "(select count(1) from ldcalendar where commondate > (select edorappdate "+
			  " from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y')";
	}else if(_DBT==_DBM){
		sql3 = "(select count(1) from ldcalendar where commondate > (select edorappdate "+
			  " from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) and workdateflag = 'Y')";
	}
	if(_DBT==_DBO){
		sql4 = " and defaultoperator ='" + operator + "' "+
		  " and (exists (select 1 from lpedorapp b where b.edoracceptno = edoracceptno and b.othernotype = '1' "+
		  " and (exists (select 1 from lpconttempinfo c where c.icedoracceptno = edoracceptno and c.edoracceptno = c.icedoracceptno) "+ 
		  "  or not exists (select 1 from lpconttempinfo c where c.edoracceptno = edoracceptno))) "+
		  " or exists (select 1 from lpedorapp b where b.edoracceptno = edoracceptno and b.othernotype = '3')) "+
			  " order by (select edorappdate  from lpedoritem p where p.EdorAcceptNo = EdorAcceptNo and rownum = 1), makedate, maketime ";
	}else if(_DBT==_DBM){
		sql4 = " and defaultoperator ='" + operator + "' "+
		  " and (exists (select 1 from lpedorapp b where b.edoracceptno = edoracceptno and b.othernotype = '1' "+
		  " and (exists (select 1 from lpconttempinfo c where c.icedoracceptno = edoracceptno and c.edoracceptno = c.icedoracceptno) "+ 
		  "  or not exists (select 1 from lpconttempinfo c where c.edoracceptno = edoracceptno))) "+
		  " or exists (select 1 from lpedorapp b where b.edoracceptno = edoracceptno and b.othernotype = '3')) "+
			  " order by (select edorappdate  from lpedoritem p where p.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime ";
	}
	if(_DBT==_DBO){
		sql5 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '''' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1)";
	}else if(_DBT==_DBM){
		sql5 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '''' end) from lpedoritem where edoracceptno = t.missionprop1 limit 0,1)";
	}
	if(_DBT==_DBO){
		sql6 = "(select count(1) from ldcalendar where commondate > (select edorappdate "+
			  " from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y')";
	}else if(_DBT==_DBM){
		sql6 = "(select count(1) from ldcalendar where commondate > (select edorappdate "+
			  " from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) and workdateflag = 'Y')";
	}
	var config = {
			functionId : "10020006",
			public : {
				query : {
					queryButton : {},
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
								  colName:"MissionProp7",
								  refercode1:"station",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
							result0  : {title : " ��ȫ�����       ",style : 1,colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 1,colNo : 3},            
							result2  : {title : " �����������     ",style : 3},  
							result3 : {title : " ������       ",style : 1,colNo : 4},  
							result4  : {title : " ���뷽ʽ         ",style : 3}, 
							result5  : {title : " ��������         ",style : 3}, 
							result6  : {title : " �������         ",style : 3},  
							result7  : {title : " ����״̬         ",style : 3},  
							result8  : {title : " Ͷ����           ",style : 3},            
							result9  : {title : " ���Ѷ�Ӧ��       ",style : 3}           
						}
					}
				},
				resultTitle : "��������",
				result : {
					selBoxEventFuncName : "HighlightAllRow",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : sql1
					},
					mulLineCount : 5,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "��������",
								style : 0,
								colNo : 3,
								width : "70px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "���뷽ʽ",
								style : 0,
								colNo : 5 ,
								width : "70px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "¼������",
								style : 0,
								colNo : 8 ,
								width : "70px",
								colName : "makedate "
							},
							newCol3 : {
								title : "��������",
								style : 0,
								colNo : 9 ,
								width : "70px",
								colName : sql2,
								rename : "edor_appdate"
							},
							newCol4 : {
								title : "¼��Ա",
								style : 0,
								colNo : 7 ,
								width : "70px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "����Ա",
								style : 3,
								colName : "defaultoperator "
							},
							newCol6 : {
								title : "��������",
								style : 0,
								colNo : 10 ,
								width : "50px",
								colName : sql3,
								rename : "out_day"
							},
							newCol7 : {
								title : "¼������",
								style : 3,
								colName : "maketime "
							},
							result0  : {title : " ��ȫ�����       ",width : "145px", style : 0,colNo : 1},  
							result1  : {title : " �ͻ�/������         ",width : "120px", style : 0,colNo : 2},            
							result2  : {title : " �����������     ",style : 3},  
							result3 : {title : " ������       ",style : 3},  
							result4  : {title : " ���뷽ʽ         ",style : 3}, 
							result5  : {title : " ��������         ",style : 3}, 
							result6  : {title : " �������         ",width : "90px", style : 0,colNo : 6 },  
							result7  : {title : " ����״̬         ",style : 3},  
							result8  : {title : " Ͷ����           ",width : "70px",  style : 0,colNo : 4},            
							result9  : {title : " ���Ѷ�Ӧ��       ",style : 3}            
						}
					}
				}	
			},
			private : {
				query :{
					queryButton : {},
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
								  colName:"MissionProp7",
								  refercode1:"station",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
							result0  : {title : " ��ȫ�����       ",style : 1,colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 1,colNo : 3},            
							result2  : {title : " �����������     ",style : 3},  
							result3 : {title : " ������       ",style : 1,colNo : 4},  
							result4  : {title : " ���뷽ʽ         ",style : 3}, 
							result5  : {title : " ��������         ",style : 3}, 
							result6  : {title : " �������         ",style : 3},  
							result7  : {title : " ����״̬         ",style : 3},  
							result8  : {title : " Ͷ����           ",style : 3},            
							result9  : {title : " ���Ѷ�Ӧ��       ",style : 3}           
						}
					}
				},
				resultTitle : "�ҵ�����",
				result : {
					selBoxEventFuncName : "HighlightSelfRow",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : sql4
					},
					mulLineCount : 5,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "��������",
								style : 0,
								colNo : 3,
								width : "70px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "���뷽ʽ",
								style : 0,
								colNo : 5 ,
								width : "70px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "¼������",
								style : 0,
								colNo : 8 ,
								width : "70px",
								colName : "makedate "
							},
							newCol3 : {
								title : "��������",
								style : 0,
								colNo : 9 ,
								width : "70px",
								colName : sql5,
								rename : "edor_appdate"
							},
							newCol4 : {
								title : "¼��Ա",
								style : 0,
								colNo : 7 ,
								width : "70px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "����Ա",
								style : 3,
								colName : "defaultoperator "
							},
							newCol6 : {
								title : "��������",
								style : 0,
								colNo : 10 ,
								width : "50px",
								colName : sql6,
								rename : "out_day"
							},
							newCol7 : {
								title : "¼������",
								style : 3,
								colName : "maketime "
							},
							result0  : {title : " ��ȫ�����       ",width : "145px", style : 0,colNo : 1},  
							result1  : {title : " �ͻ�/������         ",width : "120px", style : 0,colNo : 2},            
							result2  : {title : " �����������     ",style : 3},  
							result3 : {title : " ������       ",style : 3},  
							result4  : {title : " ���뷽ʽ         ",style : 3}, 
							result5  : {title : " ��������         ",style : 3}, 
							result6  : {title : " �������         ",width : "90px", style : 0,colNo : 6 },  
							result7  : {title : " ����״̬         ",style : 3},  
							result8  : {title : " Ͷ����           ",width : "70px",  style : 0,colNo : 4},            
							result9  : {title : " ���Ѷ�Ӧ��       ",style : 3}            
						}
					}
				}	
			},
			midContent : { 
			id : 'MidContent',
			show : true,
			<!--html : '<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="applyMission()">'-->
			html : '<a id="riskbutton" href="javascript:void(0);" class="button" onClick="applyMission();">��    ��</a>'
			}
	};
	jQuery("#CancelInputPool").workpool(config);
	jQuery("#privateSearch").click();
}

/*
function initAllGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=30;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";
      iArray[1][1]="145px";
      iArray[1][2]=170;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�/������";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="��������";
      iArray[3][1]="70px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";
      iArray[4][1]="70px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="���뷽ʽ";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="�������";
      iArray[6][1]="90px";
      iArray[6][2]=100;
      iArray[6][3]=2;
      iArray[6][4]="Station";

      iArray[7]=new Array();
      iArray[7][0]="¼��Ա";
      iArray[7][1]="70px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="¼������";
      iArray[8][1]="70px";
      iArray[8][2]=100;
      iArray[8][3]=8;
      iArray[8][21]=3;

      iArray[9]=new Array();
      iArray[9][0]="�����������";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="�������������";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="�������Id";
      iArray[11][1]="0px";
      iArray[11][2]=0;
      iArray[11][3]=3;
      
      iArray[12]=new Array();
      iArray[12][0]="��������";
      iArray[12][1]="70px";
      iArray[12][2]=0;
      iArray[12][3]=8;
      
      iArray[13]=new Array();
      iArray[13][0]="��������";
      iArray[13][1]="50px";
      iArray[13][2]=0;
      iArray[13][3]=0;

      AllGrid = new MulLineEnter("fm", "AllGrid");
      //��Щ���Ա�����loadMulLineǰ
      AllGrid.mulLineCount = 5;
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.hiddenPlus = 1;
      AllGrid.selBoxEventFuncName = "HighlightAllRow";
      AllGrid.hiddenSubtraction = 1;
      AllGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

function initSelfGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=30;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";
      iArray[1][1]="145px";
      iArray[1][2]=170;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�/������";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="��������";
      iArray[3][1]="70px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";
      iArray[4][1]="70px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="���뷽ʽ";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="�������";
      iArray[6][1]="90px";
      iArray[6][2]=100;
      iArray[6][3]=2;
      iArray[6][4]="Station";

      iArray[7]=new Array();
      iArray[7][0]="¼��Ա";
      iArray[7][1]="70px";
      iArray[7][2]=200;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="¼������";
      iArray[8][1]="100px";
      iArray[8][2]=100;
      iArray[8][3]=8;
      iArray[8][21]=3;

      iArray[9]=new Array();
      iArray[9][0]="�����������";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="�������������";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="�������Id";
      iArray[11][1]="0px";
      iArray[11][2]=0;
      iArray[11][3]=3;
      
      iArray[12]=new Array();
      iArray[12][0]="��������";
      iArray[12][1]="100px";
      iArray[12][2]=0;
      iArray[12][3]=8;
      
      iArray[13]=new Array();
      iArray[13][0]="��������";
      iArray[13][1]="50px";
      iArray[13][2]=0;
      iArray[13][3]=0;

      SelfGrid = new MulLineEnter( "fm" , "SelfGrid" );
      //��Щ���Ա�����loadMulLineǰ
      SelfGrid.mulLineCount = 5;
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;
      SelfGrid.selBoxEventFuncName = "HighlightSelfRow";
      SelfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
