<%
//�������ƣ�LLSubmitApplyDealMissInit.jsp
//�����ܣ��ʱ���Ϣ����ҳ��ؼ��ĳ�ʼ��
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
   String tCurrentDate = PubFun.getCurrentDate();
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
var CurrentDate="<%=tCurrentDate%>";

//���ղ���
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('CurDate').value = CurrentDate;
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
    	//initLLInqApplyGrid();  
    	//easyQueryClick();   
    	initCourseInputPool();
     }
    catch(re)
    {
    	alert("LLSubmitApplyDealMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//modify by lzf
function initCourseInputPool(){
	var config = {
			//activityId : "0000005145",
			functionId : "10060002",
			operator : operator,
			public : {
				show : false
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : " �ⰸ��    ", style : 1,colNo : 1},
							result1 : {title : "�������", style : 3},
							result2 : {title : "�����˿ͻ���", style : 3},
							result3 : {title : "����������", style : 3},
							result4 : {title : "��������������", style : 3},
							result5 : {title : "�����������", style : 3},
							result6 : {title : "ָ���ĵ�����", style : 3},
							result7 : {title : "�ⰸ�ű���", style : 3},
							result8 : {title : "����", style : 3}
						}
					}
				},
				resultTitle : "��ȡ�����������",
				result : {
					selBoxEventFuncName : "LLInqApplyGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " order by AcceptedDate,ClmNo"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "��������",
								style : 3,
								colName : " (select accepteddate  from llregister  where rgtno = t.missionprop1) ",
								rename : "AcceptedDate"
							},
							newCol1 : {
								title : "ָ���ĵ�����",
								style : 1,
								ColNo : 7,
								colName : " (select username from lduser where usercode = t.DefaultOperator) ",
								rename : "username"
							},
							result0 : {title : " �ⰸ��     ", style : 1,colNo : 1},
							result1 : {title : "�������  ", style : 1,colNo : 2},
							result2 : {title : "�����˱���", style : 1,colNo : 3},
							result3 : {title : "����������", style : 1,colNo : 4},
							result4 : {title : "����������", style : 1,colNo : 5},
							result5 : {title : "�����������", style : 1,colNo : 6},
							result6 : {title : "ָ���ĵ�����", style : 3},
							result7 : {
								title : "�ⰸ�ű���", 
								style : 3,
								colName : "MissionProp8",
								rename : "clmnoback"
								},
							result8 : {title : "����", style : 3}	
						}
					}
				}
			}
				
	}
	jQuery("#CourseInputPool").workpool(config);
	jQuery("#privateSearch").click();
}

//end

//��ʼ��"���ճʱ�����"����
/*function initLLInqApplyGrid()   
	{
    	var iArray = new Array();   	
      	try
        {
			iArray[0]=new Array();
		    iArray[0][0]="���";    	 //����
		    iArray[0][1]="30px";          //�п�
		    iArray[0][2]=100;            //�����ֵ
		    iArray[0][3]=0;              //�Ƿ���������,1��ʾ������0��ʾ������
		
		    iArray[1]=new Array();
		    iArray[1][0]="������";         		
		    iArray[1][1]="160px";         			
		    iArray[1][2]=160;          			   
		    iArray[1][3]=0;              			
		
		    iArray[2]=new Array();
		    iArray[2][0]="�������";         		
		    iArray[2][1]="100px";         			
		    iArray[2][2]=10;          			
		    iArray[2][3]=0;              			
		
		    iArray[3]=new Array();
		    iArray[3][0]="�����˱���";         		
		    iArray[3][1]="100px";            		
		    iArray[3][2]=100;            			
		    iArray[3][3]=0;              			
		
		    iArray[4]=new Array();
		    iArray[4][0]="����������";       
		    iArray[4][1]="100px";            	
		    iArray[4][2]=100;            			
		    iArray[4][3]=0;              			
		
		    iArray[5]=new Array();
		    iArray[5][0]="����������";        
		    iArray[5][1]="80px";            		
		    iArray[5][2]=100;            			  
		    iArray[5][3]=0;  
		      
		    iArray[6]=new Array();
		    iArray[6][0]="�����������";        
		    iArray[6][1]="80px";            		
		    iArray[6][2]=100;            			  
		    iArray[6][3]=0;  
		      
		    iArray[7]=new Array();
		    iArray[7][0]="ָ���ĵ�����";        
		    iArray[7][1]="100px";            		
		    iArray[7][2]=100;            			  
		    iArray[7][3]=0;  
		    
		    iArray[8]=new Array();
		    iArray[8][0]="Missionid";             //����
		    iArray[8][1]="100px";                //�п�
		    iArray[8][2]=200;                  //�����ֵ
		    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
		
		    iArray[9]=new Array();
		    iArray[9][0]="submissionid";             //����
		    iArray[9][1]="100px";                //�п�
		    iArray[9][2]=200;                  //�����ֵ
		    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������             
		     		     
		    iArray[10]=new Array();
		    iArray[10][0]="activityid";             //����o
		    iArray[10][1]="80px";                //�п�
		    iArray[10][2]=200;                  //�����ֵ
		    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������        		    

		    iArray[11]=new Array();
		    iArray[11][0]="��������";             //����o
		    iArray[11][1]="80px";                //�п�
		    iArray[11][2]=200;                  //�����ֵ
		    iArray[11][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������        		    
		      		                  			 
		      LLInqApplyGrid= new MulLineEnter( "document" , "LLInqApplyGrid"); 
		      LLInqApplyGrid.mulLineCount = 0;
		      LLInqApplyGrid.displayTitle = 1;
		      LLInqApplyGrid.canSel = 1;      //�Ƿ����RadioBox
		      LLInqApplyGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		      LLInqApplyGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		      LLInqApplyGrid.selBoxEventFuncName = "LLInqApplyGridClick"; //����RadioBoxʱ��Ӧ����
		      LLInqApplyGrid.loadMulLine(iArray);      
         }
        catch(ex)
        { 
        	alert(ex); 
        }
    }
*/
</script>