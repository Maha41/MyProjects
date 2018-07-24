package com.neu.me;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.me.dao.UserDao;
import com.neu.me.exception.UserException;

import com.neu.me.pojo.useraccount;

import com.neu.me.validator.UserValidator;

@Controller
@RequestMapping("/*Profile.htm")
public class ProfileController {

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	@Qualifier("regUserValidator")
	UserValidator userValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	@RequestMapping(value = "/updateProfile.htm", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("userAccount") useraccount userAccount, BindingResult result,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {

			HttpSession session = request.getSession(false);
			String username = (String) session.getAttribute("username");
			String role = (String) session.getAttribute("role");

			useraccount uacc = userDao.get(username); // retriving userdetails
														// from database
			String pass = base64Decode(uacc.getPassword());
			userAccount.setUsername(uacc.getUsername());
			userAccount.setPassword(pass);
			userAccount.setRole(uacc.getRole());
			userAccount.setStatus(uacc.getStatus());
			userAccount.setPerson(uacc.getPerson());
			userAccount.setId(uacc.getId());

			mv.addObject("task", "addPerson");
			mv.addObject("message", "");
			if (role.equalsIgnoreCase("admin"))
				mv.setViewName("admin/adminHome");
			else if (role.equalsIgnoreCase("expert"))
				mv.setViewName("expert/expertHome");
			else
				mv.setViewName("user/userHome");
		} catch (UserException e) {
			mv.addObject("error", "User Details not found");

		}
		return mv;
	}

	@RequestMapping(value = "/updateProfile.htm", method = RequestMethod.POST)
	public ModelAndView edithome(@ModelAttribute("userAccount") useraccount userAccount, BindingResult result,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		userValidator.validate(userAccount, result);
		if (result.hasErrors()) {

		}
		try {
			HttpSession session = request.getSession(false);
			String role = (String) session.getAttribute("role");
			String pass = base64Encode(userAccount.getPassword());
			userAccount.setPassword(pass);

			userAccount.getPerson().setUserAccount(userAccount);
			userDao.update(userAccount);

			mv.addObject("task", "addPerson");
			mv.addObject("message", "Profile update successfully");
			if (role.equalsIgnoreCase("admin"))
				mv.setViewName("admin/adminHome");
			else if (role.equalsIgnoreCase("expert"))
				mv.setViewName("expert/expertHome");
			else
				mv.setViewName("user/userHome");
		} catch (UserException e) {
			mv.addObject("message", "User Details not found");

		}
		return mv;
	}

	public static String base64Decode(String token) {
		byte[] encodedBytes = Base64.decode(token.getBytes());
		return new String(encodedBytes, Charset.forName("UTF-8"));
	}

	public static String base64Encode(String token) {
		byte[] encodedBytes = Base64.encode(token.getBytes());
		return new String(encodedBytes, Charset.forName("UTF-8"));
	}

	@RequestMapping(value = "/SignupProfile.htm", method = RequestMethod.GET)
	public ModelAndView sinuphome() {

		ModelAndView mv = new ModelAndView();
		mv.addObject("task", "addPerson");
		mv.addObject("message", "");
		mv.setViewName("guest");

		return mv;
	}

	@RequestMapping(value = "/SignupProfile.htm", method = RequestMethod.POST)
	public ModelAndView sinupsuccess(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		try {
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email1");
			String phone = request.getParameter("phone");
			String age = request.getParameter("age");
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			String valfirst = firstName.replaceAll("[^\\dA-Za-z] ", "").replaceAll("\\s", " ").trim();
			String vallast = lastName.replaceAll("[^\\dA-Za-z] ", "").replaceAll("\\s", " ").trim();
			String valemail = email.replaceAll("[^\\dA-Za-z] ", "").replaceAll("\\s", " ").trim();
			String valphone = phone.replaceAll("[^\\dA-Za-z] ", "").replaceAll("\\s", " ").trim();
			String valage = age.replaceAll("[^\\dA-Za-z] ", "").replaceAll("\\s", " ").trim();

			int agenew = Integer.parseInt(valage);
			String pass = base64Encode(password);
			userDao.create(valfirst, vallast, valemail, valphone, agenew, username, pass, "user");

			mv.addObject("message", "Account Created Successfully");

		} catch (UserException e) {

		}
		mv.addObject("task", "addPerson");

		mv.setViewName("guest");

		return mv;
	}
	@RequestMapping(value = "/SignupUserProfile.htm", method = RequestMethod.GET)
	public void sinupusercheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try
		{
		String username = request.getParameter("username");
		PrintWriter  out = (PrintWriter)response.getWriter();
		if(!userDao.checkUser(username))
		{
			System.out.println("inside if");
		}
		else
		{
			System.out.println("inside else");
			out.println("Username already exist");
		}
		}
		catch(UserException e)
		{
			
			System.out.println("error in user");
		}
	}
}
