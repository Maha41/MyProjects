package com.neu.me;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.me.dao.CategoryDao;
import com.neu.me.dao.SubCategoryDao;
import com.neu.me.exception.UserException;
import com.neu.me.pojo.Category;
import com.neu.me.pojo.SubCategory;
import com.neu.me.validator.SubCategoryValidator;

@Controller
@RequestMapping("/*SubCategory.htm")
public class SubCategoryController {

	@Autowired
	@Qualifier("subcatDao")
	SubCategoryDao subcatDao;

	@Autowired
	@Qualifier("regSubCategoryValidator")
	SubCategoryValidator subCategoryValidator;

	@Autowired
	@Qualifier("catDao")
	CategoryDao catDao;

	@RequestMapping(value = "/addSubCategory.htm", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("subcategory") SubCategory subcategory, HttpServletRequest request) {

		List<Category> catlist = new ArrayList<Category>();
		ModelAndView mv = new ModelAndView();
		catlist = catDao.getAll();

		mv.addObject("task", "addsubCat");
		mv.addObject("catList", catlist);
		mv.addObject("message", "");
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/viewSubCategory.htm", method = RequestMethod.POST)
	public void viewSubcat(@ModelAttribute("subcategory") SubCategory subcategory, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<SubCategory> subcatlist = new ArrayList<SubCategory>();
		try {
			String catid = request.getParameter("catid");

			Category cat = catDao.get(Integer.parseInt(catid));

			Iterator subcatIterator = cat.getSubcategory().iterator();

			while (subcatIterator.hasNext()) {
				SubCategory subcat = (SubCategory) subcatIterator.next();

				subcatlist.add(subcat);

			}

			JSONObject obj = new JSONObject();

			obj.put("subcatlist", subcatlist);

			PrintWriter out = response.getWriter();

			out.println(obj);

		} catch (UserException e) {

		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/addSubCategory.htm", method = RequestMethod.POST)
	public ModelAndView catform(@ModelAttribute("subcategory") SubCategory subcategory, BindingResult result,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		subCategoryValidator.validate(subcategory, result);
		List<Category> catlist = catDao.getAll();
		if (result.hasErrors()) {

		} else {
			try {
				String catid = request.getParameter("catid");

				Category cat = catDao.get(Integer.parseInt(catid));

				SubCategory subcat = subcatDao.create(subcategory.getSubCatName(), cat.getCatId());

				cat.getSubcategory().add(subcat);

				catDao.update(cat);

				mv.addObject("catList", catlist);
				mv.addObject("message", "SubCategory Added Successfully");

			} catch (UserException e) {

				mv.addObject("message", "SubCategory Alreay Exist");

				mv.addObject("catList", catlist);

			}
		}
		mv.addObject("task", "addsubCat");
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/deleteSubCategory.htm", method = RequestMethod.POST)
	public void deleteSubCat(HttpServletRequest request) {

		try {

			String subcatid = request.getParameter("subcatid");

			SubCategory subcat = subcatDao.get(Integer.parseInt(subcatid));
			subcatDao.delete(subcat);

		} catch (UserException e) {

		}

	}

	@RequestMapping(value = "/editSubCategory.htm", method = RequestMethod.POST)
	public ModelAndView catedit(@ModelAttribute("subcategory") SubCategory subcategory, BindingResult result,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		subCategoryValidator.validate(subcategory, result);
		if (result.hasErrors()) {

		} else {
			String subcatid = request.getParameter("subcategoryId");
			List<SubCategory> subcatlist = null;
			List<Category> catlist = catDao.getAll();
			try {
				SubCategory subcat = subcatDao.get(Integer.parseInt(subcatid));
				subcat.setSubCatName(subcategory.getSubCatName());
				;
				subcatDao.update(subcat);

				mv.addObject("message", "SubCategory updated Successfully");
				mv.addObject("catList", catlist);

			} catch (UserException e) {

				mv.addObject("message", "SubCategory Already Exist");
				mv.addObject("catList", catlist);

			}
		}
		mv.addObject("task", "addsubCat");
		mv.setViewName("admin/adminHome");
		return mv;
	}

}
