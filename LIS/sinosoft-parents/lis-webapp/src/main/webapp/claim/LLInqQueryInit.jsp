<%
//�������ƣ�LLInqQueryInit.jsp
//�����ܣ�������Ϣ��ѯҳ���ʼ��
//�������ڣ�2005-05-10
//������  ��zhoulei
//���¼�¼��
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
//	  document.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	  
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
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

//�ⰸ������ϸ��Ϣ  ��ʼ�� by niuzj 20050829
function initLLInqPayclusionForm()
{ 
    try
    {                                   
        fm.ClmNo1.value ="";
        fm.ConNo1.value = "";
        fm.InqDept1.value ="";
        fm.InqConclusion1.value = "";
        fm.Remark1.value = "";
    }
    catch(ex)
    {
        alert("��LLInqQueryInit.jsp-->InitLLInqPayclusionForm:��ʼ���������!");
    }      
}

//���������ϸ��Ϣ  ��ʼ��
function initLLInqConclusionForm()
{ 
    try
    {                                   
        fm.ClmNo2.value ="";
        fm.ConNo.value = "";
        fm.InqDept.value ="";
        fm.InqConclusion.value = "";
        fm.Remark.value = "";
    }
    catch(ex)
    {
        alert("��LLInqQueryInit.jsp-->InitLLInqConclusionForm�����з����쳣:��ʼ���������!");
    }      
}
// ����������ϸ��Ϣ  ��ʼ��
function initLLInqApplyForm()
{  
    try                 
    {
        fm.ClmNo3.value ="";
        fm.InqNo.value = "";
        fm.customerNo.value ="";
        fm.InqItem.value ="";
        fm.InqDesc.value = ""; 
    }
    catch(ex)
    {
        alert("��LLInqQueryInit.jsp-->InitLLInqApplyForm�����з����쳣:��ʼ���������!");
    }
}                                        

// ���������ϸ��Ϣ   ��ʼ��
function initLLInqCourseForm()
{  
    try                 
    {
        fm.ClmNo4.value ="";
        fm.InqNo2.value = "";
        fm.CouNo.value = "";
        fm.InqSite.value ="";
        fm.InqCourse.value =""; 
    }
    catch(ex)
    {
        alert("��LLInqQueryInit.jsp-->InitLLInqCourseForm�����з����쳣:��ʼ���������!");
    }
}   

function initForm()
{
    try
    {

        initLLInqPayclusionGrid();
        initLLInqPayclusionForm();
        document.all('DivLLInqPayclusionGrid').style.display=""; //�ⰸuzj 20050829
		document.all('DivLLInqPayclusionForm').style.display=""; //�ⰸuzj 20050829
		
        initLLInqConclusionGrid();
        initLLInqConclusionForm();
        document.all('DivLLInqConclusionGrid').style.display=""; //���������Ϣ
		document.all('DivLLInqConclusionForm').style.display="none"; //���������ϸ��Ϣ
		
        initLLInqApplyGrid();
        initLLInqApplyForm();
        document.all('DivLLInqApplyGrid').style.display="none"; //����������Ϣ
		document.all('DivLLInqApplyForm').style.display="none"; //����������ϸ��Ϣ
        
//        initLLInqCourseGrid();
//        initLLInqCourseForm();
//		  document.all('DivLLInqCourseGrid').style.display="none"; //���������Ϣ
//		  document.all('DivLLInqCourseForm').style.display="none"; //���������ϸ��Ϣ   
     
		initParam();        	
		
//		var strSQL="select * from llinqconclusion  where 1=1 "
//			+" and clmno='"+fm.ClmNo.value+"'"
//			+" and finiflag='1' and colflag='0' " 
//		var arr = easyExecSql(strSQL);
//    	if (arr == null)
//    	{
//    		document.all('DivLLInqPayclusionGrid').style.display="none"; //�ⰸ
//			document.all('DivLLInqPayclusionForm').style.display="none"; //�ⰸ
//    	}
		
		queryLLInqPayclusionGrid();
		queryLLInqConclusionGrid();
		QueryInqPAConclusionClick();
    }
    catch(re)
    {
        alert("LLInqQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

// �ⰸ���۱�ĳ�ʼ�� by niuzj 20050829
function initLLInqPayclusionGrid()
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
      iArray[4][1]="0px";                //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";             //����
      iArray[5][1]="80px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="�������";             //����
      iArray[6][1]="0px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��ɱ�־";             //����
      iArray[7][1]="50px";                //�п�
      iArray[7][2]=100;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���ر�־";             //����
      iArray[8][1]="50px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="���ܱ�־";             //����
      iArray[9][1]="0px";                //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="���Խ���";             //����
      iArray[10][1]="50px";                //�п�
      iArray[10][2]=100;                  //�����ֵ
      iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="��ע";             //����
      iArray[11][1]="0px";                //�п�
      iArray[11][2]=100;                  //�����ֵ
      iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
      
//      iArray[12]=new Array();
//      iArray[12][0]="���ܱ�־";             //����
//      iArray[12][1]="0px";                //�п�
//      iArray[12][2]=100;                  //�����ֵ
//      iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      

      LLInqPayclusionGrid = new MulLineEnter( "document" , "LLInqPayclusionGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLInqPayclusionGrid.mulLineCount = 5;   
      LLInqPayclusionGrid.displayTitle = 1;
      LLInqPayclusionGrid.locked = 1;
      LLInqPayclusionGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLInqPayclusionGrid.selBoxEventFuncName = "LLInqPayclusionGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
      LLInqPayclusionGrid.hiddenPlus=1;
      LLInqPayclusionGrid.hiddenSubtraction=1;
      LLInqPayclusionGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
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
      iArray[4][1]="0px";                //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";             //����
      iArray[5][1]="80px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 


      iArray[6]=new Array();
      iArray[6][0]="�������";             //����
      iArray[6][1]="0px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��ɱ�־";             //����
      iArray[7][1]="50px";                //�п�
      iArray[7][2]=100;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���ر�־";             //����
      iArray[8][1]="50px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="���ܱ�־";             //����
      iArray[9][1]="0px";                //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="���Խ���";             //����
      iArray[10][1]="50px";                //�п�
      iArray[10][2]=100;                  //�����ֵ
      iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="��ע";             //����
      iArray[11][1]="0px";                //�п�
      iArray[11][2]=100;                  //�����ֵ
      iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
      
//      iArray[12]=new Array();
//      iArray[12][0]="���ܱ�־";             //����
//      iArray[12][1]="0px";                //�п�
//      iArray[12][2]=100;                  //�����ֵ
//      iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������      

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
      iArray[4][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����������";             //����
      iArray[5][1]="100px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 


      iArray[6]=new Array();
      iArray[6][0]="VIP";             //����
      iArray[6][1]="30px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="����׶�";             //����
      iArray[7][1]="80px";                //�п�
      iArray[7][2]=100;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����ԭ��";             //����
      iArray[8][1]="100px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="������Ŀ";             //����
      iArray[9][1]="100px";                //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��������";             //����
      iArray[10][1]="0px";                //�п�
      iArray[10][2]=100;                  //�����ֵ
      iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������ 

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
