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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tContNo = "";
  String tFlag = "";
  String strsql ="";
  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");

  loggerDebug("QuestInputInit","ContNo:"+tContNo);
  loggerDebug("QuestInputInit","Flag:"+tFlag);

%>                            

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
    fm.all('BackObj').value = '';
    fm.all('Content').value = '';
    fm.all('Quest').value = '';
    fm.all('QuestionObj').value = '0';
    fm.all('QuestionObjName').value = 'Ͷ����';
    //alert(3);
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


	//initQuestGrid();

	initHide(tContNo,tFlag);
 
	QuestQuery(tContNo,tFlag);
 
	//QueryCont(tContNo,tFlag);
	
	initCodeDate(tContNo,tFlag);
 
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
      iArray[1][0]="�������";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="300px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
                           

      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      QuestGrid.mulLineCount = 1;
      QuestGrid.displayTitle = 1;
      QuestGrid.canChk = 1;
      QuestGrid.loadMulLine(iArray);  
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tFlag)
{
	fm.all('ContNo').value=tContNo;
	fm.all('Flag').value=tFlag;
	//alert("pol:"+tContNo);
}

function initCodeDate(tContNo,tFlag)
{
	if (tFlag == "0")
	{
		fm.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		//fm.all('BackObj').value = "1";
	}
	if (tFlag == "5")
	{
		fm.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
		//fm.all('BackObj').value = "1";
	}
	if (tFlag == "1")
	{
		fm.all('BackObj').CodeData = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
	}
}

</script>

