//该文件中包含客户端需要处理的函数和事件
var turnPage  = new turnPageClass();
var turnPage1 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();  
var GrpContNo = "";
var mSwitch   = parent.VD.gVSwitch;

//初始化险种信息和已录入信息
function initQuery()
{ 
 GrpContNo = document.all('GrpContNo').value;  
 if (GrpContNo == null || GrpContNo == ""){
 	alert("传入团体保单号为空！");
 	return;
 }
 LPInsuredGrid.clearData("LPInsuredGrid");
// var strSql = "select a.InsuredNo, a.InsuredName, b.RiskCode, a.ContNo,a.PolType " +
// 							"from LCCont a left join LCPol b on a.ContNo = b.ContNo where " +
// 							"a.AppFlag = '2' and a.GrpContNo = '" + GrpContNo + "'";
 var strSql = "";
 var sqlid1="GEdorTypeNIInputSql1";
 var mySql1=new SqlClass();
 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 mySql1.addSubPara(GrpContNo);//指定传入的参数
 strSql=mySql1.getString();

 turnPage1.queryModal(strSql, LPInsuredGrid);  
}

//打开险种录入界面
function afterCodeSelect(cCodeName, Field) 
{
 if (LPInsuredGrid.mulLineCount >= 500)
 {
  alert("超出该批次最大处理范围，请重新增加一个保全项目！");
  return;
 }
 if (cCodeName == "EdorRisk")
 {
  //showInfo = window.open("./ProposalMain.jsp?risk="+Field.value+"&prtNo="+prtNo+"&loadFlag=7");
 }
}

//=======保存申请
function edorSave()
{
 if (LPInsuredGrid.mulLineCount == 0)
 {
  alert("请首先录入被保人信息再保存申请！");
  return;
 }
 for (i = 0; i < LPInsuredGrid.mulLineCount; i++)
 {  
  if (LPInsuredGrid.getRowColData(i, 3) == "")
  {
    alert("请检查是否所有的被保人下都已录入险种!");
    return;
  }
 }
// var tSql = "select edorstate from lpgrpedoritem where grpcontno = '"+fm.GrpContNo.value+"' and edorno = '"+fm.EdorNo.value+"' and edortype = '"+fm.EdorType.value+"'";
 var tSql = "";
 var sqlid1="GEdorTypeNIInputSql2";
 var mySql1=new SqlClass();
 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 mySql1.addSubPara(fm.GrpContNo.value);//指定传入的参数
 mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
 mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
 tSql=mySql1.getString();	
 var tArr = easyExecSql(tSql, 1, 0, 1);
	var tEdorState;
	if (tArr != null)
	{
	  tEdorState = tArr[0][0];
	  
	}

	if(tEdorState != "1")
	{
		if(!confirm("确认保存申请?将不能对明细记录做变动!"))
		{
				return;
		}
	}

 var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
 	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

 document.all('fmtransact').value = "INSERT";
 fm.submit();
}

//======================
function edorNewInsured()
{
//	 	var tSql = "select edorstate from lpgrpedoritem where grpcontno = '"+fm.GrpContNo.value+"' and edorno = '"+fm.EdorNo.value+"' and edortype = '"+fm.EdorType.value+"'";
	 	 var tSql = "";
	 	 var sqlid1="GEdorTypeNIInputSql3";
	 	 var mySql1=new SqlClass();
	 	 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
	 	 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	 	 mySql1.addSubPara(fm.GrpContNo.value);//指定传入的参数
	 	 mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
	 	 mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
	 	 tSql=mySql1.getString();	
	 	var tArr = easyExecSql(tSql, 1, 0, 1);
		var tEdorState;
		if (tArr != null)
		{
		  tEdorState = tArr[0][0];
		  
		}

		if(tEdorState == "1")
		{
			alert("已经保存申请!不能再做变动!");
			return;
		}
 delGrpVar();
 addGrpVar();
 var tContNo = document.all("ContNo").value; 
 var tEdorTypeCal = document.all("EdorTypeCal").value;
 var tNameType   = document.all("NameType").value; 
 var tCustomerNo = document.all("InsuredNo").value;
 var tEdorValiDate = document.all("EdorValiDate").value;
 
 parent.fraInterface.window.location = "../appgrp/ContInsuredInput.jsp?ContNo=" + tContNo + "&InsuredNo="+tCustomerNo+"&EdorTypeCal="+tEdorTypeCal+"&tNameFlag="+tNameType+"&EdorValiDate="+fm.EdorValiDate.value+"&BQFlag=2&LoadFlag=7&ContType=2&type=noScan&EdorType=NI";
}

