<% 
//�������ƣ�WFEdorConfirmInit.jsp
//�����ܣ���ȫ������-��ȫȷ��
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

function initInpBox()
{ 

  try
  {                                   
	//��ѯ�����ÿ�
    document.all('EdorAcceptNo').value = '';
    document.all('OtherNo').value = '';
    document.all('OtherNoType').value = '';
    
    document.all('EdorAppName').value = '';
    document.all('AppType').value = '';
    document.all('ManageCom').value = '';
    
    document.all('Operator').value="<%=tGI.Operator%>";
       
  }
  catch(ex)
  {
    alert("WFEdorAppInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {    
  initConfirmInputPool();
    //initInpBox();
    //initAllGrid();  //��ʼ����������
    //initSelfGrid(); //��ʼ���ҵ��������
   // easyQueryClickSelf();  //��ѯ�ҵ��������
    //checkPrintNotice();
  }
  catch(re)
  {
    alert("WFEdorAppInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initConfirmInputPool(){
	var config = {
			functionId : "10020007",
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
						"5" : " and defaultoperator is null order by edor_appdate, MakeDate, MakeTime "
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
								width : "60px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "���뷽ʽ",
								style : 0,
								colNo : 5 ,
								width : "60px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "¼������",
								style : 0,
								colNo : 8 ,
								width : "80px",
								colName : "makedate "
							},
							newCol3 : {
								title : "�������",
								style : 0,
								colNo : 6 ,
								width : "60px",
								colName : "(select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7))",
								rename : "p_station"
							},
							newCol4 : {
								title : "¼��Ա",
								style : 0,
								colNo : 7 ,
								width : "60px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "������",
								colNo : 11 ,
								width : "50px ",
								style : 0,
								colName : " (select  b.operator from  lpedorapp b where t.missionprop1 = b.edoracceptno )",
								rename : "uw_operator"
							},
							newCol6 : {
								title : "�˱�����",
								colNo : 9 ,
								width : "70px",
								style : 0,
								colName : "(select d.codename from ldcode d,lpedormain c where t.missionprop1 = c.EdorAcceptNo and  d.code = c.uwstate and d.codetype = 'edorcontuw' )",
								rename : "uw_state_name" 
							},
							newCol7 : {
								title : "�ͻ����",
								colNo : 10 ,
								width : "60px",
								style : 0,
								colName : " (select case count(1) when 0 then 'ͬ��' else '��ͬ��' end  from lpcuwmaster d4,lpedorapp b  where t.missionprop1 = b.edoracceptno and d4.edoracceptno = b.edoracceptno and customeridea = '1') ",
								rename : "cus_opinion" 
							},
							newCol8 : {
								title : "��������",
								style : 0,
								width : "60px", 
								colNo : 12 ,
								colName : " (select c.edorappdate from  lpedormain c where t.missionprop1 = c.EdorAcceptNo ) ",
								rename : "edor_appdate" 
							},
							newCol9 : {
								title : "��������",
								colNo : 13 ,
								width : "50px", 
								style : 0,
								colName : "(select count(1) from ldcalendar l ,lpedormain c where t.missionprop1 = c.EdorAcceptNo and l.commondate > c.edorappdate and workdateflag = 'Y') ",
								rename : "out_day" 
							},
							newCol10 : {
								title : "�˱����۴���",
								style : 3,
								colName : " (select c.uwstate from  lpedormain c where t.missionprop1 = c.EdorAcceptNo) ",
								rename : "uw_state" 
							},
							newCol11 : {
								title : "¼��ʱ��",
								style : 3,
								colName : "maketime "
							},
							newCol12 : {
								title : "Ĭ�ϲ���Ա",
								style : 3,
								colName : "defaultoperator "
							},
							result0  : {title : " ��ȫ�����       ",style : 0,width : "150px",colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 0,width : "150px",colNo : 2},            
							result2 : {title : "��������", style : 3 }, 
							result3  : {title : " ������       ",style : 3},  
							result4 : {title : "���뷽ʽ ", style : 3 }, 
							result5  : {title : " �������         ",style : 3},  
							result6  : {title : " Ͷ����           ",style : 0,width : "60px",colNo : 4},            
							result7  : {title : " ���Ѷ�Ӧ��       ",style : 3}           
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
					selBoxEventFuncName : "checkPayNotice",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and defaultoperator ='" + operator + "' order by MakeDate, MakeTime "
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
								width : "60px",
								colName : "(select codename from ldcode d1 where trim(d1.codetype) = 'edornotype' and trim(d1.code) = trim(missionprop3)) ",
								rename : "edorno_type"
								},
							newCol1 : {
								title : "���뷽ʽ",
								style : 0,
								colNo : 5 ,
								width : "60px",
								colName : "(select codename from ldcode d2 where trim(d2.codetype) = 'edorapptype' and trim(d2.code) = trim(missionprop5))",
								rename : "edor_apptype"
							},
							newCol2 : {
								title : "¼������",
								style : 0,
								colNo : 8 ,
								width : "80px",
								colName : "makedate "
							},
							newCol3 : {
								title : "�������",
								style : 0,
								colNo : 6 ,
								width : "60px",
								colName : "(select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7))",
								rename : "p_station"
							},
							newCol4 : {
								title : "¼��Ա",
								style : 0,
								colNo : 7 ,
								width : "60px",
								colName : "createoperator "
							},
							newCol5 : {
								title : "�˱�����",
								colNo : 9 ,
								width : "70px",
								style : 0,
								colName : "(select d.codename from ldcode d,lpedormain c where t.missionprop1 = c.EdorAcceptNo and  d.code = c.uwstate and d.codetype = 'edorcontuw' )",
								rename : "uw_state_name" 
							},
							newCol6 : {
								title : "�ͻ����",
								colNo : 10 ,
								width : "60px",
								style : 0,
								colName : " (select case count(1) when 0 then 'ͬ��' else '��ͬ��' end  from lpcuwmaster d4,lpedorapp b  where t.missionprop1 = b.edoracceptno and d4.edoracceptno = b.edoracceptno and customeridea = '1') ",
								rename : "cus_opinion" 
							},
							newCol7 : {
								title : "��������",
								style : 0,
								width : "60px", 
								colNo : 12 ,
								colName : " (select c.edorappdate from  lpedormain c where t.missionprop1 = c.EdorAcceptNo ) ",
								rename : "edor_appdate" 
							},
							newCol8 : {
								title : "��������",
								colNo : 13 ,
								width : "50px", 
								style : 0,
								colName : "(select count(1) from ldcalendar l ,lpedormain c where t.missionprop1 = c.EdorAcceptNo and l.commondate > c.edorappdate and workdateflag = 'Y') ",
								rename : "out_day" 
							},
							newCol9 : {
								title : "�˱����۴���",
								style : 3,
								colName : " (select c.uwstate from  lpedormain c where t.missionprop1 = c.EdorAcceptNo) ",
								rename : "uw_state" 
							},
							newCol10 : {
								title : "¼��ʱ��",
								style : 3,
								colName : "maketime "
							},
							newCol11 : {
								title : "Ĭ�ϲ���Ա",
								style : 3,
								colName : "defaultoperator "
							},
							result0  : {title : " ��ȫ�����       ",style : 0,width : "150px",colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 0,width : "150px",colNo : 2},            
							result2 : {title : "��������", style : 3 }, 
							result3  : {title : " ������       ",style : 3},  
							result4 : {title : "���뷽ʽ ", style : 3 }, 
							result5  : {title : " �������         ",style : 3},  
							result6  : {title : " Ͷ����           ",style : 0,width : "60px",colNo : 4},            
							result7  : {title : " ���Ѷ�Ӧ��       ",style : 3}           
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
	jQuery("#ConfirmInputPool").workpool(config);
	jQuery("#privateSearch").click();
}
/*
function initAllGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�/������";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���뷽ʽ";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      iArray[6][4] = "Station";
      iArray[6][18] = 236;     

      iArray[7]=new Array();
      iArray[7][0]="¼��Ա";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="¼������";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[9]=new Array();
      iArray[9][0]="�����������";         	//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="�������������";        //����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="�������Id";         	//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  


      iArray[12]=new Array();
      iArray[12][0]="�˱�����";         	//����
      iArray[12][1]="70px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[13]=new Array();
      iArray[13][0]="�˱����۱���";         	//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=200;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[14]=new Array();
      iArray[14][0]="�ͻ����";         	//����
      iArray[14][1]="60px";            		//�п�
      iArray[14][2]=200;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      
      iArray[15]=new Array();
      iArray[15][0]="������";         	//����
      iArray[15][1]="50px";            		//�п�
      iArray[15][2]=200;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[16]=new Array();
      iArray[16][0]="��������";         	//����
      iArray[16][1]="60px";            		//�п�
      iArray[16][2]=200;            			//�����ֵ
      iArray[16][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      
      iArray[17]=new Array();
      iArray[17][0]="��������";         	//����
      iArray[17][1]="50px";            		//�п�
      iArray[17][2]=200;            			//�����ֵ
      iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
    
       
      AllGrid = new MulLineEnter( "fm" , "AllGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AllGrid.mulLineCount = 5;   
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.hiddenPlus = 1;
      AllGrid.hiddenSubtraction = 1; 
      AllGrid.selBoxEventFuncName = "HighlightAllRow";       
      AllGrid.loadMulLine(iArray);  

      //��Щ����������loadMulLine����

	}
	catch(ex)
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
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�/������";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���뷽ʽ";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������     
      iArray[6][4] = "Station";
      iArray[6][18] = 236; 

      iArray[7]=new Array();
      iArray[7][0]="¼��Ա";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="¼������";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[9]=new Array();
      iArray[9][0]="�����������";         	//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="�������������";        //����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="�������Id";         	//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   



      iArray[12]=new Array();
      iArray[12][0]="�˱�����";         	//����
      iArray[12][1]="70px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[13]=new Array();
      iArray[13][0]="�˱����۱���";         	//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=200;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[14]=new Array();
      iArray[14][0]="����������EdorAppName";         	//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=200;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[15]=new Array();
      iArray[15][0]="���Ѷ�Ӧ��PaytoDate";         	//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=200;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  

  //    iArray[16]=new Array();
  //    iArray[16][0]="�ͻ���CustomerNo";         	//����
  //    iArray[16][1]="0px";            		//�п�
  //    iArray[16][2]=200;            			//�����ֵ
  //    iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
  //
  //    iArray[17]=new Array();
  //    iArray[17][0]="��һ������InsuredName1";         	//����
  //    iArray[17][1]="0px";            		//�п�
  //    iArray[17][2]=200;            			//�����ֵ
  //    iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
  //
  //    iArray[18]=new Array();
  //    iArray[18][0]="�ڶ�������InsuredName2";         	//����
  //    iArray[18][1]="0px";            		//�п�
  //    iArray[18][2]=200;            			//�����ֵ
  //    iArray[18][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
  //
  //    iArray[19]=new Array();
  //    iArray[19][0]="����������InsuredName3";         	//����
  //    iArray[19][1]="0px";            		//�п�
  //    iArray[19][2]=200;            			//�����ֵ
  //    iArray[19][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
  //
  //    iArray[20]=new Array();
  //    iArray[20][0]="��������EdorType";         	//����
  //    iArray[20][1]="0px";            		//�п�
  //    iArray[20][2]=200;            			//�����ֵ
  //    iArray[20][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
  //
  //
  //    iArray[21]=new Array();
  //    iArray[21][0]="ת������theCurrentDate";         	//����
  //    iArray[21][1]="0px";            		//�п�
  //    iArray[21][2]=200;            			//�����ֵ
  //    iArray[21][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
  //
  //    iArray[22]=new Array();
  //    iArray[22][0]="���ظ�����BackDate";         	//����
  //    iArray[22][1]="0px";            		//�п�
  //    iArray[22][2]=200;            			//�����ֵ
  //    iArray[22][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
  //
  //    iArray[23]=new Array();
  //    iArray[23][0]="VIP�ͻ�VIP";         	//����
  //    iArray[23][1]="0px";            		//�п�
  //    iArray[23][2]=200;            			//�����ֵ
  //    iArray[23][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
  //
  //    iArray[24]=new Array();
  //    iArray[24][0]="�Ǽ�ҵ��ԱStarAgent";         	//����
  //    iArray[24][1]="0px";            		//�п�
  //    iArray[24][2]=200;            			//�����ֵ
  //    iArray[24][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  //modify by jiaqiangli 2009-03-25 ��ǰ�汾�ǿͻ���ͬ���ǿ���˹��˱�
	  //modify by jiaqiangli 2009-04-03 ��ȫȷ����Ҫ���ͻ�����Ƿ�ͬ��
      iArray[16]=new Array();
      iArray[16][0]="�ͻ����";         	//����
      iArray[16][1]="60px";            		//�п�
      iArray[16][2]=200;            			//�����ֵ
      iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[17]=new Array();
      iArray[17][0]="�ͻ��������";         	//����
      iArray[17][1]="0px";            		//�п�
      iArray[17][2]=200;            			//�����ֵ
      iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[18]=new Array();
      iArray[18][0]="��������";         	//����
      iArray[18][1]="60px";            		//�п�
      iArray[18][2]=200;            			//�����ֵ
      iArray[18][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      
      iArray[19]=new Array();
      iArray[19][0]="��������";         	//����
      iArray[19][1]="50px";            		//�п�
      iArray[19][2]=200;            			//�����ֵ
      iArray[19][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      SelfGrid = new MulLineEnter( "fm" , "SelfGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrid.mulLineCount = 5;   
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;   
      SelfGrid.selBoxEventFuncName="checkPayNotice";
      SelfGrid.loadMulLine(iArray);  


      //��Щ����������loadMulLine����

	}
	catch(ex)
	{
		alert(ex);
	}
}
*/
</script>