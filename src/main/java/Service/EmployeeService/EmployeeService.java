package Service.EmployeeService;

import Common.Enums.MilestonesSalary;
import DAO.EmployeeDAO.EmployeeDAO;
import DAO.EmployeeDAO.IEmployeeDAO;
import Model.Department;
import Model.Employee;
import Service.BaseService.Base;
import Service.DepartmentService.DepartmentService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Function;

public class EmployeeService extends Base<Employee> implements IEmployeeService {
    private IEmployeeDAO _empDAO;

    public EmployeeService() {
        super(Employee.class);
        _empDAO = new EmployeeDAO(Employee.class);
    }

    public EmployeeService(Class<Employee> employeeClass, IEmployeeDAO empDAO) {
        super(employeeClass);
        _empDAO = empDAO;
    }


    @Override
    public boolean DeleteBatch() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập 1 mảng mã nhân viên cần xóa ( cách nhau bởi dấu space ): ");
        String[] listCode = sc.nextLine().split(" ");
        System.out.println("Bạn có muốn xóa"+ listCode.length +" không? (Y/N)");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("n")) {
            return false;
        } else if (choice.equals("Y") || choice.equals("y")) {
            return new EmployeeDAO(Employee.class).DeleteBatch(listCode);
        } else {
            return false;
        }
    }


    @Override
    public boolean changeDepartmentID() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã nhân viên cần thay đổi phòng ban: ");
        String code = sc.nextLine();
        Employee emp = _empDAO.getByCode(code);
        if (emp == null) {
            System.out.println("Không tìm thấy nhân viên này");
            return false;
        }
        System.out.println("Tên: " + emp.getName() + " Mã: " + emp.getCode() + " Phòng ban: " + emp.getDepartmentID());
        DepartmentService departmentService = new DepartmentService();
        List<Department> lstDpm = departmentService.getAll();
        System.out.printf("%-10s %-10s %-20s %-50s", "STT", "Mã phòng ban", "Tên phòng ban", "Mô tả");
        for (int i = 0; i < lstDpm.size(); i++) {
            System.out.println();
            System.out.printf("%-10s %-10s %-20s %-50s", i + 1, lstDpm.get(i).getCode(), lstDpm.get(i).getName(), lstDpm.get(i).getDiscription());
        }

        System.out.println("\nNhập mã phòng ban mới: ");
        String newDepartmentID = sc.nextLine();
        UUID newID = UUID.fromString(lstDpm.get(Integer.parseInt(newDepartmentID) - 1).getId().toString());
        return _empDAO.changeDepartmentID(code, newID);
//        return _empDAO.changeDepartmentID(code, newDepartmentID);
    }

    @Override
    public List<Employee> filter() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ khóa cần tìm kiếm: ");
        String keyword = sc.nextLine();
        return _empDAO.filter(keyword);
    }

    @Override
    public List<Employee> sotedBySalary() {
        return _empDAO.sotedBySalary();
    }

    @Override
    public List<Employee> getListEmpByDepartment() {
        Scanner sc = new Scanner(System.in);
        DepartmentService departmentService = new DepartmentService();
        List<Department> lstDpm = departmentService.getAll();
        printDepartment(lstDpm);
        System.out.println("Nhập mã phòng ban cần lấy danh sách nhân viên: ");
        String Code = sc.nextLine();
        Department department = lstDpm.stream().filter(d -> d.getCode().equalsIgnoreCase(Code)).findFirst().orElse(null);
        Function<Department, UUID> getID = (Department d) -> d.getId();
        return _empDAO.getListEmpByDepartment(getID.apply(department));
    }

    // tính thuế sau khi giảm trừ phụ thuộc
    public static boolean checkSalaryWithOutDepen(BigDecimal salary) {
        BigDecimal salaryWithOutDepen = salary.subtract(new BigDecimal(11000000));
        if (salaryWithOutDepen.compareTo(new BigDecimal(0)) == 1) {
            return true;
        }
        return false;
    }

    public static BigDecimal calcTax(BigDecimal salary) {

        if (salary.compareTo(MilestonesSalary.Salary_5M) <= 0) {
            return salary.multiply(new BigDecimal(0.05));
        } else if (salary.compareTo(MilestonesSalary.Salary_10M) <= 0) {
            return calcTax(MilestonesSalary.Salary_5M)
                    .add(salary.subtract(MilestonesSalary.Salary_5M)
                            .multiply(new BigDecimal(0.1)));
        } else if (salary.compareTo(MilestonesSalary.Salary_18M) <= 0) {
            return calcTax(MilestonesSalary.Salary_10M)
                    .add(salary.subtract(MilestonesSalary.Salary_10M)
                            .multiply(new BigDecimal(0.15)));
        } else if (salary.compareTo(MilestonesSalary.Salary_32M) <= 0) {
            return calcTax(MilestonesSalary.Salary_18M)
                    .add(salary.subtract(MilestonesSalary.Salary_18M)
                            .multiply(new BigDecimal(0.2)));
        } else if (salary.compareTo(MilestonesSalary.Salary_52M) <= 0) {
            return calcTax(MilestonesSalary.Salary_32M)
                    .add(salary.subtract(MilestonesSalary.Salary_32M)
                            .multiply(new BigDecimal(0.25)));
        } else if (salary.compareTo(MilestonesSalary.Salary_80M) <= 0) {
            return calcTax(MilestonesSalary.Salary_52M)
                    .add(salary.subtract(MilestonesSalary.Salary_52M)
                            .multiply(new BigDecimal(0.3)));
        } else {
            return calcTax(MilestonesSalary.Salary_80M)
                    .add(salary.subtract(MilestonesSalary.Salary_80M)
                            .multiply(new BigDecimal(0.35)));
        }

    }

    public static BigDecimal getTax(BigDecimal salary) {
        if (checkSalaryWithOutDepen(salary)) {
            salary = salary.subtract(new BigDecimal(11000000));
            return calcTax(salary).setScale(0, BigDecimal.ROUND_HALF_UP);
        }
        return new BigDecimal(0);
    }

    @Override
    public boolean customCheck(Employee e) {
        DepartmentService departmentService = new DepartmentService();
        if (departmentService.checkDepartmentHasManager(e.getDepartmentID())) {
            return false;
        } else {
            return true;
        }
    }

    public static void printDepartment(List<Department> lstDep){
        System.out.println("------------------------------------------Danh sách phòng ban----------------------------------------------------------");
        System.out.printf("| %-5s | %-10s | %-20s | %-35s", "STT", "Mã PB", "Tên PB", "Mô tả");
        System.out.print("\n-----------------------------------------------------------------------------------------------------------------------");
        for(int i = 0; i < lstDep.size(); i++){
            System.out.println();
            System.out.printf("| %-5s | %-10s | %-20s | %-35s", i + 1, lstDep.get(i).getCode(), lstDep.get(i).getName(), lstDep.get(i).getDiscription());
            System.out.print("\n--------------------------------------------------------------------------------------------------------------------");
        }
        System.out.println();
    }

}