//显示提交结果
function afterSubmit( FlagStr, content )
{
 showInfo.close();          
 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
 //showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
 	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

 if (FlagStr=="Success")
 {   
 QueryEdorMoney();
 }
}

//======================
function QueryEdorMoney()
{
  divGetMoney.style.display = "";
//  var moneySql = "select sum(GetMoney) from LJSGetEndorse where EndorsementNo = '" + 
//  							 document.all('EdorNo').value + "' and FeeOperationType = 'NI'";
     var moneySql = "";
	 var sqlid1="GEdorTypeNIInputSql4";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
	 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	 mySql1.addSubPara(document.all('EdorNo').value);//指定传入的参数
	 moneySql=mySql1.getString();	
  
  var arrResult = easyExecSql(moneySql);
  try {document.all('GetMoney').value = arrResult[0][0];} catch(ex) { };
  var tGetMoney = document.all('GetMoney').value;
  if(tGetMoney == null || tGetMoney == "null" || tGetMoney == "")
  {
   document.all('GetMoney').value = "0.00";
  }
}

//===================
function delGrpVar()
{
    //删除可能留在缓存中的个人合同信息
    try { mSwitch.deleteVar('ContNo');         		} catch(ex) { };
    try { mSwitch.deleteVar('ProposalContNo'); 		} catch(ex) { };
    
    //团体合同信息
    try { mSwitch.deleteVar('GrpContNo'); 				} catch(ex) { };
    try { mSwitch.deleteVar('ProposalGrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('PrtNo'); 						} catch(ex) { };
    try { mSwitch.deleteVar('SaleChnl'); 					} catch(ex) { };
    try { mSwitch.deleteVar('ManageCom'); 				} catch(ex) { };
    try { mSwitch.deleteVar('AgentCom'); 					} catch(ex) { };
    try { mSwitch.deleteVar('AgentType'); 				} catch(ex) { };
    try { mSwitch.deleteVar('AgentCode'); 				} catch(ex) { };
    try { mSwitch.deleteVar('AgentGroup');  			} catch(ex) { };
    try { mSwitch.deleteVar('AgentCode1');  			} catch(ex) { };
    try { mSwitch.deleteVar('Password');  				} catch(ex) { };
    try { mSwitch.deleteVar('Password2'); 				} catch(ex) { };
    try { mSwitch.deleteVar('AppntNo'); 					} catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); 				} catch(ex) { };
    try { mSwitch.deleteVar('Peoples2'); 					} catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); 					} catch(ex) { };
    try { mSwitch.deleteVar('BusinessType'); 			} catch(ex) { };
    try { mSwitch.deleteVar('GrpNature'); 				} catch(ex) { };
    try { mSwitch.deleteVar('RgtMoney'); 					} catch(ex) { };
    try { mSwitch.deleteVar('Asset'); 						} catch(ex) { };
    try { mSwitch.deleteVar('NetProfitRate'); 		} catch(ex) { };
    try { mSwitch.deleteVar('MainBussiness'); 		} catch(ex) { };
    try { mSwitch.deleteVar('Corporation'); 			} catch(ex) { };
    try { mSwitch.deleteVar('ComAera'); 					} catch(ex) { };
    try { mSwitch.deleteVar('Fax'); 							} catch(ex) { };
    try { mSwitch.deleteVar('Phone'); 						} catch(ex) { };
    try { mSwitch.deleteVar('GetFlag'); 					} catch(ex) { };
    try { mSwitch.deleteVar('Satrap'); 						} catch(ex) { };
    try { mSwitch.deleteVar('EMail'); 						} catch(ex) { };
    try { mSwitch.deleteVar('FoundDate'); 				} catch(ex) { };
    try { mSwitch.deleteVar('GrpGroupNo');				} catch(ex) { };
    try { mSwitch.deleteVar('BankCode'); 					} catch(ex) { };
    try { mSwitch.deleteVar('BankAccNo'); 				} catch(ex) { };
    try { mSwitch.deleteVar('AccName'); 					} catch(ex) { };
    try { mSwitch.deleteVar('DisputedFlag'); 			} catch(ex) { };
    try { mSwitch.deleteVar('OutPayFlag'); 				} catch(ex) { };
    try { mSwitch.deleteVar('GetPolMode'); 				} catch(ex) { };
    try { mSwitch.deleteVar('Lang'); 							} catch(ex) { };
    try { mSwitch.deleteVar('Currency'); 					} catch(ex) { };
    try { mSwitch.deleteVar('LostTimes'); 				} catch(ex) { };
    try { mSwitch.deleteVar('PrintCount');				} catch(ex) { };
    try { mSwitch.deleteVar('RegetDate'); 				} catch(ex) { };
    try { mSwitch.deleteVar('LastEdorDate'); 			} catch(ex) { };
    try { mSwitch.deleteVar('LastGetDate'); 			} catch(ex) { };
    try { mSwitch.deleteVar('LastLoanDate'); 			} catch(ex) { };
    try { mSwitch.deleteVar('SpecFlag'); 					} catch(ex) { };
    try { mSwitch.deleteVar('GrpSpec'); 					} catch(ex) { };
    try { mSwitch.deleteVar('PayMode'); 					} catch(ex) { };
    try { mSwitch.deleteVar('SignCom'); 					} catch(ex) { };
    try { mSwitch.deleteVar('SignDate'); 					} catch(ex) { };
    try { mSwitch.deleteVar('SignTime'); 					} catch(ex) { };
    try { mSwitch.deleteVar('CValiDate'); 				} catch(ex) { };
    try { mSwitch.deleteVar('PayIntv'); 					} catch(ex) { };
    try { mSwitch.deleteVar('ManageFeeRate'); 		} catch(ex) { };
    try { mSwitch.deleteVar('ExpPeoples'); 				} catch(ex) { };
    try { mSwitch.deleteVar('ExpPremium'); 				} catch(ex) { };
    try { mSwitch.deleteVar('ExpAmnt'); 					} catch(ex) { };
    try { mSwitch.deleteVar('Peoples'); 					} catch(ex) { };
    try { mSwitch.deleteVar('Mult'); 							} catch(ex) { };
    try { mSwitch.deleteVar('Prem'); 							} catch(ex) { };
    try { mSwitch.deleteVar('Amnt'); 							} catch(ex) { };
    try { mSwitch.deleteVar('SumPrem'); 					} catch(ex) { };
    try { mSwitch.deleteVar('SumPay'); 						} catch(ex) { };
    try { mSwitch.deleteVar('Dif'); 							} catch(ex) { };
    try { mSwitch.deleteVar('Remark'); 						} catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag1'); 			} catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag2'); 			} catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag3'); 			} catch(ex) { };
    try { mSwitch.deleteVar('InputOperator'); 		} catch(ex) { };
    try { mSwitch.deleteVar('InputDate'); 				} catch(ex) { };
    try { mSwitch.deleteVar('InputTime'); 				} catch(ex) { };
    try { mSwitch.deleteVar('ApproveFlag'); 			} catch(ex) { };
    try { mSwitch.deleteVar('ApproveCode'); 			} catch(ex) { };
    try { mSwitch.deleteVar('ApproveDate'); 			} catch(ex) { };
    try { mSwitch.deleteVar('ApproveTime'); 			} catch(ex) { };
    try { mSwitch.deleteVar('UWOperator'); 				} catch(ex) { };
    try { mSwitch.deleteVar('UWFlag'); 						} catch(ex) { };
    try { mSwitch.deleteVar('UWDate'); 						} catch(ex) { };
    try { mSwitch.deleteVar('UWTime'); 						} catch(ex) { };
    try { mSwitch.deleteVar('AppFlag'); 					} catch(ex) { };
    try { mSwitch.deleteVar('PolApplyDate'); 			} catch(ex) { };
    try { mSwitch.deleteVar('CustomGetPolDate'); 	} catch(ex) { };
    try { mSwitch.deleteVar('GetPolDate'); 				} catch(ex) { };
    try { mSwitch.deleteVar('GetPolTime'); 				} catch(ex) { };
    try { mSwitch.deleteVar('State');						 	} catch(ex) { };
    
    //集体投保人信息
    try { mSwitch.deleteVar('GrpNo'); 						} catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); 				} catch(ex) { };
    try { mSwitch.deleteVar('AppntGrade'); 				} catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); 					} catch(ex) { };
    try { mSwitch.deleteVar('PostalAddress'); 		} catch(ex) { };
    try { mSwitch.deleteVar('GrpZipCode'); 				} catch(ex) { };
    try { mSwitch.deleteVar('Phone'); 						} catch(ex) { };
    try { mSwitch.deleteVar('Password'); 					} catch(ex) { };
    try { mSwitch.deleteVar('State'); 						} catch(ex) { };
    try { mSwitch.deleteVar('AppntType'); 				} catch(ex) { };
    try { mSwitch.deleteVar('RelationToInsured'); } catch(ex) { };
}
       
