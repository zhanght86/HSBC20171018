<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestQueryInit.jsp
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
  String tContNo = "";
  String tFlag = "";
  String tMissionID = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");
  String ActivityID = request.getParameter("ActivityID");

  loggerDebug("QuestQueryInit","ContNo:"+tContNo);
  loggerDebug("QuestQueryInit","Flag:"+tFlag);
  loggerDebug("QuestQueryInit","Flag:"+ActivityID);

%>            
<script>
	var tMissionID = "<%=tMissionID%>";   //
	var tSubMissionID = "<%=tSubMissionID%>"; //
	var tContNo = "<%=tContNo%>"; //
	var ActivityID = "<%=ActivityID%>";
</script>
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo,tFlag,tMissionID,tSubMissionID)
{ 
try
  {                 
    document.all('MissionID').value = tMissionID;
    document.all('SubMissionID').value = tSubMissionID;                  
    document.all('ContNo').value = tContNo;
    document.all('IfReply').value = 'A';
    document.all('ActivityID').value = ActivityID;
     if (tFlag == "5")
     {
     	document.all('BackObj').value = '';
     }
     else
     {
     	document.all('BackObj').value = '';
     }
    
    document.all('ManageCom').value = '';
    document.all('OManageCom').value = '';
    if(tFlag == "1")
    {
    	divOperation.style.display = "none";
    }
    if (tFlag == "5")
    {
    	document.all('OperatePos').value = '';
    }
    else if(tFlag == "3"||tFlag == "1")
    {
    	document.all('OperatePos').value = '';	    
    }
    else if(tFlag == "4")
    {
    	document.all('BackObj').value = '4';
    	document.all('OperatePos').value = '';
    }
    else
    {
    	document.all('OperatePos').value = '';
    }
    document.all('Content').value = '';
    document.all('ReplyResult').value = '';
  }
  catch(ex)
  {
    alert("��UWManuDateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

function initContent()
{
    document.all('Content').value = '';
    document.all('ReplyResult').value = '';
}

function initForm(tContNo,tFlag,tMissionID,tSubMissionID)
{
  try
  {
	initInpBox(tContNo,tFlag,tMissionID,tSubMissionID);
	//alert(1);
	initQuestGrid(tFlag);
	initHide(tContNo,tFlag);	
	QuestQuery(tContNo,tFlag);

	
	if (tFlag == "5"||tFlag == "1")
	{
		showDiv(divButton,"false");
		showDiv(divUWButton,"true");
	}
	else
	{
		showDiv(divButton,"true");
		showDiv(divUWButton,"false");
	}
	
	if (tFlag == "1")
	{
		showDiv(divModiButton,"true");
	}
	else
	{
		showDiv(divModiButton,"false");
	}
	if(tFlag=="4")
	{
		
		showDiv(divButtonCenter,"true");
	}
	else
	{
		showDiv(divButtonCenter,"false");
		}
	//tongmeng 2009-02-10 add
	//�����Ƿ������
	if(tFlag=="3")
	{
		//divErrorSave
		showDiv(divErrorSave,"true");
	}	
	initCodeData(tContNo,tFlag);
	//alert(3);
	query();

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initQuestGrid(tFlag)
  {                  
  	//alert(tFlag);    
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         			//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                           
      iArray[4]=new Array();
      iArray[4][0]="�ظ�����";         			//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="¼����";         			//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="¼������";         			//����
      iArray[6][1]="85px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����λ��";         			//����
      iArray[7][1]="118px";            		//�п�
      iArray[7][2]=50;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][10] = "OperateLocation";
      //iArray[7][11] = "0|^0|�µ�¼��/�����޸�/�����޸�^1|�˹��˱�^5|�µ�����";
      //iArray[7][12] = "7";
      //iArray[7][13] = "1";

      iArray[8]=new Array();
      iArray[8][0]="���Ͷ���";         			//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�Ƿ��·�";         			//����
      iArray[9][1]="50px";            		//�п�
      iArray[9][2]=50;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][10] = "NeedPrint";
      iArray[9][11] = "0|^Y|�·�^N|���·�";

      
      iArray[10]=new Array();
      iArray[10][0]="��ˮ��";         			//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������     
      
      iArray[11]=new Array();
      iArray[11][0]="��������";         			//����
      iArray[11][1]="85px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�ظ�����";         			//����
      iArray[12][1]="85px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[13]=new Array();
      iArray[13][0]="¼��λ��";         			//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   
   
      iArray[14]=new Array();
      iArray[14][0]="�Ƿ������";         			//����
   if(tFlag!=null&&tFlag=='3')
   {
   	iArray[14][1]="80px";            		//�п�
   }
   else
  {
      iArray[14][1]="0px";            		//�п�
    }
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      QuestGrid.mulLineCount = 1;
      QuestGrid.displayTitle = 1;
      QuestGrid.canSel = 1;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      QuestGrid.selBoxEventFuncName = "queryone";
      QuestGrid.loadMulLine(iArray);      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tFlag)
{
	document.all('ContNoHide').value=tContNo;
	document.all('Flag').value=tFlag;
	document.all('HideOperatePos').value="";
	document.all('HideQuest').value="";
	document.all('HideSerialNo').value = "";
	//alert("pol:"+tContNo);
}

function initCodeData(tContNo,tFlag)
{
	if (tFlag == "5")
	{
		document.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		document.all('OperatePos').CodeData = "0|^0|�б�^5|����";
	}
	if (tFlag == "1")
	{
		document.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		document.all('OperatePos').CodeData = "0|^0|�б�^1|�˱�^5|����";
	}
	if (tFlag == "2")
	{
		document.all('BackObj').CodeData = "0|^1|����Ա";
		document.all('OperatePos').CodeData = "0|^1|�˱�^5|����";
	}
	if (tFlag == "3")
	{
		document.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		document.all('OperatePos').CodeData = "0|^0|�б�^1|�˱�^5|����";
	}
	if (tFlag == "4")
	{
		document.all('BackObj').CodeData = "0|^4|����";
		document.all('OperatePos').CodeData = "0|^0|�б�^1|�˱�^5|����";
	}	
}

</script>


