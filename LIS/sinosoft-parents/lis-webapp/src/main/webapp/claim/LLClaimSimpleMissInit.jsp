<%
//�������ƣ�LLClaimSimpleMissInit.jsp
//�����ܣ�
//�������ڣ�2005-6-6 11:30
//������  ��zl
//���¼�¼��
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//���ղ���
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
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

// �����ĳ�ʼ��
function initInpBox()
{ 

  try
  {                                   
  }
  catch(ex)
  {
    alert("��LLClaimSimpleMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLClaimSimpleMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    //initLLClaimSimpleGrid();
   // initSelfLLClaimSimpleGrid();
   // querySelfGrid();
//	initSelfPolGrid();
//	easyQueryClickSelf();
	initSimpleClaimPool();
  }
  catch(re)
  {
    alert("LLClaimSimpleMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
function initSimpleClaimPool(){
	 var config = {
				//activityId : "0000005065",
				functionId : "10030007",
				operator : operator,
				public : {
					show : true,
					query : {
						queryButton : {
							"1" : {
								title : "��  ��",
								func : "ApplyClaim"
								}
						},
						arrayInfo : {
							col : {
								newCol0 : {
									title : "��������",
									style : 8, 
									colNo : 6,
									refercode1 : "rptdate",
									addCondition : function(colName ,value){
										return "and exists(select 1 from llregister where rgtno=t.missionprop1 and rgtdate='"+value+"')";
									}
									
								},
								result0 : {title : " �ⰸ��   ", style : 1,colNo : 1},
								result1 : {
									title : "����״̬",
									style : 2,
									colNo : 2,
									refercode2 : "RptorState",
									refercodedata2 : "0|3^10|�ѱ���^20|������^30|���",
									addCondition : function(colName ,value){
										return " and t.missionprop2 = '"+value+"'";
									}
									},
								result2 : {title : "�ͻ�����", style : 1 , colNo : 3 },
								result3 : {
									title : "�ͻ�����", 
									style : 1 , 
									colNo : 4 ,
									refercode1 : "CustomerName",
									addCondition : function(colName,value){
										return " and t.MissionProp4 like '"+value+"%'";
									}
								    },
								result4 : {
									title : "�ͻ��Ա�", 
									style : 2,
									colNo : 5,
									refercode1 : "sex",
									addCondition : function(colName,value){
										return " and t.MissionProp5 = '"+value+"'";
									}
								    },
								result5 : {title : "�¹�����", style : 3},
								result6 : {title : "��������", style : 3},
								result7 : {title : "��������", style : 3},
								result8 : {title : "��������", style : 3},
								result9 : {title : "����", style : 3}
							}
						}
					},
					result : {
						condition : {
							"0" : false,
							"1" : false,
							"2" : false,
							"3" : false,
							"4" : true,
							"5" : "and MngCom like '%"
								+ comcode
								+ "%' and exists (select 'X' from llclaimuser where simpleflag='1' and usercode='"+ operator +"')"
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
									colNo : 7,
									colName : "(select UserName from LLClaimUser where UserCode = t.lastoperator) ",
									rename : "Rptoperator"
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
									title : "�ͻ��Ա�", 
									style : 1,
									colNo : 5,
									colName : "(case t.missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end) ",
									rename : "CustomerSex"
									},
								result5 : {title : "�¹�����", style : 3},
								result6 : {
									title : "��������",
									style : 3},
								result7 : {title : "��������", style : 3 },
								result8 : {title : "��������", style : 3 },
								result9 : {title : " ����  ", style : 1,colNo : 8}

							}
						}
					}
				},
				private : {
					query : {
						queryButton : {},
						arrayInfo : {
							col : {
								result0 : {title : " �ⰸ��   ", style : 1,colNo : 1},
								result1 : {
									title : "����״̬",
									style : 3
									},
								result2 : {title : "�ͻ�����", style : 3},
								result3 : {title : "�ͻ�����", style : 3},
								result4 : {title : "�ͻ��Ա�", style : 3},
								result5 : {title : "�¹�����", style : 3},
								result6 : {title : "��������", style : 3},
								result7 : {title : "��������", style : 3},
								result8 : {title : "��������", style : 3},
								result9 : {title : "����", style : 3}
							}
						}
					},
					resultTilte : "��������",
					result : {
						selBoxEventFuncName : "SelfLLClaimSimpleGridClick",
						selBoxEventFuncParam : "",
						condition : {
							"0" : false,
							"1" : false,
							"2" : false,
							"5" : " order by RptNo"
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
									colNo : 7,
									colName : "(select UserName from LLClaimUser where UserCode = t.lastoperator) ",
									rename : "Rptoperator"
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
									title : "�ͻ��Ա�", 
									style : 1,
									colNo : 5,
									colName : "(case t.missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end) ",
									rename : "CustomerSex"
									},
								result5 : {title : "�¹�����", style : 3},
								result6 : {
									title : "��������",
									style : 3},
								result7 : {title : "��������", style : 3 },
								result8 : {title : "��������", style : 3 },
								result9 : {title : " ����  ", style : 1,colNo : 8}

							}
						}
					}
				}
		 }
		 jQuery("#SimpleClaimPool").workpool(config);
		 jQuery("#privateSearch").click();
}

//end by lzf


// ������Ϣ�б�ĳ�ʼ��
/*function initLLClaimSimpleGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="160px";                //�п�
    iArray[1][2]=160;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ⰸ״̬";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ͻ�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    iArray[10]=new Array();
    iArray[10][0]="����������";             //����
    iArray[10][1]="100px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="����";             //����
    iArray[11][1]="80px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������   
    
    LLClaimSimpleGrid = new MulLineEnter( "fm" , "LLClaimSimpleGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimSimpleGrid.mulLineCount = 0;   
    LLClaimSimpleGrid.displayTitle = 1;
    LLClaimSimpleGrid.locked = 0;
//    LLClaimSimpleGrid.canChk = 1;
    LLClaimSimpleGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimSimpleGrid.selBoxEventFuncName ="LLClaimSimpleGridClick"; //��Ӧ�ĺ���������������
//        LLClaimSimpleGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLClaimSimpleGrid.hiddenPlus=1;
    LLClaimSimpleGrid.hiddenSubtraction=1;
    LLClaimSimpleGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfLLClaimSimpleGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="160px";                //�п�
    iArray[1][2]=160;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ⰸ״̬";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�ͻ�����";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    iArray[10]=new Array();
    iArray[10][0]="����������";             //����
    iArray[10][1]="100px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="����";             //����
    iArray[11][1]="80px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������   
    
    SelfLLClaimSimpleGrid = new MulLineEnter( "fm" , "SelfLLClaimSimpleGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLClaimSimpleGrid.mulLineCount = 0;   
    SelfLLClaimSimpleGrid.displayTitle = 1;
    SelfLLClaimSimpleGrid.locked = 0;
//    SelfLLClaimSimpleGrid.canChk = 1;
    SelfLLClaimSimpleGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLClaimSimpleGrid.selBoxEventFuncName ="SelfLLClaimSimpleGridClick"; //��Ӧ�ĺ���������������
//        SelfLLClaimSimpleGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    SelfLLClaimSimpleGrid.hiddenPlus=1;
    SelfLLClaimSimpleGrid.hiddenSubtraction=1;
    SelfLLClaimSimpleGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
