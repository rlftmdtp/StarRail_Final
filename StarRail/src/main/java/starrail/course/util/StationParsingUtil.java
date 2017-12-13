package starrail.course.util;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.map.util.Comparators;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import starrail.course.domain.StationVO;
import starrail.course.domain.TrainTimeVO;

@Component
public class StationParsingUtil {

	public List<String> getCityCode() throws Exception{	//역 목록 불러올 때 쓸 지역코드
		String xmlAddr ="http://openapi.tago.go.kr/openapi/service/TrainInfoService/getCtyCodeList?ServiceKey=TGcClofLdZE%2B0HSLqvPVVLrixz8HFOrgUW2yZUuIASicA0%2BIDXMxYLiT3MCirXZQ2xG%2Bfedyb38VIDSGlB3yzQ%3D%3D";
		
		URL url = new URL(xmlAddr);
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		xpp.setInput(bis, "utf-8");
		
		
		int eventType = xpp.getEventType();
		
		ArrayList<String> list = new ArrayList<String>();
		String tag = null;
		String str = null;
		
		while(eventType != XmlPullParser.END_DOCUMENT){
			if(eventType == XmlPullParser.START_TAG){
				tag = xpp.getName();
			} else if (eventType == XmlPullParser.TEXT){
				if(tag.equals("citycode")){
					str = xpp.getText();
				}
			} else if (eventType ==XmlPullParser.END_TAG){
				tag = xpp.getName();
				if(tag.equals("item")){
					list.add(str);
				}
			}
			
			eventType=xpp.next();
		}
		
		return list;
	}
	
	public List<StationVO> stationList(List<String> citycode) throws Exception{	//지역코드 이용해서 전체 역 목록 뽑기
		String xmlAddr = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/getCtyAcctoTrainSttnList?serviceKey=TGcClofLdZE%2B0HSLqvPVVLrixz8HFOrgUW2yZUuIASicA0%2BIDXMxYLiT3MCirXZQ2xG%2Bfedyb38VIDSGlB3yzQ%3D%3D&cityCode=";
		List<StationVO> list = new ArrayList<StationVO>();
		
		
		
		for(int i=0; i<citycode.size(); i++){
			
			URL url = new URL(xmlAddr+citycode.get(i));
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			xpp.setInput(bis, "utf-8");
			
			String tag = null;
			int eventType = xpp.getEventType();
			String s_id=null;
			String s_name=null;
			
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				StationVO station = new StationVO();
				if(eventType == XmlPullParser.START_TAG){
					tag = xpp.getName();
				} else if (eventType == XmlPullParser.TEXT){
					if(tag.equals("nodeid")){
						s_id=xpp.getText();
						
					} else if(tag.equals("nodename")){
						s_name=xpp.getText();
					}
				} else if (eventType ==XmlPullParser.END_TAG){
					tag = xpp.getName();
					if(tag.equals("item")){
						station.setId(s_id);
						station.setName(s_name);
						list.add(station);
						
					}
				}
				
				eventType=xpp.next();
			}
		}
	
