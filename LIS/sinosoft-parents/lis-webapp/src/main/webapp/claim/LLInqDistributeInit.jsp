<%
//Name:LLInqDistributeInit.jsp
//function����������������ĳ�ʼ��
//author:
//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
   String tCurrentDate = PubFun.getCurrentDate();
%>
<script language="JavaScript">
var CurrentDate="<%=tCurrentDate%>";

//���ղ���
function initParam()
{
	document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");	      
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
//    document.all('tComCode').value =document.all('ComCode').value+"%"; //���ڸ��ݻ�����ѯ������
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
    document.all('tClmNo').value = '';
    document.all('tCustomerName').value = '';
    document.all('tCustomerNo').value = '';
    
  }
  catch(ex)
  {
    alter("��LLSuveryDistributInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��LLSuveryDistributInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}
function initForm()
{
  try
	  {  
		  initParam();
	     initInpBox();
	     qurycomcode(); //��ѯ��ǰ���������ڻ���-------���ڸ��ݻ�����ѯ������	
		//initInqApplyGrid();
	    initDistributeInputPool();
  }
  catch(re)
  {
    alter("��LLReportInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//modify by lzf 
function initDistributeInputPool(){
	var config = {
			//activityId : "0000005125",
			functionId : "10060001",
			//operator : operator,
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : " �ⰸ��   ", style : 1,colNo : 1},
							result1 : {title : "�ʱ����", style : 3},
							result2 : {title : "�ʱ����� ", style : 3},
							result3 : {title : "�ͻ�����", style : 1,colNo : 2},
							result4 : {title : "�ͻ�����", style : 1,colNo : 3},
							result5 : {title : "����׶�", style : 3},
							result6 : {title : "�ʱ�ԭ��", style : 3},
							result7 : {title : "�������", style : 3},
							result8 : {title : "�ⰸ�ű���", style : 3},
							result9 : {title : "����", style : 3},
							result10 : {title : "������", style : 3}
						}
					}
				},
				resultTitle : "��������������",
				result : {
					selBoxEventFuncName : "InqApplyGridSelClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : "and InqDept like'"
							+ comcode
							+ "%' and createoperator ='"+operator+"' order by AcceptedDate,ClmNo"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "����׶�",
								style : 1,
								colNo : 5,
								colName : "(select codename from ldcode  where codetype = 'llinitphase'  and trim(code) = t.missionprop6) ",
								rename : "InitPhase1"
								},
							newCol1 : {
								title : "��������",
								style : 3,
								colName : " (select accepteddate  from llregister  where rgtno = t.missionprop1) ",
								rename : "AcceptedDate"
							},
							result0 : {title : " �ⰸ��     ", style : 1,colNo : 1},
							result1 : {title : "�ʱ����  ", style : 1,colNo : 2},
							result2 : {title : "�ʱ����� ", style : 3},
							result3 : {title : "�ͻ�����  ", style : 1,colNo : 3},
							result4 : {title : "�ͻ�����  ", style : 1,colNo : 4},
							result5 : {title : "����׶�", style : 3},
							result6 : {title : "�ʱ�ԭ��", style : 3},
							result7 : {title : "�������", style : 1,colNo : 6},
							result8 : {
								title : "�ⰸ�ű���", 
								style : 3,
								colName : "MissionProp9",
								rename : "clmnoback"
								},
							result9 : {title : "����", style : 3},
							result10 : {title : "������", style : 3}
						}
					}
				}
			},
			private : {show : false}
	}
	jQuery("#DistributeInputPool").workpool(config);
}


/*function initInqApplyGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="������";
    iArray[1][1]="160px";
    iArray[1][2]=160;
    iArray[1][3]=0;
    
    iArray[2]=new Array();
    iArray[2][0]="�������";
    iArray[2][1]="100px";
    iArray[2][2]=100;
    iArray[2][3]=0;
    
    iArray[3]=new Array();
    iArray[3][0]="�������κ�";
    iArray[3][1]="100px";
    iArray[3][2]=120;
    iArray[3][3]=3;

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";
    iArray[4][1]="100px";
    iArray[4][2]=60;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="�ͻ�����";
    iArray[5][1]="100px";
    iArray[5][2]=120;
    iArray[5][3]=0;

    iArray[6]=new Array();
    iArray[6][0]="����׶�";
    iArray[6][1]="100px";
    iArray[6][2]=60;
    iArray[6][3]=0;

    iArray[7]=new Array();
    iArray[7][0]="����ԭ��";
    iArray[7][1]="100px";
    iArray[7][2]=60;
    iArray[7][3]=3;
    
    iArray[8]=new Array();
    iArray[8][0]="�������";
    iArray[8][1]="100px";
    iArray[8][2]=60;
    iArray[8][3]=0;
    
    iArray[9]=new Array();
    iArray[9][0]="Missionid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="submissionid";             //����
    iArray[10][1]="100px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������             
     		     
    iArray[11]=new Array();
    iArray[11][0]="activityid";             //����
    iArray[11][1]="80px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=3;     
    
    iArray[12]=new Array();
    iArray[12][0]="����׶δ���";
    iArray[12][1]="100px";
    iArray[12][2]=60;
    iArray[12][3]=3;

    iArray[13]=new Array();
    iArray[13][0]="��������";
    iArray[13][1]="100px";
    iArray[13][2]=60;
    iArray[13][3]=3;
    
    InqApplyGrid = new MulLineEnter("document","InqApplyGrid");
    InqApplyGrid.mulLineCount =0;
    InqApplyGrid.displayTitle = 1;
    InqApplyGrid.locked = 1;
    InqApplyGrid.canSel =1;
    InqApplyGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    InqApplyGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    InqApplyGrid.selBoxEventFuncName = "InqApplyGridSelClick";
    InqApplyGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alter(ex);
  }
}
*/
 </script>