//=================       
function addGrpVar()
{          
 //alert("This is addGrpVar!");	
 var tContNo = document.all('ContNo').value;     
 //if (tContNo == null || tContNo == ""){ 
 //	return;	
 //}
// var strSQL = "select PolType, Peoples from LCCont where ContNo = '" + tContNo + "'";
 var strSQL = "";
 var sqlid1="GEdorTypeNIInputSql5";
 var mySql1=new SqlClass();
 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 mySql1.addSubPara(tContNo);//指定传入的参数
 strSQL=mySql1.getString();	
 
 var arrReturn = new Array();
 arrResult = easyExecSql(strSQL, 1, 0);  
 if(arrResult != null && arrResult != "")
 {    
  try
  {
   mSwitch.deleteVar("PolType");  
   mSwitch.addVar("PolType", "", arrResult[0][0]);     
   mSwitch.updateVar("PolType", "", arrResult[0][0]); 
    
   mSwitch.deleteVar("InsuredPeoples");     
   mSwitch.addVar("InsuredPeoples", "", arrResult[0][1]);     
   mSwitch.updateVar("InsuredPeoples", "", arrResult[0][1]); 
  }
  catch (ex)
  {}
 } else {
 	//alert("查询失败！");
 	//return;
 }
 try { mSwitch.addVar('ContNo','',tContNo);    			} catch(ex) { };   
 try { mSwitch.addVar('ProposalContNo','',tContNo); } catch(ex) { }; 

 //集体合同信息
// var strsql = "select * from LCGrpCont where GrpContNo = '" + GrpContNo + "'";
 var strsql = "";
 var sqlid1="GEdorTypeNIInputSql6";
 var mySql1=new SqlClass();
 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 mySql1.addSubPara(GrpContNo);//指定传入的参数
 strsql=mySql1.getString();	 
 turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);  
 if (turnPage.strQueryResult == "")
 {
  return;
 }
 turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
 //团体合同号码
 try { mSwitch.addVar('GrpContNo','',turnPage.arrDataCacheSet[0][0]); } catch(ex) { }; 
 
 //团体投保单号码
 try { mSwitch.addVar('ProposalGrpContNo','',turnPage.arrDataCacheSet[0][1]); } catch(ex) { }; 
 
 //印刷号码
 try { mSwitch.addVar('PrtNo','',turnPage.arrDataCacheSet[0][2]);    	} catch(ex) { }; 
 
 //销售渠道
 try { mSwitch.addVar('SaleChnl','',turnPage.arrDataCacheSet[0][3]); 	} catch(ex) { }; 
 
 //管理机构
 try { mSwitch.addVar('ManageCom','',turnPage.arrDataCacheSet[0][4]); } catch(ex) { }; 
 
 //代理机构
 try { mSwitch.addVar('AgentCom','',turnPage.arrDataCacheSet[0][5]); 	} catch(ex) { }; 
 
 //代理机构内部分类
 try { mSwitch.addVar('AgentType','',turnPage.arrDataCacheSet[0][6]); } catch(ex) { }; 
 
 //代理人编码
 try { mSwitch.addVar('AgentCode','',turnPage.arrDataCacheSet[0][7]); } catch(ex) { }; 
 
 //代理人组别
 try { mSwitch.addVar('AgentGroup','',turnPage.arrDataCacheSet[0][8]);} catch(ex) { }; 
 
 //联合代理人代码
 try { mSwitch.addVar('AgentCode1','',turnPage.arrDataCacheSet[0][9]);} catch(ex) { }; 
 
 //保单口令
 try { mSwitch.addVar('Password','',turnPage.arrDataCacheSet[0][10]); } catch(ex) { }; 
 
 //密码
 try { mSwitch.addVar('Password2','',turnPage.arrDataCacheSet[0][11]);} catch(ex) { }; 
 
 //客户号码
 try { mSwitch.addVar('AppntNo','',turnPage.arrDataCacheSet[0][12]); 	} catch(ex) { }; 
 
 //地址号码
 try { mSwitch.addVar('AddressNo','',turnPage.arrDataCacheSet[0][13]);} catch(ex) { }; 
 
 //投保总人数
 try { mSwitch.addVar('Peoples2','',turnPage.arrDataCacheSet[0][14]); } catch(ex) { }; 
 
 //单位名称
 try { mSwitch.addVar('GrpName','',turnPage.arrDataCacheSet[0][15]); 	} catch(ex) { }; 
 
 //行业分类
 try { mSwitch.addVar('BusinessType','',turnPage.arrDataCacheSet[0][16]); } catch(ex) { }; 
 
 //单位性质
 try { mSwitch.addVar('GrpNature','',turnPage.arrDataCacheSet[0][17]); } catch(ex) { }; 
 
 //注册资本
 try { mSwitch.addVar('RgtMoney','',turnPage.arrDataCacheSet[0][18]); } catch(ex) { }; 
 
 //资产总额
 try { mSwitch.addVar('Asset','',turnPage.arrDataCacheSet[0][19]); } catch(ex) { }; 
 
 //净资产收益率
 try { mSwitch.addVar('NetProfitRate','',turnPage.arrDataCacheSet[0][20]); 	} catch(ex) { }; 
 
 //主营业务
 try { mSwitch.addVar('MainBussiness','',turnPage.arrDataCacheSet[0][21]); 	} catch(ex) { }; 
 
 //法人
 try { mSwitch.addVar('Corporation','',turnPage.arrDataCacheSet[0][22]); 		} catch(ex) { }; 
 
 //机构分布区域
 try { mSwitch.addVar('ComAera','',turnPage.arrDataCacheSet[0][23]); 				} catch(ex) { }; 
 
 //单位传真
 try { mSwitch.addVar('Fax','',turnPage.arrDataCacheSet[0][24]); 		} catch(ex) { }; 
 
 //单位电话
 try { mSwitch.addVar('Phone','',turnPage.arrDataCacheSet[0][25]); 	} catch(ex) { }; 
 
 //付款方式
 try { mSwitch.addVar('GetFlag','',turnPage.arrDataCacheSet[0][26]);} catch(ex) { }; 
 
 //负责人
 try { mSwitch.addVar('Satrap','',turnPage.arrDataCacheSet[0][27]); } catch(ex) { }; 
 
 //公司e_mail
 try { mSwitch.addVar('EMail','',turnPage.arrDataCacheSet[0][28]); 	} catch(ex) { }; 
 
 //成立日期
 try { mSwitch.addVar('FoundDate','',turnPage.arrDataCacheSet[0][29]); 	} catch(ex) { }; 
 
 //客户组号码
 try { mSwitch.addVar('GrpGroupNo','',turnPage.arrDataCacheSet[0][30]); } catch(ex) { }; 
 
 //银行编码
 try { mSwitch.addVar('BankCode','',turnPage.arrDataCacheSet[0][31]); 	} catch(ex) { }; 
 
 //银行帐号
 try { mSwitch.addVar('BankAccNo','',turnPage.arrDataCacheSet[0][32]); 	} catch(ex) { }; 
 
 //银行帐户名
 try { mSwitch.addVar('AccName','',turnPage.arrDataCacheSet[0][33]); 		} catch(ex) { }; 
 
 //合同争议处理方式
 try { mSwitch.addVar('DisputedFlag','',turnPage.arrDataCacheSet[0][34]); } catch(ex) { }; 
 
 //溢交处理方式
 try { mSwitch.addVar('OutPayFlag','',turnPage.arrDataCacheSet[0][35]); } catch(ex) { }; 
 
 //保单送达方式
 try { mSwitch.addVar('GetPolMode','',turnPage.arrDataCacheSet[0][36]); } catch(ex) { }; 
 
 //语种标记
 try { mSwitch.addVar('Lang','',turnPage.arrDataCacheSet[0][37]); } catch(ex) { }; 
 
 //币别
 try { mSwitch.addVar('Currency','',turnPage.arrDataCacheSet[0][38]); } catch(ex) { }; 
 
 //遗失补发次数
 try { mSwitch.addVar('LostTimes','',turnPage.arrDataCacheSet[0][39]); } catch(ex) { }; 
 
 //保单打印次数
 try { mSwitch.addVar('PrintCount','',turnPage.arrDataCacheSet[0][40]); } catch(ex) { }; 
 
 //最后一次催收日期
 try { mSwitch.addVar('RegetDate','',turnPage.arrDataCacheSet[0][41]); } catch(ex) { }; 
 
 //最后一次保全日期
 try { mSwitch.addVar('LastEdorDate','',turnPage.arrDataCacheSet[0][42]); } catch(ex) { }; 
 
 //最后一次给付日期
 try { mSwitch.addVar('LastGetDate','',turnPage.arrDataCacheSet[0][43]); } catch(ex) { }; 
 
 //最后一次借款日期
 try { mSwitch.addVar('LastLoanDate','',turnPage.arrDataCacheSet[0][44]); } catch(ex) { }; 
 
 //团体特殊业务标志
 try { mSwitch.addVar('SpecFlag','',turnPage.arrDataCacheSet[0][45]); } catch(ex) { }; 
 
 //集体特约
 try { mSwitch.addVar('GrpSpec','',turnPage.arrDataCacheSet[0][46]); } catch(ex) { }; 
 
 //交费方式
 try { mSwitch.addVar('PayMode','',turnPage.arrDataCacheSet[0][47]); } catch(ex) { }; 
 
 //签单机构
 try { mSwitch.addVar('SignCom','',turnPage.arrDataCacheSet[0][48]); } catch(ex) { }; 
 
 //签单日期
 try { mSwitch.addVar('SignDate','',turnPage.arrDataCacheSet[0][49]); } catch(ex) { }; 
 
 //签单时间
 try { mSwitch.addVar('SignTime','',turnPage.arrDataCacheSet[0][50]); } catch(ex) { }; 
 
 //保单生效日期
