package employee;

import java.util.List;

public interface EmployeeService {
	List<EmployeeVO> employee_list(); //사원목록조회
	List<EmployeeVO> employee_list(String depts); //사원목록조회-부서선택한 경우
	EmployeeVO employee_detail(int id);//사원상세조회
	List<DepartmentVO> employee_department();//사원이 소속된 부서조회
}
