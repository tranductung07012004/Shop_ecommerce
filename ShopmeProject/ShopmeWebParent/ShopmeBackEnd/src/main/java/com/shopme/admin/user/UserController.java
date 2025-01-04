package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import org.springframework.ui.Model;

@Controller
public class UserController {
	
	@Autowired  // Tự khởi tạo và quản lí vòng đời.
	private UserService service;
	
	@GetMapping("/users")  // Phương thức GetHTTP, 
						   // phương án khác tổng quát hơn là @RequestMapping,
						   // cách này thì tổng quát hơn khi xử lí Get/Post/Put/Delete
	public String listAll(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
	
	@GetMapping("/users/new") // Trước tiên là tạo handler GET 
							  // để trả về giao diện, chỉ giao diện thôi
							  // cho client khi client request lúc
							  // nhấn vào link "create new user" trong users.html
	public String newUser(Model model) {
		
		List <Role> listRoles = service.listRole();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		// Dùng RedirectAttribute là bởi vì chỗ này chúng ta return về redirect, ko thể dùng Model như hàm trên.
		System.out.println(user);
		service.save(user);
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");
		return "redirect:/users";  // trả về endpoint "/users", 
								   // đồng thời ở phía client cũng sẽ gọi request đến endpoint này
								   // Mục đích là để load lại UI trang users.
	}
}
