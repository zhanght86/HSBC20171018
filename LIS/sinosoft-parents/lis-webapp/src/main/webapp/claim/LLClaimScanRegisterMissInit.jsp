<%
//�������ƣ�LLClaimRegisterMissInit.jsp
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
    alert("��LLClaimRegisterMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLClaimRegisterMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
   /* initLLClaimRegisterGrid();
    initSelfLLClaimRegisterGrid();
    queryGrid();
    querySelfGrid();
    */ 
    initScanerClaimPool();
  }
  catch(re)
  {
    alert("LLClaimRegisterMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.name +":"+re.message);
  }
}

 function initScanerClaimPool(){
	var config = {
		//activityID : "0000005015",	
		functionId : "10030003",
		operator : operator,
		public : {
			show : false
		},
		private : {
			id : "ScanerClaimPool",
            query :{
				queryButton : {},
				arrayInfo : {
					col : {
						result0 : {title : "  ������    ", style : 1 , colNo : 1 },
						result1 : {title : "����״̬", style : 3 },
						result2 : {title : " �ͻ���  ", style : 1 , colNo : 2 },
						result3 : {title : "�ͻ�����  ", style : 1 , colNo : 3 },
						result4 : {title : "�ͻ��Ա�", style : 1 , colNo : 4 },
						result5 : {title : " �¹�����  ", style : 8 , colNo : 5 },
						result6 : {title : "�����ű���", style : 3 },
						result7 : {title : "ɨ����", style : 3 },
						result8 : {title : "���������", style : 3 },
						result9 : {title : "���¼����ɱ�־", style : 3 },
						result10 : {title : "����", style : 3 },
						result11 : {title : "��������", style : 3 }
					}
				}
			},
			resultTitle : "��������",
			result : {
				selBoxEventFuncName : "SelfLLClaimRegisterGridClick",
				selBoxEventFuncParam : "",
				condition : {
					"0" : false,
					"1" : false,
					"2" : false,
					"5" : "and MngCom like'"
						+ comcode
						+ "%' and ScanFlag = '1' order by rgtno"
				},
				mulLineCount : 0,
				arrayInfo : {
					col : {
						col5 : "0",
						col6 : "0",
						col7 : "0",						
						newCol0 : {
							title : "ɨ������ ",
							colName : "(select makedate from es_doc_main where docid in (select docid from es_doc_relation where doccode = t.missionprop1 and bussnotype='15' and busstype='LP' and Subtype = 'CA001')) ",
							rename : "scandate",
							style : 1,
							colNo : 2
						},
						newCol1 : {
							title : "ɨ����� ",
							colName : "(select managecom from es_doc_main where docid in (select docid from es_doc_relation where doccode = t.missionprop1 and bussnotype='15' and busstype='LP' and Subtype = 'CA001')) ",
							rename : "scanmng",
							style : 3
						},
						newCol2 : {
							title : " ɨ����Ա",
							colName : "(select username from lduser where usercode in (select scanoperator from es_doc_main where docid in (select docid from es_doc_relation where doccode = t.missionprop1 and bussnotype='15' and busstype='LP' and Subtype = 'CA001'))) ",
							rename : "ScanOperator",
							style : 1,
							colNo : 4
							},
						newCol3 : {
							title : " �������     ",
							colName : "(select bussno  From es_doc_relation  where docid In (select docid from es_doc_relation  where doccode = t.missionprop1 and BussNoType = '15'  And Busstype = 'LP' And Subtype = 'CA001') And bussnotype = '11')",
							rename : "applyno",
							style : 1,
							colNo : 9
						},
						
						result0 : {title : "  ������    ", style : 1 , colNo : 1 },
						result1 : {title : "����״̬", style : 3 },
						result2 : {title : " �ͻ���  ", style : 1 , colNo : 5 },
						result3 : {title : "�ͻ�����  ", style : 1 , colNo : 6 },
						result4 : {
							title : "�ͻ��Ա�", 
							style : 1 , 
							colNo : 7 ,
							colName : "(case t.missionprop5 when '0' then '��' when '1' then 'Ů' when '2' then 'δ֪' end)",
							rename : "CustomerSex"
							},
						result5 : {title : " �¹�����  ", style : 8 , colNo : 8 },
						result6 : {title : "�����ű���", style : 3 },
						result7 : {title : "ɨ����", style : 3 },
						result8 : {title : "���������", style : 3 },
						result9 : {title : "���¼����ɱ�־", style : 3 },
						result10 : {title : "����", style : 3 },
						result11 : {
							title : "��������", 
							style : 3,
							colName : "(select accepteddate from llregister where rgtno = t.missionprop1) ",
							rename : "AcceptedDate"
						 }						
						
					}
				}
			}
		}
	}
	jQuery("#ScanerClaimPool").workpool(config);
	jQuery("#privateSearch").click();	
}

