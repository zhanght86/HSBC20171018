<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RnewSecUWAllInit.jsp
//�����ܣ������������κ˱�
//�������ڣ�2005-01-29 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	if(globalInput == null) 
	{
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript"><!--

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                  
  }
  catch(ex)
  {
    alert("��UWInit.jspInitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {

    //initInpBox();
    //initPolGrid();  
    //initAllPolGrid();    

    //easyQueryClick();
    initRnewSecUWAllInputPool();
  }
  catch(re)
  {
    alert("��UWInit.jspInitForm�����з����쳣:��ʼ���������!");
  }
}
function initRnewSecUWAllInputPool(){
	var config = {
			functionId : "10047001",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"�������",//�еı���
								  style : 2,
								  colNo : 3,
								  width : "100px",
								  colName:"MissionProp6",
								  maxLength:60,//����������󳤶��൱��iArray[i][2]
								  refercode1:"station",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							 newcol1:{ 
								  title:"�˱�����״̬",//�еı���
								  style : 2,
								  colNo : 4,
								  width : "80px",
								  maxLength:10,//����������󳤶��൱��iArray[i][2]
								  refercode1:"uwstate",
								  colName:"MissionProp12",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  },defaultvalue:1
								  },
							newcol2:{ 
								  title:"�Ǽ�ҵ��Ա",//�еı���
								  style : 1,
								  colNo : 5,
								  width : "70px"
								  }, 
							newcol3:{ 
								  title:"VIP�ͻ�",//�еı���
								  colNo : 6,
								  style : 1,
								  width : "70px"
								  }, 
								  
							result0  : {title : " ӡˢ��       ", style : 3},  
							result1  : {title : " ��ͬ��       ",width : "145px",style : 1,colNo : 1},  
							result2  : {title :" Ͷ������   ",style : 3 },            
							result3  : {title : "����     ",style : 3},  
							result4  : {title : " ��������       ",  style : 3},  
							result5  : {title : " �������     ",style : 3}, 
							result6  : {title : " �ͻ���     ",width : "100px",style : 1,colNo : 2},  
							result7  : {title : " Ͷ����        ",width : "80px",style : 1,colNo : 7},            
							result8  : {title : " �����˱���           ",style : 3},           
							result9  : {title : " ������    ",width : "80px" ,style : 1, colNo : 8 },         
							result10  : {title : " ת������     ",width : "70px" ,style : 8,colNo : 9 },           
							result11  : {title : " �˱�״̬     ", style : 3 },           
							result12  : {title : " �˱�Ա˵��      ", style : 3},           
							result13  : {title :" ���ظ�����      ",width : "70px" , style : 8,colNo : 10},           
							result14  : {title : " ���ظ�ʱ��     ",style : 3}         
						}
					}
				},
				resultTitle : "��������",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"4" : true,
						"5" : "  and manageCom like '"  + manageCom + "%%' "
					},
					mulLineCount : 3,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							 newcol0:{ 
								  title:"�˱�����״̬",//�еı���
								  style : 3,
								  refercode1:"uwstate",
								  colName:"MissionProp12",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol1:{ 
								  title:"�Ǽ�ҵ��Ա",//�еı���
								  style : 0,
								  colNo : 3,
								  width : "70px",
								  colName:"''''",
								  rename : "Star"
								  }, 
							newcol2:{ 
								  title:"VIP�ͻ�",//�еı���
								  colNo : 2,
								  style : 0,
								  width : "70px",
								  colName:"''''",
								  rename : "VIP"
								  }, 
							newcol3:{ 
								  title:"ת��ʱ��",//�еı���
								  colNo : 6,
								  style : 0,
								  width : "70px",
								  colName:"maketime",
								  rename : "maketime"
							},
							newcol4:{ 
								  title:"���������",//�еı���
								  colNo : 11,
								  style : 0,
								  width : "70px",
								  colName:"lastoperator",
								  rename : "l_operator"
								  },
							newcol5:{ 
								  title:"Ĭ�ϲ���Ա",//�еı���
								  style : 3,
								  colName:"defaultoperator"
								  },
							result0  : {title : " ӡˢ��       ", style : 3},  
							result1  : {title : " ��ͬ��       ",width : "145px",style : 0,colNo : 1},  
							result2  : {title :" Ͷ������   ",style : 3 },            
							result3  : {title : "����     ",style : 3},  
							result4  : {title : " ��������       ",  style : 3},  
							result5  : {title : " �������     ",width : "100px",style : 0, colNo : 10}, 
							result6  : {title : " �ͻ���     ",width : "100px",style : 0,colNo : 9},  
							result7  : {title : " Ͷ����        ",style : 3},            
							result8  : {title : " �����˱���           ",style : 3 },           
							result9  : {title : " ������    ",width : "80px" ,style : 0, colNo : 4 },         
							result10  : {title : " ת������    ",width : "70px" ,style : 0,colNo : 5 },           
							result11  : {title : " �˱�״̬     ", style : 3 },           
							result12  : {title : " �˱�Ա˵��      ", style : 3 },           
							result13  : {title :" ���ظ�����      ",width : "70px" , style : 0,colNo : 8},           
							result14  : {title : " ���ظ�ʱ��     ", width : "70px",style : 0, colNo : 7}        
						}
					 }
					}
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"�������",//�еı���
								  style : 2,
								  colNo : 3,
								  width : "100px",
								  colName:"MissionProp6",
								  maxLength:60,//����������󳤶��൱��iArray[i][2]
								  refercode1:"station",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							 newcol1:{ 
								  title:"�˱�����״̬",//�еı���
								  style : 2,
								  colNo : 4,
								  width : "80px",
								  maxLength:10,//����������󳤶��൱��iArray[i][2]
								  refercode1:"uwstate",
								  colName:"MissionProp12",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  },defaultvalue:1
								  },
							newcol2:{ 
								  title:"�Ǽ�ҵ��Ա",//�еı���
								  style : 1,
								  colNo : 5,
								  width : "70px"
								  }, 
								
							newcol3:{ 
								  title:"VIP�ͻ�",//�еı���
								  colNo : 6,
								  style : 1,
								  width : "70px"
								  }, 
							result0  : {title : " ӡˢ��       ", style : 3},  
							result1  : {title : " ��ͬ��       ",width : "145px",style : 1,colNo : 1},  
							result2  : {title :" Ͷ������   ",style : 3 },            
							result3  : {title : "����     ",style : 3},  
							result4  : {title : " ��������       ",  style : 3},  
							result5  : {title : " �������     ",style : 3}, 
							result6  : {title : " �ͻ���     ",width : "100px",style : 1,colNo : 2},  
							result7  : {title : " Ͷ����        ",width : "80px",style : 1,colNo : 7},            
							result8  : {title : " �����˱���           ",style : 3},           
							result9  : {title : " ������    ",width : "80px" ,style : 1, colNo : 8 },         
							result10  : {title : " ת������     ",width : "70px" ,style : 8,colNo : 9 },           
							result11  : {title : " �˱�״̬     ", style : 3 },           
							result12  : {title : " �˱�Ա˵��      ", style : 3},           
							result13  : {title :" ���ظ�����      ",width : "70px" , style : 8,colNo : 10},           
							result14  : {title : " ���ظ�ʱ��     ",style : 3}        
						}
					}
				},
				resultTitle : "���˹�����",
				result : {
					selBoxEventFuncName : "IniteasyQueryAddClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : operator,
						"5" : " and trim(defaultoperator) ='"+operator+"' and manageCom like '"  + manageCom + "%%'" 
					},
					mulLineCount : 3,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							 newcol0:{ 
								  title:"�˱�����״̬",//�еı���
								  style : 3,
								  refercode1:"uwstate1",
								  colName:"MissionProp12",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol1:{ 
								  title:"�Ǽ�ҵ��Ա",//�еı���
								  style : 0,
								  colNo : 3,
								  width : "70px",
								  colName:"''''",
								  rename : "Star"
								  }, 
							newcol2:{ 
								  title:"VIP�ͻ�",//�еı���
								  colNo : 2,
								  style : 0,
								  width : "70px",
								  colName:"''''",
								  rename : "VIP"
								  }, 
							newcol3:{ 
								  title:"ת��ʱ��",//�еı���
								  colNo : 6,
								  style : 0,
								  width : "70px",
								  colName:"maketime",
								  rename : "maketime"
							},
							newcol4:{ 
								  title:"���������",//�еı���
								  colNo : 9,
								  style : 0,
								  width : "70px",
								  colName:"lastoperator",
								  rename : "l_operator"
								  },
							newcol5:{ 
								  title:"Ĭ�ϲ���Ա",//�еı���
								  style : 3,
								  colName:"defaultoperator"
								  },
							result0  : {title : " ӡˢ��       ", style : 3},  
							result1  : {title : " ��ͬ��       ",width : "145px",style : 0,colNo : 1},  
							result2  : {title :" Ͷ������   ",style : 3 },            
							result3  : {title : "����     ",style : 3},  
							result4  : {title : " ��������       ",  style : 3},  
							result5  : {title : " �������     ",width : "100px",style : 0, colNo : 10}, 
							result6  : {title : " �ͻ���     ",width : "100px",style : 0,colNo : 9},  
							result7  : {title : " Ͷ����        ",style : 3},            
							result8  : {title : " �����˱���           ",style : 3 },           
							result9  : {title : " ������    ",width : "80px" ,style : 0, colNo : 4 },         
							result10  : {title : " ת������    ",width : "70px" ,style : 0,colNo : 5 },           
							result11  : {title : " �˱�״̬     ", style : 3 },           
							result12  : {title : " �˱�Ա˵��      ", style : 3 },           
							result13  : {title :" ���ظ�����      ",width : "70px" , style : 0,colNo : 8},           
							result14  : {title : " ���ظ�ʱ��     ", width : "70px",style : 0, colNo : 7}                     
						}
						}
					}
			},
			midContent : { 
			id : 'MidContent',
			show : true,
			html : '<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="applyTheMission()">'
			}
	};
	jQuery("#RnewSecUWAllInputPool").workpool(config);
	jQuery("#privateSearch").click();
	totalNumPublic();
}