// try { mSwitch.addVar('CValiDate','',turnPage.arrDataCacheSet[0][51]); } catch(ex) { }; 

//交费间隔
 try { mSwitch.addVar('PayIntv','',turnPage.arrDataCacheSet[0][52]); } catch(ex) { }; 
 
 //管理费比例
 try { mSwitch.addVar('ManageFeeRate','',turnPage.arrDataCacheSet[0][53]); } catch(ex) { }; 
 
 //预计人数
 try { mSwitch.addVar('ExpPeoples','',turnPage.arrDataCacheSet[0][54]); } catch(ex) { }; 
 
 //预计保费
 try { mSwitch.addVar('ExpPremium','',turnPage.arrDataCacheSet[0][55]); } catch(ex) { }; 
 
 //预计保额
 try { mSwitch.addVar('ExpAmnt','',turnPage.arrDataCacheSet[0][56]); } catch(ex) { }; 
 
 //总人数
 try { mSwitch.addVar('Peoples','',turnPage.arrDataCacheSet[0][57]); } catch(ex) { }; 
 
 //总份数
 try { mSwitch.addVar('Mult','',turnPage.arrDataCacheSet[0][58]); } catch(ex) { }; 
 
 //总保费
 try { mSwitch.addVar('Prem','',turnPage.arrDataCacheSet[0][59]); } catch(ex) { }; 
 
 //总保额
 try { mSwitch.addVar('Amnt','',turnPage.arrDataCacheSet[0][60]); } catch(ex) { }; 
 
 //总累计保费
 try { mSwitch.addVar('SumPrem','',turnPage.arrDataCacheSet[0][61]); } catch(ex) { }; 
 
 //总累计交费
 try { mSwitch.addVar('SumPay','',turnPage.arrDataCacheSet[0][62]); } catch(ex) { }; 
 
 //差额
 try { mSwitch.addVar('Dif','',turnPage.arrDataCacheSet[0][63]); } catch(ex) { }; 
 
 //备注
 try { mSwitch.addVar('Remark','',turnPage.arrDataCacheSet[0][64]); } catch(ex) { }; 
 
 //备用属性字段1
 try { mSwitch.addVar('StandbyFlag1','',turnPage.arrDataCacheSet[0][65]); } catch(ex) { }; 
 
 //备用属性字段2
 try { mSwitch.addVar('StandbyFlag2','',turnPage.arrDataCacheSet[0][66]); } catch(ex) { }; 
 
 //备用属性字段3
 try { mSwitch.addVar('StandbyFlag3','',turnPage.arrDataCacheSet[0][67]); } catch(ex) { }; 
 
 //录单人
 try { mSwitch.addVar('InputOperator','',turnPage.arrDataCacheSet[0][68]); } catch(ex) { }; 
 
 //录单完成日期
 try { mSwitch.addVar('InputDate','',turnPage.arrDataCacheSet[0][69]); } catch(ex) { }; 
 
 //录单完成时间
 try { mSwitch.addVar('InputTime','',turnPage.arrDataCacheSet[0][70]); } catch(ex) { }; 
 
 //复核状态
 try { mSwitch.addVar('ApproveFlag','',turnPage.arrDataCacheSet[0][71]); } catch(ex) { }; 
 
 //复核人编码
 try { mSwitch.addVar('ApproveCode','',turnPage.arrDataCacheSet[0][72]); } catch(ex) { }; 
 
 //复核日期
 try { mSwitch.addVar('ApproveDate','',turnPage.arrDataCacheSet[0][73]); } catch(ex) { }; 
 
 //复核时间
 try { mSwitch.addVar('ApproveTime','',turnPage.arrDataCacheSet[0][74]); } catch(ex) { }; 
 
 //核保人
 try { mSwitch.addVar('UWOperator','',turnPage.arrDataCacheSet[0][75]); } catch(ex) { }; 
 
 //核保状态
 try { mSwitch.addVar('UWFlag','',turnPage.arrDataCacheSet[0][76]); } catch(ex) { }; 
 
 //核保完成日期
 try { mSwitch.addVar('UWDate','',turnPage.arrDataCacheSet[0][77]); } catch(ex) { }; 
 
 //核保完成时间
 try { mSwitch.addVar('UWTime','',turnPage.arrDataCacheSet[0][78]); } catch(ex) { }; 
 
 //投保单/保单标志
 try { mSwitch.addVar('AppFlag','',turnPage.arrDataCacheSet[0][79]); } catch(ex) { }; 
 
 //投保单申请日期
 try { mSwitch.addVar('PolApplyDate','',turnPage.arrDataCacheSet[0][80]); } catch(ex) { }; 
 
 //保单回执客户签收日期
 try { mSwitch.addVar('CustomGetPolDate','',turnPage.arrDataCacheSet[0][81]); } catch(ex) { }; 
 
 //保单送达日期
 try { mSwitch.addVar('GetPolDate','',turnPage.arrDataCacheSet[0][82]); } catch(ex) { }; 
 
 //保单送达时间
 try { mSwitch.addVar('GetPolTime','',turnPage.arrDataCacheSet[0][83]); } catch(ex) { }; 
 
 //状态
 try { mSwitch.addVar('State','',turnPage.arrDataCacheSet[0][84]); } catch(ex) { }; 
 
 //操作员
 try { mSwitch.addVar('Operator','',turnPage.arrDataCacheSet[0][85]); } catch(ex) { }; 
 
 //入机日期
 try { mSwitch.addVar('MakeDate','',turnPage.arrDataCacheSet[0][86]); } catch(ex) { }; 
 
 //入机时间
 try { mSwitch.addVar('MakeTime','',turnPage.arrDataCacheSet[0][87]); } catch(ex) { }; 
 
 //最后一次修改日期
 try { mSwitch.addVar('ModifyDate','',turnPage.arrDataCacheSet[0][88]); } catch(ex) { }; 
 
 //最后一次修改时间
 try { mSwitch.addVar('ModifyTime','',turnPage.arrDataCacheSet[0][89]); } catch(ex) { }; 
 
 //集体投保人信息
