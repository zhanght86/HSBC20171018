<%
//Name:LLRgtAddMAffixListInit.jsp
//Function����֤��������ʼ������
//Date��2005-07-1 17:44:28
//Author ��yuejw
%>
<!--�û�У����-->
<%
//
//  String CaseNo= request.getParameter("tClmNo");
//  String whoNo= request.getParameter("tCustomerNo");
//  String Proc=request.getParameter("tProc");
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
var turnPage = new turnPageClass();
//������ҳ��������� 
function initParam()
{
	fm.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
//	alert("fm.all('ClmNo').value"+fm.all('ClmNo').value);
    fm.all('Proc').value =nullToEmpty("<%=tProc%>");
//    alert("fm.all('Proc').value"+fm.all('Proc').value);
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
function initForm()
{
    try
    {
    	initParam()
    	initInpBox();
    	initAffixGrid();
	    initQueryAffixGrid();    	
    }
    catch(re)
    {
        alert("LLRgtAddMAffixListInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}


function initInpBox()
{
    try
    {    
    	//�ж��Ƿ���Ҫ���ɵ�֤----��ѯ����е�֤�����������ɣ������������ɵ�֤
         var strSql="select * from llaffix where rgtno='"+fm.ClmNo.value+"' ";
//         alert(strSql);
         var arr=easyExecSql(strSql);
        if(arr==null)
        {
            fm.build.disabled=false;
        }
	    else
    	{
    		fm.build.disabled=true;
    	}	
    }
    catch(re)
    {
        alert("LLMAffixListInit.jsp-->initInpBox�����з����쳣:��ʼ���������!");
    }
}

//���赥֤��Ϣ
function initAffixGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=0;
        
        iArray[1]=new Array();
        iArray[1][0]="��֤����";
        iArray[1][1]="30px";
        iArray[1][2]=10;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="��֤����";
        iArray[2][1]="200px";
        iArray[2][2]=300;
        iArray[2][3]=0;
      
        iArray[3]=new Array();
        iArray[3][0]="�Ƿ����ύ";
        iArray[3][1]="0px";
        iArray[3][2]=10;
        iArray[3][3]=3;        
//        iArray[3][10]="SubFlag";
//        iArray[3][11]="0|^0|��^1|��"; 
                
        iArray[4]=new Array();
        iArray[4][0]="�Ƿ����";
        iArray[4][1]="30px";
        iArray[4][2]=10;
        iArray[4][3]=2;   
        iArray[4][10]="NeedFlag";
        iArray[4][11]="0|^0|��^1|��"; 
        
        iArray[5]=new Array();
        iArray[5][0]="��֤����";
        iArray[5][1]="30px";
        iArray[5][2]=10;
        iArray[5][3]=1;    
                
        iArray[6]=new Array();
        iArray[6][0]="�ύ��ʽ";
        iArray[6][1]="30px";
        iArray[6][2]=10;
        iArray[6][3]=2;  
        iArray[6][10]="Property";        
        iArray[6][11]="0|^0|ԭ��^1|��ӡ��"; 
        
        iArray[7]=new Array();
        iArray[7][0]="ȱ�ټ���";
        iArray[7][1]="0px";
        iArray[7][2]=10;
        iArray[7][3]=3;  
                
        iArray[8]=new Array();
        iArray[8][0]="�Ƿ��˻�ԭ��";
        iArray[8][1]="0px";
        iArray[8][2]=50;
        iArray[8][3]=3; 
//        iArray[8][10]="ReturnFlag";
//        iArray[8][11]="0|^0|��^1|��"; 

        iArray[9]=new Array();
        iArray[9][0]="��������";
        iArray[9][1]="0px";
        iArray[9][2]=10;
        iArray[9][3]=3; 

        AffixGrid = new MulLineEnter("fm","AffixGrid");
        AffixGrid.mulLineCount = 0;
        AffixGrid.displayTitle = 1;
        AffixGrid.locked = 0;
        AffixGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
        AffixGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
        AffixGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
        AffixGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
        AffixGrid.loadMulLine(iArray);
        
    }
    catch(ex)
    {
        alter(ex);
    }
}

</script>
