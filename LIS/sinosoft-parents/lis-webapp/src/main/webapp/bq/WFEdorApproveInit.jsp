<%
//�������ƣ�WFEdorApproveInit.jsp
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
        initInpBox();
        initApproveInputPool();
      //  initAllGrid();
       // initSelfGrid();
      //  easyQueryClickSelf();
        //var tUserGradeSQL="select edorpopedom,codename  from ldedoruser a,ldcode b where a.edorpopedom=b.code and codetype='edorpopedom' and usercode='"+operator+"' and usertype='1'";
        var sqlid3="WFEdorApproveSql3";
    	var mySql3=new SqlClass();
    	mySql3.setResourceName("bq.WFEdorApproveInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3); //ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(operator);
		var tUserGradeSQL=mySql3.getString();
    	var arrResult;
    	var tUserGrade;
    	//alert(tUserGradeSQL); 
      	arrResult= easyExecSql(tUserGradeSQL, 1, 0,1);
      //alert(arrResult);
      	//alert(arrResult[0][0]);
       	//fm.EdorPopedom.value=arrResult[0][0];
       	//fm.EdorPopedomName.value=arrResult[0][1];
       	//alert(fm.EdorPopedom.value);
    }
    catch(re)
    {
        alert("WFEdorAppInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

function initInpBox()
{
    try
    {
        //nothing
    }
    catch (ex)
    {
        alert("WFEdorAppInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}
function initApproveInputPool(){
	var sql1 = "";
	var sql2 = "";
	var sql3 = "";
	var sql4 = "";
	var sql5 = "";
	var sql6 = "";
	if(_DBT==_DBO){
		sql1 = " and manageCom like '" + manageCom + "%%'" + " and defaultoperator is null  order by (select edorappdate from lpedoritem l "+
		  "where l.EdorAcceptNo = EdorAcceptNo and rownum = 1), MakeDate, MakeTime";
	}else if(_DBT==_DBM){
		sql1 = " and manageCom like '" + manageCom + "%%'" + " and defaultoperator is null  order by (select edorappdate from lpedoritem l "+
		  "where l.EdorAcceptNo = EdorAcceptNo and rownum = 1), MakeDate, MakeTime";
	}
	if(_DBT==_DBO){
		sql2 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1)";
	}else if(_DBT==_DBM){
		sql2 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1)";
	}
	if(_DBT==_DBO){
		sql3 = "(select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem "+
		"where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y')";
	}else if(_DBT==_DBM){
		sql3 = "(select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem "+
		"where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y')";
	}
	if(_DBT==_DBO){
		sql4 = " and trim(defaultoperator) ='"+operator+ "' order by (select edorappdate from lpedoritem l "+
		  "where l.EdorAcceptNo = EdorAcceptNo and rownum = 1), MakeDate, ModifyTime";
	}else if(_DBT==_DBM){
		sql4 = " and trim(defaultoperator) ='"+operator+ "' order by (select edorappdate from lpedoritem l "+
		  "where l.EdorAcceptNo = EdorAcceptNo and rownum = 1), MakeDate, ModifyTime";
	}
    if(_DBT==_DBO){
    	sql5 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1)";
	}else if(_DBT==_DBM){
		sql5 = "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1)";
	}
    if(_DBT==_DBO){
    	sql6 = "(select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem "+
		"where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y')"
    }else if(_DBT==_DBM){
    	sql6 = "(select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem "+
		"where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y')"
    }
	
	var config = {
			functionId : "10020005",
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
							newcol4:{ 
								  title:"��͸��˼���",//�еı���
								  colNo : 8,
								  style : 2,
								  colName:"edor_popedom",
								  refercode1:"edorpopedom",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and (select b.apppregrade  from lpedorapp b where t.missionprop1 = b.EdorAcceptNo and apppregrade like '" + value + "%') is not null ";
								  }
								  }, 
							result0  : {title : " ��ȫ�����       ",style : 1,colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 1,colNo : 3},            
							result2  : {title : " �����������     ",style : 3},  
							result3 : {title : " ������       ",style : 1,colNo : 4},  
							result4  : {title : " ���뷽ʽ         ",style : 3}, 
							result5  : {title : " �������         ",style : 3},  
							result6  : {title : " Ͷ����           ",style : 3},            
							result7  : {title : " ���Ѷ�Ӧ��       ",style : 3}           
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
					mulLineCount : 3,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "��������",
								style : 0,
								colNo : 3,
								width : "80px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "���뷽ʽ",
								style : 0,
								colNo : 6 ,
								width : "80px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "¼������",
								style : 0,
								colNo : 9 ,
								width : "100px",
								colName : "makedate "
							},
							newCol4 : {
								title:"��͸��˼���",//�еı���
								// colNo : 10,
								style : 3,
								colName:"(select b.apppregrade  from lpedorapp b where t.missionprop1 = b.EdorAcceptNo)",  
								rename : "edor_popedom"
							},
							newcol3:{ 
								title : "¼��Ա",
								style : 0,
								colNo : 8 ,
								width : "60px",
								colName : "createoperator "
								  }, 
							newCol5 : {
								title : "��������",
								style : 0,
								colNo : 11 ,
								width : "100px",
								colName : sql2,
								rename : "edor_edorappdate"
								
							},
							newCol6 : {
								title : "��������",
								style : 0,
								colNo : 12 ,
								width : "60px",
								colName : sql3,
								rename : "edor_outdate"
								
							},
							newCol7 : {
								title : "����Ա",
								style : 3,
								colName : "defaultoperator "
							},
							newCol8 : {
								title : "����ʱ��",
								style : 3,
								colName : "maketime "
							},
							newCol9 : {
								title:"��͸��˼���", 
							    colNo : 10,
								style : 0,
								colName:"(select concat(b.apppregrade,'����ȫԱ')  from lpedorapp b where t.missionprop1 = b.EdorAcceptNo)",  
								rename : "edor_popedom_name"
							},
							result0  : {title : " ��ȫ�����       ",style : 0,width : "145px",colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 0,width : "145px",colNo : 2},            
							result2 : {title : "��������", style : 3 }, 
							result3  : {title : " ������       ",style : 3},  
							result4 : {title : "���뷽ʽ ", style : 3 }, 
							result5  : {title : " �������         ",style : 0,width : "60px",colNo : 7 },  
							result6  : {title : " Ͷ����           ",style : 0,width : "70px",colNo : 4},            
							result7  : {title : " �´ν��Ѷ�Ӧ��       ",style : 0,width : "100px",colNo : 5}           
						}
					}
				}	
			},
			private : {
				query :{
					show : false
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
					mulLineCount : 3,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "��������",
								style : 0,
								colNo : 3,
								width : "80px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "���뷽ʽ",
								style : 0,
								colNo : 6 ,
								width : "80px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "¼������",
								style : 0,
								colNo : 9 ,
								width : "100px",
								colName : "makedate "
							},
							newCol4 : {
								title:"��͸��˼���",//�еı���
								colNo : 10,
								style : 0,
								colName:"(select concat(b.apppregrade , '����ȫԱ') from lpedorapp b where t.missionprop1 = b.EdorAcceptNo)",  
								rename : "Edor_Popedom"
							},
							newcol3:{ 
								title : "¼��Ա",
								style : 0,
								colNo : 8 ,
								width : "60px",
								colName : "createoperator "
								  }, 
							newCol5 : {
								title : "��������",
								style : 0,
								colNo : 11 ,
								width : "100px",
								colName : sql5,
								rename : "edor_edorappdate"
								
							},
							newCol6 : {
								title : "��������",
								style : 0,
								colNo : 12 ,
								width : "60px",
								colName : sql6,
								rename : "edor_outdate"
								
							},
							newCol7 : {
								title : "����Ա",
								style : 3,
								colName : "defaultoperator "
							},
							newCol8 : {
								title : "���ʱ��",
								style : 3,
								colName : "ModifyTime "
							},
							newCol9 : {
								title : "��ȫ״̬",
								style : 0,
								colNo : 13 ,
								width : "90px",
								colName : "case (select count(distinct StateFlag) from LOPRTManager "+
         								  " where othernotype = '02' "+
            							  " and StandbyFlag1 = t.missionprop1 " +
            							  " and StandbyFlag3 = '3' " +
           								  "  and Code = 'BQ38') " +
        								  "  when 0 then '�����δ�·�' when 2 then '��������·�' " +
        								  "  else (case (select distinct StateFlag from LOPRTManager " +
                  						  " where othernotype = '02' and StandbyFlag1 = t.missionprop1 and StandbyFlag3 = '3' "+
                    					  " and Code = 'BQ38') when 'A' then '��������·�' else '������ѻظ�' end) end ",
                    			rename : "edor_StateFlag"
							},
							result0  : {title : " ��ȫ�����       ",style : 0,width : "145px",colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 0,width : "145px",colNo : 2},            
							result2 : {title : "��������", style : 3 }, 
							result3  : {title : " ������       ",style : 3},  
							result4 : {title : "���뷽ʽ ", style : 3 }, 
							result5  : {title : " �������         ",style : 0,width : "60px",colNo : 7 },  
							result6  : {title : " Ͷ����           ",style : 0,width : "70px",colNo : 4},            
							result7  : {title : " �´ν��Ѷ�Ӧ��       ",style : 0,width : "100px",colNo : 5}           
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
	jQuery("#EdorApproveInputPool").workpool(config);
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
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�/������";
        iArray[2][1]="145px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="Ͷ����";
        iArray[4][1]="50px";
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
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="�������";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=2;
        iArray[7][4]="Station";

        iArray[8]=new Array();
        iArray[8][0]="¼��Ա";
        iArray[8][1]="60px";
        iArray[8][2]=200;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="¼������";
        iArray[9][1]="100px";
        iArray[9][2]=100;
        iArray[9][3]=8;
        iArray[9][21]=3;

        iArray[10]=new Array();
        iArray[10][0]="������ͼ���";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=0;
        iArray[10][21]=3;

        iArray[11]=new Array();
        iArray[11][0]="�����������";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;

        iArray[12]=new Array();
        iArray[12][0]="�������������";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="�������Id";
        iArray[13][1]="0px";
        iArray[13][2]=0;
        iArray[13][3]=3;
        
        iArray[14]=new Array();
        iArray[14][0]="��������";
        iArray[14][1]="100px";
        iArray[14][2]=0;
        iArray[14][3]=8;

        iArray[15]=new Array();
        iArray[15][0]="��������";
        iArray[15][1]="60px";
        iArray[15][2]=0;
        iArray[15][3]=0;
        


        AllGrid = new MulLineEnter("document", "AllGrid");
        //��Щ���Ա�����loadMulLineǰ
        AllGrid.mulLineCount = 3;
        AllGrid.displayTitle = 1;
        AllGrid.locked = 1;
        AllGrid.canSel = 1;
        AllGrid.canChk = 0;
        AllGrid.hiddenPlus = 1;
        AllGrid.selBoxEventFuncName = "HighlightAllRow";
        AllGrid.hiddenSubtraction = 1;
        AllGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert(ex);
    }
}

