package starrail.map.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
		return dao.stationXY(station);
	}

	@Override
	public List<FoodVO> foodList(String station) throws Exception {

		System.out.println("누른곳의 역은 " + station);

		List<FoodVO> foodList = new ArrayList<FoodVO>();

		String food = station + "역 맛집";

		/*
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
				
		for(int i =0; i<totalNum; i++){
			System.out.println(start);
			jsonObject = (JSONObject) jsonParser.parse(useNaverAPI(food,start));
			JSONArray itemsArray = (JSONArray) jsonObject.get("items");
			System.out.println(itemsArray);
			
			for (int j = 0; j < itemsArray.size(); j++) 
			{
				FoodVO foodVO = new FoodVO();
				
				JSONObject entity = (JSONObject) itemsArray.get(j);
				
				foodVO.setTitle((String)entity.get("title"));
				foodVO.setTelephone((String)entity.get("telephone"));
				foodVO.setCategory((String)entity.get("category"));
				foodVO.setDescription((String)entity.get("description"));
				foodVO.setAddress((String)entity.get("address"));
				foodVO.setMapx((String)entity.get("mapx"));
				foodVO.setMapy((String)entity.get("mapy"));
				
				System.out.println("가게좌표" + foodVO.getMapx());
				
				foodList.add(foodVO);
			}
			start += 100;
			
			// 네이버 제공 최대 가능한 개수가 1000개라서...
			if(start > 1000){
				System.out.println("강제 종료");
				break;
			}
		}
		
		

		return foodList;
	}

	@Override
	public List<StayVO> stayList(String station) throws Exception {
		String stay = station + "역 숙박";
		return null;
	}

	@Override
	public List<TourVO> tourList(String station) throws Exception {
		String tour = station + "역 관광지";
		return null;
	}

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

	@Override
	public void dataLab(String station) {

		try {
			String apiURL = "https://openapi.naver.com/v1/datalab/search";
			String body = "{\"startDate\":\"2017-01-01\",\"endDate\":\"2017-04-30\",\"timeUnit\":\"month\",\"keywordGroups\":[{\"groupName\":\""
					+ station + "\",\"keywords\":[\"" + station + "\"]}],\"device\":\"pc\",\"gender\":\"f\"}";
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
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
