<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ScanContInit.jsp
//�����ܣ���������Լɨ�������¼�� 
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
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
    document.all('PrtNo').value ="";
    document.all('ManageCom').value ="";
    document.all('InputDate').value ="";
  }
  catch(ex)
  {
    alert("��GroupUWAutoInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {
    initInpBox();
    initScanPool();

    //initSelfGrpGrid();
   // initGrpGrid();
    //easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
//add by lzf
var config;
var fId;
if(type == "1"){//������ɨ
	fId = "0000001099";	
}else if(type == "2"){//�ŵ���ɨ
	fId = "0000002099";
}

function initScanPool() {
	var config = {
		activityId : fId,
		//functionId : "10010002",
		operator :  operator,
		public : {			
			query : {	
				queryButton : {							
				},
				arrayInfo : {
					col : {		
						newCol0:{
							title : " ��������  ",					
							refercode1 : "paydate",
							addCondition : function(colName, value) {
								return " and exists (select 'X' from ljtempfee where tempfeetype='1' and confdate is null and otherno =t.missionprop1 and paydate=to_date('"
										+ value +"','yyyy-mm-dd'))";
										
							},
							style : "8"						
							},

						result0 : {
							title : " Ͷ������  ",
							verify : "Ͷ������|num&len=14",
							colNo : 1,
							style : "1"
						},
						result2 : {
							title : " �������  ",
							colNo : 2,
							refercode1 : "station",
							addCondition : function(colName, value) {
								return " and " + colName + " like '" + value
										+ "%' ";
							}
						},
						result1 : {
							title: " ɨ������  ",
							style : "8",
							colNo : 3
						},
						
						result3 : {
							title: "  ��  ��  Ա    ",
							style : "3"
						},
						result4 : {
							title: "��֤����",
							style : 3
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
						+ comcode
						+ "%'  order by prtno"
				},
				mulLineCount : 0,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",
						result0 : {
							title : " Ͷ������  ",
							colNo : 1,
							style : "1"
						},
						result2 : {
							title : " �������  ",
							colNo : "2"
						},
						result1 : {
							title : " ɨ������  ",
							style : "8",
							colNo : 3
						},
						
						result3 : {
							style : "3"
						},
						result4 : {							
							style : "3"
						}
					}
				}
			}
		},
		private :{
			query : {
				queryButton : {		
				},
				arrayInfo : {
					col : {		
						
						result0 : {
							title : " Ͷ������  ",
							verify : "Ͷ������|num&len=14",
							colNo : 1,
							style : 1
						},
						result2 : {
							title : " �������  ",
							colNo : 2,
							refercode1 : "station",
							addCondition : function(colName, value) {
								return " and " + colName + " like '" + value
										+ "%' ";
							}
						},
						result1 : {
							title: " ɨ������ ",
							style : 8,
							colNo : 3
						},
						
						result3 : {
							title: "����Ա",
							style : "3",
							colNo : 4
						},
						result4 : {
							title: "��֤����",
							style : 3,
							colNo : 5
						}
					}
				}
			},
			result : {
				 selBoxEventFuncName:"InitGoToInput",
				 selBoxEventFuncParam:"",
				condition : {
					"0" : false,
					"1" : false,
					"2" : false,
					"5" : "and managecom like'"
						+ comcode
						+ "%'  order by prtno"
				},
				mulLineCount : 10,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",
						result0 : {
							title : " Ͷ������  ",
							colNo : 1
						},
						result2 : {
							title : " �������  ",
							colNo : 2
						},
						result1 : {
							title : " ɨ������  ",
							style : 8,
							colNo : 3
						},
						
						result3 : {
							title: "����Ա",
							style : "3"
						},
						result4 : {
							title: "��֤����",  
							style : "3"
						}
					}
				}
			}						
        },
        midContent :{//the content between two pools
			id : "MidContent",
			show : true,
			html : "<a href='javascript:void(0)' class=button onclick='ApplyUW();'>��  ��</a>"
		}
	};

	jQuery("#ScanPool").workpool(config);
	jQuery("#privateSearch").click();
}

/**function initScanPrivatePool() {
	var config = {
		activityId : "0000001099",
		//functionId : "10010002",
		operator : operator,
		public : {			
			show : false		
			},
		private : {
			id : "ScanPrivatePool",
			show : true,
			resultTitle : "ɨ�����Ϣ",
			query : {
				arrayInfo : {
					col : {		
						
						result0 : {
							title : "Ͷ������",
							verify : "Ͷ������|num&len=14",
							colNo : 1,
							style : 1
						},
						result2 : {
							title : "�������",
							colNo : 2,
							refercode1 : "station",
							addCondition : function(colName, value) {
								return " and " + colName + " like '" + value
										+ "%' ";
							}
						},
						result1 : {
							title: "ɨ������",
							style : 8,
							colNo : 3
						},
						
						result3 : {
							title: "����Ա",
							style : "3",
							colNo : 4
						},
						result4 : {
							title: "��֤����",
							style : 3,
							colNo : 5
						}
					}
				}
			},
			result : {
				 selBoxEventFuncName:"InitGoToInput",
				 selBoxEventFuncParam:"",
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
						result0 : {
							title : "Ͷ������",
							colNo : 1
						},
						result2 : {
							title : "�������",
							colNo : 2
						},
						result1 : {
							title : "ɨ������",
							style : 8,
							colNo : 3
						},
						
						result3 : {
							title: "����Ա",
							style : "3"
						},
						result4 : {
							title: "��֤����",
							style : "3"
						}
					}
				},
				condition : {
					"5" : "and managecom like'"
							+ comcode
							+ "%'  order by prtno"
				}
			}
		}
	};
	jQuery("#ScanPrivatePool").workpool(config);
	jQuery("#privateSearch").click();
}
// ������Ϣ�б�ĳ�ʼ��
/**function initGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ������";         	//����
      iArray[1][1]="140px";            	//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         	//����
      iArray[2][1]="120px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ɨ������";         	//����
      iArray[3][1]="200px";            	//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="�����������";      //����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="�������������";    //����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������Id";      //����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[7]=new Array();
      iArray[7][0]="��֤����";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������    

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount =0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;        
      GrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfGrpGrid()
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
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="140px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ɨ������";         		//����
      iArray[3][1]="200px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="�����������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="�������������";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������Id";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��֤����";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
             

      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrpGrid.mulLineCount =0;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 1;
      SelfGrpGrid.canChk = 0;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;   
      SelfGrpGrid.selBoxEventFuncName = "InitGoToInput";     
      SelfGrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
*/
</script>