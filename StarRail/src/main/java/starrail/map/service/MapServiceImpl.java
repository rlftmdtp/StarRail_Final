package starrail.map.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.map.domain.BlogVO;
import starrail.map.domain.FoodVO;
import starrail.map.domain.Items;
import starrail.map.domain.NaverAPI;
import starrail.map.domain.StationXYVO;
import starrail.map.domain.StayVO;
import starrail.map.domain.TourVO;
import starrail.map.persistence.MapDAO;

@Service
public class MapServiceImpl implements MapService {

	@Inject
	private MapDAO dao;
	
	private StationXYVO stationXYVO;
	
	@Override
	public List<CourseVO> courseList(String m_id) {
		return dao.courseList(m_id);
	}
	
	@Override
	public List<CourseDetailVO> courseDetailList(String c_id) {
		return dao.courseDetailList(c_id);
	}

	@Override
	public StationXYVO stationXY(String station) {
		stationXYVO = dao.stationXY(station);
		return dao.stationXY(station);
	}

	@Override
	public List<FoodVO> foodList(String station) throws Exception {

		System.out.println("누른곳의 역은 " + station);

		List<FoodVO> foodList = new ArrayList<FoodVO>();

		String food = station + "역 맛집";

		/* RestTemplate
		 * ResponseEntity<NaverAPI> string = getJsonResult(food);
		 * 
		 * System.out.println(string.getBody()); System.out.println("전체 개수" +
		 * string.getBody().getTotal()); System.out.println("가게 이름" +
		 * string.getBody().getItems()[0]);
		 */
		
		// 초기 total값이 무엇인지 알아온다.
		JSONParser jsonParser = new JSONParser();
		int start = 1;
		JSONObject jsonObject = (JSONObject) jsonParser.parse(useNaverAPI(food,start));
		System.out.println("jsonObject " + jsonObject);
		Object total = (Object) jsonObject.get("total");
		Integer IntTotal = Integer.parseInt(total + "");
		int totalNum = IntTotal / 100;
				
		for(int i =0; i<=totalNum; i++){
			System.out.println(start);
			jsonObject = (JSONObject) jsonParser.parse(useNaverAPI(food,start));
			JSONArray itemsArray = (JSONArray) jsonObject.get("items");
			System.out.println(itemsArray);
			
			for (int j = 0; j < itemsArray.size(); j++) 
			{
				FoodVO foodVO = new FoodVO();
				
				JSONObject entity = (JSONObject) itemsArray.get(j);
				
				foodVO.setTitle(((String)entity.get("title")).replace("<b>", "").replace("</b>", ""));
				foodVO.setTelephone((String)entity.get("telephone"));
				foodVO.setCategory((String)entity.get("category"));
				foodVO.setDescription((String)entity.get("description"));
				foodVO.setAddress((String)entity.get("address"));
				foodVO.setMapx(Integer.parseInt((String)entity.get("mapx")));
				foodVO.setMapy(Integer.parseInt((String)entity.get("mapy")));
					
				foodList.add(foodVO);
			}
			start += 100;
			
			// 네이버 제공 최대 가능한 개수가 1000개라서...
			if(start > 500){
				System.out.println("강제 종료");
				break;
			}
		}
		return foodList;
	}

	@Override
	public List<StayVO> stayList(String station) throws Exception {
		
		List<StayVO> stayList = new ArrayList<StayVO>();
		String stay = station + "역 숙박";
		
		// 초기 total값이 무엇인지 알아온다.
				JSONParser jsonParser = new JSONParser();
				int start = 1;
				JSONObject jsonObject = (JSONObject) jsonParser.parse(useNaverAPI(stay,start));
				System.out.println("jsonObject " + jsonObject);
				Object total = (Object) jsonObject.get("total");
				Integer IntTotal = Integer.parseInt(total + "");
				int totalNum = IntTotal / 100;
				
				for(int i =0; i<=totalNum; i++){
					System.out.println(start);
					jsonObject = (JSONObject) jsonParser.parse(useNaverAPI(stay,start));
					JSONArray itemsArray = (JSONArray) jsonObject.get("items");
					System.out.println(itemsArray);
					
					for (int j = 0; j < itemsArray.size(); j++) 
					{
						StayVO stayVO = new StayVO();
						
						JSONObject entity = (JSONObject) itemsArray.get(j);
						
						stayVO.setTitle(((String)entity.get("title")).replace("<b>", "").replace("</b>", ""));
						stayVO.setTelephone((String)entity.get("telephone"));
						stayVO.setCategory((String)entity.get("category"));
						stayVO.setDescription((String)entity.get("description"));
						stayVO.setAddress((String)entity.get("address"));
						stayVO.setMapx(Integer.parseInt((String)entity.get("mapx")));
						stayVO.setMapy(Integer.parseInt((String)entity.get("mapy")));
							
						stayList.add(stayVO);
					}
					start += 100;
					
					// 네이버 제공 최대 가능한 개수가 1000개라서...
					if(start > 500){
						System.out.println("강제 종료");
						break;
					}
			}
				return stayList;	
	}

