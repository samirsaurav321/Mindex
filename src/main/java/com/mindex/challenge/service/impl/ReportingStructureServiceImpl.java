package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

	@Autowired
	private EmployeeRepository emmployeeRepository;

	@Override
	public ReportingStructure read(String id) {
		LOG.debug("Creating ReportingStructure with id [{}]", id);
		Employee employee = emmployeeRepository.findByEmployeeId(id);
		if (employee == null) {
			throw new RuntimeException("Invalid employeeId: " + id);
		}
		int directReports = getTotalDirectReports(id);

		ReportingStructure reports = new ReportingStructure();
		reports.setNumberOfReports(directReports);
		reports.setEmployee(employee);

		return reports;
	}

	private int getTotalDirectReports(String id) {
		int n = 0;
		Employee employee = emmployeeRepository.findByEmployeeId(id);
		if (employee == null) {
			throw new RuntimeException("Invalid employeeId: " + id);
		}
		
		if (employee.getDirectReports() != null) {
			n = employee.getDirectReports().size();
			for (int i = 0; i < employee.getDirectReports().size(); i++) {
				n = n + getTotalDirectReports(employee.getDirectReports().get(i).getEmployeeId());
			}
		}

		return n;
	}
}