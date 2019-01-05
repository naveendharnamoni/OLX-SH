package com.ts.olx.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ts.olx.dto.Category;
import com.ts.olx.dto.Interest;
import com.ts.olx.dto.Item;
import com.ts.olx.dto.Moderator;
import com.ts.olx.dto.SubCategory;
import com.ts.olx.dto.User;
import com.ts.olx.service.OLXService;

@Controller
public class OLXController {

	/*
	 * @RequestMapping("/home") public String home() { System.out.println(
	 * "ostunda !!!"); return "home";
	 * 
	 * }
	 */
	@Autowired
	OLXService olxService;
	
	@RequestMapping("/home")
	public ModelAndView home() {

		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("categoriesWithSubCategories", olxService.getCategoriesWithSubCategories());
		modelAndView.addObject("items_posted_date_desc", olxService.getAvailableItemsSortedByDateDesc());
		modelAndView.addObject("user", new User());
		return modelAndView;

	}
	
	public ModelAndView userHome(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("loggedUser");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("categoriesWithSubCategories", olxService.getCategoriesWithSubCategories());
		modelAndView.addObject("items_posted_date_desc", olxService.getAvailableItemsSortedByDateDesc());
		modelAndView.addObject("items_with_interests_posted_by",
				olxService.getItemsWithInterestByUser(user.getId()));
		modelAndView.addObject("items_with_interests_posted_date_desc",olxService.getItemsWithInterestsSortedByPostedDate());
		modelAndView.addObject("items_interest_shown_by_user",
				olxService.getItemsWithInterestByUser(user.getId()));
		modelAndView.addObject("loggedUser", user);
		return modelAndView;
		
	}

	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public ModelAndView signIn(@RequestParam("user_email") String email, @RequestParam("user_password") String password,
			@RequestParam("loginAs") String loginAs,HttpServletRequest request) {
		System.out.println("signIn");
		System.out.println(email);
		System.out.println(password);
		System.out.println(loginAs);
		if (loginAs.equals("user")) {
			User user = olxService.loginAsUser(email, password);
			ModelAndView modelAndView  = new ModelAndView("userHome");
			modelAndView.addObject("categoriesWithSubCategories", olxService.getCategoriesWithSubCategories());
			modelAndView.addObject("items_posted_date_desc", olxService.getAvailableItemsSortedByDateDesc());
			modelAndView.addObject("items_with_interests_posted_by",
					olxService.getItemsWithInterestByUser(user.getId()));
			modelAndView.addObject("items_with_interests_posted_date_desc",olxService.getItemsWithInterestsSortedByPostedDate());
			modelAndView.addObject("items_interest_shown_by_user", olxService.getItemsWithInterestByUser(user.getId()));				
			modelAndView.addObject("loggedUser", user);
			request.getSession().setAttribute("loggedUser", user);
			return modelAndView;
		}
		if (loginAs.equals("moderator")) {
			Moderator moderator = olxService.loginAsModerator(email, password);
			if (moderator.isAdmin()) {
				ModelAndView modelAndView =new ModelAndView("adminHome");
				modelAndView.addObject("categoriesWithSubCategories",
						olxService.getCategoriesWithSubCategories());
				modelAndView.addObject("items_posted_date_desc", olxService.getAvailableItemsSortedByDateDesc());
				//modelAndView.addObject("items_with_interests_posted_date_desc",
					//	olxService.getItemsWithInterestsSortedByPostedDate());
				modelAndView.addObject("item_with_no_approvals", olxService.getItemsWithNoApprovals());
				modelAndView.addObject("loggedModerator", moderator);
				request.getSession().setAttribute("loggedModerator", moderator);				
				return modelAndView;
			}else {
				ModelAndView modelAndView =new ModelAndView("moderatorHome");
				modelAndView.addObject("categoriesWithSubCategories",
						olxService.getCategoriesWithSubCategories());
				modelAndView.addObject("items_posted_date_desc", olxService.getAvailableItemsSortedByDateDesc());
				modelAndView.addObject("items_with_interests_posted_date_desc",
						olxService.getItemsWithInterestsSortedByPostedDate());
				modelAndView.addObject("item_with_no_approvals", olxService.getItemsWithNoApprovals());
				modelAndView.addObject("loggedModerator", moderator);
				request.getSession().setAttribute("loggedModerator", moderator);
				return modelAndView;
			}
				

		}
		return null;
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ModelAndView signUp(@ModelAttribute("user") User user, BindingResult bindingResult) {
		System.out.println(user);
		olxService.register(user);
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("categoriesWithSubCategories", olxService.getCategoriesWithSubCategories());
		modelAndView.addObject("items_posted_date_desc", olxService.getAvailableItemsSortedByDateDesc());
		return modelAndView.addObject(user);
	}
	
	@RequestMapping(value="/addCategory", method = RequestMethod.POST)
	public ModelAndView addCategory(@RequestParam("name") String name, @RequestParam("add_image") CommonsMultipartFile file ,HttpServletRequest request) {
		
		Moderator moderator = (Moderator)request.getSession().getAttribute("loggedModerator");
		System.out.println(moderator);
		Category category = new Category();
		category.setAddedBy(moderator);
		category.setIconPath(name+".jpg");
		category.setName(name);
		olxService.add(category);
		saveImage(file.getFileItem(), "categories",name+".jpg");
		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
	}
	@RequestMapping(value="/addSubCategory", method = RequestMethod.POST)
	public ModelAndView addSubCategory(@RequestParam("name") String name,@RequestParam("category_id") String category, @RequestParam("add_image") CommonsMultipartFile file,HttpServletRequest request) {
		
		Moderator moderator = (Moderator)request.getSession().getAttribute("loggedModerator");
		System.out.println(moderator);
		System.out.println(name);
		System.out.println(category);
		SubCategory subCategory = new SubCategory();
		subCategory.setAddedBy(moderator);
		Category category2=new Category();
		category2.setId(Integer.parseInt(category));
		subCategory.setCategory(category2);
		subCategory.setIconPath(name+".jpg");
		subCategory.setName(name);
		olxService.add(subCategory);
		saveImage(file.getFileItem(), "subcategories",name+".jpg");
		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
	}
	@RequestMapping(value="/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(@ModelAttribute("item") Item item, @RequestParam("subcategory_id") String subCategoryId,BindingResult bindingResult,@RequestParam("add_image") CommonsMultipartFile[] files,HttpServletRequest request) {
		System.out.println(item);
		User user = (User)request.getSession().getAttribute("loggedUser");
		System.out.println(user);
		SubCategory subCategory = new SubCategory();
		subCategory.setId(Integer.parseInt(subCategoryId));
		item.setSubCategory(subCategory);
		item.setPostedBy(user);
		item.setPostedDate(new java.util.Date());
		Item item1 = olxService.insert(item);
		int i=0;
		System.out.println(item1);
		for (CommonsMultipartFile commonsMultipartFile : files) {
			saveImage(commonsMultipartFile.getFileItem(), "items", item1.getId() + "_" + i + ".jpg");
			olxService.saveImage(item1.getId(), item1.getId() + "_" + i + ".jpg");
			i++;
		}
		ModelAndView modelAndView = new ModelAndView("userHome");
		return modelAndView;
	}

	private void saveImage(FileItem fileItem, String address, String fileName) {
		try {
			fileItem.write(new java.io.File(
					"C:/Users/welcome/Documents/naveen/workspace/springolx.zip_expanded/springolx/src/main/webapp/resources/" + address + "/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/addBid", method = RequestMethod.GET)
	public ModelAndView bidAmount(@RequestParam("item_id") String id,@RequestParam("bid") String bidAmount  ,HttpServletRequest request) {
		Double bidamount = Double.parseDouble(bidAmount);
		int itemId = Integer.parseInt(id);
		Interest interest = new Interest();
		if (bidamount != null) {
			interest.setBidAmount(bidamount);
			interest.setExpressedDate(new java.util.Date());
		}
		Item item = new Item();
		item.setId(itemId);
		interest.setItem(item);
		Object object = request.getSession().getAttribute("loggedUser");
		interest.setExpressedBy((User) object);
		interest = olxService.insertBidAmount(interest);
		User user = (User)request.getSession().getAttribute("loggedUser");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("categoriesWithSubCategories", olxService.getCategoriesWithSubCategories());
		modelAndView.addObject("items_posted_date_desc", olxService.getAvailableItemsSortedByDateDesc());
		modelAndView.addObject("items_with_interests_posted_by",

				olxService.getItemsWithInterestByUser(user.getId()));
		modelAndView.addObject("items_with_interests_posted_date_desc",olxService.getItemsWithInterestsSortedByPostedDate());
		modelAndView.addObject("items_interest_shown_by_user",
				olxService.getItemsWithInterestByUser(user.getId()));
		modelAndView.addObject("loggedUser", user);
		return modelAndView;
	}
	
	
	
	
	/*private void setUpData(ModelAndView modelAndView, Object... args) {
		for (Object object : args) {
			if (null != args) {
				String type = (String) object;
				if (type.equals("categories")) {
				}
				if (type.equals("categoriesWithSubCategories")) {
					modelAndView.addObject("categoriesWithSubCategories",
							olxService.getCategoriesWithSubCategories());
				}
				if (type.equals("items_with_interests_posted_date_desc")) {
					modelAndView.addObject("items_with_interests_posted_date_desc",
							olxService.getItemsWithInterestsSortedByPostedDate());

				}
				if (type.equals("items_posted_date_desc")) {
					System.out.println("items");
					modelAndView.addObject("items_posted_date_desc",
							olxService.getAvailableItemsSortedByDateDesc());
				}

				if (type.equals("items_with_interests_posted_by")) {
					User user = (User) request.getSession().getAttribute("loggedUser");
					List<Item> itemsList = olxService
							.getItemsWithInterestsSortedByPostedDatePostedBy(user.getId());
					request.setAttribute("items_with_interests_posted_by", itemsList);

				}
				if (type.equals("items_interest_shown_by_user")) {
					User user = (User) request.getSession().getAttribute("loggedUser");
					System.out.println(user.getId());
					List<Item> itemList = olxService.getItemsWithInterestByUser(user.getId());
					request.setAttribute("items_interest_shown_by_user", itemList);
				}
				if(type.equals("item_with_no_approvals")){
					Moderator moderator = (Moderator)request.getSession().getAttribute("loggedModerator");
					List<Item> itemsList = olxService.getItemsWithNoApprovals();
					request.setAttribute("item_with_no_approvals", itemsList);
				}

			}
		}

	}

*/	
	
}