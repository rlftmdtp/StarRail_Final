package starrail.review.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import starrail.review.domain.Member_RecommendVO;
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
	
	
	
	
	
	
// start 희정 코드 --------------------------------------------------------------------------------------------------	
		
		// 전체 후기 리스트 + 추천
		@RequestMapping(value = "/review_list", method = RequestMethod.GET)
		public void listReview_GET(@ModelAttribute("cri") ReviewSearchCriteria cri, Model model, HttpServletRequest request) throws Exception {
			
			// start 솔 코드
			model.addAttribute("list", service.listSearchCriteria(cri));
			
			ReviewPageMaker pageMaker = new ReviewPageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.listSearchCount(cri));
			
			model.addAttribute("pageMaker", pageMaker);
			// end 솔 코드


			//******************************************************

			
			// start 추천
			HttpSession session = request.getSession();
			int m_no;	
			String m_name;	
			List<Integer> list = new ArrayList<Integer>();
			List<Hash_SearchVO> tag_list = new ArrayList<Hash_SearchVO>();
			
			try {
				if(((UserVO) session.getAttribute("login")) != null){
					UserVO user =  (UserVO) session.getAttribute("login");		
					m_no = user.getM_no();
					
					list = service.preferList_service(m_no);
					tag_list = service.tagRecommend_service(list);
					System.out.println("컨트롤러 결과값 : " + tag_list);
					
					model.addAttribute("hashSearchVO", tag_list);
					model.addAttribute("m_name", user.getM_name());
					model.addAttribute("m_id", user.getM_id());
				}else{
					model.addAttribute("hashSearchVO", null);
					model.addAttribute("m_name", "?");
					model.addAttribute("m_id", null);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}		
			// end 추천
		}

		// 추천 태그 클릭 시 클릭한 태그가 존재하는 리뷰만 보여짐
		@RequestMapping(value = "/review_list", method = RequestMethod.POST)
		public @ResponseBody ResponseEntity<Map<Object,Object>> listReview_POST(@ModelAttribute("cri") ReviewSearchCriteria cri, Model model, @RequestParam String tag) throws Exception{
			List<ReviewVO> list = service.reviewRecommend_service(tag, cri);
			
			//System.out.println(cri);
			
			ReviewPageMaker pageMaker = new ReviewPageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.reviewRecommendCount_service(tag));
			
			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			
			//System.out.println(map.get("pageMaker").toString());
			
			ResponseEntity<Map<Object,Object>> entity = 
										new ResponseEntity<Map<Object,Object>>(map, HttpStatus.OK);	
			
			return entity;
		}

		
		
		// 한개 상세보기 눌렀을 때
		@RequestMapping(value = "/review_detail", method = RequestMethod.GET)
		public void DetailReview_GET(@RequestParam("r_no") int r_no, @RequestParam("m_id") String m_id, HttpServletRequest request,
				@ModelAttribute("cri") ReviewSearchCriteria cri, Model model) throws Exception {
			
			// start 솔
			model.addAttribute("hasgTag", service.myHash(r_no));
			model.addAttribute(service.read(r_no));
			// System.out.println(service.myHash(r_no));
			// end 솔
						
			
			//******************************************************
			HttpSession session = request.getSession();
			UserVO user =  (UserVO) session.getAttribute("login");		
			int m_no = user.getM_no();
			
			// start 추천			
		Member_RecommendVO mr = new Member_RecommendVO();
			mr.setMr_no(service.selectMr_no_service()+1);
			mr.setM_id(user.getM_id());
			mr.setR_no(r_no);
			
			int checkR_no = service.selectCheckR_no_service(mr).get(0);
			System.out.println("===" + checkR_no);
			
			// member_Recommend테이블에서 r_no가 중복되면 안됨 
			if(checkR_no == 2){
				System.out.println("****************컨트롤러");
				service.registMemberRecommend_service(mr);
			}
			else if(checkR_no == 1){
				service.updateMr_count_service(mr);
			}
			
			// 추천된 결과를 저장하는 리스트
			List<Integer> r_noList = service.list_reviewRecommend(m_no);
			System.out.println("$$$$$$$$$ : " + service.list_userBased_servie(r_noList));
			
			model.addAttribute("recommendList", service.list_userBased_servie(r_noList));
			// end 추천
		}
		
		
// end 희정 코드 --------------------------------------------------------------------------------------------------	
		
		
	

}
