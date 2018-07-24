package com.neu.me;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.me.dao.ItemsDao;
import com.neu.me.pojo.Items;
import com.neu.me.pojo.useraccount;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/*")
public class HomeController {

	@Autowired
	@Qualifier("itemDao")
	ItemsDao itemDao;

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/loginfilter.htm", method = RequestMethod.GET)
	public String homefilter(@ModelAttribute("userAccount") useraccount userAccount, BindingResult result) {

		return "home";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@ModelAttribute("userAccount") useraccount userAccount, BindingResult result) {

		return "home";
	}

	@RequestMapping(value = "/home.htm", method = RequestMethod.GET)
	public String homeAllUser(@ModelAttribute("userAccount") useraccount userAccount, BindingResult result) {

		return "home";
	}

	@RequestMapping(value = "/userLogin.htm", method = RequestMethod.GET)
	public ModelAndView userHome() {
		ModelAndView mv = new ModelAndView();
		List<Items> itemlist = itemDao.getAll();

		mv.addObject("itemlist", itemlist);
		mv.setViewName("user/userHome");
		return mv;
	}

	@RequestMapping(value = "/expertHome.htm", method = RequestMethod.GET)
	public ModelAndView expertHome() {
		ModelAndView mv = new ModelAndView();
		List<Items> itemlist = itemDao.getAll();

		mv.addObject("itemlist", itemlist);
		mv.setViewName("expert/expertHome");
		return mv;
	}

	@RequestMapping(value = "/adminHome.htm", method = RequestMethod.GET)
	public ModelAndView adminHome() {
		ModelAndView mv = new ModelAndView();
		List<Items> itemlist = itemDao.getAll();

		mv.addObject("itemlist", itemlist);
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	protected String logout(@ModelAttribute("userAccount") useraccount userAccount, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if(session !=null)
		session.invalidate();

		return "home";

	}
}
