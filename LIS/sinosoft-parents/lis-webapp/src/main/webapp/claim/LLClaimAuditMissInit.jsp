<%
//�������ƣ�LLClaimAuditMissInit.jsp
//�����ܣ�
//�������ڣ�2005-6-6 11:30
//������  ��zl
//���¼�¼��
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
   String tCurrentDate = PubFun.getCurrentDate();
%>

<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
var CurrentDate="<%=tCurrentDate%>";

//���ղ���
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
    document.all('CurDate').value = CurrentDate;
}

//��null���ַ���תΪ��
function nullToEmpty(tstring)
{
	if ((tstring == "null") || (tstring == "undefined"))
	{
		tstring = "";
	}
	return tstring;
}

// �����ĳ�ʼ��
function initInpBox()
{ 

  try
  {                                   
  }
  catch(ex)
  {
    alert("��LLClaimAuditMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("sex","0=��&1=Ů&2=����");        
  }
  catch(ex)
  {
    alert("��LLClaimAuditMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    //initLLClaimAuditGrid();
    //initSelfLLClaimAuditGrid();
    //querySelfGrid();
//	initSelfPolGrid();
//	easyQueryClickSelf();
	initAuditClaimPool();
  }
  catch(re)
  {
    alert("LLClaimAuditMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.name +":"+re.message);
  }
}

//���ҳ��
function emptyForm() 
{
	//emptyFormElements();     //���ҳ�������������COMMON.JS��ʵ��
   
	initSubInsuredGrid();
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	
	//SubInsuredGrid.clearData();
	//BnfGrid.clearData();
	//ImpartGrid.clearData();
	//SpecGrid.clearData();
	//DutyGrid.clearData();
	spanDutyGrid.innerHTML="";
}

//add by lzf 
 function initAuditClaimPool(){
	var sql1 = "";
	if(_DBT==_DBO){
		sql1 = "(Case (select 1 from llaffix where rgtno=t.missionprop1 And Rownum=1) When 1 Then (Case (select 1 from llaffix where rgtno=t.missionprop1 And (subflag is null or subflag='1') And Rownum=1) When 1 Then 'δȫ������' Else '�ѻ���'  End) Else 'δ����' End ) ";
	}else if(_DBT==_DBM){
		sql1 = "(Case (select 1 from llaffix where rgtno=t.missionprop1 limit 0,1) When 1 Then (Case (select 1 from llaffix where rgtno=t.missionprop1 And (subflag is null or subflag='1') limit 0,1) When 1 Then 'δȫ������' Else '�ѻ���'  End) Else 'δ����' End ) ";
	}
	
	
	 var config = {
			//activityId : "0000005035",
			functionId : "10030005",
			//operator : operator,
			public : {
				show : false
			},
			private : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : " ������   ", style : 1,colNo : 1},
							result1 : {title : "����״̬", style : 3},
							result2 : {title : "�ͻ�����", style : 3},
							result3 : {title : "�ͻ�����", style : 3},
							result4 : {title : "�Ա�", style : 3},
							result5 : {title : "�¹�����", style : 3},
							result6 : {title : "��������", style : 3},
							result7 : {title : "��������", style : 3},
							result8 : {title : "��������", style : 3},
							result9 : {title : "���Ȩ��", style : 3},
							result10 : {title : "Ԥ����־", style : 3},
							result11 : {title : "����", style : 3},
							result12 : {title : "Ȩ�޿缶��־", style : 3},
							result13 : {title : "��˲�����", style : 3 },
							result14 : {title : "����", style : 3},
							result15 : {
								title : "�����", 
								style : 3
							}
						}
					}
				},
				resultTilte : "��������",
				result : {
					selBoxEventFuncName : "SelfLLClaimAuditGridClick",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : "and MngCom like'"
							+ comcode
							+ "%' and ( defaultoperator = '"+ operator +"' or auditer ='"+ operator +"') and RgtObj ='1' order by AcceptedDate,RptNo"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",	
							newCol0 : {
								title : "��������",
								style : 1,
								colNo : 6,
								colName : "(select rgtdate from llregister where rgtno=t.missionprop1) ",
								rename : "RptDate"
							},
							newCol1 : {
								title : "����������",
								style : 1,
								colNo : 10,
								colName : "(select UserName from LLClaimUser where UserCode = (select operator from llregister where trim(rgtno) = t.missionprop1)) ",
								rename : "Rptoperator"
							},
							newCol2 : {
								title : "��������Ƿ����",
								style : 1,
								colNo : 12,
								colName : sql1,
								rename : "resellingflag"
							},
							newCol3 : {
								title : "��������",
								style : 3,
								colName : "(select accepteddate from llregister where rgtno=t.missionprop1) ",
								rename : "Accepteddate"
							},
							newCol4 : {
								title : "Ԥ����־",
								style : 1,
								colNo : 7,
								colName : "(case t.MissionProp11 when '0' then '��Ԥ��' when '1' then '��Ԥ��' end) ",
								rename : "PrepayFlag1"
							},
							result0 : {title : " ������     ", style : 1,colNo : 1},
							result1 : {
								title : "����״̬",
								style : 1,
								colNo : 2,
								colName : "(case t.missionprop2 when '20' then '����' when '30' then '���' when '40' then '����' end) ",
								rename : "RptorState"
								},
							result2 : {title : "�ͻ�����", style : 1,colNo : 3},
							result3 : {title : "�ͻ�����", style : 1,colNo : 4},
							result4 : {
								title : "�Ա�", 
								style : 1,
								colNo : 5,
								colName : "(case t.missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end) ",
								rename : "CustomerSex"
								},
							result5 : {title : "�¹�����", style : 3},
							result6 : {
								title : "��������",
								style : 1,
								colNo : 8,
								colName : "(case t.MissionProp7 when '1' then '����' when '2' then '����' end) ",
								rename : "RgtClass"},
							result7 : {title : "��������", style : 3 },
							result8 : {title : "��������", style : 3 },
							result9 : {title : "���Ȩ��", style : 3 },
							result10 : {
								title : "Ԥ����־",
								style : 3
								},
							result11 : {
								title : "�� ��", 
								style : 1,
								colNo : 9,
								colName : "(case t.MissionProp12 when 'A' then '����' when 'B' then '���' when 'C' then '����' when 'D' then '���װ���' end) ",
								rename : "ComeWhere"},
							result12 : {title : "Ȩ�޿缶��־", style : 3},
							result13 : {title : "��˲�����", style : 3},
							result14 : {title : "����", style : 1,colNo : 11},
							result15 : {title : "�����", style : 3}
						}
					}
				}
			}
	 }
	 jQuery("#AuditClaimPool").workpool(config);
	 jQuery("#privateSearch").click();
 }
 
