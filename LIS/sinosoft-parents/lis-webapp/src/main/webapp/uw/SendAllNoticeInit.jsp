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

  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");

  loggerDebug("SendAllNoticeInit","ContNo:"+tContNo);
  loggerDebug("SendAllNoticeInit","Flag:"+tFlag);

%>                            

<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   
    document.all('NoticeType').value = '';
    document.all('Content').value = '';
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

function initForm(tContNo,tMissionID,tSubMissionID,tEdorNo,tEdorType)
{
  try
  {

	initInpBox();

	//initQuestGrid();
	initQuestionTypeGrid();
	initUWSpecGrid()

	initHide(tContNo,tMissionID,tSubMissionID,tEdorNo,tEdorType);
	initLWMission();
	initLoprtManager();
	queryspec(tContNo);


	
	

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initQuestionTypeGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ʾ����ͱ��";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4] = "rreporttype";             			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="�ʾ���������";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      QuestionTypeGrid = new MulLineEnter( "fm" , "QuestionTypeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      QuestionTypeGrid.mulLineCount = 0;
      QuestionTypeGrid.displayTitle = 1;
      QuestionTypeGrid.canChk = 0;
      QuestionTypeGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      
      }
      catch(ex)
      {
        alert(ex);
      }
}



// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
  {                              
    var iArray = new Array();
     
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[1]=new Array();
      iArray[1][0]="��ӡ��ˮ��";         			//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][10] = "NoType";
      iArray[3][11] = "0|^00|����Ͷ������^01|����Ͷ������^02|��ͬ��^03|������^04|ʵ���վݺ�^05|����ӡˢ��";
      iArray[3][12] = "3";
      iArray[3][19] = "0";
      
      
	    iArray[4]=new Array();
			iArray[4][0]="ҵ��Ա����";
			iArray[4][1]="100px";
			iArray[4][2]=100;
			iArray[4][3]=2;
			iArray[4][4]="AgentCode";
			iArray[4][5]="3";
			iArray[4][9]="�����˱���|code:AgentCode&NOTNULL";
			iArray[4][18]=250;
			iArray[4][19]= 0 ;
		
		
      iArray[5]=new Array();
      iArray[5][0]="֪ͨ������";    	//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][4]="uwnoticetype";
			iArray[5][5]="3";
			iArray[5][9]="�����˱���|code:uwnoticetype&NOTNULL";
			iArray[5][18]=250;
			iArray[5][19]= 0 ;

      iArray[6]=new Array();
      iArray[6][0]="��ӡ״̬";    	//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
                               

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
    
      PolGrid.loadMulLine(iArray);  
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.canSel = 0;
      PolGrid.selBoxEventFuncName = "queryresult";
      
     
      }
      catch(ex)
      {
    
        alert(ex);
      }
}

function initHide(tContNo,tMissionID,tSubMissionID,tEdorNo,tEdorType)
{
		document.all('ContNo').value=tContNo;
  	document.all('MissionID').value = tMissionID;
  	document.all('SubMissionID').value = tSubMissionID;	
  	document.all('EdorNo').value = tEdorNo;
  	document.all('EdorType').value = tEdorType;   
}


function initUWSpecGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";         		//����
      iArray[1][1]="430px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ˮ��";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="״̬����";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="״̬";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="������";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[6]=new Array();
      iArray[6][0]="���к�";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������


      UWSpecGrid = new MulLineEnter( "fm" , "UWSpecGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWSpecGrid.mulLineCount = 3;   
      UWSpecGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecGrid.canSel = 0;
      UWSpecGrid.canChk = 1;
      UWSpecGrid.hiddenPlus = 1;
      UWSpecGrid.hiddenSubtraction = 1;
      UWSpecGrid.loadMulLine(iArray);       
      
     
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


