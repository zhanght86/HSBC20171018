<%
//���ƣ�LLInqOtherQueryInit.jsp
//���ܣ��鿴����������Ϣ�����̡�����������ۡ����̡��������ۣ�
//���ڣ�2005-10-6
//���¼�¼��  ������:     ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
function initParam()
{	
	var sysdatearr=easyExecSql("select to_date(sysdate) from dual"); //ȡ��������ʱ����Ϊϵͳ��ǰ����
	mCurrentDate=sysdatearr[0][0];
	
    document.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	  
    document.all('tClmNo').value =nullToEmpty("<%=tClmNo%>"); //�ⰸ��
    document.all('tBatNo').value =nullToEmpty("<%=tBatNo%>"); //���κ�
    document.all('tInqNo').value =nullToEmpty("<%=tInqNo%>"); //�������
    document.all('tInqDept').value =nullToEmpty("<%=tInqDept%>"); //�������
    document.all('tType').value =nullToEmpty("<%=tType%>"); //��־
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


//��ʼ��ҳ��
function initForm()
{
	try
	{
		initLLInqConclusionGrid(); // ������۱�ĳ�ʼ��
		initLLInqApplyGrid(); //���������
		initLLInqCourseGrid(); //���������Ϣ
		initParam();
		queryInqConclusion();
	}
	catch(re)
	{
		alter("��LLInqCourseInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

// ������۱�ĳ�ʼ��
function initLLInqConclusionGrid()
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
	      iArray[1][1]="160px";                //�п�
	      iArray[1][2]=160;                  //�����ֵ
	      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[2]=new Array();
	      iArray[2][0]="�������";             //����
	      iArray[2][1]="80px";                //�п�
	      iArray[2][2]=100;                  //�����ֵ
	      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[3]=new Array();
	      iArray[3][0]="��������";             //����
	      iArray[3][1]="80px";                //�п�
	      iArray[3][2]=100;                  //�����ֵ
	      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������  
	
	      iArray[4]=new Array();
	      iArray[4][0]="�������";             //����
	      iArray[4][1]="80px";                //�п�
	      iArray[4][2]=100;                  //�����ֵ
	      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[5]=new Array();
	      iArray[5][0]="�������";             //����
	      iArray[5][1]="80px";                //�п�
	      iArray[5][2]=200;                  //�����ֵ
	      iArray[5][3]=0; 
	
	      iArray[6]=new Array();
	      iArray[6][0]="��ɱ�־";             //����
	      iArray[6][1]="50px";                //�п�
	      iArray[6][2]=200;                  //�����ֵ
	      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[7]=new Array();
	      iArray[7][0]="���ر�־";             //����
	      iArray[7][1]="50px";                //�п�
	      iArray[7][2]=100;                  //�����ֵ
	      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[8]=new Array();
	      iArray[8][0]="���ܱ�־";             //����
	      iArray[8][1]="100px";                //�п�
	      iArray[8][2]=100;                  //�����ֵ
	      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[9]=new Array();
	      iArray[9][0]="���Խ���";             //����
	      iArray[9][1]="80px";                //�п�
	      iArray[9][2]=100;                  //�����ֵ
	      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[10]=new Array();
	      iArray[10][0]="�������";             //����
	      iArray[10][1]="0px";                //�п�
	      iArray[10][2]=100;                  //�����ֵ
	      iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[11]=new Array();
	      iArray[11][0]="��ע";             //����
	      iArray[11][1]="0px";                //�п�
	      iArray[11][2]=100;                  //�����ֵ
	      iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
      
	      LLInqConclusionGrid = new MulLineEnter( "document" , "LLInqConclusionGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      LLInqConclusionGrid.mulLineCount = 5;   
	      LLInqConclusionGrid.displayTitle = 1;
	      LLInqConclusionGrid.locked = 1;
	      LLInqConclusionGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	      LLInqConclusionGrid.selBoxEventFuncName = "LLInqConclusionGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
	      LLInqConclusionGrid.hiddenPlus=1;
	      LLInqConclusionGrid.hiddenSubtraction=1;
	      LLInqConclusionGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}

//����������ʼ��
function initLLInqApplyGrid()
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
	      iArray[1][1]="160px";                //�п�
	      iArray[1][2]=160;                  //�����ֵ
	      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[2]=new Array();
	      iArray[2][0]="�������";             //����
	      iArray[2][1]="100px";                //�п�
	      iArray[2][2]=100;                  //�����ֵ
	      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[3]=new Array();
	      iArray[3][0]="��������";             //����
	      iArray[3][1]="80px";                //�п�
	      iArray[3][2]=100;                  //�����ֵ
	      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[4]=new Array();
	      iArray[4][0]="�����˿ͻ���";             //����
	      iArray[4][1]="100px";                //�п�
	      iArray[4][2]=100;                  //�����ֵ
	      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[5]=new Array();
	      iArray[5][0]="����������";             //����
	      iArray[5][1]="100px";                //�п�
	      iArray[5][2]=200;                  //�����ֵ
	      iArray[5][3]=0; 
	
	      iArray[6]=new Array();
	      iArray[6][0]="VIP";             //����
	      iArray[6][1]="30px";                //�п�
	      iArray[6][2]=200;                  //�����ֵ
	      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[7]=new Array();
	      iArray[7][0]="����׶�";             //����
	      iArray[7][1]="80px";                //�п�
	      iArray[7][2]=100;                  //�����ֵ
	      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[8]=new Array();
	      iArray[8][0]="������";             //����
	      iArray[8][1]="80px";                //�п�
	      iArray[8][2]=100;                  //�����ֵ
	      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	
	      iArray[9]=new Array();
	      iArray[9][0]="��������";             //����
	      iArray[9][1]="80px";                //�п�
	      iArray[9][2]=100;                  //�����ֵ
	      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
	      
	      iArray[10]=new Array();
	      iArray[10][0]="���ر�־";             //����
	      iArray[10][1]="60px";                //�п�
	      iArray[10][2]=100;                  //�����ֵ
	      iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
	      
	      iArray[11]=new Array();
	      iArray[11][0]="����״̬";             //����
	      iArray[11][1]="60px";                //�п�
	      iArray[11][2]=100;                  //�����ֵ
	      iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������       
	      
	      LLInqApplyGrid = new MulLineEnter( "document" , "LLInqApplyGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      LLInqApplyGrid.mulLineCount = 5;   
	      LLInqApplyGrid.displayTitle = 1;
	      LLInqApplyGrid.locked = 1;
	      LLInqApplyGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	      LLInqApplyGrid.selBoxEventFuncName = "LLInqApplyGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����  
	      LLInqApplyGrid.hiddenPlus=1;
	      LLInqApplyGrid.hiddenSubtraction=1;
	      LLInqApplyGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

//������̱��ʼ��
function initLLInqCourseGrid()
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
      iArray[1][1]="160px";                //�п�
      iArray[1][2]=160;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";             //����
      iArray[2][1]="100px";                //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�������";             //����
      iArray[3][1]="100px";                //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����ص�";             //����
      iArray[4][1]="80px";                //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���鷽ʽ";             //����
      iArray[5][1]="100px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="��������";             //����
      iArray[6][1]="100px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��һ������";             //����
      iArray[7][1]="100px";                //�п�
      iArray[7][2]=100;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�ڶ�������";             //����
      iArray[8][1]="100px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�������";             //����
      iArray[9][1]="80px";                //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��������";             //����
      iArray[10][1]="100px";                //�п�
      iArray[10][2]=100;                  //�����ֵ
      iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="�������";             //����
      iArray[11][1]="100px";                //�п�
      iArray[11][2]=100;                  //�����ֵ
      iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[12]=new Array();
      iArray[12][0]="��ע";             //����
      iArray[12][1]="100px";                //�п�
      iArray[12][2]=100;                  //�����ֵ
      iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      

      LLInqCourseGrid = new MulLineEnter( "document" , "LLInqCourseGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLInqCourseGrid.mulLineCount = 5;   
      LLInqCourseGrid.displayTitle = 1;
      LLInqCourseGrid.locked = 1;
      LLInqCourseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLInqCourseGrid.selBoxEventFuncName = "LLInqCourseGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����  
      LLInqCourseGrid.hiddenPlus=1;
      LLInqCourseGrid.hiddenSubtraction=1;
      LLInqCourseGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

 </script>