// ������Ϣ�б��ĳ�ʼ��
/*function initLLClaimAuditGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="������";             //����
    iArray[1][1]="160px";                //�п�
    iArray[1][2]=160;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ⰸ״̬";             //����
    iArray[2][1]="70px";                //�п�
    iArray[2][2]=70;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ͻ�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="50px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="�¹�����";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������            
    
    iArray[10]=new Array();
    iArray[10][0]="Ԥ����־";             //����
    iArray[10][1]="60px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="��������";             //����
    iArray[11][1]="60px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="����";             //����
    iArray[12][1]="60px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[13]=new Array();
    iArray[13][0]="Ȩ��";             //����
    iArray[13][1]="50px";                //�п�
    iArray[13][2]=200;                  //�����ֵ
    iArray[13][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[14]=new Array();
    iArray[14][0]="Ԥ��ֵ";             //����
    iArray[14][1]="120px";                //�п�
    iArray[14][2]=200;                  //�����ֵ
    iArray[14][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
   
    iArray[15]=new Array();
    iArray[15][0]="��������";             //����
    iArray[15][1]="80px";                //�п�
    iArray[15][2]=200;                  //�����ֵ
    iArray[15][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������ 
    
    iArray[16]=new Array();
    iArray[16][0]="����������";             //����
    iArray[16][1]="80px";                //�п�
    iArray[16][2]=200;                  //�����ֵ
    iArray[16][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������ 
    
    iArray[17]=new Array();
    iArray[17][0]="����";             //����
    iArray[17][1]="80px";                //�п�
    iArray[17][2]=200;                  //�����ֵ
    iArray[17][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������ 
    
    LLClaimAuditGrid = new MulLineEnter( "document" , "LLClaimAuditGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimAuditGrid.mulLineCount = 0;   
    LLClaimAuditGrid.displayTitle = 1;
    LLClaimAuditGrid.locked = 0;
//    LLClaimAuditGrid.canChk = 1;
    LLClaimAuditGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimAuditGrid.selBoxEventFuncName ="LLClaimAuditGridClick"; //��Ӧ�ĺ���������������
//        LLClaimAuditGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLClaimAuditGrid.hiddenPlus=1;
    LLClaimAuditGrid.hiddenSubtraction=1;
    LLClaimAuditGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б��ĳ�ʼ��
/*function initSelfLLClaimAuditGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="������";             //����
    iArray[1][1]="160px";                //�п�
    iArray[1][2]=160;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ⰸ״̬";             //����
    iArray[2][1]="70px";                //�п�
    iArray[2][2]=70;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ͻ�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="50px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������            
    
    iArray[10]=new Array();
    iArray[10][0]="Ԥ����־";             //����
    iArray[10][1]="60px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="��������";             //����
    iArray[11][1]="60px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="����";             //����
    iArray[12][1]="60px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[13]=new Array();
    iArray[13][0]="Ȩ��";             //����
    iArray[13][1]="50px";                //�п�
    iArray[13][2]=200;                  //�����ֵ
    iArray[13][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[14]=new Array();
    iArray[14][0]="Ԥ��ֵ";             //����
    iArray[14][1]="120px";                //�п�
    iArray[14][2]=200;                  //�����ֵ
    iArray[14][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
   
    iArray[15]=new Array();
    iArray[15][0]="��������";             //����
    iArray[15][1]="80px";                //�п�
    iArray[15][2]=200;                  //�����ֵ
    iArray[15][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������
    
    iArray[16]=new Array();
    iArray[16][0]="����������";             //����
    iArray[16][1]="80px";                //�п�
    iArray[16][2]=200;                  //�����ֵ
    iArray[16][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������ 
    
    iArray[17]=new Array();
    iArray[17][0]="����";             //����
    iArray[17][1]="80px";                //�п�
    iArray[17][2]=200;                  //�����ֵ
    iArray[17][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������  

    iArray[18]=new Array();
    iArray[18][0]="��������";             //����
    iArray[18][1]="80px";                //�п�
    iArray[18][2]=200;                  //�����ֵ
    iArray[18][3]=3;                    //�Ƿ���������,1��ʾ������0��ʾ������  

    iArray[19]=new Array();
    iArray[19][0]="��������Ƿ����";             //����
    iArray[19][1]="100px";                //�п�
    iArray[19][2]=200;                  //�����ֵ
    iArray[19][3]=0;                    //�Ƿ���������,1��ʾ������0��ʾ������  
    
    SelfLLClaimAuditGrid = new MulLineEnter( "document" , "SelfLLClaimAuditGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLClaimAuditGrid.mulLineCount = 0;   
    SelfLLClaimAuditGrid.displayTitle = 1;
    SelfLLClaimAuditGrid.locked = 0;
//    SelfLLClaimAuditGrid.canChk = 1;
    SelfLLClaimAuditGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLClaimAuditGrid.selBoxEventFuncName ="SelfLLClaimAuditGridClick"; //��Ӧ�ĺ���������������
//        SelfLLClaimAuditGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    SelfLLClaimAuditGrid.hiddenPlus=1;
    SelfLLClaimAuditGrid.hiddenSubtraction=1;
    SelfLLClaimAuditGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>