//���ҳ��
/*function emptyForm() 
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
*/
// ������Ϣ�б�ĳ�ʼ��
/*function initLLClaimRegisterGrid()
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
    iArray[1][0]="������";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="ɨ������";             //����
    iArray[2][1]="70px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="ɨ�����";             //����
    iArray[3][1]="70px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="ɨ����Ա";             //����
    iArray[4][1]="60px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�������";             //����
    iArray[5][1]="150px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="�������";             //����
    iArray[6][1]="60px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0; 


    iArray[7]=new Array();
    iArray[7][0]="������۱�־";             //����
    iArray[7][1]="0px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3; 

    
    iArray[8]=new Array();
    iArray[8][0]="Missionid";             //����
    iArray[8][1]="0px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="submissionid";             //����
    iArray[9][1]="0px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="activityid";             //����
    iArray[10][1]="0px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
    
    
    LLClaimRegisterGrid = new MulLineEnter( "fm" , "LLClaimRegisterGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimRegisterGrid.mulLineCount = 0;   
    LLClaimRegisterGrid.displayTitle = 1;
    LLClaimRegisterGrid.locked = 0;
//    LLClaimRegisterGrid.canChk = 1;
    LLClaimRegisterGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
//        LLClaimRegisterGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���     
    LLClaimRegisterGrid.hiddenPlus=1;
    LLClaimRegisterGrid.hiddenSubtraction=1;
    LLClaimRegisterGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initSelfLLClaimRegisterGrid()
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
    iArray[1][0]="������";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�Ƿ�����";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�Ƿ�������־";             //����
    iArray[3][1]="0px";                //�п�
    iArray[3][2]=100;                  //�����ֵ
    iArray[3][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�������";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=100;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="������۱�־";             //����
    iArray[5][1]="0px";                //�п�
    iArray[5][2]=100;                  //�����ֵ
    iArray[5][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="ɨ������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=100;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="ɨ�����";             //����
    iArray[7][1]="100px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="ɨ����Ա";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=500;                  //�����ֵ
    iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="�ͻ���";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="�ͻ�����";             //����
    iArray[10][1]="80px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[11]=new Array();
    iArray[11][0]="�������";             //����
    iArray[11][1]="80px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0; 
    
    iArray[12]=new Array();
    iArray[12][0]="Missionid";             //����
    iArray[12][1]="0px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[13]=new Array();
    iArray[13][0]="submissionid";             //����
    iArray[13][1]="0px";                //�п�
    iArray[13][2]=200;                  //�����ֵ
    iArray[13][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[14]=new Array();
    iArray[14][0]="activityid";             //����
    iArray[14][1]="0px";                //�п�
    iArray[14][2]=200;                  //�����ֵ
    iArray[14][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      

    iArray[15]=new Array();
    iArray[15][0]="activityid";             //����
    iArray[15][1]="0px";                //�п�
    iArray[15][2]=200;                  //�����ֵ
    iArray[15][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������                  
    
    SelfLLClaimRegisterGrid = new MulLineEnter( "fm" , "SelfLLClaimRegisterGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLClaimRegisterGrid.mulLineCount = 0;   
    SelfLLClaimRegisterGrid.displayTitle = 1;
    SelfLLClaimRegisterGrid.locked = 0;
//    SelfLLClaimRegisterGrid.canChk = 1;
    SelfLLClaimRegisterGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SelfLLClaimRegisterGrid.selBoxEventFuncName ="SelfLLClaimRegisterGridClick"; //��Ӧ�ĺ���������������
//        SelfLLClaimRegisterGrid.selBoxEventFuncParm =""; //����Ĳ���,����ʡ�Ը���    
    SelfLLClaimRegisterGrid.hiddenPlus=1;
    SelfLLClaimRegisterGrid.hiddenSubtraction=1;
    SelfLLClaimRegisterGrid.loadMulLine(iArray);  

    }
    catch(ex)
    {
        alert(ex);
    }
}
*/
</script>
