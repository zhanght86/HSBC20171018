<%
//�������ƣ�LLInqConclusionMissInit.jsp
//�����ܣ��������ҳ���ʼ��
//�������ڣ�2005-06-27
//������  ��yuejw
//���¼�¼��
%>
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
function initInpBox()
{ 
    try
    {                                   


    }
    catch(ex)
    {
        alert("��LLInqConclusionInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
        alert("��LLInqConclusionInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}                                        

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();  
        initParam();
        //initLLInqConclusionGrid();  
        //LLInqConclusionGridQuery();
        initConclusionInputPool()
    }
    catch(re)
    {
        alert("LLInqConclusionInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
//modify by lzf 
function initConclusionInputPool(){
	var config = {
			//activityId : "0000005165",
			functionId : "10060003",
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
							result1 : {title : "������� ", style : 3},
							result2 : {title : "�������� ", style : 3},
							result3 : {title : "�������", style : 3},
							result4 : {title : "�������", style : 3},
							result5 : {title : "���ܱ�־ ", style : 3},
							result6 : {title : "�ⰸ�ű���", style : 3},
							result7 : {title : "����", style : 3}
						}
					}
				},
				resultTitle : "�������",
				result : {
					selBoxEventFuncName : "LLInqConclusionGridClick",
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
								title : "�����˱���",
								style : 1,
								colNo : 6,
								colName : " (select max(customerno)  from llsubreport  where trim(subrptno) = t.missionprop1) ",
								rename : "customerno"
							},
							newCol2 : {
								title : "����������",
								style : 1,
								colNo : 7,
								colName : " (select name from ldperson where customerno in (select max(customerno) from llsubreport where trim(subrptno) = t.missionprop1)) ",
								rename : "name"
							},
							result0 : {title : " �ⰸ��     ", style : 1,colNo : 1},
							result1 : {title : "������� ", style : 1,colNo : 2},
							result2 : {title : "�������� ", style : 1,colNo : 3},
							result3 : {title : "�������", style : 1,colNo : 4},
							result4 : {title : "�������", style : 1,colNo : 5},
							result5 : {title : "���ܱ�־ ", style : 3},
							result6 : {
								title : "�ⰸ�ű���", 
								style : 3,
								colName : "MissionProp7",
								rename : "clmnoback"
								},
							result7 : {title : "����", style : 3}
						}
					}
				}
			}
			
	}
	jQuery("#ConclusionInputPool").workpool(config);
	jQuery("#privateSearch").click();
}


//����������ʼ��
/*function initLLInqConclusionGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                //�п�
      iArray[0][2]=8;                  //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";             //����
      iArray[1][1]="160px";                //�п�
      iArray[1][2]=160;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";             //����
      iArray[2][1]="100px";                //�п�
      iArray[2][2]=80;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";             //����
      iArray[3][1]="80px";                //�п�
      iArray[3][2]=80;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������";             //����
      iArray[4][1]="80px";                //�п�
      iArray[4][2]=80;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";             //����
      iArray[5][1]="80px";                //�п�
      iArray[5][2]=80;                  //�����ֵ
      iArray[5][3]=0; 
              
	  iArray[6]=new Array();
	  iArray[6][0]="Missionid";             //����
	  iArray[6][1]="0px";                //�п�
	  iArray[6][2]=200;                  //�����ֵ
	  iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[7]=new Array();
	  iArray[7][0]="submissionid";             //����
	  iArray[7][1]="0px";                //�п�
	  iArray[7][2]=200;                  //�����ֵ
	  iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������             
	     		     
	  iArray[8]=new Array();
	  iArray[8][0]="activityid";             //����
	  iArray[8][1]="0px";                //�п�
	  iArray[8][2]=200;                  //�����ֵ
	  iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������   
	  
	  iArray[9]=new Array();
	  iArray[9][0]="�����˱���";             //����
	  iArray[9][1]="80px";                //�п�
	  iArray[9][2]=200;                  //�����ֵ
	  iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������   
	  
	  iArray[10]=new Array();
	  iArray[10][0]="����������";             //����
	  iArray[10][1]="80px";                //�п�
	  iArray[10][2]=200;                  //�����ֵ
	  iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������   	  	 

	  iArray[11]=new Array();
	  iArray[11][0]="��������";             //����
	  iArray[11][1]="80px";                //�п�
	  iArray[11][2]=200;                  //�����ֵ
	  iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������   	  	   

      LLInqConclusionGrid = new MulLineEnter( "document" , "LLInqConclusionGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLInqConclusionGrid.mulLineCount = 0;   
      LLInqConclusionGrid.displayTitle = 1;
      LLInqConclusionGrid.locked = 1;
      LLInqConclusionGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLInqConclusionGrid.selBoxEventFuncName = "LLInqConclusionGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
      LLInqConclusionGrid.hiddenPlus=1;
      LLInqConclusionGrid.hiddenSubtraction=1;
      LLInqConclusionGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}
*/
</script>
