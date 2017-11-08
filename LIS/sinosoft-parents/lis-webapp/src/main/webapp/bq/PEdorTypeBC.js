/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-10-10, 2006-10-16, 2006-11-01, 2006-11-17
 * @direction: 保全受益人变更主脚本
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                      //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();                //全局变量, 查询结果翻页, 必须有
var turnPageCustomerGrid = new turnPageClass();    //全局变量, 保单客户查询结果翻页
var turnPageOldBnfGrid = new turnPageClass();      //全局变量, 原受益人查询结果翻页
var turnPageNewBnfGrid = new turnPageClass();      //全局变量, 新受益人查询结果翻页

/*============================================================================*/

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=300;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            top.opener.getEdorItemGrid();
            queryNewBnfGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 根据操作类型(录入或查询)决定操作按钮是否显示
 */
function EdorQuery()
{
    var sButtonFlag;
    try
    {
        sButtonFlag = top.opener.document.getElementsByName("ButtonFlag")[0].value;
    }
    catch (ex) {}
    if (sButtonFlag != null && trim(sButtonFlag) == "1")
    {
        try
        {
            document.all("divEdorQuery").style.display = "none";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("divEdorQuery").style.display = "";
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 查询保单险种信息
 */
function getCustomerType()
{
    var sCustomerType;
    var sAppObj;    //区分个单还是团体复用
    try
    {
        sAppObj = document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
    if (sAppObj != null && sAppObj == "G")
    {
        sCustomerType = "0|^I|被保险人";
    }
    else
    {
        sCustomerType = "0|^A|投保人^I|被保险人";
    }
    return sCustomerType;
}

/*============================================================================*/

/**
 * 查询保单险种信息
 */
function queryPolInfo()
{
    var QuerySQL, arrResult;
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");
    mySql.setSqlId("PEdorTypeBCSql1");
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.PolNo.value);
    try
    {
        arrResult = easyExecSql(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保单险种信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("RiskCode")[0].value = arrResult[0][0];
            document.getElementsByName("RiskName")[0].value = arrResult[0][1];
            document.getElementsByName("CValiDate")[0].value = arrResult[0][2];
            document.getElementsByName("PayToDate")[0].value = arrResult[0][3];
            document.getElementsByName("Prem")[0].value = arrResult[0][4];
        }
        catch (ex) {}
    } //arrResult != null
}

/*============================================================================*/

/**
 * 查询保单客户表格
 */
function queryCustomerGrid()
{
    var sAppObj;    //区分个单还是团体复用
    try
    {
        sAppObj = document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
    
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");  
    mySql.setSqlId("PEdorTypeBCSql5");
 
    if (sAppObj != null && sAppObj != "G")
    {
    	  mySql.setSqlId("PEdorTypeBCSql2"); 	  
        mySql.addSubPara(fm.ContNo.value);	
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);    	  
 
          
    }
    
        mySql.addSubPara(fm.ContNo.value);	
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);      
 
    //第二被保人
        mySql.addSubPara(fm.ContNo.value);	
        mySql.addSubPara(fm.PolNo.value);
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);
 
    try
    {
        turnPageCustomerGrid.pageDivName = "divTurnPageCustomerGrid";
        turnPageCustomerGrid.queryModal(mySql.getString(), CustomerGrid);
    }
    catch (ex)
    {
        alert("警告：查询保单客户信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 查询被保人下拉列表信息
 */
function getInsuredCodeList()
{
    var sInsuredCodeList = "";
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");
    mySql.setSqlId("PEdorTypeBCSql3");
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.PolNo.value);
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.PolNo.value);
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.PolNo.value);    
    
    try
    {
        sInsuredCodeList = easyQueryVer3(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保单被保人信息出现异常！ ");
        return;
    }
    return sInsuredCodeList;
}

/*============================================================================*/

/**
 * 查询原受益人信息
 */
function queryOldBnfGrid()
{
 
    try
    {
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeBC");
        mySql.setSqlId("PEdorTypeBCSql6");    	
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);
        turnPageOldBnfGrid.pageDivName = "divTurnPageOldBnfGrid";
        turnPageOldBnfGrid.queryModal(mySql.getString(), OldBnfGrid);
    }
    catch (ex)
    {
        alert("警告：查询原受益人信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 查询新受益人信息
 */
function queryNewBnfGrid()
{
 
    try
    {
    	  mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeBC");
        mySql.setSqlId("PEdorTypeBCSql7");    	
        mySql.addSubPara(fm.EdorNo.value);
        mySql.addSubPara(fm.EdorType.value);
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);
        turnPageNewBnfGrid.pageDivName = "divTurnPageNewBnfGrid";
        turnPageNewBnfGrid.queryModal(mySql.getString(), NewBnfGrid);
    }
    catch (ex)
    {
        alert("警告：查询新受益人信息出现异常！ ");
        return;
    }
}

/*============================================================================*/

/**
 * 拷贝原受益人信息到新受益人信息
 */
function copyTwoBnfGrid()
{
    try
    {
        NewBnfGrid.clearData();
    }
    catch (ex) {}
    var nOldBnfRowCount, nOldBnfColCount;
    try
    {
        nOldBnfRowCount = OldBnfGrid.mulLineCount;
        nOldBnfColCount = OldBnfGrid.colCount;
    }
    catch (ex) {}
    if (nOldBnfRowCount != null || nOldBnfRowCount > 0)
    {
        try
        {
            for (var i = 0 ; i < nOldBnfRowCount; i++)
            {
                //Row 从 0 开始; Col 从 1 开始
                NewBnfGrid.addOne();
                for (var k = 1; k < nOldBnfColCount; k++)
                {
                    NewBnfGrid.setRowColData(i, k, OldBnfGrid.getRowColData(i, k))
                }
            }
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * 根据当前状态分情况处理
 */
function checkEdorState()
{
    var QuerySQL, arrResult;
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");
    mySql.setSqlId("PEdorTypeBCSql4");
    mySql.addSubPara(fm.EdorAcceptNo.value);	
    mySql.addSubPara(fm.EdorNo.value);
    mySql.addSubPara(fm.EdorType.value);
    mySql.addSubPara(fm.ContNo.value);
    mySql.addSubPara(fm.PolNo.value);
    
    try
    {
        arrResult = easyExecSql(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询批改状态信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        var sEdorState = arrResult[0][0];
        if (sEdorState != null && trim(sEdorState) == "3")    //等待录入
        {
            copyTwoBnfGrid();
        }
        else
        {
            queryNewBnfGrid();
        }
    } //arrResult != null
}

/*============================================================================*/

/**
 * showCodeList 的回调函数, 受益人速填信息
 */
function afterCodeSelect(sCodeListType, oCodeListField)
{
    if (sCodeListType == "CustomerType")
    {
        //必须是新受益人队列为焦点
        if (oCodeListField.name.indexOf("NewBnfGrid") == -1)
        {
            return;
        }
        else
        {
            var nLastFocusRowNo = NewBnfGrid.lastFocusRowNo;
            if (nLastFocusRowNo != null && nLastFocusRowNo >= 0)
            {
                //被保人不能为空
                var sInsuredNo = NewBnfGrid.getRowColData(nLastFocusRowNo, 1);
                if (sInsuredNo == null || trim(sInsuredNo) == "")
                {
                    alert("请先选择第一列被保人号码再速填！ ");
                    try
                    {
                        NewBnfGrid.setRowColData(nLastFocusRowNo, 11, "");
                    }
                    catch (ex) {}
                    return;
                }
                //区分个单还是团体复用
                var sAppObj;
                try
                {
                    sAppObj = document.getElementsByName("AppObj")[0].value;
                }
                catch (ex) {}
                //投保人
                if (oCodeListField.value == "A")
                {
                    if (sAppObj != null && sAppObj.trim() != "G")
                    {
                        try
                        {
                            //投保人始终显示在第一列
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 4, CustomerGrid.getRowColData(0, 3));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 12, CustomerGrid.getRowColData(0, 4));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 13, CustomerGrid.getRowColData(0, 5));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 6, CustomerGrid.getRowColData(0, 6));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 7, CustomerGrid.getRowColData(0, 7));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 8, "");
                        }
                        catch (ex) {}
                    }
                    else
                    {
                        try
                        {
                            //团体保单没有投保人数据
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 4, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 5, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 6, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 7, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 8, "");
                        }
                        catch (ex) {}
                    }
                }
                //被保人
                else if (oCodeListField.value == "I")
                {
                    //检查和客户列表哪个被保人号码相同
                    var nInsuredRowNo = -1;
                    var i = 0;
                    if (sAppObj != null && sAppObj.trim() != "G")
                    {
                        i = 1;    //团单下的分单没有 LCAppnt 数据, 从 0 开始, 个单从 1 开始
                    }
                    for (i; i < CustomerGrid.mulLineCount; i++)
                    {
                        if (CustomerGrid.getRowColData(i, 2) == sInsuredNo)
                        {
                            nInsuredRowNo = i;
                        }
                    }
                    //输入的被保人号码合法
                    if (nInsuredRowNo >= 0)
                    {
                        try
                        {
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 4, CustomerGrid.getRowColData(nInsuredRowNo, 3));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 12, CustomerGrid.getRowColData(nInsuredRowNo, 4));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 13, CustomerGrid.getRowColData(nInsuredRowNo, 5));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 6, CustomerGrid.getRowColData(nInsuredRowNo, 6));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 7, CustomerGrid.getRowColData(nInsuredRowNo, 7));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 8, "00");
                        }
                        catch (ex) {}
                    }
                    //输入的被保人号码非法
                    else
                    {
                        try
                        {
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 1, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 11, "");
                        }
                        catch (ex) {}
                    }
                }
            } //NewBnfGrid.lastFocusRowNo != null
        }
    } //CodeListType == CustomerType
}

