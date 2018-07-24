package com.neu.me;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.me.dao.CommentDao;
import com.neu.me.dao.JobsDao;
import com.neu.me.dao.ItemsDao;
import com.neu.me.dao.LikeDao;
import com.neu.me.dao.SubCategoryDao;
import com.neu.me.dao.UserDao;
import com.neu.me.exception.UserException;
import com.neu.me.pojo.Category;
import com.neu.me.pojo.Comments;
import com.neu.me.pojo.FAQ;
import com.neu.me.pojo.Jobs;
import com.neu.me.pojo.Items;
import com.neu.me.pojo.Like;
import com.neu.me.pojo.SubCategory;
import com.neu.me.pojo.useraccount;

@Controller
@RequestMapping("/*Items.htm")
public class ItemController {

	@Autowired
	@Qualifier("itemDao")
	ItemsDao itemDao;

	@Autowired
	@Qualifier("subcatDao")
	SubCategoryDao subcatDao;

	@Autowired
	@Qualifier("jobDao")
	JobsDao jobDao;

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	@Qualifier("likeDao")
	LikeDao likeDao;

	@Autowired
	@Qualifier("commentDao")
	CommentDao commentDao;

	@RequestMapping(value = "/addItems.htm", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			HttpSession session = request.getSession(false);
			String role = (String) session.getAttribute("role");
			String username = (String) session.getAttribute("username");

			useraccount uacc = userDao.get(username);

			List<Items> itemlist = itemDao.getByPersonid(uacc.getPerson().getPersonid());
			List<SubCategory> subcatList = subcatDao.getAll();
			List<Jobs> jobList = jobDao.getAll();
			mv.addObject("task", "additem");
			mv.addObject("itemlist", itemlist);
			mv.addObject("subcatList", subcatList);
			mv.addObject("jobList", jobList);
			if (role.equalsIgnoreCase("admin"))
				mv.setViewName("admin/adminHome");
			else if (role.equalsIgnoreCase("expert"))
				mv.setViewName("expert/expertHome");
			else
				mv.setViewName("user/userHome");

		} catch (UserException e) {
			mv.setViewName("home");
		}

