<%
//�������ƣ�LLInqConclusionMissInit.jsp
//�����ܣ��������ҳ���ʼ��
//�������ڣ�2005-06-27
//������  ��yuejw
//���¼�¼��
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            
<script language="JavaScript">
//���ղ���
function initParam()
{
    fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
        initInpBox();
        initSelBox();  
        initParam();
        initLLInqConclusionGrid();  
        LLInqConclusionGridQuery();
    }
    catch(re)
    {
        alert("LLInqConclusionInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//����������ʼ��
function initLLInqConclusionGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                //�п�
      iArray[0][2]=8;                  //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ⰸ��";             //����
      iArray[1][1]="100px";                //�п�
      iArray[1][2]=80;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";             //����
      iArray[2][1]="100px";                //�п�
      iArray[2][2]=80;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";             //����
      iArray[3][1]="80px";                //�п�
      iArray[3][2]=80;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������";             //����
      iArray[4][1]="80px";                //�п�
      iArray[4][2]=80;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";             //����
      iArray[5][1]="80px";                //�п�
      iArray[5][2]=80;                  //�����ֵ
      iArray[5][3]=0; 
              
    iArray[6]=new Array();
    iArray[6][0]="Missionid";             //����
    iArray[6][1]="0px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
  
    iArray[7]=new Array();
    iArray[7][0]="submissionid";             //����
    iArray[7][1]="0px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������             
                
    iArray[8]=new Array();
    iArray[8][0]="activityid";             //����
    iArray[8][1]="0px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������     

      LLInqConclusionGrid = new MulLineEnter( "fm" , "LLInqConclusionGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLInqConclusionGrid.mulLineCount = 0;   
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

</script>
