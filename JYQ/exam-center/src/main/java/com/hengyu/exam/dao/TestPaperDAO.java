package com.hengyu.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hengyu.exam.entity.TestPaper;
import com.hengyu.exam.po.QuerPaperDetailPo;
import com.hengyu.exam.po.QueryPastPo;
import com.hengyu.exam.po.TestPaperSubordinatePo;
import com.hengyu.exam.vo.AdminVo;
import com.hengyu.exam.vo.SendMessageVo;
import com.hengyu.exam.vo.StateVo;
import com.hengyu.exam.vo.TestPaperFullVo;
import com.hengyu.exam.vo.TestPaperSubordinateVo;
import com.hengyu.exam.vo.TestPaperVo;
import com.hengyu.exam.vo.TestUserVo;
import com.hengyu.exam.vo.UserVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
public interface TestPaperDAO extends BaseMapper<TestPaper> {
	TestPaperFullVo queryById(@Param("id") Integer id);

	List<TestPaperVo> queryList(Pagination pagination, TestPaper testPaper);

	List<TestPaperVo> queryMyExamList(Pagination pagination, TestPaper testPaper);

	List<TestPaperVo> queryMyList(Pagination pagination, @Param("userId") Integer userId,
			@Param("state") Integer state);

	/**
	 * 查询发送短信信息
	 * @return
	 */
	SendMessageVo sendMessage(@Param("userId")Integer userId);
	
	String getPhone(@Param("userId") String userId);

	Integer score(@Param("paperId") Integer paperId);

	List<AdminVo> getUserIds(Integer id);

	List<UserVo> getUsers(Integer paperId);

	TestPaperFullVo queyDetailByUser(QuerPaperDetailPo po);

	List<TestPaperVo> queryCheckList(Pagination page, @Param("userId") Integer userId, @Param("type") Integer type);

	/**
	 * 获取参考率
	 * 
	 * @param paperId
	 * @return
	 */
	TestUserVo getTestedRate(Integer paperId);

	List<TestPaperSubordinateVo> querySubordinate(Pagination pagination, TestPaperSubordinatePo testPaperSubordinatePo);

	List<TestPaperVo> queryPastList(Pagination pagination, QueryPastPo queryPastPo);

	/**
	 * 查询参考率中的学员状态
	 * 
	 * @param testPaperId
	 * @return
	 */
	List<StateVo> getTestUser(Integer paperId);

	/**
	 * 查询参考率中学员状态
	 * 
	 * @param testPaperId
	 * @return
	 */
	List<StateVo> getPassUser(Integer paperId);

	List<AdminVo> queryPaperUser(@Param("companyId") Integer companyId);
	
	List<TestPaperVo> queryTodo(Pagination pagination,@Param("userId")Integer userId);
	
	Integer countUserInfo(@Param("userId")Integer userId);
	
	UserVo getUserName(@Param("id")Integer id);
}