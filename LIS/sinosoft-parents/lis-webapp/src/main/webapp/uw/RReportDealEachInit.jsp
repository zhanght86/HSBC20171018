<%
//�������ƣ�PolQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>



<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
  String tContNo = "";
 
  String tMissionID = "";
  String tSubMissionID = "";
  String tCustomerNo = "";
  String tPrtSeq = "";
 
 
  tContNo = request.getParameter("ContNo");  
  tCustomerNo =request.getParameter("CustomerNo");
  
  tMissionID = request.getParameter("MissionID");  
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtSeq = request.getParameter("PrtSeq");
 
  
%>                            

 <SCRIPT src="../common/javascript/Common.js"></SCRIPT>                  
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("��PolQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��PolQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm(tContNo,tMissionID,tSubMissionID,tCustomerNo,tPrtSeq)
{

  try
  {

    initInpBox();
    initInvestigateGrid();
    initQuestionTypeGrid();
    initSelBox();    
    initHidden(tContNo,tMissionID,tSubMissionID,tCustomerNo,tPrtSeq);  
    returnParent();
    easyQueryClick();
   // initLWMission();
  
  
  }
  catch(re)
  {
    alert("PolQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.message);
  }
}
function initHidden(tContNo,tMissionID,tSubMissionID,tCustomerNo,tPrtSeq)
{
	fm.ContNo.value = tContNo;
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;

	fm.CustomerNo.value = tCustomerNo;
	fm.PrtSeq.value = tPrtSeq;
	


}

function initInvestigateGrid()
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
      iArray[1][0]="������Ŀ���";    	//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4] = "RReportCode1";             			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][5]="1|2";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";    //��������з������ô����еڼ�λֵ
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="������Ŀ����";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��ע";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      InvestigateGrid = new MulLineEnter( "fm" , "InvestigateGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      InvestigateGrid.mulLineCount = 0;
      InvestigateGrid.displayTitle = 1;
      InvestigateGrid.canChk = 0;
      InvestigateGrid.hiddenPlus = 1;
      InvestigateGrid.hiddenSubtraction = 1;
      InvestigateGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
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

</script>

      
