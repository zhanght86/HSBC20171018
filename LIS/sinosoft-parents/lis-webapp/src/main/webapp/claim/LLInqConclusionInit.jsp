<%
//�������ƣ�LLInqConclusionInit.jsp
//�����ܣ��������ҳ���ʼ��
//�������ڣ�2005-05-30
//������  ��zhoulei
//���¼�¼��yuejw
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            
<script language="JavaScript">
function initParam()
{	
    document.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
    document.all('ConNo').value =nullToEmpty("<%=tConNo%>");
    document.all('Activityid').value =nullToEmpty("<%=tActivityid%>");
    document.all('MissionID').value =nullToEmpty("<%=tMissionID%>");
    document.all('SubMissionID').value =nullToEmpty("<%=tSubMissionID%>");
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
function initInpBox()
{ 
    try
    {                                   
    }
    catch(ex)
    {
        alert("��LLInqConclusionInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initSelBox()
{  
    try                 
    {
 
    }
    catch(ex)
    {
        alert("��LLInqConclusionInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}                                        

function initForm()
{
    try
    {
    	initLLInqApplyGrid();
//    	initLLInqCourseGrid();
        initParam();
        initializeQuery(); 
    }
    catch(re)
    {
        alert("LLInqConclusionInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[3][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

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
      iArray[8][0]="����ԭ��";             //����
      iArray[8][1]="100px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="������Ŀ";             //����
      iArray[9][1]="0px";                //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="�������";             //����
      iArray[10][1]="0px";                //�п�
      iArray[10][2]=100;                  //�����ֵ
      iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="��ʼ����";             //����
      iArray[11][1]="80px";                //�п�
      iArray[11][2]=100;                  //�����ֵ
      iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[12]=new Array();
      iArray[12][0]="��������";             //����
      iArray[12][1]="80px";                //�п�
      iArray[12][2]=100;                  //�����ֵ
      iArray[12][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������ 

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
      iArray[4][0]="��������";             //����
      iArray[4][1]="80px";                //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���鷽ʽ";             //����
      iArray[5][1]="0px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=3; 


      iArray[6]=new Array();
      iArray[6][0]="����ص�";             //����
      iArray[6][1]="100px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";             //����
      iArray[7][1]="0px";                //�п�
      iArray[7][2]=100;                  //�����ֵ
      iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�������";             //����
      iArray[8][1]="100px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�������";             //����
      iArray[9][1]="80px";                //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��һ������";             //����
      iArray[10][1]="100px";                //�п�
      iArray[10][2]=100;                  //�����ֵ
      iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="�ڶ�������";             //����
      iArray[11][1]="100px";                //�п�
      iArray[11][2]=100;                  //�����ֵ
      iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������      


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
