package employee;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAO implements EmployeeService {
	@Autowired @Qualifier("hr") private SqlSession sql;
	
	@Override //전체 사원목록
	public List<EmployeeVO> employee_list() {
		return sql.selectList("employee.mapper.list");
	}

	@Override //특정사번의 사원정보
	public EmployeeVO employee_detail(int id) {
		return sql.selectOne("employee.mapper.detail", id);
	}

	@Override //부서정보목록
	public List<DepartmentVO> employee_department() {
		List<DepartmentVO> list = sql.selectList("employee.mapper.department");
		return list;
//				sql.selectList("employee.mapper.department");
	}

	@Override  //특정부서에 속한 사원목록
	public List<EmployeeVO> employee_list(String depts) {
		//String --> int : Integer.parseInt
		//String --> Integer : Integer.valueOf --> int(Auto Unboxing)
		return sql.selectList("employee.mapper.depts_list"
										, Integer.parseInt(depts));
	}

}