// strsql = "select * from LCGrpAppnt where GrpContNo = '" + GrpContNo + "'";
 
 var strsql = "";
 var sqlid1="GEdorTypeNIInputSql7";
 var mySql1=new SqlClass();
 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 mySql1.addSubPara(GrpContNo);//指定传入的参数
 strsql=mySql1.getString();	 
 turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
 if (turnPage.strQueryResult == "")
 {

  return;
 }
 turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
 //团体合同号码
 try { mSwitch.addVar('GrpContNo','',turnPage.arrDataCacheSet[0][0]); } catch(ex) { }; 
 
 //客户号码
 try { mSwitch.addVar('CustomerNo','',turnPage.arrDataCacheSet[0][1]); } catch(ex) { }; 
 
 //印刷号码
 try { mSwitch.addVar('PrtNo','',turnPage.arrDataCacheSet[0][2]); } catch(ex) { }; 
 
 //客户地址号码
 try { mSwitch.addVar('AddressNo','',turnPage.arrDataCacheSet[0][3]); } catch(ex) { }; 
 
 //投保人级别
 try { mSwitch.addVar('AppntGrade','',turnPage.arrDataCacheSet[0][4]); } catch(ex) { }; 
 
 //单位名称
 try { mSwitch.addVar('Name','',turnPage.arrDataCacheSet[0][5]); } catch(ex) { }; 
 
 //通讯地址
 try { mSwitch.addVar('PostalAddress','',turnPage.arrDataCacheSet[0][6]); } catch(ex) { }; 
 
 //单位邮编
 try { mSwitch.addVar('ZipCode','',turnPage.arrDataCacheSet[0][7]); } catch(ex) { }; 
 
 //单位电话
 try { mSwitch.addVar('Phone','',turnPage.arrDataCacheSet[0][8]); } catch(ex) { }; 
 
 //密码
 try { mSwitch.addVar('Password','',turnPage.arrDataCacheSet[0][9]); } catch(ex) { }; 
 
 //状态
 try { mSwitch.addVar('State','',turnPage.arrDataCacheSet[0][10]); } catch(ex) { }; 
 
 //投保人类型
 try { mSwitch.addVar('AppntType','',turnPage.arrDataCacheSet[0][11]); } catch(ex) { }; 
 
 //被保人与投保人关系
 try { mSwitch.addVar('RelationToInsured','',turnPage.arrDataCacheSet[0][12]); } catch(ex) { }; 
 
 //操作员
 try { mSwitch.addVar('Operator','',turnPage.arrDataCacheSet[0][13]); } catch(ex) { }; 
 
 //入机日期
 try { mSwitch.addVar('MakeDate','',turnPage.arrDataCacheSet[0][14]); } catch(ex) { }; 
 
 //入机时间
 try { mSwitch.addVar('MakeTime','',turnPage.arrDataCacheSet[0][15]); } catch(ex) { }; 
 
 //最后一次修改日期
 try { mSwitch.addVar('ModifyDate','',turnPage.arrDataCacheSet[0][16]); } catch(ex) { }; 
 
 //最后一次修改时间
 try { mSwitch.addVar('ModifyTime','',turnPage.arrDataCacheSet[0][17]); } catch(ex) { }; 