/*============================================================================*/

/**
 * 如果是变更的附加险, 则进行提示
 */
function sureAddtionalPol()
{
    var QuerySQL, arrResult;
 
    try
    {
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeBC");
        mySql.setSqlId("PEdorTypeBCSql11");
        mySql.addSubPara(fm.ContNo.value);	
        mySql.addSubPara(fm.PolNo.value);    	
        arrResult = easyExecSql(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("警告：检查是否是变更主险或附加险信息出现异常！ ");
        return false;
    }
    if (arrResult != null)
    {
        if (!confirm("本次保全变更的是附加险受益人。是否继续？ "))
        {
            return false;
        }
    } //arrResult != null
    return true;
}

/*============================================================================*/

/**
 * 提交保全项目结果
 */
function saveEdorTypeBC()
{
    var nNewBnfCount;
    try
    {
        NewBnfGrid.delBlankLine();
        nNewBnfCount = NewBnfGrid.mulLineCount;
    }
    catch (ex) {}
    

    if (nNewBnfCount != null && nNewBnfCount == 0)
    {
        if (!confirm("新受益人信息为空，将删除所有受益人信息。是否继续？ "))
        {
            return;
        }
    }
    else
    {
        if (!NewBnfGrid.checkValue2())
        {
            return;
        }
        //var tSumCount=0;
        //校验受益比例 yaory-rewrite the rule of bnf-lot.
        var sumLiveBnf = new Array();
        var sumDeadBnf = new Array();
			  var LiveBnfFlag=false;
			  var DeadBnfFlag=false;
        for(var i=0;i<nNewBnfCount;i++)
        {
        	  //tSumCount+=parseFloat(NewBnfGrid.getRowColData(i,10));
        	  //yaory-update:origal author do not know how to distribute .
        	  if(NewBnfGrid.getRowColData(i, 3) == "1")
				    {	
				    	if(NewBnfGrid.getRowColData(i, 8)=="00")
				    	{
				    		alert('身故受益人不能是本人!');
				    		return false;
				    	}
				    }
				    
				     if (NewBnfGrid.getRowColData(i, 3) == "0")
             {
             	 LiveBnfFlag = true;
               if (typeof(sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i, 9))]) == "undefined")
               sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] = 0;
               sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] = sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] + parseFloat(NewBnfGrid.getRowColData(i, 10))*100;
             }
             else if (NewBnfGrid.getRowColData(i, 3) == "1")
             {
               if (typeof(sumDeadBnf[parseInt(NewBnfGrid.getRowColData(i, 9))]) == "undefined")
               sumDeadBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] = 0;
               sumDeadBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] = sumDeadBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] + parseFloat(NewBnfGrid.getRowColData(i, 10))*100;
             	 DeadBnfFlag = true;
             }
        }
        //------
        for (i=0; i<sumLiveBnf.length; i++)
      {
        if (typeof(sumLiveBnf[i])!="undefined"){sumLiveBnf[i]=parseFloat(sumLiveBnf[i])/100;}
        if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1)
        {
            alert("生存受益人受益顺序 " + i + " 的受益比例和为：" + sumLiveBnf[i]+ " 。大于100%，不能提交！");
            return false;
        }
        else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
        {
            alert("注意：生存受益人受益顺序 " + i + " 的受益比例和为：" + sumLiveBnf[i] + " 。小于100%");
            return false;
        }
      }
      
      for (i=0; i<sumDeadBnf.length; i++)
      {
        if (typeof(sumDeadBnf[i])!="undefined"){sumDeadBnf[i]=parseFloat(sumDeadBnf[i])/100;}
        if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1)
        {

          alert("死亡受益人受益顺序 " + i + " 的受益比例和为：" + sumDeadBnf[i] + " 。大于100%，不能提交！");
          return false;
        }
        else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1)
        {
            alert("死亡受益人受益顺序 " + i + " 的受益比例和为：" + sumDeadBnf[i] + " 。小于100%");
            return false;
        }
      }
        //if(tSumCount!=1)
        //{
        //		alert("受益人份数之和应该为1");
        //		return;
        //}
    }
    //start-----------根据身份证填写性别及出生日期
    var tIDNo ;
    var tIDType ;
    var str ;
    var tBirthday ;
    var tSex ;
    var tRiskCode=fm.RiskCode.value ;
    var tBnfType ;
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");
    mySql.setSqlId("PEdorTypeBCSql8");
    mySql.addSubPara(tRiskCode);	              
              
  	var AliveResult = easyExecSql(mySql.getString(),1,0);
  	var trelationtoinsured;
  	var trelationtoinsuredSQL;
  	var trelationtoinsuredResult;
    for (i=0;i<NewBnfGrid.mulLineCount;i++){
		tIDType=NewBnfGrid.getRowColData(i,6);
		tIDNo=NewBnfGrid.getRowColData(i,7);
		if(tIDType=="0")
		{
			 tBirthday=getBirthdatByIdNo(tIDNo);
		   tSex=getSexByIDNo(tIDNo);
		   if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		   {
		   	  str="第"+(i+1)+"行身份证号位数输入错误!!!" ;
		   	  alert(str);
		   	  return;
		   }
		   else
		   {  
		   	  NewBnfGrid.setRowColData(i,13,tBirthday) ;
		   	  NewBnfGrid.setRowColData(i,12,tSex) ;
		   }
	  }
	 //---------end
	//start --是否存在生存受益人
	     //alert(AliveResult.length);
	    tBnfType=NewBnfGrid.getRowColData(i,3);
	    var tBnfIA=NewBnfGrid.getRowColData(i,11);
	    if(tBnfType=="0")
	    {
//	    	 if((AliveResult==null || AliveResult.length<=0)&&tRiskCode!='111502') //111502 不判断
//	    	 {
//		   	   alert("第"+(i+1)+"行录入了生存受益人,而此险种无生存责任，无生存受益人。若已经存在受益人，则可能属于历史错误数据，请联系相关人员进行处理" );
//            return false;
         	    	   //如果原来有生存受益人则，需要和现在的进行比较，如果同一个人则说明只是变更资料
//	    	 	 if(OldBnfGrid.mulLineCount>0)
//	    	 	 {
//	    	 	 	var tFlag=false;
//	    	 	 	for(var t=0;t<OldBnfGrid.mulLineCount;t++)
//	    	 	 	{	 			       	 	  
//	    	 	 		var rName=OldBnfGrid.getRowColData(t,4);
//	    	 	 		var nName=NewBnfGrid.getRowColData(i,4);
//	    	 	 		var rBnfType=NewBnfGrid.getRowColData(i,3);  	 	 		    	 	 				   	     
//	    	 	 		if(rName!=nName&&rBnfType==tBnfType) //如果生存受益人不是同一个人，则需要提示，这里只能用姓名来判断了
//	    	 	 		{
//									tFlag=true;
//									break;
//	    	 	 		}
//	    	 	 	}
//	    	 	 		if(tFlag)
//	    	 	 		{
//    	    	 str="第"+(i+1)+"行录入了生存受益人,该险种依据条款约定，不可进行生存受益人的另行指定!!!" ;
//		   	     alert(str);
//		   	      return;
//	    	 	 			}
//	    	 	 	}	    	 	 		    	 	
//	    	 }else
//	    	 	{
 
         mySql=new SqlClass();
         mySql.setResourceName("bq.PEdorTypeBC");
         mySql.setSqlId("PEdorTypeBCSql9");
         mySql.addSubPara(fm.RiskCode.value);	   						 
						 
         var brr = easyExecSql(mySql.getString(),1,0);
 
         var tBnfFLag;
	       if(brr)
         {
             brr[0][0]==null||brr[0][0]=='null'?'0':tBnfFLag  = brr[0][0];
         }
         if(tBnfFLag=='I'&& (tBnfIA ==null || tBnfIA==""))
         {
         	alert("依据条款，此险种的受益人只能是被保人，是否继续?")
         	return false;
         	}
	         if(tBnfFLag=='A'&& (tBnfIA ==null || tBnfIA==""))
         {
         	alert("依据条款，此险种的受益人只能是被保人，是否继续?")
					return false;
         	}
	    	 		    	 		
	    	 		
//	    	 		}
	    }
	//end ----
	
	//add by jiaqiangli 2009-09-11 雇主关系下的受益人变更的校验规则
	trelationtoinsured = NewBnfGrid.getRowColData(i,8);
 
  mySql=new SqlClass();
  mySql.setResourceName("bq.PEdorTypeBC");
  mySql.setSqlId("PEdorTypeBCSql10");
  mySql.addSubPara(fm.EdorItemAppDate.value);
  mySql.addSubPara(fm.ContNo.value);
  mySql.addSubPara(trelationtoinsured);
         
	trelationtoinsuredResult = easyExecSql(mySql.getString(),1,0);
 
	if (trelationtoinsuredResult != null && trelationtoinsuredResult == '1') {
		if (!confirm("第"+(i+1)+"行受益人信息中，"+"投保人为被保人的雇主，不得指定被保险人及其近亲属以外的人为受益人。是否继续？ ")) {
            return false;
        }
	}
	//add by jiaqiangli 2009-09-11 雇主关系下的受益人变更的校验规则
	}
	  if (!sureAddtionalPol())
    {
        return;
    }
    var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
    document.forms[0].action = "PEdorTypeBCSubmit.jsp";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * 重置变更受益人表格
 */
function resetNewBnfGrid()
{
    if (confirm("此操作将重置变更受益人信息为原始值。是否继续？ "))
    {
        copyTwoBnfGrid();
    }
}

/*============================================================================*/

/**
 * 记事本查看
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo;
    try
    {
        sMissionID = top.opener.document.getElementsByName("MissionID")[0].value;
        sSubMissionID = top.opener.document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = top.opener.document.getElementsByName("OtherNo")[0].value;
    }
    catch (ex) {}
    if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "" || sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("警告：无法获取工作流任务节点任务号。查看记事本失败！ ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID="+ sSubMissionID + "&ActivityID=0000000003&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "工作流记事本查看", "left");
}

/*============================================================================*/

/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}


/*============================================================================*/


//<!-- JavaScript Document END -->
