<%
//�������ƣ�ProposalApproveInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
       String Operator = tGlobalInput.Operator;
       String ComCode = tGlobalInput.ComCode;
%>                            

<script language="JavaScript">

//��ʼ��ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('Operator').value = nullToEmpty("<%= Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= ComCode %>");
    
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {                                   
	// ������ѯ����
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    //document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
    
  }
  catch(ex)
  {
  	alert("��һ������");
    alert("��ProposalApproveInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
  	alert("�ڶ�������");
    alert("��ProposalApproveInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                       

function initForm()
{
  try
  {
    initParam();
	initInpBox();  
	initSelBox();  
	initManagePublicPool();
	initManagePrivatePool();
	//initPolGrid();
	//initSelfPolGrid();	
	//easyQueryClickSelf();
  }
  catch(re)
  {
    alert("ProposalApproveInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+ re.name + ":" + re.message);
  }
}
 
//add by lzf 2013-03-14
function initManagePublicPool() {	
		var config = {		
		functionId : "10010010",
		operator : Operator,
		public : {
			id : "ManPublicPool",
			show : true,
			query : {
				queryButton : {
					"1" : {
						title : "��  ��",
						func : "ApplyUW"
					}
				},
				arrayInfo : {
					col : {
						newcol0 : {
							title : "�������",
							style : "2",
							refercode1 : "station",
							colName : "operatecom",
							addCondition : function(colName, value) {
								return " and "+ colName +" like '" + value
										+ "%' ";
							}
						},
						result1 : {
							title : "ӡˢ��",
							verify : "ӡˢ��|num&len=14",
							colNo : 2,
							style : "1"
						},						
						result2 : {
							title : "Ͷ����",
						    style : "3",
						    colNo : 3
						},
						result0 : {
							title : "��ͬ��",
							style : "3",
							colNo : 1
						},
						result3 : {
							title : "��������κ�",
						    style : "3",
						    colNo : 4 
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
					"4" : true
				},
				mulLineCount : 0,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",
						result1 : {
							title : "ӡˢ��",
							verify : "ӡˢ��|num&len=14",
							colNo : 2,
							style : "0"
						},					
						result2 : {
							title : "Ͷ����",
						    style : "0",
						    colNo : 3
						},
						result0 : {
							title : "��ͬ��",
							style : "3",
							colNo : 1
						},
						result3 : {
							title : "��������κ�",
						    style : "3",
						    colNo : 4 
						}
					}
				}
			}
		},
		private : {	
			show : false			
		}
	};
	jQuery("#ManPublicPool").workpool(config);

}

function initManagePrivatePool(){
	var config = {		
			functionId : "10010010",
			operator : Operator,
			public : {
               show : false
			},
			private : {
				id : "ManPrivatePool",		
				show : true,
				query : {
					queryButton : {
						
					},
					arrayInfo : {
						col : {	
							newcol0 : {
								title : "�������",	
								style : "2",
								refercode1 : "station",
								colName : "operatecom",
								addCondition : function(colName, value) {
									return " and "+ colName +" like '" + value
											+ "%' ";
								}
							},
							result1 : {
								title : "ӡˢ��",
								verify : "ӡˢ��|num&len=14",
								colNo : 2,
								style : "1"
							},						
							result0 : {
								title : "��ͬ��",
								verify : "��ͬ��|num&len=14",
								colNo : 1,
								style : "3"
							},
							result2 : {
								title : "Ͷ����",
								colNo : 3,
								style : "3"
							},
							result3 : {
								title : "��������κ�",
								colNo : 4,
								style : "3"
							}
						}
					}
				},
				resultTitle : "��������",
				result : {
					 selBoxEventFuncName : "QuestQuery",
					 selBoxEventFuncParam : "",					
					condition : {
						"0" : false,
						"1" : false,
						"2" : false
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result1 : {
								title : "ӡˢ��",
								colNo : 2,
								style : "0"
							},						
							result2 : {
								title : "Ͷ����",
								colNo : 3,
								style : "0"
							},
							result0 : {
								title : "��ͬ��",
								colNo : 1,
								style : "3"
							},
							result3 : {
								title : "��������κ�",
								colNo : 4,
								style : "3"
							}
						}
					}				
				}
			}
		};
		jQuery("#ManPrivatePool").workpool(config);
		jQuery("#privateSearch").click();
}
// ������Ϣ�б�ĳ�ʼ��
/**function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="50px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ����";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�����������";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="�������������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[5]=new Array();
      iArray[5][0]="�������Id";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
      	alert("����������");
        alert(ex);
      }
}
*/
// ���˱�����Ϣ�б�ĳ�ʼ��
/**function initSelfPolGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="50px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ����";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�����������";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="�������������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[5]=new Array();
      iArray[5][0]="�������Id";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      SelfPolGrid = new MulLineEnter( "fm" , "SelfPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfPolGrid.mulLineCount = 0;   
      SelfPolGrid.displayTitle = 1;
      SelfPolGrid.locked = 1;
      SelfPolGrid.canSel = 1;
      SelfPolGrid.hiddenPlus = 1;
      SelfPolGrid.hiddenSubtraction = 1;
      SelfPolGrid.selBoxEventFuncName = "QuestQuery";    
      SelfPolGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
*/



</script>
