package com.neu.me;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.neu.me.dao.ItemsDao;
import com.neu.me.exception.UserException;
import com.neu.me.pojo.Items;
import com.neu.me.springview.PdfReportView;

@Controller
public class PdfReportController {

	@Autowired
	@Qualifier("itemDao")
	ItemsDao itemDao;

	@RequestMapping(value = "/report.pdf", method = RequestMethod.GET)
	public ModelAndView createReport(HttpServletRequest request) {
		View view = null;
		try {
			String itemid = request.getParameter("itemid");
			Items item = itemDao.get(Integer.parseInt(itemid));
			view = new PdfReportView(item);
		} catch (NumberFormatException e) {

		} catch (UserException ex) {

		}
		return new ModelAndView(view);
	}
}
