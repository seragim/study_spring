package visual;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import common.LowerKeyMap;

@Repository
public class VisualDAO implements VisualService {
	@Autowired @Qualifier("hr") private SqlSession sql;

	@Override
	public List<LowerKeyMap> department_analysis() {
		// TODO Auto-generated method stub
		return sql.selectList("visual.mapper.department_analysis");
	}
	
	@Override
	public List<LowerKeyMap> hirement_analysis_year() {
		// TODO Auto-generated method stub
		return sql.selectList("visual.mapper.department_analysis_year");
	}

	@Override
	public List<LowerKeyMap> hirement_analysis_month() {
		// TODO Auto-generated method stub
		return sql.selectList("visual.mapper.department_analysis_month");
	}

	@Override
	public List<LowerKeyMap> hirement_analysis_top3_year() {
		// TODO Auto-generated method stub
		return sql.selectList("visual.mapper.hirement_analysis_top3_year");
	}

	@Override
	public List<LowerKeyMap> hirement_analysis_top3_month() {
		// TODO Auto-generated method stub
		return sql.selectList("visual.mapper.hirement_analysis_top3_month");
	}

}
