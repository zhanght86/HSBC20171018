<%
//�������ƣ�ProposalApproveModifyInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox(){   
}

// ������ĳ�ʼ��
function initSelBox(){  
}                                        

function initForm()
{
  try {
    initInpBox();
    initSelBox(); 
    initIssuePool();
    //initSelfPolGrid();
    //initQuerySelf();   
    //initPolGrid();
  }
  catch(re) {
    alert("ProposalApproveModifyInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+ re.name + ":" + re.message);
  }
}

//add by lzf 2013-03-15
 function initIssuePool(){
	var config = {
			functionId : "10010004",
			//activityId : "0000001002",
			operator : operator,
			public : {
				//id : "IssuePublicPool",			
				query :{
					queryButton : {
						"1" : {title : "��  ��" , func : "ApplyUW"}
					},
					arrayInfo : {
						col : {
							newcol0 : {
								title : "�������� ",
								style : "2",
								colNo : 6,
								refercode1 : "salechnl",
								addCondition : function(colName,value){
									return " and exists(select 'x' from lccont where lccont.contno = t.missionprop1 and lccont.salechnl='"+ value +"')";
								}
							},
							newcol1 : {
								title : "��������� ",
								style : "2",
								colNo : 7,
								refercode1 : "backobj",
								addCondition : function(colName,value){
									return " and exists(select 1 from lcissuepol,ldcode where t.missionprop1 = contno and codetype = 'backobj' and code  = '"+ value +"'  and backobjtype = comcode and standbyflag2 = othersign )";
								}
							},
							newcol2 : {
								title : "���Ȼ��� ",
								style : "2",
								colNo : 8,
								refercode2 : "precom",
								refercodedata2 : "0|^1|���Ȼ���|^2|�����Ȼ���",
								addCondition : function(colName,value){
									if(value=="1"){
										return "and exists (select '1' from ldcom where t.MissionProp5=ldcom.comcode  and ldcom.comareatype1='1')";
									}
									if(value=="2"){
										return "and exists (select '1' from ldcom where t.MissionProp5=ldcom.comcode  and (ldcom.comareatype1<>'1' or ldcom.comareatype1 is null))";
									}
								}
							},
							result0 : {title : "��ͬ����",verify : "��ͬ����|num&len=14",style :"3"},
							result1 : {title : "ӡ  ˢ  ��  ��",verify : "ӡˢ����|num&len=14",colNo :1,style :"1"},
							result2 : {title : "Ͷ����",style :"3"},
							result3 : {title : "��������",style :"3"},
							result4 : {
								title : " �������",
								colNo : 3,
								style : "2",
								refercode1 : "station",
								colName : "missionprop5",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								}
							},
							result5 : {
								title : " ҵ��Ա����",
								colNo : 4,
								style : "2",
								referfunc1 : "queryAgent",
								referpara1 : "1"
							},
							result6 : {title : "Ӫҵ����Ӫҵ��",style :"3"},
							result7 : {title : "¼��λ��",style :"3"},
							result8 : {
								title : "�ظ�״̬",
								colNo : 5,
								style : "3",
								refercode2 : "state",
								refercodedata2 : "0|^1|ȫ���ظ�|^2|δ�ظ�",
								colName : "missionprop9",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								}
							},
							result9 : {title : " �ظ����� ",colNo :2,style :"8"},
							result10 : {title : "�ظ�ʱ��",style :"3"}
						}
					}
				},
				resultTitle :" �������� ",
				result :{
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : false,
						"4" : true
					},
					mulLineCount : 0,
					arrayInfo : {
						col :{
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "��ͬ��",
								style : "3"
							},
							result1 : {
								title : " ӡ  ˢ  �� ",
								style : "0",
								colNo : 1
							},
							result2 : {
								title : "Ͷ����",
								style : "3"
							},
							result3 : {
								title : "��������",
								style : "3"
							},
							result4 : {
								title : " ������� ",
								style : "0",
								colNo : 3
							},
							result5 : {
								title : " ҵ��Ա����",
								style : "3",
								colNo : 4
							},
							result6 : {
								title : "Ӫҵ����Ӫҵ��",
								style : "3"
							},
							result7 : {
								title : "¼��λ��",
								style : "3"
							},
							result8 : {
								title : "�Ƿ������������",
								style : "0",
								colNo : 5
							},
							result9 : {
								title : " �ظ�����  ",
								style : "0",
								colNo : 2
							},
							result10 : {
								title : "�ظ�ʱ��",
								style : "3"
							}
							
						}
					}
					
				}
			},
			private :{
				//id : "IssuePrivatePool",				
				query :{
					queryButton : {		
					},
					arrayInfo : {
						col : {
							newcol0 : {
								title : " ��������",
								style : "2",
								colNo : 4,
								refercode1 : "salechnl",
								addCondition : function(colName,value){
									return " and exists(select 'x' from lccont where lccont.contno = t.missionprop1 and lccont.salechnl='"+ value +"')";
								}
							},
							newcol1 : {
								title : " ���������",
								style : "2",
								colNo : 6,
								refercode1 : "backobj",
								addCondition : function(colName,value){
									return " and exists(select 1 from lcissuepol,ldcode where t.missionprop1 = contno and codetype = 'backobj' and code  = '"+ value +"'  and backobjtype = comcode and standbyflag2 = othersign )";
								}
							},
							
							result0 : {title : "��ͬ����",verify : "��ͬ����|num&len=14",style :"3"},
							result1 : {title : "  ӡ ˢ �� �� ",verify : "ӡˢ����|num&len=14",colNo :1,style :"1"},
							result2 : {title : "Ͷ����",style :"3"},
							result3 : {title : "��������",style :"3"},
							result4 : {
								title : " �������",
								colNo : 3,
								style : "2",
								refercode1 : "station",
								colName : "missionprop5",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								}
							},
							result5 : {
								title : "ҵ��Ա����",								
								style : "3"
							},
							result6 : {title : "Ӫҵ����Ӫҵ��",style :"3"},
							result7 : {title : "¼��λ��",style :"3"},
							result8 : {
								title : "�ظ�״̬",
								colNo : 5,
								style : "3",
								refercode2 : "state",
								refercodedata2 : "0|^1|ȫ���ظ�|^2|δ�ظ�",
								colName : "missionprop9",
								addCondition : function(colName , value){
									return " and "+ colName +" like '" + value
									+ "%' ";
								}
							},
							result9 : {title : " �ظ�����  ",colNo :2,style :"8"},
							result10 : {title : "�ظ�ʱ��",style :"3"}
						}
					}
				},
				resultTitle :" ��������޸�Ͷ����",
				result :{
					 selBoxEventFuncName:"InitmodifyClick",
					 selBoxEventFuncParam:"",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false
					},
					mulLineCount : 0,
					arrayInfo : {
						col :{
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {
								title : "��ͬ��",
								style : "3"
							},
							result1 : {
								title : " ӡ  ˢ  �� ",
								style : "0",
								colNo : 1
							},
							result2 : {
								title : "Ͷ����",
								style : "3"
							},
							result3 : {
								title : "��������",
								style : "3"
							},
							result4 : {
								title : " �������",
								style : "0",
								colNo : 3
							},
							result5 : {
								title : " ҵ��Ա����",
								style : "0",
								colNo : 4
							},
							result6 : {
								title : "Ӫҵ����Ӫҵ��",
								style : "3"
							},
							result7 : {
								title : "¼��λ��",
								style : "3"
							},
							result8 : {
								title : "�Ƿ������������",
								style : "0",
								colNo : 5
							},
							result9 : {
								title : " �ظ����� ",
								style : "0",
								colNo : 2
							},
							result10 : {
								title : "�ظ�ʱ��",
								style : "3"
							}
							
						}
					}
					
				}
      }
	};
	jQuery("#IssuePool").workpool(config);
	jQuery("#privateSearch").click();
}
//var PolGrid;
// ������Ϣ�б�ĳ�ʼ��
/**function initPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�����������";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������Id";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������          

      iArray[8]=new Array();
      iArray[8][0]="�ظ�����";         		//����
      iArray[8][1]="150px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�������";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="ҵ��Ա����";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="Ӫҵ����Ӫҵ��";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�Ƿ������������";         		//����
      iArray[12][1]="100px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
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
function initSelfPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�����������";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������Id";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������       

      iArray[8]=new Array();
      iArray[8][0]="�ظ�����";         		//����
      iArray[8][1]="150px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�������";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="ҵ��Ա����";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="Ӫҵ����Ӫҵ��";         		//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="�Ƿ������������";         		//����
      iArray[12][1]="100px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      SelfPolGrid = new MulLineEnter( "fm" , "SelfPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfPolGrid.mulLineCount = 0;   
      SelfPolGrid.displayTitle = 1;
      SelfPolGrid.locked = 1;
      SelfPolGrid.canSel = 1;
      SelfPolGrid.hiddenPlus = 1;
      SelfPolGrid.hiddenSubtraction = 1;
      SelfPolGrid.selBoxEventFuncName = "InitmodifyClick";  
      SelfPolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //SelfPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}*/
</script>
