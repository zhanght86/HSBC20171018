

package com.sinosoft.productdef;

import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.PD_LBRiskInfoSchema;

/**
 * @author NicolE 当修改或删除产品信息时，记录操作轨迹
 */
public class PDRiskOperationTrackBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public VData saveResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private ArrayList mList = new ArrayList();

	private Schema mSchema = null;

	private Object mObject = null;

	/** 前台页面操作的表名，可能为空 */
	private String mTableName = "";

	private String mRiskCode = "";

	private static final String FIELDNAME = "StandByFlag";

	public PDRiskOperationTrackBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * @author NicolE 获取基础数据
	 * @see CommonBase.java, The method getInputData()
	 * @param cInputData
	 * @return
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mTableName = (String) mTransferData.getValueByName("tableName");
		mList = (ArrayList) mTransferData.getValueByName("list");
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");

		try {
			Class tSchemaClass = Class.forName("com.sinosoft.lis.schema."
					+ mTableName + "Schema");
			mSchema = (Schema) tSchemaClass.newInstance();
			Class tDBClass = Class.forName("com.sinosoft.lis.db." + mTableName
					+ "DB");
			mObject = (Object) tDBClass.newInstance();
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return false;
		}

		return true;
	}

	/**
	 * 查出可能修改了那些字段，将之备份到PD_LBRiskInfo表
	 * 
	 * @return
	 */
	private boolean dealData() {
		try {
			String keysSelect = "select fieldcode,isprimary,displayorder from PD_BASEFIELD where isdisplay = 1 and tablecode = upper('"
					+ mTableName + "') order by displayorder";
			SSRS ssrs = new ExeSQL().execSQL(keysSelect);

			Method m = null;

			if (ssrs != null) {
				// 将初始化表的主键值
				for (int i = 1; i <= ssrs.MaxRow; i++) {
					if (ssrs.GetText(i, 2).equals("1")) {
						mSchema.setV(ssrs.GetText(i, 1), String.valueOf(mList
								.get(i - 1)));
					}
				}
				// 查询出数据库中的存储数据
				Class[] parameterC = new Class[1]; // 调用方法的参数数组
				Object[] parameterO = new Object[1]; // 调用方法的对象数组
				parameterC[0] = mSchema.getClass();
				m = mObject.getClass().getMethod("setSchema", parameterC);
				parameterO[0] = mSchema;
				m.invoke(mObject, parameterO);
				m = mObject.getClass().getMethod("getInfo", null);
				Boolean b = (Boolean) m.invoke(mObject, null);
				if (b.booleanValue()) {
					m = mObject.getClass().getMethod("getSchema", null);
					mSchema = (Schema) m.invoke(mObject, null);
				}

				// 准备待写入的备份表数据
				PD_LBRiskInfoSchema saveSchema = new PD_LBRiskInfoSchema();
				// 按日期生成流水号
				SimpleDateFormat sf = new SimpleDateFormat(
						"yyyyMMddHH24mmssSSS");
				String serialNo = sf.format(new Date());
				// 设置主键和非空字段
				saveSchema.setSerialNo(serialNo);
				saveSchema.setTableCode(mTableName);
				saveSchema.setCreateOperator(mGlobalInput.Operator);
				saveSchema.setMakeDate(PubFun.getCurrentDate());
				saveSchema.setMakeTime(PubFun.getCurrentTime());
				//saveSchema.setbatchNum(serialNo);
				saveSchema.setRiskCode(mRiskCode);
				// 查询险种的工作流信息
				String tSQL = "SELECT MissionID,SubMissionID,ActivityID,LastOperator FROM LWMission WHERE MissionProp2 = '"
						+ mRiskCode + "'";
				SSRS tSSRS = new ExeSQL().execSQL(tSQL);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					saveSchema.setMissionID(tSSRS.GetText(1, 1));
					saveSchema.setSubMissionID(tSSRS.GetText(1, 2));
					saveSchema.setActivityID(tSSRS.GetText(1, 3));
					saveSchema.setLastOperator(tSSRS.GetText(1, 4));
				} else {
					System.out.println("查询险种" + mRiskCode + "的工作流信息失败！");
				}

				String fieldValue = "";
				// 将修改之前的数据写到备份表中
				for (int i = 1; i <= ssrs.MaxRow; i++) {
					// 获取待写入备份表数据
					Class[] c = new Class[1];
					c[0] = String.class;
					m = mSchema.getClass().getMethod("getV", c);
					// 取出查询出的字段值
					Object[] o = new Object[1];
					o[0] = ssrs.GetText(i, 1);
					fieldValue = (String) m.invoke(mSchema, o);

					System.out.println("原字段名：" + ssrs.GetText(i, 1) + ";目标字段名："
							+ (FIELDNAME + ssrs.GetText(i, 3)) + ";字段值:"
							+ fieldValue);

					// 准备写入数据到备份表
					Class[] coreClass = new Class[2];
					coreClass[0] = String.class;
					coreClass[1] = String.class;
					m = saveSchema.getClass().getMethod("setV", coreClass);

					Object[] coreObject = new Object[2];
					coreObject[0] = FIELDNAME + ssrs.GetText(i, 3);
					coreObject[1] = fieldValue;
					m.invoke(saveSchema, coreObject);
				}
				saveResult.add(saveSchema);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			java.util.List mList = new ArrayList();
			mList.add("7788");
			mList.add("2009");
			mList.add("asd测试操作轨迹");
			mList.add("1");
			mList.add("2");
			mList.add("3");
			mList.add("4");
			mList.add("5");
			mList.add("6");
			mList.add("7");
			mList.add("8");
			mList.add("9");
			mList.add("10");
			mList.add("Y");
			mList.add("最111好一个");

			VData cInputData = new VData();
			TransferData mTransferData = new TransferData();
			mTransferData.setNameAndValue("list", mList);
			mTransferData.setNameAndValue("tableName", "PD_LMRisk");
			mTransferData.setNameAndValue("RiskCode", "4122");
			GlobalInput mGlobalInput = new GlobalInput();
			mGlobalInput.Operator = "system";

			cInputData.add(mGlobalInput);
			cInputData.add(mTransferData);

			PDRiskOperationTrackBL bl = new PDRiskOperationTrackBL();
			boolean flag = bl.submitData(cInputData, "");
			if (flag) {
				PD_LBRiskInfoSchema schema = (PD_LBRiskInfoSchema) bl.saveResult
						.getObjectByObjectName("PD_LBRiskInfoSchema", 0);
				com.sinosoft.lis.pubfun.MMap map = new com.sinosoft.lis.pubfun.MMap();
				map.put(schema, "INSERT");
				com.sinosoft.lis.pubfun.PubSubmit submit = new com.sinosoft.lis.pubfun.PubSubmit();
				VData data = new VData();
				data.add(map);
				if (!submit.submitData(data, "")) {
					System.out.println("备份数据失败");
				} else {
					System.out.println("备份数据[成功]");
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
