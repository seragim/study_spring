package visual;

import java.util.HashMap;
import java.util.List;

import common.LowerKeyMap;

public interface VisualService {
	List<LowerKeyMap> department_analysis();
	List<LowerKeyMap> hirement_analysis_year();
	List<LowerKeyMap> hirement_analysis_month();
	List<LowerKeyMap> hirement_analysis_top3_year();
	List<LowerKeyMap> hirement_analysis_top3_month();
	

}
