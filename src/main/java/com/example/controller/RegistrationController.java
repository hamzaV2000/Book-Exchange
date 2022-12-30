package com.example.controller;

import com.example.crm.CrmUser;
import com.example.demo.exception_handling.MyErrorResponse;
import com.example.entity.User;
import com.example.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.logging.Logger;


@RestController
public class RegistrationController {
	
    @Autowired
    private UserService userService;


    private final Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody  CrmUser s, BindingResult bindingResult){

		String emailExist = null;
		String usernameExist = null;
		if(s.getEmail() != null && userService.findByEmail(s.getEmail()) != null)
			emailExist = "Email already exists";

		if(s.getUserName() != null && userService.findByUserName(s.getUserName()) != null)
			usernameExist = "username already exists";

		StringBuilder sb = new StringBuilder();

		if(bindingResult.hasErrors()){
			bindingResult.getAllErrors().forEach(objectError -> sb.append(objectError.getDefaultMessage()+','));
		}

		if(emailExist != null)
			sb.append(emailExist + ',');
		if(usernameExist != null)
			sb.append(usernameExist + ',');


		if(!sb.toString().equals(""))
			return ResponseEntity.badRequest().body(new MyErrorResponse(400, sb.toString().substring(0, sb.length() - 1), LocalDate.now()));

		userService.save(s);
		return ResponseEntity.ok("success");
	}

	@PostMapping("/registerx")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, BindingResult theBindingResult,
										  Model theModel) {
		
		String userName = theCrmUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "error";
	        }

		// check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
        	theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "registration-form";
        }
     // create user account        						
        userService.save(theCrmUser);
        
        logger.info("Successfully created user: " + userName);
        
        return "success";
	}
}
