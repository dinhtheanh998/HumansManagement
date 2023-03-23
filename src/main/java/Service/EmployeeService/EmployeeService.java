package Service.EmployeeService;

import Common.Enums.MilestonesSalary;
import DAO.EmployeeDAO.EmployeeDAO;
import Model.Employee;
import Service.BaseService.Base;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class EmployeeService extends Base<Employee> implements IEmployeeService {

    public EmployeeService(Class<Employee> employeeClass) {
        super(employeeClass);
    }


    @Override
    public boolean DeleteBatch() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập 1 mảng mã nhân viên cần xóa ( cách nhau bởi dấu space ): ");
        String[] listCode = sc.nextLine().split(" ");
        return new EmployeeDAO(Employee.class).DeleteBatch(listCode);
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
    public boolean customCheck(){
        return true;
    }
}
