<% 
//�������ƣ�WFEdorAutoUWInit.jsp
//�����ܣ���ȫ������-�Զ��˱�
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
 var turnPage = new turnPageClass();
function initInpBox()
{ 

  try
  {                                   
	//��ѯ�����ÿ�
   // document.all('EdorAcceptNo').value = '';
   // document.all('OtherNo').value = '';
   // document.all('OtherNoType').value = '';
    
   // document.all('EdorAppName').value = '';
   // document.all('AppType').value = '';
   // document.all('ManageCom').value = '';
       
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
    initInpBox();
    initAutoUWPool();
   // initAllGrid();  //��ʼ����������
   // initSelfGrid(); //��ʼ���ҵ��������
  //  easyQueryClickSelf();  //��ѯ�ҵ��������
  }
  catch(re)
  {
    alert("WFEdorAppInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initAutoUWPool(){
	var config = {
			functionId : "10020003",
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
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and defaultoperator is null order by MakeDate"
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
								width : "85px",
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
								title : "�������",
								style : 0,
								colNo : 6 ,
								width : "90px",
								colName : "(select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7))",
								rename : "p_station"
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
							result0  : {title : " ��ȫ�����       ",style : 0,width : "145px",colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 0,width : "120px",colNo : 2},            
							result2 : {title : "��������", style : 3 }, 
							result3  : {title : " ������       ",style : 3},  
							result4 : {title : "���뷽ʽ ", style : 3 }, 
							result5  : {title : " �������         ",style : 3},  
							result6  : {title : " Ͷ����           ",style : 0,width : "70px",colNo : 4},            
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
					canSel : 0,
					canChk : 1,
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						//"5" : " and trim(defaultoperator) ='"+operator+"' and OtherNo like'"+
                 		//	  + comcode+ "%'  order by MakeDate"
                 		"5" : " and trim(defaultoperator) ='"+operator+"' order by MakeDate"
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
								width : "85px",
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
								title : "�������",
								style : 0,
								colNo : 6 ,
								width : "90px",
								colName : "(select codename from ldcode d3 where trim(d3.codetype) = 'station' and trim(d3.code) = trim(missionprop7))",
								rename : "p_station"
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
							result0  : {title : " ��ȫ�����       ",style : 0,width : "145px",colNo : 1},  
							result1  : {title : " �ͻ�/������         ",style : 0,width : "120px",colNo : 2},            
							result2 : {title : "��������", style : 3 }, 
							result3  : {title : " ������       ",style : 3},  
							result4 : {title : "���뷽ʽ ", style : 3 }, 
							result5  : {title : " �������         ",style : 3},  
							result6  : {title : " Ͷ����           ",style : 0,width : "70px",colNo : 4},            
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
	jQuery("#AutoUWInputPool").workpool(config);
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
      iArray[1][1]="145px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�/������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="70px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="70px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���뷽ʽ";         		//����
      iArray[5][1]="70px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[7]=new Array();
      iArray[7][0]="¼��Ա";         		//����
      iArray[7][1]="70px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="¼������";         		//����
      iArray[8][1]="70px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


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
      iArray[12][0]="�������ʹ���";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="���뷽ʽ����";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�����������";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=100;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="���ս��Ѷ�Ӧ��";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=100;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      AllGrid = new MulLineEnter( "fm" , "AllGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AllGrid.mulLineCount = 5;   
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.hiddenPlus = 1;
      AllGrid.hiddenSubtraction = 1;        
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
      iArray[1][1]="145px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�/������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="70px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="70px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���뷽ʽ";         		//����
      iArray[5][1]="70px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[7]=new Array();
      iArray[7][0]="¼��Ա";         		//����
      iArray[7][1]="70px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="¼������";         		//����
      iArray[8][1]="70px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


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
      iArray[12][0]="�������ʹ���";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="���뷽ʽ����";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�����������";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=100;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="���ս��Ѷ�Ӧ��";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=100;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      SelfGrid = new MulLineEnter( "fm" , "SelfGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrid.mulLineCount = 5;   
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 0;
      SelfGrid.canChk = 1;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;        
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