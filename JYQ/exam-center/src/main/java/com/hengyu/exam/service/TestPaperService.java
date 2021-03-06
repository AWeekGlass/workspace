package com.hengyu.exam.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.hengyu.exam.entity.TestPaper;
import com.hengyu.exam.po.QuerPaperDetailPo;
import com.hengyu.exam.po.QueryPastPo;
import com.hengyu.exam.po.TestPaperSubordinatePo;
import com.hengyu.exam.vo.AdminVo;
import com.hengyu.exam.vo.StateVo;
import com.hengyu.exam.vo.TestPaperFullVo;
import com.hengyu.exam.vo.TestPaperSubordinateVo;
import com.hengyu.exam.vo.TestPaperVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
public interface TestPaperService extends IService<TestPaper> {
	boolean updateDelFlag(List<String> ids);

	TestPaperFullVo queryById(Integer id);

	Page<TestPaperVo> queryList(Page<TestPaperVo> page, TestPaper testPaper);

	Page<TestPaperVo> queryMyList(Page<TestPaperVo> page, Integer userId, Integer state);

	boolean save(TestPaper testPaper);

	boolean update(TestPaper testPaper);

	TestPaperFullVo queyDetailByUser(QuerPaperDetailPo po);

	/**
	 * 发送短信
	 * 
	 * @param paperId
	 * @param phone
	 * @return
	 */
	boolean sendMessage(Integer paperId, String userId);

	String getPhone(String userId);

	/**
	 * 查询批阅列表
	 * 
	 * @param userId
	 * @return
	 */
	Page<TestPaperVo> queryCheckList(Page<TestPaperVo> page, Integer userId, Integer type);

	Page<TestPaperSubordinateVo> querySubordinate(Page<TestPaperSubordinateVo> page,
			TestPaperSubordinatePo testPaperSubordinatePo);
	
	Page<TestPaperVo> queryPastList(Page<TestPaperVo> page,QueryPastPo queryPastPo);

	Page<TestPaperVo> queryMyExamList(Page<TestPaperVo> page, TestPaper testPaper);
	
	List<StateVo> getTestState(Integer paperId, Integer type);

	List<AdminVo> queryPaperUser(Integer companyId);
	
	Page<TestPaperVo> queryTodo(Page<TestPaperVo> page,Integer userId);
	
	Integer countUserInfo(Integer userId);
}