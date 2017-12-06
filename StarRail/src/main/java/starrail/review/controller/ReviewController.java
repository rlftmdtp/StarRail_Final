package starrail.review.controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import starrail.review.domain.ReviewVO;
import starrail.main.domain.UserVO;
import starrail.review.domain.Hash_SearchVO;
import starrail.review.domain.ReviewPageMaker;
import starrail.review.domain.ReviewSearchCriteria;
import starrail.review.persistence.ReviewDao;
import starrail.review.service.ReviewService;

@Controller
@RequestMapping("/review/*")
public class ReviewController {

	@Inject
	public ReviewService service;
	public ReviewDao dao;

	// 후기 글 작성하러 가는 창
	@RequestMapping(value = "/review_insert", method = RequestMethod.GET)
	public void insertReviewGET(ReviewVO review, Hash_SearchVO hash, String hashSearch, Model model) throws Exception {
		List<String> list = new ArrayList<String>();
		// 해시태그 전체 불러오기
		list = service.hashSearch();
		for (int i = 0; i < list.size(); i++) {
			list.set(i, "\"" + list.get(i) + "\"");
		}
		System.out.println("붙어오니? : " + list);
		model.addAttribute("list", list);
	}

	// 후기 게시물 등록하기 눌렀을 떄
	@RequestMapping(value = "/review_insert", method = RequestMethod.POST)
	public String insertReviewPOST(@ModelAttribute("review") ReviewVO review, Hash_SearchVO searchVO, HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("login");

		review.setM_id(userVO.getM_id());
		System.out.println(review.toString());

		//후기 게시판에 등록
		service.register(review);
		
		List<String> list = new ArrayList<String>();
		//해시태그 전체 불러오기
		list = service.hashSearch();
	
		List<String> listHash = new ArrayList<String>();
		
		//내 후기 글번호로 해시태그만 따로 저장
		listHash = service.hashtagInsert(review, searchVO);

		boolean check = false;
		for (int i = 0; i < listHash.size(); i++) {
			check = false;
			for (int j = 0; j < list.size(); j++) {
				System.out.println(list.get(j) +  " : " +  listHash.get(i));
				if (list.get(j).equals(listHash.get(i))) {
					check = true;
					service.updateHash(listHash.get(i));
				}
			}
			if (check == false) {
				service.insertHash(listHash.get(i));
				System.out.println(listHash.get(i) + " : ");
			}
		}

		return "redirect:/review/review_list";
	}

	// 전체 후기 리스트
	@RequestMapping(value = "/review_list", method = RequestMethod.GET)
	public void listReviewGET(@ModelAttribute("cri") ReviewSearchCriteria cri, Model model) throws Exception {

		model.addAttribute("list", service.listSearchCriteria(cri));
		ReviewPageMaker pageMaker = new ReviewPageMaker();
		pageMaker.setCri(cri);

		pageMaker.setTotalCount(service.listSearchCount(cri));
		model.addAttribute("pageMaker", pageMaker);
	}

	// 한개 상세보기 눌렀을 때
	@RequestMapping(value = "/review_detail", method = RequestMethod.GET)
	public void DetailReviewGET(@RequestParam("r_no") int r_no, @ModelAttribute("cri") ReviewSearchCriteria cri,
			Model model) throws Exception {
		System.out.println(service.myHash(r_no));
		model.addAttribute("hasgTag", service.myHash(r_no));
		model.addAttribute(service.read(r_no));
	}

	// 게시판 삭제
	@RequestMapping(value = "/review_remove", method = RequestMethod.GET)
	public String RemoveReviewGET(@RequestParam("r_no") int r_no, ReviewSearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		service.remove(r_no);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/review/review_list";
	}

	// 게시판 수정 전
	@RequestMapping(value = "/review_modify", method = RequestMethod.GET)
	public void ModifyReviewGET(@RequestParam("r_no") int r_no, @ModelAttribute("cri") ReviewSearchCriteria cri,
			Model model) throws Exception {
		model.addAttribute(service.read(r_no));
	}

	// 게시판 수정 후
	@RequestMapping(value = "/review_modify", method = RequestMethod.POST)
	public String ModifyReviewPOST(ReviewVO review, ReviewSearchCriteria cri, RedirectAttributes rttr)
			throws Exception {
		service.modify(review);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/review/review_list";
	}

	@RequestMapping("/getAttach/{r_no}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("r_no") Integer r_no) throws Exception {
		return service.getAttach(r_no);
	}

}
