<%
//�������ƣ�LLSubmitApplyDealMissInit.jsp
//�����ܣ��ʱ���Ϣ����ҳ��ؼ��ĳ�ʼ��
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
//���ղ���
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
    	initParam();
    	//initLLSubmitApplyGrid();  
    	//initReLLSubmitApplyGrid();	
    	//easyQueryClick();    
    	//ReQueryClick();
    	initLLSubmitApplyPool();
    	initReLLSubmitApplyPool();
    	jQuery("#privateSearch").click();
     }
    catch(re)
    {
    	alert("LLSubmitApplyDealMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

function initLLSubmitApplyPool(){
	var config = {
			//activityId : "0000005105",
			functionId : "10050001",
			//operator : operator,
			public : {
				show : false
			},
			private : {
				id : "LLSubmitApplyPool",
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "  �ⰸ��   ", style : 1 ,colNo : 1},
							result1 : {title : "�ʱ����", style : 3},
							result2 : {title : "�ʱ�����", style : 3},
							result3 : {title : "�����˿ͻ���", style : 3},
							result4 : {title : "����������", style : 3},
							result5 : {title : "�ʱ�����", style : 3},
							result6 : {title : "�ʱ�ԭ��", style : 3},
							result7 : {title : "�ֹ�˾���κ���Ա", style : 3},
							result8 : {title : "����", style : 3}
						}
					}
				},
				resultTitle : "ѡ�гʱ��ľ�����Ϣ",
				result : {
					selBoxEventFuncName : "LLSubmitApplyGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and SubType ='0' and MngCom like '"+comcode+"%' order by  ClmNo"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {title : "  �ⰸ��   ", style : 1 ,colNo : 1},
							result1 : {title : "�ʱ����", style : 1 ,colNo : 2},
							result2 : {title : "�ʱ�����", style : 1 ,colNo : 3},
							result3 : {title : "�����˿ͻ���", style : 1 ,colNo : 4},
							result4 : {title : "����������", style : 1 ,colNo : 5},
							result5 : {title : "�ʱ�����", style : 1 ,colNo : 6},
							result6 : {title : "�ʱ�ԭ��", style : 1 ,colNo : 7},
							result7 : {title : "�ֹ�˾���κ���Ա", style : 3},
							result8 : {title : "����", style : 3}
						}
					}
				}
			}
	}
	jQuery("#LLSubmitApplyPool").workpool(config);
	
}