// strSql = "select EdorValiDate from LPGrpEdorMain where EdorNo = '" + 
// 					document.all("EdorNo").value + "' and GrpContNo = '" + GrpContNo + "'";
 var strSql = "";
 var sqlid1="GEdorTypeNIInputSql8";
 var mySql1=new SqlClass();
 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 mySql1.addSubPara(document.all("EdorNo").value);//指定传入的参数
 mySql1.addSubPara(GrpContNo);//指定传入的参数
 strSql=mySql1.getString();	 
 
 turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
 if (turnPage.strQueryResult == "")
 {
  alert(1);
  return;
 }
 turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
 //保单生效日期
 try { mSwitch.addVar('CValiDate','',turnPage.arrDataCacheSet[0][0]); } catch(ex) { }; 
}               

//=============================
function onInsuredGridSelected()
{
 var tSel = LPInsuredGrid.getSelNo(); 
 if( tSel == null || tSel == 0 ){
  alert( "请先选择一条记录，再点击返回按钮。" );
  return;
 }else { 	
  try
  {
  	var tRow = LPInsuredGrid.getSelNo() - 1; 
  	document.all('ContNo').value = LPInsuredGrid.getRowColData(tRow, 4);
  	document.all('InsuredNo').value = LPInsuredGrid.getRowColData(tRow, 1);
  	//alert(document.all('InsuredNo').value);
  	document.all('NameType').value = LPInsuredGrid.getRowColData(tRow, 5); 
  } catch(ex) {}  
 } 
}