function totalNumPublic(){
		jQuery("#publicSearch").after("�ܵ�����<input id ='totalNumPublic' type='readonly' />").bind("click",function(){
			var a = PublicWorkPoolGrid.mulLineCount;
			jQuery("#totalNumPublic").val(a);
		});
	}
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="VIP�ͻ�";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="ת������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������       
      
      iArray[6]=new Array();
      iArray[6][0]="ת��ʱ��";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������       

      iArray[7]=new Array();                                                       
      iArray[7][0]="���ظ�ʱ��";         		//����                                     
      iArray[7][1]="100px";            		//�п�                                   
      iArray[7][2]=100;            			//�����ֵ                                 
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[8]=new Array();
      iArray[8][0]="���ظ�����";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="�ͻ���";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="�������";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="���������";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[12] = new Array();
      iArray[12][0] = "�����������";
      iArray[12][1] = "0px";
      iArray[12][2] = 0;
      iArray[12][3] = 3;

      iArray[13] = new Array();
      iArray[13][0] = "�������������";
      iArray[13][1] = "0px";
      iArray[13][2] = 100;
      iArray[13][3] = 3;

      iArray[14] = new Array();
      iArray[14][0] = "�������ID";
      iArray[14][1] = "0px";
      iArray[14][2] = 0;
      iArray[14][3] = 3;

      iArray[15] = new Array();
      iArray[15][0] = "��������ǰ�״̬";
      iArray[15][1] = "0px";
      iArray[15][2] = 0;
      iArray[15][3] = 3;
      
      iArray[16] = new Array();
      iArray[16][0] = "ӡˢ��";
      iArray[16][1] = "0px";
      iArray[16][2] = 0;
      iArray[16][3] = 3;
      
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray); 
      
      PolGrid.selBoxEventFuncName = "IniteasyQueryAddClick";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


