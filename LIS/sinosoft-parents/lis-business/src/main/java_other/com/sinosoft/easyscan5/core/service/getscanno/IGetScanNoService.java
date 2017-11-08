package com.sinosoft.easyscan5.core.service.getscanno;

import java.util.List;

import com.sinosoft.easyscan5.base.service.BaseService;
import com.sinosoft.easyscan5.core.vo.easyscan.GetScanNoVo;

public interface IGetScanNoService extends BaseService {

	/**
	 * 申请新箱号
	 * @param paramMap
	 * @return
	 */
	public String getNewScanNo(GetScanNoVo getScanNoVo);
	/**
	 * 查询历史箱号
	 * @param paramMap
	 * @return
	 */
	public String queryHisScanNo(GetScanNoVo getScanNoVo);
	/**
	 * 获取新箱号结果
	 * @return
	 */
	public String getNewScanNoResult();
	/**
	 * 获取历史箱号结果
	 * @return
	 */
	public List getHisScanNoResult();
}
