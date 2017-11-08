

/**
 * <p>Title: PDContDefiEntry</p>
 * <p>Description: 契约信息定义入口</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-13
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.workflow.proddef.ProdDefWorkFlowBL;

public class PDContDefiEntryBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private MMap map = new MMap();

	public PDContDefiEntryBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		
		if (!check(cInputData, cOperate)) {
			return false;
		}

		if (!alterMissionProp(cInputData, cOperate)) {
			return false;
		}

		if (!dealWorkflow(cInputData, cOperate)) {
			return false;
		}

		return true;
	}

	/***
	 * 处理工作流信息
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	private boolean dealWorkflow(VData cInputData, String cOperate) {
		try {

			if (isNeedCreateNextNode(cInputData)) {

				ProdDefWorkFlowBL tProdDefWorkFlowBL = new ProdDefWorkFlowBL();
				if(!tProdDefWorkFlowBL.submitData(cInputData, cOperate))
				{
					this.mErrors.addOneError(tProdDefWorkFlowBL.mErrors.getFirstError());
					return false;
				}
			}

			return true;

		} catch (Exception ex) {
			this.mErrors.addOneError("工作流处理错误：" + ex.getMessage());
			return false;
		}
	}

	/***
	 * 是否需要创建下一个节点
	 * @param cInputData
	 * @return
	 */
	private boolean isNeedCreateNextNode(VData cInputData) {
		TransferData tData = new TransferData();
		tData = (TransferData) cInputData.getObjectByObjectName("TransferData",
				0);
		
// 根据"(("来取得判断条件
		ExeSQL exec = new ExeSQL();
		
		String select = "select transitioncond from LWProcessInstance  where transitionid = 'pd00001101' and processid = 'pd00000011' and transitionstart = 'pd00000001' and transitionend = 'pd00000002'";
		String getWhereDualStr = exec.getOneValue(select);
		String wherePart = exec.getOneValue(getWhereDualStr);
		int thePoint = wherePart.lastIndexOf("((");
		wherePart = wherePart.substring(thePoint,wherePart.length() - 1);
		
		String sql = "select 1 from lwmission where submissionid = '1' and "
				//+ "((missionprop1 || missionprop2 || missionprop3 || missionprop4 || missionprop5 ) = '11111') "
				+ wherePart
				+ "and missionid = '"
				+ ((String) tData.getValueByName("MissionID")).trim() + "' "
				+ "and submissionid = '"
				+ ((String) tData.getValueByName("SubMissionID")).trim() + "' "
				+ "and activityid = '"
				+ ((String) tData.getValueByName("ActivityID")).trim() + "'";

		
		if (exec.getOneValue(sql).equals("1")) {
			return true;
		}

		return false;
	}

	/***
	 * 点击【契约信息录入完毕】时，修改missionprop5的值为1，表示‘契约信息定义完毕’；
	 * 点击【理赔信息录入完毕】时，修改missionprop6的值为1，表示‘理赔信息定义完毕’；
	 * 点击【保全信息录入完毕】时，修改missionprop7的值为1，表示‘保全信息定义完毕’；
	 * 点击【保监会报表定义完毕】时，修改missionprop8的值为1，表示‘保监会信息定义完毕’；
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	private boolean alterMissionProp(VData cInputData, String cOperate) {
		try {
			TransferData tData = new TransferData();
			tData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);

			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			tLWMissionSchema.setMissionID((String) tData
					.getValueByName("MissionID"));
			tLWMissionSchema.setSubMissionID((String) tData
					.getValueByName("SubMissionID"));
			tLWMissionSchema.setActivityID((String) tData
					.getValueByName("ActivityID"));

			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setSchema(tLWMissionSchema);

			if (!tLWMissionDB.getInfo()) {
				this.mErrors.addOneError("查询"
						+ (String) tData.getValueByName("RiskCode") + "的信息失败");
				return false;
			}

			LWMissionSchema oldLWMissionSchema = tLWMissionDB.getSchema();
			if (cOperate.equals("cont")) {
				oldLWMissionSchema.setMissionProp5("1");
			} else if (cOperate.equals("claim")) {
				oldLWMissionSchema.setMissionProp6("1");
			} else if (cOperate.equals("edor")) {
				oldLWMissionSchema.setMissionProp7("1");
			} else if (cOperate.equals("lfrisk")) {
				oldLWMissionSchema.setMissionProp8("1");
			} else if (cOperate.equals("sale")) {
				oldLWMissionSchema.setMissionProp9("1");
			} else if (cOperate.equals("checkrule")) {
				oldLWMissionSchema.setMissionProp10("1");
			}
			else 
			{
				return true;
			}

			oldLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
			oldLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
			oldLWMissionSchema.setMissionProp8("1");
			// 分别提交，不影响
			
			tLWMissionDB.setSchema(oldLWMissionSchema);
			tLWMissionDB.update();
			
			// this.map.put(oldLWMissionSchema, "UPDATE");

			// 备份

			return true;
		} catch (Exception ex) {
			this.mErrors.addOneError("PDContDefiEntryBL.alterMissionProp处理失败:"
					+ ex.getMessage());
			return false;
		}
	}

	private boolean check(VData cInputData, String cOperate) {
		
		// 根据cOperate进行校验
		
		return true;
	}

	public static void main(String[] args) {
ExeSQL exec = new ExeSQL();
		
		String select = "select transitioncond from LWProcessInstance  where transitionid = 'pd00001101' and processid = 'pd00000011' and transitionstart = 'pd00000001' and transitionend = 'pd00000002'";
		String getWhereDualStr = exec.getOneValue(select);
		String wherePart = exec.getOneValue(getWhereDualStr);
		int thePoint = wherePart.lastIndexOf("((");
		wherePart = wherePart.substring(thePoint,wherePart.length() - 1);
	}
}
