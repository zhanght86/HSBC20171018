//程序名称：SpeedProgressConvert.js
//程序功能：产品定义进度显示国际化方案
//创建日期：2011-11-23 ranjun
//该文件中包含客户端需要处理的函数和事件
//国际化变量存放区
//按词条频率排序
//=======================================
var RISKPROPRESSCODE01 = ""+"险种销售机构控制"+"";
var RISKPROPRESSCODE02 = ""+"险种账户定义"+"";
var RISKPROPRESSCODE03 = ""+"险种责任加费定义"+"";
var RISKPROPRESSCODE04 = ""+"利益計算要素"+"";
var RISKPROPRESSCODE05 = ""+"险种投保规则"+"";
var RISKPROPRESSCODE06 = ""+"险种各角色定义"+"";
var RISKPROPRESSCODE07 = ""+"险种分类定义"+"";
var RISKPROPRESSCODE08 = ""+"赔付算法定义"+"";
var RISKPROPRESSCODE09 = ""+"费率表定义"+"";
var RISKPROPRESSCODE10 = ""+"账户责任给付明细"+"";
var RISKPROPRESSCODE11 = ""+"账户缴费定义"+"";
var RISKPROPRESSCODE12 = ""+"账户给付定义"+"";
var RISKPROPRESSCODE13 = ""+"账户管理费定义"+"";
var RISKPROPRESSCODE14 = ""+"责任生存给付信息"+"";
var RISKPROPRESSCODE15 = ""+"责任信息"+"";
var RISKPROPRESSCODE16 = ""+"缴费责任信息"+"";
var RISKPROPRESSCODE17 = ""+"缴费字段控制"+"";
var RISKPROPRESSCODE18 = ""+"给付责任信息"+"";
var RISKPROPRESSCODE19 = ""+"算法定义"+"";
var RISKPROPRESSCODE20 = ""+"监管信息设定"+"";
var RISKPROPRESSCODE21 = ""+"理赔业务控制"+"";
var RISKPROPRESSCODE22 = ""+"测试发布"+"";
var RISKPROPRESSCODE23 = ""+"条款打印定义"+"";
var RISKPROPRESSCODE24 = ""+"数据表定义"+"";
var RISKPROPRESSCODE25 = ""+"宽限期信息"+"";
var RISKPROPRESSCODE26 = ""+"契约业务控制"+"";
var RISKPROPRESSCODE27 = ""+"基本信息"+"";
var RISKPROPRESSCODE28 = ""+"保障责任赔付明细"+"";
var RISKPROPRESSCODE29 = ""+"保障计划约定要素"+"";
var RISKPROPRESSCODE30 = ""+"保监会报表的产品分类"+"";
var RISKPROPRESSCODE31 = ""+"保全项目选择"+"";
var RISKPROPRESSCODE32 = ""+"保全项目算法定义"+"";
var RISKPROPRESSCODE33 = ""+"保全业务控制"+"";
var RISKPROPRESSCODE34 = ""+"产品测试发布"+"";
var RISKPROPRESSCODE35 = ""+"产品条款定义"+"";
var RISKPROPRESSCODE36 = ""+"产品审核"+"";
var RISKPROPRESSCODE37 = ""+"产品信息审核"+"";
var RISKPROPRESSCODE38 = ""+"个险页面录入配置"+"";
var	RISKPROPRESSCODE39 = ""+"分红定义"+"";
var	RISKPROPRESSCODE40 = ""+"关联账单定义"+"";
var RISKPROPRESSCODE41 = ""+"录入信息控制定义"+"";
var RISKPROPRESSCODE42 = ""+"产品发布"+"";
//=======================================
function speedConvert(value)
{
	if(value == null||"null"==value)  return"";
	if(value == "RISKPROPRESSCODE01") return RISKPROPRESSCODE01;
	if(value == "RISKPROPRESSCODE02") return RISKPROPRESSCODE02;
	if(value == "RISKPROPRESSCODE03") return RISKPROPRESSCODE03;
	if(value == "RISKPROPRESSCODE04") return RISKPROPRESSCODE04;
	if(value == "RISKPROPRESSCODE05") return RISKPROPRESSCODE05;
	if(value == "RISKPROPRESSCODE06") return RISKPROPRESSCODE06;
	if(value == "RISKPROPRESSCODE07") return RISKPROPRESSCODE07;
	if(value == "RISKPROPRESSCODE08") return RISKPROPRESSCODE08;
	if(value == "RISKPROPRESSCODE09") return RISKPROPRESSCODE09;
	if(value == "RISKPROPRESSCODE10") return RISKPROPRESSCODE10;
	if(value == "RISKPROPRESSCODE11") return RISKPROPRESSCODE11;
	if(value == "RISKPROPRESSCODE12") return RISKPROPRESSCODE12;
	if(value == "RISKPROPRESSCODE13") return RISKPROPRESSCODE13;
	if(value == "RISKPROPRESSCODE14") return RISKPROPRESSCODE14;
	if(value == "RISKPROPRESSCODE15") return RISKPROPRESSCODE15;
	if(value == "RISKPROPRESSCODE16") return RISKPROPRESSCODE16;
	if(value == "RISKPROPRESSCODE17") return RISKPROPRESSCODE17;
	if(value == "RISKPROPRESSCODE18") return RISKPROPRESSCODE18;
	if(value == "RISKPROPRESSCODE19") return RISKPROPRESSCODE19;
	if(value == "RISKPROPRESSCODE20") return RISKPROPRESSCODE20;
	if(value == "RISKPROPRESSCODE21") return RISKPROPRESSCODE21;
	if(value == "RISKPROPRESSCODE22") return RISKPROPRESSCODE22;
	if(value == "RISKPROPRESSCODE23") return RISKPROPRESSCODE23;
	if(value == "RISKPROPRESSCODE24") return RISKPROPRESSCODE24;
	if(value == "RISKPROPRESSCODE25") return RISKPROPRESSCODE25;
	if(value == "RISKPROPRESSCODE26") return RISKPROPRESSCODE26;
	if(value == "RISKPROPRESSCODE27") return RISKPROPRESSCODE27;
	if(value == "RISKPROPRESSCODE28") return RISKPROPRESSCODE28;
	if(value == "RISKPROPRESSCODE29") return RISKPROPRESSCODE29;
	if(value == "RISKPROPRESSCODE30") return RISKPROPRESSCODE30;
	if(value == "RISKPROPRESSCODE31") return RISKPROPRESSCODE31;
	if(value == "RISKPROPRESSCODE32") return RISKPROPRESSCODE32;
	if(value == "RISKPROPRESSCODE33") return RISKPROPRESSCODE33;
	if(value == "RISKPROPRESSCODE34") return RISKPROPRESSCODE34;
	if(value == "RISKPROPRESSCODE35") return RISKPROPRESSCODE35;
	if(value == "RISKPROPRESSCODE36") return RISKPROPRESSCODE36;
	if(value == "RISKPROPRESSCODE37") return RISKPROPRESSCODE37;
	if(value == "RISKPROPRESSCODE38") return RISKPROPRESSCODE38;
	if(value == "RISKPROPRESSCODE39") return RISKPROPRESSCODE39;
	if(value == "RISKPROPRESSCODE40") return RISKPROPRESSCODE40;
	if(value == "RISKPROPRESSCODE41") return RISKPROPRESSCODE41;
	if(value == "RISKPROPRESSCODE42") return RISKPROPRESSCODE42;
	return value;
}