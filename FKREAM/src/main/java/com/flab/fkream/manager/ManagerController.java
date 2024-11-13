package com.flab.fkream.manager;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {
	private final ManagerService managerService;

	@PostMapping("")
	public HttpStatus addManager(@RequestBody Manager managerInfo) {
		managerService.addManager(managerInfo);
		return HttpStatus.CREATED;
	}

	@GetMapping("")
	public List<Manager> findAll() {
		return managerService.findAll();
	}

	@GetMapping("/{id}")
	public Manager findOne(@PathVariable Long id) {
		return managerService.findOne(id);
	}
}
