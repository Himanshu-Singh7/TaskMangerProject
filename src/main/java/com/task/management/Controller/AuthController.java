package com.task.management.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.task.management.Entity.User;
import com.task.management.Payload.JwtResponse;
import com.task.management.Payload.LoginDto;
import com.task.management.Payload.SignUpDto;
import com.task.management.Repository.RoleRepository;
import com.task.management.Repository.UserRepository;
import com.task.management.Security.JwtHelper;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	 private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
    private AuthenticationManager manager;
   
	
	// http://localhost:9191/api/auth/signin
		@PostMapping("/signin")
	    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginDto loginDto){
			this.doAuthenticate(loginDto.getUsernameOrEmail(), loginDto.getPassword());
			
			    UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsernameOrEmail());
		        String token = this.jwtHelper.generateToken(userDetails);
		        
		        JwtResponse response = JwtResponse.builder()
		                .jwtToken(token)
		                .username(userDetails.getUsername()).build();
		        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    
		private void doAuthenticate(String usernameOrEmail, String password) {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
	        try {
	            manager.authenticate(authentication);


	        } catch (BadCredentialsException e) {
	            throw new BadCredentialsException(" Invalid UsernameOrEmail or Password  !!");
	        }

	    }
		
		 @ExceptionHandler(BadCredentialsException.class)
		    public String exceptionHandler() {

		        return "Credentials Invalid !!";
		    }

		 
		// http://localhost:9191/api/auth/signup

			@PostMapping("/signup")
			public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

				if (userRepository.existsByUsername(signUpDto.getUsername())) {

					return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
				}

				if (userRepository.existsByEmail(signUpDto.getEmail())) {

					return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
				}

				User user = new User();
				user.setName(signUpDto.getName());
				user.setEmail(signUpDto.getEmail());
				user.setUsername(signUpDto.getUsername());
				user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

				User saveUser = userRepository.save(user);

				return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
			}

}
