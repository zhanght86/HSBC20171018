<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestInputInit.jsp
//�����ܣ������¼��
//�������ڣ�2002-06-19 11:10:36
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
  String strsql ="";
  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");
  String tMissionID = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  String tActivityID = request.getParameter("ActivityID");

  loggerDebug("RnewQuestInputInit","ContNo:"+tContNo);
  loggerDebug("RnewQuestInputInit","Flag:"+tFlag);

%>                            

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {            
    document.all('BackObj').value = '';
    document.all('BackObjName').value='';
    document.all('Content').value = '';
    document.all('Quest').value = '';
    //document.all('Questionnaire').value= '';
    //document.all('QuestionObj').value = '1';
    //document.all('QuestionObjName').value = '��һ������';
    document.all("hiddenQuestionSeq").value = '';
    document.all("hiddenProposalContNo").value = '';
    
    document.all('MissionID').value='<%=tMissionID%>';
    document.all('SubMissionID').value='<%=tSubMissionID%>';
    document.all('ActivityID').value='<%=tActivityID%>';
    document.all('NeedPrintFlag').value='Y';
    document.all('IFNeedFlagName').value='�·�';
    /*alert(3);
    strsql = " select appntno,appntname from lccont where  ContNo = '<%=tContNo%>' ";
    //alert(<%=tContNo%>);	
    
     //��ѯSQL�����ؽ���ַ���
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  

    //�ж��Ƿ��ѯ�ɹ�
    if (!turnPage.strQueryResult) 
    	 {
      
           return "";
 	 }
  
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
    fm.CustomerNo.value = turnPage.arrDataCacheSet[0][0];
 
    fm.CustomerName.value = turnPage.arrDataCacheSet[0][1];
    */
  }
  catch(ex)
  {
    alert("��UWManuDateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��UWSubInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tFlag)
{
  try
  {
	initInpBox();


	initQuestGrid();

	initHide(tContNo,tFlag);
 
	QuestQuery(tContNo,tFlag);
  questAllNeedQuestion(tContNo,tFlag);
	//QueryCont(tContNo,tFlag);
	
	//initCodeDate(tContNo,tFlag);
 
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б��ĳ�ʼ��
function initQuestGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
  
      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         			//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
                           
      iArray[4]=new Array();
      iArray[4][0]="�ظ�����";         			//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="¼����";         			//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="¼������";         			//����
      iArray[6][1]="85px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����λ��";         			//����
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=50;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      //iArray[7][10] = "OperateLocation";
      //iArray[7][11] = "0|^0|�µ�¼��/�����޸�/�����޸�^1|�˹��˱�^5|�µ�����";
      //iArray[7][12] = "7";
      //iArray[7][13] = "1";

      iArray[8]=new Array();
      iArray[8][0]="���ض���";         			//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[8][10] = "ReturnToObject";
      iArray[8][11] = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
      iArray[8][12] = "8";
      iArray[8][13] = "1";
      
      iArray[9]=new Array();
      iArray[9][0]="�Ƿ��·�";         			//����
      iArray[9][1]="50px";            		//�п�
      iArray[9][2]=50;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ������0��ʾ������
      iArray[9][10] = "NeedPrint";
      iArray[9][11] = "0|^Y|�·�^N|���·�";
//      iArray[9][12] = "0|1";
//      iArray[9][13] = "1";   
      
      iArray[10]=new Array();
      iArray[10][0]="��ˮ��";         			//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ������0��ʾ������     
      
      iArray[11]=new Array();
      iArray[11][0]="���Ͷ���";         			//����
      iArray[11][1]="100px";            		//�п�
      iArray[11][2]=50;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������     

      iArray[12]=new Array();
      iArray[12][0]="����״̬";         			//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=50;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������     
      
      iArray[13]=new Array();
      iArray[13][0]="���ն���";         			//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=50;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������    

   
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
	document.all('ContNo').value=tContNo;
	document.all('Flag').value=tFlag;
	//alert("pol:"+tContNo);
}

function initCodeDate(tContNo,tFlag)
{
	//alert("tFlag:"+tFlag);
	if (tFlag == "0")
	{
		//¼������쳣������
		//fm.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		document.all('BackObj').CodeData = "0|^1|�˱�֪ͨ��(��ӡ��)^2|�˱�֪ͨ��(�Ǵ�ӡ��)^3|ҵ��Ա֪ͨ��^4|���ػ���^5|������޸ĸ�";
		//document.all('BackObj').value = "1";
	}
	if (tFlag == "5")
	{
		//����
		//document.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		//document.all('BackObj').CodeData = "0|^1|�˱�֪ͨ��(��ӡ��)^2|�˱�֪ͨ��(�Ǵ�ӡ��)^3|ҵ��Ա֪ͨ��^4|���ػ���^5|������޸ĸ�";
		document.all('BackObj').CodeData = "0|^1|�˱�֪ͨ��(��ӡ��)^2|�˱�֪ͨ��(�Ǵ�ӡ��)^3|ҵ��Ա֪ͨ��^4|���ػ���";
		//document.all('BackObj').value = "1";
	}
	if (tFlag == "1")
	{
	 //¼��
		//document.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		document.all('BackObj').CodeData = "0|^1|�˱�֪ͨ��(��ӡ��)^2|�˱�֪ͨ��(�Ǵ�ӡ��)^3|ҵ��Ա֪ͨ��^4|���ػ���";
	}
	
	if (tFlag == "3")
	{
	 //������޸�
		//document.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		document.all('BackObj').CodeData = "0|^1|�˱�֪ͨ��(��ӡ��)^2|�˱�֪ͨ��(�Ǵ�ӡ��)^3|ҵ��Ա֪ͨ��^4|���ػ���";
	}
	
	//tongmeng 2007-11-08 add
	//�����˹��˱������¼��
	if (tFlag == "6")
	{
				document.all('BackObj').CodeData = "0|^1|�˱�֪ͨ��(��ӡ��)^2|�˱�֪ͨ��(�Ǵ�ӡ��)^3|ҵ��Ա֪ͨ��^4|���ػ���^5|������޸ĸ�";

	}
	//alert(document.all('BackObj').CodeData);
}

</script>

