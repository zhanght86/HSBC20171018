<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ScoreRReportQueryInit.jsp
//�����ܣ��������ֻ���
//�������ڣ�2008-10-27 11:10:36
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String curDate = PubFun.getCurrentDate();
	//loggerDebug("ScoreRReportQueryInit",curDate);
%> 
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {       
    fm.Button1.disabled = true; 
    fm.Button2.disabled = true;  
    fm.Button3.disabled = true;
                                
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AssessOperator').value = '';  
    document.all('CustomerNo').value = '';  
    document.all('Name').value = '';
    document.all('StartDate').value = '<%=curDate%>';
    document.all('EndDate').value = '<%=curDate%>';
 
  }
  catch(ex)
  {
    alert("ScoreRReportQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
} 

// ���������ĳ�ʼ��
function initHide()
{ 
try
  {                                   
    document.all('ContNoH').value = '';   
    document.all('PrtNoH').value = '';
  }
  catch(ex)
  {
    alert("ScoreRReportQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}                                     

function initForm()
{
  try
  {
    
	initInpBox();
	initHide();	
	initScoreRReportGrid();
		
  }
  catch(re)
  {
    alert("ScoreRReportQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//��ѯͬһ��������δ�б�Ͷ����
function initScoreRReportGrid()
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
      iArray[1][0]="��ͬ��";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="0px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ӡˢ��";    	//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=130;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�������";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����Ա����";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����Ա����";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=50;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�˱�ʦ����";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�÷�";         			//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
             
      
      ScoreRReportGrid = new MulLineEnter( "fm" , "ScoreRReportGrid" ); 
      //��Щ���Ա�����loadMulLineǰ                            
      ScoreRReportGrid.mulLineCount = 0;   
      ScoreRReportGrid.displayTitle = 1;
      ScoreRReportGrid.locked = 1;
      ScoreRReportGrid.canSel = 1;
      ScoreRReportGrid.hiddenPlus = 1;
      ScoreRReportGrid.hiddenSubtraction = 1;
      ScoreRReportGrid.loadMulLine(iArray);  
      ScoreRReportGrid.selBoxEventFuncName = "initButtonQuery";

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