// ������Ϣ�б�ĳ�ʼ��
function initAllPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="VIP�ͻ�";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="ת������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������          
      
      iArray[6]=new Array();
      iArray[6][0]="ת��ʱ��";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[7]=new Array();                                                       
      iArray[7][0]="���ظ�ʱ��";         		//����                                     
      iArray[7][1]="100px";            		//�п�                                   
      iArray[7][2]=100;            			//�����ֵ                                 
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[8]=new Array();
      iArray[8][0]="���ظ�����";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="�ͻ���";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="�������";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="���������";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[12] = new Array();
      iArray[12][0] = "�����������";
      iArray[12][1] = "0px";
      iArray[12][2] = 0;
      iArray[12][3] = 3;

      iArray[13] = new Array();
      iArray[13][0] = "�������������";
      iArray[13][1] = "0px";
      iArray[13][2] = 100;
      iArray[13][3] = 3;

      iArray[14] = new Array();
      iArray[14][0] = "�������ID";
      iArray[14][1] = "0px";
      iArray[14][2] = 0;
      iArray[14][3] = 3;

      iArray[15] = new Array();
      iArray[15][0] = "��������ǰ�״̬";
      iArray[15][1] = "0px";
      iArray[15][2] = 0;
      iArray[15][3] = 3;
      
      iArray[16] = new Array();
      iArray[16][0] = "ӡˢ��";
      iArray[16][1] = "0px";
      iArray[16][2] = 0;
      iArray[16][3] = 3;
      
      AllPolGrid = new MulLineEnter( "fm" , "AllPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AllPolGrid.mulLineCount = 3;   
      AllPolGrid.displayTitle = 1;
      AllPolGrid.locked = 1;
      AllPolGrid.canSel = 1;
      AllPolGrid.hiddenPlus = 1;
      AllPolGrid.hiddenSubtraction = 1;
      AllPolGrid.loadMulLine(iArray); 
      //AllPolGrid.selBoxEventFuncName = "ApplyUW";   
      //AllPolGrid.selBoxEventFuncName = "easyQueryAddClick";   

      }
      catch(ex)
      {
        alert(ex);
      }
}

--></script>