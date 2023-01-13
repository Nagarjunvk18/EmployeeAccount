package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.entity.Account;
import com.cg.entity.Employee;
import com.cg.exception.NoSuchEmployeeFoundException;
import com.cg.repository.EmployeeRepo;

class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepo empRepo;

	@InjectMocks
	private EmployeeService eService;

	Employee emp = new Employee();
	List<Employee> empList = new ArrayList<Employee>();

	@BeforeEach
	public void setUp() {
		// initialize mocks
		eService = new EmployeeServiceImpl(empRepo);
		MockitoAnnotations.openMocks(this);
		Account acc = new Account();
		acc.setAccNo(1234);
		acc.setBankName("sbi");
		acc.setAccBal(1000);
		emp.setEid(1);
		emp.setEname("kim");
		emp.setEsal(19000);
		emp.setEaccount(acc);

		empList.add(emp);
	}

	// test method for adding an employee

	@Test
	@Order(1)
	public void testAddEmployee() {
		// Here Saving the object to the mocked object
		when(eService.addEmployee(emp)).thenReturn(emp);
		assertEquals(eService.addEmployee(emp), emp);
	}

	// test method for displaying the employeeList

	@Test
	@Order(2)
	public void testGetAllEmployees() {
		when(eService.getAllEmployees()).thenReturn(empList);
		assertEquals(eService.getAllEmployees(), empList);
	}

	// test the findEmpById exception case

	@Test
	public void testFindEmpByIdException() {
		try {
			when(eService.findEmpById(1)).thenReturn(null);
		} catch (NoSuchEmployeeFoundException e) {
		}
		assertThrows(NoSuchEmployeeFoundException.class, () -> {
			eService.findEmpById(1);
		});
	}

	// test the deleteEmpById

	@Test
	public void testDeleteEmpById() throws NoSuchEmployeeFoundException {
		boolean isDeleted = eService.deleteEmployee(1);
		verify(empRepo, times(1)).deleteById(1);
		assertTrue(isDeleted);
	}

	@Test
	public void testUpdateEmployee() {

		emp.setEsal(29000);
		try {
			when(eService.updateEmp(1, emp)).thenReturn(emp);
		} catch (NoSuchEmployeeFoundException e) {
		}

		try {
			assertEquals(eService.updateEmp(1, emp), emp);
		} catch (NoSuchEmployeeFoundException e) {

		}
	}

	@Test
	public void testFindEmpById() {
		try {
			when(eService.findEmpById(2)).thenReturn(emp);
			assertEquals(emp,eService.findEmpById(1));
		} catch (NoSuchEmployeeFoundException e) {
		}
	}

	@Test
	public void testGetEmpDetailsByAccNo() throws NoSuchEmployeeFoundException {
	    when(eService.getEmpDetailsByAccNo(1234)).thenReturn(emp);
	    assertEquals(eService.getEmpDetailsByAccNo(1234), null);
	}

}
