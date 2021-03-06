/*
* Copyright 2016, Julius Krah
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.jipasoft.domain.dto;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jipasoft.domain.Authority;
import com.jipasoft.domain.User;

import lombok.Data;

/**
 * Data Transfer Object for User. It is bound to the UI for validation
 * 
 * @author Julius Krah
 *
 */
@Data
public class UserDTO {
	private String id;

	@NotNull
	@Pattern(regexp = "^[a-z0-9]*$|(anonymousUser)")
	@Size(min = 1, max = 100)
	private String login;

	@NotNull
	@Size(min = 1, max = 60)
	private String password;

	@NotNull
	@Size(min = 1, max = 50)
	private String firstName;

	@NotNull
	@Size(min = 1, max = 50)
	private String lastName;

	@Email
	@NotNull
	@Size(min = 1, max = 100)
	private String email;

	@NotNull
	private boolean activated = false;

	@NotNull
	private String createdBy = "system";

	@NotNull
	private ZonedDateTime createdDate = ZonedDateTime.now();

	public User createUser(PasswordEncoder encoder) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(encoder.encode(password));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setActivated(activated);
		user.setCreatedBy(createdBy);
		user.setCreatedDate(createdDate);
		user.setAuthorities(Stream.of(new Authority("ROLE_USER")).collect(Collectors.toSet()));

		return user;
	}
}
