<%
	//�������ƣ�LLSecondUWAllInit.jsp
	//�����ܣ������˹��˱���ȡ����
	//�������ڣ�2005-1-28 11:10:36
	//������  ��zhangxing
	//���¼�¼��  ������  yuejw  ��������     ����ԭ��/����
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>            
<script language="JavaScript">

//������ҳ��������� 
function initParam()
{
	document.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //��¼����Ա
    document.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //��¼��½����
    document.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //��¼�������
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

function initForm()
{
	try
	{
		initParam(); //
		//initLLCUWBatchGrid();  //
		//initSelfLLCUWBatchGrid();
		//initSelfLLCUWBatchGridQuery();  //  
		initSecondUWAllPool();
	}
	catch(re)
	{
		alert("��UWInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.name +"::::"+re.message);
	}
}

//modify by lzf
function initSecondUWAllPool(){
	var config = {
			//activityId : "0000005520",
			functionId : "10030020",
			operator : operator,
			public : {
				query : {
					queryButton : {
						"1" : {
							title : "��  ��",
							func : "cancleClick"
						}
					},
					arrayInfo : {
						col : {
							result0 : {title : "    ������ ", style : 1, colNo : 2 },
							result1 : {title : "  ���κ� ", style : 1, colNo : 1 },
							result2 : {title : "�����˺���", style : 1, colNo : 4 },
							result3 : {title : " ������", style : 1, colNo : 5 },
							result4 : {
								title : "�ⰸ��ر�־", 
								style : 2, 
								colNo : 7,
								refercode2 : "qclaimrelflag",
								refercodedata2 : "0|3^0|���^1|�����",
								addCondition : function(colName,value){
									return " and t.missionprop5 = '"+value+"'";
								}
								},
							result5 : {
								title : " ת������", 
								style : 8, 
								colNo : 3,
								refercode1 : "theCurrentDate",
								addCondition : function(colName,value){
									return " and t.missionprop6 <= '"+value+"'";
								}
								},
							result6 : {title : "VIP�ͻ�", style : 3 },
							result7 : {title : "�Ǽ�ҵ��Ա", style : 3 },
							result8 : {title : "������", style : 3},
							result9 : {
								title : " �������",
								style : 2,
								colNo : 6,
								refercode1 : "station",
								colName : "missionprop20",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
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
							newCol0 : {
								title : "��ת����",
								style : 0,
								colNo : 3,
								colName : "(select (case count(distinct batno) when 0 then 0 else count(distinct batno) end) from llcuwbatch where caseno = t.missionprop1 ) ",
								rename : "uwcount"
							},
							result0 : {title : "    ������ ", style : 0, colNo : 1 },
							result1 : {title : "  ���κ� ", style : 0, colNo : 2 },
							result2 : {title : "  �ͻ��� ", style : 0, colNo : 9 },
							result3 : {title : " ������", style : 0, colNo : 7 },
							result4 : {
								title : "�ⰸ��ر�־", 
								style : 3
								},
							result5 : {title : " ת������", style : 0,colNo : 8 },
							result6 : {title : "VIP�ͻ�", style : 0 , colNo :4 },
							result7 : {title : "�Ǽ�ҵ��Ա", style : 0 , colNo :5 },
							result8 : {title : " ������", style : 0 , colNo :6 },
							result9 : {
								title : " �������",
								style : 0,
								colNo : 10
							    }
						}
					}
				}
			},
			midContent : {
				id : "MidContent",
				show : true,
				html : "<INPUT class=cssButton VALUE='��������' TYPE=button onclick=applyClick()>"
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "    ������ ", style : 1, colNo : 2 },
							result1 : {title : "  ���κ� ", style : 1, colNo : 1 },
							result2 : {title : " �����˺���", style : 1, colNo : 4 },
							result3 : {title : " ������", style : 1, colNo : 5 },
							result4 : {
								title : "�ⰸ��ر�־", 
								style : 2, 
								colNo : 7,
								refercode2 : "qclaimrelflag",
								refercodedata2 : "0|3^0|���^1|�����",
								addCondition : function(colName,value){
									return " and t.missionprop5 = '"+value+"'";
								}
								},
							result5 : {
								title : " ת������", 
								style : 8, 
								colNo : 3,
								refercode1 : "theCurrentDate",
								addCondition : function(colName,value){
									return " and t.missionprop6 <= '"+value+"'";
								}	
							},
							result6 : {title : "VIP�ͻ�", style : 3 },
							result7 : {title : "�Ǽ�ҵ��Ա", style : 3 },
							result8 : {title : " ������", style : 3},
							result9 : {
								title : " �������",
								style : 2,
								colNo : 6,
								refercode1 : "station",
								colName : "missionprop20",
								addCondition : function(colName, value) {
									return " and " + colName + " like '" + value
											+ "%' ";
								}
							    }
						}
					}
				},
				resultTitle : "���˹�����",
				result : {
					 selBoxEventFuncName:"SelfLLCUWBatchGridClick",
					 selBoxEventFuncParam:"",
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
							newCol0 : {
								title : " ��ת����",
								style : 0,
								colNo : 3,
								colName : "(select (case count(distinct batno) when 0 then 0 else count(distinct batno) end) from llcuwbatch where caseno = t.missionprop1 ) ",
								rename : "uwcount"
							},
							result0 : {title : "    ������ ", style : 0, colNo : 1 },
							result1 : {title : "  ���κ� ", style : 0, colNo : 2 },
							result2 : {title : "�����˺���", style : 0, colNo : 9 },
							result3 : {title : " ������", style : 0, colNo : 7 },
							result4 : {
								title : "�ⰸ��ر�־", 
								style : 3
								},
							result5 : {title : " ת������", style : 0,colNo : 8 },
							result6 : {title : "VIP�ͻ�", style : 0 , colNo :4 },
							result7 : {title : "�Ǽ�ҵ��Ա", style : 0 , colNo :5 },
							result8 : {title : " ������", style : 0 , colNo :6 },
							result9 : {
								title : " �������",
								style : 0,
								colNo : 10
							    }
						}
					}
				}
			}
	};
	jQuery("#SecondUWAllPool").workpool(config);
	jQuery("#privateSearch").click();
}

