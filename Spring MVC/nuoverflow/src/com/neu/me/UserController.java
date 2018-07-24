package com.neu.me;

import java.io.IOException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.security.crypto.codec.Base64;

import com.neu.me.dao.ItemsDao;
import com.neu.me.dao.PersonDao;
import com.neu.me.dao.UserDao;
import com.neu.me.exception.UserException;
import com.neu.me.pojo.Items;
import com.neu.me.pojo.Person;
import com.neu.me.pojo.useraccount;
import com.neu.me.validator.LoginValidator;

@Controller
@RequestMapping("/*user.htm")
public class UserController {

	@Autowired
	@Qualifier("regLoginValidator")
	LoginValidator loginValidator;

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	@Qualifier("itemDao")
	ItemsDao itemDao;

	@Autowired
	@Qualifier("personDao")
	PersonDao personDao;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(loginValidator);
	}

	@RequestMapping(value = "/loginsuccessuser.htm", method = RequestMethod.GET)
	protected String redirectToLogin(@ModelAttribute("userAccount") useraccount userAccount, BindingResult result) {
		return "home";
	}

	@RequestMapping(value = "/loginsuccessuser.htm", method = RequestMethod.POST)
	protected ModelAndView doSubmitAction(@ModelAttribute("userAccount") useraccount userAccount, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException, UserException {

		
		ModelAndView mv = new ModelAndView();
		loginValidator.validate(userAccount, result);
		if (result.hasErrors()) {
			mv.setViewName("home");

		} else {

		//	try {
				
				String pass = base64Encode(userAccount.getPassword());
System.out.println("pass" +pass + userAccount.getUsername());
			useraccount user = userDao.getUser(userAccount.getUsername(), pass);
				
//UserAccount user = userDao.getUser(, pass);

String checkcookie = request.getParameter("remember");
				//String userRole =  "admin" ; //
				String userRole = user.getRole();
				HttpSession session = request.getSession();
				session.setAttribute("username",  userAccount.getUsername());
				session.setAttribute("role", userRole);
				mv.addObject("usernames", user.getUsername());
			//	mv.addObject("usernames", "geetha");

				List<Items> itemlist = itemDao.getAll();
				mv.addObject("itemlist", itemlist);
			//	if (checkcookie != null) {
//					Cookie usernm = new Cookie("username", user.getUsername());
//					Cookie password = new Cookie("password", base64Decode(user.getPassword()));
//					response.addCookie(usernm);
//					response.addCookie(password);
//				}
				if (userRole.equalsIgnoreCase("admin")) {

					mv.setViewName("admin/adminHome");
				} else if (userRole.equalsIgnoreCase("expert")) {

					mv.setViewName("expert/expertHome");
				} else if (userRole.equalsIgnoreCase("user")) {

					mv.setViewName("user/userHome");
				}

	//	}
			//	catch (UserException e) {
//
//				mv.addObject("error", "Invalid Credential");
//				
//				mv.setViewName("home");
//			}
		}
		return mv;

	}

	@RequestMapping(value = "/adduser.htm", method = RequestMethod.GET)
	public ModelAndView home() {

		ModelAndView mv = new ModelAndView();
		try {
			List<useraccount> userList = userDao.getAll();
			mv.addObject("task", "addUser");
			mv.addObject("userList", userList);
			mv.setViewName("admin/adminHome");
		} catch (UserException e) {

		}
		return mv;
	}

	@RequestMapping(value = "/accountuser.htm", method = RequestMethod.GET)
	public ModelAndView accountSetting(@ModelAttribute("userAccount") useraccount userAccount,
			HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		ModelAndView mv = new ModelAndView();
		try {
			useraccount user = userDao.get(username);
			userAccount.setUsername(user.getUsername());
			userAccount.setPassword(user.getPassword());
			userAccount.setRole(user.getRole());
			mv.addObject("task", "addAccount");

			mv.setViewName("admin/adminHome");
		} catch (UserException e) {

		}
		return mv;
	}

	public static String base64Encode(String token) {
		byte[] encodedBytes = Base64.encode(token.getBytes());
		return new String(encodedBytes, Charset.forName("UTF-8"));
	}

	public static String base64Decode(String token) {
		byte[] encodedBytes = Base64.decode(token.getBytes());
		return new String(encodedBytes, Charset.forName("UTF-8"));
	}

	@RequestMapping(value = "/deleteuser.htm", method = RequestMethod.POST)
	public void deleteCat(HttpServletRequest request) {
		try {

			String userid = request.getParameter("userid");

			useraccount user = userDao.getById(Integer.parseInt(userid));
			user.setStatus("InActive");
			userDao.update(user);

		} catch (UserException e) {

		}
	}

	@RequestMapping(value = "/adduser.htm", method = RequestMethod.POST)
	public ModelAndView useraddform(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<useraccount> userlist = null;
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
			userDao.create(valfirst, vallast, valemail, valphone, agenew, username, pass, "expert");
			userlist = userDao.getAll();

			mv.addObject("message", "User Added Successfully");
			mv.addObject("userList", userlist);

		} catch (UserException e) {

			mv.addObject("message", "User Already Exist");
			mv.addObject("userList", userlist);

		}

		mv.addObject("task", "addUser");
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/edituser.htm", method = RequestMethod.POST)
	public ModelAndView catedit(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String userid = request.getParameter("userId");
		List<useraccount> userlist = null;
		try {
			String username = request.getParameter("username2");
			String password = request.getParameter("password2");
			String pass = base64Encode(password);
			useraccount user = userDao.getById(Integer.parseInt(userid));
			user.setUsername(username);
			user.setPassword(pass);
			user.setStatus("Active");
			userDao.update(user);
			userlist = userDao.getAll();

			mv.addObject("message", "User updated Successfully");
			mv.addObject("userList", userlist);

		} catch (UserException e) {

			mv.addObject("message", "Category Already Exist");
			mv.addObject("userList", userlist);

		}

		mv.addObject("task", "addUser");
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/Alluser.htm", method = RequestMethod.GET)
	public ModelAndView userallform() {
		ModelAndView mv = new ModelAndView();
		List<Person> personlist = new ArrayList<Person>();
		try {

			personlist = personDao.getAll();

			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < personlist.size(); i++) {
				if (personlist.get(i).getUserAccount().getRole().equalsIgnoreCase("admin")
						|| personlist.get(i).getUserAccount().getRole().equalsIgnoreCase("expert")) {
					JSONObject obj = new JSONObject();
					obj.put("firstname", personlist.get(i).getFirstName());
					obj.put("lastname", personlist.get(i).getLastName());
					obj.put("email", personlist.get(i).getEmail());
					obj.put("phone", personlist.get(i).getPhone());
					jsonArray.put(obj);
				}
			}

			String jsonString = jsonArray.toString().replaceAll("\"", "'");

			mv.addObject("personList", jsonString);

		} catch (UserException e) {

		}

		mv.addObject("task", "allPerson");
		mv.setViewName("guest");
		return mv;
	}

}