//2005-5-10增加,导入被保人清单调用--Start--
function getin()
{
 	if ( fm.GrpContNo.value == "")
 	{
 	 alert("请先查询保单信息");
 	 return ;
 	}
 	
// 	var tSql = "select edorstate from lpgrpedoritem where grpcontno = '"+fm.GrpContNo.value+"' and edorno = '"+fm.EdorNo.value+"' and edortype = '"+fm.EdorType.value+"'";
 	 var tSql = "";
 	 var sqlid1="GEdorTypeNIInputSql9";
 	 var mySql1=new SqlClass();
 	 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
 	 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 	 mySql1.addSubPara(fm.GrpContNo.value);//指定传入的参数
 	 mySql1.addSubPara(fm.EdorNo.value);//指定传入的参数
 	 mySql1.addSubPara(fm.EdorType.value);//指定传入的参数
 	 tSql=mySql1.getString();	 
 	var tArr = easyExecSql(tSql, 1, 0, 1);
	var tEdorState;
		if (tArr != null)
		{
		  tEdorState = tArr[0][0];
		  
		}
 			if(tEdorState == "1")
		{
			alert("已经保存申请!不能再做变动!");
			return;
		}
 	//var strUrl = "../app/DiskApplyInput3.jsp?grpcontno="+ fm.GrpContNo.value;
 	var strUrl = "../appgrp/DiskApplyInputMainBq.jsp?grpcontno="+ fm.GrpContNo.value+"&EdorValiDate="+fm.EdorValiDate.value+"&EdorTypeCal="+fm.EdorTypeCal.value+"&EdorType="+fm.EdorType.value;
 	showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no");
}