//
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
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�/������";
        iArray[2][1]="145px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="Ͷ����";
        iArray[4][1]="50px";
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
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="�������";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=2;
        iArray[7][4]="Station";

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
        iArray[10][0]="������ͼ���";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=0;
        iArray[10][21]=3;

        iArray[11]=new Array();
        iArray[11][0]="�����������";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;

        iArray[12]=new Array();
        iArray[12][0]="�������������";
        iArray[12][1]="0px";
        iArray[12][2]=0;
        iArray[12][3]=3;

        iArray[13]=new Array();
        iArray[13][0]="�������Id";
        iArray[13][1]="0px";
        iArray[13][2]=0;
        iArray[13][3]=3;
        
        iArray[14]=new Array();
        iArray[14][0]="��������";
        iArray[14][1]="100px";
        iArray[14][2]=0;
        iArray[14][3]=8;

        iArray[15]=new Array();
        iArray[15][0]="��������";
        iArray[15][1]="60px";
        iArray[15][2]=0;
        iArray[15][3]=0;
        
        iArray[16]=new Array();
        iArray[16][0]="��ȫ״̬";
        iArray[16][1]="90px";
        iArray[16][2]=0;
        iArray[16][3]=0;

        SelfGrid = new MulLineEnter("document", "SelfGrid");
        //��Щ���Ա�����loadMulLineǰ
        SelfGrid.mulLineCount = 3;
        SelfGrid.displayTitle = 1;
        SelfGrid.locked = 1;
        SelfGrid.canSel = 1;
        SelfGrid.canChk = 0;
        SelfGrid.hiddenPlus = 1;
        SelfGrid.hiddenSubtraction = 1;
        SelfGrid.selBoxEventFuncName = "HighlightSelfRow";
        SelfGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert(ex);
    }
}
*/
</script>
