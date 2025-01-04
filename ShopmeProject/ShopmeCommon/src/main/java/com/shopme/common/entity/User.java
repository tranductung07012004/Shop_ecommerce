package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=128, nullable=false, unique=true)
	private String email;
	
	@Column(length=64, nullable=false)
	private String password;
	
	@Column(name = "first_name", length=45, nullable=false)
	private String firstName;
	
	@Column(name = "last_name", length=45, nullable=false)
	private String lastName;
	
	@Column(length=64)
	private String photos;
	
	
	private boolean enabled;
	
	// Xác định mối quan hệ nhiều - nhiều giữa hai entity (một người dùng có thể có nhiều Role,
	//và một vai trò (Role) cũng có thể thuộc về nhiều người dùng 
	@ManyToMany
	// Định nghĩa bảng trung gian (join table)
	// trong db để lưu trữ mối quan hệ giữa hai entity
	@JoinTable(  
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),  // @JoinColumn ở đây sẽ được JPA hiểu để tự động tạo khóa ngoại đến bảng user hiện tại
			inverseJoinColumns = @JoinColumn(name = "role_id")  // @JoinColumn ở đây sẽ được JPA hiểu để tự động tạo khóa ngoại đến bảng role.
			)  
	private Set<Role> roles = new HashSet<>();  // Một user có thể có nhiều role khác nhau.
	// Set được sử dụng vì nó là một interface trong java collection framework, là một tập
	// hợp các phần tử không trùng lặp, không duy trì thứ tự chèn.
	
	// HashSet là lớp triển khai (implements) của interface Set.
	// Đặc điểm: không cho phép trùng lặp phần tử, Không bảo đảm thứ tự phần tử.
	// Hiệu suất cao cho các thao tác cơ bản như thêm, xóa, kiểm tra tồn tại 
	// (trung bình là O(1)).
	
	public User() {}
	
	
	public User(String email, String password, String firstname, String lastname) {
		this.email = email;
		this.password = password;
		this.firstName = firstname;
		this.lastName = lastname;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstname=" + firstName + ", lastname=" + lastName
				+ ", roles=" + roles + "]";
	}
	
	
}
