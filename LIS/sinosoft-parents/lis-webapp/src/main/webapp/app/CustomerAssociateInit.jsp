<%
//�������ƣ�ClientAssociateInit.jsp
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox(){ }

function initForm()
{
  try
  {
	  initCustomerPool();
    //initInpBox();
    //initClientListGrid();
    //initOPolGrid();
    //easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ClientAssociateInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//add by lzf 2013-03-18
function initCustomerPool(){
	var config = {
		functionId : "10010056",
		//activityId : "0000001404",
		operator : operator,
		public : {
			query : {
				queryButton : {
					"1" : {title : "��  ��" , func : "ApplyUW"}
				},
				arrayInfo : {
					col : {
						newCol0:{
							title : " ��������  ",
							refercode1 : "firsttrialdate",
							addCondition : function(colName, value) {
								return " and exists (select 1 from lccont where contno= t.missionprop1 and firsttrialdate = to_date('"
										+ value +"','yyyy-mm-dd'))";
										
							},
							style : "8"						
							},
						result0 : {title : "Ͷ������",verify : "Ͷ������|num&len=14",style :"3"},
						result1 : {title : " ӡˢ��   ",verify : "ӡˢ��|num&len=14",style :"1",colNo : 1},
						result2 : {
							title : " ���ֱ��� ",
							style :"2",
							colNo : 5,
							refercode1 : "riskcode",
							colName : "missionprop3",
							addCondition : function(colName,value){
								return " and "+ colName +" like '" + value
								+ "%' ";
							}
							
							},
						result3 : {title : "Ͷ��������",style :"1"},
						result4 : {title : "����������",style :"1"},
						result5 : {
							title : " ɨ������  ",
							style :"8",
							colNo : 4,
							refercode1 : "makedate",
							addCondition : function(colName,value){
								return " and exists (select 1 from es_doc_main where doccode = t.missionprop1 and makedate =to_date('"+ value +"','yyyy-mm-dd'))";
							}
							
							},
						result6 : {title : "Ͷ���˿ͻ���",style :"1"},
						result7 : {title : "�����˿ͻ���",style :"1"},
						result8 : {
							title : " �������  ",
							style :"2",
							colNo : 2,						
							refercode1 : "station",
							colName : "missionprop5",
							addCondition : function(colName , value){
								return " and "+ colName +" like '" + value
								+ "%' ";
							   }
							},
						result9 : {title : "�����˱���",style :"1",colNo : 6}
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
					col :{
						col5 : "0",
						col6 : "0",
						col7 : "0",
						newCol0:{
							title : " ��������  ",
							colName : "(select max(firsttrialdate) from lccont where contno= t.missionprop1)",
							rename : "firsttrialdate",							
							style : "8"						
							},
						result0 : {
							title : " Ͷ������  ",
							style : "1",
							colNo : 1
						},
						result1 : {
							title : " ӡ  ˢ  �� ",
							style : "1",
							colNo : 2
						},
						result2 : {
							title : "���ֱ���",
							style : "1",
							colNo : 3
						},
						result3 : {
							title : " Ͷ���� ",
							style : "1",
							colNo : 4
						},
						result4 : {
							title : " ������ ",
							style : "1",
							colNo : 5
						},
						result5 : {
							title : " ɨ������ ",
							style : "8",
							colNo : 6
						},
						result6 : {
							title : "Ͷ���˿ͻ���",
							style : "1",
							colNo : 7
						},
						result7 : {
							title : "�����˿ͻ���",
							style : "1",
							colNo : 8
						},
						result8 : {
							title : "�������",
							style : "3"
						},
						result9 : {
							title : "�����˱��� ",
							style : "3"
						}
						
					}
				}
			}
		},
		private : {
			query : {
				queryButton : {
				
				},
				arrayInfo : {
					col : {
						newCol0:{
							title : " ��������  ",
							refercode1 : "firsttrialdate",
							addCondition : function(colName, value) {
								return " and exists (select 1 from lccont where contno= t.missionprop1 and firsttrialdate = to_date('"
										+ value +"','yyyy-mm-dd'))";
										
							},
							style : "8"						
							},
						result0 : {title : "Ͷ������",verify : "Ͷ������|num&len=14",style :"3"},
						result1 : {title : " ӡˢ��   ",verify : "ӡˢ��|num&len=14",style :"1",colNo : 1},
						result2 : {
							title : " ���ֱ��� ",
							style :"2",
							colNo : 5,
							refercode1 : "riskcode",
							colName : "missionprop3",
							addCondition : function(colName,value){
								return " and "+ colName +" like '" + value
								+ "%' ";
							}
							
							},
						result3 : {title : "Ͷ��������",style :"1"},
						result4 : {title : "����������",style :"1"},
						result5 : {
							title : " ɨ������  ",
							style :"8",
							colNo : 4,
							refercode1 : "makedate",
							addCondition : function(colName,value){
								return " and exists (select 1 from es_doc_main where doccode = t.missionprop1 and makedate =to_date('"+ value +"','yyyy-mm-dd'))";
							}
							
							},
						result6 : {title : "Ͷ���˿ͻ���",style :"1"},
						result7 : {title : "�����˿ͻ���",style :"1"},
						result8 : {
							title : " �������  ",
							style :"2",
							colNo : 2,						
							refercode1 : "station",
							colName : "missionprop5",
							addCondition : function(colName , value){
								return " and "+ colName +" like '" + value
								+ "%' ";
							   }
							},
						result9 : {title : "�����˱���",style :"1",colNo : 6}
					}
				}
				
			},
			resultTitle : "���˹�����",
			result : {
				selBoxEventFuncName : "displayMissionInfo",
				selBoxEventFuncParam : "",
				condition : {
					"0" : false,
					"1" : false,
					"2" : false,
					"5" : "and managecom like'"
						+ comcode
						+ "%'  order by prtno"
				},
				mulLineCount : 0,
				arrayInfo : {
					col :{
						col5 : "0",
						col6 : "0",
						col7 : "0",
						newCol0:{
							title : " ��������  ",
							colName : "(select max(firsttrialdate) from lccont where contno= t.missionprop1)",
							rename : "firsttrialdate",							
							style : "0"						
							},
						result0 : {
							title : " Ͷ������  ",
							style : "0",
							colNo : 1
						},
						result1 : {
							title : " ӡ  ˢ  �� ",
							style : "0",
							colNo : 2
						},
						result2 : {
							title : "���ֱ���",
							style : "0",
							colNo : 3
						},
						result3 : {
							title : " Ͷ���� ",
							style : "0",
							colNo : 4
						},
						result4 : {
							title : " ������ ",
							style : "0",
							colNo : 5
						},
						result5 : {
							title : " ɨ������ ",
							style : "0",
							colNo : 6
						},
						result6 : {
							title : "Ͷ���˿ͻ���",
							style : "3"
						},
						result7 : {
							title : "�����˿ͻ���",
							style : "3"
						},
						result8 : {
							title : "�������",
							style : "3"
						},
						result9 : {
							title : "�����˱���",
							style : "3"
						}						
					}
				}
			}
		}
		
	};
	jQuery("#CustomerPool").workpool(config);
	jQuery("#privateSearch").click();
}
//end by lzf
/**function initClientListGrid()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         		    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";        			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="Ͷ������";    	        //����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";    	        //����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���"; 	            //����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="Ͷ����"; 	            //����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;  

      iArray[5]=new Array();
      iArray[5][0]="������"; 	            //����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������"; 	            //����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[7]=new Array();
      iArray[7][0]="ɨ������"; 	            //����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="Ͷ���˿ͻ�����"; 	            //����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;  
      
      iArray[9]=new Array();
      iArray[9][0]="�����˿ͻ�����"; 	            //����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=1;  

	  iArray[10]=new Array();
      iArray[10][0]="�������";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="�������";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�����������";      //����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=200;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[13]=new Array();
      iArray[13][0]="�������������";    //����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=200;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[14]=new Array();
      iArray[14][0]="�������Id";      //����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      ClientListGrid = new MulLineEnter( "fm" , "ClientListGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ClientListGrid.mulLineCount = 0;   
      ClientListGrid.displayTitle = 1;
      ClientListGrid.canSel = 1;
      ClientListGrid.loadMulLine(iArray);  
      
      //ClientList.selBoxEventFuncName = "customerUnion";
       } 
      catch(ex) {
      alert(ex);
    }
}

function initOPolGrid() {                               
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
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0; 
      
      
      iArray[4]=new Array();
      iArray[4][0]="Ͷ����";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[7]=new Array();
      iArray[7][0]="ɨ������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="Ͷ���˿ͻ�����";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�����˿ͻ��������";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[10]=new Array();
      iArray[10][0]="�������";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="�������";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�����������";      //����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=200;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[13]=new Array();
      iArray[13][0]="�������������";    //����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=200;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[14]=new Array();
      iArray[14][0]="�������Id";      //����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      OPolGrid = new MulLineEnter( "fm" , "OPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      OPolGrid.mulLineCount = 1;   
      OPolGrid.displayTitle = 1;
      OPolGrid.locked = 1;
      OPolGrid.canSel = 1;
      OPolGrid.hiddenPlus = 1;
      OPolGrid.hiddenSubtraction = 1;
      OPolGrid.loadMulLine(iArray); 
      
      OPolGrid. selBoxEventFuncName = "displayMissionInfo"; 
      
      //��Щ����������loadMulLine����
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}*/


</script>
