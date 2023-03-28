package Service.MenuService;

import Model.Department;
import Model.Employee;
import Service.DepartmentService.IDepartmentService;
import Service.EmployeeService.EmployeeService;
import Service.DepartmentService.DepartmentService;
import com.sun.tools.javac.Main;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

public class MenuService {
    private static EmployeeService employeeService = new EmployeeService();
    private static DepartmentService departmentService = new DepartmentService();
    private static List<Employee> lstEmp = employeeService.getAll();
    private static List<Department> lstDpm = departmentService.getAll();
    /**
     * Menu chính sau khi đăng nhập
     */
    public static void showMenu() {
        System.out.println("--------------------");
        System.out.println("1.Quản lý nhân viên");
        System.out.println("2.Quản lý phòng ban");
        System.out.println("3.Thoát");
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
                System.out.println("Thoát!");
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
        System.out.println("8.Tìm kiếm nhân viên");
        System.out.println("9.Sắp xếp nhân viên theo lương");
        System.out.println("10.Nhân viên theo phòng ban");
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
                boolean checkAdd = employeeService.add(Department.class, false);
                if(checkAdd){
                    System.out.println("Thêm thành công");}
                else {
                    System.out.println("Thêm thất bại");
                }
//                System.out.println("Bạn có muốn tiếp tục không? (Y/N)");
//                Scanner sc = new Scanner(System.in);
//                String choiceContinue = sc.nextLine();
//                if (choiceContinue.equals("Y")) {
//                    employeeMenuChoose(choice, employeeService);
//                } else {
                    choose(1);
//                }
                break;
            case 2:
                System.out.println("Nhập mã nhân viên cần sửa: ");
                Scanner sc1 = new Scanner(System.in);
                String id = sc1.nextLine();
                if (employeeService.edit(Department.class, id)) {
                    System.out.println("Sửa thành công");
                    choose(1);
                } else {
//                    System.out.println("Sửa thất bại");
                    choose(1);
                }
                ;
                break;
            case 3:
                boolean checkDelete = employeeService.delete();
                choose(1);
//                EmployeeService.deleteEmployee();
                break;
            case 4:
                List<Employee> lstEmp = employeeService.getAll();
//                System.out.printf("%-5s %-10s %-20s %-10s %-10s %-15s %-10s %-10s","STT", "Mã NV", "Tên NV", "Ngày sinh", "Giới tính","SDT","Lương", "Thuế");
                if (lstEmp != null) {
//                    for (int i = 0; i < lstEmp.size(); i++) {
//                        System.out.println();
//                        System.out.printf("%-5s %-10s %-20s %-10s %-10s %-15s %-10s %-10s",
//                                i + 1, lstEmp.get(i).getCode(), lstEmp.get(i).getName(), lstEmp.get(i).getDateOfBirth(),
//                                lstEmp.get(i).getGender() == 1 ? "Nam" : "Nữ",lstEmp.get(i).getPhone(),
//                                lstEmp.get(i).getSalary() != null ? lstEmp.get(i).getSalary().setScale(0, ROUND_HALF_EVEN).toString(): null,
//                                lstEmp.get(i).getSalary() != null ? EmployeeService.getTax(lstEmp.get(i).getSalary()).toString() : null);}
                    printEmpoyee(lstEmp);
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
            case 9:
                List<Employee> lstEmpSort = employeeService.sotedBySalary();
                printEmpoyee(lstEmpSort);
                break;
                case 10:
                    List<Employee> lstEmpByDep = employeeService.getListEmpByDepartment();
                    printEmpoyee(lstEmpByDep);
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
        System.out.println("6.Thay đổi quản lý phòng ban");
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
                printDepartment(lstDep);
                break;
            case 6:
                departmentService.changeDepartmentManager();
                break;
        }
    }

    public static void printDepartment(List<Department> lstDep){
        EmployeeService employeeService = new EmployeeService();
        System.out.println("------------------------------------------Danh sách phòng ban----------------------------------------------------------");
        System.out.printf("| %-5s | %-10s | %-20s | %-35s | %-20s | %-10s |", "STT", "Mã PB", "Tên PB", "Mô tả", "Nguời quản lý", "Mã quản lý");
        System.out.print("\n-----------------------------------------------------------------------------------------------------------------------");
        for(int i = 0; i < lstDep.size(); i++){
            System.out.println();
            System.out.printf("| %-5s | %-10s | %-20s | %-35s | %-20s | %-10s |", i + 1, lstDep.get(i).getCode(), lstDep.get(i).getName(), lstDep.get(i).getDiscription(), getNameManager(lstDep.get(i).getId()).get("name"),getNameManager(lstDep.get(i).getId()).get("code"));
            System.out.print("\n--------------------------------------------------------------------------------------------------------------------");
        }
        System.out.println();
    }

    private static void printEmpoyee(List<Employee> lstEmp){
        System.out.println("-------------------------------------------------Danh sách nhân viên-------------------------------------------------------------------------");
        System.out.printf("%-5s %-10s %-20s %-10s %-10s %-15s %-10s %-10s %-20s %-15s %-20s", "STT", "Mã NV", "Tên NV", "Ngày sinh", "Giới tính", "SDT","Lương", "Thuế", "Phòng ban", "Mã phòng ban","Chức vụ");
        System.out.print("---------------------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < lstEmp.size(); i++) {
            System.out.println();
            System.out.printf("%-5s %-10s %-20s %-10s %-10s %-15s %-10s %-10s %-20s %-15s %-20s",
                    i + 1, lstEmp.get(i).getCode(), lstEmp.get(i).getName(), lstEmp.get(i).getDateOfBirth(),
                    lstEmp.get(i).getGender() == 1 ? "Nam" : "Nữ",lstEmp.get(i).getPhone(),
                    lstEmp.get(i).getSalary() != null ? formatSalary(lstEmp.get(i).getSalary().setScale(0, ROUND_HALF_EVEN)): null,
                    lstEmp.get(i).getSalary() != null ? formatSalary(EmployeeService.getTax(lstEmp.get(i).getSalary())) : null,
                    getDepName(lstEmp.get(i).getDepartmentID()).get("name"), getDepName(lstEmp.get(i).getDepartmentID()).get("code"),
                    checkIsManager(lstEmp.get(i).getIsManager())
                    );
            System.out.print("\n------------------------------------------------------------------------------------------------------------------------------------------");
        }
        System.out.println();
    }

    public static HashMap getNameManager(UUID id){
        HashMap<String, String> map = new HashMap<>();
        for(int i = 0; i < lstEmp.size(); i++){
            if(lstEmp.get(i).getDepartmentID() == null){
                map.put("name", "Không có");
                map.put("code", "Không có");
                continue;
            }
            if(lstEmp.get(i).getDepartmentID().equals(id)){
                map.put("name", lstEmp.get(i).getName());
                map.put("code", lstEmp.get(i).getCode());
                return map;
            }
        }
        return map;
    }

    public static HashMap getDepName(UUID id){
        HashMap<String, String> map = new HashMap<>();
        for(int i = 0; i < lstDpm.size(); i++){
            if(lstDpm.get(i).getId().equals(id)){
                map.put("name", lstDpm.get(i).getName());
                map.put("code", lstDpm.get(i).getCode());
                return map;
            }
        }
        return map;
    }
    public static String checkIsManager(Integer manager){
        if(manager == null) {
            return "Nhân viên";
        }else {
            return "Quản lý";
        }
    }

    public static String formatSalary(BigDecimal salary) {
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(salary);
    }
}