//返回，关闭当前窗口
function returnParent()
{
	top.opener.getEdorItemGrid();
 	top.close();
}

//======================
function QueryEdorInfo()
{
 	var tEdortype = document.all('EdorType').value
 	if(tEdortype != null && tEdortype != '')
 	{
// 		var strSQL = "select distinct EdorCode, EdorName from LMEdorItem " +
// 								 "where EdorCode = '" + tEdortype + "'";
 		 var strSQL = "";
 	 	 var sqlid1="GEdorTypeNIInputSql10";
 	 	 var mySql1=new SqlClass();
 	 	 mySql1.setResourceName("bq.GEdorTypeNIInputSql"); //指定使用的properties文件名
 	 	 mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 	 	 mySql1.addSubPara(tEdortype);//指定传入的参数
 	 	 strSQL=mySql1.getString();	 
 	}else{
 	 	alert('未查询到保全批改项目信息！');
 	} 
 	var arrSelected = new Array(); 
 	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
 	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
	try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}

//====================
function MoneyDetail()
{
  window.open("./GetLJSGetEndorse.jsp");  
}



      
//======================      
function edorDelInsured()
{ 
 	if(fm.ContNo.value == "")
 	{
 	 	alert("请先选择需要删除的被保人");
 	 	return false;
 	}
}

//=================

