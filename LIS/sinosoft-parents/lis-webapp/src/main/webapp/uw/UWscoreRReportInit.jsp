<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//�������ƣ�UWscoreRReportInit.jsp
//�����ܣ��˱���������
//������  ��ln
//�������ڣ�2008-10-24
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String curDate = PubFun.getCurrentDate();
    String curTime = PubFun.getCurrentTime();
	//loggerDebug("UWscoreRReportInit",curDate);
%>
<script language="JavaScript">


// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
try
  {
  	//��ʼ����ť״̬      
    if(tType =="2") 
    {
     // alert(tType);
      fm.Sure.disabled = true;
     // alert(2);
      //fm.Print.disabled = true;
     // alert(1);
    }    
                               
    document.all('ManageCom').value = '';
    document.all('CustomerNo').value = '';
    document.all('Name').value = '';
      
    document.all('SScore').value = "0";
    document.all('SScore1').value = "2";
    document.all('SScore2').value = "20";
    document.all('SScore3').value = "10";
    document.all('SScore4').value = "5";
    document.all('SScore5').value = "2";
    document.all('SScore6').value = "2";
    document.all('SScore7').value = "2";
    document.all('SScore8').value = "";
    document.all('AScore').value = "0";
    document.all('AScore1').value = "10";
    document.all('AScore2').value = "5";
    document.all('AScore3').value = "5";
    document.all('AScore4').value = "5";
    document.all('AScore5').value = "5";
    document.all('AScore6').value = "";
    document.all('Score').value = "100";
    document.all('Conclusion').value = '����';
    document.all('AssessOperator').value = operator;
    document.all('AssessDay').value = '<%=curDate%>';
    <%String unitbase = "unit";
    String unit = "";
    for(int i=1; i<15; i++)
    {
    	unit = unitbase + String.valueOf(i);
    //	loggerDebug("UWscoreRReportInit",unit);
    	%>
      document.all('<%=unit%>').value = "��";
      <%
    }
    %>
  }
  catch(ex)
  {
    alert("��UWscoreRReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}   

function initHide()
{
	try
  {
	    document.all('ScoreH').value = '100';
	    document.all('SScoreH').value = '0';
	    document.all('AScoreH').value = '0';	    
	    document.all('AssessTimeH').value = '<%=curTime%>';
	    document.all('ContNoH').value = tContNo;
  }
  catch(re)
  {
    alert("UWscoreRReportInit.jsp-->initHide�����з����쳣:��ʼ���������!");
  }  
}                                   

function initForm()
{
  try
  {
	initInpBox();
	initHide();
	easyQueryClickItem();	
  }
  catch(re)
  {
    alert("UWscoreRReportInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//����÷�
function calcuScore()
{	
    var tScore =0;
 
	  tScore = 100 - parseInt(document.all('SScore').value) + parseInt(document.all('AScore').value);
	  document.all('Score').value = tScore;
	  
	  //��������
	  if(tScore >= 90)
	     document.all('Conclusion').value = "����";
	  else if(90> tScore && tScore >= 80)
	     document.all('Conclusion').value = "����";
	  else if(80> tScore && tScore >= 60)
	     document.all('Conclusion').value = "�ϸ�";
	  else 
	     document.all('Conclusion').value = "���ϸ�";
} 

//����۷�
function calcuSScore()
{	
      var tSScore = 0;
	  <%
	  
	  String Subtraction = "";
	  String SScore = "";
	  
	    for(int i=1; i<9; i++)
	    {
	    	Subtraction = "Subtraction" + String.valueOf(i);
	    	SScore = "SScore" + String.valueOf(i);
	    	%>
	    	if(document.all('<%=Subtraction%>').checked)
	    	   tSScore = tSScore + parseInt(document.all('<%=SScore%>').value) ;  
	    	
	    <%}
	    %>
	    document.all('SScore').value = tSScore;
}

//����ӷ�
function calcuAScore()
{	   
      var tAScore = 0;	
	  <%
	  
	  String Addition = "";
	  String AScore = "";
	    for(int i=1; i<7; i++)
	    {
	    	Addition = "Addition" + String.valueOf(i);
	    	AScore = "AScore" + String.valueOf(i);
	    	%>
	    	if(document.all('<%=Addition%>').checked)
	      		tAScore = tAScore + parseInt(document.all('<%=AScore%>').value) ;
	      <%
	      
	    }
	    %>
	    document.all('AScore').value = tAScore;

}

//����У���õĵ÷�
function calcuScoreH()
{	
    var tScoreH =0;
    var tSScoreH = 0;
    var tAScoreH = 0;

//����۷�
	  <%
	  String SubtractionH = "";
	  String SScoreH = "";
	    for(int i=1; i<9; i++)
	    {
	    	SubtractionH = "Subtraction" + String.valueOf(i);
	    	SScoreH = "SScore" + String.valueOf(i);
	    	%>
	    	if(document.all('<%=SubtractionH%>').checked)
	      		tSScoreH = tSScoreH + parseInt(document.all('<%=SScoreH%>').value) ;
	      <%
	    }
	    %>
	    document.all('SScoreH').value = tSScoreH;
//����ӷ�
      <%
      String AdditionH = "";
	  String AScoreH = "";
	    for(int i=1; i<7; i++)
	    {
	    	AdditionH = "Addition" + String.valueOf(i);
	    	AScoreH = "AScore" + String.valueOf(i);
	    	%>
	    	if(document.all('<%=AdditionH%>').checked)
	     		 tAScoreH = tAScoreH + parseInt(document.all('<%=AScoreH%>').value) ;
	      <%
	    }
	    %>
	    document.all('AScoreH').value = tAScoreH;
	    
//����÷�
		  tScoreH = 100 - parseInt(document.all('SScoreH').value) + parseInt(document.all('AScoreH').value);
		  document.all('ScoreH').value = tScoreH;
} 

</script>