		return mv;
	}

	@RequestMapping(value = "/commentItems.htm", method = RequestMethod.POST)
	public ModelAndView addcomment(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String userrole = null;
		try {

			HttpSession session = request.getSession(false);
			String username = (String) session.getAttribute("username");
			userrole = (String) session.getAttribute("role");
			String itemid = request.getParameter("itemid");
			String comment = request.getParameter("comment");
			System.out.println("comments before"+comment);
			String validComment = comment.replaceAll("[^-,.A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
								
			System.out.println("comments"+validComment);
			Items item = itemDao.get(Integer.parseInt(itemid));
			useraccount uacc = userDao.get(username);
			Comments com = new Comments(validComment, item, uacc.getPerson());
			item.getComments().add(com);
			itemDao.update(item);
			List<Items> itemlist = itemDao.getAll();

			mv.addObject("itemlist", itemlist);

		} catch (UserException e) {
			e.printStackTrace();

		}

		String viewname = userrole + "/" + userrole + "Home";
		mv.setViewName(viewname);
		return mv;
	}

	@RequestMapping(value = "/likeItems.htm", method = RequestMethod.POST)
	public void addlike(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String userrole = null;
		try {
			PrintWriter out = (PrintWriter) response.getWriter();
			HttpSession session = request.getSession(false);
			String username = (String) session.getAttribute("username");
			userrole = (String) session.getAttribute("role");
			String itemid = request.getParameter("itemid");

			Items item = itemDao.get(Integer.parseInt(itemid));
			useraccount uacc = userDao.get(username);
			boolean checkitemLike = itemDao.checkLikePresent(item, uacc.getPerson());

			if (!checkitemLike) {

				Like like = new Like(item, uacc.getPerson());
				item.getLike().add(like);
				like.setItemid(item);
				itemDao.update(item);
				out.println("Not Present");
			} else {
				Like like = likeDao.get(item, uacc.getPerson());
				likeDao.delete(like);

				Set<Like> likeset = new HashSet();
				likeset = item.getLike();
				for (Like like2 : likeset) {
					if (like2.getLikeid() == like.getLikeid()) {
						item.getLike().remove(like2);
					}
				}

				itemDao.update(item);

				out.println("Present");
			}

		} catch (UserException e) {
			e.printStackTrace();

		}

	}

	@RequestMapping(value = "/deleteItems.htm", method = RequestMethod.POST)
	public void deleteCat(HttpServletRequest request) {
		try {

			String itemid = request.getParameter("itemid");

			Items item = itemDao.get(Integer.parseInt(itemid));
			itemDao.delete(item);
		} catch (UserException e) {

		}
	}

	@RequestMapping(value = "/addItems.htm", method = RequestMethod.POST)
	public ModelAndView catform(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(false);
		String role = (String) session.getAttribute("role");
		String username = (String) session.getAttribute("username");
		List<Items> itemlist = null;
		List<SubCategory> subcatList = null;
		List<Jobs> jobList = null;
		try {
			String itemname = request.getParameter("itemName");
			String subcategory = request.getParameter("subcategory");
			String herbs = request.getParameter("herbs");
			String description = request.getParameter("description");
			String benifits = request.getParameter("benifits");
			String procedure = request.getParameter("procedure");

			String valitemname = itemname.replaceAll("[^-.,?A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
			String valdescription = description.replaceAll("[^-.,?A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
			String valbenifits = benifits.replaceAll("[^-.,?A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
			String valprocedure = procedure.replaceAll("[^-.,?A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();

			SubCategory subcat = subcatDao.get(Integer.parseInt(subcategory));
			Jobs job = jobDao.get(Integer.parseInt(herbs));
			useraccount user = userDao.get(username);
			itemDao.create(valitemname, subcat, job, valdescription, valbenifits, valprocedure,
					user.getPerson().getPersonid());
			itemlist = itemDao.getByPersonid(user.getPerson().getPersonid());
			subcatList = subcatDao.getAll();
			jobList = jobDao.getAll();
			mv.addObject("message", "Item Added Successfully");

			mv.addObject("itemlist", itemlist);
			mv.addObject("subcatList", subcatList);
			mv.addObject("jobList", jobList);

		} catch (UserException e) {

			mv.addObject("message", "Item Already Exist");

		}

		mv.addObject("task", "additem");
		if (role.equalsIgnoreCase("admin"))
			mv.setViewName("admin/adminHome");
		else if (role.equalsIgnoreCase("expert"))
			mv.setViewName("expert/expertHome");
		else
			mv.setViewName("user/userHome");
		return mv;
	}

	@RequestMapping(value = "/editItems.htm", method = RequestMethod.POST)
	public ModelAndView catedit(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String role = (String) session.getAttribute("role");
		String username = (String) session.getAttribute("username");
		ModelAndView mv = new ModelAndView();
		List<Items> itemlist = null;
		List<SubCategory> subcatList = null;
		List<Jobs> jobList = null;
		try {
			String itemid = request.getParameter("itemId");

			String itemname = request.getParameter("itemName");
			String subcategory = request.getParameter("subcategory");
			String herbs = request.getParameter("herbs");
			String description = request.getParameter("description");
			String benifits = request.getParameter("benifits");
			String procedure = request.getParameter("procedure");

			String valitemname = itemname.replaceAll("[^-.,A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
			String valdescription = description.replaceAll("[^-.,?A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
			String valbenifits = benifits.replaceAll("[^-.,?A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();
			String valprocedure = procedure.replaceAll("[^-.,?A-Za-z0-9 ]", "").replaceAll("\\s"," ").trim();

			SubCategory subcat = subcatDao.get(Integer.parseInt(subcategory));
			Jobs job = jobDao.get(Integer.parseInt(herbs));
			Items item = itemDao.get(Integer.parseInt(itemid));
			item.setBenefits(valbenifits);
			item.setDescription(valdescription);
			item.setHerbs(job);
			item.setProcedurestep(valprocedure);
			item.setSubcat(subcat);
			item.setItemName(valitemname);

			itemDao.update(item);
			useraccount user = userDao.get(username);
			itemlist = itemDao.getByPersonid(user.getPerson().getPersonid());
			subcatList = subcatDao.getAll();
			jobList = jobDao.getAll();

			mv.addObject("message", "Category updated Successfully");
			mv.addObject("itemlist", itemlist);
			mv.addObject("subcatList", subcatList);
			mv.addObject("herbList", jobList);

		} catch (UserException e) {

			mv.addObject("message", "Item Already Exist");

		}

		mv.addObject("task", "additem");
		if (role.equalsIgnoreCase("admin"))
			mv.setViewName("admin/adminHome");
		else if (role.equalsIgnoreCase("expert"))
			mv.setViewName("expert/expertHome");
		else
			mv.setViewName("user/userHome");
		return mv;
	}

	@RequestMapping(value = "/deleteCommentItems.htm", method = RequestMethod.POST)
	public void deleteComment(HttpServletRequest request) {
		// ModelAndView mv = new ModelAndView();
		try {

			String commentid = request.getParameter("commentid");

			Comments comment = commentDao.getById(Integer.parseInt(commentid));
			commentDao.delete(comment);

		} catch (UserException e) {

		}

	}
	
	@RequestMapping(value = "/searchItems.htm", method = RequestMethod.POST)
	public ModelAndView searchItems(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			System.out.println("inside search");
			HttpSession session = request.getSession(false);
			String role = (String) session.getAttribute("role");
			String itemdesc = request.getParameter("searchItem");
			List<Items> itemlist = null;
			if(itemdesc != null)
			{
			String validItemdesc = itemdesc.replaceAll("[^A-Za-z0-9]", "").replaceAll("\\s"," ").trim();
			itemlist = itemDao.searchItems(validItemdesc);
			System.out.println("inside search1"+role);
			
			}
			else
			{
				itemlist = itemDao.getAll();
			}
			mv.addObject("itemlist", itemlist);
			if (role.equalsIgnoreCase("admin"))
				mv.setViewName("admin/adminHome");
			else if (role.equalsIgnoreCase("expert"))
				mv.setViewName("expert/expertHome");
			else
				mv.setViewName("user/userHome");

			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return mv;
	}
}
