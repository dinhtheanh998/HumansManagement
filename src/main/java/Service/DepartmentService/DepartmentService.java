package Service.DepartmentService;

import DAO.DepartmentDAO.DepartmentDAO;
import DAO.DepartmentDAO.IDepartmentDAO;
import Model.Department;
import Model.Employee;
import Service.BaseService.Base;
import Service.EmployeeService.EmployeeService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class DepartmentService extends Base<Department> implements IDepartmentService {
    private final IDepartmentDAO _departmentDAO;

    public DepartmentService() {
        super(Department.class);
        _departmentDAO = new DepartmentDAO(Department.class);
    }

    public DepartmentService(Class<Department> departmentClass) {
        super(Department.class);
        _departmentDAO = new DepartmentDAO(Department.class);
    }

    public DepartmentService(Class<Department> departmentClass, IDepartmentDAO departmentDAO) {
        super(departmentClass);
        _departmentDAO = departmentDAO;
    }

    @Override
    public boolean checkDepartmentHasManager(UUID id) {
        return _departmentDAO.checkDepartmentHasManager(id);
    }

    @Override
    public boolean changeDepartmentManager() {
        EmployeeService employeeService = new EmployeeService();
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập vào mã của phòng ban muốn thay đổi người quản lý: ");
        String departmentId = sc.nextLine();
        Department dep = _departmentDAO.getByCode(departmentId);
        UUID departmentUUID = null;
        if (dep == null) {
            System.out.println("Không tìm thấy phòng ban này");
            return false;
        } else {
            departmentUUID = dep.getId();
        }
        //select all emp in department
        List<Employee> list = employeeService.getAll().stream().filter(employee -> {
            if (employee.getDepartmentID() == null) return false;
            return employee.getDepartmentID().equals(dep.getId());
        }).toList();
        if (list.size() == 0) {
            System.out.println("Phòng ban này không có nhân viên nào");
            return false;
        }
        System.out.printf("%-5s %-10s %-20s", "STT", "Mã NV", "Họ và tên");
        int length = list.size();
        for (int i = 0; i < length; i++) {
            System.out.println();
            System.out.printf("%-5s %-10s %-20s", i + 1, list.get(i).getCode(), list.get(i).getName());
        }
        System.out.println();
        System.out.println("Nhập mã nhân viên muốn làm người quản lý: ");
        String employeeCode = sc.nextLine();
        String lastEmpCode = employeeCode;
        boolean checkEmp = list.stream().anyMatch(employee -> employee.getCode().equals(lastEmpCode));
        while (!checkEmp) {
            System.out.println("Nhập sai mã nhân viên, vui lòng nhập lại: ");
            employeeCode = sc.nextLine();
            String temp = employeeCode;
            checkEmp = list.stream().anyMatch(employee -> employee.getCode().equals(temp));
        }
        return _departmentDAO.changeDepartmentManager(departmentUUID, employeeCode);
        //get all employee in department id
    }

}
