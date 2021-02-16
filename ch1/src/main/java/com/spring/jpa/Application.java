package com.spring.jpa;

import com.spring.jpa.DAO.EmployeeDAO;
import com.spring.jpa.vo.EmployeeVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.util.List;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        main2();
    }

    public static void main2() {
        EmployeeVO vo = new EmployeeVO();
        vo.setName("홍길동");
        vo.setStartDate(new Timestamp(System.currentTimeMillis()));
        vo.setTitle("과장");
        vo.setDeptName("총무부");
        vo.setSalary(2700.00);

        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.insertEmployee(vo);

        List<EmployeeVO> employeeVOList = employeeDAO.getEmployeeList();
        for(EmployeeVO employee : employeeVOList) {
            System.out.println("---> " + employee.toString());
        }
    }
}
