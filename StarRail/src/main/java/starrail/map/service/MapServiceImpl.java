package starrail.map.service;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

import starrail.course.domain.CourseDetailVO;
import starrail.course.domain.CourseVO;
import starrail.map.controller.MapRestController;
import starrail.map.domain.BlogVO;
import starrail.map.domain.FestivalDetailVO;
import starrail.map.domain.FestivalVO;
import starrail.map.domain.FoodDetailVO;
import starrail.map.domain.FoodImage;
import starrail.map.domain.FoodVO;
import starrail.map.domain.LocationDTO;
import starrail.map.domain.StationXYVO;
import starrail.map.domain.StayDetailVO;
import starrail.map.domain.StayImage;
import starrail.map.domain.StayVO;
import starrail.map.domain.TourDetailVO;
import starrail.map.domain.TourImage;
import starrail.map.domain.TourVO;
import starrail.map.persistence.MapDAO;

@Service
public class MapServiceImpl implements MapService {

	@Inject
	private MapDAO dao;

	private StationXYVO stationXYVO;

	public REXP x = null;
	public RConnection c = null;
	public String retStr = "";

	@Override
	public List<FestivalVO> festival(String areaCode, String pageNo, String date) throws Exception {
		
		// String => Date형 변환
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date tempDate = dateFormat.parse(date);
		System.out.println("Date형 날짜" + tempDate);
		
		//해당 달의 마지막 날짜 구하기
		Calendar calendar = Calendar.getInstance();
		calendar.set(tempDate.getYear(),tempDate.getMonth(), tempDate.getDay());
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// Date => String 변환
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMM", Locale.KOREA);
		SimpleDateFormat startTransFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String afterDate = transFormat.format(tempDate);
		
		String startDate = startTransFormat.format(tempDate);
		String endDate = afterDate + lastDay;
		System.out.println("startDate" + startDate);
		System.out.println("endDate "+ endDate);
		
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder.append(
				"&" + URLEncoder.encode("eventStartDate", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8")); /* 페이지번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("eventEndDate", "UTF-8") + "=" + URLEncoder.encode(endDate, "UTF-8")); /* 한페이지결과수 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* AND(안드로이드),IOS(아이폰),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode,
				"UTF-8")); /*
							 * (A=제목순, B=조회순, C=수정일순, D=생성일순) , 대표이미지 정렬
							 * 추가(D=제목순, P=조회순, Q=수정일순, R=생성일순)
							 */
		urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("A02", "UTF-8")); /* 대분류 */
		urlBuilder.append("&" + URLEncoder.encode("listYN", "UTF-8") + "="
				+ URLEncoder.encode("Y", "UTF-8")); /* 관광타입(관광지, 숙박등) ID */
		urlBuilder.append(
				"&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P", "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /* 시군구코드 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /* 중분류 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println("축제 정보: " + sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("jsonObject " + jsonObject);
		
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");		
		JSONObject itmes = (JSONObject) jsonObject3.get("items");
		
		MapRestController.festivalTableTotal = (Long)jsonObject3.get("totalCount");
		System.out.println(MapRestController.festivalTableTotal);
		JSONArray items = (JSONArray) itmes.get("item");

		List<FestivalVO> festivalList = new ArrayList<FestivalVO>();
		System.out.println("items사이즈 " + items.size());
		for (int i = 0; i < items.size(); i++) {
			FestivalVO festivalVO = new FestivalVO();

			JSONObject entity = (JSONObject) items.get(i);
			festivalVO.setTitle((String) entity.get("title"));
			festivalVO.setTel((String) entity.get("tel"));
			festivalVO.setAddr1((String) entity.get("addr1"));
			festivalVO.setContentid((Long) entity.get("contentid"));
			festivalVO.setFirstimage((String) entity.get("firstimage"));
			festivalVO.setMapy(Double.parseDouble(((Object) entity.get("mapx")).toString()));
			festivalVO.setMapy(Double.parseDouble(((Object) entity.get("mapy")).toString()));

			festivalList.add(festivalVO);
		}

		return festivalList;
	}
	@Override
	public FestivalDetailVO festivaldetail(String contentid) throws Exception {
		System.out.println(contentid);
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("SERVICE_KEY", "UTF-8")); /*서비스인증*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
        urlBuilder.append("&" + URLEncoder.encode("contentId","UTF-8") + "=" + URLEncoder.encode(contentid, "UTF-8")); /*콘텐츠 ID*/
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*관광타입(관광지, 숙박 등) ID*/
        urlBuilder.append("&" + URLEncoder.encode("defaultYN","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*기본정보 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("firstImageYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*원본, 썸네일 대표이미지 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("areacodeYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역코드, 시군구코드 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("catcodeYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*서비스분류코드(대,중,소 코드) 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("addrinfoYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*주소, 상세주소 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("mapinfoYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*좌표 X,Y 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("overviewYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*콘텐츠 개요 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println("축제 상세 정보 "+sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("jsonObject " + jsonObject);
		
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");		
		JSONObject itmes = (JSONObject) jsonObject3.get("items");
		
		MapRestController.festivalTableTotal = (Long)jsonObject3.get("totalCount");
		JSONObject items = (JSONObject) itmes.get("item");

		FestivalDetailVO festivalDetailVO = new FestivalDetailVO();
		
			festivalDetailVO.setAddr1((String) items.get("addr1"));
			festivalDetailVO.setFirstimage((String) items.get("firstimage"));
			festivalDetailVO.setHomepage((String) items.get("homepage"));
			festivalDetailVO.setOverview((String) items.get("overview"));
			festivalDetailVO.setTel((String) items.get("overview"));
			festivalDetailVO.setTelname((String) items.get("telname"));
			festivalDetailVO.setTitle((String) items.get("title"));
		

		return festivalDetailVO;
	}
	
	@Override
	public String Ranalysis() throws RserveException, REXPMismatchException {

		c = new RConnection();
		x = c.eval("1+2");
		retStr = x.asString();

		System.out.println("R데이터 프로그램 결과 " + retStr);

		// 마지막에 끊어줘야 한다?
		c.close();

		return null;
	}

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
	public List<FoodVO> foodList(String areaCode) throws Exception {

		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한페이지결과수 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* AND(안드로이드),IOS(아이폰),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P",
				"UTF-8")); /*
							 * (A=제목순, B=조회순, C=수정일순, D=생성일순) , 대표이미지 정렬
							 * 추가(D=제목순, P=조회순, Q=수정일순, R=생성일순)
							 */
		urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("A05", "UTF-8")); /* 대분류 */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("39", "UTF-8")); /* 관광타입(관광지, 숙박등) ID */
		urlBuilder.append(
				"&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 시군구코드 */
		urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 중분류 */
		urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 소분류 */
		urlBuilder
				.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /* 목록구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("jsonObject " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");
		JSONArray items = (JSONArray) itmes.get("item");

		List<FoodVO> foodList = new ArrayList<FoodVO>();
		System.out.println("items사이즈 " + items.size());
		for (int i = 0; i < items.size(); i++) {
			FoodVO foodVO = new FoodVO();

			JSONObject entity = (JSONObject) items.get(i);
			foodVO.setTitle((String) entity.get("title"));
			foodVO.setTel((String) entity.get("tel"));
			foodVO.setAddr1((String) entity.get("addr1"));
			foodVO.setContentid((Long) entity.get("contentid"));
			foodVO.setContenttypeid((Long) entity.get("contenttypeid"));
			foodVO.setFirstimage((String) entity.get("firstimage"));
			foodVO.setMapx((Double) entity.get("mapx"));
			foodVO.setMapy(Double.parseDouble(((Object) entity.get("mapy")).toString()));

			foodList.add(foodVO);
		}

		return foodList;
	}

	@Override
	public List<FoodVO> foodTableList(String areaCode, String pageNo) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder.append(
				"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /* 페이지번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한페이지결과수 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* AND(안드로이드),IOS(아이폰),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P",
				"UTF-8")); /*
							 * (A=제목순, B=조회순, C=수정일순, D=생성일순) , 대표이미지 정렬
							 * 추가(D=제목순, P=조회순, Q=수정일순, R=생성일순)
							 */
		urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("A05", "UTF-8")); /* 대분류 */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("39", "UTF-8")); /* 관광타입(관광지, 숙박등) ID */
		urlBuilder.append(
				"&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 시군구코드 */
		urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 중분류 */
		urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 소분류 */
		urlBuilder
				.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /* 목록구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("jsonObject " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");

		MapRestController.foodTableTotal = (Long) jsonObject3.get("totalCount");
		System.out.println("totalCount: " + MapRestController.foodTableTotal);
		JSONArray items = (JSONArray) itmes.get("item");

		List<FoodVO> foodList = new ArrayList<FoodVO>();
		System.out.println("items사이즈 " + items.size());
		for (int i = 0; i < items.size(); i++) {
			FoodVO foodVO = new FoodVO();

			JSONObject entity = (JSONObject) items.get(i);
			foodVO.setTitle((String) entity.get("title"));
			foodVO.setTel((String) entity.get("tel"));
			foodVO.setAddr1((String) entity.get("addr1"));
			foodVO.setContentid((Long) entity.get("contentid"));
			foodVO.setContenttypeid((Long) entity.get("contenttypeid"));
			foodVO.setFirstimage((String) entity.get("firstimage"));
			foodVO.setMapx(Double.parseDouble(((Object) entity.get("mapx")).toString()));
			foodVO.setMapy(Double.parseDouble(((Object) entity.get("mapy")).toString()));

			foodList.add(foodVO);
		}

		return foodList;
	}

	@Override
	public Map<String, Object> foodDetail(String contentid) throws Exception {

		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과수 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8")); /* 현재 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "="
				+ URLEncoder.encode(contentid, "UTF-8")); /* 콘텐츠 ID */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("39", "UTF-8")); /* 관광타입(관광지, 숙박 등) ID */
		urlBuilder.append("&" + URLEncoder.encode("introYN", "UTF-8") + "="
				+ URLEncoder.encode("Y", "UTF-8")); /* 소개정보 조회여부 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("jsonObject " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");
		JSONObject items = (JSONObject) itmes.get("item");

		FoodDetailVO foodDetailVO = new FoodDetailVO();

		foodDetailVO.setFirstmenu((String) items.get("firstmenu"));
		foodDetailVO.setTreatmenu((String) items.get("treatmenu"));
		foodDetailVO.setInfocenterfood((String) items.get("infocenterfood"));
		foodDetailVO.setOpentimefood((String) items.get("opentimefood"));
		foodDetailVO.setReservationfood((String) items.get("reservationfood"));
		foodDetailVO.setRestdatefood((String) items.get("restdatefood"));

		urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("SERVICE_KEY", "UTF-8")); /*공공데이터포털에서 발급받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
        urlBuilder.append("&" + URLEncoder.encode("contentId","UTF-8") + "=" + URLEncoder.encode(contentid, "UTF-8")); /*콘텐츠 ID*/
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode("39", "UTF-8")); /*관광타입(관광지,숙박 등) ID*/
        urlBuilder.append("&" + URLEncoder.encode("imageYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*Y=콘텐츠 이미지 조회, N='음식점'타입의 음식메뉴 이미지*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
        url = new URL(urlBuilder.toString());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        sb = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    
		JSONParser jsonParserB = new JSONParser();
		JSONObject jsonObjectB = (JSONObject) jsonParserB.parse(sb.toString());
		System.out.println("jsonObject " + jsonObjectB);

		JSONObject jsonObject2B = (JSONObject) jsonObjectB.get("response");
		JSONObject jsonObject3B = (JSONObject) jsonObject2B.get("body");
		JSONObject itmesB = (JSONObject) jsonObject3B.get("items");
		JSONArray itemsB = (JSONArray) itmesB.get("item");

		List<FoodImage> foodImageList = new ArrayList<FoodImage>();
		System.out.println("items사이즈 " + itemsB.size());

		for (int i = 0; i < itemsB.size(); i++) {
			FoodImage foodImage = new FoodImage();

			JSONObject entity = (JSONObject) itemsB.get(i);
			foodImage.setSmallimageurl((String)entity.get("originimgurl"));

			foodImageList.add(foodImage);
		}
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("foodDetailVO", foodDetailVO);
		map.put("foodImageList", foodImageList);
		
		return map;
	}

	@Override
	public List<StayVO> stayList(String areaCode) throws Exception {

		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한페이지결과수 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* AND(안드로이드),IOS(아이폰),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P",
				"UTF-8")); /*
							 * (A=제목순, B=조회순, C=수정일순, D=생성일순) , 대표이미지 정렬
							 * 추가(D=제목순, P=조회순, Q=수정일순, R=생성일순)
							 */
		urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("B02", "UTF-8")); /* 대분류 */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("32", "UTF-8")); /* 관광타입(관광지, 숙박등) ID */
		urlBuilder.append(
				"&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 시군구코드 */
		urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 중분류 */
		urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 소분류 */
		urlBuilder
				.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /* 목록구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("숙소정보 " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");
		JSONArray items = (JSONArray) itmes.get("item");

		List<StayVO> stayList = new ArrayList<StayVO>();
		System.out.println("items사이즈 " + items.size());
		for (int i = 0; i < items.size(); i++) {
			StayVO stayVO = new StayVO();

			JSONObject entity = (JSONObject) items.get(i);
			stayVO.setTitle((String) entity.get("title"));
			stayVO.setTel((String) entity.get("tel"));
			stayVO.setAddr1((String) entity.get("addr1"));
			stayVO.setContentid((Long) entity.get("contentid"));
			stayVO.setContenttypeid((Long) entity.get("contenttypeid"));
			stayVO.setFirstimage((String) entity.get("firstimage"));
			stayVO.setMapx(Double.parseDouble(((Object) entity.get("mapx")).toString()));
			stayVO.setMapy(Double.parseDouble(((Object) entity.get("mapy")).toString()));

			stayList.add(stayVO);
		}

		return stayList;
	}
	@Override
	public List<StayVO> stayTableList(String areaCode, String pageNo) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder.append(
				"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /* 페이지번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한페이지결과수 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* AND(안드로이드),IOS(아이폰),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P",
				"UTF-8")); /*
							 * (A=제목순, B=조회순, C=수정일순, D=생성일순) , 대표이미지 정렬
							 * 추가(D=제목순, P=조회순, Q=수정일순, R=생성일순)
							 */
		urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("B02", "UTF-8")); /* 대분류 */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("32", "UTF-8")); /* 관광타입(관광지, 숙박등) ID */
		urlBuilder.append(
				"&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 시군구코드 */
		urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 중분류 */
		urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 소분류 */
		urlBuilder
				.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /* 목록구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println("숙소:" + sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("숙소jsonObject " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");

		MapRestController.stayTableTotal = (Long) jsonObject3.get("totalCount");
		System.out.println("totalCount: " + MapRestController.stayTableTotal);
		JSONArray items = (JSONArray) itmes.get("item");

		List<StayVO> stayList = new ArrayList<StayVO>();
		System.out.println("items사이즈 " + items.size());
		for (int i = 0; i < items.size(); i++) {
			StayVO stayVO = new StayVO();

			JSONObject entity = (JSONObject) items.get(i);
			stayVO.setTitle((String) entity.get("title"));
			stayVO.setTel((String) entity.get("tel"));
			stayVO.setAddr1((String) entity.get("addr1"));
			stayVO.setContentid((Long) entity.get("contentid"));
			stayVO.setContenttypeid((Long) entity.get("contenttypeid"));
			stayVO.setFirstimage((String) entity.get("firstimage"));
			stayVO.setMapx(Double.parseDouble(((Object) entity.get("mapx")).toString()));
			stayVO.setMapy(Double.parseDouble(((Object) entity.get("mapy")).toString()));

			stayList.add(stayVO);
		}

		return stayList;
	}
	@Override
	public Map<String,Object> stayDetail(String contentid) throws Exception {

		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과수 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8")); /* 현재 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "="
				+ URLEncoder.encode(contentid, "UTF-8")); /* 콘텐츠 ID */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("32", "UTF-8")); /* 관광타입(관광지, 숙박 등) ID */
		urlBuilder.append("&" + URLEncoder.encode("introYN", "UTF-8") + "="
				+ URLEncoder.encode("Y", "UTF-8")); /* 소개정보 조회여부 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("숙소정보 " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");
		JSONObject items = (JSONObject) itmes.get("item");

		StayDetailVO stayDetailVO = new StayDetailVO();

		stayDetailVO.setCheckintime((String) items.get("checkintime"));
		stayDetailVO.setCheckouttime((String) items.get("checkouttime"));
		stayDetailVO.setReservationlodging((String) items.get("reservationlodging"));
		stayDetailVO.setReservationurl((String) items.get("reservationurl"));
		stayDetailVO.setRoomcount((String) items.get("roomcount"));
		stayDetailVO.setFirstimage((String) items.get("firstimage"));

		urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("SERVICE_KEY", "UTF-8")); /*공공데이터포털에서 발급받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
        urlBuilder.append("&" + URLEncoder.encode("contentId","UTF-8") + "=" + URLEncoder.encode(contentid, "UTF-8")); /*콘텐츠 ID*/
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode("32", "UTF-8")); /*관광타입(관광지,숙박 등) ID*/
        urlBuilder.append("&" + URLEncoder.encode("imageYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*Y=콘텐츠 이미지 조회, N='음식점'타입의 음식메뉴 이미지*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
        url = new URL(urlBuilder.toString());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        sb = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    
		JSONParser jsonParserB = new JSONParser();
		JSONObject jsonObjectB = (JSONObject) jsonParserB.parse(sb.toString());
		System.out.println("jsonObject " + jsonObjectB);

		JSONObject jsonObject2B = (JSONObject) jsonObjectB.get("response");
		JSONObject jsonObject3B = (JSONObject) jsonObject2B.get("body");
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(jsonObject3B.get("items") instanceof String)
		{
			String itmesB = (String) jsonObject3B.get("items");
			map.put("stayDetailVO", stayDetailVO);
		}
		else{
			JSONObject itmesB = (JSONObject) jsonObject3B.get("items");
			JSONArray itemsB = (JSONArray) itmesB.get("item");

			List<StayImage> stayImageList = new ArrayList<StayImage>();
			System.out.println("items사이즈 " + itemsB.size());

			for (int i = 0; i < itemsB.size(); i++) {
				StayImage stayImage = new StayImage();

				JSONObject entity = (JSONObject) itemsB.get(i);
				stayImage.setSmallimageurl((String)entity.get("originimgurl"));

				stayImageList.add(stayImage);
			}
			map.put("stayDetailVO", stayDetailVO);
			map.put("stayImageList", stayImageList);
		}
		return map;
	}

	@Override
	public List<TourVO> tourList(String areaCode) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한페이지결과수 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* AND(안드로이드),IOS(아이폰),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P",
				"UTF-8")); /*
							 * (A=제목순, B=조회순, C=수정일순, D=생성일순) , 대표이미지 정렬
							 * 추가(D=제목순, P=조회순, Q=수정일순, R=생성일순)
							 */
		urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("A01", "UTF-8")); /* 대분류 */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("12", "UTF-8")); /* 관광타입(관광지, 숙박등) ID */
		urlBuilder.append(
				"&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 시군구코드 */
		urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 중분류 */
		urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 소분류 */
		urlBuilder
				.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /* 목록구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("숙소정보 " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");
		JSONArray items = (JSONArray) itmes.get("item");

		List<TourVO> tourList = new ArrayList<TourVO>();
		System.out.println("items사이즈 " + items.size());
		for (int i = 0; i < items.size(); i++) {
			TourVO tourVO = new TourVO();

			JSONObject entity = (JSONObject) items.get(i);
			tourVO.setTitle((String) entity.get("title"));
			tourVO.setTel((String) entity.get("tel"));
			tourVO.setAddr1((String) entity.get("addr1"));
			tourVO.setContentid((Long) entity.get("contentid"));
			tourVO.setContenttypeid((Long) entity.get("contenttypeid"));
			tourVO.setFirstimage((String) entity.get("firstimage"));
			tourVO.setMapx(Double.parseDouble(((Object) entity.get("mapx")).toString()));
			tourVO.setMapy(Double.parseDouble(((Object) entity.get("mapy")).toString()));

			tourList.add(tourVO);
		}

		return tourList;
	}
	@Override
	public List<TourVO> tourTableList(String areaCode, String pageNo) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder.append(
				"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /* 페이지번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한페이지결과수 */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* AND(안드로이드),IOS(아이폰),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("arrange", "UTF-8") + "=" + URLEncoder.encode("P",
				"UTF-8")); /*
							 * (A=제목순, B=조회순, C=수정일순, D=생성일순) , 대표이미지 정렬
							 * 추가(D=제목순, P=조회순, Q=수정일순, R=생성일순)
							 */
		urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode("A01", "UTF-8")); /* 대분류 */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("12", "UTF-8")); /* 관광타입(관광지, 숙박등) ID */
		urlBuilder.append(
				"&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8")); /* 지역코드 */
		urlBuilder.append(
				"&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 시군구코드 */
		urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 중분류 */
		urlBuilder.append("&" + URLEncoder.encode("cat3", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 소분류 */
		urlBuilder
				.append("&" + URLEncoder.encode("listYN", "UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /* 목록구분 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println("관광지 정보 :" + sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("관광지jsonObject " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");

		MapRestController.tourTableTotal = (Long) jsonObject3.get("totalCount");
		System.out.println("totalCount: " + MapRestController.tourTableTotal);
		JSONArray items = (JSONArray) itmes.get("item");

		List<TourVO> tourList = new ArrayList<TourVO>();
		System.out.println("items사이즈 " + items.size());
		for (int i = 0; i < items.size(); i++) {
			TourVO tourVO = new TourVO();

			JSONObject entity = (JSONObject) items.get(i);
			tourVO.setTitle((String) entity.get("title"));
			tourVO.setTel((String) entity.get("tel"));
			tourVO.setAddr1((String) entity.get("addr1"));
			tourVO.setContentid((Long) entity.get("contentid"));
			tourVO.setContenttypeid((Long) entity.get("contenttypeid"));
			tourVO.setFirstimage((String) entity.get("firstimage"));
			tourVO.setMapx(Double.parseDouble(((Object) entity.get("mapx")).toString()));
			tourVO.setMapy(Double.parseDouble(((Object) entity.get("mapy")).toString()));

			tourList.add(tourVO);
		}

		return tourList;
	}
	@Override
	public Map<String,Object> tourDetail(String contentid) throws Exception {

		StringBuilder urlBuilder = new StringBuilder(
				"http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
				+ "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*
																																	 * Service
																																	 * Key
																																	 */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode("SERVICE_KEY", "UTF-8")); /* 서비스인증 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과수 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
				+ URLEncoder.encode("1", "UTF-8")); /* 현재 페이지 번호 */
		urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC",
				"UTF-8")); /* IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC */
		urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "="
				+ URLEncoder.encode("AppTest", "UTF-8")); /* 서비스명=어플명 */
		urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "="
				+ URLEncoder.encode(contentid, "UTF-8")); /* 콘텐츠 ID */
		urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "="
				+ URLEncoder.encode("12", "UTF-8")); /* 관광타입(관광지, 숙박 등) ID */
		urlBuilder.append("&" + URLEncoder.encode("introYN", "UTF-8") + "="
				+ URLEncoder.encode("Y", "UTF-8")); /* 소개정보 조회여부 */
		urlBuilder.append(
				"&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
		System.out.println("관광지 정보 " + jsonObject);

		JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("body");
		JSONObject itmes = (JSONObject) jsonObject3.get("items");
		JSONObject items = (JSONObject) itmes.get("item");

		TourDetailVO tourDetailVO = new TourDetailVO();

		tourDetailVO.setInfocenter((String)items.get("infocenter"));
		tourDetailVO.setUseseason((String)items.get("useseason"));
		tourDetailVO.setUsetime((String)items.get("usetime"));

		urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=1aoRBeuItrem6Z%2BBaZby%2F8f7OkZgr3%2BpMjm2d%2FJT%2F%2FPpQx4r5NzVMW%2FZssiuRUYkC%2Bkty0PGjfDPgZllc7QQPQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("SERVICE_KEY", "UTF-8")); /*공공데이터포털에서 발급받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
        urlBuilder.append("&" + URLEncoder.encode("contentId","UTF-8") + "=" + URLEncoder.encode(contentid, "UTF-8")); /*콘텐츠 ID*/
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode("12", "UTF-8")); /*관광타입(관광지,숙박 등) ID*/
        urlBuilder.append("&" + URLEncoder.encode("imageYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*Y=콘텐츠 이미지 조회, N='음식점'타입의 음식메뉴 이미지*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 목록구분 */
        url = new URL(urlBuilder.toString());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        sb = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    
		JSONParser jsonParserB = new JSONParser();
		JSONObject jsonObjectB = (JSONObject) jsonParserB.parse(sb.toString());
		System.out.println("jsonObject " + jsonObjectB);

		JSONObject jsonObject2B = (JSONObject) jsonObjectB.get("response");
		JSONObject jsonObject3B = (JSONObject) jsonObject2B.get("body");
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(jsonObject3B.get("items") instanceof String)
		{
			String itmesB = (String) jsonObject3B.get("items");
			map.put("tourDetailVO", tourDetailVO);
		}
		else{
			JSONObject itmesB = (JSONObject) jsonObject3B.get("items");
			JSONArray itemsB = (JSONArray) itmesB.get("item");

			List<TourImage> tourImageList = new ArrayList<TourImage>();
			System.out.println("items사이즈 " + itemsB.size());

			for (int i = 0; i < itemsB.size(); i++) {
				TourImage tourImage = new TourImage();

				JSONObject entity = (JSONObject) itemsB.get(i);
				tourImage.setSmallimageurl((String)entity.get("originimgurl"));

				tourImageList.add(tourImage);
			}
			map.put("tourDetailVO", tourDetailVO);
			map.put("tourImageList", tourImageList);
		}
		return map;
	}
	
	@Override
	public void addLocation(LocationDTO dto) {
		dao.addLocation(dto); 
	}

	@Override
	public List<BlogVO> dataBlog(String title) throws Exception {

		List<BlogVO> blogList = new ArrayList<BlogVO>();
		StringBuffer response = null;

		try {
			String text = URLEncoder.encode(title, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=100&sort=sim"; // json
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
		// System.out.println("jsonObject " + jsonObject);
		Object total = (Object) jsonObject.get("total");
		Integer IntTotal = Integer.parseInt(total + "");
		int totalNum = IntTotal / 100;

		for (int i = 0; i < totalNum; i++) {
			System.out.println(start);
			jsonObject = (JSONObject) jsonParser.parse(response.toString());
			JSONArray itemsArray = (JSONArray) jsonObject.get("items");

			for (int j = 0; j < itemsArray.size(); j++) {
				BlogVO blogVO = new BlogVO();

				JSONObject entity = (JSONObject) itemsArray.get(j);

				blogVO.setTitle(
						((String) entity.get("title")).replace("<b>", "").replace("</b>", "").replace("&amp", ""));
				blogVO.setDescription((String) entity.get("description"));
				blogVO.setBloggername((String) entity.get("bloggername"));
				blogVO.setBloggerlink((String) entity.get("bloggerlink"));
				blogVO.setPostdate((String) entity.get("postdate"));

				// System.out.println("블로그 내용 " + blogVO.getDescription());

				blogList.add(blogVO);
			}
			start += 100;

			// 네이버 제공 최대 가능한 개수가 1000개라서...
			if (start > 500) {
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
			String body = "{\"startDate\":\"" + startDate + "\",\"endDate\":\"" + endDate
					+ "\",\"timeUnit\":\"month\",\"keywordGroups\":[{\"groupName\":\"" + title + "\",\"keywords\":[\""
					+ title + "\"]}],\"device\":\"pc\",\"gender\":\"m\"}";
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
		JSONArray data = (JSONArray) entity.get("data");
		System.out.println(data);
		JSONObject lastObject = (JSONObject) data.get(0);
		Double ratio = (Double) lastObject.get("ratio");
		System.out.println("남녀 성비 : " + ratio);

		return ratio;
	}

	// 네이버 검색 API 연동 메서드
	public String useNaverAPI(String kind, int start) {

		StringBuffer response = new StringBuffer();

		try {
			String text = URLEncoder.encode(kind, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/search/local?query=" + text
					+ "&display=100&sort=comment&start=" + start; // json
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
	 * public ResponseEntity<NaverAPI> getJsonResult(String kind) throws
	 * Exception { RestTemplate restTemplate = new RestTemplate();
	 * 
	 * HttpHeaders httpHeaders = new HttpHeaders();
	 * httpHeaders.add("X-Naver-Client-Id", clientId);
	 * httpHeaders.add("X-Naver-Client-Secret", clientSecret);
	 * 
	 * String url = "https://openapi.naver.com/v1/search/local?query=" + kind;
	 * 
	 * // restTemplate.exchange(url, HttpMethod.GET, new //
	 * HttpEntity(httpHeaders), String.class);
	 * System.out.println(restTemplate.exchange(url, HttpMethod.GET, new
	 * HttpEntity(httpHeaders), String.class)); return
	 * restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(httpHeaders),
	 * NaverAPI.class); }
	 */
	
}