		Collections.sort(list, new StationNameComparator());
		return list;
	}
	
	
	public List<StationVO> arrStationList(String depNode, String depDate, List<StationVO> allNodes) throws Exception{	//선택 가능한 도착역 불러오기
		
		String depPlaceId="&depPlaceId="+depNode;	//출발역ID
		
		String[] arrDepDate=depDate.split("/");
		
		if(arrDepDate[1].length()<2){arrDepDate[1] = "0"+arrDepDate[1];}	//1~9월이면 앞에 0 추가
		if(arrDepDate[2].length()<2){arrDepDate[2] = "0"+arrDepDate[2];}	//1~9일이면 앞에 0 추가
		
		String depPlandTime="&depPlandTime="+arrDepDate[0]+arrDepDate[1]+arrDepDate[2];	//출발일 (n일차 버튼 value)
		
		List<StationVO> arrPlaceIds= allNodes;	//도착역ID (위 리스트 몽땅 넣어줘야 함!)		
		
		String xmlOrg = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo?serviceKey=TGcClofLdZE%2B0HSLqvPVVLrixz8HFOrgUW2yZUuIASicA0%2BIDXMxYLiT3MCirXZQ2xG%2Bfedyb38VIDSGlB3yzQ%3D%3D&numOfRows=200"
							+ depPlaceId + depPlandTime + "&arrPlaceId=";
		
		List<String> arrNames = new ArrayList<String>();
		List<StationVO> list = new ArrayList<StationVO>();
		
		for(int i=0; i<arrPlaceIds.size(); i++){
				
				
			String xmlAddr = xmlOrg + arrPlaceIds.get(i).getId();
			URL url = new URL(xmlAddr);
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			xpp.setInput(bis, "utf-8");
			
			String tag = null;
			int eventType = xpp.getEventType();
			String arrName = null;
			String trainType = null;
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				StationVO station = new StationVO();
				if(eventType == XmlPullParser.START_TAG){
					tag = xpp.getName();
				} else if (eventType == XmlPullParser.TEXT){
					if(tag.equals("arrplacename")){
						arrName=xpp.getText();
					} else if (tag.equals("traingradename")){
						trainType=xpp.getText();
					}
				} else if (eventType ==XmlPullParser.END_TAG){
					tag = xpp.getName();
					if(tag.equals("item")){
						if(trainType.equals("무궁화호") || trainType.equals("새마을호") || trainType.equals("누리로")){
							if(!(arrNames.contains(arrName))){
								arrNames.add(arrName);
								station.setId(arrPlaceIds.get(i).getId());
								station.setName(arrName);
								list.add(station);
							}
						}
					}
				}
				
				eventType=xpp.next();
			}
			
		
		
	}
	
	Collections.sort(list, new StationNameComparator());
	return list;
	}
	
	
	public List<TrainTimeVO> getTimeTable(String depNode, String depDate, String arrNode, int selectedTime) throws Exception{
		
		String depPlaceId="&depPlaceId="+depNode;	//출발역ID

		String[] arrDepDate=depDate.split("/");
		
		if(arrDepDate[1].length()<2){arrDepDate[1] = "0"+arrDepDate[1];}	//1~9월이면 앞에 0 추가
		if(arrDepDate[2].length()<2){arrDepDate[2] = "0"+arrDepDate[2];}	//1~9일이면 앞에 0 추가
		
		String depPlandTime="&depPlandTime="+arrDepDate[0]+arrDepDate[1]+arrDepDate[2];	//출발일 (n일차 버튼 value)
		
		String arrPlaceId="&arrPlaceId="+arrNode;	//도착역ID
		
		String hopingTime = "";
		
		if(selectedTime <10){hopingTime = "0"+selectedTime+"0000";}
		else{hopingTime = selectedTime+"0000";}
		
		
		String xmlOrg = "http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo?serviceKey=TGcClofLdZE%2B0HSLqvPVVLrixz8HFOrgUW2yZUuIASicA0%2BIDXMxYLiT3MCirXZQ2xG%2Bfedyb38VIDSGlB3yzQ%3D%3D&numOfRows=200"
							+ depPlaceId + depPlandTime + arrPlaceId;
		
		List<TrainTimeVO> list = new ArrayList<TrainTimeVO>();
		
		URL url = new URL(xmlOrg);
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		xpp.setInput(bis, "utf-8");
		
		String tag = null;
		int eventType = xpp.getEventType();
		String arrTime=null;
		String depTime=null;
		String trainType=null;
		
		while(eventType != XmlPullParser.END_DOCUMENT){
			TrainTimeVO vo = new TrainTimeVO();
			if(eventType == XmlPullParser.START_TAG){
				tag = xpp.getName();
			} else if (eventType == XmlPullParser.TEXT){
				if(tag.equals("arrplandtime")){
					arrTime=xpp.getText();
				} else if(tag.equals("depplandtime")){
					depTime=xpp.getText();
				} else if(tag.equals("traingradename")){
					trainType=xpp.getText();
				}
			} else if (eventType ==XmlPullParser.END_TAG){
				tag = xpp.getName();
				if(tag.equals("item")){
					if(trainType.equals("무궁화호") || trainType.equals("새마을호") || trainType.equals("누리로")){
						if(Long.parseLong(arrDepDate[0]+arrDepDate[1]+arrDepDate[2]+hopingTime)-20000 <= Long.parseLong(depTime)
								&&Long.parseLong(depTime)<=Long.parseLong(arrDepDate[0]+arrDepDate[1]+arrDepDate[2]+hopingTime)+20000){	//int의 범위를 벗어나는 숫자이므로 long으로 파싱
							
							arrTime = arrTime.substring(8, 12);
							depTime = depTime.substring(8, 12);
							
							StringBuffer sb1 = new StringBuffer(arrTime);
							StringBuffer sb2 = new StringBuffer(depTime);
							
							sb1.insert(2, ":");
							sb2.insert(2, ":");
							
							arrTime = sb1.toString();
							depTime = sb2.toString();
							
							vo.setArrTime(arrTime);
							vo.setDepTime(depTime);
							vo.setTrainType(trainType);
							
							list.add(vo);	
						}
					}
				}
			}
			
			eventType=xpp.next();
		}
		
	return list;
	}
	
	
/*	public static void main(String[] args){
		StationParsingUtil a = new StationParsingUtil();
		try {
			List<String> list = a.stationList(a.getCityCode());
			
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
