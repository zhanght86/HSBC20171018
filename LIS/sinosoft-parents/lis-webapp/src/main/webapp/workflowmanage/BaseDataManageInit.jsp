<%
/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

%>

<!--�û�У����-->

<script language="JavaScript">  

function initInpBox()
{

    try
    {
    }
    catch(ex)
    {
        alert("��InvAssOrderbuildDetailInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initTICollectGrid();
    }
    catch(re)
    {
        alert("InvAssOrderbuildDetailInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
} //�ɽ�����ܱ�
function initTICollectGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i] = new Array();
        iArray[i][0] = "���";
        iArray[i][1] = "30px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "CodeType";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "��������";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "����ֵ";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "��������";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        
        iArray[i] = new Array();
        iArray[i][0] = "�������";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        
        //add by liuyuxiao 2011-05-23 �˵��ڵ��У����ڻ�ȡ�˵��ڵ����ݣ�����ʾ
        iArray[i] = new Array();
        iArray[i][0] = "�˵��ڵ�";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        //end add by liuyuxiao 2011-05-23

        TICollectGrid = new MulLineEnter("fm", "TICollectGrid");
        //��Щ���Ա�����loadMulLineǰ
        TICollectGrid.mulLineCount = 5;
        TICollectGrid.displayTitle = 1;
        TICollectGrid.hiddenPlus = 1;
        TICollectGrid.hiddenSubtraction = 1;
        TICollectGrid.canSel = 1;
        TICollectGrid.selBoxEventFuncName = "showData";
        TICollectGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}



</script>
