package com.hengyu.exam.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hengyu.exam.dao.QuestionCityDAO;
import com.hengyu.exam.dao.QuestionDAO;
import com.hengyu.exam.entity.Question;
import com.hengyu.exam.entity.QuestionCity;
import com.hengyu.exam.po.SearchQuestionPo;
import com.hengyu.exam.po.UpdateStatePo;
import com.hengyu.exam.service.QuestionCityService;
import com.hengyu.exam.service.QuestionService;
import com.hengyu.exam.vo.CountVo;
import com.hengyu.exam.vo.QuestionVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author allnas
 * @since 2018-09-04
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionDAO, Question> implements QuestionService {

	@Autowired
	private QuestionDAO questionDAO;

	@Autowired
	private QuestionCityDAO questionCityDAO;

	@Autowired
	private QuestionCityService cityService;

	@Override
	public Page<QuestionVo> queryQuestionsByCompanyId(Page<QuestionVo> page, SearchQuestionPo po) {
		if(po.getOrderRule()=="" || Objects.equals("asc", po.getOrderRule()) || Objects.equals("ASC",  po.getOrderRule())){
			po.setOrderRule(null);
		}
		List<QuestionVo> list = questionDAO.queryQuestionsByCompanyId(page, po);
		page.setRecords(list);
		return page;
	}

	@Override
	public QuestionVo queryDetailById(Integer id,Integer state) {
		QuestionVo vo = questionDAO.queryDetailById(id);
		List<QuestionCity> cityList = questionCityDAO.getListByQuestionId(id, state);
		vo.setCityList(cityList);
		return questionDAO.queryDetailById(id);
	}

	@Override
	public boolean add(Question question) {
		boolean flag = insert(question);
		// 循环添加关系
		if (flag && Objects.nonNull(question.getCityId()) && question.getCityId() != "") {
			String city = question.getCityId();
			List<String> list = Arrays.asList(city.split(","));
			for (String id : list) {
				QuestionCity qCity = new QuestionCity();
				qCity.setQuestionId(question.getId());
				qCity.setCityId(Integer.valueOf(id));
				qCity.setCompanyId(question.getCompanyId());
				questionCityDAO.insert(qCity);
			}
		}
		return flag;
	}

	@Override
	public boolean delete(String id, String city, Integer type, Integer companyId) {
		boolean flag = false;
		// 如果是删除审核的题目
		if (Objects.equals(type, "1")) {
			flag = deleteById(Integer.valueOf(id));
			flag = cityService
					.delete(new EntityWrapper<QuestionCity>().eq("question_id", id).eq("company_id", companyId));
			if (Objects.nonNull(city) && city != "") {
				// 重新添加
				List<String> list = Arrays.asList(city.split(","));
				for (String id_ : list) {
					QuestionCity qCity = new QuestionCity();
					qCity.setQuestionId(Integer.valueOf(id));
					qCity.setCompanyId(companyId);
					qCity.setCityId(Integer.valueOf(id_));
					cityService.insert(qCity);
				}
			}
		} else {// 删除通过审核的题目
			// 根据题目id查询关联的未删除的城市
			List<QuestionCity> cityList = questionCityDAO.queryId(id);
			if (Objects.nonNull(city) && city != "") {
				// 获取未删除的城市
				List<String> list = Arrays.asList(city.split(","));
				// 如果未删除的城市不包含该城市则设置为闲置状态
				for (QuestionCity questionCity : cityList) {
					if (!list.contains(questionCity.getCityId().toString())) {
						questionCity.setState(2);
						flag = cityService.updateById(questionCity);
					}
				}
			} else {// 如果不穿城市，则默认删除该题目和该题目下所有城市

				Question question = new Question();
				question.setStatus(4);
				question.setId(Integer.valueOf(id));
				// 将题目设置成闲置
				flag = updateById(question);
				// 将城市设置成闲置
				for (QuestionCity questionCity : cityList) {
					questionCity.setState(2);
					cityService.updateById(questionCity);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean update(Question question) {
		boolean flag = updateById(question);
		String city = question.getCityId();
		if (flag && Objects.nonNull(city) && city != "") {
			// 先删除所有关系
			questionCityDAO.delete(new EntityWrapper<QuestionCity>().eq("question_id", question.getId()));
			// 重新添加
			if (Objects.nonNull(city) && city != "") {
				List<String> list = Arrays.asList(city.split(","));
				for (String id : list) {
					QuestionCity qCity = new QuestionCity();
					qCity.setQuestionId(question.getId());
					qCity.setCityId(Integer.valueOf(id));
					qCity.setCompanyId(question.getCompanyId());
					questionCityDAO.insert(qCity);
				}
			}
		}

		return flag;
	}

	@Override
	public List<CountVo> count(Integer companyId, String cityIds) {
		List<String> ids = new ArrayList<>();
		if(Objects.nonNull(cityIds) && cityIds!=""){
			ids = Arrays.asList(cityIds.split(","));
		}
		List<CountVo> list = questionDAO.count(companyId, ids);
		return list;
	}

	@Override
	public Page<QuestionVo> queryRecovery(Page<QuestionVo> page, SearchQuestionPo po) {
		//如果根据城市查询则直接赋值
		if(Objects.nonNull(po) && Objects.nonNull(po.getCityId())){
			List<String> cityIds = new ArrayList<>();
			cityIds = Arrays.asList(po.getCityId().split(","));
			po.setCityIds(cityIds);
		}
		// 查询题目
		List<QuestionVo> questionList = questionDAO.queryRecovery(page, po);
		// 查询题目对应的城市
		for (QuestionVo questionVo : questionList) {
			Integer state = 2;
			List<QuestionCity> cityList = questionCityDAO.getListByQuestionId(questionVo.getId(), state);
			questionVo.setCityList(cityList);
		}
		page.setRecords(questionList);
		return page;
	}

	@Override
	public boolean updateState(UpdateStatePo po) {
		// 更新题目状态
		Question question = new Question();
		question.setStatus(1);
		question.setId(po.getQuestionId());
		boolean flag = updateById(question);

		// 更新题目城市关系状态
		List<String> ids = Arrays.asList(po.getCityIds().split(","));
		  ids.stream().filter(id -> StringUtils.isNoneEmpty(id)).forEach(id -> {
			   QuestionCity city = new QuestionCity();
			   city.setCityId(Integer.valueOf(id));
			   city.setQuestionId(po.getQuestionId());
			   city.setState(1);
			   questionCityDAO.update(city, new EntityWrapper<QuestionCity>().eq("city_id", id)
			     .eq("question_id", po.getQuestionId()).eq("company_id", po.getCompanyId()));
			  });
		return flag;
	}
}