package visual;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.LowerKeyMap;

@Service
public class VisualServiceImpl implements VisualService {
	@Autowired private VisualDAO dao;

	@Override
	public List<LowerKeyMap> department_analysis() {
		// TODO Auto-generated method stub
		return dao.department_analysis();
	}

	@Override
	public List<LowerKeyMap> hirement_analysis() {
		// TODO Auto-generated method stub
		return null;
	}

}