function initReLLSubmitApplyPool(){
	var config = {
			//activityId : "0000005105",
			functionId : "10050001",
			//operator : operator,
			public : {
				show : false
			},
			private : {
				id : "ReLLSubmitApplyPool",
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "  �ⰸ��   ", style : 1 ,colNo : 1},
							result1 : {title : "�ʱ����", style : 3},
							result2 : {title : "�ʱ�����", style : 3},
							result3 : {title : "�����˿ͻ���", style : 3},
							result4 : {title : "����������", style : 3},
							result5 : {title : "�ʱ�����", style : 3},
							result6 : {title : "�ʱ�ԭ��", style : 3},
							result7 : {title : "�ֹ�˾���κ���Ա", style : 3},
							result8 : {title : "����", style : 3}
						}
					}
				},
				resultTitle : "ѡ�гʱ��ľ�����Ϣ",
				result : {
					selBoxEventFuncName : "ReLLSubmitApplyGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and SubType ='2' and MngCom = '"+comcode+"' order by ClmNo"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							result0 : {title : "  �ⰸ��   ", style : 1 ,colNo : 1},
							result1 : {title : "�ʱ����", style : 1 ,colNo : 2},
							result2 : {title : "�ʱ�����", style : 1 ,colNo : 3},
							result3 : {title : "�����˿ͻ���", style : 1 ,colNo : 4},
							result4 : {title : "����������", style : 1 ,colNo : 5},
							result5 : {title : "�ʱ�����", style : 1 ,colNo : 6},
							result6 : {title : "�ʱ�ԭ��", style : 1 ,colNo : 7},
							result7 : {title : "�ֹ�˾���κ���Ա", style : 3},
							result8 : {title : "����", style : 3}
						}
					}
				}
			}	
	}
	jQuery("#ReLLSubmitApplyPool").workpool(config);
}
//��ʼ��"���ճʱ�����"���
/*function initLLSubmitApplyGrid()   
	{
    	var iArray = new Array();   	
      	try
        {
			iArray[0]=new Array();
		    iArray[0][0]="���";    	 //����
		    iArray[0][1]="30px";          //�п�
		    iArray[0][2]=100;            //�����ֵ
		    iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[1]=new Array();
		    iArray[1][0]="�ⰸ��";         		
		    iArray[1][1]="160px";         			
		    iArray[1][2]=160;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="�ʱ����";         		
		    iArray[2][1]="80px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=0;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="�ʱ�����";         		
		    iArray[3][1]="50px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="�����˿ͻ���";       
		    iArray[4][1]="100px";            	
		    iArray[4][2]=100;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="����������";        
		    iArray[5][1]="100px";            		
		    iArray[5][2]=100;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="�ʱ�����";        
		    iArray[6][1]="80px";            		
		    iArray[6][2]=100;            			  
		    iArray[6][3]=0;  
		      
		    iArray[7]=new Array();
		    iArray[7][0]="�ʱ�ԭ��";        
		    iArray[7][1]="100px";            		
		    iArray[7][2]=100;            			  
		    iArray[7][3]=0;  
		    
		    iArray[8]=new Array();
		    iArray[8][0]="Missionid";             //����
		    iArray[8][1]="100px";                //�п�
		    iArray[8][2]=200;                  //�����ֵ
		    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[9]=new Array();
		    iArray[9][0]="submissionid";             //����
		    iArray[9][1]="100px";                //�п�
		    iArray[9][2]=200;                  //�����ֵ
		    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������             
		     		     
		    iArray[10]=new Array();
		    iArray[10][0]="activityid";             //����
		    iArray[10][1]="80px";                //�п�
		    iArray[10][2]=200;                  //�����ֵ
		    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������        		    
		      		                  			 
		      LLSubmitApplyGrid= new MulLineEnter( "fm" , "LLSubmitApplyGrid"); 
		      LLSubmitApplyGrid.mulLineCount = 0;
		      LLSubmitApplyGrid.displayTitle = 1;
		      LLSubmitApplyGrid.canSel = 1;      //�Ƿ����RadioBox
		      LLSubmitApplyGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		      LLSubmitApplyGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		      LLSubmitApplyGrid.selBoxEventFuncName = "LLSubmitApplyGridClick"; //����RadioBoxʱ��Ӧ����
		      LLSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }
//�������б��    
function initReLLSubmitApplyGrid()   
	{
    	var iArray = new Array();   	
      	try
        {
			iArray[0]=new Array();
		    iArray[0][0]="���";    	 //����
		    iArray[0][1]="30px";          //�п�
		    iArray[0][2]=100;            //�����ֵ
		    iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[1]=new Array();
		    iArray[1][0]="�ⰸ��";         		
		    iArray[1][1]="160px";         			
		    iArray[1][2]=10;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="�ʱ����";         		
		    iArray[2][1]="80px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=0;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="�ʱ�����";         		
		    iArray[3][1]="50px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="�����˿ͻ���";       
		    iArray[4][1]="100px";            	
		    iArray[4][2]=100;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="����������";        
		    iArray[5][1]="100px";            		
		    iArray[5][2]=100;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="�ʱ�����";        
		    iArray[6][1]="80px";            		
		    iArray[6][2]=100;            			  
		    iArray[6][3]=0;  
		      
		    iArray[7]=new Array();
		    iArray[7][0]="�ʱ�ԭ��";        
		    iArray[7][1]="100px";            		
		    iArray[7][2]=100;            			  
		    iArray[7][3]=0;  
		    
		    iArray[8]=new Array();
		    iArray[8][0]="Missionid";             //����
		    iArray[8][1]="100px";                //�п�
		    iArray[8][2]=200;                  //�����ֵ
		    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[9]=new Array();
		    iArray[9][0]="submissionid";             //����
		    iArray[9][1]="100px";                //�п�
		    iArray[9][2]=200;                  //�����ֵ
		    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������             
		     		     
		    iArray[10]=new Array();
		    iArray[10][0]="activityid";             //����
		    iArray[10][1]="80px";                //�п�
		    iArray[10][2]=200;                  //�����ֵ
		    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������        		    
		      		                  			 
		      ReLLSubmitApplyGrid= new MulLineEnter( "fm" , "ReLLSubmitApplyGrid"); 
		      ReLLSubmitApplyGrid.mulLineCount = 0;
		      ReLLSubmitApplyGrid.displayTitle = 1;
		      ReLLSubmitApplyGrid.canSel = 1;      //�Ƿ����RadioBox
		      ReLLSubmitApplyGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		      ReLLSubmitApplyGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		      ReLLSubmitApplyGrid.selBoxEventFuncName = "ReLLSubmitApplyGridClick"; //����RadioBoxʱ��Ӧ����
		      ReLLSubmitApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }    
    */
</script>