//end 

// ������Ϣ�б�ĳ�ʼ��
/*function initLLCUWBatchGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			//���� ������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=30;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������       			 
		
		iArray[1]=new Array();
		iArray[1][0]="������";         		 
		iArray[1][1]="160px";            		 
		iArray[1][2]=100;            			 
		iArray[1][3]=0;              			 
	
		iArray[2]=new Array();
		iArray[2][0]="���κ�";         		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=100;            			 
		iArray[2][3]=0;  
		            			 
		iArray[3]=new Array();
	    iArray[3][0]="ת�˴���";              // ���� ���ⰸ��ر�־��
	    iArray[3][1]="60px";                
	    iArray[3][2]=200;                  
	    iArray[3][3]=0;   
	            			 
		iArray[4]=new Array();
		iArray[4][0]="VIP�ͻ�";         		 
		iArray[4][1]="50px";            		 
		iArray[4][2]=100;            			 
		iArray[4][3]=0;              			 
		
		iArray[5]=new Array();
		iArray[5][0]="�Ǽ�ҵ��Ա";         		 
		iArray[5][1]="50px";            		 
		iArray[5][2]=100;            			 
		iArray[5][3]=0;              			 
	
		iArray[6]=new Array();
		iArray[6][0]="������";         		 
		iArray[6][1]="100px";            		 
		iArray[6][2]=100;            			 
		iArray[6][3]=0;              			 
		
		iArray[7]=new Array();
		iArray[7][0]="������";         		 
		iArray[7][1]="100px";            		 
		iArray[7][2]=100;            			 
		iArray[7][3]=0;              			    
	
		iArray[8]=new Array();                                                       
		iArray[8][0]="ת������";         		                                      
		iArray[8][1]="80px";            		                                    
		iArray[8][2]=100;            			                                  
		iArray[8][3]=0;       
		
		iArray[9]=new Array();                                                       
		iArray[9][0]="ת��ʱ��";         		                                      
		iArray[9][1]="80px";            		                                    
		iArray[9][2]=100;            			                                  
		iArray[9][3]=0; 		       			    
	
	    iArray[10]=new Array();
	    iArray[10][0]="�ͻ���";              
	    iArray[10][1]="80px";                 
	    iArray[10][2]=200;                   
	    iArray[10][3]=0;                           
	
	    iArray[11]=new Array();
	    iArray[11][0]="�������";              
	    iArray[11][1]="80px";                 
	    iArray[11][2]=200;                   
	    iArray[11][3]=0;                           
	    
	    iArray[12]=new Array();
	    iArray[12][0]="MissionID";              
	    iArray[12][1]="0px";                 
	    iArray[12][2]=200;                   
	    iArray[12][3]=3;                      
	    
	    iArray[13]=new Array();
	    iArray[13][0]="SubMissionID";              // ���� ���ⰸ��ر�־��
	    iArray[13][1]="0px";                
	    iArray[13][2]=200;                  
	    iArray[13][3]=3;                            
	
		iArray[14]=new Array();
	    iArray[14][0]="ActivityID";              // ���� ���ⰸ��ر�־��
	    iArray[14][1]="0px";                
	    iArray[14][2]=200;                  
	    iArray[14][3]=3;                    
	
		LLCUWBatchGrid = new MulLineEnter( "fm" , "LLCUWBatchGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		LLCUWBatchGrid.mulLineCount = 0;   
		LLCUWBatchGrid.displayTitle = 1;
		LLCUWBatchGrid.locked = 1;
		LLCUWBatchGrid.canSel = 1;
		LLCUWBatchGrid.hiddenPlus = 1;
		LLCUWBatchGrid.hiddenSubtraction = 1;
		LLCUWBatchGrid.loadMulLine(iArray);     
//		LLCUWBatchGrid.selBoxEventFuncName = "LLCUWBatchGridClick";
		//��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfLLCUWBatchGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			//���� ������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=30;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������       			 
		
		iArray[1]=new Array();
		iArray[1][0]="������";         		 
		iArray[1][1]="160px";            		 
		iArray[1][2]=100;            			 
		iArray[1][3]=0;              			 
	
		iArray[2]=new Array();
		iArray[2][0]="���κ�";         		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=100;            			 
		iArray[2][3]=0;  

		iArray[3]=new Array();
	    iArray[3][0]="ת�˴���";              // ���� ���ⰸ��ر�־��
	    iArray[3][1]="60px";                
	    iArray[3][2]=200;                  
	    iArray[3][3]=0;   
	            			 
		iArray[4]=new Array();
		iArray[4][0]="VIP�ͻ�";         		 
		iArray[4][1]="50px";            		 
		iArray[4][2]=100;            			 
		iArray[4][3]=0;              			 
		
		iArray[5]=new Array();
		iArray[5][0]="�Ǽ�ҵ��Ա";         		 
		iArray[5][1]="50px";            		 
		iArray[5][2]=100;            			 
		iArray[5][3]=0;              			 
	
		iArray[6]=new Array();
		iArray[6][0]="������";         		 
		iArray[6][1]="100px";            		 
		iArray[6][2]=100;            			 
		iArray[6][3]=0;              			 
		
		iArray[7]=new Array();
		iArray[7][0]="������";         		 
		iArray[7][1]="100px";            		 
		iArray[7][2]=100;            			 
		iArray[7][3]=0;              			    
	
		iArray[8]=new Array();                                                       
		iArray[8][0]="ת������";         		                                      
		iArray[8][1]="80px";            		                                    
		iArray[8][2]=100;            			                                  
		iArray[8][3]=0;          
		
		iArray[9]=new Array();                                                       
		iArray[9][0]="ת��ʱ��";         		                                      
		iArray[9][1]="80px";            		                                    
		iArray[9][2]=100;            			                                  
		iArray[9][3]=0; 				    			    
	
	    iArray[10]=new Array();
	    iArray[10][0]="�ͻ���";              
	    iArray[10][1]="80px";                 
	    iArray[10][2]=200;                   
	    iArray[10][3]=0;                           
	
	    iArray[11]=new Array();
	    iArray[11][0]="�������";              
	    iArray[11][1]="80px";                 
	    iArray[11][2]=200;                   
	    iArray[11][3]=0;                           
	    
	    iArray[12]=new Array();
	    iArray[12][0]="MissionID";              
	    iArray[12][1]="0px";                 
	    iArray[12][2]=200;                   
	    iArray[12][3]=3;                      
	    
	    iArray[13]=new Array();
	    iArray[13][0]="SubMissionID";              // ���� ���ⰸ��ر�־��
	    iArray[13][1]="0px";                
	    iArray[13][2]=200;                  
	    iArray[13][3]=3;                            
	
		iArray[14]=new Array();
	    iArray[14][0]="ActivityID";              // ���� ���ⰸ��ر�־��
	    iArray[14][1]="0px";                
	    iArray[14][2]=200;                  
	    iArray[14][3]=3;   

	    iArray[15]=new Array();
	    iArray[15][0]="�ⰸ��ر�־";              // ���� ���ⰸ��ر�־��
	    iArray[15][1]="60px";                
	    iArray[15][2]=200;                  
	    iArray[15][3]=3;   

	
		SelfLLCUWBatchGrid = new MulLineEnter( "fm" , "SelfLLCUWBatchGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		SelfLLCUWBatchGrid.mulLineCount =0;   
		SelfLLCUWBatchGrid.displayTitle = 1;
		SelfLLCUWBatchGrid.locked = 1;
		SelfLLCUWBatchGrid.canSel = 1;
		SelfLLCUWBatchGrid.hiddenPlus = 1;
		SelfLLCUWBatchGrid.hiddenSubtraction = 1;
		SelfLLCUWBatchGrid.loadMulLine(iArray);     
		SelfLLCUWBatchGrid.selBoxEventFuncName = "SelfLLCUWBatchGridClick";
		//��Щ����������loadMulLine����
	}
	catch(ex)
	{
		alert(ex);
	}
}
*/

</script>
