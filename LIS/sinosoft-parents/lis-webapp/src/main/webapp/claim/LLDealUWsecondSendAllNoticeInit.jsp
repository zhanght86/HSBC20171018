<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String tFlag = request.getParameter("Flag");
	loggerDebug("LLDealUWsecondSendAllNoticeInit","Flag:"+tFlag);
%>                            

<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('MissionID').value = <%= tMissionID %>;
    document.all('SubMissionID').value = <%= tSubMissionID %>;
 
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {                                   

    document.all('Content').value = '';
  }
  catch(ex)
  {
    alert("��LLDealUWsecondSendAllNoticeInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��LLDealUWsecondSendAllNoticeInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

/**
 * ��ʼ������ 
 *
 */
function initForm()
{
  try
  {
	    initParam();
		initInpBox();
		//initLoprtManager();
		//initQuestGrid();	
		initQuestionTypeGrid();
		//initUWSpecGrid();
		//initAddFeeGrid();	
		//querySpec(tContNo);
		//queryAddFee(tContNo);
		//queryAllNeedQuestion(tContNo);
		
		//add by ln 2008-09-25
		//initQuestDGrid();
		//initUWSpecDGrid();
		initUWHealthDGrid();
		//initUWExistDGrid();
		initUWHealthGrid();
		//initUWExistGrid();
		//initUWPlanGrid();
		//queryQuestD(tContNo);
		//queryUWSpecD(tContNo);
		queryUWHealthD();
		//queryUWExistD(tContNo);
		queryUWHealth();
		//queryUWExist(tContNo);
		//queryUWPlan(tContNo); //�����б��ƻ����
  }
  catch(re)
  {
    alert("LLDealUWsecondSendAllNoticeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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



//�ѷ������
function initQuestDGrid()
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
      iArray[1][0]="Ͷ������";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="¼����";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="¼������";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[6]=new Array();
      iArray[6][0]="���Ͷ���";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="���ն���";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="��ӡ״̬";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�ظ�״̬";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="�Ƿ���Ҫ��ӡ";         			//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������

      QuestDGrid = new MulLineEnter( "fm" , "QuestDGrid" ); 
      //��Щ���Ա�����loadMulLineǰ  
      QuestDGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      QuestDGrid.canSel = 0;
      QuestDGrid.canChk = 0;
      QuestDGrid.hiddenPlus = 1;
      QuestDGrid.hiddenSubtraction = 1;
      QuestDGrid.loadMulLine(iArray);       
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�ѷ���Լ
function initUWSpecDGrid()
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
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="¼����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="¼������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[4]=new Array();
      iArray[4][0]="���Ͷ���";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ն���";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��ӡ״̬";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�ظ�״̬";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�����˱��";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWSpecDGrid = new MulLineEnter( "fm" , "UWSpecDGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWSpecDGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecDGrid.canSel = 0;
      UWSpecDGrid.canChk = 0;
      UWSpecDGrid.hiddenPlus = 1;
      UWSpecDGrid.hiddenSubtraction = 1;
      UWSpecDGrid.loadMulLine(iArray);       
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�ѷ����
function initUWHealthDGrid()
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
      iArray[1][0]="�����Ŀ";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="¼����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="¼������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[4]=new Array();
      iArray[4][0]="���Ͷ���";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ն���";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��ӡ״̬";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�ظ�״̬";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="��ӡ��ˮ��";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWHealthDGrid = new MulLineEnter( "fm" , "UWHealthDGrid" ); 
      //��Щ���Ա�����loadMulLineǰ 
      UWHealthDGridmulLineCount = 11;
      //PolAddGrid.locked = 1;
      UWHealthDGrid.canSel = 0;
      UWHealthDGrid.canChk = 0;
      UWHealthDGrid.hiddenPlus = 1;
      UWHealthDGrid.hiddenSubtraction = 1;
      UWHealthDGrid.loadMulLine(iArray);       
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�ѷ�����
function initUWExistDGrid()
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
      iArray[1][0]="��������";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="¼����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="¼������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ն���";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��ӡ״̬";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�ظ�״̬";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWExistDGrid = new MulLineEnter( "fm" , "UWExistDGrid" ); 
      //��Щ���Ա�����loadMulLineǰ 
      UWExistDGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWExistDGrid.canSel = 0;
      UWExistDGrid.canChk = 0;
      UWExistDGrid.hiddenPlus = 1;
      UWExistDGrid.hiddenSubtraction = 1;
      UWExistDGrid.loadMulLine(iArray);       
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

//���������
function initQuestGrid()
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
      iArray[1][0]="Ͷ������";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         			//����
      iArray[2][1]="40px";            		//�п�
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
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
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
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=50;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[7][10] = "OperateLocation";
      //iArray[7][11] = "0|^0|�µ�¼��/�����޸�/�����޸�^1|�˹��˱�^5|�µ�����";
      //iArray[7][12] = "7";
      //iArray[7][13] = "1";

      iArray[8]=new Array();
      iArray[8][0]="���ض���";         			//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][10] = "ReturnToObject";
      iArray[8][11] = "0|^1|����Ա^2|ҵ��Ա^3|����^4|����";
      iArray[8][12] = "8";
      iArray[8][13] = "1";
      
      iArray[9]=new Array();
      iArray[9][0]="�Ƿ���Ҫ��ӡ";         			//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=50;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][10] = "NeedPrint";
      iArray[9][11] = "0|^Y|��Ҫ��ӡ^N|����Ҫ��ӡ";
//      iArray[9][12] = "0|1";
//      iArray[9][13] = "1";   
      
      iArray[10]=new Array();
      iArray[10][0]="��ˮ��";         			//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������     
      
      iArray[11]=new Array();
      iArray[11][0]="���Ͷ���";         			//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=50;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������     
      
      iArray[12]=new Array();
      iArray[12][0]="���ն�������";         			//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=50;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������     

      iArray[13]=new Array();
      iArray[13][0]="���ն���";         			//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=50;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[14]=new Array();
      iArray[14][0]="�·����";         			//����
      iArray[14][1]="50px";            		//�п�
      iArray[14][2]=50;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
   
      QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      QuestGrid.displayTitle = 1;
      QuestGrid.canSel = 0;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      //QuestGrid.selBoxEventFuncName = "queryone";
      QuestGrid.loadMulLine(iArray);      
      }
      catch(ex)
      {
        alert(ex);
      }
}

//������Լ
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
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="¼����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="¼������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[4]=new Array();
      iArray[4][0]="���Ͷ���";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ն���";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�·����";         		//����
      iArray[6][1]="30px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�����˱��";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWSpecGrid = new MulLineEnter( "fm" , "UWSpecGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWSpecGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecGrid.canSel = 0;
      UWSpecGrid.canChk = 0;
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

//�������
function initUWHealthGrid()
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
      iArray[1][0]="�����Ŀ";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="¼����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="¼������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[4]=new Array();
      iArray[4][0]="���Ͷ���";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ն���";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�·����";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��ӡ��ˮ��";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWHealthGrid = new MulLineEnter( "fm" , "UWHealthGrid" ); 
      //��Щ���Ա�����loadMulLineǰ  
      UWHealthGrid.displayTitle = 1;
      UWHealthGrid.canSel = 0;
      UWHealthGrid.canChk = 0;
      UWHealthGrid.hiddenPlus = 1;
      UWHealthGrid.hiddenSubtraction = 1;
      UWHealthGrid.loadMulLine(iArray);       
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

//��������
function initUWExistGrid()
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
      iArray[1][0]="��������";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="¼����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="¼������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ն���";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�·����";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWExistGrid = new MulLineEnter( "fm" , "UWExistGrid" ); 
      //��Щ���Ա�����loadMulLineǰ 
      UWExistGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWExistGrid.canSel = 0;
      UWExistGrid.canChk = 0;
      UWExistGrid.hiddenPlus = 1;
      UWExistGrid.hiddenSubtraction = 1;
      UWExistGrid.loadMulLine(iArray);       
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�����ӷ�
function initAddFeeGrid()
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
      iArray[1][0]="���ִ���";         		//����
      iArray[1][1]="30px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ӷ�����";         		//����
      iArray[3][1]="30px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���Ѽƻ�����";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[5]=new Array();
      iArray[5][0]="�ӷѽ��";         		//����
      iArray[5][1]="30px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="EMֵ";         		//����
      iArray[7][1]="30px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="¼����";         		//����
      iArray[8][1]="30px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="¼������";         		//����
      iArray[9][1]="30px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[10]=new Array();
      iArray[10][0]="���Ͷ���";         		//����
      iArray[10][1]="0px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="���ն���";         		//����
      iArray[11][1]="30px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�·����";         		//����
      iArray[12][1]="30px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="�����˱��";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   


      UWAddFeeGrid = new MulLineEnter( "fm" , "UWAddFeeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ  
      UWAddFeeGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWAddFeeGrid.canSel = 0;
      UWAddFeeGrid.canChk = 0;
      UWAddFeeGrid.hiddenPlus = 1;
      UWAddFeeGrid.hiddenSubtraction = 1;
      UWAddFeeGrid.loadMulLine(iArray);       
      
     
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�����а��ƻ����
function initUWPlanGrid()
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
      iArray[1][0]="���ֱ���";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�����ڼ�";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�����ڼ�";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[8]=new Array();
      iArray[8][0]="����Ƶ��";         		//����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   

      iArray[9]=new Array();
      iArray[9][0]="¼����";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="¼������";         		//����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[11]=new Array();
      iArray[11][0]="���Ͷ���";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="���ն���";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="�·����";         		//����
      iArray[13][1]="60px";            		//�п�
      iArray[13][2]=100;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      UWPlanGrid = new MulLineEnter( "fm" , "UWPlanGrid" ); 
      //��Щ���Ա�����loadMulLineǰ  
      UWPlanGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWPlanGrid.canSel = 0;
      UWPlanGrid.canChk = 0;
      UWPlanGrid.hiddenPlus = 1;
      UWPlanGrid.hiddenSubtraction = 1;
      UWPlanGrid.loadMulLine(iArray);       
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


