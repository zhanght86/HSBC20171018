<%@include file="../common/jsp/UsrCheck.jsp"%>

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
  }
  catch(ex)
  {
    alert("��BQUWConfirmPoolInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {
    initInpBox();
    initBQUWConfirmPool();
    //initSelfGrpGrid();
    //initGrpGrid();
   // easyQueryClickSelf();
  }
  catch(re)
  {
    alert("BQUWConfirmPoolInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initBQUWConfirmPool(){
	var config = {
			functionId : "10020330",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"ҵ�����",//�еı���
								  style : 2,
								  colNo : 4,
								  maxLength:10,//����������󳤶��൱��iArray[i][2]
								  refercode1:"edorcode",
								  colName:"MissionProp5",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							newcol1:{ 
								  title:"�������",//�еı���
								  colNo : 1,
								  style : 2,
								  colName:"MissionProp3",
								  refercode1:"station",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  },
							result0  : {title : " ��ӡ��ˮ��      ",style : 3},  
							result1  : {title : " ��������      ",style : 1,colNo : 2},            
							result2 : {title : "�������", style : 3 }, 
							result3  : {title : " ������       ",style : 3,colNo : 3},  
							result4 : {title : "ҵ�����", style : 3 }, 
							result5  : {title : " ��ȫ�����        ", style : 3}         
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
						"5" : " and (defaultoperator is null OR defaultoperator = '') order by ContNo,PrtSeq" 
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
					
							newCol0 : {
								title : "��������",
								style : 0,
								width : "60px", 
								colNo : 7 ,
								colName : "  (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop6) ",
								rename : "edor_appdate" 
							},
							newCol1 : {
								title : "��������",
								colNo : 8 ,
								width : "60px", 
								style : 0,
								colName : " (select count(1) from ldcalendar where commondate > (select edorappdate "+
                  					 	 " from lpedoritem  where edoracceptno = t.missionprop6) and workdateflag = 'Y') ",
								rename : "out_day" 
							},
							newCol2 : {
								title : "Ĭ�ϲ���Ա",
								style : 3,
								colName : "defaultoperator "
							},
							result0  : {title : " ��ӡ��ˮ��      ",width : "120px", style : 0,colNo : 4},  
							result1  : {title : " ������     ",width : "120px", style : 0,colNo : 3},            
							result2 : {title : "�������",width : "60px",  style : 0, colNo : 5}, 
							result3  : {title : " ������       ",width : "120px", style : 0,colNo : 6},  
							result4 : {title : "��������", width : "60px", style : 0, colNo : 2}, 
							result5  : {title : " ��ȫ�����        ",width : "120px", style : 0,colNo : 1}
						}
					}
				}	
			},
			private : {
				query :{
					show : false
				},
				resultTitle : "���˹�����",
				result : {
					selBoxEventFuncName : "InitGoToInput",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and defaultoperator ='" + operator + "'  and ManageCom like '" + comcode + "%%'" +
							 " order by ContNo,PrtSeq"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
					
							newCol0 : {
								title : "��������",
								style : 0,
								width : "60px", 
								colNo : 7 ,
								colName : "  (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop6) ",
								rename : "edor_appdate" 
							},
							newCol1 : {
								title : "��������",
								colNo : 8 ,
								width : "60px", 
								style : 0,
								colName : " (select count(1) from ldcalendar where commondate > (select edorappdate "+
                  					 	 " from lpedoritem  where edoracceptno = t.missionprop6) and workdateflag = 'Y') ",
								rename : "out_day" 
							},
							newCol2 : {
								title : "Ĭ�ϲ���Ա",
								style : 3,
								colName : "defaultoperator "
							},
							result0  : {title : " ��ӡ��ˮ��      ",width : "120px", style : 0,colNo : 4},  
							result1  : {title : " ������     ",width : "120px", style : 0,colNo : 3},            
							result2 : {title : "�������",width : "60px",  style : 0, colNo : 5}, 
							result3  : {title : " ������       ",width : "120px", style : 0,colNo : 6},  
							result4 : {title : "��������", width : "60px", style : 0, colNo : 2}, 
							result5  : {title : " ��ȫ�����        ",width : "120px", style : 0,colNo : 1}
						}
					}
				}	
			},
			midContent : { 
			id : 'MidContent',
			show : true,
			html : '<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW()">'
			}
	};
	jQuery("#BQUWConfirmPool").workpool(config);
	jQuery("#privateSearch").click();
}
// ������Ϣ�б�ĳ�ʼ��
/*
function initGrpGrid()
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
      iArray[1][0]="��ȫ�����";         	//����
      iArray[1][1]="120px";            	//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         	//����
      iArray[2][1]="60px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         	//����
      iArray[3][1]="120px";            	//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[4]=new Array();
      iArray[4][0]="��ӡ��ˮ��";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    

      iArray[6]=new Array();
      iArray[6][0]="�����������";      //����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";    //����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������Id";      //����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[9]=new Array();
      iArray[9][0]="������";         	//����
      iArray[9][1]="120px";            	//�п�
      iArray[9][2]=170;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[10]=new Array();
      iArray[10][0]="��������";      //����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[11]=new Array();
      iArray[11][0]="��������";         	//����
      iArray[11][1]="60px";            	//�п�
      iArray[11][2]=170;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount =0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.selBoxEventFuncName = "HighlightAllRow";
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
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";         	//����
      iArray[1][1]="120px";            	//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         	//����
      iArray[2][1]="80px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         	//����
      iArray[3][1]="80px";            	//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[4]=new Array();
      iArray[4][0]="��ӡ��ˮ��";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="140px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    

      iArray[6]=new Array();
      iArray[6][0]="�����������";      //����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";    //����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������Id";      //����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[9]=new Array();
      iArray[9][0]="������";         	//����
      iArray[9][1]="120px";            	//�п�
      iArray[9][2]=170;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��������";      //����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������   
      
      iArray[11]=new Array();
      iArray[11][0]="��������";         	//����
      iArray[11][1]="60px";            	//�п�
      iArray[11][2]=170;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
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
