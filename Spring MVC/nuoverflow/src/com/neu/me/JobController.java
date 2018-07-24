package com.neu.me;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


import com.neu.me.dao.JobsDao;
import com.neu.me.exception.UserException;

import com.neu.me.pojo.Jobs;
import com.neu.me.validator.JobValidator;

@Controller
@RequestMapping("/*job.htm")
public class JobController {

	@Autowired
	@Qualifier("jobDao")
	JobsDao jobDao;

	@Autowired
	@Qualifier("regJobValidator")
	JobValidator jobValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(jobValidator);
	}

	@RequestMapping(value = "Alljob.htm", method = RequestMethod.GET)
	public ModelAndView guestjobPage(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		List<Jobs> jobList = jobDao.getAll();

		if (jobList == null) {
			mv.addObject("nodata", "No Data Found");
		} else {
			mv.addObject("jobList", jobList);
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			String role = (String) session.getAttribute("role");
			if (role.equalsIgnoreCase("user")) {
				mv.addObject("task", "seejob");
				mv.setViewName("user/userHome");
			} else if (role.equalsIgnoreCase("expert")) {
				mv.addObject("task", "viewjob");
				mv.setViewName("expert/expertHome");
			}
		} else {
			mv.addObject("task", "viewjob");

			mv.setViewName("guest");
		}
		return mv;
	}

	@RequestMapping(value = "/addjob.htm", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("job") Jobs jobs, BindingResult result) {

		ModelAndView mv = new ModelAndView();
		List<Jobs> jobList = jobDao.getAll();
		mv.addObject("task", "addjob");
		mv.addObject("jobList", jobList);
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/deletejob.htm", method = RequestMethod.POST)
	public void deleteJob(HttpServletRequest request) {
		try {

			String jobid = request.getParameter("jobid");

			Jobs job = jobDao.get(Integer.parseInt(jobid));
			jobDao.delete(job);
		} catch (UserException e) {

		}
		// mv.setViewName("admin/adminHome");
		// return mv;
	}

	@RequestMapping(value = "/addjob.htm", method = RequestMethod.POST)
	public ModelAndView catform(@ModelAttribute("job") Jobs job, BindingResult result, HttpServletRequest request)
			throws IllegalStateException, IOException {

		ModelAndView mv = new ModelAndView();
		jobValidator.validate(job, result);
		if (result.hasErrors()) {

		} else {
			List<Jobs> joblist = null;

			try {

				File file;

				String path = getClass().getResource("/").getPath().replace("WEB-INF/classes",
						"resources/images/jobs/");
				String fileurl = "";
				if (job.getCompanyImage() != null) {
					fileurl = System.currentTimeMillis() + job.getCompanyImage().getOriginalFilename();
					file = new File(path + fileurl);
				} else {
					fileurl = System.currentTimeMillis() + "noimage.jpg";
					file = new File(path + fileurl);

				}
				job.getCompanyImage().transferTo(file);
				job.setCompanylogo(fileurl);

				jobDao.create(job);
				joblist = jobDao.getAll();

				mv.addObject("message", "Job Added Successfully");
				mv.addObject("jobList", joblist);

			} catch (UserException e) {

				mv.addObject("message", "Job Already Exist");
				mv.addObject("jobList", joblist);

			}
		}
		mv.addObject("task", "addjob");
		mv.setViewName("admin/adminHome");
		return mv;
	}

	@RequestMapping(value = "/editjob.htm", method = RequestMethod.POST)
	public ModelAndView catedit(@ModelAttribute("job") Jobs job, BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
	jobValidator.validate(job, result);
		if (result.hasErrors()) {

		} else {
			String jobid = request.getParameter("jobid");
			List<Jobs> joblist = null;
			try {
				Jobs jobs = jobDao.get(Integer.parseInt(jobid));
				jobs.setJobdescription(job.getJobdescription());
				jobs.setJobName(job.getJobName());
				jobs.setCompanylogo(job.getCompanylogo());
				jobs.setCompanyname(job.getCompanyname());
				jobDao.update(jobs);
				joblist = jobDao.getAll();

				mv.addObject("message", "Job updated Successfully");
				mv.addObject("jobList", joblist);

			} catch (UserException e) {

				mv.addObject("message", "Job Already Exist");
				mv.addObject("jobbList", joblist);

			}
		}
		mv.addObject("task", "addjob");
		mv.setViewName("admin/adminHome");
		return mv;
	}
}
