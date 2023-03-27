package Service.MenuService;

import Model.Department;
import Model.Employee;
import Service.EmployeeService.EmployeeService;
import Service.DepartmentService.DepartmentService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

public class MenuService {

    /**
     * Menu chính sau khi đăng nhập
     */
    public static void showMenu() {
        System.out.println("--------------------");
        System.out.println("1.Quản lý nhân viên");
        System.out.println("2.Quản lý phòng ban");
        System.out.println("3.Đăng xuất");
        System.out.println("--------------------");
    }

    /**
     * Lựa chọn menu chính
     */
    public static void choose(int choice) {
        switch (choice) {
            case 1:
                int choiceEmp = menuEmployee();
                EmployeeService employeeService = new EmployeeService();
                try {
                    employeeMenuChoose(choiceEmp, employeeService);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                int choiceDep = menuDepartment();
                departmentMenuChoose(choiceDep);
                break;
            case 3:
                System.out.println("Đăng xuất thành công!");
                break;
            default:
                System.out.println("Vui lòng nhập lại!");
                break;
        }
    }

    /**
     * Menu lựa chọn quan ly nhân viên
     *
     * @return 1 lựa chọn của người dùng
     */
    public static int menuEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------");
        System.out.println("1.Thêm nhân viên");
        System.out.println("2.Sửa nhân viên");
        System.out.println("3.Xóa nhân viên");
        System.out.println("4.Xem danh sách nhân viên");
        System.out.println("6.Xóa nhiều nhân viên theo mã");
        System.out.println("7.Thay đổi phòng ban của nhân viên");
        System.out.println("8. Tìm kiếm nhân viên");
        System.out.println("5.Quay lại");
        System.out.println("--------------------");
        return sc.nextInt();

    }

    /**
     * menu lựa chọn quản lý nhân viên
     *
     * @param choice          lựa chọn của người dùng
     * @param employeeService đối tượng EmployeeService     *
     */
    public static void employeeMenuChoose(int choice, EmployeeService employeeService) throws InstantiationException, IllegalAccessException {
        switch (choice) {
            case 1:
                employeeService.add(Department.class, false);
                System.out.println("Bạn có muốn tiếp tục không? (Y/N)");
                Scanner sc = new Scanner(System.in);
                String choiceContinue = sc.nextLine();
                if (choiceContinue.equals("Y")) {
                    employeeMenuChoose(choice, employeeService);
                } else {
                    choose(1);
                }
                break;
            case 2:
                System.out.println("Nhập mã nhân viên cần sửa: ");
                Scanner sc1 = new Scanner(System.in);
                String id = sc1.nextLine();
                if (employeeService.edit(Department.class, id)) {
                    System.out.println("Sửa thành công");
                    choose(1);
                } else {
                    System.out.println("Sửa thất bại");
                    choose(1);
                }
                ;
                break;
            case 3:
//                EmployeeService.deleteEmployee();
                break;
            case 4:
                List<Employee> lstEmp = employeeService.getAll();
                System.out.printf("%-5s %-10s %-20s %-10s %-10s %-15s %-10s %-10s","STT", "Mã NV", "Tên NV", "Ngày sinh", "Giới tính","SDT","Lương", "Thuế");
                if (lstEmp != null) {
                    for (int i = 0; i < lstEmp.size(); i++) {
                        System.out.println();
                        System.out.printf("%-5s %-10s %-20s %-10s %-10s %-15s %-10s %-10s",
                                i + 1, lstEmp.get(i).getCode(), lstEmp.get(i).getName(), lstEmp.get(i).getDateOfBirth(),
                                lstEmp.get(i).getGender() == 1 ? "Nam" : "Nữ",lstEmp.get(i).getPhone(),
                                lstEmp.get(i).getSalary() != null ? lstEmp.get(i).getSalary().setScale(0, ROUND_HALF_EVEN).toString(): null,
                                lstEmp.get(i).getSalary() != null ? EmployeeService.getTax(lstEmp.get(i).getSalary()).toString() : null);}
                } else {
                    System.out.println("Không có nhân viên nào");
                }
                break;
            case 5:
                System.out.println("Quay lại");
                break;
            case 6:
                employeeService.DeleteBatch();
                break;
            case 7:
                employeeService.changeDepartmentID();
                break;
            case 8:
                List<Employee> lstEmpFilter = employeeService.filter();
                System.out.printf("%-5s %-10s %-20s %-10s %-10s %-15s %-10s %-10s", "STT", "Mã NV", "Tên NV", "Ngày sinh", "Giới tính", "SDT","Lương", "Thuế");
                if (lstEmpFilter != null) {
                    for (int i = 0; i < lstEmpFilter.size(); i++) {
                        System.out.println();
                        System.out.printf("%-5s %-10s %-20s %-10s %-10s %-15s %-10s %-10s",
                                i + 1, lstEmpFilter.get(i).getCode(), lstEmpFilter.get(i).getName(), lstEmpFilter.get(i).getDateOfBirth(), lstEmpFilter.get(i).getGender() == 1 ? "Nam" : "Nữ",lstEmpFilter.get(i).getPhone(),
                                lstEmpFilter.get(i).getSalary() != null ? lstEmpFilter.get(i).getSalary().setScale(0, ROUND_HALF_EVEN).toString(): null,
                                lstEmpFilter.get(i).getSalary() != null ? EmployeeService.getTax(lstEmpFilter.get(i).getSalary()).toString() : null);
                    }
                } else {

                    System.out.println("\nKhông có nhân viên nào");
                }
                break;
            default:
                System.out.println("Vui lòng nhập lại!");
                break;
        }
    }

    /**
     * Menu lựa chọn quản lý phòng ban
     *
     * @return 1 lựa chọn của người dùng
     */
    public static int menuDepartment() {
        System.out.println("--------------------");
        System.out.println("1.Thêm phòng ban");
        System.out.println("2.Sửa phòng ban");
        System.out.println("3.Xóa phòng ban");
        System.out.println("4.Xem danh sách phòng ban");
        System.out.println("5.Quay lại");
        System.out.println("--------------------");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    /**
     * menu lựa chọn quản lý phòng ban
     *
     * @param choiceDep lựa chọn của người dùng     *
     */

    public static void departmentMenuChoose(int choiceDep) {
        DepartmentService departmentService = new DepartmentService(Department.class);
        switch (choiceDep) {
            case 1:
                Department department = new Department(UUID.randomUUID(), "PB01", "Phòng ban 1", "Phòng ban 1");
                try {
                    departmentService.add(null, false);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                System.out.println("Nhập mã phòng ban cần sửa: ");
                Scanner sc1 = new Scanner(System.in);
                String id = sc1.nextLine();
                departmentService.edit(null, id);
                break;
            case 3:
                departmentService.delete();
                break;
            case 4:
                List<Department> lstDep = departmentService.getAll();
                lstDep.forEach(System.out::println);
                break;
        }
    }
}
