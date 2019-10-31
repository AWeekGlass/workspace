package com.hengyu.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hengyu.exam.entity.TestPaperUser;
import com.hengyu.exam.vo.TestUserVo;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author allnas
 * @since 2018-10-24
 */
public interface TestPaperUserDAO extends BaseMapper<TestPaperUser> {

	/**
	 * 查询及格率
	 * 
	 * @param paperId
	 * @return
	 */
	TestUserVo getPassRate(@Param("paperId") Integer paperId);

	/**
	 * 查询批阅率
	 * 
	 * @param paperId
	 * @return
	 */
	TestUserVo getCheckRate(@Param("paperId") Integer paperId);

	/**
	 * 根据用户查询试卷id
	 * 
	 * @param ids
	 * @return
	 */
	List<Integer> getPaperId(@Param("ids") List<Integer> ids);

	/**
	 * 根据部门id查询下属人员
	 * 
	 * @param companyId
	 * @param storeId
	 * @return
	 */
	List<Integer> getUserList(@Param("companyId") Integer companyId, @Param("storeId") Integer storeId);

	boolean updateScore(TestPaperUser testPaperUser);
	
	/**
	 * 根据用户id和试卷id查询考试人员信息
	 * @param testPaperUser
	 * @return
	 */
	TestPaperUser queryTestPaperUser(TestPaperUser testPaperUser);
	
}
