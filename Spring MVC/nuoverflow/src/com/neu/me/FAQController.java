package com.neu.me;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.neu.me.dao.FAQDao;
import com.neu.me.exception.UserException;

import com.neu.me.pojo.FAQ;

import com.neu.me.validator.FAQValidator;

@Controller
@RequestMapping("/*FAQ.htm")
public class FAQController {

	@Autowired
	@Qualifier("faqDao")
	FAQDao faqDao;

	@Autowired
	@Qualifier("regFAQValidator")
	FAQValidator faqValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(faqValidator);
	}

	@RequestMapping(value = "/getFAQ.htm", method = RequestMethod.GET)
	public ModelAndView showfaq() {

		ModelAndView mv = new ModelAndView();
		List<FAQ> faqlist = faqDao.getAll();
		mv.addObject("task", "viewFAQ");
		mv.addObject("faqList", faqlist);
		mv.setViewName("user/userHome");
		return mv;
	}

	@RequestMapping(value = "/AllFAQ.htm", method = RequestMethod.GET)
	public ModelAndView showAllfaq() {

		ModelAndView mv = new ModelAndView();
		List<FAQ> faqlist = faqDao.getAll();
		mv.addObject("task", "viewFAQ");
		mv.addObject("faqList", faqlist);
		mv.setViewName("guest");
		return mv;
	}

	@RequestMapping(value = "/addFAQ.htm", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("faq") FAQ faq, BindingResult result, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String role = (String) session.getAttribute("role");
		ModelAndView mv = new ModelAndView();
		List<FAQ> faqlist = faqDao.getAll();
		mv.addObject("task", "addFAQ");
		mv.addObject("faqList", faqlist);
		if (role.equalsIgnoreCase("admin"))
			mv.setViewName("admin/adminHome");
		else if (role.equalsIgnoreCase("expert"))
			mv.setViewName("expert/expertHome");
		else
			mv.setViewName("user/userHome");

		return mv;
	}

	@RequestMapping(value = "/deleteFAQ.htm", method = RequestMethod.POST)
	public void deleteCat(HttpServletRequest request) {
		try {

			String faqid = request.getParameter("faqid");

			FAQ faq = faqDao.get(Integer.parseInt(faqid));
			faqDao.delete(faq);
		} catch (UserException e) {

		}

	}

	@RequestMapping(value = "/addFAQ.htm", method = RequestMethod.POST)
	public ModelAndView catform(@ModelAttribute("faq") FAQ faq, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(false);
		String role = (String) session.getAttribute("role");
		faqValidator.validate(faq, result);
		if (result.hasErrors()) {

		} else {
			List<FAQ> faqlist = null;

			try {
				faqDao.create(faq.getQuestion(), faq.getAnswer());
				faqlist = faqDao.getAll();
				System.out.println("addded successfully");
				mv.addObject("message", "FAQ Added Successfully");
				mv.addObject("faqList", faqlist);

			} catch (UserException e) {

				mv.addObject("message", "FAQ Alreay Exist");
				mv.addObject("faqList", faqlist);

			}
		}
		mv.addObject("task", "addFAQ");
		if (role.equalsIgnoreCase("admin"))
			mv.setViewName("admin/adminHome");
		else if (role.equalsIgnoreCase("expert"))
			mv.setViewName("expert/expertHome");
		else
			mv.setViewName("user/userHome");

		return mv;
	}

	@RequestMapping(value = "/editFAQ.htm", method = RequestMethod.POST)
	public ModelAndView catedit(@ModelAttribute("faq") FAQ faq, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(false);
		String role = (String) session.getAttribute("role");
		faqValidator.validate(faq, result);
		if (result.hasErrors()) {

		} else {
			String faqid = request.getParameter("faqId");
			List<FAQ> faqlist = null;
			try {
				FAQ faqs = faqDao.get(Integer.parseInt(faqid));
				faqs.setQuestion(faq.getQuestion());
				faqs.setAnswer(faq.getAnswer());

				faqDao.update(faqs);
				faqlist = faqDao.getAll();

				mv.addObject("message", "FAQ updated Successfully");
				mv.addObject("faqList", faqlist);

			} catch (UserException e) {

				mv.addObject("message", "FAQ Already Exist");
				mv.addObject("faqList", faqlist);

			}
		}
		mv.addObject("task", "addFAQ");
		if (role.equalsIgnoreCase("admin"))
			mv.setViewName("admin/adminHome");
		else if (role.equalsIgnoreCase("expert"))
			mv.setViewName("expert/expertHome");
		else
			mv.setViewName("user/userHome");

		return mv;
	}
}