	@Override
	public List<TourVO> tourList(String station) throws Exception {
		List<TourVO> tourList = new ArrayList<TourVO>();
		String tour = station + "역 관광지";
		
		// 초기 total값이 무엇인지 알아온다.
				JSONParser jsonParser = new JSONParser();
				int start = 1;
				JSONObject jsonObject = (JSONObject) jsonParser.parse(useNaverAPI(tour,start));
				System.out.println("jsonObject " + jsonObject);
				Object total = (Object) jsonObject.get("total");
				Integer IntTotal = Integer.parseInt(total + "");
				int totalNum = IntTotal / 100;
				
				for(int i =0; i<=totalNum; i++){
					System.out.println(start);
					jsonObject = (JSONObject) jsonParser.parse(useNaverAPI(tour,start));
					JSONArray itemsArray = (JSONArray) jsonObject.get("items");
					System.out.println(itemsArray);
					
					for (int j = 0; j < itemsArray.size(); j++) 
					{
						TourVO tourVO = new TourVO();
						
						JSONObject entity = (JSONObject) itemsArray.get(j);
						
						tourVO.setTitle(((String)entity.get("title")).replace("<b>", "").replace("</b>", ""));
						tourVO.setTelephone((String)entity.get("telephone"));
						tourVO.setCategory((String)entity.get("category"));
						tourVO.setDescription((String)entity.get("description"));
						tourVO.setAddress((String)entity.get("address"));
						tourVO.setMapx(Integer.parseInt((String)entity.get("mapx")));
						tourVO.setMapy(Integer.parseInt((String)entity.get("mapy")));
							
						tourList.add(tourVO);
					}
					start += 100;
					
					// 네이버 제공 최대 가능한 개수가 1000개라서...
					if(start > 500){
						System.out.println("강제 종료");
						break;
					}
			}
				return tourList;
	}
	@Override
	public List<BlogVO> dataBlog(String title) throws Exception{
		
		List<BlogVO> blogList = new ArrayList<BlogVO>();
		 StringBuffer response = null;
		 
		try {
            String text = URLEncoder.encode(title, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text
            		+ "&display=100&sort=sim"; // json 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
		
		JSONParser jsonParser = new JSONParser();
		int start = 1;
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		//System.out.println("jsonObject " + jsonObject);
		Object total = (Object) jsonObject.get("total");
		Integer IntTotal = Integer.parseInt(total + "");
		int totalNum = IntTotal / 100;
				
		for(int i =0; i<totalNum; i++){
			System.out.println(start);
			jsonObject = (JSONObject) jsonParser.parse(response.toString());
			JSONArray itemsArray = (JSONArray) jsonObject.get("items");
			
			for (int j = 0; j < itemsArray.size(); j++) 
			{
				BlogVO blogVO = new BlogVO();
				
				JSONObject entity = (JSONObject) itemsArray.get(j);
				
				blogVO.setTitle(((String)entity.get("title")).replace("<b>", "").replace("</b>", "").replace("&amp", ""));
				blogVO.setDescription((String)entity.get("description"));
				blogVO.setBloggername((String)entity.get("bloggername"));
				blogVO.setBloggerlink((String)entity.get("bloggerlink"));
				blogVO.setPostdate((String)entity.get("postdate"));
				
				//System.out.println("블로그 내용 " + blogVO.getDescription());
				
				blogList.add(blogVO);
			}
			start += 100;
			
			// 네이버 제공 최대 가능한 개수가 1000개라서...
			if(start > 500){
				System.out.println("강제 종료");
				break;
			}
		}
		
		return blogList;
	}
	
	@Override
	public Double dataLab(String title) throws Exception {
		
		StringBuffer response = new StringBuffer();
		
		try {
			LocalDateTime now = LocalDateTime.now(); // 현재 시간
			LocalDateTime onemonthAgo = now.minusMonths(1); // 한달 전
			
			String startDate = onemonthAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String endDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
			
			System.out.println("startDate " + startDate);
			System.out.println("endDate " + endDate);
			
			String apiURL = "https://openapi.naver.com/v1/datalab/search";
			String body = "{\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"timeUnit\":\"month\",\"keywordGroups\":[{\"groupName\":\""
					+ title + "\",\"keywords\":[\"" + title + "\"]}],\"device\":\"pc\",\"gender\":\"m\"}";
			URL url = new URL(apiURL);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			con.setRequestProperty("Content-Type", "application/json");

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(body.getBytes());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());

		} catch (Exception e) {
			System.out.println(e);
		}
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
		JSONArray itemsArray = (JSONArray) jsonObject.get("results");
		JSONObject entity = (JSONObject) itemsArray.get(0);
		JSONArray data = (JSONArray)entity.get("data");
		System.out.println(data);
		JSONObject lastObject = (JSONObject)data.get(0);
		Double ratio = (Double)lastObject.get("ratio");
		System.out.println("남녀 성비 : " + ratio);
		
		return ratio;
	}
		
	// 네이버 검색 API 연동 메서드
	public String useNaverAPI(String kind,int start) {

		StringBuffer response = new StringBuffer();

		try {
			String text = URLEncoder.encode(kind, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/search/local?query=" + text + "&display=100&sort=comment&start=" + start; // json
																														// 결과
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
		return response.toString();
	}

	/*
	public ResponseEntity<NaverAPI> getJsonResult(String kind) throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("X-Naver-Client-Id", clientId);
		httpHeaders.add("X-Naver-Client-Secret", clientSecret);

		String url = "https://openapi.naver.com/v1/search/local?query=" + kind;

		// restTemplate.exchange(url, HttpMethod.GET, new
		// HttpEntity(httpHeaders), String.class);
		System.out.println(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(httpHeaders), String.class));
		return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(httpHeaders), NaverAPI.class);
	}
	*/

}
