package com.neu.me;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.neu.me.dao.CategoryDao;
import com.neu.me.exception.UserException;
import com.neu.me.pojo.Category;
import com.neu.me.validator.CategoryValidator;

@Controller
@RequestMapping("/*Category.htm")
public class CategoryController {

	@Autowired
	@Qualifier("catDao")
	CategoryDao catDao;

	@Autowired
	@Qualifier("regCategoryValidator")
	CategoryValidator categoryValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(categoryValidator);
	}

	@RequestMapping(value = "/addCategory.htm", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("category") Category category, BindingResult result) {
		List<Category> catlist = null;
		ModelAndView mv = new ModelAndView();
		catlist = catDao.getAll();
		mv.addObject("task", "addCat");
		mv.addObject("catList", catlist);
		mv.addObject("message", "");
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/deleteCategory.htm", method = RequestMethod.POST)
	public void deleteCat(HttpServletRequest request) {
	
		try {
		
			String catid = request.getParameter("catid");
		
			Category cat = catDao.get(Integer.parseInt(catid));
			catDao.delete(cat);
			
		} 
		catch (UserException e) {
	
		}
		
	}

	@RequestMapping(value = "/addCategory.htm", method = RequestMethod.POST)
	public ModelAndView catform(@ModelAttribute("category") Category category, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		categoryValidator.validate(category, result);
		if (result.hasErrors()) {


		} else {
			List<Category> catlist = null;

			try {
				catDao.create(category.getCategoryName());
				catlist = catDao.getAll();

				mv.addObject("message", "Category Added Successfully");
				mv.addObject("catList", catlist);

			} catch (UserException e) {

				mv.addObject("message", "Category Alreay Exist");
				mv.addObject("catList", catlist);


			}
		}
		mv.addObject("task", "addCat");
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/editCategory.htm", method = RequestMethod.POST)
	public ModelAndView catedit(@ModelAttribute("category") Category category, BindingResult result,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		categoryValidator.validate(category, result);
		if (result.hasErrors()) {

		} else {
			String catid = request.getParameter("categoryId");
			List<Category> catlist = null;
			try {
				Category cat = catDao.get(Integer.parseInt(catid));
				cat.setCategoryName(category.getCategoryName());
				catDao.update(cat);
				catlist = catDao.getAll();

				mv.addObject("message", "Category updated Successfully");
				mv.addObject("catList", catlist);

			} catch (UserException e) {

				mv.addObject("message", "Category Already Exist");
				mv.addObject("catList", catlist);


			}
		}
		mv.addObject("task", "addCat");
		mv.setViewName("admin/adminHome");
		return mv;
	}
}
